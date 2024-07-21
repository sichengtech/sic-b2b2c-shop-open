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
 * 第三方账户资金流水 Entity 父类
 * @author zhaolei
 * @version 2018-07-13
 */
public class AccountThirdpartySnBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private String serialNumber;            // 流水号
	private String moneyFlowType;           // 资金流类型（1.付款、2.提现、3.充值、4.退款）
	private BigDecimal money;               // 交易金额
	private Long payWayId;                  // 交易渠道（支付方式id）
	private String payWayName;              // 支付方式名称
	private String outerTradeNo;            // 外部交易记录号
	private String tradeRemarks;            // 交易备注
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public AccountThirdpartySnBase() {
		super();
	}

	public AccountThirdpartySnBase(Long id){
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
	 * getter moneyFlowType(资金流类型（1.付款、2.提现、3.充值、4.退款）)
	 */				
	public String getMoneyFlowType() {
		return moneyFlowType;
	}

	/**
	 * setter moneyFlowType(资金流类型（1.付款、2.提现、3.充值、4.退款）)
	 */	
	public void setMoneyFlowType(String moneyFlowType) {
		this.moneyFlowType = moneyFlowType;
	}

	/**
	 * getter money(交易金额)
	 */				
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * setter money(交易金额)
	 */	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	/**
	 * getter payWayId(交易渠道（支付方式id）)
	 */				
	public Long getPayWayId() {
		return payWayId;
	}

	/**
	 * setter payWayId(交易渠道（支付方式id）)
	 */	
	public void setPayWayId(Long payWayId) {
		this.payWayId = payWayId;
	}

	/**
	 * getter payWayName(支付方式名称)
	 */				
	public String getPayWayName() {
		return payWayName;
	}

	/**
	 * setter payWayName(支付方式名称)
	 */	
	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	/**
	 * getter outerTradeNo(外部交易记录号)
	 */				
	public String getOuterTradeNo() {
		return outerTradeNo;
	}

	/**
	 * setter outerTradeNo(外部交易记录号)
	 */	
	public void setOuterTradeNo(String outerTradeNo) {
		this.outerTradeNo = outerTradeNo;
	}

	/**
	 * getter tradeRemarks(交易备注)
	 */				
	public String getTradeRemarks() {
		return tradeRemarks;
	}

	/**
	 * setter tradeRemarks(交易备注)
	 */	
	public void setTradeRemarks(String tradeRemarks) {
		this.tradeRemarks = tradeRemarks;
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