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
 * 管理网站的消息模板 Entity 父类
 *
 * @author zjl
 * @version 2017-07-06
 */
public class SiteMessageTemplateBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String num;                     // 模板编号（手输入）
    private String name;                    // 模板名称
    private String type;                    // 消息模板类型
    private Integer sort;                   // 排序
    private String isOpen;                  // 是否开启（0否、1是）
    private String msgContent;              // 站内信：站内信模板内容
    private String smsOpen;                 // 短信：是否发送短信（0否、1是）
    private String smsContent;              // 短信：短信模板内容
    private String emailOpen;               // 邮件：是否发送邮件（0否、1是）
    private String emailTitle;              // 邮件：模板标题
    private String emailContent;            // 邮件：邮件模板内容
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
    private String thirdtemplatecode;       // 第三方短信模板id
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SiteMessageTemplateBase() {
        super();
    }

    public SiteMessageTemplateBase(Long id) {
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
     * getter num(模板编号（手输入）)
     */
    public String getNum() {
        return num;
    }

    /**
     * setter num(模板编号（手输入）)
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * getter name(模板名称)
     */
    public String getName() {
        return name;
    }

    /**
     * setter name(模板名称)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter type(消息模板类型)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(消息模板类型)
     */
    public void setType(String type) {
        this.type = type;
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
     * getter isOpen(是否开启（0否、1是）)
     */
    public String getIsOpen() {
        return isOpen;
    }

    /**
     * setter isOpen(是否开启（0否、1是）)
     */
    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * getter msgContent(站内信：站内信模板内容)
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * setter msgContent(站内信：站内信模板内容)
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * getter smsOpen(短信：是否发送短信（0否、1是）)
     */
    public String getSmsOpen() {
        return smsOpen;
    }

    /**
     * setter smsOpen(短信：是否发送短信（0否、1是）)
     */
    public void setSmsOpen(String smsOpen) {
        this.smsOpen = smsOpen;
    }

    /**
     * getter smsContent(短信：短信模板内容)
     */
    public String getSmsContent() {
        return smsContent;
    }

    /**
     * setter smsContent(短信：短信模板内容)
     */
    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    /**
     * getter emailOpen(邮件：是否发送邮件（0否、1是）)
     */
    public String getEmailOpen() {
        return emailOpen;
    }

    /**
     * setter emailOpen(邮件：是否发送邮件（0否、1是）)
     */
    public void setEmailOpen(String emailOpen) {
        this.emailOpen = emailOpen;
    }

    /**
     * getter emailTitle(邮件：模板标题)
     */
    public String getEmailTitle() {
        return emailTitle;
    }

    /**
     * setter emailTitle(邮件：模板标题)
     */
    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    /**
     * getter emailContent(邮件：邮件模板内容)
     */
    public String getEmailContent() {
        return emailContent;
    }

    /**
     * setter emailContent(邮件：邮件模板内容)
     */
    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
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
     * getter thirdtemplatecode(第三方短信模板id)
     */
    public String getThirdtemplatecode() {
        return thirdtemplatecode;
    }

    /**
     * setter thirdtemplatecode(第三方短信模板id)
     */
    public void setThirdtemplatecode(String thirdtemplatecode) {
        this.thirdtemplatecode = thirdtemplatecode;
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