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

import com.sicheng.admin.cms.entity.Guestbook;
import com.sicheng.admin.cms.service.GuestbookService;
import com.sicheng.admin.sys.utils.DictUtils;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.persistence.Page;
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
import java.util.Date;

/**
 * 留言Controller
 *
 * @author zhaolei
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/guestbook")
public class GuestbookController extends BaseController {

    @Autowired
    private GuestbookService guestbookService;

    /**
     * 按实体id查询数据，类中先执行的方法
     *
     * @param id 实体的id
     * @return
     */
    @ModelAttribute
    public Guestbook get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return guestbookService.get(id);
        } else {
            return new Guestbook();
        }
    }

    /**
     * 进入列表页
     *
     * @param guestbook 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("cms:guestbook:view")
    @RequestMapping(value = {"list", ""})
    public String list(Guestbook guestbook, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Guestbook> page = guestbookService.findPage(new Page<Guestbook>(request, response), guestbook);
        model.addAttribute("page", page);
        return "admin/cms/guestbookList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param guestbook 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("cms:guestbook:view")
    @RequestMapping(value = "form")
    public String form(Guestbook guestbook, Model model) {
        model.addAttribute("guestbook", guestbook);
        return "admin/cms/guestbookForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param guestbook          实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:guestbook:edit")
    @RequestMapping(value = "save")
    public String save(Guestbook guestbook, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, guestbook)) {
            return form(guestbook, model);
        }
        if (guestbook.getReUser() == null) {
            guestbook.setReUser(UserUtils.getUser());
            guestbook.setReDate(new Date());
        }
        guestbookService.save(guestbook);
        addMessage(redirectAttributes, DictUtils.getDictLabel(guestbook.getDelFlag(), "cms_del_flag", "保存")
                + "留言'" + guestbook.getName() + "'成功");
        return "redirect:" + adminPath + "/cms/guestbook.do?repage&status=2";
    }

    /**
     * 执行删除方法
     *
     * @param guestbook          实体对象
     * @param isRe
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:guestbook:edit")
    @RequestMapping(value = "delete")
    public String delete(Guestbook guestbook, @RequestParam(required = false) Boolean isRe, RedirectAttributes redirectAttributes) {
        guestbookService.delete(guestbook, isRe);
        addMessage(redirectAttributes, (isRe != null && isRe ? "恢复审核" : "删除") + "留言成功");
        return "redirect:" + adminPath + "/cms/guestbook.do?repage&status=2";
    }

}
