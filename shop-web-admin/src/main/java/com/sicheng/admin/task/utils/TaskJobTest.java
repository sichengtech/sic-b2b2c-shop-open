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
package com.sicheng.admin.task.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
  * <p>标题: TaskJobTest</p>
  * <p>描述: 定时任务的单元测试</p>
  *
  * @author zhangjiali
  * @version 2017年10月27日 下午6:15:21
  *
  */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class TaskJobTest {

    @Autowired
    private TaskJob taskJob;

    /**
      * 商品结算定时任务测试
      *
      */
    @Test
    public void settlement_product_test() {
        taskJob.settlementProductTask();
    }

    /**
      * 清理sysToken 过期token测试 
      *
      */
    @Test
    public void sys_token_task_test() {
        taskJob.sysTokenTask();
    }

    /**
      * 计算每个店铺的商品总数测试 
      *
      */
    @Test
    public void store_product_sum_test() {
        taskJob.storeProductSum();
    }

    /**
      * 清理30天前工程所有的日志测试 
      *
      */
    @Test
    public void clean_log_test() {
        taskJob.cleanLog();
    }

    /**
      * 修改会员收藏商品的状态测试 
      *
      */
    @Test
    public void update_collection_product_test() {
        taskJob.updateCollectionProduct();
    }

    /**
      * 商品 30天销量测试 
      *
      */
    @Test
    public void month_sales_test() {
        taskJob.monthSales();
    }

    /**
      * 管理后台首页的数据测试 
      *
      */
    @Test
    public void admin_index_info_test() {
        taskJob.adminIndexInfo();
    }

    /**
      * 查询并修改超过最晚收货时间的订单测试
      *
      */
    @Test
    public void update_trade_order_test() {
        taskJob.updateTradeOrder();
    }

    /**
      * 取消过期的订单(超过24小时未支付的订单)
      *
      */
    @Test
    public void cancle_expired_tradeOrder_test() {
        taskJob.cancleExpiredTradeOrder();
    }

    /**
      * 对账
      */
    @Test
    public void balanceAccount() {
        taskJob.balanceAccount();
    }

    /**
      * 修改超过最晚到期时间的供采模块的采购单与采购单详情
      */
    @Test
    public void updatePurchase() {
        taskJob.updatePurchase();
    }

    /**
      * 查询并修改超过接单截止时间的服务单
      */
    @Test
    public void updateAppServiceInfoMeta() {
        taskJob.updateAppServiceInfoMeta();
    }

    /**
      * 回收过期的优惠券
      */
    @Test
    public void updateCouponTask() {
        taskJob.updateCouponTask();
    }
}
