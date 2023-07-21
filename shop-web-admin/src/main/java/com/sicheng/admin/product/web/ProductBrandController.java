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

import com.sicheng.admin.product.entity.ProductBrand;
import com.sicheng.admin.product.service.ProductBrandService;

import com.sicheng.common.fileStorage.AccessKey;
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
 * 品牌 Controller
 * 所属模块：product
 *
 * @author 蔡龙
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productBrand")
public class ProductBrandController extends BaseController {

    @Autowired
    private ProductBrandService productBrandService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020121";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param productBrand 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productBrand:view")
    @RequestMapping(value = "list")
    public String list(ProductBrand productBrand, HttpServletRequest request, HttpServletResponse response, Model model) {
        //三级菜单高亮
        String status = R.get("status");
        if ("1".equals(status)) {
            //品牌管理
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            super.menuHighLighting("020121");
            model.addAttribute("status", status);//审核状态发送到页面上  审核状态，0待审、1通过、2未通过
        }
        if ("0".equals(status) || "2".equals(status)) {
            //品牌审核
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            super.menuHighLighting("020123");
            model.addAttribute("status", status);//审核状态发送到页面上  审核状态，0待审、1通过、2未通过
        }
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(productBrand);
        wrapper.orderBy("a.create_date DESC");
        Page<ProductBrand> page = productBrandService.selectByWhere(new Page<ProductBrand>(request, response), wrapper);
        model.addAttribute("page", page);
        return "admin/product/productBrandList";
    }

    /**
     * 进入保存页面
     *
     * @param productBrand 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productBrand:save")
    @RequestMapping(value = "save1")
    public String save1(ProductBrand productBrand, Model model) {
        if (productBrand == null) {
            productBrand = new ProductBrand();
        }
        model.addAttribute("productBrand", productBrand);
        model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
        return "admin/product/productBrandForm";
    }

    /**
     * 执行保存
     *
     * @param productBrand       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productBrand:save")
    @RequestMapping(value = "save2")
    public String save2(ProductBrand productBrand, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productBrand, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productBrand, model);//回显错误提示
        }
        productBrand.setRecommend(R.get("recommend", "0"));//推荐，0未推荐，1推荐
        productBrand.setDisplayMode(R.get("displayMode", "2"));//展示方式，1文字、2图片
        Pinyin4j pinyin4j = new Pinyin4j();
        String firstLetter = null;
        try {
            firstLetter = pinyin4j.toPinYinUppercaseInitials(productBrand.getName());
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error(FYUtils.fy("转换商品品牌拼音首字母出错"), e);
        }
        productBrand.setFirstLetter(firstLetter);
        //业务处理
        productBrandService.insertSelective(productBrand);
        addMessage(redirectAttributes, FYUtils.fy("保存品牌成功"));
        return "redirect:" + adminPath + "/product/productBrand/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productBrand 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productBrand:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductBrand productBrand, Model model) {
        ProductBrand entity = null;
        if (productBrand != null) {
            if (productBrand.getId() != null) {
                entity = productBrandService.selectById(productBrand.getId());
            }
        }
        model.addAttribute("productBrand", entity);
        model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
        return "admin/product/productBrandForm";
    }

    /**
     * 执行编辑
     *
     * @param productBrand       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productBrand:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductBrand productBrand, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productBrand, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productBrand, model);//回显错误提示
        }
        productBrand.setRecommend(R.get("recommend", "0"));//推荐，0未推荐，1推荐
        productBrand.setDisplayMode(R.get("displayMode", "2"));//展示方式，1文字、2图片
        Pinyin4j pinyin4j = new Pinyin4j();
        String firstLetter = null;
        try {
            firstLetter = pinyin4j.toPinYinUppercaseInitials(productBrand.getName());
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error(FYUtils.fy("转换商品品牌拼音首字母出错"), e);
        }
        productBrand.setFirstLetter(firstLetter);
        //业务处理
        productBrandService.updateByIdSelective(productBrand);
        addMessage(redirectAttributes, FYUtils.fy("编辑品牌成功"));
        return "redirect:" + adminPath + "/product/productBrand/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productBrand       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productBrand:drop")
    @RequestMapping(value = "delete")
    public String delete(ProductBrand productBrand, RedirectAttributes redirectAttributes) {
        productBrandService.deleteById(productBrand.getId());
        addMessage(redirectAttributes, FYUtils.fy("删除品牌成功"));
        return "redirect:" + adminPath + "/product/productBrand/list.do?repage";
    }

    /**
     * 审核品牌
     *
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productBrand:auth")
    @RequestMapping(value = "auth")
    public String auth(RedirectAttributes redirectAttributes) {
        Long brandId = R.getLong("brandId");//品牌ID
        Boolean auth = R.getBoolean("auth");//true:审核通过，false：审核不通
        String cause = R.get("cause");//审核不通原因

        if (brandId == null || auth == null) {
            addMessage(redirectAttributes, FYUtils.fy("审核品牌失败,缺少参数"));
        } else {
            if (StringUtils.isBlank(cause)) {
                if (auth) {
                    cause = FYUtils.fy("审核通过");
                } else {
                    cause = FYUtils.fy("审核未通过");
                }
            }
            ProductBrand productBrand = new ProductBrand();
            productBrand.setBrandId(brandId);
            productBrand.setStatus(auth ? "1" : "2");//审核状态，0待审、1通过、2未通过
            productBrand.setCause(cause);
            int rs = productBrandService.updateByIdSelective(productBrand);
            if (rs == 1) {
                if (auth) {
                    addMessage(redirectAttributes, FYUtils.fy("审核通过"));
                } else {
                    addMessage(redirectAttributes, FYUtils.fy("审核未通过"));
                }
            } else {
                addMessage(redirectAttributes, FYUtils.fy("审核品牌失败")+",brandId=" + brandId + FYUtils.fy("的品牌不存在"));
            }
        }
        return "redirect:" + adminPath + "/product/productBrand/list.do?repage";
    }

    /**
     * 批量审核品牌
     *
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productBrand:auth")
    @RequestMapping(value = "batchAuth")
    public String batchAuth(RedirectAttributes redirectAttributes) {
        String brandIds = R.get("brandIds");//多个品牌ID
        String auth = R.get("auth");//审核状态 1 通过2不通过
        if (StringUtils.isBlank(brandIds) || StringUtils.isBlank(auth)) {
            addMessage(redirectAttributes, FYUtils.fy("审核品牌失败,缺少参数"));
            return "redirect:" + adminPath + "/product/productBrand/list.do?repage";
        }
        String[] brandIdss = brandIds.split(",");//多个品牌ID
        List<ProductBrand> productBrandList = new ArrayList<ProductBrand>();
        for (int i = 0; i < brandIdss.length; i++) {
            ProductBrand productBrand = new ProductBrand();
            productBrand.setBrandId(Long.parseLong(brandIdss[i]));
            productBrand.setStatus("1".equals(auth) ? "1" : "2");//审核状态，0待审、1通过、2未通过
            productBrandList.add(productBrand);
        }
        productBrandService.updateSelectiveBatch(productBrandList);
        String info = "1".equals(auth) ? FYUtils.fy("批量审核通过成功") : FYUtils.fy("批量审核不通过成功");
        addMessage(redirectAttributes, info);
        return "redirect:" + adminPath + "/product/productBrand/list.do?repage";
    }


    /**
     * 表单验证
     *
     * @param productBrand 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductBrand productBrand, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fy("品牌名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fy("品牌名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("firstLetter")) && R.get("firstLetter").length() > 64) {
            errorList.add(FYUtils.fy("首页字母最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("logo"))) {
            errorList.add(FYUtils.fy("请上传品牌")+"logo");
        }
        if (StringUtils.isNotBlank(R.get("logo")) && R.get("logo").length() > 128) {
            errorList.add(FYUtils.fy("品牌logo最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("displayMode")) && R.get("displayMode").length() > 1) {
            errorList.add(FYUtils.fy("展示方式最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("recommend"))) {
            errorList.add(FYUtils.fy("请选择推荐"));
        }
        if (StringUtils.isNotBlank(R.get("recommend")) && R.get("recommend").length() > 1) {
            errorList.add(FYUtils.fy("推荐最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add(FYUtils.fy("类型最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fy("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add(FYUtils.fy("审核状态最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("url")) && R.get("url").length() > 255) {
            errorList.add(FYUtils.fy("网址最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("cause")) && R.get("cause").length() > 512) {
            errorList.add(FYUtils.fy("审核未通过原因最大长度不能超过512字符"));
        }
        if (StringUtils.isNotBlank(R.get("applyPathP1")) && R.get("applyPathP1").length() > 64) {
            errorList.add(FYUtils.fy("商标注册证图1最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("applyPathP2")) && R.get("applyPathP2").length() > 64) {
            errorList.add(FYUtils.fy("商标注册证图2最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("brandOwner")) && R.get("brandOwner").length() > 64) {
            errorList.add(FYUtils.fy("品牌所有者最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add(FYUtils.fy("商家id最大长度不能超过19字符"));
        }
        return errorList;
    }

}