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

import com.sicheng.admin.store.entity.StoreSellerRole;
import com.sicheng.admin.store.service.StoreSellerRoleService;

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
 * 卖家和角色表中间 Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeSellerRole")
public class StoreSellerRoleController extends BaseController {

    @Autowired
    private StoreSellerRoleService storeSellerRoleService;



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
     * @param storeSellerRole 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeSellerRole:view")
    @RequestMapping(value = "list")
    public String list(StoreSellerRole storeSellerRole, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreSellerRole> page = storeSellerRoleService.selectByWhere(new Page<StoreSellerRole>(request, response), new Wrapper(storeSellerRole));
        model.addAttribute("page", page);
        return "admin/store/storeSellerRoleList";
    }

    /**
     * 进入保存页面
     *
     * @param storeSellerRole 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeSellerRole:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreSellerRole storeSellerRole, Model model) {
        if (storeSellerRole == null) {
            storeSellerRole = new StoreSellerRole();
        }
        model.addAttribute("storeSellerRole", storeSellerRole);
        return "admin/store/storeSellerRoleForm";
    }

    /**
     * 执行保存
     *
     * @param storeSellerRole    实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeSellerRole:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreSellerRole storeSellerRole, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeSellerRole, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeSellerRole, model);//回显错误提示
        }

        //业务处理
        storeSellerRoleService.insertSelective(storeSellerRole);
        addMessage(redirectAttributes, "保存卖家和角色表中间成功");
        return "redirect:" + adminPath + "/store/storeSellerRole/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeSellerRole 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeSellerRole:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreSellerRole storeSellerRole, Model model) {
        StoreSellerRole entity = null;
        if (storeSellerRole != null) {
            if (storeSellerRole.getId() != null) {
                entity = storeSellerRoleService.selectById(storeSellerRole.getId());
            }
        }
        model.addAttribute("storeSellerRole", entity);
        return "admin/store/storeSellerRoleForm";
    }

    /**
     * 执行编辑
     *
     * @param storeSellerRole    实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeSellerRole:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreSellerRole storeSellerRole, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeSellerRole, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeSellerRole, model);//回显错误提示
        }

        //业务处理
        storeSellerRoleService.updateByIdSelective(storeSellerRole);
        addMessage(redirectAttributes, "编辑卖家和角色表中间成功");
        return "redirect:" + adminPath + "/store/storeSellerRole/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeSellerRole    实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeSellerRole:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreSellerRole storeSellerRole, RedirectAttributes redirectAttributes) {
        storeSellerRoleService.deleteById(storeSellerRole.getId());
        addMessage(redirectAttributes, "删除卖家和角色表中间成功");
        return "redirect:" + adminPath + "/store/storeSellerRole/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeSellerRole 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreSellerRole storeSellerRole, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("sellerId"))) {
            errorList.add("关联store_seller(卖家表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("关联store_seller(卖家表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("roleId"))) {
            errorList.add("关联store_role(卖家角色表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("roleId")) && R.get("roleId").length() > 19) {
            errorList.add("关联store_role(卖家角色表)最大长度不能超过19字符");
        }
        return errorList;
    }

}