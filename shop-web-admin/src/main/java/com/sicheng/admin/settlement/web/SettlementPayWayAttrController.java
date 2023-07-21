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

import com.sicheng.admin.settlement.entity.SettlementPayWayAttr;
import com.sicheng.admin.settlement.service.SettlementPayWayAttrService;

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

import java.util.ArrayList;
import java.util.List;

/**
 * 支付方式属性 Controller
 * 所属模块：settlement
 *
 * @author 张加利
 * @version 2018-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/settlement/settlementPayWayAttr")
public class SettlementPayWayAttrController extends BaseController {

    @Autowired
    private SettlementPayWayAttrService settlementPayWayAttrService;



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
     * @param payWayId
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWayAttr:view")
    @RequestMapping(value = "list")
    public String list(String payWayId, Model model) {
        if (StringUtils.isBlank(payWayId) || !StringUtils.isNumeric(payWayId)) {
            return "admin/settlement/settlementPayWayAttrList";
        }
        SettlementPayWayAttr payWayAttr = new SettlementPayWayAttr();
        payWayAttr.setPayWayId(Long.parseLong(payWayId));
        List<SettlementPayWayAttr> payWayAttrList = settlementPayWayAttrService.selectByWhere(new Wrapper(payWayAttr).orderBy("pay_way_attr_id"));
        model.addAttribute("payWayAttrList", payWayAttrList);
        return "admin/settlement/settlementPayWayAttrList";
    }

    /**
     * 进入保存页面
     *
     * @param settlementPayWayAttr 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWayAttr:save")
    @RequestMapping(value = "save1")
    public String save1(SettlementPayWayAttr settlementPayWayAttr, Model model) {
        if (settlementPayWayAttr == null) {
            settlementPayWayAttr = new SettlementPayWayAttr();
        }
        model.addAttribute("settlementPayWayAttr", settlementPayWayAttr);
        return "admin/settlement/settlementPayWayAttrForm";
    }

    /**
     * 执行保存
     *
     * @param settlementPayWayAttr 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWayAttr:save")
    @RequestMapping(value = "save2")
    public String save2(SettlementPayWayAttr settlementPayWayAttr, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementPayWayAttr, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(settlementPayWayAttr, model);//回显错误提示
        }

        //业务处理
        settlementPayWayAttrService.insertSelective(settlementPayWayAttr);
        addMessage(redirectAttributes, "保存支付方式属性成功");
        return "redirect:" + adminPath + "/settlement/settlementPayWayAttr/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param settlementPayWayAttr 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWayAttr:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SettlementPayWayAttr settlementPayWayAttr, Model model) {
        SettlementPayWayAttr entity = null;
        if (settlementPayWayAttr != null) {
            if (settlementPayWayAttr.getId() != null) {
                entity = settlementPayWayAttrService.selectById(settlementPayWayAttr.getId());
            }
        }
        model.addAttribute("settlementPayWayAttr", entity);
        return "admin/settlement/settlementPayWayAttrForm";
    }

    /**
     * 执行编辑
     *
     * @param settlementPayWayAttr 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWayAttr:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SettlementPayWayAttr settlementPayWayAttr, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(settlementPayWayAttr, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(settlementPayWayAttr, model);//回显错误提示
        }

        //业务处理
        settlementPayWayAttrService.updateByIdSelective(settlementPayWayAttr);
        addMessage(redirectAttributes, "编辑支付方式属性成功");
        return "redirect:" + adminPath + "/settlement/settlementPayWayAttr/list.do?repage";
    }

    /**
     * 删除
     *
     * @param settlementPayWayAttr 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("settlement:settlementPayWayAttr:drop")
    @RequestMapping(value = "delete")
    public String delete(SettlementPayWayAttr settlementPayWayAttr, RedirectAttributes redirectAttributes) {
        settlementPayWayAttrService.deleteById(settlementPayWayAttr.getId());
        addMessage(redirectAttributes, "删除支付方式属性成功");
        return "redirect:" + adminPath + "/settlement/settlementPayWayAttr/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param settlementPayWayAttr 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SettlementPayWayAttr settlementPayWayAttr, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("payWayAttrId"))) {
            errorList.add("主键id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("payWayAttrId")) && R.get("payWayAttrId").length() > 19) {
            errorList.add("主键id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("payWayId"))) {
            errorList.add("支付方式id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("payWayId")) && R.get("payWayId").length() > 19) {
            errorList.add("支付方式id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("payWayKey"))) {
            errorList.add("支付方式属性键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("payWayKey")) && R.get("payWayKey").length() > 64) {
            errorList.add("支付方式属性键最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("payWayValue"))) {
            errorList.add("支付方式属性值不能为空");
        }
        if (StringUtils.isNotBlank(R.get("payWayValue")) && R.get("payWayValue").length() > 1024) {
            errorList.add("支付方式属性值最大长度不能超过1024字符");
        }
        if (StringUtils.isNotBlank(R.get("payWayDescribe")) && R.get("payWayDescribe").length() > 255) {
            errorList.add("支付方式属性描述最大长度不能超过255字符");
        }
        return errorList;
    }

}