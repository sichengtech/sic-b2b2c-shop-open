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
package com.sicheng.common.persistence.wrapper;

import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.product.service.ProductSpuService;
import com.sicheng.common.persistence.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>标题: Wrapper传入Null值测试 </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017-01-13
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class WrapperNullTest {
    @Autowired
    private ProductSpuService productSpuService;

    /**
     * Wrapper常规情况测试
     * 含有条件：entity、page、wrapper、distinct、orderBy
     */
    @Test
    public void test_Wrapper_1() {
        ProductSpu entity = new ProductSpu();
        entity.setPId(302L);
        entity.setName("1");

        Wrapper wrapper = new Wrapper(entity);
        wrapper.setDistinct(true);//distinct
        wrapper.or("p_id=", 301);
        wrapper.orderBy("p_id DESC");

        Page<ProductSpu> page = new Page<ProductSpu>();
        page = productSpuService.selectByWhere(page, wrapper);
        List<ProductSpu> list = page.getList();
        System.out.println("查出" + list.size() + "条");
    }

    /**
     * Wrapper测试
     * page\wrapper 为null的情况
     */
    @Test
    public void test_WrapperNull_2() {
        Page<ProductSpu> page = productSpuService.selectByWhere(null, null);
        List<ProductSpu> list = page.getList();
        System.out.println("查出" + list.size() + "条");
    }

    /**
     * Wrapper测试
     * wrapper 为null的情况
     */
    @Test
    public void test_WrapperNull_3() {
        Page<ProductSpu> page = new Page<ProductSpu>();
        page.setOrderBy("name asc");
        page = productSpuService.selectByWhere(page, null);
        List<ProductSpu> list = page.getList();
        System.out.println("查出" + list.size() + "条");
    }

    /**
     * entity 为null的情况
     */
    @Test
    public void test_WrapperNull_4() {
        ProductSpu entity = null;
        Wrapper wrapper = new Wrapper(entity);
        wrapper.or("p_id=", 301);
        Page<ProductSpu> page = new Page<ProductSpu>();
        page = productSpuService.selectByWhere(page, wrapper);
        List<ProductSpu> list = page.getList();
        System.out.println("查出" + list.size() + "条");
    }


}