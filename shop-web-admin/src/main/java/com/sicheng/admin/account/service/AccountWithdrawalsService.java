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
package com.sicheng.admin.account.service;

import com.sicheng.admin.account.dao.AccountWithdrawalsDao;
import com.sicheng.admin.account.entity.AccountWithdrawals;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 账户提现 Service
 * @author 蔡龙
 * @version 2018-07-15
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class AccountWithdrawalsService extends CrudService<AccountWithdrawalsDao, AccountWithdrawals> {

	//请在这里编写业务逻辑
	
	//父类中20个单表操作的常用方法，已全部继承下来，可直接使用。
	
	//注意：把多条业务sql包在一个事务中
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 审核提现
	 */
	@Transactional(rollbackFor=Exception.class)
	public String auth(AccountWithdrawals accountWithdrawals) {
		AccountWithdrawals adw = super.selectById(accountWithdrawals.getId());
		String auditStatus = accountWithdrawals.getAuditStatus();
		if("2".equals(auditStatus)){
			//记录流水表
			accountService.withdrawRefuseUser(adw.getAccountId(), adw.getMoney());
		}
		//修改提现表
		super.updateByIdSelective(accountWithdrawals);
		return FYUtils.fyParams("审核提现成功");
	}
	
	/**
	 * 支付
	 * @param status	1会员2平台
	 * @param accountWithdrawals
	 */
	@Transactional(rollbackFor=Exception.class)
	public String pay(String status,AccountWithdrawals accountWithdrawals) {
		AccountWithdrawals adw = super.selectById(accountWithdrawals.getId());
		//记录账户流水
		if("1".equals(status)){
			accountService.withdrawExecuteUser(adw.getId(),adw.getAccountId(), adw.getMoney(), -1L, "-1", "-1");
		}
		if("2".equals(status)){
			accountService.withdrawExecutePlatform(adw.getId(),adw.getAccountId(), adw.getMoney(), -1L, "-1", "-1");
		}
		//修改提现表
		accountWithdrawals.setIsPay("1");//是否支付（0未支付、1已支付）
		accountWithdrawals.setPayTime(new Date());
		super.updateByIdSelective(accountWithdrawals);
		return FYUtils.fyParams("支付成功");
	}
	
	/**
	 * 平台提现
	 * @param accountWithdrawals
	 */
	@Transactional(rollbackFor=Exception.class)
	public String withdrawal(AccountWithdrawals accountWithdrawals) {
		//记录账户流水
		accountService.withdrawApplyPlatform(11L, accountWithdrawals.getMoney());
		//修改提现表
		accountWithdrawals.setAccountId(11L);
		accountWithdrawals.setStatus("2");//类型（1会员提现，2平台提现）
		accountWithdrawals.setAuditStatus("1");//审核是否通过（0待审核，1审核同意，2审核失败）
		accountWithdrawals.setIsPay("0");//是否支付（0未支付、1已支付）
		super.insertSelective(accountWithdrawals);
		return FYUtils.fyParams("申请提现成功");

	}
	
}