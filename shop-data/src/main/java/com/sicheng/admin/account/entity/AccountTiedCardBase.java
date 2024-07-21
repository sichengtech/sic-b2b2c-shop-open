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

import java.util.Date;

import com.sicheng.common.persistence.DataEntity;

/**
 * 账户绑卡表 Entity 父类
 * @author cl
 * @version 2018-07-14
 */
public class AccountTiedCardBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long tiedCardId;                // 主键ID
	private Long uId;                       // 会员id
	private String bankCardNumber;          // 银行卡号
	private String payee;                   // 收款人
	private Long idNumber;                  // 身份证号
	private String accountOpeningBank;      // 开户银行
	private String mobilePhoneNumber;       // 开户手机号
	private String auditStatus;             // 审核是否通过（0待审核，1审核同意，2审核失败）
	private String auditOpinion;            // 审核理由
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public AccountTiedCardBase() {
		super();
	}

	public AccountTiedCardBase(Long id){
		super(id);
		this.tiedCardId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return tiedCardId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.tiedCardId = id;
	}

	/**
	 * getter tiedCardId(主键ID)
	 */				
	public Long getTiedCardId() {
		return tiedCardId;
	}

	/**
	 * setter tiedCardId(主键ID)
	 */	
	public void setTiedCardId(Long tiedCardId) {
		this.tiedCardId = tiedCardId;
	}

	/**
	 * getter uId(会员id)
	 */				
	public Long getUId() {
		return uId;
	}

	/**
	 * setter uId(会员id)
	 */	
	public void setUId(Long uId) {
		this.uId = uId;
	}

	/**
	 * getter bankCardNumber(银行卡号)
	 */				
	public String getBankCardNumber() {
		return bankCardNumber;
	}

	/**
	 * setter bankCardNumber(银行卡号)
	 */	
	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	/**
	 * getter payee(收款人)
	 */				
	public String getPayee() {
		return payee;
	}

	/**
	 * setter payee(收款人)
	 */	
	public void setPayee(String payee) {
		this.payee = payee;
	}

	/**
	 * getter idNumber(身份证号)
	 */				
	public Long getIdNumber() {
		return idNumber;
	}

	/**
	 * setter idNumber(身份证号)
	 */	
	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * getter accountOpeningBank(开户银行)
	 */				
	public String getAccountOpeningBank() {
		return accountOpeningBank;
	}

	/**
	 * setter accountOpeningBank(开户银行)
	 */	
	public void setAccountOpeningBank(String accountOpeningBank) {
		this.accountOpeningBank = accountOpeningBank;
	}

	/**
	 * getter mobilePhoneNumber(开户手机号)
	 */				
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	/**
	 * setter mobilePhoneNumber(开户手机号)
	 */	
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
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