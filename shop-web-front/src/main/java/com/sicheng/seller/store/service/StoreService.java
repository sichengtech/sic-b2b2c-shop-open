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

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.common.service.CrudService;
import com.sicheng.sso.utils.SsoUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店铺 Service
 *
 * @author cl
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreService extends CrudService<StoreDao, Store> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private StoreEnterService storeEnterService;

    @Autowired
    private StoreEnterAuthService storeEnterAuthService;

    /**
     * 商家中心-更新店铺设置
     *
     * @param store
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Store store) {
    	UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        store.setStoreId(userSeller.getStoreId());
        //更新店铺表中的数据
        dao.updateByIdSelective(store);
        //更新入驻申请（查看表的数据）
        StoreEnter storeEnter = new StoreEnter();
        storeEnter.setEnterId(userSeller.getUId());
        storeEnter.setStoreName(store.getName());
        storeEnterService.updateByIdSelective(storeEnter);
        //更新入驻申请（审核表中的数据）
        StoreEnterAuth storeEnterAuth = new StoreEnterAuth();
        storeEnterAuth.setEnterId(userSeller.getUId());
        storeEnterAuth.setStoreName(store.getName());
        storeEnterAuthService.updateByIdSelective(storeEnterAuth);
    }

}