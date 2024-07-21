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
package com.sicheng.admin.purchase.entity;

import java.math.BigDecimal;

/**
 * 撮合交易订单详情 Entity 子类，请把你的业务代码写在这里
 * @author cl
 * @version 2018-07-20
 */
public class PurchaseTradeOrderItem extends PurchaseTradeOrderItemBase<PurchaseTradeOrderItem> {
	
	private static final long serialVersionUID = 1L;
	public PurchaseTradeOrderItem() {
		super();
	}

	public PurchaseTradeOrderItem(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	/**
	 * 采购单价
	 */		
	@Override
	public BigDecimal getPriceRequirement() {
		if(super.getPriceRequirement()==null){
			return super.getPriceRequirement();
		}
		String priceRequirement=super.getPriceRequirement().stripTrailingZeros().toPlainString();
		return new BigDecimal(priceRequirement);
	}
	
	/**
	 * 报价单价
	 */		
	@Override
	public BigDecimal getOfferPrice() {
		if(super.getOfferPrice()==null){
			return super.getOfferPrice();
		}
		String offerPrice=super.getOfferPrice().stripTrailingZeros().toPlainString();
		return new BigDecimal(offerPrice);
	}
	
}