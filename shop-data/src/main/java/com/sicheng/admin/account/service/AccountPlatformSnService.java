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

import com.sicheng.admin.account.dao.AccountPlatformSnDao;
import com.sicheng.admin.account.entity.AccountPlatformSn;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 平台账户流水 Service
 * @author 赵磊
 * @version 2018-07-13
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class AccountPlatformSnService extends CrudService<AccountPlatformSnDao, AccountPlatformSn> {

	//请在这里编写业务逻辑
	
	//父类中20个单表操作的常用方法，已全部继承下来，可直接使用。
	
	//注意：把多条业务sql包在一个事务中
	
	/**
	 * 支付手续费用总和
	 */
	@Transactional(rollbackFor=Exception.class)
	public BigDecimal payCommissionTotal(List<String> serialNumberList) {
		BigDecimal payCommissionTotal = dao.payCommissionTotal(serialNumberList);
		if(payCommissionTotal==null){
			payCommissionTotal = new BigDecimal("0");
		}
		return payCommissionTotal;
		
	}
	
	/**
	 * 支付退回手续费用总和
	 */
	@Transactional(rollbackFor=Exception.class)
	public BigDecimal refundCommissionTotal(List<String> serialNumberList) {
		BigDecimal refundCommissionTotal = dao.refundCommissionTotal(serialNumberList);
		if(refundCommissionTotal==null){
			refundCommissionTotal = new BigDecimal("0");
		}
		return refundCommissionTotal;
		
	}
	
}