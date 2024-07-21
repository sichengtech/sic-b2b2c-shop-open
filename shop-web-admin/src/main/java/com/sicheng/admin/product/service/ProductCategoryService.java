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
package com.sicheng.admin.product.service;


import com.sicheng.admin.product.dao.ProductCategoryDao;
import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.TreeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品分类Service
 *
 * @author cl
 * @version 2017-02-14
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProductCategoryService extends TreeService<ProductCategoryDao, ProductCategory> {

    //请在这里编写业务逻辑

    //TreeService父类中N个树结构的常用方法，已全部继承下来，可直接使用。

    //父父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    public List<ProductCategory> findList(ProductCategory productCategory) {
        return dao.findList(productCategory);
    }

    public ProductCategory get(Long id) {
        return dao.get(id);
    }

    /**
     * 删除 特定节点的 所有节点（含自身节点、子节点、子子子节点、....）
     *
     * @param id 特定节点的ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.parent_ids like", "%," + id + ",%");
        wrapper.or("a.category_id =", id);
        return super.deleteByWhere(wrapper);
    }
}