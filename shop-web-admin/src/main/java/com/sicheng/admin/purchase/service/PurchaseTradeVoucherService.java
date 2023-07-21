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
package com.sicheng.admin.purchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.purchase.dao.PurchaseTradeVoucherDao;
import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.entity.PurchaseTradeOrder;
import com.sicheng.admin.purchase.entity.PurchaseTradeVoucher;
import com.sicheng.common.service.CrudService;

/**
 * 采购交易凭证 Service
 * @author 蔡龙
 * @version 2018-06-10
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class PurchaseTradeVoucherService extends CrudService<PurchaseTradeVoucherDao, PurchaseTradeVoucher> {

	//请在这里编写业务逻辑
	
	//父类中20个单表操作的常用方法，已全部继承下来，可直接使用。
	
	//注意：把多条业务sql包在一个事务中
	
	@Autowired
	private PurchaseTradeOrderService purchaseTradeOrderService;
	@Autowired
	private PurchaseService purchaseService;
	
	/**
	 * 更新交易凭证状态、交易订单状态、采购单状态
	 * @param purchaseTradeVoucher 交易凭证实体
	 * @param purchaseTradeOrder 交易订单实体
	 * @param purchase 采购单实体
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updatePurchaseTrade(PurchaseTradeVoucher purchaseTradeVoucher,PurchaseTradeOrder purchaseTradeOrder,Purchase purchase) {
		//更新交易凭证状态
		super.updateByIdSelective(purchaseTradeVoucher);
		//更新交易订单状态
		purchaseTradeOrderService.updateByIdSelective(purchaseTradeOrder);
		//更新采购单
		if(purchase!=null && purchase.getPurchaseId()!=null){
			purchaseService.updateByIdSelective(purchase);
		}
	}
}