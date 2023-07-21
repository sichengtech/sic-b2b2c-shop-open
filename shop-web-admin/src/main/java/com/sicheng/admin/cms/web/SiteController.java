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
package com.sicheng.admin.cms.web;

import com.sicheng.admin.cms.entity.Site;
import com.sicheng.admin.cms.service.SiteService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.utils.CookieUtils;
import com.sicheng.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 站点Controller
 *
 * @author zhaolei
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/site")
public class SiteController extends BaseController {

    @Autowired
    private SiteService siteService;

    /**
     * 按实体id查询数据，类中先执行的方法
     *
     * @param id 实体的id
     * @return
     */
    @ModelAttribute
    public Site get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return siteService.get(id);
        } else {
            return new Site();
        }
    }

    /**
     * 进入列表页
     *
     * @param site     实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("cms:site:view")
    @RequestMapping(value = {"list", ""})
    public String list(Site site, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Site> page = siteService.findPage(new Page<Site>(request, response), site);
        model.addAttribute("page", page);
        return "admin/cms/siteList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param site  实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("cms:site:view")
    @RequestMapping(value = "form")
    public String form(Site site, Model model) {
        model.addAttribute("site", site);
        return "admin/cms/siteForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param site               实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:site:edit")
    @RequestMapping(value = "save")
    public String save(Site site, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, site)) {
            return form(site, model);
        }
        siteService.save(site);
        addMessage(redirectAttributes, "保存站点'" + site.getName() + "'成功");
        return "redirect:" + adminPath + "/cms/site.do?repage";
    }

    /**
     * 执行删除方法
     *
     * @param site               实体对象
     * @param isRe
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:site:edit")
    @RequestMapping(value = "delete")
    public String delete(Site site, @RequestParam(required = false) Boolean isRe, RedirectAttributes redirectAttributes) {
        if (Site.isDefault(site.getId())) {
            addMessage(redirectAttributes, "删除站点失败, 不允许删除默认站点");
        } else {
            siteService.delete(site, isRe);
            addMessage(redirectAttributes, (isRe != null && isRe ? "恢复" : "") + "删除站点成功");
        }
        return "redirect:" + adminPath + "/cms/site.do?repage";
    }

    /**
     * 选择站点
     *
     * @param id
     * @return
     */
    @RequiresPermissions("cms:site:select")
    @RequestMapping(value = "select")
    public String select(String id, boolean flag, HttpServletRequest request, HttpServletResponse response) {
        if (id != null) {
            // 保存到Cookie中，下次登录后自动切换到该站点
            CookieUtils.setCookie(request, response, "siteId", id);
        }
        if (flag) {
            return "redirect:" + adminPath + "/index.do";
        }
        return "admin/cms/siteSelect";
    }
}
