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
package com.sicheng.admin.sso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 商家注册信息 Entity 父类
 *
 * @author zjl
 * @version 2017-09-13
 */
public class UserMerchantInfoBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long uId;                       // 主键,会员的ID
    private String type;                    // 企业类型（字典）
    private String industry;                // 行业属性（字典）
    private String name;                    // 公司名称
    private String introduce;               // 公司介绍
    private Long countryId;                 // 公司所在地国家(关联地区表)
    private String countryName;             // 公司所在地国家名字
    private Long provinceId;                // 公司所在地省(关联地区表)
    private String provinceName;            // 公司所在地省名字
    private Long cityId;                    // 公司所在地市(关联地区表)
    private String cityName;                // 公司所在地市名字
    private Long districtId;                // 公司所在地县(关联地区表)
    private String districtName;            // 公司县名字
    private String detailedAddress;         // 公司详细地址
    private String customCall;              // 客服电话
    private String companyWebsite;          // 公司网址
    private String companyBrand;            // 公司品牌
    private String department;              // 所在部门
    private String contacts;                // 联系人
    private String contactsTelephone;       // 联系人电话
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

    public UserMerchantInfoBase() {
        super();
    }

    public UserMerchantInfoBase(Long id) {
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
     * getter type(企业类型（字典）)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(企业类型（字典）)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter industry(行业属性（字典）)
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * setter industry(行业属性（字典）)
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * getter name(公司名称)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(公司名称)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter introduce(公司介绍)
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * setter introduce(公司介绍)
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * getter countryId(公司所在地国家(关联地区表))
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * setter countryId(公司所在地国家(关联地区表))
     */
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    /**
     * getter countryName(公司所在地国家名字)
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * setter countryName(公司所在地国家名字)
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * getter provinceId(公司所在地省(关联地区表))
     */
    public Long getProvinceId() {
        return provinceId;
    }

    /**
     * setter provinceId(公司所在地省(关联地区表))
     */
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * getter provinceName(公司所在地省名字)
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * setter provinceName(公司所在地省名字)
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * getter cityId(公司所在地市(关联地区表))
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * setter cityId(公司所在地市(关联地区表))
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * getter cityName(公司所在地市名字)
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * setter cityName(公司所在地市名字)
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * getter districtId(公司所在地县(关联地区表))
     */
    public Long getDistrictId() {
        return districtId;
    }

    /**
     * setter districtId(公司所在地县(关联地区表))
     */
    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    /**
     * getter districtName(公司县名字)
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * setter districtName(公司县名字)
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * getter detailedAddress(公司详细地址)
     */
    public String getDetailedAddress() {
        return detailedAddress;
    }

    /**
     * setter detailedAddress(公司详细地址)
     */
    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    /**
     * getter customCall(客服电话)
     */
    public String getCustomCall() {
        return customCall;
    }

    /**
     * setter customCall(客服电话)
     */
    public void setCustomCall(String customCall) {
        this.customCall = customCall;
    }

    /**
     * getter companyWebsite(公司网址)
     */
    public String getCompanyWebsite() {
        return companyWebsite;
    }

    /**
     * setter companyWebsite(公司网址)
     */
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    /**
     * getter companyBrand(公司品牌)
     */
    public String getCompanyBrand() {
        return companyBrand;
    }

    /**
     * setter companyBrand(公司品牌)
     */
    public void setCompanyBrand(String companyBrand) {
        this.companyBrand = companyBrand;
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
     * getter contacts(联系人)
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * setter contacts(联系人)
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * getter contactsTelephone(联系人电话)
     */
    public String getContactsTelephone() {
        return contactsTelephone;
    }

    /**
     * setter contactsTelephone(联系人电话)
     */
    public void setContactsTelephone(String contactsTelephone) {
        this.contactsTelephone = contactsTelephone;
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