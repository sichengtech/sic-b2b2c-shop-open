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
package com.sicheng.front.service;

import com.sicheng.admin.template.dao.TemplateSqlDao;
import com.sicheng.common.persistence.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 模板自定义sql标签的Service
 *
 * @author zhaolei
 * @version 2017-05-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TemplateSqlService {

    @Autowired
    TemplateSqlDao dao;

    /**
     * 按sql查询
     *
     * @param sql 被执行的select语句
     * @param param 被执行的select语句的参数
     * @return
     */
    public List<Map<String, Object>> selectListBysql(String sql, Map<String, Object> param) {
        List<Map<String, Object>> list = dao.selectBysql(null, sql, param);
        return list;
    }

    /**
     * 按sql查询,并分页
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     *
     * @param page  分页，null表示不分页
     * @param sql   被执行的select语句
     * @param param 被执行的select语句的参数
     * @return
     */
    public Page<Map<String, Object>> selectPageBysql(Page<Map<String, Object>> page, String sql, Map<String, Object> param) {
        List<Map<String, Object>> list = dao.selectBysql(page, sql, param);
        if (page == null) {
            page = new Page();
        }
        page.setList(list);
        return page;
    }

}
