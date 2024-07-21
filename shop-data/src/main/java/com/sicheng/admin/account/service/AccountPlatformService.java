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

import com.sicheng.admin.account.dao.AccountPlatformDao;
import com.sicheng.admin.account.entity.AccountPlatform;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 平台账户 Service
 * @author zhaolei
 * @version 2018-07-13
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class AccountPlatformService extends CrudService<AccountPlatformDao, AccountPlatform> {

	//请在这里编写业务逻辑
	
	//父类中20个单表操作的常用方法，已全部继承下来，可直接使用。
	
	//注意：把多条业务sql包在一个事务中
	
	/**
	 * 原子累加累减账户中的金额
	 * 根据主键更新记录,只把非空的值更到对应的字段
	 * @param entity 数据实体，用于存储数据，这些数据将被update到表中
	 * @return 受影响的行数
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateByIdSelectiveAtom(AccountPlatform entity) {
		return dao.updateByIdSelectiveAtom(entity);
	}
	
	/**
	 * 平台申请提现，原子操作，防止超额提现
	 * @param apId 平台账户ID
	 * @param money 金额
	 * @return 1表示成功，0表示未找到账户或超额提现被拦截
	 */
	public int withdrawApplyPlatform(Long apId,BigDecimal money) {
		return dao.withdrawApplyPlatform(apId, money);
	}
	
	/**
	 * 平台执行提现，原子操作，防止超额提现
	 * @param apId 平台账户ID
	 * @param money 金额
	 * @return 1表示成功，0表示未找到账户或超额提现被拦截
	 */
	public int withdrawExecutePlatform(Long apId,BigDecimal money) {
		return dao.withdrawExecutePlatform(apId, money);
	}
	
	/**
	 * 拒绝平台提现（资金解冻了），原子操作，防止超额解冻
	 * @param apId 平台账户ID
	 * @param money 金额
	 * @return 1表示成功，0表示未找到账户或超额解冻被拦截
	 */
	public int withdrawRefusePlatform(Long apId,BigDecimal money) {
		return dao.withdrawRefusePlatform(apId, money);
	}
	
}