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
package com.sicheng.admin.settlement.web;

import com.sicheng.admin.account.entity.SettlementBill;
import com.sicheng.admin.account.entity.SettlementBillDetail;
import com.sicheng.admin.account.service.SettlementBillDetailService;
import com.sicheng.admin.account.service.SettlementBillService;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.service.StoreService;

import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.admin.trade.service.TradeOrderService;
import com.sicheng.admin.trade.service.TradeReturnOrderService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 账单 Controller
 * 所属模块：settlement
 *
 * @author 范秀秀
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementBill")
public class SettlementBillController extends BaseController {

    @Autowired
    private SettlementBillService settlementBillService;
    @Autowired
    private SettlementBillDetailService settlementBillDetailService;

    @Autowired
    private StoreService storeService;
    @Autowired
    private TradeOrderService tradeOrderService;
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
        String menu3 = "030202";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
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
        //接收店铺名
        String storeName = R.get("storeName");
        Long storeId = 0L;
        if (!(storeName == null || "".equals(storeName))) {
            //店铺名转小写
            storeName = storeName.toLowerCase();
            //用店铺名去换取id
            Store store = new Store();
            store.setName(storeName);
            List<Store> stores = storeService.selectByWhere(new Wrapper(store));
            if (stores.size() > 0) {
                storeId = stores.get(0).getStoreId();
                settlementBill.setStoreId(storeId);
            } else {
                model.addAttribute("storeName", storeName);
                return "admin/settlement/settlementBillList";
            }
        }
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(settlementBill);
        wrapper.orderBy("a.begin_time DESC");//按任务开始时间排倒序
        Page<SettlementBill> page = settlementBillService.selectByWhere(new Page<SettlementBill>(request, response), wrapper);
        SettlementBill.fillStore(page.getList());
        model.addAttribute("storeName", storeName);
        model.addAttribute("page", page);
        return "admin/settlement/settlementBillList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementBill 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:save")
    @RequestMapping(value = "save1")
    public String save1(SettlementBill settlementBill, Model model) {
        if (settlementBill == null) {
            settlementBill = new SettlementBill();
        }
        model.addAttribute("settlementBill", settlementBill);
        return "admin/settlement/settlementBillForm";
    }

    /**
     * 执行保存
     *
     * @param settlementBill     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:save")
    @RequestMapping(value = "save2")
    public String save2(SettlementBill settlementBill, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementBill, model);
        if (errorList.size() > 0) {
            errorList.add(0,FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(settlementBill, model);//回显错误提示
        }

        //业务处理
        settlementBillService.insertSelective(settlementBill);
        addMessage(redirectAttributes, FYUtils.fyParams("保存账单成功"));
        return "redirect:" + adminPath + "/settlement/settlementBill/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param settlementBill 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SettlementBill settlementBill, Model model) {
        SettlementBill entity = null;
        if (settlementBill != null) {
            if (settlementBill.getId() != null) {
                entity = settlementBillService.selectById(settlementBill.getId());
            }
        }
        model.addAttribute("settlementBill", entity);
        return "admin/settlement/settlementBillForm";
    }

    /**
     * 执行编辑
     *
     * @param settlementBill     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SettlementBill settlementBill, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementBill, model);
        if (errorList.size() > 0) {
            errorList.add(0,FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(settlementBill, model);//回显错误提示
        }

        //业务处理
        settlementBillService.updateByIdSelective(settlementBill);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑账单成功"));
        return "redirect:" + adminPath + "/settlement/settlementBill/list.do?repage";
    }

    /**
     * 删除
     *
     * @param settlementBill     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:drop")
    @RequestMapping(value = "delete")
    public String delete(SettlementBill settlementBill, RedirectAttributes redirectAttributes) {
        settlementBillService.deleteById(settlementBill.getId());
        addMessage(redirectAttributes,FYUtils.fyParams("删除账单成功"));
        return "redirect:" + adminPath + "/settlement/settlementBill/list.do?repage";
    }

    /**
     * 支付(返回当前点击的实体)
     *
     * @param settlementBill 实体对象
     * @param model
     * @return
     */
    @ResponseBody
    @RequiresPermissions("settlement:settlementBill:edit")
    @RequestMapping(value = "pay1")
    public SettlementBill pay1(SettlementBill settlementBill, Model model) {
        SettlementBill entity = null;
        if (settlementBill != null) {
            if (settlementBill.getId() != null) {
                entity = settlementBillService.selectById(settlementBill.getId());
            }
        }
        return entity;
    }

    /**
     * 审核
     *
     * @param settlementBill     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:auth")
    @RequestMapping(value = "audit")
    public String audit(SettlementBill settlementBill, Model model, RedirectAttributes redirectAttributes) {
        if (settlementBill != null) {
            //账单状态，10账单已生成，等待商家确认、20商家已确认，等待平台审核、30平台已审核，等待平台付款、40平台已付款，平台结算完成
            settlementBill.setStatus("30");
        }
        //业务处理
        settlementBillService.updateByIdSelective(settlementBill);
        addMessage(redirectAttributes, FYUtils.fyParams("审核成功"));
        return "redirect:" + adminPath + "/settlement/settlementBill/list.do?repage";
    }

    /**
     * 执行支付
     *
     * @param settlementBill     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:edit")
    @RequestMapping(value = "pay2")
    public String pay2(SettlementBill settlementBill, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementBill, model);
        if (errorList.size() > 0) {
            errorList.add(0,FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return list(settlementBill, R.getRequest(), R.getResponse(), model);//回显错误提示
        }
        //业务处理
        //账单状态，10账单已生成，等待商家确认、20商家已确认，等待平台审核、30平台已审核，等待平台付款、40平台已付款，平台结算完成
        settlementBill.setStatus("40");
        settlementBillService.updateByIdSelective(settlementBill);
        addMessage(redirectAttributes, FYUtils.fyParams("支付成功"));
        return "redirect:" + adminPath + "/settlement/settlementBill/list.do?repage";
    }

    /**
     * 进入详情页面
     *
     * @param settlementBill 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:view")
    @RequestMapping(value = "detail")
    public String detail(SettlementBill settlementBill, Model model, HttpServletRequest request, HttpServletResponse response) {
        SettlementBill entity = null;
        if (settlementBill != null) {
            if (settlementBill.getId() != null) {
                entity = settlementBillService.selectById(settlementBill.getId());
            }
        }
        model.addAttribute("settlementBill", entity);
        return "admin/settlement/settlementBillDetail";
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
            return "admin/settlement/settlementBillDetailModel";
        }
        String orderId = R.get("orderId");
        if (StringUtils.isNoneBlank(orderId) && !StringUtils.isNumeric(orderId)) {
            return "admin/settlement/settlementBillDetailModel";
        }
        String type = settlementBillDetail.getType();
        if (StringUtils.isBlank(type)) {
            type = "1";
        }
        Page<SettlementBillDetail> settlementBillDetailPage = settlementBillDetailService.selectByWhere(new Page<SettlementBillDetail>(request, response), new Wrapper().and("bill_id=", settlementBillDetail.getBillId()).and("type=", type));
        List<Long> orderIdList = new ArrayList<>();
        if (settlementBillDetailPage.getList().size() > 0) {
            for (SettlementBillDetail detail : settlementBillDetailPage.getList()) {
                orderIdList.add(detail.getOrderId());
            }
        }
        //1代表查询的订单列表，2代表查询退单的类型
        List<Long> orderIds = new ArrayList<Long>();
        if ("1".equals(type)) {
            Page<TradeOrder> tradeOrderPage = new Page<TradeOrder>(request, response);
            tradeOrderPage.setPageNo(Integer.parseInt(pageNo));
            if (StringUtils.isNotBlank(orderId)) {
                orderIds.add(Long.parseLong(orderId));
                tradeOrderPage = tradeOrderService.selectByWhere(tradeOrderPage, new Wrapper().and("order_id in", orderIds));
                model.addAttribute("page", tradeOrderPage);
                model.addAttribute("type", type);
                return "admin/settlement/settlementBillDetailModel";
            }
            List<TradeOrder> tradeOrderList = tradeOrderService.selectByIdIn(orderIdList);
            if (tradeOrderList.isEmpty()) {
                model.addAttribute("type", type);
                return "admin/settlement/settlementBillDetailModel";
            }
            for (int i = 0; i < tradeOrderList.size(); i++) {
                orderIds.add(tradeOrderList.get(i).getOrderId());
            }
            tradeOrderPage = tradeOrderService.selectByWhere(tradeOrderPage, new Wrapper().and("order_id in", orderIds));
            model.addAttribute("page", tradeOrderPage);
            model.addAttribute("type", type);
            return "admin/settlement/settlementBillDetailModel";
        }
        Page<TradeReturnOrder> tradeReturnOrderPage = new Page<TradeReturnOrder>();
        tradeReturnOrderPage.setPageNo(Integer.parseInt(pageNo));
        List<TradeReturnOrder> tradeReturnOrderList = tradeReturnOrderService.selectByIdIn(orderIdList);
        if (tradeReturnOrderList.isEmpty()) {
            model.addAttribute("type", type);
            return "admin/settlement/settlementBillDetailModel";
        }
        for (int i = 0; i < tradeReturnOrderList.size(); i++) {
            orderIds.add(tradeReturnOrderList.get(i).getOrderId());
        }
        tradeReturnOrderPage = tradeReturnOrderService.selectByWhere(tradeReturnOrderPage, new Wrapper().and("order_id in", orderIds));
        model.addAttribute("page", tradeReturnOrderPage);
        model.addAttribute("type", type);
        return "admin/settlement/settlementBillDetailModel";
    }

    /**
     * 重算
     *
     * @param settlementBill 实体对象
     * @param model
     * @return
     */
    @ResponseBody
    @RequiresPermissions("settlement:settlementBill:edit")
    @RequestMapping(value = "retry")
    public String retry(SettlementBill settlementBill, Model model, HttpServletRequest request, HttpServletResponse response) {
        String stat = "0";
        if (settlementBill == null || settlementBill.getBillId() == null) {
            return stat;
        }
        SettlementBill bill = settlementBillService.selectById(settlementBill.getBillId());
        if (bill == null || bill.getBeginTime() == null || bill.getEndTime() == null) {
            return stat;
        }
        stat = "1";
        settlementBillService.retryProduct(bill);
		/*SettlementBill entity = null;
		if(settlementBill!=null){
			if (settlementBill.getId()!=null){
				entity = settlementBillService.selectById(settlementBill.getId());
				TradeOrder order=new TradeOrder();
				order.setBeginPlaceOrderTime(entity.getBeginTime());
				order.setEndPlaceOrderTime(entity.getEndTime());
				List<TradeOrder> orderList=tradeOrderService.selectByWhere(new Wrapper(order));
				TradeReturnOrder returnOrder=new TradeReturnOrder();
				returnOrder.setBeginSystemAgreeTime(entity.getBeginTime());
				returnOrder.setEndBusinessHandleDate(entity.getEndTime());
				List<TradeReturnOrder> returnOrderList=tradeReturnOrderService.selectByWhere(new Wrapper(returnOrder));
				//Double billAmount = 0d;
				BigDecimal billAmount=new BigDecimal("0");
				if(orderList.size()>0){
					for(TradeOrder tradeOrder:orderList){
						if(tradeOrder.getAmountPaid()!=null){
							//billAmount+=tradeOrder.getAmountPaid();
							billAmount=billAmount.add(tradeOrder.getAmountPaid());
						}
						if(tradeOrder.getFee()!=null){
							//billAmount-=tradeOrder.getFee();
							billAmount=billAmount.subtract(tradeOrder.getFee());
						}
					}
				}
				if(returnOrderList.size()>0){
					for(TradeReturnOrder tradeReturnOrder:returnOrderList){
						if(tradeReturnOrder.getReturnMoney()!=null){
							//billAmount-=tradeReturnOrder.getReturnMoney();
							billAmount=billAmount.subtract(tradeReturnOrder.getReturnMoney());
						}
						if(tradeReturnOrder.getReturnCommission()!=null){
							//billAmount+=tradeReturnOrder.getReturnCommission();
							billAmount=billAmount.add(tradeReturnOrder.getReturnCommission());
						}
					}
				}
				//保留两位小数
				new DecimalFormat("#.00").format(billAmount);
				settlementBill.setBillAmount(billAmount);
				settlementBillService.updateByIdSelective(settlementBill);
			}
		}*/
        return stat;
    }

    /**
     * 进入结算任务页面
     *
     * @param settlementBill 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementBill:edit")
    @RequestMapping(value = "settlementBillTask")
    public String settlementBillTask(SettlementBill settlementBill, Model model, HttpServletRequest request, HttpServletResponse response) {
        return "admin/settlement/settlementBillTask";
    }

    /**
     * 表单验证
     *
     * @param settlementBill 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SettlementBill settlementBill, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("billId"))) {
            errorList.add(FYUtils.fyParams("请选择支付账单"));
        }
        if (StringUtils.isBlank(R.get("payDate"))) {
            errorList.add(FYUtils.fyParams("付款时间不能为空"));
        }
        return errorList;
    }

}