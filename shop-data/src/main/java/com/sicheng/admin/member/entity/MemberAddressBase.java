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
package com.sicheng.admin.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 买家收货地址 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-05-12
 */
public class MemberAddressBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long addressId;                 // 主键
    private String addressName;             // 收货地址名称
    private Long uId;                       // 关联(会员表)
    private String name;                    // 收货人
    private Long countryId;                 // 国家(关联地区表)
    private String countryName;             // 国家名字
    private Long provinceId;                // 省(关联地区表)
    private String provinceName;            // 省名字
    private Long cityId;                    // 市(关联地区表)
    private String cityName;                // 市名字
    private Long districtId;                // 县(关联地区表)
    private String districtName;            // 县名字
    private String detailedAddress;         // 详细地址
    private String mobile;                  // 联系方式（手机，电话号码）
    private String zipCode;                 // 邮编
    private String isDefault;               // 是否默认(0不默认 1默认)
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

    public MemberAddressBase() {
        super();
    }

    public MemberAddressBase(Long id) {
        super(id);
        this.addressId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return addressId;
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
        this.addressId = id;
    }

    /**
     * getter addressId(主键)
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     * setter addressId(主键)
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     * getter addressName(收货地址名称)
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * setter addressName(收货地址名称)
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     * getter uId(关联(会员表))
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(关联(会员表))
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter name(收货人)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(收货人)
     */
    public void setName(String name) {
        this.name = name;
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
     * getter mobile(联系方式（手机，电话号码）)
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * setter mobile(联系方式（手机，电话号码）)
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * getter zipCode(邮编)
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * setter zipCode(邮编)
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * getter isDefault(是否默认(0不默认 1默认))
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * setter isDefault(是否默认(0不默认 1默认))
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
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