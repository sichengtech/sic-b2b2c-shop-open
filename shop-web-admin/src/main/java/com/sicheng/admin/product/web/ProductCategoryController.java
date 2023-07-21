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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sicheng.admin.product.entity.*;
import com.sicheng.admin.product.service.ProductBrandService;
import com.sicheng.admin.product.service.ProductCategoryService;
import com.sicheng.admin.product.service.ProductParamService;
import com.sicheng.admin.product.service.ProductSpuService;
import com.sicheng.admin.site.entity.SiteRecommend;
import com.sicheng.admin.site.entity.SiteRecommendProductCategory;
import com.sicheng.admin.site.service.SiteRecommendProductCategoryService;
import com.sicheng.admin.site.service.SiteRecommendService;

import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.FileUtils;
import com.sicheng.common.utils.Pinyin4j;
import com.sicheng.common.utils.Pinyin4j.Type;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类Controller
 *
 * @author 赵磊
 * @version 2017-01-16
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productCategory")
public class ProductCategoryController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;



    @Autowired
    private ProductParamService productParamService;

    @Autowired
    private ProductSpuService productSpuService;

    @Autowired
    private ProductBrandService productBrandService;

    @Autowired
    private SiteRecommendService siteRecommendService;

    @Autowired
    private SiteRecommendProductCategoryService siteRecommendProductCategoryService;

    @ModelAttribute
    public void get(Model model) {
        String menu3 = "020115";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param productCategory
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCategory:view")
    @RequestMapping(value = "list")
    public String list(ProductCategory productCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
        String name = R.get("name");
        Wrapper wrapper = new Wrapper();
        if (StringUtils.isNotBlank(name)) {
            wrapper.and("a.name like", "%" + name + "%");
        } else {
            if (productCategory.getParent() == null || productCategory.getParent().getCategoryId() == null) {
                ProductCategory category = new ProductCategory();
                category.setCategoryId(0L);
                productCategory.setParent(category);
            }
            productCategory.setName(null);
            wrapper.orderBy("a.sort asc");
            wrapper.setEntity(productCategory);
        }
        List<ProductCategory> list = productCategoryService.selectByWhere(wrapper);
        //在列表上显示参数名
        if (!list.isEmpty()) {
            List<Object> categoryIds = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                categoryIds.add(list.get(i).getCategoryId());
            }
            if (!categoryIds.isEmpty()) {
                List<ProductParam> productParams = productParamService.selectByCategoryIdIn(categoryIds);
                if (!productParams.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        StringBuilder sbl = new StringBuilder();
                        ProductCategory productCategory2 = list.get(i);
                        Long categoryId = productCategory2.getCategoryId();
                        for (int j = 0; j < productParams.size(); j++) {
                            Long cateId = productParams.get(j).getCategoryId();
                            if (categoryId.equals(cateId)) {
                                sbl.append(productParams.get(j).getName());
                                sbl.append(",");
                            }
                        }
                        if (sbl.length() > 1) {
                            productCategory2.setParamValues(sbl.substring(0, sbl.length() - 1));
                        }
                    }
                }
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("name", name);
        return "admin/product/productCategoryList";
    }

    /**
     * 进入编辑和新增页面
     *
     * @param productCategory
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCategory:edit")
    @RequestMapping(value = "form")
    public String form(ProductCategory productCategory, Model model) {
        ProductCategory entity = null;
        if (productCategory.getCategoryId() != null) {
            entity = productCategoryService.get(productCategory.getCategoryId());
        }
        if (entity == null) {
            entity = productCategory;
        }
        if (entity.getParent() != null && entity.getParent().getCategoryId() != null) {
            entity.setParent(productCategoryService.get(entity.getParent().getCategoryId()));
            // 获取排序号，最末节点排序号+30
            if (entity.getParent() != null && entity.getParent().getCategoryId() != null) {
                if (entity.getCategoryId() == null) {
                    ProductCategory productCategoryChild = new ProductCategory();
                    productCategoryChild.setParent(new ProductCategory(entity.getParent().getCategoryId()));
                    List<ProductCategory> list = productCategoryService.findList(entity);
                    if (list.size() > 0) {
                        entity.setSort(list.get(list.size() - 1).getSort());
                        if (entity.getSort() != null) {
                            entity.setSort(entity.getSort() + 30);
                        }
                    }
                }
            }
        }
        if (entity.getSort() == null) {
            entity.setSort(30);
        }
        model.addAttribute("productCategory", entity);
        return "admin/product/productCategoryForm";
    }

    /**
     * 保存
     * @param productCategory
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCategory:edit")
    @RequestMapping(value = "save")
    public String save(ProductCategory productCategory, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productCategory, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(productCategory, model);//回显错误提示
        }
        ProductSpu productSpu = new ProductSpu();
        productSpu.setCategoryId(productCategory.getParentId());
        List<ProductSpu> productSpus = productSpuService.selectByWhere(new Wrapper(productSpu));
        if (!productSpus.isEmpty()) {
            addMessage(redirectAttributes, FYUtils.fy("新增或编辑失败，目标分类有商品不能新增或编辑"));
            return "redirect:" + Global.getAdminPath() + "/product/productCategory/list.do?repage";
        } else {
            if (StringUtils.isNotBlank(R.get("name")) && StringUtils.isBlank(R.get("firstLetter"))) {
                Pinyin4j pinyin4j = new Pinyin4j();
                try {
                    productCategory.setFirstLetter(pinyin4j.toPinYinUppercaseInitials(R.get("name")));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error(FYUtils.fy("转换商品分类拼音首字母出错"), e);
                }
            }
            productCategory.setIsLocked(R.get("isLocked", "0"));
            productCategoryService.save(productCategory);
            addMessage(redirectAttributes, FYUtils.fy("新增或编辑商品分类成功"));
            return "redirect:" + Global.getAdminPath() + "/product/productCategory/list.do?repage";
        }
    }

    /**
     * 删除
     *
     * @param productCategory
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCategory:drop")
    @RequestMapping(value = "delete")
    public String delete(ProductCategory productCategory, RedirectAttributes redirectAttributes) {
        List<ProductCategory> productCategories = productCategoryService.findChildNodeAll(productCategory.getCategoryId());
        if (productCategories.isEmpty()) {
            //终极节点
            ProductSpu productSpu = new ProductSpu();
            productSpu.setCategoryId(productCategory.getCategoryId());
            List<ProductSpu> productSpus = productSpuService.selectByWhere(new Wrapper(productSpu));
            if (!productSpus.isEmpty()) {
                addMessage(redirectAttributes, FYUtils.fy("因本分类下有商品，不能删除分类"));
                return "redirect:" + Global.getAdminPath() + "/product/productCategory/list.do?repage";
            }
        } else {
            //不是终极节点
            for (int i = 0; i < productCategories.size(); i++) {
                ProductSpu productSpu = new ProductSpu();
                productSpu.setCategoryId(productCategories.get(i).getCategoryId());
                List<ProductSpu> productSpus = productSpuService.selectByWhere(new Wrapper(productSpu));
                if (!productSpus.isEmpty()) {
                    addMessage(redirectAttributes, FYUtils.fy("因本分类的下级分类下有商品，不能删除分类"));
                    return "redirect:" + Global.getAdminPath() + "/product/productCategory/list.do?repage";
                }
            }
        }
        productCategoryService.delete(productCategory.getCategoryId());
        addMessage(redirectAttributes, FYUtils.fy("删除商品分类成功"));
        return "redirect:" + Global.getAdminPath() + "/product/productCategory/list.do?repage";
    }

    /**
     * 商品分类
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     * @param extId 排除的ID
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<ProductCategory> list = productCategoryService.findList(new ProductCategory());
        for (int i = 0; i < list.size(); i++) {
            ProductCategory e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 进入商品分类品牌绑定表
     *
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCategory:edit")
    @RequestMapping(value = "bindBrand1")
    public String brandlist(Model model) {
        Long categoryId = R.getLong("categoryId");
        //把分类中绑定的品牌发到jsp页面进行选中回显
        ProductCategory productCategory = productCategoryService.get(categoryId);
        //获取已选择店铺品牌
        List<Long> brandIds1 = new ArrayList<Long>();
        String brand_ids = productCategory.getBrandIds();
        if (StringUtils.isNoneBlank(brand_ids)) {
            String[] arr = brand_ids.split(",");
            for (int i = 0; i < arr.length; i++) {
                brandIds1.add(Long.parseLong(arr[i]));
            }
            //brandIds1 = Arrays.asList(arr);
        }
        //获取所有店铺品牌
        ProductBrand productBrand = new ProductBrand();
        productBrand.setStatus("1");//审核状态，0待审、1通过、2未通过
        List<ProductBrand> productBrandList = productBrandService.selectByWhere(new Wrapper(productBrand));
        List<Long> brandIds3 = new ArrayList<Long>();
        if (!productBrandList.isEmpty()) {
            for (int i = 0; i < productBrandList.size(); i++) {
                brandIds3.add(productBrandList.get(i).getBrandId());
            }
        }
        brandIds3.removeAll(brandIds1);
        List<ProductBrand> selectedProductBrand = new ArrayList<ProductBrand>();
        List<ProductBrand> unselectedProductBrand = new ArrayList<ProductBrand>();
        if (brandIds1.size() != 0) {
            //已选择
            for (int i = 0; i < brandIds1.size(); i++) {
                for (int j = 0; j < productBrandList.size(); j++) {
                    if (brandIds1.get(i).equals(productBrandList.get(j).getBrandId())) {
                        selectedProductBrand.add(productBrandList.get(j));
                    }
                }
            }
        }
        for (int i = 0; i < brandIds3.size(); i++) {
            for (int j = 0; j < productBrandList.size(); j++) {
                if (brandIds3.get(i).equals(productBrandList.get(j).getBrandId())) {
                    unselectedProductBrand.add(productBrandList.get(j));
                }
            }
        }
        model.addAttribute("selectedProductBrand", selectedProductBrand);
        model.addAttribute("unselectedProductBrand", unselectedProductBrand);
//		return "admin/product/productCategoryBrandList";
        return "admin/product/productBindBrand";
    }

    /**
     * 保存店铺品牌
     */
    @RequiresPermissions("product:productCategory:edit")
    @ResponseBody
    @RequestMapping(value = "bindBrand2")
    public Map<String, String> bindBrand2(Long categoryId, String brandIds) {
        Map<String, String> map = new HashMap<String, String>();
        if (categoryId == null || StringUtils.isBlank(brandIds)) {
            map.put("status", "1");
            map.put("content", FYUtils.fy("绑定失败：商品分类或品牌为空"));
            return map;
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(categoryId);
        productCategory.setBrandIds(brandIds);
        productCategoryService.updateByIdSelective(productCategory);
        map.put("status", "0");
        map.put("content", FYUtils.fy("绑定成功"));
        return map;
    }

    /**
     * 进入商品分类绑定推荐位页
     *
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCategory:edit")
    @RequestMapping(value = "categoryAndRecommend1")
    public String categoryAndRecommend1(Model model) {
        Long categoryId = R.getLong("categoryId");
        //查询全部推荐位
        List<SiteRecommend> siteRecommends = siteRecommendService.selectAll(new Wrapper());
        //查询当前商品分类绑定的推荐位的id
        Wrapper wrapper = new Wrapper();
        wrapper.and("category_id = ", categoryId);
        List<SiteRecommendProductCategory> siteRecommendProductCategories = siteRecommendProductCategoryService.selectByWhere(wrapper);
        //找出与当前商品绑定的推荐位
        List<SiteRecommend> bindList = new ArrayList<>();
        if (siteRecommendProductCategories != null && siteRecommendProductCategories.size() > 0) {
            for (SiteRecommend siteRecommend : siteRecommends) {
                for (SiteRecommendProductCategory siteRecommendProductCategory : siteRecommendProductCategories) {
                    if (siteRecommendProductCategory.getRecommendId().equals(siteRecommend.getRecommendId())) {
                        bindList.add(siteRecommend);
                        break;
                    }
                }
            }
            for (SiteRecommend siteRecommend : bindList) {
                siteRecommends.remove(siteRecommend);
            }
        }
        model.addAttribute("siteRecommends", siteRecommends);
        model.addAttribute("bindList", bindList);
        return "admin/product/productCategoryAndRecommend";
    }

    /**
     * 保存商品分类与推荐位
     */
    @RequiresPermissions("product:productCategory:edit")
    @ResponseBody
    @RequestMapping(value = "categoryAndRecommend2")
    public Map<String, String> categoryAndRecommend2(Long categoryId, String brandIds) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String[] split = brandIds.split(",");
            if (split.length > 0) {
                //查询当前商品分类绑定的推荐位的id
                siteRecommendProductCategoryService.bind(categoryId, split);
            }
            map.put("status", "0");
            map.put("content", FYUtils.fy("商品分类绑定推荐位成功"));
        } catch (Exception e) {
            logger.error(FYUtils.fy("商品分类绑定推荐位异常，异常信息："), e);
            map.put("status", "-1");
            map.put("content", FYUtils.fy("商品分类绑定推荐位失败"));
        }
        return map;
    }

    /**
     * 绑定佣金
     *
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCategory:edit")
    @RequestMapping(value = "commission")
    public String commission(Model model, RedirectAttributes redirectAttributes) {
        String synchro = R.get("synchro");
        Long categoryId = R.getLong("categoryId");
        String c = R.get("commission");
        if (categoryId == null) {
            addMessage(redirectAttributes, FYUtils.fy("分配佣金失败，缺少参数"));
        } else {
            if (StringUtils.isBlank(c)) {
                addMessage(redirectAttributes, FYUtils.fy("分配佣金失败，佣金比例为空"));
            } else {
                BigDecimal commission = new BigDecimal(c);
                ProductCategory productCategory = new ProductCategory();
                productCategory.setCategoryId(categoryId);
                productCategory.setCommission(commission);
                if ("1".equals(synchro)) {
                    //同步到子分类节点
                    List<ProductCategory> productCategories = productCategoryService.findChildNodeAll(categoryId);
                    if (!productCategories.isEmpty()) {
                        for (int i = 0; i < productCategories.size(); i++) {
                            ProductCategory productCategory1 = new ProductCategory();
                            productCategory1.setCategoryId(productCategories.get(i).getCategoryId());
                            productCategory1.setCommission(commission);
                            productCategoryService.updateByIdSelective(productCategory1);
                        }
                    }
                    productCategoryService.updateByIdSelective(productCategory);
                } else {
                    //只修改单个分类节点
                    productCategoryService.updateByIdSelective(productCategory);
                }
                addMessage(redirectAttributes, FYUtils.fy("分配佣金成功"));
            }
        }
        return "redirect:" + adminPath + "/product/productCategory/list.do?repage";
    }

    /**
     * 进入商品分类参数页
     *
     * @param productParam 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCategory:edit")
    @RequestMapping(value = "catelist")
    public String catelist(ProductParam productParam, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductParam> page = new Page<ProductParam>(request, response);
        page.setOrderBy("param_sort");
        page = productParamService.selectByWhere(page, new Wrapper(productParam));
        model.addAttribute("page", page);
        model.addAttribute("categoryId", productParam.getCategoryId());
        String menu3 = "020115";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        return "admin/product/productParamListForm";
    }

    /**
     * 保存商品参数
     *
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCategory:edit")
    @RequestMapping(value = "saveCatelist")
    public String saveCatelist(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) {
        Long categoryId = R.getLong("categoryId");
        String[] paramId = R.getArray("paramId");
        String[] name = R.getArray("name");
        String[] paramValues = R.getArray("paramValues");
        String[] paramSort = R.getArray("paramSort");
        String[] isRequired = R.getArray("isRequired");
        String[] isDisplay = R.getArray("isDisplay");
        if (name.length == 0) {
            //判断是否添加了分类，没有分类代表为空
            ProductParam param = new ProductParam();
            param.setCategoryId(categoryId);
            productParamService.deleteByWhere(new Wrapper(param));
            addMessage(redirectAttributes, FYUtils.fy("保存商品分类参数成功"));
        }
        if (name.length != paramId.length || name.length != paramSort.length ||
                name.length != isRequired.length || name.length != isDisplay.length) {
            //判断接受的数组参数都是同样的长度
            addMessage(model, FYUtils.fy("商品分类参数缺失"));
            ProductParam productParam = new ProductParam();
            productParam.setCategoryId(categoryId);
            return catelist(productParam, request, response, model);//回显错误提示
        }
        if (!(name.length != paramId.length || name.length != paramSort.length ||
                name.length != isRequired.length || name.length != isDisplay.length)) {
            //判断这些参数有没有大于数据库最大长度的
            List<String> errorList = new ArrayList<String>();
            //判断参数名
            for (int i = 0; i < name.length; i++) {
                boolean a = false;
                boolean b = false;
                if (StringUtils.isBlank(name[i])) {
                    errorList.add(FYUtils.fy("参数名不能为空"));
                    a = true;
                }
                if (StringUtils.isNotBlank(name[i]) && name[i].length() > 64) {
                    errorList.add(FYUtils.fy("参数名不能大于64字符"));
                    b = true;
                }
                if (a == true || b == true) {
                    break;
                }
            }
            //判断排序
            for (int i = 0; i < paramSort.length; i++) {
                boolean a = false;
                boolean b = false;
                if (StringUtils.isBlank(paramSort[i])) {
                    errorList.add(FYUtils.fy("排序不能为空"));
                    a = true;
                }
                if (StringUtils.isNotBlank(paramSort[i]) && paramSort[i].length() > 10) {
                    errorList.add(FYUtils.fy("排序不能大于10字符"));
                    b = true;
                }
                if (a == true || b == true) {
                    break;
                }
            }
            if (errorList.size() > 0) {
                errorList.add(0, FYUtils.fy("数据验证失败："));
                ProductParam productParam = new ProductParam();
                productParam.setCategoryId(categoryId);
                addMessage(model, errorList.toArray(new String[]{}));
                return catelist(productParam, request, response, model);//回显错误提示
            } else {
                ProductParamVo productParamVo = new ProductParamVo();
                productParamVo.setName(name);
                productParamVo.setCategoryId(categoryId);
                productParamVo.setParamId(paramId);
                productParamVo.setParamValues(paramValues);
                productParamVo.setParamSort(paramSort);
                productParamVo.setIsRequired(isRequired);
                productParamVo.setIsDisplay(isDisplay);
                productParamService.saveProductParam(productParamVo);
                addMessage(redirectAttributes, FYUtils.fy("保存商品分类参数成功"));
            }
        }
        return "redirect:" + adminPath + "/product/productCategory/list.do?repage";
    }

    /**
     * 进入导入商品分类页
     */
    @RequiresPermissions("product:productCategory:import")
    @RequestMapping(value = "importExcel")
    public String importExcel(Model model) {
        String fail = R.get("fail");//导入失败条数
        String success = R.get("success");//导入成功条数
        model.addAttribute("fail", fail);
        model.addAttribute("success", success);
        return "admin/product/productCategoryImport";
    }

    /**
     * 执行导入商品分类
     *
     * @param excel              上传商品分类的excel文件
     * @param ststus             1.初始导入，2.清空商品分类,再导入
     * @param model
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @RequiresPermissions("product:productCategory:import")
    @RequestMapping(value = "saveExcel")
    public String saveExcel(@RequestParam("excel") MultipartFile excel, String ststus,
                            Model model, RedirectAttributes redirectAttributes) {
        if ("2".equals(ststus)) {//清空商品分类,再导入
            Wrapper w = new Wrapper();
            w.and("1=", "1");
            //productCategoryService.deleteByWhere(w);
        }
        if (excel == null) {
            model.addAttribute("message", FYUtils.fy("请选择文件"));
            return "admin/product/productCategoryImport";
        }
        if (excel.getSize() > 5242880) {
            model.addAttribute("message", FYUtils.fy("上传文件超过了5M"));
            return "admin/product/productCategoryImport";
        }
        if (!FileUtils.isExcel(excel.getOriginalFilename())) {
            model.addAttribute("message", FYUtils.fy("文件格式不正确"));
            return "admin/product/productCategoryImport";
        }

        // 判断是不是03版本的excel
        boolean is03Excel = excel.getOriginalFilename().matches("^.+\\.(?i)(xls)$");
        // 读取工作薄
        Workbook workbook = null;
        try {
            InputStream inputStream = excel.getInputStream();
            workbook = is03Excel ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            logger.error("e1：", e);
        } catch (IOException e) {
            logger.error("e1：", e);
        }

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet.getPhysicalNumberOfRows() <= 2) {
            model.addAttribute("message", FYUtils.fy("Excel格式与模板格式不一致！"));
            return "admin/product/productCategoryImport";
        }
        if (sheet.getPhysicalNumberOfRows() > 20000) {
            model.addAttribute("message", FYUtils.fy("导入的商品分类Excel超过2万行！"));
            return "admin/product/productCategoryImport";
        }
        //将Excel的分类数据导入数据库中
        Pinyin4j pinyin4j = new Pinyin4j();
        int fail = 0;//导入失败行数
        int success = 0;//导入成功行数
        Map<String, ProductCategory> map1 = new HashMap<String, ProductCategory>();//1级分类
        Map<String, Map<String, ProductCategory>> map2 = new HashMap<String, Map<String, ProductCategory>>();//2级分类(键为上级名字)
        for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            // 读取单元格
            Row row = sheet.getRow(i);
            int colNum = row.getPhysicalNumberOfCells();
            int j = 0;
            while (j < colNum) {
                if (j >= 0 && j <= 2) {
                    ProductCategory cate = new ProductCategory();
                    ProductCategory pcate = new ProductCategory();
                    String name = getCellFormatValue(row.getCell((short) j)).trim();
                    if (StringUtils.isNotBlank(name)) {
                        cate.setName(name);
                        cate.setBak7("1000");
                        try {
                            cate.setFirstLetter(pinyin4j.toPinYin(cate.getName(), "", Type.UPPERCASE));
                        } catch (BadHanyuPinyinOutputFormatCombination e) {
                            logger.error(FYUtils.fy("转换商品分类拼音首字母出错"), e);
                        }
                        if (j == 0) {
                            //一级分类
                            ProductCategory c = map1.get(name);//获取当前name在0级是否存在
                            if (c == null) {
                                pcate.setId(0L);
                                cate.setParent(pcate);
                                cate.setParentIds("0,");
                                productCategoryService.insertSelective(cate);
                                map1.put(name, cate);
                                success++;
                            }
                        }
                        if (j == 1) {
                            //二级分类
                            String nameParent = getCellFormatValue(row.getCell((short) j - 1));//获取当前name在1级是否存在
                            if (StringUtils.isNotBlank(nameParent)) {
                                ProductCategory cParent = map1.get(nameParent);
                                if (cParent != null) {
                                    Map<String, ProductCategory> cMapParent = map2.get(cParent.getName());
                                    if (cMapParent == null || (cMapParent != null && cMapParent.get(name) == null)) {
                                        if (cMapParent == null) {
                                            cMapParent = new HashMap<String, ProductCategory>();
                                        }
                                        cate.setParent(cParent);
                                        cate.setParentIds(cParent.getParentIds() + cParent.getId() + ",");
                                        productCategoryService.insertSelective(cate);
                                        cMapParent.put(name, cate);
                                        map2.put(nameParent, cMapParent);
                                        success++;
                                    }
                                }
                            }
                        }
                        if (j == 2) {
                            //三级分类
                            String nameParentParent = getCellFormatValue(row.getCell((short) j - 2));//获取当前name在2级是否存在
                            String nameParent = getCellFormatValue(row.getCell((short) j - 1));
                            if (StringUtils.isNotBlank(nameParentParent) && StringUtils.isNotBlank(nameParent)) {
                                Map<String, ProductCategory> cMapParent = map2.get(nameParentParent);
                                if (cMapParent != null && cMapParent.get(name) == null) {
                                    ProductCategory cParent = cMapParent.get(nameParent);
                                    cate.setParent(cParent);
                                    cate.setParentIds(cParent.getParentIds() + cParent.getId() + ",");
                                    productCategoryService.insertSelective(cate);
                                    cMapParent.put(name, cate);
                                    map2.put(nameParent, cMapParent);
                                    success++;
                                }
                            }
                        }
                    }
                }
                j++;
            }
        }
        if (fail == 0) {
            addMessage(redirectAttributes, FYUtils.fy("成功导入商品分类"));
        } else {
            addMessage(redirectAttributes, FYUtils.fy("导入商品分类异常"));
        }
        return "redirect:" + Global.getAdminPath() + "/product/productCategory/importExcel.do?success=" + success + "&fail=" + fail;
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (!(cell == null || "".equals(cell))) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cellvalue = cell.getStringCellValue();
        }
        if (StringUtils.isNotBlank(cellvalue)) {
            cellvalue = cellvalue.trim();
        }
        return cellvalue;
    }

    /**
     * 表单验证
     *
     * @param productBrand 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductCategory productCategory, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fy("分类名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fy("分类名称最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("commission")) && R.get("commission").length() > 12) {
            errorList.add(FYUtils.fy("分佣比例最大长度不能超过12字符"));
        }
        if (StringUtils.isBlank(R.get("isLocked"))) {
            errorList.add(FYUtils.fy("请选择是否锁定"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fy("排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fy("排序最大长度不能超过10字符"));
        }
        Long parentId = productCategory.getParentId();
        if (parentId != 0 && parentId != null) {
            ProductCategory pc = productCategoryService.selectById(parentId);
            if (pc == null) {
                errorList.add(FYUtils.fy("商品分类为空"));
            } else {
                Long level = pc.getLevel();
                if (level == 3) {
                    errorList.add(FYUtils.fy("商品分类最多为3级"));
                }
            }
        }
        return errorList;
    }
}