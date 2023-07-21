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

import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 第三方账户资金流水 Entity 子类，请把你的业务代码写在这里
 * @author 赵磊
 * @version 2018-07-13
 */
public class AccountThirdpartySn extends AccountThirdpartySnBase<AccountThirdpartySn> {
	
	private static final long serialVersionUID = 1L;
	public AccountThirdpartySn() {
		super();
	}

	public AccountThirdpartySn(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	/**
	 * getter money(交易金额)
	 */	
	@Override
	public BigDecimal getMoney() {
		if(super.getMoney()==null){
			return super.getMoney();
		}
		return super.getMoney().setScale(2, RoundingMode.HALF_UP);//保留两位小数;
//		String refund=super.getMoney().stripTrailingZeros().toPlainString();
//		return new BigDecimal(refund);
	}
	
	//支付方式信息
	private SettlementPayWay settlementPayWay;
	public SettlementPayWay getSettlementPayWay() {
		if(settlementPayWay!=null){
			return settlementPayWay;
		}
		SettlementPayWayDao settlementPayWayDao=SpringContextHolder.getBean(SettlementPayWayDao.class);
		settlementPayWay=settlementPayWayDao.selectById(this.getPayWayId());
		return settlementPayWay;
	}

	public void setSettlementPayWay(SettlementPayWay settlementPayWay) {
		this.settlementPayWay = settlementPayWay;
	}
}