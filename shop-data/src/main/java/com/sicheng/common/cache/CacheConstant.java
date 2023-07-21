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
package com.sicheng.common.cache;

/**
 * <p>标题: CacheConstant</p>
 * <p>描述: cache缓存使用常量前缀名</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @date 2017年6月27日 下午1:05:05
 */
public class CacheConstant {

    //admin文章更新权重时间
    public final static String ADMIN_UPDAT_EEXPIRED_WEIGHT_DATE_BY_ARTICLE = "admin_update_expired_weight_date_by_article";

    //admin友情链接更新权重时间
    public final static String ADMIN_UPDAT_EEXPIRED_WEIGHT_DATE_BY_LINK = "admin_update_expired_weight_date_by_link";

    //admin登录失败次数
    public final static String ADMIN_LOGINFAIL = "admin_loginFail.";

    //sso登录失败次数
    public final static String SSO_LOGINFAIL = "sso_loginFail.";

    //sso注册,大宗采购,忘记密码,绑定手机,安全验证页面,登录(发送短信手机前缀)
    public final static String SMS_MOBILE = "sms_mobile.";

    //sso注册,大宗采购,忘记密码,绑定手机,安全验证页面,登录(发送短信验证码前缀)
    public final static String SMS_CODE = "sms_code.";

    //注册是否发送短信验证码
    public final static String SMS_IS_SEND_REGISTER = "sms_is_send_register.";

    //登录是否发送短信验证码
    public final static String SMS_IS_SEND_LOGIN = "sms_is_send_login.";

    //忘记密码是否发送短信验证码
    public final static String SMS_IS_SEND_FORGETPWD = "sms_is_send_forgetpwd.";

    //绑定手机是否发送手机验证码
    public final static String SMS_IS_SEND_BINGDING = "sms_is_send_binding.";

    //安全验证页是否发送手机验证码
    public final static String SMS_IS_SEND_SECURITY = "sms_is_send_security.";

    //sso忘记密码,绑定邮箱,安全验证页面(发送邮箱前缀)
    public final static String EMAIL_ADDR = "email_addr.";

    //sso忘记密码,绑定邮箱,安全验证页面(发送邮箱验证码前缀)
    public final static String EMAIL_CODE = "email_code.";

    //注册是否发送邮箱验证码
    public final static String EMAIL_IS_SEND_REGISTER = "email_is_send_register.";

    //忘记密码是否发送邮箱验证码
    public final static String EMAIL_IS_SEND_FORGETPWD = "email_is_send_forgetpwd.";

    //绑定邮箱是否发送邮箱验证码
    public final static String EMAIL_IS_SEND_BINGDING = "email_is_send_binding.";

    //安全验证页是否发送邮箱验证码
    public final static String EMAIL_IS_SEND_SECURITY = "email_is_send_security.";

    //sso安全验证也目标页面
    public final static String SECURITY_PATH = "security_path.";

    //sso为了在去目标页面在安全验证页拦截器放过
    public final static String SECURITY_TYPE = "security_type.";

    //记录访前url,用于从哪个页面来，登录成功后，到哪个页面去。 seller
    public final static String LOGIN_REFERER_SELLER = "login_referer_seller.";

    //记录访前url,用于从哪个页面来，登录成功后，到哪个页面去。admin
    public final static String LOGIN_REFERER_ADMIN = "login_referer_admin.";

    //admin管理后台模拟登陆进入店铺
    public final static String SIMULATION_LOGIN = "simulation_login.";

    //微信注册、登录、忘记密码存微信openid
    public final static String WEIXIN_OPENID = "weixin_openid.";

}
