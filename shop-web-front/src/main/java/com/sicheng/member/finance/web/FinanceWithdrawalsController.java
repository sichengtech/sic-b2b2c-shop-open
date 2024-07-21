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

import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.settlement.entity.SettlementWithdrawals;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.settlement.service.SettlementPayWayService;
import com.sicheng.seller.settlement.service.SettlementWithdrawalsService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 提现 Controller
 * 所属模块：settlement
 *
 * @author fxx
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${memberPath}/finance/financeWithdrawals")
public class FinanceWithdrawalsController extends BaseController {

    @Autowired
    private SettlementWithdrawalsService settlementWithdrawalsService;
    @Autowired
    private SettlementPayWayService settlementPayWayService;

    /**
     * 进入列表页
     *
     * @param settlementWithdrawals 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(SettlementWithdrawals settlementWithdrawals, HttpServletRequest request, HttpServletResponse response, Model model) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        settlementWithdrawals.setUId(userMember.getUId());
        Page<SettlementWithdrawals> page = settlementWithdrawalsService.selectByWhere(new Page<SettlementWithdrawals>(request, response), new Wrapper(settlementWithdrawals));
        //获取会员信息
        SettlementWithdrawals.fillUserMain(page.getList());
        //获取支付方式信息
        SettlementWithdrawals.fillSettlementPayWay(page.getList());
        //获取后台管理员信息
        SettlementWithdrawals.fillUser(page.getList());
        model.addAttribute("page", page);
        return "member/finance/financeWithdrawalsList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementWithdrawals 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(SettlementWithdrawals settlementWithdrawals, Model model) {
        if (settlementWithdrawals == null) {
            settlementWithdrawals = new SettlementWithdrawals();
        }
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        //获取支付方法
        List<SettlementPayWay> payWayList = settlementPayWayService.selectByWhere(new Wrapper().and("status =", 1));
        model.addAttribute("userMember", userMember);
        model.addAttribute("settlementWithdrawals", settlementWithdrawals);
        model.addAttribute("payWayList", payWayList);
        return "member/finance/financeWithdrawalsForm";
        //return "member/finance/financeRechargeForm2";
    }

    /**
     * 执行保存
     *
     * @param settlementWithdrawals 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(SettlementWithdrawals settlementWithdrawals, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        settlementWithdrawals.setUId(SsoUtils.getUserMain().getUserMember().getUId());
        // 提现状态，0未支付、1已支付、2拒绝提现
        settlementWithdrawals.setStatus("0");
        // 支付终端，0pc端、1移动端
        String payTerminal = "";
        String s1 = R.getRequest().getHeader("user-agent");
        if (s1.contains("Android") || s1.contains("iPhone")) {
            payTerminal = "0";
        } else {
            payTerminal = "1";
        }
        settlementWithdrawals.setPayTerminal(payTerminal);
        settlementWithdrawals.setApplyDate(new Date());
        //表单验证
        List<String> errorList = validate(settlementWithdrawals, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(settlementWithdrawals, model);//回显错误提示
        }
        //业务处理
        settlementWithdrawalsService.addWithdrawals(settlementWithdrawals, SsoUtils.getUserMain().getUserMember());
        cache.del(CacheConstant.SECURITY_TYPE + SsoUtils.getUserMain().getUserMember().getUId());
        addMessage(redirectAttributes, "保存提现成功");
        return "redirect:" + memberPath + "/finance/financeWithdrawals/list.do?repage";
    }

    /**
     * 进入详情页面
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "withdrawalsDetail")
    public String detail(SettlementWithdrawals settlementWithdrawals, Model model) {
        SettlementWithdrawals entity = null;
        if (settlementWithdrawals != null) {
            if (settlementWithdrawals.getId() != null) {
                entity = settlementWithdrawalsService.selectById(settlementWithdrawals.getWithdrawalsId());
            }
        }
        model.addAttribute("settlementWithdrawals", entity);
        return "member/finance/financeWithdrawalsDetail";
    }

    /**
     * 删除
     *
     * @param settlementWithdrawals 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "delete")
    public String delete(SettlementWithdrawals settlementWithdrawals, RedirectAttributes redirectAttributes) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        if (settlementWithdrawals != null) {
            List<SettlementWithdrawals> entitys = settlementWithdrawalsService.selectByWhere(new Wrapper().and("withdrawals_id =", settlementWithdrawals.getWithdrawalsId()).and("u_id =", userMember.getUId()));
            if (entitys.size() > 0) {
                settlementWithdrawalsService.deleteWithdrawals(entitys.get(0), userMember);
                addMessage(redirectAttributes, "删除提现成功");
            }
        }
        return "redirect:" + memberPath + "/finance/financeWithdrawals/list.htm?repage";
    }

    /**
     * 表单验证
     *
     * @param settlementWithdrawals 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SettlementWithdrawals settlementWithdrawals, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("money"))) {
            errorList.add("提现金额不能为空");
        }
        if (StringUtils.isNotBlank(R.get("money")) && R.get("money").length() > 12) {
            errorList.add("提现金额最大长度不能超过12字符");
        }
        Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1,2})?)$");
        if (StringUtils.isNotBlank(R.get("money")) && !pattern.matcher(R.get("money")).matches()) {
            errorList.add("提现金额只能是整数或两位以内的小数");
        }

        if (StringUtils.isNotBlank(R.get("money"))) {
            BigDecimal money = new BigDecimal(R.get("money"));
            int sta = money.compareTo(SsoUtils.getUserMain().getUserMember().getBalance());
            if (sta == 1) {
                errorList.add("提现金额不能大于预存款余额");
            }
        }
        if (StringUtils.isBlank(R.get("receivablesNumber"))) {
            errorList.add("收款账号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("receivablesNumber")) && R.get("receivablesNumber").length() > 24) {
            errorList.add("收款账号最大长度不能超过24字符");
        }
        if (StringUtils.isBlank(R.get("payWayId"))) {
            errorList.add("收款方式不能为空");
        }
        if (StringUtils.isNotBlank(R.get("payWayId")) && R.get("payWayId").length() > 19) {
            errorList.add("收款方式最大长度不能超过19字符");
        }
        return errorList;
    }

}