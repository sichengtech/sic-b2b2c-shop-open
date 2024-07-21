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

import com.sicheng.admin.settlement.entity.SettlementPreDeposit;
import com.sicheng.admin.settlement.service.SettlementPreDepositService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;

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
 * 预存款明细Controller
 * 所属模块：settlement
 *
 * @author fxx
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementPreDeposit")
public class SettlementPreDepositController extends BaseController {

    @Autowired
    private SettlementPreDepositService settlementPreDepositService;



    @Autowired
    private UserMainService userMainService;

    //菜单高亮
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030201";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
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
    @RequiresPermissions("settlement:settlementPreDeposit:view")
    @RequestMapping(value = "list")
    public String list(SettlementPreDeposit settlementPreDeposit, HttpServletRequest request, HttpServletResponse response, Model model) {
        //接收会员名
        String username = R.get("userName");
        Long uId = 0L;
        if (!(username == null || "".equals(username))) {
            //用接单人的名字去换取id
            UserMain userMain = new UserMain();
            userMain.setLoginName(username);
            List<UserMain> memberUserList = userMainService.selectByWhere(new Wrapper(userMain));
            if (memberUserList.size() > 0) {
                uId = memberUserList.get(0).getId();
                settlementPreDeposit.setUId(uId);
            } else {
                model.addAttribute("username", username);
                return "admin/settlement/settlementPreDepositList";
            }
        }
        Page<SettlementPreDeposit> page = settlementPreDepositService.selectByWhere(new Page<SettlementPreDeposit>(request, response), new Wrapper(settlementPreDeposit));
        SettlementPreDeposit.fillUserMain(page.getList());
        SettlementPreDeposit.fillUser(page.getList());
        model.addAttribute("username", username);
        model.addAttribute("page", page);
        return "admin/settlement/settlementPreDepositList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementPreDeposit 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementPreDeposit:save")
    @RequestMapping(value = "save1")
    public String save1(SettlementPreDeposit settlementPreDeposit, Model model) {
        if (settlementPreDeposit == null) {
            settlementPreDeposit = new SettlementPreDeposit();
        }
        model.addAttribute("settlementPreDeposit", settlementPreDeposit);
        return "admin/settlement/settlementPreDepositForm";
    }

    /**
     * 执行保存
     *
     * @param settlementPreDeposit 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementPreDeposit:save")
    @RequestMapping(value = "save2")
    public String save2(SettlementPreDeposit settlementPreDeposit, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementPreDeposit, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(settlementPreDeposit, model);//回显错误提示
        }

        //业务处理
        settlementPreDepositService.insertSelective(settlementPreDeposit);
        addMessage(redirectAttributes, "保存预存款明细成功");
        return "redirect:" + adminPath + "/settlement/settlementPreDeposit/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param settlementPreDeposit 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementPreDeposit:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SettlementPreDeposit settlementPreDeposit, Model model) {
        SettlementPreDeposit entity = null;
        if (settlementPreDeposit != null) {
            if (settlementPreDeposit.getId() != null) {
                entity = settlementPreDepositService.selectById(settlementPreDeposit.getId());
            }
        }
        model.addAttribute("settlementPreDeposit", entity);
        return "admin/settlement/settlementPreDepositForm";
    }

    /**
     * 执行编辑
     *
     * @param settlementPreDeposit 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementPreDeposit:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SettlementPreDeposit settlementPreDeposit, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementPreDeposit, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(settlementPreDeposit, model);//回显错误提示
        }

        //业务处理
        settlementPreDepositService.updateByIdSelective(settlementPreDeposit);
        addMessage(redirectAttributes, "编辑预存款明细成功");
        return "redirect:" + adminPath + "/settlement/settlementPreDeposit/list.do?repage";
    }

    /**
     * 删除
     *
     * @param settlementPreDeposit 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementPreDeposit:drop")
    @RequestMapping(value = "delete")
    public String delete(SettlementPreDeposit settlementPreDeposit, RedirectAttributes redirectAttributes) {
        settlementPreDepositService.deleteById(settlementPreDeposit.getId());
        addMessage(redirectAttributes, "删除预存款明细成功");
        return "redirect:" + adminPath + "/settlement/settlementPreDeposit/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param settlementPreDeposit 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SettlementPreDeposit settlementPreDeposit, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("uId"))) {
            errorList.add("会员id(会员表id)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19) {
            errorList.add("会员id(会员表id)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("availableMoney"))) {
            errorList.add("可用金额不能为空");
        }
        if (StringUtils.isBlank(R.get("operationMoney"))) {
            errorList.add("操作金额不能为空");
        }
        if (StringUtils.isNotBlank(R.get("operationDescribe")) && R.get("operationDescribe").length() > 1024) {
            errorList.add("操作描述最大长度不能超过1024字符");
        }
        if (StringUtils.isNotBlank(R.get("adminId")) && R.get("adminId").length() > 19) {
            errorList.add("管理员(管理员表id)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("bak1")) && R.get("bak1").length() > 64) {
            errorList.add("备用字段1最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak2")) && R.get("bak2").length() > 64) {
            errorList.add("备用字段2最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak3")) && R.get("bak3").length() > 64) {
            errorList.add("备用字段3最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak4")) && R.get("bak4").length() > 64) {
            errorList.add("备用字段4最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak5")) && R.get("bak5").length() > 64) {
            errorList.add("备用字段5最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak6")) && R.get("bak6").length() > 64) {
            errorList.add("备用字段6最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak7")) && R.get("bak7").length() > 64) {
            errorList.add("备用字段7最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak8")) && R.get("bak8").length() > 64) {
            errorList.add("备用字段8最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak9")) && R.get("bak9").length() > 64) {
            errorList.add("备用字段9最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak10")) && R.get("bak10").length() > 64) {
            errorList.add("备用字段10最大长度不能超过64字符");
        }
        return errorList;
    }

}