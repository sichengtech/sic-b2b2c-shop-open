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
package com.sicheng.admin.site;

import com.sicheng.admin.site.entity.SiteRecommendItem;
import com.sicheng.admin.site.service.SiteRecommendItemService;
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
 * <p>标题: 单元测试--推荐位详情 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fxx
 * @version 2017-09-27
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class SiteRecommendItemServiceTest {
    @Autowired
    private SiteRecommendItemService siteRecommendItemService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        SiteRecommendItem siteRecommendItem = siteRecommendItemService.selectById(0L);
        Assert.assertNull(siteRecommendItem);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<SiteRecommendItem> list = siteRecommendItemService.selectByIdIn(paramList);
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
        Page<SiteRecommendItem> page = new Page<SiteRecommendItem>();
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        page = siteRecommendItemService.selectByWhere(page, new Wrapper(entity));
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
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        List<SiteRecommendItem> list = siteRecommendItemService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        List<SiteRecommendItem> list = siteRecommendItemService.selectAll(new Wrapper(entity));
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
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        int rs = siteRecommendItemService.insert(entity);
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
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        int rs = siteRecommendItemService.insertSelective(entity);
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
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        int rs = siteRecommendItemService.updateById(entity);
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
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        SiteRecommendItem condition = new SiteRecommendItem();
        condition.setId(2L);
        int rs = siteRecommendItemService.updateByWhere(entity, new Wrapper(condition));
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
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        int rs = siteRecommendItemService.updateByIdSelective(entity);
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
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        SiteRecommendItem condition = new SiteRecommendItem();
        condition.setId(2L);
        int rs = siteRecommendItemService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = siteRecommendItemService.deleteById(1L);
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
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        int rs = siteRecommendItemService.deleteByWhere(new Wrapper(entity));
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
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setRiId(1L);//Long 主键
        entity.setRecommendId(1l);//Long 推荐位表id
        entity.setPath("1");//String 图片地址
        entity.setPId(1L);//Long 商品id
        entity.setId(1L);//Long 商品id
        entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        entity.setOperationContent("1");//String 操作内容
        entity.setSort(1);//排序
        entity.setAddInfo1("1");//String 附加值1
        entity.setAddInfo2("1");//String 附加值2
        entity.setAddInfo3("1");//String 附加值3
        entity.setAddInfo4("1");//String 附加值4
        entity.setAddInfo5("1");//String 附加值5
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间

        int rs = siteRecommendItemService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        SiteRecommendItem p1 = new SiteRecommendItem();
        p1.setCreateDate(new Date());

        SiteRecommendItem p2 = new SiteRecommendItem();//p2的属性全是空值

        siteRecommendItemService.selectAll(null);
        siteRecommendItemService.selectAll(new Wrapper(p2));
        siteRecommendItemService.countByWhere(null);
        siteRecommendItemService.countByWhere(new Wrapper(p2));

        try {
            siteRecommendItemService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            siteRecommendItemService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            siteRecommendItemService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            siteRecommendItemService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            siteRecommendItemService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            siteRecommendItemService.deleteByWhere(null);
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
        List<SiteRecommendItem> list = new ArrayList<SiteRecommendItem>();
        for (int i = 0; i < 10; i++) {
            SiteRecommendItem entity = new SiteRecommendItem();
            entity.setRiId(1L);//Long 主键
            entity.setRecommendId(1l);//Long 推荐位表id
            entity.setPath("1");//String 图片地址
            entity.setPId(1L);//Long 商品id
            entity.setId(1L);//Long 商品id
            entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
            entity.setOperationContent("1");//String 操作内容
            entity.setSort(1);//排序
            entity.setAddInfo1("1");//String 附加值1
            entity.setAddInfo2("1");//String 附加值2
            entity.setAddInfo3("1");//String 附加值3
            entity.setAddInfo4("1");//String 附加值4
            entity.setAddInfo5("1");//String 附加值5
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间

            list.add(entity);
        }
        boolean rs = siteRecommendItemService.insertBatch(list);
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
        List<SiteRecommendItem> list = new ArrayList<SiteRecommendItem>();
        for (int i = 0; i < 10; i++) {
            SiteRecommendItem entity = new SiteRecommendItem();
            entity.setRiId(1L);//Long 主键
            entity.setRecommendId(1l);//Long 推荐位表id
            entity.setPath("1");//String 图片地址
            entity.setPId(1L);//Long 商品id
            entity.setId(1L);//Long 商品id
            entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
            entity.setOperationContent("1");//String 操作内容
            entity.setSort(1);//排序
            entity.setAddInfo1("1");//String 附加值1
            entity.setAddInfo2("1");//String 附加值2
            entity.setAddInfo3("1");//String 附加值3
            entity.setAddInfo4("1");//String 附加值4
            entity.setAddInfo5("1");//String 附加值5
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间

            list.add(entity);
        }
        boolean rs = siteRecommendItemService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<SiteRecommendItem> list = new ArrayList<SiteRecommendItem>();
        for (int i = 0; i < 10; i++) {
            SiteRecommendItem entity = new SiteRecommendItem();
            entity.setRiId(1L);//Long 主键
            entity.setRecommendId(1l);//Long 推荐位表id
            entity.setPath("1");//String 图片地址
            entity.setPId(1L);//Long 商品id
            entity.setId(1L);//Long 商品id
            entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
            entity.setOperationContent("1");//String 操作内容
            entity.setSort(1);//排序
            entity.setAddInfo1("1");//String 附加值1
            entity.setAddInfo2("1");//String 附加值2
            entity.setAddInfo3("1");//String 附加值3
            entity.setAddInfo4("1");//String 附加值4
            entity.setAddInfo5("1");//String 附加值5
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间

            list.add(entity);
        }
        boolean rs = siteRecommendItemService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<SiteRecommendItem> list = new ArrayList<SiteRecommendItem>();
        for (int i = 0; i < 10; i++) {
            SiteRecommendItem entity = new SiteRecommendItem();
            entity.setRiId(1L);//Long 主键
            entity.setRecommendId(1l);//Long 推荐位表id
            entity.setPath("1");//String 图片地址
            entity.setPId(1L);//Long 商品id
            entity.setId(1L);//Long 商品id
            entity.setOperationType("1");//String 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
            entity.setOperationContent("1");//String 操作内容
            entity.setSort(1);//排序
            entity.setAddInfo1("1");//String 附加值1
            entity.setAddInfo2("1");//String 附加值2
            entity.setAddInfo3("1");//String 附加值3
            entity.setAddInfo4("1");//String 附加值4
            entity.setAddInfo5("1");//String 附加值5
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间

            list.add(entity);
        }
        boolean rs = siteRecommendItemService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        SiteRecommendItem entity = new SiteRecommendItem();
        entity.setId(1L);
        siteRecommendItemService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = siteRecommendItemService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}