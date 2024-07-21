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
package com.sicheng.wap.web.api;

import com.sicheng.admin.app.entity.AppAd;
import com.sicheng.admin.app.entity.AppVersion;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.wap.service.AppAdService;
import com.sicheng.wap.service.AppVersionService;
import com.sicheng.common.utils4m.AppDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app接口 Controller
 * 所属模块：app
 *
 * @author hdz
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class AppController extends BaseController {

    @Autowired
    private AppAdService appAdService;

    @Autowired
    private AppVersionService appVersionService;

    /**
     * 检查当前版本是否为最新版本，如果不为最新版本则按照指定的type返回最新版本的安装包
     * 目前接口版本是1.0.0
     *
     * @param version    接口版本号
     * @param appVersion app版本号
     * @param systemType 系统类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/app/version/check", produces = {"application/json; charset=UTF-8"})
    public Object checkVersion(@PathVariable String version, String appVersion, String systemType) {
        Map<String, Object> data = new HashMap<>();
        try {
            AppVersion newVersion = appVersionService.checkVersion(appVersion, systemType);
            if (newVersion == null) {
                data.put("isUpdate", false);
            } else {
                data.put("isUpdate", true);
                newVersion.setIsNewVersion(null);
                newVersion.setCreateDate(null);
                newVersion.setUpdateDate(null);
                data.put("newVersion", newVersion);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("App版本检查成功"), data, null);
        } catch (Exception e) {
            logger.error("App版本检查异常，异常内容：", e);
            data.put("isUpdate", false);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("App版本检查失败"), data, null);
        }
    }

    /**
     * 获取App引导页(app初次打开时展示的3个页面)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/app/ad/list", produces = {"application/json; charset=UTF-8"})
    public Object adList() {
        try {
            Wrapper wrapper = new Wrapper();
            wrapper.and("is_show = ", "1");
            wrapper.orderBy("id asc");
            List<AppAd> appAds = appAdService.selectByWhere(wrapper);
            for (AppAd appAd : appAds) {
                appAd.setIsShow(null);
                appAd.setCreateDate(null);
                appAd.setUpdateBy(null);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取App欢迎页成功"), appAds, null);
        } catch (Exception e) {
            logger.error("获取App欢迎页失败，异常内容：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取App欢迎页失败"), null, null);
        }
    }

}