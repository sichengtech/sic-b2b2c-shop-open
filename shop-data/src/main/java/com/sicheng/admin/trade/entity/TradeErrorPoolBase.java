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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对账差错池 Entity 父类
 *
 * @author fxx
 * @version 2018-03-29
 */
public class TradeErrorPoolBase<T> extends DataEntity<T>{

        private static final long serialVersionUID = 1L;
        private Long epId;                      // 主键
        private Long orderId;                   // 本商城的订单编号
        private Long storeId;                   // 店铺id
        private String storeName;               // 店铺名称
        private String transactionId;           // 第三方平台支付交易号
        private String billType;                // 类型(1商品订单、2商品退单、3服务订单、4服务退单)
        private Long payWayId;                  // 支付方式id
        private String payWayName;              // 支付方式名称
        private String orderStatus;             // 本商城的订单状态
        private String transactionStatus;       // 第三方平台订单状态
        private BigDecimal orderMoney;          // 本商城操作金额
        private BigDecimal transactionMoney;    // 第三方操作金额
        private Date ordertime;                 // 下单时间
        private String handlestatus;            // 处理状态(0未处理，1已处理)
        private String handleremark;            // 处理备注
        private Date beginOrdertime;            // 开始 下单时间
        private Date endOrdertime;              // 结束 下单时间
        private Date beginCreateDate;           // 开始 创建时间
        private Date endCreateDate;             // 结束 创建时间
        private Date beginUpdateDate;           // 开始 更新时间
        private Date endUpdateDate;             // 结束 更新时间
        public TradeErrorPoolBase() {
            super();
        }

        public TradeErrorPoolBase(Long id){
            super(id);
            this.epId = id;
        }

        /**
         * 描述: 获取ID
         * @return
         * @see com.sicheng.common.persistence.BaseEntity#getId()
         */
        @Override
        public Long getId() {
            return epId;
        }

        /**
         * 描述: 设置ID
         * @param id
         * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)
         */
        @Override
        public void setId(Long id) {
            this.id = id;
            this.epId = id;
        }

        /**
         * getter epId(主键)
         */
        public Long getEpId() {
            return epId;
        }

        /**
         * setter epId(主键)
         */
        public void setEpId(Long epId) {
            this.epId = epId;
        }

        /**
         * getter orderId(本商城的订单编号)
         */
        public Long getOrderId() {
            return orderId;
        }

        /**
         * setter orderId(本商城的订单编号)
         */
        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        /**
         * getter storeId(店铺id)
         */
        public Long getStoreId() {
            return storeId;
        }

        /**
         * setter storeId(店铺id)
         */
        public void setStoreId(Long storeId) {
            this.storeId = storeId;
        }

        /**
         * getter storeName(店铺名称)
         */
        public String getStoreName() {
            return storeName;
        }

        /**
         * setter storeName(店铺名称)
         */
        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        /**
         * getter transactionId(第三方平台支付交易号)
         */
        public String getTransactionId() {
            return transactionId;
        }

        /**
         * setter transactionId(第三方平台支付交易号)
         */
        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        /**
         * getter billType(类型(1商品订单、2商品退单、3服务订单、4服务退单))
         */
        public String getBillType() {
            return billType;
        }

        /**
         * setter billType(类型(1商品订单、2商品退单、3服务订单、4服务退单))
         */
        public void setBillType(String billType) {
            this.billType = billType;
        }

        /**
         * getter payWayId(支付方式id)
         */
        public Long getPayWayId() {
            return payWayId;
        }

        /**
         * setter payWayId(支付方式id)
         */
        public void setPayWayId(Long payWayId) {
            this.payWayId = payWayId;
        }

        /**
         * getter payWayName(支付方式名称)
         */
        public String getPayWayName() {
            return payWayName;
        }

        /**
         * setter payWayName(支付方式名称)
         */
        public void setPayWayName(String payWayName) {
            this.payWayName = payWayName;
        }

        /**
         * getter orderStatus(本商城的订单状态)
         */
        public String getOrderStatus() {
            return orderStatus;
        }

        /**
         * setter orderStatus(本商城的订单状态)
         */
        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        /**
         * getter transactionStatus(第三方平台订单状态)
         */
        public String getTransactionStatus() {
            return transactionStatus;
        }

        /**
         * setter transactionStatus(第三方平台订单状态)
         */
        public void setTransactionStatus(String transactionStatus) {
            this.transactionStatus = transactionStatus;
        }

        /**
         * getter orderMoney(本商城操作金额)
         */
        public BigDecimal getOrderMoney() {
            return orderMoney;
        }

        /**
         * setter orderMoney(本商城操作金额)
         */
        public void setOrderMoney(BigDecimal orderMoney) {
            this.orderMoney = orderMoney;
        }

        /**
         * getter transactionMoney(第三方操作金额)
         */
        public BigDecimal getTransactionMoney() {
            return transactionMoney;
        }

        /**
         * setter transactionMoney(第三方操作金额)
         */
        public void setTransactionMoney(BigDecimal transactionMoney) {
            this.transactionMoney = transactionMoney;
        }

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        /**
         * getter ordertime(下单时间)
         */
        public Date getOrdertime() {
            return ordertime;
        }

        /**
         * setter ordertime(下单时间)
         */
        public void setOrdertime(Date ordertime) {
            this.ordertime = ordertime;
        }

        /**
         * getter handlestatus(处理状态(0未处理，1已处理))
         */
        public String getHandlestatus() {
            return handlestatus;
        }

        /**
         * setter handlestatus(处理状态(0未处理，1已处理))
         */
        public void setHandlestatus(String handlestatus) {
            this.handlestatus = handlestatus;
        }

        /**
         * getter handleremark(处理备注)
         */
        public String getHandleremark() {
            return handleremark;
        }

        /**
         * setter handleremark(处理备注)
         */
        public void setHandleremark(String handleremark) {
            this.handleremark = handleremark;
        }

        /**
         * getter ordertime(下单时间)
         */
        public Date getBeginOrdertime() {
            return beginOrdertime;
        }

        /**
         * setter ordertime(下单时间)
         */
        public void setBeginOrdertime(Date beginOrdertime) {
            this.beginOrdertime = beginOrdertime;
        }

        /**
         * getter ordertime(下单时间)
         */
        public Date getEndOrdertime() {
            return endOrdertime;
        }

        /**
         * setter ordertime(下单时间)
         */
        public void setEndOrdertime(Date endOrdertime) {
            this.endOrdertime = endOrdertime;
        }
        /**
         * getter createDate(创建时间)
         */
        public Date getBeginCreateDate() {
            return beginCreateDate;
        }

        /**
         * setter createDate(创建时间)
         */
        public void setBeginCreateDate(Date beginCreateDate) {
            this.beginCreateDate = beginCreateDate;
        }

        /**
         * getter createDate(创建时间)
         */
        public Date getEndCreateDate() {
            return endCreateDate;
        }

        /**
         * setter createDate(创建时间)
         */
        public void setEndCreateDate(Date endCreateDate) {
            this.endCreateDate = endCreateDate;
        }
        /**
         * getter updateDate(更新时间)
         */
        public Date getBeginUpdateDate() {
            return beginUpdateDate;
        }

        /**
         * setter updateDate(更新时间)
         */
        public void setBeginUpdateDate(Date beginUpdateDate) {
            this.beginUpdateDate = beginUpdateDate;
        }

        /**
         * getter updateDate(更新时间)
         */
        public Date getEndUpdateDate() {
            return endUpdateDate;
        }

        /**
         * setter updateDate(更新时间)
         */
        public void setEndUpdateDate(Date endUpdateDate) {
            this.endUpdateDate = endUpdateDate;
        }

}