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
 * 采购交易凭证 Entity 父类
 * @author cl
 * @version 2018-06-10
 */
public class PurchaseTradeVoucherBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long tradeVoucherId;            // 凭证id
	private Long purchaseTradeId;           // 关联purchase_trade_order(供采交易订单表)
	private BigDecimal money;               // 金额
	private String filePath;                // 凭证文件地址
	private Long uId;                       // 会员ID
	private String type;                    // 交易凭证类型：1合同、2收据、3对公发票、4合同+汇款凭证、5货物作证+汇款、6作证
	private String status;                  // 审核状态10.审核中20.审核未30.审核通过
	private String auditGrounds;            // 审核理由
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	
	public PurchaseTradeVoucherBase() {
		super();
	}

	public PurchaseTradeVoucherBase(Long id){
		super(id);
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)   
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getter tradeVoucherId(凭证id)
	 */				
	public Long getTradeVoucherId() {
		return tradeVoucherId;
	}

	/**
	 * setter tradeVoucherId(凭证id)
	 */	
	public void setTradeVoucherId(Long tradeVoucherId) {
		this.tradeVoucherId = tradeVoucherId;
	}

	/**
	 * getter purchaseTradeId(关联purchase_trade_order(供采交易订单表))
	 */				
	public Long getPurchaseTradeId() {
		return purchaseTradeId;
	}

	/**
	 * setter purchaseTradeId(关联purchase_trade_order(供采交易订单表))
	 */	
	public void setPurchaseTradeId(Long purchaseTradeId) {
		this.purchaseTradeId = purchaseTradeId;
	}

	/**
	 * getter money(金额)
	 */				
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * setter money(金额)
	 */	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	/**
	 * getter filePath(凭证文件地址)
	 */				
	public String getFilePath() {
		return filePath;
	}

	/**
	 * setter filePath(凭证文件地址)
	 */	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * getter uId(会员ID)
	 */				
	public Long getUId() {
		return uId;
	}

	/**
	 * setter uId(会员ID)
	 */	
	public void setUId(Long uId) {
		this.uId = uId;
	}

	/**
	 * getter type(交易凭证类型：1合同、2收据、3对公发票、4合同+汇款凭证、5货物作证+汇款、6作证)
	 */				
	public String getType() {
		return type;
	}

	/**
	 * setter type(交易凭证类型：1合同、2收据、3对公发票、4合同+汇款凭证、5货物作证+汇款、6作证)
	 */	
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * getter status(审核状态10.审核中20.审核未30.审核通过)
	 */				
	public String getStatus() {
		return status;
	}

	/**
	 * setter status(审核状态10.审核中20.审核未30.审核通过)
	 */	
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * getter auditGrounds(审核理由)
	 */				
	public String getAuditGrounds() {
		return auditGrounds;
	}

	/**
	 * setter auditGrounds(审核理由)
	 */	
	public void setAuditGrounds(String auditGrounds) {
		this.auditGrounds = auditGrounds;
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