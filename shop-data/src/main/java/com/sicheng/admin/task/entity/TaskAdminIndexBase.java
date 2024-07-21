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
package com.sicheng.admin.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * admin首页数据 Entity 父类
 *
 * @author zjl
 * @version 2017-07-20
 */
public class TaskAdminIndexBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long taiId;                     // 主键
    private BigDecimal moneycountday;       // 今日交易额
    private Integer ordercountpending;      // 待处理订单
    private Integer ordercountday;          // 今日订单量
    private Integer storecount;             // 商铺总数(统计的是 店铺表)
    private Integer storecountday;          // 今日新增店铺(统计的是 店铺表)
    private Integer membercount;            // 会员总数(买家)
    private Integer membercountday;         // 今日新增会员数(买家)
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
    private Integer productspucount;        // 商品总量spu
    private Integer productskucount;        // 商品总量sku
    private BigDecimal ordermoneycount;     // 总交易额
    private Integer ordercount;             // 总订单量
    private Integer activemembercount;      // 活跃买家
    private Integer activesellercount;      // 活跃卖家
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public TaskAdminIndexBase() {
        super();
    }

    public TaskAdminIndexBase(Long id) {
        super(id);
        this.taiId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return taiId;
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
        this.taiId = id;
    }

    /**
     * getter taiId(主键)
     */
    public Long getTaiId() {
        return taiId;
    }

    /**
     * setter taiId(主键)
     */
    public void setTaiId(Long taiId) {
        this.taiId = taiId;
    }

    /**
     * getter moneycountday(今日成交额)
     */
    public BigDecimal getMoneycountday() {
        return moneycountday;
    }

    /**
     * setter moneycountday(今日成交额)
     */
    public void setMoneycountday(BigDecimal moneycountday) {
        this.moneycountday = moneycountday;
    }

    /**
     * getter ordercountpending(待处理订单)
     */
    public Integer getOrdercountpending() {
        return ordercountpending;
    }

    /**
     * setter ordercountpending(待处理订单)
     */
    public void setOrdercountpending(Integer ordercountpending) {
        this.ordercountpending = ordercountpending;
    }

    /**
     * getter ordercountday(今日订单)
     */
    public Integer getOrdercountday() {
        return ordercountday;
    }

    /**
     * setter ordercountday(今日订单)
     */
    public void setOrdercountday(Integer ordercountday) {
        this.ordercountday = ordercountday;
    }

    /**
     * getter storecount(商铺总数(统计的是 店铺表))
     */
    public Integer getStorecount() {
        return storecount;
    }

    /**
     * setter storecount(商铺总数(统计的是 店铺表))
     */
    public void setStorecount(Integer storecount) {
        this.storecount = storecount;
    }

    /**
     * getter storecountday(今日新增店铺(统计的是 店铺表))
     */
    public Integer getStorecountday() {
        return storecountday;
    }

    /**
     * setter storecountday(今日新增店铺(统计的是 店铺表))
     */
    public void setStorecountday(Integer storecountday) {
        this.storecountday = storecountday;
    }

    /**
     * getter membercount(会员总数(买家))
     */
    public Integer getMembercount() {
        return membercount;
    }

    /**
     * setter membercount(会员总数(买家))
     */
    public void setMembercount(Integer membercount) {
        this.membercount = membercount;
    }

    /**
     * getter membercountday(今日新增会员数(买家))
     */
    public Integer getMembercountday() {
        return membercountday;
    }

    /**
     * setter membercountday(今日新增会员数(买家))
     */
    public void setMembercountday(Integer membercountday) {
        this.membercountday = membercountday;
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
     * getter productspucount(商品spu总量)
     */
    public Integer getProductspucount() {
        return productspucount;
    }

    /**
     * setter productspucount(商品spu总量)
     */
    public void setProductspucount(Integer productspucount) {
        this.productspucount = productspucount;
    }

    /**
     * getter productskucount(商品sku总量)
     */
    public Integer getProductskucount() {
        return productskucount;
    }

    /**
     * setter productskucount(商品sku总量)
     */
    public void setProductskucount(Integer productskucount) {
        this.productskucount = productskucount;
    }

    /**
     * getter ordermoneycount(订单总额)
     */
    public BigDecimal getOrdermoneycount() {
        return ordermoneycount;
    }

    /**
     * setter ordermoneycount(订单总额)
     */
    public void setOrdermoneycount(BigDecimal ordermoneycount) {
        this.ordermoneycount = ordermoneycount;
    }

    /**
     * getter ordercount(订单总量)
     */
    public Integer getOrdercount() {
        return ordercount;
    }

    /**
     * setter ordercount(订单总量)
     */
    public void setOrdercount(Integer ordercount) {
        this.ordercount = ordercount;
    }

    /**
     * getter activemembercount(活跃买家)
     */
    public Integer getActivemembercount() {
        return activemembercount;
    }

    /**
     * setter activemembercount(活跃买家)
     */
    public void setActivemembercount(Integer activemembercount) {
        this.activemembercount = activemembercount;
    }

    /**
     * getter activesellercount(活跃卖家)
     */
    public Integer getActivesellercount() {
        return activesellercount;
    }

    /**
     * setter activesellercount(活跃卖家)
     */
    public void setActivesellercount(Integer activesellercount) {
        this.activesellercount = activesellercount;
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