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
package com.sicheng.admin.cms.entity;

import com.drew.lang.annotations.NotNull;
import com.sicheng.admin.sys.entity.User;

/**
 * 评论表 Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-02-09
 */
public class Comment extends CommentBase<Comment> {

    private static final long serialVersionUID = 1L;

    public Comment() {
        super();
//		this.setDelFlag(DEL_FLAG_AUDIT);
    }

    public Comment(Long id) {
        super(id);
    }

    public Comment(Category category) {
        this();
        this.category = category;
    }

    //对于实体类的扩展代码，请写在这里

    private Category category;// 分类编号
    private User auditUser; // 审核人

    @NotNull
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

}