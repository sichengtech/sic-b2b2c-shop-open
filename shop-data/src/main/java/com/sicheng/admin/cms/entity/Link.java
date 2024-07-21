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
package com.sicheng.admin.cms.entity;

import com.drew.lang.annotations.NotNull;
import com.sicheng.admin.sys.entity.User;

/**
 * 友情链接 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-09
 */
public class Link extends LinkBase<Link> {

    private static final long serialVersionUID = 1L;

    public Link() {
        super();
        this.setWeight(0);
    }

    public Link(Long id) {
        super(id);
    }

    public Link(Category category) {
        this();
        this.category = category;
    }

    //对于实体类的扩展代码，请写在这里

    private Category category;// 分类编号
    private User user;        //关联用户

    @NotNull
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}