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
package com.sicheng.wap.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 服务端使用CorsFilter来实现CORS跨域的支持.
 * 同源策略（same origin policy）是浏览器安全的基石。在同源策略的限制下，非同源的网站之间不能发送ajax请求的。
 * 为了解决这个问题，w3c提出了跨源资源共享，即CORS(Cross-Origin Resource Sharing)。
 * CorsFilter过滤器实现了CORS的规范的服务端部分，让服务端支持CORS跨域请求.（同时还需要客户端也支持CORS跨域）
 * <p>标题: CorsFilter</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 * @author zhaolei
 * @version 2019年1月27日 上午11:11:17
 */
public class CorsFilter implements Filter {
    private final static String ENCODING = "UTF-8";//编码
    private final static String OPTIONS = "OPTIONS";//OPTIONS方法
    private final static String HEADER_ORIGIN = "Origin";//Origin头的KEY

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        //判断请求中是否带有Origin头
        Boolean hasHeaderOrigin = hasHeaderOrigin(servletRequest);
        if (hasHeaderOrigin) {
            //所有支持域的集合，例如"http://domain1.com"。
            //<p>这些值都显示在请求头中的Access-Control-Allow-Origin
            //"*"代表所有域的请求都支持
            //<p>如果没有定义，所有请求的域都支持
            String value = servletRequest.getHeader(HEADER_ORIGIN);//取Origin头的值
            if (StringUtils.isNoneBlank(value)) {
                //这是一个优化，目标：客户端使用任何域名都可带Cookie的
                servletResponse.setHeader("Access-Control-Allow-Origin", value);
            } else {
                servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            }

            //默认支持RequestMapping中设置的方法
            servletResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");

            //允许请求头中的header,添加自定义的请求头
            servletResponse.setHeader("Access-Control-Allow-Headers", "AppToken,TerminalType");
            //响应头中允许访问的header，默认为空
            //.exposedHeaders("header1", "header2")
            //是否允许cookie随请求发送，使用时必须指定具体的域
            servletResponse.addHeader("Access-Control-Allow-Credentials", "true");
            //预请求的结果的有效期，默认30分钟
            servletResponse.setHeader("Access-Control-Max-Age", "3601");
        }

        ////////////////////////////////////////////////////////////////
        //这次请求是 OPTION 请求，称为预检请求（preflight request）
        ////////////////////////////////////////////////////////////////
        if (isOptions(servletRequest)) {
            servletResponse.setStatus(200);
            String msg = "Support CORS(Cross-Origin Resource Sharing)";//一个提示信息，非必要
            servletResponse.getWriter().print(msg);
            response.setContentType("text/html;charset=" + ENCODING); //设置编码
            response.setCharacterEncoding(ENCODING); //设置编码
            return;//需要返回，不能放过。若放过，在登录拦截器那里会被认定为未登录。
        }

        //能走到这里，是常规的业务请求，要放过
        chain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 判断请求中是否带有Origin头
     * @param servletRequest
     * @return
     */
    public static Boolean hasHeaderOrigin(HttpServletRequest servletRequest) {
        Boolean hasHeaderOrigin = false;
        Enumeration<String> e = servletRequest.getHeaderNames();
        while (e.hasMoreElements()) {
            String name = e.nextElement();
            if (HEADER_ORIGIN.equalsIgnoreCase(name)) {
                hasHeaderOrigin = true;//发现带有Origin头
                break;
            }
        }
        return hasHeaderOrigin;
    }

    /**
     * 判断这次请求是否是 OPTION 请求，称为预检请求（preflight request）
     * @param servletRequest
     */
    public static Boolean isOptions(HttpServletRequest servletRequest) {
        Boolean hasHeaderOrigin = hasHeaderOrigin(servletRequest);//判断请求中是否带有Origin头
        String method = servletRequest.getMethod();//是OPTIONS方法
        //不是OPTION 请求
        return OPTIONS.equalsIgnoreCase(method) && hasHeaderOrigin;//是OPTION 请求
    }
}