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
package com.sicheng.admin.store.web;

import com.sicheng.admin.store.entity.StoreNavigation;
import com.sicheng.admin.store.service.StoreNavigationService;

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
 * 店铺导航 Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeNavigation")
public class StoreNavigationController extends BaseController {

    @Autowired
    private StoreNavigationService storeNavigationService;



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
     * @param storeNavigation 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeNavigation:view")
    @RequestMapping(value = "list")
    public String list(StoreNavigation storeNavigation, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreNavigation> page = storeNavigationService.selectByWhere(new Page<StoreNavigation>(request, response), new Wrapper(storeNavigation));
        model.addAttribute("page", page);
        return "admin/store/storeNavigationList";
    }

    /**
     * 进入保存页面
     *
     * @param storeNavigation 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeNavigation:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreNavigation storeNavigation, Model model) {
        if (storeNavigation == null) {
            storeNavigation = new StoreNavigation();
        }
        model.addAttribute("storeNavigation", storeNavigation);
        return "admin/store/storeNavigationForm";
    }

    /**
     * 执行保存
     *
     * @param storeNavigation    实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeNavigation:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreNavigation storeNavigation, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeNavigation, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeNavigation, model);//回显错误提示
        }

        //业务处理
        storeNavigationService.insertSelective(storeNavigation);
        addMessage(redirectAttributes, "保存店铺导航成功");
        return "redirect:" + adminPath + "/store/storeNavigation/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeNavigation 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeNavigation:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreNavigation storeNavigation, Model model) {
        StoreNavigation entity = null;
        if (storeNavigation != null) {
            if (storeNavigation.getId() != null) {
                entity = storeNavigationService.selectById(storeNavigation.getId());
            }
        }
        model.addAttribute("storeNavigation", entity);
        return "admin/store/storeNavigationForm";
    }

    /**
     * 执行编辑
     *
     * @param storeNavigation    实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeNavigation:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreNavigation storeNavigation, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeNavigation, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeNavigation, model);//回显错误提示
        }

        //业务处理
        storeNavigationService.updateByIdSelective(storeNavigation);
        addMessage(redirectAttributes, "编辑店铺导航成功");
        return "redirect:" + adminPath + "/store/storeNavigation/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeNavigation    实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeNavigation:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreNavigation storeNavigation, RedirectAttributes redirectAttributes) {
        storeNavigationService.deleteById(storeNavigation.getId());
        addMessage(redirectAttributes, "删除店铺导航成功");
        return "redirect:" + adminPath + "/store/storeNavigation/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeNavigation 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreNavigation storeNavigation, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("storeNavigationId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("storeNavigationId")) && R.get("storeNavigationId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("navNumber"))) {
            errorList.add("编号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("navNumber")) && R.get("navNumber").length() > 10) {
            errorList.add("编号最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("sellerId"))) {
            errorList.add("关联(卖家表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("关联(卖家表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("导航名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("导航名称最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("isOpen"))) {
            errorList.add("是否开启(0.是 1否)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isOpen")) && R.get("isOpen").length() > 1) {
            errorList.add("是否开启(0.是 1否)最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add("排序不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("action"))) {
            errorList.add("动作，字典不能为空");
        }
        if (StringUtils.isNotBlank(R.get("action")) && R.get("action").length() > 10) {
            errorList.add("动作，字典最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("url"))) {
            errorList.add("目标连接不能为空");
        }
        if (StringUtils.isNotBlank(R.get("url")) && R.get("url").length() > 255) {
            errorList.add("目标连接最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("target"))) {
            errorList.add("窗口打开方式（1mainFrame、2 _blank、3_self、4_parent、5_top）不能为空");
        }
        if (StringUtils.isNotBlank(R.get("target")) && R.get("target").length() > 64) {
            errorList.add("窗口打开方式（1mainFrame、2 _blank、3_self、4_parent、5_top）最大长度不能超过64字符");
        }
        return errorList;
    }

}