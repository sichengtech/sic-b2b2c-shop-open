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

import com.sicheng.admin.store.entity.StoreAdminLog;
import com.sicheng.admin.store.service.StoreAdminLogService;
import com.sicheng.admin.sys.entity.User;
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
 * <p>标题: 单元测试--店铺管理员操作日志 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017-03-03
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class StoreAdminLogServiceTest {
    @Autowired
    private StoreAdminLogService storeAdminLogService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        StoreAdminLog storeAdminLog = storeAdminLogService.selectById(0L);
        Assert.assertNull(storeAdminLog);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<StoreAdminLog> list = storeAdminLogService.selectByIdIn(paramList);
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
        Page<StoreAdminLog> page = new Page<StoreAdminLog>();
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        page = storeAdminLogService.selectByWhere(page, new Wrapper(entity));
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
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        List<StoreAdminLog> list = storeAdminLogService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String 异常信息
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        List<StoreAdminLog> list = storeAdminLogService.selectAll(new Wrapper(entity));
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
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String 异常信息
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        int rs = storeAdminLogService.insert(entity);
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
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String 异常信息
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        int rs = storeAdminLogService.insertSelective(entity);
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
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String 异常信息
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        int rs = storeAdminLogService.updateById(entity);
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
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String 异常信息
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        StoreAdminLog condition = new StoreAdminLog();
        condition.setId(2L);
        int rs = storeAdminLogService.updateByWhere(entity, new Wrapper(condition));
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
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String 异常信息
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        int rs = storeAdminLogService.updateByIdSelective(entity);
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
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String 异常信息
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        StoreAdminLog condition = new StoreAdminLog();
        condition.setId(2L);
        int rs = storeAdminLogService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = storeAdminLogService.deleteById(1L);
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
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        int rs = storeAdminLogService.deleteByWhere(new Wrapper(entity));
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
        StoreAdminLog entity = new StoreAdminLog();
        entity.setLogId(1L);//Long 主键
        entity.setStoreId(1L);//Long 关联(店铺表id)
        entity.setType("1");//String 日志类型
        entity.setTitle("1");//String 日志标题（操作菜单）
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式(提交方式)
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
        entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

        int rs = storeAdminLogService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        StoreAdminLog p1 = new StoreAdminLog();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        StoreAdminLog p2 = new StoreAdminLog();//p2的属性全是空值

        storeAdminLogService.selectAll(null);
        storeAdminLogService.selectAll(new Wrapper(p2));
        storeAdminLogService.countByWhere(null);
        storeAdminLogService.countByWhere(new Wrapper(p2));

        try {
            storeAdminLogService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeAdminLogService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeAdminLogService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeAdminLogService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeAdminLogService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeAdminLogService.deleteByWhere(null);
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
        List<StoreAdminLog> list = new ArrayList<StoreAdminLog>();
        for (int i = 0; i < 10; i++) {
            StoreAdminLog entity = new StoreAdminLog();
            entity.setLogId(1L);//Long 主键
            entity.setStoreId(1L);//Long 关联(店铺表id)
            entity.setType("1");//String 日志类型
            entity.setTitle("1");//String 日志标题（操作菜单）
            entity.setRemoteAddr("1");//String 操作用户的IP地址
            entity.setUserAgent("1");//String 操作用户代理信息
            entity.setRequestUri("1");//String 操作的URI
            entity.setMethod("1");//String 操作的方式(提交方式)
            entity.setParams("1");//String 操作提交的数据
            entity.setException("1");//String 异常信息
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
            entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

            list.add(entity);
        }
        boolean rs = storeAdminLogService.insertBatch(list);
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
        List<StoreAdminLog> list = new ArrayList<StoreAdminLog>();
        for (int i = 0; i < 10; i++) {
            StoreAdminLog entity = new StoreAdminLog();
            entity.setLogId(1L);//Long 主键
            entity.setStoreId(1L);//Long 关联(店铺表id)
            entity.setType("1");//String 日志类型
            entity.setTitle("1");//String 日志标题（操作菜单）
            entity.setRemoteAddr("1");//String 操作用户的IP地址
            entity.setUserAgent("1");//String 操作用户代理信息
            entity.setRequestUri("1");//String 操作的URI
            entity.setMethod("1");//String 操作的方式(提交方式)
            entity.setParams("1");//String 操作提交的数据
            entity.setException("1");//String 异常信息
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
            entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

            list.add(entity);
        }
        boolean rs = storeAdminLogService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<StoreAdminLog> list = new ArrayList<StoreAdminLog>();
        for (int i = 0; i < 10; i++) {
            StoreAdminLog entity = new StoreAdminLog();
            entity.setLogId(1L);//Long 主键
            entity.setStoreId(1L);//Long 关联(店铺表id)
            entity.setType("1");//String 日志类型
            entity.setTitle("1");//String 日志标题（操作菜单）
            entity.setRemoteAddr("1");//String 操作用户的IP地址
            entity.setUserAgent("1");//String 操作用户代理信息
            entity.setRequestUri("1");//String 操作的URI
            entity.setMethod("1");//String 操作的方式(提交方式)
            entity.setParams("1");//String 操作提交的数据
            entity.setException("1");//String 异常信息
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
            entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

            list.add(entity);
        }
        boolean rs = storeAdminLogService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<StoreAdminLog> list = new ArrayList<StoreAdminLog>();
        for (int i = 0; i < 10; i++) {
            StoreAdminLog entity = new StoreAdminLog();
            entity.setLogId(1L);//Long 主键
            entity.setStoreId(1L);//Long 关联(店铺表id)
            entity.setType("1");//String 日志类型
            entity.setTitle("1");//String 日志标题（操作菜单）
            entity.setRemoteAddr("1");//String 操作用户的IP地址
            entity.setUserAgent("1");//String 操作用户代理信息
            entity.setRequestUri("1");//String 操作的URI
            entity.setMethod("1");//String 操作的方式(提交方式)
            entity.setParams("1");//String 操作提交的数据
            entity.setException("1");//String 异常信息
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者（操作用户id）
            entity.setCreateDate(new Date());//java.util.Date 创建时间（操作时间）
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setOperatorId(1L);//Long 操作者id(登录的店铺会员id)

            list.add(entity);
        }
        boolean rs = storeAdminLogService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        StoreAdminLog entity = new StoreAdminLog();
        entity.setId(1L);
        storeAdminLogService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = storeAdminLogService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}