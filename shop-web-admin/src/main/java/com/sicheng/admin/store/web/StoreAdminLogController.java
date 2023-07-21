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

import com.sicheng.admin.store.entity.StoreAdminLog;
import com.sicheng.admin.store.service.StoreAdminLogService;

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
 * 店铺管理员操作日志 Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeAdminLog")
public class StoreAdminLogController extends BaseController {

    @Autowired
    private StoreAdminLogService storeAdminLogService;



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
     * @param storeAdminLog 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAdminLog:view")
    @RequestMapping(value = "list")
    public String list(StoreAdminLog storeAdminLog, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreAdminLog> page = storeAdminLogService.selectByWhere(new Page<StoreAdminLog>(request, response), new Wrapper(storeAdminLog));
        model.addAttribute("page", page);
        return "admin/store/storeAdminLogList";
    }

    /**
     * 进入保存页面
     *
     * @param storeAdminLog 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAdminLog:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreAdminLog storeAdminLog, Model model) {
        if (storeAdminLog == null) {
            storeAdminLog = new StoreAdminLog();
        }
        model.addAttribute("storeAdminLog", storeAdminLog);
        return "admin/store/storeAdminLogForm";
    }

    /**
     * 执行保存
     *
     * @param storeAdminLog      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAdminLog:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreAdminLog storeAdminLog, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeAdminLog, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeAdminLog, model);//回显错误提示
        }

        //业务处理
        storeAdminLogService.insertSelective(storeAdminLog);
        addMessage(redirectAttributes, "保存店铺管理员操作日志成功");
        return "redirect:" + adminPath + "/store/storeAdminLog/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeAdminLog 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeAdminLog:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreAdminLog storeAdminLog, Model model) {
        StoreAdminLog entity = null;
        if (storeAdminLog != null) {
            if (storeAdminLog.getId() != null) {
                entity = storeAdminLogService.selectById(storeAdminLog.getId());
            }
        }
        model.addAttribute("storeAdminLog", entity);
        return "admin/store/storeAdminLogForm";
    }

    /**
     * 执行编辑
     *
     * @param storeAdminLog      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAdminLog:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreAdminLog storeAdminLog, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeAdminLog, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeAdminLog, model);//回显错误提示
        }

        //业务处理
        storeAdminLogService.updateByIdSelective(storeAdminLog);
        addMessage(redirectAttributes, "编辑店铺管理员操作日志成功");
        return "redirect:" + adminPath + "/store/storeAdminLog/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeAdminLog      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeAdminLog:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreAdminLog storeAdminLog, RedirectAttributes redirectAttributes) {
        storeAdminLogService.deleteById(storeAdminLog.getId());
        addMessage(redirectAttributes, "删除店铺管理员操作日志成功");
        return "redirect:" + adminPath + "/store/storeAdminLog/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeAdminLog 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreAdminLog storeAdminLog, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("logId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("logId")) && R.get("logId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("sellerId"))) {
            errorList.add("关联(卖家表id)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("关联(卖家表id)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("日志类型最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("title")) && R.get("title").length() > 512) {
            errorList.add("日志标题（操作菜单）最大长度不能超过512字符");
        }
        if (StringUtils.isNotBlank(R.get("remoteAddr")) && R.get("remoteAddr").length() > 255) {
            errorList.add("操作用户的IP地址最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("userAgent")) && R.get("userAgent").length() > 255) {
            errorList.add("操作用户代理信息最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("requestUri")) && R.get("requestUri").length() > 255) {
            errorList.add("操作的URI最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("method")) && R.get("method").length() > 64) {
            errorList.add("操作的方式(提交方式)最大长度不能超过64字符");
        }
        return errorList;
    }

}