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

import com.sicheng.admin.sys.entity.SysEmailServer;

import com.sicheng.admin.sys.service.SysEmailServerService;
import com.sicheng.common.email.EmailSender;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理邮件的发送 Controller
 * 所属模块：sys
 *
 * @author 张加利
 * @version 2017-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysEmailServer")
public class SysEmailServerController extends BaseController {

    @Autowired
    private SysEmailServerService sysEmailServerService;

    @Autowired
    private EmailSender emailSender;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "080125";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param sysEmailServer 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysEmailServer:view")
    @RequestMapping(value = "list")
    public String list(SysEmailServer sysEmailServer, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysEmailServer> page = sysEmailServerService.selectByWhere(new Page<SysEmailServer>(request, response), new Wrapper(sysEmailServer));
        model.addAttribute("page", page);
        return "admin/sys/sysEmailServerList";
    }

    /**
     * 进入保存页面
     *
     * @param sysEmailServer 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysEmailServer:edit")
    @RequestMapping(value = "save1")
    public String save1(SysEmailServer sysEmailServer, Model model) {
        if (sysEmailServer == null) {
            sysEmailServer = new SysEmailServer();
        }
        model.addAttribute("sysEmailServer", sysEmailServer);
        return "admin/sys/sysEmailServerForm";
    }

    /**
     * 执行保存
     *
     * @param sysEmailServer     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysEmailServer:edit")
    @RequestMapping(value = "save2")
    public String save2(SysEmailServer sysEmailServer, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysEmailServer, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysEmailServer, model);//回显错误提示
        }

        //业务处理
        sysEmailServerService.insertSelective(sysEmailServer);
        addMessage(redirectAttributes, FYUtils.fyParams("保存邮件通道成功"));
        return "redirect:" + adminPath + "/sys/sysEmailServer/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysEmailServer 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysEmailServer:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysEmailServer sysEmailServer, Model model) {
        SysEmailServer entity = null;
        if (sysEmailServer != null) {
            if (sysEmailServer.getId() != null) {
                entity = sysEmailServerService.selectById(sysEmailServer.getId());
            }
        }
        if (entity == null) {
            //从库中查出ID最小的一个
            entity = sysEmailServerService.selectEmailSet();
        }
        //初始化一条数据
        if (entity == null) {
            //向表中插入一条记录
            entity = new SysEmailServer();
            entity.setStatus("1");//状态，0停用，1启用
            sysEmailServerService.insertSelective(entity);
        }
        model.addAttribute("sysEmailServer", entity);
        return "admin/sys/sysEmailServerForm";
    }

    /**
     * 执行编辑
     *
     * @param sysEmailServer     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysEmailServer:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysEmailServer sysEmailServer, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysEmailServer, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysEmailServer, model);//回显错误提示
        }

        //业务处理
        sysEmailServerService.updateByIdSelective(sysEmailServer);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑邮件通道成功"));
        return "redirect:" + adminPath + "/sys/sysEmailServer/edit1.do";
    }

    /**
     * 删除
     *
     * @param sysEmailServer     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysEmailServer:edit")
    @RequestMapping(value = "delete")
    public String delete(SysEmailServer sysEmailServer, RedirectAttributes redirectAttributes) {
        sysEmailServerService.deleteById(sysEmailServer.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除邮件通道成功"));
        return "redirect:" + adminPath + "/sys/sysEmailServer/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysEmailServer 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysEmailServer sysEmailServer, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add(FYUtils.fyParams("状态不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add(FYUtils.fyParams("状态最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("sender")) && R.get("sender").length() > 64) {
            errorList.add(FYUtils.fyParams("发件人展示名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("smtp")) && R.get("smtp").length() > 64) {
            errorList.add(FYUtils.fyParams("SMTP服务器地址最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("port")) && R.get("port").length() > 64) {
            errorList.add(FYUtils.fyParams("SMTP服务器端口最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("username")) && R.get("username").length() > 64) {
            errorList.add(FYUtils.fyParams("SMTP用户名最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("pwd")) && R.get("pwd").length() > 64) {
            errorList.add(FYUtils.fyParams("SMTP密码最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("safe")) && R.get("safe").length() > 64) {
            errorList.add(FYUtils.fyParams("安全连接最大长度不能超过64字符"));
        }
        return errorList;
    }

    /**
     * 发出测试邮件，用于验证邮件通道是否配置正确
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "emailTest")
    public Map<String, String> emailTest() {
        String toMail = R.get("email");//收件人邮箱地址
        String sender = FYUtils.fyParams("测试邮件发送者名称"); //发送者名称
        String subject = FYUtils.fyParams("这是一封测试邮件"); //邮件标题
        String text = FYUtils.fyParams("发送的测试邮件正文");//邮件正文
        boolean async = false;//在测试业务中请使用false,在正式业务中请使用true
        boolean result = false;
        if (StringUtils.isBlank(toMail)) {
            Map<String, String> map = new HashMap<>();
            map.put("status", "0");//测试邮件发送失败
            map.put("msg", "收件人邮箱地址为空，无法发送测试邮件");//提示信息
            return map;
        }
        //发出测试邮件
        Map<String, String> res = emailSender.send( toMail, subject, text, async);
        return res;
    }
}