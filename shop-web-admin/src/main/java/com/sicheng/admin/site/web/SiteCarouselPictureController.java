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
package com.sicheng.admin.site.web;

import com.sicheng.admin.site.entity.SiteCarouselPicture;
import com.sicheng.admin.site.service.SiteCarouselPictureService;

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
 * 管理网站的轮播图片 Controller
 * 所属模块：site
 *
 * @author zjl
 * @version 2017-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteCarouselPicture")
public class SiteCarouselPictureController extends BaseController {

    @Autowired
    private SiteCarouselPictureService siteCarouselPictureService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070103";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteCarouselPicture 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteCarouselPicture:view")
    @RequestMapping(value = "list")
    public String list(SiteCarouselPicture siteCarouselPicture, HttpServletRequest request, HttpServletResponse response, Model model) {
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(siteCarouselPicture);
        wrapper.orderBy("type,sort");
        Page<SiteCarouselPicture> page = siteCarouselPictureService.selectByWhere(new Page<SiteCarouselPicture>(request, response), wrapper);
        model.addAttribute("page", page);
        return "admin/site/siteCarouselPictureList";
    }

    /**
     * 进入保存页面
     *
     * @param siteCarouselPicture 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteCarouselPicture:save")
    @RequestMapping(value = "save1")
    public String save1(SiteCarouselPicture siteCarouselPicture, Model model) {
        if (siteCarouselPicture == null) {
            siteCarouselPicture = new SiteCarouselPicture();
        }
        model.addAttribute("siteCarouselPicture", siteCarouselPicture);
        return "admin/site/siteCarouselPictureForm";
    }

    /**
     * 执行保存
     *
     * @param siteCarouselPicture 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteCarouselPicture:save")
    @RequestMapping(value = "save2")
    public String save2(SiteCarouselPicture siteCarouselPicture, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteCarouselPicture, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteCarouselPicture, model);//回显错误提示
        }

        //业务处理
        siteCarouselPicture.setStatus(R.get("status", "0"));
        siteCarouselPictureService.insertSelective(siteCarouselPicture);
        addMessage(redirectAttributes, FYUtils.fyParams("保存网站轮播图片成功"));
        return "redirect:" + adminPath + "/site/siteCarouselPicture/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteCarouselPicture 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteCarouselPicture:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteCarouselPicture siteCarouselPicture, Model model) {
        SiteCarouselPicture entity = null;
        if (siteCarouselPicture != null) {
            if (siteCarouselPicture.getId() != null) {
                entity = siteCarouselPictureService.selectById(siteCarouselPicture.getId());
            }
        }
        model.addAttribute("siteCarouselPicture", entity);
        return "admin/site/siteCarouselPictureForm";
    }

    /**
     * 执行编辑
     *
     * @param siteCarouselPicture 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteCarouselPicture:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteCarouselPicture siteCarouselPicture, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteCarouselPicture, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteCarouselPicture, model);//回显错误提示
        }

        //业务处理
        siteCarouselPicture.setStatus(R.get("status", "0"));
        siteCarouselPictureService.updateByIdSelective(siteCarouselPicture);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑网站轮播图片成功"));
        return "redirect:" + adminPath + "/site/siteCarouselPicture/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteCarouselPicture 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteCarouselPicture:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteCarouselPicture siteCarouselPicture, RedirectAttributes redirectAttributes) {
        siteCarouselPictureService.deleteById(siteCarouselPicture.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除网站轮播图片成功"));
        return "redirect:" + adminPath + "/site/siteCarouselPicture/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteCarouselPicture 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteCarouselPicture siteCarouselPicture, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("path"))) {
            errorList.add(FYUtils.fyParams("图片地址不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("path")) && R.get("path").length() > 64) {
            errorList.add(FYUtils.fyParams("图片地址最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("url"))) {
            errorList.add(FYUtils.fyParams("目标连接不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("url")) && R.get("url").length() > 255) {
            errorList.add(FYUtils.fyParams("目标连接最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("title"))) {
            errorList.add(FYUtils.fyParams("标题不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("title")) && R.get("title").length() > 64) {
            errorList.add(FYUtils.fyParams("标题最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fyParams("排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add(FYUtils.fyParams("状态，0禁用 、1启用不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add(FYUtils.fyParams("状态，0禁用 、1启用最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add(FYUtils.fyParams("类型不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 20) {
            errorList.add(FYUtils.fyParams("类型最大长度不能超过20字符"));
        }
		/*if(StringUtils.isBlank(R.get("action"))){
			errorList.add(FYUtils.fyParams("动作，字典不能为空"));
		}
		if(StringUtils.isNotBlank(R.get("action")) && R.get("action").length() > 10){
			errorList.add(FYUtils.fyParams("动作，字典最大长度不能超过10字符"));
		}*/
        if (StringUtils.isBlank(R.get("target"))) {
            errorList.add(FYUtils.fyParams("打开方式（mainFrame、 _blank、_self、_parent、_top）不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("target")) && R.get("target").length() > 64) {
            errorList.add(FYUtils.fyParams("打开方式（mainFrame、 _blank、_self、_parent、_top）最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("txt")) && R.get("txt").length() > 255) {
            errorList.add(FYUtils.fyParams("文本最大长度不能超过255字符"));
        }
        return errorList;
    }

}