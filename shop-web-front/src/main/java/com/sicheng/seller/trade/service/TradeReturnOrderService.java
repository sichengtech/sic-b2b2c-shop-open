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
package com.sicheng.seller.trade.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.trade.dao.TradeReturnOrderDao;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.admin.trade.entity.TradeReturnOrderVoucher;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.service.TradeStockService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * 退款、退货退款订单 Service
 *
 * @author fxx
 * @version 2017-01-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeReturnOrderService extends CrudService<TradeReturnOrderDao, TradeReturnOrder> {

    @Autowired
    private TradeOrderService tradeOrderService;
//    @Autowired
//    private TradeReturnOrderService tradeReturnOrderService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private TradeReturnOrderVoucherService tradeReturnOrderVoucherService;
    @Autowired
    private TradeStockService tradeStockService;

    /**
     * 申请退货退款
     *
     * @param tradeReturnOrder 退单
     * @param tradeOrder       订单
     * @param tradeOrderItem   订单详情
     * @param imgs             图片
     */
    @Transactional(rollbackFor = Exception.class)
    public void applyReturnOrder(TradeReturnOrder tradeReturnOrder, TradeOrder tradeOrder, TradeOrderItem tradeOrderItem, List<String> imgs) {
        //状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        tradeReturnOrder.setStatus("10");
        tradeReturnOrder.setStoreId(tradeOrder.getStoreId());
        //退还佣金
        //如果订单状态为20(待发货)，表示用户收到货，是退整条订单，所以所退佣金是退整条订单的佣金
        TradeOrder order = tradeOrderService.selectById(tradeOrder.getOrderId());
        if (order == null) {
            return;
        }
        if ("20".equals(order.getOrderStatus())) {
            tradeReturnOrder.setReturnCommission(order.getFee());
        } else {
            //否则是退一个商品的佣金
            TradeOrderItem item = tradeOrderItemService.selectById(tradeOrderItem.getOrderItemId());
            BigDecimal price = item.getPrice();
            if (StringUtils.isNotBlank(item.getCommissionRatio())) {
                BigDecimal commission = new BigDecimal(item.getCommissionRatio());
                tradeReturnOrder.setReturnCommission(price.multiply(commission));
            }
        }
        //退还运费
        tradeReturnOrder.setReturnFreight(order.getFreight());
        tradeReturnOrder.setApplyDate(new Date());
        tradeReturnOrder.setOnlineReturnMoney(tradeReturnOrder.getReturnMoney());//在线退款金额
        tradeReturnOrder.setPayWayId(order.getPaymentMethodId());//支付方式
        dao.insertSelective(tradeReturnOrder);
        //标识订单为退货退款状态
        TradeOrder order2 = tradeReturnOrder.getTradeOrder();
        order2.setIsReturnStatus(tradeReturnOrder.getType());
        //订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、 50已评价、60已取消、70退货退款中
        tradeOrderService.updateByIdSelective(order2);
        //标识订单详情为退货退款状态
        //如果订单状态为20(待发货)，表示没有因没有发货而发起的退款，是退整条订单，所以要将该订单中的所有订单详情标识为退货退款状态，
        if ("20".equals(order.getOrderStatus())) {
            TradeOrderItem item1 = new TradeOrderItem();
            item1.setIsReturnStatus(tradeReturnOrder.getType());
            tradeOrderItemService.updateByWhereSelective(item1, new Wrapper().and("order_id = ", order.getOrderId()));
        } else {
            //否则是将某一个订单详情标识为退货退款状态
            TradeOrderItem item2 = tradeReturnOrder.getTradeOrderItem();
            item2.setIsReturnStatus(tradeReturnOrder.getType());
            tradeOrderItemService.updateByIdSelective(item2);
        }
        //添加收货凭证
        if (imgs != null && !imgs.isEmpty()) {
            List<TradeReturnOrderVoucher> returnOrderVoucherList = new ArrayList<>();
            for (int i = 0; i < imgs.size(); i++) {
                TradeReturnOrderVoucher tradeReturnOrderVoucher = new TradeReturnOrderVoucher();
                tradeReturnOrderVoucher.setPath(imgs.get(i));
                tradeReturnOrderVoucher.setReturnOrderId(tradeReturnOrder.getReturnOrderId());
                returnOrderVoucherList.add(tradeReturnOrderVoucher);
            }
            tradeReturnOrderVoucherService.insertBatch(returnOrderVoucherList);
        }
    }

    /**
     * 审核退款退货或者退款
     */
    @Transactional(rollbackFor = Exception.class)
    public void authReturnOrder(TradeReturnOrder tradeReturnOrder) {
        TradeReturnOrder tro = this.selectById(tradeReturnOrder.getReturnOrderId());
        //修改退款订单表
        //商家处理，0待审核、1同意、2拒绝
        if ("1".equals(tradeReturnOrder.getBusinessHandle())) {
            //状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
            if ("1".equals(tradeReturnOrder.getType())) {//类型，1退货退款、2退款
                if ("1".equals(tradeReturnOrder.getIsJettison())) {
                    //商家是否收货，0否、1是
                    tradeReturnOrder.setIsProductReceipt("1");
                    //状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
                    tradeReturnOrder.setStatus("50");
                } else {
                    tradeReturnOrder.setStatus("30");
                }
            } else {
                tradeReturnOrder.setStatus("50");
            }
        }
        //商家处理，0待审核、1同意、2拒绝
        if ("2".equals(tradeReturnOrder.getBusinessHandle())) {
            //状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
            tradeReturnOrder.setStatus("20");
            //修改订单表状态
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setOrderId(tro.getOrderId());
            tradeOrder.setOrderStatus("40");//10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消、70退货退款中、80交易关闭
            tradeOrderService.updateByIdSelective(tradeOrder);
            //修改订单详情表详情
            TradeOrderItem tradeOrderItem = new TradeOrderItem();
            tradeOrderItem.setReturnStatus("20");//10退货/款成功、20退货/款失败
            tradeOrderItemService.updateByWhereSelective(tradeOrderItem, new Wrapper().and("order_id=", tro.getOrderId()));
        }
        tradeReturnOrder.setBusinessHandleDate(new Date());
        this.updateByIdSelective(tradeReturnOrder);

    }


    /**
     * 处理退款
     * @param tradeReturnOrder 退单
     *//*
	@Transactional(rollbackFor=Exception.class)
	public Map<String,Object> handleReturnOrder(TradeReturnOrder tradeReturnOrder){
		Map<String,Object> map=new HashMap<>();
		if(tradeReturnOrder==null || tradeReturnOrder.getReturnOrderId()==null){
			map.put("success",false);
			map.put("message","退单id不能为空,处理失败");
			return map;
		}
		TradeReturnOrder returnOrder=dao.selectById(tradeReturnOrder.getReturnOrderId());
		//处理订单状态
		TradeOrder tradeOrder=returnOrder.getTradeOrder();
		if(tradeOrder==null){
			map.put("success",false);
			map.put("message","订单不存在，处理失败");
			return map;
		}
		UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
		//订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、 50已评价、60已取消、70退货退款中
		if("20".equals(tradeOrder.getOrderStatus())){
			TradeOrderItem tradeOrderItem=new TradeOrderItem();
			tradeOrderItem.setOrderId(tradeOrder.getOrderId());
			//退单状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
			//结算金额：如果调整了金额，就用调整后的金额，否则用订单总金额
			BigDecimal settlementMoney=tradeOrder.getOffsetAmount()==null?tradeOrder.getAmountPaid():tradeOrder.getOffsetAmount();
			if("20".equals(tradeReturnOrder.getStatus())){
				//退货/款状态 ，10退货/款成功、20退货/款失败
				tradeOrderItem.setReturnStatus("10");
				settlementMoney=settlementMoney.subtract(returnOrder.getReturnMoney());
			}else{
				//退货/款状态 ，10退货/款成功、20退货/款失败
				tradeOrderItem.setReturnStatus("20");
			}
			//结算金额大于0时在申请结算
			if(settlementMoney.compareTo(new BigDecimal("0"))==1){
			
			}
			//更新退款状态
			//状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
			tradeReturnOrder.setStatus("40");
			dao.updateByWhereSelective(tradeReturnOrder, new Wrapper()
					.and("return_order_id=",tradeReturnOrder.getReturnOrderId()).and("store_id=",userSeller.getStoreId()));
			//更新订单状态
			TradeOrder tradeOrder2=new TradeOrder();
			tradeOrder2.setOrderId(tradeOrder.getOrderId());
			//订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、 50已评价、60已取消、70退货退款中
			tradeOrder2.setOrderStatus("40");
			tradeOrderService.updateByIdSelective(tradeOrder2);
			//更新订单详情的退款状态
			tradeOrderItemService.updateByWhereSelective(tradeOrderItem, new Wrapper().and("order_id=", tradeOrder.getOrderId()));
			map.put("success", true);
			return map;
		}
		
		//订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、 50已评价、60已取消、70退货退款中
		if("30".equals(tradeOrder.getOrderStatus())){
			TradeOrderItem tradeOrderItem=new TradeOrderItem();
			//退单类型，1退货退款、2退款
			//退单状态，10买家申请退货、20商家处理退货申请、25商家拒绝退、30买家退货给商家、40平台审核,退款完成，45平台拒绝退)
			if("30".equals(tradeReturnOrder.getStatus()) || "20".equals(tradeReturnOrder.getStatus())){
				//退货/款状态 ，10退货/款成功、20退货/款失败
				tradeOrderItem.setReturnStatus("10");
				//如果是退款或者是退货并弃货，商家同意后就可以直接退款
				//type退单类型，1退货退款、2退款
				//isJettison是否弃货：0否、1是
				if("2".equals(tradeReturnOrder.getType()) || "1".equals(tradeReturnOrder.getIsJettison())){
					//状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
					tradeReturnOrder.setStatus("40");
				}
			}else{
				//退货/款状态 ，10退货/款成功、20退货/款失败
				tradeOrderItem.setReturnStatus("20");
			}
			//查找该订单还未处理的退单
			List<TradeOrderItem> tradeOrderItemList=tradeOrderItemService.selectByWhere(new Wrapper().and("order_id=",tradeOrder.getOrderId())
					.and("is_return_status<>",0).and("return_status is null").and("order_item_id <>",returnOrder.getTradeOrderItem().getOrderItemId()));
			//如果是退货退款并且没有弃货，或者当前订单中还有未处理的退款，都不能进行结算。需要等到商家收到货，或订单中的退单都处理完才能结算
			if(("1".equals(tradeReturnOrder.getType()) && "20".equals(returnOrder.getStatus())) || tradeOrderItemList.size()>0){
				//更新退款状态
				dao.updateByWhereSelective(tradeReturnOrder, new Wrapper()
						.and("return_order_id=",tradeReturnOrder.getReturnOrderId()).and("store_id=",userSeller.getStoreId()));
				//更新订单详情的退款状态
				tradeOrderItemService.updateByWhereSelective(tradeOrderItem, new Wrapper().and("order_id=", tradeOrder.getOrderId()).and("order_item_id=",returnOrder.getOrderItemId()));
				map.put("success",true);
				return map;
			}
			
			//结算金额=订单总金额-退单总金额
			//获取该条订单所有的退单
			List<TradeReturnOrder> tradeReturnOrderList=dao.selectByWhere(null,new Wrapper().and("order_id=",tradeOrder.getOrderId()));
			BigDecimal price=new BigDecimal("0");
			for(int i=0;i<tradeReturnOrderList.size();i++){
				if(!"25".equals(tradeReturnOrderList.get(i).getStatus())){
					price=price.add(tradeReturnOrderList.get(i).getReturnMoney());
				}
			}
			//申请结算
			//结算金额：如果调整了金额，就用调整后的金额，否则用订单总金额
			BigDecimal settlementMoney=tradeOrder.getOffsetAmount()==null?tradeOrder.getAmountPaid():tradeOrder.getOffsetAmount();
			settlementMoney=settlementMoney.subtract(price);
			//结算金额大于0时在申请结算
			if(settlementMoney.compareTo(new BigDecimal("0"))==1){
			
			}
			//更新退款状态
			dao.updateByWhereSelective(tradeReturnOrder, new Wrapper()
					.and("return_order_id=",tradeReturnOrder.getReturnOrderId()).and("store_id=",userSeller.getStoreId()));
			TradeOrder tradeOrder2=new TradeOrder();
			tradeOrder2.setOrderId(tradeOrder.getOrderId());
			tradeOrder2.setOrderStatus("40");
			tradeOrderService.updateByIdSelective(tradeOrder2);
			//更新订单详情的退款状态
			tradeOrderItemService.updateByWhereSelective(tradeOrderItem, new Wrapper().and("order_id=", tradeOrder.getOrderId()).and("order_item_id=",returnOrder.getOrderItemId()));
			map.put("success",true);
			return map;
		}
		return map;
	}*/

    /**
     * 商家收货
     *
     * @param tradeReturnOrder 退单
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> goodsReceipt(TradeReturnOrder tradeReturnOrder) {
        Map<String, Object> map = new HashMap<>();
        //更新退单信息
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        tradeReturnOrder.setStoreId(userSeller.getStoreId());
        TradeReturnOrder returnOrder = dao.selectById(tradeReturnOrder.getReturnOrderId());
        if (returnOrder == null) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("退单不存在，收货失败"));
            return map;
        }
        TradeOrder tradeOrder = returnOrder.getTradeOrder();
        if (tradeOrder == null) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("订单不存在，收货失败"));
            return map;
        }
        //结算金额=订单总金额-退单总金额
        //获取该条订单所有的退单
//		List<TradeReturnOrder> tradeReturnOrderList=dao.selectByWhere(null,new Wrapper().and("order_id=",tradeOrder.getOrderId()));
//		BigDecimal price=new BigDecimal("0");
//		for(int i=0;i<tradeReturnOrderList.size();i++){
//			if(!"25".equals(tradeReturnOrderList.get(0).getStatus())){
//				price=price.add(tradeReturnOrderList.get(i).getReturnMoney());
//			}
//		}
//		//申请结算
//		//结算金额：如果调整了金额，就用调整后的金额，否则用订单总金额
//		BigDecimal settlementMoney=tradeOrder.getOffsetAmount()==null?tradeOrder.getAmountPaid():tradeOrder.getOffsetAmount();
//		settlementMoney=settlementMoney.subtract(price);
//		//结算金额大于0时在申请结算
//		if(settlementMoney.compareTo(new BigDecimal("0"))==1){
        
//		}
        //更新退单状态
        dao.updateByWhereSelective(tradeReturnOrder, new Wrapper().and("return_order_id =", tradeReturnOrder.getReturnOrderId()).and("store_id = ", userSeller.getStoreId()));//属主检查
        //更新订单状态
//		TradeOrder tradeOrder2=new TradeOrder();
//		tradeOrder2.setOrderId(tradeOrder.getOrderId());
//		tradeOrder2.setOrderStatus("40");
//		tradeOrderService.updateByIdSelective(tradeOrder2);
        //更新商品库存
        TradeOrderItem orderItem = returnOrder.getTradeOrderItem();
        //增加可销售库存
        if (orderItem != null) {
            tradeStockService.addSalableStock(orderItem.getProductSku().getSkuId(), returnOrder.getReturnCount());
        }
        map.put("success", true);
        return map;
    }

}