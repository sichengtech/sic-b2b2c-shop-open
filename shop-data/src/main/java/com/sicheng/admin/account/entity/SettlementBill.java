
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
package com.sicheng.admin.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账单 Entity 子类，请把你的业务代码写在这里
 * @author 范秀秀
 * @version 2017-02-07
 */
public class SettlementBill extends SettlementBillBase<SettlementBill> {
	
	private static final long serialVersionUID = 1L;
	public SettlementBill() {
		super();
	}

	public SettlementBill(Long id){
		super(id);
	}
	
	//一对一映射
	@JsonIgnore
	private Store store;//一个账单--一个店铺
	public Store getStore() {
		if(store==null && this.getStoreId()!=null){
			StoreDao dao=SpringContextHolder.getBean(StoreDao.class);
			store=dao.selectById(this.getStoreId());
		}
		return store;
	}
	
	public void setStore(Store store) {
		this.store = store;
	}
	
	//ListIdIn工具  在一个list中做 一对一，10个订单对10中支付方式
	//填充 xxx,把1+N改成1+1
	public static void fillStore(List<SettlementBill> list){
		List<Object> ids=batchField(list,"storeId");//批量调用对象的getXxx()方法
		StoreDao dao=SpringContextHolder.getBean(StoreDao.class);
		List<Store> storeList=dao.selectByIdIn(ids);
		fill(storeList,"storeId",list,"storeId","store");//循环填充
	}
	
	//一对多映射
	@JsonIgnore
	private List<TradeOrder> tradeOrderList;//一条账单--多个订单详情
	public List<TradeOrder> getTradeOrderList() {
		if(tradeOrderList==null){
			TradeOrderDao dao=SpringContextHolder.getBean(TradeOrderDao.class);
			tradeOrderList= dao.selectByWhere(null,new Wrapper().and("place_order_time between {0} and {1}",this.getBeginTime(),this.getEndTime()).orderBy("order_id asc"));//排序
		}
		return tradeOrderList;
	}
	
	/**
	 * 本期应结(元)
	 */
	@Override
	public BigDecimal getBillAmount() {
		if(super.getBillAmount()==null){
			return super.getBillAmount();
		}
		String billAmount=super.getBillAmount().stripTrailingZeros().toPlainString();
		return new BigDecimal(billAmount);
	}
	
	/**
	 * 本期应结(元)
	 */
	@Override
	public BigDecimal getOrderAmount() {
		if(super.getOrderAmount()==null){
			return super.getOrderAmount();
		}
		String orderAmount=super.getOrderAmount().stripTrailingZeros().toPlainString();
		return new BigDecimal(orderAmount);
	}
	
	/**
	 * 平台分佣金额
	 */
	@Override
	public BigDecimal getPlatformCommission() {
		if(super.getPlatformCommission()==null){
			return super.getPlatformCommission();
		}
		String platformCommission=super.getPlatformCommission().stripTrailingZeros().toPlainString();
		return new BigDecimal(platformCommission);
	}
	
	/**
	 * 退还佣金
	 */
	@Override
	public BigDecimal getReturnCommission() {
		if(super.getReturnCommission()==null){
			return super.getReturnCommission();
		}
		String returnCommission=super.getReturnCommission().stripTrailingZeros().toPlainString();
		return new BigDecimal(returnCommission);
	}
	
	/**
	 * 退单金额
	 */
	@Override
	public BigDecimal getRefund() {
		if(super.getRefund()==null){
			return super.getRefund();
		}
		String refund=super.getRefund().stripTrailingZeros().toPlainString();
		return new BigDecimal(refund);
	}
	
	/**
	 * 店铺推广费用（元）(促销时用)
	 */
	@Override
	public BigDecimal getPromotionExpenses() {
		if(super.getPromotionExpenses()==null){
			return super.getPromotionExpenses();
		}
		String promotionExpenses=super.getPromotionExpenses().stripTrailingZeros().toPlainString();
		return new BigDecimal(promotionExpenses);
	}
	
	/**
	 * 店铺推广费用（元）(促销时用)
	 */
	@Override
	public BigDecimal getRedPackets() {
		if(super.getRedPackets()==null){
			return super.getRedPackets();
		}
		String redPackets=super.getRedPackets().stripTrailingZeros().toPlainString();
		return new BigDecimal(redPackets);
	}
	
	/**
	 * 退还红包(促销时用)
	 */
	@Override
	public BigDecimal getReturnRedPackets() {
		if(super.getReturnRedPackets()==null){
			return super.getReturnRedPackets();
		}
		String returnRedPackets=super.getReturnRedPackets().stripTrailingZeros().toPlainString();
		return new BigDecimal(returnRedPackets);
	}
	
	/**
	 * 预定订单未退定金(元)(促销时用)
	 */
	@Override
	public BigDecimal getDeposit() {
		if(super.getDeposit()==null){
			return super.getDeposit();
		}
		String deposit=super.getDeposit().stripTrailingZeros().toPlainString();
		return new BigDecimal(deposit);
	}
	
}