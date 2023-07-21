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
package com.sicheng.pay;

import java.util.Map;

/**
 * 支付接口
 * <p>
 * 本“接口”有以下特点
 * 提供多种支付方式的实现：支付宝、微信、慧付宝、易宝支付等知名第三方支付。
 * 业务层可调用接口，可实现订单支付、退款、关闭、查询等操作。
 * 由于接口实现的是第三方支付，强制性抛出异常。
 *
 * @author cailong
 * @version 2018年3月26日 上午10:24:07
 */
public interface ShopPay {

    /**
     * 订单支付接口
     */
    Object pay(Map<String, Object> map) throws PayException;

    /**
     * 交易查询接口
     */
    Object query(Map<String, Object> map) throws PayException;

    /**
     * 交易退款查询接口
     */
    Object queryRefund(Map<String, Object> map) throws PayException;

    /**
     * 交易退款接口
     */
    Object refund(Map<String, Object> map) throws PayException;

    /**
     * 交易关闭接口
     */
    //public Object close(Map<String,Object> map) throws PayException;

    /**
     * 下载对账单接口
     */
    Object downloadBill(Map<String, Object> map) throws PayException;

    /**
     * 支付通知
     */
    Object payCallBack() throws PayException;

    /**
     * 退款通知
     */
    //public Object refundCallBack() throws PayException;

}
