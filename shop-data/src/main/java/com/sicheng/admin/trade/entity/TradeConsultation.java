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
import com.sicheng.admin.product.dao.ProductSpuDao;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.trade.dao.TradeConsultationDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 咨询 Entity 子类，请把你的业务代码写在这里
 *
 * @author fxx
 * @version 2017-02-07
 */
public class TradeConsultation extends TradeConsultationBase<TradeConsultation> {

    private static final long serialVersionUID = 1L;

    public TradeConsultation() {
        super();
    }

    public TradeConsultation(Long id) {
        super(id);
    }

    //一对一映射
    /**
     * 获取会员信息
     */
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
    public static void fillUserMain(List<TradeConsultation> list) {
        List<Object> ids = batchField(list, "uId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainList = dao.selectByIdIn(ids);
        fill(userMainList, "uId", list, "uId", "userMain");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private Store store; // 一条咨询 一个店铺

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

    //ListIdIn工具  在一个list中做 一对一，10个订单对10中支付方式
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<TradeConsultation> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> stores = dao.selectByIdIn(ids);
        fill(stores, "storeId", list, "storeId", "store");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private ProductSpu productSpu;//一条咨询--一个商品

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

    //ListIdIn工具  在一个list中做 一对一，10个订单对10中支付方式
    //填充 xxx,把1+N改成1+1
    public static void fillProductSpu(List<TradeConsultation> list) {
        List<Object> ids = batchField(list, "pId");//批量调用对象的getXxx()方法
        ProductSpuDao dao = SpringContextHolder.getBean(ProductSpuDao.class);
        List<ProductSpu> productSpulist = dao.selectByIdIn(ids);
        fill(productSpulist, "pId", list, "pId", "productSpu");//循环填充
    }

    //一对一映射
    private TradeConsultation replyTradeConsultation;//一条咨询--一条回复信息

    public TradeConsultation getReplyTradeConsultation() {
        if (replyTradeConsultation == null) {
            TradeConsultationDao dao = SpringContextHolder.getBean(TradeConsultationDao.class);
            TradeConsultation consultation = new TradeConsultation();
            consultation.setReplyId(this.getConsultationId());
            consultation.setType("1");
            List<TradeConsultation> consultationList = dao.selectByWhere(null, new Wrapper(consultation));
            if (consultationList.size() > 0) {
                replyTradeConsultation = consultationList.get(0);
            }
        }
        return replyTradeConsultation;
    }

    public void setReplyTradeConsultation(TradeConsultation replyTradeConsultation) {
        this.replyTradeConsultation = replyTradeConsultation;
    }

    //ListIdIn工具  在一个list中做 一对一，10个咨询对10个回复信息
    //填充 xxx,把1+N改成1+1
    public static void fillReplyTradeConsultation(List<TradeConsultation> list) {
        List<Object> ids = batchField(list, "consultationId");//批量调用对象的getXxx()方法
        TradeConsultationDao dao = SpringContextHolder.getBean(TradeConsultationDao.class);
        List<TradeConsultation> tradeConsultationDaolist = dao.selectByReplyIdIn(ids);
        fill(tradeConsultationDaolist, "replyId", list, "consultationId", "replyTradeConsultation");//循环填充
    }
}