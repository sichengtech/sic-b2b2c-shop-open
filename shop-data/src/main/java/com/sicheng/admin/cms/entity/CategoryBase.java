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

import com.sicheng.admin.sys.entity.Office;
import com.sicheng.common.persistence.TreeEntity;

import java.util.Date;

/**
 * 文章栏目 Entity 父类
 *
 * @author cl
 * @version 2017-02-14
 */
public class CategoryBase<T> extends TreeEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long siteId;                    // 站点编号
    private Office office;                  // 归属机构
    private String module;                  // 栏目模块
    private String image;                   // 栏目图片
    private String href;                    // 链接
    private String target;                  // 目标
    private String description;             // 描述
    private String keywords;                // 关键字
    private String inMenu;                  // 是否在导航中显示
    private String inList;                  // 是否在分类页中显示列表
    private String showModes;               // 展现方式
    private String allowComment;            // 是否允许评论
    private String isAudit;                 // 是否需要审核
    private String customListView;          // 自定义列表视图
    private String customContentView;       // 自定义内容视图
    private String viewConfig;              // 视图配置
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public CategoryBase() {
        super();
    }

    public CategoryBase(Long id) {
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
     * getter siteId(站点编号)
     */
    public Long getSiteId() {
        return siteId;
    }

    /**
     * setter siteId(站点编号)
     */
    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    /**
     * getter office(归属机构)
     */
    public Office getOffice() {
        return office;
    }

    /**
     * setter office(归属机构)
     */
    public void setOffice(Office office) {
        this.office = office;
    }

    /**
     * getter module(栏目模块)
     */
    public String getModule() {
        return module;
    }

    /**
     * setter module(栏目模块)
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * getter image(栏目图片)
     */
    public String getImage() {
        return image;
    }

    /**
     * setter image(栏目图片)
     */
    public void setImage(String image) {
        this.image = image;
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
     * getter inMenu(是否在导航中显示)
     */
    public String getInMenu() {
        return inMenu;
    }

    /**
     * setter inMenu(是否在导航中显示)
     */
    public void setInMenu(String inMenu) {
        this.inMenu = inMenu;
    }

    /**
     * getter inList(是否在分类页中显示列表)
     */
    public String getInList() {
        return inList;
    }

    /**
     * setter inList(是否在分类页中显示列表)
     */
    public void setInList(String inList) {
        this.inList = inList;
    }

    /**
     * getter showModes(展现方式)
     */
    public String getShowModes() {
        return showModes;
    }

    /**
     * setter showModes(展现方式)
     */
    public void setShowModes(String showModes) {
        this.showModes = showModes;
    }

    /**
     * getter allowComment(是否允许评论)
     */
    public String getAllowComment() {
        return allowComment;
    }

    /**
     * setter allowComment(是否允许评论)
     */
    public void setAllowComment(String allowComment) {
        this.allowComment = allowComment;
    }

    /**
     * getter isAudit(是否需要审核)
     */
    public String getIsAudit() {
        return isAudit;
    }

    /**
     * setter isAudit(是否需要审核)
     */
    public void setIsAudit(String isAudit) {
        this.isAudit = isAudit;
    }

    /**
     * getter customListView(自定义列表视图)
     */
    public String getCustomListView() {
        return customListView;
    }

    /**
     * setter customListView(自定义列表视图)
     */
    public void setCustomListView(String customListView) {
        this.customListView = customListView;
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