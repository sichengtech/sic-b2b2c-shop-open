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

import java.util.Date;

/**
 * 会员总表 Entity 父类
 *
 * @author cl
 * @version 2017-05-10
 */
public class UserMainBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long uId;                       // 主键,会员的ID
    private Long parentUid;                 // 父ID，为0表示是主账号
    private String loginName;               // 用户名、登录名
    @JsonIgnore
    private String password;                // 密码
    @JsonIgnore
    private String salt;                    // 盐
    private String isLocked;                // 是否锁定(0否，1是)锁定后不能登录
    private String email;                   // 邮箱
    private String emailValidate;           // 邮箱是否通过验证(0否，1是)
    private String mobile;                  // 手机号
    private String mobileValidate;          // 手机号是否通过验证(0否，1是)
    private String typeUser;                // 用户类型（个人买家、采购商、门店服务商、卖家）
    private String typeAccount;             // 账号类型 (1主账号,2子账号)
    private String registerIp;              // 注册ip
    private Date registerDate;              // 注册日期
    private String loginIp;                 // 最后登录ip
    private Date loginDate;                 // 最后登录日期
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
    private String modifyDate;              // 用户名最后一次变更年月（格式：2017-02）
    private Integer modifyCount;            // 用户名变更次数(限值在fdp配置文件去配置，每自然月可变更N次)
    private Date beginRegisterDate;         // 开始 注册日期
    private Date endRegisterDate;           // 结束 注册日期
    private Date beginLoginDate;            // 开始 最后登录日期
    private Date endLoginDate;              // 结束 最后登录日期

    public UserMainBase() {
        super();
    }

    public UserMainBase(Long id) {
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
     * getter parentUid(父ID，为0表示是主账号)
     */
    public Long getParentUid() {
        return parentUid;
    }

    /**
     * setter parentUid(父ID，为0表示是主账号)
     */
    public void setParentUid(Long parentUid) {
        this.parentUid = parentUid;
    }

    /**
     * getter loginName(用户名、登录名)
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * setter loginName(用户名、登录名)
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * getter password(密码)
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter password(密码)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter salt(盐)
     */
    public String getSalt() {
        return salt;
    }

    /**
     * setter salt(盐)
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * getter isLocked(是否锁定(0否，1是)锁定后不能登录)
     */
    public String getIsLocked() {
        return isLocked;
    }

    /**
     * setter isLocked(是否锁定(0否，1是)锁定后不能登录)
     */
    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * getter email(邮箱)
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter email(邮箱)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter emailValidate(邮箱是否通过验证(0否，1是))
     */
    public String getEmailValidate() {
        return emailValidate;
    }

    /**
     * setter emailValidate(邮箱是否通过验证(0否，1是))
     */
    public void setEmailValidate(String emailValidate) {
        this.emailValidate = emailValidate;
    }

    /**
     * getter mobile(手机号)
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * setter mobile(手机号)
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * getter mobileValidate(手机号是否通过验证(0否，1是))
     */
    public String getMobileValidate() {
        return mobileValidate;
    }

    /**
     * setter mobileValidate(手机号是否通过验证(0否，1是))
     */
    public void setMobileValidate(String mobileValidate) {
        this.mobileValidate = mobileValidate;
    }

    /**
     * getter typeUser(用户类型（个人买家、采购商、门店服务商、卖家）)
     */
    public String getTypeUser() {
        return typeUser;
    }

    /**
     * setter typeUser(用户类型（个人买家、采购商、门店服务商、卖家）)
     */
    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    /**
     * getter typeAccount(账号类型 (1主账号,2子账号))
     */
    public String getTypeAccount() {
        return typeAccount;
    }

    /**
     * setter typeAccount(账号类型 (1主账号,2子账号))
     */
    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    /**
     * getter registerIp(注册ip)
     */
    public String getRegisterIp() {
        return registerIp;
    }

    /**
     * setter registerIp(注册ip)
     */
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter registerDate(注册日期)
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * setter registerDate(注册日期)
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * getter loginIp(最后登录ip)
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * setter loginIp(最后登录ip)
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter loginDate(最后登录日期)
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * setter loginDate(最后登录日期)
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
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
     * getter modifyDate(用户名最后一次变更年月（格式：2017-02）)
     */
    public String getModifyDate() {
        return modifyDate;
    }

    /**
     * setter modifyDate(用户名最后一次变更年月（格式：2017-02）)
     */
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * getter modifyCount(用户名变更次数(限值在fdp配置文件去配置，每自然月可变更N次))
     */
    public Integer getModifyCount() {
        return modifyCount;
    }

    /**
     * setter modifyCount(用户名变更次数(限值在fdp配置文件去配置，每自然月可变更N次))
     */
    public void setModifyCount(Integer modifyCount) {
        this.modifyCount = modifyCount;
    }

    /**
     * getter registerDate(注册日期)
     */
    public Date getBeginRegisterDate() {
        return beginRegisterDate;
    }

    /**
     * setter registerDate(注册日期)
     */
    public void setBeginRegisterDate(Date beginRegisterDate) {
        this.beginRegisterDate = beginRegisterDate;
    }

    /**
     * getter registerDate(注册日期)
     */
    public Date getEndRegisterDate() {
        return endRegisterDate;
    }

    /**
     * setter registerDate(注册日期)
     */
    public void setEndRegisterDate(Date endRegisterDate) {
        this.endRegisterDate = endRegisterDate;
    }

    /**
     * getter loginDate(最后登录日期)
     */
    public Date getBeginLoginDate() {
        return beginLoginDate;
    }

    /**
     * setter loginDate(最后登录日期)
     */
    public void setBeginLoginDate(Date beginLoginDate) {
        this.beginLoginDate = beginLoginDate;
    }

    /**
     * getter loginDate(最后登录日期)
     */
    public Date getEndLoginDate() {
        return endLoginDate;
    }

    /**
     * setter loginDate(最后登录日期)
     */
    public void setEndLoginDate(Date endLoginDate) {
        this.endLoginDate = endLoginDate;
    }

}