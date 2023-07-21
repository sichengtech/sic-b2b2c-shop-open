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

import com.sicheng.common.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Date;

/**
 * <p>标题: loginFailTest</p>
 * <p>描述: 登录失败次数记录器</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年7月8日 上午11:06:53
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class loginFailTest {

    @Autowired
    private ShopCache shopCache;

    @Test
    public void failTest() {
        //当前时间毫秒数
        long current = System.currentTimeMillis();
        //当天零点零分零秒的毫秒数
        String zeros = DateUtils.getDate() + " 00:00:00";
        Date zeroDate = null;
        try {
            zeroDate = DateUtils.parseDate(zeros, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("admin时间转换出错");
        }
        long zero = zeroDate.getTime();
        //当天23点59分59秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;
        //当天23点59分59秒与当前时间的秒数
        long difference = (twelve - current) / 1000;
        String username = "admin";
        Long loginFailNum = (Long) shopCache.get(CacheConstant.ADMIN_LOGINFAIL + username);
        //计数加1
        if (true) {
            if (loginFailNum == null) {
                loginFailNum = (long) 0;
            }
            loginFailNum++;
            shopCache.put(CacheConstant.ADMIN_LOGINFAIL + username, loginFailNum, difference);
        }
        System.out.println(shopCache.get(CacheConstant.ADMIN_LOGINFAIL + username));
        //计数清零
        if (true) {
            Assert.assertTrue(shopCache.del(CacheConstant.ADMIN_LOGINFAIL + username));
        }
    }

}
