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
package com.sicheng.admin.settlement.service;

import com.sicheng.admin.settlement.dao.SettlementRechargeDao;
import com.sicheng.admin.settlement.entity.SettlementPreDeposit;
import com.sicheng.admin.settlement.entity.SettlementRecharge;
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
 * 充值 Service
 *
 * @author fxx
 * @version 2017-02-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SettlementRechargeService extends CrudService<SettlementRechargeDao, SettlementRecharge> {

    @Autowired
    private UserMemberService userMemberService;
    @Autowired
    private SettlementPreDepositService settlementPreDepositService;

    //审核
    @Transactional(rollbackFor = Exception.class)
    public void examineRecharge(SettlementRecharge settlementRecharge) {
        //更新提现记录
        dao.updateByIdSelective(settlementRecharge);
        //已支付
        //更新用户的余额(加余额)
        UserMain userMain = settlementRecharge.getUserMain();
        if (userMain != null) {
            UserMember userMember = userMain.getUserMember();
            if (userMember != null) {
                BigDecimal balance = new BigDecimal("0");
                if (userMember.getBalance() != null) {
                    balance = userMember.getBalance();
                }
                userMember.setBalance(balance.add(settlementRecharge.getRechargeMoney()));
                userMemberService.updateById(userMember);
                //添加操作记录
                SettlementPreDeposit settlementPreDeposit = new SettlementPreDeposit();
                settlementPreDeposit.setUId(userMember.getUId());
                settlementPreDeposit.setAvailableMoney(userMember.getBalance());
                settlementPreDeposit.setFrozenMoney(userMember.getFrozenMoney());
                settlementPreDeposit.setOperationMoney(settlementRecharge.getRechargeMoney());
                settlementPreDeposit.setOperationDescribe("充值,充值单号：" + settlementRecharge.getRechargeId());
                settlementPreDepositService.insertSelective(settlementPreDeposit);
            }
        }
    }

}