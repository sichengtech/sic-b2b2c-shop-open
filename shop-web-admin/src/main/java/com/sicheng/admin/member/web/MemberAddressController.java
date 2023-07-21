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
package com.sicheng.admin.member.web;

import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.member.service.MemberAddressService;

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
 * 买家收货地址 Controller
 * 所属模块：member
 *
 * @author 蔡龙
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberAddress")
public class MemberAddressController extends BaseController {

    @Autowired
    private MemberAddressService memberAddressService;



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
     * @param memberAddress 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("member:memberAddress:view")
    @RequestMapping(value = "list")
    public String list(MemberAddress memberAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MemberAddress> page = memberAddressService.selectByWhere(new Page<MemberAddress>(request, response), new Wrapper(memberAddress));
        model.addAttribute("page", page);
        return "admin/member/memberAddressList";
    }

    /**
     * 进入保存页面
     *
     * @param memberAddress 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("member:memberAddress:edit")
    @RequestMapping(value = "save1")
    public String save1(MemberAddress memberAddress, Model model) {
        if (memberAddress == null) {
            memberAddress = new MemberAddress();
        }
        model.addAttribute("memberAddress", memberAddress);
        return "admin/member/memberAddressForm";
    }

    /**
     * 执行保存
     *
     * @param memberAddress      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("member:memberAddress:edit")
    @RequestMapping(value = "save2")
    public String save2(MemberAddress memberAddress, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(memberAddress, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(memberAddress, model);//回显错误提示
        }

        //业务处理
        memberAddressService.insertSelective(memberAddress);
        addMessage(redirectAttributes, "保存买家收货地址成功");
        return "redirect:" + adminPath + "/member/memberAddress/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param memberAddress 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("member:memberAddress:edit")
    @RequestMapping(value = "edit1")
    public String edit1(MemberAddress memberAddress, Model model) {
        MemberAddress entity = null;
        if (memberAddress != null) {
            if (memberAddress.getId() != null) {
                entity = memberAddressService.selectById(memberAddress.getId());
            }
        }
        model.addAttribute("memberAddress", entity);
        return "admin/member/memberAddressForm";
    }

    /**
     * 执行编辑
     *
     * @param memberAddress      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("member:memberAddress:edit")
    @RequestMapping(value = "edit2")
    public String edit2(MemberAddress memberAddress, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(memberAddress, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(memberAddress, model);//回显错误提示
        }

        //业务处理
        memberAddressService.updateByIdSelective(memberAddress);
        addMessage(redirectAttributes, "编辑买家收货地址成功");
        return "redirect:" + adminPath + "/member/memberAddress/list.do?repage";
    }

    /**
     * 删除
     *
     * @param memberAddress      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("member:memberAddress:edit")
    @RequestMapping(value = "delete")
    public String delete(MemberAddress memberAddress, RedirectAttributes redirectAttributes) {
        memberAddressService.deleteById(memberAddress.getId());
        addMessage(redirectAttributes, "删除买家收货地址成功");
        return "redirect:" + adminPath + "/member/memberAddress/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param memberAddress 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(MemberAddress memberAddress, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("addressId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("addressId")) && R.get("addressId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("mId"))) {
            errorList.add("关联(会员表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("mId")) && R.get("mId").length() > 19) {
            errorList.add("关联(会员表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("收货人不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("收货人最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("countryId"))) {
            errorList.add("国家(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("countryId")) && R.get("countryId").length() > 19) {
            errorList.add("国家(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("countryName"))) {
            errorList.add("国家名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("countryName")) && R.get("countryName").length() > 64) {
            errorList.add("国家名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("provinceId"))) {
            errorList.add("省(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("provinceId")) && R.get("provinceId").length() > 19) {
            errorList.add("省(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("provinceName"))) {
            errorList.add("省名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("provinceName")) && R.get("provinceName").length() > 64) {
            errorList.add("省名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("cityId"))) {
            errorList.add("市(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("cityId")) && R.get("cityId").length() > 19) {
            errorList.add("市(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("cityName"))) {
            errorList.add("市名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("cityName")) && R.get("cityName").length() > 64) {
            errorList.add("市名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("districtId"))) {
            errorList.add("县(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("districtId")) && R.get("districtId").length() > 19) {
            errorList.add("县(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("districtName"))) {
            errorList.add("县名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("districtName")) && R.get("districtName").length() > 64) {
            errorList.add("县名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("detailedAddress"))) {
            errorList.add("详细地址不能为空");
        }
        if (StringUtils.isNotBlank(R.get("detailedAddress")) && R.get("detailedAddress").length() > 255) {
            errorList.add("详细地址最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("mobile"))) {
            errorList.add("联系方式（手机，电话号码）不能为空");
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && R.get("mobile").length() > 64) {
            errorList.add("联系方式（手机，电话号码）最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("zipCode")) && R.get("zipCode").length() > 64) {
            errorList.add("邮编最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("isDefault"))) {
            errorList.add("是否默认(0不默认 1默认)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isDefault")) && R.get("isDefault").length() > 1) {
            errorList.add("是否默认(0不默认 1默认)最大长度不能超过1字符");
        }
        return errorList;
    }

}