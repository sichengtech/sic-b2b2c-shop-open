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
package com.sicheng.admin.product.web;

import com.sicheng.admin.product.entity.ProductParamMapping;
import com.sicheng.admin.product.service.ProductParamMappingService;

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
 * 商品与参数中间表 Controller
 * 所属模块：product
 *
 * @author zhaolei
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productParamMapping")
public class ProductParamMappingController extends BaseController {

    @Autowired
    private ProductParamMappingService productParamMappingService;



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
     * @param productParamMapping 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productParamMapping:view")
    @RequestMapping(value = "list")
    public String list(ProductParamMapping productParamMapping, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductParamMapping> page = productParamMappingService.selectByWhere(new Page<ProductParamMapping>(request, response), new Wrapper(productParamMapping));
        model.addAttribute("page", page);
        return "admin/product/productParamMappingList";
    }

    /**
     * 进入保存页面
     *
     * @param productParamMapping 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productParamMapping:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductParamMapping productParamMapping, Model model) {
        if (productParamMapping == null) {
            productParamMapping = new ProductParamMapping();
        }
        model.addAttribute("productParamMapping", productParamMapping);
        return "admin/product/productParamMappingForm";
    }

    /**
     * 执行保存
     *
     * @param productParamMapping 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productParamMapping:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductParamMapping productParamMapping, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productParamMapping, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productParamMapping, model);//回显错误提示
        }

        //业务处理
        productParamMappingService.insertSelective(productParamMapping);
        addMessage(redirectAttributes, "保存商品与参数中间表成功");
        return "redirect:" + adminPath + "/product/productParamMapping/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productParamMapping 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productParamMapping:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductParamMapping productParamMapping, Model model) {
        ProductParamMapping entity = null;
        if (productParamMapping != null) {
            if (productParamMapping.getId() != null) {
                entity = productParamMappingService.selectById(productParamMapping.getId());
            }
        }
        model.addAttribute("productParamMapping", entity);
        return "admin/product/productParamMappingForm";
    }

    /**
     * 执行编辑
     *
     * @param productParamMapping 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productParamMapping:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductParamMapping productParamMapping, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productParamMapping, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productParamMapping, model);//回显错误提示
        }

        //业务处理
        productParamMappingService.updateByIdSelective(productParamMapping);
        addMessage(redirectAttributes, "编辑商品与参数中间表成功");
        return "redirect:" + adminPath + "/product/productParamMapping/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productParamMapping 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productParamMapping:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductParamMapping productParamMapping, RedirectAttributes redirectAttributes) {
        productParamMappingService.deleteById(productParamMapping.getId());
        addMessage(redirectAttributes, "删除商品与参数中间表成功");
        return "redirect:" + adminPath + "/product/productParamMapping/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productParamMapping 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductParamMapping productParamMapping, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("pId"))) {
            errorList.add("商品ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("商品ID最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("参数类型最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("format")) && R.get("format").length() > 1) {
            errorList.add("格式最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("paramId"))) {
            errorList.add("参数ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("paramId")) && R.get("paramId").length() > 19) {
            errorList.add("参数ID最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("参数名不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("参数名最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("value"))) {
            errorList.add("参数值不能为空");
        }
        if (StringUtils.isNotBlank(R.get("value")) && R.get("value").length() > 1024) {
            errorList.add("参数值最大长度不能超过1024字符");
        }
        if (StringUtils.isNotBlank(R.get("valueImg")) && R.get("valueImg").length() > 1024) {
            errorList.add("参数值的图片最大长度不能超过1024字符");
        }
        return errorList;
    }

}