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

import java.util.List;

import com.sicheng.admin.purchase.dao.PurchaseItemDao;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 撮合采购详情 Entity 子类，请把你的业务代码写在这里
 * @author cl
 * @version 2018-07-20
 */
public class PurchaseMatchmakingItem extends PurchaseMatchmakingItemBase<PurchaseMatchmakingItem> {
	
	private static final long serialVersionUID = 1L;
	public PurchaseMatchmakingItem() {
		super();
	}

	public PurchaseMatchmakingItem(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	private PurchaseItem purchaseItem;	//采购单详情
	public PurchaseItem getPurchaseItem() {
		if(purchaseItem==null){
			PurchaseItemDao dao=SpringContextHolder.getBean(PurchaseItemDao.class);
			purchaseItem=dao.selectById(this.getPurchaseItemId());
		}
		return purchaseItem;
	}

	public void setPurchaseItem(PurchaseItem purchaseItem) {
		this.purchaseItem = purchaseItem;
	}
	
	public static void fillPurchaseItem(List<PurchaseMatchmakingItem> list){
		List<Object> ids=batchField(list,"purchaseItemId");//批量调用对象的getXxx()方法
		PurchaseItemDao dao=SpringContextHolder.getBean(PurchaseItemDao.class);
		List<PurchaseItem> purchaseItemlist=dao.selectByIdIn(ids);
		fill(purchaseItemlist,"purchaseItemId",list,"purchaseItemId","purchaseItem");//循环填充
	}
	
}