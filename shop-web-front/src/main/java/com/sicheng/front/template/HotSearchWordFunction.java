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

import com.sicheng.admin.site.entity.SiteHotSearchWord;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.service.SiteHotSearchWordService;
import com.sicheng.front.template.util.TagUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

/**
 * 热搜词
 */
public class HotSearchWordFunction implements Function {
    //查询数量
    private static final String PAGESIZE = "pageSize";
    //搜索类型：0为商品，1为店铺
    private static final String TYPE = "type";

    @Override
    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Integer pageSize = TagUtils.getInteger(tagParamMap, PAGESIZE);
        String type = TagUtils.getString(tagParamMap, TYPE);
        //表单验证
        if (pageSize == null || pageSize <= 0) {
            pageSize = 12;
        }
        if (!"0".equals(type) && !"1".equals(type)) {
            type = "0";
        }
        //查询
        SiteHotSearchWordService siteHotSearchWordService = SpringContextHolder.getBean(SiteHotSearchWordService.class);
        Wrapper wrapper = new Wrapper();
        wrapper.and("type = ", type);
        wrapper.and("is_show = ", 1);
        wrapper.orderBy("`sort` asc");
        Page<SiteHotSearchWord> page = new Page<>();
        page.setPageSize(pageSize);
        page = siteHotSearchWordService.selectByWhere(page, wrapper);
        return page.getList();
    }
}
