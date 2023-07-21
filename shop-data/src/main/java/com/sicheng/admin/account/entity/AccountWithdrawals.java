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
import java.util.List;

import com.sicheng.admin.account.dao.AccountPlatformDao;
import com.sicheng.admin.account.dao.AccountTiedCardDao;
import com.sicheng.admin.account.dao.AccountUserDao;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 账户提现 Entity 子类，请把你的业务代码写在这里
 * @author 蔡龙
 * @version 2018-07-14
 */
public class AccountWithdrawals extends AccountWithdrawalsBase<AccountWithdrawals> {
	
	private static final long serialVersionUID = 1L;
	public AccountWithdrawals() {
		super();
	}

	public AccountWithdrawals(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	private AccountTiedCard accountTiedCard;	//账户绑卡表
	public AccountTiedCard getAccountTiedCard() {
		if(accountTiedCard==null){
			AccountTiedCardDao dao=SpringContextHolder.getBean(AccountTiedCardDao.class);
			accountTiedCard=dao.selectById(this.getTiedCardId());
		}
		return accountTiedCard;
	}

	public void setAccountTiedCard(AccountTiedCard accountTiedCard) {
		this.accountTiedCard = accountTiedCard;
	}
	
	private AccountUser accountUser;	//会员账户id
	public AccountUser getAccountUser() {
		if(accountUser==null){
			AccountUserDao dao=SpringContextHolder.getBean(AccountUserDao.class);
			accountUser=dao.selectById(this.getAccountId());
		}
		return accountUser;
	}

	public void setAccountUser(AccountUser accountUser) {
		this.accountUser = accountUser;
	}
	
	private AccountPlatform accountPlatform;	//平台账户id
	public AccountPlatform getAccountPlatform() {
		if(accountPlatform==null){
			AccountPlatformDao dao=SpringContextHolder.getBean(AccountPlatformDao.class);
			accountPlatform=dao.selectById(this.getAccountId());
		}
		return accountPlatform;
	}

	public void setAccountPlatform(AccountPlatform accountPlatform) {
		this.accountPlatform = accountPlatform;
	}

	public static void fillAccountTiedCard(List<AccountWithdrawals> list){
		List<Object> ids=batchField(list,"tiedCardId");//批量调用对象的getXxx()方法
		AccountTiedCardDao dao=SpringContextHolder.getBean(AccountTiedCardDao.class);
		List<AccountTiedCard> accountTiedCardlist=dao.selectByIdIn(ids);
		fill(accountTiedCardlist,"tiedCardId",list,"tiedCardId","accountTiedCard");//循环填充
	}
	
	/**
	 * 金额
	 * @return
	 */
	@Override
	public BigDecimal getMoney() {
		if(super.getMoney()==null){
			return super.getMoney();
		}
		String refund=super.getMoney().stripTrailingZeros().toPlainString();
		return new BigDecimal(refund);
	}
	
	
}