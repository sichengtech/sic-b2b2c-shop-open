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
package com.sicheng.admin.trade.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sicheng.admin.settlement.entity.SettlementPayWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.pay.PayConstants;
import com.sicheng.pay.PayException;
import com.sicheng.pay.ShopPay;
import com.sicheng.pay.ShopPayFactory;
import com.sicheng.service.TradeStockService;

/**
 * 订单 Service
 *
 * @author 范秀秀
 * @version 2017-01-05
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeOrderService extends CrudService<TradeOrderDao, TradeOrder> {

    @Autowired
    private TradeStockService tradeStockService;

    /**
     * 成交额
     *
     * @param wrapper
     * @return
     */
    public Map<String, Object> sumByWhere(Wrapper wrapper) {
        return dao.sumByWhere(wrapper);
    }

    /**
     * 取消订单
     *
     * @param tradeOrder
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> queryAndCancelOrder(TradeOrder tradeOrder) {
        Map<String, Object> map = new HashMap<>();
        if (tradeOrder == null || tradeOrder.getPaymentMethodId() == null) {
            return map;
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(PayConstants.OUT_TRADE_NO, tradeOrder.getOutTradeNo());
        try {
            SettlementPayWay way=tradeOrder.getSettlementPayWay();
            if(way==null){
                logger.warn("订单"+tradeOrder.getId()+"的支付方式为空，可能未正确配置支付方式，请管理员处理！");
                return map;
            }
            String payWayNum = way.getPayWayNum();//支付方式编号
            ShopPay shopPay = ShopPayFactory.get(payWayNum);
            Map<String, Object> mapResult = new HashMap<>();
            mapResult = (Map<String, Object>) shopPay.query(paramMap); //交易查询接口
            //第三方支付支付成功，修改订单状态为已支付
            if ("SUCCESS".equals(mapResult.get("tradeState"))) {
                tradeOrder.setOrderStatus("20");//订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
                tradeOrder.setPayOrderTime(new Date());
                super.updateByIdSelective(tradeOrder);
                map.put("message", FYUtils.fyParams("订单") + ":" + +tradeOrder.getId() + FYUtils.fyParams("已付款,不能取消，状态已修改为待发货"));
            } else {
                tradeOrder.setOrderStatus("60");//订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
                tradeOrder.setCancelOrderDate(new Date());
                cancelOrder(tradeOrder);
                map.put("message", FYUtils.fyParams("取消订单成功"));
            }
        } catch (PayException e) {
            map.put("message", FYUtils.fyParams("查询第三方订单发生错误") + e.getMessage());
            logger.error("查询第三方订单发生异常", e);
            return map;
        } catch (RuntimeException e) {
            logger.error("运行时异常：", e);
        }
        return map;
    }

    /**
     * 取消订单
     *
     * @param tradeOrder
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(TradeOrder tradeOrder) {
        super.updateByIdSelective(tradeOrder);
        //更新商品库存
        List<TradeOrderItem> tradeOrderItemList = tradeOrder.getTradeOrderItemList();
        if (tradeOrderItemList.size() > 0) {
            for (TradeOrderItem order : tradeOrderItemList) {
                //减少占用库存
                tradeStockService.reduceOccupyStock(order.getSkuId(), order.getQuantity());
                //增加可销售库存
                tradeStockService.addSalableStock(order.getSkuId(), order.getQuantity());
            }
        }
    }

    /**
     * 确认收货、申请结算
     *
     * @param tradeOrder
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delayedReceipt(TradeOrder tradeOrder) {
        Map<String, Object> map = new HashMap<>();
        TradeOrder order = super.selectOne(new Wrapper().and("order_id =", tradeOrder.getId()));
        BigDecimal orderAmountPaid = order.getAmountPaid();
        if (order.getOffsetAmount() != null) {
            orderAmountPaid = order.getOffsetAmount();
        }
        super.updateByWhereSelective(tradeOrder, new Wrapper().and("order_id =", tradeOrder.getId()));
        map.put("success", true);
        return map;
    }
}