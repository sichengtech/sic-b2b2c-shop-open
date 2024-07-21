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
 * 品牌 Entity 父类
 *
 * @author cl
 * @version 2017-04-11
 */
public class ProductBrandBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long brandId;                   // id
    private String name;                    // 品牌名称
    private String firstLetter;             // 首页字母
    private String englishName;             // 英文名称
    private String logo;                    // logo图片的path
    private String displayMode;             // 展示方式，1文字、2图片
    private String recommend;               // 推荐，0未推荐，1推荐
    private String type;                    // 类型
    private Integer sort;                   // 排序
    private String status;                  // 审核状态，0待审、1通过、2未通过
    private String url;                     // 网址
    private String introduction;            // 品牌介绍，品牌数量少clob不单独提成一张表
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
    private String cause;                   // 审核未通过原因
    private String applyNumber;             // 注册/申请号
    private String applyPathP1;             // 注册证/受理书，图1
    private String applyPathP2;             // 注册证/受理书，图2
    private String brandOwner;              // 品牌所有者
    private Long storeId;                   // 谁申请的品牌
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public ProductBrandBase() {
        super();
    }

    public ProductBrandBase(Long id) {
        super(id);
        this.brandId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return brandId;
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
        this.brandId = id;
    }

    /**
     * getter brandId(id)
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * setter brandId(id)
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * getter name(品牌名称)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(品牌名称)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter firstLetter(首页字母)
     */
    public String getFirstLetter() {
        return firstLetter;
    }

    /**
     * setter firstLetter(首页字母)
     */
    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    /**
     * getter englishName(英文名称)
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * setter englishName(英文名称)
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    /**
     * getter logo(logo图片的path)
     */
    public String getLogo() {
        return logo;
    }

    /**
     * setter logo(logo图片的path)
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * getter displayMode(展示方式，1文字、2图片)
     */
    public String getDisplayMode() {
        return displayMode;
    }

    /**
     * setter displayMode(展示方式，1文字、2图片)
     */
    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    /**
     * getter recommend(推荐，0未推荐，1推荐)
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * setter recommend(推荐，0未推荐，1推荐)
     */
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    /**
     * getter type(类型)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(类型)
     */
    public void setType(String type) {
        this.type = type;
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
     * getter status(审核状态，0待审、1通过、2未通过)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(审核状态，0待审、1通过、2未通过)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter url(网址)
     */
    public String getUrl() {
        return url;
    }

    /**
     * setter url(网址)
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * getter introduction(品牌介绍，品牌数量少clob不单独提成一张表)
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * setter introduction(品牌介绍，品牌数量少clob不单独提成一张表)
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
     * getter cause(审核未通过原因)
     */
    public String getCause() {
        return cause;
    }

    /**
     * setter cause(审核未通过原因)
     */
    public void setCause(String cause) {
        this.cause = cause;
    }

    /**
     * getter applyNumber(注册/申请号)
     */
    public String getApplyNumber() {
        return applyNumber;
    }

    /**
     * setter applyNumber(注册/申请号)
     */
    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    /**
     * getter applyPathP1(注册证/受理书，图1)
     */
    public String getApplyPathP1() {
        return applyPathP1;
    }

    /**
     * setter applyPathP1(注册证/受理书，图1)
     */
    public void setApplyPathP1(String applyPathP1) {
        this.applyPathP1 = applyPathP1;
    }

    /**
     * getter applyPathP2(注册证/受理书，图2)
     */
    public String getApplyPathP2() {
        return applyPathP2;
    }

    /**
     * setter applyPathP2(注册证/受理书，图2)
     */
    public void setApplyPathP2(String applyPathP2) {
        this.applyPathP2 = applyPathP2;
    }

    /**
     * getter brandOwner(品牌所有者)
     */
    public String getBrandOwner() {
        return brandOwner;
    }

    /**
     * setter brandOwner(品牌所有者)
     */
    public void setBrandOwner(String brandOwner) {
        this.brandOwner = brandOwner;
    }

    /**
     * getter storeId(谁申请的品牌)
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * setter storeId(谁申请的品牌)
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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