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
package com.sicheng.admin.product.entity;


import com.sicheng.common.persistence.DataEntity;

/**
 * 商品详情 Entity 父类
 *
 * @author zhaolei
 * @version 2017-02-07
 */
public class ProductDetailBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long pId;                       // 商品ID
    private String introduction;            // 商品描述详情,pc版
    private String introduction2;           // 商品描述详情，移动版

    public ProductDetailBase() {
        super();
    }

    public ProductDetailBase(Long id) {
        super(id);
        this.pId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return pId;
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
        this.pId = id;
    }

    /**
     * getter pId(商品ID)
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(商品ID)
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter introduction(商品描述详情,pc版)
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * setter introduction(商品描述详情,pc版)
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * getter introduction2(商品描述详情，移动版)
     */
    public String getIntroduction2() {
        return introduction2;
    }

    /**
     * setter introduction2(商品描述详情，移动版)
     */
    public void setIntroduction2(String introduction2) {
        this.introduction2 = introduction2;
    }


}