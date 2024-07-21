/**
 * 本作品使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权。
 * Copyright (c) 2016 SiCheng.Net
 * This software is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.front.interceptor;

import com.sicheng.admin.store.entity.StoreDomain;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.Servlets;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.seller.store.service.StoreDomainService;
import com.sicheng.seller.sys.service.SysVariableService;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>标题: 店铺二级域名过滤器</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年8月24日 下午3:18:00
 */
public class StoreDomainFilter implements Filter {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 前缀
     * 给店铺分配的默认二级域名的格式是:shop128,统一shop开头，128是店铺的ID。
     */
    protected static String DOMAIN_LEVEL_2_PREFIX = "shop";

    /**
     * 保留的二级域名
     * 本系统可能多个子系统组成，有可能为每一个子系统分配一个二级域名，作为系统的主入口。
     * 这些二级域名需要保留，不能给会员作为店铺的二级域名使用
     */
    protected static String[] DOMAIN_LEVEL_2_RETAIN = new String[]{"www", "seller", "member", "sso", "admin", "upload", "static"};

    /**
     * 特殊域名
     */
    protected static String[] DOMAIN_SPECIAL = new String[]{"com.cn", "gov.cn", "edu.cn"};


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 如果是静态文件，则放过
        String url = request.getRequestURL().toString();
        if (Servlets.isStaticFile(url)) {
            chain.doFilter(request, response);
            return;
        }

        //取出完整的域名
        String domain = request.getServerName();
        //取出二级域名
        String domain_level_2 = getDomainLevel2(domain);

        // 如果无二级域名，放过
        if (domain_level_2 == null) {
            chain.doFilter(request, response);
            return;
        }

        //遇到保留的二级域名要放过
        for (String s : DOMAIN_LEVEL_2_RETAIN) {
            if (s.equalsIgnoreCase(domain_level_2)) {
                //放过
                chain.doFilter(request, response);
                return;
            }
        }

        // 如果访问的是非店铺首页，则重写到主店域名下
        // http://www.shop.com/  表示访问大首页，允许访问，放过
        // http://www.shop.com/xxx.htm  表示访问xxx，允许访问，放过
        // http://shop1.shop.com/  表示访问店铺首页，允许访问，转发到www.shop.com/store/1.htm
        // http://shop1.shop.com/xxx.htm  表示非店铺首页，允许访问，要重定向到 http://www.shop.com/xxx.htm
        String a = request.getRequestURI();
        String b = request.getQueryString();
        String c = request.getScheme();
        int d = request.getServerPort();
        if (!"/".equals(a)) {
            //取出一级域名（有二级缓存来保证性能）
            String domain_level_1 = getD(domain);
            //前面加一个点
            if (!domain_level_1.startsWith(".")) {
                domain_level_1 = "." + domain_level_1;
            }
            StringBuilder sbl = new StringBuilder();
            sbl.append(c);
            sbl.append("://");
            sbl.append("www");
            sbl.append(domain_level_1);
            if (d != 80) {
                sbl.append(":" + d);
            }
            sbl.append(a);
            if (b != null) {
                sbl.append("?");
                sbl.append(b);

            }
            try {//重定向
                response.sendRedirect(sbl.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //chain.doFilter(request, response);
            return;
        }

        //开始处理二级域名
        //从二级域名中截取店铺ID
        //给店铺分配的默认二级域名的格式是:shop128,统一shop开头，128是店铺的ID。
        String storeId = null;
        if (domain_level_2.startsWith(DOMAIN_LEVEL_2_PREFIX)) {
            storeId = domain_level_2.substring(DOMAIN_LEVEL_2_PREFIX.length());
        }

        if (storeId == null) {
            //非默认的二级域名格式
            //要查数据库，查出店铺ID(二级缓存)
            StoreDomainService service = SpringContextHolder.getBean(StoreDomainService.class);
            StoreDomain sd = new StoreDomain();
            sd.setDomain(domain_level_2);
            StoreDomain sd2 = service.selectOne(new Wrapper(sd));
            if (sd2 != null) {
                Long storeId_long = sd2.getStoreId();
                storeId = storeId_long.toString();
            }
        }

        if (storeId == null) {
            //没有查出来店铺ID，放过
            chain.doFilter(request, response);
            return;
        }

        if (!NumberUtils.isNumber(storeId)) {
            //店铺ID，类型不对
            chain.doFilter(request, response);
            return;
        }

        //取后缀，网站前台、会员中心的URL后缀。
        String urlSuffix = Global.getUrlSuffix();
        if (urlSuffix == null) {
            urlSuffix = ".htm";
        }

        //转发，url Rewrite
        //转发到某个controller ,会再走spring mvc自己的Interceptor，但不会走servlet的层的Filter。
        String forwardUrl = "/store/" + storeId + urlSuffix;//店铺首页
        request.getRequestDispatcher(forwardUrl).forward(request, response);
        return;
    }

    @Override
    public void destroy() {
    }

    /*
     * 从系统变量表中获取本站点的 一级域名
     * @return
     */
    private String getSiteDomain() {
        SysVariableService sysVariableService = SpringContextHolder.getBean(SysVariableService.class);
        SysVariable sysVariable = sysVariableService.getSysVariable("site_domain");
        if (sysVariable != null) {
            return sysVariable.getValue();
        }
        return null;
    }

    /**
     * 从一个完整域名中，取出二级部分
     * 域名的定级，一个域名中有几个点，就是几级域名。
     * 163.com中有一个点，就是一级域名
     * www.163.com中有两个点，就是二级域名
     * 本方法，取出www部分
     * <p>
     * 在af.info.163.com中取出了af.info部分，这是不支持的，返回null
     *
     * @param domain 完整域名
     * @return
     */
    private String getDomainLevel2(String domain) {
        if (StringUtils.isBlank(domain)) {
            return null;
        }
        if ("127.0.0.1".equals(domain)) {
            return null;
        }
        if ("localhost".equalsIgnoreCase(domain)) {
            return null;
        }

        //取出一级域名
        String domain_level_1 = getD(domain);

        //前面加一个点
        if (!domain_level_1.startsWith(".")) {
            domain_level_1 = "." + domain_level_1;
        }

        String domain_level_2 = null;
        if (domain.toLowerCase().endsWith(domain_level_1.toLowerCase())) {
            int a = domain.length();
            int b = domain_level_1.length();
            if (b > a) {
                return null;
            }
            domain_level_2 = domain.substring(0, domain.length() - domain_level_1.length());
        }
        if (domain_level_2 == null) {
            return null;
        }

        if (domain_level_2.contains(".")) {
            //说明还有3级域名,例如,在af.info.163.com中取出了af.info部分，这是不支持的
            return null;
        }

        return domain_level_2;
    }

    /*
     * 从系统变量表中获取本站点的 一级域名 ,
     * 如果取不到，自行计算出一个来
     * @return
     */
    private String getD(String domain) {
        //取出一级域名（有二级缓存来保证性能）
        String domain_level_1 = getSiteDomain();
        if (domain_level_1 == null) {
            //throw new RuntimeException("系统缺少必要的运行参数，请检查系统变量表，配置网站的一级域名，siteDomain变量");
            boolean bl = false;
            for (String s : DOMAIN_SPECIAL) {
                if (domain.toLowerCase().endsWith(s.toLowerCase())) {
                    //说明是  www.autohome.com.cn 这种域名
                    bl = true;
                    break;
                }
            }
            if (bl) {
                //说明是  www.autohome.com.cn 这种域名
                String[] arr = domain.split("\\.");
                domain_level_1 = arr[arr.length - 3] + "." + arr[arr.length - 2] + "." + arr[arr.length - 1];
            } else {
                //说明是  www.autohome.com 这种域名
                String[] arr = domain.split("\\.");
                domain_level_1 = arr[arr.length - 2] + "." + arr[arr.length - 1];
            }
        }
        return domain_level_1;
    }

}