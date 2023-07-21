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
package com.sicheng.admin.sys.entity;

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 接口 Entity 父类
 *
 * @author fanxiuxiu
 * @version 2018-02-28
 */
public class SysApiBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long apiId;                     // 接口id
    private String apiCategory;             // 所属分类
    private String apiName;                 // 接口名
    private String apiVersion;              // 接口版本号
    private String apiDescribe;             // 接口描述
    private String apiUrl;                  // 接口地址
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SysApiBase() {
        super();
    }

    public SysApiBase(Long id) {
        super(id);
        this.apiId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return apiId;
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
        this.apiId = id;
    }

    /**
     * getter apiId(接口id)
     */
    public Long getApiId() {
        return apiId;
    }

    /**
     * setter apiId(接口id)
     */
    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    /**
     * getter apiCategory(所属分类)
     */
    public String getApiCategory() {
        return apiCategory;
    }

    /**
     * setter apiCategory(所属分类)
     */
    public void setApiCategory(String apiCategory) {
        this.apiCategory = apiCategory;
    }

    /**
     * getter apiName(接口名)
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * setter apiName(接口名)
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * getter apiVersion(接口版本号)
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * setter apiVersion(接口版本号)
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * getter apiDescribe(接口描述)
     */
    public String getApiDescribe() {
        return apiDescribe;
    }

    /**
     * setter apiDescribe(接口描述)
     */
    public void setApiDescribe(String apiDescribe) {
        this.apiDescribe = apiDescribe;
    }

    /**
     * getter apiUrl(接口地址)
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * setter apiUrl(接口地址)
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
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