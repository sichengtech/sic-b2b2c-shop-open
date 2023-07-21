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
package com.sicheng.pay.web;

import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.Encodes;
import com.sicheng.common.utils.IdWorker;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.pay.PayConstants;
import com.sicheng.pay.PayException;
import com.sicheng.pay.ShopPay;
import com.sicheng.pay.ShopPayFactory;
import com.sicheng.seller.settlement.service.SettlementPayWayService;
import com.sicheng.seller.site.service.SiteInfoService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: ShopPayController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 * @author fanxiuxiu
 * @version 2018年4月20日 下午2:49:35
 */
@Controller
@RequestMapping(value = "${memberPath}/shop/pay")
public class ShopPayController extends BaseController {

    @Autowired
    TradeOrderService tradeOrderService;
    @Autowired
    SettlementPayWayService settlementPayWayService;
    @Autowired
    private SiteInfoService siteInfoService;

    /**
     * 统一下单
     * @param orderIds    多个订单编号
     * @param payWayId    支付方式id
     * @return
     * @throws PayException
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "unifiedorder")
    public String unifiedorder(String orderIds, String payWayId, RedirectAttributes redirectAttributes) {
        try {
            if (StringUtils.isBlank(orderIds)) {
                throw new PayException("订单编号不能为空");
            }
            if (StringUtils.isBlank(payWayId)) {
                throw new PayException("支付方式id不能为空");
            }
            if (!StringUtils.isNumeric(payWayId)) {
                throw new PayException("支付方式id只能为数字");
            }
            String[] orderIdss = orderIds.split(",");
            List<String> orderIdList = Arrays.asList(orderIdss);
            Long uId = SsoUtils.getUserMain().getUId();
            //查询订单
            List<TradeOrder> tradeOrderList = tradeOrderService.selectByWhere(new Wrapper().and("u_id =", uId).and("order_id in", orderIdList));
            if (tradeOrderList.isEmpty()) {
                throw new PayException("订单不存在");
            }
            SettlementPayWay payWayCondition = new SettlementPayWay();
            payWayCondition.setPayWayId(Long.parseLong(payWayId));
            payWayCondition.setStatus("1");//状态：0关闭，1开启
            payWayCondition.setUseTerminal("0");//支付方式使用终端,0pc、1移动端
            List<SettlementPayWay> payWayList = settlementPayWayService.selectByWhere(new Wrapper(payWayCondition));
            if (payWayList == null || payWayList.isEmpty()) {
                throw new PayException("支付方式不存在");
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
                        //throw new PayException("该订单已支付成功，请不要重复支付，请到订单列表刷新订单状态");
                        return "redirect:" + frontPath + "/trade/pay/payOk.htm";
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
            //查询订单
            //List<TradeOrder> tradeOrderList=tradeOrderService.selectByIdIn(orderIdList);
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
            String body = "B2B商城商品";
            if (siteInfo != null) {
                body = siteInfo.getName() + "商品";
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put(PayConstants.ORDERIDS, orderIds);
            paramMap.put(PayConstants.BODY, body);
            paramMap.put(PayConstants.AMOUNTPAID, amountPaid.toString());
            paramMap.put(PayConstants.OUT_TRADE_NO, outTradeNo);
            paramMap.put(PayConstants.PRICES, prices);
            paramMap.put(PayConstants.PAY_WAY_ID, Long.parseLong(payWayId));
            ShopPay shopPay = ShopPayFactory.get(settlementPayWayNew.getPayWayNum());
            if (shopPay != null) {
                Map<String, Object> resultMap = (Map<String, Object>) shopPay.pay(paramMap);
                if (!resultMap.isEmpty() && resultMap.get("codeUrl") != null) {
                    String codeUrl = (String) resultMap.get("codeUrl");
                    return "redirect:" + frontPath + "/trade/pay/weixin/view.htm?orderIds=" + orderIds + "&codeUrl=" + Encodes.urlEncode(codeUrl);
                } else {
                    String returnHtml = (String) resultMap.get("returnHtml");
                    redirectAttributes.addFlashAttribute("returnHtml", returnHtml);
                    return "redirect:" + frontPath + "/trade/pay/ali/view.htm";
                }
            }
            return null;
        } catch (PayException e) {
            logger.error("统一下单接口异常：", e);
            addMessage(redirectAttributes, e.getMessage());
            return "redirect:" + frontPath + "/trade/order/tradeCheckorder.htm?orderIds=" + orderIds;
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
