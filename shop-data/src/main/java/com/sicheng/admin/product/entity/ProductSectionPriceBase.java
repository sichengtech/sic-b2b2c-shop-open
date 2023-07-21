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

import java.math.BigDecimal;

/**
 * 商品区间价 Entity 父类
 *
 * @author 赵磊
 * @version 2017-04-06
 */
public class ProductSectionPriceBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long scId;                      // 主键
    private Long pId;                       // 商品ID
    private String section;                 // 区间，1表示1以上，10表示10以上
    private BigDecimal price;               // 价格,批发模式使用本价格
    private Integer sort;                   // 排序，排序从1起
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

    public ProductSectionPriceBase() {
        super();
    }

    public ProductSectionPriceBase(Long id) {
        super(id);
        this.scId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return scId;
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
        this.scId = id;
    }

    /**
     * getter scId(主键)
     */
    public Long getScId() {
        return scId;
    }

    /**
     * setter scId(主键)
     */
    public void setScId(Long scId) {
        this.scId = scId;
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
     * getter section(区间，1表示1以上，10表示10以上)
     */
    public String getSection() {
        return section;
    }

    /**
     * setter section(区间，1表示1以上，10表示10以上)
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * getter price(价格,批发模式使用本价格)
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * setter price(价格,批发模式使用本价格)
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * getter sort(排序，排序从1起)
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * setter sort(排序，排序从1起)
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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