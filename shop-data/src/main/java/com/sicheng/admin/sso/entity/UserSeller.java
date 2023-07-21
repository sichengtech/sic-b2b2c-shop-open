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

import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 会员（卖家） Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-04-25
 */
public class UserSeller extends UserSellerBase<UserSeller> {

    private static final long serialVersionUID = 1L;

    public UserSeller() {
        super();
    }

    public UserSeller(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    //会员主表
    private UserMain userMain;

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

    //卖家关联店铺表（一个卖家一个店铺）
    private Store store;

    public Store getStore() {
        if (store == null) {
            StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
            store = dao.selectById(this.getStoreId());
        }
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    //ListIdIn工具  在一个list中做 一对一，10个商家对10个店铺
    //填充店铺,把1+N改成1+1
    public static void fillStore(List<UserSeller> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao storeDao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> storeList = storeDao.selectByIdIn(ids);
        fill(storeList, "storeId", list, "storeId", "userMain");//循环填充
    }
}