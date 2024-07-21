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
package com.sicheng.seller.store.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.admin.store.entity.StoreIndustry;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.store.service.StoreEnterAuthService;
import com.sicheng.seller.store.service.StoreIndustryService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: StoreEnterInfoController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年2月22日 下午12:45:14
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeEnterInfo")
public class StoreEnterInfoController extends BaseController {

    @Autowired
    private StoreEnterAuthService storeEnterAuthService;
    @Autowired
    private AreaService areaService;

    @Autowired
    private SiteSendMessagsService siteSendMessagsService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040130";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进入入驻信息
     *
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(Model model, HttpServletRequest request) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreEnterAuth storeEnterAuth = storeEnterAuthService.selectById(userSeller.getUId());
        String storebrandPath = storeEnterAuth.getStoreBrandPath();
        //转换品牌商标图片
        String storeBrandPath0 = "";
        String storeBrandPath1 = "";
        String storeBrandPath2 = "";
        String storeBrandPath3 = "";
        String storeBrandPath4 = "";
        String storeBrandPath5 = "";
        String storeBrandPath6 = "";
        String storeBrandPath7 = "";
        String storeBrandPath8 = "";
        String storeBrandPath9 = "";
        String storeBrandPathsLength = "0";
        if (StringUtils.isNotBlank(storebrandPath)) {
            String[] storebrandPaths = storebrandPath.split(",");
            if (storebrandPaths.length == 1) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPathsLength = "1";
            }
            if (storebrandPaths.length == 2) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPath1 = storebrandPaths[1];
                storeBrandPathsLength = "2";
            }
            if (storebrandPaths.length == 3) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPath1 = storebrandPaths[1];
                storeBrandPath2 = storebrandPaths[2];
                storeBrandPathsLength = "3";
            }
            if (storebrandPaths.length == 4) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPath1 = storebrandPaths[1];
                storeBrandPath2 = storebrandPaths[2];
                storeBrandPath3 = storebrandPaths[3];
                storeBrandPathsLength = "4";
            }
            if (storebrandPaths.length == 5) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPath1 = storebrandPaths[1];
                storeBrandPath2 = storebrandPaths[2];
                storeBrandPath3 = storebrandPaths[3];
                storeBrandPath4 = storebrandPaths[4];
                storeBrandPathsLength = "5";
            }
            if (storebrandPaths.length == 6) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPath1 = storebrandPaths[1];
                storeBrandPath2 = storebrandPaths[2];
                storeBrandPath3 = storebrandPaths[3];
                storeBrandPath4 = storebrandPaths[4];
                storeBrandPath5 = storebrandPaths[5];
                storeBrandPathsLength = "6";
            }
            if (storebrandPaths.length == 7) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPath1 = storebrandPaths[1];
                storeBrandPath2 = storebrandPaths[2];
                storeBrandPath3 = storebrandPaths[3];
                storeBrandPath4 = storebrandPaths[4];
                storeBrandPath5 = storebrandPaths[5];
                storeBrandPath6 = storebrandPaths[6];
                storeBrandPathsLength = "7";
            }
            if (storebrandPaths.length == 8) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPath1 = storebrandPaths[1];
                storeBrandPath2 = storebrandPaths[2];
                storeBrandPath3 = storebrandPaths[3];
                storeBrandPath4 = storebrandPaths[4];
                storeBrandPath5 = storebrandPaths[5];
                storeBrandPath6 = storebrandPaths[6];
                storeBrandPath7 = storebrandPaths[7];
                storeBrandPathsLength = "8";
            }
            if (storebrandPaths.length == 9) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPath1 = storebrandPaths[1];
                storeBrandPath2 = storebrandPaths[2];
                storeBrandPath3 = storebrandPaths[3];
                storeBrandPath4 = storebrandPaths[4];
                storeBrandPath5 = storebrandPaths[5];
                storeBrandPath6 = storebrandPaths[6];
                storeBrandPath7 = storebrandPaths[7];
                storeBrandPath8 = storebrandPaths[8];
                storeBrandPathsLength = "9";
            }
            if (storebrandPaths.length == 10) {
                storeBrandPath0 = storebrandPaths[0];
                storeBrandPath1 = storebrandPaths[1];
                storeBrandPath2 = storebrandPaths[2];
                storeBrandPath3 = storebrandPaths[3];
                storeBrandPath4 = storebrandPaths[4];
                storeBrandPath5 = storebrandPaths[5];
                storeBrandPath6 = storebrandPaths[6];
                storeBrandPath7 = storebrandPaths[7];
                storeBrandPath8 = storebrandPaths[8];
                storeBrandPath9 = storebrandPaths[9];
                storeBrandPathsLength = "10";
            }
        }
        model.addAttribute("storeEnterAuth", storeEnterAuth);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        model.addAttribute("storeBrandPathsLength", storeBrandPathsLength);
        model.addAttribute("storeBrandPath0", storeBrandPath0);
        model.addAttribute("storeBrandPath1", storeBrandPath1);
        model.addAttribute("storeBrandPath2", storeBrandPath2);
        model.addAttribute("storeBrandPath3", storeBrandPath3);
        model.addAttribute("storeBrandPath4", storeBrandPath4);
        model.addAttribute("storeBrandPath5", storeBrandPath5);
        model.addAttribute("storeBrandPath6", storeBrandPath6);
        model.addAttribute("storeBrandPath7", storeBrandPath7);
        model.addAttribute("storeBrandPath8", storeBrandPath8);
        model.addAttribute("storeBrandPath9", storeBrandPath9);
        model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
        return "seller/store/storeEnterInfo";
    }

    /**
     * 保存入驻信息
     *
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(StoreEnterAuth storeEnterAuth1, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate1(storeEnterAuth1, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(model, request);
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeEnterAuth1.setEnterId(userSeller.getUId());
        //品牌商标
        String[] sBrand = R.getArray("sBrand");
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < sBrand.length; i++) {
        	if(StringUtils.isBlank(sBrand[i])){
        		continue;
        	}
            sf.append(",");
            sf.append(sBrand[i]);
        }
        String businessType = R.get("businessType");
        if ("2".equals(businessType)) {
            String socialCreditCodePath = storeEnterAuth1.getSocialCreditCodePath();
            storeEnterAuth1.setSellerLicensePath(socialCreditCodePath);
        }
        storeEnterAuth1.setEnterId(userSeller.getUId());
        storeEnterAuth1.setBusinessType(businessType);
        storeEnterAuth1.setStoreBrandPath(sf.substring(1));
        storeEnterAuth1.setStatus("70");//10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        //业务处理
        storeEnterAuthService.updateByIdSelective(storeEnterAuth1);
        addMessage(redirectAttributes, FYUtils.fyParams("入驻信息修改成功"));
        //短信模板参数
        Map<String, String> map = new HashMap<>();
        map.put("userName", SsoUtils.getUserMain().getLoginName());
        //给后台管理员发送短信
        siteSendMessagsService.sendMessage(map, SiteSendMessagsService.ENTER_INFO_EDIT);
        return "redirect:" + sellerPath + "/store/storeEnterInfo/save1.htm";
    }

    /**
     * 入驻信息还原
     *
     * @param storeEnterAuth1
     * @param model
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeEnterAuth:edit")
    @RequestMapping(value = "reduction")
    public String reduction(StoreEnterAuth storeEnterAuth1, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeEnterAuth1.setId(userSeller.getId());//属主检查
        storeEnterAuthService.reduction(storeEnterAuth1);
        addMessage(redirectAttributes, FYUtils.fyParams("入驻信息还原成功"));
        return "redirect:" + sellerPath + "/store/storeEnterInfo/save1.htm";
    }

    /**
     * 表单验证
     *
     * @param storeEnter 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate1(StoreEnterAuth storeEnterAuth, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("companyName"))) {
            errorList.add(FYUtils.fyParams("企业名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("companyName")) && R.get("companyName").length() > 64) {
            errorList.add(FYUtils.fyParams("企业名称最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("areas"))) {
            if (StringUtils.isBlank(R.get("provinceId"))) {
                errorList.add(FYUtils.fyParams("企业省不能为空"));
            }
            if (StringUtils.isBlank(R.get("cityId"))) {
                errorList.add(FYUtils.fyParams("企业市不能为空"));
            }
            if (StringUtils.isBlank(R.get("districtId"))) {
                errorList.add(FYUtils.fyParams("企业区不能为空"));
            }
        }
        if (StringUtils.isBlank(R.get("detailedAddress"))) {
            errorList.add(FYUtils.fyParams("企业_详细地址不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("detailedAddress")) && R.get("detailedAddress").length() > 255) {
            errorList.add(FYUtils.fyParams("企业_详细地址最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("contact"))) {
            errorList.add(FYUtils.fyParams("联系人不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("contact")) && R.get("contact").length() > 64) {
            errorList.add(FYUtils.fyParams("联系人最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("contactNumber"))) {
            errorList.add(FYUtils.fyParams("联系电话不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("contactNumber")) && R.get("contactNumber").length() > 64) {
            errorList.add(FYUtils.fyParams("联系电话最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("legalName"))) {
            errorList.add(FYUtils.fyParams("法人不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("legalName")) && R.get("legalName").length() > 64) {
            errorList.add(FYUtils.fyParams("法人最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("legalIdCardCode"))) {
            errorList.add(FYUtils.fyParams("法人身份证号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("legalIdCardCode")) && R.get("legalIdCardCode").length() > 64) {
            errorList.add(FYUtils.fyParams("法人身份证号最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("legalIdCardCodePositive"))) {
            errorList.add(FYUtils.fyParams("法人身份证正面不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("legalIdCardCodePositive")) && R.get("legalIdCardCodePositive").length() > 64) {
            errorList.add(FYUtils.fyParams("法人身份证正面最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("legalIdCardCodeOpposite"))) {
            errorList.add(FYUtils.fyParams("法人身份证反面不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("legalIdCardCodeOpposite")) && R.get("legalIdCardCodeOpposite").length() > 64) {
            errorList.add(FYUtils.fyParams("法人身份证反面最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("openAnAccountLicense"))) {
            errorList.add(FYUtils.fyParams("开户许可证核准号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("openAnAccountLicense")) && R.get("openAnAccountLicense").length() < 6) {
            errorList.add(FYUtils.fyParams("开户许可证核准号最小长度不能超过6字符"));
        }
        if (StringUtils.isNotBlank(R.get("openAnAccountLicense")) && R.get("openAnAccountLicense").length() > 64) {
            errorList.add(FYUtils.fyParams("开户许可证核准号最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("openAnAccountLicensePath"))) {
            errorList.add(FYUtils.fyParams("开户许可证核准电子版不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("openAnAccountLicensePath")) && R.get("openAnAccountLicensePath").length() > 64) {
            errorList.add(FYUtils.fyParams("开户许可证核准电子版最大长度不能超过64字符"));
        }
        String businessType = R.get("businessType");
        if ("2".equals(businessType)) {
            if (StringUtils.isBlank(R.get("socialCreditCode"))) {
                errorList.add(FYUtils.fyParams("统一社会信用代码不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("socialCreditCode")) && R.get("socialCreditCode").length() < 6) {
                errorList.add(FYUtils.fyParams("统一社会信用代码最小长度不能少于6字符"));
            }
            if (StringUtils.isNotBlank(R.get("socialCreditCode")) && R.get("socialCreditCode").length() > 64) {
                errorList.add(FYUtils.fyParams("统一社会信用代码最大长度不能超过64字符"));
            }
            if (StringUtils.isBlank(R.get("socialCreditCodePath"))) {
                errorList.add(FYUtils.fyParams("多证合一营业执照电子版不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("socialCreditCodePath")) && R.get("socialCreditCodePath").length() > 64) {
                errorList.add(FYUtils.fyParams("多证合一营业执照电子版最大长度不能超过64字符"));
            }
        }
        if ("1".equals(businessType)) {
            if (StringUtils.isBlank(R.get("sellerLicense"))) {
                errorList.add(FYUtils.fyParams("营业执照注册号不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("sellerLicense")) && R.get("sellerLicense").length() < 6) {
                errorList.add(FYUtils.fyParams("营业执照注册号最小长度不能少于6字符"));
            }
            if (StringUtils.isNotBlank(R.get("sellerLicense")) && R.get("sellerLicense").length() > 64) {
                errorList.add(FYUtils.fyParams("营业执照注册号最大长度不能超过64字符"));
            }
            if (StringUtils.isBlank(R.get("sellerLicensePath"))) {
                errorList.add(FYUtils.fyParams("营业执照电子版不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("sellerLicensePath")) && R.get("sellerLicensePath").length() > 64) {
                errorList.add(FYUtils.fyParams("营业执照电子版最大长度不能超过64字符"));
            }
            if (StringUtils.isBlank(R.get("organizationCode"))) {
                errorList.add(FYUtils.fyParams("组织机构代码不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("organizationCode")) && R.get("organizationCode").length() < 6) {
                errorList.add(FYUtils.fyParams("组织机构代码最小长度不能少于6字符"));
            }
            if (StringUtils.isNotBlank(R.get("organizationCode")) && R.get("organizationCode").length() > 64) {
                errorList.add(FYUtils.fyParams("组织机构代码最大长度不能超过64字符"));
            }
            if (StringUtils.isBlank(R.get("organizationCodePath"))) {
                errorList.add(FYUtils.fyParams("组织机构代码电子版不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("organizationCodePath")) && R.get("organizationCodePath").length() > 64) {
                errorList.add(FYUtils.fyParams("组织机构代码电子版最大长度不能超过64字符"));
            }
            if (StringUtils.isBlank(R.get("taxRegistrationNumber"))) {
                errorList.add(FYUtils.fyParams("税务登记号不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("taxRegistrationNumber")) && R.get("taxRegistrationNumber").length() < 6) {
                errorList.add(FYUtils.fyParams("税务登记号最小长度不能少于6字符"));
            }
            if (StringUtils.isNotBlank(R.get("taxRegistrationNumber")) && R.get("taxRegistrationNumber").length() > 64) {
                errorList.add(FYUtils.fyParams("税务登记号最大长度不能超过64字符"));
            }
            if (StringUtils.isBlank(R.get("taxRegistrationNumberPath"))) {
                errorList.add(FYUtils.fyParams("税务登记电子版不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("taxRegistrationNumberPath")) && R.get("taxRegistrationNumberPath").length() > 64) {
                errorList.add(FYUtils.fyParams("税务登记电子版最大长度不能超过64字符"));
            }
        }
        if (StringUtils.isBlank(R.get("storeBrand"))) {
            errorList.add(FYUtils.fyParams("店铺品牌名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("storeBrand")) && R.get("storeBrand").length() > 255) {
            errorList.add(FYUtils.fyParams("店铺品牌最大长度不能超过255字符"));
        }
        String[] sBrand = R.getArray("sBrand");
        boolean status = true;
        for (int i = 0; i < sBrand.length; i++) {
            if (StringUtils.isNotBlank(sBrand[i])) {
                status = false;
            }
        }
        if (status) {
            errorList.add(FYUtils.fyParams("请上传品牌商标证书"));
        }
        if (StringUtils.isBlank(R.get("storeName"))) {
            errorList.add(FYUtils.fyParams("店铺名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("storeName")) && R.get("storeName").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("storeName"))) {
            Wrapper wrapper = new Wrapper();
            wrapper.and("a.store_name=",R.get("storeName"));
            wrapper.and("a.enter_id !=", SsoUtils.getUserMain().getUId());
            List<StoreEnterAuth> storeEnterAuths = storeEnterAuthService.selectByWhere(wrapper);
            if (!storeEnterAuths.isEmpty()) {
                errorList.add(FYUtils.fyParams("店铺名称重复"));
            }
        }
        return errorList;
    }
}
