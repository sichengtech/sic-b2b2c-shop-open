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

import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.member.interceptor.MemberMenuInterceptor;
import com.sicheng.seller.trade.service.TradeConsultationService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>标题: TradeConsultationController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhangjiali
 * @version 2017年2月27日 上午10:12:37
 */
@Controller
@RequestMapping(value = "${memberPath}/trade/consultation")
public class TradeConsultationController extends BaseController {

    @Autowired
    private TradeConsultationService consulService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting("consultation");//三级菜单高亮
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
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(TradeConsultation consultation, Model model, HttpServletRequest request, HttpServletResponse response) {
        String category = R.get("category");
        String isReply = R.get("isReply");
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        consultation.setUId(userMember.getUId());
        consultation.setType("0");//类别，0咨询、1回复
        Page<TradeConsultation> page = consulService.selectByWhere(new Page<TradeConsultation>(request, response), new Wrapper(consultation));
        TradeConsultation.fillProductSpu(page.getList());//获取商品信息
        TradeConsultation.fillReplyTradeConsultation(page.getList());//获取咨询回复信息
        TradeConsultation.fillUserMain(page.getList());//咨询用户信息
        model.addAttribute("page", page);
        model.addAttribute("category", category);
        model.addAttribute("isReply", isReply);
        return "member/trade/tradeConsultationList";
    }

}
