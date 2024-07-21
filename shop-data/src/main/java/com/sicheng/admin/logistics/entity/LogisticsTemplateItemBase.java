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

import java.math.BigDecimal;
import java.util.Date;

/**
 * 运费模板详情 Entity 父类
 *
 * @author fxx
 * @version 2017-02-21
 */
public class LogisticsTemplateItemBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long ltiId;                     // 主键
    private Long ltId;                      // 运费模板id
    private String names;                   // 地区名(冗余字段)
    private Integer firstItem;              // 首件(件)
    private BigDecimal firstPrice;          // 首重(元)
    private Integer continueItem;           // 续件(件)
    private BigDecimal continuePrice;       // 续重(元)
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
    private String ids;                     // 运送到（地区表id，用逗号分割）
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public LogisticsTemplateItemBase() {
        super();
    }

    public LogisticsTemplateItemBase(Long id) {
        super(id);
        this.ltiId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return ltiId;
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
        this.ltiId = id;
    }

    /**
     * getter ltiId(主键)
     */
    public Long getLtiId() {
        return ltiId;
    }

    /**
     * setter ltiId(主键)
     */
    public void setLtiId(Long ltiId) {
        this.ltiId = ltiId;
    }

    /**
     * getter ltId(运费模板id)
     */
    public Long getLtId() {
        return ltId;
    }

    /**
     * setter ltId(运费模板id)
     */
    public void setLtId(Long ltId) {
        this.ltId = ltId;
    }

    /**
     * getter names(地区名(冗余字段))
     */
    public String getNames() {
        return names;
    }

    /**
     * setter names(地区名(冗余字段))
     */
    public void setNames(String names) {
        this.names = names;
    }

    /**
     * getter firstItem(首件(件))
     */
    public Integer getFirstItem() {
        return firstItem;
    }

    /**
     * setter firstItem(首件(件))
     */
    public void setFirstItem(Integer firstItem) {
        this.firstItem = firstItem;
    }

    /**
     * getter firstPrice(首重(元))
     */
    public BigDecimal getFirstPrice() {
        return firstPrice;
    }

    /**
     * setter firstPrice(首重(元))
     */
    public void setFirstPrice(BigDecimal firstPrice) {
        this.firstPrice = firstPrice;
    }

    /**
     * getter continueItem(续件(件))
     */
    public Integer getContinueItem() {
        return continueItem;
    }

    /**
     * setter continueItem(续件(件))
     */
    public void setContinueItem(Integer continueItem) {
        this.continueItem = continueItem;
    }

    /**
     * getter continuePrice(续重(元))
     */
    public BigDecimal getContinuePrice() {
        return continuePrice;
    }

    /**
     * setter continuePrice(续重(元))
     */
    public void setContinuePrice(BigDecimal continuePrice) {
        this.continuePrice = continuePrice;
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
     * getter ids(运送到（地区表id，用逗号分割）)
     */
    public String getIds() {
        return ids;
    }

    /**
     * setter ids(运送到（地区表id，用逗号分割）)
     */
    public void setIds(String ids) {
        this.ids = ids;
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