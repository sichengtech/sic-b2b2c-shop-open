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

import com.sicheng.admin.account.entity.AccountBalanceProreturnorder;
import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.admin.trade.dao.TradeOrderItemDao;
import com.sicheng.admin.trade.dao.TradeReturnOrderVoucherDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 退款、退货退款订单 Entity 子类，请把你的业务代码写在这里
 *
 * @author fxx
 * @version 2017-02-07
 */
public class TradeReturnOrder extends TradeReturnOrderBase<TradeReturnOrder> {

    private static final long serialVersionUID = 1L;

    public TradeReturnOrder() {
        super();
    }

    public TradeReturnOrder(Long id) {
        super(id);
    }

    //对账-商品退单
    List<AccountBalanceProreturnorder> accountBalanceProreturnorderList=new ArrayList<>();

    /**
     * @return the accountBalanceProreturnorderList
     */
    public List<AccountBalanceProreturnorder> getAccountBalanceProreturnorderList() {
        return accountBalanceProreturnorderList;
    }

    /**
     * @param accountBalanceProreturnorderList the accountBalanceProreturnorderList to set
     */
    public void setAccountBalanceProreturnorderList(List<AccountBalanceProreturnorder> accountBalanceProreturnorderList) {
        this.accountBalanceProreturnorderList = accountBalanceProreturnorderList;
    }

    //一对一映射
    private TradeOrder tradeOrder;//一条退单--一条订单

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

    //ListIdIn工具  在一个list中做 一对一，10个退单对10个订单
    //填充 xxx,把1+N改成1+1
    public static void fillTradeOrder(List<TradeReturnOrder> list) {
        List<Object> ids = batchField(list, "orderId");//批量调用对象的getXxx()方法
        TradeOrderDao dao = SpringContextHolder.getBean(TradeOrderDao.class);
        List<TradeOrder> tradeOrderlist = dao.selectByIdIn(ids);
        fill(tradeOrderlist, "orderId", list, "orderId", "tradeOrder");//循环填充
    }

    //一对一映射
    private TradeOrderItem tradeOrderItem;//一条退单--一条订单详情

    public TradeOrderItem getTradeOrderItem() {
        if (tradeOrderItem == null) {
            TradeOrderItemDao dao = SpringContextHolder.getBean(TradeOrderItemDao.class);
            tradeOrderItem = dao.selectById(this.getOrderItemId());
        }
        return tradeOrderItem;
    }

    public void setTradeOrderItem(TradeOrderItem tradeOrderItem) {
        this.tradeOrderItem = tradeOrderItem;
    }

    //ListIdIn工具  在一个list中做 一对一，10个订单对10中支付方式
    //填充 xxx,把1+N改成1+1
    public static void fillTradeOrderItem(List<TradeReturnOrder> list) {
        List<Object> ids = batchField(list, "orderItemId");//批量调用对象的getXxx()方法
        TradeOrderItemDao dao = SpringContextHolder.getBean(TradeOrderItemDao.class);
        List<TradeOrderItem> tradeOrderItemlist = dao.selectByIdIn(ids);
        fill(tradeOrderItemlist, "orderItemId", list, "orderItemId", "tradeOrderItem");//循环填充
        //填充订单详情和图片
        //TradeOrderItem.fillStoreAlbumPicture(tradeOrderItemlist);
    }

    //一对多映射
    /**
     * 获取会员信息
     */
    private UserMain userMain;//一条退单--一个会员

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
    public static void fillUserMain(List<TradeReturnOrder> list) {
        List<Object> ids = batchField(list, "uId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainList = dao.selectByIdIn(ids);
        fill(userMainList, "uId", list, "uId", "userMain");//循环填充
    }

    //一对多映射
    private List<TradeReturnOrderVoucher> tradeReturnOrderVoucherList;//一条退单--多个退单凭证

    public List<TradeReturnOrderVoucher> getTradeReturnOrderVoucherList() {
        if (tradeReturnOrderVoucherList == null) {
            TradeReturnOrderVoucherDao dao = SpringContextHolder.getBean(TradeReturnOrderVoucherDao.class);
            tradeReturnOrderVoucherList = dao.selectByWhere(null, new Wrapper().and("return_order_id=", this.getReturnOrderId()).orderBy("ci_id asc"));//排序
        }
        return tradeReturnOrderVoucherList;
    }

    //一对多映射
    private SettlementPayWay settlementPayWay;//一条退单--一种支付方式

    public SettlementPayWay getSettlementPayWay() {
        if (settlementPayWay == null) {
            SettlementPayWayDao dao = SpringContextHolder.getBean(SettlementPayWayDao.class);
            settlementPayWay = dao.selectById(this.getPayWayId());
        }
        return settlementPayWay;
    }

    public void setSettlementPayWay(SettlementPayWay settlementPayWay) {
        this.settlementPayWay = settlementPayWay;
    }

    //一对一映射
    private Store store;//一条退单--一个店铺

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

    //ListIdIn工具  在一个list中做 一对一，10个退单对10个订单
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<TradeReturnOrder> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> storelist = dao.selectByIdIn(ids);
        fill(storelist, "storeId", list, "storeId", "store");//循环填充
    }

    /**
     * 退款金额
     */
    @Override
    public BigDecimal getReturnMoney() {
        if (super.getReturnMoney() == null) {
            return super.getReturnMoney();
        }
        String returnMoney = super.getReturnMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(returnMoney);
    }

    /**
     * 退还佣金
     */
    @Override
    public BigDecimal getReturnCommission() {
        if (super.getReturnCommission() == null) {
            return super.getReturnCommission();
        }
        String returnCommission = super.getReturnCommission().stripTrailingZeros().toPlainString();
        return new BigDecimal(returnCommission);
    }

    /**
     * 退还运费
     */
    @Override
    public BigDecimal getReturnFreight() {
        if (super.getReturnFreight() == null) {
            return super.getReturnFreight();
        }
        String returnFreight = super.getReturnFreight().stripTrailingZeros().toPlainString();
        return new BigDecimal(returnFreight);
    }

    /**
     * 在线退款金额
     */
    @Override
    public BigDecimal getOnlineReturnMoney() {
        if (super.getOnlineReturnMoney() == null) {
            return super.getOnlineReturnMoney();
        }
        String onlineReturnMoney = super.getOnlineReturnMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(onlineReturnMoney);
    }

    /**
     * 支付宝退款金额
     */
    @Override
    public BigDecimal getAlipayReturnMoney() {
        if (super.getAlipayReturnMoney() == null) {
            return super.getAlipayReturnMoney();
        }
        String alipayReturnMoney = super.getAlipayReturnMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(alipayReturnMoney);
    }

    /**
     * 预存款退款金额
     */
    @Override
    public BigDecimal getPreDepositReturnMoney() {
        if (super.getAlipayReturnMoney() == null) {
            return super.getPreDepositReturnMoney();
        }
        String preDepositReturnMoney = super.getPreDepositReturnMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(preDepositReturnMoney);
    }

    /**
     * 微信退款金额
     */
    @Override
    public BigDecimal getWxReturnMoeny() {
        if (super.getWxReturnMoeny() == null) {
            return super.getWxReturnMoeny();
        }
        String wxReturnMoeny = super.getWxReturnMoeny().stripTrailingZeros().toPlainString();
        return new BigDecimal(wxReturnMoeny);
    }

    /**
     * 备用退款金额1
     */
    @Override
    public BigDecimal getBak1ReturnMoeny() {
        if (super.getBak1ReturnMoeny() == null) {
            return super.getBak1ReturnMoeny();
        }
        String bak1ReturnMoeny = super.getBak1ReturnMoeny().stripTrailingZeros().toPlainString();
        return new BigDecimal(bak1ReturnMoeny);
    }

    /**
     * 备用退款金额2
     */
    @Override
    public BigDecimal getBak2ReturnMoeny() {
        if (super.getBak2ReturnMoeny() == null) {
            return super.getBak2ReturnMoeny();
        }
        String bak2ReturnMoeny = super.getBak2ReturnMoeny().stripTrailingZeros().toPlainString();
        return new BigDecimal(bak2ReturnMoeny);
    }
}