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
package com.sicheng.wap.service;

import com.sicheng.admin.app.dao.AppVersionDao;
import com.sicheng.admin.app.entity.AppVersion;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * app版本管理 Service
 *
 * @author 贺东泽
 * @version 2019-10-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AppVersionService extends CrudService<AppVersionDao, AppVersion> {
    private static final String IOS = "ios";
    private static final String ANDROID = "android";

    /**
     * 检查当前版本是否为最新版本，是返回null，不是则返回安装包
     *
     * @param appVersion
     * @param systemType
     * @return
     */
    public AppVersion checkVersion(String appVersion, String systemType) {
        //统一格式化小写去空格
        appVersion = appVersion.trim();
        systemType = systemType.trim().toLowerCase();
        //查询出wgt和apk最新的版本
        Wrapper wrapper = new Wrapper();
        wrapper.and("is_new_version = ", "1");
        List<AppVersion> appVersions = this.selectByWhere(wrapper);
        AppVersion wgt = null;
        AppVersion apk = null;
        for (AppVersion buffer : appVersions) {
            if (buffer.getType().equals("2")) {
                wgt = buffer;
            } else {
                apk = buffer;
            }
        }
        //版本号格式化
        Integer apkVersionFormat = 0;
        if (apk != null) {
            apkVersionFormat = this.versionFormat(apk.getVersion());
        }
        Integer wgtVersionFormat = 0;
        if (wgt != null) {
            wgtVersionFormat = this.versionFormat(wgt.getVersion());
        }
        Integer appVersionFormat = this.versionFormat(appVersion);
        //ios系统只要wgt版本，apk版本需要去苹果商店下载。
        if (IOS.equals(systemType)) {
            if (appVersionFormat < wgtVersionFormat) {
                return wgt;
            }
        }
        //android系统，如果wgt和apk版本相同则返回wgt版本，否则返回最大的版本。
        if (ANDROID.equals(systemType)) {
            if (wgtVersionFormat >= apkVersionFormat && wgtVersionFormat > appVersionFormat) {
                return wgt;
            }
            if (apkVersionFormat > wgtVersionFormat && apkVersionFormat > appVersionFormat) {
                return apk;
            }
        }
        //当前版本就是最新版或未识别的系统
        return null;
    }

    /**
     * 版本号格式化,将x.x.x格式的版本号以数字xxx格式返回
     *
     * @param version 终端当前版本
     * @return
     */
    private Integer versionFormat(String version) {
        Integer number = 0;
        String[] array = version.split("\\.");
        if (!array[0].equals("0")) {
            number = new Integer(array[0]) * 100;
        }
        if (!array[1].equals("0")) {
            number += new Integer(array[1]) * 10;
        }
        if (!array[2].equals("0")) {
            number += new Integer(array[2]);
        }
        return number;
    }
}