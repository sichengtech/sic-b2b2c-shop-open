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
package com.sicheng.admin.sso.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 汽车服务门店 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-07-31
 */
public class UserRepairShopBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long uId;                       // 主键,会员的ID
    private String authType;                // 审核状态：0待审、1通过、2未通过
    private String type;                    // 门店类型（字典）
    private String name;                    // 门店名称
    private String introduce;               // 门店介绍
    private Long countryId;                 // 门店所在地国家(关联地区表)
    private String countryName;             // 门店所在地国家名字
    private Long provinceId;                // 门店所在地省(关联地区表)
    private String provinceName;            // 门店所在地省名字
    private Long cityId;                    // 门店所在地市(关联地区表)
    private String cityName;                // 门店所在地市名字
    private Long districtId;                // 门店所在地县(关联地区表)
    private String districtName;            // 县名字
    private String detailedAddress;         // 详细地址
    private String burns;                   // 门店面积
    private String brands;                  // 经营品牌，号分割
    private Date openDate;                  // 建店日期
    private String bossName;                // 老板姓名
    private String bossMobile;              // 老板电话
    private String path1;                   // 门店照片path1
    private String path2;                   // 门店照片path2
    private String path3;                   // 门店照片path3
    private String path4;                   // 门店照片path4
    private String path5;                   // 门店照片path5
    private String peopleCount;             // 门店人数（字典）user_people_count
    private String businessStatus;          // 营业状态（字典）user_business_status
    private Date openShopDate;              // 开店营业时间
    private Date closeShopDate;             // 关店营业时间
    private String hotline;                 // 服务热线
    private String shopkeeperName;          // 店主姓名
    private String shopkeeperMobile;        // 店主手机
    private String contactsName;            // 联系人姓名
    private String department;              // 所在部门
    private String mobile;                  // 本人手机
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
    private String authContent;             // 审核内容
    private Date beginOpenDate;             // 开始 建店日期
    private Date endOpenDate;               // 结束 建店日期
    private Date beginOpenShopDate;         // 开始 开店营业时间
    private Date endOpenShopDate;           // 结束 开店营业时间
    private Date beginCloseShopDate;        // 开始 关店营业时间
    private Date endCloseShopDate;          // 结束 关店营业时间

    public UserRepairShopBase() {
        super();
    }

    public UserRepairShopBase(Long id) {
        super(id);
        this.uId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return uId;
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
        this.uId = id;
    }

    /**
     * getter uId(主键,会员的ID)
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(主键,会员的ID)
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter authType(审核状态：0待审、1通过、2未通过)
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * setter authType(审核状态：0待审、1通过、2未通过)
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    /**
     * getter type(门店类型（字典）use_shop_type)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(门店类型（字典）use_shop_type)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter name(门店名称)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(门店名称)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter introduce(门店介绍)
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * setter introduce(门店介绍)
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * getter countryId(门店所在地国家(关联地区表))
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * setter countryId(门店所在地国家(关联地区表))
     */
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    /**
     * getter countryName(门店所在地国家名字)
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * setter countryName(门店所在地国家名字)
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * getter provinceId(门店所在地省(关联地区表))
     */
    public Long getProvinceId() {
        return provinceId;
    }

    /**
     * setter provinceId(门店所在地省(关联地区表))
     */
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * getter provinceName(门店所在地省名字)
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * setter provinceName(门店所在地省名字)
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * getter cityId(门店所在地市(关联地区表))
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * setter cityId(门店所在地市(关联地区表))
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * getter cityName(门店所在地市名字)
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * setter cityName(门店所在地市名字)
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * getter districtId(门店所在地县(关联地区表))
     */
    public Long getDistrictId() {
        return districtId;
    }

    /**
     * setter districtId(门店所在地县(关联地区表))
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
     * getter burns(门店面积)
     */
    public String getBurns() {
        return burns;
    }

    /**
     * setter burns(门店面积)
     */
    public void setBurns(String burns) {
        this.burns = burns;
    }

    /**
     * getter brands(经营品牌，号分割)
     */
    public String getBrands() {
        return brands;
    }

    /**
     * setter brands(经营品牌，号分割)
     */
    public void setBrands(String brands) {
        this.brands = brands;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter openDate(建店日期)
     */
    public Date getOpenDate() {
        return openDate;
    }

    /**
     * setter openDate(建店日期)
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * getter bossName(老板姓名)
     */
    public String getBossName() {
        return bossName;
    }

    /**
     * setter bossName(老板姓名)
     */
    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    /**
     * getter bossMobile(老板电话)
     */
    public String getBossMobile() {
        return bossMobile;
    }

    /**
     * setter bossMobile(老板电话)
     */
    public void setBossMobile(String bossMobile) {
        this.bossMobile = bossMobile;
    }

    /**
     * getter path1(门店照片path1)
     */
    public String getPath1() {
        return path1;
    }

    /**
     * setter path1(门店照片path1)
     */
    public void setPath1(String path1) {
        this.path1 = path1;
    }

    /**
     * getter path2(门店照片path2)
     */
    public String getPath2() {
        return path2;
    }

    /**
     * setter path2(门店照片path2)
     */
    public void setPath2(String path2) {
        this.path2 = path2;
    }

    /**
     * getter path3(门店照片path3)
     */
    public String getPath3() {
        return path3;
    }

    /**
     * setter path3(门店照片path3)
     */
    public void setPath3(String path3) {
        this.path3 = path3;
    }

    /**
     * getter path4(门店照片path4)
     */
    public String getPath4() {
        return path4;
    }

    /**
     * setter path4(门店照片path4)
     */
    public void setPath4(String path4) {
        this.path4 = path4;
    }

    /**
     * getter path5(门店照片path5)
     */
    public String getPath5() {
        return path5;
    }

    /**
     * setter path5(门店照片path5)
     */
    public void setPath5(String path5) {
        this.path5 = path5;
    }

    /**
     * getter peopleCount(门店人数（字典）user_people_count)
     */
    public String getPeopleCount() {
        return peopleCount;
    }

    /**
     * setter peopleCount(门店人数（字典）user_people_count)
     */
    public void setPeopleCount(String peopleCount) {
        this.peopleCount = peopleCount;
    }

    /**
     * getter businessStatus(营业状态（字典）user_business_status)
     */
    public String getBusinessStatus() {
        return businessStatus;
    }

    /**
     * setter businessStatus(营业状态（字典）user_business_status)
     */
    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter openShopDate(开店营业时间)
     */
    public Date getOpenShopDate() {
        return openShopDate;
    }

    /**
     * setter openShopDate(开店营业时间)
     */
    public void setOpenShopDate(Date openShopDate) {
        this.openShopDate = openShopDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter closeShopDate(关店营业时间)
     */
    public Date getCloseShopDate() {
        return closeShopDate;
    }

    /**
     * setter closeShopDate(关店营业时间)
     */
    public void setCloseShopDate(Date closeShopDate) {
        this.closeShopDate = closeShopDate;
    }

    /**
     * getter hotline(服务热线)
     */
    public String getHotline() {
        return hotline;
    }

    /**
     * setter hotline(服务热线)
     */
    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    /**
     * getter shopkeeperName(店主姓名)
     */
    public String getShopkeeperName() {
        return shopkeeperName;
    }

    /**
     * setter shopkeeperName(店主姓名)
     */
    public void setShopkeeperName(String shopkeeperName) {
        this.shopkeeperName = shopkeeperName;
    }

    /**
     * getter shopkeeperMobile(店主手机)
     */
    public String getShopkeeperMobile() {
        return shopkeeperMobile;
    }

    /**
     * setter shopkeeperMobile(店主手机)
     */
    public void setShopkeeperMobile(String shopkeeperMobile) {
        this.shopkeeperMobile = shopkeeperMobile;
    }

    /**
     * getter contactsName(联系人姓名)
     */
    public String getContactsName() {
        return contactsName;
    }

    /**
     * setter contactsName(联系人姓名)
     */
    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    /**
     * getter department(所在部门)
     */
    public String getDepartment() {
        return department;
    }

    /**
     * setter department(所在部门)
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * getter mobile(本人手机)
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * setter mobile(本人手机)
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
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
     * getter authContent(审核内容)
     */
    public String getAuthContent() {
        return authContent;
    }

    /**
     * setter authContent(审核内容)
     */
    public void setAuthContent(String authContent) {
        this.authContent = authContent;
    }

    /**
     * getter openDate(建店日期)
     */
    public Date getBeginOpenDate() {
        return beginOpenDate;
    }

    /**
     * setter openDate(建店日期)
     */
    public void setBeginOpenDate(Date beginOpenDate) {
        this.beginOpenDate = beginOpenDate;
    }

    /**
     * getter openDate(建店日期)
     */
    public Date getEndOpenDate() {
        return endOpenDate;
    }

    /**
     * setter openDate(建店日期)
     */
    public void setEndOpenDate(Date endOpenDate) {
        this.endOpenDate = endOpenDate;
    }

    /**
     * getter openShopDate(开店营业时间)
     */
    public Date getBeginOpenShopDate() {
        return beginOpenShopDate;
    }

    /**
     * setter openShopDate(开店营业时间)
     */
    public void setBeginOpenShopDate(Date beginOpenShopDate) {
        this.beginOpenShopDate = beginOpenShopDate;
    }

    /**
     * getter openShopDate(开店营业时间)
     */
    public Date getEndOpenShopDate() {
        return endOpenShopDate;
    }

    /**
     * setter openShopDate(开店营业时间)
     */
    public void setEndOpenShopDate(Date endOpenShopDate) {
        this.endOpenShopDate = endOpenShopDate;
    }

    /**
     * getter closeShopDate(关店营业时间)
     */
    public Date getBeginCloseShopDate() {
        return beginCloseShopDate;
    }

    /**
     * setter closeShopDate(关店营业时间)
     */
    public void setBeginCloseShopDate(Date beginCloseShopDate) {
        this.beginCloseShopDate = beginCloseShopDate;
    }

    /**
     * getter closeShopDate(关店营业时间)
     */
    public Date getEndCloseShopDate() {
        return endCloseShopDate;
    }

    /**
     * setter closeShopDate(关店营业时间)
     */
    public void setEndCloseShopDate(Date endCloseShopDate) {
        this.endCloseShopDate = endCloseShopDate;
    }

}