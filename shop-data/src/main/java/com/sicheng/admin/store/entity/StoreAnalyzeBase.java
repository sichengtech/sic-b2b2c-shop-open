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
package com.sicheng.admin.store.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.sicheng.common.persistence.DataEntity;

/**
 * 店铺统计表 Entity 父类
 * @author 贺东泽
 * @version 2019-11-19
 */
public class StoreAnalyzeBase<T> extends DataEntity<T> {

	private static final long serialVersionUID = 1L;
	private Long storeId;                   // 店铺id
	private Integer allSales;               // 总销量
	private Date allSalesDate;              // 总销量更新日期
	private Integer weekSales;              // 周销量
	private Date weekSalesDate;             // 周销量更新日期
	private Integer monthSales;             // 月销量
	private Date monthSalesDate;            // 月销量更新日期
	private Integer month3Sales;            // 三个月销量
	private Date month3SalesDate;           // 三个月销量更新日期
	private String productScore;            // 产品评分
	private String logisticsScore;          // 物流评分
	private String serviceAttitudeScore;    // 服务评分
	private String productScoreUpOrDown;    // 1为商品评分上升，0为下降
	private String logisticsScoreUpOrDown;  // 1为物流评分上升，0为下降
	private String serviceAttitudeScoreUpOrDown;      // 1为服务态度评分上升，0为下降
	private Date beginAllSalesDate;         // 开始 总销量更新日期
	private Date endAllSalesDate;           // 结束 总销量更新日期
	private Date beginWeekSalesDate;        // 开始 周销量更新日期
	private Date endWeekSalesDate;          // 结束 周销量更新日期
	private Date beginMonthSalesDate;       // 开始 月销量更新日期
	private Date endMonthSalesDate;         // 结束 月销量更新日期
	private Date beginMonth3SalesDate;      // 开始 三个月销量更新日期
	private Date endMonth3SalesDate;        // 结束 三个月销量更新日期
	public StoreAnalyzeBase() {
		super();
	}

	public StoreAnalyzeBase(Long id){
		super(id);
		this.storeId = id;
	}

	/**
	 * 描述: 获取ID
	 * @return
	 * @see com.sicheng.common.persistence.BaseEntity#getId()
	 */
	@Override
	public Long getId() {
		return storeId;
	}

	/**
	 * 描述: 设置ID
	 * @param id
	 * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
		this.storeId = id;
	}

	/**
	 * getter storeId(店铺id)
	 */
	public Long getStoreId() {
		return storeId;
	}

	/**
	 * setter storeId(店铺id)
	 */
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
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
	 * getter productScore(产品评分)
	 */
	public String getProductScore() {
		return productScore;
	}

	/**
	 * setter productScore(产品评分)
	 */
	public void setProductScore(String productScore) {
		this.productScore = productScore;
	}

	/**
	 * getter logisticsScore(物流评分)
	 */
	public String getLogisticsScore() {
		return logisticsScore;
	}

	/**
	 * setter logisticsScore(物流评分)
	 */
	public void setLogisticsScore(String logisticsScore) {
		this.logisticsScore = logisticsScore;
	}

	/**
	 * getter serviceAttitudeScore(服务评分)
	 */
	public String getServiceAttitudeScore() {
		return serviceAttitudeScore;
	}

	/**
	 * setter serviceAttitudeScore(服务评分)
	 */
	public void setServiceAttitudeScore(String serviceAttitudeScore) {
		this.serviceAttitudeScore = serviceAttitudeScore;
	}

	/**
	 * getter productScoreUpOrDown(1为商品评分上升，0为下降)
	 */
	public String getProductScoreUpOrDown() {
		return productScoreUpOrDown;
	}

	/**
	 * setter productScoreUpOrDown(1为商品评分上升，0为下降)
	 */
	public void setProductScoreUpOrDown(String productScoreUpOrDown) {
		this.productScoreUpOrDown = productScoreUpOrDown;
	}

	/**
	 * getter logisticsScoreUpOrDown(1为物流评分上升，0为下降)
	 */
	public String getLogisticsScoreUpOrDown() {
		return logisticsScoreUpOrDown;
	}

	/**
	 * setter logisticsScoreUpOrDown(1为物流评分上升，0为下降)
	 */
	public void setLogisticsScoreUpOrDown(String logisticsScoreUpOrDown) {
		this.logisticsScoreUpOrDown = logisticsScoreUpOrDown;
	}

	/**
	 * getter serviceAttitudeScoreUpOrDown(1为服务态度评分上升，0为下降)
	 */
	public String getServiceAttitudeScoreUpOrDown() {
		return serviceAttitudeScoreUpOrDown;
	}

	/**
	 * setter serviceAttitudeScoreUpOrDown(1为服务态度评分上升，0为下降)
	 */
	public void setServiceAttitudeScoreUpOrDown(String serviceAttitudeScoreUpOrDown) {
		this.serviceAttitudeScoreUpOrDown = serviceAttitudeScoreUpOrDown;
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