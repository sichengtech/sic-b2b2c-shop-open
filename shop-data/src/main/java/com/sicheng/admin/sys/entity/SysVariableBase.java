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
package com.sicheng.admin.sys.entity;

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 系统变量管理 Entity 父类
 *
 * @author zjl
 * @version 2017-10-12
 */
public class SysVariableBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long varId;                     // 主键
    private String name;                    // 变量名有唯一约束不可重名，就像map的key一样。用于通过name获取value
    private String value;                   // 变量值
    private String valueClob;               // 变量值，用于存储clob类型值
    private String bewrite;                 // 描述，说明这个变量有什么作用
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SysVariableBase() {
        super();
    }

    public SysVariableBase(Long id) {
        super(id);
        this.varId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return varId;
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
        this.varId = id;
    }

    /**
     * getter varId(主键)
     */
    public Long getVarId() {
        return varId;
    }

    /**
     * setter varId(主键)
     */
    public void setVarId(Long varId) {
        this.varId = varId;
    }

    /**
     * getter name(变量名有唯一约束不可重名，就像map的key一样。用于通过name获取value)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(变量名有唯一约束不可重名，就像map的key一样。用于通过name获取value)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter value(变量值)
     */
    public String getValue() {
        return value;
    }

    /**
     * setter value(变量值)
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * getter valueClob(变量值，用于存储clob类型值)
     */
    public String getValueClob() {
        return valueClob;
    }

    /**
     * setter valueClob(变量值，用于存储clob类型值)
     */
    public void setValueClob(String valueClob) {
        this.valueClob = valueClob;
    }

    /**
     * getter bewrite(描述，说明这个变量有什么作用)
     */
    public String getBewrite() {
        return bewrite;
    }

    /**
     * setter bewrite(描述，说明这个变量有什么作用)
     */
    public void setBewrite(String bewrite) {
        this.bewrite = bewrite;
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