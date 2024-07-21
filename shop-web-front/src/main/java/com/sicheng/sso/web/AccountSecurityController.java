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
package com.sicheng.sso.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.encryption.Encryption;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.servlet.ValidateCodeServlet;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.site.service.SiteRegisterService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.service.UserMemberService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 账户安全</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月7日 下午1:55:10
 */
@Controller
@RequestMapping(value = "${ssoPath}/accountSecurity")
public class AccountSecurityController extends BaseController {

    //为了在去目标页面在拦截器放过
    public static String TYPE = "type";
    public static String TYPE_VALUE = "1";

    @Autowired
    private SiteRegisterService siteRegisterService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private UserMemberService userMemberService;

    /**
     * 修改成功页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "ssoStatus")
    public String ssoStatus(HttpServletRequest request, Model model) {
        String successStatus = R.get("successStatus");
        model.addAttribute("successStatus", successStatus);
        return "sso/user/ssoStatus";
    }

    /**
     * 进去安全验证页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "verificationSecurity1")
    public String verificationSecurity1(HttpServletRequest request, Model model) {
        UserMain userMain = SsoUtils.getUserMain();
        Map<String, String> map = new HashMap<>();
        //邮箱是否通过验证(0否，1是)
        if ("1".equals(userMain.getEmailValidate()) && StringUtils.isNotBlank(userMain.getEmail())) {
            map.put("1", FYUtils.fyParams("邮箱【") + Encryption.EncryptionStr(userMain.getEmail(), 3, 6) + FYUtils.fyParams("】"));
            model.addAttribute("email", userMain.getEmail());
        }
        //手机号是否通过验证(0否，1是)
        if ("1".equals(userMain.getMobileValidate()) && StringUtils.isNotBlank(userMain.getMobile())) {
            map.put("2", FYUtils.fyParams("手机【") + Encryption.EncryptionStr(userMain.getMobile(), 3, 4) + FYUtils.fyParams("】"));
            model.addAttribute("mobile", userMain.getMobile());
        }
        model.addAttribute("map", map);
        //没有绑定手机和邮箱的，账号不需要验证手机和邮箱
        if (map.size() == 0) {
            cache.del(CacheConstant.EMAIL_ADDR + userMain.getEmail());
            cache.del(CacheConstant.EMAIL_CODE + userMain.getUId());
            cache.del(CacheConstant.SMS_MOBILE + userMain.getMobile());
            cache.del(CacheConstant.SMS_CODE + userMain.getUId());
            cache.del(CacheConstant.SECURITY_PATH + userMain.getUId());
            cache.put(CacheConstant.SECURITY_TYPE + userMain.getUId(), userMain.getUId(), 1800L);
            return "redirect:" + ssoPath + "/accountSecurity/changePassword1.htm";
        } else {
            cache.put(CacheConstant.SECURITY_PATH + userMain.getUId(), userMain.getUId(), 1800L);
        }
        return "sso/user/verificationSecurity";
    }

    /**
     * 保存安全验证
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "verificationSecurity2")
    public String verificationSecurity2(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String path = R.get("path");
        //表单验证
        List<String> errorList = validate1(model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return verificationSecurity1(request, model);//回显错误提示
        }
        UserMain u = SsoUtils.getUserMain();
        cache.del(CacheConstant.EMAIL_ADDR + u.getEmail());
        cache.del(CacheConstant.EMAIL_CODE + u.getUId());
        cache.del(CacheConstant.SMS_MOBILE + u.getMobile());
        cache.del(CacheConstant.SMS_CODE + u.getUId());
        cache.del(CacheConstant.SECURITY_PATH + u.getUId());
        cache.put(CacheConstant.SECURITY_TYPE + u.getUId(), u.getUId(), 1800L);
        if (StringUtils.isBlank(path)) {
            return "redirect:" + memberPath + "/index.htm";
        } else {
            return "redirect:" + path;
        }
    }


    /**
     * 进入修改密码页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "changePassword1")
    public String changePassword1(Model model) {
        UserMain userMain = SsoUtils.getUserMain();
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        model.addAttribute("siteRegister", siteRegister);
        model.addAttribute("userMain", userMain);
        model.addAttribute("highlight", "changePassword");//sso菜单高亮
        return "sso/user/changePassword";
    }

    /**
     * 保存密码
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "changePassword2")
    public String changePassword2(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate2(model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return changePassword1(model);//回显错误提示
        }
        String password = R.get("password");
        UserMain u = SsoUtils.getUserMain();
        UserMain userMain = new UserMain();
        userMain.setUId(u.getUId());
        userMain.setPassword(PasswordUtils.entryptPassword(password, u.getSalt()));
        userMainService.updateByIdSelective(userMain);
        cache.del(CacheConstant.SECURITY_TYPE + u.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("修改成功，请重新登录！"));
        return "redirect:" + ssoPath + "/logout.htm";
    }

    /**
     * 进入绑定邮箱页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "bindingEmail1")
    public String bindingEmail1(UserMain userMain, Model model) {
        model.addAttribute("userMain", userMain);
        model.addAttribute("highlight", "bindingEmail");//sso菜单高亮
        return "sso/user/bindingEmail";
    }

    /**
     * 保存绑定邮箱
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "bindingEmail2")
    public String bindingEmail2(UserMain userMain, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate3(model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return bindingEmail1(userMain, model);//回显错误提示
        }
        userMain.setEmailValidate("1"); //邮箱是否通过验证(0否，1是)
        UserMain u = SsoUtils.getUserMain();
        UserMain ua = new UserMain();
        ua.setId(u.getId());
        userMainService.updateByWhereSelective(userMain, new Wrapper(ua));
        userMainService.updateByIdSelective(userMain);
        cache.del(CacheConstant.EMAIL_ADDR + R.get("email").toLowerCase());
        cache.del(CacheConstant.EMAIL_CODE + u.getUId());
        cache.del(CacheConstant.SECURITY_TYPE + u.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("修改成功！"));
        return "redirect:" + ssoPath + "/accountSecurity/ssoStatus.htm?successStatus=1";
    }

    /**
     * 进入绑定手机页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "bindingMobile1")
    public String bindingMobile1(UserMain userMain, Model model) {
        model.addAttribute("userMain", userMain);
        model.addAttribute("highlight", "bindingMobile");//sso菜单高亮
        return "sso/user/bindingMobile";
    }

    /**
     * 保存绑定手机
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "bindingMobile2")
    public String bindingMobile2(UserMain userMain, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate4(model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return bindingMobile1(userMain, model);//回显错误提示
        }
        userMain.setMobileValidate("1"); //手机是否通过验证(0否，1是)
        UserMain u = SsoUtils.getUserMain();
        UserMain ua = new UserMain();
        ua.setId(u.getId());
        userMainService.updateByWhereSelective(userMain, new Wrapper(ua));
        cache.del(CacheConstant.SMS_MOBILE + R.get("mobile"));
        cache.del(CacheConstant.SMS_CODE + u.getUId());
        cache.del(CacheConstant.SECURITY_TYPE + u.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("修改成功！"));
        return "redirect:" + ssoPath + "/accountSecurity/ssoStatus.htm?successStatus=2";
    }

    /**
     * 进入修改支付密码页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "changePayPassword1")
    public String changePayPassword1(Model model) {
        UserMain userMain = SsoUtils.getUserMain();
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        model.addAttribute("siteRegister", siteRegister);
        model.addAttribute("userMain", userMain);
        model.addAttribute("highlight", "changePayPassword");//sso菜单高亮
        return "sso/user/changePayPassword";
    }

    /**
     * 保存支付密码
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "changePayPassword2")
    public String changePayPassword2(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate5(model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return changePayPassword1(model);//回显错误提示
        }
        String paymentPassword = R.get("paymentPassword");
        UserMain u = SsoUtils.getUserMain();
        UserMember userMember = new UserMember();
        userMember.setUId(u.getUId());
        userMember.setPaymentPassword(PasswordUtils.entryptPassword(paymentPassword, u.getSalt()));
        userMemberService.updateByIdSelective(userMember);
        cache.del(CacheConstant.SECURITY_TYPE + u.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("修改成功！"));
        return "redirect:" + ssoPath + "/accountSecurity/ssoStatus.htm?successStatus=3";
    }

    /**
     * 表单验证(安全验证页)
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate1(Model model) {
        UserMain userMain = SsoUtils.getUserMain();
        Long uId = userMain.getUId();
        List<String> errorList = new ArrayList<String>();
        String validatekey = R.get("validatekey"); //1为邮箱 2为短信
        if (StringUtils.isBlank(R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("图片验证码不能为空"));
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("图片验证码错误"));
        }
        if ("1".equals(validatekey)) {
            String email = userMain.getEmail();
            if (cache.get(CacheConstant.EMAIL_ADDR + email) == null) {
                errorList.add(FYUtils.fyParams("邮箱无效"));
            }
            if (cache.get(CacheConstant.EMAIL_ADDR + email) != null && !email.equals(cache.get(CacheConstant.EMAIL_ADDR + email).toString())) {
                errorList.add(FYUtils.fyParams("邮箱无效"));
            }
            if (cache.get(CacheConstant.EMAIL_CODE + uId) == null) {
                errorList.add(FYUtils.fyParams("邮箱验证码无效"));
            }
            if (StringUtils.isNotBlank(R.get("verification")) && !(R.get("verification")).equals(cache.get(CacheConstant.EMAIL_CODE + uId))) {
                errorList.add(FYUtils.fyParams("动态验证码无效"));
            }
        }
        if ("2".equals(validatekey)) {
            String mobile = userMain.getMobile();
            if (cache.get(CacheConstant.SMS_MOBILE + mobile) == null) {
                errorList.add(FYUtils.fyParams("手机号无效"));
            }
            if (cache.get(CacheConstant.SMS_MOBILE + mobile) != null && !mobile.equals(cache.get(CacheConstant.SMS_MOBILE + mobile).toString())) {
                errorList.add(FYUtils.fyParams("手机号无效"));
            }
            if (cache.get(CacheConstant.SMS_CODE + uId) == null) {
                errorList.add(FYUtils.fyParams("短信验证码无效"));
            }
            if (StringUtils.isNotBlank(R.get("verification")) && !(R.get("verification")).equals(cache.get(CacheConstant.SMS_CODE + uId))) {
                errorList.add(FYUtils.fyParams("验证码无效"));
            }
        }
        return errorList;
    }


    /**
     * 表单验证(修改密码)
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate2(Model model) {
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        UserMain userMain = SsoUtils.getUserMain();
        List<String> errorList = new ArrayList<String>();
        String oldPassword = R.get("oldPassword");
        String password = R.get("password");
        String repassword = R.get("repassword");
        if (StringUtils.isBlank(oldPassword)) {
            errorList.add(FYUtils.fyParams("原密码不能为空"));
        }
        if (StringUtils.isNotBlank(oldPassword) && !PasswordUtils.validatePassword(oldPassword, userMain.getPassword(), userMain.getSalt())) {
            errorList.add(FYUtils.fyParams("原密码不正确"));
        }
        if (StringUtils.isBlank(password)) {
            errorList.add(FYUtils.fyParams("密码不能为空"));
        }
        if (siteRegister != null) {
            if (StringUtils.isNotBlank(password) && password.length() > siteRegister.getPwdMax()) {
                errorList.add(FYUtils.fyParams("密码不能超过") + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
            }
            if (StringUtils.isNotBlank(password) && password.length() < siteRegister.getPwdMin()) {
                errorList.add(FYUtils.fyParams("密码不能少于") + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
            }
            if (StringUtils.isNotBlank(repassword) && repassword.length() > siteRegister.getPwdMax()) {
                errorList.add(FYUtils.fyParams("密码不能超过") + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
            }
            if (StringUtils.isNotBlank(repassword) && repassword.length() < siteRegister.getPwdMin()) {
                errorList.add(FYUtils.fyParams("密码不能少于") + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
            }
        }
        if (!password.equals(repassword)) {
            errorList.add(FYUtils.fyParams("两次密码必须一致"));
        }
        return errorList;
    }

    /**
     * 表单验证(绑定邮箱)
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate3(Model model) {
        List<String> errorList = new ArrayList<String>();
        UserMain userMain = SsoUtils.getUserMain();
        Long uId = userMain.getUId();
        if (StringUtils.isBlank(R.get("email"))) {
            errorList.add(FYUtils.fyParams("邮箱不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("email")) && !RegexUtils.checkEmail(R.get("email"))) {
            errorList.add(FYUtils.fyParams("邮箱格式不正确"));
        }
        if (StringUtils.isNotBlank(R.get("email")) && !SsoUtils.checkEmail(R.get("email"))) {
            errorList.add(FYUtils.fyParams("邮箱已被占用"));
        }
        if (StringUtils.isBlank(R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("验证码不能为空"));
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("验证码错误"));
        }
        if (StringUtils.isBlank(R.get("emailVerification"))) {
            errorList.add(FYUtils.fyParams("邮箱验证码不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("email")) && cache.get(CacheConstant.EMAIL_ADDR + R.get("email").toLowerCase()) == null) {
            errorList.add(FYUtils.fyParams("邮箱无效"));
        }else if (StringUtils.isNotBlank(R.get("email")) && !(R.get("email").toLowerCase()).equals(cache.get(CacheConstant.EMAIL_ADDR + R.get("email").toLowerCase()))) {
            errorList.add(FYUtils.fyParams("邮箱无效"));
        }
        if (cache.get(CacheConstant.EMAIL_CODE + uId) == null) {
            errorList.add(FYUtils.fyParams("邮箱验证码无效"));
        }else if (StringUtils.isNotBlank(R.get("emailVerification")) && !(R.get("emailVerification")).equals(cache.get(CacheConstant.EMAIL_CODE + uId))) {
            errorList.add(FYUtils.fyParams("邮箱验证码无效"));
        }
        return errorList;
    }

    /**
     * 表单验证(绑定手机)
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate4(Model model) {
        List<String> errorList = new ArrayList<String>();
        UserMain userMain = SsoUtils.getUserMain();
        Long uId = userMain.getUId();
        if (StringUtils.isBlank(R.get("mobile"))) {
            errorList.add(FYUtils.fyParams("手机不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && !RegexUtils.checkMobile(R.get("mobile"))) {
            errorList.add(FYUtils.fyParams("手机格式不正确"));
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && !SsoUtils.checkMobile(R.get("mobile"))) {
            errorList.add(FYUtils.fyParams("手机已被占用"));
        }
        if (StringUtils.isBlank(R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("验证码不能为空"));
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("验证码错误"));
        }
        if (StringUtils.isBlank(R.get("smsVerification"))) {
            errorList.add(FYUtils.fyParams("手机验证码不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && cache.get(CacheConstant.SMS_MOBILE + R.get("mobile")) == null) {
            errorList.add(FYUtils.fyParams("手机号无效"));
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && !(R.get("mobile")).equals(cache.get(CacheConstant.SMS_MOBILE + R.get("mobile")))) {
            errorList.add(FYUtils.fyParams("手机号无效"));
        }
        if (cache.get(CacheConstant.SMS_CODE + uId) == null) {
            errorList.add(FYUtils.fyParams("验证码无效"));
        }
        if (StringUtils.isNotBlank(R.get("smsVerification")) && !(R.get("smsVerification")).equals(cache.get(CacheConstant.SMS_CODE + uId))) {
            errorList.add(FYUtils.fyParams("验证码无效"));
        }
        return errorList;
    }

    /**
     * 表单验证(修改支付密码)
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate5(Model model) {
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        UserMain userMain = SsoUtils.getUserMain();
        List<String> errorList = new ArrayList<String>();
        String oldPaymentPassword = R.get("oldPaymentPassword");
        String paymentPassword = R.get("paymentPassword");
        String rePaymentPassword = R.get("rePaymentPassword");
        String cp = R.get("cp");
        if ("1".equals(cp)) {
            if (StringUtils.isBlank(oldPaymentPassword)) {
                errorList.add(FYUtils.fyParams("原支付密码不能为空"));
            }
            if (StringUtils.isNotBlank(oldPaymentPassword) && !PasswordUtils.validatePassword(oldPaymentPassword, userMain.getUserMember().getPaymentPassword(), userMain.getSalt())) {
                errorList.add(FYUtils.fyParams("原支付密码不正确"));
            }
            if (StringUtils.isNotBlank(oldPaymentPassword) && PasswordUtils.validatePassword(oldPaymentPassword, userMain.getPassword(), userMain.getSalt())) {
                errorList.add(FYUtils.fyParams("支付密码和登录密码不能一致"));
            }
        }
        if (StringUtils.isBlank(paymentPassword)) {
            errorList.add(FYUtils.fyParams("支付密码不能为空"));
        }
        if (siteRegister != null) {
            if (StringUtils.isNotBlank(paymentPassword) && paymentPassword.length() > siteRegister.getPwdMax()) {
                errorList.add(FYUtils.fyParams("支付密码不能超过") + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
            }
            if (StringUtils.isNotBlank(paymentPassword) && paymentPassword.length() < siteRegister.getPwdMin()) {
                errorList.add(FYUtils.fyParams("支付密码不能少于") + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
            }
            if (StringUtils.isNotBlank(rePaymentPassword) && rePaymentPassword.length() > siteRegister.getPwdMax()) {
                errorList.add(FYUtils.fyParams("支付密码不能超过") + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
            }
            if (StringUtils.isNotBlank(rePaymentPassword) && rePaymentPassword.length() < siteRegister.getPwdMin()) {
                errorList.add(FYUtils.fyParams("支付密码不能少于") + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
            }
        }
        if (!paymentPassword.equals(rePaymentPassword)) {
            errorList.add(FYUtils.fyParams("两次密码必须一致"));
        }
        return errorList;
    }

}
