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

import com.sicheng.admin.site.entity.SiteRecommend;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.site.service.SiteRecommendService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>标题: 自定义函数，siteRecommendFunction函数</p>
 * <p>描述: 根据推荐位编号获取推荐位</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fxx
 * @version 2017年9月27日 下午5:57:50
 */
public class SiteRecommendFunction implements Function, Serializable {

    public static final String NUMBER = "number";

    public SiteRecommend call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        String number = TagUtils.getString(tagParamMap, NUMBER);
        if (StringUtils.isBlank(number)) {
            return null;
        }
        SiteRecommendService service = SpringContextHolder.getBean(SiteRecommendService.class);
        //执行业务，查询出推荐位
        SiteRecommend siteRecommend = service.selectOne(new Wrapper().and("recommend_number=", number).and("is_open=", 1));
        return siteRecommend;
    }

}