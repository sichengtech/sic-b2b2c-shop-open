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
package com.sicheng.admin.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 记录发送短信情况 Entity 父类
 *
 * @author 张加利
 * @version 2017-10-12
 */
public class SysSmsLogBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long sslId;                     // 主键
    private String content;                 // 短信内容
    private String templatecode;            // 模板id
    private String status;                  // 发送状态（0、失败，1、成功）
    private String bewrite;                 // 描述
    private String type;                    // 短信网关类型（1、阿里大于，2、慧聪短信网关）
    private Date sendDate;                  // 发送时间
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
    private Date beginSendDate;             // 开始 发送时间
    private Date endSendDate;               // 结束 发送时间

    public SysSmsLogBase() {
        super();
    }

    public SysSmsLogBase(Long id) {
        super(id);
        this.sslId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return sslId;
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
        this.sslId = id;
    }

    /**
     * getter sslId(主键)
     */
    public Long getSslId() {
        return sslId;
    }

    /**
     * setter sslId(主键)
     */
    public void setSslId(Long sslId) {
        this.sslId = sslId;
    }

    /**
     * getter content(短信内容)
     */
    public String getContent() {
        return content;
    }

    /**
     * setter content(短信内容)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * getter templatecode(模板id)
     */
    public String getTemplatecode() {
        return templatecode;
    }

    /**
     * setter templatecode(模板id)
     */
    public void setTemplatecode(String templatecode) {
        this.templatecode = templatecode;
    }

    /**
     * getter status(发送状态（0、失败，1、成功）)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(发送状态（0、失败，1、成功）)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter bewrite(描述)
     */
    public String getBewrite() {
        return bewrite;
    }

    /**
     * setter bewrite(描述)
     */
    public void setBewrite(String bewrite) {
        this.bewrite = bewrite;
    }

    /**
     * getter type(短信网关类型（1、阿里大于，2、慧聪短信网关）)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(短信网关类型（1、阿里大于，2、慧聪短信网关）)
     */
    public void setType(String type) {
        this.type = type;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter sendDate(发送时间)
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * setter sendDate(发送时间)
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
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
     * getter sendDate(发送时间)
     */
    public Date getBeginSendDate() {
        return beginSendDate;
    }

    /**
     * setter sendDate(发送时间)
     */
    public void setBeginSendDate(Date beginSendDate) {
        this.beginSendDate = beginSendDate;
    }

    /**
     * getter sendDate(发送时间)
     */
    public Date getEndSendDate() {
        return endSendDate;
    }

    /**
     * setter sendDate(发送时间)
     */
    public void setEndSendDate(Date endSendDate) {
        this.endSendDate = endSendDate;
    }

}