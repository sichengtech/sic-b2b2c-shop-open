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
package com.sicheng.admin.cms.service;

import com.google.common.collect.Lists;
import com.sicheng.admin.cms.dao.ArticleDao;
import com.sicheng.admin.cms.dao.ArticleDataDao;
import com.sicheng.admin.cms.dao.CategoryDao;
import com.sicheng.admin.cms.entity.Article;
import com.sicheng.admin.cms.entity.ArticleData;
import com.sicheng.admin.cms.entity.Category;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.xss.XssClean;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章表 Service
 *
 * @author cl
 * @version 2017-02-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ArticleService extends CrudService<ArticleDao, Article> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private ArticleDataDao articleDataDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ArticleDao articleDao;


    @Transactional(readOnly = false)
    public Page<Article> findPage(Page<Article> page, Article article, boolean isDataScopeFilter) {
        // 更新过期的权重，间隔为“6”个小时
        Date updateExpiredWeightDate = (Date) super.cache.get(CacheConstant.ADMIN_UPDAT_EEXPIRED_WEIGHT_DATE_BY_ARTICLE);
        if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null && updateExpiredWeightDate.getTime() < new Date().getTime())) {
            dao.updateExpiredWeight(article);
            super.cache.put(CacheConstant.ADMIN_UPDAT_EEXPIRED_WEIGHT_DATE_BY_ARTICLE, DateUtils.addHours(new Date(), 6), 3600L);
        }
        if (article.getCategory() != null && article.getCategory().getId() != null && !Category.isRoot(article.getCategory().getId())) {
            Category category = categoryDao.get(article.getCategory().getId());
            if (category == null) {
                category = new Category();
            }
//            category.setParentIds(String.valueOf(category.getId()));
            category.setSite(category.getSite());
            article.setCategory(category);
            article.setCategoryId(category.getId());
        } else {
            article.setCategory(new Category());
        }
        return findPage1(page, article);

    }

    @Transactional(readOnly = false)
    public void save(Article article) {
        if (article.getArticleData().getContent() != null) {
            article.getArticleData().setContent(StringEscapeUtils.unescapeHtml4(
                    article.getArticleData().getContent()));//转回来（还原）
        }
        // 如果没有审核权限，则将当前内容改为待审核状态
        if (!UserUtils.getSubject().isPermitted("cms:article:auth")) {
            article.setDelFlag(Article.DEL_FLAG_AUDIT);
        }
        // 如果栏目不需要审核，则将该内容设为发布状态
        if (article.getCategory() != null && article.getCategory().getId() != null) {
            Category category = categoryDao.get(article.getCategory().getId());
            if (!Global.YES.equals(category.getIsAudit())) {
                article.setDelFlag(Article.DEL_FLAG_NORMAL);
            }
        }
        article.setUpdateBy(UserUtils.getUser());
        article.setUpdateDate(new Date());
        if (StringUtils.isNotBlank(article.getViewConfig())) {
            article.setViewConfig(StringEscapeUtils.unescapeHtml4(article.getViewConfig()));//转回来（还原）
        }

        ArticleData articleData = new ArticleData();
        if (article.getId() == null) {
            article.preInsert(UserUtils.getUser());
            articleData = article.getArticleData();
            String html_unsafe = StringEscapeUtils.unescapeHtml4(articleData.getContent());//转回来（还原）
            String html_safe = XssClean.clean(html_unsafe);//按白名单进行危险字符过滤
            articleData.setContent(html_safe);
            articleDao.insertArticle(article);
            articleData.setId(article.getId());
            articleData.setPkMode(1);
            articleDataDao.insertArticleData(articleData);
        } else {
            article.preUpdate(UserUtils.getUser());
            articleData = article.getArticleData();
            articleData.setId(article.getId());
            dao.updateByIdSelective(article);
            articleDataDao.updateByIdSelective(article.getArticleData());
        }
    }

    @Transactional(readOnly = false)
    public void delete(Article article, Boolean isRe) {
        delete(article);
    }

    /**
     * 通过编号获取内容标题
     *
     * @return new Object[]{栏目Id,文章Id,文章标题}
     */
    public List<Object[]> findByIds(String ids) {
        if (ids == null) {
            return new ArrayList<Object[]>();
        }
        List<Object[]> list = Lists.newArrayList();
        String[] idss = StringUtils.split(ids, ",");
        Article e = null;
        for (int i = 0; (idss.length - i) > 0; i++) {
            e = dao.get(Long.valueOf(idss[i]));
            list.add(new Object[]{e.getCategory().getId(), e.getId(), StringUtils.abbr(e.getTitle(), 50)});
        }
        return list;
    }

    /**
     * 点击数加一
     */
    @Transactional(readOnly = false)
    public void updateHitsAddOne(Long id) {
        dao.updateHitsAddOne(id);
    }

    /**
     * 更新索引
     */
    public void createIndex() {
        //dao.createIndex();
    }

    /**
     * 全文检索
     */
    //FIXME 暂不提供检索功能
    public Page<Article> search(Page<Article> page, String q, String categoryId, String beginDate, String endDate) {
        return page;
    }

    public Page<Article> findPage1(Page<Article> page, Article article) {
        Wrapper w=new Wrapper(article);
        w.orderBy("a.weight DESC, a.update_date DESC");
        List list=dao.selectByWhere(page,w);
        page.setList(list);
        return page;
    }

    @Transactional(readOnly = false)
    public void delete(Article article) {
        dao.deleteById(article.getId());
    }

    @Transactional(readOnly = false)
    public Article get(Long id) {
        return dao.get(id);
    }

}