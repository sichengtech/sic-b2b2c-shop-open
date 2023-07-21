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

import com.sicheng.admin.sys.service.SysTimedTaskService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理定时任务 Controller
 * 所属模块：sys
 *
 * @author 张加利
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysTimedTask")
public class SysTimedTaskController extends BaseController {

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
     * @param sysTimedTask 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysTimedTask:view")
    @RequestMapping(value = "list")
    public String list(SysTimedTask sysTimedTask, HttpServletRequest request, HttpServletResponse response, Model model) {
        Wrapper w = new Wrapper();
        w.orderBy("timed_task_num");//按编号排序
        w.setEntity(sysTimedTask);
        Page<SysTimedTask> page = sysTimedTaskService.selectByWhere(new Page<SysTimedTask>(request, response), w);
        model.addAttribute("page", page);
        return "admin/sys/sysTimedTaskList";
    }

    /**
     * 进入保存页面
     *
     * @param sysTimedTask 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysTimedTask:save")
    @RequestMapping(value = "save1")
    public String save1(SysTimedTask sysTimedTask, Model model) {
        if (sysTimedTask == null) {
            sysTimedTask = new SysTimedTask();
        }
        model.addAttribute("sysTimedTask", sysTimedTask);
        return "admin/sys/sysTimedTaskForm";
    }

    /**
     * 执行保存
     *
     * @param sysTimedTask       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysTimedTask:save")
    @RequestMapping(value = "save2")
    public String save2(SysTimedTask sysTimedTask, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysTimedTask, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysTimedTask, model);//回显错误提示
        }

        //业务处理
        sysTimedTaskService.insertSelective(sysTimedTask);
        addMessage(redirectAttributes, FYUtils.fyParams("保存定时任务成功"));
        return "redirect:" + adminPath + "/sys/sysTimedTask/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysTimedTask 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysTimedTask:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysTimedTask sysTimedTask, Model model) {
        SysTimedTask entity = null;
        if (sysTimedTask != null) {
            if (sysTimedTask.getId() != null) {
                entity = sysTimedTaskService.selectById(sysTimedTask.getId());
            }
        }
        model.addAttribute("sysTimedTask", entity);
        return "admin/sys/sysTimedTaskForm";
    }

    /**
     * 执行编辑
     *
     * @param sysTimedTask       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysTimedTask:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysTimedTask sysTimedTask, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysTimedTask, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysTimedTask, model);//回显错误提示
        }

        //业务处理
        sysTimedTaskService.updateByIdSelective(sysTimedTask);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑定时任务成功"));
        return "redirect:" + adminPath + "/sys/sysTimedTask/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysTimedTask       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysTimedTask:drop")
    @RequestMapping(value = "delete")
    public String delete(SysTimedTask sysTimedTask, RedirectAttributes redirectAttributes) {
        sysTimedTaskService.deleteById(sysTimedTask.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除定时任务成功"));
        return "redirect:" + adminPath + "/sys/sysTimedTask/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysTimedTask 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysTimedTask sysTimedTask, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("taskName"))) {
            errorList.add(FYUtils.fyParams("任务名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("taskName")) && R.get("taskName").length() > 64) {
            errorList.add(FYUtils.fyParams("任务名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("taskExplain")) && R.get("taskExplain").length() > 64) {
            errorList.add(FYUtils.fyParams("任务说明最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("taskTime"))) {
            errorList.add(FYUtils.fyParams("执行时间不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("taskTime")) && R.get("taskTime").length() > 64) {
            errorList.add(FYUtils.fyParams("执行时间最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("timeExplain")) && R.get("timeExplain").length() > 64) {
            errorList.add(FYUtils.fyParams("执行时间说明最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add(FYUtils.fyParams("状态最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("timedTaskNum"))) {
            errorList.add(FYUtils.fyParams("编号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("timedTaskNum")) && R.get("timedTaskNum").length() > 10) {
            errorList.add(FYUtils.fyParams("编号最大长度不能超过10字符"));
        }
        return errorList;
    }

    /**
     * 验证定时任务编号的唯一性
     *
     * @param oldNum       数据库的编号
     * @param timedTaskNum 输入的编号
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "exitNum")
    public String exitRoleName(String oldNum, String timedTaskNum) {
        if (StringUtils.isNotBlank(timedTaskNum)) {
            if (oldNum.equals(timedTaskNum)) {
                return "true";
            } else {
                SysTimedTask sysTimedTask = new SysTimedTask();
                sysTimedTask.setTimedTaskNum(Integer.valueOf(timedTaskNum));
                List<SysTimedTask> sysTimedTasks = sysTimedTaskService.selectByWhere(new Wrapper(sysTimedTask));
                if (sysTimedTasks.isEmpty()) {
                    return "true";
                } else {
                    return "false";
                }
            }
        }
        return "false";
    }

}