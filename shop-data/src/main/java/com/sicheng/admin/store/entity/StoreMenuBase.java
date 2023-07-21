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
package com.sicheng.admin.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.TreeEntity;

import java.util.Date;

/**
 * 店铺菜单 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-02-17
 */
public class StoreMenuBase<T> extends TreeEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long menuId;                    // 主键
    private String href;                    // 链接
    private String target;                  // 目标
    private String icon;                    // 图标
    private String isShow;                  // 是否在菜单中显示（0隐藏、1显示）
    private String permission;              // 权限标识
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
    private String menuNum;                 // 编号
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public StoreMenuBase() {
        super();
    }

    public StoreMenuBase(Long id) {
        super(id);
        this.menuId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return menuId;
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
        this.menuId = id;
    }

    /**
     * getter menuId(主键)
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * setter menuId(主键)
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * getter href(链接)
     */
    public String getHref() {
        return href;
    }

    /**
     * setter href(链接)
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * getter target(目标)
     */
    public String getTarget() {
        return target;
    }

    /**
     * setter target(目标)
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * getter icon(图标)
     */
    public String getIcon() {
        return icon;
    }

    /**
     * setter icon(图标)
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * getter isShow(是否在菜单中显示（0隐藏、1显示）)
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * setter isShow(是否在菜单中显示（0隐藏、1显示）)
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    /**
     * getter permission(权限标识)
     */
    public String getPermission() {
        return permission;
    }

    /**
     * setter permission(权限标识)
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * getter bak1(备用字段)
     */
    public String getBak1() {
        return bak1;
    }

    /**
     * setter bak1(备用字段)
     */
    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    /**
     * getter bak2(备用字段)
     */
    public String getBak2() {
        return bak2;
    }

    /**
     * setter bak2(备用字段)
     */
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    /**
     * getter bak3(备用字段)
     */
    public String getBak3() {
        return bak3;
    }

    /**
     * setter bak3(备用字段)
     */
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }

    /**
     * getter bak4(备用字段)
     */
    public String getBak4() {
        return bak4;
    }

    /**
     * setter bak4(备用字段)
     */
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }

    /**
     * getter bak5(备用字段)
     */
    public String getBak5() {
        return bak5;
    }

    /**
     * setter bak5(备用字段)
     */
    public void setBak5(String bak5) {
        this.bak5 = bak5;
    }

    /**
     * getter menuNum(编号)
     */
    public String getMenuNum() {
        return menuNum;
    }

    /**
     * setter menuNum(编号)
     */
    public void setMenuNum(String menuNum) {
        this.menuNum = menuNum;
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