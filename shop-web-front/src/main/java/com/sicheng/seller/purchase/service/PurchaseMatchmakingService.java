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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.purchase.dao.PurchaseMatchmakingDao;
import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.entity.PurchaseMatchmaking;
import com.sicheng.admin.purchase.entity.PurchaseMatchmakingItem;
import com.sicheng.admin.purchase.entity.PurchaseTradeOrder;
import com.sicheng.admin.purchase.entity.PurchaseTradeOrderItem;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * 撮合采购 Service
 * @author 蔡龙
 * @version 2018-06-10
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class PurchaseMatchmakingService extends CrudService<PurchaseMatchmakingDao, PurchaseMatchmaking> {

	//请在这里编写业务逻辑
	
	//父类中20个单表操作的常用方法，已全部继承下来，可直接使用。
	
	//注意：把多条业务sql包在一个事务中
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private PurchaseMatchmakingItemService purchaseMatchmakingItemService;
	
	@Autowired
	private PurchaseTradeOrderService purchaseTradeOrderService;
	
	@Autowired
	private PurchaseTradeOrderItemService purchaseTradeOrderItemService;
	
	@Autowired
	private SiteSendMessagsService siteSendMessagsService;
	
	@Autowired
	private UserMainService userMainService;
	
	/**
	 * 采购
	 */
	@Transactional(rollbackFor=Exception.class)
	public void purchaseOk(PurchaseMatchmaking purchaseMatchmaking){
		UserMain userMain = SsoUtils.getUserMain();
		//查询撮合采购数据
		purchaseMatchmaking = super.selectById(purchaseMatchmaking.getPurchaseMatchmakingId());
		//查询撮合采购详情数据
		PurchaseMatchmakingItem purchaseMatchmakingItem = new PurchaseMatchmakingItem();
		purchaseMatchmakingItem.setPmId(purchaseMatchmaking.getPurchaseMatchmakingId());
		List<PurchaseMatchmakingItem> purchaseMatchmakingItemList = purchaseMatchmakingItemService.selectByWhere(new Wrapper(purchaseMatchmakingItem)); 
		PurchaseMatchmakingItem.fillPurchaseItem(purchaseMatchmakingItemList);
		//查询采购单
		Purchase purchase = purchaseMatchmaking.getPurchase();
		//新增采购订单
		PurchaseTradeOrder purchaseTradeOrder = new PurchaseTradeOrder();
		purchaseTradeOrder.setPurchaseUId(userMain.getUId());
		purchaseTradeOrder.setOfferUId(purchaseMatchmaking.getOfferUId());
		purchaseTradeOrder.setPurchaseId(purchaseMatchmaking.getPurchaseId());
		purchaseTradeOrder.setTitle(purchase.getTitle());
		purchaseTradeOrder.setContent(purchase.getContent());
		purchaseTradeOrder.setBomPath(purchase.getBomPath());
		purchaseTradeOrder.setType(purchase.getType());
		purchaseTradeOrder.setPrice(purchaseMatchmaking.getPrice());
		purchaseTradeOrder.setStatus("10");//订单状态：10交易中20已上传交易凭证待审核30凭证审核未通过40交易完成
		purchaseTradeOrderService.insertSelective(purchaseTradeOrder);
		//新增采购订单详情
		List<PurchaseTradeOrderItem> purchaseTradeOrderItemList = new ArrayList<>();
		for (int i = 0; i < purchaseMatchmakingItemList.size(); i++) {
			PurchaseTradeOrderItem purchaseTradeOrderItem = new PurchaseTradeOrderItem();
			purchaseTradeOrderItem.setPtoId(purchaseTradeOrder.getId());
			purchaseTradeOrderItem.setPurchaseItemId(purchaseMatchmakingItemList.get(i).getPurchaseItemId());
			purchaseTradeOrderItem.setPmiId(purchaseMatchmakingItemList.get(i).getPmiId());
			purchaseTradeOrderItem.setName(purchaseMatchmakingItemList.get(i).getPurchaseItem().getName());
			purchaseTradeOrderItem.setModel(purchaseMatchmakingItemList.get(i).getPurchaseItem().getModel());
			purchaseTradeOrderItem.setBrand(purchaseMatchmakingItemList.get(i).getPurchaseItem().getBrand());
			purchaseTradeOrderItem.setAmount(purchaseMatchmakingItemList.get(i).getPurchaseItem().getAmount());
			purchaseTradeOrderItem.setPriceRequirement(purchaseMatchmakingItemList.get(i).getPurchaseItem().getPriceRequirement());
			purchaseTradeOrderItem.setUnit(purchaseMatchmakingItemList.get(i).getPurchaseItem().getUnit());
			purchaseTradeOrderItem.setPurchaseRemarks(purchaseMatchmakingItemList.get(i).getPurchaseItem().getPurchaseRemarks());
			purchaseTradeOrderItem.setOfferPrice(purchaseMatchmakingItemList.get(i).getOfferPrice());
			purchaseTradeOrderItem.setOfferRemarks(purchaseMatchmakingItemList.get(i).getOfferRemarks());
			purchaseTradeOrderItemList.add(purchaseTradeOrderItem);
		}
		purchaseTradeOrderItemService.insertSelectiveBatch(purchaseTradeOrderItemList);
		//修改撮合采购(放弃)
		PurchaseMatchmaking pm1 = new PurchaseMatchmaking();
		pm1.setStatus("30");
		super.updateByWhereSelective(pm1, new Wrapper().and("purchase_id=",purchase.getPurchaseId()));
		//修改撮合采购(选择)
		purchaseMatchmaking.setPurchaseTradeId(purchaseMatchmaking.getPurchaseMatchmakingId());
		purchaseMatchmaking.setStatus("20");//10待采购   20已采购 30放弃采购
		purchaseMatchmaking.setPurchaseTradeId(purchaseTradeOrder.getId());
		super.updateByIdSelective(purchaseMatchmaking);
		//修改采购单
		Purchase p = new Purchase();
		p.setStatus("40");//采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		p.setPurchaseId(purchase.getPurchaseId());
		purchaseService.updateByIdSelective(p);
		//发布短信通知
		Map<String,String> map=new HashMap<>();
		UserMain us = userMainService.selectById(purchaseMatchmaking.getOfferUId());
		PurchaseMatchmaking pmk = super.selectById(purchaseMatchmaking.getPurchaseMatchmakingId());
		map.put("loginName", us.getMobile());
		map.put("purchaseTitle", StringUtils.abbr(purchase.getTitle(), 20));
		siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PURCHASE_CHOOSE, pmk.getOfferUId());
	}
	
	/**
	 * 插入撮合采购信息与撮合采购详情信息
	 * @param purchaseMatchmaking
	 * @param list
	 */
	@Transactional(rollbackFor=Exception.class)
	public void insertAll(PurchaseMatchmaking purchaseMatchmaking,List<PurchaseMatchmakingItem> list){
		super.insertSelective(purchaseMatchmaking);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPmId(purchaseMatchmaking.getPurchaseMatchmakingId());
		}
		purchaseMatchmakingItemService.insertSelectiveBatch(list);
	}
}