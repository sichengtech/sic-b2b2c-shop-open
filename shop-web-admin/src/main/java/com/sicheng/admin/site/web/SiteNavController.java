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

import com.sicheng.admin.site.entity.SiteNav;
import com.sicheng.admin.site.service.SiteNavService;

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
 * 管理页面的导航 Controller
 * 所属模块：site
 *
 * @author 张加利
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteNav")
public class SiteNavController extends BaseController {

    @Autowired
    private SiteNavService siteNavService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070102";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteNav  实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteNav:view")
    @RequestMapping(value = "list")
    public String list(SiteNav siteNav, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteNav> page = siteNavService.selectByWhere(new Page<SiteNav>(request, response), new Wrapper(siteNav));
        model.addAttribute("page", page);
        return "admin/site/siteNavList";
    }

    /**
     * 进入保存页面
     *
     * @param siteNav 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteNav:save")
    @RequestMapping(value = "save1")
    public String save1(SiteNav siteNav, Model model) {
        if (siteNav == null) {
            siteNav = new SiteNav();
        }
        model.addAttribute("siteNav", siteNav);
        return "admin/site/siteNavForm";
    }

    /**
     * 执行保存
     *
     * @param siteNav            实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteNav:save")
    @RequestMapping(value = "save2")
    public String save2(SiteNav siteNav, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteNav, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteNav, model);//回显错误提示
        }
        //业务处理
        siteNav.setIsNewWindow(R.get("isNewWindow", "0"));
        siteNavService.insertSelective(siteNav);
        addMessage(redirectAttributes, "保存页面导航成功");
        return "redirect:" + adminPath + "/site/siteNav/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteNav 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteNav:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteNav siteNav, Model model) {
        SiteNav entity = null;
        if (siteNav != null) {
            if (siteNav.getId() != null) {
                entity = siteNavService.selectById(siteNav.getId());
            }
        }
        model.addAttribute("siteNav", entity);
        return "admin/site/siteNavForm";
    }

    /**
     * 执行编辑
     *
     * @param siteNav            实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteNav:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteNav siteNav, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteNav, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteNav, model);//回显错误提示
        }
        //业务处理
        siteNav.setIsNewWindow(R.get("isNewWindow", "0"));
        siteNavService.updateByIdSelective(siteNav);
        addMessage(redirectAttributes, "编辑页面导航成功");
        return "redirect:" + adminPath + "/site/siteNav/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteNav            实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteNav:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteNav siteNav, RedirectAttributes redirectAttributes) {
        siteNavService.deleteById(siteNav.getId());
        addMessage(redirectAttributes, "删除页面导航成功");
        return "redirect:" + adminPath + "/site/siteNav/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteNav 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteNav siteNav, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add("排序不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("导航名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("导航名称最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("location"))) {
            errorList.add("显示位置(1顶部导航、2中部导航、3底部导航)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("location")) && R.get("location").length() > 1) {
            errorList.add("显示位置(1顶部导航、2中部导航、3底部导航)最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("isNewWindow"))) {
            errorList.add("是否新窗口导航(0否、1是)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isNewWindow")) && R.get("isNewWindow").length() > 1) {
            errorList.add("是否新窗口导航(0否、1是)最大长度不能超过1字符");
        }
		/*if(StringUtils.isBlank(R.get("isOpen"))){
			errorList.add("是否启用(0禁用、1启用)不能为空");
		}
		if(StringUtils.isNotBlank(R.get("isOpen")) && R.get("isOpen").length() > 1){
			errorList.add("是否启用(0禁用、1启用)最大长度不能超过1字符");
		}*/
        if (StringUtils.isBlank(R.get("action"))) {
            errorList.add("导航动作(1搜索关键字、2打开商品、3打开店铺、4打开商品分类)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("action")) && R.get("action").length() > 1) {
            errorList.add("导航动作(1搜索关键字、2打开商品、3打开店铺、4打开商品分类)最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("target"))) {
            errorList.add("目标URL，目标ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("target")) && R.get("target").length() > 255) {
            errorList.add("目标URL，目标ID最大长度不能超过255字符");
        }
        return errorList;
    }

}