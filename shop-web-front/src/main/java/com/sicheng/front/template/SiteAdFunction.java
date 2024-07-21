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

import com.sicheng.admin.site.entity.SiteAd;
import com.sicheng.admin.site.entity.SiteAdContent;
import com.sicheng.common.beetl.StringResourceLoader;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.site.service.SiteAdContentService;
import com.sicheng.seller.site.service.SiteAdService;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.Resource;
import org.beetl.core.Template;
import org.beetl.core.io.ByteWriter_Char;
import org.beetl.core.io.NoLockStringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: 获取站点广告位(单个)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月18日 下午2:40:03
 */
public class SiteAdFunction implements Function {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String NUMBER = "number";//编号
    private static final String RENDER = "render";//广告内容中可能包含beetl模板的代码，是否执行

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Boolean render = TagUtils.getBoolean(tagParamMap, RENDER, true);
        String adNumber = TagUtils.getString(tagParamMap, NUMBER);
        if (StringUtils.isBlank(adNumber)) {
            return null;
        }
        //执行业务，查询出广告位(单个)
        SiteAdService siteAdService = SpringContextHolder.getBean(SiteAdService.class);
        SiteAd s = new SiteAd();
        s.setAdNumber(adNumber);
        s.setIsOpen("1");//是否开启(0否、1是)
        List<SiteAd> siteAdList = siteAdService.selectByWhere(new Wrapper(s));
        if (siteAdList.isEmpty()) {
            return null;
        } else {
            SiteAd siteAd = siteAdList.get(0);
            Long adId = siteAd.getAdId();

            //获取广告位内容
            SiteAdContentService siteAdContentService = SpringContextHolder.getBean(SiteAdContentService.class);
            SiteAdContent siteAdContent = new SiteAdContent();
            siteAdContent.setAdId(adId);
            siteAdContent.setStatus("1");//0删除、1有效（同ad_id下最多只有一个有效时间最近的为有效）
            List<SiteAdContent> siteAdContentList = siteAdContentService.selectByWhere(new Wrapper(siteAdContent));
            if (siteAdContentList.isEmpty()) {
                return null;
            } else {
                SiteAdContent adContent = siteAdContentList.get(0);
                String code = adContent.getContent();
                if (StringUtils.isBlank(code)) {
                    return null;
                }

                if (render) {
                    //广告内容中可能包含beetl模板的代码，解析执行这些代码

                    // 从字符串加载模板文件
                    StringResourceLoader loder = new StringResourceLoader(code);
                    Resource resource = loder.getResource(null);
                    Template t = ctx.gt.getTemplate(resource.getId(), ctx.getResourceId(), loder);

                    // 快速复制父模板的变量
                    t.binding(ctx.globalVar);
                    if (ctx.objectKeys != null && ctx.objectKeys.size() != 0) {
                        t.dynamic(ctx.objectKeys);
                    }

                    NoLockStringWriter w = new NoLockStringWriter();
                    ByteWriter_Char bw = new ByteWriter_Char(w, "utf-8", ctx);
                    //ByteWriter bw = ctx.byteWriter;
                    try {
                        t.renderTo(bw);
                    } catch (Exception e) {
                        String msg = "广告位" + adNumber + "内容错误," + e.toString();
                        logger.error(msg, e);
                        try {
                            bw.writeString(msg);
                        } catch (IOException e1) {
                        }
                    }
                    return bw.getTempConent().getBody();
                } else {
                    //广告内容中可能包含beetl模板的代码，不执行
                    NoLockStringWriter w = new NoLockStringWriter();
                    ByteWriter_Char bw = new ByteWriter_Char(w, "utf-8", ctx);
                    //ByteWriter bw = ctx.byteWriter;
                    try {
                        bw.writeString(code);
                    } catch (IOException e) {
                        logger.error("site.ad()模板发生异常", e);
                    }
                    return bw.getTempConent().getBody();
                }
            }
        }
    }
}