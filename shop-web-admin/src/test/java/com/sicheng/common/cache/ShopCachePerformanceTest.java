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
package com.sicheng.common.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>标题: ShopCacheTest</p>
 * <p>描述: ShopCache缓存性能测试</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年6月28日 下午12:13:20
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ShopCachePerformanceTest {

    @Autowired
    private ShopCache shopCache;

    /**
     * 并发测试
     *
     * @throws InterruptedException
     */
    @Test
    public void performanceTest() throws InterruptedException {
        int threads = 2;//线程数
        final int times = 10;//每个任务执行的次数

        //预热
        System.out.println("===预热开始===");
        //ProductSpu spu=new ProductSpu();
        String spu = "abc-2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms";
        JedisUtils.set("ceshi1", spu, 10000);
        JedisUtils.get("ceshi1");
        System.out.println("===预热完成===");
        Thread.sleep(1 * 1000);
        long t5 = System.currentTimeMillis();
        List<Thread> threadList = new ArrayList<Thread>();
        for (int i = 0; i < threads; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    long t1 = System.currentTimeMillis();
                    for (int j = 0; j < times; j++) {
                        Object s = JedisUtils.get("ceshi1");
                        //System.out.println("value："+s);
                    }
                    long t2 = System.currentTimeMillis();
                    System.out.println("单个循环的时间：" + (t2 - t1) + "ms");
                }
            });
            threadList.add(t);
            t.start();
        }

        for (Thread iThread : threadList) {
            try {
                iThread.join(); // 等待所有线程执行完毕
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long t6 = System.currentTimeMillis();
        System.out.println("主线执行结束,子线程数：" + threads + ",每线程执行次数:" + times + ",总耗时:" + (t6 - t5) + "ms,QPS:" + (((float) (threads * times)) / (t6 - t5) * 1000));
    }

    /**
     * 并发测试
     *
     * @throws InterruptedException
     */
    @Test
    public void performanceTest2() throws InterruptedException {
        int threads = 2;//线程数
        final int times = 10;//每个任务执行的次数

        //预热
        System.out.println("===预热开始===");
        //ProductSpu spu=new ProductSpu();
        String spu = "abc-2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms2017-10-15 18:15:09,937 [Thread-5] DEBUG [com.sicheng.common.cache.RedisShopCache] - ShopCache-Redis get,key=ceshi1,1ms";
        shopCache.put("ceshi1", spu, 10000);
        shopCache.get("ceshi1");
        System.out.println("===预热完成===");
        Thread.sleep(1 * 1000);
        long t5 = System.currentTimeMillis();
        List<Thread> threadList = new ArrayList<Thread>();
        for (int i = 0; i < threads; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    long t1 = System.currentTimeMillis();
                    for (int j = 0; j < times; j++) {
                        Object s = shopCache.get("ceshi1");
                        //System.out.println("value："+s);
                    }
                    long t2 = System.currentTimeMillis();
                    System.out.println("单个循环的时间：" + (t2 - t1) + "ms");
                }
            });
            threadList.add(t);
            t.start();
        }

        for (Thread iThread : threadList) {
            try {
                iThread.join(); // 等待所有线程执行完毕
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long t6 = System.currentTimeMillis();
        System.out.println("主线执行结束,子线程数：" + threads + ",每线程执行次数:" + times + ",总耗时:" + (t6 - t5) + "ms,QPS:" + (((float) (threads * times)) / (t6 - t5) * 1000));
    }
}
