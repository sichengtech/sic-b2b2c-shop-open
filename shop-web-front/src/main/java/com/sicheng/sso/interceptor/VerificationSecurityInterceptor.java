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
package com.sicheng.sso.interceptor;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.cache.ShopCache;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.sso.utils.SsoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>标题: 安全验证页拦截</p>
 * <p>描述: sso的“改支付密码、改密码、修改安全手机、修改安全邮箱” 被“安全验证页”拦截
 * (修改安全手机、修改安全邮箱在账号未激活的时候不拦截，在账号激活的时候拦截)</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月5日 下午12:31:15
 */
public class VerificationSecurityInterceptor implements HandlerInterceptor {

    @Value("${ssoPath}")
    protected String ssoPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ShopCache cache = SpringContextHolder.getBean(ShopCache.class);
        UserMain u = SsoUtils.getUserMain();
        Object type = cache.get(CacheConstant.SECURITY_TYPE + u.getUId());//为了在去目标页面在拦截器放过
        if (type == null) {
            // 获得用户请求的URI
            String path = null;
            if (request.getQueryString() == null || "".equals(request.getQueryString())) {
                path = request.getRequestURI();
            } else {
                path = request.getRequestURI() + "?" + request.getQueryString();
            }
            path = path.substring(request.getContextPath().length());
            request.getSession().setAttribute("path", path);
            request.getRequestDispatcher(ssoPath + "/accountSecurity/verificationSecurity1.htm").forward(request, response);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3) throws Exception {

    }
}
