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
package com.sicheng.member.finance.web;

import com.sicheng.admin.settlement.entity.SettlementPreDeposit;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.member.interceptor.MemberMenuInterceptor;
import com.sicheng.seller.settlement.service.SettlementPreDepositService;
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
 * 预存款明细Controller
 * 所属模块：settlement
 *
 * @author fxx
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${memberPath}/finance/financePreDeposit")
public class FinancePreDepositController extends BaseController {

    @Autowired
    private SettlementPreDepositService settlementPreDepositService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting("preDeposit");//三级菜单高亮
    }

    /**
     * 进入列表页
     *
     * @param settlementPreDeposit 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(SettlementPreDeposit settlementPreDeposit, HttpServletRequest request, HttpServletResponse response, Model model) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        settlementPreDeposit.setUId(userMember.getUId());
        Page<SettlementPreDeposit> page = settlementPreDepositService.selectByWhere(new Page<SettlementPreDeposit>(request, response), new Wrapper(settlementPreDeposit));
        SettlementPreDeposit.fillUserMain(page.getList());
        SettlementPreDeposit.fillUser(page.getList());
        model.addAttribute("page", page);
        model.addAttribute("userMain", SsoUtils.getUserMain());
        return "member/finance/financePreDepositList";
    }

}