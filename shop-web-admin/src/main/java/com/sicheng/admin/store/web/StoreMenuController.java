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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hc360.rsf.config.ConfigLoader;
import com.hc360.rsf.config.RsfSpringLoader;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.store.service.StoreMenuService;

import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.shiro.SsoClearAuthorizationCache;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 店铺菜单Controller
 *
 * @author 蔡龙
 * @version 2017-02-13
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeMenu")
public class StoreMenuController extends BaseController {

    @Autowired
    private StoreMenuService storeMenuService;


    /**
     * 菜单高亮
     *
     * @param menuId
     * @param model
     */
    @ModelAttribute
    public StoreMenu get(Long menuId, Model model) {
        String menu3 = "050109";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (menuId != null) {
            return storeMenuService.selectById(menuId);
        } else {
            return new StoreMenu();
        }
    }

    /**
     * 列表页面
     *
     * @param storeMenu
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeMenu:view")
    @RequestMapping(value = {"list", ""})
    public String list(StoreMenu storeMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<StoreMenu> list = Lists.newArrayList();
        Wrapper w = new Wrapper();
        w.orderBy("sort");
        w.setEntity(storeMenu);
        List<StoreMenu> sourcelist = storeMenuService.selectByWhere(w);
        StoreMenu.sortList(list, sourcelist, StoreMenu.getRootId(), true);
        model.addAttribute("list", list);
        return "admin/store/storeMenuList";
    }

    @RequiresPermissions("store:storeMenu:edit")
    @RequestMapping(value = "form")
    public String form(StoreMenu storeMenu, Model model) {
        if (storeMenu.getParent() == null || storeMenu.getParent().getId() == null) {
            storeMenu.setParent(new StoreMenu(StoreMenu.getRootId()));
        }
        storeMenu.setParent(storeMenuService.selectById(storeMenu.getParent().getId()));
        if (storeMenu.getId() == null) {
            List<StoreMenu> list = Lists.newArrayList();
            List<StoreMenu> sourcelist = storeMenuService.selectAll(new Wrapper());
            StoreMenu.sortList(list, sourcelist, storeMenu.getParentId(), false);
            if (list.size() > 0) {
                storeMenu.setSort(list.get(list.size() - 1).getSort() + 30);
            }
        }
        if (storeMenu.getSort() == null) {
            storeMenu.setSort(30);
        }
        model.addAttribute("storeMenu", storeMenu);
        return "admin/store/storeMenuForm";
    }

    @RequiresPermissions("store:storeMenu:edit")
    @RequestMapping(value = "save")
    public String save(StoreMenu storeMenu, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeMenu, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(storeMenu, model);//回显错误提示
        }
        //业务处理
        storeMenuService.save(storeMenu);
        //清理权限缓存
        SsoClearAuthorizationCache();
        addMessage(redirectAttributes, FYUtils.fyParams("保存菜单'") + storeMenu.getName() + FYUtils.fyParams("'成功"));
        String stat = R.get("submitBtn");
        if (!"".equals(stat) && "1".equals(stat)) {
            return "redirect:" + adminPath + "/store/storeMenu/form.do?parent.id=" + storeMenu.getParent().getId();
        } else {
            return "redirect:" + adminPath + "/store/storeMenu.do";

        }
    }

    @RequiresPermissions("store:storeMenu:drop")
    @RequestMapping(value = "delete")
    public String delete(StoreMenu storeMenu, RedirectAttributes redirectAttributes) {
        storeMenuService.deleteMyAll(storeMenu.getMenuId());
        //清理权限缓存
        SsoClearAuthorizationCache();
        addMessage(redirectAttributes, FYUtils.fyParams("删除店铺菜单成功"));
        return "redirect:" + adminPath + "/store/storeMenu/list.do?repage";
    }

    /**
     * 店铺菜单
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     * @param extId 排除的ID
     * @param isShowHide 是否显示隐藏菜单,0否、1是
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<StoreMenu> list = storeMenuService.findList(new StoreMenu());
        for (int i = 0; i < list.size(); i++) {
            StoreMenu e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                if (isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")) {
                    continue;
                }
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 批量修改菜单排序
     */
    @RequiresPermissions("store:storeMenu:edit")
    @RequestMapping(value = "updateSort")
    public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
        for (int i = 0; i < ids.length; i++) {
            StoreMenu menu = new StoreMenu(Long.valueOf(ids[i]));
            menu.setSort(sorts[i]);
            storeMenuService.updateByIdSelective(menu);
        }
        addMessage(redirectAttributes, FYUtils.fyParams("保存菜单排序成功!"));
        return "redirect:" + adminPath + "/store/storeMenu.do";
    }

    /**
     * @param menu
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeMenu:view")
    @RequestMapping(value = "search")
    public String search(StoreMenu menu, Model model) {
        menu.setSort(null);
        Wrapper wrapper = new Wrapper(menu);
        wrapper.orderBy("sort");
        List<StoreMenu> menuList = storeMenuService.selectByWhere(wrapper);
        if (menuList.size() > 0) {
            List<Object> menuIds = new ArrayList<>();
            for (StoreMenu a : menuList) {
                if (!"".equals(a.getParentIds())) {
                    String[] parentIds = a.getParentIds().split(",");
                    for (String id : parentIds) {
                        if (!"0".equals(id) && !menuIds.contains(id)) {
                            menuIds.add(id);
                        }
                    }
                }
            }
            if (menuIds.size() > 0) {
                List<StoreMenu> parentMenuList = storeMenuService.selectByIdIn(menuIds);
                if (parentMenuList.size() > 0) {
                    for (StoreMenu m : parentMenuList) {
                        if (!menuList.contains(m)) {
                            menuList.add(m);
                        }
                    }
                }
            }
        }
        List<StoreMenu> list = Lists.newArrayList();
        StoreMenu.sortList(list, menuList, StoreMenu.getRootId(), true);
        model.addAttribute("list", list);
        model.addAttribute("name", menu.getName());
        return "admin/store/storeMenuList";
    }

//    /**
//     * 验证商铺菜单编号的唯一性
//     *
//     * @param oldMenuNum 数据库的编号
//     * @param menuNum    输入的编号
//     * @return
//     */
//    @RequiresPermissions("user")
//    @ResponseBody
//    @RequestMapping(value = "exitNum")
//    public String exitRoleName(String oldMenuNum, String menuNum) {
//        if (StringUtils.isNotBlank(menuNum)) {
//            if (oldMenuNum.equals(menuNum)) {
//                return "true";
//            } else {
//                StoreMenu storeMenu = new StoreMenu();
//                storeMenu.setMenuNum(menuNum);
//                List<StoreMenu> storeMenus = storeMenuService.selectByWhere(new Wrapper(storeMenu));
//                if (storeMenus.isEmpty()) {
//                    return "true";
//                } else {
//                    return "false";
//                }
//            }
//        }
//        return "false";
//    }

    /**
     * 清理所有权限缓存
     */
    private void SsoClearAuthorizationCache() {
        //清理sso的权限缓存
        try {
            ConfigLoader configLoader = RsfSpringLoader.getConfigLoader();
            SsoClearAuthorizationCache ssoClearAuthorizationCacheImpl = (SsoClearAuthorizationCache) configLoader.getServiceProxyBean("clearAuthorizationCache");//配置文件中的id
            ssoClearAuthorizationCacheImpl.clearAuthorizationCache();
        } catch (Exception e) {
            logger.error(FYUtils.fyParams("RSF调用sso清理权限缓存出错"), e);
        }
    }

    /**
     * 表单验证
     *
     * @param storeMenu 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreMenu storeMenu, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("请填写名称"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("名称最大长度不能超过64字符"));
        }
//        if (StringUtils.isBlank(R.get("menuNum"))) {
//            errorList.add(FYUtils.fyParams("请填写编号"));
//        }
//        if (StringUtils.isNotBlank(R.get("menuNum")) && R.get("menuNum").length() > 64) {
//            errorList.add(FYUtils.fyParams("编号最大长度不能超过64字符"));
//        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fyParams("请填写排序"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("isShow"))) {
            errorList.add(FYUtils.fyParams("请选择是否可见"));
        }
        return errorList;
    }
}