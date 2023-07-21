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
import com.sicheng.admin.sso.dao.UserSellerDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 入驻申请（业务审核） Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-02-07
 */
public class StoreEnterAuth extends StoreEnterAuthBase<StoreEnterAuth> {

    private static final long serialVersionUID = 1L;

    public StoreEnterAuth() {
        super();
    }

    public StoreEnterAuth(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    //一对一映射
    private UserSeller userSeller;         //商家

    public UserSeller getUserSeller() {
        if (userSeller == null) {
            UserSellerDao dao = SpringContextHolder.getBean(UserSellerDao.class);
            userSeller = dao.selectById(this.getEnterId());
        }
        return userSeller;
    }

    public void setUserSeller(UserSeller userSeller) {
        this.userSeller = userSeller;
    }

    //一对一映射
    private UserMain userMain;         //会员总表

    public UserMain getUserMain() {
        if (userMain == null) {
            UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
            userMain = dao.selectById(this.getEnterId());
        }
        return userMain;
    }

    public void setUserMain(UserMain userMain) {
        this.userMain = userMain;
    }

    //ListIdIn工具  在一个list中做 一对一,商家
    //填充 xxx,把1+N改成1+1
    public static void fillUserMain(List<StoreEnterAuth> list) {
        List<Object> ids = batchField(list, "enterId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainlist = dao.selectByIdIn(ids);
        fill(userMainlist, "uId", list, "enterId", "userMain");//循环填充
    }

    /**
     * 分佣比例（去掉无用小数点0）
     */
    @Override
    public BigDecimal getCommission() {
        if (super.getCommission() == null) {
            return super.getCommission();
        }
        String commission = super.getCommission().stripTrailingZeros().toPlainString();
        return new BigDecimal(commission);
    }

    /**
     * 应付总金额（去掉无用小数点0）
     */
    @Override
    public BigDecimal getSummaryOfCoping() {
        if (super.getSummaryOfCoping() == null) {
            return super.getSummaryOfCoping();
        }
        String summaryOfCoping = super.getSummaryOfCoping().stripTrailingZeros().toPlainString();
        return new BigDecimal(summaryOfCoping);
    }

}