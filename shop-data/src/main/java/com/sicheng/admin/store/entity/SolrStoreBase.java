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
 * 店铺视图 Entity 父类
 * @author 贺东泽
 * @version 2019-11-19
 */
public class SolrStoreBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long storeId;                   // 主键(店铺表，店铺二级域名，店铺相册空间信息表用1个id)
    private String headPic;                 // 店铺头像图片路径（默认图片）
    private String logo;                    // 店铺logo图片路径（默认图片）
    private String banner;                  // 店铺横幅图片路径（默认图片）
    private String name;                    // 店铺名称（默认注册账号名称）
    private Integer productCount;           // 店铺商品spu数
    private String isOpen;                  // 店铺营业状态(0关闭、1开启)(首页搜索店铺搜索未关闭的)
    private String settlementPeriod;        // 结算周期(10日结、20周结、30双周结、40月结)
    private Long countryId;                 // 国家(关联地区表)
    private String countryName;             // 国家名字
    private Long provinceId;                // 省(关联地区表)
    private String provinceName;            // 省名字
    private Long cityId;                    // 市(关联地区表)
    private String cityName;                // 市名字
    private Long districtId;                // 县(关联地区表)
    private String districtName;            // 县名字
    private String detailedAddress;         // 详细地址
    private String storeTel;                // 店铺客服电话
    private String storeQq;                 // 店铺QQ
    private String storeWechat;             // 店铺联系微信
    private String seoTitle;                // SEO Title(用于店铺搜索引擎的优化。)
    private String seoKeyword;              // SEO关键字(用于店铺搜索引擎的优化，关键字之间请用英文逗号分隔。)
    private String seoDescribe;             // SEO店铺描述(用于店铺搜索引擎的优化，建议输入120字以内对店铺经营内容的简单描述)
    private String industry;                // 店铺主营产品
    private String storeType;               // 店铺类型字典（10普通店铺，20旗舰店）
    private Long levelId;                   // 店铺等级（关联店铺等级id）（注意：在商家入驻审核二审通过之后生成的是入驻申请中选择的店铺类型，以后修改的时候记得把入驻申请表，入驻查看表都修改了）
    private Long industryId;                // 主营行业（关联主营行业id）（注意：在商家入驻审核二审通过之后生成的是入驻申请中选择的店铺类型，以后修改的时候记得把入驻申请表，入驻查看表都修改了）
    private Double commission;              // 分佣比例
    private String markingImgPath;          // 店铺打标地址path
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
    private Long countCollection;           // count_collection
    private String productScoreUpOrDown;    // 1为商品评分上升，0为下降
    private String logisticsScoreUpOrDown;  // 1为物流评分上升，0为下降
    private String serviceAttitudeScoreUpOrDown;      // 1为服务态度评分上升，0为下降
    private Date beginCreateDate;           // 开始 创建时间（开店日期）
    private Date endCreateDate;             // 结束 创建时间（开店日期）
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间
    private Date beginAllSalesDate;         // 开始 总销量更新日期
    private Date endAllSalesDate;           // 结束 总销量更新日期
    private Date beginWeekSalesDate;        // 开始 周销量更新日期
    private Date endWeekSalesDate;          // 结束 周销量更新日期
    private Date beginMonthSalesDate;       // 开始 月销量更新日期
    private Date endMonthSalesDate;         // 结束 月销量更新日期
    private Date beginMonth3SalesDate;      // 开始 三个月销量更新日期
    private Date endMonth3SalesDate;        // 结束 三个月销量更新日期
    public SolrStoreBase() {
        super();
    }

    public SolrStoreBase(Long id){
        super(id);
    }

    /**
     * 描述: 获取ID
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * 描述: 设置ID
     * @param id
     * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter storeId(主键(店铺表，店铺二级域名，店铺相册空间信息表用1个id))
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * setter storeId(主键(店铺表，店铺二级域名，店铺相册空间信息表用1个id))
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * getter headPic(店铺头像图片路径（默认图片）)
     */
    public String getHeadPic() {
        return headPic;
    }

    /**
     * setter headPic(店铺头像图片路径（默认图片）)
     */
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    /**
     * getter logo(店铺logo图片路径（默认图片）)
     */
    public String getLogo() {
        return logo;
    }

    /**
     * setter logo(店铺logo图片路径（默认图片）)
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * getter banner(店铺横幅图片路径（默认图片）)
     */
    public String getBanner() {
        return banner;
    }

    /**
     * setter banner(店铺横幅图片路径（默认图片）)
     */
    public void setBanner(String banner) {
        this.banner = banner;
    }

    /**
     * getter name(店铺名称（默认注册账号名称）)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(店铺名称（默认注册账号名称）)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter productCount(店铺商品spu数)
     */
    public Integer getProductCount() {
        return productCount;
    }

    /**
     * setter productCount(店铺商品spu数)
     */
    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    /**
     * getter isOpen(店铺营业状态(0关闭、1开启)(首页搜索店铺搜索未关闭的))
     */
    public String getIsOpen() {
        return isOpen;
    }

    /**
     * setter isOpen(店铺营业状态(0关闭、1开启)(首页搜索店铺搜索未关闭的))
     */
    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * getter settlementPeriod(结算周期(10日结、20周结、30双周结、40月结))
     */
    public String getSettlementPeriod() {
        return settlementPeriod;
    }

    /**
     * setter settlementPeriod(结算周期(10日结、20周结、30双周结、40月结))
     */
    public void setSettlementPeriod(String settlementPeriod) {
        this.settlementPeriod = settlementPeriod;
    }

    /**
     * getter countryId(国家(关联地区表))
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * setter countryId(国家(关联地区表))
     */
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    /**
     * getter countryName(国家名字)
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * setter countryName(国家名字)
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * getter provinceId(省(关联地区表))
     */
    public Long getProvinceId() {
        return provinceId;
    }

    /**
     * setter provinceId(省(关联地区表))
     */
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * getter provinceName(省名字)
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * setter provinceName(省名字)
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * getter cityId(市(关联地区表))
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * setter cityId(市(关联地区表))
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * getter cityName(市名字)
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * setter cityName(市名字)
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * getter districtId(县(关联地区表))
     */
    public Long getDistrictId() {
        return districtId;
    }

    /**
     * setter districtId(县(关联地区表))
     */
    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    /**
     * getter districtName(县名字)
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * setter districtName(县名字)
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * getter detailedAddress(详细地址)
     */
    public String getDetailedAddress() {
        return detailedAddress;
    }

    /**
     * setter detailedAddress(详细地址)
     */
    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    /**
     * getter storeTel(店铺客服电话)
     */
    public String getStoreTel() {
        return storeTel;
    }

    /**
     * setter storeTel(店铺客服电话)
     */
    public void setStoreTel(String storeTel) {
        this.storeTel = storeTel;
    }

    /**
     * getter storeQq(店铺QQ)
     */
    public String getStoreQq() {
        return storeQq;
    }

    /**
     * setter storeQq(店铺QQ)
     */
    public void setStoreQq(String storeQq) {
        this.storeQq = storeQq;
    }

    /**
     * getter storeWechat(店铺联系微信)
     */
    public String getStoreWechat() {
        return storeWechat;
    }

    /**
     * setter storeWechat(店铺联系微信)
     */
    public void setStoreWechat(String storeWechat) {
        this.storeWechat = storeWechat;
    }

    /**
     * getter seoTitle(SEO Title(用于店铺搜索引擎的优化。))
     */
    public String getSeoTitle() {
        return seoTitle;
    }

    /**
     * setter seoTitle(SEO Title(用于店铺搜索引擎的优化。))
     */
    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    /**
     * getter seoKeyword(SEO关键字(用于店铺搜索引擎的优化，关键字之间请用英文逗号分隔。))
     */
    public String getSeoKeyword() {
        return seoKeyword;
    }

    /**
     * setter seoKeyword(SEO关键字(用于店铺搜索引擎的优化，关键字之间请用英文逗号分隔。))
     */
    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    /**
     * getter seoDescribe(SEO店铺描述(用于店铺搜索引擎的优化，建议输入120字以内对店铺经营内容的简单描述))
     */
    public String getSeoDescribe() {
        return seoDescribe;
    }

    /**
     * setter seoDescribe(SEO店铺描述(用于店铺搜索引擎的优化，建议输入120字以内对店铺经营内容的简单描述))
     */
    public void setSeoDescribe(String seoDescribe) {
        this.seoDescribe = seoDescribe;
    }

    /**
     * getter industry(店铺主营产品)
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * setter industry(店铺主营产品)
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * getter storeType(店铺类型字典（10普通店铺，20旗舰店）)
     */
    public String getStoreType() {
        return storeType;
    }

    /**
     * setter storeType(店铺类型字典（10普通店铺，20旗舰店）)
     */
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    /**
     * getter levelId(店铺等级（关联店铺等级id）（注意：在商家入驻审核二审通过之后生成的是入驻申请中选择的店铺类型，以后修改的时候记得把入驻申请表，入驻查看表都修改了）)
     */
    public Long getLevelId() {
        return levelId;
    }

    /**
     * setter levelId(店铺等级（关联店铺等级id）（注意：在商家入驻审核二审通过之后生成的是入驻申请中选择的店铺类型，以后修改的时候记得把入驻申请表，入驻查看表都修改了）)
     */
    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    /**
     * getter industryId(主营行业（关联主营行业id）（注意：在商家入驻审核二审通过之后生成的是入驻申请中选择的店铺类型，以后修改的时候记得把入驻申请表，入驻查看表都修改了）)
     */
    public Long getIndustryId() {
        return industryId;
    }

    /**
     * setter industryId(主营行业（关联主营行业id）（注意：在商家入驻审核二审通过之后生成的是入驻申请中选择的店铺类型，以后修改的时候记得把入驻申请表，入驻查看表都修改了）)
     */
    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    /**
     * getter commission(分佣比例)
     */
    public Double getCommission() {
        return commission;
    }

    /**
     * setter commission(分佣比例)
     */
    public void setCommission(Double commission) {
        this.commission = commission;
    }

    /**
     * getter markingImgPath(店铺打标地址path)
     */
    public String getMarkingImgPath() {
        return markingImgPath;
    }

    /**
     * setter markingImgPath(店铺打标地址path)
     */
    public void setMarkingImgPath(String markingImgPath) {
        this.markingImgPath = markingImgPath;
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
     * getter countCollection(count_collection)
     */
    public Long getCountCollection() {
        return countCollection;
    }

    /**
     * setter countCollection(count_collection)
     */
    public void setCountCollection(Long countCollection) {
        this.countCollection = countCollection;
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
     * getter createDate(创建时间（开店日期）)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(创建时间（开店日期）)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(创建时间（开店日期）)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(创建时间（开店日期）)
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