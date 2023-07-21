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
package com.sicheng.admin.sys.web;

import com.sicheng.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 标签Controller
 *
 * @author zhaolei
 * @version 2013-3-23
 */
@Controller
//@RequestMapping(value = "${adminPath}/tag")
public class TagController extends BaseController {

    /**
     * 管理后台,树结构选择标签（treeselect.tag）
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "${adminPath}/tag/treeselect")
    public String treeselect1(HttpServletRequest request, Model model) {
        model.addAttribute("url", request.getParameter("url"));    // 树结构数据URL
        model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
        model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
        model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
        model.addAttribute("isAll", request.getParameter("isAll"));    // 是否读取全部数据，不进行权限过滤
        model.addAttribute("module", request.getParameter("module"));    // 过滤栏目模型（仅针对CMS的Category树）
        return "admin/sys/tagTreeselect";
    }

    /**
     * 图标选择标签（iconselect.tag）
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "${adminPath}/tag/iconselect")
    public String iconselect(HttpServletRequest request, Model model) {
        model.addAttribute("value", request.getParameter("value"));
        //return "admin/sys/tagIconselect";
        return "admin/sys/tagIconselect";
    }

}
