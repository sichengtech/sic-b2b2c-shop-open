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
package com.sicheng.admin.sys;

import com.sicheng.admin.sys.entity.Log;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.admin.sys.service.LogService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import org.apache.ibatis.cursor.Cursor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>标题: 单元测试--日志 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fxx
 * @version 2017-02-08
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class SysLogServiceTest {
    @Autowired
    private LogService sysLogService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        Log sysLog = sysLogService.selectById(0L);
        Assert.assertNull(sysLog);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<Log> list = sysLogService.selectByIdIn(paramList);
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
        Page<Log> page = new Page<Log>();
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式

        page = sysLogService.selectByWhere(page, new Wrapper(entity));
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
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式

        List<Log> list = sysLogService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String exception

        List<Log> list = sysLogService.selectAll(new Wrapper(entity));
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
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String exception

        int rs = sysLogService.insert(entity);
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
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String exception

        int rs = sysLogService.insertSelective(entity);
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
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String exception

        int rs = sysLogService.updateById(entity);
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
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String exception

        Log condition = new Log();
        condition.setId(2L);
        int rs = sysLogService.updateByWhere(entity, new Wrapper(condition));
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
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String exception

        int rs = sysLogService.updateByIdSelective(entity);
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
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式
        entity.setParams("1");//String 操作提交的数据
        entity.setException("1");//String exception

        Log condition = new Log();
        condition.setId(2L);
        int rs = sysLogService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = sysLogService.deleteById(1L);
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
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式

        int rs = sysLogService.deleteByWhere(new Wrapper(entity));
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
        Log entity = new Log();
        entity.setId(1L);//Long id
        entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
        entity.setTitle("1");//String 日志标题
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
        entity.setCreateDate(new Date());//java.util.Date create_date
        entity.setRemoteAddr("1");//String 操作用户的IP地址
        entity.setUserAgent("1");//String 操作用户代理信息
        entity.setRequestUri("1");//String 操作的URI
        entity.setMethod("1");//String 操作的方式

        int rs = sysLogService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        Log p1 = new Log();
        p1.setCreateDate(new Date());
        //p1.setBak1("bak1");

        Log p2 = new Log();//p2的属性全是空值

        sysLogService.selectAll(null);
        sysLogService.selectAll(new Wrapper(p2));
        sysLogService.countByWhere(null);
        sysLogService.countByWhere(new Wrapper(p2));

        try {
            sysLogService.updateByWhere(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysLogService.updateByWhereSelective(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysLogService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysLogService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysLogService.deleteByWhere(new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            sysLogService.deleteByWhere(null);
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
        List<Log> list = new ArrayList<Log>();
        for (int i = 0; i < 10; i++) {
            Log entity = new Log();
            entity.setId(1L);//Long id
            entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
            entity.setTitle("1");//String 日志标题
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
            entity.setCreateDate(new Date());//java.util.Date create_date
            entity.setRemoteAddr("1");//String 操作用户的IP地址
            entity.setUserAgent("1");//String 操作用户代理信息
            entity.setRequestUri("1");//String 操作的URI
            entity.setMethod("1");//String 操作的方式
            entity.setParams("1");//String 操作提交的数据
            entity.setException("1");//String exception

            list.add(entity);
        }
        boolean rs = sysLogService.insertBatch(list);
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
        List<Log> list = new ArrayList<Log>();
        for (int i = 0; i < 10; i++) {
            Log entity = new Log();
            entity.setId(1L);//Long id
            entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
            entity.setTitle("1");//String 日志标题
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
            entity.setCreateDate(new Date());//java.util.Date create_date
            entity.setRemoteAddr("1");//String 操作用户的IP地址
            entity.setUserAgent("1");//String 操作用户代理信息
            entity.setRequestUri("1");//String 操作的URI
            entity.setMethod("1");//String 操作的方式
            entity.setParams("1");//String 操作提交的数据
            entity.setException("1");//String exception

            list.add(entity);
        }
        boolean rs = sysLogService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<Log> list = new ArrayList<Log>();
        for (int i = 0; i < 10; i++) {
            Log entity = new Log();
            entity.setId(1L);//Long id
            entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
            entity.setTitle("1");//String 日志标题
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
            entity.setCreateDate(new Date());//java.util.Date create_date
            entity.setRemoteAddr("1");//String 操作用户的IP地址
            entity.setUserAgent("1");//String 操作用户代理信息
            entity.setRequestUri("1");//String 操作的URI
            entity.setMethod("1");//String 操作的方式
            entity.setParams("1");//String 操作提交的数据
            entity.setException("1");//String exception

            list.add(entity);
        }
        boolean rs = sysLogService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<Log> list = new ArrayList<Log>();
        for (int i = 0; i < 10; i++) {
            Log entity = new Log();
            entity.setId(1L);//Long id
            entity.setType("1");//String 日志类型（1：接入日志；2：错误日志）
            entity.setTitle("1");//String 日志标题
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User create_by
            entity.setCreateDate(new Date());//java.util.Date create_date
            entity.setRemoteAddr("1");//String 操作用户的IP地址
            entity.setUserAgent("1");//String 操作用户代理信息
            entity.setRequestUri("1");//String 操作的URI
            entity.setMethod("1");//String 操作的方式
            entity.setParams("1");//String 操作提交的数据
            entity.setException("1");//String exception

            list.add(entity);
        }
        boolean rs = sysLogService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        Log entity = new Log();
        entity.setId(1L);
        sysLogService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = sysLogService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

    /**
     * 测试一 （使用了泛型）
     * 查询并返回Cusror游标
     */
    @Test
    public void test_select() {
        String sqlId = "selectByWhere";
        Wrapper wrapper = new Wrapper();
        wrapper.and("id >", 19019);
        Cursor<Log> cursor = sysLogService.selectCursor(sqlId, wrapper);
        Iterator<Log> iter = cursor.iterator();
//		while (iter.hasNext()) {
//			Log log=iter.next();
//			System.out.println(log.toString());
//		}
    }

    /**
     * 测试二 （查询的非本表的数据）
     * 查询并返回Cusror游标
     */
    @Test
    public void test_select2() {
        String sqlId = "com.sicheng.admin.product.dao.ProductSpuDao.selectByWhere";//查询的非本表的数据
        Wrapper wrapper = new Wrapper();
        Cursor<?> cursor = sysLogService.selectCursor(sqlId, wrapper);
        Iterator<?> iter = cursor.iterator();
        while (iter.hasNext()) {
            Object row = iter.next();
            System.out.println(row.toString());
        }
    }

//	@Test
//	public void cursor(){
//		Connection conn = null;
//		ResultSet rs = null;
//		try {
//			//TYPE_SCROLL_INSENSITIVE 应用程序需要数据库快照。
//			//CONCUR_READ_ONLY 应用程序必须在结果集中进行一次（前进）传递。
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@123.56.88.235:1521:orcl","shop","my6297872");
//			PreparedStatement pstat = conn.prepareStatement("select * from sys_log",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			rs = pstat.executeQuery();
//			int i=0;
//			while(rs.next()){
//				System.out.println(i+"=====第一列："+rs.getInt(1)+"，第三列："+rs.getString(3));
//				i++;
//			}
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		finally {
//			try {
//				if(conn!=null){
//					conn.close();
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				if(rs!=null){
//					rs.close();
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

}