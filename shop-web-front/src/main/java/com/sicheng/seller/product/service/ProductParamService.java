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
package com.sicheng.seller.product.service;

import com.sicheng.admin.product.dao.ProductParamDao;
import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.admin.product.entity.ProductParam;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数和参数值 Service
 *
 * @author 赵磊
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProductParamService extends CrudService<ProductParamDao, ProductParam> {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 根据分类id查询参数，如果当前分类没有，就找父级分类
     *
     * @param categoryId
     * @return
     */
    public List<ProductParam> selectParamByCategoryId(Long categoryId) {
        List<ProductParam> productParamList = new ArrayList<>();
        if (categoryId == null) {
            return productParamList;
        }
        ProductCategory productCategory = productCategoryService.selectById(categoryId);
        if (productCategory == null) {
            return productParamList;
        }
        //循环找父级分类的参数
        for (int i = 0; i < 5; i++) {
            ProductParam param = new ProductParam();
            param.setCategoryId(categoryId);
            param.setIsDisplay("1");// 是否显示，0否1是
            productParamList = super.selectByWhere(new Wrapper(param).orderBy("param_sort"));
            if (!productParamList.isEmpty() || productCategory.getParent() == null) {
                break;
            } else {
                categoryId = productCategory.getParent().getCategoryId();
            }
        }
        return productParamList;
    }

}