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
package com.sicheng.admin.cms.utils;

import com.sicheng.admin.cms.entity.Article;
import com.sicheng.admin.cms.entity.Category;
import com.sicheng.common.config.Global;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;

import javax.servlet.ServletContext;

/**
 * 内容管理工具类
 *
 * @author zhaolei
 * @version 2013-5-29
 */
public class CmsUtils2 {
    private static ServletContext context = SpringContextHolder.getBean(ServletContext.class);

    /**
     * 获得文章动态URL地址
     *
     * @param article
     * @return url
     */
    public static String getUrlDynamic(Article article) {
        if (StringUtils.isNotBlank(article.getLink())) {
            return article.getLink();
        }
        StringBuilder str = new StringBuilder();
        str.append(context.getContextPath()).append(Global.getFrontPath());
        str.append("/cms");
        str.append("/view-").append(article.getCategory().getId()).append("-").append(article.getId()).append(Global.getUrlSuffix());
        return str.toString();
    }

    /**
     * 获得栏目动态URL地址
     *
     * @param category
     * @return url
     */
    public static String getUrlDynamic(Category category) {
        if (StringUtils.isNotBlank(category.getHref())) {
            if (!category.getHref().contains("://")) {
                return context.getContextPath() + Global.getFrontPath() + category.getHref();
            } else {
                return category.getHref();
            }
        }
        StringBuilder str = new StringBuilder();
        str.append(context.getContextPath()).append(Global.getFrontPath());
        str.append("/cms");
        str.append("/list-").append(category.getId()).append(Global.getUrlSuffix());
        return str.toString();
    }

    /**
     * 从图片地址中去除ContextPath地址
     *
     * @param src
     * @return src
     */
    public static String formatImageSrcToDb(String src) {
        if (StringUtils.isBlank(src)) return src;
        if (src.startsWith(context.getContextPath() + "/userfiles")) {
            return src.substring(context.getContextPath().length());
        } else {
            return src;
        }
    }

    /**
     * 从图片地址中加入ContextPath地址
     *
     * @param src
     * @return src
     */
    public static String formatImageSrcToWeb(String src) {
        if (StringUtils.isBlank(src)) return src;
        if (src.startsWith(context.getContextPath() + "/userfiles")) {
            return src;
        } else {
            return context.getContextPath() + src;
        }
    }
}