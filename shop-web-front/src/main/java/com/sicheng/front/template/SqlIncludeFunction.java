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

import com.alibaba.druid.util.StringUtils;
import org.beetl.core.*;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>标题: 自定义函数 SqlInclude函数</p>
 * <p>描述: 用于加载一个sql文件，或加载sql文件的一部分，支持局部渲染技术</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年5月15日 下午3:07:48
 */
public class SqlIncludeFunction implements Function {
    public Object call(Object[] paras, Context ctx) {
        // sql模板文件的路径
        String sqlTpl = (String) paras[0];
        if (StringUtils.isEmpty(sqlTpl)) {
            throw new RuntimeException("SqlInclude函数缺少参数，模板路径不能为空。");
        }
        sqlTpl = sqlTpl.trim();

        // 加载sql模板文件
        Resource sibling = ctx.getResource();
        String resourceId = ctx.gt.getResourceLoader().getResourceId(sibling, sqlTpl);
        Template t = null;
        if (sqlTpl.contains("#")) {
            //支持局部渲染
            String path = sqlTpl.substring(0, sqlTpl.lastIndexOf("#"));
            String blockId = sqlTpl.substring(sqlTpl.lastIndexOf("#") + 1);
            t = ctx.gt.getAjaxTemplate(path, blockId);
        } else {
            //未支持局部渲染
            t = ctx.gt.getTemplate(resourceId, ctx.getResourceId());
        }

        // 快速复制父模板的变量
        t.binding(ctx.globalVar);
        if (ctx.objectKeys != null && ctx.objectKeys.size() != 0) {
            t.dynamic(ctx.objectKeys);
        }

        if (paras.length == 2) {
            Map<String, Object> map = (Map<String, Object>) paras[1];
            for (Entry<String, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Map || value instanceof Collection) {
                    t.binding(entry.getKey(), value, true);
                } else {
                    t.binding(entry.getKey(), value);
                }
            }
        }
        ByteWriter bw = ctx.byteWriter;
        t.renderTo(bw);
        return null;
    }
}