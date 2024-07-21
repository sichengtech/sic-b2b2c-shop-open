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
package com.sicheng.admin.product;

import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.admin.product.entity.ProductParamMapping;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.product.service.ProductSpuService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>标题: 单元测试--商品表，映射测试，一对一，一对多，多对多 </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017-01-13
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ProductSpuMappingTest {
    @Autowired
    private ProductSpuService productSpuService;

    @Test
    public void test_one2one() {
        Page<ProductSpu> page = new Page<ProductSpu>();
        page = productSpuService.selectByWhere(page, new Wrapper().and("p_id>", 1));
        List<ProductSpu> list = page.getList();
        if (list.size() > 0) {
            ProductSpu spu = list.get(0);
            //一对一映射 测试
            //一个商品--商品分类
            ProductCategory category = spu.getProductCategory();
            System.out.println("本商品的分类是：" + category);
        }
    }

    @Test
    public void test_one2many() {
        Page<ProductSpu> page = new Page<ProductSpu>();
        page = productSpuService.selectByWhere(page, new Wrapper().and("p_id>", 1));
        List<ProductSpu> list = page.getList();
        if (list.size() > 0) {
            ProductSpu spu = list.get(0);

            //一对多映射
            //一个商品--多个SKU
            List<ProductSku> list2 = spu.getProductSkuList();
            System.out.println("本商品的sku有：" + list2.size() + "条");

            //一对多映射
            //一个商品--多个参数
            List<ProductParamMapping> list3 = spu.getProductParamList();
            System.out.println("本商品的参数有：" + list3.size() + "条");
        }
    }

    @Test
    public void test_many2many() {
        Page<ProductSpu> page = new Page<ProductSpu>();
        page = productSpuService.selectByWhere(page, new Wrapper().and("p_id>", 1));
        List<ProductSpu> list = page.getList();
        if (list.size() > 0) {
            ProductSpu spu = list.get(0);

            //多对多（一对多+idIn来实现）
            //一个商品--多个图片，一个图片--多个商品
            List<?> list4 = spu.getStoreAlbumPictureList();
            System.out.println("本商品的图片有：" + list4.size() + "条");
        }
    }

    @Test
    public void test_one2one_batch() {
        Page<ProductSpu> page = new Page<ProductSpu>();
        page = productSpuService.selectByWhere(page, new Wrapper().and("p_id>", 1));
        List<ProductSpu> list = page.getList();

        //批量填充，并且防止1+N问题
        //ListIdIn工具,在一个list中做"一对一"
        //10个商品对10个分类
        //给商品填充分类,把1+N改成1+1
        ProductSpu.fillProductCategory(list);
    }
}