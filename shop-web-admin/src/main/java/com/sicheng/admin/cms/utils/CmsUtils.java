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
package com.sicheng.admin.cms.utils;

import com.google.common.collect.Lists;
import com.sicheng.admin.cms.entity.*;
import com.sicheng.admin.cms.service.*;
import com.sicheng.common.config.Global;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import org.springframework.ui.Model;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 内容管理工具类
 *
 * @author zhaolei
 * @version 2013-5-29
 */
public class CmsUtils {

    private static SiteService siteService = SpringContextHolder.getBean(SiteService.class);
    private static CategoryService categoryService = SpringContextHolder.getBean(CategoryService.class);
    private static ArticleService articleService = SpringContextHolder.getBean(ArticleService.class);
    private static ArticleDataService articleDataService = SpringContextHolder.getBean(ArticleDataService.class);
    private static LinkService linkService = SpringContextHolder.getBean(LinkService.class);
    private static ServletContext context = SpringContextHolder.getBean(ServletContext.class);
    // private static SiteAdService siteAdService = SpringContextHolder.getBean(SiteAdService.class);
    //rivate static MemberShopService memberShopService = SpringContextHolder.getBean(MemberShopService.class);

    /**
     * 获得站点列表
     */
    public static List<Site> getSiteList() {
        Page<Site> page = new Page<Site>(1, -1);
        page = siteService.findPage(page, new Site());
        return page.getList();
    }

    /**
     * 获得站点信息
     *
     * @param siteId 站点编号
     */
    public static Site getSite(Long siteId) {
        Long id = 1L;
        if (siteId != null) {
            id = siteId;
        }
        for (Site site : getSiteList()) {
            if (site.getId().equals(id)) {
                return site;
            }
        }
        return new Site(id);
    }

    /**
     * 获得主导航列表
     *
     * @param siteId 站点编号
     */
    public static List<Category> getMainNavList(Long siteId) {
        Category category = new Category();
        category.setSite(new Site(siteId));
        category.setParent(new Category(1L));
        category.setInMenu(Global.SHOW);
        Page<Category> page = new Page<Category>(1, -1);
        page = categoryService.find(page, category);
        return page.getList();
    }

    /**
     * 获取栏目
     *
     * @param categoryId 栏目编号
     * @return
     */
    public static Category getCategory(Long categoryId) {
        return categoryService.get(categoryId);
    }

    /**
     * 获得栏目列表
     *
     * @param siteId   站点编号
     * @param parentId 分类父编号
     * @param number   获取数目
     * @param param    预留参数，例： key1:'value1', key2:'value2' ...
     */
    public static List<Category> getCategoryList(Long siteId, Long parentId, int number, String param) {
        Page<Category> page = new Page<Category>(1, number, -1);
        Category category = new Category();
        category.setSite(new Site(siteId));
        category.setParent(new Category(parentId));
        if (StringUtils.isNotBlank(param)) {
            @SuppressWarnings({"unused", "rawtypes"})
            Map map = JsonMapper.getInstance().fromJson("{" + param + "}", Map.class);
        }
        page = categoryService.find(page, category);
        return page.getList();
    }

    /**
     * 获取栏目
     *
     * @param categoryIds 栏目编号
     * @return
     */
    public static List<Category> getCategoryListByIds(String categoryIds) {
        return categoryService.findByIds(categoryIds);
    }

    /**
     * 获取文章
     *
     * @param articleId 文章编号
     * @return
     */
    public static Article getArticle(Long articleId) {
        return articleService.get(articleId);
    }

    /**
     * 获取百度源码
     * @param ShopId 商铺ID
     * @return
     */
    //public static MemberShop getBaiduSource(String ShopId){
    //return memberShopService.selectById(ShopId);
    //}

    /**
     * 获取广告标题和内容
     * @param SiteAdContentId 广告内容编号
     * @return
     */
	/*public static SiteAd getSiteAd(String id){
		return siteAdService.get(id);
	}
	*/

    /**
     * 获取文章内容
     *
     * @param articleId 文章编号
     * @return
     */
    public static ArticleData getArticleData(Long articleId) {
        return articleDataService.get(articleId);
    }

    /**
     * 获取文章列表
     *
     * @param siteId     站点编号
     * @param categoryId 分类编号
     * @param number     获取数目
     * @param param      预留参数，例： key1:'value1', key2:'value2' ...
     *                   posid	推荐位（1：首页焦点图；2：栏目页文章推荐；）
     *                   image	文章图片（1：有图片的文章）
     *                   orderBy 排序字符串
     * @return ${fnc:getArticleList(category.site.id, category.id, not empty pageSize?pageSize:8, 'posid:2, orderBy: \"hits desc\"')}"
     */
    public static List<Article> getArticleList(Long siteId, Long categoryId, int number, String param) {
        Page<Article> page = new Page<Article>(1, number, -1);
        Category category = new Category(categoryId, new Site(siteId));
        category.setParentIds(String.valueOf(categoryId));
        Article article = new Article(category);
        article.setCategoryId(categoryId);
        if (StringUtils.isNotBlank(param)) {
            @SuppressWarnings({"rawtypes"})
            Map map = JsonMapper.getInstance().fromJson("{" + param + "}", Map.class);
            if (new Integer(1).equals(map.get("posid")) || new Integer(2).equals(map.get("posid"))) {
                article.setPosid(String.valueOf(map.get("posid")));
            }
            if (new Integer(1).equals(map.get("image"))) {
                article.setImage(Global.YES);
            }
            if (StringUtils.isNotBlank((String) map.get("orderBy"))) {
                page.setOrderBy((String) map.get("orderBy"));
            }
        }
        article.setDelFlag(Article.DEL_FLAG_NORMAL);
        page = articleService.findPage(page, article, false);
        return page.getList();
    }

    /**
     * 获取链接
     *
     * @param linkId 文章编号
     * @return
     */
    public static Link getLink(Long linkId) {
        return linkService.get(linkId);
    }

    /**
     * 获取链接列表
     *
     * @param siteId     站点编号
     * @param categoryId 分类编号
     * @param number     获取数目
     * @param param      预留参数，例： key1:'value1', key2:'value2' ...
     * @return
     */
    public static List<Link> getLinkList(Long siteId, Long categoryId, int number, String param) {
        Page<Link> page = new Page<Link>(1, number, -1);
        Link link = new Link(new Category(categoryId, new Site(siteId)));
        if (StringUtils.isNotBlank(param)) {
            @SuppressWarnings({"unused", "rawtypes"})
            Map map = JsonMapper.getInstance().fromJson("{" + param + "}", Map.class);
        }
        link.setDelFlag(Link.DEL_FLAG_NORMAL);
        page = linkService.findPage(page, link, false);
        return page.getList();
    }

    /**
     * 获得文章动态URL地址
     *
     * @param article
     * @return url
     */
    public static String getUrlDynamic(Article article) {
        if (StringUtils.isNotBlank(article.getLink())) {
            return article.getLink();
        }
        StringBuilder str = new StringBuilder();
        str.append(context.getContextPath()).append(Global.getFrontPath());
        str.append("/cms");
        str.append("/view-").append(article.getCategory().getId()).append("-").append(article.getId()).append(Global.getUrlSuffix());
        return str.toString();
    }

    /**
     * 获得栏目动态URL地址
     *
     * @param category
     * @return url
     */
    public static String getUrlDynamic(Category category) {
        if (StringUtils.isNotBlank(category.getHref())) {
            if (!category.getHref().contains("://")) {
                return context.getContextPath() + Global.getFrontPath() + category.getHref();
            } else {
                return category.getHref();
            }
        }
        StringBuilder str = new StringBuilder();
        str.append(context.getContextPath()).append(Global.getFrontPath());
        str.append("/cms");
        str.append("/list-").append(category.getId()).append(Global.getUrlSuffix());
        return str.toString();
    }

    /**
     * 从图片地址中去除ContextPath地址
     *
     * @param src
     * @return src
     */
    public static String formatImageSrcToDb(String src) {
        if (StringUtils.isBlank(src)) return src;
        if (src.startsWith(context.getContextPath() + "/userfiles")) {
            return src.substring(context.getContextPath().length());
        } else {
            return src;
        }
    }

    /**
     * 从图片地址中加入ContextPath地址
     *
     * @param src
     * @return src
     */
    public static String formatImageSrcToWeb(String src) {
        if (StringUtils.isBlank(src)) return src;
        if (src.startsWith(context.getContextPath() + "/userfiles")) {
            return src;
        } else {
            return context.getContextPath() + src;
        }
    }

    public static void addViewConfigAttribute(Model model, String param) {
        if (StringUtils.isNotBlank(param)) {
            @SuppressWarnings("rawtypes")
            Map map = JsonMapper.getInstance().fromJson(param, Map.class);
            if (map != null) {
                for (Object o : map.keySet()) {
                    model.addAttribute("viewConfig_" + o.toString(), map.get(o));
                }
            }
        }
    }

    public static void addViewConfigAttribute(Model model, Category category) {
        if(category==null){
            return;
        }
        List<Category> categoryList = Lists.newArrayList();
        Category c = category;
        boolean goon = true;
        do {
            if (c.getParent() == null || c.getParent().isRoot()) {
                goon = false;
            }
            categoryList.add(c);
            c = c.getParent();
        } while (goon);
        Collections.reverse(categoryList);
        for (Category ca : categoryList) {
            addViewConfigAttribute(model, ca.getViewConfig());
        }
    }
}