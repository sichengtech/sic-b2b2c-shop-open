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
package com.sicheng.admin.store.service;

import com.sicheng.admin.store.dao.StoreBrandDao;
import com.sicheng.admin.store.entity.StoreBrand;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺，品牌中间表 Service
 *
 * @author 蔡龙
 * @version 2017-06-02
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreBrandService extends CrudService<StoreBrandDao, StoreBrand> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中


    /**
     * 绑定店铺品牌
     *
     * @param storeId  店铺id
     * @param brandIds 品牌ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(Long storeId, String brandIds) {
        //删除绑定品牌
        StoreBrand sb = new StoreBrand();
        sb.setStoreId(storeId);
        this.deleteByWhere(new Wrapper(sb));
        //新增绑定品牌
        String[] brandIdss = brandIds.split(",");
        List<StoreBrand> storeBrandList = new ArrayList<StoreBrand>();
        for (int i = 0; i < brandIdss.length; i++) {
            StoreBrand storeBrand = new StoreBrand();
            storeBrand.setStoreId(storeId);
            storeBrand.setBrandId(Long.parseLong(brandIdss[i]));
            storeBrandList.add(storeBrand);
        }
        this.insertBatch(storeBrandList);
    }

}