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

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 热搜词 Entity 父类
 *
 * @author 贺东泽
 * @version 2019-11-05
 */
public class SiteHotSearchWordBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String word;                    // 热搜词或热搜店铺
    private String isShow;                  // 是否展示：0为否，1为是
    private String type;                    // 搜索类型：0为商品，1为店铺
    private Integer sort;                   // 排序权重，权重越高越靠前
    private String bak1;                    // 备用字段1
    private String bak2;                    // 备用字段2
    private String bak3;                    // 备用字段3
    private String bak4;                    // 备用字段4
    private String bak5;                    // 备用字段5
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SiteHotSearchWordBase() {
        super();
    }

    public SiteHotSearchWordBase(Long id) {
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
     * getter word(热搜词或热搜店铺)
     */
    public String getWord() {
        return word;
    }

    /**
     * setter word(热搜词或热搜店铺)
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * getter isShow(是否展示：0为否，1为是)
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * setter isShow(是否展示：0为否，1为是)
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    /**
     * getter type(搜索类型：0为商品，1为店铺)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(搜索类型：0为商品，1为店铺)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter sort(排序权重，权重越高越靠前)
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * setter sort(排序权重，权重越高越靠前)
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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