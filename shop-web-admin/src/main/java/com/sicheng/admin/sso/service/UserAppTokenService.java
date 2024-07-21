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

import com.sicheng.admin.sso.dao.UserAppTokenDao;
import com.sicheng.admin.sso.entity.UserAppToken;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.web.SpringContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 会员app token Service
 *
 * @author zhaolei
 * @version 2019-01-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserAppTokenService extends CrudService<UserAppTokenDao, UserAppToken> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    /**
     * 批量删除所有过期的AppToken
     * 30、清理过期的AppToken （定时任务会来调用本方法）
     * clearAppToken1()
     *
     * @return 成功清理apptoken的数量
     */
    @Transactional(rollbackFor = Exception.class)
    public int clearAppToken() {
        long t1 = getDefValidDate() * 60L * 60L * 24L * 1000L;//90天的毫秒数
        long t2 = System.currentTimeMillis();
        Wrapper w = new Wrapper();
        w.and("valid_date <", new Date(t2 - t1));//超出有效期
        UserAppTokenService userAppTokenService = SpringContextHolder.getBean(UserAppTokenService.class);
        int count = userAppTokenService.deleteByWhere(w);
        return count;
    }

    /**
     * AppToken的默认有效期,单位:天
     * @return
     */
    private int getDefValidDate() {
        try {
            String value = Global.getConfig("app.AppToken.def_valid_date");
            return Integer.valueOf(value);
        } catch (Exception e) {
            logger.error("Global.getConfig(\"app.AppToken.def_valid_date\")发生异常", e);
            return 90;
        }
    }
}