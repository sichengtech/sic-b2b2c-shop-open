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

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品SKU Entity 父类
 *
 * @author zhaolei
 * @version 2017-07-07
 */
public class ProductSkuBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long skuId;                     // 商品SKU ID
    private Long pId;                       // 商品spu id
    private Integer sort;                   // 排序
    private String isNotSpec;               // 是否无规格，0否，1是。商品无规格算1个sku,所以在sku表中也应有一行记录，也有库存有价格。
    private String spec1;                   // 规格1，格式：id_别名，示例：100_颜色，100是id用于对应product_spec表中spec_id
    private String spec1V;                  // 规格1 值，默认是规格值，也可是规格值的别名
    private String spec2;                   // 规格2
    private String spec2V;                  // 规格2 值
    private String spec3;                   // 规格3 ，最多3个规格
    private String spec3V;                  // 规格3 值
    private String spec4;                   // 规格4 ，备用
    private String spec4V;                  // 规格4 值，备用
    private BigDecimal price;               // 价格，零售模式使用本价格
    private Integer stock;                  // 库存
    private Long occupyStock;               // 占用库存
    private String sn;                      // 商家编号
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
    private Date beginCreateDate;           // 开始 创建日期
    private Date endCreateDate;             // 结束 创建日期
    private Date beginUpdateDate;           // 开始 修改日期
    private Date endUpdateDate;             // 结束 修改日期

    public ProductSkuBase() {
        super();
    }

    public ProductSkuBase(Long id) {
        super(id);
        this.skuId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return skuId;
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
        this.skuId = id;
    }

    /**
     * getter skuId(商品SKU ID)
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * setter skuId(商品SKU ID)
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * getter pId(商品spu id)
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(商品spu id)
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
     * getter isNotSpec(是否无规格，0否，1是。商品无规格算1个sku,所以在sku表中也应有一行记录，也有库存有价格。)
     */
    public String getIsNotSpec() {
        return isNotSpec;
    }

    /**
     * setter isNotSpec(是否无规格，0否，1是。商品无规格算1个sku,所以在sku表中也应有一行记录，也有库存有价格。)
     */
    public void setIsNotSpec(String isNotSpec) {
        this.isNotSpec = isNotSpec;
    }

    /**
     * getter spec1(规格1，格式：id_别名，示例：100_颜色，100是id用于对应product_spec表中spec_id)
     */
    public String getSpec1() {
        return spec1;
    }

    /**
     * setter spec1(规格1，格式：id_别名，示例：100_颜色，100是id用于对应product_spec表中spec_id)
     */
    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }

    /**
     * getter spec1V(规格1 值，默认是规格值，也可是规格值的别名)
     */
    public String getSpec1V() {
        return spec1V;
    }

    /**
     * setter spec1V(规格1 值，默认是规格值，也可是规格值的别名)
     */
    public void setSpec1V(String spec1V) {
        this.spec1V = spec1V;
    }

    /**
     * getter spec2(规格2)
     */
    public String getSpec2() {
        return spec2;
    }

    /**
     * setter spec2(规格2)
     */
    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }

    /**
     * getter spec2V(规格2 值)
     */
    public String getSpec2V() {
        return spec2V;
    }

    /**
     * setter spec2V(规格2 值)
     */
    public void setSpec2V(String spec2V) {
        this.spec2V = spec2V;
    }

    /**
     * getter spec3(规格3 ，最多3个规格)
     */
    public String getSpec3() {
        return spec3;
    }

    /**
     * setter spec3(规格3 ，最多3个规格)
     */
    public void setSpec3(String spec3) {
        this.spec3 = spec3;
    }

    /**
     * getter spec3V(规格3 值)
     */
    public String getSpec3V() {
        return spec3V;
    }

    /**
     * setter spec3V(规格3 值)
     */
    public void setSpec3V(String spec3V) {
        this.spec3V = spec3V;
    }

    /**
     * getter spec4(规格4 ，备用)
     */
    public String getSpec4() {
        return spec4;
    }

    /**
     * setter spec4(规格4 ，备用)
     */
    public void setSpec4(String spec4) {
        this.spec4 = spec4;
    }

    /**
     * getter spec4V(规格4 值，备用)
     */
    public String getSpec4V() {
        return spec4V;
    }

    /**
     * setter spec4V(规格4 值，备用)
     */
    public void setSpec4V(String spec4V) {
        this.spec4V = spec4V;
    }

    /**
     * getter price(价格，零售模式使用本价格)
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * setter price(价格，零售模式使用本价格)
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * getter stock(库存)
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * setter stock(库存)
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * getter occupyStock(占用库存)
     */
    public Long getOccupyStock() {
        return occupyStock;
    }

    /**
     * setter occupyStock(占用库存)
     */
    public void setOccupyStock(Long occupyStock) {
        this.occupyStock = occupyStock;
    }

    /**
     * getter sn(商家编号)
     */
    public String getSn() {
        return sn;
    }

    /**
     * setter sn(商家编号)
     */
    public void setSn(String sn) {
        this.sn = sn;
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
     * getter createDate(创建日期)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(创建日期)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(创建日期)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(创建日期)
     */
    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    /**
     * getter updateDate(修改日期)
     */
    public Date getBeginUpdateDate() {
        return beginUpdateDate;
    }

    /**
     * setter updateDate(修改日期)
     */
    public void setBeginUpdateDate(Date beginUpdateDate) {
        this.beginUpdateDate = beginUpdateDate;
    }

    /**
     * getter updateDate(修改日期)
     */
    public Date getEndUpdateDate() {
        return endUpdateDate;
    }

    /**
     * setter updateDate(修改日期)
     */
    public void setEndUpdateDate(Date endUpdateDate) {
        this.endUpdateDate = endUpdateDate;
    }

}