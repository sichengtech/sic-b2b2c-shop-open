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
package com.sicheng.admin.store.entity;


import com.sicheng.common.persistence.DataEntity;

/**
 * 卖家和角色表中间 Entity 父类
 *
 * @author cl
 * @version 2017-10-25
 */
public class StoreSellerRoleBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long uId;                       // 会员id
    private Long roleId;                    // 关联store_role(卖家角色表)

    public StoreSellerRoleBase() {
        super();
    }

    public StoreSellerRoleBase(Long id) {
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
     * getter uId(会员id)
     */
    public Long getUId() {
        return uId;
    }

    /**
     * setter uId(会员id)
     */
    public void setUId(Long uId) {
        this.uId = uId;
    }

    /**
     * getter roleId(关联store_role(卖家角色表))
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * setter roleId(关联store_role(卖家角色表))
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


}