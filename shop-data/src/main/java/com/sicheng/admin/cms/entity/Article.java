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
package com.sicheng.admin.cms.entity;

import com.google.common.collect.Lists;
import com.sicheng.admin.cms.dao.ArticleDataDao;
import com.sicheng.admin.cms.dao.CategoryDao;
import com.sicheng.admin.cms.utils.CmsUtils2;
import com.sicheng.admin.sys.dao.UserDao;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 文章表 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-09
 */
public class Article extends ArticleBase<Article> {

    public static final String DEFAULT_TEMPLATE = "frontViewArticle";

    private static final long serialVersionUID = 1L;

    public Article() {
        super();
//		this.setWeight(0);
//		this.setHits(0);
//		this.setPosid("");
    }

    public Article(Long id) {
        super(id);
    }

    public Article(Category category) {
        this();
        this.category = category;
    }

    public void prePersist() {
        //TODO 后续处理，暂不知有何用处
        //super.prePersist();
        articleData.setId(this.id);
    }

    //对于实体类的扩展代码，请写在这里

    private Category category;// 分类编号
    private ArticleData articleData;    //文章副表you45897
    private User user;

    public Category getCategory() {
        if (category == null) {
            CategoryDao dao = SpringContextHolder.getBean(CategoryDao.class);
            category = dao.selectById(this.getCategoryId());
        }
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArticleData getArticleData() {
        if (articleData == null) {
            ArticleDataDao dao = SpringContextHolder.getBean(ArticleDataDao.class);
            articleData = dao.selectById(this.getId());
        }
        return articleData;
    }

    public void setArticleData(ArticleData articleData) {
        this.articleData = articleData;
    }

    public User getUser() {
        if (user == null) {
            UserDao dao = SpringContextHolder.getBean(UserDao.class);
            if (this.getCreateBy() != null) {
                user = dao.selectById(this.getCreateBy().getId());
            }
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getPosidList() {
        List<String> list = Lists.newArrayList();
        if (this.getPosid() != null) {
            for (String s : StringUtils.split(this.getPosid(), ",")) {
                list.add(s);
            }
        }
        return list;
    }

    public void setPosidList(List<String> list) {
        this.setPosid("," + StringUtils.join(list, ",") + ",");
    }

    public String getUrl() {
        return CmsUtils2.getUrlDynamic(this);
    }

    public String getImageSrc() {
        return CmsUtils2.formatImageSrcToWeb(this.getImage());
    }

}