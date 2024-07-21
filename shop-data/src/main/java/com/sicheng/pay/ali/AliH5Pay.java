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
package com.sicheng.pay.ali;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.sicheng.admin.account.service.AccountService;
import com.sicheng.admin.pay.dao.PayCallbackLogDao;
import com.sicheng.admin.pay.entity.PayCallbackLog;
import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.pay.PayConstants;
import com.sicheng.pay.PayException;
import com.sicheng.pay.ShopPay;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>标题: AliWapPay</p>
 * <p>描述: 手机网站支付宝支付</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2019年10月14日 下午3:51:56
 */
@Service("aliH5Pay")
public class AliH5Pay implements ShopPay {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(AliH5Pay.class);

    private static volatile ShopPay shopPay = null;

    private final String PAY_MAY_NUM = "ali_h5";//支付方式编号

    private AliH5Pay() {
    }

    public static ShopPay getInstance() {
        if (shopPay == null) {
            synchronized (AliH5Pay.class) {
                if (shopPay == null) {
                    shopPay = new AliH5Pay();
                }
            }
        }
        return shopPay;
    }

    /**
     * <p>生成 APP支付订单</p>
     *
     * @param map 业务参数
     *            orderIds		多个订单编号
     *            prices			多个订单价格
     *            outTradeNo		商户订单号
     *            amountPaid		付款金额(订单总金额)
     *            body			订单标题(商品描述)
     *            payWayId		支付方式id
     * @return
     */
    @Override
    public Object pay(Map<String, Object> map) throws PayException {
        logger.info("======>支付宝统一下单");
        //返回值
        Map<String, String> resultMap = new HashMap<>();
        if (map.isEmpty()) {
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", "支付宝统一下单参数错误");
            return resultMap;
        }
        //业务参数
        String orderIds = (String) map.get(PayConstants.ORDERIDS);//shop商户多个订单号
        logger.info("支付宝支付-shop商户多个订单号orderIds：" + orderIds);
        String prices = (String) map.get(PayConstants.PRICES);//shop商户多个订单金额
        logger.info("支付宝支付-shop商户多个订单金额prices：" + prices);
        String outTradeNo = (String) map.get(PayConstants.OUT_TRADE_NO);//商户订单号，必填
        logger.info("支付宝支付-商户订单号out_trade_no：" + outTradeNo);
        String amountPaid = (String) map.get(PayConstants.AMOUNTPAID);//付款总金额，必填
        logger.info("支付宝支付-付款总金额total_amount：" + amountPaid);
        String payWayId = map.get(PayConstants.PAY_WAY_ID).toString();//支付方式id
        logger.info("支付宝支付-支付方式payWayId：" + payWayId);
        String subject = (String) map.get(PayConstants.BODY);//订单标题(商品描述)，必填
        logger.info("支付宝支付-订单名称subject：" + subject);
        String productCode = "FAST_INSTANT_TRADE_PAY";//销售产品码
        logger.info("支付宝支付-销售产品码product_code：" + productCode);
        String timeoutExpress = "120m";//支付宝订单失效时间(表示2小时之内如果未付款支付宝自动关闭订单)
        logger.info("支付宝支付-支付宝订单失效时间timeout_express：" + timeoutExpress);
        if (StringUtils.isBlank(orderIds)) {
            logger.error("======>支付宝统一下单参数错误:shop商户多个订单号不能为空");
            throw new PayException("支付宝统一下单参数错误:shop商户多个订单号不能为空");
        }
        if (StringUtils.isBlank(prices)) {
            logger.error("======>支付宝统一下单参数错误:shop商户多个订单价格不能为空");
            throw new PayException("支付宝统一下单参数错误:shop商户多个订单价格不能为空");
        }
        if (StringUtils.isBlank(outTradeNo)) {
            logger.error("======>支付宝统一下单参数错误:商户订单号不能为空");
            throw new PayException("支付宝统一下单参数错误:商户订单号不能为空");
        }
        if (StringUtils.isBlank(amountPaid)) {
            logger.error("======>支付宝统一下单参数错误:付款总金额不能为空");
            throw new PayException("支付宝统一下单参数错误:付款总金额不能为空");
        }
        if (StringUtils.isBlank(payWayId)) {
            logger.error("======>支付宝统一下单参数错误:支付方式id不能为空");
            throw new PayException("支付宝统一下单参数错误:支付方式id不能为空");
        }
        if (StringUtils.isBlank(subject)) {
            logger.error("======>支付宝统一下单参数错误:订单标题不能为空");
            throw new PayException("支付宝统一下单参数错误:订单标题不能为空");
        }
        //初始化
        AliPayConfig aliPayConfig = new AliPayConfig(PAY_MAY_NUM);
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getUrl(), aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), aliPayConfig.getFormat(), aliPayConfig.getCharset(), aliPayConfig.getAppPublicKey(), aliPayConfig.getSignType());
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.page.pay
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //包装调取支付接口
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);
        model.setProductCode(productCode);
        model.setTotalAmount(amountPaid);
        model.setSubject(subject);
        model.setTimeoutExpress(timeoutExpress);
        model.setPassbackParams(orderIds + ";" + prices + ";" + payWayId);
        request.setBizModel(model);
        //request.setReturnUrl(aliPayConfig.getReturnUrl());//同步返回地址，HTTP/HTTPS开头字符串
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());//支付宝服务器主动通知商户服务器里指定的页面http/https路径。
        try {
            //请求
            String result = alipayClient.pageExecute(request).getBody();
            System.out.println(alipayClient.pageExecute(request).isSuccess());
            logger.info("======>支付宝统一下单成功，返回结果：" + result);
            resultMap.put("returnCode", "SUCCESS");
            //支付宝统一下单返回结果 使用Base64进行编码
            Base64 base64 = new Base64();
            result = base64.encodeToString(result.getBytes(StandardCharsets.UTF_8));//编码
            resultMap.put("returnHtml", result);
            return resultMap;
        } catch (AlipayApiException e) {
            logger.error("======>支付宝统一下单出现问题", e);
            throw new PayException("支付宝统一下单出现问题");
        }
    }


    /**
     * <p>支付宝订单查询 </p>
     *
     * @param map 业务参数
     * @return
     */
    @Override
    public Object query(Map<String, Object> map) throws PayException {
        //业务参数验证
        Map<String, String> resultMap = new HashMap<>();
        if (map.isEmpty()) {
            throw new PayException("查询订单参数不能为空");
        }

        String outTradeNo = (String) map.get(PayConstants.OUT_TRADE_NO);//商户订单号
        logger.info("支付宝查询订单-orderId：" + outTradeNo);
        String thirdPayOrderNumber = (String) map.get(PayConstants.THIRD_PAY_ORDER_NUMBER);//支付宝订单号
        if (StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(thirdPayOrderNumber)) {
            throw new PayException("缺少参数");
        }
        //初始化
        AliPayConfig aliPayConfig = new AliPayConfig(PAY_MAY_NUM);
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getUrl(), aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), aliPayConfig.getFormat(), aliPayConfig.getCharset(), aliPayConfig.getAppPublicKey(), aliPayConfig.getSignType());
        //业务处理
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        if (StringUtils.isNotBlank(outTradeNo)) {
            model.setOutTradeNo(outTradeNo);
        }
        if (StringUtils.isNotBlank(thirdPayOrderNumber)) {
            model.setTradeNo(thirdPayOrderNumber);
        }
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            String result = response.getBody();
            JSONObject object = JSON.parseObject(result);
            Map<String, Object> resultObject = (Map<String, Object>) object.get("alipay_trade_query_response");
            //订单存在
            if ("10000".equals(resultObject.get("code"))) {
                resultMap.put("returnResult", "SUCCESS");
            } else {
                //订单不存在
                resultMap.put("returnResult", "FAIL");
            }
            //订单支付成功
            if ("10000".equals(resultObject.get("code")) && "TRADE_SUCCESS".equals(resultObject.get("trade_status"))) {
                resultMap.put("tradeState", "SUCCESS");
            } else {
                //订单支付失败
                resultMap.put("tradeState", "FAIL");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝查询订单发生错误：", e);
            throw new PayException("支付宝查询订单发生错误");
        }
        return resultMap;
    }

    /**
     * <p>支付宝订单退款查询 </p>
     *
     * @param map 业务参数
     *            orderId			订单编号
     *            refundNumber	退款请求号
     * @return
     */
    @Override
    public Object queryRefund(Map<String, Object> map) throws PayException {
        //业务参数验证
        Map<String, String> resultMap = new HashMap<>();
        if (map.isEmpty()) {
            throw new PayException("查询订单参数不能为空");
        }

        String outTradeNo = (String) map.get(PayConstants.OUT_TRADE_NO);//商户订单号
        logger.info("支付宝查询订单-orderId：" + outTradeNo);
        String thirdPayOrderNumber = (String) map.get(PayConstants.THIRD_PAY_ORDER_NUMBER);//支付宝订单号
        //商户退单号
        String outRequestNo = (String) map.get(PayConstants.OUT_REFUND_NO);
        if (StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(thirdPayOrderNumber)) {
            throw new PayException("缺少参数,请传入商户订单号或支付宝订单号");
        }
        if (StringUtils.isBlank(outRequestNo)) {
            throw new PayException("缺少参数,请传入商户退单号");
        }
        //初始化
        AliPayConfig aliPayConfig = new AliPayConfig(PAY_MAY_NUM);
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getUrl(), aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), aliPayConfig.getFormat(), aliPayConfig.getCharset(), aliPayConfig.getAppPublicKey(), aliPayConfig.getSignType());
        //业务处理
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        if (StringUtils.isNotBlank(outTradeNo)) {
            model.setOutTradeNo(outTradeNo);
        }
        if (StringUtils.isNotBlank(thirdPayOrderNumber)) {
            model.setTradeNo(thirdPayOrderNumber);
        }
        model.setOutRequestNo(outRequestNo);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            String result = response.getBody();
            JSONObject object = JSON.parseObject(result);
            Map<String, Object> resultObject = (Map<String, Object>) object.get("alipay_trade_fastpay_refund_query_response");
            //退单存在
            if (resultObject == null) {
                //订单不存在
                resultMap.put("returnResult", "FAIL");
            } else {
                //退单存在
                if ("10000".equals(resultObject.get("code"))) {
                    resultMap.put("returnResult", "SUCCESS");
                }
                //退款成功
                if ("10000".equals(resultObject.get("code")) && resultObject.get("refund_royaltys") != null) {
                    resultMap.put("tradeState", "SUCCESS");
                } else {
                    //退款失败
                    resultMap.put("tradeState", "FAIL");
                }
                //退款金额
                resultMap.put("refundFee", resultObject.get("refund_amount").toString());
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝查询订单发生错误：", e);
            throw new PayException("支付宝查询订单发生错误");
        }
        return resultMap;
    }

    /**
     * <p>支付宝订单退款 </p>
     *
     * @param map 业务参数
     *            outTradeNo		商户订单号
     *            refundFee		退款金额
     *            outRefundNo		退款单号
     * @return
     */
    @Override
    public Object refund(Map<String, Object> map) throws PayException {

        logger.info("======>支付宝订单退款");
        Map<String, String> resultMap = new HashMap<>();
        if (map.isEmpty()) {
            logger.error("======>支付宝订单退款参数错误");
//			resultMap.put("return_code","FAIL");
//			resultMap.put("return_msg", "支付宝订单退款参数错误");
//			return resultMap;
            throw new PayException("支付宝订单退款参数错误");
        }
        //业务参数
        String outTradeNo = (String) map.get(PayConstants.OUT_TRADE_NO);//商户订单号
        logger.info("支付宝退款-商户订单号out_trade_no：" + outTradeNo);
        String refundAmount = (String) map.get(PayConstants.REFUND_FEE);//退款金额
        logger.info("支付宝退款-退款金额refund_amount：" + refundAmount);
        String outRequestNo = (String) map.get(PayConstants.OUT_REFUND_NO);//退款单号
        logger.info("支付宝退款-退款单号out_request_no：" + outRequestNo);
        if (StringUtils.isBlank(outTradeNo)) {
            logger.error("======>支付宝订单退款参数错误:商户订单号不能为空");
//			resultMap.put("return_code","FAIL");
//			resultMap.put("return_msg", "支付宝订单退款参数错误：商户订单号不能为空");
//			return resultMap;
            throw new PayException("支付宝订单退款参数错误:商户订单号不能为空");
        }
        if (StringUtils.isBlank(refundAmount)) {
            logger.error("======>支付宝订单退款参数错误:退款金额不能为空");
//			resultMap.put("return_code","FAIL");
//			resultMap.put("return_msg", "支付宝订单退款参数错误：退款金额不能为空");
//			return resultMap;
            throw new PayException("支付宝订单退款参数错误:退款金额不能为空");
        }
        if (StringUtils.isBlank(outRequestNo)) {
            logger.error("======>支付宝订单退款参数错误:退款单号不能为空");
//			resultMap.put("return_code","FAIL");
//			resultMap.put("return_msg", "支付宝订单退款参数错误：退款单号不能为空");
//			return resultMap;
            throw new PayException("支付宝订单退款参数错误:退款单号不能为空");
        }
        //初始化
        AliPayConfig aliPayConfig = new AliPayConfig(PAY_MAY_NUM);
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getUrl(), aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), aliPayConfig.getFormat(), aliPayConfig.getCharset(), aliPayConfig.getAppPublicKey(), aliPayConfig.getSignType());
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.refund
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(outTradeNo);
        model.setRefundAmount(refundAmount);
        model.setOutRequestNo(outRequestNo);
        request.setBizModel(model);
        try {
            //请求
            String result = alipayClient.execute(request).getBody();
            Map<String, String> aliMap = (Map) JSON.parse(result);
            Object alipayTradeRefundResponse = aliMap.get("alipay_trade_refund_response");
            Map<String, String> alipayTradeRefundResponseMap = (Map) JSON.parse(alipayTradeRefundResponse.toString());
            String code = alipayTradeRefundResponseMap.get("code");
            if (!"10000".equals(code)) {
                logger.error("======>支付宝订单退款失败");
//				resultMap.put("returnCode","FAIL");
//				resultMap.put("returnMsg", "支付宝订单退款失败");
//				return resultMap;
                throw new PayException("支付宝订单退款失败");
            }
            resultMap.put("returnCode", "SUCCESS");
            resultMap.put("returnMsg", "支付宝订单退款成功");
            logger.info("======>支付宝订单退款成功");
            return resultMap;
        } catch (AlipayApiException e) {
            logger.error("======>支付宝订单退款出现问题", e);
//			resultMap.put("returnCode","FAIL");
//			resultMap.put("returnMsg", "支付宝订单退款失败");
//			return resultMap;
            throw new PayException("支付宝订单退款失败");
        }

    }

    /**
     * <p>支付宝订单关闭 </p>
     *
     * @param map 业务参数
     *            orderId			订单编号
     * @return
     */
    public Object close(Map<String, Object> map) throws PayException {
        return null;
    }

    /**
     * <p>支付宝订单下载账单 </p>
     *
     * @param map 业务参数
     * @return
     */
    @Override
    public Object downloadBill(Map<String, Object> map) throws PayException {
        return null;
    }

    /**
     * <p>支付宝订单支付回调</p>
     *
     * @return
     */
    @Override
    public Object payCallBack() throws PayException {

        logger.info("======>支付宝订单支付回调");
        //返回值
        String failure = "failure";//失败
        String success = "success";//成功
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = R.getRequest().getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            AliPayConfig aliPayConfig = new AliPayConfig(PAY_MAY_NUM);
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfig.getAppPublicKey(), aliPayConfig.getCharset(), aliPayConfig.getSignType());
            if (!signVerified) {
                //验证失败
                logger.error("======>支付宝订单支付回调验证失败");
                return failure;
            }
            SettlementPayWayDao settlementPayWayDao = SpringContextHolder.getBean(SettlementPayWayDao.class);
            TradeOrderDao tradeOrderDao = SpringContextHolder.getBean(TradeOrderDao.class);
            AccountService accountService = SpringContextHolder.getBean(AccountService.class);
            String appId = R.get("app_id");//商户应用id
            logger.info("支付宝支付回调-商户应用appId：" + appId);
            String param = R.get("passback_params");//shop商城订单参数
            logger.info("支付宝支付回调-shop商城订单参数param：" + param);
            String tradeNo = R.get("trade_no");//支付宝交易号
            logger.info("支付宝支付回调-支付宝交易号tradeNo：" + tradeNo);
            Date gmtPayment = R.getDate("gmt_payment", "yyyy-MM-dd HH:mm:ss");//付款时间
            logger.info("支付宝支付回调-付款时间gmtPayment：" + gmtPayment);
            if (!aliPayConfig.getAppId().equals(appId)) {
                logger.error("======>支付宝订单支付回调失败");
                return failure;
            }
            //多个订单id
            String orderIds = param.split(";")[0];
            String[] orderIdArray = orderIds.split(",");
            List<String> orderIdList = new ArrayList<>();
            for (int i = 0; i < orderIdArray.length; i++) {
                orderIdList.add(orderIdArray[i]);
            }
            //订单金额
            String amounts = param.split(";")[1];
            String[] amountArray = amounts.split(",");
            //支付方式id
            String payWayId = param.split(";")[2];
            SettlementPayWay settlementPayWay = settlementPayWayDao.selectById(Long.parseLong(payWayId));
            //修改订单状态
            for (int i = 0; i < orderIdArray.length; i++) {
                TradeOrder tr = tradeOrderDao.selectById(Long.parseLong(orderIdArray[i]));
                if (tr == null) {
                    continue;
                }
                logger.info("支付宝支付通知-订单状态：" + tr.getOrderStatus());
                // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
                if (!"10".equals(tr.getOrderStatus())) {
                    continue;
                }
                //修改订单
                TradeOrder tradeOrder = new TradeOrder();
                tradeOrder.setOrderId(Long.parseLong(orderIdArray[i]));//订单id
                tradeOrder.setOrderStatus("20");//订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、 50已评价、60已取消、70退货退款中
                tradeOrder.setThirdPayOrderNumber(tradeNo);//支付宝交易凭证号
                tradeOrder.setPayOrderTime(gmtPayment);//付款时间
                tradeOrder.setOnlinePayMoney(new BigDecimal(amountArray[i]));//在线付款金额
                tradeOrder.setPaymentMethodId(settlementPayWay.getPayWayId());//支付方式id
                tradeOrder.setPaymentMethodName(settlementPayWay.getName());//支付方式名称
                tradeOrderDao.updateByIdSelective(tradeOrder);

                //订单详情
                List<TradeOrderItem> tradeOrderItemList = tr.getTradeOrderItemList();

                //修改账户信息
                try {
                    if (tradeOrderItemList.size() == 1) {
                        accountService.paymentProduct(tradeOrderItemList.get(0).getOrderItemId(), new BigDecimal(amountArray[i]), new BigDecimal("0"), new BigDecimal(amountArray[i]), Long.parseLong(payWayId), settlementPayWay.getName(), tradeNo);
                    } else {
                        for (int j = 0; j < tradeOrderItemList.size(); j++) {
                            BigDecimal tradeOrderItemMoney = (tradeOrderItemList.get(j).getPrice()).multiply(new BigDecimal(tradeOrderItemList.get(j).getQuantity()));
                            accountService.paymentProduct(tradeOrderItemList.get(j).getOrderItemId(), tradeOrderItemMoney, new BigDecimal("0"), new BigDecimal(amountArray[i]), Long.parseLong(payWayId), settlementPayWay.getName(), tradeNo);
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            //插入回调日志
            PayCallbackLogDao payCallbackLogDao = SpringContextHolder.getBean(PayCallbackLogDao.class);
            PayCallbackLog payCallbackLog = new PayCallbackLog();
            payCallbackLog.setPayWayId(settlementPayWay.getPayWayId());
            payCallbackLog.setPayWayName(settlementPayWay.getName());
            ObjectMapper json = new ObjectMapper();
            String paramsJson = null;
            try {
                paramsJson = json.writeValueAsString(params);
            } catch (Exception e) {
                logger.error("======>支付宝订单支付回调出现问题", e);
                return failure;
            }
            payCallbackLog.setCallbackInfo(paramsJson);
            payCallbackLogDao.insertSelective(payCallbackLog);
            logger.error("======>支付宝订单支付回调成功");
            return success;
        } catch (AlipayApiException e) {
            logger.error("======>支付宝订单支付回调出现问题", e);
            return failure;
        }

    }

    /**
     * <p>支付宝订单退款回调(支付宝同步，所以方法不需要)</p>
     *
     * @return
     */
    public Object refundCallBack() throws PayException {
        return null;
    }

    public static void main(String[] args) {
        Base64 base64 = new Base64();
        String text = "字串文字";
        //编码
        String encodedText = base64.encodeToString(text.getBytes(StandardCharsets.UTF_8));
        System.out.println(encodedText);
        //解码
        System.out.println(new String(base64.decode(encodedText), StandardCharsets.UTF_8));
    }
}
