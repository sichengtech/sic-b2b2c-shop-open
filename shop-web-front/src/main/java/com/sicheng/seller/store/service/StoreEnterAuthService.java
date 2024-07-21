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
package com.sicheng.seller.store.service;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.dao.StoreEnterAuthDao;
import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.common.service.CrudService;
import com.sicheng.sso.service.UserSellerService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

/**
 * 入驻申请（业务审核） Service
 *
 * @author cl
 * @version 2017-01-11
 */
@Service
@Lazy
@Transactional(propagation = Propagation.SUPPORTS)
public class StoreEnterAuthService extends CrudService<StoreEnterAuthDao, StoreEnterAuth> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

//    @Autowired
//    private StoreEnterAuthService storeEnterAuthService;

    @Autowired
    private StoreEnterService storeEnterService;

    @Autowired
    private UserSellerService userSellerService;

    /**
     * 入驻商家插入记录
     */
    @Transactional(rollbackFor = Exception.class)
    public void save() {
        UserMain userMain = SsoUtils.getUserMain();
        UserSeller us = SsoUtils.getUserMain().getUserSeller();
        if (us == null) {
            //卖家表插入一条记录
            UserSeller userSeller = new UserSeller();
            userSeller.setPkMode(1);
            userSeller.setUId(userMain.getUId());
            userSellerService.insertSelective(userSeller);
            //入驻申请表插入一条记录
            StoreEnterAuth storeEnterAuth = new StoreEnterAuth();
            storeEnterAuth.setPkMode(1);
            storeEnterAuth.setEnterId(userMain.getUId());
            storeEnterAuth.setIsPerfect("0");
            this.insertSelective(storeEnterAuth);
        }
    }

    /**
     * 商家后台入驻信息还原
     *
     * @param storeEnterAuth
     * @param auth
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void reduction(StoreEnterAuth storeEnterAuth) {
        //删除入驻审核里面的数据
        this.deleteById(storeEnterAuth.getEnterId());
        //把入驻审核（查看表中的数据复制到审核表中）
        //利用BeanUtils进行赋值对象，赋值到StoreEnter中进行存储
        ConvertUtils.register(new DateLocaleConverter(), Date.class);//注册转化器，将按照本地格式转化为日期对象
        StoreEnterAuth sea = new StoreEnterAuth();
        StoreEnter s = storeEnterService.selectById(storeEnterAuth.getEnterId());
        try {
            BeanUtils.copyProperties(sea, s);
        } catch (Exception e) {
            logger.error("BeanUtils复制对象出错", e);
        }
        sea.setPkMode(1);
        this.insertSelective(sea);
    }
}