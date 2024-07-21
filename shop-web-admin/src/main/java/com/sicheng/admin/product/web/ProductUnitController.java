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

import com.sicheng.admin.product.entity.ProductUnit;
import com.sicheng.admin.product.service.ProductUnitService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.Pinyin4j;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
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
 * 管理商品的计量单位 Controller
 * 所属模块：product
 *
 * @author zjl
 * @version 2017-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productUnit")
public class ProductUnitController extends BaseController {

    @Autowired
    private ProductUnitService productUnitService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020122";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param productUnit 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productUnit:view")
    @RequestMapping(value = "list")
    public String list(ProductUnit productUnit, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductUnit> page = new Page<ProductUnit>(request, response);
        page.setOrderBy("sort");
        page = productUnitService.selectByWhere(page, new Wrapper(productUnit));
        model.addAttribute("page", page);
        return "admin/product/productUnitList";
    }

    /**
     * 进入保存页面
     *
     * @param productUnit 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productUnit:save")
    @RequestMapping(value = "save1")
    public String save1(ProductUnit productUnit, Model model) {
        if (productUnit == null) {
            productUnit = new ProductUnit();
        }
        model.addAttribute("productUnit", productUnit);
        return "admin/product/productUnitForm";
    }

    /**
     * 执行保存
     *
     * @param productUnit        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productUnit:save")
    @RequestMapping(value = "save2")
    public String save2(ProductUnit productUnit, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productUnit, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productUnit, model);//回显错误提示
        }
        //生成计量单位的首字母
        Pinyin4j pinyin4j = new Pinyin4j();
        try {
            String firstLetter = pinyin4j.toPinYin(productUnit.getName(), "", Pinyin4j.Type.UPPERCASE);
            if (StringUtils.isBlank(productUnit.getFirstLetter())) {
                productUnit.setFirstLetter(firstLetter);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error(FYUtils.fy("计量单位首字母大写转换出错"), e);
        }
        //业务处理
        productUnitService.insertSelective(productUnit);
        addMessage(redirectAttributes, FYUtils.fy("保存计量单位成功"));
        return "redirect:" + adminPath + "/product/productUnit/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productUnit 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productUnit:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductUnit productUnit, Model model) {
        ProductUnit entity = null;
        if (productUnit != null) {
            if (productUnit.getId() != null) {
                entity = productUnitService.selectById(productUnit.getId());
            }
        }
        model.addAttribute("productUnit", entity);
        return "admin/product/productUnitForm";
    }

    /**
     * 执行编辑
     *
     * @param productUnit        实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productUnit:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductUnit productUnit, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productUnit, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productUnit, model);//回显错误提示
        }
        //生成计量单位的首字母
        Pinyin4j pinyin4j = new Pinyin4j();
        try {
            String firstLetter = pinyin4j.toPinYin(productUnit.getName(), "", Pinyin4j.Type.UPPERCASE);
            if (StringUtils.isBlank(productUnit.getFirstLetter())) {
                productUnit.setFirstLetter(firstLetter);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error(FYUtils.fy("计量单位首字母大写转换出错"), e);
        }
        //业务处理
        productUnitService.updateByIdSelective(productUnit);
        addMessage(redirectAttributes, FYUtils.fy("编辑计量单位成功"));
        return "redirect:" + adminPath + "/product/productUnit/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productUnit        实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productUnit:drop")
    @RequestMapping(value = "delete")
    public String delete(ProductUnit productUnit, RedirectAttributes redirectAttributes) {
        productUnitService.deleteById(productUnit.getId());
        addMessage(redirectAttributes, FYUtils.fy("删除计量单位成功"));
        return "redirect:" + adminPath + "/product/productUnit/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productUnit 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductUnit productUnit, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fy("名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fy("名称最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fy("排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fy("排序最大长度不能超过10字符"));
        }
        return errorList;
    }

}