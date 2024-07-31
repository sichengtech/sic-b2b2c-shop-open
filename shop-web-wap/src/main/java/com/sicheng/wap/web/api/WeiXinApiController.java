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

package com.sicheng.wap.web.api;

import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.api.PaymentApi.TradeType;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.sicheng.common.config.Global;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.common.utils4m.AccessTokenUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import com.sicheng.common.utils4m.WeiXinUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
  * <p>标题: WeiXinController</p>
  * <p>描述: 微信接口Controller</p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zjl
  * @version 2017年12月18日 上午11:37:06
  *
  */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class WeiXinApiController extends BaseController {

    //商户相关资料
    String appid = Global.getConfig("wx_appid");
    String partner = Global.getConfig("wx_partner");
    String paternerKey = Global.getConfig("wx_paternerKey");
    //付款成功后回调的地址
    String notify_url = Global.getConfig("wx_notifyurl");

    /**
     * 微信支付
     * @param request HttpServletRequest
     * @return Map<String, Object>
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/weixin/pay")
    public Map<String, Object> weixinPay(HttpServletRequest request) {
        //支付金额
        String totalFee = R.get("total_fee");
        //支付订单号（多个订单编号用逗号分割）
        String orderId = R.get("orderId");
        logger.info("请求微信支付，支付金额：{}", totalFee);
        logger.info("请求微信支付，支付单号：{}", orderId);
        if (StrKit.isBlank(totalFee)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, "支付金额不能为空", null, null);
        }
        if (StrKit.isBlank(orderId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, "支付订单编号不能为空", null, null);
        }
        String openId = AppTokenUtils.findUser().getUserMember().getOpenId();
        String ip = IpKit.getRealIp(request);
        logger.info("请求微信支付，获取客户端的真实ip：{}", ip);
        if (!StrKit.isBlank(ip) && ip.split(",").length > 0) {
            ip = ip.split(",")[0];
        }
        if (StrKit.isBlank(ip)) {
            ip = "127.0.0.1";
        }
        //支付金额
        int price = (int) (Float.parseFloat(totalFee) * 100);
        //商户系统内部订单号
        String outTradeNo = IdGen.uuid();//Long.toString(System.currentTimeMillis());
        //统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
        Map<String, String> params = new HashMap<>();
        //公众号标识
        params.put("appid", appid);
        logger.info("请求微信支付appid:{}", appid);
        //商户号
        params.put("mch_id", partner);
        logger.info("请求微信支付partner:{}", partner);
        //商品名称
        params.put("body", R.get("body"));
        //total_fee :订单总金额
        params.put("total_fee", Integer.toString(price));
        //attach 商家数据包
        params.put("attach", orderId);
        //请求ip
        params.put("spbill_create_ip", ip);
        logger.info("请求微信支付ip:{}", ip);
        //trade_type 交易类型
        params.put("trade_type", TradeType.JSAPI.name());
        logger.info("请求微信支付trade_type:{}", TradeType.JSAPI.name());
        //nonce_str 随机串JSAPI、NATIVE、APP
        params.put("nonce_str", Long.toString(System.currentTimeMillis() / 1000));
        //notify_url 付款成功后的回调 地址
        params.put("notify_url", notify_url);
        logger.info("请求微信支付notify_url:{}", notify_url);
        //用户openId
        params.put("openid", openId);
        params.put("out_trade_no", outTradeNo);
        //sign签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        logger.info("请求微信支付sign:{}", sign);
        String xmlResult = PaymentApi.pushOrder(params);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        String returnCode = result.get("return_code");
        logger.info("请求支付returnCode:{}", returnCode);
        String returnMsg = result.get("return_msg");
        logger.info("请求支付returnMsg:{}", returnMsg);
        //code 标记成功失败，默认0：成功，1：失败、用于alert，2：失败、用于confirm
        if (StrKit.isBlank(returnCode) || !"SUCCESS".equals(returnCode)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, returnMsg, null, null);
        }
        String resultCode = result.get("result_code");
        if (StrKit.isBlank(resultCode) || !"SUCCESS".equals(resultCode)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, returnMsg, null, null);
        }
        logger.info("notify_url:{}", params.get("notify_url"));
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String prepayId = result.get("prepay_id");
        Map<String, String> packageParams = new HashMap<>();
        packageParams.put("appId", appid);
        packageParams.put("timeStamp", Long.toString(System.currentTimeMillis() / 1000));
        packageParams.put("nonceStr", Long.toString(System.currentTimeMillis()));
        packageParams.put("package", "prepay_id=" + prepayId);
        packageParams.put("signType", "MD5");
        String packageSign = PaymentKit.createSign(packageParams, paternerKey);
        packageParams.put("paySign", packageSign);
        String jsonStr = JsonUtils.toJson(packageParams);
        logger.info("请求微信支付返回的json：{}", jsonStr);
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, "请求支付成功", jsonStr, null);
    }

    /**
     * 获取微信信息(access_token,signature,timestamp,noncestr,appId)
     * @return Map<String, Object>
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/weixin/info")
    public Map<String, Object> weixinInfo() {
        try {
            String url = R.get("url");
            if (StringUtils.isBlank(url)) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, "缺少参数", null, null);
            }
            url = url.replace("&amp;", "&");
            //1、获取AccessToken jsapi_ticket
            String accessToken = "";
            String jsapiTicket = "";
            Map<String, String> tokenMap = AccessTokenUtils.getAccessToken();
            if (tokenMap != null) {
                accessToken = tokenMap.get("access_token");
                jsapiTicket = tokenMap.get("jsapi_ticket");
            }
            //3、时间戳和随机字符串
            String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
            //4、获取url
            //String url = R.getRequest().getScheme()+"://"+ R.getRequest().getServerName()+R.getRequest().getRequestURI();
            //if(StringUtils.isNoneBlank(R.getRequest().getQueryString())){
            //url = R.getRequest().getScheme()+"://"+ R.getRequest().getServerName()+R.getRequest().getRequestURI()+"?"+R.getRequest().getQueryString();
            //}
            url = url.split("#")[0];
            logger.info("当前页的地址：{}", url);
            //5、将参数排序并拼接字符串
            String str = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            logger.info("参数拼接的字符串：{}", str);
            //6、将字符串进行sha1加密
            String signature = WeiXinUtils.SHA1(str);
            logger.info("签名：{}", signature);
            Map<String, Object> data = new HashMap<>();
            data.put("timestamp", timestamp);
            data.put("nonceStr", noncestr);
            data.put("signature", signature);
            data.put("accessToken", accessToken);
            data.put("appId", Global.getConfig("wx_appid"));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, "获取成功", data, null);
        } catch (Exception e) {
            logger.error("获取微信信息的错误", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, "服务发生错误", null, null);
        }
    }
}
