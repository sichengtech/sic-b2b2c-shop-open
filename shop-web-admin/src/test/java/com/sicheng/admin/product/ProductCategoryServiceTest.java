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
package com.sicheng.admin.product;

import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.admin.product.service.ProductCategoryService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: 单元测试--商品分类 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 蔡龙
 * @version 2017-10-12
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ProductCategoryServiceTest {
    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        ProductCategory productCategory = productCategoryService.selectById(0L);
        Assert.assertNull(productCategory);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<ProductCategory> list = productCategoryService.selectByIdIn(paramList);
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
        Page<ProductCategory> page = new Page<ProductCategory>();
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999999L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999999);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        page = productCategoryService.selectByWhere(page, new Wrapper(entity));
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
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999999L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999999);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        List<ProductCategory> list = productCategoryService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999999L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999999);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        //因当表中数据量较大时，查全表很耗时，所以注释了
        //List<ProductCategory> list= productCategoryService.selectAll(new Wrapper(entity));
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
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999998L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999998);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        int rs = productCategoryService.insert(entity);
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
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999997L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999997);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        int rs = productCategoryService.insertSelective(entity);
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
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999999L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999999);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        int rs = productCategoryService.updateById(entity);
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
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999999L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999999);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        ProductCategory condition = new ProductCategory();
        condition.setId(2L);
        int rs = productCategoryService.updateByWhere(entity, new Wrapper(condition));
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
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999999L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999999);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        int rs = productCategoryService.updateByIdSelective(entity);
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
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999999L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999999);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        ProductCategory condition = new ProductCategory();
        condition.setId(2L);
        int rs = productCategoryService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = productCategoryService.deleteById(1L);
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
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999999L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999999);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        int rs = productCategoryService.deleteByWhere(new Wrapper(entity));
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
        ProductCategory entity = new ProductCategory();
        entity.setCategoryId(999999999L);//Long 主键
        entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
        entity.setName("a");//String 类别名称
        entity.setParent(new ProductCategory(0L));//This 父类id
        entity.setParentIds("a");//String 所有父级标号
        entity.setCateLevel(999999999);//Long 类别层级
        entity.setSort(888888888);//Integer 排序
        entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
        entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
        entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
        entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
        entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
        entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("a");//String 备用字段1
        entity.setBak2("a");//String 备用字段2
        entity.setBak3("a");//String 备用字段3
        entity.setBak4("a");//String 备用字段4
        entity.setBak5("a");//String 备用字段5
        entity.setBak6("a");//String 备用字段6
        entity.setBak7("a");//String 备用字段7
        entity.setBak8("a");//String 备用字段8
        entity.setBak9("a");//String 备用字段9
        entity.setBak10("a");//String 备用字段10
        entity.setFirstLetter("a");//String 分类名称首字母
        int rs = productCategoryService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        ProductCategory p1 = new ProductCategory();
        p1.setCreateDate(new Date());
        //p1.setBak1("bak1");

        ProductCategory p2 = new ProductCategory();//p2的属性全是空值

        //productCategoryService.selectAll(null);
        //productCategoryService.selectAll(new Wrapper(p2));
        productCategoryService.countByWhere(null);
        productCategoryService.countByWhere(new Wrapper(p2));

        try {
            productCategoryService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productCategoryService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productCategoryService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productCategoryService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productCategoryService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productCategoryService.deleteByWhere(null);
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
        List<ProductCategory> list = new ArrayList<ProductCategory>();
        for (int i = 0; i < 1; i++) {
            ProductCategory entity = new ProductCategory();
            entity.setCategoryId(999999999L);//Long 主键
            entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
            entity.setName("a");//String 类别名称
            entity.setParent(new ProductCategory(0L));//This 父类id
            entity.setParentIds("a");//String 所有父级标号
            entity.setCateLevel(999999999);//Long 类别层级
            entity.setSort(888888888);//Integer 排序
            entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
            entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
            entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
            entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
            entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("a");//String 备用字段1
            entity.setBak2("a");//String 备用字段2
            entity.setBak3("a");//String 备用字段3
            entity.setBak4("a");//String 备用字段4
            entity.setBak5("a");//String 备用字段5
            entity.setBak6("a");//String 备用字段6
            entity.setBak7("a");//String 备用字段7
            entity.setBak8("a");//String 备用字段8
            entity.setBak9("a");//String 备用字段9
            entity.setBak10("a");//String 备用字段10
            entity.setFirstLetter("a");//String 分类名称首字母
            list.add(entity);
        }
        boolean rs = productCategoryService.insertBatch(list);
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
        List<ProductCategory> list = new ArrayList<ProductCategory>();
        for (int i = 0; i < 1; i++) {
            ProductCategory entity = new ProductCategory();
            entity.setCategoryId(999999996L);//Long 主键
            entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
            entity.setName("a");//String 类别名称
            entity.setParent(new ProductCategory(0L));//This 父类id
            entity.setParentIds("a");//String 所有父级标号
            entity.setCateLevel(999999996);//Long 类别层级
            entity.setSort(888888888);//Integer 排序
            entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
            entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
            entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
            entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
            entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("a");//String 备用字段1
            entity.setBak2("a");//String 备用字段2
            entity.setBak3("a");//String 备用字段3
            entity.setBak4("a");//String 备用字段4
            entity.setBak5("a");//String 备用字段5
            entity.setBak6("a");//String 备用字段6
            entity.setBak7("a");//String 备用字段7
            entity.setBak8("a");//String 备用字段8
            entity.setBak9("a");//String 备用字段9
            entity.setBak10("a");//String 备用字段10
            entity.setFirstLetter("a");//String 分类名称首字母
            list.add(entity);
        }
        boolean rs = productCategoryService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<ProductCategory> list = new ArrayList<ProductCategory>();
        for (int i = 0; i < 1; i++) {
            ProductCategory entity = new ProductCategory();
            entity.setCategoryId(999999999L);//Long 主键
            entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
            entity.setName("a");//String 类别名称
            entity.setParent(new ProductCategory(0L));//This 父类id
            entity.setParentIds("a");//String 所有父级标号
            entity.setCateLevel(999999999);//Long 类别层级
            entity.setSort(888888888);//Integer 排序
            entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
            entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
            entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
            entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
            entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("a");//String 备用字段1
            entity.setBak2("a");//String 备用字段2
            entity.setBak3("a");//String 备用字段3
            entity.setBak4("a");//String 备用字段4
            entity.setBak5("a");//String 备用字段5
            entity.setBak6("a");//String 备用字段6
            entity.setBak7("a");//String 备用字段7
            entity.setBak8("a");//String 备用字段8
            entity.setBak9("a");//String 备用字段9
            entity.setBak10("a");//String 备用字段10
            entity.setFirstLetter("a");//String 分类名称首字母
            list.add(entity);
        }
        boolean rs = productCategoryService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<ProductCategory> list = new ArrayList<ProductCategory>();
        for (int i = 0; i < 1; i++) {
            ProductCategory entity = new ProductCategory();
            entity.setCategoryId(999999999L);//Long 主键
            entity.setCategoryImg("a");//String 分类导航图标（只有在层级为0的情况下有图片地址）
            entity.setName("a");//String 类别名称
            entity.setParent(new ProductCategory(0L));//This 父类id
            entity.setParentIds("a");//String 所有父级标号
            entity.setCateLevel(999999999);//Long 类别层级
            entity.setSort(888888888);//Integer 排序
            entity.setType("a");//String 类型：1父级分类、2终极分类、3超连接分类
            entity.setIsLocked("a");//String 是否锁定(1锁定,0未锁),锁定后不显示，不能向本分类下发商品
            entity.setCommission(new BigDecimal("0.1"));//Double 分佣比例
            entity.setRecommendSpu("a");//String 推荐SPU（为商品id，多个值用豆号分隔）
            entity.setBrandIds("a");//String 绑定的品牌（为品牌id，多个值用豆号分隔）
            entity.setCreateBy(new User(3L));//com.sicheng.admin.sys.entity.User 创建者
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateBy(new User(4L));//com.sicheng.admin.sys.entity.User 更新者
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("a");//String 备用字段1
            entity.setBak2("a");//String 备用字段2
            entity.setBak3("a");//String 备用字段3
            entity.setBak4("a");//String 备用字段4
            entity.setBak5("a");//String 备用字段5
            entity.setBak6("a");//String 备用字段6
            entity.setBak7("a");//String 备用字段7
            entity.setBak8("a");//String 备用字段8
            entity.setBak9("a");//String 备用字段9
            entity.setBak10("a");//String 备用字段10
            entity.setFirstLetter("a");//String 分类名称首字母
            list.add(entity);
        }
        boolean rs = productCategoryService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        ProductCategory entity = new ProductCategory();
        entity.setId(1L);
        productCategoryService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        List<Object> list = new ArrayList<Object>();
        list.add(1);
        list.add(2L);
        list.add(3L);
        int rs = productCategoryService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}