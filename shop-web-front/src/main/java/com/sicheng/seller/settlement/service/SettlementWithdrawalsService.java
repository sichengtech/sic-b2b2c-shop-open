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
package com.sicheng.seller.settlement.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.settlement.dao.SettlementWithdrawalsDao;
import com.sicheng.admin.settlement.entity.SettlementPreDeposit;
import com.sicheng.admin.settlement.entity.SettlementWithdrawals;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.sso.service.UserMemberService;

/**
 * 提现 Service
 *
 * @author fxx
 * @version 2017-02-07
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SettlementWithdrawalsService extends CrudService<SettlementWithdrawalsDao, SettlementWithdrawals> {

//    @Autowired
//    private SettlementWithdrawalsService settlementWithdrawalsService;
    @Autowired
    private UserMemberService userMemberService;
    @Autowired
    private SettlementPreDepositService settlementPreDepositService;


    @Transactional(rollbackFor = Exception.class)
    public void addWithdrawals(SettlementWithdrawals settlementWithdrawals, UserMember userMember) {
        //插入提现信息
        this.insertSelective(settlementWithdrawals);
        //更新用户的余额（减余额）
        BigDecimal balance = new BigDecimal("0");
        if (userMember.getBalance() != null) {
            balance = userMember.getBalance();
        }
        userMember.setBalance(balance.subtract(settlementWithdrawals.getMoney()));
        BigDecimal frozenMoney = new BigDecimal("0");
        if (userMember.getFrozenMoney() != null) {
            frozenMoney = userMember.getFrozenMoney();
        }
        //加冻结金额
        userMember.setFrozenMoney(frozenMoney.add(settlementWithdrawals.getMoney()));
        userMemberService.updateById(userMember);
        //添加操作记录
        SettlementPreDeposit settlementPreDeposit = new SettlementPreDeposit();
        settlementPreDeposit.setUId(userMember.getUId());
        settlementPreDeposit.setAvailableMoney(userMember.getBalance());
        settlementPreDeposit.setFrozenMoney(userMember.getFrozenMoney());
        settlementPreDeposit.setOperationMoney(settlementWithdrawals.getMoney());
        settlementPreDeposit.setOperationDescribe(FYUtils.fyParams("提现,提现单号：") + settlementWithdrawals.getWithdrawalsId());
        settlementPreDepositService.insertSelective(settlementPreDeposit);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteWithdrawals(SettlementWithdrawals settlementWithdrawals, UserMember userMember) {
        //更新用户的余额（加余额）
        BigDecimal balance = new BigDecimal("0");
        if (userMember.getBalance() != null) {
            balance = userMember.getBalance();
        }
        userMember.setBalance(balance.add(settlementWithdrawals.getMoney()));
        //减冻结金额
        BigDecimal frozenMoney = new BigDecimal("0");
        if (userMember.getFrozenMoney() != null) {
            frozenMoney = userMember.getFrozenMoney();
        }
        userMember.setFrozenMoney(frozenMoney.subtract(settlementWithdrawals.getMoney()));
        userMemberService.updateById(userMember);
        //添加操作记录
        SettlementPreDeposit settlementPreDeposit = new SettlementPreDeposit();
        settlementPreDeposit.setUId(userMember.getUId());
        settlementPreDeposit.setAvailableMoney(userMember.getBalance());
        settlementPreDeposit.setFrozenMoney(userMember.getFrozenMoney());
        settlementPreDeposit.setOperationMoney(settlementWithdrawals.getMoney());
        settlementPreDeposit.setOperationDescribe(FYUtils.fyParams("删除提现,删除提现单号：")+ settlementWithdrawals.getWithdrawalsId());
        settlementPreDepositService.insertSelective(settlementPreDeposit);
        //删除提现信息
        this.deleteByWhere(new Wrapper(settlementWithdrawals));
    }
}