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
package com.sicheng.admin.settlement.web;

import com.sicheng.admin.account.entity.SettlementTaskSub;
import com.sicheng.admin.account.service.SettlementTaskSubService;

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
 * 结算子任务 Controller
 * 所属模块：settlement
 *
 * @author 范秀秀
 * @version 2017-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementTaskSub")
public class SettlementTaskSubController extends BaseController {

    @Autowired
    private SettlementTaskSubService settlementTaskSubService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param settlementTaskSub 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementTaskSub:view")
    @RequestMapping(value = "list")
    public String list(SettlementTaskSub settlementTaskSub, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SettlementTaskSub> page = settlementTaskSubService.selectByWhere(new Page<SettlementTaskSub>(request, response), new Wrapper(settlementTaskSub));
        model.addAttribute("page", page);
        return "admin/settlement/settlementTaskSubList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementTaskSub 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementTaskSub:edit")
    @RequestMapping(value = "save1")
    public String save1(SettlementTaskSub settlementTaskSub, Model model) {
        if (settlementTaskSub == null) {
            settlementTaskSub = new SettlementTaskSub();
        }
        model.addAttribute("settlementTaskSub", settlementTaskSub);
        return "admin/settlement/settlementTaskSubForm";
    }

    /**
     * 执行保存
     *
     * @param settlementTaskSub  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementTaskSub:edit")
    @RequestMapping(value = "save2")
    public String save2(SettlementTaskSub settlementTaskSub, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementTaskSub, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(settlementTaskSub, model);//回显错误提示
        }

        //业务处理
        settlementTaskSubService.insertSelective(settlementTaskSub);
        addMessage(redirectAttributes, "保存结算子任务成功");
        return "redirect:" + adminPath + "/settlement/settlementTaskSub/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param settlementTaskSub 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementTaskSub:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SettlementTaskSub settlementTaskSub, Model model) {
        SettlementTaskSub entity = null;
        if (settlementTaskSub != null) {
            if (settlementTaskSub.getId() != null) {
                entity = settlementTaskSubService.selectById(settlementTaskSub.getId());
            }
        }
        model.addAttribute("settlementTaskSub", entity);
        return "admin/settlement/settlementTaskSubForm";
    }

    /**
     * 执行编辑
     *
     * @param settlementTaskSub  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementTaskSub:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SettlementTaskSub settlementTaskSub, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementTaskSub, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(settlementTaskSub, model);//回显错误提示
        }

        //业务处理
        settlementTaskSubService.updateByIdSelective(settlementTaskSub);
        addMessage(redirectAttributes, "编辑结算子任务成功");
        return "redirect:" + adminPath + "/settlement/settlementTaskSub/list.do?repage";
    }

    /**
     * 删除
     *
     * @param settlementTaskSub  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementTaskSub:edit")
    @RequestMapping(value = "delete")
    public String delete(SettlementTaskSub settlementTaskSub, RedirectAttributes redirectAttributes) {
        settlementTaskSubService.deleteById(settlementTaskSub.getId());
        addMessage(redirectAttributes, "删除结算子任务成功");
        return "redirect:" + adminPath + "/settlement/settlementTaskSub/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param settlementTaskSub 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SettlementTaskSub settlementTaskSub, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("taskSubId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("taskSubId")) && R.get("taskSubId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("taskMainId"))) {
            errorList.add("任务id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("taskMainId")) && R.get("taskMainId").length() > 19) {
            errorList.add("任务id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("sellerId"))) {
            errorList.add("商家id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("商家id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add("状态不能为空");
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add("状态最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("errorMsg")) && R.get("errorMsg").length() > 255) {
            errorList.add("错误信息最大长度不能超过255字符");
        }
        return errorList;
    }

}