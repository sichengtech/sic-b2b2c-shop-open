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
package com.sicheng.member.upgrade.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sicheng.member.interceptor.MemberMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserPurchase;
import com.sicheng.admin.sys.entity.Area;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.sso.service.UserPurchaseService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 升级为采购商</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年7月28日 上午11:23:39
 */
@Controller
@RequestMapping(value = "${memberPath}/upgrade/upgradePurchaser")
public class UpgradePurchaserController extends BaseController {

    @Autowired
    private UserPurchaseService userPurchaseService;

    @Autowired
    private AreaService areaService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting( "purchaser");//三级菜单高亮
    }

    /**
     * 升级为采购商状态页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(Model model) {
        UserPurchase userPurchase = SsoUtils.getUserMain().getUsePurchase();
        model.addAttribute("userPurchase", userPurchase);
        return "member/upgrade/upgradePurchaserStatus";
    }

    /**
     * 进入升级为采购商页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(Model model, HttpServletRequest request) {
        UserPurchase userPurchase = SsoUtils.getUserMain().getUsePurchase();
        model.addAttribute("userPurchase", userPurchase);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
        return "member/upgrade/upgradePurchaserForm";
    }

    /**
     * 保存升级为采购商数据
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save3")
    public String save3(UserPurchase userPurchase, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userPurchase, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save2(model, request);//回显错误提示
        }
        UserMain userMain = SsoUtils.getUserMain();
        //转换城市id变名称
        if (userPurchase.getProvinceId() != null) {
            //修改省
            Area provinceArea = areaService.selectById(userPurchase.getProvinceId());
            if (provinceArea != null) {
                userPurchase.setProvinceName(provinceArea.getName());
            }
        }
        if (userPurchase.getCityId() != null) {
            //修改市
            Area cityArea = areaService.selectById(userPurchase.getCityId());
            if (cityArea != null) {
                userPurchase.setCityName(cityArea.getName());
            }
        }
        if (userPurchase.getDistrictId() != null) {
            //修改区县
            Area districtArea = areaService.selectById(userPurchase.getDistrictId());
            if (districtArea != null) {
                userPurchase.setDistrictName(districtArea.getName());
            }
        }
        userPurchase.setPkMode(1);
        userPurchase.setUId(userMain.getUId());
        userPurchase.setAuthType("0");//0待审、1通过、2未通过
        userPurchaseService.insertOrUpdate(userPurchase);
        addMessage(redirectAttributes, FYUtils.fyParams("资料填写成功,审核中"));
        return "redirect:" + memberPath + "/upgrade/upgradePurchaser/save1.htm";
    }


    /**
     * 表单验证
     *
     * @param store 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(UserPurchase userPurchase, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("门店名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("门店名称最大长度不能超过" + "64" + FYUtils.fyParams("字符"))); 
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add(FYUtils.fyParams("企业类型不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 2) {
            errorList.add(FYUtils.fyParams("企业类型最大长度不能超过" + "2" + FYUtils.fyParams("字符"))); 
        }
        if (StringUtils.isBlank(R.get("industry"))) {
            errorList.add(FYUtils.fyParams("企业属性不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("industry")) && R.get("industry").length() > 2) {
            errorList.add(FYUtils.fyParams("企业属性最大长度不能超过" + "2" + FYUtils.fyParams("字符"))); 
        }
        if (StringUtils.isBlank(R.get("businesslicense"))) {
            errorList.add(FYUtils.fyParams("营业执照不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("businesslicense")) && R.get("businesslicense").length() > 64) {
            errorList.add(FYUtils.fyParams("营业执照最大长度不能超过" + "64" + FYUtils.fyParams("字符"))); 
        }
        if (StringUtils.isBlank(R.get("introduce"))) {
            errorList.add(FYUtils.fyParams("门店介绍不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("introduce")) && R.get("introduce").length() > 255) {
            errorList.add(FYUtils.fyParams("门店介绍最大长度不能超过" + "255" + FYUtils.fyParams("字符"))); 
        }
        if (StringUtils.isBlank(R.get("areas"))) {
            if (StringUtils.isBlank(R.get("provinceId"))) {
                errorList.add(FYUtils.fyParams("省不能为空"));
            }
            if (StringUtils.isBlank(R.get("cityId"))) {
                errorList.add(FYUtils.fyParams("市不能为空"));
            }
            if (StringUtils.isBlank(R.get("districtId"))) {
                errorList.add(FYUtils.fyParams("区不能为空"));
            }
            if (StringUtils.isBlank(R.get("detailedAddress"))) {
                errorList.add(FYUtils.fyParams("详细地址不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("detailedAddress")) && R.get("detailedAddress").length() > 255) {
                errorList.add(FYUtils.fyParams("详细地址最大长度不能超过" + "255" + FYUtils.fyParams("字符"))); 
            }
        }
        if (StringUtils.isBlank(R.get("contacts"))) {
            errorList.add(FYUtils.fyParams("联系人不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("contacts")) && R.get("contacts").length() > 64) {
            errorList.add(FYUtils.fyParams("联系人最大长度不能超过" + "64" + FYUtils.fyParams("字符"))); 
        }
        if (StringUtils.isBlank(R.get("contactsTelephone"))) {
            errorList.add(FYUtils.fyParams("联系人电话不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("contactsTelephone")) && R.get("contactsTelephone").length() > 64) {
            errorList.add(FYUtils.fyParams("联系人电话最大长度不能超过" + "64" + FYUtils.fyParams("字符"))); 
        }
        return errorList;
    }

}
