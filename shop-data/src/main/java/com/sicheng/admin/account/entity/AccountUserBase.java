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

import java.math.BigDecimal;
import java.util.Date;

import com.sicheng.common.persistence.DataEntity;

/**
 * 会员账户 Entity 父类
 * @author 赵磊
 * @version 2018-07-13
 */
public class AccountUserBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long auId;                      // 会员账户id
	private Long uId;                       // 会员id
	private Integer accountType;             // 账户类型（0.商家账户、1.服务账户）
	private BigDecimal ownMoney;            // 会员账户余额
	private BigDecimal frozenMoney;         // 会员冻结金额
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	public AccountUserBase() {
		super();
	}

	public AccountUserBase(Long id){
		super(id);
		this.auId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return auId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.auId = id;
	}

	/**
	 * getter auId(会员账户id)
	 */				
	public Long getAuId() {
		return auId;
	}

	/**
	 * setter auId(会员账户id)
	 */	
	public void setAuId(Long auId) {
		this.auId = auId;
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
	 * getter accountType(账户类型（0.商家账户、1.服务账户）)
	 */				
	public Integer getAccountType() {
		return accountType;
	}

	/**
	 * setter accountType(账户类型（0.商家账户、1.服务账户）)
	 */	
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	/**
	 * getter ownMoney(会员账户余额)
	 */				
	public BigDecimal getOwnMoney() {
		return ownMoney;
	}

	/**
	 * setter ownMoney(会员账户余额)
	 */	
	public void setOwnMoney(BigDecimal ownMoney) {
		this.ownMoney = ownMoney;
	}

	/**
	 * getter frozenMoney(会员冻结金额)
	 */				
	public BigDecimal getFrozenMoney() {
		return frozenMoney;
	}

	/**
	 * setter frozenMoney(会员冻结金额)
	 */	
	public void setFrozenMoney(BigDecimal frozenMoney) {
		this.frozenMoney = frozenMoney;
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