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
import com.sicheng.common.cache.ShopCache;
import com.sicheng.common.config.Global;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.shiro.FdpSessionDAOI;
import com.sicheng.common.utils.CookieUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.common.web.Servlets;
import com.sicheng.seller.site.service.SiteRegisterService;
import com.sicheng.sso.shiro.SsoAuthorizingRealm.Principal;
import com.sicheng.sso.shiro.SsoFormAuthenticationFilter;
import com.sicheng.sso.shiro.SsoUsernamePasswordToken;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>标题: 会员登录</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年4月25日 下午6:20:32
 */
@Controller
@RequestMapping(value = "${ssoPath}/login")
public class LoginController extends BaseController {

    private final String TOP_STATUS = "topStatus";        //登录top.jsp中的名字控制
    private final String TOP_STATUS_VALUE = "1";        //1.登录 2.注册 3.忘记密码
    private final static String COLLECTION_FIVE = "5";    //登录成功！
    private final static String COLLECTION_SIX = "6";    //用户或密码错误,请重试.
    private final int loginInvalidTime = 1800;            //cookie是否登录失效(持久性存储30分钟)（有效期要和session时间一样长）

    @Autowired
    private FdpSessionDAOI sessionDAO;

    @Autowired
    private SiteRegisterService siteRegisterService;

    /**
     * 缓存接口
     */
    @Autowired
    protected ShopCache cache;

    /**
     * 进入登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (logger.isDebugEnabled()) {
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        Principal principal = SsoUtils.getPrincipal();
        if (principal != null && !principal.isMobileLogin()) {
            return "redirect:" + ssoPath + "/login/loginSuccess.htm";
        }
        //获取用户名和密码的规则
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        model.addAttribute("siteRegister", siteRegister);
        model.addAttribute(TOP_STATUS, TOP_STATUS_VALUE);
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
        return "/login";
    }

    /**
     * 登录失败
     * 登录页面提交表单给shiro,真正登录的POST请求由Filter完成
     * 若登录失败后，才会走到这里
     * 若登录成功后，由shiro跳转到的“successUrl”
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        Principal principal = SsoUtils.getPrincipal();
        if (principal != null && !principal.isMobileLogin()) {
            return "redirect:" + ssoPath + "/login/loginSuccess.htm";
        }
        String loginName = WebUtils.getCleanParam(request, SsoFormAuthenticationFilter.SSO_DEFAULT_LOGINNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, SsoFormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, SsoFormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception = (String) request.getAttribute(SsoFormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String) request.getAttribute(SsoFormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
        message = FYUtils.fyParams(message);
        String ssoReg = WebUtils.getCleanParam(request, SsoFormAuthenticationFilter.SSO_REG_PARAM);
        if (Servlets.isAjaxRequest2(request)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", LoginController.COLLECTION_SIX);
            map.put("message", message);
            //ajax登录
            String json = JsonMapper.getInstance().toJson(map);
            R.writeJson(json, "UTF-8");
            return null;
        } else {
            if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
                message = FYUtils.fyParams("用户或密码错误,请重试.");
            }

            model.addAttribute(SsoFormAuthenticationFilter.SSO_DEFAULT_LOGINNAME_PARAM, loginName);
            model.addAttribute(SsoFormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
            model.addAttribute(SsoFormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
            model.addAttribute(SsoFormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
            model.addAttribute(SsoFormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
            model.addAttribute(SsoFormAuthenticationFilter.SSO_REG_PARAM, ssoReg);

            if (logger.isDebugEnabled()) {
                logger.debug("login fail, active session size: {}, message: {}, exception: {}",
                        sessionDAO.getActiveSessions(false).size(), message, exception);
            }

            // 非授权异常，登录失败，验证码加1。
            if (!UnauthorizedException.class.getName().equals(exception)) {
                model.addAttribute("isValidateCodeLogin", SsoUtils.isValidateCodeLogin(loginName, true, false));
            }

            // 如果是手机登录，则返回JSON字符串
            if (mobile) {
                return renderString(response, model);
            }

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
            return "/login";
        }
    }

    /**
     * 登录成功后，shiro会走到这里
     * 根据用户类型判断去哪个系统的首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/loginSuccess")
    public String loginSuccess(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        UserMain userMain = SsoUtils.getUserMain();
        CookieUtils.setCookie(request, response, "usm.headPicPath", userMain.getUserMember().getHeadPicPath(), "/", -1);//头像(临时存储)
        CookieUtils.setCookie(request, response, "usm.loginName", userMain.getLoginName(), "/", -1);//用户名(临时存储)
        CookieUtils.setCookie(request, response, "usm.isTypeUserPurchaser", userMain.isTypeUserPurchaser() ? "true" : "false", "/", -1); //是否为采购商(临时存储)
        CookieUtils.setCookie(request, response, "usm.isloginInvalid", "true", loginInvalidTime); //是否登录失效(持久性存储30分钟)（有效期要和session时间一样长）
        if (Servlets.isAjaxRequest2(request)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", LoginController.COLLECTION_FIVE);
            String json = JsonMapper.getInstance().toJson(map);
            R.writeJson(json, "UTF-8");
            return null;
        } else {
            //正常登录
            Principal principal = SsoUtils.getPrincipal();
            // 登录成功后，验证码计算器清零
            SsoUtils.isValidateCodeLogin(principal.getLoginName(), false, true);
            if (logger.isDebugEnabled()) {
                logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
            }

            // shiro 登录成功后，跳转到登录前的页面
            // shiro在跳转前会把跳转过来的页面链接保存到session的attribute中，key的值叫shiroSavedRequest，我们可以能过WebUtils类拿到
            // 当用户登录成功后，可能通过String url = WebUtils.getSavedRequest(request).getRequestUrl();，拿到跳转到登录页面前的url，然后redirect到这个url。
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
            if (savedRequest != null) {
                String url = savedRequest.getRequestUrl();
                if (url != null) {
                    String ctx = request.getContextPath();
                    if (ctx != null && url.startsWith(ctx)) {
                        url = url.substring(ctx.length());
                    }
                    return "redirect:" + url;
                }
            }

            //判断用户类型去哪个首页
            if (userMain.isTypeUserSeller()) {
                //卖家
                return "redirect:" + sellerPath + "/index.htm";
            } else {
                //买家
                return "redirect:" + memberPath + "/index.htm";
            }
        }
    }


    /**
     * admin系统模拟登陆调取此方法进行登陆
     */
    @RequestMapping(value = "/loginSimulation")
    @ResponseBody
    public Map<String, String> loginSimulation() {
        //调取退出方法，为了使服务端session更换
        Subject subject1 = SsoUtils.getSubject();
        subject1.logout();
        Map<String, String> map = new HashMap<String, String>();
        String loginName = R.get("loginName");
        String verification = R.get("verification");
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(verification)) {
            map.put("status", "0");
            map.put("content", FYUtils.fyParams("进入店铺失败"));
            return map;
        }
        SsoUsernamePasswordToken token = new SsoUsernamePasswordToken();
        token.setUsername(loginName);
        token.setPassword(verification.toCharArray());
        Subject subject2 = SsoUtils.getSubject();
        subject2.login(token);
        map.put("status", "1");
        return map;
    }

}
