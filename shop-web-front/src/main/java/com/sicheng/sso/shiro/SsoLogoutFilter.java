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
package com.sicheng.sso.shiro;

import com.sicheng.common.utils.CookieUtils;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在JavaWEB管理平台系统时，用户退出系统之时,有可能需要清理一些用户数据。
 * shiro提供了LogoutFilter过滤器，我们可以继承LogoutFilter，重写preHandle方法，在这里可执行清理动作。
 *
 * 当调用的路径匹配到/logout，表示想执行退出动作，会进入到SsoLogoutFilter过滤器。
 * SsoLogoutFilter继承了Shiro官方的LogoutFilter，并重写了preHandle方法，在preHandle方法执行清空数据清空cookie动作。
 *
 * @author zhaolei
 * @version 2023-08-09
 */
public class SsoLogoutFilter extends LogoutFilter {
    @Override
    protected boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //在这里执行退出系统时，需要做的清空动作
        //清除cookie，没有cookie，在客户端用户的状态就是退出状态了。
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response =(HttpServletResponse)servletResponse;
        CookieUtils.setCookie(request, response, "usm.loginName", null, "/", 0);
        CookieUtils.setCookie(request, response, "usm.headPicPath", null, "/", 0);
        CookieUtils.setCookie(request, response, "usm.isTypeUserPurchaser", null, "/", 0);
        CookieUtils.setCookie(request, response, "usm.isloginInvalid", null, "/", 0);

        //调用父类中原始的方法，执行原始的退出业务
        return super.preHandle(servletRequest,servletResponse);

//        //以下是手动调用 退出业务，不需要手动用调，如上super.preHandle()更优雅。
//        Subject subject = getSubject(request, response);
//        String redirectUrl = getRedirectUrl(request, response, subject);
//        try {
//            subject.logout();
//        } catch (SessionException ise) {
//            ise.printStackTrace();
//        }
//        issueRedirect(request, response, redirectUrl);

        //返回false表示不执行后续的过滤器，直接返回跳转到登录页面
        //返回true表示执行后续的过滤器，继续执行过滤器链条上的后续过滤器。
//        return true;
    }
}