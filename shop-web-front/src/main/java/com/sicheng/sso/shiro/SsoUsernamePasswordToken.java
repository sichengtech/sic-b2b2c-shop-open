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

/**
 * 用户和密码（包含验证码）令牌类
 *
 * @author zhaolei
 * @version 2013-5-19
 */
public class SsoUsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

    private static final long serialVersionUID = 1L;

    private String captcha;
    private boolean mobileLogin;
    private String ssoReg;

    public SsoUsernamePasswordToken() {
        super();
    }

    public SsoUsernamePasswordToken(String loginName, char[] password,
                                    boolean rememberMe, String ssoReg, String host, String captcha, boolean mobileLogin) {
        super(loginName, password, rememberMe, host);
        this.ssoReg = ssoReg;
        this.captcha = captcha;
        this.mobileLogin = mobileLogin;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getSsoReg() {
        return ssoReg;
    }

    public void setSsoReg(String ssoReg) {
        this.ssoReg = ssoReg;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

}