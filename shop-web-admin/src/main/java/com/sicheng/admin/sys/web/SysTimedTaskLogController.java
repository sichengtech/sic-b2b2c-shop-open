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

import com.sicheng.admin.sys.entity.SysTimedTask;
import com.sicheng.admin.sys.entity.SysTimedTaskLog;

import com.sicheng.admin.sys.service.SysTimedTaskLogService;
import com.sicheng.admin.sys.service.SysTimedTaskService;
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
 * 查看定时任务的日志 Controller
 * 所属模块：sys
 *
 * @author 张加利
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysTimedTaskLog")
public class SysTimedTaskLogController extends BaseController {

    @Autowired
    private SysTimedTaskLogService sysTimedTaskLogService;

    @Autowired
    private SysTimedTaskService sysTimedTaskService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "080112";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysTimedTaskLog:view")
    @RequestMapping(value = "list")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        SysTimedTaskLog sysTimedTaskLog = new SysTimedTaskLog();
        Long sttId = R.getLong("sttId");
        sysTimedTaskLog.setSttId(sttId);
        //定时任务日志
        Page<SysTimedTaskLog> page = sysTimedTaskLogService.selectByWhere(new Page<SysTimedTaskLog>(request, response), new Wrapper(sysTimedTaskLog));
        //定时任务
        SysTimedTask timedTask = sysTimedTaskService.selectById(sttId);
        //定时任务名称
        String taskName = timedTask.getTaskName();
        model.addAttribute("page", page);
        model.addAttribute("taskName", taskName);
        return "admin/sys/sysTimedTaskLogList";
    }

    /**
     * 进入保存页面
     *
     * @param sysTimedTaskLog 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysTimedTaskLog:edit")
    @RequestMapping(value = "save1")
    public String save1(SysTimedTaskLog sysTimedTaskLog, Model model) {
        if (sysTimedTaskLog == null) {
            sysTimedTaskLog = new SysTimedTaskLog();
        }
        model.addAttribute("sysTimedTaskLog", sysTimedTaskLog);
        return "admin/sys/sysTimedTaskLogForm";
    }

    /**
     * 执行保存
     *
     * @param sysTimedTaskLog    实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysTimedTaskLog:edit")
    @RequestMapping(value = "save2")
    public String save2(SysTimedTaskLog sysTimedTaskLog, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysTimedTaskLog, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysTimedTaskLog, model);//回显错误提示
        }

        //业务处理
        sysTimedTaskLogService.insertSelective(sysTimedTaskLog);
        addMessage(redirectAttributes, "保存定时任务日志成功");
        return "redirect:" + adminPath + "/sys/sysTimedTaskLog/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysTimedTaskLog 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysTimedTaskLog:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysTimedTaskLog sysTimedTaskLog, Model model) {
        SysTimedTaskLog entity = null;
        if (sysTimedTaskLog != null) {
            if (sysTimedTaskLog.getId() != null) {
                entity = sysTimedTaskLogService.selectById(sysTimedTaskLog.getId());
            }
        }
        model.addAttribute("sysTimedTaskLog", entity);
        return "admin/sys/sysTimedTaskLogForm";
    }

    /**
     * 执行编辑
     *
     * @param sysTimedTaskLog    实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysTimedTaskLog:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysTimedTaskLog sysTimedTaskLog, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysTimedTaskLog, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysTimedTaskLog, model);//回显错误提示
        }

        //业务处理
        sysTimedTaskLogService.updateByIdSelective(sysTimedTaskLog);
        addMessage(redirectAttributes, "编辑定时任务日志成功");
        return "redirect:" + adminPath + "/sys/sysTimedTaskLog/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysTimedTaskLog    实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysTimedTaskLog:edit")
    @RequestMapping(value = "delete")
    public String delete(SysTimedTaskLog sysTimedTaskLog, RedirectAttributes redirectAttributes) {
        sysTimedTaskLogService.deleteById(sysTimedTaskLog.getId());
        addMessage(redirectAttributes, "删除定时任务日志成功");
        return "redirect:" + adminPath + "/sys/sysTimedTaskLog/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysTimedTaskLog 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysTimedTaskLog sysTimedTaskLog, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isNotBlank(R.get("result")) && R.get("result").length() > 64) {
            errorList.add("执行结果最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 64) {
            errorList.add("执行状态最大长度不能超过64字符");
        }
        return errorList;
    }

}