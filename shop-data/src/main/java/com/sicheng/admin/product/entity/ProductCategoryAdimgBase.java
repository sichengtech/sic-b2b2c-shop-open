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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 分类导航广告图 Entity 父类
 *
 * @author zhaolei
 * @version 2017-02-07
 */
public class ProductCategoryAdimgBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long navImgId;                  // 主键
    private Long categoryId;                // 关联(分类表)
    private String imgPath;                 // 图片地址
    private String originalSize;            // 原图尺寸
    private Integer sort;                   // 排序
    private Integer action;                 // 动作，字典
    private String url;                     // 目标：id,目标链接
    private String target;                  // 窗口打开方式（1mainFrame、2 _blank、3_self、4_parent、5_top）
    @JsonIgnore
    private String bak1;                    // 备用字段1
    @JsonIgnore
    private String bak2;                    // 备用字段2
    @JsonIgnore
    private String bak3;                    // 备用字段3
    @JsonIgnore
    private String bak4;                    // 备用字段4
    @JsonIgnore
    private String bak5;                    // 备用字段5
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public ProductCategoryAdimgBase() {
        super();
    }

    public ProductCategoryAdimgBase(Long id) {
        super(id);
        this.navImgId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return navImgId;
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
        this.navImgId = id;
    }

    /**
     * getter navImgId(主键)
     */
    public Long getNavImgId() {
        return navImgId;
    }

    /**
     * setter navImgId(主键)
     */
    public void setNavImgId(Long navImgId) {
        this.navImgId = navImgId;
    }

    /**
     * getter categoryId(关联(分类表))
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * setter categoryId(关联(分类表))
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * getter imgPath(图片地址)
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * setter imgPath(图片地址)
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * getter originalSize(原图尺寸)
     */
    public String getOriginalSize() {
        return originalSize;
    }

    /**
     * setter originalSize(原图尺寸)
     */
    public void setOriginalSize(String originalSize) {
        this.originalSize = originalSize;
    }

    /**
     * getter sort(排序)
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * setter sort(排序)
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * getter action(动作，字典)
     */
    public Integer getAction() {
        return action;
    }

    /**
     * setter action(动作，字典)
     */
    public void setAction(Integer action) {
        this.action = action;
    }

    /**
     * getter url(目标：id,目标链接)
     */
    public String getUrl() {
        return url;
    }

    /**
     * setter url(目标：id,目标链接)
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * getter target(窗口打开方式（1mainFrame、2 _blank、3_self、4_parent、5_top）)
     */
    public String getTarget() {
        return target;
    }

    /**
     * setter target(窗口打开方式（1mainFrame、2 _blank、3_self、4_parent、5_top）)
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * getter bak1(备用字段1)
     */
    public String getBak1() {
        return bak1;
    }

    /**
     * setter bak1(备用字段1)
     */
    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    /**
     * getter bak2(备用字段2)
     */
    public String getBak2() {
        return bak2;
    }

    /**
     * setter bak2(备用字段2)
     */
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    /**
     * getter bak3(备用字段3)
     */
    public String getBak3() {
        return bak3;
    }

    /**
     * setter bak3(备用字段3)
     */
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }

    /**
     * getter bak4(备用字段4)
     */
    public String getBak4() {
        return bak4;
    }

    /**
     * setter bak4(备用字段4)
     */
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }

    /**
     * getter bak5(备用字段5)
     */
    public String getBak5() {
        return bak5;
    }

    /**
     * setter bak5(备用字段5)
     */
    public void setBak5(String bak5) {
        this.bak5 = bak5;
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