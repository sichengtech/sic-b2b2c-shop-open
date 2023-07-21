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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sicheng.common.config.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.servlet.ValidateCodeServlet;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.site.service.SiteRegisterService;
import com.sicheng.sso.service.UserMainService;

/**
 * <p>标题: 忘记密码</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年4月27日 下午4:54:37
 * @version 2017年4月27日 下午4:54:37
 */
@Controller
@RequestMapping(value = "${ssoPath}/forgetPassWord")
public class ForgetPassWordController extends BaseController {

    private static String TOP_STATUS = "topStatus"; //登录top.jsp中的名字控制
    private static String TOP_STATUS_VALUE = "3";    //1.登录 2.注册 3.忘记密码

    @Autowired
    private SiteRegisterService siteRegisterService;

    @Autowired
    private UserMainService userMainService;

    /**
     * 进入忘记密码页面
     *
     * @return
     */
    @RequestMapping(value = "save1")
    public String forgetPassWord(UserMain userMain, Model model) {
        //获取用户名和密码的规则
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        model.addAttribute("siteRegister", siteRegister);
        model.addAttribute("userMain", userMain);
        model.addAttribute(TOP_STATUS, TOP_STATUS_VALUE);
        //从fdp获取语言(是否是英文版商城：0否1是)
        String isEnglish = Global.getConfig("sys.isEnglish");
        model.addAttribute("isEnglish",isEnglish);
        return "sso/user/forgetPassWord";
    }

    /**
     * 保存密码
     *
     * @param userMain
     * @param model
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "save2")
    public String saveForget(UserMain userMain, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String ssoReg = R.get("ssoReg");
        Long uId = null;
        List<UserMain> userMains = null;
        if ("1".equals(ssoReg)) {
            //使用邮箱找回密码
            //表单验证
            List<String> errorList = validate1(model);
            if (errorList.size() > 0) {
                errorList.add(0, FYUtils.fyParams("数据验证失败："));
                addMessage(model, errorList.toArray(new String[]{}));
                model.addAttribute("ssoReg", ssoReg);
                return forgetPassWord(userMain, model);//回显错误提示
            }
            String email = R.get("email");
            UserMain u = new UserMain();
            u.setEmail(email);
            userMains = userMainService.selectByWhere(new Wrapper(u));
            uId = userMains.get(0).getUId();
        }
        if ("2".equals(ssoReg)) {
            //使用手机找回密码
            //表单验证
            List<String> errorList = validate2(model);
            if (errorList.size() > 0) {
                errorList.add(0, FYUtils.fyParams("数据验证失败："));
                addMessage(model, errorList.toArray(new String[]{}));
                model.addAttribute("ssoReg", ssoReg);
                return forgetPassWord(userMain, model);//回显错误提示
            }
            String mobile = R.get("mobile");
            UserMain u = new UserMain();
            u.setMobile(mobile);
            userMains = userMainService.selectByWhere(new Wrapper(u));
            uId = userMains.get(0).getUId();
        }
        UserMain usera = new UserMain();
        usera.setUId(uId);
        usera.setPassword(PasswordUtils.entryptPassword(userMain.getPassword(), userMains.get(0).getSalt()));//密码
        userMainService.updateByIdSelective(usera);
        cache.del(CacheConstant.EMAIL_ADDR + uId);
        cache.del(CacheConstant.EMAIL_CODE + uId);
        cache.del(CacheConstant.SMS_MOBILE + uId);
        cache.del(CacheConstant.SMS_CODE + uId);
        addMessage(redirectAttributes, FYUtils.fyParams("修改密码成功,请重新登录！"));
        return "redirect:" + ssoPath + "/logout.htm";
    }


    /**
     * 表单验证(邮箱找回密码)
     *
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate1(Model model) {
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("loginName"))) {
            errorList.add(FYUtils.fyParams("用户名不能为空"));
        }
        if (StringUtils.isBlank(R.get("email"))) {
            errorList.add(FYUtils.fyParams("邮箱不能为空"));
        }
        if (StringUtils.isBlank(R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("验证码不能为空"));
        }
        if (StringUtils.isBlank(R.get("emailVerification"))) {
            errorList.add(FYUtils.fyParams("邮件验证码不能为空"));
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("验证码错误"));
        }
        if (StringUtils.isNotBlank(R.get("email"))) {
            List<UserMain> userMains = new ArrayList<UserMain>();
            UserMain userMain = new UserMain();
            userMain.setEmail(R.get("email"));
            userMains = userMainService.selectByWhere(new Wrapper(userMain));
            if (userMains.isEmpty()) {
                errorList.add(FYUtils.fyParams("账号不存在"));
            } else {
                Long uId = userMains.get(0).getUId();
                if (cache.get(CacheConstant.EMAIL_ADDR + uId) == null) {
                    errorList.add(FYUtils.fyParams("邮箱无效"));
                }
                if (StringUtils.isBlank(R.get("email")) && !(uId).equals(cache.get(CacheConstant.EMAIL_ADDR + uId))) {
                    errorList.add(FYUtils.fyParams("邮箱无效"));
                }
                if (cache.get(CacheConstant.EMAIL_CODE + uId) == null) {
                    errorList.add(FYUtils.fyParams("验证码无效"));
                }
                if (StringUtils.isNotBlank(R.get("emailVerification")) && !(R.get("emailVerification")).equals(cache.get(CacheConstant.EMAIL_CODE + uId))) {
                    errorList.add(FYUtils.fyParams("验证码无效"));
                }
            }

        }
        String password = R.get("password");
        String repassword = R.get("repassword");
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
     * 表单验证(手机找回密码)
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate2(Model model) {
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("验证码不能为空"));
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
            errorList.add(FYUtils.fyParams("验证码错误"));
        }
        if (StringUtils.isBlank(R.get("smsVerification"))) {
            errorList.add(FYUtils.fyParams("短信验证码不能为空"));
        }
        if (StringUtils.isBlank(R.get("mobile"))) {
            errorList.add(FYUtils.fyParams("手机号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("mobile"))) {
            List<UserMain> userMains = new ArrayList<UserMain>();
            UserMain userMain = new UserMain();
            userMain.setMobile(R.get("mobile"));
            userMains = userMainService.selectByWhere(new Wrapper(userMain));
            if (userMains.isEmpty()) {
                errorList.add(FYUtils.fyParams("账号不存在"));
            } else {
                Long uId = userMains.get(0).getUId();
                if (cache.get(CacheConstant.SMS_MOBILE + uId) == null) {
                    errorList.add(FYUtils.fyParams("手机号无效"));
                }
                if (StringUtils.isBlank(R.get("mobile")) && !(uId).equals(cache.get(CacheConstant.SMS_MOBILE + uId))) {
                    errorList.add(FYUtils.fyParams("手机号无效"));
                }
                if (cache.get(CacheConstant.SMS_CODE + uId) == null) {
                    errorList.add(FYUtils.fyParams("验证码无效"));
                }
                if (StringUtils.isNotBlank(R.get("smsVerification")) && !(R.get("smsVerification")).equals(cache.get(CacheConstant.SMS_CODE + uId))) {
                    errorList.add(FYUtils.fyParams("验证码无效"));
                }
            }
        }
        String password = R.get("password");
        String repassword = R.get("repassword");
        if (StringUtils.isBlank(password) || StringUtils.isBlank(repassword)) {
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

}
