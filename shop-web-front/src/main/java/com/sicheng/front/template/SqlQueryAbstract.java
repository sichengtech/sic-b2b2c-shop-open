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

import com.sicheng.front.template.util.TagUtils;
import org.beetl.core.BodyContent;
import org.beetl.core.Tag;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 标题: 自定义标签的基类，SqlQueryAbstract
 * </p>
 * <p>
 * 描述: 执行select语句来查询出数据表，支持分页，查询出来的结果集被包含在page对象中，返回page对象
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author zhaolei
 * @version 2017年5月14日 下午8:52:10
 */
public abstract class SqlQueryAbstract extends Tag {
    /**
     * @Fields SQL_PARAM_KEY : 在全局变量表中，使用SQL_PARAM_KEY来存储，当前sql语句的?占位符的值
     */
    public static final String SQL_PARAM_KEY = "sql_param_498dkeif949t040d";
    public static final String OUT_VAR_KEY = "outVar";
    public static final String DEBUG_KEY = "debug";

    @Override
    public void render() {
        try {
            // 放入变量
            // 存放占位符值（where条件的值）
            Map<String, Object> sqlParam = new LinkedHashMap<String, Object>();
            ctx.set(SQL_PARAM_KEY, sqlParam);

            // 处理标签的入参数，json参数
            Map<String, Object> tagParam = TagUtils.getTagParamMap(args);

            //动态生成SQL
            BodyContent s = this.getBodyContent();
            //System.out.println("执行sql:" + s.getBody());
            //System.out.println("##参数" + param);

            //执行SQL
            String sql = s.getBody();
            Object rs = executeSql(sql, sqlParam, tagParam);

            if (tagParam != null && tagParam.get(OUT_VAR_KEY) != null) {
                Object obj = tagParam.get(OUT_VAR_KEY);
                String outVarValue = obj.toString();
                ctx.template.binding(outVarValue, rs);// 把查询结果放入全局变量
            }
            if (tagParam != null && tagParam.get(DEBUG_KEY) != null) {
                Object obj = tagParam.get(DEBUG_KEY);
                String debugValue = obj.toString();
                if ("true".equalsIgnoreCase(debugValue)) {
                    String sqlStr = s.getBody();
                    sqlStr = sqlStr.replaceAll("\n", " ");
                    sqlStr = sqlStr.replaceAll("\r", " ");
                    System.out.println("SqlQueryPageTag标签debug--执行sql:" + sqlStr);
                    System.out.println("SqlQueryPageTag标签debug--参数:" + sqlParam);
                }
            }
        } finally {
            // 清理变量
            if (ctx.globalVar != null) {
                ctx.globalVar.remove(SQL_PARAM_KEY);
            }
        }
    }

    /**
     * 描述: 执行SQL
     *
     * @param sql      SQL
     * @param sqlParam sql的参数
     * @param tagParam 标签的参数
     * @return
     */
    public abstract Object executeSql(String sql, Map<String, Object> sqlParam, Map<String, Object> tagParam);

}