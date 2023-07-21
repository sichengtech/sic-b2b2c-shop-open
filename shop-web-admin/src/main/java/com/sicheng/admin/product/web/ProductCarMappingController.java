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
package com.sicheng.admin.product.web;

import com.sicheng.admin.product.entity.ProductCarMapping;
import com.sicheng.admin.product.service.ProductCarMappingService;

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
 * 商品与车系车型中间表 Controller
 * 所属模块：product
 *
 * @author cailong
 * @version 2017-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productCarMapping")
public class ProductCarMappingController extends BaseController {

    @Autowired
    private ProductCarMappingService productCarMappingService;



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
     * @param productCarMapping 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCarMapping:view")
    @RequestMapping(value = "list")
    public String list(ProductCarMapping productCarMapping, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductCarMapping> page = productCarMappingService.selectByWhere(new Page<ProductCarMapping>(request, response), new Wrapper(productCarMapping));
        model.addAttribute("page", page);
        return "admin/product/productCarMappingList";
    }

    /**
     * 进入保存页面
     *
     * @param productCarMapping 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCarMapping:save")
    @RequestMapping(value = "save1")
    public String save1(ProductCarMapping productCarMapping, Model model) {
        if (productCarMapping == null) {
            productCarMapping = new ProductCarMapping();
        }
        model.addAttribute("productCarMapping", productCarMapping);
        return "admin/product/productCarMappingForm";
    }

    /**
     * 执行保存
     *
     * @param productCarMapping  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCarMapping:save")
    @RequestMapping(value = "save2")
    public String save2(ProductCarMapping productCarMapping, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productCarMapping, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productCarMapping, model);//回显错误提示
        }

        //业务处理
        productCarMappingService.insertSelective(productCarMapping);
        addMessage(redirectAttributes, "保存商品与车系车型中间表成功");
        return "redirect:" + adminPath + "/product/productCarMapping/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productCarMapping 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCarMapping:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductCarMapping productCarMapping, Model model) {
        ProductCarMapping entity = null;
        if (productCarMapping != null) {
            if (productCarMapping.getId() != null) {
                entity = productCarMappingService.selectById(productCarMapping.getId());
            }
        }
        model.addAttribute("productCarMapping", entity);
        return "admin/product/productCarMappingForm";
    }

    /**
     * 执行编辑
     *
     * @param productCarMapping  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCarMapping:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductCarMapping productCarMapping, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productCarMapping, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productCarMapping, model);//回显错误提示
        }

        //业务处理
        productCarMappingService.updateByIdSelective(productCarMapping);
        addMessage(redirectAttributes, "编辑商品与车系车型中间表成功");
        return "redirect:" + adminPath + "/product/productCarMapping/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productCarMapping  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCarMapping:drop")
    @RequestMapping(value = "delete")
    public String delete(ProductCarMapping productCarMapping, RedirectAttributes redirectAttributes) {
        productCarMappingService.deleteById(productCarMapping.getId());
        addMessage(redirectAttributes, "删除商品与车系车型中间表成功");
        return "redirect:" + adminPath + "/product/productCarMapping/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productCarMapping 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductCarMapping productCarMapping, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("商品id最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("carIds")) && R.get("carIds").length() > 64) {
            errorList.add("商品选择车型的id最大长度不能超过64字符");
        }
        return errorList;
    }

}