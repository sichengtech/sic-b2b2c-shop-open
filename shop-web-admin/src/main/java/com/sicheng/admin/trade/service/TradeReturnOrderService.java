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
package com.sicheng.admin.trade.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.account.service.AccountService;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.settlement.service.SettlementPayWayService;
import com.sicheng.admin.trade.dao.TradeReturnOrderDao;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.pay.PayConstants;
import com.sicheng.pay.PayException;
import com.sicheng.pay.ShopPay;
import com.sicheng.pay.ShopPayFactory;

/**
 * 退款、退货退款订单 Service
 *
 * @author fxx
 * @version 2017-01-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeReturnOrderService extends CrudService<TradeReturnOrderDao, TradeReturnOrder> {

	//请在这里编写业务逻辑

	//父类中20个单表操作的常用方法，已全部继承下来，可直接使用。
	
	//注意：把多条业务sql包在一个事务中
	
	@Autowired
	private TradeOrderService tradeOrderService;
	@Autowired
	private TradeOrderItemService tradeOrderItemService;
	@Autowired
	private SettlementPayWayService settlementPayWayService;
	@Autowired
	private AccountService accountService;
	
	//返回状态
	private static final String RETURN_STATUS = "审核成功";
	private static final String RETURN_GOODS_ERROR = "退货退款申请失败";
	private static final String RETURN_GOODS_OK = "退货退款申请成功";
	private static final String RETURN_MONEY_ERROR = "退款申请失败";
	private static final String RETURN_MONEY_OK = "退款申请成功";
	
	
	/**
	 * 审核退款/退货
	 * @param tradeReturnOrder	退款退货订单	
	 * @param attr				状态	
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String authTradeReturnOrder(TradeReturnOrder tradeReturnOrder,String attr){
		TradeReturnOrder tro = this.selectById(tradeReturnOrder.getReturnOrderId());
		//查询退款订单
		TradeOrder tradeOrder = tradeOrderService.selectById(tro.getOrderId());	
		String thirdpayordernumber = "";//第三方交易号
		Long payWayId = null;//支付id
		SettlementPayWay settlementPayWay = tro.getSettlementPayWay();//支付方式
		String name = "";//支付名称
		if(StringUtils.isBlank(thirdpayordernumber) || settlementPayWay == null ){
			payWayId = -1L;
			name = "-1";
			thirdpayordernumber = "-1";
		}else{
			payWayId = tro.getPayWayId();
			name = settlementPayWay.getName();
			thirdpayordernumber = tradeOrder.getThirdPayOrderNumber();
		}
		String type = tro.getType(); // 类型，1退货退款、2退款
		//退货退款
		if("1".equals(type)){
			//审核失败
			if("1".equals(attr)){
				authReturnGoodsError(tradeReturnOrder,tro);
			}
			//审核成功
			if("2".equals(attr)){
				authReturnGoodsSuccess(tradeReturnOrder,tro);
			}
			//审核成功并退款
			if("3".equals(attr)){
				TradeReturnOrder t = new TradeReturnOrder();
				t.setOnlineReturnMoney(tradeReturnOrder.getOnlineReturnMoney());
				t.setSystemHandleRemarks(tradeReturnOrder.getSystemHandleRemarks());
				this.updateByWhereSelective(t, new Wrapper().and("a.order_item_id=",tro.getOrderItemId()));
				//调取退款
				BigDecimal zero = new BigDecimal("0");
				if(tradeOrder.getOnlinePayMoney().compareTo(zero)!=0){//实际支付金额不为0就会调取退款接口
					Map<String, String> map = returnMoney(tradeReturnOrder,tro,tradeOrder);
					if(map.isEmpty()){
						return RETURN_GOODS_ERROR;
					}
					String returnCode = map.get("returnCode");
					if(!"SUCCESS".equals(returnCode)){
						String returnMsg = map.get("returnMsg");
						if(StringUtils.isBlank(returnMsg)){
							return RETURN_GOODS_ERROR;
						}
						return returnMsg;
					}
				}
				//修改一系列表状态
				authReturnGoodsSuccess(tradeReturnOrder,tro);
				//调取账户体系退款
				try {
					List<TradeOrderItem> tradeOrderItemList = tradeOrder.getTradeOrderItemList();
					if(tradeOrderItemList.size()==1){
						accountService.refundProduct(tro.getOrderItemId(), tradeReturnOrder.getOnlineReturnMoney(), new BigDecimal("0"), payWayId, name, thirdpayordernumber);
					}else{
						for (int i = 0; i < tradeOrderItemList.size(); i++) {
							BigDecimal returnMoney = (tradeOrderItemList.get(i).getPrice()).multiply(new BigDecimal(tradeOrderItemList.get(i).getQuantity()));
							accountService.refundProduct(tro.getOrderItemId(), returnMoney, new BigDecimal("0"), payWayId, name, thirdpayordernumber);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				return RETURN_GOODS_OK;
			}
		}
		//退款
		if("2".equals(type)){
			//审核失败
			if("1".equals(attr)){
				authReturnMoneyError(tradeReturnOrder,tro);
			}
			//审核成功
			if("2".equals(attr)){
				authReturnMoneySuccess(tradeReturnOrder,tro);
			}
			//审核成功并退款
			if("3".equals(attr)){
				TradeReturnOrder t = new TradeReturnOrder();
				t.setOnlineReturnMoney(tradeReturnOrder.getOnlineReturnMoney());
				t.setSystemHandleRemarks(tradeReturnOrder.getSystemHandleRemarks());
				this.updateByWhereSelective(t, new Wrapper().and("a.order_id=",tro.getOrderId()));
				//调取退款接口
				BigDecimal zero = new BigDecimal("0");
				if(tradeOrder.getOnlinePayMoney().compareTo(zero)!=0){//实际支付金额不为0就会调取退款接口
					Map<String, String> map = returnMoney(tradeReturnOrder,tro,tradeOrder);
					if(map.isEmpty()){
						return RETURN_MONEY_ERROR;
					}
					String returnCode = map.get("returnCode");
					if(!"SUCCESS".equals(returnCode)){
//						String returnMsg = map.get("returnMsg");
//						if(StringUtils.isBlank(returnMsg)){
//							return RETURN_MONEY_ERROR;
//						}
						return RETURN_MONEY_ERROR;
					}
				}
				//修改一系列表状态
				authReturnMoneySuccess(tradeReturnOrder,tro);
				//调取账户体系退款
				try {
					List<TradeOrderItem> tradeOrderItemList = tradeOrder.getTradeOrderItemList();
					if(tradeOrderItemList.size()==1){
						accountService.refundProduct(tro.getOrderItemId(), tradeReturnOrder.getOnlineReturnMoney(), new BigDecimal("0"), payWayId, name, thirdpayordernumber);
					}else{
						for (int i = 0; i < tradeOrderItemList.size(); i++) {
							BigDecimal returnMoney = (tradeOrderItemList.get(i).getPrice()).multiply(new BigDecimal(tradeOrderItemList.get(i).getQuantity()));
							accountService.refundProduct(tro.getOrderItemId(), returnMoney, new BigDecimal("0"), payWayId, name, thirdpayordernumber);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				return RETURN_MONEY_OK;
			}
		}
		return RETURN_STATUS;
	}
	
	/**
	 * 审核成功(退货退款)
	 */
	@Transactional(rollbackFor=Exception.class)
	private String authReturnGoodsSuccess(TradeReturnOrder tradeReturnOrder,TradeReturnOrder tro){
		//查询是否有订单未退款完成
		List<TradeOrderItem> tradeOrderItemList = tradeOrderItemService.selectByWhere(new Wrapper().and("order_id=",tro.getOrderId())
				.and("is_return_status<>",0).and("return_status is null").and("order_item_id <>",tro.getTradeOrderItem().getOrderItemId()));
		TradeOrder tradeOrder = new TradeOrder();
		if(tradeOrderItemList.isEmpty()){
			//修改订单状态(10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消、70退货退款中、80交易关闭)
			tradeOrder.setOrderId(tro.getOrderId());
			tradeOrder.setOrderStatus("40");
			tradeOrder.setProductReceiptDate(new Date());
			tradeOrderService.updateByIdSelective(tradeOrder);
		}
		//查询支付方式
		Long payWayId = tradeOrderService.selectById(tro.getOrderId()).getPaymentMethodId();
		//修改订单详情(10退货/款成功、20退货/款失败)
		TradeOrderItem tradeOrderItem = new TradeOrderItem();
		tradeOrderItem.setReturnStatus("10");
		tradeOrderItemService.updateByWhereSelective(tradeOrderItem, new Wrapper().and("a.order_item_id=",tro.getOrderItemId()));
		//修改退款退货订单(状态：10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
		TradeReturnOrder t = new TradeReturnOrder();
		t.setSystemHandle(1L);
		t.setStatus("60");
		t.setSystemAgreeTime(new Date());
		t.setPayWayId(payWayId);
		t.setOnlineReturnMoney(tradeReturnOrder.getOnlineReturnMoney());
		t.setSystemHandleRemarks(tradeReturnOrder.getSystemHandleRemarks());
		this.updateByWhereSelective(t, new Wrapper().and("a.return_order_id=",tro.getReturnOrderId()));
		return RETURN_STATUS;
	}
	
	/**
	 * 审核失败(退货退款)
	 */
	@Transactional(rollbackFor=Exception.class)
	private String authReturnGoodsError(TradeReturnOrder tradeReturnOrder,TradeReturnOrder tro){
		//查询是否有订单未退款完成
		List<TradeOrderItem> tradeOrderItemList = tradeOrderItemService.selectByWhere(new Wrapper().and("order_id=",tro.getOrderId())
				.and("is_return_status<>",0).and("return_status is null").and("order_item_id <>",tro.getTradeOrderItem().getOrderItemId()));
		TradeOrder tradeOrder = new TradeOrder();
		if(tradeOrderItemList.isEmpty()){
			//修改订单状态(10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消、70退货退款中、80交易关闭)
			tradeOrder.setOrderId(tro.getOrderId());
			tradeOrder.setOrderStatus("40");
			tradeOrder.setProductReceiptDate(new Date());
			tradeOrderService.updateByIdSelective(tradeOrder);
		}
		//查询支付方式
		Long payWayId = tradeOrderService.selectById(tro.getOrderId()).getPaymentMethodId();
		//修改订单详情(10退货/款成功、20退货/款失败)
		TradeOrderItem tradeOrderItem = new TradeOrderItem();
		tradeOrderItem.setReturnStatus("20");
		tradeOrderItemService.updateByWhereSelective(tradeOrderItem, new Wrapper().and("a.order_item_id=",tro.getOrderItemId()));
		//修改退款退货订单(状态：10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
		TradeReturnOrder t = new TradeReturnOrder();
		t.setSystemHandle(2L);
		t.setStatus("70");
		t.setSystemAgreeTime(new Date());
		t.setPayWayId(payWayId);
		t.setOnlineReturnMoney(tradeReturnOrder.getOnlineReturnMoney());
		t.setSystemHandleRemarks(tradeReturnOrder.getSystemHandleRemarks());
		this.updateByWhereSelective(t, new Wrapper().and("a.return_order_id=",tro.getReturnOrderId()));
		return RETURN_STATUS;
	}
	
	/**
	 * 审核成功(退款)
	 */
	@Transactional(rollbackFor=Exception.class)
	private String authReturnMoneySuccess(TradeReturnOrder tradeReturnOrder,TradeReturnOrder tro){
		//修改订单状态(10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消、70退货退款中、80交易关闭)
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setOrderId(tro.getOrderId());
		tradeOrder.setOrderStatus("40");
		tradeOrder.setProductReceiptDate(new Date());
		tradeOrderService.updateByIdSelective(tradeOrder);
		//查询支付方式
		Long payWayId = tradeOrderService.selectById(tro.getOrderId()).getPaymentMethodId();
		//修改订单详情(10退货/款成功、20退货/款失败)
		TradeOrderItem tradeOrderItem = new TradeOrderItem();
		tradeOrderItem.setReturnStatus("10");
		tradeOrderItemService.updateByWhereSelective(tradeOrderItem, new Wrapper().and("a.order_id=",tro.getOrderId()));
		//修改退款订单(状态：10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
		TradeReturnOrder t = new TradeReturnOrder();
		t.setSystemHandle(1L);
		t.setStatus("60");
		t.setSystemAgreeTime(new Date());
		t.setPayWayId(payWayId);
		t.setOnlineReturnMoney(tradeReturnOrder.getOnlineReturnMoney());
		t.setSystemHandleRemarks(tradeReturnOrder.getSystemHandleRemarks());
		this.updateByWhereSelective(t, new Wrapper().and("a.order_id=",tro.getOrderId()));
		return RETURN_STATUS;
	}
	
	/**
	 * 审核失败(退款)
	 */
	@Transactional(rollbackFor=Exception.class)
	private String authReturnMoneyError(TradeReturnOrder tradeReturnOrder,TradeReturnOrder tro){
		//修改订单状态(10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消、70退货退款中、80交易关闭)
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setOrderId(tro.getOrderId());
		tradeOrder.setOrderStatus("40");
		tradeOrder.setProductReceiptDate(new Date());
		tradeOrderService.updateByIdSelective(tradeOrder);
		//查询支付方式
		Long payWayId = tradeOrderService.selectById(tro.getOrderId()).getPaymentMethodId();
		//修改订单详情(10退货/款成功、20退货/款失败)
		TradeOrderItem tradeOrderItem = new TradeOrderItem();
		tradeOrderItem.setReturnStatus("20");
		tradeOrderItemService.updateByWhereSelective(tradeOrderItem, new Wrapper().and("a.order_id=",tro.getOrderId()));
		//修改退款退货订单(状态：10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
		TradeReturnOrder t = new TradeReturnOrder();
		t.setSystemHandle(2L);
		t.setStatus("70");
		t.setSystemAgreeTime(new Date());
		t.setPayWayId(payWayId);
		t.setOnlineReturnMoney(tradeReturnOrder.getOnlineReturnMoney());
		t.setSystemHandleRemarks(tradeReturnOrder.getSystemHandleRemarks());
		this.updateByWhereSelective(t, new Wrapper().and("a.order_id=",tro.getOrderId()));
		return RETURN_STATUS;
	}
	
	/**
	 * 平台退款
	 */
	private Map<String, String> returnMoney(TradeReturnOrder tradeReturnOrder,TradeReturnOrder tro,TradeOrder tradeOrder){
		Long payWayId = tradeOrder.getPaymentMethodId();
		if(payWayId!=null){
			SettlementPayWay settlementPayWay = settlementPayWayService.selectById(payWayId);
			//商户订单号
			String outTradeNo = tradeOrder.getOutTradeNo();
			//退款金额
			BigDecimal refundFee = tro.getReturnMoney();
			//退款单号
			String outRefundNo = tro.getReturnOrderId().toString();
			//获取订单总金额
			TradeOrder to = new TradeOrder();
			to.setOutTradeNo(outTradeNo);
			List<TradeOrder> tradeOrders = tradeOrderService.selectByWhere(new Wrapper(to));
			BigDecimal totalFee = new BigDecimal(0);
			for (int i = 0; i < tradeOrders.size(); i++) {
				totalFee = totalFee.add(tradeOrders.get(i).getOnlinePayMoney());
			}
			//支付方式编号
			String payWayNum = settlementPayWay.getPayWayNum();
			//退款返回值
			Map<String, String> result = new LinkedHashMap<>();
			if(StringUtils.isNotBlank(payWayNum)){
				Map<String,Object> map = new HashMap<>();
				try {
					map.put(PayConstants.OUT_TRADE_NO, outTradeNo);
					map.put(PayConstants.OUT_REFUND_NO, outRefundNo);
					map.put(PayConstants.AMOUNTPAID, totalFee);
					map.put(PayConstants.REFUND_FEE, refundFee.toString());
					ShopPay shopPay = ShopPayFactory.get(settlementPayWay.getPayWayNum());
					result = (Map<String, String>) shopPay.refund(map);
					return result;
				} catch (PayException e) {
					logger.info("退款发生错误",e);
					result.put("returnCode","FAIL");
					result.put("returnMsg",FYUtils.fyParams("退款发生错误：")+e.getMessage());
					return result;
				}
			}
		}
		return null;
	}
}