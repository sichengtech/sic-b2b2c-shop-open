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
package com.sicheng.sso.utils;

import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.store.entity.StoreRole;
import com.sicheng.admin.store.entity.StoreRoleMenu;
import com.sicheng.admin.store.entity.StoreSellerRole;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.cache.ShopCache;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.seller.site.service.SiteInfoService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.store.service.StoreRoleMenuService;
import com.sicheng.seller.store.service.StoreRoleService;
import com.sicheng.seller.store.service.StoreSellerRoleService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.shiro.SsoAuthorizingRealm.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: sso用户中心工具类</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月3日 下午3:39:41
 */
public class SsoUtils {

    private static UserMainService userMainService = SpringContextHolder.getBean(UserMainService.class);
    private static StoreRoleService storeRoleService = SpringContextHolder.getBean(StoreRoleService.class);
    private static StoreMenuService storeMenuService = SpringContextHolder.getBean(StoreMenuService.class);
    private static StoreRoleMenuService storeRoleMenuService = SpringContextHolder.getBean(StoreRoleMenuService.class);
    private static StoreSellerRoleService storeSellerRoleService = SpringContextHolder.getBean(StoreSellerRoleService.class);
    private static ShopCache cache = SpringContextHolder.getBean(ShopCache.class);
    private static Logger logger = LoggerFactory.getLogger(SsoUtils.class);
    private static SiteInfoService siteInfoService = SpringContextHolder.getBean(SiteInfoService.class);

    /**
     * 获取站点信息
     *
     * @return
     */
    public static SiteInfo getSiteInfo() {
        SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper());
        return siteInfo;
    }

    /**
     * 根据ID获取用户
     *
     * @param uId 用户uId
     * @return 取不到返回null
     */
    public static UserMain get(Long uId) {
        UserMain userMain = userMainService.selectById(uId);
        if (userMain == null) {
            return null;
        }
        
        if (userMain.isTypeUserSeller()) {
            StoreSellerRole storeSellerRole = new StoreSellerRole();
            storeSellerRole.setUId(userMain.getUId());
            List<StoreSellerRole> storeSellerRoles = storeSellerRoleService.selectByWhere(new Wrapper(storeSellerRole));
            List<Long> roleIds = new ArrayList<>();
            if (!storeSellerRoles.isEmpty()) {
                for (int i = 0; i < storeSellerRoles.size(); i++) {
                    roleIds.add(storeSellerRoles.get(i).getRoleId());
                }
                if (!roleIds.isEmpty()) {
                    userMain.setStoreRoles(storeRoleService.selectByIdIn(roleIds));
                }
            }
        }
        return userMain;
    }

    /**
     * 获取当前用户
     *
     * @return 取不到返回 null
     */
    public static UserMain getUserMain() {
        Principal principal = getPrincipal();
        if (principal != null) {
            UserMain userMain = get(principal.getUId());
            return userMain;
        }
        // 如果没有登录，则返回实例化空的User对象。
        return null;
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
            // subject.logout();
        } catch (UnavailableSecurityManagerException e) {
            // 取不到登录者对象，不应输出异常，很干扰发开人员的分析，直接返回null就很好。
            // e.printStackTrace();
        } catch (InvalidSessionException e) {
            // 取不到登录者对象，不应输出异常，很干扰发开人员的分析，直接返回null就很好。
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取用户的session
     *
     * @return
     */
    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
            // subject.logout();
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    /**
     * 获取当前用户角色列表
     *
     * @return
     */
    public static List<StoreRole> getRoleList() {
        List<StoreRole> storeRoles = new ArrayList<StoreRole>();
        UserMain userMain = getUserMain();
        if (userMain.isTypeUserSeller()) {//判断是不是卖家
            if ("1".equals(userMain.getTypeAccount())) {
                //主账号
                StoreRole storeRole = new StoreRole();
                storeRole.setStoreId(userMain.getUserSeller().getStoreId());
                storeRoles = storeRoleService.selectByWhere(new Wrapper(storeRole));
            }
            if ("2".equals(userMain.getTypeAccount())) {
                StoreSellerRole storeSellerRole = new StoreSellerRole();
                storeSellerRole.setUId(userMain.getUId());
                List<StoreSellerRole> storeSellerRoles = storeSellerRoleService.selectByWhere(new Wrapper(storeSellerRole));
                List<Long> roleIds = new ArrayList<>();
                if (!storeSellerRoles.isEmpty()) {
                    for (int i = 0; i < storeSellerRoles.size(); i++) {
                        roleIds.add(storeSellerRoles.get(i).getRoleId());
                    }
                    if (!roleIds.isEmpty()) {
                        storeRoles = storeRoleService.selectByIdIn(roleIds);
                    }
                }
            }
        }
        return storeRoles;
    }

    /**
     * 获取当前用户授权菜单
     * 管理员获取所有菜单，非管理员获取自己的权限的菜单
     *
     * @return
     */
    public static List<StoreMenu> getStoreMenuList() {
        List<StoreMenu> storeMenuList = new ArrayList<StoreMenu>();
        UserMain userMain = getUserMain();
        if (userMain.isTypeUserSeller()) {//判断是不是卖家
            if ("1".equals(userMain.getTypeAccount())) {
                //主账号就把菜单权限都给他
                StoreMenu storeMenu = new StoreMenu();
                storeMenu.setIsShow("1");//是否在菜单中显示（0隐藏、1显示）
                Wrapper wrapper = new Wrapper();
                wrapper.orderBy("a.sort");
                wrapper.setEntity(storeMenu);
                storeMenuList = storeMenuService.selectByWhere(wrapper);
            }
            if ("2".equals(userMain.getTypeAccount())) {
                //子账号就取角色应有的菜单权限给他
                StoreSellerRole storeSellerRole = new StoreSellerRole();
                storeSellerRole.setUId(userMain.getUId());
                List<StoreSellerRole> storeSellerRoles = storeSellerRoleService.selectByWhere(new Wrapper(storeSellerRole));
                List<Long> roleIds = new ArrayList<>();
                if (!storeSellerRoles.isEmpty()) {
                    for (int i = 0; i < storeSellerRoles.size(); i++) {
                        roleIds.add(storeSellerRoles.get(i).getRoleId());
                    }
                }
                if (!roleIds.isEmpty()) {
                    List<StoreRoleMenu> storeRoleMenus = storeRoleMenuService.selectByWhere(new Wrapper().and("role_id in", roleIds));
                    List<Long> menuIds = new ArrayList<>();
                    if (!storeRoleMenus.isEmpty()) {
                        for (int i = 0; i < storeRoleMenus.size(); i++) {
                            menuIds.add(storeRoleMenus.get(i).getMenuId());
                        }
                    }
                    if (!menuIds.isEmpty()) {
                        StoreMenu entity = new StoreMenu();
                        entity.setIsShow("1");//是否在菜单中显示（0隐藏、1显示）
                        Wrapper wrapper = new Wrapper();
                        wrapper.orderBy("a.sort");
                        wrapper.setEntity(entity);
                        wrapper.and("a.menu_id in", menuIds);
                        storeMenuList = storeMenuService.selectByWhere(wrapper);
                    }
                }
            }
        }
        return storeMenuList;
    }

    /**
     * 获取子菜单
     * 通过一级菜单的编号(menuNum)获取下级菜单
     * 主账号获取所有菜单，子账号获取自己的权限的菜单
     *
     * @return
     */
    public static List<StoreMenu> getStoreChildMenuList(Long menuId) {
        List<StoreMenu> storeMenuList = new ArrayList<>();
        List<StoreMenu> storeMenus = getStoreMenuList();
        if (menuId!=null) {
            for (StoreMenu m : storeMenus) {
                if (StringUtils.isNotBlank(m.getParentIds()) && m.getParentIds().contains("," + menuId + ",")) {
                    storeMenuList.add(m);
                }
            }
        } else {
            for (StoreMenu m : storeMenus) {
                if (m.getParentIds().split(",").length > 2) {//过滤根节点和一级菜单
                    storeMenuList.add(m);
                }
            }
        }
        return storeMenuList;
    }

    /**
     * 验证账号是否有效
     * 有效 return true
     * 无效 return false
     */
    public static Boolean checkLoginName(String loginName) {
        if (StringUtils.isNotBlank(loginName)) {
            Wrapper wrapper = new Wrapper();
            wrapper.and("lower(login_name) =",loginName.toLowerCase());
            List<UserMain> userMains = userMainService.selectByWhere(wrapper);
            return userMains.isEmpty();
        }
        return false;
    }

    /**
     * 验证手机号是否有效
     * 有效 return true
     * 无效 return false
     */
    public static Boolean checkMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            Wrapper wrapper = new Wrapper();
            UserMain userMain = new UserMain();
            userMain.setMobile(mobile);
            wrapper.setEntity(userMain);
            List<UserMain> userMains = userMainService.selectByWhere(wrapper);
            return userMains.isEmpty();
        }
        return false;
    }

    /**
     * 验证邮箱是否有效
     * 有效 return true
     * 无效 return false
     */
    public static Boolean checkEmail(String email) {
        if (StringUtils.isNotBlank(email)) {
            Wrapper wrapper = new Wrapper();
            UserMain userMain = new UserMain();
            //邮箱转小写
            email = email.toLowerCase();
            userMain.setEmail(email);
            wrapper.setEntity(userMain);
            List<UserMain> userMains = userMainService.selectByWhere(wrapper);
            return userMains.isEmpty();
        }
        return false;
    }

    /**
     * 登录失败记数器
     * 是否是验证码登录
     *
     * @param loginName 用户名
     * @param isFail    计数加1
     * @param clean     计数清零
     * @return
     */
    public static boolean isValidateCodeLogin(String loginName, boolean isFail, boolean clean) {
        //用户名转小写
        loginName = loginName.toLowerCase();
        //当前时间毫秒数
        long current = System.currentTimeMillis();
        //当天零点零分零秒的毫秒数
        String zeros = DateUtils.getDate() + " 00:00:00";
        Date zeroDate = null;
        try {
            zeroDate = DateUtils.parseDate(zeros, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
            logger.debug("sso时间转换出错", e);
        }
        long zero = zeroDate.getTime();
        //当天23点59分59秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;
        //当天23点59分59秒与当前时间的秒数
        long difference = (twelve - current) / 1000;
        Long loginFailNum = (Long) cache.get(CacheConstant.SSO_LOGINFAIL + loginName);
        if (loginFailNum == null) {
            loginFailNum = (long) 0;
        }
        //计数加1
        if (isFail) {
            loginFailNum++;
            cache.put(CacheConstant.SSO_LOGINFAIL + loginName, loginFailNum, difference);
        }
        //计数清零
        if (clean) {
            cache.del(CacheConstant.SSO_LOGINFAIL + loginName);
        }
        return loginFailNum >= 3;
    }


}
