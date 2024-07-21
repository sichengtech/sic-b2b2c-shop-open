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
import com.sicheng.admin.sys.dao.UserDao;
import com.sicheng.admin.sys.entity.Role;
import com.sicheng.admin.sys.entity.SysUserRole;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.security.Digests;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.service.ServiceException;
import com.sicheng.common.utils.Encodes;
import com.sicheng.common.web.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户Service
 *
 * @author fxx
 * @version 2017-02-3
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserService extends CrudService<UserDao, User> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Autowired
    private UserDao userDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public String entryptPassword(String plainPassword) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        if (user.getId() == null) {
            user.preInsert(UserUtils.getUser());
            userDao.insert(user);
        } else {
            // 更新用户数据
            user.preUpdate(user);
            userDao.updateById(user);
        }
        if (user.getId() != null) {
            // 更新用户与角色关联
            sysUserRoleService.deleteByWhere(new Wrapper().and("user_id = ", user.getId()));
            if (user.getRoleList() != null && user.getRoleList().size() > 0) {
                List<SysUserRole> list = Lists.newArrayList();
                for (Role role : user.getRoleList()) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(user.getId());
                    sysUserRole.setRoleId(role.getId());
                    list.add(sysUserRole);
                }
                sysUserRoleService.insertSelectiveBatch(list);
            } else {
                throw new ServiceException(user.getLoginName() + "没有设置角色！");
            }
        }
    }

    /**
     * 修改登录信息
     *
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserLoginInfo(User user) {
        User u = new User();
        // 保存上次登录信息
        u.setOldLoginIp(user.getLoginIp());
        u.setOldLoginDate(user.getLoginDate());
        // 更新本次登录信息
        u.setLoginIp(R.getRealIp());
        u.setLoginDate(new Date());
        u.setId(user.getId());
        userDao.updateByIdSelective(u);
    }

}
