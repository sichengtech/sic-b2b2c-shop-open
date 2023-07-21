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
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.common.express.KdniaoTrackQuery;
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
import com.sicheng.seller.trade.service.TradeOrderItemService;
import com.sicheng.seller.trade.service.TradeReturnOrderService;
import com.sicheng.sso.service.UserMainService;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 退款、退货退款订单 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${sellerPath}/trade/tradeReturnOrder")
public class TradeReturnOrderController extends BaseController {

    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private LogisticsCompanyService logisticsCompanyService;
    @Autowired
    private SiteSendMessagsService siteSendMessagsService;

    /**
     * 菜单高亮
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        String menu3 = "060202";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param tradeReturnOrder 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:view")
    @RequestMapping(value = "tradeReturnOrderList")
    public String tradeReturnOrderList(TradeReturnOrder tradeReturnOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索
        String searchCate = R.get("searchCate");
        String searchValue = R.get("searchValue");
        List<TradeOrderItem> orderItemList = new ArrayList<>();
        //搜索条件：1代表买家账号，2代表商品名称，3代表订单编号
        if (StringUtils.isNotBlank(searchValue)) {
            if ("1".equals(searchCate) || "".equals(searchCate)) {
                //用户名转换小写
                searchValue = searchValue.toLowerCase();
                UserMain userMain = new UserMain();
                userMain.setLoginName(searchValue);
                List<UserMain> userMainList = userMainService.selectByWhere(new Wrapper(userMain));
                if (!userMainList.isEmpty()) {
                    tradeReturnOrder.setUId(userMainList.get(0).getUId());
                }
            } else if ("2".equals(searchCate)) {
                //根据产品名称获取一个订单详情的list
                TradeOrderItem orderItem = new TradeOrderItem();
                orderItem.setName(searchValue);
                orderItemList = tradeOrderItemService.selectByWhere(new Wrapper(orderItem));
            } else if ("3".equals(searchCate)) {
                //如果查的是订单编号，要判断查询的内容是不是纯数字，如果是转换成Long类型，如果不是置为-1
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = pattern.matcher(searchValue);
                if (isNum.matches() && searchValue.length()<18) {
                    tradeReturnOrder.setOrderId(Long.parseLong(searchValue));
                } else {
                    tradeReturnOrder.setOrderId(-1L);
                }
            }
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        if (userSeller != null) {
            tradeReturnOrder.setStoreId(userSeller.getStoreId());
        } else {
            tradeReturnOrder.setStoreId(-1L);
        }
        //1：退货退款，2退款
        tradeReturnOrder.setType("1");
        Page<TradeReturnOrder> page = tradeReturnOrderService.selectByWhere(new Page<TradeReturnOrder>(request, response), new Wrapper(tradeReturnOrder));
        List<TradeReturnOrder> resultList = new ArrayList<>();
        //orderItemList是根据商品名称查询出的list，取出orderItemList和page.getList()中相同的数据，就是当前条件下的数据
        if ("2".equals(searchCate) && StringUtils.isNotBlank(searchValue)) {
            for (TradeOrderItem item : orderItemList) {
                for (TradeReturnOrder returnOrder : page.getList()) {
                    if (item.getOrderItemId() == returnOrder.getOrderItemId()) {
                        resultList.add(returnOrder);
                    }
                }
            }
            page.setList(resultList);
        }
        TradeReturnOrder.fillUserMain(page.getList());
        TradeReturnOrder.fillTradeOrder(page.getList());
        TradeReturnOrder.fillTradeOrderItem(page.getList());
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchCate", searchCate);
        model.addAttribute("page", page);
        return "seller/trade/tradeReturnOrderList";
    }

    /**
     * 进入列表页
     *
     * @param tradeReturnOrder 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:view")
    @RequestMapping(value = "tradeReturnMoneyList")
    public String tradeReturnMoneyList(TradeReturnOrder tradeReturnOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索
        String searchCate = R.get("searchCate");
        String searchValue = R.get("searchValue");
        List<TradeOrderItem> orderItemList = new ArrayList<>();
        //搜索条件：1代表买家账号，2代表商品名称，3代表订单编号
        if (StringUtils.isNotBlank(searchValue)) {
            if ("1".equals(searchCate) || "".equals(searchCate)) {
                //用户名转换小写
                searchValue = searchValue.toLowerCase();
                UserMain userMain = new UserMain();
                userMain.setLoginName(searchValue);
                List<UserMain> userMainList = userMainService.selectByWhere(new Wrapper(userMain));
                if (!userMainList.isEmpty()) {
                    tradeReturnOrder.setUId(userMainList.get(0).getUId());
                }
            } else if ("2".equals(searchCate)) {
                //根据产品名称获取一个订单详情的list
                TradeOrderItem orderItem = new TradeOrderItem();
                orderItem.setName(searchValue);
                orderItemList = tradeOrderItemService.selectByWhere(new Wrapper(orderItem));
            } else if ("3".equals(searchCate)) {
                //如果查的是订单编号，要判断查询的内容是不是纯数字，如果是转换成Long类型，如果不是置为-1
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = pattern.matcher(searchValue);
                if (isNum.matches() && searchValue.length()<18) {
                    tradeReturnOrder.setOrderId(Long.parseLong(searchValue));
                } else {
                    tradeReturnOrder.setOrderId(-1L);
                }
            }
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        tradeReturnOrder.setStoreId(userSeller.getStoreId());
        //1：退货退款，2退款
        tradeReturnOrder.setType("2");
        Page<TradeReturnOrder> page = tradeReturnOrderService.selectByWhere(new Page<TradeReturnOrder>(request, response), new Wrapper(tradeReturnOrder));
        List<TradeReturnOrder> resultList = new ArrayList<>();
        //orderItemList是根据商品名称查询出的list，取出orderItemList和page.getList()中相同的数据，就是当前条件下的数据
        if ("2".equals(searchCate) && StringUtils.isNotBlank(searchValue)) {
            for (TradeOrderItem item : orderItemList) {
                for (TradeReturnOrder returnOrder : page.getList()) {
                    if (item.getOrderItemId() == returnOrder.getOrderItemId()) {
                        resultList.add(returnOrder);
                    }
                }
            }
            page.setList(resultList);
        }
        TradeReturnOrder.fillUserMain(page.getList());
        TradeReturnOrder.fillTradeOrder(page.getList());
        TradeReturnOrder.fillTradeOrderItem(page.getList());
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchCate", searchCate);
        model.addAttribute("page", page);
        //菜单高亮
        String menu3 = "060203";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
        return "seller/trade/tradeReturnMoneyList";
    }

    /**
     * 进入处理页面
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:edit")
    @RequestMapping(value = "handle1")
    public String handle1(TradeReturnOrder tradeReturnOrder, Model model) {
        //入参检查
        if (tradeReturnOrder == null || tradeReturnOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("退款退货订单不存在！"));
            return "error/400";
        }
        //入参检查
        tradeReturnOrder.setStoreId(SsoUtils.getUserMain().getUserSeller().getStoreId());
        TradeReturnOrder entity = tradeReturnOrderService.selectOne(new Wrapper().and("store_id=", tradeReturnOrder.getStoreId()).and("return_order_id=", tradeReturnOrder.getReturnOrderId()));//属主检查
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("退款退货订单不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        LogisticsCompany logisticsCompany = logisticsCompanyService.selectById(entity.getLogisticsTemplateId());
        if (logisticsCompany != null) {
            KdniaoTrackQuery api = new KdniaoTrackQuery();
            //返回来的json格式物流信息
            String result = "";
            try {
                result = api.getOrderTracesByJson(logisticsCompany.getCompanyNumber(), entity.getTrackingNo());
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
            } catch (Exception e) {
                logger.error("物流查询出现错误：" + e);
            }
        }
        model.addAttribute("tradeReturnOrder", entity);
        if ("1".equals(R.get("type"))) {
            String menu3 = "060202";//请修改为正确的三级菜单编号
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            SellerMenuInterceptor.menuHighLighting(menu3);
            return "seller/trade/tradeReturnOrderDetail";
        } else {
            String menu3 = "060203";//请修改为正确的三级菜单编号
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            SellerMenuInterceptor.menuHighLighting(menu3);
            return "seller/trade/tradeReturnMoneyDetail";
        }
    }

    /**
     * 处理退款
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:edit")
    @RequestMapping(value = "handle2")
    public String handle2(TradeReturnOrder tradeReturnOrder, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (tradeReturnOrder == null || tradeReturnOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("退款退货订单不存在！"));
            return "error/400";
        }
        //表单验证
        List<String> errorList = validate(model);
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return handle1(tradeReturnOrder, model);//回显错误提示
        }
        //检查合格后，业务处理
        tradeReturnOrderService.authReturnOrder(tradeReturnOrder);
        addMessage(redirectAttributes, FYUtils.fyParams("退单处理成功"));
        //发送消息
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("returnOrderId", tradeReturnOrder.getReturnOrderId().toString());
        String messageTemplateNum = SiteSendMessagsService.MEMBER_RETURN_UPDATE;
        Long uId = tradeReturnOrder.getUId();
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        //类型，1退货退款、2退款
        if ("1".equals(tradeReturnOrder.getType())) {
            return "redirect:" + sellerPath + "/trade/tradeReturnOrder/tradeReturnOrderList.htm?repage";
        } else {
            return "redirect:" + sellerPath + "/trade/tradeReturnOrder/tradeReturnMoneyList.htm?repage";
        }
    }

    /**
     * 商家确认收货
     *
     * @param tradeReturnOrder         实体对象
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "goodsReceipt")
    public Map<String, Object> goodsReceipt(TradeReturnOrder tradeReturnOrder) {
        Map<String, Object> map = new HashMap<>();
        if (tradeReturnOrder == null || tradeReturnOrder.getReturnOrderId() == null) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("退单不能为空"));
            return map;
        }
        //用户id
        //商家是否收货，0否、1是
        tradeReturnOrder.setIsProductReceipt("1");
        tradeReturnOrder.setProductReceiptTime(new Date());
        // 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        tradeReturnOrder.setStatus("50");
        Map<String, Object> goodsReceiptMap = tradeReturnOrderService.goodsReceipt(tradeReturnOrder);
        if (!(Boolean) goodsReceiptMap.get("success")) {
            map.put("success", false);
            map.put("message", goodsReceiptMap.get("message") == null ? FYUtils.fyParams("收货失败") : goodsReceiptMap.get("message"));
            return map;
        }
        map.put("success", true);
        map.put("message", FYUtils.fyParams("收货成功"));
        return map;
    }

    /**
     * 账单详情页面中查询退单
     *
     * @param tradeReturnOrder 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:edit")
    @RequestMapping(value = {"search"})
    public String search(TradeReturnOrder tradeReturnOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        tradeReturnOrder.setStoreId(userSeller.getStoreId());
        Page<TradeReturnOrder> page = tradeReturnOrderService.selectByWhere(new Page<TradeReturnOrder>(request, response), new Wrapper(tradeReturnOrder));
        //1代表查询的订单列表，2代表查询退单的类型(1类型的查询在订单controller)
        model.addAttribute("type", "2");
        model.addAttribute("page", page);
        return "seller/settlement/settlementBillDetailModel";
    }

    /**
     * 表单验证
     *
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Model model) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(R.get("businessHandleRemarks"))) {
            errorList.add(FYUtils.fyParams("处理备注不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("businessHandleRemarks")) && R.get("businessHandleRemarks").length() > 1024) {
            errorList.add(FYUtils.fyParams("处理备注最大长度不能超过1024字符"));
        }
        return errorList;
    }

}