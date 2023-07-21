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
package com.sicheng.admin.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 留言板 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-02-09
 */
public class GuestbookBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String type;                    // 留言分类
    private String content;                 // 留言内容
    private String name;                    // 姓名
    private String email;                   // 邮箱
    private String phone;                   // 电话
    private String workunit;                // 单位
    private String ip;                      // IP
    private Long reUserId;                  // 回复人
    private Date reDate;                    // 回复时间
    private String reContent;               // 回复内容
    private Date beginCreateDate;           // 开始 留言时间
    private Date endCreateDate;             // 结束 留言时间
    private Date beginReDate;               // 开始 回复时间
    private Date endReDate;                 // 结束 回复时间

    public GuestbookBase() {
        super();
    }

    public GuestbookBase(Long id) {
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
     * getter type(留言分类)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(留言分类)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter content(留言内容)
     */
    public String getContent() {
        return content;
    }

    /**
     * setter content(留言内容)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * getter name(姓名)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(姓名)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter email(邮箱)
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter email(邮箱)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter phone(电话)
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setter phone(电话)
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getter workunit(单位)
     */
    public String getWorkunit() {
        return workunit;
    }

    /**
     * setter workunit(单位)
     */
    public void setWorkunit(String workunit) {
        this.workunit = workunit;
    }

    /**
     * getter ip(IP)
     */
    public String getIp() {
        return ip;
    }

    /**
     * setter ip(IP)
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * getter reUserId(回复人)
     */
    public Long getReUserId() {
        return reUserId;
    }

    /**
     * setter reUserId(回复人)
     */
    public void setReUserId(Long reUserId) {
        this.reUserId = reUserId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter reDate(回复时间)
     */
    public Date getReDate() {
        return reDate;
    }

    /**
     * setter reDate(回复时间)
     */
    public void setReDate(Date reDate) {
        this.reDate = reDate;
    }

    /**
     * getter reContent(回复内容)
     */
    public String getReContent() {
        return reContent;
    }

    /**
     * setter reContent(回复内容)
     */
    public void setReContent(String reContent) {
        this.reContent = reContent;
    }

    /**
     * getter createDate(留言时间)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(留言时间)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(留言时间)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(留言时间)
     */
    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    /**
     * getter reDate(回复时间)
     */
    public Date getBeginReDate() {
        return beginReDate;
    }

    /**
     * setter reDate(回复时间)
     */
    public void setBeginReDate(Date beginReDate) {
        this.beginReDate = beginReDate;
    }

    /**
     * getter reDate(回复时间)
     */
    public Date getEndReDate() {
        return endReDate;
    }

    /**
     * setter reDate(回复时间)
     */
    public void setEndReDate(Date endReDate) {
        this.endReDate = endReDate;
    }

}