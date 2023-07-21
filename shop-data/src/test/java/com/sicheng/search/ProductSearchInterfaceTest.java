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

import com.sicheng.admin.product.entity.SolrProduct;
import com.sicheng.common.persistence.Page;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “商品”全文检索接口的单元测试
 * 本接口的实现类
 *      1、solr搜索引擎实现
 *      2、ES搜索引擎实现
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ProductSearchInterfaceTest {

    @Autowired
    private ProductSearchInterface productSearch;

    /**
     * 对搜索进行测试
     */
    @Test
    public void test_search() {
        // 准备查询条件
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "1");
        paramMap.put("brand_name", "领动");
        paramMap.put("id", "282");
        //paramMap.put("p_id", "301,282");
        //paramMap.put("category_id", "3844");
        //paramMap.put("price2","[889 TO 901]");
        //paramMap.put("brand_id","861");
        //paramMap.put("param_value","1034_0-100");
        //Map<String, Object> param2 = new HashMap<String, Object>();
        // 排序
        Map<String, String> sortMap = new HashMap<String, String>();
        sortMap.put("update_date", "desc");

        Map<String, Object> resultMap = productSearch.search(paramMap, sortMap, new Page(1, 20));
        ResultSet resultSet = (ResultSet) resultMap.get("search");
        List<SolrProduct> list = resultSet.getItems();
        for (int i = 0; i < list.size(); i++) {
            SolrProduct resultDoc = list.get(i);
            System.out.println(resultDoc.getPId() + "---" + resultDoc);
        }
//        System.exit(0);

//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = "2019-11-12 11:57:01";
//        System.out.println(time);
//        try {
//            String format1 = DateUtil.getThreadLocalDateFormat().format(format.parse(time));
//            System.out.println(format1);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 对搜索进行测试2
     */
    @Test
    public void test_search2() {
        // 准备查询条件
        Map<String, Object> paramMap = new HashMap<>();
        /*paramMap.put("name", "1");
        paramMap.put("brand_name","领动");
        paramMap.put("id", "282");
        paramMap.put("p_id", "301,282");
        paramMap.put("category_id", "3844");
        paramMap.put("price2","[889 TO 901]");
        paramMap.put("brand_id","861");
        paramMap.put("param_value","1034_0-100");
        Map<String, Object> param2 = new HashMap<String, Object>();*/

        // 排序
        Map<String, String> sortMap = new HashMap<>();
        sortMap.put("update_date", "desc");
        //执行查询
        Map<String, Object> resultMap = productSearch.search(paramMap, sortMap, new Page(1, 20));
        ResultSet Result = (ResultSet) resultMap.get("search");
        List<SolrProduct> list = Result.getItems();
        for (SolrProduct resultDoc:list) {
            //SolrProduct resultDoc = list.get(i);
            System.out.println("solr搜索结果：" + resultDoc.toString());
        }
    }

//    /**
//     * 测试--删除全部商品文档
//     * 为什么注释了？因为会删除全部索引，这不是我希望的。
//     */
//    @Test
//    public void test_deleteDocAll() {
//        productSearch.deleteDocAll();
//    }

    /**
     * 测试--按ID删除文档
     */
    @Test
    public void test_deleteDoc() {
        Long id=4520L;//商品ID,id为4520的商品在表中是真实存在的
        productSearch.deleteDoc(id);
    }

    /**
     * 测试--根据店铺id删除商品文档
     */
    @Test
    public void test_deleteDocByStoreId() {
        Long id=8888L;//店铺ID,不存在，但可以运行
        productSearch.deleteDocByStoreId(id);
    }

    /**
     * 测试--按ID添加文档
     */
    @Test
    public void test_addDoc() {
        Long id=4520L;//商品ID,id为4520的商品在表中是真实存在的
        productSearch.addDoc(id);

    }
}
