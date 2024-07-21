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

import com.sicheng.admin.test.entity.TestData;
import com.sicheng.admin.test.service.TestDataService;
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
 * 单表生成Controller
 *
 * @author zhaolei
 * @version 2016-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testData")
public class TestDataController extends BaseController {

    @Autowired
    private TestDataService testDataService;

    /**
     * 按实体id查询数据，类中先执行的方法
     *
     * @param id 实体的id
     * @return
     */
    @ModelAttribute
    public TestData get(@RequestParam(required = false) Long id) {
        TestData entity = null;
        if (id != null) {
            entity = testDataService.selectById(id);
        }
        if (entity == null) {
            entity = new TestData();
        }
        return entity;
    }

    /**
     * 进入列表页
     *
     * @param testData 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = {"list", ""})
    public String list(TestData testData, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TestData> page = testDataService.selectByWhere(new Page<TestData>(request, response), testData);
        model.addAttribute("page", page);
        return "admin/test/testDataList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param testData 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("test:testData:view")
    @RequestMapping(value = "form")
    public String form(TestData testData, Model model) {
        model.addAttribute("testData", testData);
        return "admin/test/testDataForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param testData           实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("test:testData:edit")
    @RequestMapping(value = "save")
    public String save(TestData testData, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, testData)) {
            return form(testData, model);
        }
        testDataService.saveOrUpdate(testData);
        addMessage(redirectAttributes, "保存单表成功");
        return "redirect:" + Global.getAdminPath() + "/test/testData.do?repage";
    }

    /**
     * 执行删除方法
     *
     * @param testData           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("test:testData:edit")
    @RequestMapping(value = "delete")
    public String delete(TestData testData, RedirectAttributes redirectAttributes) {
        testDataService.deleteById(testData.getId());
        addMessage(redirectAttributes, "删除单表成功");
        return "redirect:" + Global.getAdminPath() + "/test/testData.do?repage";
    }

}