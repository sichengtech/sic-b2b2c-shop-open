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
package com.sicheng.admin.sys.web;

import com.sicheng.admin.sys.entity.SysVariable;

import com.sicheng.admin.sys.service.SysVariableService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统变量管理 Controller
 * 所属模块：sys
 *
 * @author 张加利
 * @version 2017-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysVariable")
public class SysVariableController extends BaseController {

    @Autowired
    private SysVariableService sysVariableService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "080132";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param sysVariable 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysVariable:view")
    @RequestMapping(value = "list")
    public String list(SysVariable sysVariable, HttpServletRequest request, HttpServletResponse response, Model model) {
    	Wrapper wrapper=new Wrapper();
    	wrapper.setEntity(sysVariable);
    	wrapper.orderBy("name");
        Page<SysVariable> page = sysVariableService.selectByWhere(new Page<SysVariable>(request, response), wrapper);
        model.addAttribute("page", page);
        return "admin/sys/sysVariableList";
    }

    /**
     * 进入保存页面
     *
     * @param sysVariable 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysVariable:save")
    @RequestMapping(value = "save1")
    public String save1(SysVariable sysVariable, Model model) {
        if (sysVariable == null) {
            sysVariable = new SysVariable();
        }
        model.addAttribute("sysVariable", sysVariable);
        return "admin/sys/sysVariableForm";
    }

    /**
     * 执行保存
     *
     * @param sysVariable        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysVariable:save")
    @RequestMapping(value = "save2")
    public String save2(SysVariable sysVariable, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        String oldName = R.get("oldName");//已添加的变量名
        List<String> errorList = validate(sysVariable, oldName, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysVariable, model);//回显错误提示
        }
        //变量2有值
        if (StringUtils.isNotBlank(sysVariable.getValueClob())) {
            String html_unsafe = StringEscapeUtils.unescapeHtml4(sysVariable.getValueClob());//转回来（还原）
            sysVariable.setValueClob(html_unsafe);
        }
        //业务处理
        sysVariableService.insertSelective(sysVariable);
        addMessage(redirectAttributes, FYUtils.fyParams("保存系统变量成功"));
        return "redirect:" + adminPath + "/sys/sysVariable/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysVariable 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysVariable:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysVariable sysVariable, Model model) {
        SysVariable entity = null;
        if (sysVariable != null) {
            if (sysVariable.getId() != null) {
                entity = sysVariableService.selectById(sysVariable.getId());
            }
        }
        model.addAttribute("sysVariable", entity);
        return "admin/sys/sysVariableForm";
    }

    /**
     * 执行编辑
     *
     * @param sysVariable        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysVariable:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysVariable sysVariable, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        String oldName = R.get("oldName");//已添加的变量名
        List<String> errorList = validate(sysVariable, oldName, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysVariable, model);//回显错误提示
        }
        //变量2有值
        if (StringUtils.isNotBlank(sysVariable.getValueClob())) {
            String html_unsafe = StringEscapeUtils.unescapeHtml4(sysVariable.getValueClob());//转回来（还原）
            sysVariable.setValueClob(html_unsafe);
        }
        //业务处理
        sysVariableService.updateByIdSelective(sysVariable);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑系统变量成功"));
        return "redirect:" + adminPath + "/sys/sysVariable/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysVariable        实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysVariable:drop")
    @RequestMapping(value = "delete")
    public String delete(SysVariable sysVariable, RedirectAttributes redirectAttributes) {
        sysVariableService.deleteById(sysVariable.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除系统变量成功"));
        return "redirect:" + adminPath + "/sys/sysVariable/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysVariable 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysVariable sysVariable, String oldName, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("变量名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name"))) {
            if (R.get("name").length() > 64) {
                errorList.add(FYUtils.fyParams("变量名最大长度不能超过64字符"));
            }
            SysVariable variable = new SysVariable();
            variable.setName(R.get("name"));
            List<SysVariable> list = sysVariableService.selectByWhere(new Wrapper(variable));
            if (StringUtils.isBlank(oldName)) {
                if (list.size() > 0) {
                    errorList.add(FYUtils.fyParams("变量名已存在"));
                }
            } else if (!R.get("name").equals(oldName)) {
                if (list.size() > 0) {
                    errorList.add(FYUtils.fyParams("模板编号已存在"));
                }
            }

        }
        if (StringUtils.isNotBlank(R.get("value")) && R.get("value").length() > 1024) {
            errorList.add(FYUtils.fyParams("变量值1最大长度不能超过1024字符"));
        }
        if (StringUtils.isNotBlank(R.get("bewrite")) && R.get("bewrite").length() > 255) {
            errorList.add(FYUtils.fyParams("描述最大长度不能超过255字符"));
        }
        return errorList;
    }

    /**
     * 验证变量名是否存在
     *
     * @param oldName 库中变量名
     * @param name    输入的变量名
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "exitName")
    public String exitNume(String oldName, String name) {
        if (StringUtils.isNotBlank(name)) {
            if (name.equals(oldName)) {
                return "true";
            } else {
                SysVariable sysVariable = new SysVariable();
                sysVariable.setName(name);
                List<SysVariable> list = sysVariableService.selectByWhere(new Wrapper(sysVariable));
                if (list.isEmpty()) {
                    return "true";
                } else {
                    return "false";
                }
            }
        }
        return "false";
    }

}