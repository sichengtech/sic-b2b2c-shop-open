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
package com.sicheng.admin.settlement.service;

import com.sicheng.admin.settlement.dao.SettlementWithdrawalsDao;
import com.sicheng.admin.settlement.entity.SettlementPreDeposit;
import com.sicheng.admin.settlement.entity.SettlementWithdrawals;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sso.service.UserMemberService;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 提现 Service
 *
 * @author 范秀秀
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

    //审核
    @Transactional(rollbackFor = Exception.class)
    public void examineWithdrawals(SettlementWithdrawals settlementWithdrawals) {
        //更新提现记录
        this.updateByIdSelective(settlementWithdrawals);
        //已支付
        //更新用户的冻结余额(减冻结金额)
        UserMain userMain = settlementWithdrawals.getUserMain();
        if (userMain != null) {
            UserMember userMember = userMain.getUserMember();
            if (userMember != null) {
                BigDecimal frozenMoney = new BigDecimal("0");
                if (userMember.getFrozenMoney() != null) {
                    frozenMoney = userMember.getFrozenMoney();
                }
                userMember.setFrozenMoney(frozenMoney.add(settlementWithdrawals.getMoney()));
                //提现状态，0未支付、1已支付、2拒绝提现
                if ("2".equals(settlementWithdrawals.getStatus())) {
                    //更新用户的冻结余额(减冻结金额,加余额)
                    //Double balance=0D;
                    BigDecimal balance = new BigDecimal("0");
                    if (userMember.getBalance() != null) {
                        balance = userMember.getBalance();
                    }
                    userMember.setBalance(balance.add(settlementWithdrawals.getMoney()));
                    //添加操作记录
                    SettlementPreDeposit settlementPreDeposit = new SettlementPreDeposit();
                    settlementPreDeposit.setUId(userMember.getUId());
                    settlementPreDeposit.setAvailableMoney(userMember.getBalance());
                    settlementPreDeposit.setFrozenMoney(userMember.getFrozenMoney());
                    settlementPreDeposit.setOperationMoney(settlementWithdrawals.getMoney());
                    settlementPreDeposit.setOperationDescribe("拒绝提现,提现单号：" + settlementWithdrawals.getWithdrawalsId());
                    settlementPreDepositService.insertSelective(settlementPreDeposit);
                }
                userMemberService.updateById(userMember);
            }
        }
    }
}