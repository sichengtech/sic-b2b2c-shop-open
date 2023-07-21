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

import com.sicheng.admin.app.entity.AppAd;
import com.sicheng.admin.app.service.AppAdService;

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
 * App引导页 Controller
 * 所属模块：app
 *
 * @author 贺东泽
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/app/appAd")
public class AppAdController extends BaseController {

    @Autowired
    private AppAdService appAdService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070302";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param appAd    实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("app:appAd:view")
    @RequestMapping(value = "list")
    public String list(AppAd appAd, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AppAd> page = appAdService.selectByWhere(new Page<AppAd>(request, response), new Wrapper(appAd));
        model.addAttribute("page", page);
        return "admin/app/appAdList";
    }

    /**
     * 进入保存页面
     *
     * @param appAd 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("app:appAd:save")
    @RequestMapping(value = "save1")
    public String save1(AppAd appAd, Model model) {
//		if (appAd == null){
//			appAd = new AppAd();
//		}
//		model.addAttribute("appAd", appAd);
//		return "admin/app/appAdForm";

        //不允许新增
        return "redirect:" + adminPath + "/app/appAd/list.do?repage";
    }

    /**
     * 执行保存
     *
     * @param appAd              实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("app:appAd:save")
    @RequestMapping(value = "save2")
    public String save2(AppAd appAd, Model model, RedirectAttributes redirectAttributes) {
//		//表单验证
//		List<String> errorList=validate(appAd, model);
//		if(errorList.size()>0){
//			errorList.add(0, FYUtils.fyParams("数据验证失败："));
//			addMessage(model, errorList.toArray(new String[]{}));
//			return save1(appAd, model);//回显错误提示
//		}
//
//		//业务处理
//		appAdService.insertSelective(appAd);
//		addMessage(redirectAttributes, FYUtils.fyParams("保存App引导页成功"));
//		return "redirect:"+adminPath+"/app/appAd/list.do?repage";

        //不允许新增
        return "redirect:" + adminPath + "/app/appAd/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param appAd 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("app:appAd:edit")
    @RequestMapping(value = "edit1")
    public String edit1(AppAd appAd, Model model) {
        AppAd entity = null;
        if (appAd != null) {
            if (appAd.getId() != null) {
                entity = appAdService.selectById(appAd.getId());
            }
        }
        model.addAttribute("appAd", entity);
        return "admin/app/appAdForm";
    }

    /**
     * 执行编辑
     *
     * @param appAd              实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("app:appAd:edit")
    @RequestMapping(value = "edit2")
    public String edit2(AppAd appAd, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(appAd, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(appAd, model);//回显错误提示
        }
        //业务处理
        appAdService.updateByIdSelective(appAd);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑App引导页成功"));
        return "redirect:" + adminPath + "/app/appAd/list.do?repage";
    }

    /**
     * 删除
     *
     * @param appAd              实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("app:appAd:drop")
    @RequestMapping(value = "delete")
    public String delete(AppAd appAd, RedirectAttributes redirectAttributes) {
//        appAdService.deleteById(appAd.getId());
//        addMessage(redirectAttributes, "删除App引导页成功");
        //不允许删除
        return "redirect:" + adminPath + "/app/appAd/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param appAd 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(AppAd appAd, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isNotBlank(appAd.getBackgroundColor()) && appAd.getBackgroundColor().indexOf("#") != 0) {
            errorList.add(FYUtils.fyParams("背景颜色格式错误"));
        }
        if (StringUtils.isNotBlank(R.get("backgroundColor")) && R.get("backgroundColor").length() > 32) {
            errorList.add(FYUtils.fyParams("背景颜色最大长度不能超过32字符"));
        }
        if (StringUtils.isNotBlank(R.get("backgroundImage")) && R.get("backgroundImage").length() > 64) {
            errorList.add(FYUtils.fyParams("背景图最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("isShow")) && R.get("isShow").length() > 1) {
            errorList.add(FYUtils.fyParams("是否展示：0为否，1为是最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("wordOne")) && R.get("wordOne").length() > 128) {
            errorList.add(FYUtils.fyParams("文本描述1最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("wordTwo")) && R.get("wordTwo").length() > 128) {
            errorList.add(FYUtils.fyParams("文本描述2最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("wordThree")) && R.get("wordThree").length() > 128) {
            errorList.add(FYUtils.fyParams("文本描述3最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("buttonWord")) && R.get("buttonWord").length() > 64) {
            errorList.add(FYUtils.fyParams("按钮文字最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("buttonColour")) && R.get("buttonColour").length() > 32) {
            errorList.add(FYUtils.fyParams("按钮颜色最大长度不能超过32字符"));
        }
        if (StringUtils.isNotBlank(R.get("buttonColour")) && R.get("buttonColour").indexOf("#") != 0) {
            errorList.add(FYUtils.fyParams("按钮颜色格式错误"));
        }
        if (StringUtils.isNotBlank(appAd.getWordOneColor()) && appAd.getWordOneColor().indexOf("#") != 0) {
            errorList.add(FYUtils.fyParams("文本1颜色格式错误"));
        }
        if (StringUtils.isNotBlank(appAd.getWordTwoColor()) && appAd.getWordTwoColor().indexOf("#") != 0) {
            errorList.add(FYUtils.fyParams("文本2颜色格式错误"));
        }
        if (StringUtils.isNotBlank(appAd.getWordThreeColor()) && appAd.getWordThreeColor().indexOf("#") != 0) {
            errorList.add(FYUtils.fyParams("文本3颜色格式错误"));
        }
        return errorList;
    }

}