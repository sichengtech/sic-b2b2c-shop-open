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

package com.sicheng.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * “文章”全文检索接口，的单元测试类
 * 本接口的实现类
 * 1、solr搜索引擎实现
 * 2、ES搜索引擎实现
 *
 * @author zhaolei
 * @version 2022-08-11 20:37
 *
 * <p>重要修改历史记录1: xxxx  。修改人：xx</p>
 * <p>重要修改历史记录2: xxxx  。修改人：xx</p>
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ArticleSearchInterfaceTest {

    @Autowired
    private ArticleSearchInterface articleSearch;

    /**
     * 按关键字搜索
     */
    @Test
    public void test_search() {
//        // String queryStr="基于java语言开发的轻量级的中文分词工具包";
//        String queryStr = "SpringBoot奔跑，大哥";
//        ResultSet<ArticleBo> resultSet = articleSearch.search(queryStr, 0, 20, 0);
//        System.out.println("搜索耗时：" + resultSet.getSearchtime());
//        System.out.println("总条数：" + resultSet.getTotal());
//        List<ArticleBo> list = resultSet.getItems();
//        for (int i = 0; i < list.size(); ++i) {
//            ArticleBo resultArticleBo = list.get(i);
//            System.out.println(resultArticleBo);
//        }
//        System.out.println("solr测试结束");
    }
}
