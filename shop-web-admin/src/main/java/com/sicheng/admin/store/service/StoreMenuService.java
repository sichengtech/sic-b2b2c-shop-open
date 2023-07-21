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
package com.sicheng.admin.store.service;


import com.sicheng.admin.store.dao.StoreMenuDao;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.service.TreeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 店铺菜单Service
 *
 * @author 蔡龙
 * @version 2017-02-13
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreMenuService extends TreeService<StoreMenuDao, StoreMenu> {

    //请在这里编写业务逻辑

    //TreeService父类中N个树结构的常用方法，已全部继承下来，可直接使用。

    //父父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    public StoreMenu get(Long id) {
        return dao.get(id);
    }

    public List<StoreMenu> findList(StoreMenu storeMenu) {
        return dao.findList(storeMenu);
    }

    @Transactional(readOnly = false)
    public void delete(StoreMenu storeMenu) {
        dao.delete(storeMenu);
    }

    /**
     * 删除自身及所有子节点数据
     *
     * @param menuId 店铺菜单id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteMyAll(Long menuId) {
        //删除自身数据
        super.deleteById(menuId);
        //删除 特定节点的 所有子节点
        super.deleteChildNodeAll(menuId);
    }

}