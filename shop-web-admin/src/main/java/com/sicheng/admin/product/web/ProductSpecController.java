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

import com.sicheng.admin.product.entity.ProductSpec;
import com.sicheng.admin.product.service.ProductSpecService;

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
 * 规格和规格值 Controller
 * 所属模块：product
 *
 * @author 赵磊
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productSpec")
public class ProductSpecController extends BaseController {

    @Autowired
    private ProductSpecService productSpecService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020135";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param productSpec 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpec:view")
    @RequestMapping(value = "list")
    public String list(ProductSpec productSpec, HttpServletRequest request, HttpServletResponse response, Model model) {
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(productSpec);
        wrapper.orderBy("a.spec_sort asc");
        Page<ProductSpec> page = productSpecService.selectByWhere(new Page<ProductSpec>(request, response), wrapper);
        model.addAttribute("page", page);
        return "admin/product/productSpecList";
    }

    /**
     * 进入保存页面
     *
     * @param productSpec 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpec:save")
    @RequestMapping(value = "save1")
    public String save1(ProductSpec productSpec, Model model) {
        if (productSpec == null) {
            productSpec = new ProductSpec();
        }
        model.addAttribute("productSpec", productSpec);
        return "admin/product/productSpecForm";
    }

    /**
     * 执行保存
     *
     * @param productSpec        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpec:save")
    @RequestMapping(value = "save2")
    public String save2(ProductSpec productSpec, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSpec, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productSpec, model);//回显错误提示
        }
        productSpec.setIsColor(R.get("isColor", "0"));
        productSpec.setCategoryId(-1L);
        //业务处理
        productSpecService.insertSelective(productSpec);
        addMessage(redirectAttributes, FYUtils.fy("保存规格和规格值成功"));
        return "redirect:" + adminPath + "/product/productSpec/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productSpec 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpec:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductSpec productSpec, Model model) {
        ProductSpec entity = null;
        if (productSpec != null) {
            if (productSpec.getId() != null) {
                entity = productSpecService.selectById(productSpec.getId());
            }
        }
        model.addAttribute("productSpec", entity);
        return "admin/product/productSpecForm";
    }

    /**
     * 执行编辑
     *
     * @param productSpec        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpec:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductSpec productSpec, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSpec, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productSpec, model);//回显错误提示
        }
        productSpec.setIsColor(R.get("isColor", "0"));
        //业务处理
        productSpecService.updateByIdSelective(productSpec);
        addMessage(redirectAttributes, FYUtils.fy("编辑规格和规格值成功"));
        return "redirect:" + adminPath + "/product/productSpec/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productSpec        实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpec:drop")
    @RequestMapping(value = "delete")
    public String delete(ProductSpec productSpec, RedirectAttributes redirectAttributes) {
        productSpecService.deleteById(productSpec.getId());
        addMessage(redirectAttributes, FYUtils.fy("删除规格和规格值成功"));
        return "redirect:" + adminPath + "/product/productSpec/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productSpec 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductSpec productSpec, Model model) {
        String specId = R.get("specId");
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("specSort"))) {
            errorList.add(FYUtils.fy("排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("specSort")) && R.get("specSort").length() > 10) {
            errorList.add(FYUtils.fy("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("isColor"))) {
            errorList.add(FYUtils.fy("请选择是否是颜色"));
        }
        if (StringUtils.isNotBlank(R.get("isColor")) && R.get("isColor").length() > 1) {
            errorList.add(FYUtils.fy("是否是颜色最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fy("规格名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fy("规格名最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("specValues"))) {
            errorList.add(FYUtils.fy("规格值不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("specValues")) && R.get("specValues").length() > 1024) {
            errorList.add(FYUtils.fy("规格值最大长度不能超过1024字符"));
        }
        String[] specValuesArray = R.get("specValues").split(",");
        if (specValuesArray.length > 10) {
            errorList.add(FYUtils.fy("规格值最多只能填写10个"));
        }
        int count = productSpecService.countByWhere(new Wrapper());
        if (StringUtils.isBlank(specId)) {
            //新增
            if (count >= 3) {
                errorList.add(FYUtils.fy("商品规格最多只能填3个"));
            }
        } else {
            //编辑
            if (count > 3) {
                errorList.add(FYUtils.fy("商品规格最多只能填3个"));
            }
        }
        return errorList;
    }

}