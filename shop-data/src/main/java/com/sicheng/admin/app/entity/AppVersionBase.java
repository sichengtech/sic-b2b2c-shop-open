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
package com.sicheng.admin.app.entity;

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * app版本观念里 Entity 父类
 *
 * @author hdz
 * @version 2019-10-29
 */
public class AppVersionBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String version;                 // 版本号
    private String type;                    // 安装包类型：1为apk，2为wgt
    private String isNewVersion;            // 是否为最新版本：0为否，1为是
    private String downloadPath;            // 下载路径
    private String explain;                 // 版本说明
    private String bak1;                    // 备用字段1
    private String bak2;                    // 备用字段2
    private String bak3;                    // 备用字段3
    private String bak4;                    // 备用字段4
    private String bak5;                    // 备用字段5
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间
    private Date beginCreateDate;           // 开始 创建时间（兼发布时间）
    private Date endCreateDate;             // 结束 创建时间（兼发布时间）

    public AppVersionBase() {
        super();
    }

    public AppVersionBase(Long id) {
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
     * @see com.sicheng.common.persistence.BaseEntity#setId(Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter version(版本号)
     */
    public String getVersion() {
        return version;
    }

    /**
     * setter version(版本号)
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * getter type(安装包类型：1为apk，2为wgt)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(安装包类型：1为apk，2为wgt)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter isNewVersion(是否为最新版本：0为否，1为是)
     */
    public String getIsNewVersion() {
        return isNewVersion;
    }

    /**
     * setter isNewVersion(是否为最新版本：0为否，1为是)
     */
    public void setIsNewVersion(String isNewVersion) {
        this.isNewVersion = isNewVersion;
    }

    /**
     * getter downloadPath(下载路径)
     */
    public String getDownloadPath() {
        return downloadPath;
    }

    /**
     * setter downloadPath(下载路径)
     */
    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    /**
     * getter explain(版本说明)
     */
    public String getExplain() {
        return explain;
    }

    /**
     * setter explain(版本说明)
     */
    public void setExplain(String explain) {
        this.explain = explain;
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

    /**
     * getter createDate(创建时间（兼发布时间）)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(创建时间（兼发布时间）)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(创建时间（兼发布时间）)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(创建时间（兼发布时间）)
     */
    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

}