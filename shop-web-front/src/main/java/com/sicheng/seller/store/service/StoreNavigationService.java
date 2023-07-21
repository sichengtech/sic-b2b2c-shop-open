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
package com.sicheng.seller.store.service;

import com.sicheng.admin.store.dao.StoreNavigationDao;
import com.sicheng.admin.store.entity.StoreNavigation;
import com.sicheng.admin.store.entity.StoreNavigationDetail;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店铺导航 Service
 *
 * @author 蔡龙
 * @version 2017-02-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreNavigationService extends CrudService<StoreNavigationDao, StoreNavigation> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中


    /*
     * -------------------店铺导航内容的逻辑(需要店铺导航内容表的时候)start----------------------
     */

    @Autowired
    private StoreNavigationDetailService storeNavigationDetailService;

    /**
     * 商家中心-店铺导航新增
     */
    @Transactional(rollbackFor = Exception.class)
    public void insert(StoreNavigation storeNavigation, String content) {
        //新增店铺导航表数据
        super.insertSelective(storeNavigation);
        //新增店铺导航内容
        if (StringUtils.isNotBlank(content)) {
            StoreNavigationDetail storeNavigationDetail = new StoreNavigationDetail();
            storeNavigationDetail.setPkMode(1);
            storeNavigationDetail.setStoreNavigationId(storeNavigation.getStoreNavigationId());
            storeNavigationDetail.setContent(content);
            storeNavigationDetailService.insert(storeNavigationDetail);
        }
    }

    /**
     * 商家中心-店铺导航更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreNavigation storeNavigation, String content) {
        //更新店铺导航表数据
        super.updateByIdSelective(storeNavigation);
        //更新店铺导航内容
        if (StringUtils.isNotBlank(content)) {
            StoreNavigationDetail storeNavigationDetail = new StoreNavigationDetail();
            storeNavigationDetail.setPkMode(1);
            storeNavigationDetail.setStoreNavigationId(storeNavigation.getStoreNavigationId());
            storeNavigationDetail.setContent(content);
            storeNavigationDetailService.updateById(storeNavigationDetail);
        }
    }

    /**
     * 商家中心-店铺导航删除
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long storeNavigationId) {
        //删除店铺导航表数据
        super.deleteById(storeNavigationId);
        //删除店铺导航内容
        storeNavigationDetailService.deleteById(storeNavigationId);
    }

    /*
     * -------------------店铺导航内容的逻辑end----------------------
     */

}