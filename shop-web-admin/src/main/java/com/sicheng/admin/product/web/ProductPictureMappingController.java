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

import com.sicheng.admin.product.entity.ProductPictureMapping;
import com.sicheng.admin.product.service.ProductPictureMappingService;

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
 * 商品图片多对多中间表 Controller
 * 所属模块：product
 *
 * @author zhaolei
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productPictureMapping")
public class ProductPictureMappingController extends BaseController {

    @Autowired
    private ProductPictureMappingService productPictureMappingService;



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
     * @param productPictureMapping 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productPictureMapping:view")
    @RequestMapping(value = "list")
    public String list(ProductPictureMapping productPictureMapping, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductPictureMapping> page = productPictureMappingService.selectByWhere(new Page<ProductPictureMapping>(request, response), new Wrapper(productPictureMapping));
        model.addAttribute("page", page);
        return "admin/product/productPictureMappingList";
    }

    /**
     * 进入保存页面
     *
     * @param productPictureMapping 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productPictureMapping:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductPictureMapping productPictureMapping, Model model) {
        if (productPictureMapping == null) {
            productPictureMapping = new ProductPictureMapping();
        }
        model.addAttribute("productPictureMapping", productPictureMapping);
        return "admin/product/productPictureMappingForm";
    }

    /**
     * 执行保存
     *
     * @param productPictureMapping 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productPictureMapping:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductPictureMapping productPictureMapping, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productPictureMapping, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productPictureMapping, model);//回显错误提示
        }

        //业务处理
        productPictureMappingService.insertSelective(productPictureMapping);
        addMessage(redirectAttributes, "保存商品图片多对多中间表成功");
        return "redirect:" + adminPath + "/product/productPictureMapping/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productPictureMapping 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productPictureMapping:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductPictureMapping productPictureMapping, Model model) {
        ProductPictureMapping entity = null;
        if (productPictureMapping != null) {
            if (productPictureMapping.getId() != null) {
                entity = productPictureMappingService.selectById(productPictureMapping.getId());
            }
        }
        model.addAttribute("productPictureMapping", entity);
        return "admin/product/productPictureMappingForm";
    }

    /**
     * 执行编辑
     *
     * @param productPictureMapping 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productPictureMapping:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductPictureMapping productPictureMapping, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productPictureMapping, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productPictureMapping, model);//回显错误提示
        }

        //业务处理
        productPictureMappingService.updateByIdSelective(productPictureMapping);
        addMessage(redirectAttributes, "编辑商品图片多对多中间表成功");
        return "redirect:" + adminPath + "/product/productPictureMapping/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productPictureMapping 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productPictureMapping:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductPictureMapping productPictureMapping, RedirectAttributes redirectAttributes) {
        productPictureMappingService.deleteById(productPictureMapping.getId());
        addMessage(redirectAttributes, "删除商品图片多对多中间表成功");
        return "redirect:" + adminPath + "/product/productPictureMapping/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productPictureMapping 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductPictureMapping productPictureMapping, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("pId"))) {
            errorList.add("商品ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("商品ID最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("color")) && R.get("color").length() > 64) {
            errorList.add("颜色最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("imgId"))) {
            errorList.add("图片ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("imgId")) && R.get("imgId").length() > 19) {
            errorList.add("图片ID最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add("排序不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        return errorList;
    }

}