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
 * 店铺导航表内容详情 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-02-20
 */
public class StoreNavigationDetailBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long storeNavigationId;         // 主键（和店铺导航id一样）
    private String content;                 // 店铺导航内容

    public StoreNavigationDetailBase() {
        super();
    }

    public StoreNavigationDetailBase(Long id) {
        super(id);
        this.storeNavigationId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return storeNavigationId;
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
        this.storeNavigationId = id;
    }

    /**
     * getter storeNavigationId(主键（和店铺导航id一样）)
     */
    public Long getStoreNavigationId() {
        return storeNavigationId;
    }

    /**
     * setter storeNavigationId(主键（和店铺导航id一样）)
     */
    public void setStoreNavigationId(Long storeNavigationId) {
        this.storeNavigationId = storeNavigationId;
    }

    /**
     * getter content(店铺导航内容)
     */
    public String getContent() {
        return content;
    }

    /**
     * setter content(店铺导航内容)
     */
    public void setContent(String content) {
        this.content = content;
    }


}