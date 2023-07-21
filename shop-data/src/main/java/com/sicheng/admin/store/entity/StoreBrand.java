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
package com.sicheng.admin.store.entity;

import com.sicheng.admin.product.dao.ProductBrandDao;
import com.sicheng.admin.product.entity.ProductBrand;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 店铺，品牌中间表 Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-06-02
 */
public class StoreBrand extends StoreBrandBase<StoreBrand> {

    private static final long serialVersionUID = 1L;

    public StoreBrand() {
        super();
    }

    public StoreBrand(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里
    private Store store;  //店铺

    public Store getStore() {
        if (store == null) {
            StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
            store = dao.selectById(this.getStoreId());
        }
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    private ProductBrand productBrand;  //品牌

    public ProductBrand getProductBrand() {
        if (productBrand == null) {
            ProductBrandDao dao = SpringContextHolder.getBean(ProductBrandDao.class);
            productBrand = dao.selectById(this.getBrandId());
        }
        return productBrand;
    }

    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
    }
}