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

import com.sicheng.admin.settlement.dao.SettlementRechargeDao;
import com.sicheng.admin.settlement.entity.SettlementPreDeposit;
import com.sicheng.admin.settlement.entity.SettlementRecharge;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.sso.service.UserMemberService;

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

    @Transactional(rollbackFor = Exception.class)
    public void addRecharge(SettlementRecharge settlementRecharge, UserMember userMember) {
        //插入充值
        dao.insertSelective(settlementRecharge);
        if ("1".equals(settlementRecharge.getStaus())) {
            //更新userMember的余额
            BigDecimal balance = userMember.getBalance();
            if (balance == null) {
                balance = new BigDecimal("0");
            }
            BigDecimal balanceNew = settlementRecharge.getRechargeMoney().add(balance);
            userMember.setBalance(balanceNew);
            UserMember entity = userMemberService.selectById(userMember.getUId());
            userMemberService.updateByWhereSelective(userMember, new Wrapper(entity));
            //添加操作记录
            SettlementPreDeposit settlementPreDeposit = new SettlementPreDeposit();
            settlementPreDeposit.setUId(userMember.getUId());
            settlementPreDeposit.setAvailableMoney(userMember.getBalance());
            settlementPreDeposit.setFrozenMoney(userMember.getFrozenMoney());
            settlementPreDeposit.setOperationMoney(settlementRecharge.getRechargeMoney());
            settlementPreDeposit.setOperationDescribe(FYUtils.fyParams("充值,充值单号：") + settlementRecharge.getRechargeId());
            settlementPreDepositService.insertSelective(settlementPreDeposit);
        }
    }

}