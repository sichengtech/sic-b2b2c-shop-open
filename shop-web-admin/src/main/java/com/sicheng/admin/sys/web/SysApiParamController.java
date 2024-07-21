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

import com.sicheng.admin.sys.entity.SysApiParam;

import com.sicheng.admin.sys.service.SysApiParamService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口参数 Controller
 * 所属模块：sys
 *
 * @author fxx
 * @version 2018-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysApiParam")
public class SysApiParamController extends BaseController {

    @Autowired
    private SysApiParamService sysApiParamService;



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
     * @param sysApiParam 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysApiParam:view")
    @RequestMapping(value = "list")
    public String list(String apiId, Model model) {
        if (StringUtils.isBlank(apiId) || !StringUtils.isNumeric(apiId)) {
            return "admin/sys/sysApiParamList";
        }
        SysApiParam sysApiParam = new SysApiParam();
        sysApiParam.setApiId(Long.parseLong(apiId));
        List<SysApiParam> sysApiParamList = sysApiParamService.selectByWhere(new Wrapper(sysApiParam));
        model.addAttribute("sysApiParamList", sysApiParamList);
        return "admin/sys/sysApiParamList";
    }

    /**
     * 进入保存页面
     *
     * @param sysApiParam 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysApiParam:save")
    @RequestMapping(value = "save1")
    public String save1(SysApiParam sysApiParam, Model model) {
        if (sysApiParam == null) {
            sysApiParam = new SysApiParam();
        }
        model.addAttribute("sysApiParam", sysApiParam);
        return "admin/sys/sysApiParamForm";
    }

    /**
     * 执行保存
     *
     * @param sysApiParam        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysApiParam:save")
    @RequestMapping(value = "save2")
    public String save2(SysApiParam sysApiParam, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysApiParam, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysApiParam, model);//回显错误提示
        }

        //业务处理
        sysApiParamService.insertSelective(sysApiParam);
        addMessage(redirectAttributes, "保存接口参数成功");
        return "redirect:" + adminPath + "/sys/sysApiParam/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysApiParam 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysApiParam:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysApiParam sysApiParam, Model model) {
        SysApiParam entity = null;
        if (sysApiParam != null) {
            if (sysApiParam.getId() != null) {
                entity = sysApiParamService.selectById(sysApiParam.getId());
            }
        }
        model.addAttribute("sysApiParam", entity);
        return "admin/sys/sysApiParamForm";
    }

    /**
     * 执行编辑
     *
     * @param sysApiParam        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysApiParam:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysApiParam sysApiParam, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysApiParam, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysApiParam, model);//回显错误提示
        }

        //业务处理
        sysApiParamService.updateByIdSelective(sysApiParam);
        addMessage(redirectAttributes, "编辑接口参数成功");
        return "redirect:" + adminPath + "/sys/sysApiParam/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysApiParam        实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysApiParam:drop")
    @RequestMapping(value = "delete")
    public String delete(SysApiParam sysApiParam, RedirectAttributes redirectAttributes) {
        sysApiParamService.deleteById(sysApiParam.getId());
        addMessage(redirectAttributes, "删除接口参数成功");
        return "redirect:" + adminPath + "/sys/sysApiParam/list.do?repage";
    }

    /**
     * 根据接口id获取接口参数
     *
     * @param apiId 接口id
     * @return
     */
    @RequiresPermissions("sys:sysApiParam:view")
    @ResponseBody
    @RequestMapping(value = "paramList")
    public Map<String, Object> selectParamByApiId(String apiId) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(apiId)) {
            map.put("message", "接口id不能为空");
            map.put("stauts", false);
            return map;
        }
        if (!StringUtils.isNumeric(apiId)) {
            map.put("message", "接口id只能为数字");
            map.put("status", false);
            return map;
        }
        SysApiParam sysApiParam = new SysApiParam();
        sysApiParam.setApiId(Long.parseLong(apiId));
        List<SysApiParam> sysApiParamList = sysApiParamService.selectByWhere(new Wrapper(sysApiParam));
        map.put("message", "查询参数成功");
        map.put("status", true);
        map.put("data", sysApiParamList);
        return map;
    }

    /**
     * 表单验证
     *
     * @param sysApiParam 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysApiParam sysApiParam, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("apiParamId"))) {
            errorList.add("参数id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("apiParamId")) && R.get("apiParamId").length() > 19) {
            errorList.add("参数id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("apiId"))) {
            errorList.add("所属接口id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("apiId")) && R.get("apiId").length() > 19) {
            errorList.add("所属接口id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("参数名不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("参数名最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add("参数(java)类型不能为空");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("参数(java)类型最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("isRequired"))) {
            errorList.add("是否必填不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isRequired")) && R.get("isRequired").length() > 1) {
            errorList.add("是否必填最大长度不能超过1字符");
        }
        return errorList;
    }

}