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

import com.sicheng.common.persistence.Page;

import java.util.List;
import java.util.Map;

/**
 * “商品”全文检索接口
 * 本接口的实现类
 *      1、solr搜索引擎实现
 *      2、ES搜索引擎实现
 *
 * @author zhaolei
 */
public interface ProductSearchInterface<T> {
    /**
     * 搜索商品
     * 按多个条件搜索商品列表
     *
     * @param paramMap 多个查询条件
     * @param sortMap  多个排序条件
     * @param page     分页对象
     * @return 搜索的结果Map,内部有ResultSet对象
     */
    Map<String, Object> search(Map<String, Object> paramMap, Map<String, String> sortMap, Page page);

    /**
     * 向索引中添加文档
     *
     * @param id 商品ID，用于从库中查出商品
     *           文章ID，用于从库中查出文章
     */
    void addDoc(Long id);

    /**
     * 按ID添加文档（异步线程池）
     *
     * @param id 商品ID
     */
    public void addDocAsyn(Long id);

    /**
     * 批量添加文档
     *
     * @param list
     */
    void addDocList(List<T> list);

    /**
     * 按ID删除文档
     * @param id 商品的ID
     */
    void deleteDoc(Long id);

    /**
     * 按ID添加文档（异步线程池）
     *
     * @param id 商品ID
     */
    public void deleteDocAsyn(Long id);

    /**
     * 删除全部文档
     */
    void deleteDocAll();

    /**
     * 根据店铺id删除商品文档
     *
     * @param storeId
     */
    public void deleteDocByStoreId(Long storeId);

    /**
     * 销毁Client
     */
    void destroy();

}
