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
import com.sicheng.admin.store.dao.StoreEnterDao;
import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 撮合采购 Entity 子类，请把你的业务代码写在这里
 * @author 蔡龙
 * @version 2018-06-10
 */
public class PurchaseMatchmaking extends PurchaseMatchmakingBase<PurchaseMatchmaking> {
	
	private static final long serialVersionUID = 1L;
	public PurchaseMatchmaking() {
		super();
	}

	public PurchaseMatchmaking(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	private StoreEnter purchaseStoreEnter;	//企业认证表(采购商)
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
	public static void fillPurchaseStoreEnter(List<PurchaseMatchmaking> list){
		List<Object> ids=batchField(list,"purchaseUId");//批量调用对象的getXxx()方法
		StoreEnterDao dao=SpringContextHolder.getBean(StoreEnterDao.class);
		List<StoreEnter> storeEnterlist=dao.selectByIdIn(ids);
		fill(storeEnterlist,"enterId",list,"purchaseUId","purchaseStoreEnter");//循环填充
	}
	
	private StoreEnter offerStoreEnter;	//企业认证表(报价商)
	public StoreEnter getOfferStoreEnter() {
		if(offerStoreEnter==null){
			StoreEnterDao dao=SpringContextHolder.getBean(StoreEnterDao.class);
			offerStoreEnter=dao.selectById(this.getOfferUId());
		}
		return offerStoreEnter;
	}
	public void setOfferStoreEnter(StoreEnter offerStoreEnter) {
		this.offerStoreEnter = offerStoreEnter;
	}
	
	public static void fillOfferStoreEnter(List<PurchaseMatchmaking> list){
		List<Object> ids=batchField(list,"offerUId");//批量调用对象的getXxx()方法
		StoreEnterDao dao=SpringContextHolder.getBean(StoreEnterDao.class);
		List<StoreEnter> storeEnterlist=dao.selectByIdIn(ids);
		fill(storeEnterlist,"enterId",list,"offerUId","offerStoreEnter");//循环填充
	}
	
	
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
	public static void fillPurchase(List<PurchaseMatchmaking> list){
		List<Object> ids=batchField(list,"purchaseId");//批量调用对象的getXxx()方法
		PurchaseDao dao=SpringContextHolder.getBean(PurchaseDao.class);
		List<Purchase> purchaselist=dao.selectByIdIn(ids);
		fill(purchaselist,"purchaseId",list,"purchaseId","purchase");//循环填充
	}
	
	/**
	 * 总价
	 * @return
	 */
	@Override
	public BigDecimal getPrice() {
		if(super.getPrice()==null){
			return super.getPrice();
		}
		String bak2ReturnMoeny=super.getPrice().stripTrailingZeros().toPlainString();
		return new BigDecimal(bak2ReturnMoeny);
	}
	
}