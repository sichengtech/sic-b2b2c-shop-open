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

package com.sicheng.wap.web.api;

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.sms.SmsSender;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.utils.VerifyCodeUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.SiteMessageTemplateService;
import com.sicheng.wap.service.SiteRegisterService;
import com.sicheng.wap.service.UserMainService;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * <p>标题: SenderSmsController</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zjl
  * @version 2018年1月28日 上午9:15:55
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class SenderSmsController extends BaseController {

    private static String MESSAGE_TEMPLATE_NUM = "activateAccount";//短信模板编号！

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private SiteMessageTemplateService siteMessageTemplateService;

    @Autowired
    private SiteRegisterService siteRegisterService;


    /**
      * 手机号注册，发送短信验证码
      * @return
     */
    @RequestMapping(value = "/{version}/sms/register/getCode")
    @ResponseBody
    public Map<String, Object> registerGetCode() {
        //验证发送短信过于频繁
        if (cache.get(CacheConstant.SMS_IS_SEND_REGISTER + AppTokenUtils.getRequestFlag()) != null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("您发送短信过于频繁"), null, null);
        }
        //验证网站是否开放注册
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        if (siteRegister != null && "0".equals(siteRegister.getIsRegister())) {
            //是否开放注册:0未开放，1开放
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("网站没有开放注册"), null, null);
        }
        List<String> errorList = new ArrayList<>();
        Boolean flag = true;
        //发送短信前的验证
        //Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());//微信授权的openId
        String mobile = R.get("mobile");//手机号
//		if(openId==null){
//			errorList.add("请使用微信登录");
//			flag=false;
//		}
        if (StringUtils.isBlank(mobile)) {
            errorList.add(FYUtils.fy("请输入手机号"));
            flag = false;
        }
        UserMain userMain = null;
        if (StringUtils.isNotBlank(mobile)) {
            if (!RegexUtils.checkMobile(mobile)) {
                errorList.add(FYUtils.fy("手机号格式不正确"));
                flag = false;
            }
            UserMain entity = new UserMain();
            entity.setMobile(mobile);
            userMain = userMainService.selectOne(new Wrapper(entity));
            if (userMain != null) {
                errorList.add(FYUtils.fy("手机号已存在，请输入其他手机号"));
                flag = false;
            }
        }
        String smsCode = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code", smsCode);
        String completeContent = siteMessageTemplateService.getSmsContent(paramMap, MESSAGE_TEMPLATE_NUM);//使用本地消息模板获得的完整内容
        if (StringUtils.isBlank(completeContent)) {
            errorList.add(FYUtils.fy("短信消息模板已停用"));
            flag = false;
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!flag) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //发送短信
        String sessionId = AppTokenUtils.getRequestFlag();
        cache.put(CacheConstant.SMS_MOBILE + sessionId, mobile, 1800L);
        cache.put(CacheConstant.SMS_CODE + sessionId, smsCode, 1800L);
        cache.put(CacheConstant.SMS_IS_SEND_REGISTER + sessionId, "1", 60L);
        boolean async = true;//true表示异步发送，false表示同步发送
        smsSender.sendSmsMessage(mobile, completeContent, paramMap, MESSAGE_TEMPLATE_NUM, async);
        logger.info("手机号：" + mobile + ",短信验证码：" + smsCode);
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("短信验证码已发送"), null, null);
    }

    /**
      * 手机号登录，发送短信验证码
      * @return
     */
    @RequestMapping(value = "/{version}/sms/login/getCode")
    @ResponseBody
    public Map<String, Object> loginGetCode() {
        //验证发送短信过于频繁
        if (cache.get(CacheConstant.SMS_IS_SEND_LOGIN + AppTokenUtils.getRequestFlag()) != null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("您发送短信过于频繁"), null, null);
        }
        List<String> errorList = new ArrayList<>();
        Boolean flag = true;
        //发送短信前的验证
        //Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());//微信授权的openId
        String mobile = R.get("mobile");//手机号
//		if(openId==null){
//			errorList.add("请使用微信登录");
//			flag=false;
//		}
        if (StringUtils.isBlank(mobile)) {
            errorList.add(FYUtils.fy("请输入手机号"));
            flag = false;
        }
        UserMain userMain = null;
        if (StringUtils.isNotBlank(mobile)) {
            if (!RegexUtils.checkMobile(mobile)) {
                errorList.add(FYUtils.fy("手机号格式不正确"));
                flag = false;
            }
            UserMain entity = new UserMain();
            entity.setMobile(mobile);
            userMain = userMainService.selectOne(new Wrapper(entity));
            if (userMain == null) {
                errorList.add(FYUtils.fy("手机号已存在，请输入其他手机号"));
                flag = false;
            }
            if (userMain != null && "0".equals(userMain.getMobileValidate())) {
                //mobileValidate(手机号是否通过验证(0否，1是))
                errorList.add(FYUtils.fy("当前手机号未通过验证"));
                flag = false;
            }
            if (userMain != null && "1".equals(userMain.getIsLocked())) {
                //isLocked(是否锁定(0否，1是)锁定后不能登录)
                errorList.add(FYUtils.fy("当前账号被锁定，请联系管理员"));
                flag = false;
            }
        }
        String smsCode = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code", smsCode);
        String completeContent = siteMessageTemplateService.getSmsContent(paramMap, MESSAGE_TEMPLATE_NUM);//使用本地消息模板获得的完整内容
        if (StringUtils.isBlank(completeContent)) {
            errorList.add(FYUtils.fy("短信消息模板已停用"));
            flag = false;
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!flag) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //发送短信
        cache.put(CacheConstant.SMS_MOBILE + userMain.getUId(), mobile, 1800L);
        cache.put(CacheConstant.SMS_CODE + userMain.getUId(), smsCode, 1800L);
        cache.put(CacheConstant.SMS_IS_SEND_LOGIN + AppTokenUtils.getRequestFlag(), "1", 60L);
        boolean async = true;//true表示异步发送，false表示同步发送
        smsSender.sendSmsMessage(mobile, completeContent, paramMap, MESSAGE_TEMPLATE_NUM, async);
        logger.info("手机号：" + mobile + ",短信验证码：" + smsCode);
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("短信验证码已发送"), null, null);
    }

    /**
      * 手机号找回密码，发送短信验证码 
      * @return
     */
    @RequestMapping(value = "/{version}/sms/forgetPwd/getCode")
    @ResponseBody
    public Map<String, Object> forgetPwdGetCode() {
        //验证发送短信过于频繁
        if (cache.get(CacheConstant.SMS_IS_SEND_FORGETPWD + AppTokenUtils.getRequestFlag()) != null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("您发送短信过于频繁"), null, null);
        }
        List<String> errorList = new ArrayList<>();
        Boolean flag = true;
        //发送短信前的验证
        //Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());//微信授权的openId
        String mobile = R.get("mobile");//手机号
//		if(openId==null){
//			errorList.add("请使用微信登录");
//			flag=false;
//		}
        if (StringUtils.isBlank(mobile)) {
            errorList.add(FYUtils.fy("请输入手机号"));
            flag = false;
        }
        UserMain userMain = null;
        if (StringUtils.isNotBlank(mobile)) {
            if (!RegexUtils.checkMobile(mobile)) {
                errorList.add(FYUtils.fy("手机号格式不正确"));
                flag = false;
            }
            UserMain entity = new UserMain();
            entity.setMobile(mobile);
            userMain = userMainService.selectOne(new Wrapper(entity));
            if (userMain == null) {
                errorList.add(FYUtils.fy("当前手机号不存在，请输入其他手机号"));
                flag = false;
            }
            if (userMain != null && "0".equals(userMain.getMobileValidate())) {
                //mobileValidate(手机号是否通过验证(0否，1是))
                errorList.add(FYUtils.fy("当前手机号未通过验证"));
                flag = false;
            }
            if (userMain != null && "1".equals(userMain.getIsLocked())) {
                //isLocked(是否锁定(0否，1是)锁定后不能登录)
                errorList.add(FYUtils.fy("当前账号被锁定，请联系管理员"));
                flag = false;
            }
        }
        String smsCode = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code", smsCode);
        String completeContent = siteMessageTemplateService.getSmsContent(paramMap, MESSAGE_TEMPLATE_NUM);//使用本地消息模板获得的完整内容
        if (StringUtils.isBlank(completeContent)) {
            errorList.add(FYUtils.fy("短信消息模板已停用"));
            flag = false;
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!flag) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //发送短信
        cache.put(CacheConstant.SMS_MOBILE + userMain.getUId(), mobile, 1800L);
        cache.put(CacheConstant.SMS_CODE + userMain.getUId(), smsCode, 1800L);
        cache.put(CacheConstant.SMS_IS_SEND_FORGETPWD + AppTokenUtils.getRequestFlag(), "1", 60L);
        boolean async = true;//true表示异步发送，false表示同步发送
        smsSender.sendSmsMessage(mobile, completeContent, paramMap, MESSAGE_TEMPLATE_NUM, async);
        logger.info("手机号：" + mobile + ",短信验证码：" + smsCode);
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("短信验证码已发送"), null, null);
    }
}
