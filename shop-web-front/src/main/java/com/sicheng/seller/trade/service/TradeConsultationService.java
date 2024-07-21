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
package com.sicheng.seller.trade.service;

import com.google.common.collect.Lists;
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
 * @author fxx
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
     * 根据ReplyId查询咨询的回复列表
     *
     * @param list ReplyId集合
     * @return
     */
    public List<TradeConsultation> selectByReplyIdIn(List<Object> list) {
        return tradeConsultationDao.selectByReplyIdIn(list);
    }

    /**
     * 保存咨询的回复内容并更新此条咨询记录
     *
     * @param tradeConsultation 咨询管理
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveAndEdit(TradeConsultation tradeConsultation) {
        //插入回复的咨询数据
        super.insertSelective(tradeConsultation);
        //修改被回复的咨询信息
        TradeConsultation consultation = new TradeConsultation();
        consultation.setIsReply("1");//是否回复，0否、1是
        TradeConsultation condition = new TradeConsultation();
        condition.setConsultationId(tradeConsultation.getReplyId());
        condition.setStoreId(tradeConsultation.getStoreId());
        super.updateByWhereSelective(consultation, new Wrapper(condition));//属主检查
    }

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
        consultation.setStoreId(tradeConsultation.getStoreId());
        List<TradeConsultation> consultationList = tradeConsultationDao.selectByWhere(null, new Wrapper(consultation));//属主检查
        if (!consultationList.isEmpty()) {
            tradeConsultationDao.deleteById(consultationList.get(0).getConsultationId());
        }
        //删除咨询
        tradeConsultationDao.deleteByWhere(new Wrapper(tradeConsultation));
    }

    /**
     * 批量删除咨询，并删除它的回复内容
     *
     * @param list 咨询id的list
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Object> list, Long storeId) {
        //批量删除该咨询的回复内容
        List<TradeConsultation> consultationts = selectByReplyIdIn(list);
        List<Object> listId = Lists.newArrayList();
        if (consultationts != null && !consultationts.isEmpty()) {
            for (int i = 0; i < consultationts.size(); i++) {
                listId.add(consultationts.get(i).getConsultationId());
            }
        }
        super.deleteByIdIn(listId);
        //批量删除咨询
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.consultation_id in", list);
        wrapper.and("a.store_id = ", storeId);
        super.deleteByWhere(wrapper);
    }
}