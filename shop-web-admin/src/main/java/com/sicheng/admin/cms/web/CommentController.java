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

import com.sicheng.admin.cms.entity.Comment;
import com.sicheng.admin.cms.service.CommentService;
import com.sicheng.admin.sys.utils.DictUtils;
import com.sicheng.admin.sys.utils.UserUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 评论Controller
 *
 * @author zhaolei
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    /**
     * 按实体id查询数据，类中先执行的方法
     *
     * @param id 实体的id
     * @return
     */
    @ModelAttribute
    public Comment get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return commentService.get(id);
        } else {
            return new Comment();
        }
    }

    /**
     * 进入列表页
     *
     * @param comment  实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("cms:comment:view")
    @RequestMapping(value = {"list", ""})
    public String list(Comment comment, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Comment> page = commentService.findPage(new Page<Comment>(request, response), comment);
        model.addAttribute("page", page);
        return "admin/cms/commentList";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param comment            实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:comment:edit")
    @RequestMapping(value = "save")
    public String save(Comment comment, RedirectAttributes redirectAttributes) {
        if (beanValidator(redirectAttributes, comment)) {
            if (comment.getAuditUser() == null) {
                comment.setAuditUser(UserUtils.getUser());
                comment.setAuditDate(new Date());
            }
            comment.setDelFlag(Comment.DEL_FLAG_NORMAL);
            commentService.save(comment);
            addMessage(redirectAttributes, DictUtils.getDictLabel(comment.getDelFlag(), "cms_del_flag", "保存")
                    + "评论'" + StringUtils.abbr(StringUtils.replaceHtml(comment.getContent()), 50) + "'成功");
        }
        return "redirect:" + adminPath + "/cms/comment.do?repage&delFlag=2";
    }

    /**
     * 执行删除方法
     *
     * @param comment            实体对象
     * @param isRe
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("cms:comment:edit")
    @RequestMapping(value = "delete")
    public String delete(Comment comment, @RequestParam(required = false) Boolean isRe, RedirectAttributes redirectAttributes) {
        commentService.delete(comment, isRe);
        addMessage(redirectAttributes, (isRe != null && isRe ? "恢复审核" : "删除") + "评论成功");
        return "redirect:" + adminPath + "/cms/comment.do?repage&delFlag=2";
    }

}
