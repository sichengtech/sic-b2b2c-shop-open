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
import com.sicheng.admin.store.entity.StoreCategory;
import com.sicheng.admin.store.service.StoreCategoryService;
import com.sicheng.common.config.Global;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
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
import java.util.List;
import java.util.Map;

/**
 * 店铺商品分类Controller
 *
 * @author 蔡龙
 * @version 2017-02-13
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeCategory")
public class StoreCategoryController extends BaseController {

    @Autowired
    private StoreCategoryService storeCategoryService;

    @ModelAttribute
    public StoreCategory get(@RequestParam(required = false) Long id) {
        StoreCategory entity = null;
        if (id != null) {
            entity = storeCategoryService.get(id);
        }
        if (entity == null) {
            entity = new StoreCategory();
        }
        return entity;
    }

    @RequiresPermissions("store:storeCategory:view")
    @RequestMapping(value = {"list", ""})
    public String list(StoreCategory storeCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<StoreCategory> list = storeCategoryService.findList(storeCategory);
        model.addAttribute("list", list);
        return "admin/store/storeCategoryList";
    }

    @RequiresPermissions("store:storeCategory:view")
    @RequestMapping(value = "form")
    public String form(StoreCategory storeCategory, Model model) {
        if (storeCategory.getParent() != null && storeCategory.getParent().getId() != null) {
            storeCategory.setParent(storeCategoryService.get(storeCategory.getParent().getId()));
            // 获取排序号，最末节点排序号+30
            if (storeCategory.getId() == null) {
                StoreCategory storeCategoryChild = new StoreCategory();
                storeCategoryChild.setParent(new StoreCategory(storeCategory.getParent().getId()));
                List<StoreCategory> list = storeCategoryService.findList(storeCategory);
                if (list.size() > 0) {
                    storeCategory.setSort(list.get(list.size() - 1).getSort());
                    if (storeCategory.getSort() != null) {
                        storeCategory.setSort(storeCategory.getSort() + 30);
                    }
                }
            }
        }
        if (storeCategory.getSort() == null) {
            storeCategory.setSort(30);
        }
        model.addAttribute("storeCategory", storeCategory);
        return "admin/store/storeCategoryForm";
    }

    @RequiresPermissions("store:storeCategory:edit")
    @RequestMapping(value = "save")
    public String save(StoreCategory storeCategory, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, storeCategory)) {
            return form(storeCategory, model);
        }
        storeCategoryService.save(storeCategory);
        addMessage(redirectAttributes, "保存店铺商品分类成功");
        return "redirect:" + Global.getAdminPath() + "/store/storeCategory/?repage";
    }

    @RequiresPermissions("store:storeCategory:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreCategory storeCategory, RedirectAttributes redirectAttributes) {
//		storeCategoryService.delete(storeCategory);
        addMessage(redirectAttributes, "删除店铺商品分类成功");
        return "redirect:" + Global.getAdminPath() + "/store/storeCategory/?repage";
    }

    /**
     * 店铺分类
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     * @param extId 排除的ID
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<StoreCategory> list = storeCategoryService.findList(new StoreCategory());
        for (int i = 0; i < list.size(); i++) {
            StoreCategory e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

}