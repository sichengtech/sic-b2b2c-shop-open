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

import com.sicheng.admin.product.entity.ProductParam;
import com.sicheng.admin.product.service.ProductParamService;

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
 * 参数和参数值 Controller
 * 所属模块：product
 *
 * @author 赵磊
 * @version 2017-01-22
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productParam")
public class ProductParamController extends BaseController {

    @Autowired
    private ProductParamService productParamService;



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
     * @param productParam 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productParam:view")
    @RequestMapping(value = "list")
    public String list(ProductParam productParam, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductParam> page = productParamService.selectByWhere(new Page<ProductParam>(request, response), new Wrapper(productParam));
        model.addAttribute("page", page);
        return "admin/product/productParamList";
    }

    /**
     * 进入保存页面
     *
     * @param productParam 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productParam:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductParam productParam, Model model) {
        if (productParam == null) {
            productParam = new ProductParam();
        }
        model.addAttribute("productParam", productParam);
        return "admin/product/productParamForm";
    }

    /**
     * 执行保存
     *
     * @param productParam       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productParam:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductParam productParam, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productParam, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productParam, model);//回显错误提示
        }

        //业务处理
        productParamService.insertSelective(productParam);
        addMessage(redirectAttributes, "保存参数和参数值成功");
        return "redirect:" + adminPath + "/product/productParam/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productParam 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productParam:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductParam productParam, Model model) {
        ProductParam entity = null;
        if (productParam != null) {
            if (productParam.getId() != null) {
                entity = productParamService.selectById(productParam.getId());
            }
        }
        model.addAttribute("productParam", entity);
        return "admin/product/productParamForm";
    }

    /**
     * 执行编辑
     *
     * @param productParam       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productParam:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductParam productParam, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productParam, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productParam, model);//回显错误提示
        }

        //业务处理
        productParamService.updateByIdSelective(productParam);
        addMessage(redirectAttributes, "编辑参数和参数值成功");
        return "redirect:" + adminPath + "/product/productParam/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productParam       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productParam:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductParam productParam, RedirectAttributes redirectAttributes) {
        productParamService.deleteById(productParam.getId());
        addMessage(redirectAttributes, "删除参数和参数值成功");
        return "redirect:" + adminPath + "/product/productParam/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productParam 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductParam productParam, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("paramId"))) {
            errorList.add("ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("paramId")) && R.get("paramId").length() > 19) {
            errorList.add("ID最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("categoryId"))) {
            errorList.add("分类ID不能为空");
        }
        if (StringUtils.isNotBlank(R.get("categoryId")) && R.get("categoryId").length() > 19) {
            errorList.add("分类ID最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("paramSort")) && R.get("paramSort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("参数名不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("参数名最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("paramValues"))) {
            errorList.add("参数值文字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("paramValues")) && R.get("paramValues").length() > 1024) {
            errorList.add("参数值文字最大长度不能超过1024字符");
        }
        if (StringUtils.isNotBlank(R.get("valuesImg")) && R.get("valuesImg").length() > 1024) {
            errorList.add("参数值图片最大长度不能超过1024字符");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("参数类型最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("format")) && R.get("format").length() > 1) {
            errorList.add("格式最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("isDisplay"))) {
            errorList.add("是否显示，0否1是不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isDisplay")) && R.get("isDisplay").length() > 1) {
            errorList.add("是否显示，0否1是最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("isRequired"))) {
            errorList.add("是否必填，0否1是，商家发布商品的时候必填项必须填写不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isRequired")) && R.get("isRequired").length() > 1) {
            errorList.add("是否必填，0否1是，商家发布商品的时候必填项必须填写最大长度不能超过1字符");
        }
        return errorList;
    }

}