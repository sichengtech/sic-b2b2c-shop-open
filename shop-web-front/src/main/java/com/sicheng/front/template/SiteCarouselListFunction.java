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

import com.sicheng.admin.site.entity.SiteCarouselPicture;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.site.service.SiteCarouselPictureService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>标题: 获取站点轮播图(列表)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月18日 下午12:40:03
 */
public class SiteCarouselListFunction implements Function {

    private static final String TYPE = "type";//类型

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        String type = TagUtils.getString(tagParamMap, TYPE);
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        if (StringUtils.isBlank(type)) {
            return new ArrayList<SiteCarouselPicture>();
        }
        //执行业务，查询出店家轮播图
        SiteCarouselPictureService siteCarouselPictureService = SpringContextHolder.getBean(SiteCarouselPictureService.class);
        SiteCarouselPicture siteCarouselPicture = new SiteCarouselPicture();
        siteCarouselPicture.setType(type);//类型：1首页、2品牌街、3大宗采购-首页、4一级分类第一位置、5一级分类第二位置、6一级分类第三位置、7一级分类第四位置、8一级分类第五位置、9一级分类第六位置
        siteCarouselPicture.setStatus("1");//状态，0禁用 、1启用
        Page<SiteCarouselPicture> siteCarouselPicturePage = siteCarouselPictureService.selectByWhere(new Page<SiteCarouselPicture>(1, limit, limit), new Wrapper(siteCarouselPicture).orderBy("a.sort asc"));
        return siteCarouselPicturePage.getList();
    }

}
