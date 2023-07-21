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
package com.sicheng.seller.account.service;

import com.sicheng.admin.account.dao.AccountWithdrawalsDao;
import com.sicheng.admin.account.entity.AccountWithdrawals;
import com.sicheng.admin.account.service.AccountService;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账户提现 Service
 * @author 蔡龙
 * @version 2018-07-14
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class AccountWithdrawalsService extends CrudService<AccountWithdrawalsDao, AccountWithdrawals> implements Serializable {

	//请在这里编写业务逻辑
	
	//父类中20个单表操作的常用方法，已全部继承下来，可直接使用。
	
	//注意：把多条业务sql包在一个事务中
	
	@Autowired
	private AccountService accountService;
	
	/**
	 *	会员申请提现 
	 * @param accountId		账户id
	 * @param tiedCardId	提现卡号id
	 * @param money			提现金额
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String applyWithdrawCash(Long accountId,Long tiedCardId,String money) {
		//调取申请提现方法
		accountService.withdrawApplyUser(accountId,new BigDecimal(money));
		//申请提现
		AccountWithdrawals accountWithdrawals = new AccountWithdrawals();
		accountWithdrawals.setAccountId(accountId);
		accountWithdrawals.setStatus("1");//类型（1会员提现，2平台提现）
		accountWithdrawals.setTiedCardId(tiedCardId);
		accountWithdrawals.setMoney(new BigDecimal(money));
		accountWithdrawals.setAuditStatus("0");//审核是否通过（0待审核，1审核同意，2审核失败）
		accountWithdrawals.setIsPay("0");//是否支付（0未支付、1已支付）
		super.insertSelective(accountWithdrawals);
		return FYUtils.fyParams("申请提现成功");
	}
}