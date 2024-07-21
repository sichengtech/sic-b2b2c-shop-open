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
package com.sicheng.admin.sys.web;

import com.sicheng.admin.sys.entity.SysRoleMenu;

import com.sicheng.admin.sys.service.SysRoleMenuService;
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
 * 角色菜单中间表 Controller
 * 所属模块：sys
 *
 * @author cl
 * @version 2017-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysRoleMenu")
public class SysRoleMenuController extends BaseController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;



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
     * @param sysRoleMenu 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysRoleMenu:view")
    @RequestMapping(value = "list")
    public String list(SysRoleMenu sysRoleMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysRoleMenu> page = sysRoleMenuService.selectByWhere(new Page<SysRoleMenu>(request, response), new Wrapper(sysRoleMenu));
        model.addAttribute("page", page);
        return "admin/sys/sysRoleMenuList";
    }

    /**
     * 进入保存页面
     *
     * @param sysRoleMenu 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysRoleMenu:save")
    @RequestMapping(value = "save1")
    public String save1(SysRoleMenu sysRoleMenu, Model model) {
        if (sysRoleMenu == null) {
            sysRoleMenu = new SysRoleMenu();
        }
        model.addAttribute("sysRoleMenu", sysRoleMenu);
        return "admin/sys/sysRoleMenuForm";
    }

    /**
     * 执行保存
     *
     * @param sysRoleMenu        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysRoleMenu:save")
    @RequestMapping(value = "save2")
    public String save2(SysRoleMenu sysRoleMenu, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysRoleMenu, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysRoleMenu, model);//回显错误提示
        }

        //业务处理
        sysRoleMenuService.insertSelective(sysRoleMenu);
        addMessage(redirectAttributes, "保存角色菜单中间表成功");
        return "redirect:" + adminPath + "/sys/sysRoleMenu/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysRoleMenu 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysRoleMenu:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysRoleMenu sysRoleMenu, Model model) {
        SysRoleMenu entity = null;
        if (sysRoleMenu != null) {
            if (sysRoleMenu.getId() != null) {
                entity = sysRoleMenuService.selectById(sysRoleMenu.getId());
            }
        }
        model.addAttribute("sysRoleMenu", entity);
        return "admin/sys/sysRoleMenuForm";
    }

    /**
     * 执行编辑
     *
     * @param sysRoleMenu        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysRoleMenu:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysRoleMenu sysRoleMenu, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysRoleMenu, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysRoleMenu, model);//回显错误提示
        }

        //业务处理
        sysRoleMenuService.updateByIdSelective(sysRoleMenu);
        addMessage(redirectAttributes, "编辑角色菜单中间表成功");
        return "redirect:" + adminPath + "/sys/sysRoleMenu/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysRoleMenu        实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysRoleMenu:drop")
    @RequestMapping(value = "delete")
    public String delete(SysRoleMenu sysRoleMenu, RedirectAttributes redirectAttributes) {
        sysRoleMenuService.deleteById(sysRoleMenu.getId());
        addMessage(redirectAttributes, "删除角色菜单中间表成功");
        return "redirect:" + adminPath + "/sys/sysRoleMenu/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysRoleMenu 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysRoleMenu sysRoleMenu, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("roleId"))) {
            errorList.add("角色编号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("roleId")) && R.get("roleId").length() > 19) {
            errorList.add("角色编号最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("menuId"))) {
            errorList.add("机构编号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("menuId")) && R.get("menuId").length() > 19) {
            errorList.add("机构编号最大长度不能超过19字符");
        }
        return errorList;
    }

}