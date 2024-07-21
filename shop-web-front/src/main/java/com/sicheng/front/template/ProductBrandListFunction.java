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
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.product.service.ProductBrandService;
import com.sicheng.seller.product.service.ProductCategoryService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，ProductBrandListFunction函数
 * </p>
 * <p>
 * 描述: 根据品牌name获取品牌列表,不分页
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class ProductBrandListFunction implements Function {

    public static final String CID = "cid";
    public static final String NAME = "name";
    public static final String BIDS = "bids";

    public List<ProductBrand> call(Object[] args, Context ctx) {
        //处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long cid = TagUtils.getLong(tagParamMap, CID);
        String name = TagUtils.getString(tagParamMap, NAME);
        String bids = TagUtils.getString(tagParamMap, BIDS);
        Wrapper wrapper = new Wrapper();
        if (cid != null) {
            ProductCategoryService categoryService = SpringContextHolder.getBean(ProductCategoryService.class);
            ProductCategory productCategory = categoryService.selectById(cid);
            List<ProductCategory> categoryList = categoryService.selectByWhere(new Wrapper().and("parent_ids like", "%," + cid + ",%"));
            if (productCategory != null) {
                categoryList.add(productCategory);
            }
            if (categoryList.size() == 0) {
                return new ArrayList<>();
            }
            List<Object> brandIdList = new ArrayList<>();
            for (ProductCategory cate : categoryList) {
                String brandIds = cate.getBrandIds();
                if (StringUtils.isBlank(brandIds)) {
                    continue;
                }
                String[] brandIdss = brandIds.split(",");
                if (brandIdss.length > 0) {
                    for (String id : brandIdss) {
                        brandIdList.add(id);
                    }
                }
            }
            wrapper.and("brand_id in", brandIdList);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.and("name=", name);
        }
        if (StringUtils.isNotBlank(bids)) {
            String[] bidss = bids.split(",");
            List<Long> bidList = new ArrayList<>();
            for (int i = 0; i < bidss.length; i++) {
                bidList.add(Long.parseLong(bidss[i]));
            }
            wrapper.and("brand_id in", bidList);
        }
        //审核状态，0待审、1通过、2未通过
        wrapper.and("status=", "1");
        //按sort排序
        wrapper.orderBy("sort");
        ProductBrandService brandService = SpringContextHolder.getBean(ProductBrandService.class);
        //执行业务，查询出产品分类列表
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        Page<ProductBrand> productBrandPage = brandService.selectByWhere(new Page<ProductBrand>(1, limit, limit), wrapper);
        return productBrandPage.getList();
    }
}