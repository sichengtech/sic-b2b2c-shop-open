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
package com.sicheng.admin.sys.utils;

import com.sicheng.admin.sys.dao.*;
import com.sicheng.admin.sys.entity.*;
import com.sicheng.admin.sys.shiro.SystemAuthorizingRealm.Principal;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.cache.ShopCache;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户工具类
 *
 * @author zhaolei
 */
public class UserUtils {

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
    private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
    private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
    private static SysUserRoleDao sysUserRoleDao = SpringContextHolder.getBean(SysUserRoleDao.class);
    private static SysRoleMenuDao sysRoleMenuDao = SpringContextHolder.getBean(SysRoleMenuDao.class);
    private static SysRoleOfficeDao sysRoleOfficeDao = SpringContextHolder.getBean(SysRoleOfficeDao.class);
    private static ShopCache cache = SpringContextHolder.getBean(ShopCache.class);
    private static Logger logger = LoggerFactory.getLogger(UserUtils.class);

    /**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     */
    public static User getUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            User user = userDao.selectById(principal.getId());
            if (user != null) {
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new User();
    }

    /**
     * 获取当前用户角色列表
     *
     * @return
     */
    public static List<Role> getRoleList() {
        List<Role> roleList = new ArrayList<Role>();
        User user = getUser();
        if (user.isAdmin()) {
            roleList = roleDao.selectByWhere(null, new Wrapper(new Role()));
        } else {
            List<SysUserRole> sysUserRoles = sysUserRoleDao.selectByWhere(null, new Wrapper(new SysUserRole()).and("user_id=", user.getId()));
            if (!sysUserRoles.isEmpty()) {
                List<Long> roleIds = new ArrayList<Long>();
                for (int i = 0; i < sysUserRoles.size(); i++) {
                    roleIds.add(sysUserRoles.get(i).getRoleId());
                }
                roleList = roleDao.selectByWhere(null, new Wrapper().and("id in", roleIds).and("del_flag=", BaseEntity.DEL_FLAG_NORMAL));
            }
        }
        return roleList;
    }

    /**
     * 获取当前用户授权菜单
     * 管理员获取所有菜单，非管理员获取自己的权限的菜单
     *
     * @return
     */
    public static List<Menu> getMenuList() {

        List<Menu> menuList = new ArrayList<Menu>();
        User user = getUser();
        if (user.isAdmin()) {
            //超管查出所有的权限
            //会过滤掉 del_flag=1的记录，del_flag=1表示逻辑删除
            Menu menu=new Menu();
            menu.setIsShow("1");//可见，0隐藏、1显示   只查出可见的
            menuList = menuDao.selectByWhere(null,new Wrapper(menu).orderBy("a.sort"));
        } else {
            //查出用户的角色id
            List<SysUserRole> sysUserRoles = sysUserRoleDao.selectByWhere(null, new Wrapper(new SysUserRole()).and("user_id=", user.getId()));
            if (!sysUserRoles.isEmpty()) {
                List<Long> roleIds = new ArrayList<Long>();
                for (int i = 0; i < sysUserRoles.size(); i++) {
                    roleIds.add(sysUserRoles.get(i).getRoleId());
                }
                //查出角色
                List<Role> roles = roleDao.selectByWhere(null, new Wrapper().and("id in", roleIds).and("useable=", Global.YES).and("del_flag=", BaseEntity.DEL_FLAG_NORMAL));
                if (!roles.isEmpty()) {
                    List<Long> rIds = new ArrayList<Long>();
                    for (int i = 0; i < roles.size(); i++) {
                        rIds.add(roles.get(i).getId());
                    }
                    if (!rIds.isEmpty()) {
                        //查出某角色可查看的菜单
                        List<SysRoleMenu> sysRoleMenus = sysRoleMenuDao.selectByWhere(null, new Wrapper().and("role_id in", rIds));
                        if (!sysRoleMenus.isEmpty()) {
                            List<Long> menuIds = new ArrayList<Long>();
                            for (int i = 0; i < sysRoleMenus.size(); i++) {
                                menuIds.add(sysRoleMenus.get(i).getMenuId());
                            }
                            //查出菜单
                            Menu menu=new Menu();
                            menu.setIsShow("1");//可见，0隐藏、1显示   只查出可见的
                            Wrapper w = new Wrapper(menu);
                            w.and("id in", menuIds);
                            w.and("del_flag=", BaseEntity.DEL_FLAG_NORMAL);
                            w.orderBy("sort");
                            menuList = menuDao.selectByWhere(null, w);
                        }
                    }
                }
            }
        }
        return menuList;
    }

    /**
     * 获取子菜单
     * 通过某级菜单的ID(menuId)获取下级菜单列表
     * 管理员获取所有菜单，非管理员获取自己的权限的菜单
     *
     * @return
     */
    public static List<Menu> getChildMenuList(Long menuId) {
        List<Menu> list= getMenuList();
        List<Menu> menuList=new ArrayList<>();
        for(Menu m:list){
            if(StringUtils.isNotBlank(m.getParentIds()) && m.getParentIds().contains(","+menuId+",")){
                menuList.add(m);
            }
        }
        return menuList;
    }

    /**
     * 获取当前用户授权的区域
     *
     * @return
     */
    public static List<Area> getAreaList() {
        return areaDao.selectAll(new Wrapper(new Area()).orderBy("a.code"));
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getOfficeList() {
        List<Office> officeList = new ArrayList<Office>();
        User user = getUser();
        if (user.isAdmin()) {
            officeList = getOfficeAllList();
        } else {
            List<Role> roleList = getRoleList();
            if (!roleList.isEmpty()) {
                List<Long> roleIds = new ArrayList<>();
                for (int i = 0; i < roleList.size(); i++) {
                    roleIds.add(roleList.get(i).getId());
                }
                if (!roleIds.isEmpty()) {
                    List<SysRoleOffice> sysRoleOfficeList = sysRoleOfficeDao.selectByWhere(null, new Wrapper().and("role_id in", roleIds));
                    if (!sysRoleOfficeList.isEmpty()) {
                        List<Long> officeIds = new ArrayList<>();
                        for (int i = 0; i < sysRoleOfficeList.size(); i++) {
                            officeIds.add(sysRoleOfficeList.get(i).getOfficeId());
                        }
                        officeList = officeDao.selectByWhere(null, new Wrapper().and("id in", officeIds).and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("code"));
                    }
                }
            }
        }
        return officeList;
    }

    /**
     * 获取所有部门
     *
     * @return
     */
    public static List<Office> getOfficeAllList() {
        List<Office> officeList = officeDao.selectByWhere(null, new Wrapper().and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("code"));
        if (!officeList.isEmpty()) {
            List<Long> areaIds = new ArrayList<Long>();
            for (int i = 0; i < officeList.size(); i++) {
                areaIds.add(officeList.get(i).getArea().getId());
            }
            List<Area> areaList = areaDao.selectByIdIn(areaIds);
            for (int i = 0; i < officeList.size(); i++) {
                for (int j = 0; j < areaList.size(); j++) {
                    if (officeList.get(i).getArea().getId().equals(areaList.get(j).getId())) {
                        officeList.get(i).setArea(areaList.get(j));
                    }
                }
            }
        }
        return officeList;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
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
     * 登录失败记数器
     * 是否是验证码登录
     *
     * @param username 用户名
     * @param isFail   计数加1
     * @param clean    计数清零
     * @return
     */
    public static boolean isValidateCodeLogin(String username, boolean isFail, boolean clean) {
        //当前时间毫秒数
        long current = System.currentTimeMillis();
        //当天零点零分零秒的毫秒数
        String zeros = DateUtils.getDate() + " 00:00:00";
        Date zeroDate = null;
        try {
            zeroDate = DateUtils.parseDate(zeros, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
            logger.debug("admin时间转换出错", e);
        }
        long zero = zeroDate.getTime();
        //当天23点59分59秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;
        //当天23点59分59秒与当前时间的秒数差
        long difference = (twelve - current) / 1000;
        Long loginFailNum = (Long) cache.get(CacheConstant.ADMIN_LOGINFAIL + username);
        if (loginFailNum == null) {
            loginFailNum = (long) 0;
        }
        //计数加1
        if (isFail) {
            loginFailNum++;
            cache.put(CacheConstant.ADMIN_LOGINFAIL + username, loginFailNum, difference);
        }
        //计数清零
        if (clean) {
            cache.del(CacheConstant.ADMIN_LOGINFAIL + username);
        }
        return loginFailNum >= 3;
    }

}