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
package com.sicheng.admin.site.entity;

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 特版明细福管理 Entity 父类
 * @author zjl
 * @version 2018-06-01
 */
public class SiteSpecialEditionDetailBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long seDetailId;                // 主键(特版详情id)
	private Long seId;                      // 特版id(关联特版表)
	private String content;                 // 特版详情内容
	private Integer sort;                   // 排序
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	
	public SiteSpecialEditionDetailBase() {
		super();
	}

	public SiteSpecialEditionDetailBase(Long id){
		super(id);
		this.seDetailId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return seDetailId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.seDetailId = id;
	}

	/**
	 * getter seDetailId(主键(特版详情id))
	 */				
	public Long getSeDetailId() {
		return seDetailId;
	}

	/**
	 * setter seDetailId(主键(特版详情id))
	 */	
	public void setSeDetailId(Long seDetailId) {
		this.seDetailId = seDetailId;
	}

	/**
	 * getter seId(特版id(关联特版表))
	 */				
	public Long getSeId() {
		return seId;
	}

	/**
	 * setter seId(特版id(关联特版表))
	 */	
	public void setSeId(Long seId) {
		this.seId = seId;
	}

	/**
	 * getter content(特版详情内容)
	 */				
	public String getContent() {
		return content;
	}

	/**
	 * setter content(特版详情内容)
	 */	
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * getter sort(排序)
	 */				
	public Integer getSort() {
		return sort;
	}

	/**
	 * setter sort(排序)
	 */	
	public void setSort(Integer sort) {
		this.sort = sort;
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