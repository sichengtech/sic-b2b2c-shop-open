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

import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.admin.store.service.StoreEnterService;

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
 * 入驻申请（业务查看） Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeEnter")
public class StoreEnterController extends BaseController {

    @Autowired
    private StoreEnterService storeEnterService;



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
     * @param storeEnter 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeEnter:view")
    @RequestMapping(value = "list")
    public String list(StoreEnter storeEnter, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreEnter> page = storeEnterService.selectByWhere(new Page<StoreEnter>(request, response), new Wrapper(storeEnter));
        model.addAttribute("page", page);
        return "admin/store/storeEnterList";
    }

    /**
     * 进入保存页面
     *
     * @param storeEnter 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeEnter:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreEnter storeEnter, Model model) {
        if (storeEnter == null) {
            storeEnter = new StoreEnter();
        }
        model.addAttribute("storeEnter", storeEnter);
        return "admin/store/storeEnterForm";
    }

    /**
     * 执行保存
     *
     * @param storeEnter         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeEnter:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreEnter storeEnter, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeEnter, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeEnter, model);//回显错误提示
        }

        //业务处理
        storeEnterService.insertSelective(storeEnter);
        addMessage(redirectAttributes, "保存入驻申请（业务查看）成功");
        return "redirect:" + adminPath + "/store/storeEnter/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeEnter 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeEnter:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreEnter storeEnter, Model model) {
        StoreEnter entity = null;
        if (storeEnter != null) {
            if (storeEnter.getId() != null) {
                entity = storeEnterService.selectById(storeEnter.getId());
            }
        }
        model.addAttribute("storeEnter", entity);
        return "admin/store/storeEnterForm";
    }

    /**
     * 执行编辑
     *
     * @param storeEnter         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeEnter:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreEnter storeEnter, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeEnter, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeEnter, model);//回显错误提示
        }

        //业务处理
        storeEnterService.updateByIdSelective(storeEnter);
        addMessage(redirectAttributes, "编辑入驻申请（业务查看）成功");
        return "redirect:" + adminPath + "/store/storeEnter/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeEnter         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeEnter:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreEnter storeEnter, RedirectAttributes redirectAttributes) {
        storeEnterService.deleteById(storeEnter.getId());
        addMessage(redirectAttributes, "删除入驻申请（业务查看）成功");
        return "redirect:" + adminPath + "/store/storeEnter/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeEnter 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreEnter storeEnter, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("enterId"))) {
            errorList.add("入驻ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("enterId")) && R.get("enterId").length() > 19) {
            errorList.add("入驻ID最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add("申请状态不能为空");
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 2) {
            errorList.add("申请状态最大长度不能超过2字符");
        }
        if (StringUtils.isBlank(R.get("isPerfect"))) {
            errorList.add("入驻信息一审是否完善，0不完善、1完善不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isPerfect")) && R.get("isPerfect").length() > 1) {
            errorList.add("入驻信息一审是否完善，0不完善、1完善最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("companyName"))) {
            errorList.add("公司名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("companyName")) && R.get("companyName").length() > 64) {
            errorList.add("公司名称最大长度不能超过64字符");
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
            errorList.add("公司_详细地址不能为空");
        }
        if (StringUtils.isNotBlank(R.get("detailedAddress")) && R.get("detailedAddress").length() > 255) {
            errorList.add("公司_详细地址最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("companyPhone"))) {
            errorList.add("公司电话不能为空");
        }
        if (StringUtils.isNotBlank(R.get("companyPhone")) && R.get("companyPhone").length() > 64) {
            errorList.add("公司电话最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("staffCount"))) {
            errorList.add("员工人数不能为空");
        }
        if (StringUtils.isNotBlank(R.get("staffCount")) && R.get("staffCount").length() > 10) {
            errorList.add("员工人数最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("registeredCapital"))) {
            errorList.add("注册资金，单位万元不能为空");
        }
        if (StringUtils.isNotBlank(R.get("registeredCapital")) && R.get("registeredCapital").length() > 10) {
            errorList.add("注册资金，单位万元最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("contact"))) {
            errorList.add("联系人不能为空");
        }
        if (StringUtils.isNotBlank(R.get("contact")) && R.get("contact").length() > 64) {
            errorList.add("联系人最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("contactNumber"))) {
            errorList.add("联系电话不能为空");
        }
        if (StringUtils.isNotBlank(R.get("contactNumber")) && R.get("contactNumber").length() > 64) {
            errorList.add("联系电话最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("qq"))) {
            errorList.add("QQ不能为空");
        }
        if (StringUtils.isNotBlank(R.get("qq")) && R.get("qq").length() > 64) {
            errorList.add("QQ最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("email"))) {
            errorList.add("电子邮箱不能为空");
        }
        if (StringUtils.isNotBlank(R.get("email")) && R.get("email").length() > 64) {
            errorList.add("电子邮箱最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("sellerLicense"))) {
            errorList.add("营业执照号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerLicense")) && R.get("sellerLicense").length() > 64) {
            errorList.add("营业执照号最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("sellerLicensePath"))) {
            errorList.add("营业执照电子版图片路径不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerLicensePath")) && R.get("sellerLicensePath").length() > 64) {
            errorList.add("营业执照电子版图片路径最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("sellerScope"))) {
            errorList.add("法定经营范围不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerScope")) && R.get("sellerScope").length() > 255) {
            errorList.add("法定经营范围最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("organizationCode"))) {
            errorList.add("组织机构代码不能为空");
        }
        if (StringUtils.isNotBlank(R.get("organizationCode")) && R.get("organizationCode").length() > 64) {
            errorList.add("组织机构代码最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("organizationCodePath"))) {
            errorList.add("组织机构代码电子版图片路径不能为空");
        }
        if (StringUtils.isNotBlank(R.get("organizationCodePath")) && R.get("organizationCodePath").length() > 64) {
            errorList.add("组织机构代码电子版图片路径最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("generalTaxpayerPath"))) {
            errorList.add("一般人纳税证明电子版图片路径不能为空");
        }
        if (StringUtils.isNotBlank(R.get("generalTaxpayerPath")) && R.get("generalTaxpayerPath").length() > 64) {
            errorList.add("一般人纳税证明电子版图片路径最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("accountName"))) {
            errorList.add("银行开户名不能为空");
        }
        if (StringUtils.isNotBlank(R.get("accountName")) && R.get("accountName").length() > 255) {
            errorList.add("银行开户名最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("bankAccount"))) {
            errorList.add("银行账号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankAccount")) && R.get("bankAccount").length() > 64) {
            errorList.add("银行账号最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("bankName"))) {
            errorList.add("开户银行支行名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankName")) && R.get("bankName").length() > 255) {
            errorList.add("开户银行支行名称最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("branchLineContact"))) {
            errorList.add("支行联行号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("branchLineContact")) && R.get("branchLineContact").length() > 64) {
            errorList.add("支行联行号最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("bankCountryId"))) {
            errorList.add("开户银行国家(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankCountryId")) && R.get("bankCountryId").length() > 19) {
            errorList.add("开户银行国家(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("bankCountryName"))) {
            errorList.add("开户银行国家名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankCountryName")) && R.get("bankCountryName").length() > 64) {
            errorList.add("开户银行国家名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("bankProvinceId"))) {
            errorList.add("开户银行省(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankProvinceId")) && R.get("bankProvinceId").length() > 19) {
            errorList.add("开户银行省(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("bankProvinceName"))) {
            errorList.add("开户银行省名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankProvinceName")) && R.get("bankProvinceName").length() > 64) {
            errorList.add("开户银行省名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("bankCityId"))) {
            errorList.add("开户银行市(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankCityId")) && R.get("bankCityId").length() > 19) {
            errorList.add("开户银行市(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("bankCityName"))) {
            errorList.add("开户银行市名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankCityName")) && R.get("bankCityName").length() > 64) {
            errorList.add("开户银行市名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("bankDistrictId"))) {
            errorList.add("开户银行县(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankDistrictId")) && R.get("bankDistrictId").length() > 19) {
            errorList.add("开户银行县(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("bankDistrictName"))) {
            errorList.add("开户银行县名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("bankDistrictName")) && R.get("bankDistrictName").length() > 64) {
            errorList.add("开户银行县名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("districtImgPath"))) {
            errorList.add("开户银行许可证电子版不能为空");
        }
        if (StringUtils.isNotBlank(R.get("districtImgPath")) && R.get("districtImgPath").length() > 64) {
            errorList.add("开户银行许可证电子版最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("settlementAccountName"))) {
            errorList.add("结算账号银行开户名不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementAccountName")) && R.get("settlementAccountName").length() > 64) {
            errorList.add("结算账号银行开户名最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("settlementBankAccount"))) {
            errorList.add("结算账号银行账号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementBankAccount")) && R.get("settlementBankAccount").length() > 64) {
            errorList.add("结算账号银行账号最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("settlementBankName"))) {
            errorList.add("结算账号开户银行支行名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementBankName")) && R.get("settlementBankName").length() > 64) {
            errorList.add("结算账号开户银行支行名称最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("settlementBranchLineContact"))) {
            errorList.add("结算账号支行联行号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementBranchLineContact")) && R.get("settlementBranchLineContact").length() > 64) {
            errorList.add("结算账号支行联行号最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("settlementCountryId"))) {
            errorList.add("结算账号国家(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementCountryId")) && R.get("settlementCountryId").length() > 19) {
            errorList.add("结算账号国家(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("settlementCountryName"))) {
            errorList.add("结算账号国家名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementCountryName")) && R.get("settlementCountryName").length() > 64) {
            errorList.add("结算账号国家名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("settlementProvinceId"))) {
            errorList.add("结算账号省(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementProvinceId")) && R.get("settlementProvinceId").length() > 19) {
            errorList.add("结算账号省(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("settlementProvinceName"))) {
            errorList.add("结算账号省名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementProvinceName")) && R.get("settlementProvinceName").length() > 64) {
            errorList.add("结算账号省名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("settlementCityId"))) {
            errorList.add("结算账号市(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementCityId")) && R.get("settlementCityId").length() > 19) {
            errorList.add("结算账号市(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("settlementCityName"))) {
            errorList.add("结算账号市名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementCityName")) && R.get("settlementCityName").length() > 64) {
            errorList.add("结算账号市名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("settlementDistrictId"))) {
            errorList.add("结算账号县(关联地区表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementDistrictId")) && R.get("settlementDistrictId").length() > 19) {
            errorList.add("结算账号县(关联地区表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("settlementDistrictName"))) {
            errorList.add("结算账号县名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("settlementDistrictName")) && R.get("settlementDistrictName").length() > 64) {
            errorList.add("结算账号县名字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("taxRegistrationNumber"))) {
            errorList.add("税务登记证号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("taxRegistrationNumber")) && R.get("taxRegistrationNumber").length() > 64) {
            errorList.add("税务登记证号最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("taxIdentificationNumber"))) {
            errorList.add("税务登记，纳税人识别号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("taxIdentificationNumber")) && R.get("taxIdentificationNumber").length() > 64) {
            errorList.add("税务登记，纳税人识别号最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("taxRegistrationNumberPath"))) {
            errorList.add("税务登记，税务登记证号电子版path不能为空");
        }
        if (StringUtils.isNotBlank(R.get("taxRegistrationNumberPath")) && R.get("taxRegistrationNumberPath").length() > 64) {
            errorList.add("税务登记，税务登记证号电子版path最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("storeName"))) {
            errorList.add("店铺名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("storeName")) && R.get("storeName").length() > 64) {
            errorList.add("店铺名称最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("levelId"))) {
            errorList.add("店铺等级（关联店铺等级id）不能为空");
        }
        if (StringUtils.isNotBlank(R.get("levelId")) && R.get("levelId").length() > 19) {
            errorList.add("店铺等级（关联店铺等级id）最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("industryId"))) {
            errorList.add("主营行业（关联主营行业id）不能为空");
        }
        if (StringUtils.isNotBlank(R.get("industryId")) && R.get("industryId").length() > 19) {
            errorList.add("主营行业（关联主营行业id）最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("categoryId"))) {
            errorList.add("经营类目（关联平台商品分类）（一级分类）多个用逗号分割，0表示入驻所有分类不能为空");
        }
        if (StringUtils.isNotBlank(R.get("categoryId")) && R.get("categoryId").length() > 19) {
            errorList.add("经营类目（关联平台商品分类）（一级分类）多个用逗号分割，0表示入驻所有分类最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("summaryOfCoping"))) {
            errorList.add("应付总金额不能为空");
        }
        if (StringUtils.isNotBlank(R.get("summaryOfCoping")) && R.get("summaryOfCoping").length() > 12) {
            errorList.add("应付总金额最大长度不能超过12字符");
        }
        if (StringUtils.isBlank(R.get("paymentVoucherPath"))) {
            errorList.add("商家上传付款凭证path不能为空");
        }
        if (StringUtils.isNotBlank(R.get("paymentVoucherPath")) && R.get("paymentVoucherPath").length() > 64) {
            errorList.add("商家上传付款凭证path最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("paymentInstructions"))) {
            errorList.add("商家付款凭证说明不能为空");
        }
        if (StringUtils.isNotBlank(R.get("paymentInstructions")) && R.get("paymentInstructions").length() > 255) {
            errorList.add("商家付款凭证说明最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("oneAuditOpinion"))) {
            errorList.add("一审审核意见不能为空");
        }
        if (StringUtils.isNotBlank(R.get("oneAuditOpinion")) && R.get("oneAuditOpinion").length() > 255) {
            errorList.add("一审审核意见最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("twoAuditOpinion"))) {
            errorList.add("二审审核意见不能为空");
        }
        if (StringUtils.isNotBlank(R.get("twoAuditOpinion")) && R.get("twoAuditOpinion").length() > 255) {
            errorList.add("二审审核意见最大长度不能超过255字符");
        }
        return errorList;
    }

}