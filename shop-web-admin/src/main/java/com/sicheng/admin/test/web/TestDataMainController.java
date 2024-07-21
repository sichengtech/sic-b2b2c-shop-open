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
package com.sicheng.admin.test.web;

import com.sicheng.admin.test.entity.TestDataMain;
import com.sicheng.admin.test.service.TestDataMainService;
import com.sicheng.common.config.Global;
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

/**
 * 主子表生成Controller
 *
 * @author zhaolei
 * @version 2015-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testDataMain")
public class TestDataMainController extends BaseController {

    @Autowired
    private TestDataMainService testDataMainService;

    /**
     * 按实体id查询数据，类中先执行的方法
     *
     * @param id 实体的id
     * @return
     */
    @ModelAttribute
    public TestDataMain get(@RequestParam(required = false) Long id) {
        TestDataMain entity = null;
        if (id != null) {
            entity = testDataMainService.get(id);
        }
        if (entity == null) {
            entity = new TestDataMain();
        }
        return entity;
    }

    /**
     * 进入列表页
     *
     * @param testDataMain 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("test:testDataMain:view")
    @RequestMapping(value = {"list", ""})
    public String list(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TestDataMain> page = testDataMainService.findPage(new Page<TestDataMain>(request, response), testDataMain);
        model.addAttribute("page", page);
        return "admin/test/testDataMainList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param testDataMain 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("test:testDataMain:view")
    @RequestMapping(value = "form")
    public String form(TestDataMain testDataMain, Model model) {
        model.addAttribute("testDataMain", testDataMain);
        return "admin/test/testDataMainForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param testDataMain       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("test:testDataMain:edit")
    @RequestMapping(value = "save")
    public String save(TestDataMain testDataMain, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, testDataMain)) {
            return form(testDataMain, model);
        }
        testDataMainService.save(testDataMain);
        addMessage(redirectAttributes, "保存主子表成功");
        return "redirect:" + Global.getAdminPath() + "/test/testDataMain.do?repage";
    }

    /**
     * 执行删除方法
     *
     * @param testDataMain       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("test:testDataMain:edit")
    @RequestMapping(value = "delete")
    public String delete(TestDataMain testDataMain, RedirectAttributes redirectAttributes) {
        testDataMainService.delete(testDataMain);
        addMessage(redirectAttributes, "删除主子表成功");
        return "redirect:" + Global.getAdminPath() + "/test/testDataMain.do?repage";
    }

}