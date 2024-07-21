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
 * 商品与参数中间表 Entity 父类
 *
 * @author zhaolei
 * @version 2017-10-25
 */
public class ProductParamMappingBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long pId;                       // 商品id
    private Integer sort;                   // 排序
    private String type;                    // 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件
    private String format;                  // 1图片、2文字、3文字+图片（颜色要配图片）
    private Long paramId;                   // 参数ID
    private String name;                    // 参数名。做冗余，参数表中的参数被删除后也不影响商品参数的正常显示
    private String value;                   // 参数值。做冗余，参数表中的参数被删除后也不影响商品参数的正常显示
    private String valueImg;                // 参数值的图片。做冗余，参数表中的参数被删除后也不影响商品参数的正常显示
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

    public ProductParamMappingBase() {
        super();
    }

    public ProductParamMappingBase(Long id) {
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
     * getter pId(商品id)
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(商品id)
     */
    public void setPId(Long pId) {
        this.pId = pId;
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
     * getter type(1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件)
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
     * getter paramId(参数ID)
     */
    public Long getParamId() {
        return paramId;
    }

    /**
     * setter paramId(参数ID)
     */
    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    /**
     * getter name(参数名。做冗余，参数表中的参数被删除后也不影响商品参数的正常显示)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(参数名。做冗余，参数表中的参数被删除后也不影响商品参数的正常显示)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter value(参数值。做冗余，参数表中的参数被删除后也不影响商品参数的正常显示)
     */
    public String getValue() {
        return value;
    }

    /**
     * setter value(参数值。做冗余，参数表中的参数被删除后也不影响商品参数的正常显示)
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * getter valueImg(参数值的图片。做冗余，参数表中的参数被删除后也不影响商品参数的正常显示)
     */
    public String getValueImg() {
        return valueImg;
    }

    /**
     * setter valueImg(参数值的图片。做冗余，参数表中的参数被删除后也不影响商品参数的正常显示)
     */
    public void setValueImg(String valueImg) {
        this.valueImg = valueImg;
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


}