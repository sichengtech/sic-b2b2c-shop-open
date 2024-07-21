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
package com.sicheng.admin.cms.entity;

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 站点表 Entity 父类
 *
 * @author cl
 * @version 2017-02-09
 */
public class SiteBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String name;                    // 站点名称
    private String title;                   // 站点标题
    private String logo;                    // 站点Logo
    private String domain;                  // 站点域名
    private String description;             // 描述
    private String keywords;                // 关键字
    private String theme;                   // 主题
    private String copyright;               // 版权信息
    private String customIndexView;         // 自定义站点首页视图
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SiteBase() {
        super();
    }

    public SiteBase(Long id) {
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
     * getter name(站点名称)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(站点名称)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter title(站点标题)
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter title(站点标题)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter logo(站点Logo)
     */
    public String getLogo() {
        return logo;
    }

    /**
     * setter logo(站点Logo)
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * getter domain(站点域名)
     */
    public String getDomain() {
        return domain;
    }

    /**
     * setter domain(站点域名)
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * getter description(描述)
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter description(描述)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter keywords(关键字)
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * setter keywords(关键字)
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * getter theme(主题)
     */
    public String getTheme() {
        return theme;
    }

    /**
     * setter theme(主题)
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * getter copyright(版权信息)
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * setter copyright(版权信息)
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * getter customIndexView(自定义站点首页视图)
     */
    public String getCustomIndexView() {
        return customIndexView;
    }

    /**
     * setter customIndexView(自定义站点首页视图)
     */
    public void setCustomIndexView(String customIndexView) {
        this.customIndexView = customIndexView;
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