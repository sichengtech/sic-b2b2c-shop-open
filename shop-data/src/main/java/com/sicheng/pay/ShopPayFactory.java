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

import com.sicheng.common.utils.StringUtils;
import com.sicheng.pay.ali.AliAppPay;
import com.sicheng.pay.ali.AliH5Pay;
import com.sicheng.pay.ali.AliPay;
import com.sicheng.pay.weixin.WeixinAppPay;
import com.sicheng.pay.weixin.WeixinH5Pay;
import com.sicheng.pay.weixin.WeixinMpPay;
import com.sicheng.pay.weixin.WeixinPay;

/**
 * <p>标题: ShopPayFactory</p>
 * <p>描述: ShopPayFactory工厂类，通过工厂获得ShopPay接口的实现</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2018年4月20日 下午2:41:07
 */
public class ShopPayFactory {

    private static final String ALI_SANDBOX = "ali_sandbox";        //支付宝(沙箱环境)
    private static final String ALI = "ali";                        //支付宝(正式环境)
    private static final String ALI_APP = "ali_app";                //支付宝app(正式环境)
    private static final String ALI_H5 = "ali_h5";                    //支付宝app(正式环境)
    private static final String WEIXIN = "weixin";                    //微信
    private static final String WEIXIN_APP = "weixin_app";            //微信app
    private static final String WEIXIN_H5 = "weixin_h5";            //微信h5
    private static final String WEIXIN_MP = "weixin_mp";            //微信小程序

    /**
     * 取得一个ShopPay接口的实现方案
     *
     * @param payWayNum 支付编号
     * @return ShopPay
     */
    public static ShopPay get(String payWayNum) {
        //判断支付编号是否存在
        if (StringUtils.isBlank(payWayNum)) {
            return null;
        }
        //支付宝(沙箱环境)
        if (ALI_SANDBOX.equals(payWayNum)) {
            ShopPay shopPay = AliPay.getInstance();
            return shopPay;
        }
        //支付宝(正式环境)
        if (ALI.equals(payWayNum)) {
            ShopPay shopPay = AliPay.getInstance();
            return shopPay;
        }
        //支付宝 app支付(正式环境)
        if (ALI_APP.equals(payWayNum)) {
            ShopPay shopPay = AliAppPay.getInstance();
            return shopPay;
        }
        //支付宝 H5支付(正式环境)
        if (ALI_H5.equals(payWayNum)) {
            ShopPay shopPay = AliH5Pay.getInstance();
            return shopPay;
        }
        //微信
        if (WEIXIN.equals(payWayNum)) {
            ShopPay shopPay = WeixinPay.getInstance();
            return shopPay;
        }
        //微信 app支付
        if (WEIXIN_APP.equals(payWayNum)) {
            ShopPay shopPay = WeixinAppPay.getInstance();
            return shopPay;
        }
        //微信 H5支付
        if (WEIXIN_H5.equals(payWayNum)) {
            ShopPay shopPay = WeixinH5Pay.getInstance();
            return shopPay;
        }
        //微信 小程序支付
        if (WEIXIN_MP.equals(payWayNum)) {
            ShopPay shopPay = WeixinMpPay.getInstance();
            return shopPay;
        }
        return null;
    }

}
