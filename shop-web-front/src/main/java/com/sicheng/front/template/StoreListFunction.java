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

import com.sicheng.admin.store.entity.SolrStore;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.service.SolrStoreService;
import com.sicheng.front.template.util.TagUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 店铺列表
 */
public class StoreListFunction implements Function {
    //排序方式，可选值：null、allSales、collectionCount
    private static final String SORT = "sort";
    //排序规则，可选值ASC（升序）、DESC（降序）
    private static final String SORTMODE = "sortMode";
    //关键字
    private static final String K = "k";
    //查询数量
    private static final String PAGESIZE = "pageSize";
    private static final String PAGENO = "pageNo";
    private static final String DESC = "DESC";
    private static final String ASC = "ASC";

    @Override
    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Integer pageSize = TagUtils.getInteger(tagParamMap, PAGESIZE);
        Integer pageNo = TagUtils.getInteger(tagParamMap, PAGENO);
        String sort = TagUtils.getString(tagParamMap, SORT);
        String sortMode = TagUtils.getString(tagParamMap, SORTMODE);
        String k = TagUtils.getString(tagParamMap, K);
        //表单验证
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        if (StringUtils.isBlank(sortMode) || (!DESC.equals(sortMode.toUpperCase()) && !ASC.equals(sortMode.toUpperCase()))) {
            sortMode = DESC;
        }
        sortMode = sortMode.toUpperCase();
        //查询
        SolrStoreService solrStoreService = SpringContextHolder.getBean(SolrStoreService.class);
        Wrapper wrapper = new Wrapper();

        wrapper.and("is_open = ", 1);
        wrapper.and("product_count > ", 0);
        if (StringUtils.isNotBlank(k)) {
            wrapper.and("name like ", "%" + k + "%");
        }
        if (StringUtils.isNotBlank(sort) && !"null".equals(sort)) {
            switch (sort) {
                case "allSales":
                    wrapper.orderBy("all_sales " + sortMode);
                    break;
                case "collectionCount":
                    wrapper.orderBy("count_collection " + sortMode);
                    break;
            }
        }
        // 分页需要request
        HttpServletRequest request = R.getRequest();
        // 分页有可能需要response，就是用于写cookie
        HttpServletResponse response = R.getResponse();
        Page<SolrStore> page = new Page<SolrStore>(request, response);
        page.setPageSize(pageSize);
        page.setPageNo(pageNo);
        page = solrStoreService.selectByWhere(page, wrapper);
        return page;
    }
}
