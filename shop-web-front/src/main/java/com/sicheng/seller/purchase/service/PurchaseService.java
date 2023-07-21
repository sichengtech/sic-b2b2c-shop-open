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
package com.sicheng.seller.purchase.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.purchase.dao.PurchaseDao;
import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.entity.PurchaseItem;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.sso.utils.SsoUtils;

/**
 * purchase Service
 * @author 蔡龙
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
	 * 批量发布采购
	 */
	@Transactional(rollbackFor=Exception.class)
	public void batchPurchase(Purchase purchase, String[] names,String[] models,String[] brands,String[] amounts,String[] priceRequirements,String[] units,String[] purchaseRemarks){
		UserMain userMain = SsoUtils.getUserMain();
		//采购单
		purchase.setContent(purchase.getTitle()+FYUtils.fyParams("(批量发布)"));
		purchase.setUId(userMain.getUId());
		purchase.setType("30");//类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		purchase.setStatus("10"); //采购状态：10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		super.insertSelective(purchase);
		//采购明细
		List<PurchaseItem> purchaseItemList = new ArrayList<>();
		for (int i = 0; i < names.length; i++) {
			//保存采购单明细
			PurchaseItem purchaseItem=new PurchaseItem();
			purchaseItem.setPurchaseId(purchase.getId());
			purchaseItem.setName(names[i]);
			purchaseItem.setModel(models[i]);
			purchaseItem.setBrand(brands[i]);
			purchaseItem.setAmount(Integer.parseInt(amounts[i]));
			if(StringUtils.isNotBlank(priceRequirements[i])){
				purchaseItem.setPriceRequirement(new BigDecimal(priceRequirements[i]));
			}
			purchaseItem.setUnit(units[i]);
			purchaseItem.setPurchaseRemarks(purchaseRemarks[i]);
			purchaseItemList.add(purchaseItem);
		}
		purchaseItemService.insertSelectiveBatch(purchaseItemList);
	}
	
}