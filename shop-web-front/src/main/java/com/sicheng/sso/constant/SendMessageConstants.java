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
package com.sicheng.sso.constant;

public class SendMessageConstants {

    /**
     * 邮箱
     */
    public static final String EMAIL_STATUS_ZERO_STATUS = "0";
    public static final String EMAIL_STATUS_ZERO_MESSAGE = "发送成功！";

    public static final String EMAIL_STATUS_ONE_STATUS = "1";
    public static final String EMAIL_STATUS_ONE_MESSAGE = "发送失败：邮箱为空！";

    public static final String EMAIL_STATUS_TWO_STATUS = "2";
    public static final String EMAIL_STATUS_TWO_MESSAGE = "发送失败：您发送邮件过于频繁！";

    public static final String EMAIL_STATUS_THREE_STATUS = "3";
    public static final String EMAIL_STATUS_THREE_MESSAGE = "发送失败：当前账号被锁定！";

    public static final String EMAIL_STATUS_FOUR_STATUS = "4";
    public static final String EMAIL_STATUS_FOUR_MESSAGE = "发送失败：邮箱格式不正确！";

    public static final String EMAIL_STATUS_FIVE_STATUS = "5";
    public static final String EMAIL_STATUS_FIVE_MESSAGE = "发送失败：用户不存在！";

    public static final String EMAIL_STATUS_SIX_STATUS = "6";
    public static final String EMAIL_STATUS_SIX_MESSAGE = "发送失败：请主账号帮你找回密码！";

    public static final String EMAIL_STATUS_SEVEN_STATUS = "7";
    public static final String EMAIL_STATUS_SEVEN_MESSAGE = "发送失败：邮箱已被占用！";

    public static final String EMAIL_STATUS_EIGHT_STATUS = "8";
    public static final String EMAIL_STATUS_EIGHT_MESSAGE = "发送失败：网站没有开放注册！";

    public static final String EMAIL_STATUS_NINE_STATUS = "9";
    public static final String EMAIL_STATUS_NINE_MESSAGE = "发送失败：验证码为空！";

    public static final String EMAIL_STATUS_TEN_STATUS = "10";
    public static final String EMAIL_STATUS_TEN_MESSAGE = "发送失败：验证码不正确！";

    /**
     * 短信
     */
    public static final String SMS_STATUS_ZERO_STATUS = "0";
    public static final String SMS_STATUS_ZERO_MESSAGE = "发送成功！";

    public static final String SMS_STATUS_ONE_STATUS = "1";
    public static final String SMS_STATUS_ONE_MESSAGE = "发送失败：网站没有开放注册！";

    public static final String SMS_STATUS_TWO_STATUS = "2";
    public static final String SMS_STATUS_TWO_MESSAGE = "发送失败：您发送验证码过于频繁！";

    public static final String SMS_STATUS_THREE_STATUS = "3";
    public static final String SMS_STATUS_THREE_MESSAGE = "发送失败：手机号为空！";

    public static final String SMS_STATUS_FOUR_STATUS = "4";
    public static final String SMS_STATUS_FOUR_MESSAGE = "发送失败：手机号已存在！";

    public static final String SMS_STATUS_FIVE_STATUS = "5";
    public static final String SMS_STATUS_FIVE_MESSAGE = "发送失败：当前账号被锁定！";

    public static final String SMS_STATUS_SIX_STATUS = "6";
    public static final String SMS_STATUS_SIX_MESSAGE = "发送失败：账号不存在！";

    public static final String SMS_STATUS_SEVEN_STATUS = "7";
    public static final String SMS_STATUS_SEVEN_MESSAGE = "发送失败：请主账号帮你找回密码！";

    public static final String SMS_STATUS_EIGHT_STATUS = "8";
    public static final String SMS_STATUS_EIGHT_MESSAGE = "发送失败：验证码为空！";

    public static final String SMS_STATUS_NINE_STATUS = "9";
    public static final String SMS_STATUS_NINE_MESSAGE = "发送失败：验证码不正确！";

    public static final String SMS_STATUS_TEN_STATUS = "10";
    public static final String SMS_STATUS_TEN_MESSAGE = "发送失败：手机号超过11位！";

}
