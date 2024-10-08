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
package com.sicheng.admin.product.entity;


import com.sicheng.common.persistence.DataEntity;

/**
 * 商品图片多对多中间表 Entity 父类
 *
 * @author zhaolei
 * @version 2017-10-25
 */
public class ProductPictureMappingBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long pId;                       // 商品ID
    private String color;                   // 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
    private Long imgId;                     // 图片的ID，商家相册空间中的图片ID
    private Integer sort;                   // 排序，排序从1起，为1的表示首图

    public ProductPictureMappingBase() {
        super();
    }

    public ProductPictureMappingBase(Long id) {
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
     * getter pId(商品ID)
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(商品ID)
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter color(颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片)
     */
    public String getColor() {
        return color;
    }

    /**
     * setter color(颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片)
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * getter imgId(图片的ID，商家相册空间中的图片ID)
     */
    public Long getImgId() {
        return imgId;
    }

    /**
     * setter imgId(图片的ID，商家相册空间中的图片ID)
     */
    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    /**
     * getter sort(排序，排序从1起，为1的表示首图)
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * setter sort(排序，排序从1起，为1的表示首图)
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }


}