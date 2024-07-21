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
package com.sicheng.admin.sso.service;

import com.sicheng.admin.sso.dao.UserRepairShopDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserRepairShop;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.web.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 汽车服务门店 Service
 *
 * @author cl
 * @version 2017-07-30
 */
@Service
@Lazy
@Transactional(propagation = Propagation.SUPPORTS)
public class UserRepairShopService extends CrudService<UserRepairShopDao, UserRepairShop> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Lazy
    @Autowired
    private UserMainService userMainService;

    /**
     * 审核门店
     */
    @Transactional(rollbackFor = Exception.class)
    public void auth(UserRepairShop useRepairShop) {
//        UserMainService userMainService= SpringContextHolder.getBean(UserMainService.class);
        //审核门店
        super.updateByIdSelective(useRepairShop);
        //审核通过
        if ("1".equals(useRepairShop.getAuthType())) {
            UserMain userMain = userMainService.selectById(useRepairShop.getUId());
            UserMain um = new UserMain();
            um.setUId(useRepairShop.getUId());
            um.setTypeUser(userMain.getTypeUser());
            um.addTypeUserService();
            userMainService.updateByIdSelective(um);
        }
    }

}