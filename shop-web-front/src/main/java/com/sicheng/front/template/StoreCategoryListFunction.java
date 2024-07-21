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

import com.sicheng.admin.store.entity.StoreCategory;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.store.service.StoreCategoryService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>标题: 获取店内分类(列表)不分页</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月18日 下午4:28:41
 */
public class StoreCategoryListFunction implements Function {

    private static final String SID = "sid";            //店铺id
    private static final String PARENT_ID = "parentId";    //分类id
    private static final String NAME = "name";            //分类名字

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long storeId = TagUtils.getLong(tagParamMap, SID);
        Long parentId = TagUtils.getLong(tagParamMap, PARENT_ID);
        String name = TagUtils.getString(tagParamMap, NAME);
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        if (storeId == null) {
            return new ArrayList<StoreCategory>();
        }
        //执行业务，查询出店铺商品分类列表
        StoreCategoryService storeCategoryService = SpringContextHolder.getBean(StoreCategoryService.class);
        StoreCategory storeCategory = new StoreCategory();
        storeCategory.setStoreId(storeId);
        StoreCategory psc = new StoreCategory();
        psc.setStoreCategoryId(parentId);
        storeCategory.setParent(psc);
        storeCategory.setName(name);
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(storeCategory);
        wrapper.orderBy("a.sort ASC");
        Page<StoreCategory> storeCategoryPage = storeCategoryService.selectByWhere(new Page<StoreCategory>(1, limit, limit), wrapper);
        return storeCategoryPage.getList();
    }

}
