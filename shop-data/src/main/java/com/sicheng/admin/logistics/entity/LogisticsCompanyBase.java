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
package com.sicheng.admin.logistics.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 管理物流的快递公司 Entity 父类
 *
 * @author 张加力
 * @version 2017-02-06
 */
public class LogisticsCompanyBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long lcId;                      // 主键id
    private String largeArea;               // 首字母
    private String companyName;             // 公司名称
    private String companyNumber;           // 公司编号
    private String companyurl;              // 公司网址
    private String status;                  // 状态，0禁用、1启用
    private String isCommonUse;             // 是否是常用快递，0否、1是
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

    public LogisticsCompanyBase() {
        super();
    }

    public LogisticsCompanyBase(Long id) {
        super(id);
        this.lcId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return lcId;
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
        this.lcId = id;
    }

    /**
     * getter lcId(主键id)
     */
    public Long getLcId() {
        return lcId;
    }

    /**
     * setter lcId(主键id)
     */
    public void setLcId(Long lcId) {
        this.lcId = lcId;
    }

    /**
     * getter largeArea(首字母)
     */
    public String getLargeArea() {
        return largeArea;
    }

    /**
     * setter largeArea(首字母)
     */
    public void setLargeArea(String largeArea) {
        this.largeArea = largeArea;
    }

    /**
     * getter companyName(公司名称)
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * setter companyName(公司名称)
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * getter companyNumber(公司编号)
     */
    public String getCompanyNumber() {
        return companyNumber;
    }

    /**
     * setter companyNumber(公司编号)
     */
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    /**
     * getter companyurl(公司网址)
     */
    public String getCompanyurl() {
        return companyurl;
    }

    /**
     * setter companyurl(公司网址)
     */
    public void setCompanyurl(String companyurl) {
        this.companyurl = companyurl;
    }

    /**
     * getter status(状态，0禁用、1启用)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(状态，0禁用、1启用)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter isCommonUse(是否是常用快递，0否、1是)
     */
    public String getIsCommonUse() {
        return isCommonUse;
    }

    /**
     * setter isCommonUse(是否是常用快递，0否、1是)
     */
    public void setIsCommonUse(String isCommonUse) {
        this.isCommonUse = isCommonUse;
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