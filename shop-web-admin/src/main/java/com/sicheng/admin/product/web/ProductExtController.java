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

import com.sicheng.admin.product.entity.ProductExt;
import com.sicheng.admin.product.service.ProductExtService;

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
 * 商品扩展表 Controller
 * 所属模块：product
 *
 * @author 蔡龙
 * @version 2017-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productExt")
public class ProductExtController extends BaseController {

    @Autowired
    private ProductExtService productExtService;



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
     * @param productExt 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productExt:view")
    @RequestMapping(value = "list")
    public String list(ProductExt productExt, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductExt> page = productExtService.selectByWhere(new Page<ProductExt>(request, response), new Wrapper(productExt));
        model.addAttribute("page", page);
        return "admin/product/productExtList";
    }

    /**
     * 进入保存页面
     *
     * @param productExt 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productExt:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductExt productExt, Model model) {
        if (productExt == null) {
            productExt = new ProductExt();
        }
        model.addAttribute("productExt", productExt);
        return "admin/product/productExtForm";
    }

    /**
     * 执行保存
     *
     * @param productExt         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productExt:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductExt productExt, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productExt, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productExt, model);//回显错误提示
        }

        //业务处理
        productExtService.insertSelective(productExt);
        addMessage(redirectAttributes, "保存商品扩展表成功");
        return "redirect:" + adminPath + "/product/productExt/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productExt 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productExt:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductExt productExt, Model model) {
        ProductExt entity = null;
        if (productExt != null) {
            if (productExt.getId() != null) {
                entity = productExtService.selectById(productExt.getId());
            }
        }
        model.addAttribute("productExt", entity);
        return "admin/product/productExtForm";
    }

    /**
     * 执行编辑
     *
     * @param productExt         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productExt:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductExt productExt, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productExt, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productExt, model);//回显错误提示
        }

        //业务处理
        productExtService.updateByIdSelective(productExt);
        addMessage(redirectAttributes, "编辑商品扩展表成功");
        return "redirect:" + adminPath + "/product/productExt/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productExt         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productExt:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductExt productExt, RedirectAttributes redirectAttributes) {
        productExtService.deleteById(productExt.getId());
        addMessage(redirectAttributes, "删除商品扩展表成功");
        return "redirect:" + adminPath + "/product/productExt/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productExt 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductExt productExt, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("pId"))) {
            errorList.add("商品ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("商品ID最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("carIds"))) {
            errorList.add("所有父级id+多个product_car表第3级&rdquo;型号&ldquo;的ID多个值用逗号隔开不能为空");
        }
        if (StringUtils.isNotBlank(R.get("carIds")) && R.get("carIds").length() > 4000) {
            errorList.add("所有父级id+多个product_car表第3级&rdquo;型号&ldquo;的ID多个值用逗号隔开最大长度不能超过4000字符");
        }
        if (StringUtils.isNotBlank(R.get("n1")) && R.get("n1").length() > 10) {
            errorList.add("备用字段n1最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("n2")) && R.get("n2").length() > 10) {
            errorList.add("备用字段n2最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("n3")) && R.get("n3").length() > 10) {
            errorList.add("备用字段n3最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("f1")) && R.get("f1").length() > 12) {
            errorList.add("备用字段f1最大长度不能超过12字符");
        }
        if (StringUtils.isNotBlank(R.get("f2")) && R.get("f2").length() > 12) {
            errorList.add("备用字段f2最大长度不能超过12字符");
        }
        if (StringUtils.isNotBlank(R.get("f3")) && R.get("f3").length() > 12) {
            errorList.add("备用字段f3最大长度不能超过12字符");
        }
        return errorList;
    }

}