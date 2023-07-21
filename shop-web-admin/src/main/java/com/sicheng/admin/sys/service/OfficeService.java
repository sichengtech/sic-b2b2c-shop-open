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
package com.sicheng.admin.sys.service;

import com.sicheng.admin.sys.dao.OfficeDao;
import com.sicheng.admin.sys.entity.Office;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机构Service
 *
 * @author zhaolei
 * @version 2014-05-16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class OfficeService extends TreeService<OfficeDao, Office> {

    //请在这里编写业务逻辑

    //TreeService父类中N个树结构的常用方法，已全部继承下来，可直接使用。

    //父父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private OfficeDao OfficeDao;

    /**
     * 查询所有部门
     *
     * @return
     */
    public List<Office> findAll() {
        return UserUtils.getOfficeList();
    }

    /**
     * 查询部门
     *
     * @return
     */
    public List<Office> findList(Boolean isAll) {
        if (isAll != null && isAll) {
            return UserUtils.getOfficeAllList();
        } else {
            return UserUtils.getOfficeList();
        }
    }

    /**
     * 删除部门
     * 级联删除，删除父节点的同时，删除所有子节点
     *
     * @param office
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Office office) {
        //级联删除，删除父节点的同时，删除所有子节点
        OfficeDao.delete(office);
    }

}
