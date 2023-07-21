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

/**
 * 商品分类参数添加,更新,删除
 *
 * @author 蔡龙
 * @version 2017-01-22
 */
public class ProductParamVo {

    private Long categoryId;//商品分类
    private String[] paramId;//商品参数
    private String[] name;//商品参数名称
    private String[] paramValues;//商品参数值
    private String[] paramSort;//商品排序
    private String[] isRequired;//商品参数是否必填
    private String[] isDisplay;//商品是否显示

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId2) {
        this.categoryId = categoryId2;
    }

    public String[] getParamId() {
        return paramId;
    }

    public void setParamId(String[] paramId) {
        this.paramId = paramId;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getParamValues() {
        return paramValues;
    }

    public void setParamValues(String[] paramValues) {
        this.paramValues = paramValues;
    }

    public String[] getParamSort() {
        return paramSort;
    }

    public void setParamSort(String[] paramSort) {
        this.paramSort = paramSort;
    }

    public String[] getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String[] isRequired) {
        this.isRequired = isRequired;
    }

    public String[] getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String[] isDisplay) {
        this.isDisplay = isDisplay;
    }
}
