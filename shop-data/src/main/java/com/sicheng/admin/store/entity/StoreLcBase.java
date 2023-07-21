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


import com.sicheng.common.persistence.DataEntity;

/**
 * 管理店铺添加的物流公司 Entity 父类
 *
 * @author 张加利
 * @version 2017-10-25
 */
public class StoreLcBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long storeId;                   // 店铺id
    private Long lcId;                      // 物流公司id

    public StoreLcBase() {
        super();
    }

    public StoreLcBase(Long id) {
        super(id);
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * 描述: 设置ID
     *
     * @param id
     * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter storeId(店铺id)
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * setter storeId(店铺id)
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * getter lcId(物流公司id)
     */
    public Long getLcId() {
        return lcId;
    }

    /**
     * setter lcId(物流公司id)
     */
    public void setLcId(Long lcId) {
        this.lcId = lcId;
    }


}