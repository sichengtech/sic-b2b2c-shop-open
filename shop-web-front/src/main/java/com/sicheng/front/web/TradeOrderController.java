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
package com.sicheng.front.web;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.admin.trade.entity.TradeDeliver;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.member.service.MemberAddressService;
import com.sicheng.seller.product.service.ProductSkuService;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.settlement.service.SettlementPayWayService;
import com.sicheng.seller.site.service.SiteInfoService;
import com.sicheng.seller.trade.service.TradeCartService;
import com.sicheng.seller.trade.service.TradeDeliverService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>标题: TradeOrderController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 * @author fxx
 * @version 2018年3月27日 下午5:42:38
 */
@Controller
@RequestMapping(value = "${frontPath}/trade")
public class TradeOrderController extends BaseController {
    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private TradeCartService tradeCartService;
    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private TradeDeliverService tradeDeliverService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private SiteInfoService siteInfoService;
    @Autowired
    private SettlementPayWayService payWayService;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("ctx", R.getCtx());
        model.addAttribute("ctxsso", R.getCtx() + Global.getSsoPath());
        model.addAttribute("ctxs", R.getCtx() + Global.getSellerPath());
        model.addAttribute("ctxf", R.getCtx() + Global.getFrontPath());
        model.addAttribute("ctxm", R.getCtx() + Global.getMemberPath());
        model.addAttribute("ctxStatic", "/static/static");
        model.addAttribute("ctxfs", "/upload" + Global.getConfig("filestorage.dir"));
        model.addAttribute("ctxu", "/upload");
    }

    /**
     * 进入确认订单页
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/order/confirmOrder")
    public String confirmOrder(Model model) {
        String priceSum = "";
        String ids = "";
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        //收货地址
        List<MemberAddress> addressList = memberAddressService.selectByWhere(new Wrapper().and("u_id=", userMember.getUId()).orderBy("is_default desc ,update_date desc"));
        //map的键：店铺id
        Set<Object> keys = new HashSet<>();
        //是否支持发票
        String invoice = "0";
        //stat=1:从购物车页直接下单
        if ("1".equals(R.get("stat"))) {
            //ids：购物车的多个id
            ids = R.get("ids");
            priceSum = R.get("priceSum");
            if (StringUtils.isBlank(ids)) {
                addMessage(model,FYUtils.fyParams("请选择要购买的商品"));
                return list(new TradeCart(), model);
            }
            String[] cartIds = ids.split(",");
            if (cartIds.length == 0) {
                addMessage(model,FYUtils.fyParams("请选择要购买的商品"));
                return list(new TradeCart(), model);
            }
            List<String> cartIdList = Arrays.asList(cartIds);
            List<TradeCart> cartList = tradeCartService.selectByIdIn(cartIdList);
            TradeCart.fillStore(cartList);
            TradeCart.fillProductSpu(cartList);
            TradeCart.fillProductSku(cartList);
            Map<String, List<TradeCart>> cartMap = new LinkedHashMap<>();
            Boolean flag = true;
            String msg = "";
            for (TradeCart cart : cartList) {
                if (cart.getProductSpu() == null) {
                    continue;
                }
                if (userMember.getUId() == cart.getProductSpu().getUId()) {
                    flag = false;
                    msg = FYUtils.fyParams("不能购买自己店铺的商品");
                    break;
                }
                if (cart.getCount() == null || cart.getCount() == 0) {
                    flag = false;
                    msg = FYUtils.fyParams("购买数量不能为空");
                    break;
                }
                if (cart.getCount() > cart.getProductSku().getStock()) {
                    flag = false;
                    msg = FYUtils.fyParams("库存不足");
                    break;
                }
                if ("1".equals(cart.getProductSpu().getInvoice())) {
                    invoice = "1";
                }
                Integer buyCount=cart.getCount();
                //查找同一个pid的商品的购买数量
                for (TradeCart cart2 : cartList) {
                    if(cart2.getPId()==cart.getPId()){
                        continue;
                    }
                    buyCount+=cart2.getCount();
                }
                if (buyCount < Long.parseLong(productSpuService.getPurchasingAmount(cart.getProductSpu()))) {
                    flag = false;
                    msg = FYUtils.fyParams("购买数量必须大于起购量，起购量是") + productSpuService.getPurchasingAmount(cart.getProductSpu());
                    break;
                }
                //计算运费，先设置为0
                cart.getProductSpu().setExpressPrice(new BigDecimal("0"));
                //type:1零售商品：一个价格，2批发商品：根据数量算价格
                BigDecimal price = new BigDecimal("0");
                String type = cart.getProductSpu().getType();
                if ("1".equals(type)) {
                    price = cart.getProductSku().getPrice();
                }
                if ("2".equals(type)) {
                    price = productSpuService.calculatePrice(cart.getProductSpu().getPId(), buyCount);
                    cart.getProductSku().setPrice(price);
                }
                cart.setPriceSum(price.multiply(new BigDecimal(cart.getCount())));
                String key =cart.getStore().getStoreId().toString();
                keys.add(key);
                if (!cartMap.isEmpty()) {
                    if (cartMap.get(key) != null) {
                        cartMap.get(key).add(cart);
                    } else {
                        List<TradeCart> list = new ArrayList<>();
                        list.add(cart);
                        cartMap.put(key, list);
                    }
                } else {
                    List<TradeCart> list = new ArrayList<>();
                    list.add(cart);
                    cartMap.put(key, list);
                }
            }
            if (!flag) {
                addMessage(model, msg);
                return list(new TradeCart(), model);
            }
            model.addAttribute("cartMap", cartMap);
        } else {
            //从单详情页下单
            String pid = R.get("pId");
            //skuMsg:skuid-数量
            String skuMsg = R.get("skuMsg");
            //type:1零售，2批发
            String type = R.get("type");
            if (StringUtils.isBlank(pid) || StringUtils.isBlank(skuMsg) || StringUtils.isBlank(type)) {
                addMessage(model,FYUtils.fyParams("请选择商品或商品规格"));
                return "/productDetail";
            }
            Long pId = Long.parseLong(pid);
            ProductSpu productSpu = productSpuService.selectById(pId);
            if (productSpu == null) {
                addMessage(model,FYUtils.fyParams("请选择商品或商品规格"));
                return "/productDetail";
            }
            //判断是否是自己店铺的商品
            if (productSpu.getUId() == userMember.getUId()) {
                addMessage(model, FYUtils.fyParams("不能购买自己店铺的商品"));
                return "/productDetail";
            }
            //判断购买总量是否超过起购起购
            String totalNum = R.get("totalNum");
            String purchasingAmount = R.get("purchasingAmount");
            if (StringUtils.isBlank(totalNum) || !StringUtils.isNumeric(totalNum) || StringUtils.isBlank(purchasingAmount) || !StringUtils.isNumeric(purchasingAmount)) {
                addMessage(model, FYUtils.fyParams("购买数量必须大于起购量"));
                model.addAttribute("pid", pid);
                return "/productDetail";
            }
            if (Integer.parseInt(totalNum) < Integer.parseInt(purchasingAmount)) {
                addMessage(model,  FYUtils.fyParams("购买数量必须大于起购量,起购量是") + purchasingAmount);
                model.addAttribute("pid", pid);
                return "/productDetail";
            }
            //判断库存
            String[] skus = skuMsg.split(",");
            if (skus.length == 0) {
                addMessage(model, FYUtils.fyParams("请选择商品或商品规格"));
                return "/productDetail";
            }
            Boolean flag = true;
            String msg = "";
            for (int i = 0; i < skus.length; i++) {
                String[] skuss = skus[i].split("-");
                if (skuss.length < 2) {
                    flag = false;
                    msg = FYUtils.fyParams("缺少参数");
                    break;
                }
                String skuId = skuss[0];
                String count = skuss[1];
                if (StringUtils.isBlank(skuId) || !StringUtils.isNumeric(skuId) || StringUtils.isBlank(count) || !StringUtils.isNumeric(count)) {
                    flag = false;
                    msg = FYUtils.fyParams("缺少参数");
                    break;
                }
                ProductSku productSku = productSkuService.selectById(Long.parseLong(skuId));
                if (productSku == null || Long.parseLong(count) > productSku.getStock()) {
                    flag = false;
                    msg = FYUtils.fyParams("库存不足");
                    break;
                }
            }
            if (!flag) {
                addMessage(model, msg);
                model.addAttribute("pid", pid);
                return "/productDetail";
            }
            //根据pId,一个或多个skuId、数量，建立一个购物车list
            List<TradeCart> tradeCartList = tradeCartService.selectBySkus(userMember, pId, skuMsg, type);
            for (TradeCart cart : tradeCartList) {
                cart.setIsValid("0");//是否有效
            }
            Map<String, List<TradeCart>> cartMap = new LinkedHashMap<>();
            String key = productSpu.getStoreId().toString();
            keys.add(key);
            cartMap.put(key, tradeCartList);
            model.addAttribute("cartMap", cartMap);
            invoice = productSpu.getInvoice();
            tradeCartService.insertBatch(tradeCartList);
        }
        //发票信息
        List<TradeDeliver> deliverList = tradeDeliverService.selectByWhere(new Wrapper().and("u_id=", userMember.getUId()));
        model.addAttribute("deliverList", deliverList);
        model.addAttribute("addressList", addressList);
        model.addAttribute("priceSum", priceSum);
        model.addAttribute("ids", ids);
        model.addAttribute("keys", keys);
        model.addAttribute("invoice", invoice);
        model.addAttribute("sysPaySwitch", Global.getConfig("sys.pay.switch"));
        return "/tradeConfirmOrder";
    }

    /**
     * 提交订单
     * @param model
     * @param tradeOrder
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "/order/addOrder")
    public Map<String, Object> addOrder(Model model, TradeOrder tradeOrder, RedirectAttributes redirectAttributes) {
        String paySwitch=Global.getConfig("sys.pay.switch");//在线支付的开启与关闭：0关闭、1开启
        Map<String, Object> orderMap = new HashMap<>();
        //map的键：店铺id
        String keyss = R.get("keys");
        if (StringUtils.isBlank(keyss)) {
            orderMap.put("success", false);
            orderMap.put("message",FYUtils.fyParams("请选择要购买的商品"));
            return orderMap;
        }
        keyss = keyss.substring(1, keyss.length() - 1);
        String[] keys = keyss.split(",");
        //购物车ids
        String ids = R.get("ids");
        String[] cartIds = ids.split(",");
        if (StringUtils.isBlank(ids) || keys.length == 0 || cartIds.length == 0) {
            orderMap.put("success", false);
            orderMap.put("message",FYUtils.fyParams("请选择要购买的商品"));
            return orderMap;
        }
        //收货地址
        String addressId = R.get("addressId");
        MemberAddress address = memberAddressService.selectById(Long.parseLong(addressId));
        if (StringUtils.isBlank(addressId) || address == null) {
            orderMap.put("success", false);
            orderMap.put("message", FYUtils.fyParams("收货地址不能为空"));
            return orderMap;
        }
        //发票
        String deliverId = R.get("deliverId");
        List<Object> cartIdList = new ArrayList<>();
        for (int i = 0; i < cartIds.length; i++) {
            if (StringUtils.isNotBlank(cartIds[i])) {
                cartIdList.add(cartIds[i]);
            }
        }
        List<TradeCart> cartList = tradeCartService.selectByIdIn(cartIdList);
        TradeCart.fillStore(cartList);
        TradeCart.fillProductSpu(cartList);
        TradeCart.fillProductSku(cartList);
        Map<String, List<TradeCart>> cartMap = new LinkedHashMap<>();
        Boolean flag = true;
        for (TradeCart cart : cartList) {
            if (cart.getProductSpu() == null || cart.getProductSku() == null) {
                flag = false;
                break;
            }
            Integer buyCount=cart.getCount();
            //查找同一个pid的商品的购买数量
            for (TradeCart cart2 : cartList) {
                if(cart2.getPId()==cart.getPId()){
                    continue;
                }
                buyCount+=cart2.getCount();
            }
            //重新计算价格
            //type:1零售商品：一个价格，2批发商品：根据数量算价格
            BigDecimal price = new BigDecimal("0");
            String type = cart.getProductSpu().getType();
            if ("1".equals(type)) {
                price = cart.getProductSku().getPrice();
            }
            if ("2".equals(type)) {
                price = productSpuService.calculatePrice(cart.getProductSpu().getPId(), buyCount);
            }
            cart.setPrice(price);
            cart.setPriceSum(price.multiply(new BigDecimal(cart.getCount())));

            String key = cart.getStore().getStoreId().toString();
            if (!cartMap.isEmpty()) {
                if (cartMap.get(key) != null) {
                    cartMap.get(key).add(cart);
                } else {
                    List<TradeCart> list = new ArrayList<>();
                    list.add(cart);
                    cartMap.put(key, list);
                }
            } else {
                List<TradeCart> list = new ArrayList<>();
                list.add(cart);
                cartMap.put(key, list);
            }
        }
        if (!flag) {
            orderMap.put("success", false);
            orderMap.put("message", FYUtils.fyParams("很遗憾，您购买的商品下架了，请选择其他商品"));
            return orderMap;
        }
        if (cartMap == null || cartMap.size() == 0) {
            orderMap.put("success", false);
            orderMap.put("message", FYUtils.fyParams("很遗憾，您购买的商品下架了，请选择其他商品"));
            return orderMap;
        }
        List<TradeOrder> orderList = tradeOrderService.addOrder(keys, address, deliverId, cartMap,/* cartList,*/ cartIdList,paySwitch);
        String orderIds = "";
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i) == null || orderList.get(i).getOrderId() == null) {
                continue;
            }
            String spiltStr = i == orderList.size() - 1 ? "" : ",";
            orderIds += orderList.get(i).getOrderId() + spiltStr;
        }
        orderMap.put("orderIds", orderIds);
        orderMap.put("success", true);
        redirectAttributes.addFlashAttribute("orderList", orderList);
        redirectAttributes.addFlashAttribute("orderPrice", R.get("orderPrice"));
        redirectAttributes.addFlashAttribute("userMember", SsoUtils.getUserMain().getUserMember());
        redirectAttributes.addFlashAttribute("address", address);
        //根据在线支付的开启与关闭的状态下单成功后跳转不同的页面（0关闭、1开启）
        if("0".equals(paySwitch)){
            orderMap.put("returnUrl", "/trade/order/placeOrder.htm");
        }else{
            orderMap.put("returnUrl", "/trade/order/tradeCheckorder.htm");
        }
        return orderMap;
    }

    /**
     * 进入支付页面
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/order/tradeCheckorder")
    public String orderPayView(Model model) {
        String orderIds = R.get("orderIds");
        if (StringUtils.isBlank(orderIds)) {
            addMessage(model, FYUtils.fyParams("订单不存在"));
            return "/tradeCheckorder";
        }
        String[] orderIdss = orderIds.split(",");
        List<String> orderIdList = Arrays.asList(orderIdss);
        List<TradeOrder> orderList = tradeOrderService.selectByIdIn(orderIdList);
        if (orderIdList.size() == 0) {
            addMessage(model, FYUtils.fyParams("订单不存在"));
            return "/tradeCheckorder";
        }
        BigDecimal orderPrice = new BigDecimal(0);
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i) == null) {
                continue;
            }
            //调整了金额就用调整后的金额，没有调整金额就用总金额
            if (orderList.get(i).getOffsetAmount() != null) {
                orderPrice = orderPrice.add(orderList.get(i).getOffsetAmount());
            } else {
                orderPrice = orderPrice.add(orderList.get(i).getAmountPaid());
            }
        }
        //获取后台设置的支付方式
        SettlementPayWay payWay = new SettlementPayWay();
        payWay.setStatus("1");//支付方式状态，0关闭、1开启
        payWay.setUseTerminal("0");//使用终端,0pc、1移动端
        List<SettlementPayWay> payWayList = payWayService.selectByWhere(new Wrapper(payWay));
        //String orderPrice=R.get("orderPrice");
        Long addressId = orderList.get(0).getAddressId();
        MemberAddress address = memberAddressService.selectById(addressId);
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderPrice", orderPrice);
        model.addAttribute("userMember", SsoUtils.getUserMain().getUserMember());
        model.addAttribute("address", address);
        model.addAttribute("payWayList", payWayList);
        model.addAttribute("payWayId", orderList.get(0).getPaymentMethodId());
        model.addAttribute("outTradeNo", orderList.get(0).getOutTradeNo());
        model.addAttribute("orderIds", orderIds);
        if (orderList.get(0).getSettlementPayWay() != null) {
            model.addAttribute("payWayNum", orderList.get(0).getSettlementPayWay().getPayWayNum());
        }
        SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
        String body = FYUtils.fyParams("B2B商城商品");
        if (siteInfo != null) {
            body = siteInfo.getName() + FYUtils.fyParams("商品");
        }
		/*if(orderList.get(0).getTradeOrderItemList().size()!=0){
			body=orderList.get(0).getTradeOrderItemList().get(0).getName();
		}*/
        model.addAttribute("body", body);
        return "/tradeCheckorder";
    }

    /**
     * 进入支付成功页
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/pay/payOk")
    public String payOk() {
        return "/payOk";
    }

    /**
     * 进入下单成功页
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/order/placeOrder")
    public String placeOrder() {
        return "/placeOrderOk";
    }

    /**
     * 进入购物车列表页
     * @param tradeCart 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/cart/list")
    public String list(TradeCart tradeCart, Model model) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeCart.setUId(userMember.getUId());
        tradeCart.setIsValid("1");//是否有效，0否、1是
        List<TradeCart> tradeCartList = tradeCartService.selectByWhere(new Wrapper(tradeCart));
        TradeCart.fillStore(tradeCartList);
        TradeCart.fillProductSpu(tradeCartList);
        TradeCart.fillProductSku(tradeCartList);
        Map<String, List<TradeCart>> cartMap = new LinkedHashMap<>();
        for (TradeCart cart : tradeCartList) {
            if (cart == null || cart.getStore() == null) {
                continue;
            }
            ProductSpu productSpu = cart.getProductSpu();
            if (productSpu == null || !"50".equals(productSpu.getStatus()) || StringUtils.isBlank(productSpu.getType())) {
                continue;
            }
            ProductSku productSku = cart.getProductSku();
            if (productSku == null) {
                continue;
            }
            cart.getProductSpu().setPurchasingAmount(productSpuService.getPurchasingAmount(cart.getProductSpu()));
            if (!"1".equals(productSpu.getType())) {
                BigDecimal price = productSpuService.calculatePrice(productSpu.getPId(), cart.getCount());
                cart.setPrice(price);
            } else {
                cart.setPrice(productSku.getPrice());
            }
            String key = cart.getStore().getName() + "-" + cart.getStore().getStoreId();
            if (!cartMap.isEmpty()) {
                if (cartMap.get(key) != null) {
                    cartMap.get(key).add(cart);
                } else {
                    List<TradeCart> list = new ArrayList<>();
                    list.add(cart);
                    cartMap.put(key, list);
                }
            } else {
                List<TradeCart> list = new ArrayList<>();
                list.add(cart);
                cartMap.put(key, list);
            }
        }
        model.addAttribute("cartMap", cartMap);
        model.addAttribute("userMember", SsoUtils.getUserMain().getUserMember());
        return "/tradeCartList";
    }

    /**
     * 加入购物车
     * @param tradeCart 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "/cart/save")
    public String save1(TradeCart tradeCart, RedirectAttributes redirectAttributes) {
        String pid = R.get("pId");
        //skuMsg:skuid-数量
        String skuMsg = R.get("skuMsg");
        //type:1零售，2批发
        String type = R.get("type");
        String stat = "0";
        if (StringUtils.isBlank(pid) || !StringUtils.isNumeric(pid) || StringUtils.isBlank(skuMsg)) {
            return stat;
        }
        Long pId = Long.parseLong(pid);
        //用户
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        String[] skuMsgs = skuMsg.split(",");
        String skuMsgNew = "";
        List<TradeCart> tradeCartOldList = new ArrayList<>();
        for (int i = 0; i < skuMsgs.length; i++) {
            String[] skus = skuMsgs[i].split("-");
            if (skus.length < 2) {
                continue;
            }
            String skuIdStr = skus[0].trim();
            String countStr = skus[1].trim();
            if (!StringUtils.isNumeric(skuIdStr) || !StringUtils.isNumeric(countStr)) {
                continue;
            }
            Long skuId = Long.parseLong(skuIdStr);
            //如果当前商品已经加入过购物车，就添加购物车中的数量
            TradeCart cart = new TradeCart();
            cart.setPId(pId);
            cart.setSkuId(skuId);
            cart.setUId(userMember.getUId());
            cart.setIsValid("1");
            TradeCart tradeCartOld = tradeCartService.selectOne(new Wrapper(cart));
            if (tradeCartOld == null) {
                skuMsgNew += skuMsgs[i] + ",";
                continue;
            }
            Integer count = tradeCartOld.getCount() == null ? 0 : tradeCartOld.getCount();
            TradeCart tradeCartOld2 = new TradeCart();
            Integer count2 = count + Integer.parseInt(countStr);
            tradeCartOld2.setCartId(tradeCartOld.getCartId());
            tradeCartOld2.setCount(count2);
            ProductSpu productSpu = tradeCartOld.getProductSpu();
            ProductSku productSku = tradeCartOld.getProductSku();
            if (productSpu == null || productSku == null) {
                continue;
            }
            if ("1".equals(productSpu.getType())) {
                tradeCartOld2.setPriceSum(productSku.getPrice().multiply(new BigDecimal(count2)));
            } else {
                BigDecimal price = productSpuService.calculatePrice(pId, count2);
                tradeCartOld2.setPriceSum(price.multiply(new BigDecimal(count2)));
            }
            tradeCartOldList.add(tradeCartOld2);
        }
        //加入过购物车就添加数量
        if (tradeCartOldList.size() > 0) {
            tradeCartService.updateSelectiveBatch(tradeCartOldList);
        }
        if (StringUtils.isNotBlank(skuMsgNew)) {
            //没有加入过购物车，就插入数据
            List<TradeCart> tradeCartList = tradeCartService.selectBySkus(userMember, pId, skuMsgNew, type);
            if (tradeCartList.size() == 0) {
                return stat;
            }
            for (TradeCart cart : tradeCartList) {
                cart.setIsValid("1");//是否有效
            }
            tradeCartService.insertSelectiveBatch(tradeCartList);
        }
        stat = "1";
        return stat;
    }

    /**
     * 进入微信扫码页面
     * @param codeUrl    微信支付返回的code_url
     * @param orderIds    订单编号
     * @param session
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/pay/weixin/view")
    public String payWeixinView(String codeUrl, String orderIds, HttpSession session, Model model) {
        if (orderIds == null) {
            return "/tradeWeixinPay";
        }
        String[] orderIdss = orderIds.split(",");
        List<String> orderIdList = Arrays.asList(orderIdss);
        List<TradeOrder> tradeOrderList = tradeOrderService.selectByIdIn(orderIdList);
        BigDecimal amountPaid = new BigDecimal("0");
        for (int i = 0; i < tradeOrderList.size(); i++) {
            TradeOrder order = tradeOrderList.get(i);
            BigDecimal amount = order.getOffsetAmount() != null ? order.getOffsetAmount() : order.getAmountPaid();
            amountPaid = amountPaid.add(amount);
        }
        model.addAttribute("totalFee", amountPaid);
        model.addAttribute("orderIds", orderIds);
        model.addAttribute("codeUrl", codeUrl);
        return "/tradeWeixinPay";
    }

    /**
     * 支付宝支付页面
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/pay/ali/view")
    public String payAliView() {
        return "/tradeAliPay";
    }

    /**
     * 微信二维码图片
     * @param codeUrl    微信支付返回的code_url
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "/pay/weixin/codeImg")
    public void codeImg(String codeUrl) {
        ServletOutputStream stream = null;
        try {
            stream = R.getResponse().getOutputStream();
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix m = writer.encode(codeUrl, BarcodeFormat.QR_CODE, 300, 300);
            MatrixToImageWriter.writeToStream(m, "png", stream);
        } catch (Exception e) {
            logger.info("生成二维码图片错误：", e);
        } finally {
            if (stream != null) {
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                    logger.info("生成二维码图片错误：", e);
                }
            }
        }
    }

    /**
     * 查询订单状态
     * @param orderId 订单编号
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "/order/orderStatus")
    public Map<String, Object> orderStatus(String orderId) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(orderId)) {
            map.put("status", false);
            map.put("message", "订单编号不能为空");
            return map;
        }
        if (!StringUtils.isNumeric(orderId)) {
            map.put("status", false);
            map.put("message", "订单编号只能是数字");
            return map;
        }
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setOrderId(Long.parseLong(orderId));
        tradeOrder.setUId(SsoUtils.getUserMain().getUId());
        List<TradeOrder> tradeOrderList = tradeOrderService.selectByWhere(new Wrapper(tradeOrder));
        if (!tradeOrderList.isEmpty()) {
            map.put("tradeOrderStatus", tradeOrderList.get(0).getOrderStatus());
        }
        map.put("status", true);
        map.put("message", "修改成功");
        return map;
    }
}
