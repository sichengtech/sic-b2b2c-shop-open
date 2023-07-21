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
package com.sicheng.admin.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.store.dao.StoreBrandDao;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreBrand;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 品牌 Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-02-07
 */
public class ProductBrand extends ProductBrandBase<ProductBrand> {

    private static final long serialVersionUID = 1L;

    public ProductBrand() {
        super();
    }

    public ProductBrand(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里
    @JsonIgnore
    private Store store;               //店铺

    //一对一映射
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

    @JsonIgnore
    private StoreBrand storeBrand;        //获取店铺与品牌的对应关系

    public StoreBrand getStoreBrand() {
        StoreBrandDao dao = SpringContextHolder.getBean(StoreBrandDao.class);
        StoreBrand storeBrand = new StoreBrand();
        storeBrand.setBrandId(this.getBrandId());
        List<StoreBrand> list = dao.selectByWhere(null, new Wrapper(storeBrand));
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public void setStoreBrand(StoreBrand storeBrand) {
        this.storeBrand = storeBrand;
    }

}