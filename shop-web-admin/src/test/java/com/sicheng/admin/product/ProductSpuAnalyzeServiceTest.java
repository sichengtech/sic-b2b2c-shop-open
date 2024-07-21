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

import com.sicheng.admin.product.entity.ProductSpuAnalyze;
import com.sicheng.admin.product.service.ProductSpuAnalyzeService;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: 单元测试--商品统计 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2017-05-09
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ProductSpuAnalyzeServiceTest {
    @Autowired
    private ProductSpuAnalyzeService productSpuAnalyzeService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        ProductSpuAnalyze productSpuAnalyze = productSpuAnalyzeService.selectById(0L);
        Assert.assertNull(productSpuAnalyze);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<ProductSpuAnalyze> list = productSpuAnalyzeService.selectByIdIn(paramList);
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
        Page<ProductSpuAnalyze> page = new Page<ProductSpuAnalyze>();
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        page = productSpuAnalyzeService.selectByWhere(page, new Wrapper(entity));
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
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        List<ProductSpuAnalyze> list = productSpuAnalyzeService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        List<ProductSpuAnalyze> list = productSpuAnalyzeService.selectAll(new Wrapper(entity));
        Assert.assertNotNull(list);
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
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPkMode(1);
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        int rs = productSpuAnalyzeService.insert(entity);
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
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPkMode(1);
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        int rs = productSpuAnalyzeService.insertSelective(entity);
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
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        int rs = productSpuAnalyzeService.updateById(entity);
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
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        ProductSpuAnalyze condition = new ProductSpuAnalyze();
        condition.setId(2L);
        int rs = productSpuAnalyzeService.updateByWhere(entity, new Wrapper(condition));
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
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        int rs = productSpuAnalyzeService.updateByIdSelective(entity);
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
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        ProductSpuAnalyze condition = new ProductSpuAnalyze();
        condition.setId(2L);
        int rs = productSpuAnalyzeService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = productSpuAnalyzeService.deleteById(1L);
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
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        int rs = productSpuAnalyzeService.deleteByWhere(new Wrapper(entity));
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
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setPId(1L);//Long 商品spu的id
        entity.setAllBrowse(1);//Long 总浏览量
        entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
        entity.setWeekBrowse(1);//Long 周浏览量
        entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
        entity.setMonthBrowse(1);//Long 月浏览量
        entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
        entity.setMonth3Browse(1);//Long 三个月浏览量
        entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
        entity.setAllSales(1);//Long 总销量
        entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
        entity.setWeekSales(1);//Long 周销量
        entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
        entity.setMonthSales(1);//Long 月销量
        entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
        entity.setMonth3Sales(1);//Long 三个月销量
        entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

        int rs = productSpuAnalyzeService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        ProductSpuAnalyze p1 = new ProductSpuAnalyze();
        p1.setCreateDate(new Date());
        //p1.setBak1("bak1");

        ProductSpuAnalyze p2 = new ProductSpuAnalyze();//p2的属性全是空值

        productSpuAnalyzeService.selectAll(null);
        productSpuAnalyzeService.selectAll(new Wrapper(p2));
        productSpuAnalyzeService.countByWhere(null);
        productSpuAnalyzeService.countByWhere(new Wrapper(p2));

        try {
            productSpuAnalyzeService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuAnalyzeService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuAnalyzeService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuAnalyzeService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuAnalyzeService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuAnalyzeService.deleteByWhere(null);
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
        List<ProductSpuAnalyze> list = new ArrayList<ProductSpuAnalyze>();
        for (int i = 0; i < 1; i++) {
            ProductSpuAnalyze entity = new ProductSpuAnalyze();
            entity.setPkMode(1);
            entity.setPId(1L);//Long 商品spu的id
            entity.setAllBrowse(1);//Long 总浏览量
            entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
            entity.setWeekBrowse(1);//Long 周浏览量
            entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
            entity.setMonthBrowse(1);//Long 月浏览量
            entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
            entity.setMonth3Browse(1);//Long 三个月浏览量
            entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
            entity.setAllSales(1);//Long 总销量
            entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
            entity.setWeekSales(1);//Long 周销量
            entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
            entity.setMonthSales(1);//Long 月销量
            entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
            entity.setMonth3Sales(1);//Long 三个月销量
            entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

            list.add(entity);
        }
        boolean rs = productSpuAnalyzeService.insertBatch(list);
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
        List<ProductSpuAnalyze> list = new ArrayList<ProductSpuAnalyze>();
        for (int i = 0; i < 1; i++) {
            ProductSpuAnalyze entity = new ProductSpuAnalyze();
            entity.setPkMode(1);
            entity.setPId(1L);//Long 商品spu的id
            entity.setAllBrowse(1);//Long 总浏览量
            entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
            entity.setWeekBrowse(1);//Long 周浏览量
            entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
            entity.setMonthBrowse(1);//Long 月浏览量
            entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
            entity.setMonth3Browse(1);//Long 三个月浏览量
            entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
            entity.setAllSales(1);//Long 总销量
            entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
            entity.setWeekSales(1);//Long 周销量
            entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
            entity.setMonthSales(1);//Long 月销量
            entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
            entity.setMonth3Sales(1);//Long 三个月销量
            entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

            list.add(entity);
        }
        boolean rs = productSpuAnalyzeService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<ProductSpuAnalyze> list = new ArrayList<ProductSpuAnalyze>();
        for (int i = 0; i < 10; i++) {
            ProductSpuAnalyze entity = new ProductSpuAnalyze();
            entity.setPId(1L);//Long 商品spu的id
            entity.setAllBrowse(1);//Long 总浏览量
            entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
            entity.setWeekBrowse(1);//Long 周浏览量
            entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
            entity.setMonthBrowse(1);//Long 月浏览量
            entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
            entity.setMonth3Browse(1);//Long 三个月浏览量
            entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
            entity.setAllSales(1);//Long 总销量
            entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
            entity.setWeekSales(1);//Long 周销量
            entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
            entity.setMonthSales(1);//Long 月销量
            entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
            entity.setMonth3Sales(1);//Long 三个月销量
            entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

            list.add(entity);
        }
        boolean rs = productSpuAnalyzeService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<ProductSpuAnalyze> list = new ArrayList<ProductSpuAnalyze>();
        for (int i = 0; i < 10; i++) {
            ProductSpuAnalyze entity = new ProductSpuAnalyze();
            entity.setPId(1L);//Long 商品spu的id
            entity.setAllBrowse(1);//Long 总浏览量
            entity.setAllBrowseDate(new Date());//java.util.Date 总浏览量更新日期
            entity.setWeekBrowse(1);//Long 周浏览量
            entity.setWeekBrowseDate(new Date());//java.util.Date 周浏览量更新日期
            entity.setMonthBrowse(1);//Long 月浏览量
            entity.setMonthBrowseDate(new Date());//java.util.Date 月浏览量更新日期
            entity.setMonth3Browse(1);//Long 三个月浏览量
            entity.setMonth3BrowseDate(new Date());//java.util.Date 三个月浏览量更新日期
            entity.setAllSales(1);//Long 总销量
            entity.setAllSalesDate(new Date());//java.util.Date 总销量更新日期
            entity.setWeekSales(1);//Long 周销量
            entity.setWeekSalesDate(new Date());//java.util.Date 周销量更新日期
            entity.setMonthSales(1);//Long 月销量
            entity.setMonthSalesDate(new Date());//java.util.Date 月销量更新日期
            entity.setMonth3Sales(1);//Long 三个月销量
            entity.setMonth3SalesDate(new Date());//java.util.Date 三个月销量更新日期

            list.add(entity);
        }
        boolean rs = productSpuAnalyzeService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        ProductSpuAnalyze entity = new ProductSpuAnalyze();
        entity.setId(1L);
        productSpuAnalyzeService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = productSpuAnalyzeService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}