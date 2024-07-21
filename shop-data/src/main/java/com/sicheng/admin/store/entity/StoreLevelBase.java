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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺等级 Entity 父类
 *
 * @author cl
 * @version 2017-02-07
 */
public class StoreLevelBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long levelId;                   // 主键
    private String name;                    // 等级名称
    private Integer recommendProductCount;  // 可推荐商品数
    private Integer releaseProcuctCount;    // 可发布商品数
    private Integer pictureSpace;           // 图片空间容量,单位byte
    private BigDecimal money;               // 收费标准/年
    private String applicationNote;         // 申请说明
    private Integer sort;                   // 排序
    private String isOpen;                  // 是否可用(0关闭，1开启)
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

    public StoreLevelBase() {
        super();
    }

    public StoreLevelBase(Long id) {
        super(id);
        this.levelId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return levelId;
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
        this.levelId = id;
    }

    /**
     * getter levelId(主键)
     */
    public Long getLevelId() {
        return levelId;
    }

    /**
     * setter levelId(主键)
     */
    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    /**
     * getter name(等级名称)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(等级名称)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter recommendProductCount(可推荐商品数)
     */
    public Integer getRecommendProductCount() {
        return recommendProductCount;
    }

    /**
     * setter recommendProductCount(可推荐商品数)
     */
    public void setRecommendProductCount(Integer recommendProductCount) {
        this.recommendProductCount = recommendProductCount;
    }

    /**
     * getter releaseProcuctCount(可发布商品数)
     */
    public Integer getReleaseProcuctCount() {
        return releaseProcuctCount;
    }

    /**
     * setter releaseProcuctCount(可发布商品数)
     */
    public void setReleaseProcuctCount(Integer releaseProcuctCount) {
        this.releaseProcuctCount = releaseProcuctCount;
    }

    /**
     * getter pictureSpace(图片空间容量,单位byte)
     */
    public Integer getPictureSpace() {
        return pictureSpace;
    }

    /**
     * setter pictureSpace(图片空间容量,单位byte)
     */
    public void setPictureSpace(Integer pictureSpace) {
        this.pictureSpace = pictureSpace;
    }

    /**
     * getter money(收费标准/年)
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * setter money(收费标准/年)
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * getter applicationNote(申请说明)
     */
    public String getApplicationNote() {
        return applicationNote;
    }

    /**
     * setter applicationNote(申请说明)
     */
    public void setApplicationNote(String applicationNote) {
        this.applicationNote = applicationNote;
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
     * getter isOpen(是否可用(0关闭，1开启))
     */
    public String getIsOpen() {
        return isOpen;
    }

    /**
     * setter isOpen(是否可用(0关闭，1开启))
     */
    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
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