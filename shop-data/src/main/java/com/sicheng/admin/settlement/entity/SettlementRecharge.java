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
package com.sicheng.admin.settlement.entity;

import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sys.dao.UserDao;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 充值 Entity 子类，请把你的业务代码写在这里
 *
 * @author 范秀秀
 * @version 2017-02-06
 */
public class SettlementRecharge extends SettlementRechargeBase<SettlementRecharge> {

    private static final long serialVersionUID = 1L;

    public SettlementRecharge() {
        super();
    }

    public SettlementRecharge(Long id) {
        super(id);
    }

    /**
     * 获取会员信息
     */
    private UserMain userMain;

    public void setUserMain(UserMain userMain) {
        this.userMain = userMain;
    }

    //一对一映射
    public UserMain getUserMain() {
        if (userMain == null) {
            UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
            userMain = dao.selectById(this.getUId());
        }
        return userMain;
    }

    //ListIdIn工具  在一个list中做 一对一，多个充值记录对应多个会员
    //填充 xxx,把1+N改成1+1
    public static void findUserMain(List<SettlementRecharge> list) {
        List<Object> ids = batchField(list, "uId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainList = dao.selectByIdIn(ids);
        fill(userMainList, "uId", list, "uId", "userMain");
    }

    /**
     * 获取支付方式信息
     */
    private SettlementPayWay settlementPayWay;

    public void setSettlementPayWay(SettlementPayWay settlementPayWay) {
        this.settlementPayWay = settlementPayWay;
    }

    //一对一映射
    public SettlementPayWay getSettlementPayWay() {
        if (settlementPayWay == null) {
            SettlementPayWayDao dao = SpringContextHolder.getBean(SettlementPayWayDao.class);
            settlementPayWay = dao.selectById(this.getPayWayId());
        }
        return settlementPayWay;
    }

    //ListIdIn工具  在一个list中做 一对一，多个充值记录对应多个支付方式
    //填充 xxx,把1+N改成1+1
    public static void findSettlementPayWay(List<SettlementRecharge> list) {
        List<Object> ids = batchField(list, "payWayId");//批量调用对象的getXxx()方法
        SettlementPayWayDao dao = SpringContextHolder.getBean(SettlementPayWayDao.class);
        List<SettlementPayWay> settlementPayWayList = dao.selectByIdIn(ids);
        fill(settlementPayWayList, "payWayId", list, "payWayId", "settlementPayWay");
    }

    /**
     * 获取后台管理员信息
     */
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    //一对一映射
    public User getUser() {
        if (user == null) {
            UserDao dao = SpringContextHolder.getBean(UserDao.class);
            user = dao.selectById(this.getAdminId());
        }
        return user;
    }

    //ListIdIn工具  在一个list中做 一对一，多个充值记录对应多个会员
    //填充 xxx,把1+N改成1+1
    public static void findUser(List<SettlementRecharge> list) {
        List<Object> ids = batchField(list, "adminId");//批量调用对象的getXxx()方法
        UserDao dao = SpringContextHolder.getBean(UserDao.class);
        List<User> userList = dao.selectByIdIn(ids);
        fill(userList, "id", list, "adminId", "user");
    }

    /**
     * 充值金额
     */
    @Override
    public BigDecimal getRechargeMoney() {
        if (super.getRechargeMoney() == null) {
            return super.getRechargeMoney();
        }
        String rechargeMoney = super.getRechargeMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(rechargeMoney);
    }
}