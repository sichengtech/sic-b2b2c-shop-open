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


import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.admin.trade.service.TradeReturnOrderService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.pay.ShopPay;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 退款、退货退款订单 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeReturnOrder")
public class TradeReturnOrderController extends BaseController {

    @Autowired
    @Qualifier("weixinPay")
    ShopPay weixinPay;

    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String type = R.get("type");
        String menu3 = "";//请修改为正确的三级菜单编号
        if ("1".equals(type)) {
            menu3 = "030104";
        } else if ("2".equals(type)) {
            menu3 = "030103";
        }
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        model.addAttribute("type", type);
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
    @RequestMapping(value = "list")
    public String list(TradeReturnOrder tradeReturnOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeReturnOrder> page = tradeReturnOrderService.selectByWhere(new Page<TradeReturnOrder>(request, response), new Wrapper(tradeReturnOrder));
        TradeReturnOrder.fillUserMain(page.getList());
        TradeReturnOrder.fillTradeOrder(page.getList());
        TradeReturnOrder.fillTradeOrderItem(page.getList());
        model.addAttribute("page", page);
        return "admin/trade/tradeReturnOrderList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:save")
    @RequestMapping(value = "save1")
    public String save1(TradeReturnOrder tradeReturnOrder, Model model) {
        if (tradeReturnOrder == null) {
            tradeReturnOrder = new TradeReturnOrder();
        }
        model.addAttribute("tradeReturnOrder", tradeReturnOrder);
        return "admin/trade/tradeReturnOrderForm";
    }

    /**
     * 执行保存
     *
     * @param tradeReturnOrder   实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:save")
    @RequestMapping(value = "save2")
    public String save2(TradeReturnOrder tradeReturnOrder, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeReturnOrder, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeReturnOrder, model);//回显错误提示
        }

        //业务处理
        tradeReturnOrderService.insertSelective(tradeReturnOrder);
        addMessage(redirectAttributes, "保存退款、退货退款订单成功");
        return "redirect:" + adminPath + "/trade/tradeReturnOrder/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeReturnOrder tradeReturnOrder, Model model) {
        TradeReturnOrder entity = null;
        if (tradeReturnOrder != null) {
            if (tradeReturnOrder.getId() != null) {
                entity = tradeReturnOrderService.selectById(tradeReturnOrder.getId());
            }
        }
        model.addAttribute("tradeReturnOrder", entity);
        return "admin/trade/tradeReturnOrderForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeReturnOrder   实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeReturnOrder tradeReturnOrder, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeReturnOrder, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeReturnOrder, model);//回显错误提示
        }

        //业务处理
        tradeReturnOrderService.updateByIdSelective(tradeReturnOrder);
        addMessage(redirectAttributes, "编辑退款、退货退款订单成功");
        return "redirect:" + adminPath + "/trade/tradeReturnOrder/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeReturnOrder   实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:drop")
    @RequestMapping(value = "delete")
    public String delete(TradeReturnOrder tradeReturnOrder, RedirectAttributes redirectAttributes) {
        tradeReturnOrderService.deleteById(tradeReturnOrder.getId());
        addMessage(redirectAttributes, "删除退款、退货退款订单成功");
        return "redirect:" + adminPath + "/trade/tradeReturnOrder/list.do?repage";
    }

    /**
     * 进入处理页面
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:auth")
    @RequestMapping(value = "handle1")
    public String handle1(TradeReturnOrder tradeReturnOrder, Model model) {
        TradeReturnOrder entity = null;
        if (tradeReturnOrder != null) {
            if (tradeReturnOrder.getId() != null) {
                entity = tradeReturnOrderService.selectById(tradeReturnOrder.getId());
            }
        }
        String stat = R.get("stat");//1代表处理，2代表查看
        if (StringUtils.isNotBlank(stat)) {
            model.addAttribute("stat", stat);
        }
        model.addAttribute("tradeReturnOrder", entity);
        return "admin/trade/tradeReturnOrderDetail";
    }

    /**
     * 处理退款
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeReturnOrder:auth")
    @RequestMapping(value = "handle2")
    public String handle2(TradeReturnOrder tradeReturnOrder, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeReturnOrder, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return handle1(tradeReturnOrder, model);//回显错误提示
        }
        //业务处理
        String attr = R.get("buttonState");
        String mess = tradeReturnOrderService.authTradeReturnOrder(tradeReturnOrder, attr);
        addMessage(redirectAttributes, FYUtils.fyParams(mess));
        return "redirect:" + adminPath + "/trade/tradeReturnOrder/list.do?repage";
    }

    /**
     * 账单详情页面中查询退单
     * @param tradeOrder 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
//	@RequiresPermissions("trade:tradeOrder:view")
//	@RequestMapping(value = {"search"})
//	public String search(TradeReturnOrder tradeReturnOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<TradeReturnOrder> page = tradeReturnOrderService.selectByWhere(new Page<TradeReturnOrder>(request, response), new Wrapper(tradeReturnOrder)); 
//		//1代表查询的订单列表，2代表查询退单的类型(1类型的查询在订单controller)
//		model.addAttribute("type","2");
//		model.addAttribute("page", page);
//		return "admin/settlement/settlementBillDetailModel";
//	}

    /**
     * 表单验证
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeReturnOrder tradeReturnOrder, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("systemHandleRemarks"))) {
            errorList.add(FYUtils.fy("平台处理备注不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("systemHandleRemarks")) && R.get("systemHandleRemarks").length() > 1024) {
            errorList.add(FYUtils.fy("平台处理备注最大长度不能超过1024字符"));
        }
        return errorList;
    }

}