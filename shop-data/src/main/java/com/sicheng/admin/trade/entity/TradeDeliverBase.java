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
package com.sicheng.admin.trade.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

/**
 * 发票 Entity 父类
 *
 * @author 范秀秀
 * @version 2017-08-23
 */
public class TradeDeliverBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long deliverId;                 // 主键
    private Long uId;                       // 会员id(会员表Id)
    private String deliverType;             // 发票类型：1普通发票，2增值税普通发票，3增值税专用发票
    private String deliverTitle;            // 发票抬头
    private String deliverContent;          // 发票内容
    private String hbjbuyer;                // 默认，0否、1是
    private String companyName;             // 公司名称
    private String axpayerIdentityNumber;   // 纳税人识别号
    private String openingBank;             // 开户行
    private String accountNumber;           // 账号
    private String address;                 // 地址
    private String phone;                   // 电话
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

    public TradeDeliverBase() {
        super();
    }

    public TradeDeliverBase(Long id) {
        super(id);
        this.deliverId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return deliverId;
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
        this.deliverId = id;
    }

    /**
     * getter deliverId(主键)
     */
    public Long getDeliverId() {
        return deliverId;
    }

    /**
     * setter deliverId(主键)
     */
    public void setDeliverId(Long deliverId) {
        this.deliverId = deliverId;
    }

    /**
     * getter uId(会员id(会员表Id))
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(会员id(会员表Id))
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter deliverType(发票类型：1普通发票，2增值税普通发票，3增值税专用发票)
     */
    public String getDeliverType() {
        return deliverType;
    }

    /**
     * setter deliverType(发票类型：1普通发票，2增值税普通发票，3增值税专用发票)
     */
    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    /**
     * getter deliverTitle(发票抬头)
     */
    public String getDeliverTitle() {
        return deliverTitle;
    }

    /**
     * setter deliverTitle(发票抬头)
     */
    public void setDeliverTitle(String deliverTitle) {
        this.deliverTitle = deliverTitle;
    }

    /**
     * getter deliverContent(发票内容)
     */
    public String getDeliverContent() {
        return deliverContent;
    }

    /**
     * setter deliverContent(发票内容)
     */
    public void setDeliverContent(String deliverContent) {
        this.deliverContent = deliverContent;
    }

    /**
     * getter hbjbuyer(默认，0否、1是)
     */
    public String getHbjbuyer() {
        return hbjbuyer;
    }

    /**
     * setter hbjbuyer(默认，0否、1是)
     */
    public void setHbjbuyer(String hbjbuyer) {
        this.hbjbuyer = hbjbuyer;
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
     * getter axpayerIdentityNumber(纳税人识别号)
     */
    public String getAxpayerIdentityNumber() {
        return axpayerIdentityNumber;
    }

    /**
     * setter axpayerIdentityNumber(纳税人识别号)
     */
    public void setAxpayerIdentityNumber(String axpayerIdentityNumber) {
        this.axpayerIdentityNumber = axpayerIdentityNumber;
    }

    /**
     * getter openingBank(开户行)
     */
    public String getOpeningBank() {
        return openingBank;
    }

    /**
     * setter openingBank(开户行)
     */
    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    /**
     * getter accountNumber(账号)
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * setter accountNumber(账号)
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * getter address(地址)
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter address(地址)
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getter phone(电话)
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setter phone(电话)
     */
    public void setPhone(String phone) {
        this.phone = phone;
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


}