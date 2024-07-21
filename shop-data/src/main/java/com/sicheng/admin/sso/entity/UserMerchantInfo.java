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
package com.sicheng.admin.sso.entity;

import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 商家注册信息 Entity 子类，请把你的业务代码写在这里
 *
 * @author zjl
 * @version 2017-09-13
 */
public class UserMerchantInfo extends UserMerchantInfoBase<UserMerchantInfo> {

    private static final long serialVersionUID = 1L;

    public UserMerchantInfo() {
        super();
    }

    public UserMerchantInfo(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    private UserMain userMain; //会员总表

    public UserMain getUserMain() {
        if (userMain == null) {
            UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
            userMain = dao.selectById(this.getUId());
        }
        return userMain;
    }

    public void setUserMain(UserMain userMain) {
        this.userMain = userMain;
    }

}