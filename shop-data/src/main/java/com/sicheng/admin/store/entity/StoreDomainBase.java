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
package com.sicheng.admin.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 店铺二级域名 Entity 父类
 *
 * @author cl
 * @version 2017-04-11
 */
public class StoreDomainBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long storeId;                   // 主键(店铺表，店铺二级域名，店铺相册空间信息表用1个id)
    private String domain;                  // 二级域名，假如一级域名是：taobao.com本字段存储：tanchisha， 表示tanchisha.taobao.com可访问店铺首页
    private Integer modifyCount;            // 二级域名变更次数(限值在fdp配置文件去配置，每自然月可变更N次)
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
    private String modifyDate;              // 二级域名最后一次变更年月（格式：2017-02）
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间 （修改时间）
    private Date endUpdateDate;             // 结束 更新时间 （修改时间）

    public StoreDomainBase() {
        super();
    }

    public StoreDomainBase(Long id) {
        super(id);
        this.storeId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return storeId;
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
        this.storeId = id;
    }

    /**
     * getter storeId(主键(店铺表，店铺二级域名，店铺相册空间信息表用1个id))
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * setter storeId(主键(店铺表，店铺二级域名，店铺相册空间信息表用1个id))
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * getter domain(二级域名，假如一级域名是：taobao.com本字段存储：tanchisha， 表示tanchisha.taobao.com可访问店铺首页)
     */
    public String getDomain() {
        return domain;
    }

    /**
     * setter domain(二级域名，假如一级域名是：taobao.com本字段存储：tanchisha， 表示tanchisha.taobao.com可访问店铺首页)
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * getter modifyCount(二级域名变更次数(限值在fdp配置文件去配置，每自然月可变更N次))
     */
    public Integer getModifyCount() {
        return modifyCount;
    }

    /**
     * setter modifyCount(二级域名变更次数(限值在fdp配置文件去配置，每自然月可变更N次))
     */
    public void setModifyCount(Integer modifyCount) {
        this.modifyCount = modifyCount;
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
     * getter modifyDate(二级域名最后一次变更年月（格式：2017-02）)
     */
    public String getModifyDate() {
        return modifyDate;
    }

    /**
     * setter modifyDate(二级域名最后一次变更年月（格式：2017-02）)
     */
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
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
     * getter updateDate(更新时间 （修改时间）)
     */
    public Date getBeginUpdateDate() {
        return beginUpdateDate;
    }

    /**
     * setter updateDate(更新时间 （修改时间）)
     */
    public void setBeginUpdateDate(Date beginUpdateDate) {
        this.beginUpdateDate = beginUpdateDate;
    }

    /**
     * getter updateDate(更新时间 （修改时间）)
     */
    public Date getEndUpdateDate() {
        return endUpdateDate;
    }

    /**
     * setter updateDate(更新时间 （修改时间）)
     */
    public void setEndUpdateDate(Date endUpdateDate) {
        this.endUpdateDate = endUpdateDate;
    }

}