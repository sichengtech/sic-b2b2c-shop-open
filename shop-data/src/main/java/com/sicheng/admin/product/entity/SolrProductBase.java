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
package com.sicheng.admin.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品搜索 Entity 父类
 *
 * @author 范秀秀
 * @version 2018-01-24
 */
public class SolrProductBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String userName;                // 卖家登录名
    private String storeName;               // 店铺名称
    private Long provinceId;                // 省id
    private Long cityId;                    // 市id
    private String provinceName;            // 省名称
    private String cityName;                // 市名称
    private String categoryName;            // 分类名称
    private Long categoryLevel;             // 分类等级
    private String storeType;               // 店铺类型字典（10普通店铺，20旗舰店）
    private String cateFirstLetter;         // 分类首字母
    private String cateParentIds;           // 分类父ids
    private String storeCateName;           // 店铺分类名
    private String isOpen;                  // 是否开店
    private String storeCateParentIds;      // 店铺分类父ids
    private String brandName;               // 品牌名
    private String brandFirstLeftter;       // 品牌首字母
    private String brandEnglishName;        // 品牌英文名
    private Long allSales;                  // 总销量
    private Long weekSales;                 // 周销量
    private Long monthSales;                // 月销量
    private Long month3Sales;               // 三个月销量
    private String carIds;                  // 车型ids
    private String paramValue;              // 参数值
    private Long collectionCount;           // 收藏量
    private Long commentCount;              // 评论量
    private Long pId;                       // 商品id
    private Long uId;                       // 用户id
    private Long storeId;                   // 店铺id
    private String name;                    // 商品名称
    private String status;                  // 状态
    private Long categoryId;                // 分类id
    private Long storeCategoryId;           // 店内分类
    private String image;                   // 封面图
    private Long brandId;                   // 品牌id
    private String nameSub;                 // 副标题
    private String unit;                    // 单位
    private String type;                    // 类型(1零售、2批发、3混合)
    private String isGift;                  // 是否是礼品
    private String isRecommend;             // 是否推荐
    private String benefit;                 // 优惠
    private Long recommendSort;             // 活动积分
    private BigDecimal marketPrice;         // 市场价
    private String point;                   // 赠送积分
    private BigDecimal maxPrice;            // 最高价
    private BigDecimal minPrice;            // 最低价
    private String action;                  // 是否参加活动
    private BigDecimal maxPrice1;           // 最高零售价
    private BigDecimal minPrice1;           // 最低零售价
    private BigDecimal maxPrice2;           // 最高批发价
    private BigDecimal minPrice2;           // 最低批发价
    private Date shelfTime;                 // 上架时间
    private String deliverGoodsDate;        // 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
    private String purchasingAmount;        // 起购量
    private Date beginShelfTime;            // 开始 上架时间
    private Date endShelfTime;              // 结束 上架时间
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SolrProductBase() {
        super();
    }

    public SolrProductBase(Long id) {
        super(id);
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return id;
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
    }

    /**
     * getter userName(卖家登录名)
     */
    public String getUserName() {
        return userName;
    }

    /**
     * setter userName(卖家登录名)
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * getter storeName(店铺名称)
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * setter storeName(店铺名称)
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * getter provinceId(省id)
     */
    public Long getProvinceId() {
        return provinceId;
    }

    /**
     * setter provinceId(省id)
     */
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * getter cityId(市id)
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * setter cityId(市id)
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * getter provinceName(省名称)
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * setter provinceName(省名称)
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * getter cityName(市名称)
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * setter cityName(市名称)
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * getter categoryName(分类名称)
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * setter categoryName(分类名称)
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * getter categoryLevel(分类等级)
     */
    public Long getCategoryLevel() {
        return categoryLevel;
    }

    /**
     * setter categoryLevel(分类等级)
     */
    public void setCategoryLevel(Long categoryLevel) {
        this.categoryLevel = categoryLevel;
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
     * getter cateFirstLetter(分类首字母)
     */
    public String getCateFirstLetter() {
        return cateFirstLetter;
    }

    /**
     * setter cateFirstLetter(分类首字母)
     */
    public void setCateFirstLetter(String cateFirstLetter) {
        this.cateFirstLetter = cateFirstLetter;
    }

    /**
     * getter cateParentIds(分类父ids)
     */
    public String getCateParentIds() {
        return cateParentIds;
    }

    /**
     * setter cateParentIds(分类父ids)
     */
    public void setCateParentIds(String cateParentIds) {
        this.cateParentIds = cateParentIds;
    }

    /**
     * getter storeCateName(店铺分类名)
     */
    public String getStoreCateName() {
        return storeCateName;
    }

    /**
     * setter storeCateName(店铺分类名)
     */
    public void setStoreCateName(String storeCateName) {
        this.storeCateName = storeCateName;
    }

    /**
     * getter isOpen(是否开店)
     */
    public String getIsOpen() {
        return isOpen;
    }

    /**
     * setter isOpen(是否开店)
     */
    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * getter storeCateParentIds(店铺分类父ids)
     */
    public String getStoreCateParentIds() {
        return storeCateParentIds;
    }

    /**
     * setter storeCateParentIds(店铺分类父ids)
     */
    public void setStoreCateParentIds(String storeCateParentIds) {
        this.storeCateParentIds = storeCateParentIds;
    }

    /**
     * getter brandName(品牌名)
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * setter brandName(品牌名)
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * getter brandFirstLeftter(品牌首字母)
     */
    public String getBrandFirstLeftter() {
        return brandFirstLeftter;
    }

    /**
     * setter brandFirstLeftter(品牌首字母)
     */
    public void setBrandFirstLeftter(String brandFirstLeftter) {
        this.brandFirstLeftter = brandFirstLeftter;
    }

    /**
     * getter brandEnglishName(品牌英文名)
     */
    public String getBrandEnglishName() {
        return brandEnglishName;
    }

    /**
     * setter brandEnglishName(品牌英文名)
     */
    public void setBrandEnglishName(String brandEnglishName) {
        this.brandEnglishName = brandEnglishName;
    }

    /**
     * getter allSales(总销量)
     */
    public Long getAllSales() {
        return allSales;
    }

    /**
     * setter allSales(总销量)
     */
    public void setAllSales(Long allSales) {
        this.allSales = allSales;
    }

    /**
     * getter weekSales(周销量)
     */
    public Long getWeekSales() {
        return weekSales;
    }

    /**
     * setter weekSales(周销量)
     */
    public void setWeekSales(Long weekSales) {
        this.weekSales = weekSales;
    }

    /**
     * getter monthSales(月销量)
     */
    public Long getMonthSales() {
        return monthSales;
    }

    /**
     * setter monthSales(月销量)
     */
    public void setMonthSales(Long monthSales) {
        this.monthSales = monthSales;
    }

    /**
     * getter month3Sales(三个月销量)
     */
    public Long getMonth3Sales() {
        return month3Sales;
    }

    /**
     * setter month3Sales(三个月销量)
     */
    public void setMonth3Sales(Long month3Sales) {
        this.month3Sales = month3Sales;
    }

    /**
     * getter carIds(车型ids)
     */
    public String getCarIds() {
        return carIds;
    }

    /**
     * setter carIds(车型ids)
     */
    public void setCarIds(String carIds) {
        this.carIds = carIds;
    }

    /**
     * getter paramValue(参数值)
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * setter paramValue(参数值)
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * getter collectionCount(收藏量)
     */
    public Long getCollectionCount() {
        return collectionCount;
    }

    /**
     * setter collectionCount(收藏量)
     */
    public void setCollectionCount(Long collectionCount) {
        this.collectionCount = collectionCount;
    }

    /**
     * getter commentCount(评论量)
     */
    public Long getCommentCount() {
        return commentCount;
    }

    /**
     * setter commentCount(评论量)
     */
    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * getter pId(商品id)
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(商品id)
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter uId(用户id)
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(用户id)
     */
    public void setUId(Long uId) {
        this.uId = uId;
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
     * getter name(商品名称)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(商品名称)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter status(状态)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(状态)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter categoryId(分类id)
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * setter categoryId(分类id)
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * getter storeCategoryId(店内分类)
     */
    public Long getStoreCategoryId() {
        return storeCategoryId;
    }

    /**
     * setter storeCategoryId(店内分类)
     */
    public void setStoreCategoryId(Long storeCategoryId) {
        this.storeCategoryId = storeCategoryId;
    }

    /**
     * getter image(封面图)
     */
    public String getImage() {
        return image;
    }

    /**
     * setter image(封面图)
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * getter brandId(品牌id)
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * setter brandId(品牌id)
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * getter nameSub(副标题)
     */
    public String getNameSub() {
        return nameSub;
    }

    /**
     * setter nameSub(副标题)
     */
    public void setNameSub(String nameSub) {
        this.nameSub = nameSub;
    }

    /**
     * getter unit(单位)
     */
    public String getUnit() {
        return unit;
    }

    /**
     * setter unit(单位)
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * getter type(类型(1零售、2批发、3混合))
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(类型(1零售、2批发、3混合))
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter isGift(是否是礼品)
     */
    public String getIsGift() {
        return isGift;
    }

    /**
     * setter isGift(是否是礼品)
     */
    public void setIsGift(String isGift) {
        this.isGift = isGift;
    }

    /**
     * getter isRecommend(是否推荐)
     */
    public String getIsRecommend() {
        return isRecommend;
    }

    /**
     * setter isRecommend(是否推荐)
     */
    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    /**
     * getter benefit(优惠)
     */
    public String getBenefit() {
        return benefit;
    }

    /**
     * setter benefit(优惠)
     */
    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    /**
     * getter recommendSort(活动积分)
     */
    public Long getRecommendSort() {
        return recommendSort;
    }

    /**
     * setter recommendSort(活动积分)
     */
    public void setRecommendSort(Long recommendSort) {
        this.recommendSort = recommendSort;
    }

    /**
     * getter marketPrice(市场价)
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * setter marketPrice(市场价)
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * getter point(赠送积分)
     */
    public String getPoint() {
        return point;
    }

    /**
     * setter point(赠送积分)
     */
    public void setPoint(String point) {
        this.point = point;
    }

    /**
     * getter maxPrice(最高价)
     */
    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    /**
     * setter maxPrice(最高价)
     */
    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * getter minPrice(最低价)
     */
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    /**
     * setter minPrice(最低价)
     */
    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * getter action(是否参加活动)
     */
    public String getAction() {
        return action;
    }

    /**
     * setter action(是否参加活动)
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * getter maxPrice1(最高零售价)
     */
    public BigDecimal getMaxPrice1() {
        return maxPrice1;
    }

    /**
     * setter maxPrice1(最高零售价)
     */
    public void setMaxPrice1(BigDecimal maxPrice1) {
        this.maxPrice1 = maxPrice1;
    }

    /**
     * getter minPrice1(最低零售价)
     */
    public BigDecimal getMinPrice1() {
        return minPrice1;
    }

    /**
     * setter minPrice1(最低零售价)
     */
    public void setMinPrice1(BigDecimal minPrice1) {
        this.minPrice1 = minPrice1;
    }

    /**
     * getter maxPrice2(最高批发价)
     */
    public BigDecimal getMaxPrice2() {
        return maxPrice2;
    }

    /**
     * setter maxPrice2(最高批发价)
     */
    public void setMaxPrice2(BigDecimal maxPrice2) {
        this.maxPrice2 = maxPrice2;
    }

    /**
     * getter minPrice2(最低批发价)
     */
    public BigDecimal getMinPrice2() {
        return minPrice2;
    }

    /**
     * setter minPrice2(最低批发价)
     */
    public void setMinPrice2(BigDecimal minPrice2) {
        this.minPrice2 = minPrice2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter shelfTime(上架时间)
     */
    public Date getShelfTime() {
        return shelfTime;
    }

    /**
     * setter shelfTime(上架时间)
     */
    public void setShelfTime(Date shelfTime) {
        this.shelfTime = shelfTime;
    }

    /**
     * getter deliverGoodsDate(发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议)
     */
    public String getDeliverGoodsDate() {
        return deliverGoodsDate;
    }

    /**
     * setter deliverGoodsDate(发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议)
     */
    public void setDeliverGoodsDate(String deliverGoodsDate) {
        this.deliverGoodsDate = deliverGoodsDate;
    }

    /**
     * getter purchasingAmount(起购量)
     */
    public String getPurchasingAmount() {
        return purchasingAmount;
    }

    /**
     * setter purchasingAmount(起购量)
     */
    public void setPurchasingAmount(String purchasingAmount) {
        this.purchasingAmount = purchasingAmount;
    }

    /**
     * getter shelfTime(上架时间)
     */
    public Date getBeginShelfTime() {
        return beginShelfTime;
    }

    /**
     * setter shelfTime(上架时间)
     */
    public void setBeginShelfTime(Date beginShelfTime) {
        this.beginShelfTime = beginShelfTime;
    }

    /**
     * getter shelfTime(上架时间)
     */
    public Date getEndShelfTime() {
        return endShelfTime;
    }

    /**
     * setter shelfTime(上架时间)
     */
    public void setEndShelfTime(Date endShelfTime) {
        this.endShelfTime = endShelfTime;
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

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }
}