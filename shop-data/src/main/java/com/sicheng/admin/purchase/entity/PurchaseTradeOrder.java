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
import java.util.List;

import com.sicheng.admin.purchase.dao.PurchaseDao;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.dao.StoreEnterDao;
import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 撮合交易订单 Entity 子类，请把你的业务代码写在这里
 * @author cl
 * @version 2018-06-10
 */
public class PurchaseTradeOrder extends PurchaseTradeOrderBase<PurchaseTradeOrder> {
	
	private static final long serialVersionUID = 1L;
	public PurchaseTradeOrder() {
		super();
	}

	public PurchaseTradeOrder(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	private UserMain purchaseUser; //采购方信息(会员总表)
	public UserMain getPurchaseUser() {
		if(purchaseUser==null){
			UserMainDao dao=SpringContextHolder.getBean(UserMainDao.class);
			purchaseUser=dao.selectById(this.getPurchaseUId());
		}
		return purchaseUser;
	}
	public void setPurchaseUser(UserMain purchaseUser) {
		this.purchaseUser = purchaseUser;
	}
	public static void fillPurchaseUser(List<PurchaseTradeOrder> list){
		List<Object> ids=batchField(list,"purchaseUId");//批量调用对象的getXxx()方法
		UserMainDao dao=SpringContextHolder.getBean(UserMainDao.class);
		List<UserMain> userMainlist=dao.selectByIdIn(ids);
		fill(userMainlist,"uId",list,"purchaseUId","purchaseUser");//循环填充
	}
	
	
	private UserMain offerUser; //报价方信息(会员总表)
	public UserMain getOfferUser() {
		if(offerUser==null){
			UserMainDao dao=SpringContextHolder.getBean(UserMainDao.class);
			offerUser=dao.selectById(this.getOfferUId());
		}
		return offerUser;
	}
	public void setOfferUser(UserMain offerUser) {
		this.offerUser = offerUser;
	}
	public static void fillOfferUser(List<PurchaseTradeOrder> list){
		List<Object> ids=batchField(list,"offerUId");//批量调用对象的getXxx()方法
		UserMainDao dao=SpringContextHolder.getBean(UserMainDao.class);
		List<UserMain> userMainlist=dao.selectByIdIn(ids);
		fill(userMainlist,"uId",list,"offerUId","offerUser");//循环填充
	}
	
	private StoreEnter purchaseStoreEnter;	//采购方信息(企业认证表)
	public StoreEnter getPurchaseStoreEnter() {
		if(purchaseStoreEnter==null){
			StoreEnterDao dao=SpringContextHolder.getBean(StoreEnterDao.class);
			purchaseStoreEnter=dao.selectById(this.getPurchaseUId());
		}
		return purchaseStoreEnter;
	}
	public void setPurchaseStoreEnter(StoreEnter purchaseStoreEnter) {
		this.purchaseStoreEnter = purchaseStoreEnter;
	}
	public static void fillPurchaseStoreEnter(List<PurchaseTradeOrder> list){
		List<Object> ids=batchField(list,"purchaseUId");//批量调用对象的getXxx()方法
		StoreEnterDao dao=SpringContextHolder.getBean(StoreEnterDao.class);
		List<StoreEnter> storeEnterlist=dao.selectByIdIn(ids);
		fill(storeEnterlist,"enterId",list,"purchaseUId","purchaseStoreEnter");//循环填充
	}
	
	private StoreEnter offerStoreEnter;	//报价方信息(企业认证表)
	public StoreEnter getOfferStoreEnter() {
		if(offerStoreEnter==null){
			StoreEnterDao dao=SpringContextHolder.getBean(StoreEnterDao.class);
			purchaseStoreEnter=dao.selectById(this.getOfferUId());
		}
		return purchaseStoreEnter;
	}
	public void setOfferStoreEnter(StoreEnter offerStoreEnter) {
		this.offerStoreEnter = offerStoreEnter;
	}
	public static void fillOfferStoreEnter(List<PurchaseTradeOrder> list){
		List<Object> ids=batchField(list,"purchaseUId");//批量调用对象的getXxx()方法
		StoreEnterDao dao=SpringContextHolder.getBean(StoreEnterDao.class);
		List<StoreEnter> storeEnterlist=dao.selectByIdIn(ids);
		fill(storeEnterlist,"enterId",list,"offerUId","offerStoreEnter");//循环填充
	}
	
	private Purchase purchase; //采购单信息
	public Purchase getPurchase() {
		if(purchase==null){
			PurchaseDao dao=SpringContextHolder.getBean(PurchaseDao.class);
			purchase=dao.selectById(this.getPurchaseId());//采购单id
		}
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	/**
	 * 总金额
	 */	
	@Override
	public BigDecimal getPrice() {
		if(super.getPrice()==null){
			return super.getPrice();
		}
		String refund=super.getPrice().stripTrailingZeros().toPlainString();
		return new BigDecimal(refund);
	}
	
}