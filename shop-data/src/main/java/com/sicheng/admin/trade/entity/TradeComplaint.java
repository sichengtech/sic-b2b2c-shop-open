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

import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.sys.dao.UserDao;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.admin.trade.dao.*;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 投诉 Entity 子类，请把你的业务代码写在这里
 *
 * @author fxx
 * @version 2017-02-07
 */
public class TradeComplaint extends TradeComplaintBase<TradeComplaint> {

    private static final long serialVersionUID = 1L;

    public TradeComplaint() {
        super();
    }

    public TradeComplaint(Long id) {
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
    public static void fillUserMain(List<TradeComplaint> list) {
        List<Object> ids = batchField(list, "uId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainList = dao.selectByIdIn(ids);
        fill(userMainList, "uId", list, "uId", "userMain");//循环填充
    }

    //一对一映射
    private Store store; // 一条投诉 一个店铺

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

    //ListIdIn工具  在一个list中做 一对一，10条投诉对10个商家
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<TradeComplaint> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> stores = dao.selectByIdIn(ids);
        fill(stores, "storeId", list, "storeId", "store");//循环填充
    }

    //一对一映射
    private TradeOrder tradeOrder;//一条投诉--一条订单

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

    //ListIdIn工具  在一个list中做 一对一，10条投诉对10条订单
    //填充 xxx,把1+N改成1+1
    public static void fillTradeOrder(List<TradeComplaint> list) {
        List<Object> ids = batchField(list, "orderId");//批量调用对象的getXxx()方法
        TradeOrderDao dao = SpringContextHolder.getBean(TradeOrderDao.class);
        List<TradeOrder> tradeOrderlist = dao.selectByIdIn(ids);
        fill(tradeOrderlist, "orderId", list, "orderId", "tradeOrder");//循环填充
    }

    //一对一映射
    private TradeOrderItem tradeOrderItem;//一条投诉--一条订单详情

    public TradeOrderItem getTradeOrderItem() {
        if (tradeOrderItem == null) {
            TradeOrderItemDao dao = SpringContextHolder.getBean(TradeOrderItemDao.class);
            tradeOrderItem = dao.selectById(this.getOrderDetailId());
        }
        return tradeOrderItem;
    }

    public void setTradeOrderItem(TradeOrderItem tradeOrderItem) {
        this.tradeOrderItem = tradeOrderItem;
    }

    //ListIdIn工具  在一个list中做 一对一，10条投诉对10条订单详情
    //填充 xxx,把1+N改成1+1
    public static void fillTradeOrderItem(List<TradeComplaint> list) {
        List<Object> ids = batchField(list, "orderDetailId");//批量调用对象的getXxx()方法
        TradeOrderItemDao dao = SpringContextHolder.getBean(TradeOrderItemDao.class);
        List<TradeOrderItem> tradeOrderItemlist = dao.selectByIdIn(ids);
        fill(tradeOrderItemlist, "orderItemId", list, "orderDetailId", "tradeOrderItem");//循环填充
    }

    private User user;//一条投诉--一个管理员

    public User getUser() {
        if (user == null) {
            UserDao dao = SpringContextHolder.getBean(UserDao.class);
            user = dao.selectById(this.getAdminId());
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //ListIdIn工具  在一个list中做 一对一，10条投诉对10个管理员
    //填充 xxx,把1+N改成1+1
    public static void fillUser(List<TradeComplaint> list) {
        List<Object> ids = batchField(list, "adminId");//批量调用对象的getXxx()方法
        UserDao dao = SpringContextHolder.getBean(UserDao.class);
        List<User> userlist = dao.selectByIdIn(ids);
        fill(userlist, "id", list, "adminId", "user");//循环填充
    }

    //一对一映射
    private TradeComplaint replyTradeComplaint;//一条投诉--一条申诉信息

    public TradeComplaint getReplyTradeComplaint() {
        if (replyTradeComplaint == null) {
            TradeComplaintDao dao = SpringContextHolder.getBean(TradeComplaintDao.class);
            replyTradeComplaint = dao.selectById(this.getReplyId());

            TradeComplaint complaint = new TradeComplaint();
            complaint.setReplyId(this.getComplaintId());
            complaint.setType("1");
            List<TradeComplaint> complaintList = dao.selectByWhere(null, new Wrapper(complaint));
            if (complaintList.size() > 0) {
                replyTradeComplaint = complaintList.get(0);
            }
        }
        return replyTradeComplaint;
    }

    public void setReplyTradeComplaint(TradeComplaint replyTradeComplaint) {
        this.replyTradeComplaint = replyTradeComplaint;
    }

    //一对多映射
    private List<TradeComplaintImage> tradeComplaintImageList;//一条投诉--多个凭证

    public List<TradeComplaintImage> getTradeComplaintImageList() {
        if (tradeComplaintImageList == null) {
            TradeComplaintImageDao dao = SpringContextHolder.getBean(TradeComplaintImageDao.class);
            tradeComplaintImageList = dao.selectByWhere(null, new Wrapper().and("complaint_id=", this.getComplaintId()).orderBy("ci_id asc"));//排序
        }
        return tradeComplaintImageList;
    }

    //一对多映射
    private List<TradeComplaintDetail> tradeComplaintDetailList;//一条投诉--多个对话详情

    public List<TradeComplaintDetail> getTradeComplaintDetailList() {
        if (tradeComplaintDetailList == null) {
            TradeComplaintDetailDao dao = SpringContextHolder.getBean(TradeComplaintDetailDao.class);
            tradeComplaintDetailList = dao.selectByWhere(null, new Wrapper().and("complaint_id=", this.getComplaintId()).orderBy("cd_id desc"));//排序
        }
        return tradeComplaintDetailList;
    }
}