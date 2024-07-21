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
package com.sicheng.seller.trade.web;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.trade.entity.TradeComplaint;
import com.sicheng.admin.trade.entity.TradeComplaintDetail;
import com.sicheng.admin.trade.entity.TradeComplaintImage;
import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.trade.service.TradeComplaintDetailService;
import com.sicheng.seller.trade.service.TradeComplaintImageService;
import com.sicheng.seller.trade.service.TradeComplaintService;
import com.sicheng.seller.trade.service.TradeOrderItemService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 投诉 Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${sellerPath}/trade/tradeComplaint")
public class TradeComplaintController extends BaseController {

    @Autowired
    private TradeComplaintService tradeComplaintService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private TradeOrderItemService tradeOrderItemService;
    @Autowired
    private TradeComplaintImageService tradeComplaintImageService;
    @Autowired
    private TradeComplaintDetailService tradeComplaintDetailService;

    @Autowired
    private SiteSendMessagsService siteSendMessagsService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        String menu3 = "060204";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
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
    @RequiresPermissions("trade:tradeComplaint:view")
    @RequestMapping(value = "list")
    public String list(TradeComplaint tradeComplaint, HttpServletRequest request, HttpServletResponse response, Model model) {
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
                    tradeComplaint.setUId(userMainList.get(0).getUId());
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
                    tradeComplaint.setComplaintId(Long.parseLong(searchValue));
                } else {
                    tradeComplaint.setOrderId(-1L);
                }
            }
        }
        //设置投诉的类型 0投诉、1申诉
        tradeComplaint.setType("0");
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        if (userSeller.getStoreId() != null) {
            tradeComplaint.setStoreId(userSeller.getStoreId());
        } else {
            tradeComplaint.setStoreId(-1L);
        }
        tradeComplaint.setStoreId(userSeller.getStoreId());
        Page<TradeComplaint> page = tradeComplaintService.selectByWhere(new Page<TradeComplaint>(request, response), new Wrapper(tradeComplaint));
        List<TradeComplaint> resultList = new ArrayList<>();
        //orderItemList是根据商品名称查询出的list，取出orderItemList和page.getList()中相同的数据，就是当前条件下的数据
        if ("2".equals(searchCate) && StringUtils.isNotBlank(searchValue)) {
            for (TradeOrderItem item : orderItemList) {
                for (TradeComplaint complaint : page.getList()) {
                    if (item.getOrderItemId().equals(complaint.getOrderDetailId())) {
                        resultList.add(complaint);
                    }
                }
            }
            page.setList(resultList);
        }
        TradeComplaint.fillUserMain(page.getList());
        TradeComplaint.fillStore(page.getList());
        TradeComplaint.fillTradeOrderItem(page.getList());
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchCate", searchCate);
        model.addAttribute("page", page);
        return "seller/trade/tradeComplaintList";
    }

    /**
     * 进入编辑(处理投诉、查看投诉)页面
     *
     * @param tradeComplaint 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeComplaint tradeComplaint, Model model) {
        //参数检查
        if (tradeComplaint == null || tradeComplaint.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("投诉不存在！"));
            return "error/400";
        }
        //参数检查
        tradeComplaint.setStoreId(SsoUtils.getUserMain().getUserSeller().getStoreId());//属主检查
        TradeComplaint entity = tradeComplaintService.selectOne(new Wrapper(tradeComplaint));
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("投诉不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        String stat = R.get("stat");
        //stat:1处理,2查看
        model.addAttribute("stat", stat);
        model.addAttribute("tradeComplaint", entity);
        return "seller/trade/tradeComplaintDetail";
    }

    /**
     * 执行编辑(提交申诉信息)
     *
     * @param tradeComplaint     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeComplaint tradeComplaint, Model model, RedirectAttributes redirectAttributes) {
        //参数检查
        if (tradeComplaint == null || tradeComplaint.getReplyId() == null) {
            model.addAttribute("message", FYUtils.fyParams("投诉不存在！"));
            return "error/400";
        }
        //表单验证
        List<String> errorList = validate(model);
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeComplaint, model);//回显错误提示
        }
        //参数检查
        TradeComplaint complaint = tradeComplaintService.selectById(tradeComplaint.getReplyId());
        if (complaint == null || complaint.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("投诉不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        //把当前的投诉的状态改为对话中
        complaint.setStatus("30");// 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        TradeComplaint condition = new TradeComplaint();
        condition.setId(complaint.getId());//属主检查
        condition.setStoreId(userSeller.getStoreId());//属主检查
        tradeComplaintService.updateByWhereSelective(complaint, new Wrapper(condition));
        tradeComplaint.setType("1");
        //业务处理
        tradeComplaintService.insertSelective(tradeComplaint);
        String img1 = R.get("img1");
        if (StringUtils.isNoneBlank(img1)) {
            TradeComplaintImage tradeComplaintImage1 = new TradeComplaintImage();
            tradeComplaintImage1.setPath(img1);
            tradeComplaintImage1.setComplaintId(tradeComplaint.getComplaintId());
            tradeComplaintImageService.insertSelective(tradeComplaintImage1);
        }
        String img2 = R.get("img2");
        if (StringUtils.isNoneBlank(img2)) {
            TradeComplaintImage tradeComplaintImage2 = new TradeComplaintImage();
            tradeComplaintImage2.setPath(img2);
            tradeComplaintImage2.setComplaintId(tradeComplaint.getComplaintId());
            tradeComplaintImageService.insertSelective(tradeComplaintImage2);
        }
        String img3 = R.get("img3");
        if (StringUtils.isNoneBlank(img3)) {
            TradeComplaintImage tradeComplaintImage3 = new TradeComplaintImage();
            tradeComplaintImage3.setPath(img3);
            tradeComplaintImage3.setComplaintId(tradeComplaint.getComplaintId());
            tradeComplaintImageService.insertSelective(tradeComplaintImage3);
        }
        addMessage(redirectAttributes, "申诉成功");
        //发送消息
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("complaintId", tradeComplaint.getComplaintId().toString());
        String messageTemplateNum = SiteSendMessagsService.MEMBER_COMPLAINT_UPDATE;
        Long uId = tradeComplaint.getUId();
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        return "redirect:" + sellerPath + "/trade/tradeComplaint/list.htm?repage";
    }

    /**
     * 发送对话
     *
     * @param tradeComplaintDetail     实体对象
     * @param model
     * @return
     */
    @ResponseBody
    @RequiresPermissions("trade:tradeComplaint:edit")
    @RequestMapping(value = "sendMessage")
    public String sendMessage(TradeComplaintDetail tradeComplaintDetail, Model model) {
        String stat = "1";
        //表单验证
        List<String> errorList = validate(tradeComplaintDetail, model);
        //用户类型，1买家、2商家、3平台
        tradeComplaintDetail.setUserType("2");
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            stat = "0";
            return stat;
        }
        //业务处理
        tradeComplaintDetailService.insertSelective(tradeComplaintDetail);
        return stat;
    }

    /**
     * 提交仲裁
     *
     * @param tradeComplaint     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:edit")
    @RequestMapping(value = "arbitration")
    public String arbitration(TradeComplaint tradeComplaint, Model model, RedirectAttributes redirectAttributes) {
        //参数检查
        if (tradeComplaint == null || tradeComplaint.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("投诉不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        tradeComplaint.setStatus("40");//投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //业务处理
        TradeComplaint condition = new TradeComplaint();
        condition.setId(tradeComplaint.getId());//属主检查
        condition.setStoreId(userSeller.getStoreId());//属主检查
        tradeComplaintService.updateByWhereSelective(tradeComplaint, new Wrapper(condition));
        addMessage(redirectAttributes, FYUtils.fyParams("提交仲裁成功"));
        return "redirect:" + sellerPath + "/trade/tradeComplaint/list.htm?repage";
    }

    /**
     * 表单验证
     *
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Model model) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(R.get("content"))) {
            errorList.add(FYUtils.fyParams("申诉内容不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("content")) && R.get("content").length() > 255) {
            errorList.add(FYUtils.fyParams("申诉内容最大长度不能超过255字符"));
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
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(R.get("count"))) {
            errorList.add(FYUtils.fyParams("内容不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("count")) && R.get("count").length() > 1024) {
            errorList.add(FYUtils.fyParams("内容最大长度不能超过1024字符"));
        }
        return errorList;
    }

}