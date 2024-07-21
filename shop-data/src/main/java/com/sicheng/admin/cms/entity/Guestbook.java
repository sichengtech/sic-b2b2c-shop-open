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

import com.sicheng.admin.sys.entity.User;
import com.sicheng.common.utils.IdWorker;

import java.util.Date;

/**
 * 留言板 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-09
 */
public class Guestbook extends GuestbookBase<Guestbook> {

    private static final long serialVersionUID = 1L;

    public Guestbook() {
        this.delFlag = DEL_FLAG_AUDIT;
    }

    public Guestbook(Long id) {
        super(id);
    }

    public void prePersist() {
        this.id = IdWorker.getId();
        this.createDate = new Date();
    }

    //对于实体类的扩展代码，请写在这里

    private User reUser;        // 回复人

    public User getReUser() {
        return reUser;
    }

    public void setReUser(User reUser) {
        this.reUser = reUser;
    }

}