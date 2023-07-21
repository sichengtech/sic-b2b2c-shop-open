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

import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.trade.service.TradeCommentService;
import com.sicheng.sso.service.UserMainService;
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
import java.util.*;

/**
 * 评论 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${sellerPath}/trade/tradeComment")
public class TradeCommentController extends BaseController {

    @Autowired
    private TradeCommentService tradeCommentService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private ProductSpuService productSpuService;

    @Autowired
    private SiteSendMessagsService siteSendMessagsService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        String menu3 = "020215";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
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
    @RequiresPermissions("trade:tradeComment:view")
    @RequestMapping(value = "list")
    public String list(TradeComment tradeComment, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        if (userSeller.getStoreId() != null) {
            tradeComment.setStoreId(userSeller.getStoreId());
        } else {
            tradeComment.setStoreId(-1L);
        }
        //接收会员名
        String userName = R.get("userName");
        if (StringUtils.isNotBlank(userName)) {
            //用户名转换小写
            userName = userName.toLowerCase();
            //用会员名去换取id
            UserMain userMain = new UserMain();
            userMain.setLoginName(userName);
            List<UserMain> userMainList = userMainService.selectByWhere(new Wrapper(userMain));
            if (!userMainList.isEmpty()) {
                Long uId = userMainList.get(0).getUId();
                tradeComment.setUId(uId);
            } else {
                model.addAttribute("userName", userName);
                return "seller/trade/tradeCommentList";
            }
        }
        //接收产品名
        String productName = R.get("productName");
        if (StringUtils.isNotBlank(productName)) {
            //用产品名换取id
            ProductSpu productSpu = new ProductSpu();
            productSpu.setName(productName);
            List<ProductSpu> productSpuList = productSpuService.selectByWhere(new Wrapper(productSpu));
            if (!productSpuList.isEmpty()) {
                Long pId = productSpuList.get(0).getId();
                tradeComment.setPId(pId);
            } else {
                model.addAttribute("productName", productName);
                return "seller/trade/tradeCommentList";
            }
        }
        //type类型，0评论、1追评、2回复
        tradeComment.setType("0");
        //isShow是否显示，0否、1是
        tradeComment.setIsShow("1");
        Page<TradeComment> page = tradeCommentService.selectByWhere(new Page<TradeComment>(request, response), new Wrapper(tradeComment));
        TradeComment.fillUserMain(page.getList());
        TradeComment.fillProductSpu(page.getList());
        TradeComment.fillTradeCommentAdd(page.getList());
        TradeComment.fillTradeCommentExplain(page.getList());
        model.addAttribute("userName", userName);
        model.addAttribute("productName", productName);
        model.addAttribute("page", page);
        return "seller/trade/tradeCommentList";
    }

    /**
     * 评价解释
     *
     * @param tradeComment       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComment:edit")
    @RequestMapping(value = "explainComment")
    public String explainComment(TradeComment tradeComment, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (tradeComment == null || tradeComment.getReplyId() == null) {
            model.addAttribute("message", FYUtils.fyParams("评价信息不存在！"));
            return "error/400";
        }
        //表单验证
        List<String> errorList = validate(model);
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return list(tradeComment, R.getRequest(), R.getResponse(), model);//回显错误提示
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        TradeComment comment = tradeCommentService.selectOne(new Wrapper().and("store_id=", userSeller.getStoreId()));//属主检查
        //入参检查
        if (comment == null) {
            model.addAttribute("message", FYUtils.fyParams("评价信息不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        tradeComment.setPId(comment.getPId());
        //type类型，0评论、1追评、2回复
        tradeComment.setType("2");
        //业务处理
        tradeCommentService.insertSelective(tradeComment);
        addMessage(redirectAttributes, FYUtils.fyParams("解释成功"));
        //发送消息
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("orderId", comment.getOrderId().toString());
        contentMap.put("storeName", SsoUtils.getUserMain().getLoginName());
        contentMap.put("evalTime", DateUtils.formatDateTime(new Date()));
        String messageTemplateNum = SiteSendMessagsService.MEMBER_ORDERS_COMMENT_REPLAY;
        Long uId = comment.getTradeOrder().getUId();
        siteSendMessagsService.sendMessage(contentMap, messageTemplateNum, uId);
        return "redirect:" + sellerPath + "/trade/tradeComment/list.htm?repage";
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
            errorList.add(FYUtils.fyParams("解释内容不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("content")) && R.get("content").length() > 512) {
            errorList.add(FYUtils.fyParams("解释内容最大长度不能超过512字符"));
        }
        return errorList;
    }

}