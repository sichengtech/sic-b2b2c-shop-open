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

import com.sicheng.admin.logistics.entity.LogisticsTemplate;
import com.sicheng.admin.product.entity.*;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.*;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.common.config.Global;
import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.common.xss.XssClean;
import com.sicheng.front.service.ProductCarService;
import com.sicheng.search.ProductSearchInterface;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.logistics.service.LogisticsTemplateService;
import com.sicheng.seller.product.service.*;
import com.sicheng.seller.store.service.*;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 商品 Controller
 * 所属模块：product
 *
 * @author zhaolei
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${sellerPath}/product/productSpu")
public class ProductSpuController extends BaseController {

    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductBrandService productBrandService;
    @Autowired
    private ProductUnitService productUnitService;
    @Autowired
    private ProductExtService productExtService;
    @Autowired
    private ProductSpecService productSpecService;
    @Autowired
    private ProductParamService productParamService;
    @Autowired
    private LogisticsTemplateService logisticsTemplateService;
    @Autowired
    private ProductConfigService productConfigService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSectionPriceService productSectionPriceService;
    @Autowired
    private ProductParamMappingService productParamMappingService;
    @Autowired
    private StoreAlbumService storeAlbumService;
    @Autowired
    private ProductPictureMappingService productPictureMappingService;
    @Autowired
    private StoreBrandService storeBrandService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductCarService productCarService;
    @Autowired
    private ProductCarMappingService productCarMappingService;
    @Autowired
    private SysVariableService sysVariableService;
    @Autowired
    private StoreCategoryService storeCategoryService;
    @Autowired
    private ProductSearchInterface productSearch;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
    }

    /**
     * 进入列表页
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:view")
    @RequestMapping(value = "list")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        //搜索
        String searchCate = R.get("searchCate");
        String searchValue = R.get("searchValue");
        Wrapper wrapper = new Wrapper();
        //搜索条件：1代表商品名称，2代表商家账号，3代表商品ID
        if (StringUtils.isNotBlank(searchValue)) {
            if ("1".equals(searchCate) || "".equals(searchCate)) {
                wrapper.and("name like", "%" + searchValue + "%");
            } else if ("2".equals(searchCate)) {
                //如果查的是订单编号，要判断查询的内容是不是纯数字，如果是转换成Long类型，如果不是置为-1
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = pattern.matcher(searchValue);
                if (isNum.matches()) {
                    wrapper.and("p_id =", searchValue);
                } else {
                    wrapper.and("p_id =", -1);
                }
            }
        }
        //三级菜单高亮
        String status = R.get("sign");
        if ("50".equals(status)) {
            //出售中的
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            SellerMenuInterceptor.menuHighLighting("020110");
        } else {
            //仓库中的商品
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            SellerMenuInterceptor.menuHighLighting("020115");
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        if (StringUtils.isNoneBlank(status)) {
            wrapper.and("status =", status);
        }
        if (userSeller.getStoreId() != null) {
            wrapper.and("store_id =", userSeller.getStoreId());
        } else {
            wrapper.and("store_id =", "-1");
        }
        //已推荐商品
        String isRecommend = R.get("recommend");
        if (StringUtils.isNotBlank(isRecommend)) {
            wrapper.and("is_recommend =", isRecommend);
        }
        Page<ProductSpu> page = productSpuService.selectByWhere(new Page<ProductSpu>(request, response), wrapper);
        ProductSpu.fillProductSpuAnalyze(page.getList());
        //查询商品设置
        List<ProductConfig> producConfigtList = productConfigService.selectAll(new Wrapper());
        if (!producConfigtList.isEmpty()) {
            model.addAttribute("productConfig", producConfigtList.get(0));
        }
        model.addAttribute("page", page);
        model.addAttribute("sign", status);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchCate", searchCate);
        model.addAttribute("isRecommend", isRecommend);
        model.addAttribute("recommend", isRecommend);
        return "seller/product/productList";
    }

    /**
     * 进入保存页面，第一步
     *
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "save1")
    public String save1(Model model) {
        //发布商品
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting("020105");
        return "seller/product/productRelease1";
    }

    /**
     * 进入保存页面，第二步
     *
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "save2")
    public String save2(Model model, RedirectAttributes redirectAttributes) {
        Long productCategoryId = R.getLong("product_category_id");//商品分类的ID
        if (productCategoryId == null) {
            //无商品分类的ID，无法进入后续的业务操作
            addMessage(redirectAttributes, FYUtils.fyParams("请先选择商品的分类"));
            return "redirect:" + sellerPath + "/product/productSpu/save1.htm";
        }
        //查找分类、父级分类，用于显示的发布商品的页面上
        ProductCategory c = productCategoryService.selectById(productCategoryId);
        if (c == null) {
            //无商品分类，无法进入后续的业务操作
            addMessage(redirectAttributes, FYUtils.fyParams("请先选择商品的分类"));
            return "redirect:" + sellerPath + "/product/productSpu/save1.htm";
        }
        List<ProductCategory> categoryList = productCategoryService.findParentNode(productCategoryId);
        categoryList.add(c);
        //查找规格，用户显示发布商品的页面上
        List<ProductSpec> productSpecList = productSpecService.selectAll(new Wrapper());
        //查找商品分类参数
        List<ProductParam> productParamList = productParamService.selectParamByCategoryId(productCategoryId);
        //查询运费模板
        LogisticsTemplate template = new LogisticsTemplate();
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        template.setStoreId(userSeller.getStoreId());
        List<LogisticsTemplate> templateList = logisticsTemplateService.selectByWhere(new Wrapper(template));
        //查询商品设置
        List<ProductConfig> producConfigtList = productConfigService.selectAll(new Wrapper());
        if (!producConfigtList.isEmpty()) {
            model.addAttribute("productConfig", producConfigtList.get(0));
        }
        //获取店铺(旗舰店)品牌
        Store store = userSeller.getStore();
        if ("20".equals(store.getStoreType())) {
            List<StoreBrand> storeBrandList = storeBrandService.selectByWhere(new Wrapper().and("store_id =", store.getStoreId()));
            if (storeBrandList.size() == 1) {
                if (storeBrandList.get(0).getProductBrand() != null) {
                    model.addAttribute("productBrand", storeBrandList.get(0).getProductBrand());
                }
            }
        }
        model.addAttribute("templateList", templateList);
        model.addAttribute("productParamList", productParamList);
        model.addAttribute("productSpecList", productSpecList);
        model.addAttribute("product_category_id", productCategoryId);//商品分类的ID,把商品发布到这个分类下
        model.addAttribute("category_list", categoryList);//用于显示三级分类的名称
        model.addAttribute("storeId", userSeller.getStore().getStoreId());
        model.addAttribute("accessKey", AccessKey.generateAccessKey());//上传图片需要AccessKey
        //从fdp获取语言(是否是英文版商城：0否1是)
        String isEnglish = Global.getConfig("sys.isEnglish");
        model.addAttribute("isEnglish",isEnglish);

        //发布商品
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting("020105");
        return "seller/product/productRelease2";
    }

    /**
     * 执行保存
     *
     * @param productSpu         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "save3")
    public String save3(ProductSpu productSpu, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSpu, model);
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(model);//回显错误提示
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        productSpu.setUId(userSeller.getUId());
        productSpu.setStoreId(userSeller.getStoreId());
        //根据商品是否审核的设置，修改商品的状态,10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        String status = "50";
        //发布,0放入仓库中，1立即发布
        String publish = R.get("publish");
        //查询商品设置
        List<ProductConfig> producConfigList = productConfigService.selectAll(new Wrapper());
        if (!producConfigList.isEmpty() && producConfigList.get(0) != null && "1".equals(producConfigList.get(0).getSet1())) {
            status = "30";
        } else if ("0".equals(publish)) {
            status = "10";
        }
        productSpu.setStatus(status);
        if ("50".equals(status)) {
            productSpu.setShelfTime(new Date());
        }
        //商品分类参数
        //车系
        String[] carIds = R.getArray("carIds");
        //是否全车系
        String allCar = R.get("allCar");
        //参数id_参数名
        String param = R.get("selectParams");
        String[] params = null;
        if (StringUtils.isNotBlank(param)) {
            params = param.split(",");
        }
        //参数值图片
        String[] valuesImg = R.getArray("valuesImg");
        //参数类型：1核心参数、2辅助参数、3自定义参数
        String[] type = R.getArray("paramType");
        //参数格式：1图片、2文字、3文字+图片（颜色要配图片）
        String[] format = R.getArray("format");
        //参数名
        String[] name = R.getArray("paramName");
        Map<String, String[]> paramMap = new HashMap<>();
        paramMap.put("params", params);
        paramMap.put("valuesImg", valuesImg);
        paramMap.put("type", type);
        paramMap.put("format", format);
        paramMap.put("name", name);
        //商品区间价
        String section = R.get("section");
        String[] sections = section.split(",");
        //商品规格
        String productSku1 = R.get("productSku");
        //String[] productSku2=productSku1.split("-");
        String[] productSku2 = R.getArray("productSpec");
        //获取商品详情
        String detail = R.get("introduction");
        String htmlUnsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
        String detailSafe = XssClean.clean(htmlUnsafe);//按白名单进行危险字符过滤
        //商品图片(颜色：图片id-图片id)
        String productImgs = R.get("productImgs");
        logger.info("发布商品的图片：" + productImgs);
        String[] productImgArray = productImgs.split(",");
        //业务处理
        productSpuService.publishProduct(paramMap, sections, productSku2, productSpu, detailSafe, productImgArray, carIds, allCar);
        addMessage(redirectAttributes, FYUtils.fyParams("保存商品成功"));
        redirectAttributes.addFlashAttribute("productSpu", productSpu);
        redirectAttributes.addFlashAttribute("menu3", "020105");//发布商品菜单
        String stat = R.get("submitBtn");


        //按ID添加索引（异步线程池）
        productSearch.addDocAsyn(productSpu.getPId());

        //stat:1调到保存完成页，2保存完成继续添加
        if ("1".equals(stat)) {
            return "redirect:" + sellerPath + "/product/productSpu/productRelease3.htm";
        } else {
            return "redirect:" + sellerPath + "/product/productSpu/save1.htm";
        }
    }

    //发布成功页面，第三步
    @RequestMapping(value = "productRelease3")
    public String productRelease3() {
        return "seller/product/productRelease3";
    }

    /**
     * 进入编辑页面
     *
     * @param productSpu 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductSpu productSpu, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (productSpu == null || productSpu.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("商品不存在！"));
            return "error/400";
        }
        //入参检查
        productSpu.setStoreId(SsoUtils.getUserMain().getUserSeller().getStoreId());//属主检查
        ProductSpu entity = productSpuService.selectOne(new Wrapper(productSpu));
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("商品不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        Long productCategoryId = entity.getCategoryId();
        //查找分类、父级分类，用于显示的发布商品的页面上
        ProductCategory c = productCategoryService.selectById(productCategoryId);
        if (c == null) {
            //无商品分类，无法进入后续的业务操作
            addMessage(redirectAttributes, FYUtils.fyParams("请先选择商品的分类"));
            return "redirect:" + sellerPath + "/product/productSpu/save1.htm";
        }
        List<ProductCategory> categoryList = productCategoryService.findParentNode(productCategoryId);
        categoryList.add(c);
        //查询当前spu商品扩展表
        ProductExt productExt = productExtService.selectById(entity.getPId());
        List<Map<String, String>> productCarlistMap = new ArrayList<>();
        if (productExt == null || "0".equals(productExt.getAllCar())) {//是否全车系（0否，1是）
            //如果当前不是全车系才去查询当前spu商品的车系车型(productCarMapping表)
            productCarlistMap = editProductCar(productSpu);
        }
        //查找规格，用于显示发布商品的页面上
        List<ProductSpec> productSpecList = productSpecService.selectAll(new Wrapper());
        //查找商品分类的所有参数
        List<ProductParam> productParamList = productParamService.selectParamByCategoryId(productCategoryId);
        //查询当前商品的参数
        List<ProductParamMapping> productParamMappingList = productParamMappingService.selectByWhere(new Wrapper().and("p_id =", entity.getPId()));
        //查询运费模板
        LogisticsTemplate template = new LogisticsTemplate();
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        template.setStoreId(userSeller.getStoreId());
        List<LogisticsTemplate> templateList = logisticsTemplateService.selectByWhere(new Wrapper(template));
        //查询商品设置
        List<ProductConfig> producConfigtList = productConfigService.selectAll(new Wrapper());
        if (!producConfigtList.isEmpty()) {
            model.addAttribute("productConfig", producConfigtList.get(0));
        }
        //获取商品的sku
        List<ProductSku> productSkuList = productSkuService.selectByWhere(new Wrapper().and("p_id =", entity.getPId()).orderBy("sku_id asc"));
        //循环获取规格值，用于显示
        if (productSkuList != null && !productSkuList.isEmpty()) {
            List<Object> spec1VList = new ArrayList<>();
            List<Object> spec2VList = new ArrayList<>();
            List<Object> spec3VList = new ArrayList<>();
            for (ProductSku sku : productSkuList) {
                if (!spec1VList.contains(sku.getSpec1V())) {
                    spec1VList.add(sku.getSpec1V());
                }
                if (!spec2VList.contains(sku.getSpec2V())) {
                    spec2VList.add(sku.getSpec2V());
                }
                if (!spec3VList.contains(sku.getSpec3V())) {
                    spec3VList.add(sku.getSpec3V());
                }
            }
            Map<String, List<Object>> skuMap = new LinkedHashMap<>();
            if (StringUtils.isNotBlank(productSkuList.get(0).getSpec1())) {
                skuMap.put(productSkuList.get(0).getSpec1(), spec1VList);
            }
            if (StringUtils.isNotBlank(productSkuList.get(0).getSpec2())) {
                skuMap.put(productSkuList.get(0).getSpec2(), spec2VList);
            }
            if (StringUtils.isNotBlank(productSkuList.get(0).getSpec3())) {
                skuMap.put(productSkuList.get(0).getSpec3(), spec3VList);
            }
            model.addAttribute("skuMap", skuMap);
        }
        //如果当前商品是批发型或混合型，则查询区间价
        if (!"1".equals(productSpu.getType())) {
            List<ProductSectionPrice> productSectionPricesList = productSectionPriceService.selectByWhere(new Wrapper().and("p_id =", entity.getPId()).orderBy("SC_ID"));
            model.addAttribute("productSectionPricesList", productSectionPricesList);
        }
        //获取商品图片
        List<ProductPictureMapping> pictureMappingList = productPictureMappingService.selectByWhere(new Wrapper().and("p_id =", entity.getPId()).orderBy("id asc"));
        LinkedHashMap<String, List<ProductPictureMapping>> pictureMap = new LinkedHashMap<>();
        for (int i = 0; i < pictureMappingList.size(); i++) {
            ProductPictureMapping pic = pictureMappingList.get(i);
            String key = pic.getColor();
            if (StringUtils.isBlank(key)) {
                if (i == 0 || pictureMap.get("key") == null) {
                    List<ProductPictureMapping> list = new ArrayList<>();
                    list.add(pic);
                    pictureMap.put("key", list);
                } else {
                    pictureMap.get("key").add(pic);
                }
            } else {
                if (!pictureMap.isEmpty()) {
                    if (pictureMap.get(key) != null) {
                        pictureMap.get(key).add(pic);
                    } else {
                        List<ProductPictureMapping> list = new ArrayList<>();
                        list.add(pic);
                        pictureMap.put(key, list);
                    }
                } else {
                    List<ProductPictureMapping> list = new ArrayList<>();
                    list.add(pic);
                    pictureMap.put(key, list);
                }
            }
        }
        ProductDetail detail = productSpu.getProductDetail();
        model.addAttribute("productCarlistMap", JsonMapper.getInstance().toJson(productCarlistMap));
        model.addAttribute("detail", detail);
        model.addAttribute("pictureMap", JsonMapper.getInstance().toJson(pictureMap));
        model.addAttribute("productSpu", entity);
        model.addAttribute("productExt", productExt);
        model.addAttribute("product_category_id", productCategoryId);//商品分类的ID,把商品发布到这个分类下
        model.addAttribute("category_list", categoryList);
        model.addAttribute("productSkuList", productSkuList);
        model.addAttribute("productSkuJsonList", JsonMapper.getInstance().toJson(productSkuList));
        model.addAttribute("templateList", templateList);
        model.addAttribute("productParamList", productParamList);
        model.addAttribute("productParamMappingList", productParamMappingList);
        model.addAttribute("productSpecList", productSpecList);
        model.addAttribute("storeId", userSeller.getStore().getStoreId());

        model.addAttribute("accessKey", AccessKey.generateAccessKey());//上传图片需要AccessKey
        model.addAttribute("sign", R.get("sign"));
        model.addAttribute("recommend", R.get("recommend"));
        model.addAttribute("isCopy", R.get("isCopy"));
        //从fdp获取语言(是否是英文版商城：0否1是)
        String isEnglish = Global.getConfig("sys.isEnglish");
        model.addAttribute("isEnglish",isEnglish);

        //发布商品
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting("020105");
        return "seller/product/productRelease2";
    }

    /**
     * 执行编辑
     *
     * @param productSpu         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductSpu productSpu, Model model, RedirectAttributes redirectAttributes) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //表单验证
        List<String> errorList = validate(productSpu, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(model);//回显错误提示
        }
        //入参检查
        if (productSpu == null || productSpu.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("商品不存在！"));
            return "error/400";
        }
        //入参检查
        ProductSpu condition = new ProductSpu();
        condition.setId(productSpu.getId());//属主检查
        condition.setStoreId(userSeller.getStoreId());//属主检查
        List<ProductSpu> list = productSpuService.selectByWhere(new Wrapper(condition));
        if (list.isEmpty()) {
            model.addAttribute("message", FYUtils.fyParams("商品不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        productSpu.setUId(userSeller.getUId());
        productSpu.setStoreId(userSeller.getStoreId());
        //发布
        String publish = R.get("publish");
        //设置状态,10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        String status = "50";
        String sign = R.get("sign");
        Date shelfTime = DateUtils.parseDate(R.get("shelfTime"));
        Date createDate = DateUtils.parseDate(R.get("createDate"));
        Date updateDate = DateUtils.parseDate(R.get("updateDate"));
        productSpu.setShelfTime(shelfTime);
        productSpu.setCreateDate(createDate);
        productSpu.setUpdateDate(updateDate);
        //查询商品设置
        List<ProductConfig> producConfigList = productConfigService.selectAll(new Wrapper());
        ProductConfig productConfig = new ProductConfig();
        if (producConfigList.size() > 0 && producConfigList.get(0) != null) {
            productConfig = producConfigList.get(0);
        }
        //set2,禁售的商品设置，1禁售的商品不允许再编辑，再售出，2禁售的商品可以编辑，但编辑后必须由管理员审核，审核通过后可以再次售出
        //3禁售的商品可以编辑，根据商品审核开关判断是否需要管理员审核
        if ("50".equals(sign) || "10".equals(sign) || "3".equals(productConfig.getSet2())) {
            //set1，新发商品是否需要审核，0否，1是
            if ("1".equals(producConfigList.get(0).getSet1())) {
                status = "30";
            } else if ("0".equals(publish)) {
                status = "10";
            }
        } else if (("20".equals(sign) && "2".equals(productConfig.getSet2())) || "30".equals(sign) || "40".equals(sign)) {
            status = "30";
        }
        productSpu.setStatus(status);
        //商品分类参数
        //车系
        String[] carIds = R.getArray("carIds");
        //是否全车系
        String allCar = R.get("allCar");
        //参数id_参数名
        String param = R.get("selectParams");
        String[] params = null;
        if (StringUtils.isNotBlank(param)) {
            params = param.split(",");
        }
        //商品参数图片
        String[] valuesImg = R.getArray("valuesImg");
        //参数类型
        String[] type = R.getArray("paramType");
        //参数格式：1图片、2文字、3文字+图片（颜色要配图片）
        String[] format = R.getArray("format");
        //参数名
        String[] name = R.getArray("paramName");
        Map<String, String[]> map = new HashMap<>();
        map.put("params", params);
        map.put("valuesImg", valuesImg);
        map.put("type", type);
        map.put("format", format);
        map.put("name", name);
        //商品区间价
        String section = R.get("section");
        String[] sections = section.split(",");
        //商品规格
        //String productSku1=R.get("productSku");
        //String[] productSku2=productSku1.split("-");
        String[] productSku2 = R.getArray("productSpec");
        //获取商品详情
        String detail = R.get("introduction");
        String htmlUnsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
        String detailSafe = XssClean.clean(htmlUnsafe);//按白名单进行危险字符过滤
        //商品图片
        String productImgs = R.get("productImgs");
        String[] productImgArray = productImgs.split(",");
        String isCopy = R.get("isCopy");
        String message = FYUtils.fyParams("编辑商品成功");
        if ("1".equals(isCopy)) {
            productSpu.setPId(null);
            message = FYUtils.fyParams("复制商品成功");
        }
        //业务处理
        productSpuService.publishProduct(map, sections, productSku2, productSpu, detailSafe, productImgArray, carIds, allCar);
        model.addAttribute("productSpu", productSpu);
        addMessage(redirectAttributes, message);
        String recommend = R.get("recommend");
        String rec = "";
        if (StringUtils.isNoneBlank(recommend)) {
            rec = "&recommend=" + recommend;
        }

        //向MQ发消息
        //状态,10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        String status2 = productSpu.getStatus();
        if(status2!=null) {
            int status3=Integer.valueOf(status2);
            if(status3<50) {
                //按ID删除索引（异步线程池）
                productSearch.deleteDocAsyn(productSpu.getPId());
            }else {
                //按ID添加索引（异步线程池）
                productSearch.addDocAsyn(productSpu.getPId());
            }
        }

        return "redirect:" + sellerPath + "/product/productSpu/list.htm?repage&sign=" + sign + rec;
    }

    /**
     * 删除
     *
     * @param productSpu         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductSpu productSpu, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (productSpu == null || productSpu.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("商品不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        productSpuService.deleteProduct(productSpu);
        addMessage(redirectAttributes, FYUtils.fyParams("删除商品成功"));
        String recommend = R.get("recommend");
        String rec = "";
        if (StringUtils.isNoneBlank(recommend)) {
            rec = "&recommend=" + recommend;
        }

        //按ID删除索引（异步线程池）
        productSearch.deleteDocAsyn(productSpu.getPId());

        String sign = R.get("sign");
        return "redirect:" + sellerPath + "/product/productSpu/list.htm?repage&sign=" + sign + rec;
    }

    /**
     * 编辑页面回显商品的车型库
     */
    private List<Map<String, String>> editProductCar(ProductSpu productSpu) {
        ProductCarMapping productCarMapping = new ProductCarMapping();
        productCarMapping.setPId(productSpu.getPId());
        List<ProductCarMapping> productCarMappingList = productCarMappingService.selectByWhere(new Wrapper(productCarMapping));
        Set<Long> cSet = new HashSet<Long>();
        for (int i = 0; i < productCarMappingList.size(); i++) {
            String[] carIds = productCarMappingList.get(i).getCarIds().split(",");
            for (int j = 0; j < carIds.length; j++) {
                cSet.add(Long.parseLong(carIds[j]));
            }
        }
        List<Long> carIdlist = new ArrayList<>();
        Iterator<Long> iterator = cSet.iterator();
        while (iterator.hasNext()) {
            carIdlist.add(iterator.next());
        }
        List<ProductCar> productCarNameList = productCarService.selectByIdIn(carIdlist);
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        if (!productCarNameList.isEmpty()) {
            //id与名字的组装
            for (int i = 0; i < productCarMappingList.size(); i++) {
                String[] carIds = productCarMappingList.get(i).getCarIds().split(",");
                String carIdsNames = "";
                for (int j = 0; j < carIds.length; j++) {
                    for (int j2 = 0; j2 < productCarNameList.size(); j2++) {
                        if (productCarNameList.get(j2).getCarId().equals(Long.parseLong((carIds[j])))) {
                            carIdsNames += "-" + productCarNameList.get(j2).getName();
                        }
                    }
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put(productCarMappingList.get(i).getCarIds(), carIdsNames.substring(1));
                listMap.add(map);
            }
        }
        return listMap;
    }

    /**
     * 上架、下架
     *
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "offShelf")
    public String offShelf(RedirectAttributes redirectAttributes) {
        String ids = R.get("ids");
        if (StringUtils.isNotBlank(ids)) {
            String[] idss = ids.split(",");
            List<ProductSpu> productSpuList = new ArrayList<>();
            //type:1上架，2下架
            String type = R.get("type");
            String status = "10";//商品状态：10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
            String msg = FYUtils.fyParams("下架");
            if ("1".equals(type)) {
                //商品状态：10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
                status = "50";
                msg = FYUtils.fyParams("上架");
            }
            for (int i = 0; i < idss.length; i++) {
                ProductSpu productSpu = new ProductSpu();
                productSpu.setPId(Long.valueOf(idss[i]));
                //商品状态：10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
                productSpu.setStatus(status);
                if ("50".equals(status)) {
                    productSpu.setShelfTime(new Date());
                }
                productSpuList.add(productSpu);
            }
            productSpuService.updateSelectiveBatch(productSpuList);
            addMessage(redirectAttributes, FYUtils.fyParams("商品") + msg + FYUtils.fyParams("成功"));

            for(ProductSpu p:productSpuList) {
                //向MQ发消息
                //状态,10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
                String status2 = p.getStatus();
                if(status2!=null) {
                    int status3=Integer.valueOf(status2);
                    if(status3<50) {
                        //按ID删除索引（异步线程池）
                        productSearch.deleteDocAsyn(p.getPId());
                    }else {
                        //按ID添加索引（异步线程池）
                        productSearch.addDocAsyn(p.getPId());
                    }
                }
            }
        }
        String recommend = R.get("recommend");
        String rec = "";
        if (StringUtils.isNoneBlank(recommend)) {
            rec = "&recommend=" + recommend;
        }
        String sign = R.get("sign");
        return "redirect:" + sellerPath + "/product/productSpu/list.htm?repage&sign=" + sign + rec;
    }

    /**
     * 推荐、取消推荐
     *
     * @param redirectAttributes 说明
     * @return 地址
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "recommend")
    public String recommend(RedirectAttributes redirectAttributes) {
        String ids = R.get("ids");
        if (StringUtils.isNotBlank(ids)) {
            //type:1推荐，2取消推荐
            String type = R.get("type");
            String recomment = "0";//是否推荐：0非，1是
            String msg = FYUtils.fyParams("取消推荐");
            if ("1".equals(type)) {
                //是否推荐：0非，1是
                recomment = "1";
                msg = FYUtils.fyParams("推荐");
            }
            String[] idss = ids.split(",");
            List<ProductSpu> productSpuList = new ArrayList<>();
            for (int i = 0; i < idss.length; i++) {
                ProductSpu productSpu = new ProductSpu();
                productSpu.setPId(Long.valueOf(idss[i]));
                productSpu.setIsRecommend(recomment);
                productSpuList.add(productSpu);
            }
            productSpuService.updateSelectiveBatch(productSpuList);
            addMessage(redirectAttributes, FYUtils.fyParams("商品") + msg + FYUtils.fyParams("成功"));
        }
        String recommend = R.get("recommend");
        String rec = "";
        if (StringUtils.isNoneBlank(recommend)) {
            rec = "&recommend=" + recommend;
        }
        String sign = R.get("sign");
        return "redirect:" + sellerPath + "/product/productSpu/list.htm?repage&sign=" + sign + rec;
    }

    /**
     * 方法说明：查看规格
     *
     * @param model model
     * @return java.lang.String
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "showSpec")
    public String showSpec(Model model) {

        Long pId = R.getLong("id");
        if (pId != null) {
            List<ProductSku> productSkuList = productSkuService.selectByWhere(new Wrapper().and("p_id =", pId));
            ProductSpu productSpu = productSpuService.selectById(pId);
            model.addAttribute("productSkuList", productSkuList);
            model.addAttribute("productSpu", productSpu);
        }
        return "seller/product/productSku";
    }

    /**
     * 表单验证
     *
     * @param productSpu 实体对象
     * @param model model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductSpu productSpu, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isNotBlank(R.get("productSku"))) {
            String result = productSpuService.productForbiddenWords(R.get("productSku"));
            if (StringUtils.isNotBlank(result)) {
                errorList.add(FYUtils.fyParams("当前的商品规格包含违禁词:") + result);
            }
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("商品名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 128) {
            errorList.add(FYUtils.fyParams("商品名称最大长度不能超过128字符"));
        }
        if (StringUtils.isNotBlank(R.get("name"))) {
            String result = productSpuService.productForbiddenWords(R.get("name"));
            if (StringUtils.isNotBlank(result)) {
                errorList.add(FYUtils.fyParams("当前的商品名称包含违禁词:") + result);
            }
        }
        if (StringUtils.isBlank(R.get("categoryId"))) {
            errorList.add(FYUtils.fyParams("商品分类不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("categoryId")) && R.get("categoryId").length() > 19) {
            errorList.add(FYUtils.fyParams("商品分类最大长度不能超过19字符"));
        }
        if (StringUtils.isNotBlank(R.get("storeCategoryId")) && R.get("storeCategoryId").length() > 19) {
            errorList.add(FYUtils.fyParams("店内分类最大长度不能超过19字符"));
        }
        if (productSpu.getStoreId() != null && productSpu.getStoreId() > 19) {
            errorList.add(FYUtils.fyParams("店铺表最大长度不能超过19字符"));
        }
        if (productSpu.getUId() != null && productSpu.getUId() > 19) {
            errorList.add(FYUtils.fyParams("卖家最大长度不能超过19字符"));
        }
        if (StringUtils.isBlank(R.get("categoryId"))) {
            errorList.add(FYUtils.fyParams("商品分类不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 2) {
            errorList.add(FYUtils.fyParams("状态最大长度不能超过2字符"));
        }
        if (StringUtils.isBlank(R.get("productImgs"))) {
            errorList.add(FYUtils.fyParams("图片不能为空"));
        }
        String[] productImg_inputs = R.getArray("productImg_input");
        if (productImg_inputs.length == 0) {
            errorList.add(FYUtils.fyParams("图片不能为空"));
        }
        if (productImg_inputs.length > 0) {
            for (int i = 0; i < productImg_inputs.length; i++) {
                if (StringUtils.isBlank(productImg_inputs[i])) {
                    errorList.add(FYUtils.fyParams("图片不能为空"));
                    break;
                }
            }
        }
        if (StringUtils.isNotBlank(R.get("nameSub")) && R.get("nameSub").length() > 255) {
            errorList.add(FYUtils.fyParams("副标题最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("nameSub"))) {
            String result = productSpuService.productForbiddenWords(R.get("nameSub"));
            if (StringUtils.isNotBlank(result)) {
                errorList.add(FYUtils.fyParams("当前的商品卖点描述包含违禁词:") + result);
            }
        }
        if (StringUtils.isNotBlank(R.get("unit")) && R.get("unit").length() > 10) {
            errorList.add(FYUtils.fyParams("计量单位最大长度不能超过10字符"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add(FYUtils.fyParams("销售类型最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("introduction"))) {
            errorList.add(FYUtils.fyParams("商品描述不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("introduction"))) {
            String result = productSpuService.productForbiddenWords(R.get("introduction"));
            if (StringUtils.isNotBlank(result)) {
                errorList.add(FYUtils.fyParams("当前的商品描述包含违禁词:") + result);
            }
        }
        if (StringUtils.isBlank(R.get("isRecommend"))) {
            errorList.add(FYUtils.fyParams("是否推荐不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("isRecommend")) && R.get("isRecommend").length() > 1) {
            errorList.add(FYUtils.fyParams("是否推荐最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("recommendSort")) && R.get("recommendSort").length() > 10) {
            errorList.add(FYUtils.fyParams("推荐排序最大长度不能超过10字符"));
        }
        if (StringUtils.isNotBlank(R.get("invoice")) && R.get("invoice").length() > 1) {
            errorList.add(FYUtils.fyParams("发票最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("action")) && R.get("action").length() > 1) {
            errorList.add(FYUtils.fyParams("是否参加促销活动最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("expressType")) && R.get("expressType").length() > 1) {
            errorList.add(FYUtils.fyParams("运费方式最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("ltId")) && R.get("ltId").length() > 19) {
            errorList.add(FYUtils.fyParams("运费模板ID最大长度不能超过19字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak1")) && R.get("bak1").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段最大长度不能超过64字符"));
        }
        return errorList;
    }

    /**
     * 选择品牌（在发布商品页需要选择品牌）
     *
     * @param request request
     * @param response response
     * @param model model
     * @return String
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "selectBrand")
    public String selectBrand(ProductBrand productBrand, HttpServletRequest request, HttpServletResponse response, Model model) {
        String name = R.get("name");//单位的名称
        String firstLetter = R.get("firstLetter");//单位的名称的首字母
        String storeId = R.get("storeId");
        if (StringUtils.isBlank(storeId) || !StringUtils.isNumeric(storeId)) {
            return "seller/product/searchBrand";
        }
        Long sid = Long.parseLong(storeId);
        Store store = storeService.selectById(sid);
        if (storeId == null) {
            return "seller/product/searchBrand";
        }
        Wrapper wrapper = new Wrapper();
        //获取品牌的开关，0表示普通店铺选择所有品牌，旗舰店铺选择管理员分配的品牌，1表示所有店铺都选择管理员给分配的品牌
        SysVariable sysVariable = sysVariableService.getSysVariable("store_change_brand");
        //storeType 店铺类型,1普通店铺、2旗舰店铺
        String storeType = store.getStoreType().trim();
        if (((sysVariable == null || "0".equals(sysVariable.getValue())) && "2".equals(storeType)) || (sysVariable != null && "1".equals(sysVariable.getValue()))) {
            List<StoreBrand> storeBrandList = storeBrandService.selectByWhere(new Wrapper().and("store_id =", sid));
            List<Long> sidList = new ArrayList<>();
            for (StoreBrand storeBrand : storeBrandList) {
                sidList.add(storeBrand.getBrandId());
            }
            wrapper.and("brand_id in", sidList);
        }
        wrapper.and("status=", "1");//审核状态，0待审、1通过、2未通过
        //获取首字母
        Map<String, String> firstLetterMap = new HashMap<>();
        List<ProductBrand> brandList = productBrandService.selectByWhere(wrapper);
        if (brandList.size() > 0) {
            for (ProductBrand brand : brandList) {
                if (StringUtils.isBlank(firstLetterMap.get(brand.getFirstLetter()))) {
                    firstLetterMap.put(brand.getFirstLetter(), brand.getFirstLetter());
                }
            }
        }
        //按名称或拼音字母搜索
        if (StringUtils.isNotBlank(name)) {
            //搜索计量单位名称或拼音首字母
            wrapper.andNew("name like", "%" + name + "%");
            wrapper.or("first_Letter =", name.toUpperCase());
        }
        //按拼音首字母搜索
        if (StringUtils.isNotBlank(firstLetter)) {
            wrapper.andNew("first_Letter =", firstLetter.toUpperCase());
        }
        wrapper.orderBy("sort asc");
        Page<ProductBrand> page = new Page<ProductBrand>(request, response);
        page = productBrandService.selectByWhere(page, wrapper);
        model.addAttribute("page", page);
        model.addAttribute("firstLetter", firstLetter);
        model.addAttribute("storeId", storeId);
        model.addAttribute("firstLetterMap", JsonMapper.getInstance().toJson(firstLetterMap));
        return "seller/product/searchBrand";
    }

    /**
     * 弹出发布商品选择车型库
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "selectProductCar")
    public String selectProductCar(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<ProductCar> productCarOneList = productCarService.selectByWhere(new Wrapper().and("a.parent_id=", 0));
        List<ProductCar> productCarTwoList = null;
        if (!productCarOneList.isEmpty()) {
            List<Long> carIdOnes = new ArrayList<Long>();
            for (int i = 0; i < productCarOneList.size(); i++) {
                carIdOnes.add(productCarOneList.get(i).getCarId());
            }
            productCarTwoList = productCarService.selectByWhere(new Wrapper().and("a.parent_id in", carIdOnes));
        }
        model.addAttribute("productCarTwoList", JsonMapper.toJsonString(productCarTwoList));
        return "seller/product/productCarForm";
    }

    /**
     * 点击品牌ajax生成jTree
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @ResponseBody
    @RequestMapping(value = "ajaxProductCar")
    public Object ajaxProductCar(HttpServletRequest request, HttpServletResponse response, Model model) {
        Long carId = R.getLong("carId");
        List<ProductCar> productCarList = null;
        Wrapper wrapper = new Wrapper();
        if (carId == null) {
            List<ProductCar> productCarOneList = productCarService.selectByWhere(new Wrapper().and("a.parent_id=", 0));
            if (!productCarOneList.isEmpty()) {
                List<Long> carIdOnes = new ArrayList<Long>();
                for (int i = 0; i < productCarOneList.size(); i++) {
                    carIdOnes.add(productCarOneList.get(i).getCarId());
                }
                wrapper.and("a.parent_id in", carIdOnes);
            }
            productCarList = productCarService.selectByWhere(wrapper);
        } else {
            productCarList = productCarService.findChildNodeAll(carId);
        }
        return productCarList;
    }

    /**
     * 选择计量单位（在发布商品地需要选择计量单位）
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "selectUnit")
    public String selectUnit(ProductUnit productUnit, HttpServletRequest request, HttpServletResponse response, Model model) {
        String name = R.get("name");//单位的名称
        String firstLetter = R.get("firstLetter");//单位的名称的首字母
        Wrapper wrapper = new Wrapper();
        if (StringUtils.isNotBlank(name)) {
            //搜索计量单位名称或拼音首字母
            wrapper.and("name like", "%" + name + "%");
            wrapper.or("first_Letter =", name.toUpperCase());
            //按拼音首字母搜索
        } else if (StringUtils.isNotBlank(firstLetter)) {
            wrapper.or("first_Letter like", "%" + firstLetter.toUpperCase() + "%");
        }
        Page<ProductUnit> page = new Page<ProductUnit>(request, response);
        page.setPageSize(50);//每页50个
        page.setOrderBy("sort");
        page = productUnitService.selectByWhere(page, wrapper);
        model.addAttribute("page", page);
        return "seller/product/searchUnit";
    }

    /**
     * 验证商品信息是否存在违禁词
     *
     * @param name          输入的 商品名称
     * @param nameSub       输入的卖点描述
     * @param specification 输入的商品规格
     * @param introduction  输入的商品描述
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "forbiddenWordsValidate")
    public Map<String, Object> forbiddenWordsValidate(String name, String nameSub, String specification, String introduction) {
        Map<String, Object> map = new HashMap<>();
        String msg1 = "";
        String msg2 = "";
        String msg3 = "";
        String msg4 = "";
        map.put("status", true);
        if (StringUtils.isNotBlank(name)) {
            String result = productSpuService.productForbiddenWords(name);
            if (StringUtils.isNotBlank(result)) {
                map.put("status", false);
                msg1 = FYUtils.fyParams("当前的商品名称包含违禁词:") + result + ",";
            }
        }
        if (StringUtils.isNotBlank(nameSub)) {
            String result = productSpuService.productForbiddenWords(nameSub);
            if (StringUtils.isNotBlank(result)) {
                map.put("status", false);
                msg2 = FYUtils.fyParams("当前的商品卖点描述包含违禁词:") + result + ",";
            }
        }
        if (StringUtils.isNotBlank(specification)) {
            String result = productSpuService.productForbiddenWords(specification);
            if (StringUtils.isNotBlank(result)) {
                map.put("status", false);
                msg3 = FYUtils.fyParams("当前的商品规格包含违禁词:") + result + ",";
            }
        }
        if (StringUtils.isNotBlank(introduction)) {
            String result = productSpuService.productForbiddenWords(introduction);
            if (StringUtils.isNotBlank(result)) {
                map.put("status", false);
                msg4 = FYUtils.fyParams("当前的商品描述包含违禁词:") + result + ",";
            }
        }
        String msg = msg1 + msg2 + msg3 + msg4;
        if (StringUtils.isNotBlank(msg)) {
            map.put("msg", msg.substring(0, msg.length() - 1));
        }
        return map;
    }

    /**
     * 进入上传图片页面
     *
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "productImgUpload")
    public String productUpload(Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        List<StoreAlbum> albumList = storeAlbumService.selectByWhere(new Wrapper().and("store_id=", userSeller.getStoreId()));
        model.addAttribute("albumList", albumList);
        return "seller/product/productImgUpload";
    }

    /**
     * 进入店铺分类页面
     *
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "productStoreCategory")
    public String productStoreCategory(StoreCategory storeCategory, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeCategory.setStoreId(userSeller.getStoreId());
        //是否开启：0否，1是
        storeCategory.setIsOpen("1");
        List<StoreCategory> storeCategoryList = storeCategoryService.selectByWhere(new Wrapper(storeCategory).orderBy("sort"));
        model.addAttribute("storeCategoryList", storeCategoryList);
        return "seller/product/productStoreCategory";
    }
}