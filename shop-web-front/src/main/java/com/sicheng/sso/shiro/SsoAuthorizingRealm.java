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
package com.sicheng.sso.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.store.entity.StoreRole;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.cache.ShopCache;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.servlet.ValidateCodeServlet;
import com.sicheng.common.shiro.FdpSimpleByteSource;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.Servlets;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.seller.user.util.LogUtils;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * 系统安全认证实现类
 *
 * @author zhaolei
 * @version 2014-7-5
 */
public class SsoAuthorizingRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private UserMainService userMainService;

    private ShopCache cache;

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        SsoUsernamePasswordToken token = (SsoUsernamePasswordToken) authcToken;

        if (logger.isDebugEnabled()) {
            int activeSessionSize = getUserMainService().getSessionDAO().getActiveSessions(false).size();
            logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
        }

        // 校验登录验证码
        if (SsoUtils.isValidateCodeLogin(token.getUsername(), false, false)) {
            Session session = SsoUtils.getSession();
            String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
            if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)) {
                throw new AuthenticationException("msg:验证码错误,请重试.");
            }
        }

        String ssoReg = token.getSsoReg();//登陆类型 1 账号登陆 2 手机号登陆
        // 校验用户名密码
        Wrapper wrapper = new Wrapper();
        if ("1".equals(ssoReg) || StringUtils.isBlank(ssoReg)) {
            wrapper.and("lower(login_name) =",token.getUsername().toLowerCase());
        }
        if ("2".equals(ssoReg)) {
            wrapper.and("mobile=",token.getUsername());
        }
        List<UserMain> userMains = getUserMainService().selectByWhere(wrapper);
        UserMain userMain = null;
        if (!userMains.isEmpty()) {
            userMain = userMains.get(0);
            if (Global.YES.equals(userMain.getIsLocked())) {
                throw new AuthenticationException("msg:该帐号禁止登录.");
            }
            if ("1".equals(ssoReg) && StringUtils.isBlank(userMain.getPassword())) {
                throw new AuthenticationException("msg:该账号为手机注册，未设置密码！");
            }
            byte[] salt = userMain.getSalt().getBytes();
            //获取注册时存入的缓存
            Object value_Register_cache = getcache().get(CacheConstant.SMS_CODE + R.getSession().getId());
            //删除注册时存入的缓存
            getcache().del(CacheConstant.SMS_MOBILE + R.getSession().getId());
            getcache().del(CacheConstant.SMS_CODE + R.getSession().getId());
            //获取手机动态登录时存入的缓存
            Object value_login_cache = getcache().get(CacheConstant.SMS_CODE + userMain.getUId());
            //删除手机动态登陆时存入的缓存
            getcache().del(CacheConstant.SMS_MOBILE + userMain.getUId());
            getcache().del(CacheConstant.SMS_CODE + userMain.getUId());
            //获取admin系统“代运营”模拟登陆存入缓存
            Object value_simulationLogin_cache = getcache().get(CacheConstant.SIMULATION_LOGIN + userMain.getUId());
            //删除admin系统“代运营”模拟登陆存入缓存
            getcache().del(CacheConstant.SIMULATION_LOGIN + userMain.getUId());
            //业务逻辑
            String verification_cache = null;
            if (value_Register_cache != null) {
                verification_cache = value_Register_cache.toString();
            }
            if (value_login_cache != null) {
                verification_cache = value_login_cache.toString();
            }
            if (value_simulationLogin_cache != null) {
                verification_cache = value_simulationLogin_cache.toString();
            }
            if (StringUtils.isBlank(verification_cache)) {
                //账号登录
                return new SimpleAuthenticationInfo(new Principal(userMain, token.isMobileLogin()),
                        userMain.getPassword(), new FdpSimpleByteSource(salt), getName());
            } else {
                //验证码登录
                String password = PasswordUtils.entryptPassword(verification_cache, userMain.getSalt());
                return new SimpleAuthenticationInfo(new Principal(userMain, token.isMobileLogin()),
                        password, new FdpSimpleByteSource(salt), getName());
            }
        } else {
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) getAvailablePrincipal(principals);

        if (!Global.TRUE.equals(Global.getConfig("multiAccountLogin.sso"))) {
            // 获取当前已登录的所有用户
            // TODO 有性能问题
            Collection<Session> sessions = getUserMainService().getSessionDAO().getActiveSessions(true, principal, SsoUtils.getSession());
            if (sessions.size() > 0) {
                // 如果是登录进来的，则踢出已在线用户
                if (SsoUtils.getSubject().isAuthenticated()) {
                    for (Session session : sessions) {
                        getUserMainService().getSessionDAO().delete(session);
                    }
                }
                // 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
                else {
                    SsoUtils.getSubject().logout();
                    throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
                }
            }
        }
        UserMain u = new UserMain();
        u.setLoginName(principal.getLoginName());
        List<UserMain> userMains = getUserMainService().selectByWhere(new Wrapper(u));
        UserMain userMain = null;
        if (!userMains.isEmpty()) {
            userMain = userMains.get(0);
        } else {
            userMain = new UserMain();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (userMain.getUId() != null) {
            if (userMain.isTypeUserSeller()) {//判断是不是卖家
                List<StoreMenu> storeMenus = SsoUtils.getStoreMenuList();
                for (StoreMenu storeMenu : storeMenus) {
                    if (StringUtils.isNotBlank(storeMenu.getPermission())) {
                        // 添加基于Permission的权限信息
                        for (String permission : StringUtils.split(storeMenu.getPermission(), ",")) {
                            info.addStringPermission(permission);
                        }
                    }
                }
                // 添加用户角色信息
                List<StoreRole> storeRoles = SsoUtils.getRoleList();
                for (StoreRole storeRole : storeRoles) {
                    info.addRole(storeRole.getEnname());
                }
                // 更新登录IP和时间
                getUserMainService().updateUserLoginInfo(userMain);
                // 记录登录日志
                LogUtils.saveLog(Servlets.getRequest(), "系统登录");
            }
            // 添加用户权限
            info.addStringPermission("user");
        }
        return info;
    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        authorizationValidate(permission);
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        authorizationValidate(permission);
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermittedAll(permissions, info);
    }

    /**
     * 授权验证方法
     *
     * @param permission
     */
    private void authorizationValidate(Permission permission) {
        // 模块授权预留接口
    }

    /**
     * 获取系统业务对象
     */
    public UserMainService getUserMainService() {
        if (userMainService == null) {
            userMainService = SpringContextHolder.getBean(UserMainService.class);
        }
        return userMainService;
    }

    /**
     * 获取缓存
     */
    public ShopCache getcache() {
        if (cache == null) {
            cache = SpringContextHolder.getBean(ShopCache.class);
        }
        return cache;
    }

    /**
     * 清空用户关联权限认证
     */
    public void clearCachedAuthorizationInfo(Principal principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清空所有关联认证
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PasswordUtils.HASH_ALGORITHM);
        matcher.setHashIterations(PasswordUtils.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long uId; // 编号
        private String loginName; // 登录名
        private String name; // 姓名
        private boolean mobileLogin; // 是否手机登录

        public Principal(UserMain userMain, boolean mobileLogin) {
            this.uId = userMain.getUId();
            this.loginName = userMain.getLoginName();
            this.mobileLogin = mobileLogin;
        }

        public Long getUId() {
            return uId;
        }

        public String getLoginName() {
            return loginName;
        }

        public String getName() {
            return name;
        }

        public boolean isMobileLogin() {
            return mobileLogin;
        }

        /**
         * 获取SESSIONID
         */
        public String getSessionid() {
            try {
                return (String) SsoUtils.getSession().getId();
            } catch (Exception e) {
                return "";
            }
        }

        @Override
        public String toString() {
            return String.valueOf(uId);
        }

    }
}
