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
package com.sicheng.admin.settlement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现 Entity 父类
 *
 * @author fxx
 * @version 2017-05-12
 */
public class SettlementWithdrawalsBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long withdrawalsId;             // 主键
    private String rechargeNumber;          // 提现编号
    private Long uId;                       // 会员id(会员表id)
    private BigDecimal money;               // 提现金额
    private Long receivablesNumber;         // 收款账号
    private Long payWayId;                  // 收款方式(支付方式id)
    private String status;                  // 提现状态，0未支付、1已支付、2拒绝提现
    private String transactionNumber;       // 交易号
    private String payTerminal;             // 提现终端，0pc端1、移动端
    private Date applyDate;                 // 申请时间
    private Date payTime;                   // 支付时间
    private Long adminId;                   // 管理员(管理员表id)
    private String rejectionReason;         // 拒绝提现理由
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
    private String accountName;             // 开户名
    private Date beginApplyDate;            // 开始 申请时间
    private Date endApplyDate;              // 结束 申请时间
    private Date beginPayTime;              // 开始 支付时间
    private Date endPayTime;                // 结束 支付时间
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SettlementWithdrawalsBase() {
        super();
    }

    public SettlementWithdrawalsBase(Long id) {
        super(id);
        this.withdrawalsId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return withdrawalsId;
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
        this.withdrawalsId = id;
    }

    /**
     * getter withdrawalsId(主键)
     */
    public Long getWithdrawalsId() {
        return withdrawalsId;
    }

    /**
     * setter withdrawalsId(主键)
     */
    public void setWithdrawalsId(Long withdrawalsId) {
        this.withdrawalsId = withdrawalsId;
    }

    /**
     * getter rechargeNumber(提现编号)
     */
    public String getRechargeNumber() {
        return rechargeNumber;
    }

    /**
     * setter rechargeNumber(提现编号)
     */
    public void setRechargeNumber(String rechargeNumber) {
        this.rechargeNumber = rechargeNumber;
    }

    /**
     * getter uId(会员id(会员表id))
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(会员id(会员表id))
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter money(提现金额)
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * setter money(提现金额)
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * getter receivablesNumber(收款账号)
     */
    public Long getReceivablesNumber() {
        return receivablesNumber;
    }

    /**
     * setter receivablesNumber(收款账号)
     */
    public void setReceivablesNumber(Long receivablesNumber) {
        this.receivablesNumber = receivablesNumber;
    }

    /**
     * getter payWayId(收款方式(支付方式id))
     */
    public Long getPayWayId() {
        return payWayId;
    }

    /**
     * setter payWayId(收款方式(支付方式id))
     */
    public void setPayWayId(Long payWayId) {
        this.payWayId = payWayId;
    }

    /**
     * getter status(提现状态，0未支付、1已支付、2拒绝提现)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(提现状态，0未支付、1已支付、2拒绝提现)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter transactionNumber(交易号)
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * setter transactionNumber(交易号)
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * getter payTerminal(提现终端，0pc端1、移动端)
     */
    public String getPayTerminal() {
        return payTerminal;
    }

    /**
     * setter payTerminal(提现终端，0pc端1、移动端)
     */
    public void setPayTerminal(String payTerminal) {
        this.payTerminal = payTerminal;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter payTime(支付时间)
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * setter payTime(支付时间)
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * getter adminId(管理员(管理员表id))
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * setter adminId(管理员(管理员表id))
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * getter rejectionReason(拒绝提现理由)
     */
    public String getRejectionReason() {
        return rejectionReason;
    }

    /**
     * setter rejectionReason(拒绝提现理由)
     */
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
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
     * getter accountName(开户名)
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * setter accountName(开户名)
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
     * getter payTime(支付时间)
     */
    public Date getBeginPayTime() {
        return beginPayTime;
    }

    /**
     * setter payTime(支付时间)
     */
    public void setBeginPayTime(Date beginPayTime) {
        this.beginPayTime = beginPayTime;
    }

    /**
     * getter payTime(支付时间)
     */
    public Date getEndPayTime() {
        return endPayTime;
    }

    /**
     * setter payTime(支付时间)
     */
    public void setEndPayTime(Date endPayTime) {
        this.endPayTime = endPayTime;
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