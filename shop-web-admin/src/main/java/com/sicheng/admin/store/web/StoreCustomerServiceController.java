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

import com.sicheng.admin.store.entity.StoreCustomerService;
import com.sicheng.admin.store.service.StoreCustomerServiceService;

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
 * 管理店铺的客服 Controller
 * 所属模块：store
 *
 * @author 张加利
 * @version 2017-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeCustomerService")
public class StoreCustomerServiceController extends BaseController {

    @Autowired
    private StoreCustomerServiceService storeCustomerServiceService;



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
     * @param storeCustomerService 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeCustomerService:view")
    @RequestMapping(value = "list")
    public String list(StoreCustomerService storeCustomerService, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreCustomerService> page = storeCustomerServiceService.selectByWhere(new Page<StoreCustomerService>(request, response), new Wrapper(storeCustomerService));
        model.addAttribute("page", page);
        return "admin/store/storeCustomerServiceList";
    }

    /**
     * 进入保存页面
     *
     * @param storeCustomerService 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeCustomerService:edit")
    @RequestMapping(value = "save1")
    public String save1(StoreCustomerService storeCustomerService, Model model) {
        if (storeCustomerService == null) {
            storeCustomerService = new StoreCustomerService();
        }
        model.addAttribute("storeCustomerService", storeCustomerService);
        return "admin/store/storeCustomerServiceForm";
    }

    /**
     * 执行保存
     *
     * @param storeCustomerService 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeCustomerService:edit")
    @RequestMapping(value = "save2")
    public String save2(StoreCustomerService storeCustomerService, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeCustomerService, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeCustomerService, model);//回显错误提示
        }

        //业务处理
        storeCustomerServiceService.insertSelective(storeCustomerService);
        addMessage(redirectAttributes, "保存店铺客服表成功");
        return "redirect:" + adminPath + "/store/storeCustomerService/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeCustomerService 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeCustomerService:edit")
    @RequestMapping(value = "edit1")
    public String edit1(StoreCustomerService storeCustomerService, Model model) {
        StoreCustomerService entity = null;
        if (storeCustomerService != null) {
            if (storeCustomerService.getId() != null) {
                entity = storeCustomerServiceService.selectById(storeCustomerService.getId());
            }
        }
        model.addAttribute("storeCustomerService", entity);
        return "admin/store/storeCustomerServiceForm";
    }

    /**
     * 执行编辑
     *
     * @param storeCustomerService 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeCustomerService:edit")
    @RequestMapping(value = "edit2")
    public String edit2(StoreCustomerService storeCustomerService, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeCustomerService, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeCustomerService, model);//回显错误提示
        }

        //业务处理
        storeCustomerServiceService.updateByIdSelective(storeCustomerService);
        addMessage(redirectAttributes, "编辑店铺客服表成功");
        return "redirect:" + adminPath + "/store/storeCustomerService/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeCustomerService 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeCustomerService:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreCustomerService storeCustomerService, RedirectAttributes redirectAttributes) {
        storeCustomerServiceService.deleteById(storeCustomerService.getId());
        addMessage(redirectAttributes, "删除店铺客服表成功");
        return "redirect:" + adminPath + "/store/storeCustomerService/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeCustomerService 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreCustomerService storeCustomerService, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("scsId"))) {
            errorList.add("客服id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("scsId")) && R.get("scsId").length() > 19) {
            errorList.add("客服id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("storeId"))) {
            errorList.add("店铺id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 19) {
            errorList.add("店铺id最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("客服名称最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("tool")) && R.get("tool").length() > 1) {
            errorList.add("客服工具（1.QQ，2.站内IM，3.旺旺）最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("account")) && R.get("account").length() > 64) {
            errorList.add("客服账号最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("类型（1.售前服务，2.售后服务）最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        return errorList;
    }

}