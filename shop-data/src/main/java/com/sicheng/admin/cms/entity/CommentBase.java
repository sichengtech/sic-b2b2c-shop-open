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
 * 评论表 Entity 父类
 *
 * @author cl
 * @version 2017-02-09
 */
public class CommentBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long categoryId;                // 栏目编号
    private Long contentId;                 // 栏目内容的编号
    private String title;                   // 栏目内容的标题
    private String content;                 // 评论内容
    private String name;                    // 评论姓名
    private String ip;                      // 评论IP
    private Long auditUserId;               // 审核人
    private Date auditDate;                 // 审核时间
    private Date beginCreateDate;           // 开始 评论时间
    private Date endCreateDate;             // 结束 评论时间
    private Date beginAuditDate;            // 开始 审核时间
    private Date endAuditDate;              // 结束 审核时间

    public CommentBase() {
        super();
    }

    public CommentBase(Long id) {
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
     * getter contentId(栏目内容的编号)
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * setter contentId(栏目内容的编号)
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /**
     * getter title(栏目内容的标题)
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter title(栏目内容的标题)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter content(评论内容)
     */
    public String getContent() {
        return content;
    }

    /**
     * setter content(评论内容)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * getter name(评论姓名)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(评论姓名)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter ip(评论IP)
     */
    public String getIp() {
        return ip;
    }

    /**
     * setter ip(评论IP)
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * getter auditUserId(审核人)
     */
    public Long getAuditUserId() {
        return auditUserId;
    }

    /**
     * setter auditUserId(审核人)
     */
    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter auditDate(审核时间)
     */
    public Date getAuditDate() {
        return auditDate;
    }

    /**
     * setter auditDate(审核时间)
     */
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    /**
     * getter createDate(评论时间)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(评论时间)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(评论时间)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(评论时间)
     */
    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    /**
     * getter auditDate(审核时间)
     */
    public Date getBeginAuditDate() {
        return beginAuditDate;
    }

    /**
     * setter auditDate(审核时间)
     */
    public void setBeginAuditDate(Date beginAuditDate) {
        this.beginAuditDate = beginAuditDate;
    }

    /**
     * getter auditDate(审核时间)
     */
    public Date getEndAuditDate() {
        return endAuditDate;
    }

    /**
     * setter auditDate(审核时间)
     */
    public void setEndAuditDate(Date endAuditDate) {
        this.endAuditDate = endAuditDate;
    }

}