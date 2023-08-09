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
package com.sicheng.wap.web.weixin;

import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.CookieUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.TradeOrderService;
import com.sicheng.wap.service.UserMainService;
import com.sicheng.wap.service.UserMemberService;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * <p>标题: WeiXinController</p>
  * <p>描述: 微信接口Controller</p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author  zhangjiali
  * @version 2017年12月18日 上午11:37:06
  *
  */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping(value = "${wapPath}")
public class WeiXinSdkController extends BaseController {

    @Autowired
    private UserMainService userMainService;
    @Autowired
    private UserMemberService userMemberService;
    @Autowired
    private TradeOrderService tradeOrderService;

    /**
      * 微信回调，获取微信openId
      * @param request
      * @param redirectAttributes
      * @return
     */
    @RequestMapping(value = "/oauth2/wxlogin")
    public String wxlogin(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //用户授权，获取到的code
        String code = R.get("code");
        //1表示被拦截器拦到微信授权获取openId后，自动登录
        //2表示直接进入注册页，无openId，进入到微信授权获取openId后，进入注册页
        //3表示直接进入登陆页，无openId，进入到微信授权获取openId后，进入登陆页
        //4表示直接进入忘记密码页，无openId，进入到微信授权获取openId后，进入忘记密码页
        String flag = R.get("flag");

        //通过code换取网页授权access_token
        SnsAccessToken accessToken = null;
        try {
            accessToken = SnsAccessTokenApi.getSnsAccessToken(
                    Global.getConfig("wx_appid"), Global.getConfig("wx_secret"), code);
        } catch (Exception e) {
            logger.info("微信网络出现异常", e);
            addMessage(redirectAttributes, "微信网络出现异常！");
            R.getSession().removeAttribute("path");
            return "redirect:" + wapPath + "/webRequest/error.htm";
        }
        //获取用户信息(需scope为 snsapi_userinfo)
        ApiResult result = null;
        try {
            //ApiConfigKit.setThreadLocalApiConfig(apiConfig) 将 ApiConfig对象存入，才可以调用 ApiConfigKit.getApiConfig() 方法
            ApiConfigKit.setThreadLocalApiConfig(new ApiConfig(accessToken.getAccessToken(), Global.getConfig("wx_appid"), Global.getConfig("wx_secret")));
            result = SnsApi.getUserInfo(accessToken.getAccessToken(), accessToken.getOpenid());
        } catch (Exception e) {
            logger.info("微信网络出现异常", e);
            addMessage(redirectAttributes, "微信网络出现异常！");
            R.getSession().removeAttribute("path");
            return "redirect:" + wapPath + "/webRequest/error.htm";
        }
        //判断是否获取openid
        if (StringUtils.isBlank(result.getStr("openid"))) {
            addMessage(redirectAttributes, "微信网络出现异常,无法正常授权登录！");
            R.getSession().removeAttribute("path");
            return "redirect:" + wapPath + "/webRequest/error.htm";
        }

        if ("1".equals(flag)) {
            UserMember userMember = new UserMember();
            userMember.setOpenId(result.getStr("openid"));
            //userMain.setIsLocked("0");//账号是否锁定（0否，1是）
            List<UserMember> list = userMemberService.selectByWhere(new Wrapper(userMember));
            if (list.size() == 0) {
                //库中无openId进入登录页（用户未关联）
                cache.put(CacheConstant.WEIXIN_OPENID + AppTokenUtils.getRequestFlag(), result.getStr("openid"), 1800L);
                return "redirect:" + wapPath + "/user/login/form.htm";
            }
            UserMain userMain = userMainService.selectById(list.get(0).getUId());
            if ("1".equals(userMain.getIsLocked())) {
                ////账号是否锁定（0否，1是）,账号被锁
                cache.put(CacheConstant.WEIXIN_OPENID + AppTokenUtils.getRequestFlag(), result.getStr("openid"), 1800L);
                return "redirect:" + wapPath + "/user/login/form.htm";
            }
            //登录成功后将用户信息存入session
            AppTokenUtils.saveUser(userMain);
            //将登录的用户信息存入Cookie中
            CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.uid", userMain.getUId().toString(), "/", -1);//用户id(临时存储)
            CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.isTypeUserPurchaser", userMain.isTypeUserPurchaser() ? "true" : "false", "/", -1); //是否为采购商(临时存储)
            CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.isloginInvalid", "true", 1800); //是否登录失效(持久性存储30分钟)（有效期要和session时间一样长）
            //更新会员信息
            userMainService.upUserIpDate(userMain.getUId());//登录成功后更新登录ip和登录日期
            //登录成功后，跳转到要进入的页面
            String path = (String) R.getSession().getAttribute("path");
            R.getSession().removeAttribute("path");
            if (path != null && !path.equals("")) {
                String suff = request.getContextPath();
                if (path.startsWith(suff) && suff != null) {
                    return "redirect:" + path.substring(suff.length());
                } else {
                    return "redirect:" + path;
                }
            }
        }
        if ("2".equals(flag)) {
            //微信授权后进入注册页
            cache.put(CacheConstant.WEIXIN_OPENID + AppTokenUtils.getRequestFlag(), result.getStr("openid"), 1800L);
            return "redirect:" + wapPath + "/user/register/form.htm";
        }
        if ("3".equals(flag)) {
            //微信授权后进入登录页
            cache.put(CacheConstant.WEIXIN_OPENID + AppTokenUtils.getRequestFlag(), result.getStr("openid"), 1800L);
            return "redirect:" + wapPath + "/user/login/form.htm";
        }
        if ("4".equals(flag)) {
            //微信授权后进入忘记密码页
            cache.put(CacheConstant.WEIXIN_OPENID + AppTokenUtils.getRequestFlag(), result.getStr("openid"), 1800L);
            return "redirect:" + wapPath + "/user/forgetPwd/form.htm";
        }
        return "redirect:" + wapPath + "/index.htm";
    }

    /**
     * 微信支付回调
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/weixin/payNotify")
    public void payNotify(HttpServletRequest request) {
        logger.info("进入回调方法了");
        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
        String xmlMsg = HttpKit.readData(request);
        Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
        logger.info("微信支付回调params:" + params);
        //总金额
        Float totalFee = Float.valueOf(params.get("total_fee"));
        //订单编号
        String orderId = params.get("attach");
        logger.info("微信支付回调orderId：" + orderId);
        //状态码
        String resultCode = params.get("result_code");
        logger.info("微信支付回调resultCode：" + resultCode);
        try {
            String[] orderIds = orderId.split(",");
            logger.info("orderIds:" + orderIds);
            logger.info("result_code:" + resultCode);
            logger.info("orderLength:" + orderIds.length);
            if (!("SUCCESS").equals(resultCode) || orderIds.length == 0) {
                return;
            }
            //根据订单编号更新订单状态
            for (int i = 0; i < orderIds.length; i++) {
                logger.info("orderIds[i]1:" + orderIds[i]);
                TradeOrder tradeOrder = tradeOrderService.selectById(Long.parseLong(orderIds[i]));
                String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                logger.info(xml);
                if (tradeOrder == null) {
                    continue;
                }
                logger.info("订单订单状态：" + tradeOrder.getOrderStatus());
                // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
                if ("20".equals(tradeOrder.getOrderStatus())) {
                    R.writeHtml(xml, "UTF-8");
                    continue;
                }
                tradeOrder.setOrderStatus("20");
                tradeOrder.setOnlinePayMoney(BigDecimal.valueOf(totalFee / 100));
                tradeOrder.setPaymentMethodId(1L);
                tradeOrder.setPaymentMethodName("微信");
                tradeOrder.setPayOrderTime(new Date());
                TradeOrder tradeOrder2 = new TradeOrder();
                tradeOrder2.setOrderId(Long.parseLong(orderIds[i]));
                tradeOrderService.updateByWhere(tradeOrder, new Wrapper(tradeOrder2));
                R.writeHtml(xml, "UTF-8");
            }
        } catch (Exception e) {
            logger.error("微信支付回调异常：", e);
        }
    }
}
