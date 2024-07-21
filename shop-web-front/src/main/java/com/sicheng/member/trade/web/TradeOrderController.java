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
package com.sicheng.member.trade.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sicheng.admin.logistics.entity.LogisticsCompany;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.admin.trade.entity.TradeDeliver;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.common.config.Global;
import com.sicheng.common.express.KdniaoTrackQuery;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.member.interceptor.MemberMenuInterceptor;
import com.sicheng.seller.logistics.service.LogisticsCompanyService;
import com.sicheng.seller.member.service.MemberAddressService;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.seller.trade.service.TradeCartService;
import com.sicheng.seller.trade.service.TradeDeliverService;
import com.sicheng.seller.trade.service.TradeOrderItemService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 订单 Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${memberPath}/trade/tradeOrder")
public class TradeOrderController extends BaseController {

    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private LogisticsCompanyService logisticsCompanyService;
    @Autowired
    private TradeCartService tradeCartService;
    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private TradeDeliverService tradeDeliverService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private SiteSendMessagsService siteSendMessagsService;
    @Autowired
    private SysVariableService sysVariableService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting("tradrOrder");//三级菜单高亮
    }

    /**
     * 进入列表页
     *
     * @param tradeOrder 订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(TradeOrder tradeOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索
        String searchCate = R.get("searchCate");
        String searchValue = R.get("searchValue");
        List<TradeOrder> orderList = new ArrayList<>();
        //搜索条件：1代表商品名称，2代表订单编号
        if (StringUtils.isNotBlank(searchValue)) {
            if ("1".equals(searchCate) || "".equals(searchCate)) {
                //根据产品名称获取一个订单详情的list,遍历订单详情list获取所有订单id的list，根据订单id的list找到订单list
                TradeOrderItem orderItem = new TradeOrderItem();
                orderItem.setName(searchValue);
                List<TradeOrderItem> orderItemList = tradeOrderItemService.selectByWhere(new Wrapper(orderItem));
                List<Object> tradeOrderIds = new ArrayList<>();
                if (orderItemList.size() > 0) {
                    for (TradeOrderItem item : orderItemList) {
                        tradeOrderIds.add(item.getOrderId());
                    }
                }
                if (tradeOrderIds.size() > 0) {
                    orderList = tradeOrderService.selectByIdIn(tradeOrderIds);
                }
            } else if ("2".equals(searchCate)) {
                //如果查的是订单编号，要判断查询的内容是不是纯数字，如果是转换成Long类型，如果不是置为-1
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = pattern.matcher(searchValue);
                if (isNum.matches()) {
                	//订单号查出18位，无法搜索出订单数据
                	if(searchValue.length()>18){
                		tradeOrder.setOrderId(-1L);
                	}else{
                		tradeOrder.setOrderId(Long.parseLong(searchValue));
                	}
                } else {
                    tradeOrder.setOrderId(-1L);
                }
            }
        }
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeOrder.setUId(userMember.getUId());
        Wrapper wrapper = new Wrapper(tradeOrder);
        if ("1".equals(R.get("isReturnStat"))) {
            wrapper.and("is_return_status <>", 0);
        }
        Page<TradeOrder> page = tradeOrderService.selectByWhere(new Page<TradeOrder>(request, response), wrapper);
        List<TradeOrder> resultList = new ArrayList<>();
        //orderList是根据商品名称查询出的list，取出orderList和page.getList()中相同的数据，就是当前条件下的数据
        if (("1".equals(searchCate) || "".equals(searchCate)) && StringUtils.isNotBlank(searchValue)) {
            for (TradeOrder order : orderList) {
                if (page.getList().contains(order)) {
                    resultList.add(order);
                }
            }
            page.setList(resultList);
        }
        TradeOrder.fillStore(page.getList());
        //遍历取出各个状态下的订单数
        int status10Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 10).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int status20Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 20).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int status30Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 30).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int status40Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 40).and("u_id=", userMember.getUId()));
        int status50Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 50).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        int status60Count = tradeOrderService.countByWhere(new Wrapper().and("order_status =", 60).and("is_return_status=", 0).and("u_id=", userMember.getUId()));
        //获取最晚收货时间
        SysVariable sysVariable = sysVariableService.getSysVariable("trade_order_delivery_time");
        if (sysVariable != null) {
            model.addAttribute("daysOfReceipt", sysVariable.getValue());
        }
        model.addAttribute("page", page);
        model.addAttribute("orderStatus", tradeOrder.getOrderStatus());
        model.addAttribute("isReturnStatus", R.get("isReturnStat"));
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchCate", searchCate);
        model.addAttribute("status10Count", status10Count);
        model.addAttribute("status20Count", status20Count);
        model.addAttribute("status30Count", status30Count);
        model.addAttribute("status40Count", status40Count);
        model.addAttribute("status50Count", status50Count);
        model.addAttribute("status60Count", status60Count);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        return "member/trade/tradeOrderList";
    }

    /**
     * 取消订单
     *
     * @param tradeOrder         订单
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "cancelOrder")
    public Map<String, Object> cancelOrder(TradeOrder tradeOrder, RedirectAttributes redirectAttributes, Model model) {
        Map<String, Object> map = new LinkedHashMap<>();
        //入参检查
        if (tradeOrder == null || tradeOrder.getId() == null) {
            map.put("statusCode", "404");
            map.put("message", FYUtils.fyParams("订单不存在！"));
            return map;
        }
        tradeOrder = tradeOrderService.selectById(tradeOrder.getId());//订单信息
        if (tradeOrder == null) {
            map.put("statusCode", "404");
            map.put("message", FYUtils.fyParams("订单不存在！"));
            return map;
        }
        //检查合格后，业务处理
        if (tradeOrder.getPaymentMethodId() == null) {
            //未选择支付方式,直接取消订单
            tradeOrder.setOrderStatus("60");//订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
            tradeOrder.setCancelOrderDate(new Date());
            tradeOrderService.cancelOrder(tradeOrder, "2");
            map.put("statusCode", "201");
            map.put("message", FYUtils.fyParams("取消订单成功！"));
            return map;
        }
        //选择了支付方式,查看第三方是否支付
        map = tradeOrderService.queryAndCancelOrder(tradeOrder, "2");
        return map;
    }

    /**
     * 确认收货
     *
     * @param tradeOrder 订单
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "confirmReceipt")
    public Map<String, Object> confirmReceipt(TradeOrder tradeOrder, Model model) {
        Map<String, Object> map = new HashMap<>();
        //入参检查
        if (tradeOrder == null || tradeOrder.getId() == null) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("订单不存在！"));
            return map;
        }
        //检查合格后，业务处理
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        //订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
        tradeOrder.setOrderStatus("40");
        tradeOrder.setProductReceiptDate(new Date());
        Map<String, Object> delayedMap = tradeOrderService.delayedReceipt(tradeOrder);
        if (!(Boolean) delayedMap.get("success")) {
            map.put("success", false);
            map.put("message", delayedMap.get("message") == null ? FYUtils.fyParams("收货失败！") : delayedMap.get("message"));
            return map;
        }
        TradeOrder order = tradeOrderService.selectOne(new Wrapper().and("order_id =", tradeOrder.getId()).and("u_id = ", userMember.getUId()));
        //发送消息
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("orderId", order.getOrderId().toString());
        contentMap.put("finishTime", DateUtils.formatDateTime(order.getProductReceiptDate()));
        //给买家发送消息
        String messageTemplateNum1 = SiteSendMessagsService.MEMBER_ORDERS_RESEIVE;
        Long uId1 = order.getUId();
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum1, uId1);
        //给卖家发送消息
        String messageTemplateNum2 = SiteSendMessagsService.STORE_ORDERS_RESEIVE;
        if (order.getStore() != null && order.getStore().getUserMain() != null) {
            Long uId2 = order.getStore().getUserMain().getUId();
            siteSendMessagsService.sendMessage(contentMap, messageTemplateNum2, uId2);
        }
        map.put("success", true);
        map.put("message", FYUtils.fyParams("收货成功！"));
        return map;
    }

    /**
     * 延迟收货
     *
     * @param tradeOrder         订单
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "delayedReceipt")
    public String delayedReceipt(TradeOrder tradeOrder, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (tradeOrder == null || tradeOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        String stat = "0";
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeOrderService.updateByWhereSelective(tradeOrder, new Wrapper().and("order_id =", tradeOrder.getId()).and("u_id = ", userMember.getUId()));
        stat = "1";
        //发送消息
        TradeOrder order = tradeOrderService.selectOne(new Wrapper().and("order_id =", tradeOrder.getId()).and("u_id = ", userMember.getUId()));
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("orderId", order.getOrderId().toString());
        contentMap.put("memberName", SsoUtils.getUserMain().getLoginName());
        //获取最晚收货时间
        SysVariable sysVariable = sysVariableService.getSysVariable("trade_order_delivery_time");
        if (sysVariable == null || StringUtils.isBlank(sysVariable.getValue())) {
            return stat;
        }
        String daysOfReceipt = sysVariable.getValue();
        //默认收货的天数
        Long daysOfReceiptTime = Long.parseLong(daysOfReceipt) * 24 * 60 * 60 * 1000;
        //延迟收货的天数
        Long delayDaysTime = Long.valueOf(order.getDelayDays()) * 24 * 60 * 60 * 1000;
        //下单时间
        Long placeOrderTime = order.getPlaceOrderTime().getTime();
        if (daysOfReceiptTime == null || delayDaysTime == null || placeOrderTime == null) {
            return stat;
        }
        //下单时间+默认收货天数+延迟收货天数=最后收货时间
        contentMap.put("autoReceiveTime", DateUtils.formatDateTime(new Date(daysOfReceiptTime + delayDaysTime + placeOrderTime)));
        String messageTemplateNum = siteSendMessagsService.STORE_ORDERS_DELAY;
        Long uId = order.getStore().getUserMain().getUId();
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        return stat;
    }

    /**
     * 进入订单详情页
     *
     * @param tradeOrder 订单
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "tradeOrderDetail")
    public String tradeOrderDetail(TradeOrder tradeOrder, Model model) {
        //入参检查
        if (tradeOrder == null || tradeOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        tradeOrder.setUId(SsoUtils.getUserMain().getUserMember().getUId());//属主检查
        TradeOrder entity = tradeOrderService.selectOne(new Wrapper(tradeOrder));
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        //TradeOrderItem.fillStoreAlbumPicture(entity.getTradeOrderItemList());
        //最迟收货时间
        Date latestDeliveryTime = entity.getPlaceOrderTime();
        Integer days = 0;
        //获取最晚收货天数
        SysVariable sysVariable = sysVariableService.getSysVariable("trade_order_delivery_time");
        if (sysVariable != null && StringUtils.isNoneBlank(sysVariable.getValue()) && StringUtils.isNumeric(sysVariable.getValue())) {
            days = Integer.parseInt(sysVariable.getValue());
            model.addAttribute("daysOfReceipt", sysVariable.getValue());
        }
        if (!"0".equals(entity.getDelayDays()) && entity.getDelayDays() != null) {
            days += entity.getDelayDays();
        }
        long time = latestDeliveryTime.getTime(); // 得到指定日期的毫秒数
        days = days * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += days; // 相加得到新的毫秒数
        Date lateTime = new Date(time);// 将毫秒数转换成日期
        model.addAttribute("lateTime", lateTime);
        model.addAttribute("tradeOrder", entity);
        return "member/trade/tradeOrderDetail";
    }

    /**
     * 查询物流信息
     *
     * @param tradeOrder 订单
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "showLogisticsMsg")
    public String showLogisticsMsg(TradeOrder tradeOrder, Model model) {
        //入参检查
        if (tradeOrder == null || tradeOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        tradeOrder.setUId(SsoUtils.getUserMain().getUserMember().getUId());//属主检查
        TradeOrder entity = tradeOrderService.selectOne(new Wrapper(tradeOrder));
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        String result = "";
        Long logisticsTemplateId = entity.getLogisticsTemplateId();
        String trackingNo = entity.getTrackingNo();
        LogisticsCompany logisticsCompany = logisticsCompanyService.selectById(logisticsTemplateId);
        if (logisticsCompany == null) {
            return "member/trade/logisticsMsg";
        }
        KdniaoTrackQuery api = new KdniaoTrackQuery();
        //返回来的json格式物流信息
        try {
            result = api.getOrderTracesByJson(logisticsCompany.getCompanyNumber(), trackingNo);
            //把json转为map
            Map<String, Object> resultMap = JSON.parseObject(result);
            //获取物流轨迹信息
            JSONArray traces = (JSONArray) resultMap.get("Traces");
            List<Map<String, Object>> mapListJson = (List) traces;
            List<Map<String, Object>> mapListJsonNew = new ArrayList<>();
            for (int i = mapListJson.size() - 1; i >= 0; i--) {
                mapListJsonNew.add(mapListJson.get(i));
            }
            resultMap.put("Traces", mapListJsonNew);
            model.addAttribute("resultMap", resultMap);
            model.addAttribute("tradeOrder", entity);
        } catch (Exception e) {
            logger.error("物流查询出现错误：" + e);
        }
        return "member/trade/logisticsMsg";
    }

    /**
     * 进入选择退款或退货页面
     *
     * @param tradeOrder     订单
     * @param tradeOrderItem 订单详情
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "selectReturnOrderType")
    public String selectReturnOrderType(TradeOrder tradeOrder, TradeOrderItem tradeOrderItem, Model model) {
        //订单入参检查
        if (tradeOrder == null || tradeOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        tradeOrder.setUId(SsoUtils.getUserMain().getUserMember().getUId());//属主检查
        TradeOrder entity1 = tradeOrderService.selectOne(new Wrapper(tradeOrder));
        if (entity1 == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        //订单详情入参检查
        if (tradeOrderItem == null || tradeOrderItem.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("订单详情不存在！"));
            return "error/400";
        }
        tradeOrderItem.setOrderId(entity1.getId());//属主检查
        TradeOrderItem entity2 = tradeOrderItemService.selectOne(new Wrapper(tradeOrderItem));
        if (entity2 == null) {
            model.addAttribute("message", FYUtils.fyParams("订单详情不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        model.addAttribute("tradeOrder", entity1);
        model.addAttribute("tradeOrderItem", entity2);
        return "member/trade/tradeSelectReturnType";
    }

    /**
     * 进入确认订单页
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "confirmOrder")
    public String confirmOrder(Model model) {
        String priceSum = "";
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        //收货地址
        List<MemberAddress> addressList = memberAddressService.selectByWhere(new Wrapper().and("u_id=", userMember.getUId()).orderBy("is_default desc"));//属主检查
        //map的键：店铺名-店铺id
        Set<Object> keys = new HashSet<>();
        if ("1".equals(R.get("stat"))) {
            //1==stat从购物车页直接下单
            String ids = R.get("ids");
            priceSum = R.get("priceSum");
            if (StringUtils.isNotBlank(ids)) {
                String[] cartIds = ids.split(",");
                if (cartIds.length > 0) {
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
                    for (TradeCart cart : cartList) {
                        //计算运费
                        //如果当前用户还没有收货地址，暂时把运费设为0
                        if (addressList.isEmpty()) {
                            cart.getProductSpu().setExpressPrice(new BigDecimal("0"));
                        } else {
                            cart.getProductSpu().setExpressPrice(tradeOrderService.calculateFreight(cart.getProductSpu(), addressList.get(0), cart.getCount()));
                        }
                        cart.setPriceSum(cart.getProductSku().getPrice().multiply(new BigDecimal(cart.getCount())));
                        String key = cart.getStore().getName() + "-" + cart.getStore().getStoreId();
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
                    model.addAttribute("cartMap", cartMap);
                }
            }
            //发票信息
            List<TradeDeliver> deliverList = tradeDeliverService.selectByWhere(new Wrapper().and("u_id=", userMember.getUId()));
            model.addAttribute("deliverList", deliverList);
            model.addAttribute("addressList", addressList);
            model.addAttribute("priceSum", priceSum);
            model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
            model.addAttribute("ids", ids);
            model.addAttribute("keys", keys);
        }
        return "member/trade/tradeConfirmOrder";
    }

    /**
     * 提交订单
     *
     * @param model
     * @param tradeOrder         订单
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "addOrder")
    public String addOrder(Model model, TradeOrder tradeOrder, RedirectAttributes redirectAttributes) {
        String paySwitch= Global.getConfig("sys.pay.switch");//在线支付的开启与关闭：0关闭、1开启
        //map的键：店铺名-店铺id
        String keyss = R.get("keys");
        keyss = keyss.substring(1, keyss.length() - 1);
        String[] keys = keyss.split(",");
        //购物车ids
        String ids = R.get("ids");
        String[] cartIds = ids.split(",");
        if (StringUtils.isBlank(ids) || keys.length == 0 || cartIds.length == 0) {
            addMessage(model, FYUtils.fyParams("请选择商品"));
            return confirmOrder(model);//回显错误提示
        }
        //收货地址
        String addressId = R.get("addressId");
        MemberAddress address = memberAddressService.selectById(Long.parseLong(addressId));
        if (StringUtils.isBlank(addressId) || address == null) {
            addMessage(model, FYUtils.fyParams("请选择收货地址"));
            return confirmOrder(model);//回显错误提示
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
        for (TradeCart cart : cartList) {
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
        if (cartMap == null || cartMap.size() == 0) {
            addMessage(model, FYUtils.fyParams("请选择商品"));
            return confirmOrder(model);//回显错误提示
        }
        List<TradeOrder> orderList = tradeOrderService.addOrder(keys, address, deliverId, cartMap,/* cartList,*/ cartIdList,paySwitch);
        //删除购物车数据
        //tradeCartService.deleteByIdIn(cartIdList);
        redirectAttributes.addFlashAttribute("orderList", orderList);
        redirectAttributes.addFlashAttribute("orderPrice", R.get("orderPrice"));
        redirectAttributes.addFlashAttribute("userMember", SsoUtils.getUserMain().getUserMember());
        redirectAttributes.addFlashAttribute("address", address);
        //根据在线支付的开启与关闭的状态下单成功后跳转不同的页面（0关闭、1开启）
        if("0".equals(paySwitch)){
            return "redirect:" + frontPath + "/trade/order/placeOrder.htm";
        }else{
            return "redirect:" + memberPath + "/trade/tradeOrder/tradeCheckorder.htm";
        }
    }

    /**
     * 进入支付页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "tradeCheckorder")
    public String orderPayView(Model model) {
        return "member/trade/tradeCheckorder";
    }

    /**
     * 选择收货地址
     *
     * @param request
     * @param response
     * @param memberAddress
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "selectAddress")
    public Map<String, Object> selectAddress(HttpServletRequest request, HttpServletResponse response, MemberAddress memberAddress, Model model) {
        String ids = R.get("ids");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(ids) || memberAddress == null) {
            map.put("message", "fail");
            return map;
        }
        String[] cartIds = ids.split(",");
        if (cartIds.length == 0) {
            map.put("message", "fail");
            return map;
        }
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
        Map<Long,Object> pidCartMap=new HashMap<>();
        //找出同一个spu的购物车，累加数量
        for (TradeCart cart : cartList) {
            if(pidCartMap.get(cart.getPId())!=null){
                Integer count=cart.getCount();
                TradeCart tradeCart = (TradeCart) pidCartMap.get(cart.getPId());
                tradeCart.setCount(tradeCart.getCount()+count);
            }else{
                pidCartMap.put(cart.getPId(),cart);
            }
        }
        //计算运费
        MemberAddress address = memberAddressService.selectById(memberAddress.getAddressId());
        Set set = pidCartMap.keySet();
        List<TradeCart> resultTradeCartList=new ArrayList<>();
        for(Iterator iter = set.iterator(); iter.hasNext();) {
            Long pid = (Long)iter.next();
            TradeCart tradeCart=(TradeCart)pidCartMap.get(pid);
            tradeCart.setFreight(tradeOrderService.calculateFreight(tradeCart.getProductSpu(), address, tradeCart.getCount()));
            resultTradeCartList.add(tradeCart);
        }
        map.put("message", "success");
        map.put("cartList", resultTradeCartList);
        return map;
    }

    /**
     * 计算运费
     *
     * @param pid        商品id
     * @param provinceId 省id
     * @param cityId     市id
     * @param count      数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "calculateFreight")
    public String calculateFreight(Long pid, Long provinceId, Long cityId, Integer count) {
        if (pid == null) {
            return "-1";
        }
        if (provinceId == null || cityId == null) {
            return "0";
        }
        if (count == null) {
            count = 0;
        }
        ProductSpu productSpu = productSpuService.selectById(pid);
        if (productSpu == null) {
            return "-1";
        }
        String freight = "";
        MemberAddress memberAddress = new MemberAddress();
        memberAddress.setProvinceId(provinceId);
        memberAddress.setCityId(cityId);
        freight = tradeOrderService.calculateFreight(productSpu, memberAddress, count).toString();
        return freight;
    }

}