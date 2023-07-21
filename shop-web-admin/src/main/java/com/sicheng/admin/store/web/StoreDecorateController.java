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
package com.sicheng.admin.store.web;

import com.sicheng.admin.store.entity.StoreDecorate;
import com.sicheng.admin.store.service.StoreDecorateService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺装修 Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-06-20
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeDecorate")
public class StoreDecorateController extends BaseController {

    @Autowired
    private StoreDecorateService storeDecorateService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param storeDecorate 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeDecorate:view")
    @RequestMapping(value = "list")
    public String list(StoreDecorate storeDecorate, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreDecorate> page = storeDecorateService.selectByWhere(new Page<StoreDecorate>(request, response), new Wrapper(storeDecorate));
        model.addAttribute("page", page);
        return "admin/store/storeDecorateList";
    }

    /**
     * 进入保存页面
     *
     * @param storeDecorate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeDecorate:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreDecorate storeDecorate, Model model) {
        if (storeDecorate == null) {
            storeDecorate = new StoreDecorate();
        }
        model.addAttribute("storeDecorate", storeDecorate);
        return "admin/store/storeDecorateForm";
    }

    /**
     * 执行保存
     *
     * @param storeDecorate      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeDecorate:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreDecorate storeDecorate, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeDecorate, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeDecorate, model);//回显错误提示
        }

        //业务处理
        storeDecorateService.insertSelective(storeDecorate);
        addMessage(redirectAttributes, "保存店铺装修成功");
        return "redirect:" + adminPath + "/store/storeDecorate/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeDecorate 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeDecorate:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreDecorate storeDecorate, Model model) {
        StoreDecorate entity = null;
        if (storeDecorate != null) {
            if (storeDecorate.getId() != null) {
                entity = storeDecorateService.selectById(storeDecorate.getId());
            }
        }
        model.addAttribute("storeDecorate", entity);
        return "admin/store/storeDecorateForm";
    }

    /**
     * 执行编辑
     *
     * @param storeDecorate      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeDecorate:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreDecorate storeDecorate, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeDecorate, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeDecorate, model);//回显错误提示
        }

        //业务处理
        storeDecorateService.updateByIdSelective(storeDecorate);
        addMessage(redirectAttributes, "编辑店铺装修成功");
        return "redirect:" + adminPath + "/store/storeDecorate/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeDecorate      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeDecorate:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreDecorate storeDecorate, RedirectAttributes redirectAttributes) {
        storeDecorateService.deleteById(storeDecorate.getId());
        addMessage(redirectAttributes, "删除店铺装修成功");
        return "redirect:" + adminPath + "/store/storeDecorate/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeDecorate 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreDecorate storeDecorate, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("storeId"))) {
            errorList.add("主键(店铺id)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 19) {
            errorList.add("主键(店铺id)最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("solution"))) {
            errorList.add("店铺装修方案(1模板一2模板二3模板三)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("solution")) && R.get("solution").length() > 1) {
            errorList.add("店铺装修方案(1模板一2模板二3模板三)最大长度不能超过1字符");
        }
        return errorList;
    }

}