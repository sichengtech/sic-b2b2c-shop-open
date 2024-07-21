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
package com.sicheng.pay;

/**
 * <p>标题: PayConstants</p>
 * <p>描述: 支付的常量类</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 * @author zjl
 * @version 2018年4月23日 上午11:04:35
 */

public class PayConstants {

    public static final String APP_ID = "appid";                                    //appid

    public static final String OUT_TRADE_NO = "outTradeNo";                            //商户订单号

    public static final String THIRD_PAY_ORDER_NUMBER = "thirdPayOrderNumber";        //第三方付款平台交易号

    public static final String ORDERIDS = "orderIds";                                //多个订单id

    public static final String BODY = "body";                                        //订单标题

    public static final String AMOUNTPAID = "amountPaid";                            //支付总金额

    public static final String PRICES = "prices";                                    //多个订单价格

    public static final String PAY_WAY_ID = "payWayId";                                //支付方式id

    public static final String REFUND_FEE = "refundFee";                            //退款金额

    public static final String OUT_REFUND_NO = "outRefundNo";                        //退款单号

    public static final String THIRD_PAY_REFUND_NO = "thirdPayRefundNo";			//第三方退款单号

    public static final String OPEN_ID = "openid";                                    //微信小程序支付使用


}
