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


import com.sicheng.admin.store.dao.StoreCategoryDao;
import com.sicheng.admin.store.entity.StoreCategory;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.TreeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 店铺商品分类Service
 *
 * @author 蔡龙
 * @version 2017-02-13
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreCategoryService extends TreeService<StoreCategoryDao, StoreCategory> {

    //请在这里编写业务逻辑

    //TreeService父类中N个树结构的常用方法，已全部继承下来，可直接使用。

    //父父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    public List<StoreCategory> findList(StoreCategory storeCategory) {
        return dao.findList(storeCategory);
    }

    public StoreCategory get(Long id) {
        return dao.get(id);
    }

    /**
     * 商家后台--删除 特定节点的 所有节点（含自身节点、子节点、子子子节点、....）
     *
     * @param id 特定节点的ID
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(StoreCategory storeCategory) {
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.parent_ids like", "%," + storeCategory.getStoreCategoryId() + ",%");
        wrapper.or("a.store_category_id =", storeCategory.getStoreCategoryId());
        return super.deleteByWhere(wrapper);
    }

}