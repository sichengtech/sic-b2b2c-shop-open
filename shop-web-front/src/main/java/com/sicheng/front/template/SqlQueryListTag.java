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

import com.sicheng.common.persistence.Page;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.service.TemplateSqlService;
import com.sicheng.front.template.util.TagUtils;

import java.util.Map;

/**
 * <p>
 * 标题: 自定义标签，SqlQueryList标签
 * </p>
 * <p>
 * 描述: 执行select语句来查询出数据表，不支持分页，
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author zhaolei
 * @version 2017年5月14日 下午8:52:10
 */
public class SqlQueryListTag extends SqlQueryAbstract {
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

        //查出几条记录
        Integer limit = TagUtils.getInteger(tagParam, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        //count>0 表示已通过其它途径获得了conut总条数，不再需要再查询总条数，节省性能。
        Page<Map<String, Object>> page = service.selectPageBysql(new Page<Map<String, Object>>(1, limit, limit), sql, sqlParam);
        return page.getList();
    }

}