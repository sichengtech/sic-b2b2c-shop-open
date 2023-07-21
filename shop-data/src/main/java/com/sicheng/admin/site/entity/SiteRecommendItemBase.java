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
 * 推荐位详情 Entity 父类
 *
 * @author fanxiuxiu
 * @version 2017-09-27
 */
public class SiteRecommendItemBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long riId;                      // 主键
    private Long recommendId;               // 推荐位表id
    private String path;                    // 图片地址
    private Long pId;                       // 商品id
    private String operationType;           // 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
    private String operationContent;        // 操作内容
    private Integer sort;                   // 排序
    private String addInfo1;                // 附加值1
    private String addInfo2;                // 附加值2
    private String addInfo3;                // 附加值3
    private String addInfo4;                // 附加值4
    private String addInfo5;                // 附加值5
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public SiteRecommendItemBase() {
        super();
    }

    public SiteRecommendItemBase(Long id) {
        super(id);
        this.riId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return riId;
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
        this.riId = id;
    }

    /**
     * getter riId(主键)
     */
    public Long getRiId() {
        return riId;
    }

    /**
     * setter riId(主键)
     */
    public void setRiId(Long riId) {
        this.riId = riId;
    }

    /**
     * getter recommendId(推荐位表id)
     */
    public Long getRecommendId() {
        return recommendId;
    }

    /**
     * setter recommendId(推荐位表id)
     */
    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    /**
     * getter path(图片地址)
     */
    public String getPath() {
        return path;
    }

    /**
     * setter path(图片地址)
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * getter pId(商品id)
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(商品id)
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter operationType(操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类))
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * setter operationType(操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类))
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * getter operationContent(操作内容)
     */
    public String getOperationContent() {
        return operationContent;
    }

    /**
     * setter operationContent(操作内容)
     */
    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
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
     * getter addInfo1(附加值1)
     */
    public String getAddInfo1() {
        return addInfo1;
    }

    /**
     * setter addInfo1(附加值1)
     */
    public void setAddInfo1(String addInfo1) {
        this.addInfo1 = addInfo1;
    }

    /**
     * getter addInfo2(附加值2)
     */
    public String getAddInfo2() {
        return addInfo2;
    }

    /**
     * setter addInfo2(附加值2)
     */
    public void setAddInfo2(String addInfo2) {
        this.addInfo2 = addInfo2;
    }

    /**
     * getter addInfo3(附加值3)
     */
    public String getAddInfo3() {
        return addInfo3;
    }

    /**
     * setter addInfo3(附加值3)
     */
    public void setAddInfo3(String addInfo3) {
        this.addInfo3 = addInfo3;
    }

    /**
     * getter addInfo4(附加值4)
     */
    public String getAddInfo4() {
        return addInfo4;
    }

    /**
     * setter addInfo4(附加值4)
     */
    public void setAddInfo4(String addInfo4) {
        this.addInfo4 = addInfo4;
    }

    /**
     * getter addInfo5(附加值5)
     */
    public String getAddInfo5() {
        return addInfo5;
    }

    /**
     * setter addInfo5(附加值5)
     */
    public void setAddInfo5(String addInfo5) {
        this.addInfo5 = addInfo5;
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