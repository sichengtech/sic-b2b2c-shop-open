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
package com.sicheng.admin.account.dao;

import com.sicheng.admin.account.entity.AccountPlatform;
import com.sicheng.common.persistence.CrudDao;
import com.sicheng.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 平台账户DAO接口
 * @author 赵磊
 * @version 2018-07-13
 */
@MyBatisDao
public interface AccountPlatformDao extends CrudDao<AccountPlatform> {

	//请在这里增加你自己的DAO层方法

	//14条单表操作的通用SQL调用方法都在父类中，全继承下来了，可直接使用。

	/**
	 * 原子累加累减账户中的金额
	 * 根据主键更新记录,只把非空的值更到对应的字段
	 * @param entity 数据实体，用于存储数据，这些数据将被update到表中
	 * @return 受影响的行数
	 */
	public int updateByIdSelectiveAtom(@Param(value = "entity") AccountPlatform entity);

	/**
	 * 平台申请提现（资金冻结了），原子操作，防止超额提现
	 * @param apId 平台账户ID
	 * @param money 金额
	 * @return 1表示成功，0表示未找到账户或超额提现被拦截
	 */
	public int withdrawApplyPlatform(@Param(value = "apId") Long apId, @Param(value = "money") BigDecimal money);

	/**
	 * 平台执行提现，原子操作，防止超额提现
	 * @param apId 平台账户ID
	 * @param money 金额
	 * @return 1表示成功，0表示未找到账户或超额提现被拦截
	 */
	public int withdrawExecutePlatform(@Param(value = "apId") Long apId, @Param(value = "money") BigDecimal money);

	/**
	 * 拒绝平台提现（资金解冻了），原子操作，防止超额解冻
	 * @param apId 平台账户ID
	 * @param money 金额
	 * @return 1表示成功，0表示未找到账户或超额解冻被拦截
	 */
	public int withdrawRefusePlatform(@Param(value = "apId") Long apId, @Param(value = "money") BigDecimal money);

	/**
	 * 统计 平台现有资金总额
	 * 平台现有资金总额 = 平台未结账户金额 + 平台营收账户余额  + 平台营收账户冻结金额 + 每个会员账户余额 + 每个会员账户冻结金额 - 补贴账户 。（商家支付服务费用）
	 * 平台现有资金总额 = 平台未结账户金额 + 平台营收账户余额  + 平台营收账户冻结金额 + 每个会员账户余额 + 每个会员账户冻结金额 - 补贴账户 。（平台支付服务费用）
	 * 1、不支持买家账户，无需要计算买家账户
	 * 2、平台手续费账户中的钱不计算在内
	 * @return
	 */
	public BigDecimal countAllMoney();

	/**
	 * 计算各个账户钱
	 * 10：未结算账户(临时)总额
	 * 11：营收账户(收入)总额
	 * 14：补贴账户(支出)总额
	 * 20：商家账户总额
	 */
	public BigDecimal sumMoney(@Param(value = "v") String v);
}