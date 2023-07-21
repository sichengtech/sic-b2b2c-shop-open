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
package com.sicheng.admin.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算账单 Entity 父类
 * @author fanxiuxiu
 * @version 2018-07-13
 */
public class SettlementBillBase<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = 1L;
	private Long billId;                    // 主键
	private String billType;                // 账单类型，1商品账单，2服务账单
	private Long storeId;                   // 关联(店铺表)，服务类型是1时有值
	private Date balanceDate;               // 出账日期
	private BigDecimal billAmount;          // 本期应结(元)
	private String status;                  // 账单状态，10账单已生成，等待商家确认、20商家已确认，等待平台审核、30平台已审核，等待平台付款、40平台已付款，平台结算完成
	private BigDecimal orderAmount;         // 订单总金额
	private BigDecimal platformCommission;  // 平台分佣金额
	private BigDecimal returnCommission;    // 退还佣金
	private BigDecimal refund;              // 退单金额
	private BigDecimal promotionExpenses;   // 店铺推广费用（元）(促销时用)
	private BigDecimal redPackets;          // 红包(促销时用)
	private BigDecimal returnRedPackets;    // 退还红包(促销时用)
	private BigDecimal deposit;             // 预定订单未退定金(元)(促销时用)
	private Date beginTime;                 // 账单开始时间
	private Date endTime;                   // 账单结束时间(大于开始时间，小于等于结束时间)
	private Date payDate;                   // 付款时间
	private String payPerson;               // 付款人
	private String payRemarks;              // 付款备注
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
	private Long tasksubid;                 // 子任务id
	private Date beginBalanceDate;          // 开始 出账日期
	private Date endBalanceDate;            // 结束 出账日期
	private Date beginCreateDate;           // 开始 创建时间
	private Date endCreateDate;             // 结束 创建时间
	private Date beginUpdateDate;           // 开始 更新时间
	private Date endUpdateDate;             // 结束 更新时间
	private Date beginBeginTime;            // 开始 账单开始时间
	private Date endBeginTime;              // 结束 账单开始时间
	private Date beginEndTime;              // 开始 账单结束时间(大于开始时间，小于等于结束时间)
	private Date endEndTime;                // 结束 账单结束时间(大于开始时间，小于等于结束时间)
	private Date beginPayDate;              // 开始 付款时间
	private Date endPayDate;                // 结束 付款时间
	public SettlementBillBase() {
		super();
	}

	public SettlementBillBase(Long id){
		super(id);
		this.billId = id;
	}
	
	/**   
	 * 描述: 获取ID  
	 * @return   
	 * @see com.sicheng.common.persistence.BaseEntity#getId()   
	 */
	@Override
	public Long getId() {
		return billId;
	}

	/**   
	 * 描述: 设置ID
	 * @param id   
	 * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
	 */	
	@Override
	public void setId(Long id) {
		this.id = id;
		this.billId = id;
	}

	/**
	 * getter billId(主键)
	 */				
	public Long getBillId() {
		return billId;
	}

	/**
	 * setter billId(主键)
	 */	
	public void setBillId(Long billId) {
		this.billId = billId;
	}

	/**
	 * getter billType(账单类型，1商品账单，2服务账单)
	 */				
	public String getBillType() {
		return billType;
	}

	/**
	 * setter billType(账单类型，1商品账单，2服务账单)
	 */	
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * getter storeId(关联(店铺表)，服务类型是1时有值)
	 */				
	public Long getStoreId() {
		return storeId;
	}

	/**
	 * setter storeId(关联(店铺表)，服务类型是1时有值)
	 */	
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter balanceDate(出账日期)
	 */				
	public Date getBalanceDate() {
		return balanceDate;
	}

	/**
	 * setter balanceDate(出账日期)
	 */	
	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	/**
	 * getter billAmount(本期应结(元))
	 */				
	public BigDecimal getBillAmount() {
		return billAmount;
	}

	/**
	 * setter billAmount(本期应结(元))
	 */	
	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	/**
	 * getter status(账单状态，10账单已生成，等待商家确认、20商家已确认，等待平台审核、30平台已审核，等待平台付款、40平台已付款，平台结算完成)
	 */				
	public String getStatus() {
		return status;
	}

	/**
	 * setter status(账单状态，10账单已生成，等待商家确认、20商家已确认，等待平台审核、30平台已审核，等待平台付款、40平台已付款，平台结算完成)
	 */	
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * getter orderAmount(订单总金额)
	 */				
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	/**
	 * setter orderAmount(订单总金额)
	 */	
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * getter platformCommission(平台分佣金额)
	 */				
	public BigDecimal getPlatformCommission() {
		return platformCommission;
	}

	/**
	 * setter platformCommission(平台分佣金额)
	 */	
	public void setPlatformCommission(BigDecimal platformCommission) {
		this.platformCommission = platformCommission;
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
	 * getter refund(退单金额)
	 */				
	public BigDecimal getRefund() {
		return refund;
	}

	/**
	 * setter refund(退单金额)
	 */	
	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	/**
	 * getter promotionExpenses(店铺推广费用（元）(促销时用))
	 */				
	public BigDecimal getPromotionExpenses() {
		return promotionExpenses;
	}

	/**
	 * setter promotionExpenses(店铺推广费用（元）(促销时用))
	 */	
	public void setPromotionExpenses(BigDecimal promotionExpenses) {
		this.promotionExpenses = promotionExpenses;
	}

	/**
	 * getter redPackets(红包(促销时用))
	 */				
	public BigDecimal getRedPackets() {
		return redPackets;
	}

	/**
	 * setter redPackets(红包(促销时用))
	 */	
	public void setRedPackets(BigDecimal redPackets) {
		this.redPackets = redPackets;
	}

	/**
	 * getter returnRedPackets(退还红包(促销时用))
	 */				
	public BigDecimal getReturnRedPackets() {
		return returnRedPackets;
	}

	/**
	 * setter returnRedPackets(退还红包(促销时用))
	 */	
	public void setReturnRedPackets(BigDecimal returnRedPackets) {
		this.returnRedPackets = returnRedPackets;
	}

	/**
	 * getter deposit(预定订单未退定金(元)(促销时用))
	 */				
	public BigDecimal getDeposit() {
		return deposit;
	}

	/**
	 * setter deposit(预定订单未退定金(元)(促销时用))
	 */	
	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter beginTime(账单开始时间)
	 */				
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * setter beginTime(账单开始时间)
	 */	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter endTime(账单结束时间(大于开始时间，小于等于结束时间))
	 */				
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * setter endTime(账单结束时间(大于开始时间，小于等于结束时间))
	 */	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/**
	 * getter payDate(付款时间)
	 */				
	public Date getPayDate() {
		return payDate;
	}

	/**
	 * setter payDate(付款时间)
	 */	
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	/**
	 * getter payPerson(付款人)
	 */				
	public String getPayPerson() {
		return payPerson;
	}

	/**
	 * setter payPerson(付款人)
	 */	
	public void setPayPerson(String payPerson) {
		this.payPerson = payPerson;
	}

	/**
	 * getter payRemarks(付款备注)
	 */				
	public String getPayRemarks() {
		return payRemarks;
	}

	/**
	 * setter payRemarks(付款备注)
	 */	
	public void setPayRemarks(String payRemarks) {
		this.payRemarks = payRemarks;
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
	 * getter tasksubid(子任务id)
	 */				
	public Long getTasksubid() {
		return tasksubid;
	}

	/**
	 * setter tasksubid(子任务id)
	 */	
	public void setTasksubid(Long tasksubid) {
		this.tasksubid = tasksubid;
	}

	/**
	 * getter balanceDate(出账日期)
	 */
	public Date getBeginBalanceDate() {
		return beginBalanceDate;
	}

	/**
	 * setter balanceDate(出账日期)
	 */	
	public void setBeginBalanceDate(Date beginBalanceDate) {
		this.beginBalanceDate = beginBalanceDate;
	}
	
	/**
	 * getter balanceDate(出账日期)
	 */		
	public Date getEndBalanceDate() {
		return endBalanceDate;
	}

	/**
	 * setter balanceDate(出账日期)
	 */	
	public void setEndBalanceDate(Date endBalanceDate) {
		this.endBalanceDate = endBalanceDate;
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
	 * getter beginTime(账单开始时间)
	 */
	public Date getBeginBeginTime() {
		return beginBeginTime;
	}

	/**
	 * setter beginTime(账单开始时间)
	 */	
	public void setBeginBeginTime(Date beginBeginTime) {
		this.beginBeginTime = beginBeginTime;
	}
	
	/**
	 * getter beginTime(账单开始时间)
	 */		
	public Date getEndBeginTime() {
		return endBeginTime;
	}

	/**
	 * setter beginTime(账单开始时间)
	 */	
	public void setEndBeginTime(Date endBeginTime) {
		this.endBeginTime = endBeginTime;
	}
	/**
	 * getter endTime(账单结束时间(大于开始时间，小于等于结束时间))
	 */
	public Date getBeginEndTime() {
		return beginEndTime;
	}

	/**
	 * setter endTime(账单结束时间(大于开始时间，小于等于结束时间))
	 */	
	public void setBeginEndTime(Date beginEndTime) {
		this.beginEndTime = beginEndTime;
	}
	
	/**
	 * getter endTime(账单结束时间(大于开始时间，小于等于结束时间))
	 */		
	public Date getEndEndTime() {
		return endEndTime;
	}

	/**
	 * setter endTime(账单结束时间(大于开始时间，小于等于结束时间))
	 */	
	public void setEndEndTime(Date endEndTime) {
		this.endEndTime = endEndTime;
	}
	/**
	 * getter payDate(付款时间)
	 */
	public Date getBeginPayDate() {
		return beginPayDate;
	}

	/**
	 * setter payDate(付款时间)
	 */	
	public void setBeginPayDate(Date beginPayDate) {
		this.beginPayDate = beginPayDate;
	}
	
	/**
	 * getter payDate(付款时间)
	 */		
	public Date getEndPayDate() {
		return endPayDate;
	}

	/**
	 * setter payDate(付款时间)
	 */	
	public void setEndPayDate(Date endPayDate) {
		this.endPayDate = endPayDate;
	}
	
}