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
 * 采购单明细 Entity 父类
 * @author cl
 * @version 2018-07-20
 */
public class PurchaseItemBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long purchaseItemId;            // 采购详情id
	private Long purchaseId;                // 采购单id(关联purchaes_order(采购单表))
	private String name;                    // 产品名称
	private String model;                   // 产品型号
	private String brand;                   // 产品品牌
	private Integer amount;                 // 产品数量
	private String purchaseRemarks;         // 产品描述
	private BigDecimal priceRequirement;    // 价格要求
	private String unit;                    // 单位
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public PurchaseItemBase() {
		super();
	}

	public PurchaseItemBase(Long id){
		super(id);
		this.purchaseItemId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return purchaseItemId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)   
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.purchaseItemId = id;
	}

	/**
	 * getter purchaseItemId(采购详情id)
	 */				
	public Long getPurchaseItemId() {
		return purchaseItemId;
	}

	/**
	 * setter purchaseItemId(采购详情id)
	 */	
	public void setPurchaseItemId(Long purchaseItemId) {
		this.purchaseItemId = purchaseItemId;
	}

	/**
	 * getter purchaseId(采购单id(关联purchaes_order(采购单表)))
	 */				
	public Long getPurchaseId() {
		return purchaseId;
	}

	/**
	 * setter purchaseId(采购单id(关联purchaes_order(采购单表)))
	 */	
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
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
	 * getter model(产品型号)
	 */				
	public String getModel() {
		return model;
	}

	/**
	 * setter model(产品型号)
	 */	
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * getter brand(产品品牌)
	 */				
	public String getBrand() {
		return brand;
	}

	/**
	 * setter brand(产品品牌)
	 */	
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * getter amount(产品数量)
	 */				
	public Integer getAmount() {
		return amount;
	}

	/**
	 * setter amount(产品数量)
	 */	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * getter purchaseRemarks(产品描述)
	 */				
	public String getPurchaseRemarks() {
		return purchaseRemarks;
	}

	/**
	 * setter purchaseRemarks(产品描述)
	 */	
	public void setPurchaseRemarks(String purchaseRemarks) {
		this.purchaseRemarks = purchaseRemarks;
	}

	/**
	 * getter priceRequirement(价格要求)
	 */				
	public BigDecimal getPriceRequirement() {
		return priceRequirement;
	}

	/**
	 * setter priceRequirement(价格要求)
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