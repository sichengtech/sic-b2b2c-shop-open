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
package com.sicheng.admin.store.entity;

import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.dao.StoreDao;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;
import java.util.Map;

/**
 * 店铺管理员操作日志 Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-02-07
 */
public class StoreAdminLog extends StoreAdminLogBase<StoreAdminLog> {

    private static final long serialVersionUID = 1L;

    public StoreAdminLog() {
        super();
    }

    public StoreAdminLog(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里


    // 日志类型（1：接入日志；2：错误日志）
    public static final String TYPE_ACCESS = "1";
    public static final String TYPE_EXCEPTION = "2";

    /**
     * 设置请求参数
     *
     * @param paramMap
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setParams(Map paramMap) {
        if (paramMap == null) {
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        this.setParams(params.toString());
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    //一对一映射
    private UserMain userMain; // 一条日志 一个会员

    public UserMain getUserMain() {
        if (userMain == null) {
            UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
            userMain = dao.selectById(this.getOperatorId());
        }
        return userMain;
    }

    public void setUserMain(UserMain userMain) {
        this.userMain = userMain;
    }

    //ListIdIn工具  在一个list中做 一对一，10个日志对10个会员
    //填充 xxx,把1+N改成1+1
    public static void fillUserMain(List<StoreAdminLog> list) {
        List<Object> ids = batchField(list, "operatorId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMains = dao.selectByIdIn(ids);
        fill(userMains, "uId", list, "operatorId", "userMain");//循环填充
    }

    //一对一映射
    private Store store; // 一条日志 一个店铺

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

    //ListIdIn工具  在一个list中做 一对一，10个日志对10个商铺管理者
    //填充 xxx,把1+N改成1+1
    public static void fillStore(List<StoreAdminLog> list) {
        List<Object> ids = batchField(list, "storeId");//批量调用对象的getXxx()方法
        StoreDao dao = SpringContextHolder.getBean(StoreDao.class);
        List<Store> stores = dao.selectByIdIn(ids);
        fill(stores, "storeId", list, "storeId", "store");//循环填充
    }
}