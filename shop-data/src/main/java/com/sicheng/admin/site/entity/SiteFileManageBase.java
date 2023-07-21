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
package com.sicheng.admin.site.entity;

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 文件管理器 Entity 父类
 * @author 范秀秀
 * @version 2018-06-01
 */
public class SiteFileManageBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long sfId;                      // 主键
	private String category;                // 文件分类
	private String name;                    // 文件名
	private String path;                    // 文件地址
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	
	public SiteFileManageBase() {
		super();
	}

	public SiteFileManageBase(Long id){
		super(id);
		this.sfId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return sfId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.sfId = id;
	}

	/**
	 * getter sfId(主键)
	 */				
	public Long getSfId() {
		return sfId;
	}

	/**
	 * setter sfId(主键)
	 */	
	public void setSfId(Long sfId) {
		this.sfId = sfId;
	}

	/**
	 * getter category(文件分类)
	 */				
	public String getCategory() {
		return category;
	}

	/**
	 * setter category(文件分类)
	 */	
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * getter name(文件名)
	 */				
	public String getName() {
		return name;
	}

	/**
	 * setter name(文件名)
	 */	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter path(文件地址)
	 */				
	public String getPath() {
		return path;
	}

	/**
	 * setter path(文件地址)
	 */	
	public void setPath(String path) {
		this.path = path;
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