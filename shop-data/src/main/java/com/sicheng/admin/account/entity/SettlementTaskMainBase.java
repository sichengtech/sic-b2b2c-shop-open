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
import com.fasterxml.jackson.annotation.JsonFormat;

import com.sicheng.common.persistence.DataEntity;

/**
 * 结算主任务 Entity 父类
 * @author fxx
 * @version 2018-07-13
 */
public class SettlementTaskMainBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long taskMainId;                // 主键
	private String name;                    // 名称(年-月-日)
	private String type;                    // 任务类型，1商品账单，2服务账单
	private Date beginTime;                 // 开始时间(年-月-日 时：分：秒)
	private Date endTime;                   // 结束时间(年-月-日 时：分：秒
	private String status;                  // 状态(1未运行,2运行中,3已完成)
	private Long successCount;              // 成功数
	private Long errorCount;                // 失败数
	private Long sumCount;                  // 总数
	private Date beginBeginTime;            // 开始 开始时间(年-月-日 时：分：秒)
	private Date endBeginTime;              // 结束 开始时间(年-月-日 时：分：秒)
	private Date beginEndTime;              // 开始 结束时间(年-月-日 时：分：秒
	private Date endEndTime;                // 结束 结束时间(年-月-日 时：分：秒
	public SettlementTaskMainBase() {
		super();
	}

	public SettlementTaskMainBase(Long id){
		super(id);
		this.taskMainId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return taskMainId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.taskMainId = id;
	}

	/**
	 * getter taskMainId(主键)
	 */				
	public Long getTaskMainId() {
		return taskMainId;
	}

	/**
	 * setter taskMainId(主键)
	 */	
	public void setTaskMainId(Long taskMainId) {
		this.taskMainId = taskMainId;
	}

	/**
	 * getter name(名称(年-月-日))
	 */				
	public String getName() {
		return name;
	}

	/**
	 * setter name(名称(年-月-日))
	 */	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter type(任务类型，1商品账单，2服务账单)
	 */				
	public String getType() {
		return type;
	}

	/**
	 * setter type(任务类型，1商品账单，2服务账单)
	 */	
	public void setType(String type) {
		this.type = type;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter beginTime(开始时间(年-月-日 时：分：秒))
	 */				
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * setter beginTime(开始时间(年-月-日 时：分：秒))
	 */	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter endTime(结束时间(年-月-日 时：分：秒)
	 */				
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * setter endTime(结束时间(年-月-日 时：分：秒)
	 */	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * getter status(状态(1未运行,2运行中,3已完成))
	 */				
	public String getStatus() {
		return status;
	}

	/**
	 * setter status(状态(1未运行,2运行中,3已完成))
	 */	
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * getter successCount(成功数)
	 */				
	public Long getSuccessCount() {
		return successCount;
	}

	/**
	 * setter successCount(成功数)
	 */	
	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
	}

	/**
	 * getter errorCount(失败数)
	 */				
	public Long getErrorCount() {
		return errorCount;
	}

	/**
	 * setter errorCount(失败数)
	 */	
	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}

	/**
	 * getter sumCount(总数)
	 */				
	public Long getSumCount() {
		return sumCount;
	}

	/**
	 * setter sumCount(总数)
	 */	
	public void setSumCount(Long sumCount) {
		this.sumCount = sumCount;
	}

	/**
	 * getter beginTime(开始时间(年-月-日 时：分：秒))
	 */
	public Date getBeginBeginTime() {
		return beginBeginTime;
	}

	/**
	 * setter beginTime(开始时间(年-月-日 时：分：秒))
	 */	
	public void setBeginBeginTime(Date beginBeginTime) {
		this.beginBeginTime = beginBeginTime;
	}
	
	/**
	 * getter beginTime(开始时间(年-月-日 时：分：秒))
	 */		
	public Date getEndBeginTime() {
		return endBeginTime;
	}

	/**
	 * setter beginTime(开始时间(年-月-日 时：分：秒))
	 */	
	public void setEndBeginTime(Date endBeginTime) {
		this.endBeginTime = endBeginTime;
	}
	/**
	 * getter endTime(结束时间(年-月-日 时：分：秒)
	 */
	public Date getBeginEndTime() {
		return beginEndTime;
	}

	/**
	 * setter endTime(结束时间(年-月-日 时：分：秒)
	 */	
	public void setBeginEndTime(Date beginEndTime) {
		this.beginEndTime = beginEndTime;
	}
	
	/**
	 * getter endTime(结束时间(年-月-日 时：分：秒)
	 */		
	public Date getEndEndTime() {
		return endEndTime;
	}

	/**
	 * setter endTime(结束时间(年-月-日 时：分：秒)
	 */	
	public void setEndEndTime(Date endEndTime) {
		this.endEndTime = endEndTime;
	}
	
}