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
package com.sicheng.seller.settlement.web;

import com.sicheng.admin.account.entity.SettlementBill;
import com.sicheng.admin.account.entity.SettlementBillDetail;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.settlement.service.SettlementBillDetailService;
import com.sicheng.seller.settlement.service.SettlementBillService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.seller.trade.service.TradeReturnOrderService;
import com.sicheng.sso.utils.SsoUtils;
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

/**
 * 账单 Controller
 * 所属模块：settlement
 *
 * @author fxx
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${sellerPath}/settlement/settlementBill")
public class SettlementBillController extends BaseController {

    @Autowired
    private SettlementBillService settlementBillService;
    @Autowired
    private SettlementBillDetailService settlementBillDetailService;

    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        String menu3 = "060103";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param settlementBill 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:view")
    @RequestMapping(value = "list")
    public String list(SettlementBill settlementBill, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        settlementBill.setStoreId(userSeller.getStoreId());
        Page<SettlementBill> page = settlementBillService.selectByWhere(new Page<SettlementBill>(request, response), new Wrapper(settlementBill));
        SettlementBill.fillStore(page.getList());
        model.addAttribute("page", page);
        return "seller/settlement/settlementBillList";
    }

    /**
     * 确认账单
     *
     * @param settlementBill     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:edit")
    @RequestMapping(value = "confirmationBill")
    public String confirmationBill(SettlementBill settlementBill, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (settlementBill == null || settlementBill.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("账单不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        settlementBill.setStatus("20");//账单状态，10账单已生成，等待商家确认、20商家已确认，等待平台审核、30平台已审核，等待平台付款、40平台已付款，平台结算完成
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        SettlementBill condition = new SettlementBill();
        condition.setId(settlementBill.getId());//属主检查
        condition.setStoreId(userSeller.getStoreId());//属主检查
        settlementBillService.updateByWhereSelective(settlementBill, new Wrapper(condition));
        addMessage(redirectAttributes, FYUtils.fyParams("确认成功"));
        return "redirect:" + sellerPath + "/settlement/settlementBill/list.htm?repage";
    }

    /**
     * 进入详情页面
     *
     * @param settlementBill 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:edit")
    @RequestMapping(value = "detail")
    public String detail(SettlementBill settlementBill, Model model) {
        if (settlementBill == null || settlementBill.getId() == null) {
            return "seller/settlement/settlementBillDetail";
        }
        SettlementBill entity = settlementBillService.selectById(settlementBill.getId());
        model.addAttribute("settlementBill", entity);
        return "seller/settlement/settlementBillDetail";
    }

    /**
     * 账单详情页面中查询订单
     *
     * @param settlementBillDetail 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeOrder:view")
    @RequestMapping(value = {"detailOrder"})
    public String search(SettlementBillDetail settlementBillDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        String pageNo = R.get("pageNo");
        if (StringUtils.isBlank(pageNo)) {
            pageNo = "1";
        }
        if (settlementBillDetail == null || settlementBillDetail.getBillId() == null) {
            return "seller/settlement/settlementBillDetailModel";
        }
        String type = settlementBillDetail.getType();
        if (StringUtils.isBlank(type)) {
            type = "1";
        }
        Page<SettlementBillDetail> settlementBillDetailPage = settlementBillDetailService.selectByWhere(new Page<SettlementBillDetail>(request, response), new Wrapper().and("bill_id=", settlementBillDetail.getBillId()).and("type=", type));
        List<Long> orderIdList = new ArrayList<>();
        if (!settlementBillDetailPage.getList().isEmpty()) {
            for (SettlementBillDetail detail : settlementBillDetailPage.getList()) {
                orderIdList.add(detail.getOrderId());
            }
        }

        //1代表查询的订单列表，2代表查询退单的类型
        List<Long> orderIds = new ArrayList<>();
        if ("1".equals(type)) {
            Page<TradeOrder> tradeOrderPage = new Page<>(request, response);
            tradeOrderPage.setPageNo(Integer.parseInt(pageNo));
            List<TradeOrder> tradeOrderList = tradeOrderService.selectByIdIn(orderIdList);
            if (!tradeOrderList.isEmpty()) {
                for (int i = 0; i < tradeOrderList.size(); i++) {
                    orderIds.add(tradeOrderList.get(i).getOrderId());
                }
                tradeOrderPage = tradeOrderService.selectByWhere(tradeOrderPage, new Wrapper().and("order_id in", orderIds));
            }
            model.addAttribute("page", tradeOrderPage);
        } else {
            Page<TradeReturnOrder> tradeReturnOrderPage = new Page<>();
            tradeReturnOrderPage.setPageNo(Integer.parseInt(pageNo));
            List<TradeReturnOrder> tradeReturnOrderList = tradeReturnOrderService.selectByIdIn(orderIdList);
            if (!tradeReturnOrderList.isEmpty()) {
                for (int i = 0; i < tradeReturnOrderList.size(); i++) {
                    orderIds.add(tradeReturnOrderList.get(i).getOrderId());
                }
                tradeReturnOrderPage = tradeReturnOrderService.selectByWhere(tradeReturnOrderPage, new Wrapper().and("order_id in", orderIds));
            }
            model.addAttribute("page", tradeReturnOrderPage);
        }
        model.addAttribute("type", type);
        return "seller/settlement/settlementBillDetailModel";
    }

    /**
     * 进入账单打印页面
     *
     * @param settlementBill 实体对象
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:edit")
    @RequestMapping(value = "print")
    public String point(SettlementBill settlementBill, Model model) {
        SettlementBill entity = null;
        if (settlementBill != null && settlementBill.getId() != null) {
            entity = settlementBillService.selectById(settlementBill.getId());
        }
        model.addAttribute("settlementBill", entity);
        return "seller/settlement/settlementBillPoint";
    }
}