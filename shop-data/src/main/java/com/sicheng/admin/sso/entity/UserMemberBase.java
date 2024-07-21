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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员（买家） Entity 父类
 *
 * @author cl
 * @version 2018-01-06
 */
public class UserMemberBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long uId;                       // 主键
    private String point;                   // 积分（积分规则设置送的积分数）
    private String isBuy;                   // 是否允许购买(0不允许、1允许)
    private Date notBuyDateStart;           // 禁止购买日期（开始时间）
    private Date notBuyDateEnd;             // 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
    private BigDecimal balance;             // 预存款，账户余额
    private BigDecimal frozenMoney;         // 冻结金额
    private String paymentPassword;         // 支付密码
    private Long memberTagId;               // 会员标签（给会员打标）(关联member_tag表)
    private String realName;                // 真实姓名
    private String headPicPath;             // 会员头像path
    private String sex;                     // 性别(1男、2女、3保密)
    private Date birthday;                  // 生日
    private String postcode;                // 邮编
    private Long countryId;                 // 国家(关联地区表)
    private String countryName;             // 国家名字
    private Long provinceId;                // 省(关联地区表)
    private String provinceName;            // 省名字
    private Long cityId;                    // 市(关联地区表)
    private String cityName;                // 市名字
    private Long districtId;                // 县(关联地区表)
    private String districtName;            // 县名字
    private String detailedAddress;         // 详细地址
    private String qq;                      // qq号
    private String microblog;               // 微博
    private String weChat;                  // 微信
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
    private String identityType;            // 1.普通用户,2.车主用户
    private String applyCarIds;             // 车主用户使用车型id,号分割
    private String openId;                  // 微信授权的openId
    private Date beginNotBuyDateStart;      // 开始 禁止购买日期（开始时间）
    private Date endNotBuyDateStart;        // 结束 禁止购买日期（开始时间）
    private Date beginNotBuyDateEnd;        // 开始 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
    private Date endNotBuyDateEnd;          // 结束 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
    private Date beginBirthday;             // 开始 生日
    private Date endBirthday;               // 结束 生日
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public UserMemberBase() {
        super();
    }

    public UserMemberBase(Long id) {
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
     * getter uId(主键)
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(主键)
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter point(积分（积分规则设置送的积分数）)
     */
    public String getPoint() {
        return point;
    }

    /**
     * setter point(积分（积分规则设置送的积分数）)
     */
    public void setPoint(String point) {
        this.point = point;
    }

    /**
     * getter isBuy(是否允许购买(0不允许、1允许))
     */
    public String getIsBuy() {
        return isBuy;
    }

    /**
     * setter isBuy(是否允许购买(0不允许、1允许))
     */
    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter notBuyDateStart(禁止购买日期（开始时间）)
     */
    public Date getNotBuyDateStart() {
        return notBuyDateStart;
    }

    /**
     * setter notBuyDateStart(禁止购买日期（开始时间）)
     */
    public void setNotBuyDateStart(Date notBuyDateStart) {
        this.notBuyDateStart = notBuyDateStart;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter notBuyDateEnd(禁止购买日期（结束时间）(需要有定时程序来检查并解锁))
     */
    public Date getNotBuyDateEnd() {
        return notBuyDateEnd;
    }

    /**
     * setter notBuyDateEnd(禁止购买日期（结束时间）(需要有定时程序来检查并解锁))
     */
    public void setNotBuyDateEnd(Date notBuyDateEnd) {
        this.notBuyDateEnd = notBuyDateEnd;
    }

    /**
     * getter balance(预存款，账户余额)
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * setter balance(预存款，账户余额)
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * getter frozenMoney(冻结金额)
     */
    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    /**
     * setter frozenMoney(冻结金额)
     */
    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    /**
     * getter paymentPassword(支付密码)
     */
    public String getPaymentPassword() {
        return paymentPassword;
    }

    /**
     * setter paymentPassword(支付密码)
     */
    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    /**
     * getter memberTagId(会员标签（给会员打标）(关联member_tag表))
     */
    public Long getMemberTagId() {
        return memberTagId;
    }

    /**
     * setter memberTagId(会员标签（给会员打标）(关联member_tag表))
     */
    public void setMemberTagId(Long memberTagId) {
        this.memberTagId = memberTagId;
    }

    /**
     * getter realName(真实姓名)
     */
    public String getRealName() {
        return realName;
    }

    /**
     * setter realName(真实姓名)
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * getter headPicPath(会员头像path)
     */
    public String getHeadPicPath() {
        return headPicPath;
    }

    /**
     * setter headPicPath(会员头像path)
     */
    public void setHeadPicPath(String headPicPath) {
        this.headPicPath = headPicPath;
    }

    /**
     * getter sex(性别(1男、2女、3保密))
     */
    public String getSex() {
        return sex;
    }

    /**
     * setter sex(性别(1男、2女、3保密))
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter birthday(生日)
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * setter birthday(生日)
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * getter postcode(邮编)
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * setter postcode(邮编)
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
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
     * getter qq(qq号)
     */
    public String getQq() {
        return qq;
    }

    /**
     * setter qq(qq号)
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * getter microblog(微博)
     */
    public String getMicroblog() {
        return microblog;
    }

    /**
     * setter microblog(微博)
     */
    public void setMicroblog(String microblog) {
        this.microblog = microblog;
    }

    /**
     * getter weChat(微信)
     */
    public String getWeChat() {
        return weChat;
    }

    /**
     * setter weChat(微信)
     */
    public void setWeChat(String weChat) {
        this.weChat = weChat;
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
     * getter identityType(1.普通用户,2.车主用户)
     */
    public String getIdentityType() {
        return identityType;
    }

    /**
     * setter identityType(1.普通用户,2.车主用户)
     */
    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    /**
     * getter applyCarIds(车主用户使用车型id,号分割)
     */
    public String getApplyCarIds() {
        return applyCarIds;
    }

    /**
     * setter applyCarIds(车主用户使用车型id,号分割)
     */
    public void setApplyCarIds(String applyCarIds) {
        this.applyCarIds = applyCarIds;
    }

    /**
     * getter openId(微信授权的openId)
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * setter openId(微信授权的openId)
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * getter notBuyDateStart(禁止购买日期（开始时间）)
     */
    public Date getBeginNotBuyDateStart() {
        return beginNotBuyDateStart;
    }

    /**
     * setter notBuyDateStart(禁止购买日期（开始时间）)
     */
    public void setBeginNotBuyDateStart(Date beginNotBuyDateStart) {
        this.beginNotBuyDateStart = beginNotBuyDateStart;
    }

    /**
     * getter notBuyDateStart(禁止购买日期（开始时间）)
     */
    public Date getEndNotBuyDateStart() {
        return endNotBuyDateStart;
    }

    /**
     * setter notBuyDateStart(禁止购买日期（开始时间）)
     */
    public void setEndNotBuyDateStart(Date endNotBuyDateStart) {
        this.endNotBuyDateStart = endNotBuyDateStart;
    }

    /**
     * getter notBuyDateEnd(禁止购买日期（结束时间）(需要有定时程序来检查并解锁))
     */
    public Date getBeginNotBuyDateEnd() {
        return beginNotBuyDateEnd;
    }

    /**
     * setter notBuyDateEnd(禁止购买日期（结束时间）(需要有定时程序来检查并解锁))
     */
    public void setBeginNotBuyDateEnd(Date beginNotBuyDateEnd) {
        this.beginNotBuyDateEnd = beginNotBuyDateEnd;
    }

    /**
     * getter notBuyDateEnd(禁止购买日期（结束时间）(需要有定时程序来检查并解锁))
     */
    public Date getEndNotBuyDateEnd() {
        return endNotBuyDateEnd;
    }

    /**
     * setter notBuyDateEnd(禁止购买日期（结束时间）(需要有定时程序来检查并解锁))
     */
    public void setEndNotBuyDateEnd(Date endNotBuyDateEnd) {
        this.endNotBuyDateEnd = endNotBuyDateEnd;
    }

    /**
     * getter birthday(生日)
     */
    public Date getBeginBirthday() {
        return beginBirthday;
    }

    /**
     * setter birthday(生日)
     */
    public void setBeginBirthday(Date beginBirthday) {
        this.beginBirthday = beginBirthday;
    }

    /**
     * getter birthday(生日)
     */
    public Date getEndBirthday() {
        return endBirthday;
    }

    /**
     * setter birthday(生日)
     */
    public void setEndBirthday(Date endBirthday) {
        this.endBirthday = endBirthday;
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