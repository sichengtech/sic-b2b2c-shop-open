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
package com.sicheng.admin.purchase.entity;

import java.math.BigDecimal;
import java.util.List;

import com.sicheng.admin.purchase.dao.PurchaseTradeOrderDao;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 采购交易凭证 Entity 子类，请把你的业务代码写在这里
 * @author 蔡龙
 * @version 2018-06-10
 */
public class PurchaseTradeVoucher extends PurchaseTradeVoucherBase<PurchaseTradeVoucher> {
	
	private static final long serialVersionUID = 1L;
	public PurchaseTradeVoucher() {
		super();
	}

	public PurchaseTradeVoucher(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	private UserMain userMain; //会员总表
	public UserMain getUserMain() {
		if(userMain==null){
			UserMainDao dao=SpringContextHolder.getBean(UserMainDao.class);
			userMain=dao.selectById(this.getUId());
		}
		return userMain;
	}

	public void setUserMain(UserMain userMain) {
		this.userMain = userMain;
	}
	
	public static void fillUserMain(List<PurchaseTradeVoucher> list){
		List<Object> ids=batchField(list,"uId");//批量调用对象的getXxx()方法
		UserMainDao dao=SpringContextHolder.getBean(UserMainDao.class);
		List<UserMain> userMainlist=dao.selectByIdIn(ids);
		fill(userMainlist,"uId",list,"uId","userMain");//循环填充
	}
	
	private PurchaseTradeOrder purchaseTradeOrder; //撮合采购订单
	public PurchaseTradeOrder getPurchaseTradeOrder() {
		if(purchaseTradeOrder==null){
			PurchaseTradeOrderDao dao=SpringContextHolder.getBean(PurchaseTradeOrderDao.class);
			purchaseTradeOrder=dao.selectById(this.getPurchaseTradeId());//采购订单id 关联purchase_trade_order(供采交易订单表)
		}
		return purchaseTradeOrder;
	}

	public void setPurchaseTradeOrder(PurchaseTradeOrder purchaseTradeOrder) {
		this.purchaseTradeOrder = purchaseTradeOrder;
	}
	
	/**
	 * 金额
	 */	
	@Override
	public BigDecimal getMoney() {
		if(super.getMoney()==null){
			return super.getMoney();
		}
		String refund=super.getMoney().stripTrailingZeros().toPlainString();
		return new BigDecimal(refund);
	}
	
}