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
package com.sicheng.front.template;

import com.sicheng.admin.product.entity.ProductBrand;
import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.search.ProductSearch4Solr;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.search.ProductSearchInterface;
import com.sicheng.seller.product.service.ProductBrandService;
import com.sicheng.seller.product.service.ProductCategoryService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，ProductPageFunction函数
 * </p>
 * <p>
 * 描述: 根据零个或多个条件获取商品列表,带分页
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class ProductPageFunction implements Function {

    /*查询条件：分类id、分类name、关键字、店铺id、车型、ids、价格(10-100),品牌id、品牌name
    参数：attr=1039_广州,1098_4g,1099_100-200,1023_xxl:xl
    排序条件：价格、销量、综合、收藏、综合,评论量
    排序方式：升序、降序*/
    public static final String CID = "cid";//分类id
    public static final String CNAME = "cname";//分类名称
    public static final String K = "k";//关键字
    public static final String SID = "sid";//店铺id
    public static final String PIDS = "pids";//多个商品id
    public static final String PRICE = "price";//价格
    public static final String BID = "bid";//品牌id
    public static final String BNAME = "bname";//品牌名称
    public static final String ATTR = "attr";//参数
    public static final String ISRECOMMEND = "isRecommend";//是否推荐
    public static final String SCID = "scid";//店铺分类id
    public static final String CARID = "carId";//车型id
    public static final String SORT = "sort";//排序条件
    public static final String SORT_MODE = "sortMode";//排序方式

    public Map<String, Object> call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        //Wrapper wrapper=new Wrapper();
        Long cid = TagUtils.getLong(tagParamMap, CID);
        String cname = TagUtils.getString(tagParamMap, CNAME);
        String k = TagUtils.getString(tagParamMap, K);
        Long sid = TagUtils.getLong(tagParamMap, SID);
        String pids = TagUtils.getString(tagParamMap, PIDS);
        String price = TagUtils.getString(tagParamMap, PRICE);
        String bid = TagUtils.getString(tagParamMap, BID);
        String bname = TagUtils.getString(tagParamMap, BNAME);
        String attr = TagUtils.getString(tagParamMap, ATTR);
        String isRecommend = TagUtils.getString(tagParamMap, ISRECOMMEND);
        Long scid = TagUtils.getLong(tagParamMap, SCID);
        Long carId = TagUtils.getLong(tagParamMap, CARID);
        String sort = TagUtils.getString(tagParamMap, SORT);
        String sortMode = TagUtils.getString(tagParamMap, SORT_MODE);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (cid != null) {
            //wrapper.and("cate_parent_ids like","%,"+cid+",%");
            paramMap.put("cate_parent_ids", cid);
        }
        if (StringUtils.isNotBlank(cname)) {
            //wrapper.and("category_name=",cname);
            paramMap.put("category_name", cname);
        }
        if (StringUtils.isNotBlank(k)) {
            //wrapper.and("name like","%"+k+"%");
            paramMap.put("text", k);
        }
        if (sid != null) {
            //wrapper.and("store_id=",sid);
            paramMap.put("store_id", sid);
        }
        if (StringUtils.isNotBlank(pids)) {
            paramMap.put("id", pids);
        }
        if (StringUtils.isNotBlank(price)) {
            int index = price.indexOf("-");
            if (index != -1) {
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
        }
        if (bid != null) {
            if (StringUtils.isNotBlank(bid)) {
                paramMap.put("brand_id", bid);
            }
        }
        if (StringUtils.isNotBlank(bname)) {
            //wrapper.and("brand_name=",bname);
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
                                //wrapper.andNew("param_value like","%,"+att[j]+",%");
                            } else {
                                paramValue += attK + "_" + att[j] + ",";
                                //wrapper.or("param_value like","%,"+attK+"_"+att[j]+",%");
                            }
                        }
                    } else {
                        paramValue += attrs[i] + ",";
                        //wrapper.and("param_value like","%,"+attrs[i]+",%");
                    }
                }
            }
            paramMap.put("param_value", paramValue);
        }
        if (StringUtils.isNotBlank(isRecommend)) {
            //wrapper.and("is_recommend =",isRecommend);
            paramMap.put("is_recommend", isRecommend);
        }
        if (scid != null) {
            //wrapper.and("store_cate_parent_ids like","%,"+scid+",%");
            paramMap.put("store_cate_parent_ids", scid);
        }
        if (carId != null) {
            //wrapper.and("car_ids like","%,"+carId+",%");
            paramMap.put("car_ids", carId);
        }
        Map<String, String> sortMap = new HashMap<String, String>();
        if (StringUtils.isNotBlank(sort)) {
            if (StringUtils.isBlank(sortMode)) {
                sortMode = "";
            }
            //wrapper.orderBy(StringUtils.toUnderScoreCase(sort)+" "+sortMode);
            sortMap.put(StringUtils.toUnderScoreCase(sort), sortMode);
        } else {
            //wrapper.orderBy("create_date");
            sortMap.put("update_date", "desc");
        }
        //SolrProductService service=SpringContextHolder.getBean(SolrProductService.class);
        //执行sql,并查出了结果集
        Page page = TagUtils.getPage(tagParamMap);// 从入参中取得Page分页对象
        //page=service.selectByWhere(page,wrapper);

        ProductSearchInterface instance= SpringContextHolder.getBean(ProductSearchInterface.class);
        Map<String, Object> resultMap = instance.search(paramMap, sortMap, page);
        //根据品牌id list查询多个品牌
        List<Long> brandIdList = (List<Long>) resultMap.get("brandIdList");
        ProductBrandService brandService = SpringContextHolder.getBean(ProductBrandService.class);
        //List<ProductBrand> brandList=brandService.selectByIdIn(brandIdList);
        List<ProductBrand> brandList = brandService.selectByWhere(new Wrapper().and("brand_id in", brandIdList).and("status=", 1));
        //根据分类id list查询多个分类
        List<Long> categoryIdList = (List<Long>) resultMap.get("categoryIdList");
        ProductCategoryService categoryService = SpringContextHolder.getBean(ProductCategoryService.class);
        List<ProductCategory> categoryList = categoryService.selectByIdIn(categoryIdList);
        resultMap.put("brandList", brandList);
        resultMap.put("categoryList", categoryList);
        resultMap.put("page", page);
        return resultMap;
    }
}