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
 * 卖家角色 Entity 父类
 *
 * @author cl
 * @version 2017-08-02
 */
public class StoreRoleBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long roleId;                    // 主键
    private Long storeId;                   // 店铺id
    private String roleName;                // 角色名称
    private String enname;                  // 英文名称
    private String roleType;                // 角色类型
    private String dataScope;               // 数据范围（0：所有数据；1：所在公司及以下数据；2：所在公司数据；3：所在部门及以下数据；4：所在部门数据；8：仅本人数据；9：按明细设置）
    private String isSys;                   // 是否系统数据(0否、1是)
    private String useable;                 // 是否可用，0否、1是
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

    public StoreRoleBase() {
        super();
    }

    public StoreRoleBase(Long id) {
        super(id);
        this.roleId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return roleId;
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
        this.roleId = id;
    }

    /**
     * getter roleId(主键)
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * setter roleId(主键)
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * getter storeId(店铺id)
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * setter storeId(店铺id)
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * getter roleName(角色名称)
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * setter roleName(角色名称)
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * getter enname(英文名称)
     */
    public String getEnname() {
        return enname;
    }

    /**
     * setter enname(英文名称)
     */
    public void setEnname(String enname) {
        this.enname = enname;
    }

    /**
     * getter roleType(角色类型)
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * setter roleType(角色类型)
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * getter dataScope(数据范围（0：所有数据；1：所在公司及以下数据；2：所在公司数据；3：所在部门及以下数据；4：所在部门数据；8：仅本人数据；9：按明细设置）)
     */
    public String getDataScope() {
        return dataScope;
    }

    /**
     * setter dataScope(数据范围（0：所有数据；1：所在公司及以下数据；2：所在公司数据；3：所在部门及以下数据；4：所在部门数据；8：仅本人数据；9：按明细设置）)
     */
    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    /**
     * getter isSys(是否系统数据(0否、1是))
     */
    public String getIsSys() {
        return isSys;
    }

    /**
     * setter isSys(是否系统数据(0否、1是))
     */
    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }

    /**
     * getter useable(是否可用，0否、1是)
     */
    public String getUseable() {
        return useable;
    }

    /**
     * setter useable(是否可用，0否、1是)
     */
    public void setUseable(String useable) {
        this.useable = useable;
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