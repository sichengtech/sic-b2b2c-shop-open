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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.member.interceptor.MemberMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeComplaint;
import com.sicheng.admin.trade.entity.TradeComplaintDetail;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.trade.service.TradeComplaintDetailService;
import com.sicheng.seller.trade.service.TradeComplaintService;
import com.sicheng.seller.trade.service.TradeOrderItemService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * 投诉 Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${memberPath}/trade/tradeComplaint")
public class TradeComplaintController extends BaseController {

    @Autowired
    private TradeComplaintService tradeComplaintService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private TradeComplaintDetailService tradeComplaintDetailService;
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
        //菜单高亮
        MemberMenuInterceptor.menuHighLighting("complaint");//三级菜单高亮
    }

    /**
     * 进入列表页
     *
     * @param tradeComplaint 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(TradeComplaint tradeComplaint, HttpServletRequest request, HttpServletResponse response, Model model) {
        //设置投诉的类型 0投诉、1申诉
        tradeComplaint.setType("0");
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeComplaint.setUId(userMember.getUId());
        Wrapper wrapper = new Wrapper(tradeComplaint);
        //列表中的检索：1进行中，2已完成
        String stat = R.get("stat");
        if (StringUtils.isNotBlank(stat)) {
            if ("1".equals(stat)) {
                wrapper.and("status <>", 50);
            } else if ("2".equals(stat)) {
                wrapper.and("status =", 50);
            }
        }
        Page<TradeComplaint> page = tradeComplaintService.selectByWhere(new Page<TradeComplaint>(request, response), wrapper);
        TradeComplaint.fillUserMain(page.getList());
        TradeComplaint.fillStore(page.getList());
        TradeComplaint.fillTradeOrderItem(page.getList());
        model.addAttribute("page", page);
        model.addAttribute("stat", stat);
        return "member/trade/tradeComplaintList";
    }

    /**
     * 进入投诉详情页面
     *
     * @param tradeComplaint 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(TradeComplaint tradeComplaint, TradeOrder tradeOrder, TradeOrderItem tradeOrderItem, Model model) {
        TradeComplaint entity = null;
        if (tradeComplaint != null) {
            entity = tradeComplaintService.selectById(tradeComplaint.getComplaintId());
        }
        //stat:1处理,2查看
        String stat = R.get("stat");
        TradeOrder tradeOrder2 = new TradeOrder();
        if (tradeOrder.getOrderId() != null) {
            tradeOrder2 = tradeOrderService.selectById(tradeOrder.getOrderId());
        } else if (entity != null) {
            tradeOrder2 = entity.getTradeOrder();
        }

        TradeOrderItem tradeOrderItem2 = new TradeOrderItem();
        if (tradeOrderItem.getOrderItemId() != null) {
            tradeOrderItem2 = tradeOrderItemService.selectById(tradeOrderItem.getOrderItemId());
        } else if (entity != null) {
            tradeOrderItem2 = entity.getTradeOrderItem();
        }
        model.addAttribute("tradeOrder", tradeOrder2);
        model.addAttribute("tradeOrderItem", tradeOrderItem2);
        model.addAttribute("stat", stat);
        model.addAttribute("tradeComplaint", entity);
        return "member/trade/tradeComplaintDetail";
    }

    /**
     * 执行投诉
     *
     * @param tradeComplaint     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(TradeComplaint tradeComplaint, TradeOrder tradeOrder, TradeOrderItem tradeOrderItem, Model model, RedirectAttributes redirectAttributes) {
        if (StringUtils.isNotBlank(R.get("orderDetailId"))) {
            TradeOrderItem tradeOrderItem2 = tradeOrderItemService.selectById(tradeComplaint.getOrderDetailId());
            if (tradeOrderItem2 != null) {
                tradeComplaint.setPId(tradeOrderItem2.getPId());
                tradeComplaint.setSkuId(tradeOrderItem2.getSkuId());
            }
        }
        if (tradeOrder != null) {
            TradeOrder tradeOrder2 = tradeOrderService.selectById(tradeOrder.getOrderId());
            if (tradeOrder2 != null) {
                tradeComplaint.setStoreId(tradeOrder2.getStoreId());
            }
        }
        //表单验证
        List<String> errorList = validate(tradeComplaint, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeComplaint, tradeOrder, tradeOrderItem, model);//回显错误提示
        }
        // 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        tradeComplaint.setStatus("10");
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeComplaint.setUId(userMember.getUId());
        tradeComplaint.setType("0");
        //tradeComplaintService.insert(tradeComplaint);
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
        //业务处理
        tradeComplaintService.applyTradeComplaint(tradeComplaint, imgList);
        addMessage(redirectAttributes, FYUtils.fyParams("投诉成功"));
        //发送消息
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("complaintId", tradeComplaint.getComplaintId().toString());
        String messageTemplateNum = SiteSendMessagsService.STORE_COMPLAINT;
        Long uId = tradeComplaint.getStore().getUserMain().getUId();
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        return "redirect:" + memberPath + "/trade/tradeComplaint/list.htm?repage";
    }

    /**
     * 发送对话
     *
     * @param tradeComplaint     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequiresPermissions("user")
    @RequestMapping(value = "sendMessage")
    public String sendMessage(TradeComplaintDetail tradeComplaintDetail, Model model, RedirectAttributes redirectAttributes) {
        String stat = "1";
        //表单验证
        List<String> errorList = validate(tradeComplaintDetail, model);
        //用户类型，1买家、2商家、3平台
        tradeComplaintDetail.setUserType("1");
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            stat = "0";
            return stat;
        }
        //业务处理
        tradeComplaintDetailService.insertSelective(tradeComplaintDetail);
        return stat;
    }

    /**
     * 刷新对话
     *
     * @param tradeComplaint 实体对象
     * @return
     */
    @ResponseBody
    @RequiresPermissions("user")
    @RequestMapping(value = "refreshMessage")
    public List<TradeComplaintDetail> refreshMessage(TradeComplaintDetail tradeComplaintDetail) {
        //业务处理
        List<TradeComplaintDetail> complaintDetailList = tradeComplaintDetailService.selectByWhere(new Wrapper(tradeComplaintDetail));
        return complaintDetailList;
    }

    /**
     * 提交仲裁
     *
     * @param tradeComplaint     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "arbitration")
    public String arbitration(TradeComplaint tradeComplaint, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (tradeComplaint == null || tradeComplaint.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("提交仲裁信息不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        tradeComplaint.setStatus("40");//投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeComplaint.setUId(userMember.getUId());//属主检查
        tradeComplaintService.updateByWhereSelective(tradeComplaint, new Wrapper().and("complaint_id =", tradeComplaint.getComplaintId()).and("u_id =", userMember.getUId()));
        addMessage(redirectAttributes, FYUtils.fyParams("提交仲裁成功！"));
        return "redirect:" + memberPath + "/trade/tradeComplaint/list.htm?repage";
    }

    /**
     * 删除
     *
     * @param tradeOrderItem     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "delete")
    public String delete(TradeComplaint tradeComplaint, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (tradeComplaint == null || tradeComplaint.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("交易投诉信息不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeComplaint.setUId(userMember.getUId());//属主检查
        tradeComplaintService.deleteByWhere(new Wrapper(tradeComplaint));
        addMessage(redirectAttributes, FYUtils.fyParams("删除成功！"));
        return "redirect:" + memberPath + "/trade/tradeComplaint/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeComplaint 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeComplaint tradeComplaint, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("content"))) {
            errorList.add(FYUtils.fyParams("投诉内容不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("content")) && R.get("content").length() > 255) {
            errorList.add(FYUtils.fyParams("投诉内容最大长度不能超过")+"255"+FYUtils.fyParams("字符"));
        }
        if (tradeComplaint.getStoreId() == null) {
            errorList.add(FYUtils.fyParams("投诉店铺不能为空"));
        }
        if (tradeComplaint.getPId() == null) {
            errorList.add(FYUtils.fyParams("投诉商品不能为空"));
        }
        if (tradeComplaint.getSkuId() == null) {
            errorList.add(FYUtils.fyParams("投诉商品sku不能为空"));
        }
        if (tradeComplaint.getOrderId() == null) {
            errorList.add(FYUtils.fyParams("投诉商品订单不能为空"));
        }
        if (tradeComplaint.getOrderDetailId() == null) {
            errorList.add(FYUtils.fyParams("订单详情不能为空"));
        }
        return errorList;
    }

    /**
     * 表单验证
     *
     * @param tradeComplaintDetail 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeComplaintDetail tradeComplaintDetail, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("count"))) {
            errorList.add(FYUtils.fyParams("内容不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("count")) && R.get("count").length() > 1024) {
            errorList.add(FYUtils.fyParams("内容最大长度不能超过")+"1024"+FYUtils.fyParams("字符"));
        }
        return errorList;
    }

}