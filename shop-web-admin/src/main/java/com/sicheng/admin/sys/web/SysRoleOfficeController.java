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

import com.sicheng.admin.sys.entity.SysRoleOffice;

import com.sicheng.admin.sys.service.SysRoleOfficeService;
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
 * 角色机构中间表 Controller
 * 所属模块：sys
 *
 * @author cl
 * @version 2017-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysRoleOffice")
public class SysRoleOfficeController extends BaseController {

    @Autowired
    private SysRoleOfficeService sysRoleOfficeService;



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
     * @param sysRoleOffice 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysRoleOffice:view")
    @RequestMapping(value = "list")
    public String list(SysRoleOffice sysRoleOffice, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysRoleOffice> page = sysRoleOfficeService.selectByWhere(new Page<SysRoleOffice>(request, response), new Wrapper(sysRoleOffice));
        model.addAttribute("page", page);
        return "admin/sys/sysRoleOfficeList";
    }

    /**
     * 进入保存页面
     *
     * @param sysRoleOffice 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysRoleOffice:save")
    @RequestMapping(value = "save1")
    public String save1(SysRoleOffice sysRoleOffice, Model model) {
        if (sysRoleOffice == null) {
            sysRoleOffice = new SysRoleOffice();
        }
        model.addAttribute("sysRoleOffice", sysRoleOffice);
        return "admin/sys/sysRoleOfficeForm";
    }

    /**
     * 执行保存
     *
     * @param sysRoleOffice      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysRoleOffice:save")
    @RequestMapping(value = "save2")
    public String save2(SysRoleOffice sysRoleOffice, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysRoleOffice, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysRoleOffice, model);//回显错误提示
        }

        //业务处理
        sysRoleOfficeService.insertSelective(sysRoleOffice);
        addMessage(redirectAttributes, "保存角色机构中间表成功");
        return "redirect:" + adminPath + "/sys/sysRoleOffice/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysRoleOffice 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysRoleOffice:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysRoleOffice sysRoleOffice, Model model) {
        SysRoleOffice entity = null;
        if (sysRoleOffice != null) {
            if (sysRoleOffice.getId() != null) {
                entity = sysRoleOfficeService.selectById(sysRoleOffice.getId());
            }
        }
        model.addAttribute("sysRoleOffice", entity);
        return "admin/sys/sysRoleOfficeForm";
    }

    /**
     * 执行编辑
     *
     * @param sysRoleOffice      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysRoleOffice:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysRoleOffice sysRoleOffice, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysRoleOffice, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysRoleOffice, model);//回显错误提示
        }

        //业务处理
        sysRoleOfficeService.updateByIdSelective(sysRoleOffice);
        addMessage(redirectAttributes, "编辑角色机构中间表成功");
        return "redirect:" + adminPath + "/sys/sysRoleOffice/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysRoleOffice      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysRoleOffice:drop")
    @RequestMapping(value = "delete")
    public String delete(SysRoleOffice sysRoleOffice, RedirectAttributes redirectAttributes) {
        sysRoleOfficeService.deleteById(sysRoleOffice.getId());
        addMessage(redirectAttributes, "删除角色机构中间表成功");
        return "redirect:" + adminPath + "/sys/sysRoleOffice/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysRoleOffice 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysRoleOffice sysRoleOffice, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("roleId"))) {
            errorList.add("role_id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("roleId")) && R.get("roleId").length() > 19) {
            errorList.add("role_id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("officeId"))) {
            errorList.add("office_id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("officeId")) && R.get("officeId").length() > 19) {
            errorList.add("office_id最大长度不能超过19字符");
        }
        return errorList;
    }

}