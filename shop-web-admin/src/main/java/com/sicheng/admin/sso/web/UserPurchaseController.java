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
package com.sicheng.admin.sso.web;

import com.sicheng.admin.sso.entity.UserPurchase;
import com.sicheng.admin.sso.service.UserPurchaseService;

import com.sicheng.common.fileStorage.AccessKey;
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
 * 采购商 Controller
 * 所属模块：sso
 *
 * @author cl
 * @version 2017-07-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sso/userPurchase")
public class UserPurchaseController extends BaseController {

    @Autowired
    private UserPurchaseService userPurchaseService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "050111";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param userPurchase 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userPurchase:view")
    @RequestMapping(value = "list")
    public String list(UserPurchase userPurchase, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserPurchase> page = userPurchaseService.selectByWhere(new Page<UserPurchase>(request, response), new Wrapper(userPurchase));
        model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
        model.addAttribute("page", page);
        return "admin/sso/userPurchaseList";
    }

    /**
     * 进入保存页面
     *
     * @param userPurchase 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userPurchase:save")
    @RequestMapping(value = "save1")
    public String save1(UserPurchase userPurchase, Model model) {
        if (userPurchase == null) {
            userPurchase = new UserPurchase();
        }
        model.addAttribute("userPurchase", userPurchase);
        return "admin/sso/userPurchaseForm";
    }

    /**
     * 执行保存
     *
     * @param userPurchase       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userPurchase:save")
    @RequestMapping(value = "save2")
    public String save2(UserPurchase userPurchase, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userPurchase, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(userPurchase, model);//回显错误提示
        }

        //业务处理
        userPurchaseService.insertSelective(userPurchase);
        addMessage(redirectAttributes, FYUtils.fyParams("保存采购商成功"));
        return "redirect:" + adminPath + "/sso/userPurchase/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param userPurchase 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userPurchase:edit")
    @RequestMapping(value = "edit1")
    public String edit1(UserPurchase userPurchase, Model model) {
        UserPurchase entity = null;
        if (userPurchase != null) {
            if (userPurchase.getId() != null) {
                entity = userPurchaseService.selectById(userPurchase.getId());
            }
        }
        model.addAttribute("userPurchase", entity);
        return "admin/sso/userPurchaseForm";
    }

    /**
     * 执行编辑
     *
     * @param userPurchase       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userPurchase:edit")
    @RequestMapping(value = "edit2")
    public String edit2(UserPurchase userPurchase, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userPurchase, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(userPurchase, model);//回显错误提示
        }

        //业务处理
        userPurchaseService.updateByIdSelective(userPurchase);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑采购商成功"));
        return "redirect:" + adminPath + "/sso/userPurchase/list.do?repage";
    }

    /**
     * 删除
     *
     * @param userPurchase       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userPurchase:drop")
    @RequestMapping(value = "delete")
    public String delete(UserPurchase userPurchase, RedirectAttributes redirectAttributes) {
        userPurchaseService.deleteById(userPurchase.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除采购商成功"));
        return "redirect:" + adminPath + "/sso/userPurchase/list.do?repage";
    }

    /**
     * 进入审核页面
     *
     * @param useRepairShop      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userPurchase:auth")
    @RequestMapping(value = "auth1")
    public String auth1(UserPurchase userPurchase, Model model) {
        UserPurchase entity = null;
        if (userPurchase != null) {
            if (userPurchase.getId() != null) {
                entity = userPurchaseService.selectById(userPurchase.getId());
            }
        }
        model.addAttribute("userPurchase", entity);
        return "admin/sso/userPurchaseAuth";
    }

    /**
     * 保存审核
     *
     * @param useRepairShop      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userPurchase:auth")
    @RequestMapping(value = "auth2")
    public String auth2(UserPurchase userPurchase, RedirectAttributes redirectAttributes, Model model) {
        //表单验证
        List<String> errorList = validate(userPurchase, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return auth1(userPurchase, model);//回显错误提示
        }
        //业务处理
        userPurchaseService.auth(userPurchase);
        addMessage(redirectAttributes, FYUtils.fyParams("审核汽车服务门店成功"));
        return "redirect:" + adminPath + "/sso/userPurchase/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param userPurchase 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(UserPurchase userPurchase, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("authContent"))) {
            errorList.add(FYUtils.fyParams("审核意见不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("authContent")) && R.get("authContent").length() > 255) {
            errorList.add(FYUtils.fyParams("审核意见最大长度不能超过255字符"));
        }
        return errorList;
    }

}