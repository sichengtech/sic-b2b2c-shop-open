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
import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.trade.service.TradeCommentService;
import com.sicheng.seller.trade.service.TradeOrderService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * 评论 Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${memberPath}/trade/tradeComment")
public class TradeCommentController extends BaseController {

    @Autowired
    private TradeCommentService tradeCommentService;
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
        MemberMenuInterceptor.menuHighLighting( "tradeComment");//三级菜单高亮
    }

    /**
     * 进入列表页
     *
     * @param tradeComment 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(TradeComment tradeComment, HttpServletRequest request, HttpServletResponse response, Model model) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeComment.setUId(userMember.getUId());
        //type类型，0评论、1追评、2回复
        tradeComment.setType("0");
        //isShow是否显示，0否、1是
        tradeComment.setIsShow("1");
        Page<TradeComment> page = tradeCommentService.selectByWhere(new Page<TradeComment>(request, response), new Wrapper(tradeComment));
        TradeComment.fillUserMain(page.getList());
        TradeComment.fillProductSpu(page.getList());
        TradeComment.fillTradeCommentAdd(page.getList());
        TradeComment.fillTradeCommentExplain(page.getList());
        TradeComment.fillProductSku(page.getList());
        model.addAttribute("page", page);
        return "member/trade/tradeCommentList";
    }

    /**
     * 进入评价页面
     *
     * @param tradeReturnOrder 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(TradeComment tradeComment, TradeOrder tradeOrder, Model model) {
        //入参检查
        if (tradeOrder == null || tradeOrder.getOrderId() == null) {
            model.addAttribute("message", FYUtils.fyParams("评价不存在！"));
            return "error/400";
        }
        Long uId = SsoUtils.getUserMain().getUserMember().getUId();
        TradeOrder entity = tradeOrderService.selectOne(new Wrapper().and("order_id=", tradeOrder.getOrderId()).and("u_id=", uId));//属主检查
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("评价不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        model.addAttribute("tradeOrder", entity);
        model.addAttribute("isAdditionalComment", R.get("isAdditionalComment"));
        return "member/trade/tradeCommentForm";
    }

    /**
     * 保存评价
     *
     * @param tradeComment       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(TradeComment tradeComment, TradeOrder tradeOrder, Model model, RedirectAttributes redirectAttributes) {
        if (tradeOrder == null || tradeOrder.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("评价的订单不存在！"));
            return "error/400";
        }
        //表单验证
        List<String> errorList = validate(tradeComment, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeComment, tradeOrder, model);//回显错误提示
        }
        //图片
        String[] img1 = R.getArray("img1");
        String[] img2 = R.getArray("img2");
        String[] img3 = R.getArray("img3");
        String[] img4 = R.getArray("img4");
        String[] img5 = R.getArray("img5");
        List<String[]> imgs = new ArrayList<>();
        imgs.add(img1);
        imgs.add(img2);
        imgs.add(img3);
        imgs.add(img4);
        imgs.add(img5);
        //评价内容
        String[] content = R.getArray("content");
        //spuId
        String[] pId = R.getArray("pId");
        //skuId
        String[] skuId = R.getArray("skuId");
        //评论等级
        String[] grades = R.getArray("grade");
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        List<TradeComment> tradeCommentList = new ArrayList<>();
        //业务处理
        if (img1.length > 0) {
            for (int i = 0; i < img1.length; i++) {
                TradeComment tradeComment2 = new TradeComment();
                tradeComment2.setOrderId(tradeComment.getOrderId());
                tradeComment2.setStoreId(tradeComment.getStoreId());
                tradeComment2.setPId(Long.valueOf(pId[i]));
                tradeComment2.setSkuId(Long.valueOf(skuId[i]));
                tradeComment2.setUId(userMember.getUId());
                if ("1".equals(R.get("isAdditionalComment"))) {
                    List<TradeComment> list = tradeCommentService.selectByWhere(new Wrapper().and("order_id=", tradeComment.getOrderId()).and("sku_id=", Long.valueOf(skuId[i])).and("p_id=", Long.valueOf(pId[i])));
                    if (list.size() == 0) {
                        continue;
                    }
                    tradeComment2.setReplyId(list.get(0).getCommentId());
                    //type类型，0评论、1追评、2回复
                    tradeComment2.setType("1");
                } else {
                    //type类型，0评论、1追评、2回复
                    tradeComment2.setType("0");
                }
                tradeComment2.setProductScore(tradeComment.getProductScore());
                tradeComment2.setServiceAttitudeScore(tradeComment.getServiceAttitudeScore());
                tradeComment2.setDeliverySpeedScore(tradeComment.getDeliverySpeedScore());
                //是否显示,0否 、1是
                tradeComment2.setIsShow("1");
                tradeComment2.setCreateDate(new Date());
                if (content.length > i) {
                    if ("".equals(content[i])) {
                        tradeComment2.setContent(FYUtils.fyParams("好评"));
                    } else {
                        tradeComment2.setContent(content[i]);
                    }
                }
                if (grades.length > i) {
                    tradeComment2.setGrade(grades[i]);
                }
                tradeCommentList.add(tradeComment2);
            }
        }
        //业务处理
        tradeOrder.setUId(userMember.getUId());//属主检查
        if (tradeCommentList.size() == 0) {
            model.addAttribute("message", FYUtils.fyParams("缺少参数！"));
            return "error/400";
        }
        tradeCommentService.addComment(tradeCommentList, imgs, tradeOrder);
        addMessage(redirectAttributes, FYUtils.fyParams("评价成功"));
        //发送消息
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("orderId", tradeComment.getOrderId().toString());
        contentMap.put("userName", SsoUtils.getUserMain().getLoginName());
        contentMap.put("evalTime", DateUtils.formatDateTime(new Date()));
        String messageTemplateNum = SiteSendMessagsService.MEMBER_ORDERS_COMMENT;
        Long uId = tradeComment.getStore().getUserMain().getUId();
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        return "redirect:" + memberPath + "/trade/tradeComment/list.htm?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeComment tradeComment, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("orderId"))) {
            errorList.add(FYUtils.fyParams("评论订单编号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("orderId")) && R.get("orderId").length() > 64) {
            errorList.add(FYUtils.fyParams("评论订单编号最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("storeId"))) {
            errorList.add(FYUtils.fyParams("评论店铺编号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 64) {
            errorList.add(FYUtils.fyParams("评论店铺编号最大长度不能超过64字符"));
        }
        return errorList;
    }

}