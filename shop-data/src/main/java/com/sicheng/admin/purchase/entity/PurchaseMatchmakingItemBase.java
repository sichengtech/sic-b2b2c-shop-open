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
package com.sicheng.admin.purchase.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.sicheng.common.persistence.DataEntity;

/**
 * 撮合采购详情 Entity 父类
 * @author 蔡龙
 * @version 2018-07-20
 */
public class PurchaseMatchmakingItemBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long pmiId;                     // 撮合采购详情id
	private Long pmId;                      // 撮合采购id
	private Long purchaseItemId;            // 采购单详情id
	private Integer amount;                 // 数量
	private BigDecimal offerPrice;          // 报价单价
	private String offerRemarks;            // 报价备注
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public PurchaseMatchmakingItemBase() {
		super();
	}

	public PurchaseMatchmakingItemBase(Long id){
		super(id);
		this.pmiId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return pmiId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)   
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.pmiId = id;
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
	 * getter pmId(撮合采购id)
	 */				
	public Long getPmId() {
		return pmId;
	}

	/**
	 * setter pmId(撮合采购id)
	 */	
	public void setPmId(Long pmId) {
		this.pmId = pmId;
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