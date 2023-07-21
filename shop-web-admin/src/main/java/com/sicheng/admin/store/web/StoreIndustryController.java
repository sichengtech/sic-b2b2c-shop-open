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

import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.admin.store.entity.StoreIndustry;
import com.sicheng.admin.store.service.StoreEnterAuthService;
import com.sicheng.admin.store.service.StoreEnterService;
import com.sicheng.admin.store.service.StoreIndustryService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
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
 * 主营行业 Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeIndustry")
public class StoreIndustryController extends BaseController {

    @Autowired
    private StoreIndustryService storeIndustryService;



    @Autowired
    private StoreEnterService storeEnterService;

    @Autowired
    private StoreEnterAuthService storeEnterAuthService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "050105";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param storeIndustry 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeIndustry:view")
    @RequestMapping(value = "list")
    public String list(StoreIndustry storeIndustry, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreIndustry> page = new Page<StoreIndustry>(request, response);
        page.setOrderBy("sort asc");
        page = storeIndustryService.selectByWhere(new Page<StoreIndustry>(request, response), new Wrapper(storeIndustry));
        model.addAttribute("page", page);
        return "admin/store/storeIndustryList";
    }

    /**
     * 进入保存页面
     *
     * @param storeIndustry 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeIndustry:save")
    @RequestMapping(value = "save1")
    public String save1(StoreIndustry storeIndustry, Model model) {
        if (storeIndustry == null) {
            storeIndustry = new StoreIndustry();
        }
        model.addAttribute("storeIndustry", storeIndustry);
        return "admin/store/storeIndustryForm";
    }

    /**
     * 执行保存
     *
     * @param storeIndustry      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeIndustry:save")
    @RequestMapping(value = "save2")
    public String save2(StoreIndustry storeIndustry, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeIndustry, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeIndustry, model);//回显错误提示
        }
        storeIndustry.setIsOpen(R.get("isOpen", "0"));
        //业务处理
        storeIndustryService.insertSelective(storeIndustry);
        addMessage(redirectAttributes, FYUtils.fyParams("保存主营行业成功"));
        return "redirect:" + adminPath + "/store/storeIndustry/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeIndustry 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeIndustry:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreIndustry storeIndustry, Model model) {
        StoreIndustry entity = null;
        if (storeIndustry != null) {
            if (storeIndustry.getId() != null) {
                entity = storeIndustryService.selectById(storeIndustry.getId());
            }
        }
        model.addAttribute("storeIndustry", entity);
        return "admin/store/storeIndustryForm";
    }

    /**
     * 执行编辑
     *
     * @param storeIndustry      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeIndustry:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreIndustry storeIndustry, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeIndustry, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeIndustry, model);//回显错误提示
        }
        storeIndustry.setIsOpen(R.get("isOpen", "0"));
        //业务处理
        storeIndustryService.updateByIdSelective(storeIndustry);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑主营行业成功"));
        return "redirect:" + adminPath + "/store/storeIndustry/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeIndustry      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeIndustry:drop")
    @RequestMapping(value = "delete")
    public String delete(StoreIndustry storeIndustry, RedirectAttributes redirectAttributes) {
        //查询入驻申请表里有没有店铺绑定这主营行业
        StoreEnter storeEnter = new StoreEnter();
        storeEnter.setIndustryId(storeIndustry.getIndustryId());
        List<StoreEnter> storeEnters = storeEnterService.selectByWhere(new Wrapper(storeEnter));
        //查询入驻申请审核表中有没有店铺绑定这主营行业
        StoreEnterAuth storeEnterAuth = new StoreEnterAuth();
        storeEnterAuth.setIndustryId(storeIndustry.getIndustryId());
        List<StoreEnterAuth> storeEnterAuths = storeEnterAuthService.selectByWhere(new Wrapper(storeEnterAuth));
        if (!storeEnters.isEmpty() || !storeEnterAuths.isEmpty()) {
            addMessage(redirectAttributes, FYUtils.fyParams("删除主营行业失败，该行业下有店铺，请移除该行业下的店铺"));
            return "redirect:" + adminPath + "/store/storeIndustry/list.do?repage";
        }
        storeIndustryService.deleteById(storeIndustry.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除主营行业成功"));
        return "redirect:" + adminPath + "/store/storeIndustry/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeIndustry 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreIndustry storeIndustry, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("industryName"))) {
            errorList.add(FYUtils.fyParams("主营行业名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("industryName")) && R.get("industryName").length() > 64) {
            errorList.add(FYUtils.fyParams("主营行业名称最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("industryMoney"))) {
            errorList.add(FYUtils.fyParams("保证金不能为空"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fyParams("排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("isOpen"))) {
            errorList.add(FYUtils.fyParams("请选择是否开启"));
        }
        return errorList;
    }

}