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
package com.sicheng.admin.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 收藏商品 Entity 父类
 *
 * @author cl
 * @version 2017-05-12
 */
public class MemberCollectionProductBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long collectionId;              // 主键
    private Long uId;                       // 关联(会员表)
    private Long pId;                       // 关联(商品SPU表)(SPU级别)
    private String image;                   // 封面图path，冗余
    private String pictureName;             // 商品名称
    private BigDecimal picturePrice;        // 商品价格(取SKU中最低价)
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
    private String status;                  // 0、下架，1、上架
    private Integer monthSales;             // 月销
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public MemberCollectionProductBase() {
        super();
    }

    public MemberCollectionProductBase(Long id) {
        super(id);
        this.collectionId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return collectionId;
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
        this.collectionId = id;
    }

    /**
     * getter collectionId(主键)
     */
    public Long getCollectionId() {
        return collectionId;
    }

    /**
     * setter collectionId(主键)
     */
    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    /**
     * getter uId(关联(会员表))
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(关联(会员表))
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter pId(关联(商品SPU表)(SPU级别))
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(关联(商品SPU表)(SPU级别))
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter image(封面图path，冗余)
     */
    public String getImage() {
        return image;
    }

    /**
     * setter image(封面图path，冗余)
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * getter pictureName(商品名称)
     */
    public String getPictureName() {
        return pictureName;
    }

    /**
     * setter pictureName(商品名称)
     */
    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    /**
     * getter picturePrice(商品价格(取SKU中最低价))
     */
    public BigDecimal getPicturePrice() {
        return picturePrice;
    }

    /**
     * setter picturePrice(商品价格(取SKU中最低价))
     */
    public void setPicturePrice(BigDecimal picturePrice) {
        this.picturePrice = picturePrice;
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
     * getter status(0、下架，1、上架)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(0、下架，1、上架)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter monthSales(月销)
     */
    public Integer getMonthSales() {
        return monthSales;
    }

    /**
     * setter monthSales(月销)
     */
    public void setMonthSales(Integer monthSales) {
        this.monthSales = monthSales;
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