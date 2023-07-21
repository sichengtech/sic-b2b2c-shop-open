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

import com.sicheng.admin.sso.entity.UserMerchantInfo;
import com.sicheng.admin.sso.service.UserMerchantInfoService;

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
 * 商家注册信息 Controller
 * 所属模块：sso
 *
 * @author 张加利
 * @version 2017-09-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sso/userMerchantInfo")
public class UserMerchantInfoController extends BaseController {

    @Autowired
    private UserMerchantInfoService userMerchantInfoService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040103";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param userMerchantInfo 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userMerchantInfo:view")
    @RequestMapping(value = "list")
    public String list(UserMerchantInfo userMerchantInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserMerchantInfo> page = userMerchantInfoService.selectByWhere(new Page<UserMerchantInfo>(request, response), new Wrapper(userMerchantInfo));
        model.addAttribute("page", page);
        return "admin/sso/userMerchantInfoList";
    }

    /**
     * 进入保存页面
     *
     * @param userMerchantInfo 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userMerchantInfo:save")
    @RequestMapping(value = "save1")
    public String save1(UserMerchantInfo userMerchantInfo, Model model) {
        if (userMerchantInfo == null) {
            userMerchantInfo = new UserMerchantInfo();
        }
        model.addAttribute("userMerchantInfo", userMerchantInfo);
        return "admin/sso/userMerchantInfoForm";
    }

    /**
     * 执行保存
     *
     * @param userMerchantInfo   实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userMerchantInfo:save")
    @RequestMapping(value = "save2")
    public String save2(UserMerchantInfo userMerchantInfo, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userMerchantInfo, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(userMerchantInfo, model);//回显错误提示
        }

        //业务处理
        userMerchantInfoService.insertSelective(userMerchantInfo);
        addMessage(redirectAttributes, FYUtils.fyParams("保存商家注册信息成功"));
        return "redirect:" + adminPath + "/sso/userMerchantInfo/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param userMerchantInfo 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:userMerchantInfo:edit")
    @RequestMapping(value = "edit1")
    public String edit1(UserMerchantInfo userMerchantInfo, Model model) {
        UserMerchantInfo entity = null;
        if (userMerchantInfo != null) {
            if (userMerchantInfo.getId() != null) {
                entity = userMerchantInfoService.selectById(userMerchantInfo.getId());
            }
        }
        model.addAttribute("userMerchantInfo", entity);
        return "admin/sso/userMerchantInfoForm";
    }

    /**
     * 执行编辑
     *
     * @param userMerchantInfo   实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userMerchantInfo:edit")
    @RequestMapping(value = "edit2")
    public String edit2(UserMerchantInfo userMerchantInfo, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userMerchantInfo, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(userMerchantInfo, model);//回显错误提示
        }

        //业务处理
        userMerchantInfoService.updateByIdSelective(userMerchantInfo);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑商家注册信息成功"));
        return "redirect:" + adminPath + "/sso/userMerchantInfo/list.do?repage";
    }

    /**
     * 删除
     *
     * @param userMerchantInfo   实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userMerchantInfo:drop")
    @RequestMapping(value = "delete")
    public String delete(UserMerchantInfo userMerchantInfo, RedirectAttributes redirectAttributes) {
        userMerchantInfoService.deleteById(userMerchantInfo.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除商家注册信息成功"));
        return "redirect:" + adminPath + "/sso/userMerchantInfo/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param userMerchantInfo 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(UserMerchantInfo userMerchantInfo, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("uId"))) {
            errorList.add(FYUtils.fyParams("主键,会员的ID不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19) {
            errorList.add(FYUtils.fyParams("主键,会员的ID最大长度不能超过19字符"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 2) {
            errorList.add(FYUtils.fyParams("企业类型（字典）最大长度不能超过2字符"));
        }
        if (StringUtils.isNotBlank(R.get("industry")) && R.get("industry").length() > 2) {
            errorList.add(FYUtils.fyParams("行业属性（字典）最大长度不能超过2字符"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("公司名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("introduce")) && R.get("introduce").length() > 512) {
            errorList.add(FYUtils.fyParams("公司介绍最大长度不能超过512字符"));
        }
        if (StringUtils.isNotBlank(R.get("countryId")) && R.get("countryId").length() > 19) {
            errorList.add(FYUtils.fyParams("公司所在地国家(关联地区表)最大长度不能超过19字符"));
        }
        if (StringUtils.isNotBlank(R.get("countryName")) && R.get("countryName").length() > 64) {
            errorList.add(FYUtils.fyParams("公司所在地国家名字最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("provinceId")) && R.get("provinceId").length() > 19) {
            errorList.add(FYUtils.fyParams("公司所在地省(关联地区表)最大长度不能超过19字符"));
        }
        if (StringUtils.isNotBlank(R.get("provinceName")) && R.get("provinceName").length() > 64) {
            errorList.add(FYUtils.fyParams("公司所在地省名字最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("cityId")) && R.get("cityId").length() > 19) {
            errorList.add(FYUtils.fyParams("公司所在地市(关联地区表)最大长度不能超过19字符"));
        }
        if (StringUtils.isNotBlank(R.get("cityName")) && R.get("cityName").length() > 64) {
            errorList.add(FYUtils.fyParams("公司所在地市名字最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("districtId")) && R.get("districtId").length() > 19) {
            errorList.add(FYUtils.fyParams("公司所在地县(关联地区表)最大长度不能超过19字符"));
        }
        if (StringUtils.isNotBlank(R.get("districtName")) && R.get("districtName").length() > 64) {
            errorList.add(FYUtils.fyParams("公司县名字最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("detailedAddress")) && R.get("detailedAddress").length() > 128) {
            errorList.add(FYUtils.fyParams("公司详细地址最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("customCall")) && R.get("customCall").length() > 32) {
            errorList.add(FYUtils.fyParams("客服电话最大长度不能超过32字符"));
        }
        if (StringUtils.isNotBlank(R.get("companyWebsite")) && R.get("companyWebsite").length() > 32) {
            errorList.add(FYUtils.fyParams("公司网址最大长度不能超过32字符"));
        }
        if (StringUtils.isNotBlank(R.get("companyBrand")) && R.get("companyBrand").length() > 64) {
            errorList.add(FYUtils.fyParams("公司品牌最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("department")) && R.get("department").length() > 2) {
            errorList.add(FYUtils.fyParams("所在部门最大长度不能超过2字符"));
        }
        if (StringUtils.isNotBlank(R.get("contacts")) && R.get("contacts").length() > 32) {
            errorList.add(FYUtils.fyParams("联系人最大长度不能超过32字符"));
        }
        if (StringUtils.isNotBlank(R.get("contactsTelephone")) && R.get("contactsTelephone").length() > 64) {
            errorList.add(FYUtils.fyParams("联系人电话最大长度不能超过64字符"));
        }
        return errorList;
    }

}