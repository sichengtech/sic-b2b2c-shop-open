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

import com.sicheng.admin.product.entity.ProductCategoryAdimg;
import com.sicheng.admin.product.service.ProductCategoryAdimgService;

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
 * 分类导航广告图 Controller
 * 所属模块：product
 *
 * @author zhaolei
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productCategoryAdimg")
public class ProductCategoryAdimgController extends BaseController {

    @Autowired
    private ProductCategoryAdimgService productCategoryAdimgService;



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
     * @param productCategoryAdimg 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCategoryAdimg:view")
    @RequestMapping(value = "list")
    public String list(ProductCategoryAdimg productCategoryAdimg, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductCategoryAdimg> page = productCategoryAdimgService.selectByWhere(new Page<ProductCategoryAdimg>(request, response), new Wrapper(productCategoryAdimg));
        model.addAttribute("page", page);
        return "admin/product/productCategoryAdimgList";
    }

    /**
     * 进入保存页面
     *
     * @param productCategoryAdimg 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCategoryAdimg:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductCategoryAdimg productCategoryAdimg, Model model) {
        if (productCategoryAdimg == null) {
            productCategoryAdimg = new ProductCategoryAdimg();
        }
        model.addAttribute("productCategoryAdimg", productCategoryAdimg);
        return "admin/product/productCategoryAdimgForm";
    }

    /**
     * 执行保存
     *
     * @param productCategoryAdimg 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCategoryAdimg:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductCategoryAdimg productCategoryAdimg, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productCategoryAdimg, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productCategoryAdimg, model);//回显错误提示
        }

        //业务处理
        productCategoryAdimgService.insertSelective(productCategoryAdimg);
        addMessage(redirectAttributes, "保存分类导航广告图成功");
        return "redirect:" + adminPath + "/product/productCategoryAdimg/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productCategoryAdimg 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCategoryAdimg:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductCategoryAdimg productCategoryAdimg, Model model) {
        ProductCategoryAdimg entity = null;
        if (productCategoryAdimg != null) {
            if (productCategoryAdimg.getId() != null) {
                entity = productCategoryAdimgService.selectById(productCategoryAdimg.getId());
            }
        }
        model.addAttribute("productCategoryAdimg", entity);
        return "admin/product/productCategoryAdimgForm";
    }

    /**
     * 执行编辑
     *
     * @param productCategoryAdimg 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCategoryAdimg:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductCategoryAdimg productCategoryAdimg, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productCategoryAdimg, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productCategoryAdimg, model);//回显错误提示
        }

        //业务处理
        productCategoryAdimgService.updateByIdSelective(productCategoryAdimg);
        addMessage(redirectAttributes, "编辑分类导航广告图成功");
        return "redirect:" + adminPath + "/product/productCategoryAdimg/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productCategoryAdimg 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCategoryAdimg:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductCategoryAdimg productCategoryAdimg, RedirectAttributes redirectAttributes) {
        productCategoryAdimgService.deleteById(productCategoryAdimg.getId());
        addMessage(redirectAttributes, "删除分类导航广告图成功");
        return "redirect:" + adminPath + "/product/productCategoryAdimg/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productCategoryAdimg 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductCategoryAdimg productCategoryAdimg, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("navImgId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("navImgId")) && R.get("navImgId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("categoryId"))) {
            errorList.add("分类ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("categoryId")) && R.get("categoryId").length() > 19) {
            errorList.add("分类ID最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("imgPath"))) {
            errorList.add("图片不能为空");
        }
        if (StringUtils.isNotBlank(R.get("imgPath")) && R.get("imgPath").length() > 64) {
            errorList.add("图片最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("originalSize"))) {
            errorList.add("原图尺寸不能为空");
        }
        if (StringUtils.isNotBlank(R.get("originalSize")) && R.get("originalSize").length() > 64) {
            errorList.add("原图尺寸最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add("排序不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("action")) && R.get("action").length() > 10) {
            errorList.add("动作最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("url")) && R.get("url").length() > 255) {
            errorList.add("目标最大长度不能超过255字符");
        }
        if (StringUtils.isBlank(R.get("target"))) {
            errorList.add("窗口打开方式不能为空");
        }
        if (StringUtils.isNotBlank(R.get("target")) && R.get("target").length() > 64) {
            errorList.add("窗口打开方式最大长度不能超过64字符");
        }
        return errorList;
    }

}