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
package com.sicheng.admin.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.product.dao.ProductSpuDao;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 店铺视图 Entity 子类，请把你的业务代码写在这里
 *
 * @author hdz
 * @version 2019-11-12
 */
public class SolrStore extends SolrStoreBase<SolrStore> {
    private static final long serialVersionUID = 1L;

    public SolrStore() {
        super();
    }

    public SolrStore(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    //该店铺最新的4个商品
    @JsonIgnore
    private List<ProductSpu> fourProduct;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<ProductSpu> getFourProduct() {
        if (fourProduct == null && this.getStoreId() != null) {
            ProductSpuDao productSpuDao = SpringContextHolder.getBean(ProductSpuDao.class);
            this.fourProduct = productSpuDao.selectFourProductByStoreId(this.getStoreId());
        }
        return fourProduct;
    }

    public void setFourProduct(List<ProductSpu> fourProduct) {
        this.fourProduct = fourProduct;
    }

    @Override
    public Integer getAllSales() {
        if (super.getAllSales() == null) {
            return 0;
        }
        return super.getAllSales();
    }

    @Override
    public String getProductScore() {
        if (super.getProductScore() == null) {
            return "5";
        }
        return super.getProductScore();
    }

    @Override
    public String getLogisticsScore() {
        if (super.getLogisticsScore() == null) {
            return "5";
        }
        return super.getLogisticsScore();
    }

    @Override
    public String getServiceAttitudeScore() {
        if(super.getServiceAttitudeScore() == null){
            return "5";
        }
        return super.getServiceAttitudeScore();
    }
}