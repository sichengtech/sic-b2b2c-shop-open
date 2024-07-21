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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 退款、退货退款订单 Entity 父类
 *
 * @author fxx
 * @version 2017-07-17
 */
public class TradeReturnOrderBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long returnOrderId;             // 主键
    private Long orderId;                   // 订单编号(订单表id)
    private Long orderItemId;               // 订单详情编号(订单详情表id)
    private String type;                    // 类型，1退货退款、2退款
    private BigDecimal returnMoney;         // 退款金额
    private BigDecimal returnCommission;    // 退还佣金
    private BigDecimal returnFreight;       // 退还运费
    private Integer returnCount;            // 退货数量
    private String returnReason;            // 退货原因(从字典里取)
    private String returnExplain;           // 退货说明
    private Date applyDate;                 // 申请时间
    private Long uId;                       // 申请人(会员表id)
    private String businessHandle;          // 商家处理，0待审核、1同意、2拒绝
    private String businessHandleRemarks;   // 商家处理备注
    private Date businessHandleDate;        // 商家处理时间
    private Long systemHandle;              // 平台处理，0未处理、1已审核并退款、2拒绝退款
    private Date systemAgreeTime;           // 平台同意退款时间（结算时以此时间为准）
    private String systemHandleRemarks;     // 平台处理备注
    private Long payWayId;                  // 支付方式(支付方式表id)
    private Long adminId;                   // 管理员id(管理员表id)
    private String status;                  // 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
    private Date deliverProductTime;        // 买家发货时间
    private Long logisticsTemplateId;       // 物流公司id(物流公司表外键，买家发货使用的物流公司)
    private String logisticsTemplateName;   // 物流公司名称
    private String trackingNo;              // 运单号
    private String isProductReceipt;        // 商家是否收货，0否、1是
    private Date productReceiptTime;        // 商家收货时间
    private BigDecimal onlineReturnMoney;   // 在线退款金额
    private BigDecimal alipayReturnMoney;   // 支付宝退款金额
    private BigDecimal preDepositReturnMoney;   // 预存款退款金额
    private BigDecimal wxReturnMoeny;       // 微信退款金额
    private BigDecimal bak1ReturnMoeny;     // 备用退款金额1
    private BigDecimal bak2ReturnMoeny;     // 备用退款金额2
    private Long storeId;                   // 关联(店铺表)
    private String isJettison;              // 是否弃货
    private String isReturnMoney;           // 是否退款
    @JsonIgnore
    private String bak1;                    // 备用字段1
    @JsonIgnore
    private String bak2;                    // 备用字段2
    @JsonIgnore
    private String bak3;                    // 备用字段3
    @JsonIgnore
    private String bak4;                    // 备用字段4
    @JsonIgnore
    private String bak5;                    // 备用字段5
    @JsonIgnore
    private String bak6;                    // 备用字段6
    @JsonIgnore
    private String bak7;                    // 备用字段7
    @JsonIgnore
    private String bak8;                    // 备用字段8
    @JsonIgnore
    private String bak9;                    // 备用字段9
    @JsonIgnore
    private String bak10;                   // 备用字段10
    private Date beginApplyDate;            // 开始 申请时间
    private Date endApplyDate;              // 结束 申请时间
    private Date beginBusinessHandleDate;   // 开始 商家处理时间
    private Date endBusinessHandleDate;     // 结束 商家处理时间
    private Date beginSystemAgreeTime;      // 开始 平台同意退款时间（结算时以此时间为准）
    private Date endSystemAgreeTime;        // 结束 平台同意退款时间（结算时以此时间为准）
    private Date beginDeliverProductTime;   // 开始 买家发货时间
    private Date endDeliverProductTime;     // 结束 买家发货时间
    private Date beginProductReceiptTime;   // 开始 商家收货时间
    private Date endProductReceiptTime;     // 结束 商家收货时间
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public TradeReturnOrderBase() {
        super();
    }

    public TradeReturnOrderBase(Long id) {
        super(id);
        this.returnOrderId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return returnOrderId;
    }

    /**
     * 描述: 设置ID
     *
     * @param id
     * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
        this.returnOrderId = id;
    }

    /**
     * getter returnOrderId(主键)
     */
    public Long getReturnOrderId() {
        return returnOrderId;
    }

    /**
     * setter returnOrderId(主键)
     */
    public void setReturnOrderId(Long returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    /**
     * getter orderId(订单编号(订单表id))
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * setter orderId(订单编号(订单表id))
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * getter orderItemId(订单详情编号(订单详情表id))
     */
    public Long getOrderItemId() {
        return orderItemId;
    }

    /**
     * setter orderItemId(订单详情编号(订单详情表id))
     */
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * getter type(类型，1退货退款、2退款)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(类型，1退货退款、2退款)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter returnMoney(退款金额)
     */
    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    /**
     * setter returnMoney(退款金额)
     */
    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    /**
     * getter returnCommission(退还佣金)
     */
    public BigDecimal getReturnCommission() {
        return returnCommission;
    }

    /**
     * setter returnCommission(退还佣金)
     */
    public void setReturnCommission(BigDecimal returnCommission) {
        this.returnCommission = returnCommission;
    }

    /**
     * getter returnFreight(退还运费)
     */
    public BigDecimal getReturnFreight() {
        return returnFreight;
    }

    /**
     * setter returnFreight(退还运费)
     */
    public void setReturnFreight(BigDecimal returnFreight) {
        this.returnFreight = returnFreight;
    }

    /**
     * getter returnCount(退货数量)
     */
    public Integer getReturnCount() {
        return returnCount;
    }

    /**
     * setter returnCount(退货数量)
     */
    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    /**
     * getter returnReason(退货原因(从字典里取))
     */
    public String getReturnReason() {
        return returnReason;
    }

    /**
     * setter returnReason(退货原因(从字典里取))
     */
    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    /**
     * getter returnExplain(退货说明)
     */
    public String getReturnExplain() {
        return returnExplain;
    }

    /**
     * setter returnExplain(退货说明)
     */
    public void setReturnExplain(String returnExplain) {
        this.returnExplain = returnExplain;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter applyDate(申请时间)
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * setter applyDate(申请时间)
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    /**
     * getter uId(申请人(会员表id))
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(申请人(会员表id))
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter businessHandle(商家处理，0待审核、1同意、2拒绝)
     */
    public String getBusinessHandle() {
        return businessHandle;
    }

    /**
     * setter businessHandle(商家处理，0待审核、1同意、2拒绝)
     */
    public void setBusinessHandle(String businessHandle) {
        this.businessHandle = businessHandle;
    }

    /**
     * getter businessHandleRemarks(商家处理备注)
     */
    public String getBusinessHandleRemarks() {
        return businessHandleRemarks;
    }

    /**
     * setter businessHandleRemarks(商家处理备注)
     */
    public void setBusinessHandleRemarks(String businessHandleRemarks) {
        this.businessHandleRemarks = businessHandleRemarks;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter businessHandleDate(商家处理时间)
     */
    public Date getBusinessHandleDate() {
        return businessHandleDate;
    }

    /**
     * setter businessHandleDate(商家处理时间)
     */
    public void setBusinessHandleDate(Date businessHandleDate) {
        this.businessHandleDate = businessHandleDate;
    }

    /**
     * getter systemHandle(平台处理，0未处理、1已审核并退款、2拒绝退款)
     */
    public Long getSystemHandle() {
        return systemHandle;
    }

    /**
     * setter systemHandle(平台处理，0未处理、1已审核并退款、2拒绝退款)
     */
    public void setSystemHandle(Long systemHandle) {
        this.systemHandle = systemHandle;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter systemAgreeTime(平台同意退款时间（结算时以此时间为准）)
     */
    public Date getSystemAgreeTime() {
        return systemAgreeTime;
    }

    /**
     * setter systemAgreeTime(平台同意退款时间（结算时以此时间为准）)
     */
    public void setSystemAgreeTime(Date systemAgreeTime) {
        this.systemAgreeTime = systemAgreeTime;
    }

    /**
     * getter systemHandleRemarks(平台处理备注)
     */
    public String getSystemHandleRemarks() {
        return systemHandleRemarks;
    }

    /**
     * setter systemHandleRemarks(平台处理备注)
     */
    public void setSystemHandleRemarks(String systemHandleRemarks) {
        this.systemHandleRemarks = systemHandleRemarks;
    }

    /**
     * getter payWayId(支付方式(支付方式表id))
     */
    public Long getPayWayId() {
        return payWayId;
    }

    /**
     * setter payWayId(支付方式(支付方式表id))
     */
    public void setPayWayId(Long payWayId) {
        this.payWayId = payWayId;
    }

    /**
     * getter adminId(管理员id(管理员表id))
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * setter adminId(管理员id(管理员表id))
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * getter status(状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter deliverProductTime(买家发货时间)
     */
    public Date getDeliverProductTime() {
        return deliverProductTime;
    }

    /**
     * setter deliverProductTime(买家发货时间)
     */
    public void setDeliverProductTime(Date deliverProductTime) {
        this.deliverProductTime = deliverProductTime;
    }

    /**
     * getter logisticsTemplateId(物流公司id(物流公司表外键，买家发货使用的物流公司))
     */
    public Long getLogisticsTemplateId() {
        return logisticsTemplateId;
    }

    /**
     * setter logisticsTemplateId(物流公司id(物流公司表外键，买家发货使用的物流公司))
     */
    public void setLogisticsTemplateId(Long logisticsTemplateId) {
        this.logisticsTemplateId = logisticsTemplateId;
    }

    /**
     * getter logisticsTemplateName(物流公司名称)
     */
    public String getLogisticsTemplateName() {
        return logisticsTemplateName;
    }

    /**
     * setter logisticsTemplateName(物流公司名称)
     */
    public void setLogisticsTemplateName(String logisticsTemplateName) {
        this.logisticsTemplateName = logisticsTemplateName;
    }

    /**
     * getter trackingNo(运单号)
     */
    public String getTrackingNo() {
        return trackingNo;
    }

    /**
     * setter trackingNo(运单号)
     */
    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    /**
     * getter isProductReceipt(商家是否收货，0否、1是)
     */
    public String getIsProductReceipt() {
        return isProductReceipt;
    }

    /**
     * setter isProductReceipt(商家是否收货，0否、1是)
     */
    public void setIsProductReceipt(String isProductReceipt) {
        this.isProductReceipt = isProductReceipt;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter productReceiptTime(商家收货时间)
     */
    public Date getProductReceiptTime() {
        return productReceiptTime;
    }

    /**
     * setter productReceiptTime(商家收货时间)
     */
    public void setProductReceiptTime(Date productReceiptTime) {
        this.productReceiptTime = productReceiptTime;
    }

    /**
     * getter onlineReturnMoney(在线退款金额)
     */
    public BigDecimal getOnlineReturnMoney() {
        return onlineReturnMoney;
    }

    /**
     * setter onlineReturnMoney(在线退款金额)
     */
    public void setOnlineReturnMoney(BigDecimal onlineReturnMoney) {
        this.onlineReturnMoney = onlineReturnMoney;
    }

    /**
     * getter alipayReturnMoney(支付宝退款金额)
     */
    public BigDecimal getAlipayReturnMoney() {
        return alipayReturnMoney;
    }

    /**
     * setter alipayReturnMoney(支付宝退款金额)
     */
    public void setAlipayReturnMoney(BigDecimal alipayReturnMoney) {
        this.alipayReturnMoney = alipayReturnMoney;
    }

    /**
     * getter preDepositReturnMoney(预存款退款金额)
     */
    public BigDecimal getPreDepositReturnMoney() {
        return preDepositReturnMoney;
    }

    /**
     * setter preDepositReturnMoney(预存款退款金额)
     */
    public void setPreDepositReturnMoney(BigDecimal preDepositReturnMoney) {
        this.preDepositReturnMoney = preDepositReturnMoney;
    }

    /**
     * getter wxReturnMoeny(微信退款金额)
     */
    public BigDecimal getWxReturnMoeny() {
        return wxReturnMoeny;
    }

    /**
     * setter wxReturnMoeny(微信退款金额)
     */
    public void setWxReturnMoeny(BigDecimal wxReturnMoeny) {
        this.wxReturnMoeny = wxReturnMoeny;
    }

    /**
     * getter bak1ReturnMoeny(备用退款金额1)
     */
    public BigDecimal getBak1ReturnMoeny() {
        return bak1ReturnMoeny;
    }

    /**
     * setter bak1ReturnMoeny(备用退款金额1)
     */
    public void setBak1ReturnMoeny(BigDecimal bak1ReturnMoeny) {
        this.bak1ReturnMoeny = bak1ReturnMoeny;
    }

    /**
     * getter bak2ReturnMoeny(备用退款金额2)
     */
    public BigDecimal getBak2ReturnMoeny() {
        return bak2ReturnMoeny;
    }

    /**
     * setter bak2ReturnMoeny(备用退款金额2)
     */
    public void setBak2ReturnMoeny(BigDecimal bak2ReturnMoeny) {
        this.bak2ReturnMoeny = bak2ReturnMoeny;
    }

    /**
     * getter storeId(关联(店铺表))
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * setter storeId(关联(店铺表))
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * getter isJettison(是否弃货)
     */
    public String getIsJettison() {
        return isJettison;
    }

    /**
     * setter isJettison(是否弃货)
     */
    public void setIsJettison(String isJettison) {
        this.isJettison = isJettison;
    }

    /**
     * getter isReturnMoney(是否退款)
     */
    public String getIsReturnMoney() {
        return isReturnMoney;
    }

    /**
     * setter isReturnMoney(是否退款)
     */
    public void setIsReturnMoney(String isReturnMoney) {
        this.isReturnMoney = isReturnMoney;
    }

    /**
     * getter bak1(备用字段1)
     */
    public String getBak1() {
        return bak1;
    }

    /**
     * setter bak1(备用字段1)
     */
    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    /**
     * getter bak2(备用字段2)
     */
    public String getBak2() {
        return bak2;
    }

    /**
     * setter bak2(备用字段2)
     */
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    /**
     * getter bak3(备用字段3)
     */
    public String getBak3() {
        return bak3;
    }

    /**
     * setter bak3(备用字段3)
     */
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }

    /**
     * getter bak4(备用字段4)
     */
    public String getBak4() {
        return bak4;
    }

    /**
     * setter bak4(备用字段4)
     */
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }

    /**
     * getter bak5(备用字段5)
     */
    public String getBak5() {
        return bak5;
    }

    /**
     * setter bak5(备用字段5)
     */
    public void setBak5(String bak5) {
        this.bak5 = bak5;
    }

    /**
     * getter bak6(备用字段6)
     */
    public String getBak6() {
        return bak6;
    }

    /**
     * setter bak6(备用字段6)
     */
    public void setBak6(String bak6) {
        this.bak6 = bak6;
    }

    /**
     * getter bak7(备用字段7)
     */
    public String getBak7() {
        return bak7;
    }

    /**
     * setter bak7(备用字段7)
     */
    public void setBak7(String bak7) {
        this.bak7 = bak7;
    }

    /**
     * getter bak8(备用字段8)
     */
    public String getBak8() {
        return bak8;
    }

    /**
     * setter bak8(备用字段8)
     */
    public void setBak8(String bak8) {
        this.bak8 = bak8;
    }

    /**
     * getter bak9(备用字段9)
     */
    public String getBak9() {
        return bak9;
    }

    /**
     * setter bak9(备用字段9)
     */
    public void setBak9(String bak9) {
        this.bak9 = bak9;
    }

    /**
     * getter bak10(备用字段10)
     */
    public String getBak10() {
        return bak10;
    }

    /**
     * setter bak10(备用字段10)
     */
    public void setBak10(String bak10) {
        this.bak10 = bak10;
    }

    /**
     * getter applyDate(申请时间)
     */
    public Date getBeginApplyDate() {
        return beginApplyDate;
    }

    /**
     * setter applyDate(申请时间)
     */
    public void setBeginApplyDate(Date beginApplyDate) {
        this.beginApplyDate = beginApplyDate;
    }

    /**
     * getter applyDate(申请时间)
     */
    public Date getEndApplyDate() {
        return endApplyDate;
    }

    /**
     * setter applyDate(申请时间)
     */
    public void setEndApplyDate(Date endApplyDate) {
        this.endApplyDate = endApplyDate;
    }

    /**
     * getter businessHandleDate(商家处理时间)
     */
    public Date getBeginBusinessHandleDate() {
        return beginBusinessHandleDate;
    }

    /**
     * setter businessHandleDate(商家处理时间)
     */
    public void setBeginBusinessHandleDate(Date beginBusinessHandleDate) {
        this.beginBusinessHandleDate = beginBusinessHandleDate;
    }

    /**
     * getter businessHandleDate(商家处理时间)
     */
    public Date getEndBusinessHandleDate() {
        return endBusinessHandleDate;
    }

    /**
     * setter businessHandleDate(商家处理时间)
     */
    public void setEndBusinessHandleDate(Date endBusinessHandleDate) {
        this.endBusinessHandleDate = endBusinessHandleDate;
    }

    /**
     * getter systemAgreeTime(平台同意退款时间（结算时以此时间为准）)
     */
    public Date getBeginSystemAgreeTime() {
        return beginSystemAgreeTime;
    }

    /**
     * setter systemAgreeTime(平台同意退款时间（结算时以此时间为准）)
     */
    public void setBeginSystemAgreeTime(Date beginSystemAgreeTime) {
        this.beginSystemAgreeTime = beginSystemAgreeTime;
    }

    /**
     * getter systemAgreeTime(平台同意退款时间（结算时以此时间为准）)
     */
    public Date getEndSystemAgreeTime() {
        return endSystemAgreeTime;
    }

    /**
     * setter systemAgreeTime(平台同意退款时间（结算时以此时间为准）)
     */
    public void setEndSystemAgreeTime(Date endSystemAgreeTime) {
        this.endSystemAgreeTime = endSystemAgreeTime;
    }

    /**
     * getter deliverProductTime(买家发货时间)
     */
    public Date getBeginDeliverProductTime() {
        return beginDeliverProductTime;
    }

    /**
     * setter deliverProductTime(买家发货时间)
     */
    public void setBeginDeliverProductTime(Date beginDeliverProductTime) {
        this.beginDeliverProductTime = beginDeliverProductTime;
    }

    /**
     * getter deliverProductTime(买家发货时间)
     */
    public Date getEndDeliverProductTime() {
        return endDeliverProductTime;
    }

    /**
     * setter deliverProductTime(买家发货时间)
     */
    public void setEndDeliverProductTime(Date endDeliverProductTime) {
        this.endDeliverProductTime = endDeliverProductTime;
    }

    /**
     * getter productReceiptTime(商家收货时间)
     */
    public Date getBeginProductReceiptTime() {
        return beginProductReceiptTime;
    }

    /**
     * setter productReceiptTime(商家收货时间)
     */
    public void setBeginProductReceiptTime(Date beginProductReceiptTime) {
        this.beginProductReceiptTime = beginProductReceiptTime;
    }

    /**
     * getter productReceiptTime(商家收货时间)
     */
    public Date getEndProductReceiptTime() {
        return endProductReceiptTime;
    }

    /**
     * setter productReceiptTime(商家收货时间)
     */
    public void setEndProductReceiptTime(Date endProductReceiptTime) {
        this.endProductReceiptTime = endProductReceiptTime;
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