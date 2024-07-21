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

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.sso.service.UserSellerService;

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
 * 会员（卖家） Controller
 * 所属模块：sso
 *
 * @author cl
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sso/userSeller")
public class UserSellerController extends BaseController {

    @Autowired
    private UserSellerService userSellerService;



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
     * @param userSeller 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userSeller:view")
    @RequestMapping(value = "list")
    public String list(UserSeller userSeller, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserSeller> page = userSellerService.selectByWhere(new Page<UserSeller>(request, response), new Wrapper(userSeller));
        model.addAttribute("page", page);
        return "admin/sso/userSellerList";
    }

    /**
     * 进入保存页面
     *
     * @param userSeller 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "save1")
    public String save1(UserSeller userSeller, Model model) {
        if (userSeller == null) {
            userSeller = new UserSeller();
        }
        model.addAttribute("userSeller", userSeller);
        return "admin/sso/userSellerForm";
    }

    /**
     * 执行保存
     *
     * @param userSeller         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "save2")
    public String save2(UserSeller userSeller, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userSeller, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(userSeller, model);//回显错误提示
        }

        //业务处理
        userSellerService.insertSelective(userSeller);
        addMessage(redirectAttributes, "保存会员（卖家）成功");
        return "redirect:" + adminPath + "/sso/userSeller/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param userSeller 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "edit1")
    public String edit1(UserSeller userSeller, Model model) {
        UserSeller entity = null;
        if (userSeller != null) {
            if (userSeller.getId() != null) {
                entity = userSellerService.selectById(userSeller.getId());
            }
        }
        model.addAttribute("userSeller", entity);
        return "admin/sso/userSellerForm";
    }

    /**
     * 执行编辑
     *
     * @param userSeller         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "edit2")
    public String edit2(UserSeller userSeller, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userSeller, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(userSeller, model);//回显错误提示
        }

        //业务处理
        userSellerService.updateByIdSelective(userSeller);
        addMessage(redirectAttributes, "编辑会员（卖家）成功");
        return "redirect:" + adminPath + "/sso/userSeller/list.do?repage";
    }

    /**
     * 删除
     *
     * @param userSeller         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "delete")
    public String delete(UserSeller userSeller, RedirectAttributes redirectAttributes) {
        userSellerService.deleteById(userSeller.getId());
        addMessage(redirectAttributes, "删除会员（卖家）成功");
        return "redirect:" + adminPath + "/sso/userSeller/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param userSeller 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(UserSeller userSeller, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("uId"))) {
            errorList.add("主键(卖家表，入驻申请用1个id)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19) {
            errorList.add("主键(卖家表，入驻申请用1个id)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("isOpen"))) {
            errorList.add("是否已完成开店:0否、1是（入驻申请二审审核通过之后修改为1）不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isOpen")) && R.get("isOpen").length() > 1) {
            errorList.add("是否已完成开店:0否、1是（入驻申请二审审核通过之后修改为1）最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 19) {
            errorList.add("店铺id(店铺表，店铺二级域名，店铺相册空间信息表用1个id)最大长度不能超过19字符");
        }
        return errorList;
    }

}