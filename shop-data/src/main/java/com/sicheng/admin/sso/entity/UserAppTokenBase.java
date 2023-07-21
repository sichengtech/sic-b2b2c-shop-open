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
package com.sicheng.admin.sso.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 会员app token Entity 父类
 *
 * @author 赵磊
 * @version 2019-01-29
 */
public class UserAppTokenBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long tId;                       // 主键
    private Long uId;                       // 会员ID
    private String token;                   // 令牌（uuid）
    private String type;                    // 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
    private String status;                  // 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
    private Date validDate;                 // 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间
    private Date beginValidDate;            // 开始 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
    private Date endValidDate;              // 结束 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新

    public UserAppTokenBase() {
        super();
    }

    public UserAppTokenBase(Long id) {
        super(id);
        this.tId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return tId;
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
        this.tId = id;
    }

    /**
     * getter tId(主键)
     */
    public Long getTId() {
        return tId;
    }

    /**
     * setter tId(主键)
     */
    public void setTId(Long tId) {
        this.tId = tId;
    }

    /**
     * getter uId(会员ID)
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(会员ID)
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter token(令牌（uuid）)
     */
    public String getToken() {
        return token;
    }

    /**
     * setter token(令牌（uuid）)
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * getter type(业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter status(是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter validDate(有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新)
     */
    public Date getValidDate() {
        return validDate;
    }

    /**
     * setter validDate(有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新)
     */
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
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
     * getter validDate(有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新)
     */
    public Date getBeginValidDate() {
        return beginValidDate;
    }

    /**
     * setter validDate(有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新)
     */
    public void setBeginValidDate(Date beginValidDate) {
        this.beginValidDate = beginValidDate;
    }

    /**
     * getter validDate(有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新)
     */
    public Date getEndValidDate() {
        return endValidDate;
    }

    /**
     * setter validDate(有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新)
     */
    public void setEndValidDate(Date endValidDate) {
        this.endValidDate = endValidDate;
    }

}