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
import com.sicheng.admin.sso.entity.UserRepairShop;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.sso.service.UserRepairShopService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 升级为服务门店</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年7月28日 上午10:36:10
 */
@Controller
@RequestMapping(value = "${memberPath}/upgrade/openDoorStore")
public class OpenDoorStoreController extends BaseController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private UserRepairShopService userRepairShopService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting( "openDoorStore");//三级菜单高亮
    }

    /**
     * 升级为服务门店状态页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(Model model) {
        UserRepairShop userRepairShop = SsoUtils.getUserMain().getUseRepairShop();
        model.addAttribute("userRepairShop", userRepairShop);
        return "member/upgrade/openDoorStoreStatus";
    }

    /**
     * 进入升级为服务门店页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(Model model, HttpServletRequest request) {
        UserRepairShop userRepairShop = SsoUtils.getUserMain().getUseRepairShop();
        if (userRepairShop != null) {
            String brands = userRepairShop.getBrands();
            List<String> brandList = new ArrayList<>();
            if (StringUtils.isNotBlank(brands)) {
                for (int i = 0; i < brands.split(",").length; i++) {
                    brandList.add(brands.split(",")[i]);
                }
            }
            model.addAttribute("brandList", brandList);
        }
        model.addAttribute("userRepairShop", userRepairShop);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        return "member/upgrade/openDoorStoreForm";
    }

    /**
     * 保存或编辑升级为服务门店页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save3")
    public String save3(UserRepairShop userRepairShop, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {//表单验证
        //获取门店的开店时间和闭店时间
        userRepairShop.setOpenShopDate(R.getDate("openShopDate", "HH:mm:ss", null));//开店时间
        userRepairShop.setCloseShopDate(R.getDate("closeShopDate", "HH:mm:ss", null));//闭店时间
        List<String> errorList = validate(userRepairShop, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save2(model, request);//回显错误提示
        }
        UserMain userMain = SsoUtils.getUserMain();
        //拼接已选品牌
        String[] brands = R.getArray("brands");
        if (brands.length > 0) {
            StringBuffer brandss = new StringBuffer();
            for (int i = 0; i < brands.length; i++) {
                brandss.append(",");
                brandss.append(brands[i]);
            }
            userRepairShop.setBrands(brandss.substring(1));
        }
        userRepairShop.setPkMode(1);
        userRepairShop.setUId(userMain.getUId());
        userRepairShop.setAuthType("0");//0待审、1通过、2未通过
        userRepairShopService.insertOrUpdate(userRepairShop);
        addMessage(redirectAttributes, FYUtils.fyParams("资料填写成功,审核中"));
        return "redirect:" + memberPath + "/upgrade/openDoorStore/save1.htm";
    }

    /**
     * 表单验证
     *
     * @param store 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(UserRepairShop userRepairShop, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add(FYUtils.fyParams("门店类型不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 64) {
            errorList.add(FYUtils.fyParams("门店类型最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("门店名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("门店名称最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("introduce"))) {
            errorList.add(FYUtils.fyParams("门店介绍不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("introduce")) && R.get("introduce").length() > 255) {
            errorList.add(FYUtils.fyParams("门店介绍最大长度不能超过") + "255" + FYUtils.fyParams("字符"));
        }
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
            errorList.add(FYUtils.fyParams("详细地址最大长度不能超过") + "255" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("burns"))) {
            errorList.add(FYUtils.fyParams("门店面积不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("burns")) && R.get("burns").length() > 64) {
            errorList.add(FYUtils.fyParams("门店面积最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("bossName"))) {
            errorList.add(FYUtils.fyParams("老板姓名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("bossName")) && R.get("bossName").length() > 64) {
            errorList.add(FYUtils.fyParams("老板姓名最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("bossMobile"))) {
            errorList.add(FYUtils.fyParams("老板电话不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("bossMobile")) && R.get("bossMobile").length() > 64) {
            errorList.add(FYUtils.fyParams("老板电话最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("peopleCount"))) {
            errorList.add(FYUtils.fyParams("门店人数不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("peopleCount")) && R.get("peopleCount").length() > 64) {
            errorList.add(FYUtils.fyParams("门店人数最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("hotline"))) {
            errorList.add(FYUtils.fyParams("服务热线不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("hotline")) && R.get("hotline").length() > 64) {
            errorList.add(FYUtils.fyParams("服务热线最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("shopkeeperName"))) {
            errorList.add(FYUtils.fyParams("店主姓名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("shopkeeperName")) && R.get("shopkeeperName").length() > 64) {
            errorList.add(FYUtils.fyParams("店主姓名最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("shopkeeperMobile"))) {
            errorList.add(FYUtils.fyParams("店主手机不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("shopkeeperMobile")) && R.get("shopkeeperMobile").length() > 64) {
            errorList.add(FYUtils.fyParams("店主手机最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("contactsName"))) {
            errorList.add(FYUtils.fyParams("联系人姓名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("contactsName")) && R.get("contactsName").length() > 64) {
            errorList.add(FYUtils.fyParams("联系人姓名最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("department"))) {
            errorList.add(FYUtils.fyParams("所在部门不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("department")) && R.get("department").length() > 64) {
            errorList.add(FYUtils.fyParams("所在部门最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        if (StringUtils.isBlank(R.get("mobile"))) {
            errorList.add(FYUtils.fyParams("本人手机不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && R.get("mobile").length() > 64) {
            errorList.add(FYUtils.fyParams("本人手机最大长度不能超过") + "64" + FYUtils.fyParams("字符"));
        }
        return errorList;
    }

}
