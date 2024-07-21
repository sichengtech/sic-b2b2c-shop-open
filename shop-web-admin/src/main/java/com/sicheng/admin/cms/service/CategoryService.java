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
import com.google.common.collect.Sets;
import com.sicheng.admin.cms.dao.CategoryDao;
import com.sicheng.admin.cms.entity.Category;
import com.sicheng.admin.cms.entity.Site;
import com.sicheng.admin.sys.entity.Office;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.TreeService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 文章栏目Service
 *
 * @author cl
 * @version 2017-02-14
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class CategoryService extends TreeService<CategoryDao, Category> {

    //请在这里编写业务逻辑

    //TreeService父类中N个树结构的常用方法，已全部继承下来，可直接使用。

    //父父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    public static final String CACHE_CATEGORY_LIST = "categoryList";

    private Category entity = new Category();

    public List<Category> findByUser(boolean isCurrentSite, String module) {
        List<Category> list = new ArrayList<Category>();
        User user = UserUtils.getUser();
        Category category = new Category();
        category.setOffice(new Office());
        category.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
        category.setSite(new Site());
        category.setParent(new Category());
        list = dao.findList(category);
        // 将没有父节点的节点，找到父节点
        Set<Long> parentIdSet = Sets.newHashSet();
        for (Category e : list) {
            if (e.getParent() != null && e.getParent().getId() != null) {
                boolean isExistParent = false;
                for (Category e2 : list) {
                    if (e.getParent().getId().equals(e2.getId())) {
                        isExistParent = true;
                        break;
                    }
                }
                if (!isExistParent) {
                    parentIdSet.add(e.getParent().getId());
                }
            }
        }
        if (parentIdSet.size() > 0) {
            //FIXME 暂且注释，用于测试
//				dc = dao.createDetachedCriteria();
//				dc.add(Restrictions.in("id", parentIdSet));
//				dc.add(Restrictions.eq("delFlag", Category.DEL_FLAG_NORMAL));
//				dc.addOrder(Order.asc("site.id")).addOrder(Order.asc("sort"));
//				list.addAll(0, dao.find(dc));
        }
        if (isCurrentSite) {
            List<Category> categoryList = Lists.newArrayList();
            for (Category e : list) {
                if (Category.isRoot(e.getId()) || (e.getSite() != null && e.getSite().getId() != null
                        && e.getSite().getId().equals(Site.getCurrentSiteId()))) {
                    if (StringUtils.isNotEmpty(module)) {
                        if (module.equals(e.getModule()) || "".equals(e.getModule())) {
                            categoryList.add(e);
                        }
                    } else {
                        categoryList.add(e);
                    }
                }
            }
            return categoryList;
        }
        return list;
    }

    public List<Category> findByParentId(Long parentId, Long siteId) {
        Category parent = new Category();
        parent.setId(parentId);
        entity.setParent(parent);
        Site site = new Site();
        site.setId(siteId);
        entity.setSite(site);
        return dao.findByParentIdAndSiteId(entity);
    }

    public Page<Category> find(Page<Category> page, Category category) {
        category.setInMenu(Global.SHOW);
        page.setList(dao.findModule(category));
        Wrapper w=new Wrapper(category);
        w.orderBy("a.site_id,a.sort ASC");
        List list=dao.selectByWhere(page,w);
        page.setList(list);
        return page;
    }

    @Transactional(readOnly = false)
    public void save(Category category) {
        category.setSite(new Site(Site.getCurrentSiteId()));
        if (StringUtils.isNotBlank(category.getViewConfig())) {
            category.setViewConfig(StringEscapeUtils.unescapeHtml4(category.getViewConfig()));//转回来（还原）
        }
        category.preInsert(UserUtils.getUser());
        super.save(category);
    }

    @Transactional(readOnly = false)
    public void delete(Category category) {
        delete1(category);
    }

    /**
     * 通过编号获取栏目列表
     */
    public List<Category> findByIds(String ids) {
        List<Category> list = Lists.newArrayList();
        String[] idss = StringUtils.split(ids, ",");
        if (idss.length > 0) {
            for (String id : idss) {
                Category e = dao.get(Long.valueOf(id));
                if (null != e) {
                    list.add(e);
                }
            }
        }
        return list;
    }


    @Transactional(readOnly = false)
    public void delete1(Category category) {
        dao.delete(category);
    }

    @Transactional(readOnly = false)
    public Category get(Long id) {
        return dao.get(id);
    }

}