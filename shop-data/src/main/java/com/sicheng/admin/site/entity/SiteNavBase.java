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
package com.sicheng.admin.site.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 管理页面的导航 Entity 父类
 *
 * @author zjl
 * @version 2017-02-06
 */
public class SiteNavBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Integer sort;                   // 排序
    private String name;                    // 导航名称
    private String location;                // 显示位置(1顶部导航、2中部导航、3底部导航)
    private String isNewWindow;             // 是否新窗口导航(0否、1是)
    private String isOpen;                  // 是否启用(0禁用、1启用)
    private String action;                  // 导航动作(1搜索关键字、2打开商品、3打开店铺、4打开商品分类)
    private String target;                  // 目标URL，目标ID
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

    public SiteNavBase() {
        super();
    }

    public SiteNavBase(Long id) {
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
     * getter sort(排序)
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * setter sort(排序)
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * getter name(导航名称)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(导航名称)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter location(显示位置(1顶部导航、2中部导航、3底部导航))
     */
    public String getLocation() {
        return location;
    }

    /**
     * setter location(显示位置(1顶部导航、2中部导航、3底部导航))
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * getter isNewWindow(是否新窗口导航(0否、1是))
     */
    public String getIsNewWindow() {
        return isNewWindow;
    }

    /**
     * setter isNewWindow(是否新窗口导航(0否、1是))
     */
    public void setIsNewWindow(String isNewWindow) {
        this.isNewWindow = isNewWindow;
    }

    /**
     * getter isOpen(是否启用(0禁用、1启用))
     */
    public String getIsOpen() {
        return isOpen;
    }

    /**
     * setter isOpen(是否启用(0禁用、1启用))
     */
    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * getter action(导航动作(1搜索关键字、2打开商品、3打开店铺、4打开商品分类))
     */
    public String getAction() {
        return action;
    }

    /**
     * setter action(导航动作(1搜索关键字、2打开商品、3打开店铺、4打开商品分类))
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * getter target(目标URL，目标ID)
     */
    public String getTarget() {
        return target;
    }

    /**
     * setter target(目标URL，目标ID)
     */
    public void setTarget(String target) {
        this.target = target;
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