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

import com.sicheng.admin.settlement.entity.SettlementRecharge;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.member.interceptor.MemberMenuInterceptor;
import com.sicheng.seller.settlement.service.SettlementRechargeService;
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
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 充值 Controller
 * 所属模块：settlement
 *
 * @author fxx
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${memberPath}/finance/financeRecharge")
public class FinanceRechargeController extends BaseController {

    @Autowired
    private SettlementRechargeService settlementRechargeService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting("recharge");//三级菜单高亮
    }

    /**
     * 进入列表页
     *
     * @param settlementRecharge 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(SettlementRecharge settlementRecharge, HttpServletRequest request, HttpServletResponse response, Model model) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        settlementRecharge.setUId(userMember.getUId());
        Page<SettlementRecharge> page = settlementRechargeService.selectByWhere(new Page<SettlementRecharge>(request, response), new Wrapper(settlementRecharge));
        //获取会员信息
        SettlementRecharge.findUserMain(page.getList());
        //获取支付方式信息
        SettlementRecharge.findSettlementPayWay(page.getList());
        //获取后台管理员信息
        SettlementRecharge.findUser(page.getList());
        model.addAttribute("page", page);
        model.addAttribute("isList", R.get("isList"));
        return "member/finance/financeRechargeList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(SettlementRecharge settlementRecharge, Model model) {
        if (settlementRecharge == null) {
            settlementRecharge = new SettlementRecharge();
        }
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        model.addAttribute("userMember", userMember);
        model.addAttribute("settlementRecharge", settlementRecharge);
        return "member/finance/financeRechargeForm";
    }

    /**
     * 执行保存
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(SettlementRecharge settlementRecharge, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementRecharge, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(settlementRecharge, model);//回显错误提示
        }
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        settlementRecharge.setUId(userMember.getUId());
        //支付状态，0未支付、1已支付
        settlementRecharge.setStaus("0");
        // 支付终端，0pc端、1移动端
        String payTerminal = "";
        String s1 = request.getHeader("user-agent");
        if (s1.contains("Android") || s1.contains("iPhone")) {
            payTerminal = "0";
        } else {
            payTerminal = "1";
        }
        settlementRecharge.setPayTerminal(payTerminal);
        settlementRecharge.setRechargeTime(new Date());
        //业务处理
        //settlementRechargeService.insertSelective(settlementRecharge);
        settlementRechargeService.addRecharge(settlementRecharge, userMember);
        return "redirect:" + memberPath + "/finance/financeRecharge/rechargePay.htm?rechargeId=" + settlementRecharge.getRechargeId();
    }

    /**
     * 进入支付页面
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "rechargePay")
    public String rechargePay(SettlementRecharge settlementRecharge, Model model) {
        SettlementRecharge entity = null;
        if (settlementRecharge != null) {
            if (settlementRecharge.getId() != null) {
                entity = settlementRechargeService.selectById(settlementRecharge.getId());
            }
        }
        model.addAttribute("settlementRecharge", entity);
        return "member/finance/financeRechargePay";
    }

    /**
     * 进入详情页面
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "rechargeDetail")
    public String detail(SettlementRecharge settlementRecharge, Model model) {
        SettlementRecharge entity = null;
        if (settlementRecharge != null) {
            if (settlementRecharge.getId() != null) {
                entity = settlementRechargeService.selectById(settlementRecharge.getId());
            }
        }
        model.addAttribute("settlementRecharge", entity);
        return "member/finance/financeRechargeDetail";
    }

    /**
     * 删除
     *
     * @param settlementRecharge 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "delete")
    public String delete(SettlementRecharge settlementRecharge, RedirectAttributes redirectAttributes) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        settlementRecharge.setUId(userMember.getUId());
        settlementRechargeService.deleteByWhere(new Wrapper(settlementRecharge));
        addMessage(redirectAttributes, "删除充值成功");
        return "redirect:" + memberPath + "/finance/financeRecharge/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SettlementRecharge settlementRecharge, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("rechargeMoney"))) {
            errorList.add("充值金额不能为空");
        }
        if (StringUtils.isNotBlank(R.get("rechargeMoney")) && R.get("rechargeMoney").length() > 12) {
            errorList.add("充值金额最大长度不能超过12字符");
        }
        Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1,2})?)$");
        if (StringUtils.isNotBlank(R.get("rechargeMoney")) && !pattern.matcher(R.get("rechargeMoney")).matches()) {
            errorList.add("充值金额只能是整数或两位以内的小数");
        }
        return errorList;
    }

}