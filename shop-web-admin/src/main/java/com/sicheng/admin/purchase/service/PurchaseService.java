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
package com.sicheng.admin.purchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.entity.PurchaseItem;
import com.sicheng.admin.purchase.dao.PurchaseDao;

/**
 * purchase Service
 * @author cl
 * @version 2018-06-10
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class PurchaseService extends CrudService<PurchaseDao, Purchase> {

	//请在这里编写业务逻辑
	
	//父类中20个单表操作的常用方法，已全部继承下来，可直接使用。
	
	//注意：把多条业务sql包在一个事务中
	
	@Autowired
	private PurchaseItemService purchaseItemService;
	
	/**
	 * 根据采购单id删除采购单和采购单明细
	 * @param purchaseId 采购单id
	 */
	@Transactional(rollbackFor=Exception.class)
	public void delete(Long purchaseId) {
		//删除采购单
		super.deleteById(purchaseId);
		//采购单明细
		PurchaseItem purchaseItem=new PurchaseItem();
		purchaseItem.setPurchaseId(purchaseId);
		purchaseItemService.deleteByWhere(new Wrapper(purchaseItem));
	}
	
	/**
	 * 审核采购单，更新采购单信息与更新采购单明细状态
	 * @param purchase
	 * @param type 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
	 */
	@Transactional(rollbackFor=Exception.class)
	public void authPurchase(Purchase purchase,String type) {
		//更新采购单信息
		super.updateByIdSelective(purchase);
	}
	
	/**
	 * 更新采购单信息并保存采购单明细
	 * @param purchase
	 * @param list
	 */
	@Transactional(rollbackFor=Exception.class)
	public void savePurchase(Purchase purchase,List<PurchaseItem> list) {
		//更新采购单信息
		super.updateByIdSelective(purchase);
		//保存采购单明细
		purchaseItemService.insertSelectiveBatch(list);
	}
}