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
     * 1、对商家结算定时任务
     * 商品结算定时任务测试
     */
    @Test
    public void settlement_product_test() {
        taskJob.settlementProductTask();
    }

    /**
     * 2、清理过期的sysToken
     * 清理sysToken 过期token测试
     */
    @Test
    public void sys_token_task_test() {
        taskJob.sysTokenTask();
    }

    /**
     * 3、计算每个店铺的商品总数
     * 计算每个店铺的商品总数测试
     */
    @Test
    public void store_product_sum_test() {
        taskJob.storeProductSum();
    }

    /**
     * 4、清理超过30天的日志（删除日志表中的记录）
     * 清理30天前工程所有的日志测试
     */
    @Test
    public void clean_log_test() {
        taskJob.cleanLog();
    }

    /**
     * 5、维护会员收藏商品的状态（0下架，1上架）
     * 修改会员收藏商品的状态测试
     */
    @Test
    public void update_collection_product_test() {
        taskJob.updateCollectionProduct();
    }

    /**
     * 6、更新商品30天销量
     * 商品 30天销量测试
     */
    @Test
    public void month_sales_test() {
        taskJob.monthSales();
    }

    /**
     * 7、定时更新管理后台首页的统计数据
     * 管理后台首页的数据 测试
     */
    @Test
    public void admin_index_info_test() {
        taskJob.adminIndexInfo();
    }

    /**
     * 8、查询并修改超过最晚收货时间的订单
     * 超过了最晚收货时间,将订单状态修改为已收货待评价
     * 查询并修改超过最晚收货时间的订单测试
     */
    @Test
    public void update_trade_order_test() {
        taskJob.updateTradeOrder();
    }

    /**
     * 9、处理超时订单。
     * 判断订单是否超过24小时未支付，若是则取消订单，释放库存。
     * 取消过期的订单(超过24小时未支付的订单)
     */
    @Test
    public void cancle_expired_tradeOrder_test() {
        taskJob.cancleExpiredTradeOrder();
    }

    /**
      * 10、定时执行对账任务
      */
    @Test
    public void balanceAccount() {
        taskJob.balanceAccount();
    }

    /**
     * 12、处理超时的采购单。
     * 查询超时的供采模块的采购单（20.审核未通过，30.待拆分，35.报价中，40.交易中），修改状态为50完成交易
     */
    @Test
    public void updatePurchase() {
        taskJob.updatePurchase();
    }

    /**
     * 13、处理超时的服务单。【目前无用】
     * 查询并修改超过接单截止时间的服务单
      */
    @Test
    public void updateAppServiceInfoMeta() {
        taskJob.updateAppServiceInfoMeta();
    }

    /**
     * 16、回收过期的优惠券   【目前无用】
     */
    @Test
    public void updateCouponTask() {
        taskJob.updateCouponTask();
    }

    /**
     * 32、定时生成Solr索引（重建）
     */
    @Test
    public void creatSolrIndex() {
        taskJob.creatSolrIndex();
    }
}
