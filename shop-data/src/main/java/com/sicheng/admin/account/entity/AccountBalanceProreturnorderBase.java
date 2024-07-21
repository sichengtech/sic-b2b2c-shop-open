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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 对账-商品订单 Entity 父类
 * @author fxx
 * @version 2018-08-04
 */
public class AccountBalanceProreturnorderBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long returnId;                  // 商品退单id
	private String status;                  // 状态(1对账中，2对账失败，3对账成功)
	private Integer failTimes;              // 失败次数
	private Date beginTime;                 // 开始对账时间
	private Date endTime;                   // 结束对账时间
	private Date beginBeginTime;            // 开始 开始对账时间
	private Date endBeginTime;              // 结束 开始对账时间
	private Date beginEndTime;              // 开始 结束对账时间
	private Date endEndTime;                // 结束 结束对账时间
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public AccountBalanceProreturnorderBase() {
		super();
	}

	public AccountBalanceProreturnorderBase(Long id){
		super(id);
		this.returnId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return returnId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.returnId = id;
	}

	/**
	 * getter returnId(商品退单id)
	 */				
	public Long getReturnId() {
		return returnId;
	}

	/**
	 * setter returnId(商品退单id)
	 */	
	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

	/**
	 * getter status(状态(1对账中，2对账失败，3对账成功))
	 */				
	public String getStatus() {
		return status;
	}

	/**
	 * setter status(状态(1对账中，2对账失败，3对账成功))
	 */	
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * getter failTimes(失败次数)
	 */				
	public Integer getFailTimes() {
		return failTimes;
	}

	/**
	 * setter failTimes(失败次数)
	 */	
	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter beginTime(开始对账时间)
	 */				
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * setter beginTime(开始对账时间)
	 */	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter endTime(结束对账时间)
	 */				
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * setter endTime(结束对账时间)
	 */	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * getter beginTime(开始对账时间)
	 */
	public Date getBeginBeginTime() {
		return beginBeginTime;
	}

	/**
	 * setter beginTime(开始对账时间)
	 */	
	public void setBeginBeginTime(Date beginBeginTime) {
		this.beginBeginTime = beginBeginTime;
	}
	
	/**
	 * getter beginTime(开始对账时间)
	 */		
	public Date getEndBeginTime() {
		return endBeginTime;
	}

	/**
	 * setter beginTime(开始对账时间)
	 */	
	public void setEndBeginTime(Date endBeginTime) {
		this.endBeginTime = endBeginTime;
	}
	/**
	 * getter endTime(结束对账时间)
	 */
	public Date getBeginEndTime() {
		return beginEndTime;
	}

	/**
	 * setter endTime(结束对账时间)
	 */	
	public void setBeginEndTime(Date beginEndTime) {
		this.beginEndTime = beginEndTime;
	}
	
	/**
	 * getter endTime(结束对账时间)
	 */		
	public Date getEndEndTime() {
		return endEndTime;
	}

	/**
	 * setter endTime(结束对账时间)
	 */	
	public void setEndEndTime(Date endEndTime) {
		this.endEndTime = endEndTime;
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