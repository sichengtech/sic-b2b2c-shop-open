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

import com.sicheng.admin.product.entity.ProductPictureMapping;
import com.sicheng.admin.product.service.ProductPictureMappingService;
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
 * <p>标题: 单元测试--商品图片多对多中间表 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017-10-25
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ProductPictureMappingServiceTest {
    @Autowired
    private ProductPictureMappingService productPictureMappingService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        ProductPictureMapping productPictureMapping = productPictureMappingService.selectById(0L);
        Assert.assertNull(productPictureMapping);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<ProductPictureMapping> list = productPictureMappingService.selectByIdIn(paramList);
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
        Page<ProductPictureMapping> page = new Page<ProductPictureMapping>();
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999999L);//Long 主键id
        entity.setPId(999999999L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        page = productPictureMappingService.selectByWhere(page, new Wrapper(entity));
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
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999999L);//Long 主键id
        entity.setPId(999999999L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        List<ProductPictureMapping> list = productPictureMappingService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999999L);//Long 主键id
        entity.setPId(999999999L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        //因当表中数据量较大时，查全表很耗时，所以注释了
        //List<ProductPictureMapping> list= productPictureMappingService.selectAll(new Wrapper(entity));
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
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999998L);//Long 主键id
        entity.setPId(999999998L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999998L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        int rs = productPictureMappingService.insert(entity);
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
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999997L);//Long 主键id
        entity.setPId(999999997L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999997L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        int rs = productPictureMappingService.insertSelective(entity);
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
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999999L);//Long 主键id
        entity.setPId(999999999L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        int rs = productPictureMappingService.updateById(entity);
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
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999999L);//Long 主键id
        entity.setPId(999999999L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        ProductPictureMapping condition = new ProductPictureMapping();
        condition.setId(2L);
        int rs = productPictureMappingService.updateByWhere(entity, new Wrapper(condition));
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
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999999L);//Long 主键id
        entity.setPId(999999999L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        int rs = productPictureMappingService.updateByIdSelective(entity);
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
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999999L);//Long 主键id
        entity.setPId(999999999L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        ProductPictureMapping condition = new ProductPictureMapping();
        condition.setId(2L);
        int rs = productPictureMappingService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = productPictureMappingService.deleteById(1L);
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
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999999L);//Long 主键id
        entity.setPId(999999999L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        int rs = productPictureMappingService.deleteByWhere(new Wrapper(entity));
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
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(999999999L);//Long 主键id
        entity.setPId(999999999L);//Long 商品ID
        entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
        entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
        entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
        int rs = productPictureMappingService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        ProductPictureMapping p1 = new ProductPictureMapping();
        p1.setCreateDate(new Date());
        //p1.setBak1("bak1");

        ProductPictureMapping p2 = new ProductPictureMapping();//p2的属性全是空值

        //productPictureMappingService.selectAll(null);
        //productPictureMappingService.selectAll(new Wrapper(p2));
        productPictureMappingService.countByWhere(null);
        productPictureMappingService.countByWhere(new Wrapper(p2));

        try {
            productPictureMappingService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productPictureMappingService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productPictureMappingService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productPictureMappingService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productPictureMappingService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productPictureMappingService.deleteByWhere(null);
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
        List<ProductPictureMapping> list = new ArrayList<ProductPictureMapping>();
        for (int i = 0; i < 1; i++) {
            ProductPictureMapping entity = new ProductPictureMapping();
            entity.setId(999999999L);//Long 主键id
            entity.setPId(999999999L);//Long 商品ID
            entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
            entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
            entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
            list.add(entity);
        }
        boolean rs = productPictureMappingService.insertBatch(list);
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
        List<ProductPictureMapping> list = new ArrayList<ProductPictureMapping>();
        for (int i = 0; i < 1; i++) {
            ProductPictureMapping entity = new ProductPictureMapping();
            entity.setId(999999996L);//Long 主键id
            entity.setPId(999999996L);//Long 商品ID
            entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
            entity.setImgId(999999996L);//Long 图片的ID，商家相册空间中的图片ID
            entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
            list.add(entity);
        }
        boolean rs = productPictureMappingService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<ProductPictureMapping> list = new ArrayList<ProductPictureMapping>();
        for (int i = 0; i < 1; i++) {
            ProductPictureMapping entity = new ProductPictureMapping();
            entity.setId(999999999L);//Long 主键id
            entity.setPId(999999999L);//Long 商品ID
            entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
            entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
            entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
            list.add(entity);
        }
        boolean rs = productPictureMappingService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<ProductPictureMapping> list = new ArrayList<ProductPictureMapping>();
        for (int i = 0; i < 1; i++) {
            ProductPictureMapping entity = new ProductPictureMapping();
            entity.setId(999999999L);//Long 主键id
            entity.setPId(999999999L);//Long 商品ID
            entity.setColor("a");//String 颜色，商品的每种颜色都有一套图片。(由规格1决定，规格1的值有几种，就有几套图片
            entity.setImgId(999999999L);//Long 图片的ID，商家相册空间中的图片ID
            entity.setSort(888888888);//Integer 排序，排序从1起，为1的表示首图
            list.add(entity);
        }
        boolean rs = productPictureMappingService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        ProductPictureMapping entity = new ProductPictureMapping();
        entity.setId(1L);
        productPictureMappingService.selectOne(new Wrapper(entity));
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
        int rs = productPictureMappingService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}