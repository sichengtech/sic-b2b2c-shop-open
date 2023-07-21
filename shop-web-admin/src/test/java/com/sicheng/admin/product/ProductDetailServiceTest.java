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
package com.sicheng.admin.product;

import com.sicheng.admin.product.entity.ProductDetail;
import com.sicheng.admin.product.service.ProductDetailService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: 单元测试--商品详情 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 赵磊
 * @version 2017-10-12
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ProductDetailServiceTest {
    @Autowired
    private ProductDetailService productDetailService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        ProductDetail productDetail = productDetailService.selectById(0L);
        Assert.assertNull(productDetail);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<ProductDetail> list = productDetailService.selectByIdIn(paramList);
        Assert.assertNotNull(list);
    }

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     *
     * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     *                  入参为null，或入参为new一个实体对象但无属性值，会查全表
     */
    @Test
    public void test_selectByWhere() {
        Page<ProductDetail> page = new Page<ProductDetail>();
        ProductDetail entity = new ProductDetail();
        entity.setPId(999999999L);//Long 商品ID
        page = productDetailService.selectByWhere(page, new Wrapper(entity));
        Assert.assertNotNull(page.getList());
    }

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     *
     * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     *                  入参为null，或入参为new一个实体对象但无属性值，会查全表
     */
    @Test
    public void test_selectByWhere2() {
        ProductDetail entity = new ProductDetail();
        entity.setPId(999999999L);//Long 商品ID
        List<ProductDetail> list = productDetailService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        ProductDetail entity = new ProductDetail();
        entity.setPId(999999999L);//Long 商品ID
        //因当表中数据量较大时，查全表很耗时，所以注释了
        //List<ProductDetail> list= productDetailService.selectAll(new Wrapper(entity));
        //Assert.assertNotNull(list);
    }

    /**
     * 插入数据
     * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
     *
     * @param entity
     * @return 受影响的行数
     */
    @Test
    public void test_insert() {
        ProductDetail entity = new ProductDetail();
        entity.setPkMode(1);
        entity.setPId(999999998L);//Long 商品ID
        entity.setIntroduction("a");//String 商品描述详情,pc版
        entity.setIntroduction2("a");//String 商品描述详情，移动版
        int rs = productDetailService.insert(entity);
        Assert.assertEquals(1, rs);
    }

    /**
     * 插入,只把非空的值插入到对应的字段
     * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
     *
     * @param entity
     * @return 受影响的行数
     */
    @Test
    public void test_insertSelective() {
        ProductDetail entity = new ProductDetail();
        entity.setPkMode(1);
        entity.setPId(999999997L);//Long 商品ID
        entity.setIntroduction("a");//String 商品描述详情,pc版
        entity.setIntroduction2("a");//String 商品描述详情，移动版
        int rs = productDetailService.insertSelective(entity);
        Assert.assertEquals(1, rs);
    }

    /**
     * 根据主键更新记录,更新除了主键的所有字段
     *
     * @param entity
     * @return 受影响的行数
     */
    @Test
    public void test_updateById() {
        ProductDetail entity = new ProductDetail();
        entity.setPId(999999999L);//Long 商品ID
        entity.setIntroduction("a");//String 商品描述详情,pc版
        entity.setIntroduction2("a");//String 商品描述详情，移动版
        int rs = productDetailService.updateById(entity);
        Assert.assertNotNull(rs);
    }

    /**
     * 根据条件更新记录,更新除了主键的所有字段
     *
     * @param entity    数据实体，用于存储数据，这些数据将被update到表中
     * @param condition 条件，用于where条件，找出符合条件的数据。入参为null，或入参为new一个实体对象但无属性值，执行sql时会报错，防止更新全表
     * @return 受影响的行数
     * <p>
     * 注意：condition.setDelFlag(null);把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
     */
    @Test
    public void test_updateByWhere() {
        ProductDetail entity = new ProductDetail();
        entity.setPId(999999999L);//Long 商品ID
        entity.setIntroduction("a");//String 商品描述详情,pc版
        entity.setIntroduction2("a");//String 商品描述详情，移动版
        ProductDetail condition = new ProductDetail();
        condition.setId(2L);
        int rs = productDetailService.updateByWhere(entity, new Wrapper(condition));
        Assert.assertNotNull(rs);
    }

    /**
     * 根据主键更新记录,只把非空的值更到对应的字段
     *
     * @param entity 数据实体，用于存储数据，这些数据将被update到表中
     * @return 受影响的行数
     */
    @Test
    public void test_updateByIdSelective() {
        ProductDetail entity = new ProductDetail();
        entity.setPId(999999999L);//Long 商品ID
        entity.setIntroduction("a");//String 商品描述详情,pc版
        entity.setIntroduction2("a");//String 商品描述详情，移动版
        int rs = productDetailService.updateByIdSelective(entity);
        Assert.assertNotNull(rs);
    }

    /**
     * 根据条件更新记录,只把非空的值更到对应的字段
     *
     * @param entity    数据实体，用于存储数据，这些数据将被update到表中
     * @param condition 条件，用于where条件，找出符合条件的数据。入参为null，或入参为new一个实体对象但无属性值，执行sql时会报错，防止更新全表
     * @return 受影响的行数
     * <p>
     * 注意：condition.setDelFlag(null);把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
     */
    @Test
    public void test_updateByWhereSelective() {
        ProductDetail entity = new ProductDetail();
        entity.setPId(999999999L);//Long 商品ID
        entity.setIntroduction("a");//String 商品描述详情,pc版
        entity.setIntroduction2("a");//String 商品描述详情，移动版
        ProductDetail condition = new ProductDetail();
        condition.setId(2L);
        int rs = productDetailService.updateByWhereSelective(entity, new Wrapper(condition));
        Assert.assertNotNull(rs);
    }

    /**
     * 删除数据
     * （如果有del_flag字段，就逻辑删除，更新del_flag字段为1表示删除）
     * （如果无del_flag字段，就物理删除）
     *
     * @param id 主键
     * @return 受影响的行数
     */
    @Test
    public void test_deleteById() {
        int rs = productDetailService.deleteById(1L);
        Assert.assertNotNull(rs);
    }

    /**
     * 删除数据（物理删除）
     *
     * @param condition 删除条件。入参为null，或入参为new一个实体对象但无属性值，执行SQL会报错，防止删除全表
     * @return 受影响的行数
     * <p>
     * 注意：data.setDelFlag(null);//把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
     */
    @Test
    public void test_deleteByWhere() {
        ProductDetail entity = new ProductDetail();
        entity.setPId(999999999L);//Long 商品ID
        int rs = productDetailService.deleteByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 根据条件查询记录总数
     *
     * @param condition 可为null。或new一个实体对象，用于控制del_flag、控制distinct
     * @return 总行数
     * <p>
     * 注意：data.setDelFlag(null);//把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
     */
    @Test
    public void test_countByWhere() {
        ProductDetail entity = new ProductDetail();
        entity.setPId(999999999L);//Long 商品ID
        int rs = productDetailService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        ProductDetail p1 = new ProductDetail();
        p1.setCreateDate(new Date());
        //p1.setBak1("bak1");

        ProductDetail p2 = new ProductDetail();//p2的属性全是空值

        //productDetailService.selectAll(null);
        //productDetailService.selectAll(new Wrapper(p2));
        productDetailService.countByWhere(null);
        productDetailService.countByWhere(new Wrapper(p2));

        try {
            productDetailService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productDetailService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productDetailService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productDetailService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productDetailService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productDetailService.deleteByWhere(null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }
    }

    /**
     * 批量插入
     */
    @Test
    public void test_insertBatch() {
        List<ProductDetail> list = new ArrayList<ProductDetail>();
        for (int i = 0; i < 1; i++) {
            ProductDetail entity = new ProductDetail();
            entity.setPkMode(1);
            entity.setPId(999999999L);//Long 商品ID
            entity.setIntroduction("a");//String 商品描述详情,pc版
            entity.setIntroduction2("a");//String 商品描述详情，移动版
            list.add(entity);
        }
        boolean rs = productDetailService.insertBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_insertSelectiveBatch() {
        List<ProductDetail> list = new ArrayList<ProductDetail>();
        for (int i = 0; i < 1; i++) {
            ProductDetail entity = new ProductDetail();
            entity.setPkMode(1);
            entity.setPId(999999996L);//Long 商品ID
            entity.setIntroduction("a");//String 商品描述详情,pc版
            entity.setIntroduction2("a");//String 商品描述详情，移动版
            list.add(entity);
        }
        boolean rs = productDetailService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<ProductDetail> list = new ArrayList<ProductDetail>();
        for (int i = 0; i < 1; i++) {
            ProductDetail entity = new ProductDetail();
            entity.setPId(999999999L);//Long 商品ID
            entity.setIntroduction("a");//String 商品描述详情,pc版
            entity.setIntroduction2("a");//String 商品描述详情，移动版
            list.add(entity);
        }
        boolean rs = productDetailService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<ProductDetail> list = new ArrayList<ProductDetail>();
        for (int i = 0; i < 1; i++) {
            ProductDetail entity = new ProductDetail();
            entity.setPId(999999999L);//Long 商品ID
            entity.setIntroduction("a");//String 商品描述详情,pc版
            entity.setIntroduction2("a");//String 商品描述详情，移动版
            list.add(entity);
        }
        boolean rs = productDetailService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        ProductDetail entity = new ProductDetail();
        entity.setId(1L);
        productDetailService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        List<Object> list = new ArrayList<Object>();
        list.add(1);
        list.add(2L);
        list.add(3L);
        int rs = productDetailService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}