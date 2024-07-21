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
package com.sicheng.wap.service;

import com.sicheng.admin.product.dao.ProductSpuDao;
import com.sicheng.admin.product.entity.ProductSectionPrice;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品 Service
 *
 * @author zhaolei
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProductSpuService extends CrudService<ProductSpuDao, ProductSpu> {

    @Autowired
    private ProductSectionPriceService productSectionPriceService;

    /**
     * 获取商品起购量
     * 零售型商品直接获取，
     * 批发型商品获取商品阶梯价的最低价
     * 零售加批发型商品根据用户类型获取，采购商类型获取商品阶梯价的最低价，非采购商不限制起购量，默认是1
     *
     * @param productSpu
     * @return
     */
    public String getPurchasingAmount(ProductSpu productSpu) {
        String purchasingAmount = "1";
        if (productSpu == null) {
            return purchasingAmount;
        }
        //销售类型type:1零售，2批发，3混批
        if ("1".equals(productSpu.getType())) {
            return productSpu.getPurchasingAmount() == null ? purchasingAmount : productSpu.getPurchasingAmount();
        }
        UserMain userMain = AppTokenUtils.findUser();
        if ((userMain == null || !userMain.isTypeUserPurchaser()) && "3".equals(productSpu.getType())) {
            return purchasingAmount;
        }
        List<ProductSectionPrice> productSectionPriceList = productSpu.getProductSectionPriceList();
        if (productSectionPriceList.isEmpty() || StringUtils.isBlank(productSectionPriceList.get(0).getSection())) {
            return purchasingAmount;
        }
        return productSectionPriceList.get(0).getSection();

    }

    /**
     * 批发模式下,根据商品数量计算价格
     *
     * @param pId        商品id
     * @param totalCount 商品数量
     * @return
     */
    public BigDecimal calculatePrice(Long pId, Integer totalCount) {
        List<ProductSectionPrice> sectionPriceList = productSectionPriceService.selectByWhere(new Wrapper().and("p_id = ", pId).orderBy("sort"));
        BigDecimal price = new BigDecimal("0");
        if (!sectionPriceList.isEmpty()) {
            if (totalCount <= Long.parseLong(sectionPriceList.get(0).getSection())) {
                price = sectionPriceList.get(0).getPrice();
            }
            if (totalCount >= Long.parseLong(sectionPriceList.get(sectionPriceList.size() - 1).getSection())) {
                price = sectionPriceList.get(sectionPriceList.size() - 1).getPrice();
            }
            if (price.equals(new BigDecimal("0"))) {
                for (int i = 0; i < sectionPriceList.size() - 1; i++) {
                    Long section1 = Long.parseLong(sectionPriceList.get(i).getSection());
                    Long section2 = Long.parseLong(sectionPriceList.get(i + 1).getSection());
                    if (totalCount >= section1 && totalCount < section2) {
                        price = sectionPriceList.get(i).getPrice();
                        break;
                    }
                }
            }
        }
        return price;
    }

}