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

import java.util.Date;

/**
 * 店铺管理员操作日志 Entity 父类
 *
 * @author cl
 * @version 2017-04-11
 */
public class StoreAdminLogBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long logId;                     // 主键
    private Long storeId;                   // 关联(店铺表)
    private String type;                    // 日志类型
    private String title;                   // 日志标题（操作菜单）
    private String remoteAddr;              // 操作用户的IP地址
    private String userAgent;               // 操作用户代理信息
    private String requestUri;              // 操作的URI
    private String method;                  // 操作的方式(提交方式)
    private String params;                  // 操作提交的数据
    private String exception;               // 异常信息
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
    private Long operatorId;                // 操作者id(登录的店铺会员id)
    private Date beginCreateDate;           // 开始 创建时间（操作时间）
    private Date endCreateDate;             // 结束 创建时间（操作时间）
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public StoreAdminLogBase() {
        super();
    }

    public StoreAdminLogBase(Long id) {
        super(id);
        this.logId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return logId;
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
        this.logId = id;
    }

    /**
     * getter logId(主键)
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * setter logId(主键)
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * getter storeId(关联(店铺表))
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * setter storeId(关联(店铺表))
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * getter type(日志类型)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(日志类型)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter title(日志标题（操作菜单）)
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter title(日志标题（操作菜单）)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter remoteAddr(操作用户的IP地址)
     */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
     * setter remoteAddr(操作用户的IP地址)
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    /**
     * getter userAgent(操作用户代理信息)
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * setter userAgent(操作用户代理信息)
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * getter requestUri(操作的URI)
     */
    public String getRequestUri() {
        return requestUri;
    }

    /**
     * setter requestUri(操作的URI)
     */
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    /**
     * getter method(操作的方式(提交方式))
     */
    public String getMethod() {
        return method;
    }

    /**
     * setter method(操作的方式(提交方式))
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * getter params(操作提交的数据)
     */
    public String getParams() {
        return params;
    }

    /**
     * setter params(操作提交的数据)
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * getter exception(异常信息)
     */
    public String getException() {
        return exception;
    }

    /**
     * setter exception(异常信息)
     */
    public void setException(String exception) {
        this.exception = exception;
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
     * getter operatorId(操作者id(登录的店铺会员id))
     */
    public Long getOperatorId() {
        return operatorId;
    }

    /**
     * setter operatorId(操作者id(登录的店铺会员id))
     */
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * getter createDate(创建时间（操作时间）)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(创建时间（操作时间）)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(创建时间（操作时间）)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(创建时间（操作时间）)
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