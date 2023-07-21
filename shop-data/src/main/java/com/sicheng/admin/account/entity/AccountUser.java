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
import java.math.RoundingMode;

import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 会员账户 Entity 子类，请把你的业务代码写在这里
 * @author 赵磊
 * @version 2018-07-13
 */
public class AccountUser extends AccountUserBase<AccountUser> {
	
	private static final long serialVersionUID = 1L;
	public AccountUser() {
		super();
	}

	public AccountUser(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	/**
	 * getter ownMoney(会员账户余额)
	 */		
	@Override
	public BigDecimal getOwnMoney() {
		if(super.getOwnMoney()==null){
			return super.getOwnMoney();
		}
//		return super.getOwnMoney().stripTrailingZeros();//去掉0
		return super.getOwnMoney().setScale(2, RoundingMode.HALF_UP);//保留两位小数;
	}

	/**
	 * getter frozenMoney(会员冻结金额)
	 */	
	@Override
	public BigDecimal getFrozenMoney() {
		if(super.getFrozenMoney()==null){
			return super.getFrozenMoney();
		}
//		return super.getFrozenMoney().stripTrailingZeros();//去掉0
		return super.getFrozenMoney().setScale(2, RoundingMode.HALF_UP);//保留两位小数;
	}
	
	//会员信息
	private UserMain userMain;
	public UserMain getUserMain() {
		if(userMain!=null){
			return userMain;
		}
		UserMainDao userMainDao=SpringContextHolder.getBean(UserMainDao.class);
		userMain=userMainDao.selectById(this.getUId());
		return userMain;
	}

	public void setUserMain(UserMain userMain) {
		this.userMain = userMain;
	}
	
}