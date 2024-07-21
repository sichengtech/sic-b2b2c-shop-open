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

import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 会员通知 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-02-06
 */
public class SysMessage extends SysMessageBase<SysMessage> {

    private static final long serialVersionUID = 1L;

    public SysMessage() {
        super();
    }

    public SysMessage(Long id) {
        super(id);
    }

    //一对一映射
    private UserMain userMain;//一条消息--一个会员

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

    //ListIdIn工具  在一个list中做 一对一，10个一条预存款明细对10个用户
    //填充 xxx,把1+N改成1+1
    public static void fillUserMain(List<SysMessage> list) {
        List<Object> ids = batchField(list, "uId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainList = dao.selectByIdIn(ids);
        fill(userMainList, "uId", list, "uId", "userMain");//循环填充
    }

    //一对一映射
    private Store store; //一个店铺

    public Store getStore() {
        if (store == null) {
            if (getUserMain().getUserSeller() != null) {
                store = getUserMain().getUserSeller().getStore();
            }
        }
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

//	//ListIdIn工具  在一个list中做 一对一
//	//填充店铺,把1+N改成1+1
//	public static void fillStore(List<SysMessage> list){
//		List<Object> ids=batchField(list,"uId");//批量调用对象的getXxx()方法
//		UserSellerDao userSellerDao=SpringContextHolder.getBean(UserSellerDao.class);
//		List<UserSeller> userSellerList=userSellerDao.selectByIdIn(ids);
//		UserSeller.fillStore(userSellerList);
//	}

}