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

import com.sicheng.admin.sys.entity.SysUserRole;

import com.sicheng.admin.sys.service.SysUserRoleService;
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
import com.sicheng.admin.sys.service.MenuService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员角色中间表 Controller
 * 所属模块：sys
 *
 * @author cl
 * @version 2017-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUserRole")
public class SysUserRoleController extends BaseController {

    @Autowired
    private SysUserRoleService sysUserRoleService;



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
     * @param sysUserRole 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysUserRole:view")
    @RequestMapping(value = "list")
    public String list(SysUserRole sysUserRole, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysUserRole> page = sysUserRoleService.selectByWhere(new Page<SysUserRole>(request, response), new Wrapper(sysUserRole));
        model.addAttribute("page", page);
        return "admin/sys/sysUserRoleList";
    }

    /**
     * 进入保存页面
     *
     * @param sysUserRole 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysUserRole:save")
    @RequestMapping(value = "save1")
    public String save1(SysUserRole sysUserRole, Model model) {
        if (sysUserRole == null) {
            sysUserRole = new SysUserRole();
        }
        model.addAttribute("sysUserRole", sysUserRole);
        return "admin/sys/sysUserRoleForm";
    }

    /**
     * 执行保存
     *
     * @param sysUserRole        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysUserRole:save")
    @RequestMapping(value = "save2")
    public String save2(SysUserRole sysUserRole, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysUserRole, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysUserRole, model);//回显错误提示
        }

        //业务处理
        sysUserRoleService.insertSelective(sysUserRole);
        addMessage(redirectAttributes, "保存会员角色中间表成功");
        return "redirect:" + adminPath + "/sys/sysUserRole/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysUserRole 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysUserRole:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysUserRole sysUserRole, Model model) {
        SysUserRole entity = null;
        if (sysUserRole != null) {
            if (sysUserRole.getId() != null) {
                entity = sysUserRoleService.selectById(sysUserRole.getId());
            }
        }
        model.addAttribute("sysUserRole", entity);
        return "admin/sys/sysUserRoleForm";
    }

    /**
     * 执行编辑
     *
     * @param sysUserRole        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysUserRole:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysUserRole sysUserRole, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysUserRole, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysUserRole, model);//回显错误提示
        }

        //业务处理
        sysUserRoleService.updateByIdSelective(sysUserRole);
        addMessage(redirectAttributes, "编辑会员角色中间表成功");
        return "redirect:" + adminPath + "/sys/sysUserRole/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysUserRole        实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysUserRole:drop")
    @RequestMapping(value = "delete")
    public String delete(SysUserRole sysUserRole, RedirectAttributes redirectAttributes) {
        sysUserRoleService.deleteById(sysUserRole.getId());
        addMessage(redirectAttributes, "删除会员角色中间表成功");
        return "redirect:" + adminPath + "/sys/sysUserRole/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysUserRole 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysUserRole sysUserRole, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("userId"))) {
            errorList.add("用户编号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("userId")) && R.get("userId").length() > 19) {
            errorList.add("用户编号最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("roleId"))) {
            errorList.add("角色编号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("roleId")) && R.get("roleId").length() > 19) {
            errorList.add("角色编号最大长度不能超过19字符");
        }
        return errorList;
    }

}