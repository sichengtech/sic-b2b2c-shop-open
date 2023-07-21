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

import com.sicheng.admin.cms.entity.Category;
import com.sicheng.admin.cms.entity.Link;
import com.sicheng.admin.cms.entity.Site;
import com.sicheng.admin.cms.service.CategoryService;
import com.sicheng.admin.cms.service.LinkService;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 链接Controller
 *
 * @author zhaolei
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/link")
public class LinkController extends BaseController {

    @Autowired
    private LinkService linkService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 按实体id查询数据，类中先执行的方法
     *
     * @param id 实体的id
     * @return
     */
    @ModelAttribute
    public Link get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return linkService.get(id);
        } else {
            return new Link();
        }
    }

    /**
     * 进入列表页
     *
     * @param link     实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("cms:link:view")
    @RequestMapping(value = {"list", ""})
    public String list(Link link, HttpServletRequest request, HttpServletResponse response, Model model) {
//		User user = UserUtils.getUser();
//		if (!user.isAdmin() && !SecurityUtils.getSubject().isPermitted("cms:link:audit")){
//			link.setUser(user);
//		}
        Page<Link> page = linkService.findPage(new Page<Link>(request, response), link, true);
        model.addAttribute("page", page);
        return "admin/cms/linkList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param link  实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("cms:link:view")
    @RequestMapping(value = "form")
    public String form(Link link, Model model) {
        // 如果当前传参有子节点，则选择取消传参选择
        if (link.getCategory() != null && link.getCategory().getId() != null) {
            List<Category> list = categoryService.findByParentId(link.getCategory().getId(), Site.getCurrentSiteId());
            if (list.size() > 0) {
                link.setCategory(null);
            } else {
                link.setCategory(categoryService.get(link.getCategory().getId()));
            }
        }
        model.addAttribute("link", link);
        return "admin/cms/linkForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param link               实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:link:edit")
    @RequestMapping(value = "save")
    public String save(Link link, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, link)) {
            return form(link, model);
        }
        linkService.save(link);
        addMessage(redirectAttributes, "保存链接'" + StringUtils.abbr(link.getTitle(), 50) + "'成功");
        return "redirect:" + adminPath + "/cms/link.do?repage&category.id=" + link.getCategory().getId();
    }

    /**
     * 执行删除方法
     *
     * @param link               v
     * @param categoryId
     * @param isRe
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:link:edit")
    @RequestMapping(value = "delete")
    public String delete(Link link, String categoryId, @RequestParam(required = false) Boolean isRe, RedirectAttributes redirectAttributes) {
        linkService.delete(link, isRe);
        addMessage(redirectAttributes, (isRe != null && isRe ? "发布" : "删除") + "链接成功");
        return "redirect:" + adminPath + "/cms/link.do?repage&category.id=" + categoryId;
    }

    /**
     * 链接选择列表
     */
    @RequiresPermissions("cms:link:view")
    @RequestMapping(value = "selectList")
    public String selectList(Link link, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(link, request, response, model);
        return "admin/cms/linkSelectList";
    }

    /**
     * 通过编号获取链接名称
     */
    @RequiresPermissions("cms:link:view")
    @ResponseBody
    @RequestMapping(value = "findByIds")
    public String findByIds(String ids) {
        List<Object[]> list = linkService.findByIds(ids);
        return JsonMapper.getInstance().toJson(list);
    }
}
