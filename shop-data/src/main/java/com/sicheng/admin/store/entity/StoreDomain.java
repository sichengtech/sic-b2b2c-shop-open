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

import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 店铺二级域名 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-07
 */
public class StoreDomain extends StoreDomainBase<StoreDomain> {

    private static final long serialVersionUID = 1L;

    public StoreDomain() {
        super();
    }

    public StoreDomain(Long id) {
        super(id);
    }

    //一对一映射
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

    //ListIdIn工具  在一个list中做 一对一
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<StoreDomain> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> Storelist = dao.selectByIdIn(ids);
        fill(Storelist, "storeId", list, "storeId", "store");//循环填充
    }

}