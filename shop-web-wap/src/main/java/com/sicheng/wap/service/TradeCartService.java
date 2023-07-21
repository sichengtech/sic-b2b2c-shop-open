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
package com.sicheng.wap.service;

import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.dao.TradeCartDao;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车Service
 *
 * @author fxx
 * @version 2017-01-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeCartService extends CrudService<TradeCartDao, TradeCart> {

    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private MemberAddressService memberAddressService;

    /**
     * 根据pId,一个或多个skuId、数量，建立一个购物车list
     *
     * @param userMember 会员
     * @param pId        商品id
     * @param skuMsg     商品sku信息(skuId_数量,skuId_数量)
     * @param type       商品类型，1零售，2批发(根据用户身份判断用零售价还是批发价)
     * @return
     */
    public List<TradeCart> selectBySkus(UserMember userMember, Long pId, String skuMsg, String type) {
        if (StringUtils.isBlank(skuMsg) || pId == null) {
            return new ArrayList<>();
        }
        ProductSpu productSpu = productSpuService.selectById(pId);
        String[] skus = skuMsg.split(",");
        if (skus.length == 0) {
            return new ArrayList<>();
        }
        if (userMember == null) {
            userMember = AppTokenUtils.findUser().getUserMember();
        }
        List<TradeCart> tradeCartList = new ArrayList<>();
        Integer totalCount = 0;
        for (int i = 0; i < skus.length; i++) {
            TradeCart cart = new TradeCart();
            cart.setPId(pId);
            cart.setUId(userMember.getUId());
            if (productSpu != null) {
                cart.setStoreId(productSpu.getStoreId());
            }
            //收货地址
            List<MemberAddress> addressList = memberAddressService.selectByWhere(new Wrapper().and("u_id=", userMember.getUId()).orderBy("is_default desc"));//属主检查
            String[] sku = skus[i].split("-");
            if (sku.length < 2) {
                continue;
            }
            //skuId
            Long skuId = Long.parseLong(sku[0]);
            //数量
            Integer count = Integer.parseInt(sku[1]);
            totalCount += count;
            cart.setCount(count);
            //计算运费
            //如果当前用户还没有收货地址，暂时把运费设为0
            if (addressList.isEmpty()) {
                cart.getProductSpu().setExpressPrice(new BigDecimal("0"));
            } else {
                cart.getProductSpu().setExpressPrice(tradeOrderService.calculateFreight(cart.getProductSpu(), addressList.get(0),cart.getCount()));
            }
            ProductSku productSku = productSkuService.selectById(skuId);
            String skuValue = "";
            if (productSku == null) {
                continue;
            }
            if (StringUtils.isNotBlank(productSku.getSpec1()) && StringUtils.isNotBlank(productSku.getSpec1V())) {
                String[] spece1s = productSku.getSpec1().split("_");
                String spece1Name = "";
                if (spece1s.length > 1) {
                    spece1Name = spece1s[1];
                }
                skuValue += spece1Name + ":" + productSku.getSpec1V();
            }
            if (StringUtils.isNotBlank(productSku.getSpec2()) && StringUtils.isNotBlank(productSku.getSpec2V())) {
                String[] spece2s = productSku.getSpec2().split("_");
                String spece2Name = "";
                if (spece2s.length > 1) {
                    spece2Name = spece2s[1];
                }
                skuValue += "," + spece2Name + ":" + productSku.getSpec2V();
            }
            if (StringUtils.isNotBlank(productSku.getSpec3()) && StringUtils.isNotBlank(productSku.getSpec3V())) {
                String[] spece3s = productSku.getSpec2().split("_");
                String spece3Name = "";
                if (spece3s.length > 1) {
                    spece3Name = spece3s[1];
                }
                skuValue += "," + spece3Name + ":" + productSku.getSpec3V();
            }
            //零售：总价=单价*数量
            if ("1".equals(type)) {
                cart.setPrice(productSku.getPrice());
                cart.setPriceSum(productSku.getPrice().multiply(new BigDecimal(count)));
            }
            cart.setSkuId(skuId);
            cart.setSkuValue(skuValue);
            tradeCartList.add(cart);

        }
        //批发：根据数量算价格
        if ("2".equals(type)) {
            BigDecimal price = productSpuService.calculatePrice(pId, totalCount);
            if (price != null) {
                for (int i = 0; i < tradeCartList.size(); i++) {
                    tradeCartList.get(i).setPrice(price);
                    tradeCartList.get(i).setPriceSum(price.multiply(new BigDecimal(tradeCartList.get(i).getCount())));
                    tradeCartList.get(i).getProductSku().setPrice(price);
                }
            }
        }
        return tradeCartList;
    }

}