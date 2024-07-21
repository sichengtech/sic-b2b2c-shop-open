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
package com.sicheng.seller.purchase.service;

import com.sicheng.common.persistence.wrapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.purchase.dao.PurchaseTradeVoucherDao;
import com.sicheng.admin.purchase.entity.PurchaseTradeOrder;
import com.sicheng.admin.purchase.entity.PurchaseTradeVoucher;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.service.CrudService;
import com.sicheng.sso.utils.SsoUtils;

import java.util.List;

/**
 * 采购交易凭证 Service
 * @author cl
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

	/**
	 * 上传交易凭证
	 */
	@Transactional(rollbackFor=Exception.class)
	public void uploadVoucher(PurchaseTradeVoucher purchaseTradeVoucher){
		UserMain userMain = SsoUtils.getUserMain();
		//修改采购订单表
		PurchaseTradeOrder purchaseTradeOrder = new PurchaseTradeOrder();
		purchaseTradeOrder.setPurchaseTradeId(purchaseTradeVoucher.getPurchaseTradeId());
		purchaseTradeOrder.setStatus("20");//10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成
		purchaseTradeOrderService.updateByIdSelective(purchaseTradeOrder);
		//查询交易凭证
		PurchaseTradeVoucher ptv = new PurchaseTradeVoucher();
		ptv.setPurchaseTradeId(purchaseTradeVoucher.getPurchaseTradeId());
		ptv.setUId(userMain.getUId());
		List<PurchaseTradeVoucher> purchaseTradeVoucherList = this.selectByWhere(new Wrapper(ptv));
		if (purchaseTradeVoucherList.isEmpty()){
			purchaseTradeVoucher.setUId(userMain.getUId());
			purchaseTradeVoucher.setStatus("10");//审核状态10.审核中20.审核未通过30.审核通过
			this.insertSelective(purchaseTradeVoucher);
		}else{
			purchaseTradeVoucher.setTradeVoucherId(purchaseTradeVoucherList.get(0).getTradeVoucherId());
			purchaseTradeVoucher.setUId(userMain.getUId());
			purchaseTradeVoucher.setStatus("10");//审核状态10.审核中20.审核未通过30.审核通过
			this.updateByIdSelective(purchaseTradeVoucher);
		}
	}
	
}