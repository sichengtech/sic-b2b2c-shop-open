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

import com.sicheng.admin.store.entity.StoreRole;
import com.sicheng.admin.store.service.StoreRoleService;

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
 * 卖家角色 Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeRole")
public class StoreRoleController extends BaseController {

    @Autowired
    private StoreRoleService storeRoleService;



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
     * @param storeRole 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeRole:view")
    @RequestMapping(value = "list")
    public String list(StoreRole storeRole, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreRole> page = storeRoleService.selectByWhere(new Page<StoreRole>(request, response), new Wrapper(storeRole));
        model.addAttribute("page", page);
        return "admin/store/storeRoleList";
    }

    /**
     * 进入保存页面
     *
     * @param storeRole 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreRole storeRole, Model model) {
        if (storeRole == null) {
            storeRole = new StoreRole();
        }
        model.addAttribute("storeRole", storeRole);
        return "admin/store/storeRoleForm";
    }

    /**
     * 执行保存
     *
     * @param storeRole          实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreRole storeRole, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeRole, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeRole, model);//回显错误提示
        }

        //业务处理
        storeRoleService.insertSelective(storeRole);
        addMessage(redirectAttributes, "保存卖家角色成功");
        return "redirect:" + adminPath + "/store/storeRole/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeRole 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreRole storeRole, Model model) {
        StoreRole entity = null;
        if (storeRole != null) {
            if (storeRole.getId() != null) {
                entity = storeRoleService.selectById(storeRole.getId());
            }
        }
        model.addAttribute("storeRole", entity);
        return "admin/store/storeRoleForm";
    }

    /**
     * 执行编辑
     *
     * @param storeRole          实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreRole storeRole, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeRole, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeRole, model);//回显错误提示
        }

        //业务处理
        storeRoleService.updateByIdSelective(storeRole);
        addMessage(redirectAttributes, "编辑卖家角色成功");
        return "redirect:" + adminPath + "/store/storeRole/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeRole          实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreRole storeRole, RedirectAttributes redirectAttributes) {
        storeRoleService.deleteById(storeRole.getId());
        addMessage(redirectAttributes, "删除卖家角色成功");
        return "redirect:" + adminPath + "/store/storeRole/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeRole 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreRole storeRole, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("roleId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("roleId")) && R.get("roleId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("sellerId"))) {
            errorList.add("关联(卖家表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("关联(卖家表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("roleName"))) {
            errorList.add("角色名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("roleName")) && R.get("roleName").length() > 64) {
            errorList.add("角色名称最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("enname")) && R.get("enname").length() > 255) {
            errorList.add("英文名称最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("roleType")) && R.get("roleType").length() > 64) {
            errorList.add("角色类型最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("dataScope")) && R.get("dataScope").length() > 1) {
            errorList.add("数据范围最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("isSys"))) {
            errorList.add("是否系统数据(0否、1是)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isSys")) && R.get("isSys").length() > 1) {
            errorList.add("是否系统数据(0否、1是)最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("useable"))) {
            errorList.add("是否可用，0否、1是不能为空");
        }
        if (StringUtils.isNotBlank(R.get("useable")) && R.get("useable").length() > 64) {
            errorList.add("是否可用，0否、1是最大长度不能超过64字符");
        }
        return errorList;
    }

}