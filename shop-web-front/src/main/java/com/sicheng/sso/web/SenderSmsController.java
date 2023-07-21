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
package com.sicheng.sso.web;

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.servlet.ValidateCodeServlet;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.utils.VerifyCodeUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.site.service.SiteRegisterService;
import com.sicheng.sso.constant.SendMessageConstants;
import com.sicheng.sso.service.SendSmsService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.utils.SsoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${ssoPath}/security")
public class SenderSmsController extends BaseController {

    @Autowired
    private SiteRegisterService siteRegisterService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private SendSmsService sendSmsService;

    /**
     * 发送短信验证码(注册)
     *
     * @param mobile       手机号
     * @param validateCode 验证码
     * @param request
     * @return
     */
    @RequestMapping(value = "registerGetSms")
    @ResponseBody
    public Object registerGetCode(String mobile, String validateCode, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_EIGHT_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_EIGHT_MESSAGE));
            return map;
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_NINE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_NINE_MESSAGE));
            return map;
        }
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 11) {
            map.put("status", SendMessageConstants.SMS_STATUS_TEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_TEN_MESSAGE));
            return map;
        }
        if (StringUtils.isBlank(mobile)) {
            map.put("status", SendMessageConstants.SMS_STATUS_THREE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_THREE_MESSAGE));
            return map;
        }
        //验证发送短信过于频繁
        if (cache.get(CacheConstant.SMS_IS_SEND_REGISTER + R.getSession().getId()) != null) {
            map.put("status", SendMessageConstants.SMS_STATUS_TWO_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_TWO_MESSAGE));
            return map;
        }
        //验证网站是否开放注册
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        if (siteRegister != null && "0".equals(siteRegister.getIsRegister())) {
            //是否开放注册:0未开放，1开放
            map.put("status", SendMessageConstants.SMS_STATUS_ONE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_ONE_MESSAGE));
            return map;
        }
        String smsVerification = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        //需要判断注册账号在库中是否存在
        UserMain userMain = new UserMain();
        userMain.setMobile(mobile);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (!userMains.isEmpty()) {
            map.put("status", SendMessageConstants.SMS_STATUS_FOUR_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_FOUR_MESSAGE));
            return map;
        }
        String sessionId = request.getSession().getId();
        cache.put(CacheConstant.SMS_MOBILE + sessionId, mobile, 1800L);
        cache.put(CacheConstant.SMS_CODE + sessionId, smsVerification, 1800L);
        cache.put(CacheConstant.SMS_IS_SEND_REGISTER + sessionId, "1", 60L);
        logger.info("您的" + mobile + "手机号验证码是：" + smsVerification);
        return sendSmsService.sendSms(mobile, smsVerification);
    }

    /**
     * 大宗采购-我的采购需求单
     *
     * @param mobile       手机号
     * @param validateCode 图片验证码
     * @return
     */
//    @RequestMapping(value = "purchaseGetCode")
//    @ResponseBody
//    public Object purchaseGetCode(String mobile, String validateCode, HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        if (StringUtils.isBlank(validateCode)) {
//            map.put("status", SendMessageConstants.SMS_STATUS_EIGHT_STATUS);
//            map.put("status", SendMessageConstants.SMS_STATUS_EIGHT_MESSAGE);
//            return map;
//        }
//        if (!ValidateCodeServlet.validate(R.getRequest(), validateCode)) {
//            map.put("status", SendMessageConstants.SMS_STATUS_NINE_STATUS);
//            map.put("status", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_ONE_MESSAGE));
//            return map;
//        }
//        if (StringUtils.isNotBlank(mobile) && mobile.length() > 11) {
//            map.put("status", SendMessageConstants.SMS_STATUS_TEN_STATUS);
//            map.put("status", SendMessageConstants.SMS_STATUS_TEN_MESSAGE);
//            return map;
//        }
//        if (StringUtils.isBlank(mobile)) {
//            map.put("status", SendMessageConstants.SMS_STATUS_THREE_STATUS);
//            map.put("status", SendMessageConstants.SMS_STATUS_THREE_MESSAGE);
//            return map;
//        }
//        String smsVerification = VerifyCodeUtils.createRandom(true, 4);//短信验证码
//        String sessionId = request.getSession().getId();
//        cache.put(CacheConstant.SMS_MOBILE + sessionId, mobile, 1800L);
//        cache.put(CacheConstant.SMS_CODE + sessionId, smsVerification, 1800L);
//        logger.info("您的" + mobile + "手机号验证码是：" + smsVerification);
//        return sendSmsService.sendSms(mobile, smsVerification);
//    }

    /**
     * 发送短信验证码(忘记密码)
     *
     * @param mobile       手机号
     * @param validateCode 验证码
     * @return
     */
    @RequestMapping(value = "forgetPasswordGetSms")
    @ResponseBody
    public Object forgetPasswordGetCode(String mobile, String validateCode, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_EIGHT_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_EIGHT_MESSAGE));
            return map;
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_NINE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_NINE_MESSAGE));
            return map;
        }
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 11) {
            map.put("status", SendMessageConstants.SMS_STATUS_TEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_TEN_MESSAGE));
            return map;
        }
        //验证发送短信过于频繁
        if (cache.get(CacheConstant.SMS_IS_SEND_FORGETPWD + R.getSession().getId()) != null) {
            map.put("status", SendMessageConstants.SMS_STATUS_TWO_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_TWO_MESSAGE));
            return map;
        }
        if (StringUtils.isBlank(mobile)) {
            map.put("status", SendMessageConstants.SMS_STATUS_THREE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_THREE_MESSAGE));
            return map;
        }
        String smsVerification = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        //判断是否存在该账号
        UserMain userMain = new UserMain();
        userMain.setMobile(mobile);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (userMains.isEmpty()) {
            map.put("status", SendMessageConstants.SMS_STATUS_SIX_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_SIX_MESSAGE));
            return map;
        }
        UserMain userMain2 = userMains.get(0);
        //账号被锁定不让发送
        if ("1".equals(userMain2.getIsLocked())) {
            map.put("status", SendMessageConstants.SMS_STATUS_FIVE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_FIVE_MESSAGE));
            return map;
        }
        //子账号不允许找回密码
        if ("2".equals(userMain2.getTypeAccount())) {
            map.put("status", SendMessageConstants.SMS_STATUS_SEVEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_SEVEN_MESSAGE));
            return map;
        }
        cache.put(CacheConstant.SMS_MOBILE + userMains.get(0).getUId(), userMains.get(0).getUId(), 1800L);
        cache.put(CacheConstant.SMS_CODE + userMains.get(0).getUId(), smsVerification, 1800L);
        cache.put(CacheConstant.SMS_IS_SEND_FORGETPWD + R.getSession().getId(), "1", 60L);
        logger.info("您的" + mobile + "手机号验证码是：" + smsVerification);
        return sendSmsService.sendSms(mobile, smsVerification);
    }

    /**
     * 绑定手机
     *
     * @param mobile       手机号
     * @param validateCode 验证码
     * @return
     */
    @RequestMapping(value = "bindingGetSms")
    @ResponseBody
    public Object bindingGetSms(String mobile, String validateCode, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_EIGHT_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_EIGHT_MESSAGE));
            return map;
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_NINE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_NINE_MESSAGE));
            return map;
        }
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 11) {
            map.put("status", SendMessageConstants.SMS_STATUS_TEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_TEN_MESSAGE));
            return map;
        }
        //验证发送短信过于频繁
        if (cache.get(CacheConstant.SMS_IS_SEND_BINGDING + R.getSession().getId()) != null) {
            map.put("status", SendMessageConstants.SMS_STATUS_TWO_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_TWO_MESSAGE));
            return map;
        }
        if (StringUtils.isBlank(mobile)) {
            map.put("status", SendMessageConstants.SMS_STATUS_THREE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_THREE_MESSAGE));
            return map;
        }
        String smsVerification = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        UserMain userMain = new UserMain();
        userMain.setMobile(mobile);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (!userMains.isEmpty()) {
            map.put("status", SendMessageConstants.SMS_STATUS_FOUR_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_FOUR_MESSAGE));
            return map;
        }
        UserMain userMain2 = SsoUtils.getUserMain();
        //账号被锁定不让发送
        if ("1".equals(userMain2.getIsLocked())) {
            map.put("status", SendMessageConstants.SMS_STATUS_FIVE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_FIVE_MESSAGE));
            return map;
        }
        cache.put(CacheConstant.SMS_MOBILE + mobile, mobile, 1800L);
        cache.put(CacheConstant.SMS_CODE + SsoUtils.getUserMain().getUId(), smsVerification, 1800L);
        cache.put(CacheConstant.SMS_IS_SEND_BINGDING + R.getSession().getId(), "1", 60L);
        logger.info("您的" + mobile + "手机号验证码是：" + smsVerification);
        return sendSmsService.sendSms(mobile, smsVerification);
    }

    /**
     * 安全验证页发送短信
     */
    @RequestMapping(value = "verificationSecurityGetSms")
    @ResponseBody
    public Object verificationSecurityGetSms(String mobile, String validateCode, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_EIGHT_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_EIGHT_MESSAGE));
            return map;
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_NINE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_NINE_MESSAGE));
            return map;
        }
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 11) {
            map.put("status", SendMessageConstants.SMS_STATUS_TEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_TEN_MESSAGE));
            return map;
        }
        if (StringUtils.isBlank(mobile)) {
            map.put("status", SendMessageConstants.SMS_STATUS_THREE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_THREE_MESSAGE));
            return map;
        }
        //验证发送短信过于频繁
        if (cache.get(CacheConstant.SMS_IS_SEND_SECURITY + R.getSession().getId()) != null) {
            map.put("status", SendMessageConstants.SMS_STATUS_TWO_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_TWO_MESSAGE));
            return map;
        }
        String smsVerification = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        UserMain userMain = new UserMain();
        userMain.setMobile(mobile);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (userMains.isEmpty()) {
            map.put("status", SendMessageConstants.SMS_STATUS_SIX_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_SIX_MESSAGE));
            return map;
        }
        UserMain userMain2 = userMains.get(0);
        //账号被锁定不让发送
        if ("1".equals(userMain2.getIsLocked())) {
            map.put("status", SendMessageConstants.SMS_STATUS_FIVE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_FIVE_MESSAGE));
            return map;
        }
        cache.put(CacheConstant.SMS_MOBILE + userMain2.getMobile(), userMains.get(0).getMobile(), 1800L);
        cache.put(CacheConstant.SMS_CODE + userMain2.getUId(), smsVerification, 1800L);
        cache.put(CacheConstant.SMS_IS_SEND_SECURITY + R.getSession().getId(), "1", 60L);
        logger.info("您的" + mobile + "手机号验证码是：" + smsVerification);
        return sendSmsService.sendSms(mobile, smsVerification);
    }

    /**
     * 发送短信验证码(登录)
     *
     * @param mobile  手机号
     * @param mobile  验证码
     * @param request
     * @return
     */
    @RequestMapping(value = "loginGetSms")
    @ResponseBody
    public Object loginGetCode(String mobile, String validateCode, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_EIGHT_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_EIGHT_MESSAGE));
            return map;
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), validateCode)) {
            map.put("status", SendMessageConstants.SMS_STATUS_NINE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_NINE_MESSAGE));
            return map;
        }
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 11) {
            map.put("status", SendMessageConstants.SMS_STATUS_TEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_TEN_MESSAGE));
            return map;
        }
        if (StringUtils.isBlank(mobile)) {
            map.put("status", SendMessageConstants.SMS_STATUS_THREE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_THREE_MESSAGE));
            return map;
        }
        //验证发送短信过于频繁
        if (cache.get(CacheConstant.SMS_IS_SEND_LOGIN + R.getSession().getId()) != null) {
            map.put("status", SendMessageConstants.SMS_STATUS_TWO_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_THREE_MESSAGE));
            return map;
        }
        String smsVerification = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        //需要判断注册账号在库中是否存在
        UserMain userMain = new UserMain();
        userMain.setMobile(mobile);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (userMains.isEmpty()) {
            map.put("status", SendMessageConstants.SMS_STATUS_SIX_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_SIX_MESSAGE));
            return map;
        }
        UserMain userMain2 = userMains.get(0);
        //账号被锁定不让发送
        if ("1".equals(userMain2.getIsLocked())) {
            map.put("status", SendMessageConstants.SMS_STATUS_FIVE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.SMS_STATUS_FIVE_MESSAGE));
            return map;
        }
        cache.put(CacheConstant.SMS_MOBILE + userMain2.getUId(), userMain2.getUId(), 1800L);
        cache.put(CacheConstant.SMS_CODE + userMain2.getUId(), smsVerification, 1800L);
        cache.put(CacheConstant.SMS_IS_SEND_LOGIN + R.getSession().getId(), "1", 60L);
        logger.info("您的" + mobile + "手机号验证码是：" + smsVerification);
        return sendSmsService.sendSms(mobile, smsVerification);
    }

    public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("1200.00");
        System.out.println(decimal.stripTrailingZeros());
        System.out.println(decimal.stripTrailingZeros().toPlainString());
        //  BigDecimal setScale = decimal.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        BigDecimal decimal2 = new BigDecimal("1.10023450000");
        System.out.println(decimal2.stripTrailingZeros().toPlainString());
    }
}
