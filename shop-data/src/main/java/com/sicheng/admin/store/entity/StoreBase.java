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

import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺管理 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-08-16
 */
public class StoreBase<T> extends DataEntity<T> {

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
    private String bak1;                    // 备用字段1
    private String bak2;                    // 备用字段2
    private String bak3;                    // 备用字段3
    private String bak4;                    // 备用字段4
    private String bak5;                    // 备用字段5
    private String bak6;                    // 备用字段6
    private String bak7;                    // 备用字段7
    private String bak8;                    // 备用字段8
    private String bak9;                    // 备用字段9
    private String bak10;                   // 备用字段10
    private String industry;                // 店铺主营产品
    private String storeType;               // 店铺类型字典（1普通店铺，2旗舰店铺）
    private Long levelId;                   // 店铺等级（关联店铺等级id）（注意：在商家入驻审核二审通过之后生成的是入驻申请中选择的店铺类型，以后修改的时候记得把入驻申请表，入驻查看表都修改了）
    private Long industryId;                // 主营行业（关联主营行业id）（注意：在商家入驻审核二审通过之后生成的是入驻申请中选择的店铺类型，以后修改的时候记得把入驻申请表，入驻查看表都修改了）
    private BigDecimal commission;          // 分佣比例
    private String markingImgPath;          // 店铺打标地址path
    private Date beginCreateDate;           // 开始 创建时间（开店日期）
    private Date endCreateDate;             // 结束 创建时间（开店日期）
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public StoreBase() {
        super();
    }

    public StoreBase(Long id) {
        super(id);
        this.storeId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return storeId;
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
        this.storeId = id;
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
     * getter bak6(备用字段6)
     */
    public String getBak6() {
        return bak6;
    }

    /**
     * setter bak6(备用字段6)
     */
    public void setBak6(String bak6) {
        this.bak6 = bak6;
    }

    /**
     * getter bak7(备用字段7)
     */
    public String getBak7() {
        return bak7;
    }

    /**
     * setter bak7(备用字段7)
     */
    public void setBak7(String bak7) {
        this.bak7 = bak7;
    }

    /**
     * getter bak8(备用字段8)
     */
    public String getBak8() {
        return bak8;
    }

    /**
     * setter bak8(备用字段8)
     */
    public void setBak8(String bak8) {
        this.bak8 = bak8;
    }

    /**
     * getter bak9(备用字段9)
     */
    public String getBak9() {
        return bak9;
    }

    /**
     * setter bak9(备用字段9)
     */
    public void setBak9(String bak9) {
        this.bak9 = bak9;
    }

    /**
     * getter bak10(备用字段10)
     */
    public String getBak10() {
        return bak10;
    }

    /**
     * setter bak10(备用字段10)
     */
    public void setBak10(String bak10) {
        this.bak10 = bak10;
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
     * getter storeType(店铺类型字典（1普通店铺，2旗舰店铺）)
     */
    public String getStoreType() {
        return storeType;
    }

    /**
     * setter storeType(店铺类型字典（1普通店铺，2旗舰店铺）)
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
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * setter commission(分佣比例)
     */
    public void setCommission(BigDecimal commission) {
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

}