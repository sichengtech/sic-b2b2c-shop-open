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
package com.sicheng.admin.settlement.web;

import com.sicheng.admin.settlement.entity.SettlementWithdrawals;
import com.sicheng.admin.settlement.service.SettlementWithdrawalsService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sys.entity.User;

import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
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
import java.util.List;

/**
 * 提现 Controller
 * 所属模块：settlement
 *
 * @author fxx
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementWithdrawals")
public class SettlementWithdrawalsController extends BaseController {

    @Autowired
    private SettlementWithdrawalsService settlementWithdrawalsService;



    @Autowired
    private UserMainService userMainService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030205";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param settlementWithdrawals 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementWithdrawals:view")
    @RequestMapping(value = "list")
    public String list(SettlementWithdrawals settlementWithdrawals, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SettlementWithdrawals> page = settlementWithdrawalsService.selectByWhere(new Page<SettlementWithdrawals>(request, response), new Wrapper(settlementWithdrawals));
        //根据外键获取关联表的信息
        selectRelation(page.getList());
        //根据会员登录名（买家）查询充值记录
        String loginName = R.get("loginName");//会员登录名（买家）
        if (StringUtils.isNotBlank(loginName)) {
            UserMain userMain = new UserMain();
            userMain.setLoginName(loginName);
            List<UserMain> muList = userMainService.selectByWhere(new Wrapper(userMain));
            if (!muList.isEmpty()) {
                Long memUserId = muList.get(0).getId();//用户名唯一，只能查出一条记录
                SettlementWithdrawals settlementWithdrawals1 = new SettlementWithdrawals();
                settlementWithdrawals1.setUId(memUserId);
                List<SettlementWithdrawals> swList = settlementWithdrawalsService.selectByWhere(new Wrapper(settlementWithdrawals1));
                //根据外键获取关联表的信息
                selectRelation(swList);
                page.setList(swList);
            } else {
                page.setList(null);
            }
        }
        model.addAttribute("page", page);
        model.addAttribute("loginName", loginName);
        return "admin/settlement/settlementWithdrawalsList";
    }

    /**
     * 执行提现审核
     *
     * @param settlementWithdrawals 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementWithdrawals:auth")
    @RequestMapping(value = "examine")
    public String examine(SettlementWithdrawals settlementWithdrawals, Model model, RedirectAttributes redirectAttributes) {
        List<String> errorList = validate(settlementWithdrawals, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return list(settlementWithdrawals, R.getRequest(), R.getResponse(), model);//回显错误提示
        }
        //业务处理
        User user = UserUtils.getUser();//获取当前登录用户
        if (user != null) {
            Long adminId = user.getId();
            if (adminId != null) {
                settlementWithdrawals.setAdminId(adminId);//管理员(管理员表id)
            }
        }
        settlementWithdrawalsService.examineWithdrawals(settlementWithdrawals);
        addMessage(redirectAttributes, "审核提现成功");
        return "redirect:" + adminPath + "/settlement/settlementWithdrawals/list.do?repage";
    }

    /**
     * 删除
     *
     * @param settlementWithdrawals 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementWithdrawals:drop")
    @RequestMapping(value = "delete")
    public String delete(SettlementWithdrawals settlementWithdrawals, RedirectAttributes redirectAttributes) {
        settlementWithdrawalsService.deleteById(settlementWithdrawals.getId());
        addMessage(redirectAttributes, "删除提现成功");
        return "redirect:" + adminPath + "/settlement/settlementWithdrawals/list.do?repage";
    }

    /**
     * 根据外键获取关联表的信息
     *
     * @param page         查询出的列表信息
     * @param memIdList    会员（买家）id
     * @param payWayIdList 支付方式id
     * @param userIdList   后台管理员id
     */
    public void selectRelation(List<SettlementWithdrawals> list) {
        //获取会员信息
        SettlementWithdrawals.fillUserMain(list);
        //获取支付方式信息
        SettlementWithdrawals.fillSettlementPayWay(list);
        //获取后台管理员信息
        SettlementWithdrawals.fillUser(list);
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
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add("提现状态不能为空");
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add("提现状态最大长度不能超过1字符");
        }
        if ("1".equals(R.get("status"))) {
            if (StringUtils.isBlank(R.get("payTime"))) {
                errorList.add("支付时间不能为空");
            }
        }
        if ("2".equals(R.get("status"))) {
            if (StringUtils.isBlank(R.get("rejectionReason"))) {
                errorList.add("拒绝提现理由不能为空");
            }
            if (StringUtils.isNotBlank(R.get("rejectionReason")) && R.get("rejectionReason").length() > 512) {
                errorList.add("拒绝提现理由最大长度不能超过512字符");
            }
        }
        return errorList;
    }

}