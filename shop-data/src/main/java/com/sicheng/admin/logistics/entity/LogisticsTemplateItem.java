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
package com.sicheng.admin.logistics.entity;

import java.math.BigDecimal;

/**
 * 运费模板详情 Entity 子类，请把你的业务代码写在这里
 *
 * @author 范秀秀
 * @version 2017-02-20
 */
public class LogisticsTemplateItem extends LogisticsTemplateItemBase<LogisticsTemplateItem> {

    private static final long serialVersionUID = 1L;

    public LogisticsTemplateItem() {
        super();
    }

    public LogisticsTemplateItem(Long id) {
        super(id);
    }

    /**
     * 首重(元)
     */
    @Override
    public BigDecimal getFirstPrice() {
        if (super.getFirstPrice() == null) {
            return super.getFirstPrice();
        }
        String firstPrice = super.getFirstPrice().stripTrailingZeros().toPlainString();
        return new BigDecimal(firstPrice);
    }
}