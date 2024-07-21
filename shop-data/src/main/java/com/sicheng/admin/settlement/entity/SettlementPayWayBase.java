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

import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付方式 Entity 父类
 *
 * @author fxx
 * @version 2018-03-26
 */
public class SettlementPayWayBase<T> extends DataEntity<T> {



    private static final long serialVersionUID = 1L;
    private Long payWayId;                  // 主键
    private String payWayNum;               // 支付方式编号
    private String name;                    // 支付方式
    private BigDecimal poundage;            // 手续费
    private String useTerminal;             // 使用终端,0pc、1移动端
    private String payWayLogo;              // 支付方式logo
    private String status;                  // 状态，0关闭、1开启
    private String bak1;                    // 备用字段1
    private String bak2;                    // 备用字段2
    private String bak3;                    // 备用字段3
    private String bak4;                    // 备用字段4
    private String bak5;                    // 备用字段5
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间
    public SettlementPayWayBase() {
        super();
    }

    public SettlementPayWayBase(Long id){
        super(id);
        this.payWayId = id;
    }

    /**
     * 描述: 获取ID
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return payWayId;
    }

    /**
     * 描述: 设置ID
     * @param id
     * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
        this.payWayId = id;
    }

    /**
     * getter payWayId(主键)
     */
    public Long getPayWayId() {
        return payWayId;
    }

    /**
     * setter payWayId(主键)
     */
    public void setPayWayId(Long payWayId) {
        this.payWayId = payWayId;
    }

    /**
     * getter payWayNum(支付方式编号)
     */
    public String getPayWayNum() {
        return payWayNum;
    }

    /**
     * setter payWayNum(支付方式编号)
     */
    public void setPayWayNum(String payWayNum) {
        this.payWayNum = payWayNum;
    }

    /**
     * getter name(支付方式)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(支付方式)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter poundage(手续费)
     */
    public BigDecimal getPoundage() {
        return poundage;
    }

    /**
     * setter poundage(手续费)
     */
    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    /**
     * getter useTerminal(使用终端,0pc、1移动端)
     */
    public String getUseTerminal() {
        return useTerminal;
    }

    /**
     * setter useTerminal(使用终端,0pc、1移动端)
     */
    public void setUseTerminal(String useTerminal) {
        this.useTerminal = useTerminal;
    }

    /**
     * getter payWayLogo(支付方式logo)
     */
    public String getPayWayLogo() {
        return payWayLogo;
    }

    /**
     * setter payWayLogo(支付方式logo)
     */
    public void setPayWayLogo(String payWayLogo) {
        this.payWayLogo = payWayLogo;
    }

    /**
     * getter status(状态，0关闭、1开启)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(状态，0关闭、1开启)
     */
    public void setStatus(String status) {
        this.status = status;
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