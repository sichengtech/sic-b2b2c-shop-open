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
package com.sicheng.admin.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sicheng.admin.sys.entity.Menu;

import com.sicheng.admin.sys.service.MenuService;
import com.sicheng.admin.sys.utils.UserUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单Controller
 *
 * @author zhaolei
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService sysMenuService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     * @return
     */
    @ModelAttribute("menu")
    public Menu get(@RequestParam(required = false) Long id, Model model) {
        String menu3 = "080115";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (id != null) {
            return sysMenuService.selectById(id);
        } else {
            return new Menu();
        }
    }

    /**
     * 进入列表页
     *
     * @param model
     * @return
     */
    @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<Menu> targetList = Lists.newArrayList();
        //取所有，含隐藏
        //会过滤掉 del_flag=1的记录，del_flag=1表示逻辑删除
        List<Menu> sourceList = sourceList = sysMenuService.selectAll(new Wrapper(new Menu()).orderBy("a.sort"));
        Menu.sortList2Tree(targetList, sourceList, Menu.getRootId(), true);//排序
        model.addAttribute("list", targetList);
        return "admin/sys/sysMenuList";
    }

    /**
     * 查询
     *
     * @param menu  实体对象
     * @param model
     * @return 查询出当前条件下的菜单以及所有父菜单
     */
    @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = "search")
    public String search(Menu menu, Model model) {
        menu.setSort(null);
        Wrapper wrapper = new Wrapper(menu);
        wrapper.orderBy("sort");
        List<Menu> menuList = sysMenuService.selectByWhere(wrapper);
        if (menuList.size() > 0) {
            List<Object> menuIds = new ArrayList<>();
            for (Menu a : menuList) {
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
                List<Menu> parentMenuList = sysMenuService.selectByIdIn(menuIds);
                if (parentMenuList.size() > 0) {
                    for (Menu m : parentMenuList) {
                        if (!menuList.contains(m)) {
                            menuList.add(m);
                        }
                    }
                }
            }
        }
        List<Menu> targetList = Lists.newArrayList();
        Menu.sortList2Tree(targetList, menuList, Menu.getRootId(), true);
        model.addAttribute("list", targetList);
        model.addAttribute("name", menu.getName());
        return "admin/sys/sysMenuList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param menu  实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "form")
    public String form(Menu menu, Model model) {
        if (menu.getParent() == null || menu.getParent().getId() == null) {
            menu.setParent(new Menu(Menu.getRootId()));
        }
        //menu.setParent(sysMenuService.getMenu(menu.getParent().getId()));
        menu.setParent(sysMenuService.selectById(menu.getParent().getId()));
        // 获取排序号，最末节点排序号+30
        if (menu.getId() == null) {
            List<Menu> targetList = Lists.newArrayList();
            List<Menu> sourceList = sysMenuService.findAllMenu();
            Menu.sortList2Tree(targetList, sourceList, menu.getParentId(), false);
            if (targetList.size() > 0) {
                menu.setSort(targetList.get(targetList.size() - 1).getSort() + 30);
            }
        }
        model.addAttribute("menu", menu);
        return "admin/sys/sysMenuForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param menu 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "save")
    public String save(Menu menu, Model model, RedirectAttributes redirectAttributes) {
        menu.setIsShow(R.get("isShow", "0"));
        if (!UserUtils.getUser().isAdmin()) {
            addMessage(redirectAttributes, FYUtils.fyParams("越权操作，只有超级管理员才能添加或修改数据！"));
            return "redirect:" + adminPath + "/sys/role.do?repage";
        }
        //表单验证
        List<String> errorList = validate(menu, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(menu, model);//回显错误提示
        }
        //业务处理
        sysMenuService.saveMenu(menu);
        addMessage(redirectAttributes, FYUtils.fyParams("保存菜单'") + menu.getName() + FYUtils.fyParams("'成功"));
        String stat = R.get("submitBtn");
        if (!"".equals(stat) && "1".equals(stat)) {
            return "redirect:" + adminPath + "/sys/menu/form.do?parent.id=" + menu.getParentId();
        } else {
            return "redirect:" + adminPath + "/sys/menu.do";

        }
    }

    /**
     * 执行删除方法
     *
     * @param menu 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:menu:drop")
    @RequestMapping(value = "delete")
    public String delete(Menu menu, RedirectAttributes redirectAttributes) {
        if (menu.getId() == null || Menu.isRoot(menu.getId())) {
            addMessage(redirectAttributes, FYUtils.fyParams("删除菜单失败, 不允许删除顶级菜单或编号为空"));
        } else {
            sysMenuService.deleteById(menu.getId());
            addMessage(redirectAttributes, FYUtils.fyParams("删除菜单成功"));
        }
        return "redirect:" + adminPath + "/sys/menu.do";
    }

    /**
     * 进入菜单树页面
     *
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "tree")
    public String tree() {
        return "admin/sys/menuTree";
    }

    /**
     * 进入菜单选择树页面
     *
     * @param parentId 菜单父id
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "treeselect")
    public String treeselect(String parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "admin/sys/menuTreeselect";
    }

    /**
     * 批量修改菜单排序
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "updateSort")
    public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
        List<Menu> menuList = new ArrayList<Menu>();
        for (int i = 0; i < ids.length; i++) {
            Menu menu = new Menu();
            menu.setId(Long.valueOf(ids[i]));
            menu.setSort(sorts[i]);
            menuList.add(menu);
        }
        sysMenuService.updateSelectiveBatch(menuList);
        addMessage(redirectAttributes, FYUtils.fyParams("保存菜单排序成功!"));
        return "redirect:" + adminPath + "/sys/menu.do";
    }

    /**
     * 列出所有菜单，用于设置上级菜单
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     *
     * @param extId 排除的ID
     * @param isShowHide 是否显示隐藏菜单,0否、1是
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide, HttpServletResponse response) {
        if(StringUtils.isBlank(isShowHide)){
            isShowHide="0";//默认不显示隐藏菜单
        }
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Menu> sourceList = sysMenuService.findAllMenu();
        List<Menu> targetList = Lists.newArrayList();
        Menu.sortList2Tree(targetList, sourceList, Menu.getRootId(), true);//排序
        for (int i = 0; i < targetList.size(); i++) {
            Menu e = targetList.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                if (isShowHide.equals("0") && e.getIsShow().equals("0")) {
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

//    /**
//     * 验证菜单编号是否有效
//     *
//     * @param oldMenuNum
//     * @param menuNum
//     * @return
//     */
//    @RequiresPermissions("user")
//    @ResponseBody
//    @RequestMapping(value = "checkMenuNum")
//    public String ajaxCheckMenuNum(String oldMenuNum, String menuNum) {
//        if (menuNum != null && menuNum.equals(oldMenuNum)) {
//            return "true";
//        } else if (menuNum != null && sysMenuService.findByMenuNum(menuNum) == null) {
//            return "true";
//        }
//        return "false";
//    }

    /**
     * 表单验证
     *
     * @param menu  实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Menu menu, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("请填写名称"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 100) {
            errorList.add(FYUtils.fyParams("名称最大长度不能超过100字符"));
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
