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

import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.email.EmailSender;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.utils.VerifyCodeUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.SiteInfoService;
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
import java.util.List;
import java.util.Map;

/**
  * <p>标题: SenderEmailController</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zjl
  * @version 2018年1月28日 上午9:15:43
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class SenderEmailController extends BaseController {

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private SiteInfoService siteInfoService;

    @Autowired
    private SiteRegisterService siteRegisterService;

    /**
      * 账号注册，发送邮件验证码
      * @return
     */
    @RequestMapping(value = "/{version}/email/register/getCode")
    @ResponseBody
    public Map<String, Object> registerGetCode() {
        //发送邮件过于频繁验证
        if (cache.get(CacheConstant.EMAIL_IS_SEND_REGISTER + AppTokenUtils.getRequestFlag()) != null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("您发送邮件过于频繁"), null, null);
        }
        //验证网站是否开放注册
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        if (siteRegister != null && "0".equals(siteRegister.getIsRegister())) {
            //是否开放注册:0未开放，1开放
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("网站没有开放注册"), null, null);
        }
        List<String> errorList = new ArrayList<>();
        Boolean flag = true;
        //发送邮件前的验证
//		Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());//微信授权的openId
        String loginName = R.get("loginName");//账号
        String email = R.get("email");//邮箱地址
//		if(openId==null){
//			errorList.add("请使用微信登录");
//			flag=false;
//		}
        //账号验证
        if (StringUtils.isNotBlank(loginName)) {
            UserMain usermain = new UserMain();
            usermain.setLoginName(loginName.toLowerCase());
            usermain = userMainService.selectOne(new Wrapper(usermain));
            if (usermain != null) {
                errorList.add(FYUtils.fy("用户名已存在"));
                flag = false;
            }
        }
        if (StringUtils.isBlank(email)) {
            errorList.add(FYUtils.fy("请输入邮箱地址"));
            flag = false;
        }
        if (StringUtils.isNotBlank(email)) {
            if (!RegexUtils.checkEmail(email)) {
                errorList.add(FYUtils.fy("请填写正确邮箱地址"));
                flag = false;
            }
            UserMain userMain = new UserMain();
            userMain.setEmail(email.toLowerCase());
            List<UserMain> list = userMainService.selectByWhere(new Wrapper(userMain));
            if (!list.isEmpty()) {
                errorList.add(FYUtils.fy("邮箱地址已存在，请输入其他邮箱地址"));
                flag = false;
            }
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!flag) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //发送邮件
        String emailCode = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        String sessionId = AppTokenUtils.getRequestFlag();
        cache.put(CacheConstant.EMAIL_ADDR + sessionId, email, 1800L);
        cache.put(CacheConstant.EMAIL_CODE + sessionId, emailCode, 1800L);
        cache.put(CacheConstant.EMAIL_IS_SEND_REGISTER + AppTokenUtils.getRequestFlag(), "1", 60L);
        boolean async = true;
        SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
        if (siteInfo != null) {
            emailSender.send(email, FYUtils.fy("邮件验证码"), FYUtils.fy("您的验证码是：") + emailCode, async);
        }
        logger.info("邮箱地址：" + email + ",邮箱验证码：" + emailCode);
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("邮箱验证码已发送"), null, null);
    }

    /**
      * 邮箱地址找回密码，发送邮件验证码
      * @return
     */
    @RequestMapping(value = "/{version}/eamil/forgetPwd/getCode")
    @ResponseBody
    public Map<String, Object> forgetPwdGetCode() {
        //发送邮件过于频繁验证
        if (cache.get(CacheConstant.EMAIL_IS_SEND_FORGETPWD + AppTokenUtils.getRequestFlag()) != null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("您发送邮件过于频繁"), null, null);
        }
        List<String> errorList = new ArrayList<>();
        Boolean flag = true;
        //发送短信前的验证
//		Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());//微信授权的openId
        String email = R.get("email");//邮箱地址
//		if(openId==null){
//			errorList.add("请使用微信登录");
//			flag=false;
//		}
        if (StringUtils.isBlank(email)) {
            errorList.add(FYUtils.fy("请输入邮箱地址"));
            flag = false;
        }
        UserMain userMain = null;
        if (StringUtils.isNotBlank(email)) {
            if (!RegexUtils.checkEmail(email)) {
                errorList.add(FYUtils.fy("邮箱地址格式不正确"));
                flag = false;
            }
            UserMain entity = new UserMain();
            entity.setEmail(email);
            userMain = userMainService.selectOne(new Wrapper(entity));
            if (userMain == null) {
                errorList.add(FYUtils.fy("当前邮箱地址不存在，请输入其他邮箱地址"));
                flag = false;
            }
            if (userMain != null && "0".equals(userMain.getEmailValidate())) {
                //emailValidate(邮箱地址是否通过验证(0否，1是))
                errorList.add(FYUtils.fy("当前邮箱地址未通过验证"));
                flag = false;
            }
            if (userMain != null && "1".equals(userMain.getIsLocked())) {
                //isLocked(是否锁定(0否，1是)锁定后不能登录)
                errorList.add(FYUtils.fy("当前账号被锁定，请联系管理员"));
                flag = false;
            }
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!flag) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //发送邮件
        String emailCode = VerifyCodeUtils.createRandom(true, 4);//短信验证码
        cache.put(CacheConstant.EMAIL_ADDR + userMain.getUId(), email, 1800L);
        cache.put(CacheConstant.EMAIL_CODE + userMain.getUId(), emailCode, 1800L);
        cache.put(CacheConstant.EMAIL_IS_SEND_FORGETPWD + AppTokenUtils.getRequestFlag(), "1", 60L);
        boolean async = true;
        SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
        if (siteInfo != null) {
            emailSender.send(email, FYUtils.fy("邮件验证码"), FYUtils.fy("您的验证码是：") + emailCode, async);
        }
        logger.info("邮箱地址：" + email + ",邮箱验证码：" + emailCode);
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("邮箱验证码已发送"), null, null);
    }
}
