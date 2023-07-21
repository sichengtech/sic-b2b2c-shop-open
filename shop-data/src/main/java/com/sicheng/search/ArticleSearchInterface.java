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
package com.sicheng.search;

import com.sicheng.admin.cms.entity.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * “文章”全文检索接口，用于通过关键词来搜索出相关的文章
 * 本接口的实现类
 *      1、solr搜索引擎实现
 *      2、ES搜索引擎实现
 *
 * @author zhaolei
 */
public interface ArticleSearchInterface<T> {
    Logger logger = LoggerFactory.getLogger(ArticleSearchInterface.class);

    /**
     * 搜索文章
     * 按一个关键词搜索文章列表
     *
     * @param queryStr 搜索的关键词
     * @param start    指定搜索结果集的偏移量。取值范围：[0,5000], 默认值：0
     * @param count    指定返回结果集的数量。 取值范围：[0,500],默认值：20
     * @param type    0:文章的摘要中的关键字要高亮，1：关键字不高亮
     *                0为文章服务搜索文章，1为sitemap服务只搜索id
     * @return
     */
    ResultSet<T> search(String queryStr, int start, int count, int type) ;

    /**
     * 向索引库中添加文档
     *
     * @param id 商品ID，用于从库中查出商品
     *           文章ID，用于从库中查出文章
     */
    void addDoc(Long id);

    /**
     * 批量添加文档到索引中
     *
     * @param list
     */
    void addDocList(List<Article> list);

    /**
     * 按ID删除文档
     *
     * @param id
     */
    void deleteDoc(Long id) ;

    /**
     * 删除全部文档
     */
    void deleteDocAll();

    /**
     * 销毁Client
     */
    void destroy();

}
