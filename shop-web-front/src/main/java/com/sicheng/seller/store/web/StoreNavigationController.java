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
import javax.servlet.http.HttpServletResponse;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.store.entity.StoreNavigation;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.store.service.StoreNavigationService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: StoreNavigationController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年2月17日 下午6:47:37
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeNavigation")
public class StoreNavigationController extends BaseController {

    @Autowired
    private StoreNavigationService storeNavigationService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040115";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进入店铺导航列表页面
     *
     * @param storeNavigation
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeNavigation:view")
    @RequestMapping(value = "list")
    public String list(StoreNavigation storeNavigation, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeNavigation.setStoreId(userSeller.getStoreId());
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(storeNavigation);
        wrapper.orderBy("a.sort ASC");
        Page<StoreNavigation> page = storeNavigationService.selectByWhere(new Page<StoreNavigation>(request, response), wrapper);
        model.addAttribute("page", page);
        return "seller/store/storeNavigationList";
    }

    /**
     * 进去新增店铺导航页面
     *
     * @param storeNavigation
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeNavigation:view")
    @RequestMapping(value = "save1")
    public String save1(StoreNavigation storeNavigation, Model model) {
        if (storeNavigation == null) {
            storeNavigation = new StoreNavigation();
        }
        model.addAttribute("storeNavigation", storeNavigation);
        return "seller/store/storeNavigationForm";
    }

    /**
     * 保存新增店铺导航的数据
     *
     * @param storeNavigation
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeNavigation:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreNavigation storeNavigation, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeNavigation, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeNavigation, model);//回显错误提示
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeNavigation.setStoreId(userSeller.getStoreId());
        if (StringUtils.isBlank(storeNavigation.getTarget())) {
            storeNavigation.setTarget("2");
        }
        if (StringUtils.isBlank(storeNavigation.getIsOpen())) {
            storeNavigation.setIsOpen("1");
        }
        //业务处理
        storeNavigationService.insertSelective(storeNavigation);
        addMessage(redirectAttributes, FYUtils.fyParams("保存店铺导航成功"));
        return "redirect:" + sellerPath + "/store/storeNavigation/list.htm?repage";
    }

    /**
     * 进去编辑页面导航页面
     *
     * @param storeNavigation
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeNavigation:view")
    @RequestMapping(value = "edit1")
    public String edit1(StoreNavigation storeNavigation, Model model) {
        //入参检查
        if (storeNavigation == null || storeNavigation.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺导航不存在！"));
            return "error/400";
        }
        //入参检查
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeNavigation.setStoreId(userSeller.getStoreId());//属主检查
        StoreNavigation entity = storeNavigationService.selectOne(new Wrapper(storeNavigation));
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺导航不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        model.addAttribute("storeNavigation", entity);
        return "seller/store/storeNavigationForm";
    }

    /**
     * 保存编辑店铺导航页面的数据
     *
     * @param storeNavigation
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeNavigation:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreNavigation storeNavigation, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //入参检查
        if (storeNavigation == null || storeNavigation.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺导航不存在！"));
            return "error/400";
        }
        //表单验证
        List<String> errorList = validate(storeNavigation, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeNavigation, model);//回显错误提示
        }
        if (StringUtils.isBlank(storeNavigation.getTarget())) {
            storeNavigation.setTarget(R.get("targetHidden"));
        }
        if (StringUtils.isBlank(storeNavigation.getIsOpen())) {
            storeNavigation.setIsOpen(R.get("isOpenHidden"));
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //业务处理
        StoreNavigation condition = new StoreNavigation();
        condition.setId(storeNavigation.getId());//属主检查
        condition.setStoreId(userSeller.getStoreId());//属主检查
        storeNavigationService.updateByWhereSelective(storeNavigation, new Wrapper(condition));
        addMessage(redirectAttributes, FYUtils.fyParams("编辑店铺导航成功"));
        return "redirect:" + sellerPath + "/store/storeNavigation/list.htm?repage";
    }

    /**
     * 删除页面导航
     *
     * @param storeNavigation
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeNavigation:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreNavigation storeNavigation, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        //入参检查
        if (storeNavigation == null || storeNavigation.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("店铺导航不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeNavigation.setStoreId(userSeller.getStoreId());//属主检查
        storeNavigationService.deleteByWhere(new Wrapper(storeNavigation));
        addMessage(redirectAttributes, FYUtils.fyParams("删除店铺导航成功"));
        return "redirect:" + sellerPath + "/store/storeNavigation/list.htm?repage";
    }

    /**
     * 表单验证
     *
     * @param storeNavigation 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreNavigation storeNavigation, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("导航名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("导航名称最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fyParams("排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("url"))) {
            errorList.add(FYUtils.fyParams("导航外链接不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("url")) && R.get("url").length() > 255) {
            errorList.add(FYUtils.fyParams("导航外链接最大长度不能超过255字符"));
        }
        return errorList;
    }

}
