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
package com.sicheng.wap.web.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.sicheng.admin.logistics.entity.LogisticsCompany;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.common.config.Global;
import com.sicheng.common.express.KdniaoTrackQuery;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.*;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>标题: TradeController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年12月18日 下午2:39:50
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class TradeController extends BaseController {

    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private LogisticsCompanyService logisticsCompanyService;
    @Autowired
    private TradeCartService tradeCartService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private ProductSkuService productSkuService;

    /**
     * 根据订单id查询订单
     *
     * @param orderId 订单id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/order/one")
    public Map<String, Object> tradeOrderOne(String orderId) {
        // 处理参数
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(orderId)) {
            errorList.add(FYUtils.fy("订单id不能为空"));
        }
        if (StringUtils.isNotBlank(orderId) && !StringUtils.isNumeric(orderId)) {
            errorList.add(FYUtils.fy("订单id必须为数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //执行业务，查询订单
        try {
            TradeOrder tradeOrderCondition = new TradeOrder();
            tradeOrderCondition.setOrderId(Long.parseLong(orderId));
            tradeOrderCondition.setUId(AppTokenUtils.findUser().getUId());//属主检查
            TradeOrder tradeOrder = tradeOrderService.selectOne(new Wrapper(tradeOrderCondition));
            message = FYUtils.fy("查询订单成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, tradeOrder, null);
        } catch (Exception e) {
            logger.error("根据订单编号查询订单出现错误：", e);
            message = FYUtils.fy("服务发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, message, null, null);
        }
    }

    /**
     * 查询订单数据
     *
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/order/page")
    public Map<String, Object> tradeOrderPage(String status) {
        if (StringUtils.isNotBlank(status) && !StringUtils.isNumeric(status)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("订单状态只能是数字"), null, null);
        }
        TradeOrder tradeOrder = new TradeOrder();
        if (StringUtils.isNotBlank(status)) {
            tradeOrder.setOrderStatus(status);
        }
        tradeOrder.setUId(AppTokenUtils.findUser().getUId());//属主检查
        try {
            Page<TradeOrder> page = Page.newPage();// 从入参中取得Page分页对象
            page = tradeOrderService.selectByWhere(page, new Wrapper(tradeOrder).orderBy("update_date desc"));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询订单成功"), page.getList(), page);
        } catch (Exception e) {
            logger.error("查询订单数据出现错误：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 查看订单详情
     *
     * @param orderIds 多个订单编号
     * @param count   数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/orderItem/list")
    public Map<String, Object> tradeOrderItemList(String orderIds, String count) {
        if (StringUtils.isBlank(orderIds)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("订单id参数有误"), null, null);
        }
        Object[] orderIdss = orderIds.split(",");
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setUId(AppTokenUtils.findUser().getUId());//属主检查
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(tradeOrder);
        wrapper.and("order_id in", Arrays.asList(orderIdss));
        List<TradeOrder> list = tradeOrderService.selectByWhere(wrapper);
        if (list.isEmpty() || list.size() == 0) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("订单不存在"), null, null);
        }
        List<Object> tradeOrderIds = Lists.newArrayList();
        for (int i = 0; i < list.size(); i++) {
            tradeOrderIds.add(list.get(i).getOrderId());
        }
        try {
            List<TradeOrderItem> tradeOrderItemList = tradeOrderItemService.selectByWhere(new Wrapper().and("order_id in", tradeOrderIds));
            if (tradeOrderItemList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("订单详情不存在"), tradeOrderItemList.get(0), null);
            }
            if (StringUtils.isNotBlank(count) && "1".equals(count)) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("订单详情数据获取成功"), tradeOrderItemList.get(0), null);
            } else {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("订单详情数据获取成功"), tradeOrderItemList, null);
            }
        } catch (Exception e) {
            logger.error("查询订单详情数据出现错误：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 查看物流信息
     *
     * @param orderId 订单编号
     * @return
     */
    @RequestMapping(value = "/{version}/trade/logistics/info")
    @ResponseBody
    public Map<String, Object> trderLogisticsInfo(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("订单id不能为空"), null, null);
        }
        if (!StringUtils.isNumeric(orderId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("订单id必须为数字"), null, null);
        }
        TradeOrder entity = new TradeOrder();
        entity.setUId(AppTokenUtils.findUser().getUId());//属主检查
        entity.setOrderId(Long.parseLong(orderId));
        TradeOrder tradeOrder = tradeOrderService.selectOne(new Wrapper(entity));
        if (tradeOrder == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("订单不存在"), null, null);
        }
        if (tradeOrder.getLogisticsTemplateId() == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("无物流信息"), null, null);
        }
        LogisticsCompany logisticsCompany = logisticsCompanyService.selectById(tradeOrder.getLogisticsTemplateId());
        if (logisticsCompany == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("物流公司不存在"), null, null);
        }
        KdniaoTrackQuery api = new KdniaoTrackQuery();
        //返回来的json格式物流信息
        String result = "";
        Map<String, Object> resultMap = new HashMap<>();
        try {
            result = api.getOrderTracesByJson(logisticsCompany.getCompanyNumber(), tradeOrder.getTrackingNo());    //真实数据
            //result=api.getOrderTracesByJson("YZPY", "9891316655086");	//模拟数据
            //把json转为map
            resultMap = JSON.parseObject(result);
            //获取物流轨迹信息
            JSONArray traces = (JSONArray) resultMap.get("Traces");
            List<Map<String, Object>> mapListJson = (List) traces;
            List<Map<String, Object>> mapListJsonNew = new ArrayList<>();
            for (int i = mapListJson.size() - 1; i >= 0; i--) {
                mapListJsonNew.add(mapListJson.get(i));
            }
            resultMap.put("Traces", mapListJsonNew);
            resultMap.put("companyName",logisticsCompany.getCompanyName());
        } catch (Exception e) {
            logger.error("物流信息出现错误：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("物流信息出现错误"), null, null);
        }
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("物流信息数据获取成功"), resultMap, null);
    }

    /**
     * 验证确认订单页数据接口
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/order/confimOrder/validate")
    public Map<String, Object> tradeConfirmOrderValidate() {
        List<String> errorList = new ArrayList<>();
        //用户id
        UserMember userMember = AppTokenUtils.findUser().getUserMember();
        String message = "";
        String stat = R.get("stat");
        if (StringUtils.isBlank(stat)) {
            message = FYUtils.fy("缺少参数下单入口");
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, false, null);
        }
        //stat=1:从购物车页直接下单
        if ("1".equals(R.get("stat"))) {
            //ids：购物车的多个id
            String ids = R.get("ids");
            String priceSum = R.get("productSumPrice");
            if (StringUtils.isBlank(ids)) {
                message = FYUtils.fy("请选择要结算的商品");
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, false, null);
            }
            if (StringUtils.isBlank(priceSum)) {
                message = FYUtils.fy("缺少参数总价");
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, false, null);
            }
            String[] cartIds = ids.split(",");
            List<String> cartIdList = Arrays.asList(cartIds);
            List<TradeCart> cartList = tradeCartService.selectByIdIn(cartIdList);
            TradeCart.fillStore(cartList);
            TradeCart.fillProductSpu(cartList);
            TradeCart.fillProductSku(cartList);
            for (TradeCart cart : cartList) {
                if (cart.getProductSpu() == null) {
                    errorList.add(FYUtils.fy("商品不存在"));
                    break;
                }
                if (userMember.getUId() == cart.getProductSpu().getUId()) {
                    errorList.add(FYUtils.fy("不能购买自己店铺的商品"));
                    break;
                }
                if (cart.getCount() == null || cart.getCount() == 0) {
                    errorList.add(FYUtils.fy("购买数量不能为空或为0"));
                    break;
                }
                if (cart.getCount() < Long.parseLong(productSpuService.getPurchasingAmount(cart.getProductSpu()))) {
                    errorList.add(FYUtils.fyParams("商品起购量", cart.getProductSpu().getName(), productSpuService.getPurchasingAmount(cart.getProductSpu())));
                    break;
                }
                if (cart.getCount() > cart.getProductSku().getStock()) {
                    errorList.add(FYUtils.fy("库存不足"));
                    break;
                }
            }
            if (!errorList.isEmpty()) {
                message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, false, null);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, true, null);
        } else {
            //从单详情页下单
            String pid = R.get("pid");
            //skuMsg:skuid-数量
            String skuMsg = R.get("skuMsg");
            //type:1零售，2批发
            String type = R.get("type");
            if (StringUtils.isBlank(pid)) {
                errorList.add(FYUtils.fy("商品id不能为空"));
            }
            if (StringUtils.isNotBlank(pid) && !StringUtils.isNumeric(pid)) {
                errorList.add(FYUtils.fy("商品id只能是数字"));
            }
            if (StringUtils.isBlank(skuMsg)) {
                errorList.add(FYUtils.fy("请选择商品规格和数量"));
            }
            if (StringUtils.isBlank(type)) {
                errorList.add(FYUtils.fy("商品类型不能为空"));
            }
            if (!errorList.isEmpty()) {
                message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, false, null);
            }
            ProductSpu productSpu = productSpuService.selectById(Long.parseLong(pid));
            if (productSpu == null) {
                errorList.add(FYUtils.fy("商品不存在"));
            }
            if (!errorList.isEmpty()) {
                message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, false, null);
            }
            //判断库存
            String[] skus = skuMsg.split(",");
            Long totalNum = 0L;
            for (int i = 0; i < skus.length; i++) {
                String[] skuss = skus[i].split("-");
                if (skuss.length < 2) {
                    errorList.add(FYUtils.fy("缺少参数商品规格和数量"));
                }
                String skuId = skuss[0];
                String count = skuss[1];
                if (StringUtils.isBlank(skuId)) {
                    errorList.add(FYUtils.fy("缺少参数商品规格"));
                }
                if (!StringUtils.isNumeric(skuId)) {
                    errorList.add(FYUtils.fy("商品skuId必须是数字"));
                }
                if (StringUtils.isBlank(count)) {
                    errorList.add(FYUtils.fy("缺少参数商品数量"));
                }
                if (!StringUtils.isNumeric(count)) {
                    errorList.add(FYUtils.fy("商品数量只能是数字"));
                }
                if (errorList.isEmpty()) {
                    ProductSku productSku = productSkuService.selectById(Long.parseLong(skuId));
                    if (productSku == null) {
                        errorList.add(FYUtils.fy("商品sku不存在"));
                    }
                    if (productSku != null && Long.parseLong(count) > productSku.getStock()) {
                        errorList.add(FYUtils.fyParam("商品库存不足", productSpu.getName()));
                    }
                    totalNum += Long.parseLong(count);
                }
            }
            Long purchasingAmount = Long.parseLong(productSpuService.getPurchasingAmount(productSpu));
            if (totalNum < purchasingAmount) {
                errorList.add(FYUtils.fyParams("商品起购量", productSpu.getName(), purchasingAmount+""));
            }
            if (!errorList.isEmpty()) {
                message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, false, null);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, true, null);
        }
    }

    /**
     * 获取确认订单页数据接口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/order/confimOrder")
    public Map<String, Object> tradeConfirmOrder() {
        //对type进行检查
        String typeCheck = R.get("type").trim();
        if (!"1".equals(typeCheck) && !"2".equals(typeCheck) && !"3".equals(typeCheck)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("入参type错误：") + typeCheck, null, null);
        }
        //用户id
        UserMember userMember = AppTokenUtils.findUser().getUserMember();
        //map的键：店铺名-店铺id
        Set<Object> keys = new HashSet<>();
        String stat = R.get("stat");
        try {
            String invoice = "0";
            //stat=1 从购物车页直接下单
            if ("1".equals(stat)) {
                //ids：购物车的多个id
                String ids = R.get("ids");
                String[] cartIds = ids.split(",");
                List<String> cartIdList = Arrays.asList(cartIds);
                List<TradeCart> cartList = tradeCartService.selectByIdIn(cartIdList);
                TradeCart.fillStore(cartList);
                TradeCart.fillProductSpu(cartList);
                TradeCart.fillProductSku(cartList);
                Map<String, List<TradeCart>> cartMap = new LinkedHashMap<>();
                for (TradeCart cart : cartList) {
                    //type:1零售商品：一个价格，2批发商品：根据数量算价格，3混合型商品：根据用户身份和购买数量算价格
                    BigDecimal price = new BigDecimal("0");
                    Integer count = cart.getCount();
                    String type = cart.getProductSpu().getType();
                    if ("1".equals(type) ) {
                        price = cart.getProductSku().getPrice();
                    }
                    if ("2".equals(type)) {
                        price = productSpuService.calculatePrice(cart.getProductSpu().getPId(), count);
                        cart.getProductSku().setPrice(price);
                    }
                    cart.setPrice(price);
                    cart.setPriceSum(price.multiply(new BigDecimal(cart.getCount())));
                    String key = cart.getStore().getName() + "-" + cart.getStore().getStoreId();
                    keys.add(key);
                    if (cartMap.get(key) != null) {
                        cartMap.get(key).add(cart);
                    } else {
                        List<TradeCart> list = new ArrayList<>();
                        list.add(cart);
                        cartMap.put(key, list);
                    }
                    if ("1".equals(cart.getProductSpu().getInvoice())) {
                        invoice = "1";
                    }
                }
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("invoice", invoice);
                dataMap.put("cartMap", cartMap);
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("下单成功1"), dataMap, null);
            } else {
                //stat==2 从单详情页下单
                String pid = R.get("pid");
                //skuMsg:skuid-数量
                String skuMsg = R.get("skuMsg");
                //type:1零售，2批发
                String type = R.get("type");
                ProductSpu productSpu = productSpuService.selectById(Long.parseLong(pid));
                if ("1".equals(productSpu.getInvoice())) {
                    invoice = "1";
                }
                //根据pId,一个或多个skuId、数量，建立一个购物车list
                List<TradeCart> tradeCartList = tradeCartService.selectBySkus(userMember, Long.parseLong(pid), skuMsg, type);
                Map<String, List<TradeCart>> cartMap = new LinkedHashMap<>();
                String key = productSpu.getStore().getName() + "-" + productSpu.getStoreId();
                keys.add(key);
                cartMap.put(key, tradeCartList);
                for (TradeCart cart : tradeCartList) {
                    cart.setIsValid("0");//是否有效
                }
                tradeCartService.insertBatch(tradeCartList);
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("invoice", invoice);
                dataMap.put("cartMap", cartMap);
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("下单成功2"), dataMap, null);
            }
        } catch (Exception e) {
            logger.error("获取确认订单页面数据错误：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 提交订单
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/order/save")
    public Map<String, Object> tradeOrderSave() {
        String paySwitch=Global.getConfig("sys.pay.switch");//在线支付的开启与关闭：0关闭、1开启
        //map的键：店铺id
        String keyss = R.get("keys");
        if (StringUtils.isBlank(keyss)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("请选择要购买的商品"), null, null);
        }
        String[] keys = keyss.split(",");
        //购物车ids
        String ids = R.get("ids");
        String[] cartIds = ids.split(",");
        //收货地址
        String addressId = R.get("addressId");
        MemberAddress entity = new MemberAddress();
        entity.setUId(AppTokenUtils.findUser().getUId());//属主检查
        entity.setAddressId(Long.parseLong(addressId));
        MemberAddress address = memberAddressService.selectOne(new Wrapper(entity));
        if (StringUtils.isBlank(addressId) || address == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("收货地址不能为空"), null, null);
        }
        //发票
        String deliverId = R.get("deliverId");
        List<Object> cartIdList = new ArrayList<>();
        for (int i = 0; i < cartIds.length; i++) {
            if (StringUtils.isNotBlank(cartIds[i])) {
                cartIdList.add(cartIds[i]);
            }
        }
        try {
            List<TradeCart> cartList = tradeCartService.selectByIdIn(cartIdList);
            TradeCart.fillStore(cartList);
            TradeCart.fillProductSpu(cartList);
            TradeCart.fillProductSku(cartList);
            Map<String, List<TradeCart>> cartMap = new LinkedHashMap<>();
            List<String> errorList = new ArrayList<>();
            for (TradeCart cart : cartList) {
                if (cart.getProductSpu() == null || cart.getProductSku() == null) {
                    errorList.add(FYUtils.fy("商品不存在"));
                    break;
                }
                //重新计算价格
                //type:1零售商品：一个价格，2批发商品：根据数量算价格
                BigDecimal price = new BigDecimal("0");
                Integer count = cart.getCount();
                String type = cart.getProductSpu().getType();
                if ("1".equals(type)) {
                    price = cart.getProductSku().getPrice();
                }
                if ("2".equals(type)) {
                    price = productSpuService.calculatePrice(cart.getProductSpu().getPId(), count);
                }
                cart.setPrice(price);
                cart.setPriceSum(price.multiply(new BigDecimal(cart.getCount())));

                String key =cart.getStore().getStoreId().toString();
                if (cartMap.get(key) != null) {
                    cartMap.get(key).add(cart);
                } else {
                    List<TradeCart> list = new ArrayList<>();
                    list.add(cart);
                    cartMap.put(key, list);
                }
            }
            String message = ApiUtils.errorMessage(errorList);
            if (!errorList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            List<TradeOrder> tradeOrderList = tradeOrderService.addOrder(keys, address, deliverId, cartMap, cartIdList, paySwitch);
            message = FYUtils.fy("下单成功");
            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("tradeOrderList", tradeOrderList);
            dataMap.put("paySwitch", paySwitch);//在线支付的开启与关闭：0关闭、1开启
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, dataMap, null);
        } catch (Exception e) {
            logger.error("下单失败：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 计算运费
     *
     * @param pids       多个商品id
     * @param addressIds 多个收货地址id
     * @param counts     多个商品数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/order/calculateFreight")
    public Map<String, Object> tradeCalculateFreight(String pids, String addressIds, String counts) {
        String message = FYUtils.fy("商品id不能为空");
        if (StringUtils.isBlank(pids)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        if (StringUtils.isBlank(addressIds)) {
            message = FYUtils.fy("地址id不能为空");
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        if (StringUtils.isBlank(counts)) {
            message = FYUtils.fy("数量不能为空");
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            String[] pidss = pids.split(",");
            String[] addressIdss = addressIds.split(",");
            String[] countss = counts.split(",");
            List<String> pidList = Arrays.asList(pidss);
            List<String> addressIdList = Arrays.asList(addressIdss);
            List<String> countList = Arrays.asList(countss);
            if (addressIdss.length < pidss.length || countss.length < pidss.length) {
                message = FYUtils.fy("缺少参数");
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            List<Object> freightList = new ArrayList<>();
            Map<Long,Integer> pidCountMap=new HashMap<>();
            for (int i = 0; i < pidList.size(); i++) {
                String pid = pidList.get(i);
                String addressId = addressIdList.get(i);
                String count = countList.get(i);
                if (!StringUtils.isNumeric(pid)) {
                    message = FYUtils.fy("商品id只能是数字");
                    return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
                }
                if (!StringUtils.isNumeric(addressId)) {
                    message = FYUtils.fy("地址id只能是数字");
                    return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
                }
                if (!StringUtils.isNumeric(count)) {
                    message = FYUtils.fy("数量只能是数字");
                    return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
                }
                ProductSpu productSpu = productSpuService.selectById(Long.parseLong(pid));
                if (productSpu == null) {
                    message = FYUtils.fy("商品不存在");
                    return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
                }
                Long pidL=Long.parseLong(pid);
                if(pidCountMap.get(pidL)==null){
                    pidCountMap.put(pidL,0);
                }
                Integer pidCount=pidCountMap.get(pidL);
                pidCountMap.put(pidL,pidCount+ Integer.parseInt(count));
            }

            MemberAddress memberAddress = memberAddressService.selectById(Long.parseLong(addressIdList.get(0)));
            if (memberAddress == null) {
                message = FYUtils.fy("收货地址不存在");
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            //计算运费
            Set set = pidCountMap.keySet();
            for(Iterator iter = set.iterator(); iter.hasNext();) {
                Long pid = (Long)iter.next();
                ProductSpu productSpu=productSpuService.selectById(pid);
                Integer count =pidCountMap.get(pid);
                BigDecimal freight=tradeOrderService.calculateFreight(productSpu, memberAddress,count);
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("pid", pid);
                map.put("freight", freight);
                freightList.add(map);
            }
            message = FYUtils.fy("计算运费成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, freightList, null);
        } catch (Exception e) {
            logger.error("计算运费发生错误：", e);
            message = FYUtils.fy("服务发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 取消订单
     *
     * @param orderId 订单id
     * @param reason  取消订单理由
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/order/cancelOrder")
    public Map<String, Object> tradeCancelOrder(String orderId, String reason) {
        //入参检查
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(orderId)) {
            errorList.add(FYUtils.fy("订单id不能为空"));
        }
        if (StringUtils.isNotBlank(orderId) && !StringUtils.isNumeric(orderId)) {
            errorList.add(FYUtils.fy("订单id只能是数字"));
        }
        if (StringUtils.isBlank(reason)) {
            errorList.add(FYUtils.fy("取消订单理由不能为空"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //检查合格后，业务处理
        //订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setOrderStatus("60");
        tradeOrder.setCancelOrderDate(new Date());
        tradeOrder.setCancelOrderReason(reason);
        tradeOrder.setOrderId(Long.parseLong(orderId));
        try {
            tradeOrderService.cancelOrder(tradeOrder);
            message = FYUtils.fy("取消订单成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("取消订单发生错误：", e);
            message = FYUtils.fy("服务发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }

    }

    /**
     * 确认收货
     *
     * @param orderId 订单id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/order/confirmReceipt")
    public Map<String, Object> tradeConfirmReceipt(String orderId) {
        //入参检查
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(orderId)) {
            errorList.add(FYUtils.fy("订单id不能为空"));
        }
        if (StringUtils.isNotBlank(orderId) && !StringUtils.isNumeric(orderId)) {
            errorList.add(FYUtils.fy("订单id只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //检查合格后，业务处理
        TradeOrder tradeOrder = new TradeOrder();
        //订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
        tradeOrder.setOrderStatus("40");
        tradeOrder.setProductReceiptDate(new Date());
        tradeOrder.setOrderId(Long.parseLong(orderId));
        try {
            tradeOrderService.delayedReceipt(tradeOrder);
            message = FYUtils.fy("收货成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("确认收货发生错误：", e);
            message = FYUtils.fy("服务发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

}
