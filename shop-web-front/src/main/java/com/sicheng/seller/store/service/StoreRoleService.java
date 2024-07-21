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
package com.sicheng.seller.store.service;

import com.google.common.collect.Lists;
import com.sicheng.admin.store.dao.StoreRoleDao;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.store.entity.StoreRole;
import com.sicheng.admin.store.entity.StoreRoleMenu;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 卖家角色 Service
 *
 * @author cl
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreRoleService extends CrudService<StoreRoleDao, StoreRole> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private StoreRoleMenuService storeRoleMenuService;
    @Autowired
    private StoreMenuService storeMenuService;



    /**
     * 商家角色与商家角色菜单中间表的插入
     *
     * @param storeRole         商家角色
     * @param listMenuId 商家角色 与商家菜单的list
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(StoreRole storeRole, String[] listMenuId) {
        //把权限的1级2级3级都加入菜单中（进行转换）
        List menuIds = Arrays.asList(listMenuId);
        List<StoreMenu> storeMenus = storeMenuService.selectByIdIn(menuIds);
        Set<Long> testSet = new HashSet<Long>();
        if (!storeMenus.isEmpty()) {
            for (int i = 0; i < storeMenus.size(); i++) {
                String[] ps = null;
                String parentIds = storeMenus.get(i).getParentIds();
                if (StringUtils.isNotBlank(parentIds)) {
                    ps = parentIds.split(",");
                }
                for (int j = 0; j < ps.length; j++) {
                    if (!("0".equals(ps[j]) || "1".equals(ps[j]))) {
                        testSet.add(Long.parseLong(ps[j]));
                    }
                }
            }
            for (int i = 0; i < listMenuId.length; i++) {
                testSet.add(Long.parseLong(listMenuId[i]));
            }
        }
        //新增商家角色
        super.insertSelective(storeRole);
        Long roleId = storeRole.getRoleId();
        //批量将角色id和菜单id存入中间表
        List<StoreRoleMenu> list = Lists.newArrayList();
        for (Iterator<Long> iterator = testSet.iterator(); iterator.hasNext(); ) {
            StoreRoleMenu newRoleMenu = new StoreRoleMenu();
            newRoleMenu.setRoleId(roleId);
            newRoleMenu.setMenuId(iterator.next());
            list.add(newRoleMenu);
        }
        storeRoleMenuService.insertSelectiveBatch(list);
    }

    /**
     * 商家角色与商家角色菜单中间表的跟新
     *
     * @param storeRole         商家角色
     * @param listMenuId 商家角色 与商家菜单的list
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAll(StoreRole storeRole, String[] listMenuId) {
        //把权限的1级2级3级都加入菜单中（进行转换）
        List menuIds = Arrays.asList(listMenuId);
        List<StoreMenu> storeMenus = storeMenuService.selectByIdIn(menuIds);
        Set<Long> testSet = new HashSet<Long>();
        if (!storeMenus.isEmpty()) {
            for (int i = 0; i < storeMenus.size(); i++) {
                String[] ps = null;
                String parentIds = storeMenus.get(i).getParentIds();
                if (StringUtils.isNotBlank(parentIds)) {
                    ps = parentIds.split(",");
                }
                for (int j = 0; j < ps.length; j++) {
                    if (!("0".equals(ps[j]) || "1".equals(ps[j]))) {
                        testSet.add(Long.parseLong(ps[j]));
                    }
                }
            }
            for (int i = 0; i < listMenuId.length; i++) {
                testSet.add(Long.parseLong(listMenuId[i]));
            }
        }
        //更新商家角色
        super.updateByIdSelective(storeRole);
        Long roleId = storeRole.getRoleId();
        //删除角色拥有旧的菜单
        StoreRoleMenu storeRoleMenu = new StoreRoleMenu();
        storeRoleMenu.setRoleId(roleId);
        storeRoleMenuService.deleteByWhere(new Wrapper(storeRoleMenu));
        //批量将角色id和新的菜单id存入中间表
        List<StoreRoleMenu> list = Lists.newArrayList();
        for (Iterator<Long> iterator = testSet.iterator(); iterator.hasNext(); ) {
            StoreRoleMenu newRoleMenu = new StoreRoleMenu();
            newRoleMenu.setRoleId(roleId);
            newRoleMenu.setMenuId(iterator.next());
            list.add(newRoleMenu);
        }
        storeRoleMenuService.insertSelectiveBatch(list);
    }

    /**
     * 删除角色
     *
     * @param storeRole
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(StoreRole storeRole) {
        //逻辑删除角色
        super.deleteById(storeRole.getRoleId());
        //删除角色拥有的菜单
        StoreRoleMenu storeRoleMenu = new StoreRoleMenu();
        storeRoleMenu.setRoleId(storeRole.getRoleId());
        storeRoleMenuService.deleteByWhere(new Wrapper(storeRoleMenu));
    }
}