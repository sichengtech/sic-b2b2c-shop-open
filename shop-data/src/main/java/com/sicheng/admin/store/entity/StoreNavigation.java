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

import com.sicheng.admin.store.dao.StoreNavigationDetailDao;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 店铺导航 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-06
 */
public class StoreNavigation extends StoreNavigationBase<StoreNavigation> {

    private static final long serialVersionUID = 1L;

    public StoreNavigation() {
        super();
    }

    public StoreNavigation(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    private StoreNavigationDetail storeNavigationDetail;      //店铺导航内容

    //一对一映射
    public StoreNavigationDetail getStoreNavigationDetail() {
        if (storeNavigationDetail == null) {
            StoreNavigationDetailDao dao = SpringContextHolder.getBean(StoreNavigationDetailDao.class);
            storeNavigationDetail = dao.selectById(this.getStoreNavigationId());
        }
        return storeNavigationDetail;
    }

    public void setStoreNavigationDetail(StoreNavigationDetail storeNavigationDetail) {
        this.storeNavigationDetail = storeNavigationDetail;
    }


}