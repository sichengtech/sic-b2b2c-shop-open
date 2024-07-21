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

import com.sicheng.admin.sys.entity.SysSmsServer;

import com.sicheng.admin.sys.service.SysSmsServerService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.sms.SmsSender;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理短信的发送 Controller
 * 所属模块：sys
 *
 * @author zjl
 * @version 2017-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysSmsServer")
public class SysSmsServerController extends BaseController {

    @Autowired
    private SysSmsServerService sysSmsServerService;



    @Autowired
    private SmsSender smsSender;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {

        String menu3 = "080128";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param sysSmsServer 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysSmsServer:view")
    @RequestMapping(value = "list")
    public String list(SysSmsServer sysSmsServer, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysSmsServer> page = sysSmsServerService.selectByWhere(new Page<SysSmsServer>(request, response), new Wrapper(sysSmsServer));
        model.addAttribute("page", page);
        return "admin/sys/sysSmsServerList";
    }

    /**
     * 进入保存页面
     *
     * @param sysSmsServer 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysSmsServer:edit")
    @RequestMapping(value = "save1")
    public String save1(SysSmsServer sysSmsServer, Model model) {
        if (sysSmsServer == null) {
            sysSmsServer = new SysSmsServer();
        }
        model.addAttribute("sysSmsServer", sysSmsServer);
        return "admin/sys/sysSmsServerForm";
    }

    /**
     * 执行保存
     *
     * @param sysSmsServer       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysSmsServer:edit")
    @RequestMapping(value = "save2")
    public String save2(SysSmsServer sysSmsServer, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysSmsServer, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysSmsServer, model);//回显错误提示
        }

        //业务处理
        sysSmsServerService.insertSelective(sysSmsServer);
        addMessage(redirectAttributes, FYUtils.fyParams("保存短信通道成功"));
        return "redirect:" + adminPath + "/sys/sysSmsServer/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysSmsServer 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysSmsServer:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysSmsServer sysSmsServer, Model model) {
        SysSmsServer entity = null;
        if (sysSmsServer != null) {
            if (sysSmsServer.getId() != null) {
                entity = sysSmsServerService.selectById(sysSmsServer.getId());
            }
        }
        if (entity == null) {
            //从库中查出ID最小的一个
            Page<SysSmsServer> page = new Page<SysSmsServer>();
            page.setOrderBy("id asc");//按ID排序
            SysSmsServer smsServer = new SysSmsServer();
            page = sysSmsServerService.selectByWhere(page, new Wrapper(smsServer));
            if (page.getList().size() > 0) {
                entity = page.getList().get(0);//取ID最小的一个
            }
        }
        //初始化一条数据
        if (entity == null) {
            //向表中插入一条记录
            entity = new SysSmsServer();
            entity.setStatus("1");//状态，0停用，1启用
            sysSmsServerService.insertSelective(entity);
        }
        model.addAttribute("sysSmsServer", entity);
        return "admin/sys/sysSmsServerForm";
    }

    /**
     * 执行编辑
     *
     * @param sysSmsServer       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysSmsServer:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysSmsServer sysSmsServer, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysSmsServer, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysSmsServer, model);//回显错误提示
        }

        //业务处理
        sysSmsServerService.updateByIdSelective(sysSmsServer);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑短信通道成功"));
        return "redirect:" + adminPath + "/sys/sysSmsServer/edit1.do";
    }

    /**
     * 删除
     *
     * @param sysSmsServer       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysSmsServer:edit")
    @RequestMapping(value = "delete")
    public String delete(SysSmsServer sysSmsServer, RedirectAttributes redirectAttributes) {
        sysSmsServerService.deleteById(sysSmsServer.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除短信通道成功"));
        return "redirect:" + adminPath + "/sys/sysSmsServer/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysSmsServer 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysSmsServer sysSmsServer, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add(FYUtils.fyParams("状态不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add(FYUtils.fyParams("状态最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("url")) && R.get("url").length() > 255) {
            errorList.add(FYUtils.fyParams("短信网关URL最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("username")) && R.get("username").length() > 64) {
            errorList.add(FYUtils.fyParams("用户名最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("pwd")) && R.get("pwd").length() > 64) {
            errorList.add(FYUtils.fyParams("密码最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("accessKey")) && R.get("accessKey").length() > 64) {
            errorList.add(FYUtils.fyParams("ACCESS KEY最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("appId")) && R.get("appId").length() > 64) {
            errorList.add(FYUtils.fyParams("APP ID最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("clientId")) && R.get("clientId").length() > 64) {
            errorList.add(FYUtils.fyParams("客户id最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("token")) && R.get("token").length() > 64) {
            errorList.add(FYUtils.fyParams("令牌最大长度不能超过64字符"));
        }
        return errorList;
    }

    @ResponseBody
    @RequestMapping(value = "smsTest")
    public Map<String, String> smsTest() {
        Map<String, String> map = new HashMap<>();
        String phone = R.get("phone");
        String paramString = R.get("text");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code", "1234");
        boolean result = smsSender.sendSmsMessage(phone, paramString, paramMap, "activateAccount", false);
        if (result) {
            map.put("status", "1");//邮件发送成功
        } else {
            map.put("status", "0");//邮件发送失败
        }
        return map;
    }

}