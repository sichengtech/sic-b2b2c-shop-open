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
package com.sicheng.admin.sys.service;

import com.sicheng.admin.sys.dao.SysVariableDao;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统变量管理 Service
 *
 * @author zjl
 * @version 2017-06-21
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SysVariableService extends CrudService<SysVariableDao, SysVariable> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    /**
     * 插入,只把非空的值插入到对应的字段
     * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
     *
     * @param entity
     * @return 受影响的行数
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(SysVariable entity) {
        entity.preInsert(UserUtils.getUser());
        return dao.insertSelective(entity);
    }

    /**
     * 修改数据(主键)
     * 根据主键更新记录,只把非空的值更到对应的字段
     *
     * @param entity
     * @return 受影响的行数
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateByIdSelective(SysVariable entity) {
        entity.preInsert(UserUtils.getUser());
        return dao.updateByIdSelective(entity);
    }

    /**
     * 获取系统变量
     *
     * @param name 变量名
     * @return SysVariable
     */
    public SysVariable getSysVariable(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        SysVariable variable = new SysVariable();
        variable.setName(name);
        return super.selectOne(new Wrapper(variable));
    }
}