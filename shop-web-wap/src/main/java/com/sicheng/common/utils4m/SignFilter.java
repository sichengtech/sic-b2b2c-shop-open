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

    /**
     * 签名与验签时使用的“盐”
     */
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

        try {
            //取由前端传来的参数，前端永远只传这3个参数来
            String dataBase64 = r.getParameter("data");//业务数据，前端发来的是Base64格式
            String timestamp = r.getParameter("make");//make : 前端发来的时间戳   （取这个名字是为了迷惑）
            String clientSign = r.getParameter("random");//random : 前端发来的签名    （取这个名字是为了迷惑）

            //////////////////////////
            // 验签,验证签名是否正确
            //////////////////////////
            int res = verifySignature(dataBase64, timestamp, clientSign,salt_d);

            if (res == 1) {
                String message = "验证签名失败-1：缺少参数";
                result(response, message, AppDataUtils.STATUS_SIGN_ERROR);
                return;
            }

            //验证时间戳(允许正负偏差各一个小时)
            if (res == 2) {
                String message = "验证签名失败-2";//提示信息就想这样
                result(response, message, AppDataUtils.STATUS_SIGN_ERROR);
                return;
            }

            if (res == 3) {
                //验证签名失败
                String message = "验证签名失败-3：验证签名未通过";
                result(response, message, AppDataUtils.STATUS_SIGN_ERROR);
                return;
            }

            //解析出业务数据
            byte[] d = new Base64().decode(dataBase64);
            String jsonStr = new String(d, StandardCharsets.UTF_8);
            if (!StringUtils.isBlank(jsonStr)) {
                mapData = JsonMapper.getInstance().fromJson(jsonStr, Map.class);
                if (mapData == null) {
                    String message = "验证签名失败-4：业务数据格式不正确";
                    result(response, message, AppDataUtils.STATUS_SIGN_ERROR);
                    return;
                }
            }

            if (res == 5) {
                String message = "验证签名失败-5：服务端验证签名发生异常";
                result(response, message, AppDataUtils.STATUS_SERVER_ERROR);
                return;
            }

        } catch (Exception e) {
            String message = "服务端发生未知异常";
            logger.error(message, e);
            result(response, message, AppDataUtils.STATUS_SERVER_ERROR);
            return;
        }
        //验证签名通过，继续执行后续业务
        HttpServletRequest new_request = new SignHttpServletRequestWrapper(r, mapData);
        chain.doFilter(new_request, response);
    }

    /**
     * 验签,验证签名的方法
     *
     * @param dataBase64 data:业务数据，前端发来的是Base64格式
     * @param timestamp  make : 前端发来的时间戳（取这个名字是为了迷惑）
     * @param clientSign       random : 前端发来的签名（取这个名字是为了迷惑）
     * @param salt       签名用的盐
     * @return 验证结果
     * 0：验证签名成功
     * 1：验证签名失败-1：无sign、无timestamp、无dataBase64
     * 2：验证签名失败-2: 验证时间戳未通过(允许正负偏差各一个小时)
     * 3：验证签名失败-3：验证签名未通过
     * 4：验证签名失败-4：业务数据格式不正确
     * 5：验证签名失败-5：服务端验证签名发生异常
     */

    public int verifySignature(String dataBase64, String timestamp, String clientSign,String salt) {
        try {
            if (StringUtils.isBlank(dataBase64) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(clientSign)) {
                //验证签名失败-1：无sign、无timestamp、无dataBase64
                return 1;
            }

            //验证时间戳(允许正负偏差各一个小时)
            long t = Long.parseLong(timestamp);
            if (Math.abs(System.currentTimeMillis() - t) > (1000 * 60 * 60)) {
                //验证签名失败-2: 验证时间戳未通过(允许正负偏差各一个小时)
                return 2;
            }

            //验证签名
            //签名规则 签名=md5(sha3_256( base64业务数据json串+ 时间戳 + md5(盐)))
            String serverSign = signature(dataBase64, timestamp, salt);

            //sign是前端传来的签名，mySign2就是服务端计算出来的签名，两个签名相等，说明验证通过
            if (!clientSign.equals(serverSign)) {
                //验证签名失败-3：验证签名未通过
                String message = "验证签名失败-3：验证签名未通过";
                logger.warn("{},客户端签名={},服务端签名={},data={},timestamp={},salt={}", message, clientSign, serverSign, dataBase64, timestamp, salt_d);
                return 3;
            }

        } catch (Exception e) {
            String message = "验证签名失败-5：服务端验证签名发生异常";
            logger.error(message, e);
            return 5;
        }
        return 0;  //0：验证签名成功
    }

    /**
     * 签名，计算出签名
     * 签名规则 签名=md5(sha3_256( base64业务数据json串+ 时间戳 + md5(盐)))
     *
     * @param dataBase64 业务数据是Base64格式
     * @param timestamp  时间戳(允许正负偏差各一个小时)
     * @param salt       签名用的盐(客户端与服务端必须使用同一个值)
     * @return 签名
     */
    public String signature(String dataBase64, String timestamp, String salt) {
        String saltMd5 = MD5.encrypt(salt);//这是“盐”，把“盐”先md5一下
        String allData = dataBase64 + timestamp + saltMd5; //拼接
        String sign = FIPS202.hexFromBytes(FIPS202.HashFunction.SHA3_256.apply(allData.getBytes(StandardCharsets.UTF_8)));//sha3_256算法
        return MD5.encrypt(sign.toLowerCase());
    }

    @Override
    public void destroy() {
    }

    /**
     * 向response写入返回结果
     *
     * @param response response
     * @param message  错误提示信息
     * @param status   状态码
     */
    private void result(ServletResponse response, String message, String status) {
        Map<String, Object> map = AppDataUtils.getMap(status, message, null, null);
        String jsonRs = JsonMapper.getInstance().toJson(map);
        R.writeJson(((HttpServletResponse) response), jsonRs, "UTF-8");
    }
}