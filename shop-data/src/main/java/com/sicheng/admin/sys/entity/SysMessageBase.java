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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 会员通知 Entity 父类
 *
 * @author cl
 * @version 2017-10-12
 */
public class SysMessageBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long informationId;             // 主键
    private String sender;                  // 发送人类型:0系统
    private Long uId;                       // 接收人id 关联(买家表)
    private String type;                    // 类型:1交易信息 2售后服务信息 3商品信息 4 运营信息
    private String statusMsg;               // 站内信：0未发送、1已发送
    private String statusSms;               // 短信：0未发送、1已发送
    private String statusEmail;             // 邮件：0未发送、1已发送
    private String statusWeixin;            // 微信：0未发送、1已发送
    private String statusB;                 // 预留：0未发送、1已发送
    private String reading;                 // 0未读、1已读 （只限站内信）
    private String title;                   // 消息标题，预留
    private String content;                 // 消息内容
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

    public SysMessageBase() {
        super();
    }

    public SysMessageBase(Long id) {
        super(id);
        this.informationId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return informationId;
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
        this.informationId = id;
    }

    /**
     * getter informationId(主键)
     */
    public Long getInformationId() {
        return informationId;
    }

    /**
     * setter informationId(主键)
     */
    public void setInformationId(Long informationId) {
        this.informationId = informationId;
    }

    /**
     * getter sender(发送人类型:0系统)
     */
    public String getSender() {
        return sender;
    }

    /**
     * setter sender(发送人类型:0系统)
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * getter uId(接收人id 关联(买家表))
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(接收人id 关联(买家表))
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter type(类型:1交易信息 2售后服务信息 3商品信息 4 运营信息)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(类型:1交易信息 2售后服务信息 3商品信息 4 运营信息)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter statusMsg(站内信：0未发送、1已发送)
     */
    public String getStatusMsg() {
        return statusMsg;
    }

    /**
     * setter statusMsg(站内信：0未发送、1已发送)
     */
    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    /**
     * getter statusSms(短信：0未发送、1已发送)
     */
    public String getStatusSms() {
        return statusSms;
    }

    /**
     * setter statusSms(短信：0未发送、1已发送)
     */
    public void setStatusSms(String statusSms) {
        this.statusSms = statusSms;
    }

    /**
     * getter statusEmail(邮件：0未发送、1已发送)
     */
    public String getStatusEmail() {
        return statusEmail;
    }

    /**
     * setter statusEmail(邮件：0未发送、1已发送)
     */
    public void setStatusEmail(String statusEmail) {
        this.statusEmail = statusEmail;
    }

    /**
     * getter statusWeixin(微信：0未发送、1已发送)
     */
    public String getStatusWeixin() {
        return statusWeixin;
    }

    /**
     * setter statusWeixin(微信：0未发送、1已发送)
     */
    public void setStatusWeixin(String statusWeixin) {
        this.statusWeixin = statusWeixin;
    }

    /**
     * getter statusB(预留：0未发送、1已发送)
     */
    public String getStatusB() {
        return statusB;
    }

    /**
     * setter statusB(预留：0未发送、1已发送)
     */
    public void setStatusB(String statusB) {
        this.statusB = statusB;
    }

    /**
     * getter reading(0未读、1已读 （只限站内信）)
     */
    public String getReading() {
        return reading;
    }

    /**
     * setter reading(0未读、1已读 （只限站内信）)
     */
    public void setReading(String reading) {
        this.reading = reading;
    }

    /**
     * getter title(消息标题，预留)
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter title(消息标题，预留)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter content(消息内容)
     */
    public String getContent() {
        return content;
    }

    /**
     * setter content(消息内容)
     */
    public void setContent(String content) {
        this.content = content;
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