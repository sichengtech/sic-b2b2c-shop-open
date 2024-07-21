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
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.SiteRegisterService;
import com.sicheng.wap.service.UserMainService;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
  * <p>标题: ForgetPwdController</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zjl
  * @version 2018年1月28日 上午9:15:13
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class ForgetPwdController extends BaseController {

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private SiteRegisterService siteRegisterService;

    /**
      * 忘记密码--修改成新密码
      * @return
     */
    @RequestMapping(value = "/{version}/user/forgetPwd/edit")
    @ResponseBody
    public Map<String, Object> userForgetPwdEdit() {
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());//注册设置
        String type = R.get("type");//找回密码的类型(1邮箱地址找回密码、2手机号找回密码)
        if (StringUtils.isBlank(type)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("请选择找回密码类型"), null, null);
        }
        if ("1".equals(type)) {
            //表单验证(邮箱地址找回密码)
            List<String> errorList = validate1(siteRegister);
            if (errorList.size() > 0) {
                String message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            //具体业务
            String email = R.get("email");//邮箱地址
            String epassword = R.get("epassword");//设置的新密码
            UserMain userMain = new UserMain();
            userMain.setEmail(email);
            userMainService.editForgetPwd(type, userMain, epassword);
        }
        if ("2".equals(type)) {
            String mobile = R.get("mobile");//手机号
            String mpassword = R.get("mpassword");//设置的新密码
            //表单验证(手机号找回密码)
            List<String> errorList = validate2(siteRegister);
            if (errorList.size() > 0) {
                String message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            //具体业务
            UserMain userMain = new UserMain();
            userMain.setMobile(mobile);
            userMainService.editForgetPwd(type, userMain, mpassword);
        }
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("找回密码成功"), "/user/login/form.htm", null);
    }

    /**
      * 表单验证(邮箱地址找回密码) 
      * @param model
      * @return
     */
    private List<String> validate1(SiteRegister siteRegister) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("email"))) {
            errorList.add(FYUtils.fy("请输入邮箱地址"));
        }
        UserMain userMain = null;
        if (StringUtils.isNotBlank(R.get("email"))) {
            if (!RegexUtils.checkEmail(R.get("email"))) {
                errorList.add(FYUtils.fy("邮箱地址格式不正确"));
            }
            UserMain entity = new UserMain();
            entity.setEmail(R.get("email"));
            userMain = userMainService.selectOne(new Wrapper(entity));
            if (userMain == null) {
                errorList.add(FYUtils.fy("当前邮箱地址不存在，请输入其他邮箱地址"));
            }
            if (userMain != null && "0".equals(userMain.getEmailValidate())) {
                //emailValidate(邮箱地址是否通过验证(0否，1是))
                errorList.add(FYUtils.fy("当前邮箱地址未通过验证"));
            }
        }
        if (StringUtils.isBlank(R.get("ecode"))) {
            errorList.add(FYUtils.fy("请输入邮箱验证码"));
        }
        if (userMain != null && cache.get(CacheConstant.EMAIL_ADDR + userMain.getUId()) == null) {
            errorList.add(FYUtils.fy("请填写正确邮箱地址"));
        }
        if (userMain != null && StringUtils.isNotBlank(R.get("email")) && !(R.get("email")).equals(cache.get(CacheConstant.EMAIL_ADDR + userMain.getUId()))) {
            errorList.add(FYUtils.fy("请填写正确邮箱地址"));
        }
        if (userMain != null && cache.get(CacheConstant.EMAIL_CODE + userMain.getUId()) == null) {
            errorList.add(FYUtils.fy("请填写正确邮箱验证码"));
        }
        if (userMain != null && StringUtils.isNotBlank(R.get("ecode")) && !(R.get("ecode")).equals(cache.get(CacheConstant.EMAIL_CODE + userMain.getUId()))) {
            errorList.add(FYUtils.fy("验证码无效"));
        }
        if (StringUtils.isBlank(R.get("epassword"))) {
            errorList.add(FYUtils.fy("设置密码不能为空"));
        }
        if (siteRegister == null && R.get("epassword").length() > 64) {
            errorList.add(FYUtils.fy("密码不能超过64字符"));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("epassword")) && R.get("epassword").length() > siteRegister.getPwdMax()) {
        	errorList.add(FYUtils.fyParam("密码最大长度", siteRegister.getUsernameMax()+""));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("epassword")) && R.get("epassword").length() < siteRegister.getPwdMin()) {
            errorList.add(FYUtils.fyParam("密码最小长度", siteRegister.getUsernameMin()+""));
        }
        if (StringUtils.isBlank(R.get("enextPassword"))) {
            errorList.add(FYUtils.fy("设置密码不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("epassword")) && StringUtils.isNotBlank(R.get("enextPassword")) && !R.get("epassword").equals(R.get("enextPassword"))) {
            errorList.add(FYUtils.fy("设置密码与确认密码不一致"));
        }
        return errorList;
    }

    /**
      * 表单验证(手机号找回密码) 
      * @param model
      * @return
     */
    private List<String> validate2(SiteRegister siteRegister) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("mobile"))) {
            errorList.add(FYUtils.fy("请输入手机号"));
        }
        UserMain userMain = null;
        if (StringUtils.isNotBlank(R.get("mobile"))) {
            if (!RegexUtils.checkMobile(R.get("mobile"))) {
                errorList.add(FYUtils.fy("手机号格式不正确"));
            }
            UserMain entity = new UserMain();
            entity.setMobile(R.get("mobile"));
            userMain = userMainService.selectOne(new Wrapper(entity));
            if (userMain == null) {
                errorList.add(FYUtils.fy("当前手机号不存在，请输入其他手机号"));
            }
            if (userMain != null && "0".equals(userMain.getMobileValidate())) {
                //mobileValidate(手机号是否通过验证(0否，1是))
                errorList.add(FYUtils.fy("当前手机号未通过验证"));
            }
        }
        if (StringUtils.isBlank(R.get("mcode"))) {
            errorList.add(FYUtils.fy("请输入短信验证码"));
        }
        if (cache.get(CacheConstant.SMS_MOBILE + userMain.getUId()) == null) {
            errorList.add(FYUtils.fy("请填写正确手机号"));
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && !(R.get("mobile")).equals(cache.get(CacheConstant.SMS_MOBILE + userMain.getUId()))) {
            errorList.add(FYUtils.fy("请填写正确手机号"));
        }
        if (cache.get(CacheConstant.SMS_CODE + userMain.getUId()) == null) {
            errorList.add(FYUtils.fy("请填写正确短信验证码"));
        }
        if (StringUtils.isNotBlank(R.get("mcode")) && !(R.get("mcode")).equals(cache.get(CacheConstant.SMS_CODE + userMain.getUId()))) {
            errorList.add(FYUtils.fy("验证码无效"));
        }
        if (StringUtils.isBlank(R.get("mpassword"))) {
            errorList.add(FYUtils.fy("设置密码不能为空"));
        }
        if (siteRegister == null && R.get("mpassword").length() > 64) {
            errorList.add(FYUtils.fy("密码不能超过64字符"));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("epassword")) && R.get("epassword").length() > siteRegister.getPwdMax()) {
        	errorList.add(FYUtils.fyParam("密码最大长度", siteRegister.getUsernameMax()+""));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("epassword")) && R.get("epassword").length() < siteRegister.getPwdMin()) {
        	errorList.add(FYUtils.fyParam("密码最小长度", siteRegister.getUsernameMin()+""));
        }
        if (StringUtils.isBlank(R.get("mnextPassword"))) {
            errorList.add(FYUtils.fy("设置密码不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("mpassword")) && StringUtils.isNotBlank(R.get("mnextPassword")) && !R.get("mpassword").equals(R.get("mnextPassword"))) {
            errorList.add(FYUtils.fy("设置密码与确认密码不一致"));
        }
        return errorList;
    }
}
