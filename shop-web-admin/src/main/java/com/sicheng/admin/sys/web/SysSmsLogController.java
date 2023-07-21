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

import com.sicheng.admin.sys.entity.SysSmsLog;

import com.sicheng.admin.sys.service.SysSmsLogService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
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
 * 记录发送短信情况 Controller
 * 所属模块：sys
 *
 * @author 张加利
 * @version 2017-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysSmsLog")
public class SysSmsLogController extends BaseController {

    @Autowired
    private SysSmsLogService sysSmsLogService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "080129";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param sysSmsLog 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysSmsLog:view")
    @RequestMapping(value = "list")
    public String list(SysSmsLog sysSmsLog, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysSmsLog> page = sysSmsLogService.selectByWhere(new Page<SysSmsLog>(request, response), new Wrapper(sysSmsLog));
        model.addAttribute("page", page);
        return "admin/sys/sysSmsLogList";
    }

    /**
     * 进入保存页面
     *
     * @param sysSmsLog 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysSmsLog:edit")
    @RequestMapping(value = "save1")
    public String save1(SysSmsLog sysSmsLog, Model model) {
        if (sysSmsLog == null) {
            sysSmsLog = new SysSmsLog();
        }
        model.addAttribute("sysSmsLog", sysSmsLog);
        return "admin/sys/sysSmsLogForm";
    }

    /**
     * 执行保存
     *
     * @param sysSmsLog          实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysSmsLog:edit")
    @RequestMapping(value = "save2")
    public String save2(SysSmsLog sysSmsLog, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysSmsLog, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysSmsLog, model);//回显错误提示
        }

        //业务处理
        sysSmsLogService.insertSelective(sysSmsLog);
        addMessage(redirectAttributes, FYUtils.fyParams("保存短信日志成功"));
        return "redirect:" + adminPath + "/sys/sysSmsLog/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysSmsLog 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysSmsLog:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysSmsLog sysSmsLog, Model model) {
        SysSmsLog entity = null;
        if (sysSmsLog != null) {
            if (sysSmsLog.getId() != null) {
                entity = sysSmsLogService.selectById(sysSmsLog.getId());
            }
        }
        model.addAttribute("sysSmsLog", entity);
        return "admin/sys/sysSmsLogForm";
    }

    /**
     * 执行编辑
     *
     * @param sysSmsLog          实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysSmsLog:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysSmsLog sysSmsLog, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysSmsLog, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysSmsLog, model);//回显错误提示
        }

        //业务处理
        sysSmsLogService.updateByIdSelective(sysSmsLog);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑短信日志成功"));
        return "redirect:" + adminPath + "/sys/sysSmsLog/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysSmsLog          实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysSmsLog:edit")
    @RequestMapping(value = "delete")
    public String delete(SysSmsLog sysSmsLog, RedirectAttributes redirectAttributes) {
        sysSmsLogService.deleteById(sysSmsLog.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除短信日志成功"));
        return "redirect:" + adminPath + "/sys/sysSmsLog/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysSmsLog 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysSmsLog sysSmsLog, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("sslId"))) {
            errorList.add(FYUtils.fyParams("主键不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sslId")) && R.get("sslId").length() > 19) {
            errorList.add(FYUtils.fyParams("主键最大长度不能超过19字符"));
        }
        if (StringUtils.isNotBlank(R.get("content")) && R.get("content").length() > 255) {
            errorList.add(FYUtils.fyParams("短信内容最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("templatecode")) && R.get("templatecode").length() > 128) {
            errorList.add(FYUtils.fyParams("模板id最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add(FYUtils.fyParams("发送状态最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("bewrite")) && R.get("bewrite").length() > 128) {
            errorList.add(FYUtils.fyParams("描述最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add(FYUtils.fyParams("短信网关类型最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("sendDate"))) {
            errorList.add(FYUtils.fyParams("发送时间不能为空"));
        }
        return errorList;
    }

}