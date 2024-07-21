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
package com.sicheng.pay.weixin;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.sicheng.admin.account.service.AccountService;
import com.sicheng.admin.pay.dao.PayCallbackLogDao;
import com.sicheng.admin.pay.entity.PayCallbackLog;
import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.admin.trade.dao.TradeOrderItemDao;
import com.sicheng.admin.trade.dao.TradeReturnOrderDao;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.pay.PayConstants;
import com.sicheng.pay.PayException;
import com.sicheng.pay.ShopPay;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Security;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: WeixinMpPay</p>
 * <p>描述: 微信小程序支付</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2019年10月15日 下午5:37:56
 */

@Service("weixinMpPay")
public class WeixinMpPay implements ShopPay {

    @Autowired
    TradeOrderDao tradeOrderDao;
    @Autowired
    TradeReturnOrderDao tradeReturnOrderDao;
    @Autowired
    TradeOrderItemDao tradeOrderItemDao;

    //日志
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static volatile ShopPay shopPay = null;

    private final String PAY_MAY_NUM = "weixin_mp";//支付方式编号

    private WeixinMpPay() {
    }

    public static ShopPay getInstance() {
        if (shopPay == null) {
            synchronized (WeixinMpPay.class) {
                if (shopPay == null) {
                    shopPay = new WeixinMpPay();
                }
            }
        }
        return shopPay;
    }

    /**
     * <p>描述:微信支付 </p>
     * @param map
     * @return
     * @throws PayException
     * @see com.sicheng.pay.ShopPay#pay(java.util.Map)
     */
    @Override
    public Object pay(Map<String, Object> map) throws PayException {
        Map<String, Object> resultMap = new HashMap<>();
        //设备号
        String deviceInfo = "WEB";
        //随机字符串
        String nonceStr = Long.toString(System.currentTimeMillis() / 1000);
        logger.info("微信扫码支付-nonceStr:" + nonceStr);
        //商品描述
        String body = (String) map.get(PayConstants.BODY);
        logger.info("微信扫码支付-body:" + body);
        //订单编号号(多个订单号用逗号分割)
        String orderId = (String) map.get(PayConstants.ORDERIDS);
        logger.info("微信扫码支付-订单号:" + orderId);
        //订单价格
        String prices = (String) map.get(PayConstants.PRICES);
        logger.info("微信扫码支付-订单价格:" + prices);
        //总金额
        String amountPaid = (String) map.get(PayConstants.AMOUNTPAID);
        logger.info("微信扫码支付-订单总金额:" + amountPaid);
        //商户订单号
        String outTradeNo = (String) map.get(PayConstants.OUT_TRADE_NO);
        logger.info("微信扫码支付-商户订单号:" + outTradeNo);
        //openid
        String openid = (String) map.get(PayConstants.OPEN_ID);
        logger.info("微信扫码支付-商户订单号:" + openid);
        //ip
        String ip = R.getRealIp();
        logger.info("微信扫码支付-ip：" + ip);
        //交易类型
        String tradeType = "JSAPI";
        //验证参数
        if (StringUtils.isBlank(body)) {
            throw new PayException("商品描述不能为空");
        }
        if (StringUtils.isBlank(orderId)) {
            throw new PayException("订单编号不能为空");
        }
        if (StringUtils.isBlank(prices)) {
            throw new PayException("订单价格不能为空");
        }
        if (StringUtils.isBlank(amountPaid)) {
            throw new PayException("订单总金额不能为空");
        }
        if (StringUtils.isBlank(outTradeNo)) {
            throw new PayException("商户订单号不能为空");
        }
        if (StringUtils.isBlank(openid)) {
            throw new PayException("openid不能为空");
        }
        if (StringUtils.isBlank(ip)) {
            ip = "127.0.0.1";
        }
        //组装参数
        Map<String, String> packageParams = new HashMap<String, String>();
        WeixinConfig weixinConfig = new WeixinConfig(PAY_MAY_NUM);
        String appid = weixinConfig.getAppID();
        logger.info("微信扫码支付-appid:" + appid);
        String mchId = weixinConfig.getMchID();
        logger.info("微信扫码支付-mchId:" + mchId);
        String notifyUrl = weixinConfig.getNotifyUrl();
        logger.info("微信扫码支付-notifyUrl:" + notifyUrl);
        int price = (int) (Float.valueOf(amountPaid) * 100);
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mchId);
        packageParams.put("device_info", deviceInfo);
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("body", body);
        packageParams.put("attach", orderId + ";" + prices);
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("total_fee", Integer.toString(price));
        packageParams.put("spbill_create_ip", ip);
        packageParams.put("notify_url", notifyUrl);
        packageParams.put("trade_type", tradeType);
        packageParams.put("openid", openid);
        //签名
        String sign = "";
        Map<String, String> result = new HashMap<>();
        try {
            sign = WXPayUtil.generateSignature(packageParams, weixinConfig.getKey());
            packageParams.put("sign", sign);
            logger.info("微信扫码支付-sign:" + sign);
            WXPay wxpay = new WXPay(weixinConfig, WXPayConstants.SignType.MD5, false);
            //统一下单
            result = wxpay.unifiedOrder(packageParams);
        } catch (Exception e) {
            logger.info("请求微信支付错误：", e);
            throw new PayException("请求微信支付错误");
        }
        String returnCode = result.get("return_code");
        logger.info("微信扫码支付-returnCode:" + returnCode);
        String returnMsg = result.get("return_msg");
        logger.info("微信扫码支付-returnMsg:" + returnMsg);
        //code 标记成功失败，默认0：成功，1：失败、用于alert，2：失败、用于confirm
        if (StringUtils.isBlank(returnCode) || !"SUCCESS".equals(returnCode)) {
            throw new PayException(returnMsg);
        }
        String resultCode = result.get("result_code");
        if (StringUtils.isBlank(resultCode) || !"SUCCESS".equals(resultCode)) {
            throw new PayException(result.get("err_code_des"));
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String timestamp = String.valueOf(new Date().getTime());
        timestamp = timestamp.substring(0, timestamp.length() - 3);

        Map<String, String> returnResult = new HashMap<>();
        returnResult.put("appId", result.get("appid"));//应用ID
        returnResult.put("timeStamp", timestamp);//时间戳
        returnResult.put("nonceStr", nonceStr);//随机字符串
        returnResult.put("package", "prepay_id=" + result.get("prepay_id"));//统一下单接口返回的 prepay_id 参数值，提交格式如：prepay_id=wx2017033010242291fcfe0db70013231072
        returnResult.put("signType", "MD5");//随机字符串
        //签名
        try {
            returnResult.put("paySign", WXPayUtil.generateSignature(returnResult, weixinConfig.getKey()));
        } catch (Exception e) {
            logger.info("微信app支付签名错误：", e);
            throw new PayException("微信app支付签名错误");
        }
        resultMap.put("wxResult", returnResult);
        System.out.println(returnResult);
        return resultMap;
    }

    /**
     * <p>描述: 查询订单 </p>
     *
     * @param map
     * @return
     * @throws PayException
     * @see com.sicheng.pay.ShopPay#query(java.util.Map)
     */
    @Override
    public Object query(Map<String, Object> map) throws PayException {
        Map<String, String> resultMap = new HashMap<>();
        //参数验证
        if (map == null || map.isEmpty()) {
//			resultMap.put("statusCode","400");
//			resultMap.put("statusMsg","缺少参数");
//			return resultMap;
            throw new PayException("查询订单参数不能为空");
        }
        //商户订单号
        String outTradeNo = (String) map.get(PayConstants.OUT_TRADE_NO);
        logger.info("微信查询订单-orderId：" + outTradeNo);
        //微信订单号
        String thirdPayOrderNumber = (String) map.get(PayConstants.THIRD_PAY_ORDER_NUMBER);
        if (StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(thirdPayOrderNumber)) {
//			resultMap.put("statusCode","400");
//			resultMap.put("statusMsg","缺少参数");
//			return resultMap;
            throw new PayException("缺少参数");
        }
        //查询订单
        Map<String, String> wxResult = new HashMap<>();
        try {
            WeixinConfig weixinConfig = new WeixinConfig(PAY_MAY_NUM);
            String nonce_str = Long.toString(System.currentTimeMillis() / 1000);
            logger.info("微信查询订单-nonce_str：" + nonce_str);
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", weixinConfig.getAppID());
            paramMap.put("mch_id", weixinConfig.getMchID());
            if (StringUtils.isNotBlank(outTradeNo)) {
                paramMap.put("out_trade_no", outTradeNo);
            }
            if (StringUtils.isNotBlank(thirdPayOrderNumber)) {
                paramMap.put("transaction_id", thirdPayOrderNumber);
            }
            paramMap.put("nonce_str", nonce_str);
            String sign = WXPayUtil.generateSignature(paramMap, weixinConfig.getKey());//签名
            logger.info("微信查询订单-sign:" + sign);
            paramMap.put("sign", sign);
            WXPay wxpay = new WXPay(weixinConfig, WXPayConstants.SignType.MD5, false);//查询订单
            wxResult = wxpay.orderQuery(paramMap);//查询订单结果
        } catch (Exception e) {
            logger.error("微信查询订单发生错误：", e);
            throw new PayException("微信查询订单发生错误");
        }
        //通讯没有成功
        if (!"SUCCESS".equals(wxResult.get("return_code"))) {
            throw new PayException(wxResult.get("return_msg"));
        }
        //订单存在
        if ("SUCCESS".equals(wxResult.get("result_code"))) {
            resultMap.put("returnResult", "SUCCESS");
        } else {
            //订单不存在
            resultMap.put("returnResult", "FAIL");
        }
        //订单支付成功
        if ("SUCCESS".equals(wxResult.get("result_code")) && "SUCCESS".equals(wxResult.get("trade_state"))) {
            resultMap.put("tradeState", "SUCCESS");
        } else {
            //订单支付失败
            resultMap.put("tradeState", "FAIL");
        }
        return resultMap;
    }

    /**
     * <p>描述: TODO(这里用一句话描述这个方法的作用) </p>
     * @param map
     * @return
     * @throws PayException
     * @see com.sicheng.pay.ShopPay#queryRefund(java.util.Map)
     */
    @Override
    public Object queryRefund(Map<String, Object> map) throws PayException {
        Map<String, Object> resultMap = new HashMap<>();
        //参数验证
        if (map == null || map.isEmpty()) {
            throw new PayException("查询退单参数不能为空");
        }
        //商户订单号
        String outTradeNo = (String) map.get(PayConstants.OUT_TRADE_NO);
        logger.info("微信查询订单-orderId：" + outTradeNo);
        //微信订单号
        String thirdPayOrderNumber = (String) map.get(PayConstants.THIRD_PAY_ORDER_NUMBER);
        //商户退单号
        String outRefundNo = (String) map.get(PayConstants.OUT_REFUND_NO);
        //微信退单号
        String refundId = (String) map.get(PayConstants.THIRD_PAY_REFUND_NO);
        if (StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(thirdPayOrderNumber) && StringUtils.isBlank(outRefundNo) && StringUtils.isBlank(refundId)) {
            throw new PayException("缺少参数");
        }
        //查询订单
        Map<String, String> wxResult = new HashMap<>();
        try {
            WeixinConfig weixinConfig = new WeixinConfig(PAY_MAY_NUM);
            String nonceStr = Long.toString(System.currentTimeMillis() / 1000);
            logger.info("微信查询订单-nonce_str：" + nonceStr);
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", weixinConfig.getAppID());
            paramMap.put("mch_id", weixinConfig.getMchID());
            if (StringUtils.isNotBlank(outTradeNo)) {
                paramMap.put("out_trade_no", outTradeNo);
            }
            if (StringUtils.isNotBlank(thirdPayOrderNumber)) {
                paramMap.put("transaction_id", thirdPayOrderNumber);
            }
            if (StringUtils.isNotBlank(outRefundNo)) {
                paramMap.put("out_refund_no", outRefundNo);
            }
            if (StringUtils.isNotBlank(refundId)) {
                paramMap.put("refund_id", refundId);
            }
            paramMap.put("nonce_str", nonceStr);
            String sign = WXPayUtil.generateSignature(paramMap, weixinConfig.getKey());//签名
            logger.info("微信查询订单-sign:" + sign);
            paramMap.put("sign", sign);
            WXPay wxpay = new WXPay(weixinConfig, WXPayConstants.SignType.MD5, false);//查询订单
            wxResult = wxpay.refundQuery(paramMap);//查询退单结果
        } catch (Exception e) {
            logger.error("微信查询退单发生错误：", e);
            throw new PayException("微信查询退单发生错误");
        }
        //通讯没有成功
        if (!"SUCCESS".equals(wxResult.get("return_code"))) {
            throw new PayException(wxResult.get("return_msg"));
        }
        //退单存在
        if ("SUCCESS".equals(wxResult.get("result_code"))) {
            resultMap.put("returnResult", "SUCCESS");
        } else {
            //退单不存在
            resultMap.put("returnResult", "FAIL");
        }
        //退款申请失败
        if (!"SUCCESS".equals(wxResult.get("result_code"))) {
            resultMap.put("returnResult", "FAIL");
        } else {
            //退款成功
            if ("SUCCESS".equals(wxResult.get("refund_status_0"))) {
                resultMap.put("tradeState", "SUCCESS");
            } else {
                //退款失败
                resultMap.put("tradeState", "FAIL");
            }
        }

        //退款总金额
        BigDecimal refundFee = new BigDecimal(wxResult.get("refund_fee_0"));
        refundFee = refundFee.multiply(new BigDecimal("100"));
        resultMap.put("refundFee", refundFee.toString());
        return resultMap;
    }

    /**
     * <p>描述: 微信申请退款 </p>
     *
     * @param map 请求参数
     */
    @Override
    public Object refund(Map<String, Object> map) throws PayException {
        Map<String, String> resultMap = new HashMap<>();
        try {
            if (map.isEmpty()) {
                logger.error("微信申请退款缺少参数");
//				resultMap.put("returnCode","FAIL");
//				resultMap.put("returnMsg","微信申请退款缺少参数");
//				return resultMap;
                throw new PayException("微信申请退款缺少参数");
            }
            //随机字符串
            String nonceStr = Long.toString(System.currentTimeMillis() / 1000);
            logger.info("微信退款-nonce_str:" + nonceStr);
            //商户订单号
            String outTradeNo = (String) map.get(PayConstants.OUT_TRADE_NO);
            logger.info("微信退款-商户订单号out_trade_no：" + outTradeNo);
            //退款单号
            String outRefundNo = (String) map.get(PayConstants.OUT_REFUND_NO);
            logger.info("微信退款-退款单号out_refund_no：" + outRefundNo);
            //订单总金额
            String totalFee = map.get(PayConstants.AMOUNTPAID).toString();
            logger.info("微信退款-订单总金额total_fee：" + totalFee);
            //退款总金额
            String refundFee = (String) map.get(PayConstants.REFUND_FEE);
            logger.info("微信退款-退款金额refund_fee：" + refundFee);
            //异步通知地址
			/*String notifyUrl=Global.getConfig("wx_returnNotifyurl");
			logger.info("微信退款-异步通知notify_url:"+notifyUrl);*/
            //验证参数
            if (StringUtils.isBlank(outTradeNo)) {
                logger.error("微信申请退款缺少参数-商户订单号为空");
//				resultMap.put("returnCode", "FAIL");
//				resultMap.put("returnMsg", "商户订单号不能为空");
//				return resultMap;
                throw new PayException("微信申请退款缺少参数-商户订单号不能为空");
            }
            if (StringUtils.isBlank(outRefundNo)) {
                logger.error("微信申请退款缺少参数-退款单号为空");
//				resultMap.put("returnCode", "FAIL");
//				resultMap.put("returnMsg", "微信申请退款缺少参数-退款单号不能为空");
//				return resultMap;
                throw new PayException("微信申请退款缺少参数-退款单号不能为空");
            }
            if (StringUtils.isBlank(totalFee)) {
                logger.error("微信申请退款缺少参数-订单总金额为空");
//				resultMap.put("returnCode", "FAIL");
//				resultMap.put("returnMsg", "微信申请退款缺少参数-订单总金额不能为空");
//				return resultMap;
                throw new PayException("微信申请退款缺少参数-订单总金额不能为空");
            }
            if (StringUtils.isBlank(refundFee)) {
                logger.error("微信申请退款缺少参数-退款总金额为空");
//				resultMap.put("returnCode", "FAIL");
//				resultMap.put("returnMsg", "退款总金额不能为空");
//				return resultMap;
                throw new PayException("微信申请退款缺少参数-退款总金额不能为空");
            }
            Map<String, String> packageParams = new HashMap<String, String>();
            WeixinConfig weixinConfig = new WeixinConfig(PAY_MAY_NUM);
            int price1 = (int) (Float.valueOf(totalFee) * 100);
            int price2 = (int) (Float.valueOf(refundFee) * 100);
            packageParams.put("appid", weixinConfig.getAppID());
            packageParams.put("mch_id", weixinConfig.getMchID());
            packageParams.put("nonce_str", nonceStr);
            //packageParams.put("notify_url", notifyUrl);
            packageParams.put("out_trade_no", outTradeNo);
            packageParams.put("out_refund_no", outRefundNo);
            packageParams.put("total_fee", Integer.toString(price1));
            packageParams.put("refund_fee", Integer.toString(price2));
            //签名
            String sign = WXPayUtil.generateSignature(packageParams, weixinConfig.getKey());
            packageParams.put("sign", sign);
            logger.info("微信申请退款-sign:" + sign);
            //拼接需要提交微信的数据
            String xml = WXPayUtil.mapToXml(packageParams);
            String result = null;
            try {
                result = ClientCustomSSL.doRefund(xml, PAY_MAY_NUM);
                if (StringUtils.isBlank(result)) {
                    logger.error("微信申请退款发生错误");
//					resultMap.put("returnCode","FAIL");
//					resultMap.put("returnMsg", "微信申请退款发生错误");
//					return resultMap;
                    throw new PayException("微信申请退款发生错误");
                }
                Map<String, String> refundResultMap = WXPayUtil.xmlToMap(result);
                String returnCode = refundResultMap.get("return_code");
                logger.info("微信申请退款-return_code:" + returnCode);
                if (!returnCode.equals("SUCCESS")) {
                    String returnMsg = refundResultMap.get("return_msg");
                    if (StringUtils.isBlank(returnMsg)) {
                        returnMsg = "微信申请退款发生错误";
                    }
                    logger.info("微信申请退款-return_msg:" + returnMsg);
                    resultMap.put("returnCode", "FAIL");
                    resultMap.put("returnMsg", returnMsg);
                    return resultMap;
                }
                String resultCode = refundResultMap.get("result_code");
                if (!resultCode.equals("SUCCESS")) {
                    String errCodeDes = refundResultMap.get("err_code_des");
                    if (StringUtils.isBlank(errCodeDes)) {
                        errCodeDes = "微信申请退款发生错误";
                    }
                    logger.info("微信申请退款-err_code_des:" + errCodeDes);
                    resultMap.put("returnCode", "FAIL");
                    resultMap.put("returnMsg", errCodeDes);
                    return resultMap;
                }
                resultMap.put("returnCode", "SUCCESS");
                resultMap.put("returnMsg", "微信申请退款成功");
                logger.info("微信退款申请成功");
                return resultMap;
            } catch (PayException e) {
                logger.error("微信申请退款发生错误：", e);
//		    	resultMap.put("returnCode","FAIL");
//				resultMap.put("returnMsg", "微信申请退款发生错误");
//				return resultMap;
                throw new PayException("微信申请退款发生错误");
            }
        } catch (Exception e) {
            logger.error("微信申请退款发生错误：", e);
//			resultMap.put("returnCode","FAIL");
//			resultMap.put("returnMsg", "微信申请退款发生错误");
//			return resultMap;
            throw new PayException("微信申请退款发生错误");
        }
    }

    /**
     * <p>描述: 关闭订单 </p>
     * @param map
     * @return
     * @throws PayException
     * @see com.sicheng.pay.ShopPay#close(java.util.Map)
     */
//	@Override
    public Object close(Map<String, Object> map) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            if (map.isEmpty()) {
                resultMap.put("return_code", "FAIL");
                resultMap.put("return_msg", "缺少参数");
                return resultMap;
            }
            //商户订单号
            String orderId = (String) map.get("orderId");
            logger.info("微信查询订单-orderId：" + orderId);
            //微信订单号
            String transactionId = (String) map.get("transaction_id");
            logger.info("微信查询订单-transactionId：" + orderId);
            if (StringUtils.isBlank(orderId) && StringUtils.isBlank(transactionId)) {
                resultMap.put("return_code", "FAIL");
                resultMap.put("return_msg", "缺少参数");
                return resultMap;
            }
            WeixinConfig weixinConfig = new WeixinConfig(PAY_MAY_NUM);
            String nonce_str = Long.toString(System.currentTimeMillis() / 1000);
            logger.info("微信查询订单-nonce_str：" + nonce_str);
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", weixinConfig.getAppID());
            paramMap.put("mch_id", weixinConfig.getMchID());
            paramMap.put("out_trade_no", orderId);
            paramMap.put("transaction_id", transactionId);
            paramMap.put("nonce_str", nonce_str);
            //签名
            String sign = WXPayUtil.generateSignature(paramMap, weixinConfig.getKey());
            logger.info("微信查询订单-sign:" + sign);
            paramMap.put("sign", sign);
            //关闭订单
            WXPay wxpay = new WXPay(weixinConfig, WXPayConstants.SignType.MD5, false);
            resultMap = wxpay.closeOrder(paramMap);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("return_code", "FAIL");
            resultMap.put("return_msg", "服务发生错误");
            logger.error("微信查询订单发生错误：", e);
            return resultMap;
        }
    }

    /**
     * <p>描述: 下载对账单 </p>
     * @param map
     * @return
     * @throws PayException
     * @see com.sicheng.pay.ShopPay#downloadBill(java.util.Map)
     */
    @Override
    public Object downloadBill(Map<String, Object> map) throws PayException {
        if (map.isEmpty()) {
            throw new PayException("缺少参数");
        }
        //对账账单日期
        String billDate = (String) map.get("bill_date");
        logger.info("微信下载账单-billDate:" + billDate);
        //账单类型
        String billType = (String) map.get("bill_type");
        logger.info("微信下载账单-billType:" + billType);
        Map<String, String> paramMap = new HashMap<>();
        WeixinConfig weixinConfig = new WeixinConfig(PAY_MAY_NUM);
        paramMap.put("appid", weixinConfig.getAppID());
        paramMap.put("mch_id", weixinConfig.getMchID());
        paramMap.put("nonce_str", Long.toString(System.currentTimeMillis() / 1000));
        paramMap.put("bill_date", billDate);
        paramMap.put("bill_type", billType);
        try {
            //签名
            String sign = WXPayUtil.generateSignature(paramMap, weixinConfig.getKey());
            logger.info("微信下载账单-sign:" + sign);
            paramMap.put("sign", sign);
            //下载对账单
            WXPay wxpay = new WXPay(weixinConfig, WXPayConstants.SignType.MD5, false);
            Map<String, String> resultMap = wxpay.downloadBill(paramMap);
            String returnCode = resultMap.get("return_code");
            logger.info("微信下载账单-returnCode:" + returnCode);
            String returnMsg = resultMap.get("return_msg");
            logger.info("微信下载账单-returnMsg:" + returnMsg);
            //code 标记成功失败，默认0：成功，1：失败、用于alert，2：失败、用于confirm
            if (StringUtils.isBlank(returnCode) || !"SUCCESS".equals(returnCode)) {
                throw new PayException(returnMsg);
            }
            Map<String, String> resultMap2 = new HashMap<>();
            resultMap2.put("returnCode", "SUCCESS");
            resultMap2.put("data", resultMap.get("data"));
            return resultMap;
        } catch (Exception e) {
            logger.error("微信下载对账单发生错误：", e);
            throw new PayException("服务发生错误");
        }
    }

    /**
     * <p>描述: 支付回调 </p>
     * @param map
     * @return
     * @throws PayException
     * @see com.sicheng.pay.ShopPay#payCallBack(java.util.Map)
     */
    @Override
    public Object payCallBack() {
        String fail = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>";
        String success = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        try {
            TradeOrderDao tradeOrderDao = SpringContextHolder.getBean(TradeOrderDao.class);
            SettlementPayWayDao settlementPayWayDao = SpringContextHolder.getBean(SettlementPayWayDao.class);
            AccountService accountService = SpringContextHolder.getBean(AccountService.class);
            String xmlMsg = WeixinPayUtil.readData(R.getRequest());
            logger.info("微信回调通知参数：---" + xmlMsg);
            if (StringUtils.isBlank(xmlMsg)) {
                return fail;
            }
            Map<String, String> paramMap = WXPayUtil.xmlToMap(xmlMsg);
            //验证签名
            WeixinConfig weixinConfig = new WeixinConfig(PAY_MAY_NUM);
            WXPay wxPay = new WXPay(weixinConfig);
            Boolean flag = wxPay.isPayResultNotifySignatureValid(paramMap);
            logger.info("微信支付通知-验签：" + flag);
            if (!flag) {
                logger.info("微信支付通知-验签失败");
                return fail;
            }
            //总金额
            Float totalFee = Float.valueOf(paramMap.get("total_fee"));
            logger.info("微信支付通知-totalFee:" + totalFee);
            //订单编号
            String attach = paramMap.get("attach");
            logger.info("微信支付通知-orderId：" + attach);
            String orderId = "";
            String onlinePrice = "";
            if (StringUtils.isNotBlank(attach) && attach.split(";").length > 1) {
                orderId = attach.split(";")[0];
                onlinePrice = attach.split(";")[1];
            }
            //微信订单编号
            String transactionId = paramMap.get("transaction_id");
            logger.info("微信支付通知-微信订单编号：" + transactionId);
            //状态码
            String resultCode = paramMap.get("result_code");
            logger.info("微信支付通知-resultCode：" + resultCode);
            if (!("SUCCESS").equals(resultCode) || StringUtils.isBlank(orderId)) {
                return fail;
            }
            String[] orderIds = orderId.split(",");
            String[] onlinePrices = onlinePrice.split(",");
            //查询支付方式
            SettlementPayWay settlementPayWay = new SettlementPayWay();
            settlementPayWay.setPayWayNum(PAY_MAY_NUM);
            List<SettlementPayWay> settlementPayWayList = settlementPayWayDao.selectByWhere(null, new Wrapper(settlementPayWay));
            Long payWayId = 0L;
            String payWayName = "微信";
            if (settlementPayWayList != null && !settlementPayWayList.isEmpty()) {
                payWayId = settlementPayWayList.get(0).getPayWayId();
                payWayName = settlementPayWayList.get(0).getName();
            }
            //根据订单编号更新订单状态
            for (int i = 0; i < orderIds.length; i++) {
                logger.info("orderIds[i]:" + orderIds[i]);
                TradeOrder tradeOrder = tradeOrderDao.selectById(Long.parseLong(orderIds[i]));
                if (tradeOrder == null) {
                    continue;
                }
                logger.info("微信支付通知-订单状态：" + tradeOrder.getOrderStatus());
                // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
                if (!"10".equals(tradeOrder.getOrderStatus())) {
                    continue;
                }
                tradeOrder.setOrderStatus("20");
                if (onlinePrices.length >= i) {
                    tradeOrder.setOnlinePayMoney(new BigDecimal(onlinePrices[i]));
                }
                tradeOrder.setPaymentMethodId(payWayId);
                tradeOrder.setPaymentMethodName(payWayName);
                tradeOrder.setThirdPayOrderNumber(transactionId);
                tradeOrder.setPayOrderTime(new Date());
                TradeOrder tradeOrder2 = new TradeOrder();
                tradeOrder2.setOrderId(Long.parseLong(orderIds[i]));
                tradeOrderDao.updateByWhere(tradeOrder, new Wrapper(tradeOrder2));

                //订单详情
                List<TradeOrderItem> tradeOrderItemList = tradeOrder.getTradeOrderItemList();

                //修改账户信息
                try {
                    if (tradeOrderItemList.size() == 1) {
                        accountService.paymentProduct(tradeOrderItemList.get(0).getOrderItemId(), new BigDecimal(onlinePrices[i]), new BigDecimal("0"), new BigDecimal(onlinePrices[i]), payWayId, payWayName, transactionId);
                    } else {
                        for (int j = 0; j < tradeOrderItemList.size(); j++) {
                            BigDecimal tradeOrderItemMoney = (tradeOrderItemList.get(j).getPrice()).multiply(new BigDecimal(tradeOrderItemList.get(j).getQuantity()));
                            accountService.paymentProduct(tradeOrderItemList.get(j).getOrderItemId(), tradeOrderItemMoney, new BigDecimal("0"), new BigDecimal(onlinePrices[i]), payWayId, payWayName, transactionId);
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            //插入回调日志
            PayCallbackLogDao payCallbackLogDao = SpringContextHolder.getBean(PayCallbackLogDao.class);
            PayCallbackLog payCallbackLog = new PayCallbackLog();
            payCallbackLog.setPayWayId(payWayId);
            payCallbackLog.setPayWayName(payWayName);
            payCallbackLog.setCallbackInfo(xmlMsg);
            payCallbackLogDao.insertSelective(payCallbackLog);

            return success;
        } catch (Exception e) {
            logger.info("微信支付回调发生错误：", e);
            return fail;
        }
    }

    /**
     * <p>描述: 退款回调 </p>
     *
     * @return
     */
    //@Override
    public Object refundCallBack() throws PayException {
        try {
            String xmlMsg = WeixinPayUtil.readData(R.getRequest());
            if (StringUtils.isBlank(xmlMsg)) {
                return false;
            }
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlMsg);
            //状态码
            String return_code = resultMap.get("return_code");
            logger.info("微信退款通知-return_code：" + return_code);
            if (!("SUCCESS").equals(return_code) || StringUtils.isBlank(return_code)) {
                return false;
            }
            String base64Result = resultMap.get("req_info");
            logger.info("微信退款-base64Result:" + base64Result);
            Security.addProvider(new BouncyCastleProvider());
            String result = WeixinPayUtil.decryptData(base64Result, PAY_MAY_NUM);
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            if (map.isEmpty()) {
                return false;
            }
            //状态码
            String refund_status = map.get("refund_status");
            logger.info("微信退款通知-refund_status：" + refund_status);
            if (!("SUCCESS").equals(refund_status) || StringUtils.isBlank(refund_status)) {
                return false;
            }
            //退款单号
            String outRefundNo = map.get("out_refund_no");
            logger.info("微信退款通知-out_refund_no：" + outRefundNo);
            if (StringUtils.isBlank(outRefundNo)) {
                return false;
            }
            //商户号
            String outTradeNo = map.get("out_trade_no");
            logger.info("微信退款通知-out_trade_no：" + outTradeNo);
            //退款金额
            String refundFee = map.get("refund_fee");
            logger.info("微信退款通知-refund_fee：" + refundFee);
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setOutTradeNo(outTradeNo);
            List<TradeOrder> tradeOrders = tradeOrderDao.selectByWhere(null, new Wrapper(tradeOrder));
            if (tradeOrders.isEmpty()) {
                return false;
            }
            TradeReturnOrder tradeReturnOrder = tradeReturnOrderDao.selectById(Long.parseLong(outRefundNo));
            String type = tradeReturnOrder.getType();// 类型，1退货退款、2退款
            if ("1".equals(type)) {
                //修改订单详情(10退货/款成功、20退货/款失败)
                TradeOrderItem tradeOrderItem = new TradeOrderItem();
                tradeOrderItem.setReturnStatus("10");
                tradeOrderItemDao.updateByWhereSelective(tradeOrderItem, new Wrapper().and("a.order_item_id=", tradeReturnOrder.getOrderItemId()));
                //修改退款退货订单(状态：10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
                TradeReturnOrder t = new TradeReturnOrder();
                t.setSystemHandle(1L);
                t.setStatus("60");
                t.setSystemAgreeTime(new Date());
                t.setPayWayId(tradeOrders.get(0).getPaymentMethodId());
                t.setOnlineReturnMoney(new BigDecimal(refundFee).divide(new BigDecimal("100")));
                tradeReturnOrderDao.updateByWhereSelective(t, new Wrapper().and("a.return_order_id=", tradeReturnOrder.getReturnOrderId()));
//				//修改订单状态(10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消、70退货退款中、80交易关闭)
//				TradeOrder to = new TradeOrder();
//				to.setOrderId(tradeReturnOrder.getOrderId());
//				to.setOrderStatus("40");
//				to.setProductReceiptDate(new Date());
//				tradeOrderDao.updateByIdSelective(to);
            }
            if ("2".equals(type)) {
                //修改订单状态(10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消、70退货退款中、80交易关闭)
//				TradeOrder to = new TradeOrder();
//				to.setOrderId(tradeReturnOrder.getOrderId());
//				to.setOrderStatus("40");
//				to.setProductReceiptDate(new Date());
//				tradeOrderDao.updateByIdSelective(to);
                //修改订单详情(10退货/款成功、20退货/款失败)
                TradeOrderItem tradeOrderItem = new TradeOrderItem();
                tradeOrderItem.setReturnStatus("10");
                tradeOrderItemDao.updateByWhereSelective(tradeOrderItem, new Wrapper().and("a.order_id=", tradeReturnOrder.getOrderId()));
                //修改退款退货订单(状态：10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
                TradeReturnOrder t = new TradeReturnOrder();
                t.setSystemHandle(1L);
                t.setStatus("60");
                t.setSystemAgreeTime(new Date());
                t.setPayWayId(tradeOrders.get(0).getPaymentMethodId());
                t.setOnlineReturnMoney(new BigDecimal(refundFee).divide(new BigDecimal("100")));
                tradeReturnOrderDao.updateByWhereSelective(t, new Wrapper().and("a.order_id=", tradeReturnOrder.getOrderId()));
            }
            //查询是否有订单未退款完成
            List<TradeOrderItem> tradeOrderItemList = tradeOrderItemDao.selectByWhere(null, new Wrapper().and("order_id=", tradeReturnOrder.getOrderId())
                    .and("is_return_status<>", 0).and("return_status is null").and("order_item_id <>", tradeReturnOrder.getTradeOrderItem().getOrderItemId()));
            if (tradeOrderItemList.isEmpty()) {
                //修改订单状态(10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消、70退货退款中、80交易关闭)
                TradeOrder to = new TradeOrder();
                to.setOrderId(tradeReturnOrder.getOrderId());
                to.setOrderStatus("40");
                to.setProductReceiptDate(new Date());
                tradeOrderDao.updateByIdSelective(to);
            }
//			TradeReturnOrder tradeReturnOrder = new TradeReturnOrder();
//			tradeReturnOrder.setReturnOrderId(Long.parseLong(outRefundNo));
//			tradeReturnOrder.setPayWayId(tradeOrders.get(0).getPaymentMethodId());
//			tradeReturnOrder.setOnlineReturnMoney(new BigDecimal(refundFee));
//			tradeReturnOrderDao.updateByIdSelective(tradeReturnOrder);
//			TradeReturnOrder tradeReturnOrder = tradeReturnOrderDao.selectById(Long.parseLong(outRefundNo));
//			Long orderId = tradeReturnOrder.getOrderId();
//			//修改订单状态(10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消、70退货退款中、80交易关闭)
//			TradeOrder tradeOrder = new TradeOrder();
//			tradeOrder.setOrderId(orderId);
//			tradeOrder.setOrderStatus("40");
//			tradeOrderDao.updateByIdSelective(tradeOrder);
//			//修改订单详情(10退货/款成功、20退货/款失败)
//			TradeOrderItem tradeOrderItem = new TradeOrderItem();
//			tradeOrderItem.setReturnStatus("10");
//			tradeOrderItemDao.updateByWhereSelective(tradeOrderItem, new Wrapper().and("a.order_id=",orderId));
//			//修改退款退货订单(状态：10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
//			TradeReturnOrder t = new TradeReturnOrder();
//			t.setSystemHandle(2L);
//			t.setStatus("60");
//			t.setSystemHandleRemarks(tradeReturnOrder.getSystemHandleRemarks());
//			tradeReturnOrderDao.updateByWhereSelective(tradeReturnOrder, new Wrapper().and("a.order_id=",orderId));
            return true;
        } catch (Exception e) {
            logger.info("微信退款回调发生错误：", e);
            return false;
        }
    }


    /**
     * 发送xml数据,获取返回结果
     *
     * @param requestUrl
     * @param requestMethod
     * @param xmlStr
     * @return
     */
    /*public Map<String, Object> httpXmlRequest(String requestUrl, String requestMethod, String xmlStr) {
        // 将解析结果存储在HashMap中
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpsURLConnection urlCon = (HttpsURLConnection) (new URL(requestUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            // 设置请求方式（GET/POST）
            urlCon.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                urlCon.connect();
            }

            urlCon.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes().length));
            urlCon.setUseCaches(false);
            // 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            if (null != xmlStr) {
                OutputStream outputStream = urlCon.getOutputStream();
                outputStream.write(xmlStr.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            }
            InputStream inputStream = urlCon.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStreamReader);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            urlCon.disconnect();
        } catch (MalformedURLException e) {
        	logger.error(e.getMessage());
        } catch (IOException e) {
        	logger.error(e.getMessage());
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return map;
    }*/
    public static void main(String[] args) {
        String xmlStr = "<xml><sign>88E6773259F95FCE7D58EFE4B9913B2E</sign><body>测试商品</body><mch_id>1396812602</mch_id><spbill_create_ip>127.0.0.1</spbill_create_ip><total_fee>100</total_fee><attach>18032700000031</attach><sign_type>MD5</sign_type><notify_url>http://demo.sicheng.net/wap/weixin/payNotify.htm</notify_url><appid>wx4e1a5094a8463318</appid><out_trade_no>18032700000031</out_trade_no><nonce_str>1522121170</nonce_str><device_info>WEB</device_info><trade_type>NATIVE</trade_type></xml>";
        String key = "60a4e87f706ee07758c41a3f40446844";
        try {
            Boolean b = WXPayUtil.isSignatureValid(xmlStr, key);
            System.err.println(b);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
