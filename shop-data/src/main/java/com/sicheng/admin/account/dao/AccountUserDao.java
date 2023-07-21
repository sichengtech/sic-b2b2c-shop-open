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

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.sicheng.admin.account.entity.AccountUser;
import com.sicheng.common.persistence.CrudDao;
import com.sicheng.common.persistence.annotation.MyBatisDao;

/**
 * 会员账户DAO接口
 * @author 赵磊
 * @version 2018-07-13
 */
@MyBatisDao
public interface AccountUserDao extends CrudDao<AccountUser> {

	//请在这里增加你自己的DAO层方法
	
	//14条单表操作的通用SQL调用方法都在父类中，全继承下来了，可直接使用。
	
	/**
	 * 原子累加累减账户中的金额
	 * 根据主键更新记录,只把非空的值更到对应的字段
	 * @param entity 数据实体，用于存储数据，这些数据将被update到表中
	 * @return 受影响的行数
	 */
	public int updateByIdSelectiveAtom(@Param(value = "entity") AccountUser entity);
	
	/**
	 * 会员申请提现（资金冻结了），原子操作，防止超额提现
	 * @param auId 会员账户ID
	 * @param money 金额
	 * @return 1表示成功，0表示未找到账户或超额提现被拦截
	 */
	public int withdrawApplyUser(@Param(value = "auId") Long auId, @Param(value = "money") BigDecimal money);
	
	/**
	 * 会员执行提现，原子操作，防止超额提现
	 * @param auId 平台账户ID
	 * @param money 金额
	 * @return 1表示成功，0表示未找到账户或超额提现被拦截
	 */
	public int withdrawExecuteUser(@Param(value = "auId") Long auId, @Param(value = "money") BigDecimal money);
	
	/**
	 * 拒绝会员提现（资金解冻了），原子操作，防止超额解冻
	 * @param auId 会员账户ID
	 * @param money 金额
	 * @return 1表示成功，0表示未找到账户或超额解冻被拦截
	 */
	public int withdrawRefuseUser(@Param(value = "auId") Long auId, @Param(value = "money") BigDecimal money);
}