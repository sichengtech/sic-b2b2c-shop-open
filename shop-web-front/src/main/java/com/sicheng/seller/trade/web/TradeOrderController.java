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
package com.sicheng.seller.trade.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sicheng.admin.logistics.entity.LogisticsCompany;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.common.express.KdniaoTrackQuery;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.logistics.service.LogisticsCompanyService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.seller.trade.service.TradeOrderItemService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 订单 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${sellerPath}/trade/tradeOrder")
public class TradeOrderController extends BaseController {

    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private LogisticsCompanyService logisticsCompanyService;

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
        String menu3 = "020205";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param tradeOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:view")
    @RequestMapping(value = "list")
    public String list(TradeOrder tradeOrder, Model model) {
        //搜索
        String searchCate = R.get("searchCate");
        String searchValue = R.get("searchValue");
        if ("1".equals(searchCate)) {
            //如果是用户名转换成小写
            searchValue = searchValue.toLowerCase();
        }
        Page<TradeOrder> page = selectTradeOrder(searchValue, searchCate, tradeOrder);
        model.addAttribute("page", page);
        model.addAttribute("orderStatus", tradeOrder.getOrderStatus());
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchCate", searchCate);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        return "seller/trade/tradeOrderList";
    }

    /**
     * 取消订单
     *
     * @param tradeOrder         实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @ResponseBody
    @RequestMapping(value = "cancelOrder")
    public Map<String, Object> cancelOrder(TradeOrder tradeOrder, Model model) {
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
            tradeOrderService.cancelOrder(tradeOrder, "1");
            map.put("statusCode", "201");
            map.put("message", FYUtils.fyParams("取消订单成功"));
            return map;
        }
        //选择了支付方式,查看第三方是否支付
        map = tradeOrderService.queryAndCancelOrder(tradeOrder, "1");
        return map;
    }

    /**
     * 修改运费、修改价格、修改发货弹出框中的用户信息
     *
     * @param tradeOrder         实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @ResponseBody
    @RequestMapping(value = "edit")
    public String edit(TradeOrder tradeOrder, Model model) {
        //入参检查
        if (tradeOrder == null || tradeOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //按多个条件进行修改
        TradeOrder condition = new TradeOrder();
        condition.setOrderId(tradeOrder.getId());//属主检查
        condition.setStoreId(userSeller.getStoreId());//属主检查
        tradeOrderService.updateByWhereSelective(tradeOrder, new Wrapper(condition));
        String stat = "1";
        //发送消息
        if (StringUtils.isNoneBlank(R.get("freight"))) {
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("orderId", tradeOrder.getOrderId().toString());
            contentMap.put("freightAmount", tradeOrder.getFreight().toString());
            String messageTemplateNum = SiteSendMessagsService.MEMBER_ORDERS_MODIFY_FREIGHT;
            Long uId = tradeOrder.getUId();
            siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        }
        return stat;
    }

    /**
     * 发货弹出框
     *
     * @param tradeOrder         实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "deliverGoods1")
    public String deliverGoods1(TradeOrder tradeOrder, Model model) {
        //入参检查
        if (tradeOrder == null || tradeOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        //入参检查
        tradeOrder.setStoreId(SsoUtils.getUserMain().getUserSeller().getStoreId());//属主检查
        TradeOrder entity = tradeOrderService.selectOne(new Wrapper(tradeOrder));
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        model.addAttribute("tradeOrder", entity);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        model.addAttribute("logisticsCompanyList", logisticsCompanyService.selectByWhere(new Wrapper().and("status =", "1")));
        return "seller/trade/shipmentModal";
    }

    /**
     * 执行发货
     *
     * @param tradeOrder         实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @ResponseBody
    @RequestMapping(value = "deliverGoods2")
    public String deliverGoods2(TradeOrder tradeOrder, Model model) {
        //入参检查
        if (tradeOrder == null || tradeOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        //订单置已发货状态
        tradeOrder.setOrderStatus("30");
        tradeOrder.setDeliverProductDate(new Date());
        tradeOrderService.deliverGoods(tradeOrder);
        String stat = "1";
        return stat;
    }

    /**
     * 进入订单详情页
     *
     * @param tradeOrder 实体对象
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "tradeOrderDetail")
    public String tradeOrderDetail(TradeOrder tradeOrder, Model model) {
        TradeOrder entity = null;
        if (tradeOrder != null) {
            if (tradeOrder.getId() != null) {
                entity = tradeOrderService.selectById(tradeOrder.getId());
                if (entity != null) {
                    //最迟收货时间
                    Date latestDeliveryTime = entity.getPlaceOrderTime();
                    //获取最晚收货天数
                    Integer days = 0;
                    SysVariable sysVariable = sysVariableService.getSysVariable("trade_order_delivery_time");
                    if (sysVariable != null && StringUtils.isNotBlank(sysVariable.getValue()) && StringUtils.isNumeric(sysVariable.getValue())) {
                        days = Integer.parseInt(sysVariable.getValue());
                    }
                    if (!"0".equals(entity.getDelayDays()) && entity.getDelayDays() != null) {
                        days += entity.getDelayDays();
                    }
                    long time = latestDeliveryTime.getTime(); // 得到指定日期的毫秒数
                    days = days * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
                    time += days; // 相加得到新的毫秒数
                    Date lateTime = new Date(time);// 将毫秒数转换成日期
                    model.addAttribute("lateTime", lateTime);
                }
            }
        }
        model.addAttribute("tradeOrder", entity);
        return "seller/trade/tradeOrderDetail";
    }

    /**
     * 进入订单打印页面
     *
     * @param tradeOrder 实体对象
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "print")
    public String print(TradeOrder tradeOrder, Model model) {
        TradeOrder entity = null;
        if (tradeOrder != null) {
            if (tradeOrder.getId() != null) {
                entity = tradeOrderService.selectById(tradeOrder.getId());
                //TradeOrderItem.fillStoreAlbumPicture(entity.getTradeOrderItemList());
            }
        }
        model.addAttribute("tradeOrder", entity);
        model.addAttribute("pointDate", new Date());
        return "seller/trade/tradeOrderPoint";
    }

    /**
     * 账单详情页面中查询订单
     *
     * @param tradeOrder 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:view")
    @RequestMapping(value = {"search"})
    public String search(TradeOrder tradeOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        tradeOrder.setStoreId(userSeller.getStoreId());
        Page<TradeOrder> page = tradeOrderService.selectByWhere(new Page<TradeOrder>(request, response), new Wrapper(tradeOrder));
        //1代表查询的订单列表，2代表查询退单的类型(2类型的查询在退单controller)
        model.addAttribute("type", "1");
        model.addAttribute("page", page);
        return "seller/settlement/settlementBillDetailModel";
    }

    /**
     * 查询物流信息
     *
     * @throws Exception
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "showLogisticsMsg")
    public String showLogisticsMsg(TradeOrder tradeOrder, Model model) {
        String result = "";
        if (tradeOrder == null) {
            return "";
        }
        TradeOrder entity = tradeOrderService.selectById(tradeOrder.getOrderId());
        if (entity != null) {
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
        }
        return "seller/trade/logisticsMsg";

    }

    /**
     * 导出订单
     *
     * @param tradeOrder 订单列表
     * @param request
     * @param response
     * @param redirectAttributes
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "exportOrder", method = RequestMethod.POST)
    public String exportOrder(TradeOrder tradeOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        String searchCate = R.get("searchCate");
        String searchValue = R.get("searchValue");
        Page<TradeOrder> page = selectTradeOrder(searchValue, searchCate, tradeOrder);
        if (page.getList() == null) {
            return "redirect:" + sellerPath + "/trade/tradeOrder/list.do?repage";
        }
        try {
            tradeOrderService.exprotOrder(page.getList(), response);
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, FYUtils.fyParams("导出用户失败！失败信息：") + e.getMessage());
        }
        return "redirect:" + sellerPath + "/trade/tradeOrder/list.do?repage";
    }

    /**
     * 订单列表页上根据条件查询订单
     *
     * @param searchValue 条件值
     * @param searchCate  条件名
     * @param tradeOrder  订单
     * @return
     */
    private Page<TradeOrder> selectTradeOrder(String searchValue, String searchCate, TradeOrder tradeOrder) {
        List<TradeOrder> orderList = new ArrayList<>();
        //搜索条件：1代表买家账号，2代表商品名称，3代表订单编号
        if (StringUtils.isNotBlank(searchValue)) {
            if ("1".equals(searchCate) || "".equals(searchCate)) {
                //用户名转换小写
                searchValue = searchValue.toLowerCase();
                UserMain userMain = new UserMain();
                userMain.setLoginName(searchValue);
                List<UserMain> userMainList = userMainService.selectByWhere(new Wrapper(userMain));
                if (userMainList.isEmpty()) {
                    return new Page<TradeOrder>(R.getRequest(), R.getResponse());
                }
                tradeOrder.setUId(userMainList.get(0).getUId());
            } else if ("2".equals(searchCate)) {
                //根据产品名称获取一个订单详情的list,遍历订单详情list获取所有订单id的list，根据订单id的list找到订单list
                TradeOrderItem orderItem = new TradeOrderItem();
                orderItem.setName(searchValue);
                List<TradeOrderItem> orderItemList = tradeOrderItemService.selectByWhere(new Wrapper(orderItem));
                if (orderItemList.isEmpty()) {
                    return new Page<TradeOrder>(R.getRequest(), R.getResponse());
                }
                List<Object> tradeOrderIds = new ArrayList<>();
                for (TradeOrderItem item : orderItemList) {
                    tradeOrderIds.add(item.getOrderId());
                }
                if (tradeOrderIds.isEmpty()) {
                    return new Page<TradeOrder>(R.getRequest(), R.getResponse());
                }
                orderList = tradeOrderService.selectByIdIn(tradeOrderIds);
            } else if ("3".equals(searchCate)) {
                //如果查的是订单编号，要判断查询的内容是不是纯数字，如果是转换成Long类型，如果不是置为-1
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = pattern.matcher(searchValue);
                if (!isNum.matches() || searchValue.length()>18) {
                    return new Page<TradeOrder>(R.getRequest(), R.getResponse());
                }
                tradeOrder.setOrderId(Long.parseLong(searchValue));
            }
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        if (userSeller.getStoreId() != null) {
            tradeOrder.setStoreId(userSeller.getStoreId());
        } else {
            tradeOrder.setStoreId(-1l);
        }
        Page<TradeOrder> page = tradeOrderService.selectByWhere(new Page<TradeOrder>(R.getRequest(), R.getResponse()), new Wrapper(tradeOrder));
        List<TradeOrder> resultList = new ArrayList<>();
        //orderList是根据商品名称查询出的list，取出orderList和page.getList()中相同的数据，就是当前条件下的数据
        if ("2".equals(searchCate) && StringUtils.isNotBlank(searchValue)) {
            for (TradeOrder order : orderList) {
                if (page.getList().contains(order)) {
                    resultList.add(order);
                }
            }
            page.setList(resultList);
        }
        return page;
    }
}