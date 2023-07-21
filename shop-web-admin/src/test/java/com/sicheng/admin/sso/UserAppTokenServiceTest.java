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
package com.sicheng.admin.sso;

import com.sicheng.admin.sso.entity.UserAppToken;
import com.sicheng.admin.sso.service.UserAppTokenService;
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
 * <p>标题: 单元测试--会员app token Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 赵磊
 * @version 2019-01-29
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class UserAppTokenServiceTest {
    @Autowired
    private UserAppTokenService userAppTokenService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        UserAppToken userAppToken = userAppTokenService.selectById(0L);
        Assert.assertNull(userAppToken);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<UserAppToken> list = userAppTokenService.selectByIdIn(paramList);
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
        Page<UserAppToken> page = new Page<UserAppToken>();
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999999L);//Long 主键
        entity.setUId(999999999L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        page = userAppTokenService.selectByWhere(page, new Wrapper(entity));
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
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999999L);//Long 主键
        entity.setUId(999999999L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        List<UserAppToken> list = userAppTokenService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999999L);//Long 主键
        entity.setUId(999999999L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        //因当表中数据量较大时，查全表很耗时，所以注释了
        //List<UserAppToken> list= userAppTokenService.selectAll(new Wrapper(entity));
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
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999998L);//Long 主键
        entity.setUId(999999998L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        int rs = userAppTokenService.insert(entity);
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
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999997L);//Long 主键
        entity.setUId(999999997L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        int rs = userAppTokenService.insertSelective(entity);
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
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999999L);//Long 主键
        entity.setUId(999999999L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        int rs = userAppTokenService.updateById(entity);
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
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999999L);//Long 主键
        entity.setUId(999999999L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        UserAppToken condition = new UserAppToken();
        condition.setId(2L);
        int rs = userAppTokenService.updateByWhere(entity, new Wrapper(condition));
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
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999999L);//Long 主键
        entity.setUId(999999999L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        int rs = userAppTokenService.updateByIdSelective(entity);
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
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999999L);//Long 主键
        entity.setUId(999999999L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        UserAppToken condition = new UserAppToken();
        condition.setId(2L);
        int rs = userAppTokenService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = userAppTokenService.deleteById(1L);
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
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999999L);//Long 主键
        entity.setUId(999999999L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        int rs = userAppTokenService.deleteByWhere(new Wrapper(entity));
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
        UserAppToken entity = new UserAppToken();
        entity.setTId(999999999L);//Long 主键
        entity.setUId(999999999L);//Long 会员ID
        entity.setToken("a");//String 令牌（uuid）
        entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
        entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
        int rs = userAppTokenService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        UserAppToken p1 = new UserAppToken();
        p1.setCreateDate(new Date());
        //p1.setBak1("bak1");

        UserAppToken p2 = new UserAppToken();//p2的属性全是空值

        //userAppTokenService.selectAll(null);
        //userAppTokenService.selectAll(new Wrapper(p2));
        userAppTokenService.countByWhere(null);
        userAppTokenService.countByWhere(new Wrapper(p2));

        try {
            userAppTokenService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userAppTokenService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userAppTokenService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userAppTokenService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userAppTokenService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userAppTokenService.deleteByWhere(null);
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
        List<UserAppToken> list = new ArrayList<UserAppToken>();
        for (int i = 0; i < 1; i++) {
            UserAppToken entity = new UserAppToken();
            entity.setTId(999999999L);//Long 主键
            entity.setUId(999999999L);//Long 会员ID
            entity.setToken("a");//String 令牌（uuid）
            entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
            entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
            list.add(entity);
        }
        boolean rs = userAppTokenService.insertBatch(list);
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
        List<UserAppToken> list = new ArrayList<UserAppToken>();
        for (int i = 0; i < 1; i++) {
            UserAppToken entity = new UserAppToken();
            entity.setTId(999999996L);//Long 主键
            entity.setUId(999999996L);//Long 会员ID
            entity.setToken("a");//String 令牌（uuid）
            entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
            entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
            list.add(entity);
        }
        boolean rs = userAppTokenService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<UserAppToken> list = new ArrayList<UserAppToken>();
        for (int i = 0; i < 1; i++) {
            UserAppToken entity = new UserAppToken();
            entity.setTId(999999999L);//Long 主键
            entity.setUId(999999999L);//Long 会员ID
            entity.setToken("a");//String 令牌（uuid）
            entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
            entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
            list.add(entity);
        }
        boolean rs = userAppTokenService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<UserAppToken> list = new ArrayList<UserAppToken>();
        for (int i = 0; i < 1; i++) {
            UserAppToken entity = new UserAppToken();
            entity.setTId(999999999L);//Long 主键
            entity.setUId(999999999L);//Long 会员ID
            entity.setToken("a");//String 令牌（uuid）
            entity.setType("a");//String 业务类型：0用户是前台会员，1用户是后台管理员。shop有两套用户表，目前只给前台会员开发了app，未给后台管理员开发app
            entity.setStatus("a");//String 是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setValidDate(new Date());//java.util.Date 有效期的开始时间，如2019-01-01，有效果是3个月，将于2019-03-01的前一秒到期。当生成续命时，valid_date值被更新
            list.add(entity);
        }
        boolean rs = userAppTokenService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        UserAppToken entity = new UserAppToken();
        entity.setId(1L);
        userAppTokenService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        List<Object> list = new ArrayList<Object>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        int rs = userAppTokenService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}