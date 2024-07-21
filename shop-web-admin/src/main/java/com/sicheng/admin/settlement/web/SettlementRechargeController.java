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

import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.settlement.entity.SettlementRecharge;
import com.sicheng.admin.settlement.service.SettlementPayWayService;
import com.sicheng.admin.settlement.service.SettlementRechargeService;
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
 * 充值 Controller
 * 所属模块：settlement
 *
 * @author fxx
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementRecharge")
public class SettlementRechargeController extends BaseController {

    @Autowired
    private SettlementRechargeService settlementRechargeService;



    @Autowired
    private UserMainService userMainService;

    @Autowired
    private SettlementPayWayService settlementPayWayService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030204";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
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
    @RequiresPermissions("settlement:settlementRecharge:view")
    @RequestMapping(value = "list")
    public String list(SettlementRecharge settlementRecharge, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SettlementRecharge> page = settlementRechargeService.selectByWhere(new Page<SettlementRecharge>(request, response), new Wrapper(settlementRecharge));
        //根据外键获取关联表的信息
        selectRelation(page.getList());
        //查询支付方式
        SettlementPayWay settlementPayWay = new SettlementPayWay();
        List<SettlementPayWay> listPW = settlementPayWayService.selectAll(new Wrapper(settlementPayWay));
        //根据会员登录名（买家）查询充值记录
        String loginName = R.get("loginName");//会员登录名（买家）
        if (StringUtils.isNotBlank(loginName)) {
            UserMain userMain = new UserMain();
            userMain.setLoginName(loginName);
            List<UserMain> muList = userMainService.selectByWhere(new Wrapper(userMain));
            if (!muList.isEmpty()) {
                Long memUserId = muList.get(0).getId();//用户名唯一，只能查出一条记录
                SettlementRecharge settlementRecharge1 = new SettlementRecharge();
                settlementRecharge1.setUId(memUserId);
                List<SettlementRecharge> reList = settlementRechargeService.selectByWhere(new Wrapper(settlementRecharge1));
                //根据外键获取关联表的信息
                selectRelation(reList);
                page.setList(reList);
            } else {
                page.setList(null);
            }
        }
        model.addAttribute("page", page);
        model.addAttribute("listPW", listPW);
        model.addAttribute("loginName", loginName);
        return "admin/settlement/settlementRechargeList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementRecharge:save")
    @RequestMapping(value = "save1")
    public String save1(SettlementRecharge settlementRecharge, Model model) {
        if (settlementRecharge == null) {
            settlementRecharge = new SettlementRecharge();
        }
        model.addAttribute("settlementRecharge", settlementRecharge);
        return "admin/settlement/settlementRechargeForm";
    }

    /**
     * 执行保存
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementRecharge:save")
    @RequestMapping(value = "save2")
    public String save2(SettlementRecharge settlementRecharge, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementRecharge, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(settlementRecharge, model);//回显错误提示
        }

        //业务处理
        settlementRechargeService.insertSelective(settlementRecharge);
        addMessage(redirectAttributes, "保存充值成功");
        return "redirect:" + adminPath + "/settlement/settlementRecharge/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementRecharge:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SettlementRecharge settlementRecharge, Model model) {
        SettlementRecharge entity = null;
        if (settlementRecharge != null) {
            if (settlementRecharge.getId() != null) {
                entity = settlementRechargeService.selectById(settlementRecharge.getId());
            }
        }
        model.addAttribute("settlementRecharge", entity);
        return "admin/settlement/settlementRechargeForm";
    }

    /**
     * 执行编辑
     *
     * @param settlementRecharge 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementRecharge:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SettlementRecharge settlementRecharge, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementRecharge, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(settlementRecharge, model);//回显错误提示
        }

        //业务处理
        settlementRechargeService.updateByIdSelective(settlementRecharge);
        addMessage(redirectAttributes, "编辑充值成功");
        return "redirect:" + adminPath + "/settlement/settlementRecharge/list.do?repage";
    }

    /**
     * 执行审核
     *
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementRecharge:auth")
    @RequestMapping(value = "examine")
    public String examine(SettlementRecharge settlementRecharge, Model model, RedirectAttributes redirectAttributes) {
        //业务处理
        User user = UserUtils.getUser();//获取当前登录用户
		/*Long rechargeId=R.getLong("id");//充值id
		Date payDate=R.getDate("payDate","yyyy-MM-dd HH:mm:ss",null);//支付时间
		Long payWayId=R.getLong("payWayId");//支付方式id
		String payTerminal=R.get("payTerminal");//付款终端
		String tradeNumber=R.get("tradeNumber");//第三方付款平台交易号
	SettlementRecharge settlementRecharge=new SettlementRecharge();
		settlementRecharge.setRechargeId(rechargeId);
		settlementRecharge.setPayDate(payDate);
		settlementRecharge.setPayWayId(payWayId);
		settlementRecharge.setPayTerminal(payTerminal);
		settlementRecharge.setTradeNumber(tradeNumber);*/
        settlementRecharge.setStaus("1");//支付状态，0未支付、1已支付
        if (user != null) {
            Long adminId = user.getId();
            if (adminId != null) {
                settlementRecharge.setAdminId(adminId);//管理员(管理员表id)
            }
        }
        settlementRechargeService.examineRecharge(settlementRecharge);
        addMessage(redirectAttributes, "审核充值成功");
        return "redirect:" + adminPath + "/settlement/settlementRecharge/list.do?repage";
    }

    /**
     * 删除
     *
     * @param settlementRecharge 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementRecharge:drop")
    @RequestMapping(value = "delete")
    public String delete(SettlementRecharge settlementRecharge, RedirectAttributes redirectAttributes) {
        settlementRechargeService.deleteById(settlementRecharge.getId());
        addMessage(redirectAttributes, "删除充值成功");
        return "redirect:" + adminPath + "/settlement/settlementRecharge/list.do?repage";
    }

    /**
     * 根据外键获取关联表的信息
     *
     * @param list 查询出的列表信息
     */
    public void selectRelation(List<SettlementRecharge> list) {
        //获取会员信息
        SettlementRecharge.findUserMain(list);
        //获取支付方式信息
        SettlementRecharge.findSettlementPayWay(list);
        //获取后台管理员信息
        SettlementRecharge.findUser(list);
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
        if (StringUtils.isBlank(R.get("rechargeId"))) {
            errorList.add("充值ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("rechargeId")) && R.get("rechargeId").length() > 19) {
            errorList.add("充值ID最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("rechargeNumber")) && R.get("rechargeNumber").length() > 64) {
            errorList.add("充值编号最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("uId"))) {
            errorList.add("会员id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19) {
            errorList.add("会员id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("rechargeMoney"))) {
            errorList.add("充值金额不能为空");
        }
        if (StringUtils.isNotBlank(R.get("rechargeMoney")) && R.get("rechargeMoney").length() > 12) {
            errorList.add("充值金额最大长度不能超过12字符");
        }
        if (StringUtils.isBlank(R.get("rechargeTime"))) {
            errorList.add("充值时间不能为空");
        }
        if (StringUtils.isNotBlank(R.get("payWayId")) && R.get("payWayId").length() > 19) {
            errorList.add("支付方式最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("staus"))) {
            errorList.add("支付状态不能为空");
        }
        if (StringUtils.isNotBlank(R.get("staus")) && R.get("staus").length() > 1) {
            errorList.add("支付状态最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("payTerminal")) && R.get("payTerminal").length() > 1) {
            errorList.add("支付终端最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("tradeNumber")) && R.get("tradeNumber").length() > 64) {
            errorList.add("交易号最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("adminId")) && R.get("adminId").length() > 19) {
            errorList.add("管理员最大长度不能超过19字符");
        }
        return errorList;
    }

}