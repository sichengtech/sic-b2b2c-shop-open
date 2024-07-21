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
package com.sicheng.seller.product.web;

import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.common.web.BaseController;
import com.sicheng.seller.product.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 商品分类Controller (在seller中展示分类)
 *
 * @author zhaolei
 * @version 2017-03-12
 */
@Controller
@RequestMapping(value = "${sellerPath}/product/productCategory")
public class ProductCategoryController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ModelAttribute
    public void get(Model model) {

    }

    @RequestMapping(value = "categorySearch.js.htm")
    public String form(Model model) {
        //TODO 商品分类应从从缓存中取
        List<ProductCategory> cateList = productCategoryService.selectAll(null);
        model.addAttribute("cateList", cateList);
        return "seller/product/categorySearch";
    }
}