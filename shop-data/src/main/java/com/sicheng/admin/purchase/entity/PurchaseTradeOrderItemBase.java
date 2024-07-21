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
package com.sicheng.admin.purchase.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.sicheng.common.persistence.DataEntity;

/**
 * 采购订单详情 Entity 父类
 * @author zjl
 * @version 2019-12-18
 */
public class PurchaseTradeOrderItemBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long ptoiId;                    // 采购订单详情id
	private Long ptoId;                     // 采购订单id
	private Long purchaseItemId;            // 采购单详情id
	private Long pmiId;                     // 撮合采购详情id
	private String name;                    // 产品名称
	private String model;                   // 规格型号
	private String brand;                   // 品牌
	private Integer amount;                 // 数量
	private BigDecimal priceRequirement;    // 采购单价
	private String unit;                    // 单位
	private String purchaseRemarks;         // 采购备注
	private BigDecimal offerPrice;          // 报价单价
	private String offerRemarks;            // 报价备注
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public PurchaseTradeOrderItemBase() {
		super();
	}

	public PurchaseTradeOrderItemBase(Long id){
		super(id);
		this.ptoiId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return ptoiId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)   
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.ptoiId = id;
	}

	/**
	 * getter ptoiId(采购订单详情id)
	 */				
	public Long getPtoiId() {
		return ptoiId;
	}

	/**
	 * setter ptoiId(采购订单详情id)
	 */	
	public void setPtoiId(Long ptoiId) {
		this.ptoiId = ptoiId;
	}

	/**
	 * getter ptoId(采购订单id)
	 */				
	public Long getPtoId() {
		return ptoId;
	}

	/**
	 * setter ptoId(采购订单id)
	 */	
	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	/**
	 * getter purchaseItemId(采购单详情id)
	 */				
	public Long getPurchaseItemId() {
		return purchaseItemId;
	}

	/**
	 * setter purchaseItemId(采购单详情id)
	 */	
	public void setPurchaseItemId(Long purchaseItemId) {
		this.purchaseItemId = purchaseItemId;
	}

	/**
	 * getter pmiId(撮合采购详情id)
	 */				
	public Long getPmiId() {
		return pmiId;
	}

	/**
	 * setter pmiId(撮合采购详情id)
	 */	
	public void setPmiId(Long pmiId) {
		this.pmiId = pmiId;
	}

	/**
	 * getter name(产品名称)
	 */				
	public String getName() {
		return name;
	}

	/**
	 * setter name(产品名称)
	 */	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter model(规格型号)
	 */				
	public String getModel() {
		return model;
	}

	/**
	 * setter model(规格型号)
	 */	
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * getter brand(品牌)
	 */				
	public String getBrand() {
		return brand;
	}

	/**
	 * setter brand(品牌)
	 */	
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * getter amount(数量)
	 */				
	public Integer getAmount() {
		return amount;
	}

	/**
	 * setter amount(数量)
	 */	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * getter priceRequirement(采购单价)
	 */				
	public BigDecimal getPriceRequirement() {
		return priceRequirement;
	}

	/**
	 * setter priceRequirement(采购单价)
	 */	
	public void setPriceRequirement(BigDecimal priceRequirement) {
		this.priceRequirement = priceRequirement;
	}

	/**
	 * getter unit(单位)
	 */				
	public String getUnit() {
		return unit;
	}

	/**
	 * setter unit(单位)
	 */	
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * getter purchaseRemarks(采购备注)
	 */				
	public String getPurchaseRemarks() {
		return purchaseRemarks;
	}

	/**
	 * setter purchaseRemarks(采购备注)
	 */	
	public void setPurchaseRemarks(String purchaseRemarks) {
		this.purchaseRemarks = purchaseRemarks;
	}

	/**
	 * getter offerPrice(报价单价)
	 */				
	public BigDecimal getOfferPrice() {
		return offerPrice;
	}

	/**
	 * setter offerPrice(报价单价)
	 */	
	public void setOfferPrice(BigDecimal offerPrice) {
		this.offerPrice = offerPrice;
	}

	/**
	 * getter offerRemarks(报价备注)
	 */				
	public String getOfferRemarks() {
		return offerRemarks;
	}

	/**
	 * setter offerRemarks(报价备注)
	 */	
	public void setOfferRemarks(String offerRemarks) {
		this.offerRemarks = offerRemarks;
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