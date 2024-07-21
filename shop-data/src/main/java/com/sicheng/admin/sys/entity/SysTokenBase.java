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
package com.sicheng.admin.sys.entity;

import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * token表 Entity 父类
 *
 * @author cl
 * @version 2017-03-23
 */
public class SysTokenBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long tId;                       // 主键
    private Long userId;                    // 用户id(公用上传不需要存值；激活邮箱需要存值)
    private String token;                   // 令牌
    private String type;                    // 业务类型：10.公用上传20.用户激活邮箱
    private String status;                  // 状态：0.失效1.有效
    private Date beginCreateDate;           // 开始 创建时间
    private Date endCreateDate;             // 结束 创建时间

    public SysTokenBase() {
        super();
    }

    public SysTokenBase(Long id) {
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
     * getter userId(用户id(公用上传不需要存值；激活邮箱需要存值))
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * setter userId(用户id(公用上传不需要存值；激活邮箱需要存值))
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * getter token(令牌)
     */
    public String getToken() {
        return token;
    }

    /**
     * setter token(令牌)
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * getter type(业务类型：10.公用上传20.用户激活邮箱)
     */
    public String getType() {
        return type;
    }

    /**
     * setter type(业务类型：10.公用上传20.用户激活邮箱)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter status(状态：0.失效1.有效)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(状态：0.失效1.有效)
     */
    public void setStatus(String status) {
        this.status = status;
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

}