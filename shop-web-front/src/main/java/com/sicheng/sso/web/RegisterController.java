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

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.servlet.ValidateCodeServlet;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.front.service.ProductCarService;
import com.sicheng.seller.site.service.SiteRegisterService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.service.UserMerchantInfoService;
import com.sicheng.sso.service.UserRepairShopService;
import com.sicheng.sso.shiro.SsoUsernamePasswordToken;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: 用户注册</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年4月25日 下午6:20:32
 */
@Controller
@RequestMapping(value = "${ssoPath}/register")
public class RegisterController extends BaseController {

    private final String TOP_STATUS = "topStatus"; //登录top.jsp中的名字控制
    private final String TOP_STATUS_VALUE = "2";    //1.登录 2.注册 3.忘记密码

    @Autowired
    private SiteRegisterService siteRegisterService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private ProductCarService productCarService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private UserRepairShopService userRepairShopService;
    @Autowired
    private UserMerchantInfoService userMerchantInfoService;
    @Autowired
    private SiteSendMessagsService siteSendMessagsService;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("ctx", R.getCtx());

        model.addAttribute("ctxf", R.getCtx() + Global.getFrontPath());
        model.addAttribute("ctxa", R.getCtx() + Global.getAdminPath());
        model.addAttribute("ctxs", R.getCtx() + Global.getSellerPath());
        model.addAttribute("ctxm", R.getCtx() + Global.getMemberPath());
        model.addAttribute("ctxsso", R.getCtx() + Global.getSsoPath());
        model.addAttribute("ctxw", R.getCtx() + Global.getWapPath());
        model.addAttribute("ctxu", "/upload");

        model.addAttribute("ctxStatic", "/static/static");
        model.addAttribute("ctxfs", "/upload" + Global.getConfig("filestorage.dir"));
    }

    /**
     * 进入注册页面
     *
     * @return
     */
    @RequestMapping(value = "save1")
    public String register(UserMain userMain, Model model, RedirectAttributes redirectAttributes) {
        //获取用户名和密码的规则
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        if (siteRegister != null) {
            if ("0".equals(siteRegister.getIsRegister())) {//是否开放买家注册
                addMessage(redirectAttributes, FYUtils.fyParams("未开放注册！"));
                return "redirect:" + ssoPath + "/login/login.htm";
            }
            String agreement = siteRegister.getAgreement();
            if (StringUtils.isNotBlank(agreement)) {
                String html_unsafe = StringEscapeUtils.unescapeHtml4(agreement);//转回来（还原）
                siteRegister.setAgreement(html_unsafe);
            }
        }
        model.addAttribute(TOP_STATUS, TOP_STATUS_VALUE);
        model.addAttribute("siteRegister", siteRegister);
        model.addAttribute("userMain", userMain);
        return "/register";
    }

    /**
     * 注册保存页面
     *
     * @param userMain
     * @param model
     * @return
     */
    @RequestMapping(value = "save2")
    public String saveRegister(UserMain userMain, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //获取用户名和密码的规则
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        if ("0".equals(siteRegister.getIsRegister())) {//是否开放买家注册
            addMessage(redirectAttributes, FYUtils.fyParams("未开放注册"));
            return "redirect:" + ssoPath + "/login/login.htm";
        }
        String password = R.get("password");
        String ssoReg = R.get("ssoReg");
        String memberType = R.get("memberType");
        //使用账号注册
        SsoUsernamePasswordToken token = new SsoUsernamePasswordToken();
        if ("1".equals(ssoReg)) {
            //表单验证
            List<String> errorList = validate1(request, model);
            if (errorList.size() > 0) {
                errorList.add(0, FYUtils.fyParams("数据验证失败："));
                model.addAttribute("ssoReg", ssoReg);
                addMessage(model, errorList.toArray(new String[]{}));
                return register(userMain, model, redirectAttributes);//回显错误提示
            }
            //用户名
            if (StringUtils.isNotBlank(userMain.getLoginName())) {
                userMain.setLoginName(userMain.getLoginName());
            }
            //邮箱转小写
            if (StringUtils.isNotBlank(userMain.getEmail())) {
                userMain.setEmail(userMain.getEmail().toLowerCase());
            }
            userMainService.save(userMain, "1");
            token.setUsername(userMain.getLoginName());
            token.setPassword(password.toCharArray());
            Subject subject = SsoUtils.getSubject();
            subject.login(token);
            addMessage(redirectAttributes, FYUtils.fyParams("注册成功！"));
        }
        //使用手机注册
        if ("2".equals(ssoReg)) {
            //表单验证
            List<String> errorList = validate2(request, model);
            if (errorList.size() > 0) {
                errorList.add(0, "数据验证失败：");
                model.addAttribute("ssoReg", ssoReg);
                addMessage(model, errorList.toArray(new String[]{}));
                return register(userMain, model, redirectAttributes);//回显错误提示
            }
            userMainService.save(userMain, "2");
            String sessionId = request.getSession().getId();
            token.setUsername(userMain.getLoginName());
            token.setPassword(R.get("smsVerification").toCharArray());
            Subject subject = SsoUtils.getSubject();
            subject.login(token);
            addMessage(redirectAttributes, "注册成功！");
            cache.del(CacheConstant.SMS_MOBILE + sessionId);
            cache.del(CacheConstant.SMS_CODE + sessionId);
        }
        //会员注册成功后给后台管理员发送短信通知
        sendMessage(userMain);
        if("1".equals(memberType)){
            //买家
            return "redirect:" + memberPath + "/index.htm";
        }
        if("2".equals(memberType)){
            //商家入驻
            return "redirect:" + sellerPath + "/store/storeEnterAuth/auth4.htm";
        }
        return "redirect:" + memberPath + "/index.htm";
    }

    /**
     *  会员注册成功后给后台管理员发送短信通知
     *  @param userMain 注册会员的信息
     */
    private void sendMessage(UserMain userMain) {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userMain.getLoginName());
        siteSendMessagsService.sendMessage(map, SiteSendMessagsService.USER_REGISTER);
    }

    /**
     * shop账号注册ajax验证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "ajaxAccountRegisterVerification")
    @ResponseBody
    public Object ajaxAccountRegisterVerification(HttpServletRequest request) {
        String loginName = R.get("loginName");
        String email = R.get("email");
        Map<String, String> map = new HashMap<String, String>();
        if (!SsoUtils.checkLoginName(loginName)) {
            map.put("status1", "1");
            map.put("error1", FYUtils.fyParams("账号已存在"));
        }
        if (!SsoUtils.checkEmail(email)) {
            map.put("status2", "2");
            map.put("error2", FYUtils.fyParams("邮箱已存在"));
        }
        return map;
    }

    /**
     * shop手机注册ajax验证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "ajaxMobileRegisterVerification")
    @ResponseBody
    public Object ajaxMobileRegisterVerification(HttpServletRequest request) {
        String mobile = R.get("mobile");
        Map<String, String> map = new HashMap<String, String>();
        if (!SsoUtils.checkMobile(mobile)) {
            map.put("status1", "1");
            map.put("error1", FYUtils.fyParams("手机已存在"));
        }
        return map;
    }

    /**
     * 汽车注册验证用户名称是否重复
     *
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "validateLoginName")
    public String validateLoginName(String loginName) {
        if (StringUtils.isBlank(loginName)) {
            return "false";
        }
        loginName = loginName.toLowerCase();
        UserMain userMain = new UserMain();
        userMain.setLoginName(loginName);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (userMains.isEmpty()) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 汽车注册验证手机号是否重复
     *
     * @param mobile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "validateMobile")
    public String validateMobile(String mobile) {
        UserMain userMain = new UserMain();
        userMain.setMobile(mobile);
        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
        if (userMains.isEmpty()) {
            return "true";
        } else {
            return "false";
        }
    }


    /**
     * 表单验证(账号注册)
     *
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate1(HttpServletRequest request, Model model) {
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        List<String> errorList = new ArrayList<String>();
        String sessionId = request.getSession().getId();
        if (StringUtils.isBlank(R.get("loginName"))) {
            errorList.add(FYUtils.fyParams("用户名不能为空"));
            return errorList;
        }
        if (!SsoUtils.checkLoginName(R.get("loginName"))) {
            errorList.add(FYUtils.fyParams("账号已存在"));
        }
        if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > 64) {
            errorList.add(FYUtils.fyParams("用户名不能超")+"64"+FYUtils.fyParams("字符"));
        }
        if (siteRegister != null) {
            if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > siteRegister.getUsernameMax()) {
                errorList.add(FYUtils.fyParams("用户名不能超过") + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
            }
            if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() < siteRegister.getUsernameMin()) {
                errorList.add(FYUtils.fyParams("用户名不能少于") + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
            }
            if (StringUtils.isNotBlank(R.get("loginName")) && StringUtils.isNotBlank(siteRegister.getDisableUsername())) {
                String[] disableName = siteRegister.getDisableUsername().split(",");
                for (int i = 0; i < disableName.length; i++) {
                    if (R.get("loginName").equals(disableName)) {
                        errorList.add(FYUtils.fyParams("用户名不能是") + disableName);
                    }
                }
            }
        }
        if (StringUtils.isBlank(R.get("email"))) {
            errorList.add(FYUtils.fyParams("邮箱不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("email")) && !RegexUtils.checkEmail(R.get("email"))) {
            errorList.add(FYUtils.fyParams("邮箱格式不正确"));
        }
        if (StringUtils.isNotBlank(R.get("email")) && R.get("email").length() > 64) {
            errorList.add(FYUtils.fyParams("邮箱不能超过")+"64"+FYUtils.fyParams("字符"));
        }
        if (!SsoUtils.checkEmail(R.get("email"))) {
            errorList.add(FYUtils.fyParams("邮箱已存在"));
        }
        if (cache.get(CacheConstant.EMAIL_ADDR + sessionId) == null) {
            errorList.add(FYUtils.fyParams("请填写正确邮箱"));
        }
        if (StringUtils.isNotBlank(R.get("email")) && !(R.get("email")).equals(cache.get(CacheConstant.EMAIL_ADDR + sessionId))) {
            errorList.add(FYUtils.fyParams("邮箱无效"));
        }
        if (StringUtils.isBlank(R.get("emailCodeVerification"))) {
            errorList.add(FYUtils.fyParams("邮箱验证码不能为空"));
        }
        if (cache.get(CacheConstant.EMAIL_CODE + sessionId) == null) {
            errorList.add(FYUtils.fyParams("请填写正确邮箱验证码"));
        }
        if (StringUtils.isNotBlank(R.get("emailCodeVerification")) && !(R.get("emailCodeVerification")).equals(cache.get(CacheConstant.EMAIL_CODE + sessionId))) {
            errorList.add(FYUtils.fyParams("验证码无效"));
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
     * 表单验证(手机注册)
     *
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate2(HttpServletRequest request, Model model) {
        List<String> errorList = new ArrayList<String>();
        String sessionId = request.getSession().getId();
        if (StringUtils.isBlank(R.get("mobile"))) {
            errorList.add("手机号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && R.get("mobile").length() > 11) {
            errorList.add("手机号不能超过11字符");
        }
        if (!SsoUtils.checkMobile(R.get("mobile"))) {
            errorList.add("手机号已存在");
        }
        if (!SsoUtils.checkLoginName(R.get("mobile"))) {
            errorList.add("手机号已存在");
        }
        if (StringUtils.isBlank(R.get("smsVerification"))) {
            errorList.add("短信验证码不能为空");
        }
        if (StringUtils.isBlank(R.get("validateCode"))) {
            errorList.add("验证码不能为空");
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
            errorList.add("验证码错误");
        }
        if (StringUtils.isBlank(R.get("smsVerification"))) {
            errorList.add("短信验证码不能为空");
        }
        if (cache.get(CacheConstant.SMS_MOBILE + sessionId) == null) {
            errorList.add("请填写正确短信手机号");
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && !(R.get("mobile")).equals(cache.get(CacheConstant.SMS_MOBILE + sessionId))) {
            errorList.add("手机号无效");
        }
        if (cache.get(CacheConstant.SMS_CODE + sessionId) == null) {
            errorList.add("请填写正确短信验证码");
        }
        if (StringUtils.isNotBlank(R.get("smsVerification")) && !(R.get("smsVerification")).equals(cache.get(CacheConstant.SMS_CODE + sessionId))) {
            errorList.add("验证码无效");
        }
        return errorList;
    }


    /**
     * 汽车注册(个人注册)
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
//    private List<String> validate3(HttpServletRequest request, Model model) {
//        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
//        List<String> errorList = new ArrayList<String>();
//        String sessionId = request.getSession().getId();
//        if (StringUtils.isBlank(R.get("loginName"))) {
//            errorList.add("用户名不能为空");
//            return errorList;
//        }
//        if (!SsoUtils.checkLoginName(R.get("loginName"))) {
//            errorList.add("账号已存在");
//        }
//        if (siteRegister != null) {
//            if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > siteRegister.getUsernameMax()) {
//                errorList.add("用户名不能超过" + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() < siteRegister.getUsernameMin()) {
//                errorList.add("用户名不能少于" + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(R.get("loginName")) && StringUtils.isNotBlank(siteRegister.getDisableUsername())) {
//                String[] disableName = siteRegister.getDisableUsername().split(",");
//                for (int i = 0; i < disableName.length; i++) {
//                    if (R.get("loginName").equals(disableName)) {
//                        errorList.add("用户名不能是" + disableName);
//                    }
//                }
//            }
//        }
//        String password = R.get("password");
//        String repassword = R.get("repassword");
//        if (StringUtils.isBlank(password)) {
//            errorList.add("密码不能为空");
//        }
//        if (siteRegister != null) {
//            if (StringUtils.isNotBlank(password) && password.length() > siteRegister.getPwdMax()) {
//                errorList.add("密码不能超过" + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(password) && password.length() < siteRegister.getPwdMin()) {
//                errorList.add("密码不能少于" + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(repassword) && repassword.length() > siteRegister.getPwdMax()) {
//                errorList.add("密码不能超过" + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(repassword) && repassword.length() < siteRegister.getPwdMin()) {
//                errorList.add("密码不能少于" + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
//            }
//        }
//        if (!password.equals(repassword)) {
//            errorList.add("两次密码必须一致");
//        }
//        if (StringUtils.isBlank(R.get("mobile"))) {
//            errorList.add("手机号不能为空");
//        }
//        if (StringUtils.isNotBlank(R.get("mobile")) && R.get("mobile").length() > 64) {
//            errorList.add("手机号不能超过64字符");
//        }
//        if (!SsoUtils.checkMobile(R.get("mobile"))) {
//            errorList.add("手机号已存在");
//        }
//        if (StringUtils.isBlank(R.get("validateCode"))) {
//            errorList.add("验证码不能为空");
//        }
//        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
//            errorList.add("验证码错误");
//        }
//        if (StringUtils.isBlank(R.get("smsVerification"))) {
//            errorList.add("短信验证码不能为空");
//        }
//        if (StringUtils.isBlank(R.get("smsVerification"))) {
//            errorList.add("短信验证码不能为空");
//        }
//        if (cache.get(CacheConstant.SMS_MOBILE + sessionId) == null) {
//            errorList.add("手机号无效");
//        }
//        if (StringUtils.isNotBlank(R.get("mobile")) && !(R.get("mobile")).equals(cache.get(CacheConstant.SMS_MOBILE + sessionId))) {
//            errorList.add("请填写正确手机号");
//        }
//        if (cache.get(CacheConstant.SMS_CODE + sessionId) == null) {
//            errorList.add("验证码无效");
//        }
//        if (StringUtils.isNotBlank(R.get("smsVerification")) && !(R.get("smsVerification")).equals(cache.get(CacheConstant.SMS_CODE + sessionId))) {
//            errorList.add("请填写正确验证码");
//        }
//        return errorList;
//    }

    /**
     *  汽车--商家注册信息验证
     *  @param request
     *  @param model
     *  @return
     */
//    private List<String> merchantValidate(HttpServletRequest request, Model model) {
//        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
//        List<String> errorList = new ArrayList<String>();
//        String sessionId = request.getSession().getId();
//        if (StringUtils.isBlank(R.get("loginName"))) {
//            errorList.add("用户名不能为空");
//            return errorList;
//        }
//        if (!SsoUtils.checkLoginName(R.get("loginName"))) {
//            errorList.add("账号已存在");
//        }
//        if (siteRegister != null) {
//            if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > siteRegister.getUsernameMax()) {
//                errorList.add("用户名不能超过" + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() < siteRegister.getUsernameMin()) {
//                errorList.add("用户名不能少于" + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(R.get("loginName")) && StringUtils.isNotBlank(siteRegister.getDisableUsername())) {
//                String[] disableName = siteRegister.getDisableUsername().split(",");
//                for (int i = 0; i < disableName.length; i++) {
//                    if (R.get("loginName").equals(disableName)) {
//                        errorList.add("用户名不能是" + disableName);
//                    }
//                }
//            }
//        }
//        String password = R.get("password");
//        String repassword = R.get("repassword");
//        if (StringUtils.isBlank(password)) {
//            errorList.add("密码不能为空");
//        }
//        if (siteRegister != null) {
//            if (StringUtils.isNotBlank(password) && password.length() > siteRegister.getPwdMax()) {
//                errorList.add("密码不能超过" + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(password) && password.length() < siteRegister.getPwdMin()) {
//                errorList.add("密码不能少于" + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(repassword) && repassword.length() > siteRegister.getPwdMax()) {
//                errorList.add("密码不能超过" + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(repassword) && repassword.length() < siteRegister.getPwdMin()) {
//                errorList.add("密码不能少于" + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
//            }
//        }
//        if (!password.equals(repassword)) {
//            errorList.add("两次密码必须一致");
//        }
//        if (StringUtils.isBlank(R.get("mobile"))) {
//            errorList.add("手机号不能为空");
//        }
//        if (StringUtils.isNotBlank(R.get("mobile")) && R.get("mobile").length() > 64) {
//            errorList.add("手机号不能超过64字符");
//        }
//        if (!SsoUtils.checkMobile(R.get("mobile"))) {
//            errorList.add("手机号已存在");
//        }
//        if (StringUtils.isBlank(R.get("validateCode"))) {
//            errorList.add("验证码不能为空");
//        }
//        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
//            errorList.add("验证码错误");
//        }
//        if (StringUtils.isBlank(R.get("smsVerification"))) {
//            errorList.add("短信验证码不能为空");
//        }
//        if (StringUtils.isBlank(R.get("smsVerification"))) {
//            errorList.add("短信验证码不能为空");
//        }
//        if (cache.get(CacheConstant.SMS_MOBILE + sessionId) == null) {
//            errorList.add("手机号无效");
//        }
//        if (StringUtils.isNotBlank(R.get("mobile")) && !(R.get("mobile")).equals(cache.get(CacheConstant.SMS_MOBILE + sessionId))) {
//            errorList.add("请填写正确手机号");
//        }
//        if (cache.get(CacheConstant.SMS_CODE + sessionId) == null) {
//            errorList.add("验证码无效");
//        }
//        if (StringUtils.isNotBlank(R.get("smsVerification")) && !(R.get("smsVerification")).equals(cache.get(CacheConstant.SMS_CODE + sessionId))) {
//            errorList.add("请填写正确验证码");
//        }
//        if (StringUtils.isBlank(R.get("type"))) {
//            errorList.add("请选择企业类型");
//        }
//        if (StringUtils.isBlank(R.get("industry"))) {
//            errorList.add("请选择行业属性");
//        }
//        if (StringUtils.isBlank(R.get("name"))) {
//            errorList.add("请输入公司名称");
//        }
//        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
//            errorList.add("公司名称最大长度不能超过64字符");
//        }
//        if (StringUtils.isBlank(R.get("contacts"))) {
//            errorList.add("请输入联系人");
//        }
//        if (StringUtils.isNotBlank(R.get("contacts")) && R.get("contacts").length() > 32) {
//            errorList.add("联系人最大长度不能超过32字符");
//        }
//        return errorList;
//    }

    /**
     *  汽车--服务门店入驻信息验证 
     *  @param request
     *  @param model
     *  @return
     */
//    private List<String> repairShopValidate(HttpServletRequest request, Model model) {
//        List<String> errorList = new ArrayList<String>();
//        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
//        String sessionId = request.getSession().getId();
//        if (StringUtils.isBlank(R.get("loginName"))) {
//            errorList.add("用户名不能为空");
//            return errorList;
//        }
//        if (!SsoUtils.checkLoginName(R.get("loginName"))) {
//            errorList.add("账号已存在");
//        }
//        if (siteRegister != null) {
//            if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > siteRegister.getUsernameMax()) {
//                errorList.add("用户名不能超过" + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() < siteRegister.getUsernameMin()) {
//                errorList.add("用户名不能少于" + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(R.get("loginName")) && StringUtils.isNotBlank(siteRegister.getDisableUsername())) {
//                String[] disableName = siteRegister.getDisableUsername().split(",");
//                for (int i = 0; i < disableName.length; i++) {
//                    if (R.get("loginName").equals(disableName)) {
//                        errorList.add("用户名不能是" + disableName);
//                    }
//                }
//            }
//        }
//        String password = R.get("password");
//        String repassword = R.get("repassword");
//        if (StringUtils.isBlank(password)) {
//            errorList.add("密码不能为空");
//        }
//        if (siteRegister != null) {
//            if (StringUtils.isNotBlank(password) && password.length() > siteRegister.getPwdMax()) {
//                errorList.add("密码不能超过" + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(password) && password.length() < siteRegister.getPwdMin()) {
//                errorList.add("密码不能少于" + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(repassword) && repassword.length() > siteRegister.getPwdMax()) {
//                errorList.add("密码不能超过" + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
//            }
//            if (StringUtils.isNotBlank(repassword) && repassword.length() < siteRegister.getPwdMin()) {
//                errorList.add("密码不能少于" + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
//            }
//        }
//        if (!password.equals(repassword)) {
//            errorList.add("两次密码必须一致");
//        }
//        if (StringUtils.isBlank(R.get("mobile"))) {
//            errorList.add("手机号不能为空");
//        }
//        if (StringUtils.isNotBlank(R.get("mobile")) && R.get("mobile").length() > 64) {
//            errorList.add("手机号不能超过64字符");
//        }
//        if (!SsoUtils.checkMobile(R.get("mobile"))) {
//            errorList.add("手机号已存在");
//        }
//        if (StringUtils.isBlank(R.get("validateCode"))) {
//            errorList.add("验证码不能为空");
//        }
//        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
//            errorList.add("验证码错误");
//        }
//        if (StringUtils.isBlank(R.get("smsVerification"))) {
//            errorList.add("短信验证码不能为空");
//        }
//        if (StringUtils.isBlank(R.get("smsVerification"))) {
//            errorList.add("短信验证码不能为空");
//        }
//        if (cache.get(CacheConstant.SMS_MOBILE + sessionId) == null) {
//            errorList.add("手机号无效");
//        }
//        if (StringUtils.isNotBlank(R.get("mobile")) && !(R.get("mobile")).equals(cache.get(CacheConstant.SMS_MOBILE + sessionId))) {
//            errorList.add("请填写正确手机号");
//        }
//        if (cache.get(CacheConstant.SMS_CODE + sessionId) == null) {
//            errorList.add("验证码无效");
//        }
//        if (StringUtils.isNotBlank(R.get("smsVerification")) && !(R.get("smsVerification")).equals(cache.get(CacheConstant.SMS_CODE + sessionId))) {
//            errorList.add("请填写正确验证码");
//        }
//        if (StringUtils.isBlank(R.get("type"))) {
//            errorList.add("请选择企业类型");
//        }
//        if (StringUtils.isBlank(R.get("type"))) {
//            errorList.add("请选择门店类型");
//        }
//        if (StringUtils.isBlank(R.get("name"))) {
//            errorList.add("请输入门店名称");
//        }
//        if (StringUtils.isBlank(R.get("provinceId"))) {
//            errorList.add("请选择省");
//        }
//        if (StringUtils.isBlank(R.get("cityId"))) {
//            errorList.add("请选择市");
//        }
//        if (StringUtils.isBlank(R.get("districtId"))) {
//            errorList.add("请选择县");
//        }
//        if (StringUtils.isBlank(R.get("detailedAddress"))) {
//            errorList.add("请输入门店地址");
//        }
//        if (StringUtils.isBlank(R.get("burns"))) {
//            errorList.add("请输入门店面积");
//        }
//        if (StringUtils.isBlank(R.get("bossName"))) {
//            errorList.add("请输入老板姓名");
//        }
//        if (StringUtils.isBlank(R.get("burns"))) {
//            errorList.add("请输入老板手机号");
//        }
//        if (StringUtils.isBlank(R.get("peopleCount"))) {
//            errorList.add("请选择门店人数");
//        }
//        if (StringUtils.isBlank(R.get("hotline"))) {
//            errorList.add("请输入服务热线");
//        }
//        if (StringUtils.isBlank(R.get("shopkeeperName"))) {
//            errorList.add("请输入店长姓名");
//        }
//        if (StringUtils.isBlank(R.get("shopkeeperMobile"))) {
//            errorList.add("请输入店长手机号");
//        }
//        if (StringUtils.isBlank(R.get("contactsName"))) {
//            errorList.add("请输入联系人姓名");
//        }
//        if (StringUtils.isBlank(R.get("department"))) {
//            errorList.add("请选择所在部门");
//        }
//        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
//            errorList.add("门店名称最大长度不能超过64字符");
//        }
//        if (StringUtils.isNotBlank(R.get("brands")) && R.get("brands").length() > 64) {
//            errorList.add("经营品牌最大长度不能超过64字符");
//        }
//        if (StringUtils.isNotBlank(R.get("introduce")) && R.get("introduce").length() > 255) {
//            errorList.add("门店介绍最大长度不能超过255字符");
//        }
//        if (StringUtils.isNotBlank(R.get("detailedAddress")) && R.get("detailedAddress").length() > 255) {
//            errorList.add("门店地址最大长度不能超过255字符");
//        }
//        if (StringUtils.isNotBlank(R.get("burns")) && R.get("burns").length() > 64) {
//            errorList.add("门店面积最大长度不能超过64字符");
//        }
//        if (StringUtils.isNotBlank(R.get("bossName")) && R.get("bossName").length() > 64) {
//            errorList.add("老板姓名最大长度不能超过64字符");
//        }
//        if (StringUtils.isNotBlank(R.get("bossMobile")) && R.get("bossMobile").length() > 64) {
//            errorList.add("老板电话最大长度不能超过64字符");
//        }
//        if (StringUtils.isNotBlank(R.get("peopleCount")) && R.get("peopleCount").length() > 64) {
//            errorList.add("门店人数最大长度不能超过64字符");
//        }
//        if (StringUtils.isNotBlank(R.get("hotline")) && R.get("hotline").length() > 64) {
//            errorList.add("服务热线最大长度不能超过64字符");
//        }
//        if (StringUtils.isNotBlank(R.get("shopkeeperName")) && R.get("shopkeeperName").length() > 64) {
//            errorList.add("店主姓名最大长度不能超过64字符");
//        }
//        if (StringUtils.isNotBlank(R.get("shopkeeperMobile")) && R.get("shopkeeperMobile").length() > 64) {
//            errorList.add("店主手机最大长度不能超过64字符");
//        }
//        if (StringUtils.isNotBlank(R.get("contactsName")) && R.get("contactsName").length() > 64) {
//            errorList.add("联系人姓名最大长度不能超过64字符");
//        }
//        return errorList;
//    }
}
