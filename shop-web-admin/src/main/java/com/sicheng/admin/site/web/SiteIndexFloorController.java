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
package com.sicheng.admin.site.web;

import com.sicheng.admin.site.entity.SiteIndexFloor;
import com.sicheng.admin.site.service.SiteIndexFloorService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
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
 * 管理网站首页楼层 Controller
 * 所属模块：site
 *
 * @author 张加利
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteIndexFloor")
public class SiteIndexFloorController extends BaseController {

    @Autowired
    private SiteIndexFloorService siteIndexFloorService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteIndexFloor 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteIndexFloor:view")
    @RequestMapping(value = "list")
    public String list(SiteIndexFloor siteIndexFloor, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteIndexFloor> page = new Page<SiteIndexFloor>(request, response);
        page.setOrderBy("sort");
        page = siteIndexFloorService.selectByWhere(page, new Wrapper(siteIndexFloor));
        model.addAttribute("page", page);
        return "admin/site/siteIndexFloorList";
    }

    /**
     * 进入保存页面
     *
     * @param siteIndexFloor 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteIndexFloor:save")
    @RequestMapping(value = "save1")
    public String save1(SiteIndexFloor siteIndexFloor, Model model) {
        if (siteIndexFloor == null) {
            siteIndexFloor = new SiteIndexFloor();
        }
        model.addAttribute("siteIndexFloor", siteIndexFloor);
        return "admin/site/siteIndexFloorForm";
    }

    /**
     * 执行保存
     *
     * @param siteIndexFloor     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteIndexFloor:save")
    @RequestMapping(value = "save2")
    public String save2(SiteIndexFloor siteIndexFloor, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteIndexFloor, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteIndexFloor, model);//回显错误提示
        }

        //业务处理
        siteIndexFloor.setIsDisplay(R.get("isDisplay", "0"));
        siteIndexFloorService.insertSelective(siteIndexFloor);
        addMessage(redirectAttributes, "保存网站首页楼层成功");
        return "redirect:" + adminPath + "/site/siteIndexFloor/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteIndexFloor 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteIndexFloor:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteIndexFloor siteIndexFloor, Model model) {
        SiteIndexFloor entity = null;
        if (siteIndexFloor != null) {
            if (siteIndexFloor.getId() != null) {
                entity = siteIndexFloorService.selectById(siteIndexFloor.getId());
            }
        }
        model.addAttribute("siteIndexFloor", entity);
        return "admin/site/siteIndexFloorForm";
    }

    /**
     * 执行编辑
     *
     * @param siteIndexFloor     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteIndexFloor:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteIndexFloor siteIndexFloor, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteIndexFloor, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteIndexFloor, model);//回显错误提示
        }

        //业务处理
        siteIndexFloor.setIsDisplay(R.get("isDisplay", "0"));
        siteIndexFloorService.updateByIdSelective(siteIndexFloor);
        addMessage(redirectAttributes, "编辑网站首页楼层成功");
        return "redirect:" + adminPath + "/site/siteIndexFloor/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteIndexFloor     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteIndexFloor:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteIndexFloor siteIndexFloor, RedirectAttributes redirectAttributes) {
        siteIndexFloorService.deleteById(siteIndexFloor.getId());
        addMessage(redirectAttributes, "删除网站首页楼层成功");
        return "redirect:" + adminPath + "/site/siteIndexFloor/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteIndexFloor 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteIndexFloor siteIndexFloor, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add("楼层排序不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("楼层排序最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("楼层名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("楼层名称最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("color"))) {
            errorList.add("色彩风格(例如#XXXXXX)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("color")) && R.get("color").length() > 64) {
            errorList.add("色彩风格(例如#XXXXXX)最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("isDisplay"))) {
            errorList.add("是否显示（0不显示、1显示）不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isDisplay")) && R.get("isDisplay").length() > 1) {
            errorList.add("是否显示（0不显示、1显示）最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("text"))) {
            errorList.add("导航文字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("text")) && R.get("text").length() > 64) {
            errorList.add("导航文字最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("imgPath"))) {
            errorList.add("导航图片path不能为空");
        }
        if (StringUtils.isNotBlank(R.get("imgPath")) && R.get("imgPath").length() > 64) {
            errorList.add("导航图片path最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add("楼层类型（1普通类型、2品牌、3广告条）不能为空");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("楼层类型（1普通类型、2品牌、3广告条）最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("style"))) {
            errorList.add("模板风格（模板风格1、模板风格2）不能为空");
        }
        if (StringUtils.isNotBlank(R.get("style")) && R.get("style").length() > 64) {
            errorList.add("模板风格（模板风格1、模板风格2）最大长度不能超过64字符");
        }
        return errorList;
    }
}