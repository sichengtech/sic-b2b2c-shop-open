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
package com.sicheng.admin.sys.service;

import com.google.common.collect.Lists;
import com.sicheng.admin.sys.dao.RoleDao;
import com.sicheng.admin.sys.entity.*;
import com.sicheng.admin.sys.shiro.SystemAuthorizingRealm;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色Service
 *
 * @author fxx
 * @version 2017-02-3
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class RoleService extends CrudService<RoleDao, Role> {

    //请在这里编写业务逻辑

    //TreeService父类中N个树结构的常用方法，已全部继承下来，可直接使用。

    //父父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleOfficeService sysRoleOfficeService;
    @Autowired
    private UserService userService;
    @Autowired
    private SystemAuthorizingRealm systemAuthorizingRealm;

    public List<Role> findAllRole() {
        return UserUtils.getRoleList();
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean outUserInRole(Role role, User user) {
        List<Role> roles = user.getRoleList();
        for (Role e : roles) {
            if (e.getId().equals(role.getId())) {
                roles.remove(e);
                userService.saveUser(user);
                return true;
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public User assignUserToRole(Role role, User user) {
        if (user == null) {
            return null;
        }
        List<Long> roleIds = user.getRoleIdList();
        if (roleIds.contains(role.getId())) {
            return null;
        }
        user.getRoleList().add(role);
        userService.saveUser(user);
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveRole(Role role) {
        if (role.getId() == null) {
            role.preInsert(UserUtils.getUser());
            roleDao.insert(role);
        } else {
            role.preUpdate(UserUtils.getUser());
            Role oldRole = dao.selectById(role.getId());
            roleDao.updateByWhereSelective(role, new Wrapper(oldRole));
        }
        // 更新角色与菜单关联
        sysRoleMenuService.deleteByWhere(new Wrapper().and("role_id=", role.getId()));

        if (role.getMenuList().size() > 0) {
            List<SysRoleMenu> list = Lists.newArrayList();
            for (Menu menu : role.getMenuList()) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(role.getId());
                sysRoleMenu.setMenuId(menu.getId());
                list.add(sysRoleMenu);
            }
            sysRoleMenuService.insertSelectiveBatch(list);
        }
        // 更新角色与部门关联
        sysRoleOfficeService.deleteByWhere(new Wrapper().and("role_id=", role.getId()));

        if (role.getOfficeList().size() > 0) {
            List<SysRoleOffice> list = Lists.newArrayList();
            for (Office office : role.getOfficeList()) {
                SysRoleOffice sysRoleOffice = new SysRoleOffice();
                sysRoleOffice.setRoleId(role.getId());
                sysRoleOffice.setOfficeId(office.getId());
                list.add(sysRoleOffice);
            }
            sysRoleOfficeService.insertSelectiveBatch(list);
        }
        //清空所有权限
        systemAuthorizingRealm.clearAllCachedAuthorizationInfo();
    }

}
