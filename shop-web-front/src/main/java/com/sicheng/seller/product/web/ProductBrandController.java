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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.product.entity.ProductBrand;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.sms.SmsSender;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.Pinyin4j;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.product.service.ProductBrandService;
import com.sicheng.seller.site.service.SiteMessageTemplateService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.sso.utils.SsoUtils;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <p>标题: ProductBrandController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年2月16日 上午10:13:39
 */
@Controller
@RequestMapping(value = "${sellerPath}/product/productBrand")
public class ProductBrandController extends BaseController {

    @Autowired
    private ProductBrandService productBrandService;

    @Autowired
    private SmsSender smsSender;
    @Autowired
    private SiteMessageTemplateService siteMessageTemplateService;
    @Autowired
    private SysVariableService variableService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020120";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 进去品牌列表页面
     *
     * @param productBrand
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productBrand:view")
    @RequestMapping(value = "list")
    public String list(ProductBrand productBrand, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        productBrand.setStoreId(userSeller.getStoreId());
        Page<ProductBrand> page = productBrandService.selectByWhere(new Page<ProductBrand>(request, response), new Wrapper(productBrand));
        model.addAttribute("page", page);
        return "seller/product/productBrandList";
    }

    /**
     * 进入保存页面
     *
     * @param productBrand 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productBrand:edit")
    @RequestMapping(value = "save1")
    public String save1(ProductBrand productBrand, Model model) {
        if (productBrand == null) {
            productBrand = new ProductBrand();
        }
        model.addAttribute("productBrand", productBrand);
        model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
        return "seller/product/productBrandForm";
    }

    /**
     * 执行保存
     *
     * @param productBrand       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productBrand:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductBrand productBrand, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productBrand, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productBrand, model);//回显错误提示
        }
        productBrand.setRecommend(R.get("recommend", "0"));
        Pinyin4j pinyin4j = new Pinyin4j();
        String firstLetter = null;
        try {
            firstLetter = pinyin4j.toPinYinUppercaseInitials(productBrand.getName());
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("转换商品分类拼音首字母出错", e);
        }
        productBrand.setFirstLetter(firstLetter);
        productBrand.setStatus("0");//审核状态，0待审、1通过、2未通过
        productBrand.setDisplayMode("2");//展示方式，1文字、2图片
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        productBrand.setStoreId(userSeller.getStoreId());
        //业务处理
        productBrandService.insertSelective(productBrand);
        addMessage(redirectAttributes, FYUtils.fyParams("申请品牌成功"));
        //品牌申请成功后，通知后台管理员审核
        sendMessage(productBrand);
        return "redirect:" + sellerPath + "/product/productBrand/list.htm?repage";
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
        //入参检查
        if (productBrand == null || productBrand.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("品牌不存在！"));
            return "error/400";
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        productBrand.setStoreId(userSeller.getStoreId());//属主检查
        ProductBrand entity = productBrandService.selectOne(new Wrapper(productBrand));
        //入参检查
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("品牌不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        model.addAttribute("productBrand", entity);
        model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
        return "seller/product/productBrandForm";
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
    public String edit2(ProductBrand productBrand, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //入参检查
        if (productBrand == null || productBrand.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("品牌不存在！"));
            return "error/400";
        }
        //表单验证
        List<String> errorList = validate(productBrand, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productBrand, model);//回显错误提示
        }
        productBrand.setRecommend(R.get("recommend", "0"));
        Pinyin4j pinyin4j = new Pinyin4j();
        String firstLetter = null;
        try {
            firstLetter = pinyin4j.toPinYinUppercaseInitials(productBrand.getName());
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("转换商品分类拼音首字母出错", e);
        }
        productBrand.setFirstLetter(firstLetter);
        productBrand.setStatus("0");//审核状态，0待审、1通过、2未通过
        productBrand.setDisplayMode("2");//展示方式，1文字、2图片
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //业务处理
        ProductBrand condition = new ProductBrand();
        condition.setId(productBrand.getId());//属主检查
        condition.setStoreId(userSeller.getStoreId());//属主检查
        productBrandService.updateByWhereSelective(productBrand, new Wrapper(condition));
        addMessage(redirectAttributes, FYUtils.fyParams("申请品牌成功"));
        //品牌申请成功后，通知后台管理员审核
        productBrand.setStoreId(userSeller.getStoreId());
        sendMessage(productBrand);
        return "redirect:" + sellerPath + "/product/productBrand/list.htm?repage";
    }

    /**
     *  品牌申请成功后，通知后台管理员审核
     *  @param productBrands 商品品牌信息
     */
    private void sendMessage(ProductBrand productBrand) {
        //获取系统变量的设置（后台管理员手机号）
        SysVariable phoneNums = variableService.getSysVariable("sys_phone_num");
        //获取短信内容
        Map<String, String> map = new HashMap<>();
        map.put("storeName", productBrand.getStore().getName());
        map.put("productName", productBrand.getName());
        String content = siteMessageTemplateService.getSmsContent(map, SiteSendMessagsService.PRODUCT_AUTH);
        //商家添加品牌后台审核,向后台管理员发送短信通知
        if (phoneNums != null && StringUtils.isNotBlank(phoneNums.getValueClob())) {
            String[] phones = phoneNums.getValueClob().split(",");
            if (phones.length > 0) {
                for (int i = 0; i < phones.length; i++) {
                    smsSender.sendSmsMessage(phones[i], content, map, SiteSendMessagsService.PRODUCT_AUTH, true);
                    logger.info("接收短信手机号：", phones[i]);
                    logger.info("发送短信内容：", content);
                }
            }
        }
    }

    /**
     * 删除
     *
     * @param productBrand       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productBrand:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductBrand productBrand, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        //入参检查
        if (productBrand == null || productBrand.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("品牌不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        productBrand.setStoreId(userSeller.getStoreId());
        productBrandService.deleteByWhere(new Wrapper(productBrand));//属主检查
        addMessage(redirectAttributes, FYUtils.fyParams("删除品牌成功"));
        return "redirect:" + sellerPath + "/product/productBrand/list.htm?repage";
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
            errorList.add(FYUtils.fyParams("品牌名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("品牌名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("firstLetter")) && R.get("firstLetter").length() > 64) {
            errorList.add(FYUtils.fyParams("首页字母最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("logo"))) {
            errorList.add(FYUtils.fyParams("请上传品牌logo"));
        }
        if (StringUtils.isNotBlank(R.get("logo")) && R.get("logo").length() > 128) {
            errorList.add(FYUtils.fyParams("品牌logo最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("displayMode")) && R.get("displayMode").length() > 1) {
            errorList.add(FYUtils.fyParams("展示方式最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("recommend")) && R.get("recommend").length() > 1) {
            errorList.add(FYUtils.fyParams("推荐最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add(FYUtils.fyParams("类型最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 1) {
            errorList.add(FYUtils.fyParams("审核状态最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("url")) && R.get("url").length() > 255) {
            errorList.add(FYUtils.fyParams("网址最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("cause")) && R.get("cause").length() > 512) {
            errorList.add(FYUtils.fyParams("审核未通过原因最大长度不能超过512字符"));
        }
        if (StringUtils.isBlank(R.get("applyPathP1"))) {
            errorList.add(FYUtils.fyParams("请上传商标注册证图1"));
        }
        if (StringUtils.isNotBlank(R.get("applyPathP1")) && R.get("applyPathP1").length() > 64) {
            errorList.add(FYUtils.fyParams("商标注册证图1最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("applyPathP2")) && R.get("applyPathP2").length() > 64) {
            errorList.add(FYUtils.fyParams("商标注册证图2最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("brandOwner"))) {
            errorList.add(FYUtils.fyParams("请填写品牌所有者"));
        }
        if (StringUtils.isNotBlank(R.get("brandOwner")) && R.get("brandOwner").length() > 64) {
            errorList.add(FYUtils.fyParams("品牌所有者最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add(FYUtils.fyParams("谁申请的品牌最大长度不能超过19字符"));
        }
        return errorList;
    }

}
