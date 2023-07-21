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
 * 接口参数 Entity 父类
 *
 * @author fanxiuxiu
 * @version 2018-02-28
 */
public class SysApiParamBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long paramId;                   // 参数id
    private Long apiId;                     // 所属接口id
    private String paramName;               // 参数名
    private String paramType;               // 参数类型
    private String isRequired;              // 是否必填
    private String paramDescribe;           // 参数描述
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SysApiParamBase() {
        super();
    }

    public SysApiParamBase(Long id) {
        super(id);
        this.paramId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return paramId;
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
        this.paramId = id;
    }

    /**
     * getter paramId(参数id)
     */
    public Long getParamId() {
        return paramId;
    }

    /**
     * setter paramId(参数id)
     */
    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    /**
     * getter apiId(所属接口id)
     */
    public Long getApiId() {
        return apiId;
    }

    /**
     * setter apiId(所属接口id)
     */
    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    /**
     * getter paramName(参数名)
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * setter paramName(参数名)
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * getter paramType(参数类型)
     */
    public String getParamType() {
        return paramType;
    }

    /**
     * setter paramType(参数类型)
     */
    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    /**
     * getter isRequired(是否必填)
     */
    public String getIsRequired() {
        return isRequired;
    }

    /**
     * setter isRequired(是否必填)
     */
    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * getter paramDescribe(参数描述)
     */
    public String getParamDescribe() {
        return paramDescribe;
    }

    /**
     * setter paramDescribe(参数描述)
     */
    public void setParamDescribe(String paramDescribe) {
        this.paramDescribe = paramDescribe;
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