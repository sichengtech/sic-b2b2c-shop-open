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

import com.sicheng.admin.site.entity.SiteAdContent;
import com.sicheng.admin.site.service.SiteAdContentService;

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
 * 广告位内容 Controller
 * 所属模块：site
 *
 * @author cl
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteAdContent")
public class SiteAdContentController extends BaseController {

    @Autowired
    private SiteAdContentService siteAdContentService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070108";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteAdContent 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteAdContent:view")
    @RequestMapping(value = "list")
    public String list(SiteAdContent siteAdContent, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteAdContent> page = siteAdContentService.selectByWhere(new Page<SiteAdContent>(request, response), new Wrapper(siteAdContent));
        model.addAttribute("page", page);
        return "admin/site/siteAdContentList";
    }

    /**
     * 进入保存页面
     *
     * @param siteAdContent 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteAdContent:save")
    @RequestMapping(value = "save1")
    public String save1(SiteAdContent siteAdContent, Model model) {
        if (siteAdContent == null) {
            siteAdContent = new SiteAdContent();
        }
        model.addAttribute("siteAdContent", siteAdContent);
        return "admin/site/siteAdContentForm";
    }

    /**
     * 执行保存
     *
     * @param siteAdContent      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteAdContent:save")
    @RequestMapping(value = "save2")
    public String save2(SiteAdContent siteAdContent, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteAdContent, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteAdContent, model);//回显错误提示
        }

        //业务处理
        siteAdContentService.insertSelective(siteAdContent);
        addMessage(redirectAttributes, "保存广告位内容成功");
        return "redirect:" + adminPath + "/site/siteAdContent/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteAdContent 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteAdContent:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteAdContent siteAdContent, Model model) {
        SiteAdContent entity = null;
        if (siteAdContent != null) {
            if (siteAdContent.getId() != null) {
                entity = siteAdContentService.selectById(siteAdContent.getId());
            }
        }
        model.addAttribute("siteAdContent", entity);
        return "admin/site/siteAdContentForm";
    }

    /**
     * 执行编辑
     *
     * @param siteAdContent      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteAdContent:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteAdContent siteAdContent, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteAdContent, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteAdContent, model);//回显错误提示
        }

        //业务处理
        siteAdContentService.updateByIdSelective(siteAdContent);
        addMessage(redirectAttributes, "编辑广告位内容成功");
        return "redirect:" + adminPath + "/site/siteAdContent/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteAdContent      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteAdContent:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteAdContent siteAdContent, RedirectAttributes redirectAttributes) {
        siteAdContentService.deleteById(siteAdContent.getId());
        addMessage(redirectAttributes, "删除广告位内容成功");
        return "redirect:" + adminPath + "/site/siteAd/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteAdContent 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteAdContent siteAdContent, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("adContentId"))) {
            errorList.add("广告内容ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("adContentId")) && R.get("adContentId").length() > 19) {
            errorList.add("广告内容ID最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("adId"))) {
            errorList.add("关联(广告表)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("adId")) && R.get("adId").length() > 19) {
            errorList.add("关联(广告表)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("content"))) {
            errorList.add("广告内容不能为空");
        }
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add("是否有效不能为空");
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add("是否有效最大长度不能超过1字符");
        }
        return errorList;
    }

}