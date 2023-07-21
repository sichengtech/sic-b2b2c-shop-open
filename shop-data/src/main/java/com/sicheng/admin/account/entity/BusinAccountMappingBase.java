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
package com.sicheng.admin.account.entity;

import java.util.Date;

import com.sicheng.common.persistence.DataEntity;

/**
 * 业务账号中间表 Entity 父类
 * @author 蔡龙
 * @version 2018-08-20
 */
public class BusinAccountMappingBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long bId;                       // 主键
	private Long businessId;                // 业务id
	private String serialNumber;            // 流水号
	private String businessType;            // 类型（10商品支付，12保证金支付，14支付服务费用，16支付二次服务费用，18会员提现，20平台提现，22商品退款，24保证金退款，26服务费用退款，28二次服务费用退款，30商品结算，32服务单结算，34结束服务单，36生成优惠券，38回收优惠券）
	private String serviceChargeStatus;     // 第三方支付手续费用：1.商家承担，2平台承担
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 修改时间
	private Date endUpdateDate;             // 结束 修改时间
	public BusinAccountMappingBase() {
		super();
	}

	public BusinAccountMappingBase(Long id){
		super(id);
		this.bId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return bId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.bId = id;
	}

	/**
	 * getter bId(主键)
	 */				
	public Long getBId() {
		return bId;
	}

	/**
	 * setter bId(主键)
	 */	
	public void setBId(Long bId) {
		this.bId = bId;
	}

	/**
	 * getter businessId(业务id)
	 */				
	public Long getBusinessId() {
		return businessId;
	}

	/**
	 * setter businessId(业务id)
	 */	
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
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
	 * getter businessType(类型（10商品支付，12保证金支付，14支付服务费用，16支付二次服务费用，18会员提现，20平台提现，22商品退款，24保证金退款，26服务费用退款，28二次服务费用退款，30商品结算，32服务单结算，34结束服务单，36生成优惠券，38回收优惠券）)
	 */				
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * setter businessType(类型（10商品支付，12保证金支付，14支付服务费用，16支付二次服务费用，18会员提现，20平台提现，22商品退款，24保证金退款，26服务费用退款，28二次服务费用退款，30商品结算，32服务单结算，34结束服务单，36生成优惠券，38回收优惠券）)
	 */	
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * getter serviceChargeStatus(第三方支付手续费用：1.商家承担，2平台承担)
	 */				
	public String getServiceChargeStatus() {
		return serviceChargeStatus;
	}

	/**
	 * setter serviceChargeStatus(第三方支付手续费用：1.商家承担，2平台承担)
	 */	
	public void setServiceChargeStatus(String serviceChargeStatus) {
		this.serviceChargeStatus = serviceChargeStatus;
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
	 * getter updateDate(修改时间)
	 */
	public Date getBeginUpdateDate() {
		return beginUpdateDate;
	}

	/**
	 * setter updateDate(修改时间)
	 */	
	public void setBeginUpdateDate(Date beginUpdateDate) {
		this.beginUpdateDate = beginUpdateDate;
	}
	
	/**
	 * getter updateDate(修改时间)
	 */		
	public Date getEndUpdateDate() {
		return endUpdateDate;
	}

	/**
	 * setter updateDate(修改时间)
	 */	
	public void setEndUpdateDate(Date endUpdateDate) {
		this.endUpdateDate = endUpdateDate;
	}
	
}