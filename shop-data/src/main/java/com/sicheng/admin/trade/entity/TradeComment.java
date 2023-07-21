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
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.trade.dao.TradeCommentDao;
import com.sicheng.admin.trade.dao.TradeCommentImageDao;
import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 评论 Entity 子类，请把你的业务代码写在这里
 *
 * @author 范秀秀
 * @version 2017-02-07
 */
public class TradeComment extends TradeCommentBase<TradeComment> {

    private static final long serialVersionUID = 1L;
    private String headPicPath;

    public TradeComment() {
        super();
    }

    public TradeComment(Long id) {
        super(id);
    }

    //一对一映射
    /**
     * 获取会员信息
     */
    @JsonIgnore
    private UserMain userMain;//一条预存款明细--一个会员

    public UserMain getUserMain() {
        if (userMain == null) {
            UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
            userMain = dao.selectById(this.getUId());
        }
        return userMain;
    }

    public void setUserMain(UserMain userMain) {
        this.userMain = userMain;
    }

    //ListIdIn工具  在一个list中做 一对一，10个一条预存款明细对10个用户
    //填充 xxx,把1+N改成1+1
    public static void fillUserMain(List<TradeComment> list) {
        List<Object> ids = batchField(list, "uId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainList = dao.selectByIdIn(ids);
        fill(userMainList, "uId", list, "uId", "userMain");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private Store store; // 一条评论 一个店铺

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

    //ListIdIn工具  在一个list中做 一对一，10个评论对10个店铺
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<TradeComment> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> stores = dao.selectByIdIn(ids);
        fill(stores, "storeId", list, "storeId", "store");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private ProductSpu productSpu;//一条评论--一个商品

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

    //ListIdIn工具  在一个list中做 一对一，10个评论对10个商品
    //填充 xxx,把1+N改成1+1
    public static void fillProductSpu(List<TradeComment> list) {
        List<Object> ids = batchField(list, "pId");//批量调用对象的getXxx()方法
        ProductSpuDao dao = SpringContextHolder.getBean(ProductSpuDao.class);
        List<ProductSpu> productSpulist = dao.selectByIdIn(ids);
        fill(productSpulist, "pId", list, "pId", "productSpu");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private ProductSku productSku;//一条评论--一个商品sku

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

    //ListIdIn工具  在一个list中做 一对一，10个评论对10个商品sku
    //填充 xxx,把1+N改成1+1
    public static void fillProductSku(List<TradeComment> list) {
        List<Object> ids = batchField(list, "pId");//批量调用对象的getXxx()方法
        ProductSkuDao dao = SpringContextHolder.getBean(ProductSkuDao.class);
        List<ProductSku> productSkulist = dao.selectByIdIn(ids);
        fill(productSkulist, "skuId", list, "skuId", "productSku");//循环填充
    }

    //一对多映射
    private List<TradeCommentImage> tradeCommentImageList;//一条评论--多个图片

    public List<TradeCommentImage> getTradeCommentImageList() {
        if (tradeCommentImageList == null) {
            TradeCommentImageDao dao = SpringContextHolder.getBean(TradeCommentImageDao.class);
            tradeCommentImageList = dao.selectByWhere(null, new Wrapper().and("comment_id=", this.getCommentId()).orderBy("ci_id asc"));//排序
        }
        return tradeCommentImageList;
    }

    private TradeComment tradeCommentAdd;//一条评论--一条追评信息

    public TradeComment getTradeCommentAdd() {
        //if(tradeCommentAdd==null){
        TradeCommentDao dao = SpringContextHolder.getBean(TradeCommentDao.class);
        TradeComment comment = new TradeComment();
        comment.setReplyId(this.getCommentId());
        comment.setType("1");
        List<TradeComment> commentList = dao.selectByWhere(null, new Wrapper(comment));
        if (commentList.size() > 0) {
            tradeCommentAdd = commentList.get(0);
        } else {
            tradeCommentAdd = null;
        }
        //}
        return tradeCommentAdd;
    }

    public void setTradeCommentAdd(TradeComment tradeCommentAdd) {
        this.tradeCommentAdd = tradeCommentAdd;
    }

    //ListIdIn工具  在一个list中做 一对一，十条评论--十条追评信息
    //填充 xxx,把1+N改成1+1
    public static void fillTradeCommentAdd(List<TradeComment> list) {
        List<Object> ids = batchField(list, "commentId");//批量调用对象的getXxx()方法
        TradeCommentDao dao = SpringContextHolder.getBean(TradeCommentDao.class);
        List<TradeComment> userlist = dao.selectByReplyIdIn(ids);
        fill(userlist, "replyId", list, "commentId", "tradeCommentAdd");//循环填充
    }

    private TradeComment tradeCommentExplain;//一条评论--一条卖家解释信息

    public TradeComment getTradeCommentExplain() {
        //tradeCommentExplain=new TradeComment();
        //if(tradeCommentExplain==null){
        TradeCommentDao dao = SpringContextHolder.getBean(TradeCommentDao.class);
        TradeComment comment = new TradeComment();
        comment.setReplyId(this.getCommentId());
        comment.setType("2");
        List<TradeComment> commentList = dao.selectByWhere(null, new Wrapper(comment));
        if (commentList.size() > 0) {
            tradeCommentExplain = commentList.get(0);
        } else {
            tradeCommentExplain = null;
        }
        //}
        return tradeCommentExplain;
    }

    public void setTradeCommentExplain(TradeComment tradeCommentExplain) {
        this.tradeCommentExplain = tradeCommentExplain;
    }

    //ListIdIn工具  在一个list中做 一对一，十条评论--十条解释信息
    //填充 xxx,把1+N改成1+1
    public static void fillTradeCommentExplain(List<TradeComment> list) {
        List<Object> ids = batchField(list, "commentId");//批量调用对象的getXxx()方法
        TradeCommentDao dao = SpringContextHolder.getBean(TradeCommentDao.class);
        List<TradeComment> tradeCommentlist = dao.selectByReplyIdIn(ids);
        fill(tradeCommentlist, "replyId", list, "commentId", "tradeCommentExplain");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private TradeOrder tradeOrder;//一条评论--一条订单

    public TradeOrder getTradeOrder() {
        if (tradeOrder == null) {
            TradeOrderDao dao = SpringContextHolder.getBean(TradeOrderDao.class);
            tradeOrder = dao.selectById(this.getOrderId());
        }
        return tradeOrder;
    }

    public void setTradeOrder(TradeOrder tradeOrder) {
        this.tradeOrder = tradeOrder;
    }

    //ListIdIn工具  在一个list中做 一对一，10个评论对10条订单
    //填充 xxx,把1+N改成1+1
    public static void fillTradeOrder(List<TradeComment> list) {
        List<Object> ids = batchField(list, "orderId");//批量调用对象的getXxx()方法
        TradeOrderDao dao = SpringContextHolder.getBean(TradeOrderDao.class);
        List<TradeOrder> tradeOrderlist = dao.selectByIdIn(ids);
        fill(tradeOrderlist, "orderId", list, "orderId", "tradeOrder");//循环填充
    }

    public String getHeadPicPath() {
        return headPicPath;
    }

    public void setHeadPicPath(String headPicPath) {
        this.headPicPath = headPicPath;
    }
}