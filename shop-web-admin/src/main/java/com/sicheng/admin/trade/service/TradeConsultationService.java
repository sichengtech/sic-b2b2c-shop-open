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
package com.sicheng.admin.trade.service;

import com.sicheng.admin.trade.dao.TradeConsultationDao;
import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 咨询 Service
 *
 * @author 范秀秀
 * @version 2017-01-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TradeConsultationService extends CrudService<TradeConsultationDao, TradeConsultation> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private TradeConsultationDao tradeConsultationDao;

    /**
     * 删除单条咨询，并删除它的回复内容
     *
     * @param tradeConsultation 咨询管理
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteConsultation(TradeConsultation tradeConsultation) {
        //删除该咨询的回复内容
        TradeConsultation consultation = new TradeConsultation();
        consultation.setReplyId(tradeConsultation.getConsultationId());
        List<TradeConsultation> consultationList = tradeConsultationDao.selectByWhere(null, new Wrapper(consultation));
        if (consultationList.size() > 0) {
            tradeConsultationDao.deleteById(consultationList.get(0).getConsultationId());
        }
        //删除咨询
        tradeConsultationDao.deleteById(tradeConsultation.getConsultationId());
    }
}