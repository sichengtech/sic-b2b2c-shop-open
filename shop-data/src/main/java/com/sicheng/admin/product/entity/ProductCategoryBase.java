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
import com.sicheng.common.persistence.TreeEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品分类 Entity 父类
 *
 * @author cl
 * @version 2017-02-14
 */
public class ProductCategoryBase<T> extends TreeEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long categoryId;                // 主键
    private String categoryImg;             // 分类导航图标（只有在层级为0的情况下有图片地址）
    private Integer cateLevel;              // 类别层级
    private String type;                    // 类型：1物理分类、2超连接分类
    private String isLocked;                // 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
    private BigDecimal commission;          // 分佣比例
    private String recommendSpu;            // 推荐SPU（为商品id，多个值用豆号分隔）
    private String brandIds;                // 绑定的品牌（为品牌id，多个值用豆号分隔）

    private String bak1;                    // 备用字段1，暂且用于存放分类图标
    @JsonIgnore
    private String bak2;                    // 备用字段2
    @JsonIgnore
    private String bak3;                    // 备用字段3
    @JsonIgnore
    private String bak4;                    // 备用字段4
    @JsonIgnore
    private String bak5;                    // 备用字段5
    @JsonIgnore
    private String bak6;                    // 备用字段6
    @JsonIgnore
    private String bak7;                    // 备用字段7
    @JsonIgnore
    private String bak8;                    // 备用字段8
    @JsonIgnore
    private String bak9;                    // 备用字段9
    @JsonIgnore
    private String bak10;                   // 备用字段10
    private String firstLetter;             // 分类名称首字母
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public ProductCategoryBase() {
        super();
    }

    public ProductCategoryBase(Long id) {
        super(id);
        this.categoryId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return categoryId;
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
        this.categoryId = id;
    }

    /**
     * getter categoryId(主键)
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * setter categoryId(主键)
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * getter categoryImg(分类导航图标（只有在层级为0的情况下有图片地址）)
     */
    public String getCategoryImg() {
        return categoryImg;
    }

    /**
     * setter categoryImg(分类导航图标（只有在层级为0的情况下有图片地址）)
     */
    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    /**
     * getter cateLevel(类别层级)
     */
    public Integer getCateLevel() {
        return cateLevel;
    }

    /**
     * setter cateLevel(类别层级)
     */
    public void setCateLevel(Integer cateLevel) {
        this.cateLevel = cateLevel;
    }

    /**
     * getter type(类型：1物理分类、2超连接分类)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(类型：1物理分类、2超连接分类)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter isLocked(是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品)
     */
    public String getIsLocked() {
        return isLocked;
    }

    /**
     * setter isLocked(是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品)
     */
    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * getter commission(分佣比例)
     */
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * setter commission(分佣比例)
     */
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    /**
     * getter recommendSpu(推荐SPU（为商品id，多个值用豆号分隔）)
     */
    public String getRecommendSpu() {
        return recommendSpu;
    }

    /**
     * setter recommendSpu(推荐SPU（为商品id，多个值用豆号分隔）)
     */
    public void setRecommendSpu(String recommendSpu) {
        this.recommendSpu = recommendSpu;
    }

    /**
     * getter brandIds(绑定的品牌（为品牌id，多个值用豆号分隔）)
     */
    public String getBrandIds() {
        return brandIds;
    }

    /**
     * setter brandIds(绑定的品牌（为品牌id，多个值用豆号分隔）)
     */
    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
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
     * getter bak6(备用字段6)
     */
    public String getBak6() {
        return bak6;
    }

    /**
     * setter bak6(备用字段6)
     */
    public void setBak6(String bak6) {
        this.bak6 = bak6;
    }

    /**
     * getter bak7(备用字段7)
     */
    public String getBak7() {
        return bak7;
    }

    /**
     * setter bak7(备用字段7)
     */
    public void setBak7(String bak7) {
        this.bak7 = bak7;
    }

    /**
     * getter bak8(备用字段8)
     */
    public String getBak8() {
        return bak8;
    }

    /**
     * setter bak8(备用字段8)
     */
    public void setBak8(String bak8) {
        this.bak8 = bak8;
    }

    /**
     * getter bak9(备用字段9)
     */
    public String getBak9() {
        return bak9;
    }

    /**
     * setter bak9(备用字段9)
     */
    public void setBak9(String bak9) {
        this.bak9 = bak9;
    }

    /**
     * getter bak10(备用字段10)
     */
    public String getBak10() {
        return bak10;
    }

    /**
     * setter bak10(备用字段10)
     */
    public void setBak10(String bak10) {
        this.bak10 = bak10;
    }

    /**
     * getter firstLetter(分类名称首字母)
     */
    public String getFirstLetter() {
        return firstLetter;
    }

    /**
     * setter firstLetter(分类名称首字母)
     */
    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
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