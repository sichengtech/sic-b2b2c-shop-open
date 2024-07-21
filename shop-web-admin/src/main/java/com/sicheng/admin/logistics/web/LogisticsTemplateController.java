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
package com.sicheng.admin.logistics.web;

import com.sicheng.admin.logistics.entity.LogisticsTemplate;
import com.sicheng.admin.logistics.service.LogisticsTemplateService;

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
 * 运费模板 Controller
 * 所属模块：logistics
 *
 * @author fxx
 * @version 2017-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/logistics/logisticsTemplate")
public class LogisticsTemplateController extends BaseController {

    @Autowired
    private LogisticsTemplateService logisticsTemplateService;



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
     * @param logisticsTemplate 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:view")
    @RequestMapping(value = "list")
    public String list(LogisticsTemplate logisticsTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<LogisticsTemplate> page = logisticsTemplateService.selectByWhere(new Page<LogisticsTemplate>(request, response), new Wrapper(logisticsTemplate));
        model.addAttribute("page", page);
        return "admin/logistics/logisticsTemplateList";
    }

    /**
     * 进入保存页面
     *
     * @param logisticsTemplate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "save1")
    public String save1(LogisticsTemplate logisticsTemplate, Model model) {
        if (logisticsTemplate == null) {
            logisticsTemplate = new LogisticsTemplate();
        }
        model.addAttribute("logisticsTemplate", logisticsTemplate);
        return "admin/logistics/logisticsTemplateForm";
    }

    /**
     * 执行保存
     *
     * @param logisticsTemplate  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "save2")
    public String save2(LogisticsTemplate logisticsTemplate, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(logisticsTemplate, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(logisticsTemplate, model);//回显错误提示
        }

        //业务处理
        logisticsTemplateService.insertSelective(logisticsTemplate);
        addMessage(redirectAttributes, "保存运费模板成功");
        return "redirect:" + adminPath + "/logistics/logisticsTemplate/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param logisticsTemplate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "edit1")
    public String edit1(LogisticsTemplate logisticsTemplate, Model model) {
        LogisticsTemplate entity = null;
        if (logisticsTemplate != null) {
            if (logisticsTemplate.getId() != null) {
                entity = logisticsTemplateService.selectById(logisticsTemplate.getId());
            }
        }
        model.addAttribute("logisticsTemplate", entity);
        return "admin/logistics/logisticsTemplateForm";
    }

    /**
     * 执行编辑
     *
     * @param logisticsTemplate  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "edit2")
    public String edit2(LogisticsTemplate logisticsTemplate, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(logisticsTemplate, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(logisticsTemplate, model);//回显错误提示
        }

        //业务处理
        logisticsTemplateService.updateByIdSelective(logisticsTemplate);
        addMessage(redirectAttributes, "编辑运费模板成功");
        return "redirect:" + adminPath + "/logistics/logisticsTemplate/list.do?repage";
    }

    /**
     * 删除
     *
     * @param logisticsTemplate  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplate:edit")
    @RequestMapping(value = "delete")
    public String delete(LogisticsTemplate logisticsTemplate, RedirectAttributes redirectAttributes) {
        logisticsTemplateService.deleteById(logisticsTemplate.getId());
        addMessage(redirectAttributes, "删除运费模板成功");
        return "redirect:" + adminPath + "/logistics/logisticsTemplate/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param logisticsTemplate 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(LogisticsTemplate logisticsTemplate, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("ltId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("ltId")) && R.get("ltId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("defaultDcId"))) {
            errorList.add("默认物流公司(物流公司表id)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("defaultDcId")) && R.get("defaultDcId").length() > 19) {
            errorList.add("默认物流公司(物流公司表id)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("sellerId"))) {
            errorList.add("商家id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("商家id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("模板名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("模板名称最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("isFreeShipping"))) {
            errorList.add("是否包邮不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isFreeShipping")) && R.get("isFreeShipping").length() > 1) {
            errorList.add("是否包邮最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("valuationMethod")) && R.get("valuationMethod").length() > 1) {
            errorList.add("计价方式最大长度不能超过1字符");
        }
        return errorList;
    }

}