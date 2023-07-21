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
package com.sicheng.admin.app.web;

import com.sicheng.admin.app.entity.AppVersion;
import com.sicheng.admin.app.service.AppVersionService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * app版本管理 Controller
 * 所属模块：app
 *
 * @author 贺东泽
 * @version 2019-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/app/appVersion")
public class AppVersionController extends BaseController {

    @Autowired
    private AppVersionService appVersionService;



    private static final String APK = "apk";
    private static final String WGT = "wgt";

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {

        String menu3 = "070301";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param appVersion 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("app:appVersion:view")
    @RequestMapping(value = "list")
    public String list(AppVersion appVersion, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AppVersion> page = appVersionService.selectByWhere(new Page<AppVersion>(request, response), new Wrapper(appVersion));
        model.addAttribute("page", page);
        return "admin/app/appVersionList";
    }

    /**
     * 进入保存页面
     *
     * @param appVersion 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("app:appVersion:save")
    @RequestMapping(value = "save1")
    public String save1(AppVersion appVersion, Model model) {
        if (appVersion == null) {
            appVersion = new AppVersion();
        }
        model.addAttribute("appVersion", appVersion);
        return "admin/app/appVersionForm";
    }

    /**
     * 执行保存
     *
     * @param appVersion         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("app:appVersion:save")
    @RequestMapping(value = "save2")
    public String save2(AppVersion appVersion, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(appVersion, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(appVersion, model);//回显错误提示
        }

        //安装包类型检查
        String type = appVersion.getDownloadPath().split("\\.")[1];
        if (WGT.equals(type)) {
            appVersion.setType("2");
        } else if (APK.equals(type)) {
            appVersion.setType("1");
        }

        //版本检查
        appVersion.setVersion(appVersion.getVersion().trim());
        if (appVersionService.checkVersion(appVersion)) {
            addMessage(model, FYUtils.fyParams("该版本号已存在"));
            return save1(appVersion, model);//回显错误提示
        }

        //业务处理
        appVersionService.addVersion(appVersion);
        addMessage(redirectAttributes, FYUtils.fyParams("保存app版本成功"));
        return "redirect:" + adminPath + "/app/appVersion/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param appVersion 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("app:appVersion:edit")
    @RequestMapping(value = "edit1")
    public String edit1(AppVersion appVersion, Model model) {
        AppVersion entity = null;
        if (appVersion != null) {
            if (appVersion.getId() != null) {
                entity = appVersionService.selectById(appVersion.getId());
            }
        }
        model.addAttribute("appVersion", entity);
        return "admin/app/appVersionForm";
    }

    /**
     * 执行编辑
     *
     * @param appVersion         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("app:appVersion:edit")
    @RequestMapping(value = "edit2")
    public String edit2(AppVersion appVersion, Model model, RedirectAttributes redirectAttributes) {
        //走这个if说明未更改安装包
        if (StringUtils.isBlank(appVersion.getDownloadPath())) {
            AppVersion buffer = appVersionService.selectById(appVersion.getId());
            appVersion.setDownloadPath(buffer.getDownloadPath());
        }
        //表单验证
        List<String> errorList = validate(appVersion, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(appVersion, model);//回显错误提示
        }

        //安装包类型检查
        String type = appVersion.getDownloadPath().split("\\.")[1];
        if (WGT.equals(type)) {
            appVersion.setType("2");
        } else if (APK.equals(type)) {
            appVersion.setType("1");
        }

        //版本检查
        appVersion.setVersion(appVersion.getVersion().trim());
        if (appVersionService.checkVersion(appVersion)) {
            addMessage(model, FYUtils.fyParams("该版本号已存在"));
            return save1(appVersion, model);//回显错误提示
        }

        //业务处理
        appVersionService.updateVersion(appVersion);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑app版本成功"));
        return "redirect:" + adminPath + "/app/appVersion/list.do?repage";
    }

    /**
     * 删除
     *
     * @param appVersion         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("app:appVersion:drop")
    @RequestMapping(value = "delete")
    public String delete(AppVersion appVersion, RedirectAttributes redirectAttributes) {
        appVersionService.deleteById(appVersion.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除app版本成功"));
        return "redirect:" + adminPath + "/app/appVersion/list.do?repage";
    }

//	@ResponseBody
//	@RequestMapping(value = "test")
//	public Object checkVersion(String appVersion, String type) {
//		try {
//			AppVersion appVersion1 = appVersionService.checkVersion(appVersion, type);
//			return appVersion1;
//		} catch (Exception e) {
//			logger.info("App版本检查异常，异常内容：", e);
//			return e;
//		}
//	}

    /**
     * 表单验证
     *
     * @param appVersion 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(AppVersion appVersion, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("version"))) {
            errorList.add(FYUtils.fyParams("版本号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("version")) && R.get("version").length() > 32) {
            errorList.add(FYUtils.fyParams("版本号最大长度不能超过32字符"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add(FYUtils.fyParams("安装包类型：1为apk，2为wgt最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("isNewVersion")) && R.get("isNewVersion").length() > 1) {
            errorList.add(FYUtils.fyParams("是否为最新版本：0为否，1为是最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("downloadPath")) && StringUtils.isBlank(appVersion.getDownloadPath())) {
            errorList.add(FYUtils.fyParams("请上传安装包"));
        }
        if (StringUtils.isNotBlank(R.get("downloadPath")) && R.get("downloadPath").length() > 255) {
            errorList.add(FYUtils.fyParams("下载路径最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("explain")) && R.get("explain").length() > 255) {
            errorList.add(FYUtils.fyParams("版本说明最大长度不能超过255字符"));
        }
        return errorList;
    }

}