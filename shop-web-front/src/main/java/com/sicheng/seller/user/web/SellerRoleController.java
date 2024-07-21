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
package com.sicheng.seller.user.web;

import com.google.common.collect.Lists;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.store.entity.StoreRole;
import com.sicheng.admin.store.entity.StoreRoleMenu;
import com.sicheng.admin.store.entity.StoreSellerRole;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.store.service.StoreRoleMenuService;
import com.sicheng.seller.store.service.StoreRoleService;
import com.sicheng.seller.store.service.StoreSellerRoleService;
import com.sicheng.sso.shiro.SsoAuthorizingRealm;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>标题: userRolerController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2017年2月16日 上午10:22:17
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/storeRole")
public class SellerRoleController extends BaseController {
    @Autowired
    private StoreRoleService storeRoleService;
    @Autowired
    private StoreRoleMenuService storeRoleMenuService;
    @Autowired
    private StoreSellerRoleService storeSellerRoleService;
    @Autowired
    private StoreMenuService storeMenuService;
    @Autowired
    private SsoAuthorizingRealm ssoAuthorizingRealm;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "060302";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 查询并显示商家的账号组(角色)
     *
     * @param storeRole 商家角色
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("store:storeRole:view")
    @RequestMapping(value = "list")
    public String list(StoreRole storeRole, Model model, HttpServletRequest request, HttpServletResponse response) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeRole.setStoreId(userSeller.getStoreId());
        Page<StoreRole> page = storeRoleService.selectByWhere(new Page<StoreRole>(request, response), new Wrapper(storeRole));
        model.addAttribute("page", page);
        return "seller/user/userRoleList";
    }

    /**
     * 进入账号组添加页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreRole storeRole, Model model) {
        if (storeRole.getRoleId() == null) {
            storeRole = new StoreRole();
        }
        model.addAttribute("storeRole", storeRole);
        //获取去所有商家菜单
        menu(model);
        return "seller/user/userRoleSave";
    }

    /**
     * 执行保存账号组
     *
     * @param storeRole          商家角色
     * @param model
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreRole storeRole, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //表单验证
        String oldRoleName = R.get("oldRoleName");
        String[] listMenuId = R.getArray("listMenuId");
        List<String> errorList = validate(storeRole, oldRoleName, listMenuId, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeRole, model);//回显错误提示
        }
        //业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeRole.setStoreId(userSeller.getStoreId());
        storeRoleService.insertAll(storeRole, listMenuId);
        addMessage(redirectAttributes, FYUtils.fyParams("添加账号组成功"));
        return "redirect:" + sellerPath + "/store/storeRole/list.htm";
    }

    /**
     * 进入账号组编辑页面
     *
     * @param storeRole          商家角色
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreRole storeRole, Model model) {
        //入参检查
        if (storeRole == null || storeRole.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("账号组不存在！"));
            return "error/400";
        }
        //入参检查
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeRole.setStoreId(userSeller.getStoreId());//属主检查
        storeRole = storeRoleService.selectOne(new Wrapper(storeRole));
        if (storeRole == null) {
            model.addAttribute("message", FYUtils.fyParams("账号组不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        StoreRoleMenu storeRoleMenu = new StoreRoleMenu();
        storeRoleMenu.setRoleId(storeRole.getRoleId());
        List<StoreRoleMenu> list = storeRoleMenuService.selectByWhere(new Wrapper(storeRoleMenu));//商家角色所拥有的菜单
        model.addAttribute("list", list);
        //获取所有商家菜单
        menu(model);
        //商家角色信息
        model.addAttribute("storeRole", storeRole);
        return "seller/user/userRoleSave";
    }

    /**
     * 执行编辑账号组
     *
     * @param storeRole          商家角色
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreRole storeRole, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (storeRole == null || storeRole.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("账号组不存在！"));
            return "error/400";
        }
        //表单验证
        String oldRoleName = R.get("oldRoleName");
        String[] listMenuId = R.getArray("listMenuId");
        List<String> errorList = validate(storeRole, oldRoleName, listMenuId, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeRole, model);//回显错误提示
        }
        //入参检查
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreRole oldStoreRole = storeRoleService.selectById(storeRole.getId());
        if (oldStoreRole == null) {
            model.addAttribute("message", FYUtils.fyParams("账号组不存在！"));
            return "error/400";
        }
        //清空所有权限
        ssoAuthorizingRealm.clearAllCachedAuthorizationInfo();
        //检查合格后，业务处理
        if (oldStoreRole.getStoreId().equals(userSeller.getStoreId())) {//属主检查
            storeRoleService.updateAll(storeRole, listMenuId);
        }
        addMessage(redirectAttributes, FYUtils.fyParams("修改账号组成功"));
        return "redirect:" + sellerPath + "/store/storeRole/list.htm";
    }

    /**
     * 删除账号组
     *
     * @param storeRole          商家角色
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeRole:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreRole storeRole, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (storeRole == null || storeRole.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("账号组不存在！"));
            return "error/400";
        }
        //入参检查
        StoreRole entity = storeRoleService.selectById(storeRole.getId());
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("账号组不存在！"));
            return "error/400";
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        if (!entity.getStoreId().equals(userSeller.getStoreId())) {//属主检查
            model.addAttribute("message", FYUtils.fyParams("账号组不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        StoreSellerRole storeSellerRole = new StoreSellerRole();
        storeSellerRole.setRoleId(entity.getRoleId());
        List<StoreSellerRole> list = storeSellerRoleService.selectByWhere(new Wrapper(storeSellerRole));
        //清空所有权限
        ssoAuthorizingRealm.clearAllCachedAuthorizationInfo();
        if (list.isEmpty()) {
            storeRoleService.deleteAll(storeRole);
            addMessage(redirectAttributes, FYUtils.fyParams("删除账号组成功"));
        } else {
            addMessage(redirectAttributes, FYUtils.fyParams("账号已添加此账号组，不能删除"));
        }
        return "redirect:" + sellerPath + "/store/storeRole/list.htm";
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "exitRoleName")
    public String exitRoleName(String oldRoleName, String roleName) {
        if (StringUtils.isNotBlank(roleName)) {
            if (roleName.equals(oldRoleName)) {
                return "true";
            } else {
                StoreRole storeRole = new StoreRole();
                storeRole.setRoleName(roleName);
                List<StoreRole> storeRoles = storeRoleService.selectByWhere(new Wrapper(storeRole));
                if (storeRoles.isEmpty()) {
                    return "true";
                } else {
                    return "false";
                }
            }
        }
        return "false";
    }

    /**
     * 获取去所有商家菜单
     */
    private void menu(Model model) {
        //获取去所有商家菜单
        StoreMenu storeMenu = new StoreMenu();
        storeMenu.setDelFlag("0");//0：正常；1：删除；2：审核
        storeMenu.setIsShow("1");//是否在菜单中显示（0隐藏、1显示）
        Wrapper w = new Wrapper();
        w.orderBy("sort");
        w.setEntity(storeMenu);
        List<StoreMenu> list = Lists.newArrayList();
        List<StoreMenu> listNew = Lists.newArrayList();
        List<StoreMenu> listNew2 = Lists.newArrayList();//二级菜单
        List<StoreMenu> listNew3 = Lists.newArrayList();//三级菜单
        List<StoreMenu> listMenu = storeMenuService.selectByWhere(w);
        StoreMenu.sortList(list, listMenu, StoreMenu.getRootId(), true);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getParentId() != 1) {
                    listNew.add(list.get(i));
                }
            }
        }
        for (int i = 0; i < listNew.size(); i++) {
            if (storeMenuService.hadChildNode(listNew.get(i).getMenuId())) {
                listNew2.add(listNew.get(i));
            } else {
                listNew3.add(listNew.get(i));
            }
        }
        model.addAttribute("listNew2", listNew2);
        model.addAttribute("listNew3", listNew3);
    }

    private List<String> validate(StoreRole storeRole, String oldRoleName, String[] listMenuId, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(storeRole.getRoleName())) {
            errorList.add(FYUtils.fyParams("账号组名不能为空"));
        }
        if (StringUtils.isNotBlank(storeRole.getRoleName())) {
            if (storeRole.getRoleName().length() > 64) {
                errorList.add(FYUtils.fyParams("账号组名长度大于64"));
            }
            if (!storeRole.getRoleName().equals(oldRoleName)) {
                StoreRole newRole = new StoreRole();
                newRole.setRoleName(storeRole.getRoleName());
                List<StoreRole> storeRoles = storeRoleService.selectByWhere(new Wrapper(storeRole));
                if (!storeRoles.isEmpty()) {
                    errorList.add(FYUtils.fyParams("此账号组名已存在"));
                }
            }
        }
        if (listMenuId.length == 0) {
            errorList.add(FYUtils.fyParams("请选择账号权限"));
        }
        return errorList;
    }
}
