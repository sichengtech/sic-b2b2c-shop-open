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
package com.sicheng.search;

import com.sicheng.admin.sys.entity.User;
import com.sicheng.common.persistence.DataEntity;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * ProductBo表示一个商品
 *
 * @author 范秀秀
 * @version 2018-01-24
 */
public class ProductBo<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    @Field("user_name")
    private String userName;                // 卖家登录名
    @Field("store_name")
    private String storeName;               // 店铺名称
    @Field("province_id")
    private Long provinceId;                // 省id
    @Field("city_id")
    private Long cityId;                    // 市id
    @Field("province_name")
    private String provinceName;            // 省名称
    @Field("city_name")
    private String cityName;                // 市名称
    @Field("category_name")
    private String categoryName;            // 分类名称
    @Field("category_level")
    private Long categoryLevel;             // 分类等级
    @Field("store_type")
    private String storeType;               // 店铺类型字典（10普通店铺，20旗舰店）
    @Field("cate_first_letter")
    private String cateFirstLetter;         // 分类首字母
    @Field("cate_parent_ids")
    private String cateParentIds;           // 分类父ids
    @Field("store_cate_name")
    private String storeCateName;           // 店铺分类名
    private String isOpen;                  // 是否开店
    @Field("store_cate_parent_ids")
    private String storeCateParentIds;      // 店铺分类父ids
    @Field("brand_name")
    private String brandName;               // 品牌名
    @Field("brand_first_leftter")
    private String brandFirstLeftter;       // 品牌首字母
    @Field("brand_english_name")
    private String brandEnglishName;        // 品牌英文名
    @Field("all_sales")
    private Long allSales;                  // 总销量
    @Field("week_sales")
    private Long weekSales;                 // 周销量
    @Field("month_sales")
    private Long monthSales;                // 月销量monthSales
    @Field("month3_sales")
    private Long month3Sales;               // 三个月销量
    @Field("car_ids")
    private String carIds;                  // 车型ids
    @Field("param_value")
    private String paramValue;              // 参数值
    @Field("collection_count")
    private Long collectionCount;           // 收藏量
    @Field("comment_count")
    private Long commentCount;              // 评论量

    private Long pId;                       // 商品id
    @Field("u_id")
    private Long uId;                       // 用户id
    @Field("store_id")
    private Long storeId;                   // 店铺id
    @Field("name")
    private String name;                    // 商品名称
    @Field("status")
    private String status;                  // 状态
    @Field("category_id")
    private Long categoryId;                // 分类id
    private Long storeCategoryId;           // 店内分类
    @Field("image")
    private String image;                   // 封面图
    @Field("brand_id")
    private Long brandId;                   // 品牌id
    @Field("name_sub")
    private String nameSub;                 // 副标题
    @Field("unit")
    private String unit;                    // 单位
    @Field("type")
    private String type;                    // 类型(1零售、2批发、3混合)
    private String isGift;                  // 是否是礼品
    @Field("is_recommend")
    private String isRecommend;             // 是否推荐
    private String benefit;                 // 优惠
    private Long recommendSort;             // 活动积分
    private String marketPrice;         // 市场价
    private String point;                   // 赠送积分
    @Field("max_price")
    private String maxPrice;            // 最高价
    @Field("min_price")
    private String minPrice;            // 最低价
    private String action;                  // 是否参加活动
    @Field("max_price1")
    private String maxPrice1;           // 最高零售价
    @Field("min_price1")
    private String minPrice1;           // 最低零售价
    @Field("max_price2")
    private String maxPrice2;           // 最高批发价
    @Field("min_price2")
    private String minPrice2;           // 最低批发价
    private Date shelfTime;                 // 上架时间
    private String deliverGoodsDate;        // 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
    private String purchasingAmount;        // 起购量
    private Date beginShelfTime;            // 开始 上架时间
    private Date endShelfTime;              // 结束 上架时间
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public ProductBo() {
        super();
    }

    public ProductBo(Long id) {
        super(id);
    }


    @Override
    @Field("create_date")
    public void setCreateDate(Date createDate) {
        super.setCreateDate(createDate);
    }

    @Override
    @Field("update_by")
    public void setUpdateBy(User updateBy) {
        super.setUpdateBy(updateBy);
    }

    @Override
    @Field("create_by")
    public void setCreateBy(User createBy) {
        super.setCreateBy(createBy);
    }

    @Override
    @Field("update_date")
    public void setUpdateDate(Date updateDate) {
        super.setUpdateDate(updateDate);
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Long categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getCateFirstLetter() {
        return cateFirstLetter;
    }

    public void setCateFirstLetter(String cateFirstLetter) {
        this.cateFirstLetter = cateFirstLetter;
    }

    public String getCateParentIds() {
        return cateParentIds;
    }

    public void setCateParentIds(String cateParentIds) {
        this.cateParentIds = cateParentIds;
    }

    public String getStoreCateName() {
        return storeCateName;
    }

    public void setStoreCateName(String storeCateName) {
        this.storeCateName = storeCateName;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getStoreCateParentIds() {
        return storeCateParentIds;
    }

    public void setStoreCateParentIds(String storeCateParentIds) {
        this.storeCateParentIds = storeCateParentIds;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandFirstLeftter() {
        return brandFirstLeftter;
    }

    public void setBrandFirstLeftter(String brandFirstLeftter) {
        this.brandFirstLeftter = brandFirstLeftter;
    }

    public String getBrandEnglishName() {
        return brandEnglishName;
    }

    public void setBrandEnglishName(String brandEnglishName) {
        this.brandEnglishName = brandEnglishName;
    }

    public Long getAllSales() {
        return allSales;
    }

    public void setAllSales(Long allSales) {
        this.allSales = allSales;
    }

    public Long getWeekSales() {
        return weekSales;
    }

    public void setWeekSales(Long weekSales) {
        this.weekSales = weekSales;
    }

    public Long getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(Long monthSales) {
        this.monthSales = monthSales;
    }

    public Long getMonth3Sales() {
        return month3Sales;
    }

    public void setMonth3Sales(Long month3Sales) {
        this.month3Sales = month3Sales;
    }

    public String getCarIds() {
        return carIds;
    }

    public void setCarIds(String carIds) {
        this.carIds = carIds;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Long getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Long collectionCount) {
        this.collectionCount = collectionCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getpId() {
        return pId;
    }

    @Field("id")
    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getStoreCategoryId() {
        return storeCategoryId;
    }

    public void setStoreCategoryId(Long storeCategoryId) {
        this.storeCategoryId = storeCategoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getNameSub() {
        return nameSub;
    }

    public void setNameSub(String nameSub) {
        this.nameSub = nameSub;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIsGift(String isGift) {
        this.isGift = isGift;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public void setRecommendSort(Long recommendSort) {
        this.recommendSort = recommendSort;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setMaxPrice1(String maxPrice1) {
        this.maxPrice1 = maxPrice1;
    }

    public void setMinPrice1(String minPrice1) {
        this.minPrice1 = minPrice1;
    }

    public void setMaxPrice2(String maxPrice2) {
        this.maxPrice2 = maxPrice2;
    }

    public void setMinPrice2(String minPrice2) {
        this.minPrice2 = minPrice2;
    }

    public void setShelfTime(Date shelfTime) {
        this.shelfTime = shelfTime;
    }

    public void setDeliverGoodsDate(String deliverGoodsDate) {
        this.deliverGoodsDate = deliverGoodsDate;
    }

    public void setPurchasingAmount(String purchasingAmount) {
        this.purchasingAmount = purchasingAmount;
    }

    public void setBeginShelfTime(Date beginShelfTime) {
        this.beginShelfTime = beginShelfTime;
    }

    public void setEndShelfTime(Date endShelfTime) {
        this.endShelfTime = endShelfTime;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public void setBeginUpdateDate(Date beginUpdateDate) {
        this.beginUpdateDate = beginUpdateDate;
    }

    public void setEndUpdateDate(Date endUpdateDate) {
        this.endUpdateDate = endUpdateDate;
    }
}