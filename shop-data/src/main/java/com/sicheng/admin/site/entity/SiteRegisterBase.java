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
package com.sicheng.admin.site.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

/**
 * 注册设置 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-05-23
 */
public class SiteRegisterBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String isRegister;              // 是否开放注册
    private Integer usernameMax;            // 用户名最大长度
    private Integer usernameMin;            // 用户名最小长度
    private Integer pwdMax;                 // 密码最大长度
    private Integer pwdMin;                 // 密码最小长度
    private String disableUsername;         // 禁用用户名
    private String agreement;               // 注册协议
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

    public SiteRegisterBase() {
        super();
    }

    public SiteRegisterBase(Long id) {
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
     * getter isRegister(是否开放注册)
     */
    public String getIsRegister() {
        return isRegister;
    }

    /**
     * setter isRegister(是否开放注册)
     */
    public void setIsRegister(String isRegister) {
        this.isRegister = isRegister;
    }

    /**
     * getter usernameMax(用户名最大长度)
     */
    public Integer getUsernameMax() {
        return usernameMax;
    }

    /**
     * setter usernameMax(用户名最大长度)
     */
    public void setUsernameMax(Integer usernameMax) {
        this.usernameMax = usernameMax;
    }

    /**
     * getter usernameMin(用户名最小长度)
     */
    public Integer getUsernameMin() {
        return usernameMin;
    }

    /**
     * setter usernameMin(用户名最小长度)
     */
    public void setUsernameMin(Integer usernameMin) {
        this.usernameMin = usernameMin;
    }

    /**
     * getter pwdMax(密码最大长度)
     */
    public Integer getPwdMax() {
        return pwdMax;
    }

    /**
     * setter pwdMax(密码最大长度)
     */
    public void setPwdMax(Integer pwdMax) {
        this.pwdMax = pwdMax;
    }

    /**
     * getter pwdMin(密码最小长度)
     */
    public Integer getPwdMin() {
        return pwdMin;
    }

    /**
     * setter pwdMin(密码最小长度)
     */
    public void setPwdMin(Integer pwdMin) {
        this.pwdMin = pwdMin;
    }

    /**
     * getter disableUsername(禁用用户名)
     */
    public String getDisableUsername() {
        return disableUsername;
    }

    /**
     * setter disableUsername(禁用用户名)
     */
    public void setDisableUsername(String disableUsername) {
        this.disableUsername = disableUsername;
    }

    /**
     * getter agreement(注册协议)
     */
    public String getAgreement() {
        return agreement;
    }

    /**
     * setter agreement(注册协议)
     */
    public void setAgreement(String agreement) {
        this.agreement = agreement;
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