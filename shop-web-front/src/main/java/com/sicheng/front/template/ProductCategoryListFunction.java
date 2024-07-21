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

import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.product.service.ProductCategoryService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，ProductCategoryFunction函数
 * </p>
 * <p>
 * 描述: 根据分类naem获取分类列表，不分页
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author fxx
 * @version 2017年5月22日 上午10:59:10
 */
public class ProductCategoryListFunction implements Function {

    public static final String NAME = "name";
    public static final String PARENT_ID = "parentId";

    public List<ProductCategory> call(Object[] args, Context ctx) {
        //处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        String name = TagUtils.getString(tagParamMap, NAME);
        Long parentId = TagUtils.getLong(tagParamMap, PARENT_ID);
        ProductCategory productCategory = new ProductCategory();
        if (StringUtils.isNotBlank(name)) {
            productCategory.setName(name);
        }
        if (parentId != null) {
            ProductCategory parentCategory = new ProductCategory();
            parentCategory.setCategoryId(parentId);
            productCategory.setParent(parentCategory);
        }
        //是否锁定：1是，0否
        productCategory.setIsLocked("0");
        ProductCategoryService service = SpringContextHolder.getBean(ProductCategoryService.class);
        //执行业务，查询出产品分类列表
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        Page<ProductCategory> productCategoryPage = service.selectByWhere(new Page<ProductCategory>(1, limit, limit), new Wrapper(productCategory));
        return productCategoryPage.getList();
    }
}