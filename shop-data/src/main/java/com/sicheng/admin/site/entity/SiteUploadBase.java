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
package com.sicheng.admin.site.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

/**
 * 管理文件上传的格式 Entity 父类
 *
 * @author 张加利
 * @version 2017-02-06
 */
public class SiteUploadBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Integer uploadSize;             // 上传文件大小 ,单位字节
    private String type;                    // 上传文件类型，jgp,jpeg,gif,png
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
    @JsonIgnore
    private String bak6;                    // 备用字段6
    @JsonIgnore
    private String bak7;                    // 备用字段7
    @JsonIgnore
    private String bak8;                    // 备用字段8
    @JsonIgnore
    private String bak9;                    // 备用字段9
    @JsonIgnore
    private String bak10;                   // 备用字段10

    public SiteUploadBase() {
        super();
    }

    public SiteUploadBase(Long id) {
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
     * getter uploadSize(上传文件大小 ,单位字节)
     */
    public Integer getUploadSize() {
        return uploadSize;
    }

    /**
     * setter uploadSize(上传文件大小 ,单位字节)
     */
    public void setUploadSize(Integer uploadSize) {
        this.uploadSize = uploadSize;
    }

    /**
     * getter type(上传文件类型，jgp,jpeg,gif,png)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(上传文件类型，jgp,jpeg,gif,png)
     */
    public void setType(String type) {
        this.type = type;
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
     * getter bak6(备用字段6)
     */
    public String getBak6() {
        return bak6;
    }

    /**
     * setter bak6(备用字段6)
     */
    public void setBak6(String bak6) {
        this.bak6 = bak6;
    }

    /**
     * getter bak7(备用字段7)
     */
    public String getBak7() {
        return bak7;
    }

    /**
     * setter bak7(备用字段7)
     */
    public void setBak7(String bak7) {
        this.bak7 = bak7;
    }

    /**
     * getter bak8(备用字段8)
     */
    public String getBak8() {
        return bak8;
    }

    /**
     * setter bak8(备用字段8)
     */
    public void setBak8(String bak8) {
        this.bak8 = bak8;
    }

    /**
     * getter bak9(备用字段9)
     */
    public String getBak9() {
        return bak9;
    }

    /**
     * setter bak9(备用字段9)
     */
    public void setBak9(String bak9) {
        this.bak9 = bak9;
    }

    /**
     * getter bak10(备用字段10)
     */
    public String getBak10() {
        return bak10;
    }

    /**
     * setter bak10(备用字段10)
     */
    public void setBak10(String bak10) {
        this.bak10 = bak10;
    }


}