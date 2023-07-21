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
package com.sicheng.admin.logistics.web;

import com.sicheng.admin.logistics.entity.LogisticsTemplateItem;
import com.sicheng.admin.logistics.service.LogisticsTemplateItemService;

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
 * 运费模板详情 Controller
 * 所属模块：logistics
 *
 * @author 范秀秀
 * @version 2017-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/logistics/logisticsTemplateItem")
public class LogisticsTemplateItemController extends BaseController {

    @Autowired
    private LogisticsTemplateItemService logisticsTemplateItemService;



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
     * @param logisticsTemplateItem 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplateItem:view")
    @RequestMapping(value = "list")
    public String list(LogisticsTemplateItem logisticsTemplateItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<LogisticsTemplateItem> page = logisticsTemplateItemService.selectByWhere(new Page<LogisticsTemplateItem>(request, response), new Wrapper(logisticsTemplateItem));
        model.addAttribute("page", page);
        return "admin/logistics/logisticsTemplateItemList";
    }

    /**
     * 进入保存页面
     *
     * @param logisticsTemplateItem 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplateItem:edit")
    @RequestMapping(value = "save1")
    public String save1(LogisticsTemplateItem logisticsTemplateItem, Model model) {
        if (logisticsTemplateItem == null) {
            logisticsTemplateItem = new LogisticsTemplateItem();
        }
        model.addAttribute("logisticsTemplateItem", logisticsTemplateItem);
        return "admin/logistics/logisticsTemplateItemForm";
    }

    /**
     * 执行保存
     *
     * @param logisticsTemplateItem 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplateItem:edit")
    @RequestMapping(value = "save2")
    public String save2(LogisticsTemplateItem logisticsTemplateItem, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(logisticsTemplateItem, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(logisticsTemplateItem, model);//回显错误提示
        }

        //业务处理
        logisticsTemplateItemService.insertSelective(logisticsTemplateItem);
        addMessage(redirectAttributes, "保存运费模板详情成功");
        return "redirect:" + adminPath + "/logistics/logisticsTemplateItem/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param logisticsTemplateItem 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplateItem:edit")
    @RequestMapping(value = "edit1")
    public String edit1(LogisticsTemplateItem logisticsTemplateItem, Model model) {
        LogisticsTemplateItem entity = null;
        if (logisticsTemplateItem != null) {
            if (logisticsTemplateItem.getId() != null) {
                entity = logisticsTemplateItemService.selectById(logisticsTemplateItem.getId());
            }
        }
        model.addAttribute("logisticsTemplateItem", entity);
        return "admin/logistics/logisticsTemplateItemForm";
    }

    /**
     * 执行编辑
     *
     * @param logisticsTemplateItem 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplateItem:edit")
    @RequestMapping(value = "edit2")
    public String edit2(LogisticsTemplateItem logisticsTemplateItem, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(logisticsTemplateItem, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(logisticsTemplateItem, model);//回显错误提示
        }

        //业务处理
        logisticsTemplateItemService.updateByIdSelective(logisticsTemplateItem);
        addMessage(redirectAttributes, "编辑运费模板详情成功");
        return "redirect:" + adminPath + "/logistics/logisticsTemplateItem/list.do?repage";
    }

    /**
     * 删除
     *
     * @param logisticsTemplateItem 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("logistics:logisticsTemplateItem:edit")
    @RequestMapping(value = "delete")
    public String delete(LogisticsTemplateItem logisticsTemplateItem, RedirectAttributes redirectAttributes) {
        logisticsTemplateItemService.deleteById(logisticsTemplateItem.getId());
        addMessage(redirectAttributes, "删除运费模板详情成功");
        return "redirect:" + adminPath + "/logistics/logisticsTemplateItem/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param logisticsTemplateItem 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(LogisticsTemplateItem logisticsTemplateItem, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("ltiId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("ltiId")) && R.get("ltiId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("ltId"))) {
            errorList.add("运费模板id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("ltId")) && R.get("ltId").length() > 19) {
            errorList.add("运费模板id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("area.id"))) {
            errorList.add("运送到不能为空");
        }
        if (StringUtils.isNotBlank(R.get("areaName")) && R.get("areaName").length() > 64) {
            errorList.add("地区名最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("firstItem")) && R.get("firstItem").length() > 10) {
            errorList.add("首件(件)最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("firstPrice")) && R.get("firstPrice").length() > 12) {
            errorList.add("首重(元)最大长度不能超过12字符");
        }
        if (StringUtils.isNotBlank(R.get("continueItem")) && R.get("continueItem").length() > 10) {
            errorList.add("续件(件)最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("continuePrice")) && R.get("continuePrice").length() > 12) {
            errorList.add("续重(元)最大长度不能超过12字符");
        }
        return errorList;
    }

}