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

/**
 * 规格和规格值 Entity 父类
 *
 * @author zhaolei
 * @version 2017-02-07
 */
public class ProductSpecBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long specId;                    // 规格id
    private Long categoryId;                // 分类id，目前无用做为保留。-1表示未与任何分类关联的规格，是平台的通用规格
    private Integer specSort;               // 排序
    private String isColor;                 // 是否是颜色，颜色规格会上传不同的图片。0否，1是。
    private String name;                    // 规格名
    private String specValues;              // 规格值，多个值用逗号隔开
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

    public ProductSpecBase() {
        super();
    }

    public ProductSpecBase(Long id) {
        super(id);
        this.specId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return specId;
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
        this.specId = id;
    }

    /**
     * getter specId(规格id)
     */
    public Long getSpecId() {
        return specId;
    }

    /**
     * setter specId(规格id)
     */
    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    /**
     * getter categoryId(分类id，目前无用做为保留。-1表示未与任何分类关联的规格，是平台的通用规格)
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * setter categoryId(分类id，目前无用做为保留。-1表示未与任何分类关联的规格，是平台的通用规格)
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * getter specSort(排序)
     */
    public Integer getSpecSort() {
        return specSort;
    }

    /**
     * setter specSort(排序)
     */
    public void setSpecSort(Integer specSort) {
        this.specSort = specSort;
    }

    /**
     * getter isColor(是否是颜色，颜色规格会上传不同的图片。0否，1是。)
     */
    public String getIsColor() {
        return isColor;
    }

    /**
     * setter isColor(是否是颜色，颜色规格会上传不同的图片。0否，1是。)
     */
    public void setIsColor(String isColor) {
        this.isColor = isColor;
    }

    /**
     * getter name(规格名)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(规格名)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter specValues(规格值，多个值用逗号隔开)
     */
    public String getSpecValues() {
        return specValues;
    }

    /**
     * setter specValues(规格值，多个值用逗号隔开)
     */
    public void setSpecValues(String specValues) {
        this.specValues = specValues;
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
     * getter bak2(备用字段1)
     */
    public String getBak2() {
        return bak2;
    }

    /**
     * setter bak2(备用字段1)
     */
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    /**
     * getter bak3(备用字段1)
     */
    public String getBak3() {
        return bak3;
    }

    /**
     * setter bak3(备用字段1)
     */
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }

    /**
     * getter bak4(备用字段1)
     */
    public String getBak4() {
        return bak4;
    }

    /**
     * setter bak4(备用字段1)
     */
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }

    /**
     * getter bak5(备用字段1)
     */
    public String getBak5() {
        return bak5;
    }

    /**
     * setter bak5(备用字段1)
     */
    public void setBak5(String bak5) {
        this.bak5 = bak5;
    }


}