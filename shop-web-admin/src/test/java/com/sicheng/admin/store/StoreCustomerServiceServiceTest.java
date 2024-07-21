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

import com.sicheng.admin.store.entity.StoreCustomerService;
import com.sicheng.admin.store.service.StoreCustomerServiceService;
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
 * <p>标题: 单元测试--管理店铺的客服 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2017-02-23
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class StoreCustomerServiceServiceTest {
    @Autowired
    private StoreCustomerServiceService storeCustomerServiceService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        StoreCustomerService storeCustomerService = storeCustomerServiceService.selectById(0L);
        Assert.assertNull(storeCustomerService);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<StoreCustomerService> list = storeCustomerServiceService.selectByIdIn(paramList);
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
        Page<StoreCustomerService> page = new Page<StoreCustomerService>();
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        page = storeCustomerServiceService.selectByWhere(page, new Wrapper(entity));
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
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        List<StoreCustomerService> list = storeCustomerServiceService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        List<StoreCustomerService> list = storeCustomerServiceService.selectAll(new Wrapper(entity));
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
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        int rs = storeCustomerServiceService.insert(entity);
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
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        int rs = storeCustomerServiceService.insertSelective(entity);
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
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        int rs = storeCustomerServiceService.updateById(entity);
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
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        StoreCustomerService condition = new StoreCustomerService();
        condition.setId(2L);
        int rs = storeCustomerServiceService.updateByWhere(entity, new Wrapper(condition));
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
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        int rs = storeCustomerServiceService.updateByIdSelective(entity);
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
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        StoreCustomerService condition = new StoreCustomerService();
        condition.setId(2L);
        int rs = storeCustomerServiceService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = storeCustomerServiceService.deleteById(1L);
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
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        int rs = storeCustomerServiceService.deleteByWhere(new Wrapper(entity));
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
        StoreCustomerService entity = new StoreCustomerService();
        entity.setScsId(1L);//Long 客服id
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setName("1");//String 客服名称
        entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
        entity.setAccount("1");//String 客服账号
        entity.setType("1");//String 类型（1.售前服务，2.售后服务）
        entity.setSort(1);//Integer 排序
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setBak1("1");//String 备用字段
        entity.setBak2("1");//String 备用字段
        entity.setBak3("1");//String 备用字段
        entity.setBak4("1");//String 备用字段
        entity.setBak5("1");//String 备用字段

        int rs = storeCustomerServiceService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        StoreCustomerService p1 = new StoreCustomerService();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        StoreCustomerService p2 = new StoreCustomerService();//p2的属性全是空值

        storeCustomerServiceService.selectAll(null);
        storeCustomerServiceService.selectAll(new Wrapper(p2));
        storeCustomerServiceService.countByWhere(null);
        storeCustomerServiceService.countByWhere(new Wrapper(p2));

        try {
            storeCustomerServiceService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeCustomerServiceService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeCustomerServiceService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeCustomerServiceService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeCustomerServiceService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeCustomerServiceService.deleteByWhere(null);
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
        List<StoreCustomerService> list = new ArrayList<StoreCustomerService>();
        for (int i = 0; i < 10; i++) {
            StoreCustomerService entity = new StoreCustomerService();
            entity.setScsId(1L);//Long 客服id
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setName("1");//String 客服名称
            entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
            entity.setAccount("1");//String 客服账号
            entity.setType("1");//String 类型（1.售前服务，2.售后服务）
            entity.setSort(1);//Integer 排序
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setRemarks("备注信息");//String 备注信息
            entity.setBak1("1");//String 备用字段
            entity.setBak2("1");//String 备用字段
            entity.setBak3("1");//String 备用字段
            entity.setBak4("1");//String 备用字段
            entity.setBak5("1");//String 备用字段

            list.add(entity);
        }
        boolean rs = storeCustomerServiceService.insertBatch(list);
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
        List<StoreCustomerService> list = new ArrayList<StoreCustomerService>();
        for (int i = 0; i < 10; i++) {
            StoreCustomerService entity = new StoreCustomerService();
            entity.setScsId(1L);//Long 客服id
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setName("1");//String 客服名称
            entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
            entity.setAccount("1");//String 客服账号
            entity.setType("1");//String 类型（1.售前服务，2.售后服务）
            entity.setSort(1);//Integer 排序
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setRemarks("备注信息");//String 备注信息
            entity.setBak1("1");//String 备用字段
            entity.setBak2("1");//String 备用字段
            entity.setBak3("1");//String 备用字段
            entity.setBak4("1");//String 备用字段
            entity.setBak5("1");//String 备用字段

            list.add(entity);
        }
        boolean rs = storeCustomerServiceService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<StoreCustomerService> list = new ArrayList<StoreCustomerService>();
        for (int i = 0; i < 10; i++) {
            StoreCustomerService entity = new StoreCustomerService();
            entity.setScsId(1L);//Long 客服id
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setName("1");//String 客服名称
            entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
            entity.setAccount("1");//String 客服账号
            entity.setType("1");//String 类型（1.售前服务，2.售后服务）
            entity.setSort(1);//Integer 排序
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setRemarks("备注信息");//String 备注信息
            entity.setBak1("1");//String 备用字段
            entity.setBak2("1");//String 备用字段
            entity.setBak3("1");//String 备用字段
            entity.setBak4("1");//String 备用字段
            entity.setBak5("1");//String 备用字段

            list.add(entity);
        }
        boolean rs = storeCustomerServiceService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<StoreCustomerService> list = new ArrayList<StoreCustomerService>();
        for (int i = 0; i < 10; i++) {
            StoreCustomerService entity = new StoreCustomerService();
            entity.setScsId(1L);//Long 客服id
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setName("1");//String 客服名称
            entity.setTool("1");//String 客服工具（1.QQ，2.站内IM，3.旺旺）
            entity.setAccount("1");//String 客服账号
            entity.setType("1");//String 类型（1.售前服务，2.售后服务）
            entity.setSort(1);//Integer 排序
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setRemarks("备注信息");//String 备注信息
            entity.setBak1("1");//String 备用字段
            entity.setBak2("1");//String 备用字段
            entity.setBak3("1");//String 备用字段
            entity.setBak4("1");//String 备用字段
            entity.setBak5("1");//String 备用字段

            list.add(entity);
        }
        boolean rs = storeCustomerServiceService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        StoreCustomerService entity = new StoreCustomerService();
        entity.setId(1L);
        storeCustomerServiceService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = storeCustomerServiceService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}