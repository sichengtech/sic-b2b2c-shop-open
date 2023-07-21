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
package com.sicheng.admin.cms;

import com.sicheng.admin.cms.entity.Article;
import com.sicheng.admin.cms.service.ArticleService;
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
import java.util.Date;
import java.util.List;

/**
 * <p>标题: 单元测试--文章表 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 蔡龙
 * @version 2017-10-27
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        Article article = articleService.selectById(0L);
        Assert.assertNull(article);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<Article> list = articleService.selectByIdIn(paramList);
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
        Page<Article> page = new Page<Article>();
        Article entity = new Article();
        entity.setId(999999999L);//Long 编号
        entity.setCategoryId(999999999L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        page = articleService.selectByWhere(page, new Wrapper(entity));
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
        Article entity = new Article();
        entity.setId(999999999L);//Long 编号
        entity.setCategoryId(999999999L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        List<Article> list = articleService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        Article entity = new Article();
        entity.setId(999999999L);//Long 编号
        entity.setCategoryId(999999999L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        //因当表中数据量较大时，查全表很耗时，所以注释了
        //List<Article> list= articleService.selectAll(new Wrapper(entity));
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
        Article entity = new Article();
        entity.setId(999999998L);//Long 编号
        entity.setCategoryId(999999998L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setViewConfig("a");//String 视图配置
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        int rs = articleService.insert(entity);
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
        Article entity = new Article();
        entity.setId(999999997L);//Long 编号
        entity.setCategoryId(999999997L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setViewConfig("a");//String 视图配置
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        int rs = articleService.insertSelective(entity);
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
        Article entity = new Article();
        entity.setId(999999999L);//Long 编号
        entity.setCategoryId(999999999L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setViewConfig("a");//String 视图配置
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        int rs = articleService.updateById(entity);
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
        Article entity = new Article();
        entity.setId(999999999L);//Long 编号
        entity.setCategoryId(999999999L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setViewConfig("a");//String 视图配置
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        Article condition = new Article();
        condition.setId(2L);
        int rs = articleService.updateByWhere(entity, new Wrapper(condition));
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
        Article entity = new Article();
        entity.setId(999999999L);//Long 编号
        entity.setCategoryId(999999999L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setViewConfig("a");//String 视图配置
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        int rs = articleService.updateByIdSelective(entity);
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
        Article entity = new Article();
        entity.setId(999999999L);//Long 编号
        entity.setCategoryId(999999999L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setViewConfig("a");//String 视图配置
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        Article condition = new Article();
        condition.setId(2L);
        int rs = articleService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = articleService.deleteById(1L);
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
        Article entity = new Article();
        entity.setId(999999999L);//Long 编号
        entity.setCategoryId(999999999L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        int rs = articleService.deleteByWhere(new Wrapper(entity));
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
        Article entity = new Article();
        entity.setId(999999999L);//Long 编号
        entity.setCategoryId(999999999L);//Long 栏目编号
        entity.setTitle("a");//String 标题
        entity.setLink("a");//String 文章链接
        entity.setColor("a");//String 标题颜色
        entity.setImage("a");//String 文章图片
        entity.setKeywords("a");//String 关键字
        entity.setDescription("a");//String 描述、摘要
        entity.setWeight(888888888);//Integer 权重，越大越靠前
        entity.setWeightDate(new Date());//java.util.Date 权重期限
        entity.setHits(888888888);//Integer 点击数
        entity.setPosid("a");//String 推荐位，多选
        entity.setCustomContentView("a");//String 自定义内容视图
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setRemarks("备注信息");//String 备注信息
        entity.setDelFlag("0");//String 删除标记
        int rs = articleService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        Article p1 = new Article();
        p1.setCreateDate(new Date());
        //p1.setBak1("bak1");

        Article p2 = new Article();//p2的属性全是空值

        //articleService.selectAll(null);
        //articleService.selectAll(new Wrapper(p2));
        articleService.countByWhere(null);
        articleService.countByWhere(new Wrapper(p2));

        try {
            articleService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            articleService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            articleService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            articleService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            articleService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            articleService.deleteByWhere(null);
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
        List<Article> list = new ArrayList<Article>();
        for (int i = 0; i < 1; i++) {
            Article entity = new Article();
            entity.setId(999999999L);//Long 编号
            entity.setCategoryId(999999999L);//Long 栏目编号
            entity.setTitle("a");//String 标题
            entity.setLink("a");//String 文章链接
            entity.setColor("a");//String 标题颜色
            entity.setImage("a");//String 文章图片
            entity.setKeywords("a");//String 关键字
            entity.setDescription("a");//String 描述、摘要
            entity.setWeight(888888888);//Integer 权重，越大越靠前
            entity.setWeightDate(new Date());//java.util.Date 权重期限
            entity.setHits(888888888);//Integer 点击数
            entity.setPosid("a");//String 推荐位，多选
            entity.setCustomContentView("a");//String 自定义内容视图
            entity.setViewConfig("a");//String 视图配置
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setRemarks("备注信息");//String 备注信息
            entity.setDelFlag("0");//String 删除标记
            list.add(entity);
        }
        boolean rs = articleService.insertBatch(list);
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
        List<Article> list = new ArrayList<Article>();
        for (int i = 0; i < 1; i++) {
            Article entity = new Article();
            entity.setId(999999996L);//Long 编号
            entity.setCategoryId(999999996L);//Long 栏目编号
            entity.setTitle("a");//String 标题
            entity.setLink("a");//String 文章链接
            entity.setColor("a");//String 标题颜色
            entity.setImage("a");//String 文章图片
            entity.setKeywords("a");//String 关键字
            entity.setDescription("a");//String 描述、摘要
            entity.setWeight(888888888);//Integer 权重，越大越靠前
            entity.setWeightDate(new Date());//java.util.Date 权重期限
            entity.setHits(888888888);//Integer 点击数
            entity.setPosid("a");//String 推荐位，多选
            entity.setCustomContentView("a");//String 自定义内容视图
            entity.setViewConfig("a");//String 视图配置
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setRemarks("备注信息");//String 备注信息
            entity.setDelFlag("0");//String 删除标记
            list.add(entity);
        }
        boolean rs = articleService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<Article> list = new ArrayList<Article>();
        for (int i = 0; i < 1; i++) {
            Article entity = new Article();
            entity.setId(999999999L);//Long 编号
            entity.setCategoryId(999999999L);//Long 栏目编号
            entity.setTitle("a");//String 标题
            entity.setLink("a");//String 文章链接
            entity.setColor("a");//String 标题颜色
            entity.setImage("a");//String 文章图片
            entity.setKeywords("a");//String 关键字
            entity.setDescription("a");//String 描述、摘要
            entity.setWeight(888888888);//Integer 权重，越大越靠前
            entity.setWeightDate(new Date());//java.util.Date 权重期限
            entity.setHits(888888888);//Integer 点击数
            entity.setPosid("a");//String 推荐位，多选
            entity.setCustomContentView("a");//String 自定义内容视图
            entity.setViewConfig("a");//String 视图配置
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setRemarks("备注信息");//String 备注信息
            entity.setDelFlag("0");//String 删除标记
            list.add(entity);
        }
        boolean rs = articleService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<Article> list = new ArrayList<Article>();
        for (int i = 0; i < 1; i++) {
            Article entity = new Article();
            entity.setId(999999999L);//Long 编号
            entity.setCategoryId(999999999L);//Long 栏目编号
            entity.setTitle("a");//String 标题
            entity.setLink("a");//String 文章链接
            entity.setColor("a");//String 标题颜色
            entity.setImage("a");//String 文章图片
            entity.setKeywords("a");//String 关键字
            entity.setDescription("a");//String 描述、摘要
            entity.setWeight(888888888);//Integer 权重，越大越靠前
            entity.setWeightDate(new Date());//java.util.Date 权重期限
            entity.setHits(888888888);//Integer 点击数
            entity.setPosid("a");//String 推荐位，多选
            entity.setCustomContentView("a");//String 自定义内容视图
            entity.setViewConfig("a");//String 视图配置
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setRemarks("备注信息");//String 备注信息
            entity.setDelFlag("0");//String 删除标记
            list.add(entity);
        }
        boolean rs = articleService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        Article entity = new Article();
        entity.setId(1L);
        articleService.selectOne(new Wrapper(entity));
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
        int rs = articleService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}