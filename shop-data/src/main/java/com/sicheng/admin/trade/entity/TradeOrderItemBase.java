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
package com.sicheng.admin.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情 Entity 父类
 *
 * @author fxx
 * @version 2017-09-11
 */
public class TradeOrderItemBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long orderItemId;               // 主键
    private Long orderId;                   // 订单id(订单表id)
    private Long pId;                       // 商品id(商品表id)
    private String thumbnailPath;           // 商品缩略图地址(快照)
    private Long skuId;                     // 商品sku的id
    private String skuValue;                // sku规格值(快照)
    private String name;                    // 商品名称(快照)
    private BigDecimal price;               // 商品单价(快照)
    private Integer quantity;               // 数量
    private String category;                // 商品分类(平台)(快照)
    private String storeCategory;           // 商品分类(本店)(快照)
    private String brand;                   // 品牌(快照)
    private String type;                    // 销售类型，1零售型、2批发型(快照)
    private String productType;             // 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
    private Integer score;                  // 所用积分
    private Double weight;                  // 商品重量(快照)
    private String benefit;                 // 商品优惠(快照)
    private String isAdditionalComment;     // 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
    private String commissionRatio;         // 佣金比
    private String isReturnStatus;          // 是否退货退款,0否、1退货退款、2退款
    private String returnStatus;            // 退货/款状,10退货/款成功、20退货/款失败
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
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public TradeOrderItemBase() {
        super();
    }

    public TradeOrderItemBase(Long id) {
        super(id);
        this.orderItemId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return orderItemId;
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
        this.orderItemId = id;
    }

    /**
     * getter orderItemId(主键)
     */
    public Long getOrderItemId() {
        return orderItemId;
    }

    /**
     * setter orderItemId(主键)
     */
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * getter orderId(订单id(订单表id))
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * setter orderId(订单id(订单表id))
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * getter pId(商品id(商品表id))
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(商品id(商品表id))
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter thumbnailPath(商品缩略图地址(快照))
     */
    public String getThumbnailPath() {
        return thumbnailPath;
    }

    /**
     * setter thumbnailPath(商品缩略图地址(快照))
     */
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    /**
     * getter skuId(商品sku的id)
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * setter skuId(商品sku的id)
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * getter skuValue(sku规格值(快照))
     */
    public String getSkuValue() {
        return skuValue;
    }

    /**
     * setter skuValue(sku规格值(快照))
     */
    public void setSkuValue(String skuValue) {
        this.skuValue = skuValue;
    }

    /**
     * getter name(商品名称(快照))
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(商品名称(快照))
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter price(商品单价(快照))
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * setter price(商品单价(快照))
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * getter quantity(数量)
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * setter quantity(数量)
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * getter category(商品分类(平台)(快照))
     */
    public String getCategory() {
        return category;
    }

    /**
     * setter category(商品分类(平台)(快照))
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * getter storeCategory(商品分类(本店)(快照))
     */
    public String getStoreCategory() {
        return storeCategory;
    }

    /**
     * setter storeCategory(商品分类(本店)(快照))
     */
    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    /**
     * getter brand(品牌(快照))
     */
    public String getBrand() {
        return brand;
    }

    /**
     * setter brand(品牌(快照))
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * getter type(销售类型，1零售型、2批发型(快照))
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(销售类型，1零售型、2批发型(快照))
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter productType(商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照))
     */
    public String getProductType() {
        return productType;
    }

    /**
     * setter productType(商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照))
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * getter score(所用积分)
     */
    public Integer getScore() {
        return score;
    }

    /**
     * setter score(所用积分)
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * getter weight(商品重量(快照))
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * setter weight(商品重量(快照))
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * getter benefit(商品优惠(快照))
     */
    public String getBenefit() {
        return benefit;
    }

    /**
     * setter benefit(商品优惠(快照))
     */
    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    /**
     * getter isAdditionalComment(是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评))
     */
    public String getIsAdditionalComment() {
        return isAdditionalComment;
    }

    /**
     * setter isAdditionalComment(是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评))
     */
    public void setIsAdditionalComment(String isAdditionalComment) {
        this.isAdditionalComment = isAdditionalComment;
    }

    /**
     * getter commissionRatio(佣金比)
     */
    public String getCommissionRatio() {
        return commissionRatio;
    }

    /**
     * setter commissionRatio(佣金比)
     */
    public void setCommissionRatio(String commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    /**
     * getter isReturnStatus(是否退货退款,0否、1退货退款、2退款)
     */
    public String getIsReturnStatus() {
        return isReturnStatus;
    }

    /**
     * setter isReturnStatus(是否退货退款,0否、1退货退款、2退款)
     */
    public void setIsReturnStatus(String isReturnStatus) {
        this.isReturnStatus = isReturnStatus;
    }

    /**
     * getter returnStatus(退货/款状,10退货/款成功、20退货/款失败)
     */
    public String getReturnStatus() {
        return returnStatus;
    }

    /**
     * setter returnStatus(退货/款状,10退货/款成功、20退货/款失败)
     */
    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
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