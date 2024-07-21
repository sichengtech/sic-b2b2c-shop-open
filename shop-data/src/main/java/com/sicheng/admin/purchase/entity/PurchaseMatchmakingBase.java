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

import java.util.Date;
import java.math.BigDecimal;

import com.sicheng.common.persistence.DataEntity;

/**
 * 撮合采购 Entity 父类
 * @author cl
 * @version 2018-07-20
 */
public class PurchaseMatchmakingBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long purchaseMatchmakingId;     // 撮合采购id
	private Long purchaseId;                // 采购单id
	private Long purchaseTradeId;           // 订单id(关联trade_order订单表)
	private Long offerUId;                  // 会员id(报价方id)
	private String status;                  // 10待采购   20已采购 30放弃采购
	private Long purchaseUId;               // 会员id(采购方id)
	private BigDecimal price;               // 总价格
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public PurchaseMatchmakingBase() {
		super();
	}

	public PurchaseMatchmakingBase(Long id){
		super(id);
		this.purchaseMatchmakingId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return purchaseMatchmakingId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)   
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.purchaseMatchmakingId = id;
	}

	/**
	 * getter purchaseMatchmakingId(撮合采购id)
	 */				
	public Long getPurchaseMatchmakingId() {
		return purchaseMatchmakingId;
	}

	/**
	 * setter purchaseMatchmakingId(撮合采购id)
	 */	
	public void setPurchaseMatchmakingId(Long purchaseMatchmakingId) {
		this.purchaseMatchmakingId = purchaseMatchmakingId;
	}

	/**
	 * getter purchaseId(采购单id)
	 */				
	public Long getPurchaseId() {
		return purchaseId;
	}

	/**
	 * setter purchaseId(采购单id)
	 */	
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	/**
	 * getter purchaseTradeId(订单id(关联trade_order订单表))
	 */				
	public Long getPurchaseTradeId() {
		return purchaseTradeId;
	}

	/**
	 * setter purchaseTradeId(订单id(关联trade_order订单表))
	 */	
	public void setPurchaseTradeId(Long purchaseTradeId) {
		this.purchaseTradeId = purchaseTradeId;
	}

	/**
	 * getter offerUId(会员id(报价方id))
	 */				
	public Long getOfferUId() {
		return offerUId;
	}

	/**
	 * setter offerUId(会员id(报价方id))
	 */	
	public void setOfferUId(Long offerUId) {
		this.offerUId = offerUId;
	}

	/**
	 * getter status(10待采购   20已采购 30放弃采购)
	 */				
	public String getStatus() {
		return status;
	}

	/**
	 * setter status(10待采购   20已采购 30放弃采购)
	 */	
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * getter purchaseUId(会员id(采购方id))
	 */				
	public Long getPurchaseUId() {
		return purchaseUId;
	}

	/**
	 * setter purchaseUId(会员id(采购方id))
	 */	
	public void setPurchaseUId(Long purchaseUId) {
		this.purchaseUId = purchaseUId;
	}

	/**
	 * getter price(总价格)
	 */				
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * setter price(总价格)
	 */	
	public void setPrice(BigDecimal price) {
		this.price = price;
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