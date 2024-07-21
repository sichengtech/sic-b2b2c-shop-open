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
package com.sicheng.admin.cms.web;

import com.sicheng.admin.cms.service.CategoryService;
import com.sicheng.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 内容管理Controller
 *
 * @author zhaolei
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cms")
public class CmsController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 进入内容管理页
     *
     * @return
     */
    @RequiresPermissions("cms:view")
    @RequestMapping(value = "")
    public String index() {
        return "admin/cms/cmsIndex";
    }

    /**
     * 显示内容管理业左侧的栏目树
     *
     * @param model
     * @return
     */
    @RequiresPermissions("cms:view")
    @RequestMapping(value = "tree")
    public String tree(Model model) {
        model.addAttribute("categoryList", categoryService.findByUser(true, null));
        return "admin/cms/cmsTree";
    }

    /**
     * 显示内容管理业右侧页面
     *
     * @return
     */
    @RequiresPermissions("cms:view")
    @RequestMapping(value = "none")
    public String none() {
        return "admin/cms/cmsNone";
    }

}
