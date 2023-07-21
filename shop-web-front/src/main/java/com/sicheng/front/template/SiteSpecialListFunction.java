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

import com.google.common.collect.Lists;
import com.sicheng.admin.site.entity.SiteSpecialEdition;
import com.sicheng.admin.site.entity.SiteSpecialEditionDetail;
import com.sicheng.common.beetl.StringResourceLoader;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.service.SiteSpecialEditionDetailService;
import com.sicheng.front.service.SiteSpecialEditionService;
import com.sicheng.front.template.util.TagUtils;
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
 *  <p>标题: SiteSpecialListFunction</p>
 *  <p>描述: 获取特版详情(列表)</p>
 *  <p>公司: 思程科技 www.sicheng.net</p>
 *  @author zhangjiali
 *  @date 2018年6月2日 下午2:40:59
 * 
 */
public class SiteSpecialListFunction implements Function {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String NUMBER = "number";//编号
    private static final String RENDER = "render";//广告内容中可能包含beetl模板的代码，是否执行

    @Override
    public Object call(Object[] args, Context ctx) {
        List<Object> listContent = Lists.newArrayList();
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Boolean render = TagUtils.getBoolean(tagParamMap, RENDER, true);
        String number = TagUtils.getString(tagParamMap, NUMBER);
        if (StringUtils.isBlank(number)) {
            return listContent;
        }
        SiteSpecialEditionService siteSpecialEditionService = SpringContextHolder.getBean(SiteSpecialEditionService.class);
        SiteSpecialEditionDetailService siteSpecialEditionDetailService = SpringContextHolder.getBean(SiteSpecialEditionDetailService.class);
        //查询特版信息
        SiteSpecialEdition siteSpecialEdition = siteSpecialEditionService.selectOne(new Wrapper().and("a.number=", number));
        if (siteSpecialEdition == null) {
            return listContent;
        }
        //查询特版明细信息
        SiteSpecialEditionDetail siteSpecialEditionDetail = new SiteSpecialEditionDetail();
        siteSpecialEditionDetail.setSeId(siteSpecialEdition.getSeId());
        List<SiteSpecialEditionDetail> list = siteSpecialEditionDetailService.selectByWhere(new Wrapper(siteSpecialEditionDetail).orderBy("a.sort ASC,a.update_date DESC"));
        if (list.isEmpty()) {
            return listContent;
        }
        for (SiteSpecialEditionDetail specialEditionDetail : list) {
            String code = specialEditionDetail.getContent();
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
                try {
                    t.renderTo(bw);
                } catch (Exception e) {
                    String msg = FYUtils.fyParams("特版位") + number + FYUtils.fyParams("内容错误") + e.toString();
                    logger.error(msg, e);
                    try {
                        bw.writeString(msg);
                    } catch (IOException e1) {
                    }
                }
                listContent.add(bw.getTempConent().getBody());
            } else {
                //广告内容中可能包含beetl模板的代码，不执行
                NoLockStringWriter w = new NoLockStringWriter();
                ByteWriter_Char bw = new ByteWriter_Char(w, "utf-8", ctx);
                //ByteWriter bw = ctx.byteWriter;
                try {
                    bw.writeString(code);
                } catch (IOException e) {
                    logger.error("site.specialList()标签发生异常", e);
                }
                listContent.add(bw.getTempConent().getBody());
            }
        }
        return listContent;
    }
}