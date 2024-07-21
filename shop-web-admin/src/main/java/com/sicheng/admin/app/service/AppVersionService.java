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
package com.sicheng.admin.app.service;

import com.sicheng.admin.app.dao.AppVersionDao;
import com.sicheng.admin.app.entity.AppVersion;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * app版本管理 Service
 *
 * @author hdz
 * @version 2019-10-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AppVersionService extends CrudService<AppVersionDao, AppVersion> {
    @Autowired
    private AppVersionDao appVersionDao;

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    /**
     * 版本检查，检查该版本是否存在，存在返回true
     *
     * @param appVersion
     * @return
     */
    public Boolean checkVersion(AppVersion appVersion) {
        if (appVersion.getId() == null) {
            return false;
        }
        Wrapper wrapper = new Wrapper();
        wrapper.and("version = ", appVersion.getVersion());
        AppVersion appVersion2 = this.selectOne(wrapper);
        /**
         * 放过条件：
         * 该版本号在库里不存在
         * id相同，说明是操作的同一个版本
         * 安装包类型不同，支持多种安装包类型是同一个版本
         */
        return appVersion2 != null && !appVersion.getId().equals(appVersion2.getId()) && appVersion.getType().equals(appVersion2.getType());
    }

    /**
     * 添加新版本
     *
     * @param appVersion
     */
    @Transactional(rollbackFor = Exception.class)
    public void addVersion(AppVersion appVersion) {
        if (appVersion.getIsNewVersion().equals("1")) {
            appVersionDao.setNewVersion(appVersion.getType());
        }
        this.insertSelective(appVersion);
    }

    /**
     * 修改版本
     *
     * @param appVersion
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateVersion(AppVersion appVersion) {
        if (appVersion.getIsNewVersion().equals("1")) {
            appVersionDao.setNewVersion(appVersion.getType());
        }
        if (appVersion.getDownloadPath().equals("")) {
            appVersion.setDownloadPath(null);
        }
        this.updateByIdSelective(appVersion);
    }


    /**
     * 检查当前版本是否为最新版本，如果不为最新版本则按照指定的type返回最新版本的安装包
     *
     * @param appVersion
     * @param type
     * @return
     */
    public AppVersion checkVersion(String appVersion, String type) {
        Wrapper wrapper = new Wrapper();
        wrapper.and("version = ", appVersion.trim());
        AppVersion appVersion2 = this.selectOne(wrapper);
        //如果未查询出或不为最新版本则查询
        if (appVersion2 == null || appVersion2.getIsNewVersion().equals("0")) {
            if (!"1".equals(type) && !"2".equals(type)) {
                type = "1";
            }
            wrapper = new Wrapper();
            wrapper.and("is_new_version = ", "1");
            wrapper.and("type = ", type.trim());
            AppVersion newAppVersion = this.selectOne(wrapper);
            return newAppVersion;
        }
        return null;
    }
}