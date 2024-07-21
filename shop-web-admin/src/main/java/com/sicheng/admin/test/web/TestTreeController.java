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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sicheng.admin.test.entity.TestTree;
import com.sicheng.admin.test.service.TestTreeService;
import com.sicheng.common.config.Global;
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
import java.util.Map;

/**
 * 树结构生成Controller
 *
 * @author zhaolei
 * @version 2015-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testTree")
public class TestTreeController extends BaseController {

    @Autowired
    private TestTreeService testTreeService;

    /**
     * 按实体id查询数据，类中先执行的方法
     *
     * @param id 实体的id
     * @return
     */
    @ModelAttribute
    public TestTree get(@RequestParam(required = false) Long id) {
        TestTree entity = null;
        if (id != null) {
            entity = testTreeService.get(id);
        }
        if (entity == null) {
            entity = new TestTree();
        }
        return entity;
    }

    /**
     * 进入列表页
     *
     * @param testTree 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("test:testTree:view")
    @RequestMapping(value = {"list", ""})
    public String list(TestTree testTree, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<TestTree> list = testTreeService.findList(testTree);
        model.addAttribute("list", list);
        return "admin/test/testTreeList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param testTree 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("test:testTree:view")
    @RequestMapping(value = "form")
    public String form(TestTree testTree, Model model) {
        if (testTree.getParent() != null && testTree.getParent().getId() != null) {
            testTree.setParent(testTreeService.get(testTree.getParent().getId()));
            // 获取排序号，最末节点排序号+30
            if (testTree.getId() == null) {
                TestTree testTreeChild = new TestTree();
                testTreeChild.setParent(new TestTree(testTree.getParent().getId()));
                List<TestTree> list = testTreeService.findList(testTree);
                if (list.size() > 0) {
                    testTree.setSort(list.get(list.size() - 1).getSort());
                    if (testTree.getSort() != null) {
                        testTree.setSort(testTree.getSort() + 30);
                    }
                }
            }
        }
        if (testTree.getSort() == null) {
            testTree.setSort(30);
        }
        model.addAttribute("testTree", testTree);
        return "admin/test/testTreeForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param testTree           实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("test:testTree:edit")
    @RequestMapping(value = "save")
    public String save(TestTree testTree, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, testTree)) {
            return form(testTree, model);
        }
        testTreeService.save(testTree);
        addMessage(redirectAttributes, "保存树结构成功");
        return "redirect:" + Global.getAdminPath() + "/test/testTree.do?repage";
    }

    /**
     * 执行删除方法
     *
     * @param testTree           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("test:testTree:edit")
    @RequestMapping(value = "delete")
    public String delete(TestTree testTree, RedirectAttributes redirectAttributes) {
        testTreeService.delete(testTree);
        addMessage(redirectAttributes, "删除树结构成功");
        return "redirect:" + Global.getAdminPath() + "/test/testTree.do?repage";
    }

    /**
     * 获取树的数据
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     *
     * @param extId 排除的ID
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<TestTree> list = testTreeService.findList(new TestTree());
        for (int i = 0; i < list.size(); i++) {
            TestTree e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

}