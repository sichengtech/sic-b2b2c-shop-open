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

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.sicheng.common.persistence.DataEntity;

/**
 * 采购单视图 Entity 父类
 * @author 张加利
 * @version 2018-07-24
 */
public class ViewPurchaseBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long purchaseId;                // 采购单id
	private String purchaseTitle;           // 采购标题
	private String purchaseContent;         // 采购内容
	private String purchaseBomPath;         // 用户bom表的地址（当type=20时使用本字段）
	private String purchaseBomNewPath;      // 管理员上传的bom表地址（当type=20时使用本字段）
	private Long purchaseUId;               // 会员ID
	private String purchaseType;            // 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
	private String purchaseStatus;          // 采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
	private Date purchaseExpiryTime;        // 采购到期时间
	private String puCycle;                 // 交货周期
	private String purchaseExplain;         // purchase_explain
	private Date purchaseCreateDate;        // 创建时间
	private Date purchaseUpdateDate;        // 更新时间
	private Long purchaseCounts;            // purchase_counts
	private Long piId;                      // 采购详情id
	private String piName;                  // 产品名称
	private String piModel;                 // 产品型号
	private String piBrand;                 // 产品品牌
	private String umLoginName;             // 用户名、登录名
	private String umMobile;                // 手机号
	private Long psId;                      // 采购空间id
	private Date beginPurchaseExpiryTime;   // 开始 采购到期时间
	private Date endPurchaseExpiryTime;     // 结束 采购到期时间
	private Date beginPurchaseCreateDate;   // 开始 创建时间
	private Date endPurchaseCreateDate;     // 结束 创建时间
	private Date beginPurchaseUpdateDate;   // 开始 更新时间
	private Date endPurchaseUpdateDate;     // 结束 更新时间
	public ViewPurchaseBase() {
		super();
	}

	public ViewPurchaseBase(Long id){
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
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)   
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
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
	 * getter purchaseTitle(采购标题)
	 */				
	public String getPurchaseTitle() {
		return purchaseTitle;
	}

	/**
	 * setter purchaseTitle(采购标题)
	 */	
	public void setPurchaseTitle(String purchaseTitle) {
		this.purchaseTitle = purchaseTitle;
	}

	/**
	 * getter purchaseContent(采购内容)
	 */				
	public String getPurchaseContent() {
		return purchaseContent;
	}

	/**
	 * setter purchaseContent(采购内容)
	 */	
	public void setPurchaseContent(String purchaseContent) {
		this.purchaseContent = purchaseContent;
	}

	/**
	 * getter purchaseBomPath(用户bom表的地址（当type=20时使用本字段）)
	 */				
	public String getPurchaseBomPath() {
		return purchaseBomPath;
	}

	/**
	 * setter purchaseBomPath(用户bom表的地址（当type=20时使用本字段）)
	 */	
	public void setPurchaseBomPath(String purchaseBomPath) {
		this.purchaseBomPath = purchaseBomPath;
	}

	/**
	 * getter purchaseBomNewPath(管理员上传的bom表地址（当type=20时使用本字段）)
	 */				
	public String getPurchaseBomNewPath() {
		return purchaseBomNewPath;
	}

	/**
	 * setter purchaseBomNewPath(管理员上传的bom表地址（当type=20时使用本字段）)
	 */	
	public void setPurchaseBomNewPath(String purchaseBomNewPath) {
		this.purchaseBomNewPath = purchaseBomNewPath;
	}

	/**
	 * getter purchaseUId(会员ID)
	 */				
	public Long getPurchaseUId() {
		return purchaseUId;
	}

	/**
	 * setter purchaseUId(会员ID)
	 */	
	public void setPurchaseUId(Long purchaseUId) {
		this.purchaseUId = purchaseUId;
	}

	/**
	 * getter purchaseType(类型：10快捷采购一句话采购，20BOM采购 30批量发布采购)
	 */				
	public String getPurchaseType() {
		return purchaseType;
	}

	/**
	 * setter purchaseType(类型：10快捷采购一句话采购，20BOM采购 30批量发布采购)
	 */	
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	/**
	 * getter purchaseStatus(采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消)
	 */				
	public String getPurchaseStatus() {
		return purchaseStatus;
	}

	/**
	 * setter purchaseStatus(采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消)
	 */	
	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter purchaseExpiryTime(采购到期时间)
	 */				
	public Date getPurchaseExpiryTime() {
		return purchaseExpiryTime;
	}

	/**
	 * setter purchaseExpiryTime(采购到期时间)
	 */	
	public void setPurchaseExpiryTime(Date purchaseExpiryTime) {
		this.purchaseExpiryTime = purchaseExpiryTime;
	}

	/**
	 * getter puCycle(交货周期)
	 */				
	public String getPuCycle() {
		return puCycle;
	}

	/**
	 * setter puCycle(交货周期)
	 */	
	public void setPuCycle(String puCycle) {
		this.puCycle = puCycle;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter purchaseCreateDate(创建时间)
	 */				
	public Date getPurchaseCreateDate() {
		return purchaseCreateDate;
	}

	/**
	 * setter purchaseCreateDate(创建时间)
	 */	
	public void setPurchaseCreateDate(Date purchaseCreateDate) {
		this.purchaseCreateDate = purchaseCreateDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter purchaseUpdateDate(更新时间)
	 */				
	public Date getPurchaseUpdateDate() {
		return purchaseUpdateDate;
	}

	/**
	 * setter purchaseUpdateDate(更新时间)
	 */	
	public void setPurchaseUpdateDate(Date purchaseUpdateDate) {
		this.purchaseUpdateDate = purchaseUpdateDate;
	}

	/**
	 * getter purchaseCounts(purchase_counts)
	 */				
	public Long getPurchaseCounts() {
		return purchaseCounts;
	}

	/**
	 * setter purchaseCounts(purchase_counts)
	 */	
	public void setPurchaseCounts(Long purchaseCounts) {
		this.purchaseCounts = purchaseCounts;
	}

	/**
	 * getter piId(采购详情id)
	 */				
	public Long getPiId() {
		return piId;
	}

	/**
	 * setter piId(采购详情id)
	 */	
	public void setPiId(Long piId) {
		this.piId = piId;
	}

	/**
	 * getter piName(产品名称)
	 */				
	public String getPiName() {
		return piName;
	}

	/**
	 * setter piName(产品名称)
	 */	
	public void setPiName(String piName) {
		this.piName = piName;
	}

	/**
	 * getter piModel(产品型号)
	 */				
	public String getPiModel() {
		return piModel;
	}

	/**
	 * setter piModel(产品型号)
	 */	
	public void setPiModel(String piModel) {
		this.piModel = piModel;
	}

	/**
	 * getter piBrand(产品品牌)
	 */				
	public String getPiBrand() {
		return piBrand;
	}

	/**
	 * setter piBrand(产品品牌)
	 */	
	public void setPiBrand(String piBrand) {
		this.piBrand = piBrand;
	}

	/**
	 * getter umLoginName(用户名、登录名)
	 */				
	public String getUmLoginName() {
		return umLoginName;
	}

	/**
	 * setter umLoginName(用户名、登录名)
	 */	
	public void setUmLoginName(String umLoginName) {
		this.umLoginName = umLoginName;
	}

	/**
	 * getter umMobile(手机号)
	 */				
	public String getUmMobile() {
		return umMobile;
	}

	/**
	 * setter umMobile(手机号)
	 */	
	public void setUmMobile(String umMobile) {
		this.umMobile = umMobile;
	}

	/**
	 * getter psId(采购空间id)
	 */				
	public Long getPsId() {
		return psId;
	}

	/**
	 * setter psId(采购空间id)
	 */	
	public void setPsId(Long psId) {
		this.psId = psId;
	}

	/**
	 * getter purchaseExpiryTime(采购到期时间)
	 */
	public Date getBeginPurchaseExpiryTime() {
		return beginPurchaseExpiryTime;
	}

	/**
	 * setter purchaseExpiryTime(采购到期时间)
	 */	
	public void setBeginPurchaseExpiryTime(Date beginPurchaseExpiryTime) {
		this.beginPurchaseExpiryTime = beginPurchaseExpiryTime;
	}
	
	/**
	 * getter purchaseExpiryTime(采购到期时间)
	 */		
	public Date getEndPurchaseExpiryTime() {
		return endPurchaseExpiryTime;
	}

	/**
	 * setter purchaseExpiryTime(采购到期时间)
	 */	
	public void setEndPurchaseExpiryTime(Date endPurchaseExpiryTime) {
		this.endPurchaseExpiryTime = endPurchaseExpiryTime;
	}
	/**
	 * getter purchaseCreateDate(创建时间)
	 */
	public Date getBeginPurchaseCreateDate() {
		return beginPurchaseCreateDate;
	}

	/**
	 * setter purchaseCreateDate(创建时间)
	 */	
	public void setBeginPurchaseCreateDate(Date beginPurchaseCreateDate) {
		this.beginPurchaseCreateDate = beginPurchaseCreateDate;
	}
	
	/**
	 * getter purchaseCreateDate(创建时间)
	 */		
	public Date getEndPurchaseCreateDate() {
		return endPurchaseCreateDate;
	}

	/**
	 * setter purchaseCreateDate(创建时间)
	 */	
	public void setEndPurchaseCreateDate(Date endPurchaseCreateDate) {
		this.endPurchaseCreateDate = endPurchaseCreateDate;
	}
	/**
	 * getter purchaseUpdateDate(更新时间)
	 */
	public Date getBeginPurchaseUpdateDate() {
		return beginPurchaseUpdateDate;
	}

	/**
	 * setter purchaseUpdateDate(更新时间)
	 */	
	public void setBeginPurchaseUpdateDate(Date beginPurchaseUpdateDate) {
		this.beginPurchaseUpdateDate = beginPurchaseUpdateDate;
	}
	
	/**
	 * getter purchaseUpdateDate(更新时间)
	 */		
	public Date getEndPurchaseUpdateDate() {
		return endPurchaseUpdateDate;
	}

	/**
	 * setter purchaseUpdateDate(更新时间)
	 */	
	public void setEndPurchaseUpdateDate(Date endPurchaseUpdateDate) {
		this.endPurchaseUpdateDate = endPurchaseUpdateDate;
	}
	
}