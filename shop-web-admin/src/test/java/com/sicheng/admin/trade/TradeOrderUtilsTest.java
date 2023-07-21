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
package com.sicheng.admin.trade;

import com.sicheng.admin.trade.utils.TradeOrderUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>标题: TradeOrderUtilsTest</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年3月21日 上午10:57:20
 */
@Transactional//表示测试完成后回滚事务 （mysql下对存储过程回滚无效）
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class TradeOrderUtilsTest {
    /**
     * 订单号生成测试
     */
    @Test
    public void test_creatOrderNumber() {
        System.out.println("第1次，生成的订单号是：" + TradeOrderUtils.creatOrderNumber("1"));
        System.out.println("第2次，生成的订单号是：" + TradeOrderUtils.creatOrderNumber("2"));
        System.out.println("第3次，生成的订单号是：" + TradeOrderUtils.creatOrderNumber("3"));
    }

    /**
     * 多线程并发测试 ，防止生成重复的订单号
     */
    @Test
    public void test_thread() throws InterruptedException {
        Thread.sleep(1 * 1000);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("多线程并发，生成的订单号是：" + TradeOrderUtils.creatOrderNumber("2"));
                }
            }).start();
        }
        Thread.sleep(5 * 1000);
    }
}
