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

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.sicheng.common.persistence.DataEntity;

/**
 * purchase Entity 父类
 * @author cl
 * @version 2018-07-20
 */
public class PurchaseBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long purchaseId;                // 采购单id
	private String content;                 // 采购内容
	private String title;                   // 采购标题
	private String bomPath;                 // 用户bom表的地址（当type=20时使用本字段）
	private String bomNewPath;              // 管理员上传的bom表地址（当type=20时使用本字段）
	private Long uId;                       // 会员ID
	private String type;                    // 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
	private String status;                  // 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
	private Date expiryTime;                // 采购到期时间
	private String cycle;                   // 交货周期
	private String purchaseExplain;         // purchase_explain
	private Date beginExpiryTime;           // 开始 采购到期时间
	private Date endExpiryTime;             // 结束 采购到期时间
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public PurchaseBase() {
		super();
	}

	public PurchaseBase(Long id){
		super(id);
		this.purchaseId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return purchaseId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)   
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.purchaseId = id;
	}

	/**
	 * getter purchaseId(采购单id)
	 */				
	public Long getPurchaseId() {
		return purchaseId;
	}

	/**
	 * setter purchaseId(采购单id)
	 */	
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	/**
	 * getter content(采购内容)
	 */				
	public String getContent() {
		return content;
	}

	/**
	 * setter content(采购内容)
	 */	
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * getter title(采购标题)
	 */				
	public String getTitle() {
		return title;
	}

	/**
	 * setter title(采购标题)
	 */	
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * getter bomPath(用户bom表的地址（当type=20时使用本字段）)
	 */				
	public String getBomPath() {
		return bomPath;
	}

	/**
	 * setter bomPath(用户bom表的地址（当type=20时使用本字段）)
	 */	
	public void setBomPath(String bomPath) {
		this.bomPath = bomPath;
	}

	/**
	 * getter bomNewPath(管理员上传的bom表地址（当type=20时使用本字段）)
	 */				
	public String getBomNewPath() {
		return bomNewPath;
	}

	/**
	 * setter bomNewPath(管理员上传的bom表地址（当type=20时使用本字段）)
	 */	
	public void setBomNewPath(String bomNewPath) {
		this.bomNewPath = bomNewPath;
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
	 * getter type(类型：10快捷采购一句话采购，20BOM采购 30批量发布采购)
	 */				
	public String getType() {
		return type;
	}

	/**
	 * setter type(类型：10快捷采购一句话采购，20BOM采购 30批量发布采购)
	 */	
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * getter status(采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消)
	 */				
	public String getStatus() {
		return status;
	}

	/**
	 * setter status(采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消)
	 */	
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter expiryTime(采购到期时间)
	 */				
	public Date getExpiryTime() {
		return expiryTime;
	}

	/**
	 * setter expiryTime(采购到期时间)
	 */	
	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	/**
	 * getter cycle(交货周期)
	 */				
	public String getCycle() {
		return cycle;
	}

	/**
	 * setter cycle(交货周期)
	 */	
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	/**
	 * getter purchaseExplain(purchase_explain)
	 */				
	public String getPurchaseExplain() {
		return purchaseExplain;
	}

	/**
	 * setter purchaseExplain(purchase_explain)
	 */	
	public void setPurchaseExplain(String purchaseExplain) {
		this.purchaseExplain = purchaseExplain;
	}

	/**
	 * getter expiryTime(采购到期时间)
	 */
	public Date getBeginExpiryTime() {
		return beginExpiryTime;
	}

	/**
	 * setter expiryTime(采购到期时间)
	 */	
	public void setBeginExpiryTime(Date beginExpiryTime) {
		this.beginExpiryTime = beginExpiryTime;
	}
	
	/**
	 * getter expiryTime(采购到期时间)
	 */		
	public Date getEndExpiryTime() {
		return endExpiryTime;
	}

	/**
	 * setter expiryTime(采购到期时间)
	 */	
	public void setEndExpiryTime(Date endExpiryTime) {
		this.endExpiryTime = endExpiryTime;
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