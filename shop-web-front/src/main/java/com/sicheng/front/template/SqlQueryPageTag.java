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

import com.sicheng.common.persistence.Page;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.service.TemplateSqlService;
import com.sicheng.front.template.util.TagUtils;

import java.util.Map;

/**
 * <p>
 * 标题: 自定义标签，SqlQueryPage标签
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
public class SqlQueryPageTag extends SqlQueryAbstract {
    /**
     * 描述: 执行SQL
     *
     * @param sql      SQL
     * @param sqlParam sql的参数
     * @param tagParam 标签的参数
     * @return
     */
    public Object executeSql(String sql, Map<String, Object> sqlParam, Map<String, Object> tagParam) {
        TemplateSqlService service = SpringContextHolder.getBean(TemplateSqlService.class);

        Page page = TagUtils.getPage(tagParam);// 从入参中取得Page分页对象
        page = service.selectPageBysql(page, sql, sqlParam);
        return page;
    }

}