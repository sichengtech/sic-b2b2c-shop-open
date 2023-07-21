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

import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sys.dao.UserDao;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预存款明细 Entity 子类，请把你的业务代码写在这里
 *
 * @author 范秀秀
 * @version 2017-02-07
 */
public class SettlementPreDeposit extends SettlementPreDepositBase<SettlementPreDeposit> {

    private static final long serialVersionUID = 1L;

    public SettlementPreDeposit() {
        super();
    }

    public SettlementPreDeposit(Long id) {
        super(id);
    }

    //一对多映射
    private UserMain userMain;//一条预存款明细--一个会员

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
    public static void fillUserMain(List<SettlementPreDeposit> list) {
        List<Object> ids = batchField(list, "uId");//批量调用对象的getXxx()方法
        UserMainDao dao = SpringContextHolder.getBean(UserMainDao.class);
        List<UserMain> userMainList = dao.selectByIdIn(ids);
        fill(userMainList, "uId", list, "uId", "userMain");//循环填充
    }

    private User user;//一条预存款明细--一个管理员

    public User getUser() {
        if (user == null) {
            UserDao dao = SpringContextHolder.getBean(UserDao.class);
            user = dao.selectById(this.getAdminId());
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //ListIdIn工具  在一个list中做 一对一，10条预存款明细对10个管理员
    //填充 xxx,把1+N改成1+1
    public static void fillUser(List<SettlementPreDeposit> list) {
        List<Object> ids = batchField(list, "adminId");//批量调用对象的getXxx()方法
        UserDao dao = SpringContextHolder.getBean(UserDao.class);
        List<User> userlist = dao.selectByIdIn(ids);
        fill(userlist, "id", list, "adminId", "user");//循环填充
    }

    /**
     * 可用金额
     */
    @Override
    public BigDecimal getAvailableMoney() {
        if (super.getAvailableMoney() == null) {
            return super.getAvailableMoney();
        }
        String availableMoney = super.getAvailableMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(availableMoney);
    }

    /**
     * 冻结金额
     */
    @Override
    public BigDecimal getFrozenMoney() {
        if (super.getFrozenMoney() == null) {
            return super.getFrozenMoney();
        }
        String billAmount = super.getFrozenMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(billAmount);
    }

    /**
     * 操作金额
     */
    @Override
    public BigDecimal getOperationMoney() {
        if (super.getOperationMoney() == null) {
            return super.getOperationMoney();
        }
        String billAmount = super.getOperationMoney().stripTrailingZeros().toPlainString();
        return new BigDecimal(billAmount);
    }

}