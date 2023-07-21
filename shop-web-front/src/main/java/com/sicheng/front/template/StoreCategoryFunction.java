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
package com.sicheng.front.template;

import com.sicheng.admin.store.entity.StoreCategory;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.store.service.StoreCategoryService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.List;
import java.util.Map;

/**
 * <p>标题: 获取店内分类(单个)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年5月18日 下午4:24:55
 */
public class StoreCategoryFunction implements Function {

    private static final String SID = "sid";//店铺id
    private static final String CID = "cid";//店铺分类id

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long storeId = TagUtils.getLong(tagParamMap, SID);
        Long storeCategoryId = TagUtils.getLong(tagParamMap, CID);
        if (storeId == null) {
            return null;
        }
        //执行业务，查询出单个店铺商品分类
        StoreCategoryService storeCategoryService = SpringContextHolder.getBean(StoreCategoryService.class);
        StoreCategory storeCategory = new StoreCategory();
        storeCategory.setStoreId(storeId);
        storeCategory.setStoreCategoryId(storeCategoryId);
        List<StoreCategory> storeCategorieList = storeCategoryService.selectByWhere(new Wrapper(storeCategory));
        if (storeCategorieList.isEmpty()) {
            return null;
        } else {
            return storeCategorieList.get(0);
        }
    }

}
