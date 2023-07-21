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

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.sicheng.common.persistence.DataEntity;

/**
 * 订单基本信息 Entity 父类
 * @author 张加利
 * @version 2019-11-20
 */
public class TradeOrderBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long orderId;                   // 主键
	private Long uId;                       // 买家id(关联会员表)
	private String outTradeNo;              // 商户订单号
	private Long storeId;                   // 关联(店铺表)
	private String bName;                   // 商家公司名称(快照)
	private Long adminId;                   // 管理员id(关联管理员,管理员可以取消订单和确认收款)
	private Long addressId;                 // 收货地址id(关联收货地址)
	private String consignee;               // 收货人(快照)
	private String phone;                   // 电话(快照)
	private String zipCode;                 // 邮编(快照)
	private BigDecimal amountPaid;              // 订单总金额
	private BigDecimal offsetAmount;            // 调整后的金额
	private Long couponDiscount;            // 优惠券id(预留)
	private Long cashCouponId;              // 代金券id(预留)
	private BigDecimal promotionDiscount;       // 促销折扣，0.95表示95折(预留)
	private String promotion;               // 促销(预留)，促销活动名称
	private BigDecimal redPacket;               // 平台红包(预留)
	private BigDecimal fee;                     // 支付佣金
	private String orderStatus;             // 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
	private String isReturnStatus;          // 是否退货退款，0否、1退货退款、2退款
	private String paymentMethodName;       // 支付方式名称（快照）
	private Long paymentMethodId;           // 支付方式(支付方式表Id)
	private BigDecimal freight;                 // 运费，计算出来的总运费
	private String shippingMethodName;      // 运费模板名称(只用于显示)(快照)
	private Long shippingMethodId;          // 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
	private String point;                   // 赠送的积分
	private String isNeedLogistics;         // 是否需物流，0无需物流、1需物流
	private Long logisticsTemplateId;       // 物流公司id(物流公司表外键)
	private String logisticsTemplateName;   // 物流公司名称（快照）
	private String trackingNo;              // 运单号
	private String tax;                     // 税金
	private String isInvoice;               // 是否开据发票，0否、1是
	private Long deliverId;                 // 发票id(发票表id)
	private String memo;                    // 买家附言
	private Date placeOrderTime;            // 下单时间
	private Date payOrderTime;              // 付款时间（结算时以此时间为准）
	private Date deliverProductDate;        // 发货时间
	private Date productReceiptDate;        // 收货时间
	private String bak1;                    // 备用字段1
	private String bak2;                    // 备用字段2
	private String bak3;                    // 备用字段3
	private String bak4;                    // 备用字段4
	private String bak5;                    // 备用字段5
	private String bak6;                    // 备用字段6
	private String bak7;                    // 备用字段7
	private String bak8;                    // 备用字段8
	private String bak9;                    // 备用字段9
	private String bak10;                   // 备用字段10
	private String payOrderNumber;          // 支付单号
	private String sources;                 // 来源(0pc、1移动端)
	private BigDecimal preDepositPay;           // 预存款支付(元)
	private String payTerminal;             // 支付终端(0pc、1移动端)
	private String thirdPayOrderNumber;     // 第三方付款平台交易号
	private BigDecimal onlinePayMoney;          // 在线支付金额
	private String cancelOrderReason;       // 取消订单理由
	private String cityName;                // 收货地址所在市(快照)
	private String detailedAddress;         // 详细地址(快照)
	private String districtName;            // 收货地址所在县(快照)
	private String provinceName;            // 收货地址所在省(快照)
	private String sellerMemo;              // 卖家附言
	private Date cancelOrderDate;           // 取消订单时间
	private Integer delayDays;              // 延迟收货天数
	private String isAdditionalComment;     // 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
	private String isSettlement;            // 是否已结算
	private BigDecimal settlementMoney;         // 结算金额
	private String needPay;                 // 0.不需要、1.需要。
	private Date beginPlaceOrderTime;       // 开始 下单时间
	private Date endPlaceOrderTime;         // 结束 下单时间
	private Date beginPayOrderTime;         // 开始 付款时间（结算时以此时间为准）
	private Date endPayOrderTime;           // 结束 付款时间（结算时以此时间为准）
	private Date beginDeliverProductDate;   // 开始 发货时间
	private Date endDeliverProductDate;     // 结束 发货时间
	private Date beginProductReceiptDate;   // 开始 收货时间
	private Date endProductReceiptDate;     // 结束 收货时间
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	private Date beginCancelOrderDate;      // 开始 取消订单时间
	private Date endCancelOrderDate;        // 结束 取消订单时间
	public TradeOrderBase() {
		super();
	}

	public TradeOrderBase(Long id){
		super(id);
		this.orderId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return orderId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)   
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.orderId = id;
	}

	/**
	 * getter orderId(主键)
	 */				
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * setter orderId(主键)
	 */	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * getter outTradeNo(商户订单号)
	 */				
	public String getOutTradeNo() {
		return outTradeNo;
	}

	/**
	 * setter outTradeNo(商户订单号)
	 */	
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	/**
	 * getter uId(买家id(关联会员表))
	 */				
	public Long getUId() {
		return uId;
	}

	/**
	 * setter uId(买家id(关联会员表))
	 */	
	public void setUId(Long uId) {
		this.uId = uId;
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
	 * getter bName(商家公司名称(快照))
	 */				
	public String getBName() {
		return bName;
	}

	/**
	 * setter bName(商家公司名称(快照))
	 */	
	public void setBName(String bName) {
		this.bName = bName;
	}

	/**
	 * getter adminId(管理员id(关联管理员,管理员可以取消订单和确认收款))
	 */				
	public Long getAdminId() {
		return adminId;
	}

	/**
	 * setter adminId(管理员id(关联管理员,管理员可以取消订单和确认收款))
	 */	
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	/**
	 * getter addressId(收货地址id(关联收货地址))
	 */				
	public Long getAddressId() {
		return addressId;
	}

	/**
	 * setter addressId(收货地址id(关联收货地址))
	 */	
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	/**
	 * getter consignee(收货人(快照))
	 */				
	public String getConsignee() {
		return consignee;
	}

	/**
	 * setter consignee(收货人(快照))
	 */	
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * getter phone(电话(快照))
	 */				
	public String getPhone() {
		return phone;
	}

	/**
	 * setter phone(电话(快照))
	 */	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * getter zipCode(邮编(快照))
	 */				
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * setter zipCode(邮编(快照))
	 */	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * getter amountPaid(订单总金额)
	 */				
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	/**
	 * setter amountPaid(订单总金额)
	 */	
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	/**
	 * getter offsetAmount(调整后的金额)
	 */				
	public BigDecimal getOffsetAmount() {
		return offsetAmount;
	}

	/**
	 * setter offsetAmount(调整后的金额)
	 */	
	public void setOffsetAmount(BigDecimal offsetAmount) {
		this.offsetAmount = offsetAmount;
	}

	/**
	 * getter couponDiscount(优惠券id(预留))
	 */				
	public Long getCouponDiscount() {
		return couponDiscount;
	}

	/**
	 * setter couponDiscount(优惠券id(预留))
	 */	
	public void setCouponDiscount(Long couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	/**
	 * getter cashCouponId(代金券id(预留))
	 */				
	public Long getCashCouponId() {
		return cashCouponId;
	}

	/**
	 * setter cashCouponId(代金券id(预留))
	 */	
	public void setCashCouponId(Long cashCouponId) {
		this.cashCouponId = cashCouponId;
	}

	/**
	 * getter promotionDiscount(促销折扣，0.95表示95折(预留))
	 */				
	public BigDecimal getPromotionDiscount() {
		return promotionDiscount;
	}

	/**
	 * setter promotionDiscount(促销折扣，0.95表示95折(预留))
	 */	
	public void setPromotionDiscount(BigDecimal promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	/**
	 * getter promotion(促销(预留)，促销活动名称)
	 */				
	public String getPromotion() {
		return promotion;
	}

	/**
	 * setter promotion(促销(预留)，促销活动名称)
	 */	
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	/**
	 * getter redPacket(平台红包(预留))
	 */				
	public BigDecimal getRedPacket() {
		return redPacket;
	}

	/**
	 * setter redPacket(平台红包(预留))
	 */	
	public void setRedPacket(BigDecimal redPacket) {
		this.redPacket = redPacket;
	}

	/**
	 * getter fee(支付佣金)
	 */				
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * setter fee(支付佣金)
	 */	
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * getter orderStatus(订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中)
	 */				
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * setter orderStatus(订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中)
	 */	
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * getter isReturnStatus(是否退货退款，0否、1退货退款、2退款)
	 */				
	public String getIsReturnStatus() {
		return isReturnStatus;
	}

	/**
	 * setter isReturnStatus(是否退货退款，0否、1退货退款、2退款)
	 */	
	public void setIsReturnStatus(String isReturnStatus) {
		this.isReturnStatus = isReturnStatus;
	}

	/**
	 * getter paymentMethodName(支付方式名称（快照）)
	 */				
	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	/**
	 * setter paymentMethodName(支付方式名称（快照）)
	 */	
	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	/**
	 * getter paymentMethodId(支付方式(支付方式表Id))
	 */				
	public Long getPaymentMethodId() {
		return paymentMethodId;
	}

	/**
	 * setter paymentMethodId(支付方式(支付方式表Id))
	 */	
	public void setPaymentMethodId(Long paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	/**
	 * getter freight(运费，计算出来的总运费)
	 */				
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * setter freight(运费，计算出来的总运费)
	 */	
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	/**
	 * getter shippingMethodName(运费模板名称(只用于显示)(快照))
	 */				
	public String getShippingMethodName() {
		return shippingMethodName;
	}

	/**
	 * setter shippingMethodName(运费模板名称(只用于显示)(快照))
	 */	
	public void setShippingMethodName(String shippingMethodName) {
		this.shippingMethodName = shippingMethodName;
	}

	/**
	 * getter shippingMethodId(运费模板详情Id,dti_id(通过此id可间接找到运费模板id))
	 */				
	public Long getShippingMethodId() {
		return shippingMethodId;
	}

	/**
	 * setter shippingMethodId(运费模板详情Id,dti_id(通过此id可间接找到运费模板id))
	 */	
	public void setShippingMethodId(Long shippingMethodId) {
		this.shippingMethodId = shippingMethodId;
	}

	/**
	 * getter point(赠送的积分)
	 */				
	public String getPoint() {
		return point;
	}

	/**
	 * setter point(赠送的积分)
	 */	
	public void setPoint(String point) {
		this.point = point;
	}

	/**
	 * getter isNeedLogistics(是否需物流，0无需物流、1需物流)
	 */				
	public String getIsNeedLogistics() {
		return isNeedLogistics;
	}

	/**
	 * setter isNeedLogistics(是否需物流，0无需物流、1需物流)
	 */	
	public void setIsNeedLogistics(String isNeedLogistics) {
		this.isNeedLogistics = isNeedLogistics;
	}

	/**
	 * getter logisticsTemplateId(物流公司id(物流公司表外键))
	 */				
	public Long getLogisticsTemplateId() {
		return logisticsTemplateId;
	}

	/**
	 * setter logisticsTemplateId(物流公司id(物流公司表外键))
	 */	
	public void setLogisticsTemplateId(Long logisticsTemplateId) {
		this.logisticsTemplateId = logisticsTemplateId;
	}

	/**
	 * getter logisticsTemplateName(物流公司名称（快照）)
	 */				
	public String getLogisticsTemplateName() {
		return logisticsTemplateName;
	}

	/**
	 * setter logisticsTemplateName(物流公司名称（快照）)
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
	 * getter tax(税金)
	 */				
	public String getTax() {
		return tax;
	}

	/**
	 * setter tax(税金)
	 */	
	public void setTax(String tax) {
		this.tax = tax;
	}

	/**
	 * getter isInvoice(是否开据发票，0否、1是)
	 */				
	public String getIsInvoice() {
		return isInvoice;
	}

	/**
	 * setter isInvoice(是否开据发票，0否、1是)
	 */	
	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	/**
	 * getter deliverId(发票id(发票表id))
	 */				
	public Long getDeliverId() {
		return deliverId;
	}

	/**
	 * setter deliverId(发票id(发票表id))
	 */	
	public void setDeliverId(Long deliverId) {
		this.deliverId = deliverId;
	}

	/**
	 * getter memo(买家附言)
	 */				
	public String getMemo() {
		return memo;
	}

	/**
	 * setter memo(买家附言)
	 */	
	public void setMemo(String memo) {
		this.memo = memo;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter placeOrderTime(下单时间)
	 */				
	public Date getPlaceOrderTime() {
		return placeOrderTime;
	}

	/**
	 * setter placeOrderTime(下单时间)
	 */	
	public void setPlaceOrderTime(Date placeOrderTime) {
		this.placeOrderTime = placeOrderTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter payOrderTime(付款时间（结算时以此时间为准）)
	 */				
	public Date getPayOrderTime() {
		return payOrderTime;
	}

	/**
	 * setter payOrderTime(付款时间（结算时以此时间为准）)
	 */	
	public void setPayOrderTime(Date payOrderTime) {
		this.payOrderTime = payOrderTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter deliverProductDate(发货时间)
	 */				
	public Date getDeliverProductDate() {
		return deliverProductDate;
	}

	/**
	 * setter deliverProductDate(发货时间)
	 */	
	public void setDeliverProductDate(Date deliverProductDate) {
		this.deliverProductDate = deliverProductDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter productReceiptDate(收货时间)
	 */				
	public Date getProductReceiptDate() {
		return productReceiptDate;
	}

	/**
	 * setter productReceiptDate(收货时间)
	 */	
	public void setProductReceiptDate(Date productReceiptDate) {
		this.productReceiptDate = productReceiptDate;
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
	 * getter payOrderNumber(支付单号)
	 */				
	public String getPayOrderNumber() {
		return payOrderNumber;
	}

	/**
	 * setter payOrderNumber(支付单号)
	 */	
	public void setPayOrderNumber(String payOrderNumber) {
		this.payOrderNumber = payOrderNumber;
	}

	/**
	 * getter sources(来源(0pc、1移动端))
	 */				
	public String getSources() {
		return sources;
	}

	/**
	 * setter sources(来源(0pc、1移动端))
	 */	
	public void setSources(String sources) {
		this.sources = sources;
	}

	/**
	 * getter preDepositPay(预存款支付(元))
	 */				
	public BigDecimal getPreDepositPay() {
		return preDepositPay;
	}

	/**
	 * setter preDepositPay(预存款支付(元))
	 */	
	public void setPreDepositPay(BigDecimal preDepositPay) {
		this.preDepositPay = preDepositPay;
	}

	/**
	 * getter payTerminal(支付终端(0pc、1移动端))
	 */				
	public String getPayTerminal() {
		return payTerminal;
	}

	/**
	 * setter payTerminal(支付终端(0pc、1移动端))
	 */	
	public void setPayTerminal(String payTerminal) {
		this.payTerminal = payTerminal;
	}

	/**
	 * getter thirdPayOrderNumber(第三方付款平台交易号)
	 */				
	public String getThirdPayOrderNumber() {
		return thirdPayOrderNumber;
	}

	/**
	 * setter thirdPayOrderNumber(第三方付款平台交易号)
	 */	
	public void setThirdPayOrderNumber(String thirdPayOrderNumber) {
		this.thirdPayOrderNumber = thirdPayOrderNumber;
	}

	/**
	 * getter onlinePayMoney(在线支付金额)
	 */				
	public BigDecimal getOnlinePayMoney() {
		return onlinePayMoney;
	}

	/**
	 * setter onlinePayMoney(在线支付金额)
	 */	
	public void setOnlinePayMoney(BigDecimal onlinePayMoney) {
		this.onlinePayMoney = onlinePayMoney;
	}

	/**
	 * getter cancelOrderReason(取消订单理由)
	 */				
	public String getCancelOrderReason() {
		return cancelOrderReason;
	}

	/**
	 * setter cancelOrderReason(取消订单理由)
	 */	
	public void setCancelOrderReason(String cancelOrderReason) {
		this.cancelOrderReason = cancelOrderReason;
	}

	/**
	 * getter cityName(收货地址所在市(快照))
	 */				
	public String getCityName() {
		return cityName;
	}

	/**
	 * setter cityName(收货地址所在市(快照))
	 */	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * getter detailedAddress(详细地址(快照))
	 */				
	public String getDetailedAddress() {
		return detailedAddress;
	}

	/**
	 * setter detailedAddress(详细地址(快照))
	 */	
	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	/**
	 * getter districtName(收货地址所在县(快照))
	 */				
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * setter districtName(收货地址所在县(快照))
	 */	
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * getter provinceName(收货地址所在省(快照))
	 */				
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * setter provinceName(收货地址所在省(快照))
	 */	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * getter sellerMemo(卖家附言)
	 */				
	public String getSellerMemo() {
		return sellerMemo;
	}

	/**
	 * setter sellerMemo(卖家附言)
	 */	
	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter cancelOrderDate(取消订单时间)
	 */				
	public Date getCancelOrderDate() {
		return cancelOrderDate;
	}

	/**
	 * setter cancelOrderDate(取消订单时间)
	 */	
	public void setCancelOrderDate(Date cancelOrderDate) {
		this.cancelOrderDate = cancelOrderDate;
	}

	/**
	 * getter delayDays(延迟收货天数)
	 */				
	public Integer getDelayDays() {
		return delayDays;
	}

	/**
	 * setter delayDays(延迟收货天数)
	 */	
	public void setDelayDays(Integer delayDays) {
		this.delayDays = delayDays;
	}

	/**
	 * getter isAdditionalComment(是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评))
	 */				
	public String getIsAdditionalComment() {
		return isAdditionalComment;
	}

	/**
	 * setter isAdditionalComment(是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评))
	 */	
	public void setIsAdditionalComment(String isAdditionalComment) {
		this.isAdditionalComment = isAdditionalComment;
	}

	/**
	 * getter isSettlement(是否已结算)
	 */				
	public String getIsSettlement() {
		return isSettlement;
	}

	/**
	 * setter isSettlement(是否已结算)
	 */	
	public void setIsSettlement(String isSettlement) {
		this.isSettlement = isSettlement;
	}

	/**
	 * getter settlementMoney(结算金额)
	 */				
	public BigDecimal getSettlementMoney() {
		return settlementMoney;
	}

	/**
	 * setter settlementMoney(结算金额)
	 */	
	public void setSettlementMoney(BigDecimal settlementMoney) {
		this.settlementMoney = settlementMoney;
	}

	/**
	 * getter needPay(0.不需要、1.需要。)
	 */				
	public String getNeedPay() {
		return needPay;
	}

	/**
	 * setter needPay(0.不需要、1.需要。)
	 */	
	public void setNeedPay(String needPay) {
		this.needPay = needPay;
	}

	/**
	 * getter placeOrderTime(下单时间)
	 */
	public Date getBeginPlaceOrderTime() {
		return beginPlaceOrderTime;
	}

	/**
	 * setter placeOrderTime(下单时间)
	 */	
	public void setBeginPlaceOrderTime(Date beginPlaceOrderTime) {
		this.beginPlaceOrderTime = beginPlaceOrderTime;
	}
	
	/**
	 * getter placeOrderTime(下单时间)
	 */		
	public Date getEndPlaceOrderTime() {
		return endPlaceOrderTime;
	}

	/**
	 * setter placeOrderTime(下单时间)
	 */	
	public void setEndPlaceOrderTime(Date endPlaceOrderTime) {
		this.endPlaceOrderTime = endPlaceOrderTime;
	}
	/**
	 * getter payOrderTime(付款时间（结算时以此时间为准）)
	 */
	public Date getBeginPayOrderTime() {
		return beginPayOrderTime;
	}

	/**
	 * setter payOrderTime(付款时间（结算时以此时间为准）)
	 */	
	public void setBeginPayOrderTime(Date beginPayOrderTime) {
		this.beginPayOrderTime = beginPayOrderTime;
	}
	
	/**
	 * getter payOrderTime(付款时间（结算时以此时间为准）)
	 */		
	public Date getEndPayOrderTime() {
		return endPayOrderTime;
	}

	/**
	 * setter payOrderTime(付款时间（结算时以此时间为准）)
	 */	
	public void setEndPayOrderTime(Date endPayOrderTime) {
		this.endPayOrderTime = endPayOrderTime;
	}
	/**
	 * getter deliverProductDate(发货时间)
	 */
	public Date getBeginDeliverProductDate() {
		return beginDeliverProductDate;
	}

	/**
	 * setter deliverProductDate(发货时间)
	 */	
	public void setBeginDeliverProductDate(Date beginDeliverProductDate) {
		this.beginDeliverProductDate = beginDeliverProductDate;
	}
	
	/**
	 * getter deliverProductDate(发货时间)
	 */		
	public Date getEndDeliverProductDate() {
		return endDeliverProductDate;
	}

	/**
	 * setter deliverProductDate(发货时间)
	 */	
	public void setEndDeliverProductDate(Date endDeliverProductDate) {
		this.endDeliverProductDate = endDeliverProductDate;
	}
	/**
	 * getter productReceiptDate(收货时间)
	 */
	public Date getBeginProductReceiptDate() {
		return beginProductReceiptDate;
	}

	/**
	 * setter productReceiptDate(收货时间)
	 */	
	public void setBeginProductReceiptDate(Date beginProductReceiptDate) {
		this.beginProductReceiptDate = beginProductReceiptDate;
	}
	
	/**
	 * getter productReceiptDate(收货时间)
	 */		
	public Date getEndProductReceiptDate() {
		return endProductReceiptDate;
	}

	/**
	 * setter productReceiptDate(收货时间)
	 */	
	public void setEndProductReceiptDate(Date endProductReceiptDate) {
		this.endProductReceiptDate = endProductReceiptDate;
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
	/**
	 * getter cancelOrderDate(取消订单时间)
	 */
	public Date getBeginCancelOrderDate() {
		return beginCancelOrderDate;
	}

	/**
	 * setter cancelOrderDate(取消订单时间)
	 */	
	public void setBeginCancelOrderDate(Date beginCancelOrderDate) {
		this.beginCancelOrderDate = beginCancelOrderDate;
	}
	
	/**
	 * getter cancelOrderDate(取消订单时间)
	 */		
	public Date getEndCancelOrderDate() {
		return endCancelOrderDate;
	}

	/**
	 * setter cancelOrderDate(取消订单时间)
	 */	
	public void setEndCancelOrderDate(Date endCancelOrderDate) {
		this.endCancelOrderDate = endCancelOrderDate;
	}
	
}