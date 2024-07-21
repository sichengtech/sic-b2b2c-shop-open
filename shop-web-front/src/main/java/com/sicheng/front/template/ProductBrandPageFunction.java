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
 * 标题: 自定义函数，ProductBrandPageFunction函数
 * </p>
 * <p>
 * 描述: 根据品牌name获取品牌列表,带分页
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class ProductBrandPageFunction implements Function {

    public static final String CID = "cid";
    public static final String NAME = "name";

    public Page<ProductBrand> call(Object[] args, Context ctx) {
        //处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long cid = TagUtils.getLong(tagParamMap, CID);
        String name = TagUtils.getString(tagParamMap, NAME);
        Wrapper wrapper = new Wrapper();
        if (cid != null) {
            ProductCategoryService categoryService = SpringContextHolder.getBean(ProductCategoryService.class);
            ProductCategory productCategory = categoryService.selectById(cid);
            List<ProductCategory> categoryList = categoryService.selectByWhere(new Wrapper().and("parent_ids like", "%," + cid + ",%"));
            if (productCategory != null) {
                categoryList.add(productCategory);
            }
            if (categoryList.size() == 0) {
                return new Page<>();
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
        //审核状态，0待审、1通过、2未通过
        wrapper.and("status=", "1");
        //按sort排序
        wrapper.orderBy("sort");
        //执行业务，查询出产品分类列表
        ProductBrandService brandService = SpringContextHolder.getBean(ProductBrandService.class);
        Page page = TagUtils.getPage(tagParamMap);// 从入参中取得Page分页对象
        page = brandService.selectByWhere(page, wrapper);
        return page;
    }
}