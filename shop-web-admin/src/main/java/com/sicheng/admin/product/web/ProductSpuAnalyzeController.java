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

import com.sicheng.admin.product.entity.ProductSpuAnalyze;
import com.sicheng.admin.product.service.ProductSpuAnalyzeService;

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
 * 商品统计 Controller
 * 所属模块：product
 *
 * @author 张加利
 * @version 2017-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productSpuAnalyze")
public class ProductSpuAnalyzeController extends BaseController {

    @Autowired
    private ProductSpuAnalyzeService productSpuAnalyzeService;



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
     * @param productSpuAnalyze 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpuAnalyze:view")
    @RequestMapping(value = "list")
    public String list(ProductSpuAnalyze productSpuAnalyze, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductSpuAnalyze> page = productSpuAnalyzeService.selectByWhere(new Page<ProductSpuAnalyze>(request, response), new Wrapper(productSpuAnalyze));
        model.addAttribute("page", page);
        return "admin/product/productSpuAnalyzeList";
    }

    /**
     * 进入保存页面
     *
     * @param productSpuAnalyze 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpuAnalyze:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductSpuAnalyze productSpuAnalyze, Model model) {
        if (productSpuAnalyze == null) {
            productSpuAnalyze = new ProductSpuAnalyze();
        }
        model.addAttribute("productSpuAnalyze", productSpuAnalyze);
        return "admin/product/productSpuAnalyzeForm";
    }

    /**
     * 执行保存
     *
     * @param productSpuAnalyze  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpuAnalyze:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductSpuAnalyze productSpuAnalyze, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSpuAnalyze, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productSpuAnalyze, model);//回显错误提示
        }

        //业务处理
        productSpuAnalyzeService.insertSelective(productSpuAnalyze);
        addMessage(redirectAttributes, "保存商品统计成功");
        return "redirect:" + adminPath + "/product/productSpuAnalyze/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productSpuAnalyze 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpuAnalyze:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductSpuAnalyze productSpuAnalyze, Model model) {
        ProductSpuAnalyze entity = null;
        if (productSpuAnalyze != null) {
            if (productSpuAnalyze.getId() != null) {
                entity = productSpuAnalyzeService.selectById(productSpuAnalyze.getId());
            }
        }
        model.addAttribute("productSpuAnalyze", entity);
        return "admin/product/productSpuAnalyzeForm";
    }

    /**
     * 执行编辑
     *
     * @param productSpuAnalyze  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpuAnalyze:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductSpuAnalyze productSpuAnalyze, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSpuAnalyze, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productSpuAnalyze, model);//回显错误提示
        }

        //业务处理
        productSpuAnalyzeService.updateByIdSelective(productSpuAnalyze);
        addMessage(redirectAttributes, "编辑商品统计成功");
        return "redirect:" + adminPath + "/product/productSpuAnalyze/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productSpuAnalyze  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpuAnalyze:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductSpuAnalyze productSpuAnalyze, RedirectAttributes redirectAttributes) {
        productSpuAnalyzeService.deleteById(productSpuAnalyze.getId());
        addMessage(redirectAttributes, "删除商品统计成功");
        return "redirect:" + adminPath + "/product/productSpuAnalyze/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productSpuAnalyze 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductSpuAnalyze productSpuAnalyze, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("pId"))) {
            errorList.add("商品spu的id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("商品spu的id最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("allBrowse")) && R.get("allBrowse").length() > 10) {
            errorList.add("总浏览量最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("weekBrowse")) && R.get("weekBrowse").length() > 10) {
            errorList.add("周浏览量最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("monthBrowse")) && R.get("monthBrowse").length() > 10) {
            errorList.add("月浏览量最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("month3Browse")) && R.get("month3Browse").length() > 10) {
            errorList.add("三个月浏览量最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("allSales")) && R.get("allSales").length() > 10) {
            errorList.add("总销量最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("weekSales")) && R.get("weekSales").length() > 10) {
            errorList.add("周销量最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("monthSales")) && R.get("monthSales").length() > 10) {
            errorList.add("月销量最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("month3Sales")) && R.get("month3Sales").length() > 10) {
            errorList.add("三个月销量最大长度不能超过10字符");
        }
        return errorList;
    }

}