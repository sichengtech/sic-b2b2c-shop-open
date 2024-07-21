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

import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sso.service.UserMemberService;

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
 * 会员（买家） Controller
 * 所属模块：sso
 *
 * @author cl
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sso/userMember")
public class UserMemberController extends BaseController {

    @Autowired
    private UserMemberService userMemberService;



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
     * @param userMember 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userMember:view")
    @RequestMapping(value = "list")
    public String list(UserMember userMember, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserMember> page = userMemberService.selectByWhere(new Page<UserMember>(request, response), new Wrapper(userMember));
        model.addAttribute("page", page);
        return "admin/sso/userMemberList";
    }

    /**
     * 进入保存页面
     *
     * @param userMember 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userMember:edit")
    @RequestMapping(value = "save1")
    public String save1(UserMember userMember, Model model) {
        if (userMember == null) {
            userMember = new UserMember();
        }
        model.addAttribute("userMember", userMember);
        return "admin/sso/userMemberForm";
    }

    /**
     * 执行保存
     *
     * @param userMember         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userMember:edit")
    @RequestMapping(value = "save2")
    public String save2(UserMember userMember, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userMember, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(userMember, model);//回显错误提示
        }

        //业务处理
        userMemberService.insertSelective(userMember);
        addMessage(redirectAttributes, "保存会员（买家）成功");
        return "redirect:" + adminPath + "/sso/userMember/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param userMember 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userMember:edit")
    @RequestMapping(value = "edit1")
    public String edit1(UserMember userMember, Model model) {
        UserMember entity = null;
        if (userMember != null) {
            if (userMember.getId() != null) {
                entity = userMemberService.selectById(userMember.getId());
            }
        }
        model.addAttribute("userMember", entity);
        return "admin/sso/userMemberForm";
    }

    /**
     * 执行编辑
     *
     * @param userMember         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userMember:edit")
    @RequestMapping(value = "edit2")
    public String edit2(UserMember userMember, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userMember, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(userMember, model);//回显错误提示
        }

        //业务处理
        userMemberService.updateByIdSelective(userMember);
        addMessage(redirectAttributes, "编辑会员（买家）成功");
        return "redirect:" + adminPath + "/sso/userMember/list.do?repage";
    }

    /**
     * 删除
     *
     * @param userMember         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userMember:edit")
    @RequestMapping(value = "delete")
    public String delete(UserMember userMember, RedirectAttributes redirectAttributes) {
        userMemberService.deleteById(userMember.getId());
        addMessage(redirectAttributes, "删除会员（买家）成功");
        return "redirect:" + adminPath + "/sso/userMember/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param userMember 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(UserMember userMember, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("uId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("point"))) {
            errorList.add("积分（积分规则设置送的积分数）不能为空");
        }
        if (StringUtils.isNotBlank(R.get("point")) && R.get("point").length() > 64) {
            errorList.add("积分（积分规则设置送的积分数）最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("isBuy"))) {
            errorList.add("是否允许购买(0不允许、1允许)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isBuy")) && R.get("isBuy").length() > 1) {
            errorList.add("是否允许购买(0不允许、1允许)最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("balance"))) {
            errorList.add("预存款，账户余额不能为空");
        }
        if (StringUtils.isNotBlank(R.get("balance")) && R.get("balance").length() > 12) {
            errorList.add("预存款，账户余额最大长度不能超过12字符");
        }
        if (StringUtils.isBlank(R.get("frozenMoney"))) {
            errorList.add("冻结金额不能为空");
        }
        if (StringUtils.isNotBlank(R.get("frozenMoney")) && R.get("frozenMoney").length() > 12) {
            errorList.add("冻结金额最大长度不能超过12字符");
        }
        if (StringUtils.isNotBlank(R.get("paymentPassword")) && R.get("paymentPassword").length() > 64) {
            errorList.add("支付密码最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("memberTagId")) && R.get("memberTagId").length() > 19) {
            errorList.add("会员标签（给会员打标）(关联member_tag表)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("realName")) && R.get("realName").length() > 64) {
            errorList.add("真实姓名最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("headPicPath")) && R.get("headPicPath").length() > 64) {
            errorList.add("会员头像path最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("sex"))) {
            errorList.add("性别(1男、2女、3保密)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sex")) && R.get("sex").length() > 1) {
            errorList.add("性别(1男、2女、3保密)最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("postcode")) && R.get("postcode").length() > 64) {
            errorList.add("邮编最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("countryId")) && R.get("countryId").length() > 19) {
            errorList.add("国家(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("countryName")) && R.get("countryName").length() > 64) {
            errorList.add("国家名字最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("provinceId")) && R.get("provinceId").length() > 19) {
            errorList.add("省(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("provinceName")) && R.get("provinceName").length() > 64) {
            errorList.add("省名字最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("cityId")) && R.get("cityId").length() > 19) {
            errorList.add("市(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("cityName")) && R.get("cityName").length() > 64) {
            errorList.add("市名字最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("districtId")) && R.get("districtId").length() > 19) {
            errorList.add("县(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("districtName")) && R.get("districtName").length() > 64) {
            errorList.add("县名字最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("detailedAddress")) && R.get("detailedAddress").length() > 255) {
            errorList.add("详细地址最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("qq")) && R.get("qq").length() > 64) {
            errorList.add("qq号最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("microblog")) && R.get("microblog").length() > 64) {
            errorList.add("微博最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("weChat")) && R.get("weChat").length() > 64) {
            errorList.add("微信最大长度不能超过64字符");
        }
        return errorList;
    }

}