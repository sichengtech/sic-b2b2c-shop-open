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

import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.common.persistence.CrudDao;
import com.sicheng.common.persistence.annotation.MyBatisDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 订单详情DAO接口
 *
 * @author 范秀秀
 * @version 2017-01-06
 */
@MyBatisDao
public interface TradeOrderItemDao extends CrudDao<TradeOrderItem> {
    //请在这里增加你自己的DAO层方法
    //14条单表操作的通用SQL调用方法都在父类中，全继承下来了，可直接使用。

    /**
     * 商品 最近30天销量
     *
     * @param wrapper
     * @return
     */
    Integer sumByWhere(@Param(value = "w") Wrapper wrapper);

    /**
     * 根据店铺id获取指定时间内该店铺销售总额，不指定时间则不限时间
     *
     * @param storeId
     * @param date
     * @return
     */
    Integer countSalesByStoreId(@Param("storeId") Long storeId, @Param("date") Date date);
}