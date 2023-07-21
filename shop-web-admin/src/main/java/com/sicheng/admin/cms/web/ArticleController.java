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

import com.sicheng.admin.cms.entity.Article;
import com.sicheng.admin.cms.entity.Category;
import com.sicheng.admin.cms.entity.Site;
import com.sicheng.admin.cms.service.*;
import com.sicheng.admin.cms.utils.CmsUtils;
import com.sicheng.admin.cms.utils.TplUtils;

import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.Page;
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

/**
 * 文章Controller
 *
 * @author zhaolei
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDataService articleDataService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileTplService fileTplService;
    @Autowired
    private SiteService siteService;


    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public Article get(@RequestParam(required = false) Long id, Model model) {
        String menu3 = "070212";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (id != null) {
            return articleService.selectById(id);
        } else {
            return new Article();
        }
    }

    /**
     * 进入列表页
     *
     * @param article  实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = {"list", ""})
    public String list(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
        model.addAttribute("page", page);
        model.addAttribute("article", article);
        return "admin/cms/siteArticleList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param article 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "form")
    public String form(Article article, Model model) {
        if (article == null) {
            return "error/404";
        }
        if (article.getId() != null) {
            article = articleService.selectById(article.getId());
        }
        // 如果当前传参有子节点，则选择取消传参选择
        if (article.getCategory() != null && article.getCategory().getId() != null) {
            List<Category> list = categoryService.findByParentId(article.getCategory().getId(), Site.getCurrentSiteId());
            if (list.size() > 0) {
                article.setCategory(null);
            } else {
                article.setCategory(categoryService.get(article.getCategory().getId()));
            }
        }
        article.setArticleData(articleDataService.get(article.getId()));
//		if (article.getCategory()=null && StringUtils.isNotBlank(article.getCategory().getId())){
//			Category category = categoryService.get(article.getCategory().getId());
//		}
        model.addAttribute("contentViewList", getTplContent());
        model.addAttribute("article_DEFAULT_TEMPLATE", Article.DEFAULT_TEMPLATE);
        model.addAttribute("article", article);
        CmsUtils.addViewConfigAttribute(model, article.getCategory());
        model.addAttribute("accessKey", AccessKey.generateAccessKey());//上传图片需要AccessKey
        return "admin/cms/siteArticleForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param article            实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "save")
    public String save(Article article, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(article, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(article, model);//回显错误提示
        }
        article.setHits(0);
        //业务处理
        if (article.getCategory() != null && article.getCategory().getId() != null) {
            article.setCategoryId(article.getCategory().getId());
        }
        //article.getArticleData().setAllowComment(R.get("articleData.allowComment","0"));
        articleService.save(article);
        addMessage(redirectAttributes, FYUtils.fyParams("保存文章'") + StringUtils.abbr(article.getTitle(), 50) + FYUtils.fyParams("'成功"));
        if ("1".equals(R.get("submit"))) {
            return "redirect:" + adminPath + "/cms/article/form.do?repage";
        } else if ("2".equals(R.get("submit"))) {
            return "redirect:" + adminPath + "/cms/article.do?repage";
        }
        return null;
    }

    /**
     * 执行删除方法
     *
     * @param article            实体对象
     * @param categoryId         文章分类id
     * @param isRe
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:article:drop")
    @RequestMapping(value = "delete")
    public String delete(Article article, String categoryId, @RequestParam(required = false) Boolean isRe, RedirectAttributes redirectAttributes) {
        // 如果没有审核权限，则不允许删除或发布。
        if (!UserUtils.getSubject().isPermitted("cms:article:audit")) {
            addMessage(redirectAttributes, FYUtils.fyParams("你没有删除或发布权限"));
        }
        articleService.delete(article, isRe);
        addMessage(redirectAttributes, (isRe != null && isRe ? FYUtils.fyParams("发布") : FYUtils.fyParams("删除")) + FYUtils.fyParams("文章成功"));
        return "redirect:" + adminPath + "/cms/article.do?repage";
    }

    /**
     * 文章选择列表
     */
    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = "selectList")
    public String selectList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(article, request, response, model);
        return "admin/cms/articleSelectList";
    }

    /**
     * 通过编号获取文章标题
     */
    @RequiresPermissions("cms:article:view")
    @ResponseBody
    @RequestMapping(value = "findByIds")
    public String findByIds(String ids) {
        List<Object[]> list = articleService.findByIds(ids);
        return JsonMapper.getInstance().toJson(list);
    }

    private List<String> getTplContent() {
        List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
        tplList = TplUtils.tplTrim(tplList, Article.DEFAULT_TEMPLATE, "");
        return tplList;
    }

    /**
     * 表单验证
     *
     * @param article 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Article article, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("category.id"))) {
            errorList.add(FYUtils.fyParams("请选择归属栏目"));
        }
        if (StringUtils.isBlank(R.get("title"))) {
            errorList.add(FYUtils.fyParams("请填写标题"));
        }
        if (StringUtils.isNotBlank(R.get("title")) && R.get("title").length() > 100) {
            errorList.add(FYUtils.fyParams("标题最大长度不能超过100字符"));
        }
        if (StringUtils.isBlank(R.get("articleData.content"))) {
            errorList.add(FYUtils.fyParams("请填写文章内容"));
        }
/*		if(StringUtils.isBlank(R.get("articleData.allowComment"))){
			errorList.add(FYUtils.fyParams("请选择是否允许评论"));
		}*/
        return errorList;
    }
}
