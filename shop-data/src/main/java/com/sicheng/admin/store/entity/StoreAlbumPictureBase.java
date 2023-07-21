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
 * 商家相册图片 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-04-11
 */
public class StoreAlbumPictureBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long pictureId;                 // 主键
    private Long storeId;                   // 关联(店铺表)
    private Long albumId;                   // store_album(相册夹表)
    private String path;                    // 图片的存储路径
    private String pixel;                   // 原图像素，例如：200x300，单位px
    private Integer fileSize;               // 文件大小，单位byte
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
    private Date beginCreateDate;           // 开始 创建时间、上传时间
    private Date endCreateDate;             // 结束 创建时间、上传时间

    public StoreAlbumPictureBase() {
        super();
    }

    public StoreAlbumPictureBase(Long id) {
        super(id);
        this.pictureId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return pictureId;
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
        this.pictureId = id;
    }

    /**
     * getter pictureId(主键)
     */
    public Long getPictureId() {
        return pictureId;
    }

    /**
     * setter pictureId(主键)
     */
    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    /**
     * getter storeId(关联(店铺表))
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * setter storeId(关联(店铺表))
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * getter albumId(store_album(相册夹表))
     */
    public Long getAlbumId() {
        return albumId;
    }

    /**
     * setter albumId(store_album(相册夹表))
     */
    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    /**
     * getter path(图片的存储路径)
     */
    public String getPath() {
        return path;
    }

    /**
     * setter path(图片的存储路径)
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * getter pixel(原图像素，例如：200x300，单位px)
     */
    public String getPixel() {
        return pixel;
    }

    /**
     * setter pixel(原图像素，例如：200x300，单位px)
     */
    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    /**
     * getter fileSize(文件大小，单位byte)
     */
    public Integer getFileSize() {
        return fileSize;
    }

    /**
     * setter fileSize(文件大小，单位byte)
     */
    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
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
     * getter createDate(创建时间、上传时间)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(创建时间、上传时间)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(创建时间、上传时间)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(创建时间、上传时间)
     */
    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

}