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
package com.sicheng.admin.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.product.dao.ProductSkuDao;
import com.sicheng.admin.product.dao.ProductSpuDao;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车 Entity 子类，请把你的业务代码写在这里
 *
 * @author 范秀秀
 * @version 2017-02-08
 */
public class TradeCart extends TradeCartBase<TradeCart> {

    private static final long serialVersionUID = 1L;

    public TradeCart() {
        super();
    }

    public TradeCart(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里
    //一对一映射
    @JsonIgnore
    private Store store;

    public Store getStore() {
        if (store == null) {
            StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
            store = dao.selectById(this.getStoreId());
        }
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    //ListIdIn工具  在一个list中做 一对一，10个购物车商品对10个店铺
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<TradeCart> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> storeList = dao.selectByIdIn(ids);
        fill(storeList, "storeId", list, "storeId", "store");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private ProductSpu productSpu;

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

    //ListIdIn工具  在一个list中做 一对一，10个购物车商品对10个spu
    //填充 xxx,把1+N改成1+1
    public static void fillProductSpu(List<TradeCart> list) {
        List<Object> ids = batchField(list, "pId");//批量调用对象的getXxx()方法
        ProductSpuDao dao = SpringContextHolder.getBean(ProductSpuDao.class);
        List<ProductSpu> productSpuList = dao.selectByIdIn(ids);
        fill(productSpuList, "pId", list, "pId", "productSpu");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private ProductSku productSku;

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

    //ListIdIn工具  在一个list中做 一对一，10个购物车商品对10个sku
    //填充 xxx,把1+N改成1+1
    public static void fillProductSku(List<TradeCart> list) {
        List<Object> ids = batchField(list, "skuId");//批量调用对象的getXxx()方法
        ProductSkuDao dao = SpringContextHolder.getBean(ProductSkuDao.class);
        List<ProductSku> productSpuList = dao.selectByIdIn(ids);
        fill(productSpuList, "skuId", list, "skuId", "productSku");//循环填充
    }

    BigDecimal freight; //运费

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    private BigDecimal price;   //单价

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private Boolean isOffShelf; //是否下架

    public Boolean getIsOffShelf() {
        return isOffShelf;
    }

    public void setIsOffShelf(Boolean isOffShelf) {
        this.isOffShelf = isOffShelf;
    }

    /**
     * 总价格
     */
    @Override
    public BigDecimal getPriceSum() {
        if (super.getPriceSum() == null) {
            return super.getPriceSum();
        }
        String priceSum = super.getPriceSum().stripTrailingZeros().toPlainString();
        return new BigDecimal(priceSum);
    }
}