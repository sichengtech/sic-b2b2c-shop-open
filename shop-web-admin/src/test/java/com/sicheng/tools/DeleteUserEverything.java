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
package com.sicheng.tools;

import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sso.service.UserSellerService;
import com.sicheng.common.persistence.wrapper.Wrapper;
import org.apache.ibatis.cursor.Cursor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;

/**
 * <p>标题: 删除用户的一切  </p>
 * <p>描述: 删除用户的一切，包含店铺、商品等等一切。由于不使用@Transactional注解所以会提交事务会真删除。 </p>
 *
 * @author zhaolei
 * @version 2024-01-20 21:10
 */
//@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class DeleteUserEverything {
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private UserSellerService userSellerService;

    /**
     * 删除用户的一切，包含店铺、商品等等一切。
     * 由于不使用@Transactional注解所以会提交事务会真删除。
     * 下面的@Test注解平时也是被注释掉的，以保证不被做为单元测试执行。当你需要它执行时请手动放开注释。
     */
//    @Test
    public void DeleteUserEverything_Test() {
        del_1();//入口1
        //del_2();//入口2
    }

    /**
     * 删除用户的一切  (可成功运行)
     * 使用List来临时存放查询待删用户的结果集,不适合海量数据
     */
    private void del_1() {
        //先查询出要被删除的用户,请检查你实际业务来修改查询条件
        //  u_id >2517 ,小于2517的用户都是我动建立的，希望不删除。
        //  uid为5148的用户不删除，因为首页引用了它的商品。
        //  其它用户，删除按95%比例删除,u_id % 19!= 0 就是。
        Wrapper w = new Wrapper()
                .and("u_id >", 2517)
                .and("u_id !=", 5148)
                .and("u_id % 3!= ", 0)
                .and("u_id < ", 40020);

        List<UserSeller> list = userSellerService.selectByWhere(w);
        int count = 0;
        for (UserSeller entity : list) {
            count++;
            Long uid = entity.getUId();
            System.out.println("开始删除第" + count + ",uid=" + uid);
            //按uId删除会员的店铺及一切
            userMainService.cleanupMyWorld_DeleteUserEverything(uid);
        }
    }

    /**
     * 删除用户的一切  (不成功运行)
     * 为了适应处理海量数据使用了游标,但因为脱离事务游标立即关闭了而无法使用。(待解决) 请使用del_1()方法.
     */
    private void del_2() {
        //先查询出要被删除的用户,请检查你实际业务来修改查询条件
        //  u_id >2517 ,小于2517的用户都是我动建立的，希望不删除。
        //  uid为5148的用户不删除，因为首页引用了它的商品。
        //  其它用户，删除按95%比例删除,u_id % 19!= 0 就是。
        Wrapper w = new Wrapper()
                .and("u_id >", 2517)
                .and("u_id !=", 5148)
                .and("u_id % 19!= ", 0)
                .and("u_id < ", 40020);

        Cursor<UserSeller> cursor = userSellerService.selectCursor("selectByWhere", w);
        Iterator<UserSeller> iter = cursor.iterator();
        int count = 0;
        while (iter.hasNext()) {
            count++;
            UserSeller entity = iter.next();
            Long uid = entity.getUId();
            System.out.println("开始删除第" + count + ",uid=" + uid);
            //按uId删除会员的店铺及一切
            userMainService.cleanupMyWorld_DeleteUserEverything(uid);
        }
    }
}
