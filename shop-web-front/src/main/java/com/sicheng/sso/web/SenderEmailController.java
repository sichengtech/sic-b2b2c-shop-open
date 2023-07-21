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
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.utils.VerifyCodeUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.site.service.SiteRegisterService;
import com.sicheng.sso.constant.SendMessageConstants;
import com.sicheng.sso.service.SendEmailService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.utils.SsoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${ssoPath}/security")
public class SenderEmailController extends BaseController {

    @Autowired
    private SiteRegisterService siteRegisterService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private SendEmailService sendEmailService;

    /**
     * 发送短信验证码(注册)
     *
     * @param email       邮箱
     * @return
     */
    @RequestMapping(value = "registerGetEmail")
    @ResponseBody
    public Object registerGetCode(String email) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sessionId = R.getSession().getId();
        if (StringUtils.isBlank(email)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_ONE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_ONE_MESSAGE));
            return map;
        }
        if (StringUtils.isNotBlank(email) && !RegexUtils.checkEmail(email)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_FOUR_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_FOUR_MESSAGE));
            return map;
        }
        //验证发送邮件过于频繁
        if (cache.get(CacheConstant.EMAIL_IS_SEND_REGISTER + sessionId) != null) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_TWO_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_TWO_MESSAGE));
            return map;
        }
        //验证网站是否开放注册
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        if (siteRegister != null && "0".equals(siteRegister.getIsRegister())) {
            //是否开放注册:0未开放，1开放
            map.put("status", SendMessageConstants.EMAIL_STATUS_EIGHT_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_EIGHT_STATUS));
            return map;
        }
        //邮箱转换大小写
        email = email.toLowerCase();
        String emailVerification = VerifyCodeUtils.createRandom(true, 4);//邮件验证码
        //需要判断注册邮箱在库中是否存在
        UserMain userMain = new UserMain();
        userMain.setEmail(email);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (!userMains.isEmpty()) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_SEVEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_SEVEN_MESSAGE));
            return map;
        }
        cache.put(CacheConstant.EMAIL_ADDR + sessionId, email, 1800L);
        cache.put(CacheConstant.EMAIL_CODE + sessionId, emailVerification, 1800L);
        cache.put(CacheConstant.SMS_IS_SEND_REGISTER + sessionId, "1", 60L);
        logger.info("您的" + email + "邮箱验证码是：" + emailVerification);
        return sendEmailService.sendEmail(email, FYUtils.fyParams("账号注册"), FYUtils.fy("您的邮箱验证码是：")+emailVerification);
    }

    /**
     * 发送邮箱验证码(找回密码)
     *
     * @param email        邮箱
     * @param validateCode 验证码
     * @return
     */
    @RequestMapping(value = "forgetPasswordGetEmail")
    @ResponseBody
    public Object forgetPasswordGetEmail(String email, String validateCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(validateCode)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_NINE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_NINE_MESSAGE));
            return map;
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), validateCode)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_TEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_TEN_MESSAGE));
            return map;
        }
        if (StringUtils.isBlank(email)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_ONE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_ONE_MESSAGE));
            return map;
        }
        if (StringUtils.isNotBlank(email) && !RegexUtils.checkEmail(email)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_FOUR_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_FOUR_MESSAGE));
            return map;
        }
        if (cache.get(CacheConstant.EMAIL_IS_SEND_FORGETPWD + R.getSession().getId()) != null) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_TWO_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_TWO_MESSAGE));
            return map;
        }
        //邮箱转换大小写
        email = email.toLowerCase();
        String emailVerification = VerifyCodeUtils.createRandom(true, 4);//邮件验证码
        //判断是否存在该账号
        String loginName = R.get("loginName");
        UserMain userMain = new UserMain();
        userMain.setLoginName(loginName);
        userMain.setEmail(email);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (userMains.isEmpty()) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_FIVE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_FIVE_MESSAGE));
            return map;
        }
        UserMain userMain2 = userMains.get(0);
        //账号被锁定不让发送
        if ("1".equals(userMain2.getIsLocked())) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_THREE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_THREE_MESSAGE));
            return map;
        }
        //子账号不允许找回密码
        if ("2".equals(userMain2.getTypeAccount())) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_SIX_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_SIX_MESSAGE));
            return map;
        }
        cache.put(CacheConstant.EMAIL_ADDR + userMains.get(0).getUId(), userMains.get(0).getUId(), 1800L);
        cache.put(CacheConstant.EMAIL_CODE + userMains.get(0).getUId(), emailVerification, 1800L);
        cache.put(CacheConstant.EMAIL_IS_SEND_FORGETPWD + R.getSession().getId(), "1", 60L);
        logger.info("您的" + email + "邮件验证码是：" + emailVerification);
        return sendEmailService.sendEmail(userMains.get(0).getEmail(), FYUtils.fyParams("找回密码"), FYUtils.fy("您的邮箱验证码是：")+emailVerification);
    }

    /**
     * 绑定邮箱
     *
     * @param email        邮箱
     * @param validateCode 验证码
     */
    @RequestMapping(value = "bindingGetEmail")
    @ResponseBody
    public Object bindingGetEmail(String email, String validateCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(validateCode)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_NINE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_NINE_MESSAGE));
            return map;
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), validateCode)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_TEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_TEN_MESSAGE));
            return map;
        }
        if (StringUtils.isBlank(email)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_ONE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_ONE_STATUS));
            return map;
        }
        if (StringUtils.isNotBlank(email) && !RegexUtils.checkEmail(email)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_FOUR_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_FOUR_MESSAGE));
            return map;
        }
        if (cache.get(CacheConstant.EMAIL_IS_SEND_BINGDING + R.getSession().getId()) != null) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_TWO_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_TWO_STATUS));
            return map;
        }
        //邮箱转换大小写
        email = email.toLowerCase();
        String emailVerification = VerifyCodeUtils.createRandom(true, 4);//邮件验证码
        UserMain userMain = new UserMain();
        userMain.setEmail(email);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (!userMains.isEmpty()) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_SEVEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_SEVEN_MESSAGE));
            return map;
        }
        UserMain userMain2 = SsoUtils.getUserMain();
        //账号被锁定不让发送
        if ("1".equals(userMain2.getIsLocked())) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_THREE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_THREE_MESSAGE));
            return map;
        }
        cache.put(CacheConstant.EMAIL_ADDR + email, email, 1800L);
        cache.put(CacheConstant.EMAIL_CODE + userMain2.getUId(), emailVerification, 1800L);
        cache.put(CacheConstant.EMAIL_IS_SEND_BINGDING + R.getSession().getId(), "1", 60L);
        logger.info("您的" + email + FYUtils.fyParams("邮件验证码是：") + emailVerification);
        return sendEmailService.sendEmail(email, FYUtils.fyParams("绑定邮箱"), FYUtils.fy("您的邮箱验证码是：")+emailVerification);
    }

    /**
     * 安全验证页发送邮件
     */
    @RequestMapping(value = "verificationSecurityGetEmail")
    @ResponseBody
    public Object verificationSecurityGetEmail(String email, String validateCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(validateCode)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_NINE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_NINE_MESSAGE));
            return map;
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), validateCode)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_TEN_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_TEN_MESSAGE));
            return map;
        }
        if (StringUtils.isBlank(email)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_ONE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_EIGHT_MESSAGE));
            return map;
        }
        if (StringUtils.isNotBlank(email) && !RegexUtils.checkEmail(email)) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_FOUR_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_FOUR_MESSAGE));
            return map;
        }
        if (cache.get(CacheConstant.EMAIL_IS_SEND_SECURITY + R.getSession().getId()) != null) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_TWO_STATUS);
            map.put("message", SendMessageConstants.EMAIL_STATUS_TWO_MESSAGE);
            return map;
        }
        //邮箱转换大小写
        email = email.toLowerCase();
        String emailVerification = VerifyCodeUtils.createRandom(true, 4);//邮件验证码
        UserMain userMain = new UserMain();
        userMain.setEmail(email);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (userMains.isEmpty()) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_FIVE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_FIVE_MESSAGE));
            return map;
        }
        UserMain userMain2 = userMains.get(0);
        //账号被锁定不让发送
        if ("1".equals(userMain2.getIsLocked())) {
            map.put("status", SendMessageConstants.EMAIL_STATUS_THREE_STATUS);
            map.put("message", FYUtils.fyParams(SendMessageConstants.EMAIL_STATUS_THREE_MESSAGE));
            return map;
        }
        cache.put(CacheConstant.EMAIL_ADDR + userMain2.getEmail(), userMains.get(0).getEmail(), 1800L);
        cache.put(CacheConstant.EMAIL_CODE + userMain2.getUId(), emailVerification, 1800L);
        cache.put(CacheConstant.EMAIL_IS_SEND_SECURITY + R.getSession().getId(), "1", 60L);
        logger.info("您的" + email + "邮件验证码是：" + emailVerification);
        return sendEmailService.sendEmail(email, FYUtils.fyParams("绑定邮箱"), FYUtils.fy("您的邮箱验证码是：")+emailVerification);
    }
}
