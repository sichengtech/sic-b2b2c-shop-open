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

import com.sicheng.admin.purchase.dao.PurchaseDao;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 采购单明细 Entity 子类，请把你的业务代码写在这里
 * @author 蔡龙
 * @version 2018-06-10
 */
public class PurchaseItem extends PurchaseItemBase<PurchaseItem> {
	
	private static final long serialVersionUID = 1L;
	public PurchaseItem() {
		super();
	}

	public PurchaseItem(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	private Purchase purchase;	//采购单
	public Purchase getPurchase() {
		if(purchase==null){
			PurchaseDao dao=SpringContextHolder.getBean(PurchaseDao.class);
			purchase=dao.selectById(this.getPurchaseId());
		}
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	public static void fillPurchase(List<PurchaseItem> list){
		List<Object> ids=batchField(list,"purchaseId");//批量调用对象的getXxx()方法
		PurchaseDao dao=SpringContextHolder.getBean(PurchaseDao.class);
		List<Purchase> purchaselist=dao.selectByIdIn(ids);
		fill(purchaselist,"purchaseId",list,"purchaseId","purchase");//循环填充
	}

	/**
	 * 价格要求
	 */		
	@Override
	public BigDecimal getPriceRequirement() {
		if(super.getPriceRequirement()==null){
			return super.getPriceRequirement();
		}
		String refund=super.getPriceRequirement().stripTrailingZeros().toPlainString();
		return new BigDecimal(refund);
	}
	
}