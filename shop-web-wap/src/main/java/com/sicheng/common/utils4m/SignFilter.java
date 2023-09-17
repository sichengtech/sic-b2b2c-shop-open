/**
 * SiC B2B2C Shop 使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权书。
 * Copyright (c) 2016 SiCheng.Net
 * SiC B2B2C Shop is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.common.utils4m;

import com.sicheng.common.config.Global;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.security.MD5;
import com.sicheng.common.security.keccak.FIPS202;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.wap.filter.CorsFilter;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>标题: SignFilter 签名过滤器,移动端接口的签名处理类</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 * <p>
 * web.xml配置示例：
 * <filter>
 * <filter-name>SignFilter</filter-name>
 * <filter-class>com.sicheng.wap.utils.SignFilter</filter-class>
 * </filter>
 * <filter-mapping>
 * <filter-name>SignFilter</filter-name>
 * <url-pattern>/api/*</url-pattern>
 * <dispatcher>REQUEST</dispatcher>
 * </filter-mapping>
 *
 * @author zhaolei
 * @version 2019年11月03日
 */
public class SignFilter implements Filter {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    //base url盐(e)
//    private String salt_baseUrl = Global.getConfig("wap.api.baseUrl");
    //固化在代码中的盐(d)
    private String salt_d = Global.getConfig("wap.api.sign");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) request;
        Map<String, Object> mapData = null;

        //判断这次请求是否是 OPTION 请求
        Boolean isOptions = CorsFilter.isOptions(r);
        if (isOptions) {
            chain.doFilter(request, response);//放过
            return;
        }

        //验证签名失败-1：无sign、无timestamp、无dataBase64
        //验证签名失败-2: 验证时间戳未通过(允许正负偏差各一个小时)
        //验证签名失败-3：验证签名未通过
        try {
            //取由前端传来的参数，前端永远只传这3个参数来
            String dataBase64 = r.getParameter("data");
            String timestamp = r.getParameter("make");//make : 时间戳,    （取这个名字是为了迷惑）
            String sign = r.getParameter("random");//random : 签名    （取这个名字是为了迷惑）
            if (StringUtils.isBlank(dataBase64) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(sign)) {
                String message = "验证签名失败-1";
                result(response, message, AppDataUtils.STATUS_SIGN_ERROR);
                return;
            }
            //解析出业务数据
            byte[] d = new Base64().decode(dataBase64);
            String jsonStr = new String(d, StandardCharsets.UTF_8);
            if (StringUtils.isBlank(jsonStr)) {
                mapData = null;
            } else {
                mapData = JsonMapper.getInstance().fromJson(jsonStr, Map.class);
                if (mapData == null) {
                    String message = "验证签名失败,业务数据格式不正确";
                    result(response, message, AppDataUtils.STATUS_SIGN_ERROR);
                    return;
                }
            }

            //验证时间戳(允许正负偏差各一个小时)
            Long t = Long.valueOf(timestamp);
            if (Math.abs(System.currentTimeMillis() - t) > (1000 * 60 * 60)) {
                String message = "验证签名失败-2";
                result(response, message, AppDataUtils.STATUS_SIGN_ERROR);
                return;
            }

            //验证签名
            //签名规则 md5(sha3_256( base64(业务数据json串) + 时间戳 + md5(d + md5(e))))
//            String salt = MD5.encrypt(salt_d + MD5.encrypt(salt_baseUrl));
            String salt = MD5.encrypt(salt_d );
            String allStr = dataBase64 + timestamp + salt;
            String mySign1 = FIPS202.hexFromBytes(FIPS202.HashFunction.SHA3_256.apply(allStr.getBytes(StandardCharsets.UTF_8)));
            String mySign2 = MD5.encrypt(mySign1.toLowerCase());
            if (sign == null || !sign.equals(mySign2)) {
                //验证签名失败
                String message = "验证签名失败-3";
                logger.warn(message + ",sign+" + sign + ",mySign2=" + mySign2 + ",data=" + jsonStr);
                result(response, message, AppDataUtils.STATUS_SIGN_ERROR);
                return;
            }

        } catch (Exception e) {
            String message = "服务端验证签名发生异常";
            logger.error(message, e);
            result(response, message, AppDataUtils.STATUS_SERVER_ERROR);
            return;
        }
        //验证签名通过，继续执行后续业务
        HttpServletRequest new_request = new SignHttpServletRequestWrapper(r, mapData);
        chain.doFilter(new_request, response);
    }

    @Override
    public void destroy() {
    }

    /**
     * 向response写入返回结果
     *
     * @param response
     * @param message  错误提示信息
     * @param status   状态码
     */
    private void result(ServletResponse response, String message, String status) {
        Map<String, Object> map = AppDataUtils.getMap(status, message, null, null);
        String jsonRs = JsonMapper.getInstance().toJson(map);
        R.writeJson(((HttpServletResponse) response), jsonRs, "UTF-8");
    }
}