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

import com.google.common.collect.Lists;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.trade.service.TradeConsultationService;
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
import java.util.List;

/**
 * <p>标题: TradeConsultationController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2017年2月27日 上午10:12:37
 */
@Controller
@RequestMapping(value = "${sellerPath}/trade/consultation")
public class TradeConsultationController extends BaseController {

    @Autowired
    private TradeConsultationService consulService;



    /**
     * 菜单高亮
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        String menu3 = "060201";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 查询并进入咨询管理页面
     *
     * @param consultation 咨询管理
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:view")
    @RequestMapping(value = "list")
    public String list(TradeConsultation consultation, Model model, HttpServletRequest request, HttpServletResponse response) {
        String category = R.get("category");
        String isReply = R.get("isReply");
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        if (userSeller.getStoreId() != null) {
            consultation.setStoreId(userSeller.getStoreId());
        } else {
            consultation.setStoreId(-1L);
        }
        consultation.setType("0");//类别，0咨询、1回复
        Page<TradeConsultation> page = consulService.selectByWhere(new Page<TradeConsultation>(request, response), new Wrapper(consultation));
        TradeConsultation.fillProductSpu(page.getList());//获取商品信息
        TradeConsultation.fillReplyTradeConsultation(page.getList());//获取咨询回复信息
        TradeConsultation.fillUserMain(page.getList());//咨询用户信息
        model.addAttribute("page", page);
        model.addAttribute("category", category);
        model.addAttribute("isReply", isReply);
        return "seller/trade/tradeConsultationList";
    }

    /**
     * 保存咨询的回复内容并更新此条咨询记录
     *
     * @param consultation       咨询管理
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:edit")
    @RequestMapping(value = "save")
    public String save(TradeConsultation consultation, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (consultation == null || consultation.getReplyId() == null) {
            model.addAttribute("message", FYUtils.fyParams("咨询不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        if (StringUtils.isBlank(consultation.getContent())) {
            model.addAttribute("message", FYUtils.fyParams("回复内容不能为空"));
            return "seller/trade/tradeConsultationList";
        }
        if (StringUtils.isNotBlank(consultation.getContent()) && consultation.getContent().length() > 255) {
            model.addAttribute("message", FYUtils.fyParams("最大长度不能超过255字符"));
            return "seller/trade/tradeConsultationList";
        }
        consultation.setType("1");//类别，0咨询、1回复
        consultation.setStoreId(userSeller.getStoreId());//属主检查
        consulService.saveAndEdit(consultation);//保存咨询的回复内容
        addMessage(redirectAttributes, FYUtils.fyParams("回复咨询成功"));
        return "redirect:" + sellerPath + "/trade/consultation/list.htm";
    }

    /**
     * 删除单条咨询，并删除它的回复内容
     *
     * @param consultation       咨询管理
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:edit")
    @RequestMapping(value = "delete")
    public String delete(TradeConsultation consultation, RedirectAttributes redirectAttributes, Model model) {
        if (consultation == null || consultation.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("咨询不存在！"));
            return "error/400";
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        consultation.setStoreId(userSeller.getStoreId());//属主检查
        consulService.deleteConsultation(consultation);//删除咨询和它的回复内容
        addMessage(redirectAttributes, FYUtils.fyParams("删除信息成功"));
        return "redirect:" + sellerPath + "/trade/consultation/list.htm";
    }

    /**
     * 批量删除咨询，并删除它的回复内容
     *
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeConsultation:edit")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(RedirectAttributes redirectAttributes) {
        String[] ids = R.get("ids").split(",");
        List<Object> list = Lists.newArrayList();
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                list.add(Long.valueOf(ids[i]));
            }
        }
        if (!list.isEmpty()) {
            consulService.deleteAll(list, SsoUtils.getUserMain().getUserSeller().getStoreId());//属主检查
        }
        addMessage(redirectAttributes, FYUtils.fyParams("批量删除信息成功"));
        return "redirect:" + sellerPath + "/trade/consultation/list.htm";
    }
}
