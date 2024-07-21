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

import com.sicheng.admin.sys.entity.SysToken;

import com.sicheng.admin.sys.service.SysTokenService;
import com.sicheng.admin.sys.utils.TokenUtils;
import com.sicheng.common.mapper.JsonMapper;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * token表 Controller
 * 所属模块：sys
 *
 * @author cl
 * @version 2017-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysToken")
public class SysTokenController extends BaseController {

    @Autowired
    private SysTokenService sysTokenService;



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
     * @param sysToken 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysToken:view")
    @RequestMapping(value = "list")
    public String list(SysToken sysToken, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysToken> page = sysTokenService.selectByWhere(new Page<SysToken>(request, response), new Wrapper(sysToken));
        model.addAttribute("page", page);
        return "admin/sys/sysTokenList";
    }

    /**
     * 进入保存页面
     *
     * @param sysToken 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysToken:edit")
    @RequestMapping(value = "save1")
    public String save1(SysToken sysToken, Model model) {
        if (sysToken == null) {
            sysToken = new SysToken();
        }
        model.addAttribute("sysToken", sysToken);
        return "admin/sys/sysTokenForm";
    }

    /**
     * 执行保存
     *
     * @param sysToken           实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysToken:edit")
    @RequestMapping(value = "save2")
    public String save2(SysToken sysToken, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysToken, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysToken, model);//回显错误提示
        }

        //业务处理
        sysTokenService.insertSelective(sysToken);
        addMessage(redirectAttributes, "保存token成功");
        return "redirect:" + adminPath + "/sys/sysToken/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysToken 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysToken:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysToken sysToken, Model model) {
        SysToken entity = null;
        if (sysToken != null) {
            if (sysToken.getId() != null) {
                entity = sysTokenService.selectById(sysToken.getId());
            }
        }
        model.addAttribute("sysToken", entity);
        return "admin/sys/sysTokenForm";
    }

    /**
     * 执行编辑
     *
     * @param sysToken           实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysToken:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysToken sysToken, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysToken, model);
        if (errorList.size() == 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(sysToken, model);//回显错误提示
        }

        //业务处理
        sysTokenService.updateByIdSelective(sysToken);
        addMessage(redirectAttributes, "编辑token成功");
        return "redirect:" + adminPath + "/sys/sysToken/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysToken           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysToken:edit")
    @RequestMapping(value = "delete")
    public String delete(SysToken sysToken, RedirectAttributes redirectAttributes) {
        sysTokenService.deleteById(sysToken.getId());
        addMessage(redirectAttributes, "删除token成功");
        return "redirect:" + adminPath + "/sys/sysToken/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param sysToken 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysToken sysToken, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("tId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("tId")) && R.get("tId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("userId")) && R.get("userId").length() > 19) {
            errorList.add("用户id(公用上传不需要存值；激活邮箱需要存值)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("token"))) {
            errorList.add("令牌不能为空");
        }
        if (StringUtils.isNotBlank(R.get("token")) && R.get("token").length() > 64) {
            errorList.add("令牌最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add("业务类型：10.公用上传20.用户激活邮箱不能为空");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 4) {
            errorList.add("业务类型：10.公用上传20.用户激活邮箱最大长度不能超过4字符");
        }
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add("状态：0.失效1.有效不能为空");
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add("状态：0.失效1.有效最大长度不能超过1字符");
        }
        return errorList;
    }

    /**
     * 管理后台系统获取token(令牌)
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "getToken")
    public Object getToken() {
        SysToken sysToken = TokenUtils.generateToken(null, "10");
        PrintWriter print;
        try {

            HttpServletRequest request = R.getRequest();
            HttpServletResponse response = R.getResponse();
            response.setContentType("application/json");//发送json数据需要设置contentType
            print = response.getWriter();
            String jsonp = request.getParameter("callback");//callback为回调函数名，要和js名保持一致
            String text = "";
            if (jsonp != null) {
                text = jsonp + "({\"token\":" + "'" + sysToken.getToken() + "'" + "})";
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("token", sysToken.getToken());
                text = JsonMapper.toJsonString(map);
            }
            print.write(text);
            print.close();
        } catch (IOException e) {
            logger.debug("获取token错误", e);
        }
        return null;
    }

}