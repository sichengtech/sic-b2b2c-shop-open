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
 *  
 */
package com.sicheng.wap.web.api;

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * <p>标题: RegisterController</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2018年1月28日 上午9:15:30
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class RegisterController extends BaseController {

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private SiteRegisterService siteRegisterService;

    /**
      * 保存注册用户信息 
      * @return
     */
    @RequestMapping(value = "/{version}/user/register/save")
    @ResponseBody
    public Map<String, Object> userRegisterSave() {
//		Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());//微信授权的openId
//		//验证是否微信登录
//		if(openId==null){
//			return ApiUtils.getMap(ApiUtils.STATUS_INVALID,"请使用微信登录",null,null);
//		}
        //验证网站是否开发注册
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        if (siteRegister != null && "0".equals(siteRegister.getIsRegister())) {
            //是否开放注册:0未开放，1开放
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("网站没有开放注册"), null, null);
        }
        String type = R.get("type");//注册类型，1账号注册、2手机号注册
        if (StringUtils.isBlank(type)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("请选择注册类型"), null, null);
        }
        String sessionId = AppTokenUtils.getRequestFlag();//session的id
        String salt = IdGen.randomBase62(32);//盐
        String appToken = null;
        UserMain userMain = null;
        if ("1".equals(type)) {
            String loginName = R.get("loginName");//用户名
            String password = R.get("password");//密码
            String email = R.get("email");//邮箱地址
            //表单验证(账号注册的)
            List<String> errorList = validate1(siteRegister);
            if (errorList.size() > 0) {
                String message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            //具体业务
            userMain = new UserMain();
            userMain.setLoginName(loginName);//用户名
            userMain.setPassword(PasswordUtils.entryptPassword(password, salt));//密码
            userMain.setEmail(email.toLowerCase());//邮箱地址
            userMain.setEmailValidate("1");//邮箱是否通过验证(0否，1是)
            userMain.setSalt(salt);//盐
            userMain.setParentUid(0L);//父ID，为0表示是主账号
            userMain.addTypeUserMember();//添加买家
            userMain.setTypeAccount("1");//账号类型 (1主账号,2子账号)
            userMain.setRegisterIp(R.getRealIp());//注册ip
            userMain.setLoginIp(R.getRealIp());//最后登录ip
            userMain.setLoginDate(new Date());//最后登录日期
            //userMainService.saveRrgister(userMain,openId.toString());
            appToken = userMainService.saveRegister(userMain, null);
            cache.del(CacheConstant.EMAIL_ADDR + sessionId);
            cache.del(CacheConstant.EMAIL_CODE + sessionId);
        }
        if ("2".equals(type)) {
            String mobile = R.get("mobile");//手机号
            //表单验证(手机号注册的)
            List<String> errorList = validate2();
            if (errorList.size() > 0) {
                String message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            //具体业务
            userMain = new UserMain();
            userMain.setLoginName(mobile);
            userMain.setMobile(mobile);
            userMain.setMobileValidate("1");//手机号是否通过验证(0否，1是)
            userMain.setSalt(salt);//盐
            userMain.setParentUid(0L);//父ID，为0表示是主账号
            userMain.addTypeUserMember();//添加买家
            userMain.setTypeAccount("1");//账号类型 (1主账号,2子账号)
            userMain.setRegisterIp(R.getRealIp());//注册ip
            userMain.setLoginIp(R.getRealIp());//最后登录ip
            userMain.setLoginDate(new Date());//最后登录日期
            //userMainService.saveRrgister(userMain,openId.toString());
            appToken = userMainService.saveRegister(userMain, null);
            cache.del(CacheConstant.SMS_MOBILE + sessionId);
            cache.del(CacheConstant.SMS_CODE + sessionId);
        }
        //注册成功后清除缓存中的微信openId
        cache.del(CacheConstant.WEIXIN_OPENID + AppTokenUtils.getRequestFlag());

        if (AppTokenUtils.isAppRequest()) {
            //APP
            Map<String, Object> data = LoginController.data(userMain, appToken);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("注册成功"), data, null);
        } else {
            //wap微信商城
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("注册成功"), "/user/userCentral.htm", null);
        }
    }

    /**
      * 表单验证(账号注册) 
      * @param model
      * @return
     */
    private List<String> validate1(SiteRegister siteRegister) {
        String sessionId = AppTokenUtils.getRequestFlag();
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("loginName"))) {
            errorList.add(FYUtils.fy("用户名不能为空"));
        }
        if (siteRegister == null && StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > 64) {
            errorList.add(FYUtils.fy("用户名不能超过64字符"));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > siteRegister.getUsernameMax()) {
            errorList.add(FYUtils.fyParam("用户名最大长度", siteRegister.getUsernameMax()+""));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() < siteRegister.getUsernameMin()) {
            errorList.add(FYUtils.fyParam("用户名最小长度", siteRegister.getUsernameMin()+""));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("loginName")) && StringUtils.isNotBlank(siteRegister.getDisableUsername())) {
            String[] disableName = siteRegister.getDisableUsername().split(",");
            for (int i = 0; i < disableName.length; i++) {
                if (R.get("loginName").equals(disableName)) {
                    errorList.add(FYUtils.fy("用户名不能是") + disableName);
                }
            }
        }
        if (StringUtils.isNotBlank(R.get("loginName"))) {
            Wrapper wrapper = new Wrapper();
            wrapper.and("lower(login_name) =",R.get("loginName").toLowerCase());
            List<UserMain> list = userMainService.selectByWhere(wrapper);
            if (!list.isEmpty()) {
                errorList.add(FYUtils.fy("账号已存在，请输入其他账号"));
            }
        }
        if (StringUtils.isBlank(R.get("password"))) {
            errorList.add(FYUtils.fy("密码不能为空"));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("epassword")) && R.get("epassword").length() > siteRegister.getPwdMax()) {
        	errorList.add(FYUtils.fyParam("密码最大长度", siteRegister.getUsernameMax()+""));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("epassword")) && R.get("epassword").length() < siteRegister.getPwdMin()) {
            errorList.add(FYUtils.fyParam("密码最小长度", siteRegister.getUsernameMin()+""));
        }
        if (StringUtils.isBlank(R.get("email"))) {
            errorList.add(FYUtils.fy("邮箱地址不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("email")) && !RegexUtils.checkEmail(R.get("email"))) {
            errorList.add(FYUtils.fy("邮箱地址格式不正确"));
        }
        if (StringUtils.isNotBlank(R.get("email"))) {
            UserMain userMain = new UserMain();
            userMain.setEmail(R.get("email").toLowerCase());
            List<UserMain> list = userMainService.selectByWhere(new Wrapper(userMain));
            if (!list.isEmpty()) {
                errorList.add(FYUtils.fy("邮箱地址已存在，请输入其他邮箱地址"));
            }
        }
        if (StringUtils.isNotBlank(R.get("email")) && R.get("email").length() > 64) {
            errorList.add(FYUtils.fy("邮箱地址不能超64字符"));
        }
        if (StringUtils.isBlank(R.get("emailCode"))) {
            errorList.add(FYUtils.fy("邮箱验证码不能为空"));
        }
        if (cache.get(CacheConstant.EMAIL_ADDR + sessionId) == null) {
            errorList.add(FYUtils.fy("请填写正确邮箱地址"));
        }
        if (StringUtils.isNotBlank(R.get("email")) && !(R.get("email")).equals(cache.get(CacheConstant.EMAIL_ADDR + sessionId))) {
            errorList.add(FYUtils.fy("请填写正确邮箱地址"));
        }
        if (cache.get(CacheConstant.EMAIL_CODE + sessionId) == null) {
            errorList.add(FYUtils.fy("请填写正确邮箱验证码"));
        }
        if (StringUtils.isNotBlank(R.get("emailCode")) && !(R.get("emailCode")).equals(cache.get(CacheConstant.EMAIL_CODE + sessionId))) {
            errorList.add(FYUtils.fy("验证码无效"));
        }
        return errorList;
    }

    /**
      * 表单验证(手机号注册) 
      * @param model
      * @return
     */
    private List<String> validate2() {
        String sessionId = AppTokenUtils.getRequestFlag();
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("mobile"))) {
            errorList.add(FYUtils.fy("手机号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("mobile"))) {
            if (R.get("mobile").length() > 64) {
                errorList.add(FYUtils.fy("手机号不能超64字符"));
            }
            if (!RegexUtils.checkMobile(R.get("mobile"))) {
                errorList.add(FYUtils.fy("手机号格式不正确"));
            }
            UserMain userMain = new UserMain();
            userMain.setMobile(R.get("mobile"));
            List<UserMain> list = userMainService.selectByWhere(new Wrapper(userMain));
            if (!list.isEmpty()) {
                errorList.add(FYUtils.fy("手机号已存在，请输入其他手机号"));
            }
        }
        if (StringUtils.isBlank(R.get("mobileCode"))) {
            errorList.add(FYUtils.fy("短信验证码不能为空"));
        }
        if (cache.get(CacheConstant.SMS_MOBILE + sessionId) == null) {
            errorList.add(FYUtils.fy("请填写正确手机号"));
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && !(R.get("mobile")).equals(cache.get(CacheConstant.SMS_MOBILE + sessionId))) {
            errorList.add(FYUtils.fy("请填写正确手机号"));
        }
        if (cache.get(CacheConstant.SMS_CODE + sessionId) == null) {
            errorList.add(FYUtils.fy("请填写正确短信验证码"));
        }
        if (StringUtils.isNotBlank(R.get("mobileCode")) && !(R.get("mobileCode")).equals(cache.get(CacheConstant.SMS_CODE + sessionId))) {
            errorList.add(FYUtils.fy("验证码无效"));
        }
        return errorList;
    }
}
