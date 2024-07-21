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

import com.sicheng.admin.store.entity.Store;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.store.service.StoreService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

/**
 * <p>标题: 店铺(单个)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月17日 下午5:23:15
 */
public class StoreOneFunction implements Function {

    private static final String SID = "sid";//店铺id

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long storeId = TagUtils.getLong(tagParamMap, SID);
        if (storeId == null) {
            return null;
        }
        //执行业务，查询出店铺(单个)
        StoreService storeService = SpringContextHolder.getBean(StoreService.class);
        Store store = storeService.selectById(storeId);
        return store;
    }

}
