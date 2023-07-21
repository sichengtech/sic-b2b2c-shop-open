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
 *  
 */
package com.sicheng.wap.web.api;

import com.gexin.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.IdWorker;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.pay.PayConstants;
import com.sicheng.pay.PayException;
import com.sicheng.pay.ShopPay;
import com.sicheng.pay.ShopPayFactory;
import com.sicheng.wap.service.SettlementPayWayService;
import com.sicheng.wap.service.SiteInfoService;
import com.sicheng.wap.service.TradeOrderService;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import com.sicheng.common.utils4m.WeiXinUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
  * <p>标题: ShopPayController</p>
  * <p>描述: 移动端支付</p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2019年10月12日 下午2:06:39
  *
  
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class ShopPayController extends BaseController {

    @Autowired
    TradeOrderService tradeOrderService;
    @Autowired
    SettlementPayWayService settlementPayWayService;
    @Autowired
    private SiteInfoService siteInfoService;

    /**
     * 获取支付方式数据
      * @param payWayNum 支付方式编号
      * @param status 支付方式状态，0关闭、1开启
      * @param useTerminal 支付方式使用终端,0pc、1移动端
      * @return
      
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/shop/pay/payWayList")
    public Map<String, Object> payWayList(String payWayNum, String useTerminal) {
        // 处理参数
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(useTerminal)) {
            errorList.add(FYUtils.fy("支付方式使用终端不能为空"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            SettlementPayWay settlementPayWay = new SettlementPayWay();
            settlementPayWay.setPayWayNum(payWayNum);
            settlementPayWay.setStatus("1");//支付方式状态，0关闭、1开启
            settlementPayWay.setUseTerminal(useTerminal);//支付方式使用终端,0pc、1移动端
            List<SettlementPayWay> payWayList = settlementPayWayService.selectByWhere(new Wrapper(settlementPayWay));
            if (payWayList == null || payWayList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("支付方式不存在"), null, null);
            }
            List<Map<String, Object>> payWayDatas = Lists.newArrayList();
            for (SettlementPayWay payWay : payWayList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", payWay.getId());//支付方式id
                map.put("name", payWay.getName());//支付方式名称
                map.put("payWayNum", payWay.getPayWayNum());//支付方式编号
                payWayDatas.add(map);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取支付方式成功"), payWayDatas, null);
        } catch (Exception e) {
            logger.error("获取支付方式出现错误：", e);
            message = FYUtils.fy("服务器出现错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 统一下单
     * @param orderIds    多个订单编号
     * @param payWayId    支付方式id
     * @param code            获取微信小程序openid的code
     * @return
     * @throws PayException
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/shop/pay/unifiedorder")
    public Map<String, Object> unifiedorder(String orderIds, String payWayId, String code) {
        // 处理参数
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(orderIds)) {
            errorList.add(FYUtils.fy("订单编号不能为空"));
        }
        if (StringUtils.isBlank(payWayId)) {
            errorList.add(FYUtils.fy("支付方式id不能为空"));
        }
        if (!StringUtils.isNumeric(payWayId)) {
            errorList.add(FYUtils.fy("支付方式id只能为数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            String[] orderIdss = orderIds.split(",");
            List<String> orderIdList = Arrays.asList(orderIdss);
            Long uId = AppTokenUtils.findUser().getUId();
            //查询订单
            List<TradeOrder> tradeOrderList = tradeOrderService.selectByWhere(new Wrapper().and("u_id =", uId).and("order_id in", orderIdList));
            if (tradeOrderList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("订单不存在"), null, null);
            }
            SettlementPayWay payWayCondition = new SettlementPayWay();
            payWayCondition.setPayWayId(Long.parseLong(payWayId));
            payWayCondition.setStatus("1");//状态：0关闭，1开启
            List<SettlementPayWay> payWayList = settlementPayWayService.selectByWhere(new Wrapper(payWayCondition));
            if (payWayList == null || payWayList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("支付方式不存在"), null, null);
            }
            SettlementPayWay settlementPayWayNew = payWayList.get(0);
            //查询支付方式
            TradeOrder tradeOrder = tradeOrderList.get(0);
            SettlementPayWay settlementPayWayOld = tradeOrder.getSettlementPayWay();
            //支付方式不为空，查询订单
            if (settlementPayWayOld != null && StringUtils.isNoneBlank(settlementPayWayOld.getPayWayNum())) {
                ShopPay shopPay = ShopPayFactory.get(settlementPayWayOld.getPayWayNum());
                if (shopPay != null) {
                    Map<String, Object> queryParamMap = new HashMap<>();
                    queryParamMap.put(PayConstants.OUT_TRADE_NO, tradeOrder.getOutTradeNo());
                    Map<String, Object> queryResultmap = (Map<String, Object>) shopPay.query(queryParamMap);
                    //订单已经支付过，将订单状态改为20(已支付)
                    if (queryParamMap != null && "SUCCESS".equals(queryResultmap.get("tradeState"))) {
                        TradeOrder order = new TradeOrder();
                        order.setOrderStatus("20");//10待付款、20待发货、30待收货、40(已完成)待评价、 50已评价、60已取消、70退货退款中
                        tradeOrderService.updateByWhereSelective(order, new Wrapper().and("u_id =", uId).and("out_trade_no in", orderIdList));
                        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("支付成功"), null, null);
                    }
                }
            }
            //设置支付方式和商户订单号
            TradeOrder tradeOrder2 = new TradeOrder();
            String outTradeNo = String.valueOf(IdWorker.getId());
            tradeOrder2.setOutTradeNo(outTradeNo);
            tradeOrder2.setPaymentMethodId(Long.parseLong(payWayId));
            tradeOrder2.setPaymentMethodName(settlementPayWayNew.getName());
            tradeOrderService.updateByWhereSelective(tradeOrder2, new Wrapper().and("u_id =", uId).and("order_id in", orderIdList));
            //总价格
            BigDecimal amountPaid = new BigDecimal("0");
            //多个订单价格
            String prices = "";
            for (int i = 0; i < tradeOrderList.size(); i++) {
                TradeOrder order = tradeOrderList.get(i);
                BigDecimal amount = order.getOffsetAmount() != null ? order.getOffsetAmount() : order.getAmountPaid();
                amountPaid = amountPaid.add(amount);
                String spritStr = i == tradeOrderList.size() - 1 ? "" : ",";
                prices += amount + spritStr;
            }
            SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
            String body = FYUtils.fy("B2B商城商品");
            if (siteInfo != null) {
                body = siteInfo.getName() + FYUtils.fy("商品");
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put(PayConstants.ORDERIDS, orderIds);
            paramMap.put(PayConstants.BODY, body);
            paramMap.put(PayConstants.AMOUNTPAID, amountPaid.toString());
            paramMap.put(PayConstants.OUT_TRADE_NO, outTradeNo);
            paramMap.put(PayConstants.PRICES, prices);
            paramMap.put(PayConstants.PAY_WAY_ID, Long.parseLong(payWayId));
            if ("weixin_mp".equals(settlementPayWayNew.getPayWayNum())) {
                String openid = WeiXinUtils.getOpenId(code);
                paramMap.put(PayConstants.OPEN_ID, openid);//openid 微信小程序支付使用
            }
            ShopPay shopPay = ShopPayFactory.get(settlementPayWayNew.getPayWayNum());
            if (shopPay != null) {
                Map<String, Object> resultMap = (Map<String, Object>) shopPay.pay(paramMap);
                if (!resultMap.isEmpty() && resultMap.get("wxResult") != null) {
                    return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("统一下单成功"), JSON.toJSON(resultMap.get("wxResult")), null);
                } else {
                    String returnHtml = (String) resultMap.get("returnHtml");
                    return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("统一下单成功"), returnHtml, null);
                }
            }
            return null;
        } catch (PayException e) {
            logger.error("统一下单接口异常：", e);
            message = FYUtils.fy("服务器出现错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * shop回调地址
     */
    @RequestMapping(value = "callback/{payWay}")
    public void payCallBack(@PathVariable String payWay) {
        logger.info("======进入支付回调了=========");
        try {
            ShopPay shopPay = ShopPayFactory.get(payWay);
            String xml = (String) shopPay.payCallBack();
            R.writeHtml(xml, "UTF-8");
            return;
        } catch (Exception e) {
            logger.error("支付回调发生错误：", e);
        }
    }
}
