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

import com.sicheng.common.persistence.DataEntity;

/**
 * 采购空间 Entity 父类
 * @author 蔡龙
 * @version 2018-06-10
 */
public class PurchaseSpaceBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long spaceId;                   // 采购空间id
	private Long uId;                       // 会员id
	private String banner;                  // 采购空间banner
	private String logo;                    // 采购空间logo
	private String name;                    // 采购空间名称
	private String synopsis;                // 采购空间简介
	private String isOpen;                  // 开启状态：0关闭，1开启
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	
	public PurchaseSpaceBase() {
		super();
	}

	public PurchaseSpaceBase(Long id){
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
	 * getter spaceId(采购空间id)
	 */				
	public Long getSpaceId() {
		return spaceId;
	}

	/**
	 * setter spaceId(采购空间id)
	 */	
	public void setSpaceId(Long spaceId) {
		this.spaceId = spaceId;
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
	 * getter banner(采购空间banner)
	 */				
	public String getBanner() {
		return banner;
	}

	/**
	 * setter banner(采购空间banner)
	 */	
	public void setBanner(String banner) {
		this.banner = banner;
	}

	/**
	 * getter logo(采购空间logo)
	 */				
	public String getLogo() {
		return logo;
	}

	/**
	 * setter logo(采购空间logo)
	 */	
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * getter name(采购空间名称)
	 */				
	public String getName() {
		return name;
	}

	/**
	 * setter name(采购空间名称)
	 */	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter synopsis(采购空间简介)
	 */				
	public String getSynopsis() {
		return synopsis;
	}

	/**
	 * setter synopsis(采购空间简介)
	 */	
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	/**
	 * getter isOpen(is_open)
	 */				
	public String getIsOpen() {
		return isOpen;
	}

	/**
	 * setter isOpen(is_open)
	 */	
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
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