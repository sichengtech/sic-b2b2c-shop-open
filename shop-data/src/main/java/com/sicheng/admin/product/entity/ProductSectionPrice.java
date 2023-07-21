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
package com.sicheng.admin.product.entity;

import java.math.BigDecimal;

/**
 * 商品区间价 Entity 子类，请把你的业务代码写在这里
 *
 * @author 赵磊
 * @version 2017-02-07
 */
public class ProductSectionPrice extends ProductSectionPriceBase<ProductSectionPrice> {

    private static final long serialVersionUID = 1L;

    public ProductSectionPrice() {
        super();
    }

    public ProductSectionPrice(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    /**
     * 获取价格（去掉无用小数点0）
     */
    @Override
    public BigDecimal getPrice() {
        if (super.getPrice() == null) {
            return super.getPrice();
        }
        String price = super.getPrice().stripTrailingZeros().toPlainString();
        return new BigDecimal(price);
    }
}