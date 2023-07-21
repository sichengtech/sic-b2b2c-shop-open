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
package com.sicheng.admin.cms.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sicheng.admin.cms.entity.Article;
import com.sicheng.admin.cms.entity.Category;
import com.sicheng.admin.cms.entity.Site;
import com.sicheng.admin.cms.service.CategoryService;
import com.sicheng.admin.cms.service.FileTplService;
import com.sicheng.admin.cms.service.SiteService;
import com.sicheng.admin.cms.utils.TplUtils;

import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
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
 * 栏目Controller
 *
 * @author zhaolei
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileTplService fileTplService;
    @Autowired
    private SiteService siteService;


    /**
     * 按实体id查询数据，类中先执行的方法
     *
     * @param id 实体的id
     * @return
     */
    @ModelAttribute("category")
    public Category get(@RequestParam(required = false) Long id, Model model) {
        String menu3 = "070210";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (id != null) {
            return categoryService.get(id);
        } else {
            return new Category();
        }
    }

    /**
     * 进入列表页
     *
     * @param model
     * @return
     */
    @RequiresPermissions("cms:category:view")
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<Category> list = Lists.newArrayList();
        List<Category> sourcelist = categoryService.findByUser(true, null);
        Category.sortList(list, sourcelist, 1L);
        model.addAttribute("list", list);
        return "admin/cms/siteArticleChannelList";

    }

    /**
     * 进入添加页/编辑页
     *
     * @param category 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("cms:category:edit")
    @RequestMapping(value = "form")
    public String form(Category category, Model model) {
        if (category.getParent() == null || category.getParent().getId() == null) {
            category.setParent(new Category(1L));
        }
        Category parent = categoryService.get(category.getParent().getId());
        category.setParent(parent);
        if (category.getOffice() == null || category.getOffice().getId() == null) {
            category.setOffice(parent.getOffice());
        }
        model.addAttribute("listViewList", getTplContent(Category.DEFAULT_TEMPLATE));
        model.addAttribute("category_DEFAULT_TEMPLATE", Category.DEFAULT_TEMPLATE);
        model.addAttribute("contentViewList", getTplContent(Article.DEFAULT_TEMPLATE));
        model.addAttribute("article_DEFAULT_TEMPLATE", Article.DEFAULT_TEMPLATE);
        model.addAttribute("office", category.getOffice());
        model.addAttribute("category", category);
        return "admin/cms/siteArticleChannelForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param category           实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:category:edit")
    @RequestMapping(value = "save")
    public String save(Category category, Model model, RedirectAttributes redirectAttributes) {
        category.setInMenu(R.get("inMenu", "0"));
        category.setInList(R.get("inList", "0"));
        category.setAllowComment(R.get("allowComment", "0"));
        category.setIsAudit(R.get("isAudit", "0"));
        //表单验证
        List<String> errorList = validate(category, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(category, model);//回显错误提示
        }
        //业务处理
        categoryService.save(category);
        addMessage(redirectAttributes, FYUtils.fyParams("保存栏目'") + category.getName() + FYUtils.fyParams("'成功"));
        if ("1".equals(R.get("submit"))) {
            return "redirect:" + adminPath + "/cms/category/form.do?repage";
        } else if ("2".equals(R.get("submit"))) {
            return "redirect:" + adminPath + "/cms/category/list.do?repage";
        }
        return null;
    }

    /**
     * 执行删除方法
     *
     * @param category           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:category:drop")
    @RequestMapping(value = "delete")
    public String delete(Category category, RedirectAttributes redirectAttributes) {
        if (Category.isRoot(category.getId())) {
            addMessage(redirectAttributes, FYUtils.fyParams("删除栏目失败, 不允许删除顶级栏目或编号为空"));
        } else {
            //删除栏目
            categoryService.delete(category);
//			//删除栏目下的文章
//			Article article = new Article();
//			article.setCategory(category);
//			articleService.delete(article);
            addMessage(redirectAttributes, FYUtils.fyParams("删除栏目成功"));
        }
        return "redirect:" + adminPath + "/cms/category.do";
    }

    /**
     * 批量修改栏目排序
     */
    @RequiresPermissions("cms:category:edit")
    @RequestMapping(value = "updateSort")
    public String updateSort(Long[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
        int len = ids.length;
        Category[] entitys = new Category[len];
        for (int i = 0; i < len; i++) {
            entitys[i] = categoryService.get(ids[i]);
            entitys[i].setSort(sorts[i]);
            categoryService.save(entitys[i]);
        }
        addMessage(redirectAttributes, FYUtils.fyParams("保存栏目排序成功!"));
        return "redirect:" + adminPath + "/cms/category.do";
    }

    /**
     * 文章分类树的数据
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     *
     * @param module
     * @param extId 排除的ID
     * @param response
     * @return
     */
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(String module, @RequestParam(required = false) String extId, HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Category> list = categoryService.findByUser(true, module);
        for (int i = 0; i < list.size(); i++) {
            Category e = list.get(i);
            if (extId == null || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParent() != null ? e.getParent().getId() : 0);
                map.put("name", e.getName());
                map.put("module", e.getModule());
                mapList.add(map);
            }
        }
        return mapList;
    }

    private List<String> getTplContent(String prefix) {
        List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
        tplList = TplUtils.tplTrim(tplList, prefix, "");
        return tplList;
    }

    /**
     * 表单验证
     *
     * @param category 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Category category, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("栏目名称不能为空"));
        }
        System.out.println(R.get("name").length());
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 100) {
            errorList.add(FYUtils.fyParams("栏目名称最大长度不能超过100字符"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fyParams("楼层排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("楼层排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("inMenu"))) {
            errorList.add(FYUtils.fyParams("请选择在导航中显示"));
        }
        if (StringUtils.isBlank(R.get("inList"))) {
            errorList.add(FYUtils.fyParams("请选择在分类页中显示"));
        }
        if (StringUtils.isBlank(R.get("allowComment"))) {
            errorList.add(FYUtils.fyParams("请选择是否允许评论"));
        }
        if (StringUtils.isBlank(R.get("isAudit"))) {
            errorList.add(FYUtils.fyParams("请选择是否需要审核"));
        }
        return errorList;
    }
}
