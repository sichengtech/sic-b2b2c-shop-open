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
package com.sicheng.member.trade.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.member.interceptor.MemberMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sicheng.admin.logistics.entity.LogisticsCompany;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.common.express.KdniaoTrackQuery;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.logistics.service.LogisticsCompanyService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.trade.service.TradeOrderItemService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.seller.trade.service.TradeReturnOrderService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * 退款、退货退款订单 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${memberPath}/trade/tradeReturnOrder")
public class TradeReturnOrderController extends BaseController {

    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private LogisticsCompanyService logisticsCompanyService;
    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private SiteSendMessagsService siteSendMessagsService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting( "returnOrder");//三级菜单高亮
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
    @RequiresPermissions("user")
    @RequestMapping(value = "tradeReturnOrderList")
    public String tradeReturnOrderList(TradeReturnOrder tradeReturnOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索
        String searchCate = R.get("searchCate");
        String searchValue = R.get("searchValue");
        List<TradeOrderItem> orderItemList = new ArrayList<>();
        //搜索条件：1代表商品名称，2代表订单编号
        if (StringUtils.isNotBlank(searchValue)) {
            if ("1".equals(searchCate) || "".equals(searchCate)) {
                //根据产品名称获取一个订单详情的list
                TradeOrderItem orderItem = new TradeOrderItem();
                orderItem.setName(searchValue);
                orderItemList = tradeOrderItemService.selectByWhere(new Wrapper(orderItem));
            } else if ("2".equals(searchCate)) {
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
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeReturnOrder.setUId(userMember.getUId());
        //1：退货退款，2退款
        //tradeReturnOrder.setType("1");
        Page<TradeReturnOrder> page = tradeReturnOrderService.selectByWhere(new Page<TradeReturnOrder>(request, response), new Wrapper(tradeReturnOrder));
        List<TradeReturnOrder> resultList = new ArrayList<>();
        //orderItemList是根据商品名称查询出的list，取出orderItemList和page.getList()中相同的数据，就是当前条件下的数据
        if (("1".equals(searchCate) || "".equals(searchCate)) && StringUtils.isNotBlank(searchValue)) {
            for (TradeOrderItem item : orderItemList) {
                for (TradeReturnOrder returnOrder : page.getList()) {
                    if (item.getOrderItemId().equals(returnOrder.getOrderItemId())) {
                        resultList.add(returnOrder);
                    }
                }
            }
            page.setList(resultList);
        }
        TradeReturnOrder.fillUserMain(page.getList());
        TradeReturnOrder.fillTradeOrder(page.getList());
        TradeReturnOrder.fillTradeOrderItem(page.getList());
        TradeReturnOrder.fillStore(page.getList());
        int returnMoneyCount = tradeReturnOrderService.countByWhere(new Wrapper().and("type =", 2).and("u_id=", userMember.getUId()));
        int returnOrderCount = tradeReturnOrderService.countByWhere(new Wrapper().and("type =", 1).and("u_id=", userMember.getUId()));
        model.addAttribute("returnMoneyCount", returnMoneyCount);
        model.addAttribute("returnOrderCount", returnOrderCount);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchCate", searchCate);
        model.addAttribute("page", page);
        model.addAttribute("type", tradeReturnOrder.getType());
        return "member/trade/tradeReturnOrderList";
    }

    /**
     * 进入退货退款页面
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(TradeReturnOrder tradeReturnOrder, TradeOrder tradeOrder, TradeOrderItem tradeOrderItem, Model model) {
        TradeReturnOrder entity = new TradeReturnOrder();
        TradeOrder tradeOrder2 = new TradeOrder();
        TradeOrderItem tradeOrderItem2 = new TradeOrderItem();
        //isCreatReturnOrder==1：申请退货退款，否则是查看退货退款
        if ("1".equals(R.get("isCreatReturnOrder"))) {
            //入参检查
            if ("".equals(R.get("orderId")) || "".equals(R.get("orderItemId"))) {
                model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
                return "error/400";
            }
            tradeOrder2 = tradeOrderService.selectById(tradeOrder.getOrderId());
            tradeOrderItem2 = tradeOrderItemService.selectById(tradeOrderItem.getOrderItemId());
            if (tradeOrder2 == null || tradeOrderItem2 == null) {
                model.addAttribute("message", FYUtils.fyParams("订单不存在！"));
                return "error/400";
            }
        } else {
            //stat:1表示从订单列表中查看退款退货
            String stat = R.get("stat");
            if ("1".equals(stat)) {
                List<TradeReturnOrder> list = tradeReturnOrderService.selectByWhere(new Wrapper().and("order_id=", R.get("orderId")).
                        and("order_item_id=", R.get("orderItemId")).and("type=", R.get("type")));
                if (list.size() > 0) {
                    tradeReturnOrder = list.get(0);
                }
            }
            //入参检查
            if (tradeReturnOrder == null || tradeReturnOrder.getId() == null) {
                model.addAttribute("message", FYUtils.fyParams("退款退货订单不存在！"));
                return "error/400";
            }
            tradeReturnOrder.setUId(SsoUtils.getUserMain().getUserMember().getUId());//属主检查
            entity = tradeReturnOrderService.selectOne(new Wrapper(tradeReturnOrder));
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
                    model.addAttribute("state", resultMap.get("State"));

                } catch (Exception e) {
                    logger.error("物流查询出现错误：" + e);
                }
            }
            tradeOrder2 = entity.getTradeOrder();
            tradeOrderItem2 = entity.getTradeOrderItem();
            if (tradeOrder2 == null || tradeOrderItem2 == null) {
                model.addAttribute("message", FYUtils.fyParams("退款退货订单不存在！"));
                return "error/400";
            }
        }
        //退货信息
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
        model.addAttribute("tradeOrder", tradeOrder2);
        model.addAttribute("tradeOrderItem", tradeOrderItem2);
        model.addAttribute("tradeReturnOrder", entity);
        model.addAttribute("type", R.get("type"));
        model.addAttribute("logisticsCompanyList", logisticsCompanyService.selectByWhere(new Wrapper().and("status=", "1")));
        if ("1".equals(R.get("type"))) {
            return "member/trade/tradeReturnOrderDetail";
        } else {
            return "member/trade/tradeReturnMoneyDetail";
        }
    }

    /**
     * 申请退货退款、退款
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(TradeReturnOrder tradeReturnOrder, TradeOrder tradeOrder, TradeOrderItem tradeOrderItem, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeReturnOrder, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeReturnOrder, tradeOrder, tradeOrderItem, model);//回显错误提示
        }
        //业务处理
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeReturnOrder.setUId(userMember.getUId());
        String img1 = R.get("img1");
        String img2 = R.get("img2");
        String img3 = R.get("img3");
        List<String> imgList = new ArrayList<>();
        if (StringUtils.isNotBlank(img1)) {
            imgList.add(img1);
        }
        if (StringUtils.isNotBlank(img2)) {
            imgList.add(img2);
        }
        if (StringUtils.isNotBlank(img3)) {
            imgList.add(img3);
        }
        tradeReturnOrderService.applyReturnOrder(tradeReturnOrder, tradeOrder, tradeOrderItem, imgList);
        addMessage(redirectAttributes, FYUtils.fyParams("申请成功！"));
        //发送消息
        TradeReturnOrder returnOrder = tradeReturnOrderService.selectById(tradeReturnOrder.getReturnOrderId());
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("returnOrderId", returnOrder.getReturnOrderId().toString());
        String messageTemplateNum = SiteSendMessagsService.STORE_RETURN;
        Long uId = returnOrder.getStore().getUserMain().getUId();
        if (returnOrder.getStore() != null && returnOrder.getStore().getUserMain() != null) {
            siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        }
        return "redirect:" + memberPath + "/trade/tradeReturnOrder/tradeReturnOrderList.htm?repage";
    }

    /**
     * 买家发货
     *
     * @param tradeOrder         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "deliverGoods")
    public String deliverGoods(TradeReturnOrder tradeReturnOrder, RedirectAttributes redirectAttributes) {
        if (tradeReturnOrder != null && tradeReturnOrder.getReturnOrderId() != null) {
            UserMember userMember = SsoUtils.getUserMain().getUserMember();
            //30买家退货给商家
            tradeReturnOrder.setStatus("40");
            tradeReturnOrder.setDeliverProductTime(new Date());
            tradeReturnOrderService.updateByWhereSelective(tradeReturnOrder, new Wrapper().and("return_order_id=", tradeReturnOrder.getReturnOrderId()).and("u_id=", userMember.getUId()));
        }
        return "redirect:" + memberPath + "/trade/tradeReturnOrder/tradeReturnOrderList.htm?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeReturnOrder tradeReturnOrder, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("orderId"))) {
            errorList.add(FYUtils.fyParams("订单编号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("orderId")) && R.get("orderId").length() > 19) {
            errorList.add(FYUtils.fyParams("订单编号最大长度不能超过"+"19" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isBlank(R.get("orderItemId"))) {
            errorList.add(FYUtils.fyParams("订单详情编号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("orderItemId")) && R.get("orderItemId").length() > 19) {
            errorList.add(FYUtils.fyParams("订单详情编号最大长度不能超过"+"19" + FYUtils.fyParams("字符")));
        }

        if (StringUtils.isBlank(R.get("returnReason"))) {
            errorList.add(FYUtils.fyParams("退货原因不能为空"));
        }
        if (StringUtils.isBlank(R.get("returnMoney"))) {
            errorList.add(FYUtils.fyParams("退款金额不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("returnMoney")) && R.get("returnMoney").length() > 12) {
            errorList.add(FYUtils.fyParams("退款金额最大长度不能超过12字符"));
        }
        if (StringUtils.isBlank(R.get("returnExplain"))) {
            errorList.add(FYUtils.fyParams("退货说明不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("returnExplain")) && R.get("returnExplain").length() > 1024) {
            errorList.add(FYUtils.fyParams("退货说明不能为空最大长度不能超过"+"1024" + FYUtils.fyParams("字符")));
        }
        if ("1".equals(tradeReturnOrder)) {
            if (StringUtils.isBlank(R.get("returnCount"))) {
                errorList.add(FYUtils.fyParams("退款数量不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("returnCount")) && R.get("returnCount").length() > 10) {
                errorList.add(FYUtils.fyParams("退款数量最大长度不能超过10字符"));
            }
        }
        if (StringUtils.isNotBlank(R.get("oldReturnMoney")) && StringUtils.isNotBlank(R.get("returnMoney"))) {
            Double returnMoney = Double.parseDouble(R.get("returnMoney"));
            Double oldReturnMoney = Double.parseDouble(R.get("oldReturnMoney"));
            if (returnMoney > oldReturnMoney) {
                errorList.add(FYUtils.fyParams("退款金额不能超过可退金额。"));
            }
        }
        return errorList;
    }

}