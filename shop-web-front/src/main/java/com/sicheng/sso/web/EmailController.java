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

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sys.entity.SysToken;
import com.sicheng.admin.sys.utils.TokenUtils;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.servlet.ValidateCodeServlet;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.site.service.SiteInfoService;
import com.sicheng.seller.site.service.SiteMessageTemplateService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.sys.service.SysTokenService;
import com.sicheng.sso.service.SendEmailService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 邮箱</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月5日 上午11:12:21
 */
@Controller
@RequestMapping(value = "${ssoPath}/email")
public class EmailController extends BaseController {

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private SysTokenService sysTokenService;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private SiteMessageTemplateService messageTemplateService;

    @Autowired
    private SiteInfoService siteInfoService;

    /**
     * 验证邮箱
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "verificationEmail")
    public String verificationEmail(Model model) {
        UserMain userMain = SsoUtils.getUserMain();
        boolean isOperation = userMain.isOperation();
        if (isOperation) {
            return "redirect:" + ssoPath + "/login/loginSuccess.htm";
        }
        model.addAttribute("userMain", userMain);
        return "sso/user/verificationEmail";
    }

    /**
     * 激活邮箱
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "activeMail")
    public String activeMail(RedirectAttributes redirectAttributes, Model model) {
        String status = "0";
        String token = R.get("token");
        String uId = R.get("uId");
        if (StringUtils.isNotBlank(token) || StringUtils.isNotBlank(uId)) {
            SysToken sysToken = new SysToken();
            sysToken.setToken(token);
            sysToken.setUserId(Long.parseLong(uId));
            sysToken.setStatus("1");//状态：0.失效1.有效
            List<SysToken> sysTokens = sysTokenService.selectByWhere(new Wrapper(sysToken));
            if (!sysTokens.isEmpty()) {
                UserMain userMain = userMainService.selectById(Long.parseLong(uId));
                if (userMain != null) {
                    if ("1".equals(userMain.getEmailValidate())) {
                        status = "1";
                        model.addAttribute("message", FYUtils.fyParams("当前账号邮箱已经是激活状态，不能重复激活！"));
                    } else {
                        status = "2";
                        userMainService.activeMail(sysTokens.get(0), uId);
                        model.addAttribute("message", FYUtils.fyParams("恭喜你，激活成功,注册成功。"));
                    }
                }
            }
        }
        if ("0".equals(status)) {
            model.addAttribute("message", FYUtils.fyParams("激活失败"));
        }
        model.addAttribute("status", status);
        return "sso/user/activeMail";
    }

    /**
     * 更换邮箱
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "changeEmail")
    public String changeEmail(RedirectAttributes redirectAttributes, Model model) {
        //表单验证
        List<String> errorList = validate1(model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return verificationEmail(model);//回显错误提示
        }
        String email = R.get("email");
        UserMain u = SsoUtils.getUserMain();
        UserMain userMain = new UserMain();
        userMain.setUId(u.getUId());
        userMain.setEmail(email);
        userMainService.updateByIdSelective(userMain);
        addMessage(redirectAttributes, "更换成功");
        //给用户发邮件
        SysToken sysToken = TokenUtils.generateToken(u.getUId(), "20");
        String url = R.getRequest().getScheme() + "://" + R.getRequest().getServerName() + ":" + R.getRequest().getServerPort() + R.getRequest().getContextPath() + Global.getConfig("activationUrl");
        //邮件模板参数
        Map<String, String> map = new HashMap<>();
        SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
        if (siteInfo != null) {
            map.put("siteName", siteInfo.getName());
        }
        map.put("userName", u.getLoginName());
        map.put("activationUrl", url + "?token=" + sysToken.getToken() + "&uId=" + sysToken.getUserId());
        //返回的邮件模板的标题和邮件内容
        Map<String, String> mapInfo = messageTemplateService.getEmailInfo(map, SiteSendMessagsService.ACTIVATE_ACCOUNT);
        if (mapInfo != null) {
            if (StringUtils.isNotBlank(mapInfo.get("emailContent")) && StringUtils.isNotBlank(mapInfo.get("emailTitle"))) {
                sendEmailService.sendEmail(userMain.getEmail(), mapInfo.get("emailTitle"), mapInfo.get("emailContent"));
            }
        }
        return "redirect:" + ssoPath + "/email/verificationEmail.htm";
    }

    /**
     * 发送激活邮件
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "sendActiveMail")
    public String sendActiveMail(RedirectAttributes redirectAttributes, Model model) {
        //表单验证
        List<String> errorList = validate2(model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return verificationEmail(model);//回显错误提示
        }
        UserMain userMain = SsoUtils.getUserMain();
        SysToken sysToken = TokenUtils.generateToken(userMain.getUId(), "20");
        String url = R.getRequest().getScheme() + "://" + R.getRequest().getServerName() + ":" + R.getRequest().getServerPort() + R.getRequest().getContextPath() + Global.getConfig("activationUrl");
        //String text = "请点击以下链接激活邮箱："+url+"?token="+sysToken.getToken()+"&uId="+sysToken.getUserId();
        //sendEmailService.sendEmail(userMain.getEmail(), validateCode, "激活邮件", text);
        //邮件模板参数
        Map<String, String> map = new HashMap<>();
        SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
        if (siteInfo != null) {
            map.put("siteName", siteInfo.getName());
        }
        map.put("userName", userMain.getLoginName());
        map.put("activationUrl", url + "?token=" + sysToken.getToken() + "&uId=" + sysToken.getUserId());
        //返回的邮件模板的标题和邮件内容
        Map<String, String> mapInfo = messageTemplateService.getEmailInfo(map, SiteSendMessagsService.ACTIVATE_ACCOUNT);
        if (mapInfo != null) {
            if (StringUtils.isNotBlank(mapInfo.get("emailContent")) && StringUtils.isNotBlank(mapInfo.get("emailTitle"))) {
                sendEmailService.sendEmail(userMain.getEmail(), mapInfo.get("emailTitle"), mapInfo.get("emailContent"));
            }
        }
        addMessage(redirectAttributes, "发送成功");
        return "redirect:" + ssoPath + "/email/verificationEmail.htm";
    }

    /**
     * 表单验证（更换邮箱）
     *
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate1(Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("email"))) {
            errorList.add("邮箱不能为空");
        }
        if (StringUtils.isNotBlank(R.get("email")) && !RegexUtils.checkEmail(R.get("email"))) {
            errorList.add("邮箱格式不正确");
        }
        if (StringUtils.isNotBlank(R.get("email")) && !SsoUtils.checkEmail(R.get("email"))) {
            errorList.add("邮箱已被占用，请更换邮箱");
        }
        if (StringUtils.isBlank(R.get("validateCode"))) {
            errorList.add("验证码不能为空");
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
            errorList.add("验证码错误");
        }
        return errorList;
    }

    /**
     * 表单验证（发送激活邮件）
     *
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate2(Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("validateCode"))) {
            errorList.add("验证码不能为空");
        }
        if (!ValidateCodeServlet.validate(R.getRequest(), R.get("validateCode"))) {
            errorList.add("验证码错误");
        }
        return errorList;
    }

}
