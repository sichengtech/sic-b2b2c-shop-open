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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 文章表 Entity 父类
 *
 * @author cl
 * @version 2017-10-27
 */
public class ArticleBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long categoryId;                // 栏目编号
    private String title;                   // 标题
    private String link;                    // 文章链接
    private String color;                   // 标题颜色
    private String image;                   // 文章图片
    private String keywords;                // 关键字
    private String description;             // 描述、摘要
    private Integer weight;                 // 权重，越大越靠前
    private Date weightDate;                // 权重期限
    private Integer hits;                   // 点击数
    private String posid;                   // 推荐位，多选
    private String customContentView;       // 自定义内容视图
    private String viewConfig;              // 视图配置
    private Date beginWeightDate;           // 开始 权重期限
    private Date endWeightDate;             // 结束 权重期限
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public ArticleBase() {
        super();
    }

    public ArticleBase(Long id) {
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
     * getter categoryId(栏目编号)
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * setter categoryId(栏目编号)
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * getter title(标题)
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter title(标题)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter link(文章链接)
     */
    public String getLink() {
        return link;
    }

    /**
     * setter link(文章链接)
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * getter color(标题颜色)
     */
    public String getColor() {
        return color;
    }

    /**
     * setter color(标题颜色)
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * getter image(文章图片)
     */
    public String getImage() {
        return image;
    }

    /**
     * setter image(文章图片)
     */
    public void setImage(String image) {
        this.image = image;
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
     * getter description(描述、摘要)
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter description(描述、摘要)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter weight(权重，越大越靠前)
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * setter weight(权重，越大越靠前)
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter weightDate(权重期限)
     */
    public Date getWeightDate() {
        return weightDate;
    }

    /**
     * setter weightDate(权重期限)
     */
    public void setWeightDate(Date weightDate) {
        this.weightDate = weightDate;
    }

    /**
     * getter hits(点击数)
     */
    public Integer getHits() {
        return hits;
    }

    /**
     * setter hits(点击数)
     */
    public void setHits(Integer hits) {
        this.hits = hits;
    }

    /**
     * getter posid(推荐位，多选)
     */
    public String getPosid() {
        return posid;
    }

    /**
     * setter posid(推荐位，多选)
     */
    public void setPosid(String posid) {
        this.posid = posid;
    }

    /**
     * getter customContentView(自定义内容视图)
     */
    public String getCustomContentView() {
        return customContentView;
    }

    /**
     * setter customContentView(自定义内容视图)
     */
    public void setCustomContentView(String customContentView) {
        this.customContentView = customContentView;
    }

    /**
     * getter viewConfig(视图配置)
     */
    public String getViewConfig() {
        return viewConfig;
    }

    /**
     * setter viewConfig(视图配置)
     */
    public void setViewConfig(String viewConfig) {
        this.viewConfig = viewConfig;
    }

    /**
     * getter weightDate(权重期限)
     */
    public Date getBeginWeightDate() {
        return beginWeightDate;
    }

    /**
     * setter weightDate(权重期限)
     */
    public void setBeginWeightDate(Date beginWeightDate) {
        this.beginWeightDate = beginWeightDate;
    }

    /**
     * getter weightDate(权重期限)
     */
    public Date getEndWeightDate() {
        return endWeightDate;
    }

    /**
     * setter weightDate(权重期限)
     */
    public void setEndWeightDate(Date endWeightDate) {
        this.endWeightDate = endWeightDate;
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