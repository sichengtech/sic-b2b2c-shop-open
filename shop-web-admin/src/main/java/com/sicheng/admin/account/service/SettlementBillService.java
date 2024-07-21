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
package com.sicheng.admin.account.service;

import com.sicheng.admin.account.dao.SettlementBillDao;
import com.sicheng.admin.account.entity.AccountUser;
import com.sicheng.admin.account.entity.BusinAccountMapping;
import com.sicheng.admin.account.entity.SettlementBill;
import com.sicheng.admin.account.entity.SettlementBillDetail;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.admin.trade.service.TradeOrderService;
import com.sicheng.admin.trade.service.TradeReturnOrderService;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账单 Service
 * @author fxx
 * @version 2017-01-12
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class SettlementBillService extends CrudService<SettlementBillDao, SettlementBill> {
	
	@Autowired
	private TradeOrderService tradeOrderService;
	@Autowired
	private TradeReturnOrderService tradeReturnOrderService;
	@Autowired
	private SettlementBillDetailService settlementBillDetailService;
	@Autowired
	private AccountUserService accountUserService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BusinAccountMappingService businAccountMappingService;
	@Autowired
	private AccountPlatformSnService accountPlatformSnService;
	
	/**
	 * 重算商品账单
	 * @param settlementBill 实体对象
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String retryProduct(SettlementBill settlementBill) {
		if(settlementBill==null || settlementBill.getBeginTime()==null || settlementBill.getEndTime()==null){
			return "账单不存在";
		}
		//找到当前商家的此时间段内的所有订单
		TradeOrder order=new TradeOrder();
		order.setStoreId(settlementBill.getStoreId());
		order.setBeginProductReceiptDate(settlementBill.getBeginTime());
		order.setEndProductReceiptDate(settlementBill.getEndTime());
		//10待付款、20待发货、30待收货、40(已完成)待评价、 50已评价、60已取消、70退货退款中
		List<TradeOrder> orderList=tradeOrderService.selectByWhere(new Wrapper(order).and("a.order_status =", "40").or("a.order_status =", "50"));
		//找到当前商家的此时间段内的所有退单
		TradeReturnOrder returnOrder=new TradeReturnOrder();
		returnOrder.setStoreId(settlementBill.getStoreId());
		returnOrder.setBeginSystemAgreeTime(settlementBill.getBeginTime());
		returnOrder.setEndSystemAgreeTime(settlementBill.getEndTime());
		returnOrder.setStatus("60");//退款订单状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
		List<TradeReturnOrder> returnOrderList=tradeReturnOrderService.selectByWhere(new Wrapper(returnOrder));
		if(orderList.isEmpty() && returnOrderList.isEmpty()){
			return "";
		}
		//结算订单详情list
		List<Long> tradeOrderItemIdList = new ArrayList<>();
		//结算退款订单流水号
		List<Long> refundTradeOrderItemIdList = new ArrayList<>();
		//订单总金额
		BigDecimal orderAmount = new BigDecimal("0");
		//收取佣金
		BigDecimal platformCommission = new BigDecimal("0");
		List<SettlementBillDetail> settlementBillDetailList=new ArrayList<>();
		if(!orderList.isEmpty()){
			for (int i = 0; i < orderList.size(); i++) {
				TradeOrder tradeOrder = orderList.get(i);
				for (int j = 0; j < tradeOrder.getTradeOrderItemList().size(); j++) {
					TradeOrderItem tradeOrderItem = tradeOrder.getTradeOrderItemList().get(j);
					tradeOrderItemIdList.add(tradeOrderItem.getOrderItemId());
				}
				if(tradeOrder.getOffsetAmount()!=null){
					orderAmount = orderAmount.add(tradeOrder.getOffsetAmount());
				}else{
					orderAmount = orderAmount.add(tradeOrder.getAmountPaid());
				}
				if(tradeOrder.getFee()!=null){
					platformCommission=platformCommission.add(tradeOrder.getFee());
				}
				//添加账单详情
				SettlementBillDetail settlementBillDetail=new SettlementBillDetail();
				settlementBillDetail.setOrderId(tradeOrder.getOrderId());
				settlementBillDetail.setType("1");// 类型，1商品订单、2退货订单
				settlementBillDetailList.add(settlementBillDetail);
			}
		}
		//退单金额
		BigDecimal refundMoney = new BigDecimal("0");
		//退还佣金
		BigDecimal returnCommission=new BigDecimal("0");
		if(!returnOrderList.isEmpty()){
			for (int i = 0; i < returnOrderList.size(); i++) {
				TradeReturnOrder tradeReturnOrder = returnOrderList.get(i);
				if(tradeReturnOrder.getReturnMoney()!=null){
					BigDecimal returnMoney = tradeReturnOrder.getReturnMoney();
					refundMoney = refundMoney.add(returnMoney);
					//订单
					TradeOrder tradeOrder = tradeReturnOrder.getTradeOrder();
					//订单详情
					List<TradeOrderItem> tradeOrderItemList = tradeOrder.getTradeOrderItemList();
					if("1".equals(tradeReturnOrder.getType())){
						//退货提款
						if(tradeOrderItemList.size()==1){
							refundTradeOrderItemIdList.add(tradeOrderItemList.get(0).getOrderItemId());
						}else{
							for (int j = 0; j < tradeOrderItemList.size(); j++) {
								BigDecimal payMoney = tradeOrderItemList.get(j).getPrice().multiply(new BigDecimal(tradeOrderItemList.get(j).getQuantity()));
								if(returnMoney.compareTo(payMoney)==0){
									refundTradeOrderItemIdList.add(tradeOrderItemList.get(j).getOrderItemId());
									break;
								}
							}
						}
					}
					if("2".equals(tradeReturnOrder.getType())){
						//提款
						for (int j = 0; j < tradeOrderItemList.size(); j++) {
							refundTradeOrderItemIdList.add(tradeOrderItemList.get(j).getOrderItemId());
						}
					}
				}
				if(tradeReturnOrder.getReturnCommission()!=null){
					returnCommission=returnCommission.add(tradeReturnOrder.getReturnCommission());
				}
				//添加账单详情
				SettlementBillDetail settlementBillDetail=new SettlementBillDetail();
				settlementBillDetail.setOrderId(tradeReturnOrder.getReturnOrderId());
				settlementBillDetail.setType("2");// 类型，1商品订单、2退货订单
				settlementBillDetailList.add(settlementBillDetail);
			}
		}
		
		//获取订单业务中间表订单数据
		List<BusinAccountMapping> businAccountMappingList1 = businAccountMappingService.selectByWhere(new Wrapper().and("business_id in",tradeOrderItemIdList).and("business_type=",10));
		//订单流水号
		List<String> serialNumberList1 = new ArrayList<>();//订单商家承担手续费用
		List<String> serialNumberList2 = new ArrayList<>();//订单平台承担手续费用
		for (int i = 0; i < businAccountMappingList1.size(); i++) {
			if("1".equals(businAccountMappingList1.get(i).getServiceChargeStatus())){
				serialNumberList1.add(businAccountMappingList1.get(i).getSerialNumber());
			}
			if("2".equals(businAccountMappingList1.get(i).getServiceChargeStatus())){
				serialNumberList2.add(businAccountMappingList1.get(i).getSerialNumber());
			}
		}
		BigDecimal orderMoney2 = accountPlatformSnService.payCommissionTotal(serialNumberList1);//订单商家承担手续费用
		BigDecimal orderMoney4 = accountPlatformSnService.payCommissionTotal(serialNumberList2);//订单平台承担手续费用
		
		//获取订单业务中间表订单数据
		List<BusinAccountMapping> businAccountMappingList2 = businAccountMappingService.selectByWhere(new Wrapper().and("business_id in",refundTradeOrderItemIdList).and("business_type=",22));
		//退款流水号
		List<String> serialNumberList3 = new ArrayList<>();//退单商家承担手续费用
		List<String> serialNumberList4 = new ArrayList<>();//退单平台承担手续费用
		for (int i = 0; i < businAccountMappingList2.size(); i++) {
			if("1".equals(businAccountMappingList2.get(i).getServiceChargeStatus())){
				serialNumberList3.add(businAccountMappingList2.get(i).getSerialNumber());
			}
			if("2".equals(businAccountMappingList2.get(i).getServiceChargeStatus())){
				serialNumberList4.add(businAccountMappingList2.get(i).getSerialNumber());
			}
		}
		BigDecimal refundMoney2 = accountPlatformSnService.refundCommissionTotal(serialNumberList1);//订单商家承担手续费用
		BigDecimal refundMoney4 = accountPlatformSnService.refundCommissionTotal(serialNumberList2);//退单平台承担手续费用
		
		//订单支付总额（包括优惠券）
		BigDecimal money1 = orderAmount.subtract(refundMoney);
		//商家承担的手续费用
		BigDecimal money2 = orderMoney2.subtract(refundMoney2);
		//money3 结算给平台的佣金，如果没有佣金请输入0元
		BigDecimal money3 = platformCommission.subtract(returnCommission);
		//money4 补贴金额
		BigDecimal money4 = orderMoney4.subtract(refundMoney4);
		
		//账单总金额(结算给商家的金额) = (订单总金额-退单金额)-(收取佣金-退还佣金)
		BigDecimal settlementAmount = money1.subtract(money2).subtract(money3); 
		
		//保留两位小数
		new DecimalFormat("#.00").format(orderAmount);
		new DecimalFormat("#.00").format(money2);
		new DecimalFormat("#.00").format(money3);
		new DecimalFormat("#.00").format(money4);
		SettlementBill bill=null;
		if(settlementBill.getBillId()!=null){
			bill=super.selectById(settlementBill.getBillId());
		}
		Long billId=null;
		if(bill!=null){
			settlementBill.setBillAmount(settlementAmount);
			settlementBill.setOrderAmount(orderAmount);
			super.updateByIdSelective(settlementBill);
			billId=settlementBill.getBillId();
		}else{
			SettlementBill newBill=new SettlementBill();
			//账单类型，1商品账单
			newBill.setBillType("1");
			newBill.setStoreId(settlementBill.getStoreId());
			newBill.setBalanceDate(new Date());
			newBill.setBillAmount(settlementAmount);
			// 账单状态，10账单已生成，等待商家确认、20商家已确认，等待平台审核、30平台已审核，等待平台付款、40平台已付款，平台结算完成
			newBill.setStatus("20");
			newBill.setOrderAmount(orderAmount);
			newBill.setPlatformCommission(platformCommission);
			newBill.setRefund(refundMoney);
			newBill.setBillAmount(settlementAmount);
			newBill.setBeginTime(settlementBill.getBeginTime());
			newBill.setEndTime(settlementBill.getEndTime());
			super.insertSelective(newBill);
			billId=newBill.getBillId();
		}
		if(!settlementBillDetailList.isEmpty()){
			for(SettlementBillDetail detail:settlementBillDetailList){
				detail.setBillId(billId);
			}
			//删除旧的账单详情
			settlementBillDetailService.deleteByWhere(new Wrapper().and("bill_id=",billId));
			//插入账单详情信息
			settlementBillDetailService.insertBatch(settlementBillDetailList);
			//修改账户信息
			AccountUser accountUser=new AccountUser();
			//账户类型, 0 商家账户、1 服务账户
			accountUser.setAccountType(0);
			accountUser.setUId(settlementBill.getStore().getUserMain().getUId());
			List<AccountUser> accountUserList=accountUserService.selectByWhere(new Wrapper(accountUser));
			if(!accountUserList.isEmpty()){
				//总金额
				try {
					accountService.settlementProduct(billId, accountUserList.get(0).getAuId(), money1, money2, money3, money4);
				} catch (Exception e) {
					logger.error("商品结算报错:",e.getMessage());
				}
			}
		}
		return "重算成功";
	}

}