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
package com.sicheng.admin.template.dao;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 模板自定义sql标签的DAO接口
 *
 * @author zhaolei
 * @version 2017-05-09
 */
@MyBatisDao
public interface TemplateSqlDao {

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     *
     * @param page    分页，null表示不分页
     * @param sql    sql
     * @param param   被执行的select语句的参数
     * @return 结果集
     */
    List<Map<String, Object>> selectBysql(@Param(value = "p") Page<Map<String, Object>> page,
                                          @Param(value = "sql") String sql, @Param(value = "param") Map<String, Object> param);

}