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
package com.sicheng.admin.trade.dao;

import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.common.persistence.CrudDao;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.annotation.MyBatisDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单DAO接口
 *
 * @author 范秀秀
 * @version 2017-01-05
 */
@MyBatisDao
public interface TradeOrderDao extends CrudDao<TradeOrder> {
    //请在这里增加你自己的DAO层方法
    //14条单表操作的通用SQL调用方法都在父类中，全继承下来了，可直接使用。


    /**
     * oracle方案,生成订单号
     * 获取自己增长的主键，用于生成订单号
     * 调用Oracle库的getOrderNum()函数（）
     * 使用两个序列：TRADE_ORDER_SEQ_A、TRADE_ORDER_SEQ_B，一个在被使用，一个在被清0，每天交换使用
     * 每天0点，由定时任务调用ResetOrderNumSeq存储过程，来清0其中一个未在使用序列。
     *
     * @return
     */
    Long generateOrderNumber4Oracle();

    /**
     * mysql方案,生成订单号
     * 获取自己增长的主键，用于生成订单号
     * 此语法目前只支持mysql
     * 插入。并返回自增长的主键值
     *
     * @param param
     * @return
     */
    Long generateOrderNumber4Mysql(Map<String, Object> param);

    /**
     * 成交额
     *
     * @param wrapper
     * @return
     */
    Map<String, Object> sumByWhere(@Param(value = "w") Wrapper wrapper);

    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    public List<TradeOrder> joinSelectByWhere(Page<TradeOrder> page, Wrapper wrapper);

}