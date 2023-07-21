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


import com.sicheng.common.persistence.DataEntity;

/**
 * 结算子任务 Entity 父类
 * @author fanxiuxiu
 * @version 2018-07-13
 */
public class SettlementTaskSubBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long taskSubId;                 // 主键
	private Long taskMainId;                // 任务id
	private Long storeId;                   // 店铺id(关联店铺表)，任务类型是商品结算任务时有值
	private String status;                  // 状态(1未运行,2运行中,3成功,4失败)
	private String errorMsg;                // 错误信息
	public SettlementTaskSubBase() {
		super();
	}

	public SettlementTaskSubBase(Long id){
		super(id);
		this.taskSubId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return taskSubId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.taskSubId = id;
	}

	/**
	 * getter taskSubId(主键)
	 */				
	public Long getTaskSubId() {
		return taskSubId;
	}

	/**
	 * setter taskSubId(主键)
	 */	
	public void setTaskSubId(Long taskSubId) {
		this.taskSubId = taskSubId;
	}

	/**
	 * getter taskMainId(任务id)
	 */				
	public Long getTaskMainId() {
		return taskMainId;
	}

	/**
	 * setter taskMainId(任务id)
	 */	
	public void setTaskMainId(Long taskMainId) {
		this.taskMainId = taskMainId;
	}

	/**
	 * getter storeId(店铺id(关联店铺表)，任务类型是商品结算任务时有值)
	 */				
	public Long getStoreId() {
		return storeId;
	}

	/**
	 * setter storeId(店铺id(关联店铺表)，任务类型是商品结算任务时有值)
	 */	
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	/**
	 * getter status(状态(1未运行,2运行中,3成功,4失败))
	 */				
	public String getStatus() {
		return status;
	}

	/**
	 * setter status(状态(1未运行,2运行中,3成功,4失败))
	 */	
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * getter errorMsg(错误信息)
	 */				
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * setter errorMsg(错误信息)
	 */	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	
}