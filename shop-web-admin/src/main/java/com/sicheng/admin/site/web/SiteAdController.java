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

import com.sicheng.admin.site.entity.SiteAd;
import com.sicheng.admin.site.entity.SiteAdContent;
import com.sicheng.admin.site.service.SiteAdContentService;
import com.sicheng.admin.site.service.SiteAdService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 广告位 Controller
 * 所属模块：site
 *
 * @author 蔡龙
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteAd")
public class SiteAdController extends BaseController {

    @Autowired
    private SiteAdService siteAdService;



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
        String menu3 = "070105";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteAd   实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteAd:view")
    @RequestMapping(value = "list")
    public String list(SiteAd siteAd, HttpServletRequest request, HttpServletResponse response, Model model) {
        Wrapper wrapper = new Wrapper();
        wrapper.orderBy("a.sort asc");
        wrapper.setEntity(siteAd);
        Page<SiteAd> page = siteAdService.selectByWhere(new Page<SiteAd>(request, response), wrapper);
        model.addAttribute("page", page);
        return "admin/site/siteAdList";
    }

    /**
     * 进入保存页面
     *
     * @param siteAd 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteAd:save")
    @RequestMapping(value = "save1")
    public String save1(SiteAd siteAd, Model model) {
        if (siteAd == null) {
            siteAd = new SiteAd();
        }
        model.addAttribute("siteAd", siteAd);
        return "admin/site/siteAdForm";
    }

    /**
     * 执行保存
     *
     * @param siteAd             实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteAd:save")
    @RequestMapping(value = "save2")
    public String save2(SiteAd siteAd, SiteAdContent siteAdContent, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteAd, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteAd, model);//回显错误提示
        }
        //业务处理
        siteAd.setIsOpen(R.get("isOpen", "0"));
        String detail = R.get("content");
        String html_unsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
        siteAdContent.setContent(html_unsafe);
        siteAdService.changeSiteAd(siteAd, siteAdContent);
        addMessage(redirectAttributes, FYUtils.fyParams("保存广告位成功"));
        return "redirect:" + adminPath + "/site/siteAd/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteAd 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteAd:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteAd siteAd, Model model) {
        SiteAd entity = null;
        if (siteAd != null) {
            if (siteAd.getId() != null) {
                entity = siteAdService.selectById(siteAd.getId());
            }
        }
        SiteAdContent siteAdContent = new SiteAdContent();
        siteAdContent.setAdId(siteAd.getAdId());
        Page<SiteAdContent> page = siteAdContentService.selectByWhere(new Page<SiteAdContent>(R.getRequest(), R.getResponse()), new Wrapper(siteAdContent));
        //把广告位内容回显回来
        SiteAdContent sContent = new SiteAdContent();
        sContent.setAdId(siteAd.getAdId());
        sContent.setStatus("1");//0删除、1有效（同ad_id下最多只有一个有效时间最近的为有效）
        List<SiteAdContent> contentList = siteAdContentService.selectByWhere(new Wrapper(sContent));
        if (!contentList.isEmpty()) {
            model.addAttribute("sContent", contentList.get(0));
        }
        model.addAttribute("page", page);
        model.addAttribute("siteAd", entity);
        return "admin/site/siteAdForm";
    }

    /**
     * 执行编辑
     *
     * @param siteAd             实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteAd:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteAd siteAd, SiteAdContent siteAdContent, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteAd, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteAd, model);//回显错误提示
        }
        //业务处理
        siteAd.setIsOpen(R.get("isOpen", "0"));
        String detail = R.get("content");
        String html_unsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
        siteAdContent.setContent(html_unsafe);
        siteAdService.changeSiteAd(siteAd, siteAdContent);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑广告位成功"));
        return "redirect:" + adminPath + "/site/siteAd/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteAd             实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteAd:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteAd siteAd, RedirectAttributes redirectAttributes) {
        siteAdService.deleteAd(siteAd);
        addMessage(redirectAttributes, FYUtils.fyParams("删除广告位成功"));
        return "redirect:" + adminPath + "/site/siteAd/list.do?repage";
    }

    /**
     * 判断编号是否重复
     *
     * @param oldAdNumber
     * @param adNumber
     * @return
     */

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "validateAdNumber")
    public String validateAdNumber(String oldAdNumber, String adNumber) {
        if (StringUtils.isNotBlank(adNumber) && adNumber.equals(oldAdNumber)) {
            return "true";
        } else if (StringUtils.isNotBlank(adNumber)) {
            SiteAd siteAd = new SiteAd();
            siteAd.setAdNumber(adNumber);
            List<SiteAd> SiteAds = siteAdService.selectByWhere(new Wrapper(siteAd));
            if (SiteAds.isEmpty()) {
                return "true";
            } else {
                return "false";
            }
        }
        return "false";
    }

    /**
     * 表单验证
     *
     * @param siteAd 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteAd siteAd, Model model) {
        List<String> errorList = new ArrayList<String>();
        String oldAdNumber = R.get("oldAdNumber");
        String adNumber = R.get("adNumber");
        if (StringUtils.isBlank(R.get("adNumber"))) {
            errorList.add("编号不能为空");
        }
        if (StringUtils.isNotBlank(R.get("adNumber")) && R.get("adNumber").length() > 64) {
            errorList.add(FYUtils.fyParams("编号最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(oldAdNumber)) {
            if (!oldAdNumber.equals(adNumber)) {
                SiteAd s = new SiteAd();
                s.setAdNumber(adNumber);
                List<SiteAd> siteAds = siteAdService.selectByWhere(new Wrapper(s));
                if (!siteAds.isEmpty()) {
                    errorList.add(FYUtils.fyParams("编号不能重复"));
                }
            }
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("广告标题不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 255) {
            errorList.add(FYUtils.fyParams("广告标题最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("isOpen"))) {
            errorList.add(FYUtils.fyParams("是否开启不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("isOpen")) && R.get("isOpen").length() > 1) {
            errorList.add(FYUtils.fyParams("是否开启最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fyParams("排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 5) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过5字符"));
        }
        if (StringUtils.isBlank(R.get("content"))) {
            errorList.add(FYUtils.fyParams("内容不能为空"));
        }
        return errorList;
    }

}