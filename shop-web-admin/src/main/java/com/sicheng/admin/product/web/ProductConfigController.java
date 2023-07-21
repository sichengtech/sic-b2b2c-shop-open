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

import com.sicheng.admin.product.entity.ProductConfig;
import com.sicheng.admin.product.service.ProductConfigService;

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
 * 商品设置 Controller
 * 所属模块：product
 *
 * @author 赵磊
 * @version 2017-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productConfig")
public class ProductConfigController extends BaseController {

    @Autowired
    private ProductConfigService productConfigService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020112";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param productConfig 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productConfig:view")
    @RequestMapping(value = "list")
    public String list(ProductConfig productConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductConfig> page = productConfigService.selectByWhere(new Page<ProductConfig>(request, response), new Wrapper(productConfig));
        model.addAttribute("page", page);
        return "admin/product/productConfigList";
    }

    /**
     * 进入保存页面
     *
     * @param productConfig 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productConfig:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductConfig productConfig, Model model) {
        if (productConfig == null) {
            productConfig = new ProductConfig();
        }
        model.addAttribute("productConfig", productConfig);
        return "admin/product/productConfigForm";
    }

    /**
     * 执行保存
     *
     * @param productConfig      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productConfig:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductConfig productConfig, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productConfig, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productConfig, model);//回显错误提示
        }

        //业务处理
        productConfigService.insertSelective(productConfig);
        addMessage(redirectAttributes, "保存商品设置成功");
        return "redirect:" + adminPath + "/product/productConfig/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productConfig 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productConfig:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductConfig productConfig, Model model) {
        ProductConfig entity = null;
        //按ID查
        if (productConfig != null) {
            if (productConfig.getId() != null) {
                entity = productConfigService.selectById(productConfig.getId());
            }
        }
        if (entity == null) {
            //从库中查出ID最小的一个
            Page<ProductConfig> page = new Page<ProductConfig>();
            page.setOrderBy("id asc");//按ID排序
            ProductConfig condition = new ProductConfig();
            page = productConfigService.selectByWhere(page, new Wrapper(condition));
            if (page.getList().size() > 0) {
                entity = page.getList().get(0);//取ID最小的一个
            }
        }

        if (entity == null) {
            //向表中插入一条记录
            entity = new ProductConfig();
            entity.setSet1("0");//设置1，新发的商品是否需要审核，0否、1是
            productConfigService.insertSelective(entity);
        }
        model.addAttribute("productConfig", entity);
        return "admin/product/productConfigForm";
    }

    /**
     * 执行编辑
     *
     * @param productConfig      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productConfig:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductConfig productConfig, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productConfig, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productConfig, model);//回显错误提示
        }

        //业务处理
        productConfigService.updateByIdSelective(productConfig);
        addMessage(redirectAttributes, FYUtils.fy("编辑商品设置成功"));
        return "redirect:" + adminPath + "/product/productConfig/edit1.do";
    }

    /**
     * 删除
     *
     * @param productConfig      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productConfig:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductConfig productConfig, RedirectAttributes redirectAttributes) {
        productConfigService.deleteById(productConfig.getId());
        addMessage(redirectAttributes, "删除商品设置成功");
        return "redirect:" + adminPath + "/product/productConfig/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param productConfig 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductConfig productConfig, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("set1"))) {
            errorList.add("请选择是否审核");
        }
        if (StringUtils.isNotBlank(R.get("set1")) && R.get("set1").length() > 64) {
            errorList.add("是否需要审核最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("set2")) && R.get("set2").length() > 64) {
            errorList.add("设置2最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("set3")) && R.get("set3").length() > 64) {
            errorList.add("设置3最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("set4")) && R.get("set4").length() > 64) {
            errorList.add("设置4最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("set5")) && R.get("set5").length() > 64) {
            errorList.add("设置5最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("set6")) && R.get("set6").length() > 64) {
            errorList.add("设置6最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("set7")) && R.get("set7").length() > 64) {
            errorList.add("设置7最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("set8")) && R.get("set8").length() > 64) {
            errorList.add("设置8最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("set9")) && R.get("set9").length() > 64) {
            errorList.add("设置9最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("set10")) && R.get("set10").length() > 64) {
            errorList.add("设置10最大长度不能超过64字符");
        }
        return errorList;
    }

}