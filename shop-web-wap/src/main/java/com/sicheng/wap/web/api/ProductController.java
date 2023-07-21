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
package com.sicheng.wap.web.api;

import com.sicheng.admin.product.entity.*;
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.entity.TradeCommentImage;
import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.search.ProductSearchInterface;
import com.sicheng.search.ResultSet;
import com.sicheng.search.ProductSearch4Solr;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.*;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>标题: ProductController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fanxiuxiu
 * @version 2017年12月16日 下午3:47:48
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class ProductController extends BaseController {

    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private SolrProductService solrProductService;
    @Autowired
    private ProductSectionPriceService productSectionPriceService;
    @Autowired
    private ProductParamMappingService productParamMappingService;
    @Autowired
    private ProductPictureMappingService productPictureMappingService;
    @Autowired
    private StoreAlbumPictureService storeAlbumPictureService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductBrandService productBrandService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private TradeCommentService tradeCommentService;
    @Autowired
    private TradeCommentImageService tradeCommentImageService;
    @Autowired
    private TradeConsultationService tradeConsultationService;
    @Autowired
    private SiteRecommendProductCategoryService siteRecommendProductCategoryService;
    @Autowired
    private StoreController storeController;
    @Autowired
    private ProductSearchInterface productSearch;

    /**
     * 获取商品信息，总接口
     *
     * @param pid    商品id，必传
     * @param skuIds skuId列表，非必传，用于整合/wap/api/v1/product/sku/list.htm接口
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/all", produces = {"application/json; charset=UTF-8"})
    public Object productAll(String pid, String skuIds) {
        //表单验证
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(pid) && StringUtils.isBlank(skuIds)) {
            errorList.add(FYUtils.fy("缺少参数"));
        }
        if (StringUtils.isNotBlank(pid) && !StringUtils.isNumeric(pid)) {
            errorList.add(FYUtils.fy("商品id必须是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            //商品sku，对应：/wap/api/v1/product/sku/list.htm
            ArrayList productSkuList = (ArrayList) this.productSkuList(pid, skuIds).get("data");

            //商品信息，对应：/wap/api/v1/product/one.htm
            Map<String, Object> productOne = (Map<String, Object>) this.productOne(pid).get("data");
            /**
             * 商品图片，对应：/wap/api/v1/product/image/list.htm
             * 如果不传入color属性则查询该pid下全部图片
             * color属性可以传入从productSkuList里查询出来的spec1V，这样就只查询该pid指定颜色的图片
             */
            ArrayList productImageList = (ArrayList) this.productImageList(pid, null).get("data");

            //商品批发价，对应：/wap/api/v1/product/sectionPrice/list.htm
            ArrayList productSectionPriceList = (ArrayList) this.productSectionPriceList(pid, null).get("data");

            //获取店铺id
            Long storeId = (Long) productOne.get("storeId");

            //店铺信息，对应：/wap/api/v1/store/one.htm
            Map<String, Object> storeOne = (Map<String, Object>) storeController.storeOne(storeId.toString()).get("data");

            //店铺头部信息，对应：/wap/api/v1/store/nav/count.htm
            Map<String, Object> storeNavCount = (Map<String, Object>) storeController.storeNavCount(storeId.toString()).get("data");

            //拼装并返回信息
            Map<String, Object> countMap = new HashMap<>();
            countMap.put("productSkuList", productSkuList);
            countMap.put("productOne", productOne);
            countMap.put("productImageList", productImageList);
            countMap.put("productSectionPriceList", productSectionPriceList);
            countMap.put("storeOne", storeOne);
            countMap.put("storeNavCount", storeNavCount);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取商品总信息成功"), countMap, null);
        } catch (Exception e) {
            logger.error("获取商品总信息失败，异常内容：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取商品总信息失败"), null, null);
        }
    }

    /**
     * 根据商品id查询商品
     *
     * @param pid 商品id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/one")
    public Map<String, Object> productOne(String pid) {
        // 处理参数
        String message = FYUtils.fy("pid不能为空");
        if (StringUtils.isBlank(pid)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        message = FYUtils.fy("pid必须为数字");
        if (!StringUtils.isNumeric(pid)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //执行业务，查询出产品列表
        SolrProduct solrProduct = solrProductService.selectOne(new Wrapper().and("p_id=", pid));
        message = FYUtils.fy("查询商品成功");
        if (solrProduct == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, solrProduct, null);
        }
        ProductSpu productSpu = new ProductSpu();
        productSpu.setPId(solrProduct.getPId());
        productSpu.setType(solrProduct.getType());
        productSpu.setPurchasingAmount(solrProduct.getPurchasingAmount());
        String purchaingAmount = productSpuService.getPurchasingAmount(productSpu);
        Map<String, Object> map = new LinkedHashMap<>();
        //商品id
        map.put("pid", solrProduct.getPId());
        //名称
        map.put("name", solrProduct.getName());
        //副标题
        map.put("nameSub", solrProduct.getNameSub());
        //评价数
        map.put("commentCount", solrProduct.getCommentCount());
        //月销
        map.put("monthSales", solrProduct.getMonthSales());
        //收藏数
        map.put("collectionCount", solrProduct.getCollectionCount());
        //单位
        map.put("unit", solrProduct.getUnit());
        //封面图
        map.put("image", solrProduct.getImage());
        //起购量
        map.put("purchasingAmount", purchaingAmount);
        //类型
        map.put("type", solrProduct.getType());
        //店铺id
        map.put("storeId", solrProduct.getStoreId());
        //最低零售价
        map.put("minPrice1", solrProduct.getMinPrice1());
        //最高批发价
        map.put("maxPrice1", solrProduct.getMaxPrice1());
        //最低批发价
        map.put("minPrice2", solrProduct.getMinPrice2());
        //最高批发价
        map.put("maxPrice2", solrProduct.getMaxPrice2());
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, map, null);
    }

    /**
     * 查询商品
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/list")
    public Map<String, Object> productList() {
        // 处理参数
        String limit = R.get("limit");//数量
        String cid = R.get("cid"); //分类id
        String cname = R.get("canme"); //分类名称
        String k = R.get("k"); //关键字
        String sid = R.get("sid"); //店铺id
        String pids = R.get("pids"); //多个商品id
        String price = R.get("price"); //价格
        String bid = R.get("bid"); //品牌id
        String bname = R.get("bname"); //品牌名称
        String attr = R.get("attr"); //参数
        String isRecommend = R.get("isRecommend"); //是否推荐
        String scid = R.get("scid"); //店铺分类id
        String carId = R.get("carId"); //车型id
        String sort = R.get("sort"); //排序条件
        String sortMode = R.get("sortMode"); //排序方式
        String sDate = R.get("sDate"); //起始时间
        String eDate = R.get("eDate"); //结束时间
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isNotBlank(cid) && !StringUtils.isNumeric(cid)) {
            errorList.add(FYUtils.fy("分类id必须是数字"));
        }
        if (StringUtils.isNotBlank(sid) && !StringUtils.isNumeric(sid)) {
            errorList.add(FYUtils.fy("店铺id必须是数字"));
        }
        if (StringUtils.isNotBlank(scid) && !StringUtils.isNumeric(scid)) {
            if(!"-1".equals(scid)){
                errorList.add(FYUtils.fy("店铺分类id必须是数字"));
            }
        }
        if (StringUtils.isNotBlank(carId) && !StringUtils.isNumeric(carId)) {
            errorList.add(FYUtils.fy("车型id必须是数字"));
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(sDate) || StringUtils.isNotBlank(eDate)) {
                String sDate2 = "*";
                String eDate2 = "*";
                if (StringUtils.isNotBlank(sDate)) {
//					sDate2 = DateUtil.getThreadLocalDateFormat().format(format.parse(sDate));
                    sDate2 = format.format(format.parse(sDate));
                }
                if (StringUtils.isNotBlank(eDate)) {
//					eDate2 = DateUtil.getThreadLocalDateFormat().format(format.parse(eDate));
                    eDate2 = format.format(format.parse(eDate));
                }
                paramMap.put("update_date", "[" + sDate2 + " TO " + eDate2 + "]");
            }
            String message = ApiUtils.errorMessage(errorList);
            if (!errorList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            if (StringUtils.isNoneBlank(cid)) {
                paramMap.put("cate_parent_ids", cid);
            }
            if (StringUtils.isNotBlank(cname)) {
                paramMap.put("category_name", cname);
            }
            if (StringUtils.isNotBlank(k)) {
                paramMap.put("text", k);
            }
            if (StringUtils.isNotBlank(sid)) {
                paramMap.put("store_id", sid);
            }
            if (StringUtils.isNotBlank(pids)) {
                paramMap.put("id", pids);
            }
            if (StringUtils.isNotBlank(price) && price.indexOf("-") != -1) {
                int index = price.indexOf("-");
                String minPrice = price.substring(0, index);
                String maxPrice = price.substring(index + 1);
                String minPrice2 = "*";
                String maxPrice2 = "*";
                if (StringUtils.isNotBlank(minPrice) && StringUtils.isNumeric(minPrice)) {
                    minPrice2 = minPrice;
                }
                if (StringUtils.isNotBlank(maxPrice) && StringUtils.isNumeric(maxPrice)) {
                    maxPrice2 = maxPrice;
                }
                paramMap.put("price2", "[" + minPrice2 + " TO " + maxPrice2 + "]");
            }
            if (StringUtils.isNotBlank(bid)) {
                paramMap.put("brand_id", bid);
            }
            if (StringUtils.isNotBlank(bname)) {
                paramMap.put("brand_name", bname);
            }
            if (StringUtils.isNoneBlank(attr)) {
                String paramValue = "";
                String[] attrs = attr.split(",");
                if (attrs.length > 0) {
                    for (int i = 0; i < attrs.length; i++) {
                        String[] att = attrs[i].split(":");
                        if (att.length > 1) {
                            String[] attrKV = att[0].split("_");
                            String attK = "";
                            if (attrKV.length > 0) {
                                attK = attrKV[0];
                            }
                            for (int j = 0; j < att.length; j++) {
                                if (j == 0) {
                                    paramValue += att[j] + ",";
                                } else {
                                    paramValue += attK + "_" + att[j] + ",";
                                }
                            }
                        } else {
                            paramValue += attrs[i] + ",";
                        }
                    }
                }
                paramMap.put("param_value", paramValue);
            }
            if (StringUtils.isNotBlank(isRecommend)) {
                paramMap.put("is_recommend", isRecommend);
            }
            if (StringUtils.isNotBlank(scid)) {
                paramMap.put("store_cate_parent_ids", scid);
            }
            if (StringUtils.isNotBlank(carId)) {
                paramMap.put("car_ids", carId);
            }
            Map<String, String> sortMap = new HashMap<>();
            if (StringUtils.isNotBlank(sort)) {
                if (StringUtils.isBlank(sortMode)) {
                    sortMode = "";
                }
                sortMap.put(StringUtils.toUnderScoreCase(sort), sortMode);
            } else {
                sortMap.put("update_date", "desc");
            }
            Integer defaultLimit = null;
            try {
                defaultLimit = ApiUtils.getLimit(limit);
            } catch (Exception e) {
                logger.error("limit参数出现错误：", e);
                return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("limit必须为数字"), null, null);
            }
            //执行sql,并查出了结果集
            Page<SolrProduct> page = new Page<SolrProduct>(1, defaultLimit, defaultLimit);
            Map<String, Object> resultMap = productSearch.search(paramMap, sortMap, page);
            Map<String, Object> productListMap = new LinkedHashMap<>();
            if (resultMap.size() == 0) {
                message = FYUtils.fy("查询成功");
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, resultMap, null);
            }
            //商品信息
            ResultSet<SolrProduct> result = (ResultSet<SolrProduct>) resultMap.get("search");
            if (result == null || result.getItems() == null) {
                message = FYUtils.fy("查询成功");
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, resultMap, null);
            }
            List<SolrProduct> solrProducts = result.getItems();
            List<Map<String, Object>> solrProductList = new ArrayList<>();
            for (int i = 0; i < solrProducts.size(); i++) {
                Map<String, Object> productMap = new LinkedHashMap<>();
                SolrProduct solrProduct = solrProducts.get(i);
                productMap.put("pid", solrProduct.getPId());//商品id
                productMap.put("name", solrProduct.getName());//商品名称
                productMap.put("type", solrProduct.getType());//商品类型
                productMap.put("image", solrProduct.getImage());//商品图片
                productMap.put("minPrice", solrProduct.getMinPrice());//最低价
                productMap.put("minPrice1", solrProduct.getMinPrice1());//商品零售价格
                productMap.put("minPrice2", solrProduct.getMinPrice2());//商品批发价格
                productMap.put("unit", solrProduct.getUnit());//单位
                productMap.put("storeType", solrProduct.getStoreType());//店铺类型
                //获取起购量
                ProductSpu productSpu = new ProductSpu();
                productSpu.setPId(solrProduct.getPId());
                productSpu.setType(solrProduct.getType());
                productSpu.setPurchasingAmount(solrProduct.getPurchasingAmount());
                productMap.put("purchasingAmount", productSpuService.getPurchasingAmount(productSpu));
                solrProductList.add(productMap);
            }
            productListMap.put("productList", solrProductList);
            message = FYUtils.fy("查询成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, productListMap, null);
        } catch (Exception e) {
            logger.error("获取商品数据参数出现错误：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 查询商品(带分页)
     * 如果要查询不属于任何店铺分类的，scid请传入-1
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/page")
    public Map<String, Object> productPage() {
        // 处理参数
        String cid = R.get("cid"); //分类id
        String cname = R.get("canme"); //分类名称
        String k = R.get("k"); //关键字
        String sid = R.get("sid"); //店铺id
        String pids = R.get("pids"); //多个商品id
        String price = R.get("price"); //价格
        String bid = R.get("bid"); //品牌id
        String bname = R.get("bname"); //品牌名称
        String attr = R.get("attr"); //参数
        String isRecommend = R.get("isRecommend"); //是否推荐
        String scid = R.get("scid"); //店铺分类id
        String carId = R.get("carId"); //车型id
        String sort = R.get("sort"); //排序条件
        String sortMode = R.get("sortMode"); //排序方式
        String sDate = R.get("sDate"); //起始时间
        String eDate = R.get("eDate"); //结束时间
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isNotBlank(cid) && !StringUtils.isNumeric(cid)) {
            errorList.add(FYUtils.fy("分类id必须是数字"));
        }
        if (StringUtils.isNotBlank(sid) && !StringUtils.isNumeric(sid)) {
            errorList.add(FYUtils.fy("店铺id必须是数字"));
        }
        //isNumeric方法不认识负数，而-1意味着没有店铺分类
        if (StringUtils.isNotBlank(scid) && !StringUtils.isNumeric(scid)) {
            if (!scid.equals("-1")) {
                errorList.add(FYUtils.fy("店铺分类id必须是数字"));
            }
        }
        if (StringUtils.isNotBlank(carId) && !StringUtils.isNumeric(carId)) {
            errorList.add(FYUtils.fy("车型id必须是数字"));
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean dateflag = true;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(sDate) || StringUtils.isNotBlank(eDate)) {
                String sDate2 = "*";
                String eDate2 = "*";
                if (StringUtils.isNotBlank(sDate)) {
//					sDate2 = DateUtil.getThreadLocalDateFormat().format(format.parse(sDate));
                    sDate2 = format.format(format.parse(sDate));
                }
                if (StringUtils.isNotBlank(eDate)) {
//					eDate2 = DateUtil.getThreadLocalDateFormat().format(format.parse(eDate));
                    eDate2 = format.format(format.parse(eDate));
                }
                paramMap.put("update_date", "[" + sDate2 + " TO " + eDate2 + "]");
            }
            String message = ApiUtils.errorMessage(errorList);
            if (!errorList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            if (StringUtils.isNoneBlank(cid)) {
                paramMap.put("cate_parent_ids", cid);
            }
            if (StringUtils.isNotBlank(cname)) {
                paramMap.put("category_name", cname);
            }
            if (StringUtils.isNotBlank(k)) {
                paramMap.put("text", k);
            }
            if (StringUtils.isNotBlank(sid)) {
                paramMap.put("store_id", sid);
            }
            if (StringUtils.isNotBlank(pids)) {
                paramMap.put("id", pids);
            }
            if (StringUtils.isNotBlank(price) && price.indexOf("-") != -1) {
                int index = price.indexOf("-");
                String minPrice = price.substring(0, index);
                String maxPrice = price.substring(index + 1);
                String minPrice2 = "*";
                String maxPrice2 = "*";
                if (StringUtils.isNotBlank(minPrice) && StringUtils.isNumeric(minPrice)) {
                    minPrice2 = minPrice;
                }
                if (StringUtils.isNotBlank(maxPrice) && StringUtils.isNumeric(maxPrice)) {
                    maxPrice2 = maxPrice;
                }
                paramMap.put("price2", "[" + minPrice2 + " TO " + maxPrice2 + "]");
            }
            if (StringUtils.isNotBlank(bid)) {
                paramMap.put("brand_id", bid);
            }
            if (StringUtils.isNotBlank(bname)) {
                paramMap.put("brand_name", bname);
            }
            if (StringUtils.isNoneBlank(attr)) {
                String paramValue = "";
                String[] attrs = attr.split(",");
                if (attrs.length > 0) {
                    for (int i = 0; i < attrs.length; i++) {
                        String[] att = attrs[i].split(":");
                        if (att.length > 1) {
                            String[] attrKV = att[0].split("_");
                            String attK = "";
                            if (attrKV.length > 0) {
                                attK = attrKV[0];
                            }
                            for (int j = 0; j < att.length; j++) {
                                if (j == 0) {
                                    paramValue += att[j] + ",";
                                } else {
                                    paramValue += attK + "_" + att[j] + ",";
                                }
                            }
                        } else {
                            paramValue += attrs[i] + ",";
                        }
                    }
                }
                paramMap.put("param_value", paramValue);
            }
            if (StringUtils.isNotBlank(isRecommend)) {
                paramMap.put("is_recommend", isRecommend);
            }
            if (StringUtils.isNotBlank(scid)) {
                paramMap.put("store_cate_parent_ids", scid);
            }
            if (StringUtils.isNotBlank(carId)) {
                paramMap.put("car_ids", carId);
            }
            Map<String, String> sortMap = new HashMap<>();
            if (StringUtils.isNotBlank(sort)) {
                if (StringUtils.isBlank(sortMode)) {
                    sortMode = "";
                }
                sortMap.put(StringUtils.toUnderScoreCase(sort), sortMode);
            } else {
                sortMap.put("update_date", "desc");
            }
            //执行sql,并查出了结果集
            try {
                Page<SolrProduct> page = Page.newPage();// 从入参中取得Page分页对象
                Map<String, Object> resultMap = productSearch.search(paramMap, sortMap, page);
                Map<String, Object> productListMap = new LinkedHashMap<>();
                if (resultMap.size() == 0) {
                    message = FYUtils.fy("查询成功");
                    return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, resultMap, null);
                }
                //商品信息
                ResultSet<SolrProduct> result = (ResultSet<SolrProduct>) resultMap.get("search");
                if (result == null || result.getItems() == null) {
                    message = FYUtils.fy("查询成功");
                    return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, resultMap, null);
                }
                List<SolrProduct> solrProducts = result.getItems();
                List<Map<String, Object>> solrProductList = new ArrayList<>();
                for (int i = 0; i < solrProducts.size(); i++) {
                    Map<String, Object> productMap = new LinkedHashMap<>();
                    SolrProduct solrProduct = solrProducts.get(i);
                    productMap.put("pid", solrProduct.getPId());//商品id
                    productMap.put("name", solrProduct.getName());//商品名称
                    productMap.put("type", solrProduct.getType());//商品类型
                    productMap.put("image", solrProduct.getImage());//商品图片
                    productMap.put("minPrice", solrProduct.getMinPrice());//最低价
                    productMap.put("minPrice1", solrProduct.getMinPrice1());//商品零售价格
                    productMap.put("minPrice2", solrProduct.getMinPrice2());//商品批发价格
                    productMap.put("storeType", solrProduct.getStoreType());//店铺类型
                    //获取起购量
                    ProductSpu productSpu = new ProductSpu();
                    productSpu.setPId(solrProduct.getPId());
                    productSpu.setType(solrProduct.getType());
                    productSpu.setPurchasingAmount(solrProduct.getPurchasingAmount());
                    productMap.put("purchasingAmount", productSpuService.getPurchasingAmount(productSpu));
                    solrProductList.add(productMap);
                }
                productListMap.put("productList", solrProducts);
                //根据品牌id list查询多个品牌
                List<Long> brandIdList = (List<Long>) resultMap.get("brandIdList");
                List<ProductBrand> brandList = new ArrayList<>();
                if (!brandIdList.isEmpty()) {
                    brandList = productBrandService.selectByWhere(new Wrapper().and("brand_id in", brandIdList).and("status=", 1));
                }
                productListMap.put("brandList", brandList);
                //根据分类id list查询多个分类
                List<Long> categoryIdList = (List<Long>) resultMap.get("categoryIdList");
                List<ProductCategory> categoryList = new ArrayList<>();
                if (!categoryIdList.isEmpty()) {
                    categoryList = productCategoryService.selectByIdIn(categoryIdList);
                }
                productListMap.put("categoryList", categoryList);
                //商品参数
                Map<String, String> paramValueMap = (Map<String, String>) resultMap.get("paramValueMap");
                productListMap.put("paramValueMap", paramValueMap);
                message = FYUtils.fy("查询成功");
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, productListMap, page);
            } catch (Exception e) {
                logger.error("获取page错误:", e);
                message = FYUtils.fy("获取page错误");
                return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
            }
        } catch (Exception e) {
            logger.error("获取商品数据参数出现错误：" + e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 根据商品id查询商品参数
     *
     * @param pid   商品id
     * @param limit 查询数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/param/list")
    public Map<String, Object> productParamList(String pid, String limit) {
        // 处理参数
        List<String> errorList = validate(pid);
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            Integer limit2 = ApiUtils.getLimit(limit);
            ProductParamMapping param = new ProductParamMapping();
            param.setPId(Long.parseLong(pid));
            Page<ProductParamMapping> paramMappingPage = productParamMappingService.selectByWhere(new Page<ProductParamMapping>(1, limit2, limit2), new Wrapper(param));
            message = FYUtils.fy("查询成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, paramMappingPage.getList(), null);
        } catch (Exception e) {
            logger.error("查询商品参数错误:", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 根据商品id查询商品批发价
     *
     * @param pid   商品id
     * @param limit 查询数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/sectionPrice/list")
    public Map<String, Object> productSectionPriceList(String pid, String limit) {
        // 处理参数
        List<String> errorList = validate(pid);
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            Integer limit2 = ApiUtils.getLimit(limit);
            ProductSectionPrice productSectionPrice = new ProductSectionPrice();
            productSectionPrice.setPId(Long.parseLong(pid));
            Page<ProductSectionPrice> productSectonPricePage = productSectionPriceService.selectByWhere(new Page<ProductSectionPrice>(1, limit2, limit2), new Wrapper(productSectionPrice).orderBy("sort asc"));
            message = FYUtils.fy("查询成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, productSectonPricePage.getList(), null);
        } catch (Exception e) {
            logger.error("查询商品批发价错误:", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 根据商品id和颜色查询商品图片
     *
     * @param pid   商品id
     * @param color 颜色
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/image/list")
    public Map<String, Object> productImageList(String pid, String color) {
        // 处理参数
        List<String> errorList = validate(pid);
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        ProductPictureMapping productPictureMapping = new ProductPictureMapping();
        productPictureMapping.setPId(Long.parseLong(pid));
        if (StringUtils.isNotBlank(color)) {
            productPictureMapping.setColor(color);
        }
        try {
            List<ProductPictureMapping> productPictureMappingList = productPictureMappingService.selectByWhere(new Wrapper(productPictureMapping).orderBy("id asc"));
            List<StoreAlbumPicture> storeAlbumPictureList = new ArrayList<>();
            if (productPictureMappingList.isEmpty()) {
                message = FYUtils.fy("查询成功");
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, storeAlbumPictureList, null);
            }
            List<Long> imgIdList = new ArrayList<>();
            String imgIds = "";
            for (int i = 0; i < productPictureMappingList.size(); i++) {
                imgIdList.add(productPictureMappingList.get(i).getImgId());
                Boolean flag = i == productPictureMappingList.size() - 1;
                imgIds += productPictureMappingList.get(i).getImgId() + (flag ? "" : ",");
            }
            //按照in的原有顺序排序
            storeAlbumPictureList = storeAlbumPictureService.selectByWhere(new Wrapper().and("picture_id in", imgIdList).orderBy(" FIELD (picture_id," + imgIds + ")"));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, storeAlbumPictureList, null);
        } catch (Exception e) {
            logger.error("查询商品图片发生错误:", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 根据商品id查询商品详情
     *
     * @param pid 商品id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/detail")
    public Map<String, Object> productDetail(String pid) {
        // 处理参数
        List<String> errorList = validate(pid);
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            ProductDetail productDetail = productDetailService.selectById(Long.parseLong(pid));

            // 业务处理
            // 商品详情中的内容是富文本编辑器编辑的，也可能是从其它平台copy来的。
            // 需要处理商品详情中的图片的宽度，设置为width=100%，用以适应移动端。
            String intro=productDetail.getIntroduction();
            String contentHtml= HtmlUtils.imageWidth(intro);
            productDetail.setIntroduction(contentHtml);

            message = FYUtils.fy("查询成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, productDetail, null);
        } catch (Exception e) {
            logger.error("查询商品详情发生错误:", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 根据skuId获取商品sku
     *
     * @param skuId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/sku/one")
    public Map<String, Object> productSkuOne(String skuId) {
        //项目中暂无用到本接口，直接返回空
        return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, "暂无数据", null, null);
		/*// 处理参数
		List<String> errorList = new ArrayList<>();
		if(StringUtils.isBlank(skuId)){
			errorList.add("商品skuId不能为空");
		}
		if(StringUtils.isBlank(skuId) && !StringUtils.isNumeric(skuId)){
			errorList.add("商品skuId必须是数字");
		}
		String message=ApiUtils.errorMessage(errorList);
		if(!errorList.isEmpty()){
			return ApiUtils.getMap(ApiUtils.STATUS_INVALID,message,null,null);
		}
		try {
			ProductSku productSku=productSkuService.selectById(Long.parseLong(skuId));
			message="查询成功";
			return ApiUtils.getMap(ApiUtils.STATUS_OK,message,productSku,null);
		} catch (Exception e) {
			logger.error("查询商品sku发生错误:",e);
			message="服务器发生错误";
			return ApiUtils.getMap(ApiUtils.STATUS_SERVER_ERROR, message,null,null);
		}*/
    }

    /**
     * 根据spuId和多个skuId获取skuList获取sku
     *
     * @param pid    商品id
     * @param skuIds 多个skuIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/sku/list")
    public Map<String, Object> productSkuList(String pid, String skuIds) {
        //处理参数
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(pid) && StringUtils.isBlank(skuIds)) {
            errorList.add(FYUtils.fy("缺少参数"));
        }
        if (StringUtils.isNotBlank(pid) && !StringUtils.isNumeric(pid)) {
            errorList.add(FYUtils.fy("商品id必须是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        ProductSku productSku = new ProductSku();
        if (StringUtils.isNotBlank(pid)) {
            productSku.setPId(Long.parseLong(pid));
        }
        Wrapper wrapper = new Wrapper(productSku);
        if (StringUtils.isNotBlank(skuIds)) {
            Object[] skuIdss = skuIds.split(",");
            List<Object> skuIdList = Arrays.asList(skuIdss);
            wrapper.and("sku_id in", skuIdList);
        }
        try {
            List<ProductSku> productSkuList = productSkuService.selectByWhere(wrapper.orderBy("sku_id asc"));
            message = FYUtils.fy("查询成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, productSkuList, null);
        } catch (Exception e) {
            logger.error("查询商品skuList发生错误:", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 根据商品id和商品的数量获取批发价
     *
     * @param pid   商品id
     * @param count 商品数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/getSectionPrice")
    public Map<String, Object> getSectionPrice(String pid, String count) {
        List<String> errorList = validate(pid);
        if (StringUtils.isBlank(count)) {
            errorList.add(FYUtils.fy("商品数量不能为空"));
        }
        if (StringUtils.isNotBlank(count) && !StringUtils.isNumeric(count)) {
            errorList.add(FYUtils.fy("商品数量只能为数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            BigDecimal price = productSpuService.calculatePrice(Long.parseLong(pid), Integer.parseInt(count));
            message = FYUtils.fy("获取批发价成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, price, null);
        } catch (Exception e) {
            logger.error("查询商品批发价发生错误:", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 根据回复的评价id、评价类型、商品id、评价等级获取商品评价
     *
     * @param replyIds 回复的评价id
     * @param type     评价类型(0评论、1追评、2回复)
     * @param pid      商品id
     * @param grade    评价等级(1好评,2中评,3差评)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/comment/page")
    public Map<String, Object> productCommentPage(String replyIds, String type, String pid, String grade) {
        List<String> errorList = validateCommentParam(replyIds, pid, grade, type);
        if (StringUtils.isNotBlank(grade) && !StringUtils.isNumeric(grade)) {
            errorList.add(FYUtils.fy("评价等级只能是数字"));
        }
        if (StringUtils.isNotBlank(type) && !StringUtils.isNumeric(type)) {
            errorList.add(FYUtils.fy("评价类型只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        TradeComment tradeComment = new TradeComment();
        //类型，0评论、1追评、2回复
        if (StringUtils.isNotBlank(type)) {
            tradeComment.setType(type);
        }
        if (StringUtils.isNotBlank(pid)) {
            tradeComment.setPId(Long.parseLong(pid));
        }
        //评价等级(1好评,2中评,3差评)
        if (StringUtils.isNotBlank(grade)) {
            tradeComment.setGrade(grade);
        }
        // 是否显示，0否、1是
        tradeComment.setIsShow("1");
        Wrapper wrapper = new Wrapper(tradeComment);
        if (StringUtils.isNotBlank(replyIds)) {
            Object[] commentIdss = replyIds.split(",");
            List<Object> commentIdList = Arrays.asList(commentIdss);
            wrapper.and("reply_id in", commentIdList);
        }
        try {
            Page<TradeComment> tradeCommentPage = Page.newPage();
            tradeCommentPage = tradeCommentService.selectByWhere(tradeCommentPage, wrapper);
            //取头像
            for (TradeComment buffer : tradeCommentPage.getList()) {
                buffer.setHeadPicPath(buffer.getUserMain().getUserMember().getHeadPicPath());
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, tradeCommentPage.getList(), tradeCommentPage);
        } catch (Exception e) {
            logger.error("查询商品评价异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 根据多个回复的评价id、评价类型、商品id、评价等级获取商品评价
     *
     * @param replyIds 回复的评价id
     * @param type     评价类型(0评论、1追评、2回复)
     * @param pid      商品id
     * @param grade    评价等级(1好评,2中评,3差评)
     * @param limit    查询数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/comment/list")
    public Map<String, Object> productCommentList(String replyIds, String type, String pid, String grade, String limit) {
        List<String> errorList = validateCommentParam(replyIds, pid, grade, type);
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        TradeComment tradeComment = new TradeComment();
        //类型，0评论、1追评、2回复
        if (StringUtils.isNotBlank(type)) {
            tradeComment.setType(type);
        }
        if (StringUtils.isNotBlank(pid)) {
            tradeComment.setPId(Long.parseLong(pid));
        }
        //评价等级(1好评,2中评,3差评)
        if (StringUtils.isNotBlank(grade)) {
            tradeComment.setGrade(grade);
        }
        // 是否显示，0否、1是
        tradeComment.setIsShow("1");
        Wrapper wrapper = new Wrapper(tradeComment);
        if (StringUtils.isNotBlank(replyIds)) {
            Object[] commentIdss = replyIds.split(",");
            List<Object> commentIdList = Arrays.asList(commentIdss);
            wrapper.and("comment_id in", commentIdList);
        }
        try {
            Integer limit2 = ApiUtils.getLimit(limit);
            Page<TradeComment> tradeCommentPage = tradeCommentService.selectByWhere(new Page<TradeComment>(1, limit2, limit2), wrapper);
            //取头像
            for (TradeComment buffer : tradeCommentPage.getList()) {
                buffer.setHeadPicPath(buffer.getUserMain().getUserMember().getHeadPicPath());
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, tradeCommentPage.getList(), null);
        } catch (Exception e) {
            logger.error("查询商品评价异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 根据多个评价id获取评价的图片
     *
     * @param commentIds 多个评价id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/comment/image/list")
    public Map<String, Object> productCommentImageList(String commentIds) {
        if (StringUtils.isBlank(commentIds)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("评论id不能为空"), null, null);
        }
        Object[] commentIdss = commentIds.split(",");
        List<Object> commentIdList = Arrays.asList(commentIdss);
        try {
            List<TradeCommentImage> tradeCommentImageList = tradeCommentImageService.selectByWhere(new Wrapper().and("comment_id in", commentIdList));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询成功"), tradeCommentImageList, null);
        } catch (Exception e) {
            logger.error("查询评价图片异常：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 查询咨询或回复
     *
     * @param consultationIds 多个咨询ids
     * @param pid             商品id
     * @param type            类别，0咨询、1回复
     * @param category        咨询类型(数据字典(1商品咨询,2支付问题,3发票及保修,4促销及赠品))
     * @param isReply         是否回复
     * @param limit           数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/consultation/list")
    public Map<String, Object> productConsultationList(String consultationIds, String pid, String type, String category, String isReply, String limit) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(consultationIds) && StringUtils.isBlank(pid)) {
            errorList.add(FYUtils.fy("请输入咨询id或商品id"));
        }
        if (StringUtils.isNotBlank(pid) && !StringUtils.isNumeric(pid)) {
            errorList.add(FYUtils.fy("商品id只能是数字"));
        }
        if (StringUtils.isNotBlank(category) && !StringUtils.isNumeric(category)) {
            errorList.add(FYUtils.fy("咨询分类只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        List<Long> consultationIdList = new ArrayList<>();
        TradeConsultation tradeConsultation = new TradeConsultation();
        if (StringUtils.isNotBlank(consultationIds)) {
            String[] consultationIdArray = consultationIds.split(",");
            for (int i = 0; i < consultationIdArray.length; i++) {
                if (StringUtils.isNumeric(consultationIdArray[i])) {
                    consultationIdList.add(Long.parseLong(consultationIdArray[i]));
                }
            }
        }
        if (StringUtils.isNotBlank(pid)) {
            tradeConsultation.setPId(Long.parseLong(pid));
        }
        // 类别，0咨询、1回复
        if (StringUtils.isNotBlank(type)) {
            tradeConsultation.setType(type);
        } else {
            tradeConsultation.setType("0");
        }
        if (StringUtils.isNotBlank(category)) {
            tradeConsultation.setCategory(category);
        }
        // 是否显示，0否、1是
        tradeConsultation.setIsShow("1");
        //是否回复，0否、1是
        if (StringUtils.isNotBlank(isReply)) {
            tradeConsultation.setIsReply(isReply);
        }
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(tradeConsultation);
        if (!consultationIdList.isEmpty()) {
            wrapper.and("a.reply_id in", consultationIdList);
        }
        try {
            Integer defaultLimit = ApiUtils.getLimit(limit);
            Page<TradeConsultation> tradeConsultationPage = tradeConsultationService.selectByWhere(new Page<TradeConsultation>(1, defaultLimit, defaultLimit), wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询咨询或回复成功"), tradeConsultationPage.getList(), null);
        } catch (Exception e) {
            logger.error("查询咨询异常：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 查询咨询或回复(分页)
     *
     * @param consultationIds 多个咨询ids
     * @param pid             商品id
     * @param type            类别，0咨询、1回复
     * @param category        咨询类型(数据字典(1商品咨询,2支付问题,3发票及保修,4促销及赠品))
     * @param isReply         是否回复
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/consultation/page")
    public Map<String, Object> productConsultationPage(String consultationIds, String pid, String type, String category, String isReply) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(consultationIds) && StringUtils.isBlank(pid)) {
            errorList.add(FYUtils.fy("请输入咨询id或商品id"));
        }
        if (StringUtils.isNotBlank(pid) && !StringUtils.isNumeric(pid)) {
            errorList.add(FYUtils.fy("商品id只能是数字"));
        }
        if (StringUtils.isNotBlank(category) && !StringUtils.isNumeric(category)) {
            errorList.add(FYUtils.fy("咨询分类只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        List<Long> consultationIdList = new ArrayList<>();
        TradeConsultation tradeConsultation = new TradeConsultation();
        if (StringUtils.isNotBlank(consultationIds)) {
            String[] consultationIdArray = consultationIds.split(",");
            for (int i = 0; i < consultationIdArray.length; i++) {
                if (StringUtils.isNumeric(consultationIdArray[i])) {
                    consultationIdList.add(Long.parseLong(consultationIdArray[i]));
                }
            }
        }
        if (StringUtils.isNotBlank(pid)) {
            tradeConsultation.setPId(Long.parseLong(pid));
        }
        // 类别，0咨询、1回复
        if (StringUtils.isNotBlank(type)) {
            tradeConsultation.setType(type);
        } else {
            tradeConsultation.setType("0");
        }
        if (StringUtils.isNotBlank(category)) {
            tradeConsultation.setCategory(category);
        }
        // 是否显示，0否、1是
        tradeConsultation.setIsShow("1");
        //是否回复，0否、1是
        if (StringUtils.isNotBlank(isReply)) {
            tradeConsultation.setIsReply(isReply);
        }
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(tradeConsultation);
        if (!consultationIdList.isEmpty()) {
            wrapper.and("a.reply_id in", consultationIdList);
        }
        try {
            Page<TradeConsultation> tradeConsultationPage = Page.newPage();
            tradeConsultationPage = tradeConsultationService.selectByWhere(tradeConsultationPage, wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询咨询或回复成功"), tradeConsultationPage.getList(), tradeConsultationPage);
        } catch (Exception e) {
            logger.error("查询咨询异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 验证参数
     *
     * @param pid
     * @return
     */
    private List<String> validate(String pid) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(pid)) {
            errorList.add(FYUtils.fy("商品id不能为空"));
        }
        if (StringUtils.isBlank(pid) && !StringUtils.isNumeric(pid)) {
            errorList.add(FYUtils.fy("商品id必须是数字"));
        }
        return errorList;
    }

    /**
     * 验证商品评价的参数
     *
     * @param pid   商品id
     * @param grade 评价等级
     * @param type  评价类型
     * @return
     */
    private List<String> validateCommentParam(String commentIds, String pid, String grade, String type) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(pid) && StringUtils.isBlank(commentIds)) {
            errorList.add(FYUtils.fy("缺少参数"));
        }
        if (StringUtils.isBlank(pid) && !StringUtils.isNumeric(pid)) {
            errorList.add(FYUtils.fy("商品id必须是数字"));
        }
        if (StringUtils.isNotBlank(grade) && !StringUtils.isNumeric(grade)) {
            errorList.add(FYUtils.fy("评价等级只能是数字"));
        }
        if (StringUtils.isNotBlank(type) && !StringUtils.isNumeric(type)) {
            errorList.add(FYUtils.fy("评价类型只能是数字"));
        }
        return errorList;
    }

    /**
     * 获取商品分类列表
     *
     * @param version  接口版本号
     * @param parentId 父级商品类型id，可不传。不传或0会返回一级商品列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/product/productCategory/list", produces = {"application/json; charset=UTF-8"})
    public Object adList(@PathVariable String version, Long parentId) {
        try {
            if (parentId == null || parentId < 0) {
                parentId = 0L;
            }
            //查询商品
            Wrapper wrapper = new Wrapper();
            wrapper.and("parent_id = ", parentId);
            wrapper.orderBy("sort asc");
            List<ProductCategory> productCategories = productCategoryService.selectByWhere(wrapper);
            //循环处理不该返回的字段，并查询每个分类下的推荐位
            for (ProductCategory productCategory : productCategories) {
                productCategory.setSiteRecommends(siteRecommendProductCategoryService.queryProductCategoryRecommend(productCategory.getCategoryId()));
                productCategory.setCreateDate(null);
                productCategory.setUpdateDate(null);
                productCategory.setCommission(null);
                productCategory.setIsLocked(null);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取商品分类成功"), productCategories, null);
        } catch (Exception e) {
            logger.error("获取商品分类失败，异常内容：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取商品分类失败"), null, null);
        }
    }
}
