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

import com.sicheng.admin.product.entity.ProductSectionPrice;
import com.sicheng.admin.product.service.ProductSectionPriceService;

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
 * 商品区间价 Controller
 * 所属模块：product
 *
 * @author zhaolei
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productSectionPrice")
public class ProductSectionPriceController extends BaseController {

    @Autowired
    private ProductSectionPriceService productSectionPriceService;



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
     * @param productSectionPrice 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSectionPrice:view")
    @RequestMapping(value = "list")
    public String list(ProductSectionPrice productSectionPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductSectionPrice> page = productSectionPriceService.selectByWhere(new Page<ProductSectionPrice>(request, response), new Wrapper(productSectionPrice));
        model.addAttribute("page", page);
        return "admin/product/productSectionPriceList";
    }

    /**
     * 进入保存页面
     *
     * @param productSectionPrice 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSectionPrice:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductSectionPrice productSectionPrice, Model model) {
        if (productSectionPrice == null) {
            productSectionPrice = new ProductSectionPrice();
        }
        model.addAttribute("productSectionPrice", productSectionPrice);
        return "admin/product/productSectionPriceForm";
    }

    /**
     * 执行保存
     *
     * @param productSectionPrice 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSectionPrice:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductSectionPrice productSectionPrice, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSectionPrice, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productSectionPrice, model);//回显错误提示
        }

        //业务处理
        productSectionPriceService.insertSelective(productSectionPrice);
        addMessage(redirectAttributes, "保存商品区间价成功");
        return "redirect:" + adminPath + "/product/productSectionPrice/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productSectionPrice 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSectionPrice:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductSectionPrice productSectionPrice, Model model) {
        ProductSectionPrice entity = null;
        if (productSectionPrice != null) {
            if (productSectionPrice.getId() != null) {
                entity = productSectionPriceService.selectById(productSectionPrice.getId());
            }
        }
        model.addAttribute("productSectionPrice", entity);
        return "admin/product/productSectionPriceForm";
    }

    /**
     * 执行编辑
     *
     * @param productSectionPrice 实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSectionPrice:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductSectionPrice productSectionPrice, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSectionPrice, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productSectionPrice, model);//回显错误提示
        }

        //业务处理
        productSectionPriceService.updateByIdSelective(productSectionPrice);
        addMessage(redirectAttributes, "编辑商品区间价成功");
        return "redirect:" + adminPath + "/product/productSectionPrice/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productSectionPrice 实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSectionPrice:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductSectionPrice productSectionPrice, RedirectAttributes redirectAttributes) {
        productSectionPriceService.deleteById(productSectionPrice.getId());
        addMessage(redirectAttributes, "删除商品区间价成功");
        return "redirect:" + adminPath + "/product/productSectionPrice/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productSectionPrice 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductSectionPrice productSectionPrice, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("pId"))) {
            errorList.add("商品ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("商品ID最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("section")) && R.get("section").length() > 64) {
            errorList.add("价格区间最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("price")) && R.get("price").length() > 12) {
            errorList.add("批发价格最大长度不能超过12字符");
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