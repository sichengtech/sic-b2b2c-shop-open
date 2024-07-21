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
package com.sicheng.admin.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.product.dao.ProductSkuDao;
import com.sicheng.admin.product.dao.ProductSpuDao;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.store.entity.StoreAlbumPicture;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单详情 Entity 子类，请把你的业务代码写在这里
 *
 * @author fxx
 * @version 2017-02-06
 */
public class TradeOrderItem extends TradeOrderItemBase<TradeOrderItem> {

    private static final long serialVersionUID = 1L;
    private StoreAlbumPicture storeAlbumPicture;//图片

    public TradeOrderItem() {
        super();
    }

    public TradeOrderItem(Long id) {
        super(id);
    }

    //一对一映射
/*	public StoreAlbumPicture getStoreAlbumPicture() {
		if(storeAlbumPicture==null){
			StoreAlbumPictureDao dao=SpringContextHolder.getBean(StoreAlbumPictureDao.class);
			storeAlbumPicture=dao.selectById(this.getThumbnailId());
		}
		return storeAlbumPicture;
	}
	
	public void setStoreAlbumPicture(StoreAlbumPicture storeAlbumPicture) {
		this.storeAlbumPicture = storeAlbumPicture;
	}
	
	//ListIdIn工具  在一个list中做 一对一，10个订单详情对10张图片
	//填充 xxx,把1+N改成1+1
	public static void fillStoreAlbumPicture(List<TradeOrderItem> list){
		List<Object> ids=batchField(list,"thumbnailId");//批量调用对象的getXxx()方法
		StoreAlbumPictureDao dao=SpringContextHolder.getBean(StoreAlbumPictureDao.class);
		List<StoreAlbumPicture> storeAlbumPicturelist=dao.selectByIdIn(ids);
		fill(storeAlbumPicturelist,"pictureId",list,"thumbnailId","storeAlbumPicture");//循环填充
	}*/

    //一对一映射
    @JsonIgnore
    private ProductSku productSku;    //一条订单详情-一个productSku

    public ProductSku getProductSku() {
        if (productSku == null) {
            ProductSkuDao dao = SpringContextHolder.getBean(ProductSkuDao.class);
            productSku = dao.selectById(this.getSkuId());
        }
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

    //ListIdIn工具  在一个list中做 一对一，10个订单详情对10个productSku
    //填充 xxx,把1+N改成1+1
    public static void fillProductSku(List<TradeOrderItem> list) {
        List<Object> ids = batchField(list, "skuId");//批量调用对象的getXxx()方法
        ProductSkuDao dao = SpringContextHolder.getBean(ProductSkuDao.class);
        List<ProductSku> productSkulist = dao.selectByIdIn(ids);
        fill(productSkulist, "skuId", list, "skuId", "productSku");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private ProductSpu productSpu;    //一条订单详情-一个productSku

    public ProductSpu getProductSpu() {
        if (productSpu == null) {
            ProductSpuDao dao = SpringContextHolder.getBean(ProductSpuDao.class);
            productSpu = dao.selectById(this.getPId());
        }
        return productSpu;
    }

    public void setProductSpu(ProductSpu productSpu) {
        this.productSpu = productSpu;
    }

    //ListIdIn工具  在一个list中做 一对一，10个订单详情对10个productSku
    //填充 xxx,把1+N改成1+1
    public static void fillProductSpu(List<TradeOrderItem> list) {
        List<Object> ids = batchField(list, "pId");//批量调用对象的getXxx()方法
        ProductSpuDao dao = SpringContextHolder.getBean(ProductSpuDao.class);
        List<ProductSpu> productSpulist = dao.selectByIdIn(ids);
        fill(productSpulist, "pId", list, "pId", "productSpu");//循环填充
    }

    /**
     * 商品单价
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