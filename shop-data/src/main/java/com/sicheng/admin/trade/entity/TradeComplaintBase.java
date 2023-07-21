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
package com.sicheng.admin.trade.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 投诉 Entity 父类
 *
 * @author 范秀秀
 * @version 2017-05-12
 */
public class TradeComplaintBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long complaintId;               // 主键
    private Long uId;                       // 会员id(会员表id)
    private Long storeId;                   // 关联(店铺表)
    private Long pId;                       // 产品id(产品表id)
    private Long skuId;                     // sku id(产品sku表id)
    private Long orderId;                   // 订单id(订单表id)
    private String theme;                   // 投诉主题，1售后保障服务，2未收到货
    private String content;                 // 投诉(申诉)内容
    private String ip;                      // ip
    private String type;                    // 类型，0投诉、1申诉
    private Long replyId;                   // 申诉的投诉id(本表的id)
    private String status;                  // 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
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
    private Long orderDetailId;             // 订单详情id(订单详情表id)
    private Date systemHandleTime;          // 平台处理时间
    private String systemHandleHandelOpinion;   // 平台处理意见
    private Long adminId;                   // 处理人(管理员表id)
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginUpdateDate;           // 开始 更新时间
    private Date endUpdateDate;             // 结束 更新时间
    private Date beginSystemHandleTime;     // 开始 平台处理时间
    private Date endSystemHandleTime;       // 结束 平台处理时间

    public TradeComplaintBase() {
        super();
    }

    public TradeComplaintBase(Long id) {
        super(id);
        this.complaintId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return complaintId;
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
        this.complaintId = id;
    }

    /**
     * getter complaintId(主键)
     */
    public Long getComplaintId() {
        return complaintId;
    }

    /**
     * setter complaintId(主键)
     */
    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
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
     * getter skuId(sku id(产品sku表id))
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * setter skuId(sku id(产品sku表id))
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * getter orderId(订单id(订单表id))
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * setter orderId(订单id(订单表id))
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * getter theme(投诉主题，1售后保障服务，2未收到货)
     */
    public String getTheme() {
        return theme;
    }

    /**
     * setter theme(投诉主题，1售后保障服务，2未收到货)
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * getter content(投诉(申诉)内容)
     */
    public String getContent() {
        return content;
    }

    /**
     * setter content(投诉(申诉)内容)
     */
    public void setContent(String content) {
        this.content = content;
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
     * getter type(类型，0投诉、1申诉)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(类型，0投诉、1申诉)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter replyId(申诉的投诉id(本表的id))
     */
    public Long getReplyId() {
        return replyId;
    }

    /**
     * setter replyId(申诉的投诉id(本表的id))
     */
    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    /**
     * getter status(投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成)
     */
    public void setStatus(String status) {
        this.status = status;
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
     * getter orderDetailId(订单详情id(订单详情表id))
     */
    public Long getOrderDetailId() {
        return orderDetailId;
    }

    /**
     * setter orderDetailId(订单详情id(订单详情表id))
     */
    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter systemHandleTime(平台处理时间)
     */
    public Date getSystemHandleTime() {
        return systemHandleTime;
    }

    /**
     * setter systemHandleTime(平台处理时间)
     */
    public void setSystemHandleTime(Date systemHandleTime) {
        this.systemHandleTime = systemHandleTime;
    }

    /**
     * getter systemHandleHandelOpinion(平台处理意见)
     */
    public String getSystemHandleHandelOpinion() {
        return systemHandleHandelOpinion;
    }

    /**
     * setter systemHandleHandelOpinion(平台处理意见)
     */
    public void setSystemHandleHandelOpinion(String systemHandleHandelOpinion) {
        this.systemHandleHandelOpinion = systemHandleHandelOpinion;
    }

    /**
     * getter adminId(处理人(管理员表id))
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * setter adminId(处理人(管理员表id))
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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

    /**
     * getter systemHandleTime(平台处理时间)
     */
    public Date getBeginSystemHandleTime() {
        return beginSystemHandleTime;
    }

    /**
     * setter systemHandleTime(平台处理时间)
     */
    public void setBeginSystemHandleTime(Date beginSystemHandleTime) {
        this.beginSystemHandleTime = beginSystemHandleTime;
    }

    /**
     * getter systemHandleTime(平台处理时间)
     */
    public Date getEndSystemHandleTime() {
        return endSystemHandleTime;
    }

    /**
     * setter systemHandleTime(平台处理时间)
     */
    public void setEndSystemHandleTime(Date endSystemHandleTime) {
        this.endSystemHandleTime = endSystemHandleTime;
    }

}