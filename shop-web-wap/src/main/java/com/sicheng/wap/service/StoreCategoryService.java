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
package com.sicheng.wap.service;


import com.sicheng.admin.store.dao.StoreCategoryDao;
import com.sicheng.admin.store.entity.StoreCategory;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.TreeService;
import com.sicheng.common.utils4m.AppDataUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询一级店铺分类
     *
     * @param storeId
     * @return
     */
    public Map<String, Object> storeCategoryList(String storeId) {
        //查询
        Wrapper wrapper = new Wrapper();
        StoreCategory storeCategory = new StoreCategory();
        storeCategory.setStoreId(Long.parseLong(storeId));
        wrapper.setEntity(storeCategory);
        wrapper.and("parent_id = ", "0");
        wrapper.orderBy("a.sort ASC");
        List<StoreCategory> storeCategoryList = this.selectByWhere(wrapper);
        for (StoreCategory buffer : storeCategoryList) {
            buffer.setCreateDate(null);
            buffer.setUpdateDate(null);
            buffer.setIsOpen(null);
            buffer.setSort(null);
        }
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, "店铺商品分类获取成功", storeCategoryList, null);
    }
}