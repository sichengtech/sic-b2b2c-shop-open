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

import java.math.BigDecimal;
import java.util.Date;

import com.sicheng.common.persistence.DataEntity;

/**
 * 撮合交易订单 Entity 父类
 * @author 蔡龙
 * @version 2018-07-20
 */
public class PurchaseTradeOrderBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long purchaseTradeId;           // 订单id
	private Long purchaseUId;               // 采购方
	private Long offerUId;                  // 报价方
	private Long purchaseId;                // 采购单id
	private String title;                   // 采购标题
	private String content;                 // 采购内容
	private String bomPath;                 // 用户bom表的地址（当type=20时使用本字段）
	private BigDecimal price;               // 总价格
	private String type;                    // 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
	private String status;                  // 订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public PurchaseTradeOrderBase() {
		super();
	}

	public PurchaseTradeOrderBase(Long id){
		super(id);
		this.purchaseTradeId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return purchaseTradeId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)   
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.purchaseTradeId = id;
	}

	/**
	 * getter purchaseTradeId(订单id)
	 */				
	public Long getPurchaseTradeId() {
		return purchaseTradeId;
	}

	/**
	 * setter purchaseTradeId(订单id)
	 */	
	public void setPurchaseTradeId(Long purchaseTradeId) {
		this.purchaseTradeId = purchaseTradeId;
	}

	/**
	 * getter purchaseUId(采购方)
	 */				
	public Long getPurchaseUId() {
		return purchaseUId;
	}

	/**
	 * setter purchaseUId(采购方)
	 */	
	public void setPurchaseUId(Long purchaseUId) {
		this.purchaseUId = purchaseUId;
	}

	/**
	 * getter offerUId(报价方)
	 */				
	public Long getOfferUId() {
		return offerUId;
	}

	/**
	 * setter offerUId(报价方)
	 */	
	public void setOfferUId(Long offerUId) {
		this.offerUId = offerUId;
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
	 * getter price(总价格)
	 */				
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * setter price(总价格)
	 */	
	public void setPrice(BigDecimal price) {
		this.price = price;
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
	 * getter status(订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成)
	 */				
	public String getStatus() {
		return status;
	}

	/**
	 * setter status(订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成)
	 */	
	public void setStatus(String status) {
		this.status = status;
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