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
package com.sicheng.admin.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 商家相册 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-02-07
 */
public class StoreAlbumSpaceBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long albumSpaceId;              // 主键
    private Integer pictureCount;           // 图片数量
    private Integer albumCount;             // 相册的数量
    private Integer totalSpace;             // 图片总空间(单位byte)
    private Integer pictureSpace;           // 图片占用空间(单位byte)
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

    public StoreAlbumSpaceBase() {
        super();
    }

    public StoreAlbumSpaceBase(Long id) {
        super(id);
        this.albumSpaceId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return albumSpaceId;
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
        this.albumSpaceId = id;
    }

    /**
     * getter albumSpaceId(主键)
     */
    public Long getAlbumSpaceId() {
        return albumSpaceId;
    }

    /**
     * setter albumSpaceId(主键)
     */
    public void setAlbumSpaceId(Long albumSpaceId) {
        this.albumSpaceId = albumSpaceId;
    }

    /**
     * getter pictureCount(图片数量)
     */
    public Integer getPictureCount() {
        return pictureCount;
    }

    /**
     * setter pictureCount(图片数量)
     */
    public void setPictureCount(Integer pictureCount) {
        this.pictureCount = pictureCount;
    }

    /**
     * getter albumCount(相册的数量)
     */
    public Integer getAlbumCount() {
        return albumCount;
    }

    /**
     * setter albumCount(相册的数量)
     */
    public void setAlbumCount(Integer albumCount) {
        this.albumCount = albumCount;
    }

    /**
     * getter totalSpace(图片总空间(单位byte))
     */
    public Integer getTotalSpace() {
        return totalSpace;
    }

    /**
     * setter totalSpace(图片总空间(单位byte))
     */
    public void setTotalSpace(Integer totalSpace) {
        this.totalSpace = totalSpace;
    }

    /**
     * getter pictureSpace(图片占用空间(单位byte))
     */
    public Integer getPictureSpace() {
        return pictureSpace;
    }

    /**
     * setter pictureSpace(图片占用空间(单位byte))
     */
    public void setPictureSpace(Integer pictureSpace) {
        this.pictureSpace = pictureSpace;
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