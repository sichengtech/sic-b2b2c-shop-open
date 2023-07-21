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

/**
 * 平台账户流水 Entity 子类，请把你的业务代码写在这里
 * @author 赵磊
 * @version 2018-07-13
 */
public class AccountPlatformSn extends AccountPlatformSnBase<AccountPlatformSn> {
	
	private static final long serialVersionUID = 1L;
	public AccountPlatformSn() {
		super();
	}

	public AccountPlatformSn(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	/**
	 * getter incomeMoney(收入金额)
	 */	
	@Override
	public BigDecimal getIncomeMoney() {
		if(super.getIncomeMoney()==null){
			return super.getIncomeMoney();
		}
		return super.getIncomeMoney().setScale(2, RoundingMode.HALF_UP);//保留两位小数;
//		String refund=super.getIncomeMoney().stripTrailingZeros().toPlainString();
//		return new BigDecimal(refund);
	}

	/**
	 * getter expensesMoney(支出金额)
	 */		
	@Override
	public BigDecimal getExpensesMoney() {
		if(super.getExpensesMoney()==null){
			return super.getExpensesMoney();
		}
		return super.getExpensesMoney().setScale(2, RoundingMode.HALF_UP);//保留两位小数;
//		String refund=super.getExpensesMoney().stripTrailingZeros().toPlainString();
//		return new BigDecimal(refund);
	}
}