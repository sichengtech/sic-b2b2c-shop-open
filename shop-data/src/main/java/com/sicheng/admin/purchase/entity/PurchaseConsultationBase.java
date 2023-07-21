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

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 采购咨询 Entity 父类
 * @author fanxiuxiu
 * @version 2019-12-09
 */
public class PurchaseConsultationBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long pcId;                      // 主键
	private Long uId;                       // 用户id
	private Long pId;                       // 商品id
	private String purchaseDescribe;        // 描述
	private Integer quantity;               // 数量
	private String contactInfo;             // 联系方式（手机号或邮箱）
	private String isContact;               // 是否联系
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public PurchaseConsultationBase() {
		super();
	}

	public PurchaseConsultationBase(Long id){
		super(id);
		this.pcId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return pcId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.pcId = id;
	}

	/**
	 * getter pcId(主键)
	 */				
	public Long getPcId() {
		return pcId;
	}

	/**
	 * setter pcId(主键)
	 */	
	public void setPcId(Long pcId) {
		this.pcId = pcId;
	}

	/**
	 * getter uId(用户id)
	 */				
	public Long getUId() {
		return uId;
	}

	/**
	 * setter uId(用户id)
	 */	
	public void setUId(Long uId) {
		this.uId = uId;
	}

	/**
	 * getter pId(商品id)
	 */				
	public Long getPId() {
		return pId;
	}

	/**
	 * setter pId(商品id)
	 */	
	public void setPId(Long pId) {
		this.pId = pId;
	}

	/**
	 * getter purchaseDescribe(描述)
	 */				
	public String getPurchaseDescribe() {
		return purchaseDescribe;
	}

	/**
	 * setter purchaseDescribe(描述)
	 */	
	public void setPurchaseDescribe(String purchaseDescribe) {
		this.purchaseDescribe = purchaseDescribe;
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
	 * getter contactInfo(联系方式（手机号或邮箱）)
	 */				
	public String getContactInfo() {
		return contactInfo;
	}

	/**
	 * setter contactInfo(联系方式（手机号或邮箱）)
	 */	
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * getter isContact(是否联系)
	 */				
	public String getIsContact() {
		return isContact;
	}

	/**
	 * setter isContact(是否联系)
	 */	
	public void setIsContact(String isContact) {
		this.isContact = isContact;
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