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

import com.sicheng.admin.store.entity.StoreNavigation;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.store.service.StoreNavigationService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>标题: 店铺导航（列表）</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年6月5日 上午11:49:16
 */
public class StoreNavListFunction implements Function {

    private static final String SID = "sid";//店铺id

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long storeId = TagUtils.getLong(tagParamMap, SID);
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        if (storeId == null) {
            return new ArrayList<StoreNavigation>();
        }
        //执行业务，查询出店铺导航
        StoreNavigationService storeNavigationServiceService = SpringContextHolder.getBean(StoreNavigationService.class);
        StoreNavigation storeNavigation = new StoreNavigation();
        storeNavigation.setStoreId(storeId);
        storeNavigation.setIsOpen("1");//是否开启(0是 1否)
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(storeNavigation);
        wrapper.orderBy("a.sort ASC");
        Page<StoreNavigation> storeNavigationPage = storeNavigationServiceService.selectByWhere(new Page<StoreNavigation>(1, limit, limit), wrapper);
        return storeNavigationPage.getList();
    }

}
