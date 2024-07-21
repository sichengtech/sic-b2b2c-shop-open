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
package com.sicheng.admin.store.service;

import com.sicheng.admin.product.entity.SolrProduct;
import com.sicheng.admin.product.service.SolrProductService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sso.service.UserSellerService;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.search.ProductSearchInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺 Service
 *
 * @author cl
 * @version 2017-02-07
 */
@Service
@Lazy
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreService extends CrudService<StoreDao, Store> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private UserSellerService userSellerService;
    @Lazy
    @Autowired
    private StoreEnterAuthService storeEnterAuthService;
    @Autowired
    private StoreEnterService storeEnterService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private SolrProductService solrProductService;
    @Autowired
    private ProductSearchInterface productSearch;

    /**
     * 绑定佣金
     *
     * @param storeId    店铺id
     * @param commission 佣金
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void bindCommission(Long storeId, BigDecimal commission) {
        //修改店铺表
        Store store = new Store();
        store.setStoreId(storeId);
        store.setCommission(commission);
        super.updateByIdSelective(store);
        //获取uId
        UserSeller us = new UserSeller();
        us.setStoreId(storeId);
        List<UserSeller> userSellerList = userSellerService.selectByWhere(new Wrapper(us));
        if (!userSellerList.isEmpty()) {
            Long uId = userSellerList.get(0).getUId();
            //修改入驻审核表
            StoreEnterAuth storeEnterAuth = new StoreEnterAuth();
            storeEnterAuth.setEnterId(uId);
            storeEnterAuth.setCommission(commission);
            storeEnterAuthService.updateByIdSelective(storeEnterAuth);
            //修改入住查看表
            StoreEnter storeEnter = new StoreEnter();
            storeEnter.setEnterId(uId);
            storeEnter.setCommission(commission);
            storeEnterService.updateByIdSelective(storeEnter);
        }
    }

    /**
     * 关闭店铺
     */
    @Transactional(rollbackFor = Exception.class)
    public void closeStore(Store store, String isOpen) {
        //获取商家uId
        UserSeller userSeller = new UserSeller();
        userSeller.setStoreId(store.getStoreId());
        List<UserSeller> userSellerList = userSellerService.selectByWhere(new Wrapper(userSeller));
        if (!userSellerList.isEmpty()) {
            Long uId = userSellerList.get(0).getUId();
            //修改入驻申请(审核表)
            StoreEnterAuth storeEnterAuth = new StoreEnterAuth();
            storeEnterAuth.setStoreName(store.getName());
            storeEnterAuth.setEnterId(uId);
            storeEnterAuthService.updateByIdSelective(storeEnterAuth);
            //修改入驻申请(查看表)
            StoreEnter storeEnter = new StoreEnter();
            storeEnter.setStoreName(store.getName());
            storeEnter.setEnterId(uId);
            storeEnterService.updateByIdSelective(storeEnter);
            //修改店铺表
            this.updateByIdSelective(store);
            //修改店铺
            if ("0".equals(isOpen)) {
                //锁定会员表
                UserMain userMain = new UserMain();
                userMain.setUId(uId);
                userMain.setIsLocked("1");//是否锁定(0否，1是)锁定后不能登录
                userMainService.updateByIdSelective(userMain);
                //锁定子账号
                UserMain userChild = new UserMain();
                userChild.setParentUid(userMain.getUId());
                UserMain userChild1 = new UserMain();
                userChild1.setIsLocked("1");//是否锁定(0否，1是)锁定后不能登录
                userMainService.updateByWhereSelective(userChild1, new Wrapper(userChild));
                //根据店铺id删除商品solr索引
                productSearch.deleteDocByStoreId(store.getStoreId());
            }
            if ("1".equals(isOpen)) {
                //开启会员表
                UserMain userMain = new UserMain();
                userMain.setUId(uId);
                userMain.setIsLocked("0");//是否锁定(0否，1是)锁定后不能登录
                userMainService.updateByIdSelective(userMain);
                //开启子账号
                UserMain userChild = new UserMain();
                userChild.setParentUid(userMain.getUId());
                UserMain userChild1 = new UserMain();
                userChild1.setIsLocked("0");//是否锁定(0否，1是)锁定后不能登录
                userMainService.updateByWhereSelective(userChild1, new Wrapper(userChild));
                //根据店铺id查询商品，并生成solr索引
                List<SolrProduct> solrProducts = solrProductService.selectByWhere(new Wrapper().and("store_id=", store.getStoreId()));
                productSearch.addDocList(solrProducts);
            }
        }
    }
}