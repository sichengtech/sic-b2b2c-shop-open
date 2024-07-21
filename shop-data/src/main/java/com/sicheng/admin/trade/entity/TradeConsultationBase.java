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
package com.sicheng.admin.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 咨询 Entity 父类
 *
 * @author fxx
 * @version 2017-05-12
 */
public class TradeConsultationBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long consultationId;            // 主键
    private Long uId;                       // 会员id(会员表id)
    private Long storeId;                   // 关联(店铺表)
    private Long pId;                       // 产品id(产品表id)
    private String content;                 // 咨询内容
    private String category;                // 咨询类型(数据字典(1商品咨询,2支付问题,3发票及保修,4促销及赠品))
    private String type;                    // 类别，0咨询、1回复
    private Long replyId;                   // 回复的咨询id(本表的id)
    private String ip;                      // ip
    private String isShow;                  // 是否显示，0否、1是
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
    private String isReply;                 // 是否回复，0否、1是
    private Date beginCreateDate;           // 开始 创建时间（咨询时间和回复时间）
    private Date endCreateDate;             // 结束 创建时间（咨询时间和回复时间）
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间

    public TradeConsultationBase() {
        super();
    }

    public TradeConsultationBase(Long id) {
        super(id);
        this.consultationId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return consultationId;
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
        this.consultationId = id;
    }

    /**
     * getter consultationId(主键)
     */
    public Long getConsultationId() {
        return consultationId;
    }

    /**
     * setter consultationId(主键)
     */
    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    /**
     * getter uId(会员id(会员表id))
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(会员id(会员表id))
     */
    public void setUId(Long uId) {
        this.uId = uId;
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
     * getter pId(产品id(产品表id))
     */
    public Long getPId() {
        return pId;
    }

    /**
     * setter pId(产品id(产品表id))
     */
    public void setPId(Long pId) {
        this.pId = pId;
    }

    /**
     * getter content(咨询内容)
     */
    public String getContent() {
        return content;
    }

    /**
     * setter content(咨询内容)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * getter category(咨询类型(数据字典(商品咨询,支付问题,发票及保修,促销及赠品)))
     */
    public String getCategory() {
        return category;
    }

    /**
     * setter category(咨询类型(数据字典(商品咨询,支付问题,发票及保修,促销及赠品)))
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * getter type(类别，0咨询、1回复)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(类别，0咨询、1回复)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter replyId(回复的咨询id(本表的id))
     */
    public Long getReplyId() {
        return replyId;
    }

    /**
     * setter replyId(回复的咨询id(本表的id))
     */
    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    /**
     * getter ip(ip)
     */
    public String getIp() {
        return ip;
    }

    /**
     * setter ip(ip)
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * getter isShow(是否显示，0否、1是)
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * setter isShow(是否显示，0否、1是)
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
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

    /**
     * getter isReply(是否回复，0否、1是)
     */
    public String getIsReply() {
        return isReply;
    }

    /**
     * setter isReply(是否回复，0否、1是)
     */
    public void setIsReply(String isReply) {
        this.isReply = isReply;
    }

    /**
     * getter createDate(创建时间（咨询时间和回复时间）)
     */
    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    /**
     * setter createDate(创建时间（咨询时间和回复时间）)
     */
    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    /**
     * getter createDate(创建时间（咨询时间和回复时间）)
     */
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * setter createDate(创建时间（咨询时间和回复时间）)
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