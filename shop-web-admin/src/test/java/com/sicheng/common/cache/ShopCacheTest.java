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
package com.sicheng.common.cache;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>标题: ShopCacheTest</p>
 * <p>描述: ShopCache缓存接口测试用例</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年6月28日 下午12:13:20
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ShopCacheTest {

    @Autowired
    private ShopCache shopCache;

    /**
     * 测试put
     */
    @Test
    public void putTest() {
        Assert.assertFalse(shopCache.put(null, null, 0));
        Assert.assertTrue(shopCache.put("ceshi1", "value", 100));
    }

    /**
     * 测试get
     */
    @Test
    public void getTest() {
        shopCache.put("ceshi1", "value", 100);
        Assert.assertEquals(shopCache.get("ceshi1"), "value");
    }

    /**
     * 测试keys
     */
    @Test
    public void keysTest() {
        shopCache.put("ceshi495849598", "value", 100);
        Set<Object> set = shopCache.keys("ceshi49584*");
//		for(Object obj:set){
//			System.out.println(obj);
//		}
        Assert.assertEquals(1, set.size());
    }

    /**
     * 测试del
     */
    @Test
    public void delTest() {
        shopCache.put("ceshi1", "value", 100);
        Assert.assertTrue(shopCache.del("ceshi1"));
    }

    /**
     * 测试delAll
     */
    @Test
    public void delAllTest() {
        //shopCache.delAll();
    }

    /**
     * 测试exists
     */
    @Test
    public void existsTest() {
        shopCache.put("ceshi3", "value", 3);
        Assert.assertTrue(shopCache.isExists("ceshi3"));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertFalse(shopCache.isExists("ceshi3"));
    }

    @Test
    public void touchTest() throws InterruptedException {
        shopCache.put("ceshi4", "value", 3);
        Assert.assertTrue(shopCache.isExists("ceshi4"));
        shopCache.touch("ceshi4", 10);
        Thread.sleep(6000);
        Assert.assertTrue(shopCache.isExists("ceshi4"));
        Thread.sleep(6000);
        Assert.assertFalse(shopCache.isExists("ceshi4"));
    }

    @Test
    public void getAndTouchTest() throws InterruptedException {
        shopCache.put("ceshi5", "value", 3);
        Assert.assertTrue(shopCache.isExists("ceshi5"));
        shopCache.getAndTouch("ceshi5", 10);
        Thread.sleep(6000);
        Assert.assertTrue(shopCache.isExists("ceshi5"));
        Thread.sleep(6000);
        Assert.assertFalse(shopCache.isExists("ceshi5"));
    }

    /**
     * 返回值安全测试
     */
    @Test
    public void safeTest() {
        //模拟a用户向缓存中写了一个值
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("1", "111");
        map.put("2", "222");
        shopCache.put("ceshi6", map, 100);

        //模拟b用户取值，并修改，但不写回
        Map<String, Object> map2 = (Map<String, Object>) shopCache.get("ceshi6");
        map2.put("1", "333");//修改了map的值，但未写入缓存，请问缓存中的那个map会被改变吗？

        //模拟c用户取的值，受到影响了吗？（Ehcache是java引用传递，会受影响，要克隆解决）
        Map<String, Object> map3 = (Map<String, Object>) shopCache.get("ceshi6");
        Assert.assertEquals(map3.get("1").toString(), "111");
    }

}
