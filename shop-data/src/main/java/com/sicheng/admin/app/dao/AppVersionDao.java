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
package com.sicheng.admin.app.dao;

import com.sicheng.admin.app.entity.AppVersion;
import com.sicheng.common.persistence.CrudDao;
import com.sicheng.common.persistence.annotation.MyBatisDao;

/**
 * app版本观念里DAO接口
 *
 * @author 贺东泽
 * @version 2019-10-29
 */
@MyBatisDao
public interface AppVersionDao extends CrudDao<AppVersion> {

    //请在这里增加你自己的DAO层方法

    //14条单表操作的通用SQL调用方法都在父类中，全继承下来了，可直接使用。

    /**
     * 将指定类型的最新版本设置为非最新版本
     *
     * @param type
     * @return
     */
    Integer setNewVersion(String type);
}