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

import com.sicheng.common.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaolei
 * Date: 13-8-22
 * Time: 上午10:23
 */
public class TplUtils {
    /**
     * 去除模板前缀
     *
     * @param list     模板列表
     * @param prefix   模板前缀  例如“frontViewArticle”
     * @param include  需包含的模板  例如“/views/admin/cms/front/themes/fdp/articleSelectList.jsp”
     * @param excludes 需去除的模板  例如“frontViewArticle”
     * @return
     */
    public static List<String> tplTrim(List<String> list, String prefix, String include, String... excludes) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(include) && !list.contains(include)) {
            if (!tplContain(excludes, include)) {
                int start = include.lastIndexOf("/");
                int end = include.lastIndexOf(".");
                if (start == -1 || end == -1) {
                    throw new RuntimeException("include not contain '/' or '.':" + include);
                }
                result.add(include.substring(start + 1, end));
            }
        }
        for (String t : list) {
            if (!tplContain(excludes, t)) {
                int start = t.lastIndexOf("/");
                int end = t.lastIndexOf(".");
                if (start == -1 || end == -1) {
                    throw new RuntimeException("name not contain '/' or '.':" + t);
                }
                t = t.substring(start + 1, end);
                if (t.contains(prefix)) {
                    result.add(t);
                }
            }
        }
        return result;
    }

    /**
     * 检查tpl是否存在于excludes里面。
     *
     * @param excludes
     * @param tpl
     * @return
     */
    private static boolean tplContain(String[] excludes, String tpl) {
        int start = tpl.lastIndexOf("/");
        int end = tpl.lastIndexOf(".");
        if (start == -1 || end == -1) {
            throw new RuntimeException("tpl not contain '/' or '.':" + tpl);
        }
        String name = tpl.substring(start + 1, end);
        for (String e : excludes) {
            if (e.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
