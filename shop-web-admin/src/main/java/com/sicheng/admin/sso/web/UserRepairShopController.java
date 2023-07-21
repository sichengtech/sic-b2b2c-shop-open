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
package com.sicheng.admin.sso.web;

import com.sicheng.admin.sso.entity.UserRepairShop;
import com.sicheng.admin.sso.service.UserRepairShopService;

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
 * 汽车服务门店 Controller
 * 所属模块：sso
 *
 * @author 蔡龙
 * @version 2017-07-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sso/userRepairShop")
public class UserRepairShopController extends BaseController {

    @Autowired
    private UserRepairShopService userRepairShopService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "050110";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param userRepairShop 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userRepairShop:view")
    @RequestMapping(value = "list")
    public String list(UserRepairShop userRepairShop, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserRepairShop> page = userRepairShopService.selectByWhere(new Page<UserRepairShop>(request, response), new Wrapper(userRepairShop));
        model.addAttribute("page", page);
        return "admin/sso/userRepairShopList";
    }

    /**
     * 进入保存页面
     *
     * @param userRepairShop 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userRepairShop:save")
    @RequestMapping(value = "save1")
    public String save1(UserRepairShop userRepairShop, Model model) {
        if (userRepairShop == null) {
            userRepairShop = new UserRepairShop();
        }
        model.addAttribute("userRepairShop", userRepairShop);
        return "admin/sso/userRepairShopForm";
    }

    /**
     * 执行保存
     *
     * @param userRepairShop     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userRepairShop:save")
    @RequestMapping(value = "save2")
    public String save2(UserRepairShop userRepairShop, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userRepairShop, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(userRepairShop, model);//回显错误提示
        }

        //业务处理
        userRepairShopService.insertSelective(userRepairShop);
        addMessage(redirectAttributes, FYUtils.fyParams("保存汽车服务门店成功"));
        return "redirect:" + adminPath + "/sso/userRepairShop/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param userRepairShop 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userRepairShop:edit")
    @RequestMapping(value = "edit1")
    public String edit1(UserRepairShop userRepairShop, Model model) {
        UserRepairShop entity = null;
        if (userRepairShop != null) {
            if (userRepairShop.getId() != null) {
                entity = userRepairShopService.selectById(userRepairShop.getId());
            }
        }
        model.addAttribute("userRepairShop", entity);
        return "admin/sso/userRepairShopForm";
    }

    /**
     * 执行编辑
     *
     * @param userRepairShop     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userRepairShop:edit")
    @RequestMapping(value = "edit2")
    public String edit2(UserRepairShop userRepairShop, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userRepairShop, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(userRepairShop, model);//回显错误提示
        }

        //业务处理
        userRepairShopService.updateByIdSelective(userRepairShop);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑汽车服务门店成功"));
        return "redirect:" + adminPath + "/sso/userRepairShop/list.do?repage";
    }

    /**
     * 删除
     *
     * @param userRepairShop     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userRepairShop:drop")
    @RequestMapping(value = "delete")
    public String delete(UserRepairShop userRepairShop, RedirectAttributes redirectAttributes) {
        userRepairShopService.deleteById(userRepairShop.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除汽车服务门店成功"));
        return "redirect:" + adminPath + "/sso/userRepairShop/list.do?repage";
    }

    /**
     * 进入审核页面
     *
     * @param userRepairShop     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userRepairShop:auth")
    @RequestMapping(value = "auth1")
    public String auth1(UserRepairShop userRepairShop, Model model) {
        UserRepairShop entity = null;
        if (userRepairShop != null) {
            if (userRepairShop.getId() != null) {
                entity = userRepairShopService.selectById(userRepairShop.getId());
            }
        }
        model.addAttribute("userRepairShop", entity);
        return "admin/sso/userRepairShopAuth";
    }

    /**
     * 保存审核
     *
     * @param userRepairShop     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userRepairShop:auth")
    @RequestMapping(value = "auth2")
    public String auth2(UserRepairShop userRepairShop, RedirectAttributes redirectAttributes, Model model) {
        //表单验证
        List<String> errorList = validate(userRepairShop, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return auth1(userRepairShop, model);//回显错误提示
        }
        //业务处理
        userRepairShopService.auth(userRepairShop);
        addMessage(redirectAttributes, FYUtils.fyParams("审核汽车服务门店成功"));
        return "redirect:" + adminPath + "/sso/userRepairShop/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param userRepairShop 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(UserRepairShop userRepairShop, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("authContent"))) {
            errorList.add(FYUtils.fyParams("请填写审核意见"));
        }
        if (StringUtils.isNotBlank(R.get("authContent")) && R.get("authContent").length() > 255) {
            errorList.add(FYUtils.fyParams("审核意见最大长度不能超过19字符"));
        }
        return errorList;
    }

}