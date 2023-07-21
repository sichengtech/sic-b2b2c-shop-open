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

import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.settlement.entity.SettlementPayWayAttr;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 支付方式 Service
 *
 * @author 范秀秀
 * @version 2017-02-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SettlementPayWayService extends CrudService<SettlementPayWayDao, SettlementPayWay> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private SettlementPayWayAttrService payWayAttrService;

    /**
     *  添加与修改支付方式 
     *  @param payWay
     *  @param payWayAttrList
     */
    @Transactional(rollbackFor = Exception.class)
    public void savePayWay(SettlementPayWay payWay, List<SettlementPayWayAttr> payWayAttrList) {
        if (payWay == null) {
            return;
        }
        //id为空时，添加新数据，否则更新数据
        if (payWay.getPayWayId() == null) {
            super.insertSelective(payWay);
        } else {
            super.updateByIdSelective(payWay);
            //删除支付方式属性，重新添加
            payWayAttrService.deleteByWhere(new Wrapper().and("pay_way_id =", payWay.getPayWayId()));
        }
        //批量添加支付方式属性
        if (payWayAttrList != null) {
            for (int i = 0; i < payWayAttrList.size(); i++) {
                payWayAttrList.get(i).setPayWayId(payWay.getPayWayId());
            }
        }
        payWayAttrService.insertSelectiveBatch(payWayAttrList);
    }

    /**
     *  删除支付方式并删除支付方式属性 
     *  @param payWayId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePayWay(Long payWayId) {
        if (payWayId == null) {
            return;
        }
        super.deleteById(payWayId);
        payWayAttrService.deleteByWhere(new Wrapper().and("pay_way_id =", payWayId));
    }

}