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
package com.sicheng.admin.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 商品统计 Entity 父类
 *
 * @author zjl
 * @version 2017-05-09
 */
public class ProductSpuAnalyzeBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long pId;                       // 商品spu的id
    private Integer allBrowse;              // 总浏览量
    private Date allBrowseDate;             // 总浏览量更新日期
    private Integer weekBrowse;             // 周浏览量
    private Date weekBrowseDate;            // 周浏览量更新日期
    private Integer monthBrowse;            // 月浏览量
    private Date monthBrowseDate;           // 月浏览量更新日期
    private Integer month3Browse;           // 三个月浏览量
    private Date month3BrowseDate;          // 三个月浏览量更新日期
    private Integer allSales;               // 总销量
    private Date allSalesDate;              // 总销量更新日期
    private Integer weekSales;              // 周销量
    private Date weekSalesDate;             // 周销量更新日期
    private Integer monthSales;             // 月销量
    private Date monthSalesDate;            // 月销量更新日期
    private Integer month3Sales;            // 三个月销量
    private Date month3SalesDate;           // 三个月销量更新日期
    private Date beginAllBrowseDate;        // 开始 总浏览量更新日期
    private Date endAllBrowseDate;          // 结束 总浏览量更新日期
    private Date beginWeekBrowseDate;       // 开始 周浏览量更新日期
    private Date endWeekBrowseDate;         // 结束 周浏览量更新日期
    private Date beginMonthBrowseDate;      // 开始 月浏览量更新日期
    private Date endMonthBrowseDate;        // 结束 月浏览量更新日期
    private Date beginMonth3BrowseDate;     // 开始 三个月浏览量更新日期
    private Date endMonth3BrowseDate;       // 结束 三个月浏览量更新日期
    private Date beginAllSalesDate;         // 开始 总销量更新日期
    private Date endAllSalesDate;           // 结束 总销量更新日期
    private Date beginWeekSalesDate;        // 开始 周销量更新日期
    private Date endWeekSalesDate;          // 结束 周销量更新日期
    private Date beginMonthSalesDate;       // 开始 月销量更新日期
    private Date endMonthSalesDate;         // 结束 月销量更新日期
    private Date beginMonth3SalesDate;      // 开始 三个月销量更新日期
    private Date endMonth3SalesDate;        // 结束 三个月销量更新日期

    public ProductSpuAnalyzeBase() {
        super();
    }

    public ProductSpuAnalyzeBase(Long id) {
        super(id);
        this.pId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return pId;
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
        this.pId = id;
    }

    /**
     * getter pId(商品spu的id)
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(商品spu的id)
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter allBrowse(总浏览量)
     */
    public Integer getAllBrowse() {
        return allBrowse;
    }

    /**
     * setter allBrowse(总浏览量)
     */
    public void setAllBrowse(Integer allBrowse) {
        this.allBrowse = allBrowse;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter allBrowseDate(总浏览量更新日期)
     */
    public Date getAllBrowseDate() {
        return allBrowseDate;
    }

    /**
     * setter allBrowseDate(总浏览量更新日期)
     */
    public void setAllBrowseDate(Date allBrowseDate) {
        this.allBrowseDate = allBrowseDate;
    }

    /**
     * getter weekBrowse(周浏览量)
     */
    public Integer getWeekBrowse() {
        return weekBrowse;
    }

    /**
     * setter weekBrowse(周浏览量)
     */
    public void setWeekBrowse(Integer weekBrowse) {
        this.weekBrowse = weekBrowse;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter weekBrowseDate(周浏览量更新日期)
     */
    public Date getWeekBrowseDate() {
        return weekBrowseDate;
    }

    /**
     * setter weekBrowseDate(周浏览量更新日期)
     */
    public void setWeekBrowseDate(Date weekBrowseDate) {
        this.weekBrowseDate = weekBrowseDate;
    }

    /**
     * getter monthBrowse(月浏览量)
     */
    public Integer getMonthBrowse() {
        return monthBrowse;
    }

    /**
     * setter monthBrowse(月浏览量)
     */
    public void setMonthBrowse(Integer monthBrowse) {
        this.monthBrowse = monthBrowse;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter monthBrowseDate(月浏览量更新日期)
     */
    public Date getMonthBrowseDate() {
        return monthBrowseDate;
    }

    /**
     * setter monthBrowseDate(月浏览量更新日期)
     */
    public void setMonthBrowseDate(Date monthBrowseDate) {
        this.monthBrowseDate = monthBrowseDate;
    }

    /**
     * getter month3Browse(三个月浏览量)
     */
    public Integer getMonth3Browse() {
        return month3Browse;
    }

    /**
     * setter month3Browse(三个月浏览量)
     */
    public void setMonth3Browse(Integer month3Browse) {
        this.month3Browse = month3Browse;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter month3BrowseDate(三个月浏览量更新日期)
     */
    public Date getMonth3BrowseDate() {
        return month3BrowseDate;
    }

    /**
     * setter month3BrowseDate(三个月浏览量更新日期)
     */
    public void setMonth3BrowseDate(Date month3BrowseDate) {
        this.month3BrowseDate = month3BrowseDate;
    }

    /**
     * getter allSales(总销量)
     */
    public Integer getAllSales() {
        return allSales;
    }

    /**
     * setter allSales(总销量)
     */
    public void setAllSales(Integer allSales) {
        this.allSales = allSales;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter allSalesDate(总销量更新日期)
     */
    public Date getAllSalesDate() {
        return allSalesDate;
    }

    /**
     * setter allSalesDate(总销量更新日期)
     */
    public void setAllSalesDate(Date allSalesDate) {
        this.allSalesDate = allSalesDate;
    }

    /**
     * getter weekSales(周销量)
     */
    public Integer getWeekSales() {
        return weekSales;
    }

    /**
     * setter weekSales(周销量)
     */
    public void setWeekSales(Integer weekSales) {
        this.weekSales = weekSales;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter weekSalesDate(周销量更新日期)
     */
    public Date getWeekSalesDate() {
        return weekSalesDate;
    }

    /**
     * setter weekSalesDate(周销量更新日期)
     */
    public void setWeekSalesDate(Date weekSalesDate) {
        this.weekSalesDate = weekSalesDate;
    }

    /**
     * getter monthSales(月销量)
     */
    public Integer getMonthSales() {
        return monthSales;
    }

    /**
     * setter monthSales(月销量)
     */
    public void setMonthSales(Integer monthSales) {
        this.monthSales = monthSales;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter monthSalesDate(月销量更新日期)
     */
    public Date getMonthSalesDate() {
        return monthSalesDate;
    }

    /**
     * setter monthSalesDate(月销量更新日期)
     */
    public void setMonthSalesDate(Date monthSalesDate) {
        this.monthSalesDate = monthSalesDate;
    }

    /**
     * getter month3Sales(三个月销量)
     */
    public Integer getMonth3Sales() {
        return month3Sales;
    }

    /**
     * setter month3Sales(三个月销量)
     */
    public void setMonth3Sales(Integer month3Sales) {
        this.month3Sales = month3Sales;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter month3SalesDate(三个月销量更新日期)
     */
    public Date getMonth3SalesDate() {
        return month3SalesDate;
    }

    /**
     * setter month3SalesDate(三个月销量更新日期)
     */
    public void setMonth3SalesDate(Date month3SalesDate) {
        this.month3SalesDate = month3SalesDate;
    }

    /**
     * getter allBrowseDate(总浏览量更新日期)
     */
    public Date getBeginAllBrowseDate() {
        return beginAllBrowseDate;
    }

    /**
     * setter allBrowseDate(总浏览量更新日期)
     */
    public void setBeginAllBrowseDate(Date beginAllBrowseDate) {
        this.beginAllBrowseDate = beginAllBrowseDate;
    }

    /**
     * getter allBrowseDate(总浏览量更新日期)
     */
    public Date getEndAllBrowseDate() {
        return endAllBrowseDate;
    }

    /**
     * setter allBrowseDate(总浏览量更新日期)
     */
    public void setEndAllBrowseDate(Date endAllBrowseDate) {
        this.endAllBrowseDate = endAllBrowseDate;
    }

    /**
     * getter weekBrowseDate(周浏览量更新日期)
     */
    public Date getBeginWeekBrowseDate() {
        return beginWeekBrowseDate;
    }

    /**
     * setter weekBrowseDate(周浏览量更新日期)
     */
    public void setBeginWeekBrowseDate(Date beginWeekBrowseDate) {
        this.beginWeekBrowseDate = beginWeekBrowseDate;
    }

    /**
     * getter weekBrowseDate(周浏览量更新日期)
     */
    public Date getEndWeekBrowseDate() {
        return endWeekBrowseDate;
    }

    /**
     * setter weekBrowseDate(周浏览量更新日期)
     */
    public void setEndWeekBrowseDate(Date endWeekBrowseDate) {
        this.endWeekBrowseDate = endWeekBrowseDate;
    }

    /**
     * getter monthBrowseDate(月浏览量更新日期)
     */
    public Date getBeginMonthBrowseDate() {
        return beginMonthBrowseDate;
    }

    /**
     * setter monthBrowseDate(月浏览量更新日期)
     */
    public void setBeginMonthBrowseDate(Date beginMonthBrowseDate) {
        this.beginMonthBrowseDate = beginMonthBrowseDate;
    }

    /**
     * getter monthBrowseDate(月浏览量更新日期)
     */
    public Date getEndMonthBrowseDate() {
        return endMonthBrowseDate;
    }

    /**
     * setter monthBrowseDate(月浏览量更新日期)
     */
    public void setEndMonthBrowseDate(Date endMonthBrowseDate) {
        this.endMonthBrowseDate = endMonthBrowseDate;
    }

    /**
     * getter month3BrowseDate(三个月浏览量更新日期)
     */
    public Date getBeginMonth3BrowseDate() {
        return beginMonth3BrowseDate;
    }

    /**
     * setter month3BrowseDate(三个月浏览量更新日期)
     */
    public void setBeginMonth3BrowseDate(Date beginMonth3BrowseDate) {
        this.beginMonth3BrowseDate = beginMonth3BrowseDate;
    }

    /**
     * getter month3BrowseDate(三个月浏览量更新日期)
     */
    public Date getEndMonth3BrowseDate() {
        return endMonth3BrowseDate;
    }

    /**
     * setter month3BrowseDate(三个月浏览量更新日期)
     */
    public void setEndMonth3BrowseDate(Date endMonth3BrowseDate) {
        this.endMonth3BrowseDate = endMonth3BrowseDate;
    }

    /**
     * getter allSalesDate(总销量更新日期)
     */
    public Date getBeginAllSalesDate() {
        return beginAllSalesDate;
    }

    /**
     * setter allSalesDate(总销量更新日期)
     */
    public void setBeginAllSalesDate(Date beginAllSalesDate) {
        this.beginAllSalesDate = beginAllSalesDate;
    }

    /**
     * getter allSalesDate(总销量更新日期)
     */
    public Date getEndAllSalesDate() {
        return endAllSalesDate;
    }

    /**
     * setter allSalesDate(总销量更新日期)
     */
    public void setEndAllSalesDate(Date endAllSalesDate) {
        this.endAllSalesDate = endAllSalesDate;
    }

    /**
     * getter weekSalesDate(周销量更新日期)
     */
    public Date getBeginWeekSalesDate() {
        return beginWeekSalesDate;
    }

    /**
     * setter weekSalesDate(周销量更新日期)
     */
    public void setBeginWeekSalesDate(Date beginWeekSalesDate) {
        this.beginWeekSalesDate = beginWeekSalesDate;
    }

    /**
     * getter weekSalesDate(周销量更新日期)
     */
    public Date getEndWeekSalesDate() {
        return endWeekSalesDate;
    }

    /**
     * setter weekSalesDate(周销量更新日期)
     */
    public void setEndWeekSalesDate(Date endWeekSalesDate) {
        this.endWeekSalesDate = endWeekSalesDate;
    }

    /**
     * getter monthSalesDate(月销量更新日期)
     */
    public Date getBeginMonthSalesDate() {
        return beginMonthSalesDate;
    }

    /**
     * setter monthSalesDate(月销量更新日期)
     */
    public void setBeginMonthSalesDate(Date beginMonthSalesDate) {
        this.beginMonthSalesDate = beginMonthSalesDate;
    }

    /**
     * getter monthSalesDate(月销量更新日期)
     */
    public Date getEndMonthSalesDate() {
        return endMonthSalesDate;
    }

    /**
     * setter monthSalesDate(月销量更新日期)
     */
    public void setEndMonthSalesDate(Date endMonthSalesDate) {
        this.endMonthSalesDate = endMonthSalesDate;
    }

    /**
     * getter month3SalesDate(三个月销量更新日期)
     */
    public Date getBeginMonth3SalesDate() {
        return beginMonth3SalesDate;
    }

    /**
     * setter month3SalesDate(三个月销量更新日期)
     */
    public void setBeginMonth3SalesDate(Date beginMonth3SalesDate) {
        this.beginMonth3SalesDate = beginMonth3SalesDate;
    }

    /**
     * getter month3SalesDate(三个月销量更新日期)
     */
    public Date getEndMonth3SalesDate() {
        return endMonth3SalesDate;
    }

    /**
     * setter month3SalesDate(三个月销量更新日期)
     */
    public void setEndMonth3SalesDate(Date endMonth3SalesDate) {
        this.endMonth3SalesDate = endMonth3SalesDate;
    }

}