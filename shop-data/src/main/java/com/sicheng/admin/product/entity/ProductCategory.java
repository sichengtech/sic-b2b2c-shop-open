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
package com.sicheng.admin.product.entity;

import com.sicheng.admin.product.dao.ProductCategoryDao;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品分类 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-14
 */
public class ProductCategory extends ProductCategoryBase<ProductCategory> {

    private static final long serialVersionUID = 1L;

    public ProductCategory() {
        super();
    }

    public ProductCategory(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    private String paramValues;     //参数名

    public String getParamValues() {
        return paramValues;
    }

    //推荐位集合
    private List<String> siteRecommends;

    public void setParamValues(String paramValues) {
        this.paramValues = paramValues;
    }

    private String productParentNames;  //父级名字

    public String getProductParentNames() {
        if (!"0,".equals(this.parentIds)) {
            ProductCategoryDao dao = SpringContextHolder.getBean(ProductCategoryDao.class);
            String[] categoryIds = this.getParentIds().split(",");
            StringBuffer categoryNames = new StringBuffer();
            for (int i = 0; i < categoryIds.length; i++) {
                if (!"0".equals(categoryIds[i])) {
                    ProductCategory productCategory = dao.selectById(Long.parseLong(categoryIds[i]));
                    categoryNames.append("-");
                    categoryNames.append(productCategory.getName());
                }
            }
            productParentNames = categoryNames.substring(1);
        }
        return productParentNames;
    }

    public void setProductParentNames(String productParentNames) {
        this.productParentNames = productParentNames;
    }

    private Long level; //等级

    public Long getLevel() {
        String[] carIds = this.getParentIds().split(",");
        if (carIds.length == 1) {
            level = 1L;
        }
        if (carIds.length == 2) {
            level = 2L;
        }
        if (carIds.length == 3) {
            level = 3L;
        }
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    /**
     * 佣金比例（去掉无用小数点0）
     */
    @Override
    public BigDecimal getCommission() {
        if (super.getCommission() == null) {
            return super.getCommission();
        }
        String commission = super.getCommission().stripTrailingZeros().toPlainString();
        return new BigDecimal(commission);
    }

    public List<String> getSiteRecommends() {
        return siteRecommends;
    }

    public void setSiteRecommends(List<String> siteRecommends) {
        this.siteRecommends = siteRecommends;
    }
}