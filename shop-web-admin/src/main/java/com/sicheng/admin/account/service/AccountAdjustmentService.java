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
package com.sicheng.admin.account.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.account.dao.AccountPlatformDao;
import com.sicheng.admin.account.entity.AccountPlatform;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.StringUtils;

/**
 * 平台账户 Service
 * @author zhaolei
 * @version 2018-07-13
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class AccountAdjustmentService extends CrudService<AccountPlatformDao, AccountPlatform> {

	//请在这里编写业务逻辑
	
	//父类中20个单表操作的常用方法，已全部继承下来，可直接使用。
	
	//注意：把多条业务sql包在一个事务中
	
	@Autowired
	AccountUserService accountUserService;//会员账户
	@Autowired
	AccountUserSnService accountUserSnService;//会员账户流水
	@Autowired
	AccountPlatformService accountPlatformService;//平台账户
	@Autowired
	AccountPlatformSnService accountPlatformSnService;//平台账户流水
	@Autowired
	AccountService accountService;
	
	/**
	 * 调账，当后台管理员发现账单错误时，进行此操作，只是同时调节三个账户
	 * @param account1  调节1号账户的具体账户
	 * @param operation1 调节1号账户金额的类型0.减少、1.增加
	 * @param money1 调节1号账户的金额
	 * @param account2 调节2号账户的具体账户
	 * @param operation2 调节2号账户金额的类型0.减少、1.增加
	 * @param money2 调节2号账户的金额
	 * @param account3  调节3号账户的具体账户
	 * @param operation3 调节3号账户金额的类型0.减少、1.增加
	 * @param money3 调节3号账户的金额
	 * @param cause 调账原因
	 */
	@Transactional(rollbackFor=Exception.class)
	public void adjustmentAccount(String account1,String operation1,String money1,
			String account2,String operation2,String money2,
			String account3,String operation3,String money3,String cause) {
		String sn=IdGen.snowflake().toString();//生成流水号
		String tips = "";//调账结果信息
		//操作账户1 
		if(StringUtils.isNotBlank(account1) && StringUtils.isNotBlank(money1)){
			String tips1=updateAccount(account1, operation1, money1, sn, cause);
			if(StringUtils.isNotBlank(tips1)){
				tips="操作账户1"+tips1;
			}
		}
		//操作账户2 
		if(StringUtils.isNotBlank(account2) && StringUtils.isNotBlank(money2)){
			String tips2=updateAccount(account2, operation2, money2, sn, cause);
			if(StringUtils.isNotBlank(tips2)){
				tips+=",操作账户2"+tips2;
			}
		}
		//操作账户3 
		if(StringUtils.isNotBlank(account3) && StringUtils.isNotBlank(money3)){
			String tips3=updateAccount(account3, operation3, money3, sn, cause);
			if(StringUtils.isNotBlank(tips3)){
				tips+=",操作账户3"+tips3;
			}
		}
		if(StringUtils.isNotBlank(tips)){
			throw new RuntimeException(tips);
		}
	}
	
	/**
	 * 调账业务操作
	 * @param account 调账操作的账户
	 * @param operation 调账金额的类型0.减少、1.增加
	 * @param accountMoney 调账的金额
	 * @param sn 流水号
	 * @param cause 调账原因
	 */
	private String updateAccount(String account,String operation,String accountMoney,String sn,String cause) {
		BigDecimal money=new BigDecimal(accountMoney);
		String[] accounts=account.split(",");
		String accountType=accounts[0];//账户类型:1.平台账户、2.会员账户
		String accountId=accounts[1];//账户id
		//平台账户余额的变化
		AccountPlatform ap=new AccountPlatform();
		ap.setApId(Long.valueOf(accountId));//未结算账户的ID是10
		if("0".equals(operation)){
			money=money.negate();//减少账户金额操作,转成负数
		}
		String tips = "";
		try {
			accountService.accountAdjustment(Integer.valueOf(accountType), Long.valueOf(accountId), money, sn, cause);
			return tips;
		} catch (Exception e) {
			logger.error("验收报错:",e.getMessage());
		    tips = e.getMessage();
		    return tips;
		}
	}
}