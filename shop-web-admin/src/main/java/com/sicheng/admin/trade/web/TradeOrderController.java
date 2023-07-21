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
package com.sicheng.admin.trade.web;

import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.settlement.service.SettlementPayWayService;

import com.sicheng.admin.task.service.TaskListService;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.service.TradeOrderService;
import com.sicheng.admin.trade.utils.TradeOrderUtils;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeOrder")
public class TradeOrderController extends BaseController {

    @Autowired
    private TradeOrderService tradeOrderService;



    @Autowired
    private SettlementPayWayService settlementPayWayService;

    @Autowired
    private TaskListService taskListService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param tradeOrder 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:view")
    @RequestMapping(value = "list")
    public String list(TradeOrder tradeOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeOrder> page = tradeOrderService.selectByWhere(new Page<TradeOrder>(request, response), new Wrapper(tradeOrder));
        SettlementPayWay settlementPayWay = new SettlementPayWay();
        settlementPayWay.setStatus("1");
        List<SettlementPayWay> payWays = settlementPayWayService.selectAll(new Wrapper(settlementPayWay));
        model.addAttribute("payWays", payWays);
        model.addAttribute("page", page);
        return "admin/trade/tradeOrderList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:save")
    @RequestMapping(value = "save1")
    public String save1(TradeOrder tradeOrder, Model model) {
        if (tradeOrder == null) {
            tradeOrder = new TradeOrder();
        }
        model.addAttribute("tradeOrder", tradeOrder);
        return "admin/trade/tradeOrderForm";
    }

    /**
     * 执行保存
     *
     * @param tradeOrder         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:save")
    @RequestMapping(value = "save2")
    public String save2(TradeOrder tradeOrder, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeOrder, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeOrder, model);//回显错误提示
        }
        //订单的编号采用模式1
        tradeOrder.setPkMode(1);
        //创建订单编号
        tradeOrder.setId(TradeOrderUtils.creatOrderNumber("1"));
        tradeOrderService.insertSelective(tradeOrder);
        addMessage(redirectAttributes, "保存订单成功");
        return "redirect:" + adminPath + "/trade/tradeOrder/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeOrder tradeOrder, Model model) {
        TradeOrder entity = null;
        if (tradeOrder != null) {
            if (tradeOrder.getId() != null) {
                entity = tradeOrderService.selectById(tradeOrder.getId());
            }
        }
        model.addAttribute("tradeOrder", entity);
        return "admin/trade/tradeOrderForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeOrder         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeOrder tradeOrder, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeOrder, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeOrder, model);//回显错误提示
        }

        //业务处理
        tradeOrderService.updateByIdSelective(tradeOrder);
        addMessage(redirectAttributes, "编辑订单成功");
        return "redirect:" + adminPath + "/trade/tradeOrder/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeOrder         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:drop")
    @RequestMapping(value = "delete")
    public String delete(TradeOrder tradeOrder, RedirectAttributes redirectAttributes) {
        tradeOrderService.deleteById(tradeOrder.getId());
        addMessage(redirectAttributes, "删除订单成功");
        return "redirect:" + adminPath + "/trade/tradeOrder/list.do?repage";
    }

    /**
     * 取消订单
     *
     * @param tradeOrder         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "cancelOrder")
    public String cancelOrder(TradeOrder tradeOrder, RedirectAttributes redirectAttributes) {
        if (tradeOrder != null && tradeOrder.getId() != null) {
            tradeOrder = tradeOrderService.selectById(tradeOrder.getId());
            if (tradeOrder.getPaymentMethodId() == null) {
                tradeOrder.setOrderStatus("60");//订单状态，10待付款、20待发货、30待收货、40已收货待评价、50已评价(已完成)、60已取消
                tradeOrderService.cancelOrder(tradeOrder);
                addMessage(redirectAttributes, FYUtils.fy("取消订单成功"));
            } else {
                //选择了支付方式,查看第三方是否支付
                Map<String, Object> map = tradeOrderService.queryAndCancelOrder(tradeOrder);
                if (!map.isEmpty()) {
                    addMessage(redirectAttributes, map.get("message").toString());
                }
            }
        }
        return "redirect:" + adminPath + "/trade/tradeOrder/list.do?repage";
    }

    /**
     * 进入收到货款页
     *
     * @param tradeOrder 实体对象
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "receivePayment1")
    public String receivePayment1(TradeOrder tradeOrder, Model model) {
        TradeOrder entity = null;
        if (tradeOrder != null) {
            if (tradeOrder.getId() != null) {
                entity = tradeOrderService.selectById(tradeOrder.getId());
            }
        }
        SettlementPayWay settlementPayWay = new SettlementPayWay();
        settlementPayWay.setStatus("1");
        List<SettlementPayWay> payWays = settlementPayWayService.selectAll(new Wrapper(settlementPayWay));
        model.addAttribute("payWays", payWays);
        model.addAttribute("tradeOrder", entity);
        return "admin/trade/tradeOrderEdit";
    }

    /**
     * 确认收到货款
     *
     * @param tradeOrder         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:edit")
    @RequestMapping(value = "receivePayment2")
    public String receivePayment2(TradeOrder tradeOrder, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeOrder, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return receivePayment1(tradeOrder, model);//回显错误提示
        }
        if (tradeOrder != null && tradeOrder.getId() != null) {
            //订单置为已付款状态
        	tradeOrder = tradeOrderService.selectById(tradeOrder.getId());
            tradeOrder.setOrderStatus("20");
            if(tradeOrder.getOffsetAmount()==null){
            	tradeOrder.setOnlinePayMoney(tradeOrder.getAmountPaid());
            }else{
            	tradeOrder.setOnlinePayMoney(tradeOrder.getOffsetAmount());
            }
            if (tradeOrder.getPaymentMethodId() != null) {
                if (tradeOrder.getSettlementPayWay() != null) {
                    tradeOrder.setPaymentMethodName(tradeOrder.getSettlementPayWay().getName());
                }
            }
            tradeOrderService.updateByIdSelective(tradeOrder);
        }
        addMessage(redirectAttributes, FYUtils.fy("收到货款成功"));
        return "redirect:" + adminPath + "/trade/tradeOrder/list.do?repage";
    }

    /**
     * 进入订单详情页
     *
     * @param tradeOrder 实体对象
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:view")
    @RequestMapping(value = "tradeOrderDetail")
    public String tradeOrderDetail(TradeOrder tradeOrder, Model model) {
        TradeOrder entity = null;
        if (tradeOrder != null) {
            if (tradeOrder.getId() != null) {
                entity = tradeOrderService.selectById(tradeOrder.getId());
                //TradeOrderItem.fillStoreAlbumPicture(entity.getTradeOrderItemList());
            }
        }
        model.addAttribute("tradeOrder", entity);
        return "admin/trade/tradeOrderDetail";
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
        String pageNo = R.get("pageNo");
        if (StringUtils.isBlank(pageNo)) {
            pageNo = "1";
        }
        Page<TradeOrder> page = new Page<TradeOrder>(request, response);
        page.setPageNo(Integer.parseInt(pageNo));
        page = tradeOrderService.selectByWhere(page, new Wrapper(tradeOrder));
        //1代表查询的订单列表，2代表查询退单的类型(2类型的查询在退单controller)
        model.addAttribute("type", "1");
        model.addAttribute("page", page);
        return "admin/settlement/settlementBillDetailModel";
    }

    /**
     * 表单验证
     *
     * @param tradeOrder 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeOrder tradeOrder, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("payOrderTime"))) {
            errorList.add(FYUtils.fy("付款时间不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("thirdPayOrderNumber")) && R.get("thirdPayOrderNumber").length() > 64) {
            errorList.add(FYUtils.fy("第三方付款平台交易号最大长度不能超过64字符"));
        }
        return errorList;
    }

}