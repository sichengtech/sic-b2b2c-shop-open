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

import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.seller.site.service.SiteInfoService;
import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * <p>标题: 获取站点信息</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年5月18日 下午12:25:38
 */
public class SiteInfoFunction implements Function {

    public Object call(Object[] args, Context ctx) {
        //执行业务，查询出站点设置
        SiteInfoService siteInfoService = SpringContextHolder.getBean(SiteInfoService.class);
        SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper());
        return siteInfo;
    }

}
