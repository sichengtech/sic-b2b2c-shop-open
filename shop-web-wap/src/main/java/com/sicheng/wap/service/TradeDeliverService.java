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
package com.sicheng.wap.service;

import com.sicheng.admin.trade.dao.TradeDeliverDao;
import com.sicheng.admin.trade.entity.TradeDeliver;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 发票 Service
 *
 * @author fxx
 * @version 2017-01-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeDeliverService extends CrudService<TradeDeliverDao, TradeDeliver> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    /**
     * 新增
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveTradeDeliver(TradeDeliver tradeDeliver) {
        Long uId = tradeDeliver.getUId();
        String hbjbuyer = tradeDeliver.getHbjbuyer();//默认，0否、1是
        if ("1".equals(hbjbuyer)) {
            TradeDeliver td1 = new TradeDeliver();
            TradeDeliver td2 = new TradeDeliver();
            td1.setUId(uId);
            td2.setHbjbuyer("0");
            super.updateByWhereSelective(td2, new Wrapper(td1));
        }
        super.insertSelective(tradeDeliver);
    }

    /**
     * 编辑
     */
    @Transactional(rollbackFor = Exception.class)
    public void editTradeDeliver(TradeDeliver tradeDeliver) {
        Long uId = tradeDeliver.getUId();
        String hbjbuyer = tradeDeliver.getHbjbuyer();//默认，0否、1是
        if ("1".equals(hbjbuyer)) {
            TradeDeliver td1 = new TradeDeliver();
            TradeDeliver td2 = new TradeDeliver();
            td1.setUId(uId);
            td2.setHbjbuyer("0");
            super.updateByWhereSelective(td2, new Wrapper(td1));
        }
        TradeDeliver entity = new TradeDeliver();
        entity.setUId(uId);//属主检查
        entity.setDeliverId(tradeDeliver.getDeliverId());
        super.updateByWhereSelective(tradeDeliver, new Wrapper(entity));
    }

}