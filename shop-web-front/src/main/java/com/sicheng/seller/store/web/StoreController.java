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
package com.sicheng.seller.store.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.store.service.StoreService;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: StoreController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年2月16日 上午10:35:51
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/store")
public class StoreController extends BaseController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private AreaService areaService;


    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040105";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进入店铺设置页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("store:store:view")
    @RequestMapping(value = "save1")
    public String save1(Model model, HttpServletRequest request) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        Store store = storeService.selectById(userSeller.getStoreId());
        model.addAttribute("store", store);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        return "seller/store/storeSet";
    }

    /**
     * 保存店铺设置
     *
     * @param store
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "save2")
    public String save2(Store store, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(store, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(model, request);//回显错误提示
        }
        storeService.update(store);
        addMessage(redirectAttributes, FYUtils.fyParams("店铺设置成功"));
        return "redirect:" + sellerPath + "/store/store/save1.htm?repage";
    }

    /**
     * 验证店铺名称是否重复
     *
     * @param oldStoreName
     * @param name
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "validateStoreName")
    public String validateStoreName(String oldStoreName, String name) {
        if (StringUtils.isNotBlank(oldStoreName) && name.equals(oldStoreName)) {
            return "true";
        } else if (StringUtils.isNotBlank(name)) {
            List<Store> stores = storeService.selectByWhere(new Wrapper().and("a.name like", name));
            if (stores.isEmpty()) {
                return "true";
            } else {
                return "false";
            }
        }
        return "false";
    }

    /**
     * 表单验证
     *
     * @param store 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Store store, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("storeId"))) {
            errorList.add(FYUtils.fyParams("店铺ID不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 19) {
            errorList.add(FYUtils.fyParams("店铺ID最大长度不能超过19字符"));
        }
        if (StringUtils.isBlank(R.get("banner"))) {
            errorList.add(FYUtils.fyParams("店铺横幅图片路径（默认图片）不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("banner")) && R.get("banner").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺横幅图片路径（默认图片）最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("店铺名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺名称最大长度不能超过64字符"));
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
            errorList.add(FYUtils.fyParams("详细地址最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("industry"))) {
            errorList.add(FYUtils.fyParams("店铺主营产品不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("industry")) && R.get("industry").length() > 255) {
            errorList.add(FYUtils.fyParams("店铺主营产品最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("storeTel"))) {
            errorList.add(FYUtils.fyParams("店铺客服电话不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("storeTel")) && R.get("storeTel").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺客服电话最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("storeQq"))) {
            errorList.add(FYUtils.fyParams("店铺QQ不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("storeQq")) && R.get("storeQq").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺QQ最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("storeWechat"))) {
            errorList.add(FYUtils.fyParams("店铺联系微信不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("storeWechat")) && R.get("storeWechat").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺联系微信最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("seoTitle"))) {
            errorList.add(FYUtils.fyParams("SEO-Title不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("seoTitle")) && R.get("seoTitle").length() > 255) {
            errorList.add(FYUtils.fyParams("SEO-Title最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("seoKeyword"))) {
            errorList.add(FYUtils.fyParams("SEO关键字不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("seoKeyword")) && R.get("seoKeyword").length() > 255) {
            errorList.add(FYUtils.fyParams("SEO关键字最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("seoDescribe"))) {
            errorList.add(FYUtils.fyParams("SEO店铺描述不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("seoDescribe")) && R.get("seoDescribe").length() > 255) {
            errorList.add(FYUtils.fyParams("SEO店铺描述最大长度不能超过255字符"));
        }
        return errorList;
    }
}
