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
package com.sicheng.service;

import com.sicheng.admin.product.dao.ProductSkuDao;
import com.sicheng.admin.product.entity.ProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>标题: TradeStockService</p>
 * <p>描述: 库存 管理</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fxx
 * @version 2017年7月7日 下午5:29:26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeStockService {

    @Autowired
    private ProductSkuDao productSkuDao;

    /**
     * 增加可销售库存
     *
     * @param skuId  商品skuId
     * @param number 增加库存数量
     */
    public void addSalableStock(Long skuId, Integer number) {
        ProductSku sku = getProductSku(skuId, number);
        if (sku == null) {
            return;
        }
        //Long salableStock=sku.getStock()==null?0L:sku.getStock();
        //sku.setStock(salableStock+number);
        //productSkuDao.updateByIdSelective(sku);
        productSkuDao.addStockById(skuId, number);
    }

    /**
     * 减少可销售库存
     *
     * @param skuId  商品skuId
     * @param number 增加库存数量
     */
    public void reduceSalableStock(Long skuId, Integer number) {
        ProductSku sku = getProductSku(skuId, number);
        if (sku == null) {
            return;
        }
		/*Long oldSalableStock=sku.getStock()==null?0L:sku.getStock();
		Long newStock=oldSalableStock-number<0?0L:oldSalableStock-number;
		sku.setStock(newStock);
		productSkuDao.updateByIdSelective(sku);*/
        productSkuDao.reduceStockById(skuId, number);
    }

    /**
     * 增加占用库存
     *
     * @param skuId  商品skuId
     * @param number 增加占用库存数量
     */
    public void addOccupyStock(Long skuId, Integer number) {
        ProductSku sku = getProductSku(skuId, number);
        if (sku == null) {
            return;
        }
		/*Long occupyStock=sku.getOccupyStock()==null?0L:sku.getOccupyStock();
		sku.setOccupyStock(occupyStock+number);
		productSkuDao.updateByIdSelective(sku);*/
        productSkuDao.addOccupyStockById(skuId, number);
    }

    /**
     * 减少占用库存
     *
     * @param skuId  商品skuId
     * @param number 增加占用库存数量
     */
    public void reduceOccupyStock(Long skuId, Integer number) {
        ProductSku sku = getProductSku(skuId, number);
        if (sku == null) {
            return;
        }
		/*Long oldOccupyStock=sku.getOccupyStock()==null?0L:sku.getOccupyStock();
		Long newOccupyStock=oldOccupyStock-number<0?0L:oldOccupyStock-number;
		sku.setOccupyStock(newOccupyStock);
		productSkuDao.updateByIdSelective(sku);*/
        productSkuDao.reduceOccupyStockById(skuId, number);
    }

    /**
     * 获取可销售库存
     * @param skuId 商品skuId
     */
/*	public Long getSalableStock(Long skuId){
		Long salableStock=0L;
		if(skuId==null){
			return salableStock;
		}
		ProductSku sku=productSkuDao.selectById(skuId);
		if(sku==null){
			return salableStock;
		}
		Long occupyStock=sku.getOccupyStock()==null?0l:sku.getOccupyStock();
		Long stock=sku.getStock()==null?0l:sku.getStock();
		salableStock=stock-occupyStock;
		return salableStock;
	}*/

    /**
     * 根据skuId获取商品sku(此方式只是封装了一些公共代码供本类使用)
     *
     * @param skuId  商品skuId
     * @param number 增加库存数量
     */
    private ProductSku getProductSku(Long skuId, Integer number) {
        if (skuId == null || number == null) {
            return null;
        }
        ProductSku sku = productSkuDao.selectById(skuId);
        return sku;
    }

}