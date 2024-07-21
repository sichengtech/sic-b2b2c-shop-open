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

import com.sicheng.common.persistence.DataEntity;

/**
 * 平台账户流水 Entity 父类
 * @author zhaolei
 * @version 2018-07-13
 */
public class AccountPlatformSnBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long apId;                      // 平台账户id
	private String serialNumber;            // 流水号
	private String payRemarks;              // 名称/备注
	private BigDecimal incomeMoney;         // 收入金额
	private BigDecimal expensesMoney;       // 支出金额
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public AccountPlatformSnBase() {
		super();
	}

	public AccountPlatformSnBase(Long id){
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
	 * getter apId(平台账户id)
	 */				
	public Long getApId() {
		return apId;
	}

	/**
	 * setter apId(平台账户id)
	 */	
	public void setApId(Long apId) {
		this.apId = apId;
	}

	/**
	 * getter serialNumber(流水号)
	 */				
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * setter serialNumber(流水号)
	 */	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * getter payRemarks(名称/备注)
	 */				
	public String getPayRemarks() {
		return payRemarks;
	}

	/**
	 * setter payRemarks(名称/备注)
	 */	
	public void setPayRemarks(String payRemarks) {
		this.payRemarks = payRemarks;
	}

	/**
	 * getter incomeMoney(收入金额)
	 */				
	public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	/**
	 * setter incomeMoney(收入金额)
	 */	
	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	/**
	 * getter expensesMoney(支出金额)
	 */				
	public BigDecimal getExpensesMoney() {
		return expensesMoney;
	}

	/**
	 * setter expensesMoney(支出金额)
	 */	
	public void setExpensesMoney(BigDecimal expensesMoney) {
		this.expensesMoney = expensesMoney;
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