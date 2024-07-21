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
package com.sicheng.front.template;

import com.sicheng.admin.cms.entity.Article;
import com.sicheng.admin.cms.entity.Category;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.service.ArticleService;
import com.sicheng.front.service.CategoryService;
import com.sicheng.front.template.util.TagUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: 获取文章(列表,有分页)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月25日 下午4:47:29
 */
public class CmsArticlePageFunction implements Function {

    private static final String CHANNEL_ID = "channelId";//栏目id
    private static final String IDS = "ids";//，隔开的多个id
    private static final String K = "k";//按关键词搜索文章

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long channelId = TagUtils.getLong(tagParamMap, CHANNEL_ID);
        String ids = TagUtils.getString(tagParamMap, IDS);
        String k = TagUtils.getString(tagParamMap, K);
        Page page = TagUtils.getPage(tagParamMap);// 从入参中取得Page分页对象
        //获取文章栏目的Service
        CategoryService categoryService = SpringContextHolder.getBean(CategoryService.class);
        //执行业务，查询出文章列表
        ArticleService articleService = SpringContextHolder.getBean(ArticleService.class);
        Article article = new Article();
        Wrapper wrapper = new Wrapper();
        //判断栏目id是否有子节点
        if (categoryService.hadChildNode(channelId)) {
            List<Category> list = categoryService.findChildNodeAll(channelId);
            List<Long> cidList = new ArrayList<>();
            for (Category category : list) {
                cidList.add(category.getId());
            }
            wrapper.and("a.category_id in", cidList);
        } else {
            article.setCategoryId(channelId);
        }
        wrapper.setEntity(article);
        if (StringUtils.isNotBlank(ids)) {
            String[] idsArray = ids.split(",");
            List<Long> idList = new ArrayList<>();
            if (idsArray.length > 0) {
                for (int i = 0; i < idsArray.length; i++) {
                    idList.add(Long.parseLong(idsArray[i]));
                }
                wrapper.and("a.id in", idList);
            }
        }
        if (StringUtils.isNotBlank(k)) {
            wrapper.and("a.title like", "%" + k + "%");
        }
        wrapper.orderBy("a.weight ASC,a.create_date DESC");
        page = articleService.selectByWhere(page, wrapper);
        return page;
    }

}
