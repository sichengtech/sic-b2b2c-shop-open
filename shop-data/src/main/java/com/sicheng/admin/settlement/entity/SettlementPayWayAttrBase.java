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
package com.sicheng.admin.settlement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 支付方式属性 Entity 父类
 *
 * @author 张加利
 * @version 2018-03-26
 */
public class SettlementPayWayAttrBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long payWayAttrId;              // 主键id
    private Long payWayId;                  // 支付方式id
    private String payWayKey;               // 支付方式属性键
    private String payWayValue;             // 支付方式属性值
    private String payWayDescribe;          // 支付方式属性描述
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
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SettlementPayWayAttrBase() {
        super();
    }

    public SettlementPayWayAttrBase(Long id) {
        super(id);
        this.payWayAttrId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return payWayAttrId;
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
        this.payWayAttrId = id;
    }

    /**
     * getter payWayAttrId(主键id)
     */
    public Long getPayWayAttrId() {
        return payWayAttrId;
    }

    /**
     * setter payWayAttrId(主键id)
     */
    public void setPayWayAttrId(Long payWayAttrId) {
        this.payWayAttrId = payWayAttrId;
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
     * getter payWayKey(支付方式属性键)
     */
    public String getPayWayKey() {
        return payWayKey;
    }

    /**
     * setter payWayKey(支付方式属性键)
     */
    public void setPayWayKey(String payWayKey) {
        this.payWayKey = payWayKey;
    }

    /**
     * getter payWayValue(支付方式属性值)
     */
    public String getPayWayValue() {
        return payWayValue;
    }

    /**
     * setter payWayValue(支付方式属性值)
     */
    public void setPayWayValue(String payWayValue) {
        this.payWayValue = payWayValue;
    }

    /**
     * getter payWayDescribe(支付方式属性描述)
     */
    public String getPayWayDescribe() {
        return payWayDescribe;
    }

    /**
     * setter payWayDescribe(支付方式属性描述)
     */
    public void setPayWayDescribe(String payWayDescribe) {
        this.payWayDescribe = payWayDescribe;
    }

    /**
     * getter bak1(备用字段)
     */
    public String getBak1() {
        return bak1;
    }

    /**
     * setter bak1(备用字段)
     */
    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    /**
     * getter bak2(备用字段)
     */
    public String getBak2() {
        return bak2;
    }

    /**
     * setter bak2(备用字段)
     */
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    /**
     * getter bak3(备用字段)
     */
    public String getBak3() {
        return bak3;
    }

    /**
     * setter bak3(备用字段)
     */
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }

    /**
     * getter bak4(备用字段)
     */
    public String getBak4() {
        return bak4;
    }

    /**
     * setter bak4(备用字段)
     */
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }

    /**
     * getter bak5(备用字段)
     */
    public String getBak5() {
        return bak5;
    }

    /**
     * setter bak5(备用字段)
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