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
package com.sicheng.admin.site.entity;

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 推荐位与一级商品类型关系表 Entity 父类
 *
 * @author 贺东泽
 * @version 2019-11-08
 */
public class SiteRecommendProductCategoryBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long categoryId;                // 商品类型id
    private Long recommendId;               // 推荐位表id
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SiteRecommendProductCategoryBase() {
        super();
    }

    public SiteRecommendProductCategoryBase(Long id) {
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
     * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter categoryId(商品类型id)
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * setter categoryId(商品类型id)
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * getter recommendId(推荐位表id)
     */
    public Long getRecommendId() {
        return recommendId;
    }

    /**
     * setter recommendId(推荐位表id)
     */
    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    /**
     * getter createDate(创建时间)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(创建时间)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(创建时间)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(创建时间)
     */
    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    /**
     * getter updateDate(更新时间)
     */
    public Date getBeginUpdateDate() {
        return beginUpdateDate;
    }

    /**
     * setter updateDate(更新时间)
     */
    public void setBeginUpdateDate(Date beginUpdateDate) {
        this.beginUpdateDate = beginUpdateDate;
    }

    /**
     * getter updateDate(更新时间)
     */
    public Date getEndUpdateDate() {
        return endUpdateDate;
    }

    /**
     * setter updateDate(更新时间)
     */
    public void setEndUpdateDate(Date endUpdateDate) {
        this.endUpdateDate = endUpdateDate;
    }

}