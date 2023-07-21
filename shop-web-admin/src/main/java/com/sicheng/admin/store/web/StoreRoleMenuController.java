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

import com.sicheng.admin.store.entity.StoreRoleMenu;
import com.sicheng.admin.store.service.StoreRoleMenuService;

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
 * 角色、资源中间表 Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeRoleMenu")
public class StoreRoleMenuController extends BaseController {

    @Autowired
    private StoreRoleMenuService storeRoleMenuService;



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
     * @param storeRoleMenu 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeRoleMenu:view")
    @RequestMapping(value = "list")
    public String list(StoreRoleMenu storeRoleMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreRoleMenu> page = storeRoleMenuService.selectByWhere(new Page<StoreRoleMenu>(request, response), new Wrapper(storeRoleMenu));
        model.addAttribute("page", page);
        return "admin/store/storeRoleMenuList";
    }

    /**
     * 进入保存页面
     *
     * @param storeRoleMenu 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeRoleMenu:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreRoleMenu storeRoleMenu, Model model) {
        if (storeRoleMenu == null) {
            storeRoleMenu = new StoreRoleMenu();
        }
        model.addAttribute("storeRoleMenu", storeRoleMenu);
        return "admin/store/storeRoleMenuForm";
    }

    /**
     * 执行保存
     *
     * @param storeRoleMenu      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeRoleMenu:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreRoleMenu storeRoleMenu, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeRoleMenu, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeRoleMenu, model);//回显错误提示
        }

        //业务处理
        storeRoleMenuService.insertSelective(storeRoleMenu);
        addMessage(redirectAttributes, "保存角色、资源中间表成功");
        return "redirect:" + adminPath + "/store/storeRoleMenu/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeRoleMenu 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeRoleMenu:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreRoleMenu storeRoleMenu, Model model) {
        StoreRoleMenu entity = null;
        if (storeRoleMenu != null) {
            if (storeRoleMenu.getId() != null) {
                entity = storeRoleMenuService.selectById(storeRoleMenu.getId());
            }
        }
        model.addAttribute("storeRoleMenu", entity);
        return "admin/store/storeRoleMenuForm";
    }

    /**
     * 执行编辑
     *
     * @param storeRoleMenu      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeRoleMenu:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreRoleMenu storeRoleMenu, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeRoleMenu, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeRoleMenu, model);//回显错误提示
        }

        //业务处理
        storeRoleMenuService.updateByIdSelective(storeRoleMenu);
        addMessage(redirectAttributes, "编辑角色、资源中间表成功");
        return "redirect:" + adminPath + "/store/storeRoleMenu/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeRoleMenu      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeRoleMenu:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreRoleMenu storeRoleMenu, RedirectAttributes redirectAttributes) {
        storeRoleMenuService.deleteById(storeRoleMenu.getId());
        addMessage(redirectAttributes, "删除角色、资源中间表成功");
        return "redirect:" + adminPath + "/store/storeRoleMenu/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeRoleMenu 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreRoleMenu storeRoleMenu, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("roleId"))) {
            errorList.add("关联(角色表)角色id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("roleId")) && R.get("roleId").length() > 19) {
            errorList.add("关联(角色表)角色id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("menuId"))) {
            errorList.add("店铺资源表id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("menuId")) && R.get("menuId").length() > 19) {
            errorList.add("店铺资源表id最大长度不能超过19字符");
        }
        return errorList;
    }

}