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
package com.sicheng.admin.sys;

import com.sicheng.admin.sys.entity.SysSmsServer;
import com.sicheng.admin.sys.service.SysSmsServerService;
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
 * <p>标题: 单元测试--管理短信的发送 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 张加利
 * @version 2017-02-06
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class SysSmsServerServiceTest {
    @Autowired
    private SysSmsServerService sysSmsServerService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        SysSmsServer sysSmsServer = sysSmsServerService.selectById(0L);
        Assert.assertNull(sysSmsServer);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<SysSmsServer> list = sysSmsServerService.selectByIdIn(paramList);
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
        Page<SysSmsServer> page = new Page<SysSmsServer>();
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        page = sysSmsServerService.selectByWhere(page, new Wrapper(entity));
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
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        List<SysSmsServer> list = sysSmsServerService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        List<SysSmsServer> list = sysSmsServerService.selectAll(new Wrapper(entity));
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
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = sysSmsServerService.insert(entity);
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
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = sysSmsServerService.insertSelective(entity);
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
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = sysSmsServerService.updateById(entity);
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
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        SysSmsServer condition = new SysSmsServer();
        condition.setId(2L);
        int rs = sysSmsServerService.updateByWhere(entity, new Wrapper(condition));
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
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = sysSmsServerService.updateByIdSelective(entity);
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
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        SysSmsServer condition = new SysSmsServer();
        condition.setId(2L);
        int rs = sysSmsServerService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = sysSmsServerService.deleteById(1L);
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
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = sysSmsServerService.deleteByWhere(new Wrapper(entity));
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
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);//Long 主键
        entity.setStatus("1");//String 状态，0停用，1启用
        entity.setUrl("1");//String 短信网关URL
        entity.setUsername("1");//String 用户名
        entity.setPwd("1");//String 密码
        entity.setAccessKey("1");//String ACCESS KEY
        entity.setAppId("1");//String APP ID
        entity.setClientId("1");//String 客户id
        entity.setToken("1");//String 令牌
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = sysSmsServerService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        SysSmsServer p1 = new SysSmsServer();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        SysSmsServer p2 = new SysSmsServer();//p2的属性全是空值

        sysSmsServerService.selectAll(null);
        sysSmsServerService.selectAll(new Wrapper(p2));
        sysSmsServerService.countByWhere(null);
        sysSmsServerService.countByWhere(new Wrapper(p2));

        try {
            sysSmsServerService.updateByWhere(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysSmsServerService.updateByWhereSelective(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysSmsServerService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysSmsServerService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysSmsServerService.deleteByWhere(new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysSmsServerService.deleteByWhere(null);
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
        List<SysSmsServer> list = new ArrayList<SysSmsServer>();
        for (int i = 0; i < 10; i++) {
            SysSmsServer entity = new SysSmsServer();
            entity.setId(1L);//Long 主键
            entity.setStatus("1");//String 状态，0停用，1启用
            entity.setUrl("1");//String 短信网关URL
            entity.setUsername("1");//String 用户名
            entity.setPwd("1");//String 密码
            entity.setAccessKey("1");//String ACCESS KEY
            entity.setAppId("1");//String APP ID
            entity.setClientId("1");//String 客户id
            entity.setToken("1");//String 令牌
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = sysSmsServerService.insertBatch(list);
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
        List<SysSmsServer> list = new ArrayList<SysSmsServer>();
        for (int i = 0; i < 10; i++) {
            SysSmsServer entity = new SysSmsServer();
            entity.setId(1L);//Long 主键
            entity.setStatus("1");//String 状态，0停用，1启用
            entity.setUrl("1");//String 短信网关URL
            entity.setUsername("1");//String 用户名
            entity.setPwd("1");//String 密码
            entity.setAccessKey("1");//String ACCESS KEY
            entity.setAppId("1");//String APP ID
            entity.setClientId("1");//String 客户id
            entity.setToken("1");//String 令牌
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = sysSmsServerService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<SysSmsServer> list = new ArrayList<SysSmsServer>();
        for (int i = 0; i < 10; i++) {
            SysSmsServer entity = new SysSmsServer();
            entity.setId(1L);//Long 主键
            entity.setStatus("1");//String 状态，0停用，1启用
            entity.setUrl("1");//String 短信网关URL
            entity.setUsername("1");//String 用户名
            entity.setPwd("1");//String 密码
            entity.setAccessKey("1");//String ACCESS KEY
            entity.setAppId("1");//String APP ID
            entity.setClientId("1");//String 客户id
            entity.setToken("1");//String 令牌
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = sysSmsServerService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<SysSmsServer> list = new ArrayList<SysSmsServer>();
        for (int i = 0; i < 10; i++) {
            SysSmsServer entity = new SysSmsServer();
            entity.setId(1L);//Long 主键
            entity.setStatus("1");//String 状态，0停用，1启用
            entity.setUrl("1");//String 短信网关URL
            entity.setUsername("1");//String 用户名
            entity.setPwd("1");//String 密码
            entity.setAccessKey("1");//String ACCESS KEY
            entity.setAppId("1");//String APP ID
            entity.setClientId("1");//String 客户id
            entity.setToken("1");//String 令牌
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = sysSmsServerService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        SysSmsServer entity = new SysSmsServer();
        entity.setId(1L);
        sysSmsServerService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = sysSmsServerService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}