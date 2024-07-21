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
package com.sicheng.admin.account.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.sicheng.common.persistence.DataEntity;

/**
 * 账户提现 Entity 父类
 * @author cl
 * @version 2018-07-15
 */
public class AccountWithdrawalsBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;                 // 账户id
	private String status;                  // 类型（1会员提现，2平台提现）
	private BigDecimal money;               // 提现金额
	private Long tiedCardId;                // 提现卡号id（account_tied_card(账户绑卡表)）（个人提现有值，平台提现没有值）
	private String auditStatus;             // 审核是否通过（0待审核，1审核同意，2审核失败）
	private String auditOpinion;            // 审核理由
	private String isPay;                   // 是否支付（0未支付、1已支付）
	private Date payTime;                   // 支付时间
	private Date beginPayTime;              // 开始 支付时间
	private Date endPayTime;                // 结束 支付时间
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public AccountWithdrawalsBase() {
		super();
	}

	public AccountWithdrawalsBase(Long id){
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
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getter accountId(账户id)
	 */				
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * setter accountId(账户id)
	 */	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * getter status(类型（1会员提现，2平台提现）)
	 */				
	public String getStatus() {
		return status;
	}

	/**
	 * setter status(类型（1会员提现，2平台提现）)
	 */	
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * getter money(提现金额)
	 */				
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * setter money(提现金额)
	 */	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	/**
	 * getter tiedCardId(提现卡号id（account_tied_card(账户绑卡表)）（个人提现有值，平台提现没有值）)
	 */				
	public Long getTiedCardId() {
		return tiedCardId;
	}

	/**
	 * setter tiedCardId(提现卡号id（account_tied_card(账户绑卡表)）（个人提现有值，平台提现没有值）)
	 */	
	public void setTiedCardId(Long tiedCardId) {
		this.tiedCardId = tiedCardId;
	}

	/**
	 * getter auditStatus(审核是否通过（0待审核，1审核同意，2审核失败）)
	 */				
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * setter auditStatus(审核是否通过（0待审核，1审核同意，2审核失败）)
	 */	
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * getter auditOpinion(审核理由)
	 */				
	public String getAuditOpinion() {
		return auditOpinion;
	}

	/**
	 * setter auditOpinion(审核理由)
	 */	
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	/**
	 * getter isPay(是否支付（0未支付、1已支付）)
	 */				
	public String getIsPay() {
		return isPay;
	}

	/**
	 * setter isPay(是否支付（0未支付、1已支付）)
	 */	
	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter payTime(支付时间)
	 */				
	public Date getPayTime() {
		return payTime;
	}

	/**
	 * setter payTime(支付时间)
	 */	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	/**
	 * getter payTime(支付时间)
	 */
	public Date getBeginPayTime() {
		return beginPayTime;
	}

	/**
	 * setter payTime(支付时间)
	 */	
	public void setBeginPayTime(Date beginPayTime) {
		this.beginPayTime = beginPayTime;
	}
	
	/**
	 * getter payTime(支付时间)
	 */		
	public Date getEndPayTime() {
		return endPayTime;
	}

	/**
	 * setter payTime(支付时间)
	 */	
	public void setEndPayTime(Date endPayTime) {
		this.endPayTime = endPayTime;
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