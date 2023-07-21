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
package com.sicheng.admin.product.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

/**
 * 参数和参数值 Entity 父类
 *
 * @author 赵磊
 * @version 2017-02-07
 */
public class ProductParamBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long paramId;                   // id
    private Long categoryId;                // 分类id
    private Integer paramSort;              // 排序
    private String name;                    // 参数名
    private String paramValues;             // 参数值文字，多个值用逗号隔开
    private String valuesImg;               // 参数值图片，多个值用逗号隔开values_img与values按下标对应
    private String type;                    // 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
    private String format;                  // 1图片、2文字、3文字+图片（颜色要配图片）
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
    private String isDisplay;               // 是否显示，0否1是
    private String isRequired;              // 是否必填，0否1是，商家发布商品的时候必填项必须填写

    public ProductParamBase() {
        super();
    }

    public ProductParamBase(Long id) {
        super(id);
        this.paramId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return paramId;
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
        this.paramId = id;
    }

    /**
     * getter paramId(id)
     */
    public Long getParamId() {
        return paramId;
    }

    /**
     * setter paramId(id)
     */
    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    /**
     * getter categoryId(分类id)
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * setter categoryId(分类id)
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * getter paramSort(排序)
     */
    public Integer getParamSort() {
        return paramSort;
    }

    /**
     * setter paramSort(排序)
     */
    public void setParamSort(Integer paramSort) {
        this.paramSort = paramSort;
    }

    /**
     * getter name(参数名)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(参数名)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter paramValues(参数值文字，多个值用逗号隔开)
     */
    public String getParamValues() {
        return paramValues;
    }

    /**
     * setter paramValues(参数值文字，多个值用逗号隔开)
     */
    public void setParamValues(String paramValues) {
        this.paramValues = paramValues;
    }

    /**
     * getter valuesImg(参数值图片，多个值用逗号隔开values_img与values按下标对应)
     */
    public String getValuesImg() {
        return valuesImg;
    }

    /**
     * setter valuesImg(参数值图片，多个值用逗号隔开values_img与values按下标对应)
     */
    public void setValuesImg(String valuesImg) {
        this.valuesImg = valuesImg;
    }

    /**
     * getter type(1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter format(1图片、2文字、3文字+图片（颜色要配图片）)
     */
    public String getFormat() {
        return format;
    }

    /**
     * setter format(1图片、2文字、3文字+图片（颜色要配图片）)
     */
    public void setFormat(String format) {
        this.format = format;
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
     * getter isDisplay(是否显示，0否1是)
     */
    public String getIsDisplay() {
        return isDisplay;
    }

    /**
     * setter isDisplay(是否显示，0否1是)
     */
    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    /**
     * getter isRequired(是否必填，0否1是，商家发布商品的时候必填项必须填写)
     */
    public String getIsRequired() {
        return isRequired;
    }

    /**
     * setter isRequired(是否必填，0否1是，商家发布商品的时候必填项必须填写)
     */
    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }


}