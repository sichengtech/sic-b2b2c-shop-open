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
package com.sicheng.admin.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.product.dao.SolrProductDao;
import com.sicheng.admin.product.entity.SolrProduct;
import com.sicheng.admin.store.dao.StoreCustomerServiceDao;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreCustomerService;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 收藏店铺 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-07
 */
public class MemberCollectionStore extends MemberCollectionStoreBase<MemberCollectionStore> {

    private static final long serialVersionUID = 1L;

    public MemberCollectionStore() {
        super();
    }

    public MemberCollectionStore(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里
    //一对一映射
    private Store store;

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

    //一对多映射
    @JsonIgnore
    private List<SolrProduct> solrProductList;//一个收藏店铺对应多个商品

    public List<SolrProduct> getSolrProductList() {
        if (solrProductList == null) {
            SolrProductDao dao = SpringContextHolder.getBean(SolrProductDao.class);
            solrProductList = dao.selectByWhere(null, new Wrapper().and("a.store_id=", this.getStoreId()));
        }
        return solrProductList;
    }

    public void setSolrProductList(List<SolrProduct> solrProductList) {
        this.solrProductList = solrProductList;
    }

    //一对多映射
    @JsonIgnore
    private List<StoreCustomerService> storeCustomerServiceList;//一个收藏店铺对应多个客服

    public List<StoreCustomerService> getStoreCustomerServiceList() {
        if (storeCustomerServiceList == null) {
            StoreCustomerServiceDao dao = SpringContextHolder.getBean(StoreCustomerServiceDao.class);
            storeCustomerServiceList = dao.selectByWhere(null, new Wrapper().and("a.store_id=", this.getStoreId()));
        }
        return storeCustomerServiceList;
    }

    public void setStoreCustomerServiceList(List<StoreCustomerService> storeCustomerServiceList) {
        this.storeCustomerServiceList = storeCustomerServiceList;
    }


}