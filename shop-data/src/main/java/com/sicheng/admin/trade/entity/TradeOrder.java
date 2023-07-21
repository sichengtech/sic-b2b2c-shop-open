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
import com.sicheng.admin.account.entity.AccountBalanceProorder;
import com.sicheng.admin.logistics.dao.LogisticsCompanyDao;
import com.sicheng.admin.logistics.entity.LogisticsCompany;
import com.sicheng.admin.member.dao.MemberAddressDao;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.dao.UserSellerDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.dao.StoreEnterDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.admin.trade.dao.TradeDeliverDao;
import com.sicheng.admin.trade.dao.TradeOrderItemDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单 Entity 子类，请把你的业务代码写在这里
 *
 * @author 范秀秀
 * @version 2017-02-06
 */
public class TradeOrder extends TradeOrderBase<TradeOrder> {

    private static final long serialVersionUID = 1L;

    public TradeOrder() {
        super();
    }

    public TradeOrder(Long id) {
        super(id);
    }

    //对账-商品订单
    List<AccountBalanceProorder> accountBalanceProorderList=new ArrayList<>();
    public List<AccountBalanceProorder> getAccountBalanceProorderList() {
        return accountBalanceProorderList;
    }

    public void setAccountBalanceProorderList(List<AccountBalanceProorder> accountBalanceProorderList) {
        this.accountBalanceProorderList = accountBalanceProorderList;
    }

    //一对一映射
    @JsonIgnore
    private SettlementPayWay settlementPayWay;    //一条订单-一种支付方式

    public SettlementPayWay getSettlementPayWay() {
        if (settlementPayWay == null) {
            SettlementPayWayDao dao = SpringContextHolder.getBean(SettlementPayWayDao.class);
            settlementPayWay = dao.selectById(this.getPaymentMethodId());
        }
        return settlementPayWay;
    }

    public void setSettlementPayWay(SettlementPayWay settlementPayWay) {
        this.settlementPayWay = settlementPayWay;
    }

    //ListIdIn工具  在一个list中做 一对一，10个订单对10中支付方式
    //填充 xxx,把1+N改成1+1
    public static void fillSettlementPayWay(List<TradeOrder> list) {
        List<Object> ids = batchField(list, "paymentMethodId");//批量调用对象的getXxx()方法
        SettlementPayWayDao dao = SpringContextHolder.getBean(SettlementPayWayDao.class);
        List<SettlementPayWay> settlementPayWaylist = dao.selectByIdIn(ids);
        fill(settlementPayWaylist, "payWayId", list, "paymentMethodId", "settlementPayWay");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private MemberAddress memberAddress;    //一条订单-一个收货地址

    public MemberAddress getMemberAddress() {
        if (memberAddress == null) {
            MemberAddressDao dao = SpringContextHolder.getBean(MemberAddressDao.class);
            memberAddress = dao.selectById(this.getAddressId());
        }
        return memberAddress;
    }

    public void setMemberAddress(MemberAddress memberAddress) {
        this.memberAddress = memberAddress;
    }

    //ListIdIn工具  在一个list中做 一对一，10个订单对10个收货地址
    //填充 xxx,把1+N改成1+1
    public static void fillMemberAddress(List<TradeOrder> list) {
        List<Object> ids = batchField(list, "addressId");//批量调用对象的getXxx()方法
        MemberAddressDao dao = SpringContextHolder.getBean(MemberAddressDao.class);
        List<MemberAddress> memberAddressList = dao.selectByIdIn(ids);
        fill(memberAddressList, "addressId", list, "addressId", "memberAddress");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private LogisticsCompany logisticsCompany;    //一条订单-一个快递公司

    public LogisticsCompany getLogisticsCompany() {
        if (logisticsCompany == null) {
//			LogisticsCompanyDao dao=SpringContextHolder.getBean(LogisticsCompanyDao.class);
//			logisticsCompany=dao.selectById(this.getAddressId());
            LogisticsCompanyDao dao = SpringContextHolder.getBean(LogisticsCompanyDao.class);
            logisticsCompany = dao.selectById(this.getAddressId());
        }
        return logisticsCompany;
    }

    public void setLogisticsCompany(LogisticsCompany logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    //ListIdIn工具  在一个list中做 一对一，10个订单对10个快递公司
    //填充 xxx,把1+N改成1+1
    public static void fillLogisticsCompany(List<TradeOrder> list) {
        List<Object> ids = batchField(list, "logisticsTemplateName");//批量调用对象的getXxx()方法
//		LogisticsCompanyDao dao=SpringContextHolder.getBean(LogisticsCompanyDao.class);
//		List<LogisticsCompany> logisticsCompanyList=dao.selectByIdIn(ids);
        LogisticsCompanyDao dao = SpringContextHolder.getBean(LogisticsCompanyDao.class);
        List<LogisticsCompany> logisticsCompanyList = dao.selectByIdIn(ids);
        fill(logisticsCompanyList, "lcId", list, "logisticsTemplateName", "logisticsCompany");//循环填充
    }

    //一对多映射
    @JsonIgnore
    private List<TradeOrderItem> tradeOrderItemList;//一条订单--多个订单详情

    public List<TradeOrderItem> getTradeOrderItemList() {
        if (tradeOrderItemList == null) {
            TradeOrderItemDao dao = SpringContextHolder.getBean(TradeOrderItemDao.class);
            tradeOrderItemList = dao.selectByWhere(null, new Wrapper().and("order_id=", this.getOrderId()).orderBy("ORDER_ITEM_ID asc"));//排序
        }
        return tradeOrderItemList;
    }

    public void setTradeOrderItemList(List<TradeOrderItem> tradeOrderItemList) {
        this.tradeOrderItemList = tradeOrderItemList;
    }
    //一对一映射
    /**
     * 获取会员信息
     */
    @JsonIgnore
    private UserMain userMain;//一条订单--一个会员

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
    public static void fillUserMain(List<TradeOrder> list) {
        List<Object> ids = batchField(list, "uId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainList = dao.selectByIdIn(ids);
        fill(userMainList, "uId", list, "uId", "userMain");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private Store store; // 一条订单 一个店铺

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

    //ListIdIn工具  在一个list中做 一对一，10个订单对10个店铺
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<TradeOrder> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> storeList = dao.selectByIdIn(ids);
        fill(storeList, "storeId", list, "storeId", "store");//循环填充
    }

    //一对一映射
    @JsonIgnore
    private StoreEnter storeEnter;//一条订单-商家入住申请信息

    public StoreEnter getStoreEnter() {
        if (storeEnter == null) {
            StoreEnterDao dao = SpringContextHolder.getBean(StoreEnterDao.class);
            UserMainDao userMainDao = SpringContextHolder.getBean(UserMainDao.class);

            UserSellerDao userSellerDao = SpringContextHolder.getBean(UserSellerDao.class);
            UserSeller userSeller = new UserSeller();
            userSeller.setStoreId(this.getStoreId());
            List<UserSeller> userSellerList = userSellerDao.selectByWhere(null, new Wrapper(userSeller));
            List<Object> idList = new ArrayList<>();
            if (!userSellerList.isEmpty()) {
                for (UserSeller seller : userSellerList) {
                    idList.add(seller.getUId());
                }
            }
            List<UserMain> userMainList = userMainDao.selectByWhere(null, new Wrapper().and("u_id in", idList).and("type_account =", 1));
            Long uId = null;
            if (!userMainList.isEmpty()) {
                uId = userMainList.get(0).getUId();
            }
            storeEnter = dao.selectById(uId);
        }
        return storeEnter;
    }

    public void setStoreEnter(StoreEnter storeEnter) {
        this.storeEnter = storeEnter;
    }

    //一对一映射
    @JsonIgnore
    private TradeDeliver tradeDeliver;//一条订单-发票信息

    public TradeDeliver getTradeDeliver() {
        if (tradeDeliver == null) {
            TradeDeliverDao dao = SpringContextHolder.getBean(TradeDeliverDao.class);
            tradeDeliver = dao.selectById(this.getDeliverId());
        }
        return tradeDeliver;
    }

    public void setTradeDeliver(TradeDeliver tradeDeliver) {
        this.tradeDeliver = tradeDeliver;
    }

    /**
     * 订单总金额（去掉无用小数点0）
     */
    @Override
    public BigDecimal getAmountPaid() {
        if (super.getAmountPaid() == null) {
            return super.getAmountPaid();
        }
        String amountPaid = super.getAmountPaid().stripTrailingZeros().toPlainString();
        return new BigDecimal(amountPaid);
    }

    /**
     * 调整后的金额（去掉无用小数点0）
     */
    @Override
    public BigDecimal getOffsetAmount() {
        if (super.getOffsetAmount() == null) {
            return super.getOffsetAmount();
        }
        String offsetAmount = super.getOffsetAmount().stripTrailingZeros().toPlainString();
        return new BigDecimal(offsetAmount);
    }

    /**
     * 平台红包（去掉无用小数点0）
     */
    @Override
    public BigDecimal getRedPacket() {
        if (super.getRedPacket() == null) {
            return super.getRedPacket();
        }
        String redPacket = super.getRedPacket().stripTrailingZeros().toPlainString();
        return new BigDecimal(redPacket);
    }

    /**
     * 佣金（去掉无用小数点0）
     */
    @Override
    public BigDecimal getFee() {
        if (super.getFee() == null) {
            return super.getFee();
        }
        String fee = super.getFee().stripTrailingZeros().toPlainString();
        return new BigDecimal(fee);
    }

    /**
     * 运费（去掉无用小数点0）
     */
    @Override
    public BigDecimal getFreight() {
        if (super.getFreight() == null) {
            return super.getFreight();
        }
        String freight = super.getFreight().stripTrailingZeros().toPlainString();
        return new BigDecimal(freight);
    }

    /**
     * 预存款支付（去掉无用小数点0）
     */
    @Override
    public BigDecimal getPreDepositPay() {
        if (super.getPreDepositPay() == null) {
            return super.getPreDepositPay();
        }
        String preDepositPay = super.getPreDepositPay().stripTrailingZeros().toPlainString();
        return new BigDecimal(preDepositPay);
    }

    /**
     * 在线支付金额（去掉无用小数点0）
     */
    @Override
    public BigDecimal getOnlinePayMoney() {
        if (super.getOnlinePayMoney() == null) {
            return super.getOnlinePayMoney();
        }
        String onlinePayMoney = super.getOnlinePayMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(onlinePayMoney);
    }

    /**
     * 结算金额（去掉无用小数点0）
     */
    @Override
    public BigDecimal getSettlementMoney() {
        if (super.getSettlementMoney() == null) {
            return super.getSettlementMoney();
        }
        String settlementMoney = super.getSettlementMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(settlementMoney);
    }

    /**
     * 订单实际金额(商家修改，获取商家修改之后的；没有修改，使用订单总额)
     */
    private BigDecimal actualMoney;
    public BigDecimal getActualMoney() {
        if(super.getOffsetAmount()!=null){
            //商家调整过
           this.getOffsetAmount();
        }
        return this.getAmountPaid();
    }

    public void setActualMoney(BigDecimal actualMoney) {
        this.actualMoney = actualMoney;
    }

}