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

import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.service.ProductSkuService;

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
 * 商品SKU Controller
 * 所属模块：product
 *
 * @author zhaolei
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productSku")
public class ProductSkuController extends BaseController {

    @Autowired
    private ProductSkuService productSkuService;



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
     * @param productSku 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSku:view")
    @RequestMapping(value = "list")
    public String list(ProductSku productSku, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductSku> page = productSkuService.selectByWhere(new Page<ProductSku>(request, response), new Wrapper(productSku));
        model.addAttribute("page", page);
        return "admin/product/productSkuList";
    }

    /**
     * 进入保存页面
     *
     * @param productSku 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSku:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductSku productSku, Model model) {
        if (productSku == null) {
            productSku = new ProductSku();
        }
        model.addAttribute("productSku", productSku);
        return "admin/product/productSkuForm";
    }

    /**
     * 执行保存
     *
     * @param productSku         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSku:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductSku productSku, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSku, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productSku, model);//回显错误提示
        }

        //业务处理
        productSkuService.insertSelective(productSku);
        addMessage(redirectAttributes, "保存商品SKU成功");
        return "redirect:" + adminPath + "/product/productSku/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productSku 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSku:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductSku productSku, Model model) {
        ProductSku entity = null;
        if (productSku != null) {
            if (productSku.getId() != null) {
                entity = productSkuService.selectById(productSku.getId());
            }
        }
        model.addAttribute("productSku", entity);
        return "admin/product/productSkuForm";
    }

    /**
     * 执行编辑
     *
     * @param productSku         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSku:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductSku productSku, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSku, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productSku, model);//回显错误提示
        }

        //业务处理
        productSkuService.updateByIdSelective(productSku);
        addMessage(redirectAttributes, "编辑商品SKU成功");
        return "redirect:" + adminPath + "/product/productSku/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productSku         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSku:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductSku productSku, RedirectAttributes redirectAttributes) {
        productSkuService.deleteById(productSku.getId());
        addMessage(redirectAttributes, "删除商品SKU成功");
        return "redirect:" + adminPath + "/product/productSku/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productSku 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductSku productSku, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("skuId"))) {
            errorList.add("SKU ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("skuId")) && R.get("skuId").length() > 19) {
            errorList.add("SKU ID最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("pId"))) {
            errorList.add("商品ID(SPU)不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("商品ID(SPU)最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("isNotSpec")) && R.get("isNotSpec").length() > 1) {
            errorList.add("是否无规格最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("spec1")) && R.get("spec1").length() > 64) {
            errorList.add("规格1最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("spec1V")) && R.get("spec1V").length() > 64) {
            errorList.add("规格1值最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("spec2")) && R.get("spec2").length() > 64) {
            errorList.add("规格2最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("spec2V")) && R.get("spec2V").length() > 64) {
            errorList.add("规格2值最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("spec3")) && R.get("spec3").length() > 64) {
            errorList.add("规格3最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("spec3V")) && R.get("spec3V").length() > 64) {
            errorList.add("规格3值最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("spec4")) && R.get("spec4").length() > 64) {
            errorList.add("规格4最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("spec4V")) && R.get("spec4V").length() > 64) {
            errorList.add("规格4值最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("price")) && R.get("price").length() > 12) {
            errorList.add("零售价格最大长度不能超过12字符");
        }
        if (StringUtils.isNotBlank(R.get("stock")) && R.get("stock").length() > 10) {
            errorList.add("库存最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("sn")) && R.get("sn").length() > 64) {
            errorList.add("商家编号最大长度不能超过64字符");
        }
        return errorList;
    }

}