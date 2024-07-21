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
 * 充值 Entity 父类
 *
 * @author fxx
 * @version 2017-05-12
 */
public class SettlementRechargeBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long rechargeId;                // 主键
    private String rechargeNumber;          // 充值编号
    private Long uId;                       // 会员id(会员表id)
    private BigDecimal rechargeMoney;       // 充值金额
    private Date rechargeTime;              // 充值时间
    private Date payDate;                   // 支付时间
    private Long payWayId;                  // 支付方式(支付方式id)
    private String staus;                   // 支付状态，0未支付、1已支付
    private String payTerminal;             // 支付终端，0pc端、1移动端
    private String tradeNumber;             // 交易号
    private Long adminId;                   // 管理员(管理员表id)
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
    private Date beginRechargeTime;         // 开始 充值时间
    private Date endRechargeTime;           // 结束 充值时间
    private Date beginPayDate;              // 开始 支付时间
    private Date endPayDate;                // 结束 支付时间
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SettlementRechargeBase() {
        super();
    }

    public SettlementRechargeBase(Long id) {
        super(id);
        this.rechargeId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return rechargeId;
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
        this.rechargeId = id;
    }

    /**
     * getter rechargeId(主键)
     */
    public Long getRechargeId() {
        return rechargeId;
    }

    /**
     * setter rechargeId(主键)
     */
    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
    }

    /**
     * getter rechargeNumber(充值编号)
     */
    public String getRechargeNumber() {
        return rechargeNumber;
    }

    /**
     * setter rechargeNumber(充值编号)
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
     * getter rechargeMoney(充值金额)
     */
    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    /**
     * setter rechargeMoney(充值金额)
     */
    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter rechargeTime(充值时间)
     */
    public Date getRechargeTime() {
        return rechargeTime;
    }

    /**
     * setter rechargeTime(充值时间)
     */
    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter payDate(支付时间)
     */
    public Date getPayDate() {
        return payDate;
    }

    /**
     * setter payDate(支付时间)
     */
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    /**
     * getter payWayId(支付方式(支付方式id))
     */
    public Long getPayWayId() {
        return payWayId;
    }

    /**
     * setter payWayId(支付方式(支付方式id))
     */
    public void setPayWayId(Long payWayId) {
        this.payWayId = payWayId;
    }

    /**
     * getter staus(支付状态，0未支付、1已支付)
     */
    public String getStaus() {
        return staus;
    }

    /**
     * setter staus(支付状态，0未支付、1已支付)
     */
    public void setStaus(String staus) {
        this.staus = staus;
    }

    /**
     * getter payTerminal(支付终端，0pc端、1移动端)
     */
    public String getPayTerminal() {
        return payTerminal;
    }

    /**
     * setter payTerminal(支付终端，0pc端、1移动端)
     */
    public void setPayTerminal(String payTerminal) {
        this.payTerminal = payTerminal;
    }

    /**
     * getter tradeNumber(交易号)
     */
    public String getTradeNumber() {
        return tradeNumber;
    }

    /**
     * setter tradeNumber(交易号)
     */
    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
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
     * getter rechargeTime(充值时间)
     */
    public Date getBeginRechargeTime() {
        return beginRechargeTime;
    }

    /**
     * setter rechargeTime(充值时间)
     */
    public void setBeginRechargeTime(Date beginRechargeTime) {
        this.beginRechargeTime = beginRechargeTime;
    }

    /**
     * getter rechargeTime(充值时间)
     */
    public Date getEndRechargeTime() {
        return endRechargeTime;
    }

    /**
     * setter rechargeTime(充值时间)
     */
    public void setEndRechargeTime(Date endRechargeTime) {
        this.endRechargeTime = endRechargeTime;
    }

    /**
     * getter payDate(支付时间)
     */
    public Date getBeginPayDate() {
        return beginPayDate;
    }

    /**
     * setter payDate(支付时间)
     */
    public void setBeginPayDate(Date beginPayDate) {
        this.beginPayDate = beginPayDate;
    }

    /**
     * getter payDate(支付时间)
     */
    public Date getEndPayDate() {
        return endPayDate;
    }

    /**
     * setter payDate(支付时间)
     */
    public void setEndPayDate(Date endPayDate) {
        this.endPayDate = endPayDate;
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