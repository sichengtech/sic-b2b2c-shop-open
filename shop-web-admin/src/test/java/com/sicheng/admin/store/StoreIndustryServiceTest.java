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
package com.sicheng.admin.store;

import com.sicheng.admin.store.entity.StoreIndustry;
import com.sicheng.admin.store.service.StoreIndustryService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.IdGen;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: 单元测试--主营行业 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017-01-10
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class StoreIndustryServiceTest {
    @Autowired
    private StoreIndustryService storeIndustryService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        StoreIndustry storeIndustry = storeIndustryService.selectById(0L);
        Assert.assertNull(storeIndustry);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<StoreIndustry> list = storeIndustryService.selectByIdIn(paramList);
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
        Page<StoreIndustry> page = new Page<StoreIndustry>();
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        page = storeIndustryService.selectByWhere(page, new Wrapper(entity));
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
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        List<StoreIndustry> list = storeIndustryService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        List<StoreIndustry> list = storeIndustryService.selectAll(new Wrapper(entity));
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
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = storeIndustryService.insert(entity);
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
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = storeIndustryService.insertSelective(entity);
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
        StoreIndustry entity = new StoreIndustry();
        entity.setId(1L);
        entity.setIndustryName(IdGen.randomBase62(5));
        entity.setIndustryMoney(new BigDecimal("0.1"));
        entity.setSort(10);
        entity.setIsOpen("1");
        entity.setCreateDate(new Date());
        int rs = storeIndustryService.updateById(entity);
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
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        StoreIndustry condition = new StoreIndustry();
        condition.setId(2L);
        int rs = storeIndustryService.updateByWhere(entity, new Wrapper(condition));
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
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = storeIndustryService.updateByIdSelective(entity);
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
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        StoreIndustry condition = new StoreIndustry();
        condition.setId(2L);
        int rs = storeIndustryService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = storeIndustryService.deleteById(1L);
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
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = storeIndustryService.deleteByWhere(new Wrapper(entity));
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
        StoreIndustry entity = new StoreIndustry();
        entity.setIndustryId(1L);//Long 主键
        entity.setIndustryName("1");//String 行业名称
        entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
        entity.setSort(1);//Long 排序
        entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = storeIndustryService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        StoreIndustry p1 = new StoreIndustry();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        StoreIndustry p2 = new StoreIndustry();//p2的属性全是空值

        storeIndustryService.selectAll(null);
        storeIndustryService.selectAll(new Wrapper(p2));
        storeIndustryService.countByWhere(null);
        storeIndustryService.countByWhere(new Wrapper(p2));

        try {
            storeIndustryService.updateByWhere(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeIndustryService.updateByWhereSelective(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeIndustryService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeIndustryService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeIndustryService.deleteByWhere(new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeIndustryService.deleteByWhere(null);
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
        List<StoreIndustry> list = new ArrayList<StoreIndustry>();
        for (int i = 0; i < 10; i++) {
            StoreIndustry entity = new StoreIndustry();
            entity.setIndustryId(1L);//Long 主键
            entity.setIndustryName("1");//String 行业名称
            entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
            entity.setSort(1);//Long 排序
            entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = storeIndustryService.insertBatch(list);
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
        List<StoreIndustry> list = new ArrayList<StoreIndustry>();
        for (int i = 0; i < 10; i++) {
            StoreIndustry entity = new StoreIndustry();
            entity.setIndustryId(1L);//Long 主键
            entity.setIndustryName("1");//String 行业名称
            entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
            entity.setSort(1);//Long 排序
            entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = storeIndustryService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<StoreIndustry> list = new ArrayList<StoreIndustry>();
        for (int i = 0; i < 10; i++) {
            StoreIndustry entity = new StoreIndustry();
            entity.setIndustryId(1L);//Long 主键
            entity.setIndustryName("1");//String 行业名称
            entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
            entity.setSort(1);//Long 排序
            entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = storeIndustryService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<StoreIndustry> list = new ArrayList<StoreIndustry>();
        for (int i = 0; i < 10; i++) {
            StoreIndustry entity = new StoreIndustry();
            entity.setIndustryId(1L);//Long 主键
            entity.setIndustryName("1");//String 行业名称
            entity.setIndustryMoney(new BigDecimal("0.1"));//BigDecimal 保证金
            entity.setSort(1);//Long 排序
            entity.setIsOpen("1");//String 是否开启(0关闭、1开启)
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = storeIndustryService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        StoreIndustry entity = new StoreIndustry();
        entity.setId(1L);
        storeIndustryService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = storeIndustryService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }
}