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
package com.sicheng.admin.store.dao;

import com.sicheng.admin.store.entity.StoreCategory;
import com.sicheng.common.persistence.TreeDao;
import com.sicheng.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * 店铺商品分类DAO接口
 *
 * @author cl
 * @version 2017-02-13
 */
@MyBatisDao
public interface StoreCategoryDao extends TreeDao<StoreCategory> {

    //请在这里增加你自己的DAO层方法

    //14条单表操作的通用SQL调用方法都在父类中，全继承下来了，可直接使用。

    //同时继承了TreeDao

    int delete(StoreCategory storeCategory);

    List<StoreCategory> findList(StoreCategory storeCategory);

    StoreCategory get(Long id);
}