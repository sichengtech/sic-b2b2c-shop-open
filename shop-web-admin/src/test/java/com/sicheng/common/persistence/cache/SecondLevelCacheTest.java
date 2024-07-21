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
package com.sicheng.common.persistence.cache;

import com.sicheng.common.web.SpringContextHolder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>标题: SecondLevelCacheTest 单元测试</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年3月2日 下午5:57:43
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class SecondLevelCacheTest {

    /**
     * Mybatis二级缓存测试
     */
    @Test
    public void test_1() {
        //注意这个类是 多实例，才能满足mybatis的要求
        SecondLevelCache cache = SpringContextHolder.getBean(SecondLevelCache.class);
        cache.init("cacheName1", 3);//初始化
        cache.putObject("test.100", "123");
        Object obj = cache.getObject("test.100");
        Assert.assertEquals("123", obj);
    }

    /**
     * Mybatis二级缓存测试
     *
     * @throws InterruptedException
     */
    @Test
    public void test_2() throws InterruptedException {
        //注意这个类是 多实例，才能满足mybatis的要求
        SecondLevelCache cache = SpringContextHolder.getBean(SecondLevelCache.class);
        cache.init("cacheName2", 3);//初始化
        cache.putObject("test.100", "123");
        Object obj = cache.getObject("test.100");
        Assert.assertEquals("123", obj);
        Thread.sleep(4000);
        Object obj2 = cache.getObject("test.100");
        Assert.assertNull(obj2);
    }
}