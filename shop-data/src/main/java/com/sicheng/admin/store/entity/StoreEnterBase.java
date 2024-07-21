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
package com.sicheng.admin.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 入驻申请（业务查看） Entity 父类
 *
 * @author cl
 * @version 2017-08-17
 */
public class StoreEnterBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long enterId;                   // 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
    private String status;                  // 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过)一审审核基本信息，二审审核是付钱
    private String isPerfect;               // 入驻信息一审是否完善，0不完善、1完善
    private String companyName;             // 公司名称
    private Long countryId;                 // 国家(关联地区表)
    private String countryName;             // 国家名字
    private Long provinceId;                // 省(关联地区表)
    private String provinceName;            // 省名字
    private Long cityId;                    // 市(关联地区表)
    private String cityName;                // 市名字
    private Long districtId;                // 县(关联地区表)
    private String districtName;            // 县名字
    private String detailedAddress;         // 公司_详细地址
    private Integer registeredCapital;      // 注册资金，单位万元
    private String sellerLicense;           // 营业执照号
    private String sellerLicensePath;       // 营业执照电子版图片路径
    private String organizationCode;        // 组织机构代码
    private String organizationCodePath;    // 组织机构代码电子版图片路径
    private String businessType;            // 证件类型（1.普通营业执照2.多证合一营业执照）
    private String socialCreditCode;        // 统一社会信用代码
    private String socialCreditCodePath;    // 多证合一营业执照
    private String openAnAccountLicense;    // 开户许可证核准号
    private String openAnAccountLicensePath;  // 开户许可证核准号电子版
    private String storeType;               // 店铺类型（1.普通店铺，2.旗舰店铺）
    private String storeBrand;              // 店铺品牌名（多个按照，分割）
    private String storeBrandPath;          // 店铺品牌商标证书path（多个按照，分割）
    private String contact;                 // 联系人
    private String contactNumber;           // 联系人电话
    private String taxRegistrationNumber;   // 税务登记证号
    private String legalName;               // 法人姓名
    private String taxRegistrationNumberPath;   // 税务登记，税务登记证号电子版path
    private String legalIdCardCode;         // 法人身份证号
    private String storeName;               // 店铺名（注意店铺设置修改的时候记得把这个修改了）
    private String legalIdCardCodePositive; // 法人身份证号（正面）
    private String legalIdCardCodeOpposite; // 法人身份证号（反面）
    private Long levelId;                   // 店铺等级（关联店铺等级id）
    private Long industryId;                // 主营行业（关联主营行业id）
    private BigDecimal summaryOfCoping;     // 应付总金额
    private String paymentVoucherPath;      // 商家上传付款凭证path
    private String paymentInstructions;     // 商家付款凭证说明
    private String oneAuditOpinion;         // 一审审核意见
    private String twoAuditOpinion;         // 二审审核意见
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
    @JsonIgnore
    private String bak6;                    // 备用字段6
    @JsonIgnore
    private String bak7;                    // 备用字段7
    @JsonIgnore
    private String bak8;                    // 备用字段8
    @JsonIgnore
    private String bak9;                    // 备用字段9
    @JsonIgnore
    private String bak10;                   // 备用字段10
    private BigDecimal commission;          // 分佣比例
    private String auditOpinion;            // 入驻信息审核意见
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public StoreEnterBase() {
        super();
    }

    public StoreEnterBase(Long id) {
        super(id);
        this.enterId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return enterId;
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
        this.enterId = id;
    }

    /**
     * getter enterId(主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id))
     */
    public Long getEnterId() {
        return enterId;
    }

    /**
     * setter enterId(主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id))
     */
    public void setEnterId(Long enterId) {
        this.enterId = enterId;
    }

    /**
     * getter status(入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过)一审审核基本信息，二审审核是付钱)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过)一审审核基本信息，二审审核是付钱)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter isPerfect(入驻信息一审是否完善，0不完善、1完善)
     */
    public String getIsPerfect() {
        return isPerfect;
    }

    /**
     * setter isPerfect(入驻信息一审是否完善，0不完善、1完善)
     */
    public void setIsPerfect(String isPerfect) {
        this.isPerfect = isPerfect;
    }

    /**
     * getter companyName(公司名称)
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * setter companyName(公司名称)
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
     * getter detailedAddress(公司_详细地址)
     */
    public String getDetailedAddress() {
        return detailedAddress;
    }

    /**
     * setter detailedAddress(公司_详细地址)
     */
    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    /**
     * getter registeredCapital(注册资金，单位万元)
     */
    public Integer getRegisteredCapital() {
        return registeredCapital;
    }

    /**
     * setter registeredCapital(注册资金，单位万元)
     */
    public void setRegisteredCapital(Integer registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    /**
     * getter sellerLicense(营业执照号)
     */
    public String getSellerLicense() {
        return sellerLicense;
    }

    /**
     * setter sellerLicense(营业执照号)
     */
    public void setSellerLicense(String sellerLicense) {
        this.sellerLicense = sellerLicense;
    }

    /**
     * getter sellerLicensePath(营业执照电子版图片路径)
     */
    public String getSellerLicensePath() {
        return sellerLicensePath;
    }

    /**
     * setter sellerLicensePath(营业执照电子版图片路径)
     */
    public void setSellerLicensePath(String sellerLicensePath) {
        this.sellerLicensePath = sellerLicensePath;
    }

    /**
     * getter organizationCode(组织机构代码)
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * setter organizationCode(组织机构代码)
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    /**
     * getter organizationCodePath(组织机构代码电子版图片路径)
     */
    public String getOrganizationCodePath() {
        return organizationCodePath;
    }

    /**
     * setter organizationCodePath(组织机构代码电子版图片路径)
     */
    public void setOrganizationCodePath(String organizationCodePath) {
        this.organizationCodePath = organizationCodePath;
    }

    /**
     * getter businessType(证件类型（1.普通营业执照2.多证合一营业执照）)
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * setter businessType(证件类型（1.普通营业执照2.多证合一营业执照）)
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * getter socialCreditCode(统一社会信用代码)
     */
    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    /**
     * setter socialCreditCode(统一社会信用代码)
     */
    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    /**
     * getter socialCreditCodePath(多证合一营业执照)
     */
    public String getSocialCreditCodePath() {
        return socialCreditCodePath;
    }

    /**
     * setter socialCreditCodePath(多证合一营业执照)
     */
    public void setSocialCreditCodePath(String socialCreditCodePath) {
        this.socialCreditCodePath = socialCreditCodePath;
    }

    /**
     * getter openAnAccountLicense(开户许可证核准号)
     */
    public String getOpenAnAccountLicense() {
        return openAnAccountLicense;
    }

    /**
     * setter openAnAccountLicense(开户许可证核准号)
     */
    public void setOpenAnAccountLicense(String openAnAccountLicense) {
        this.openAnAccountLicense = openAnAccountLicense;
    }

    /**
     * getter openAnAccountLicensePath(开户许可证核准号电子版)
     */
    public String getOpenAnAccountLicensePath() {
        return openAnAccountLicensePath;
    }

    /**
     * setter openAnAccountLicensePath(开户许可证核准号电子版)
     */
    public void setOpenAnAccountLicensePath(String openAnAccountLicensePath) {
        this.openAnAccountLicensePath = openAnAccountLicensePath;
    }

    /**
     * getter storeType(店铺类型（1.普通店铺，2.旗舰店铺）)
     */
    public String getStoreType() {
        return storeType;
    }

    /**
     * setter storeType(店铺类型（1.普通店铺，2.旗舰店铺）)
     */
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    /**
     * getter storeBrand(店铺品牌名（多个按照，分割）)
     */
    public String getStoreBrand() {
        return storeBrand;
    }

    /**
     * setter storeBrand(店铺品牌名（多个按照，分割）)
     */
    public void setStoreBrand(String storeBrand) {
        this.storeBrand = storeBrand;
    }

    /**
     * getter storeBrandPath(店铺品牌商标证书path（多个按照，分割）)
     */
    public String getStoreBrandPath() {
        return storeBrandPath;
    }

    /**
     * setter storeBrandPath(店铺品牌商标证书path（多个按照，分割）)
     */
    public void setStoreBrandPath(String storeBrandPath) {
        this.storeBrandPath = storeBrandPath;
    }

    /**
     * getter contact(联系人)
     */
    public String getContact() {
        return contact;
    }

    /**
     * setter contact(联系人)
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * getter contactNumber(联系人电话)
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * setter contactNumber(联系人电话)
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * getter taxRegistrationNumber(税务登记证号)
     */
    public String getTaxRegistrationNumber() {
        return taxRegistrationNumber;
    }

    /**
     * setter taxRegistrationNumber(税务登记证号)
     */
    public void setTaxRegistrationNumber(String taxRegistrationNumber) {
        this.taxRegistrationNumber = taxRegistrationNumber;
    }

    /**
     * getter legalName(法人姓名)
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * setter legalName(法人姓名)
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    /**
     * getter taxRegistrationNumberPath(税务登记，税务登记证号电子版path)
     */
    public String getTaxRegistrationNumberPath() {
        return taxRegistrationNumberPath;
    }

    /**
     * setter taxRegistrationNumberPath(税务登记，税务登记证号电子版path)
     */
    public void setTaxRegistrationNumberPath(String taxRegistrationNumberPath) {
        this.taxRegistrationNumberPath = taxRegistrationNumberPath;
    }

    /**
     * getter legalIdCardCode(法人身份证号)
     */
    public String getLegalIdCardCode() {
        return legalIdCardCode;
    }

    /**
     * setter legalIdCardCode(法人身份证号)
     */
    public void setLegalIdCardCode(String legalIdCardCode) {
        this.legalIdCardCode = legalIdCardCode;
    }

    /**
     * getter storeName(店铺名（注意店铺设置修改的时候记得把这个修改了）)
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * setter storeName(店铺名（注意店铺设置修改的时候记得把这个修改了）)
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * getter legalIdCardCodePositive(法人身份证号（正面）)
     */
    public String getLegalIdCardCodePositive() {
        return legalIdCardCodePositive;
    }

    /**
     * setter legalIdCardCodePositive(法人身份证号（正面）)
     */
    public void setLegalIdCardCodePositive(String legalIdCardCodePositive) {
        this.legalIdCardCodePositive = legalIdCardCodePositive;
    }

    /**
     * getter legalIdCardCodeOpposite(法人身份证号（反面）)
     */
    public String getLegalIdCardCodeOpposite() {
        return legalIdCardCodeOpposite;
    }

    /**
     * setter legalIdCardCodeOpposite(法人身份证号（反面）)
     */
    public void setLegalIdCardCodeOpposite(String legalIdCardCodeOpposite) {
        this.legalIdCardCodeOpposite = legalIdCardCodeOpposite;
    }

    /**
     * getter levelId(店铺等级（关联店铺等级id）)
     */
    public Long getLevelId() {
        return levelId;
    }

    /**
     * setter levelId(店铺等级（关联店铺等级id）)
     */
    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    /**
     * getter industryId(主营行业（关联主营行业id）)
     */
    public Long getIndustryId() {
        return industryId;
    }

    /**
     * setter industryId(主营行业（关联主营行业id）)
     */
    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    /**
     * getter summaryOfCoping(应付总金额)
     */
    public BigDecimal getSummaryOfCoping() {
        return summaryOfCoping;
    }

    /**
     * setter summaryOfCoping(应付总金额)
     */
    public void setSummaryOfCoping(BigDecimal summaryOfCoping) {
        this.summaryOfCoping = summaryOfCoping;
    }

    /**
     * getter paymentVoucherPath(商家上传付款凭证path)
     */
    public String getPaymentVoucherPath() {
        return paymentVoucherPath;
    }

    /**
     * setter paymentVoucherPath(商家上传付款凭证path)
     */
    public void setPaymentVoucherPath(String paymentVoucherPath) {
        this.paymentVoucherPath = paymentVoucherPath;
    }

    /**
     * getter paymentInstructions(商家付款凭证说明)
     */
    public String getPaymentInstructions() {
        return paymentInstructions;
    }

    /**
     * setter paymentInstructions(商家付款凭证说明)
     */
    public void setPaymentInstructions(String paymentInstructions) {
        this.paymentInstructions = paymentInstructions;
    }

    /**
     * getter oneAuditOpinion(一审审核意见)
     */
    public String getOneAuditOpinion() {
        return oneAuditOpinion;
    }

    /**
     * setter oneAuditOpinion(一审审核意见)
     */
    public void setOneAuditOpinion(String oneAuditOpinion) {
        this.oneAuditOpinion = oneAuditOpinion;
    }

    /**
     * getter twoAuditOpinion(二审审核意见)
     */
    public String getTwoAuditOpinion() {
        return twoAuditOpinion;
    }

    /**
     * setter twoAuditOpinion(二审审核意见)
     */
    public void setTwoAuditOpinion(String twoAuditOpinion) {
        this.twoAuditOpinion = twoAuditOpinion;
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
     * getter auditOpinion(入驻信息审核意见)
     */
    public String getAuditOpinion() {
        return auditOpinion;
    }

    /**
     * setter auditOpinion(入驻信息审核意见)
     */
    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
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