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

import com.sicheng.admin.product.entity.ProductParam;
import com.sicheng.admin.product.service.ProductCategoryService;
import com.sicheng.admin.product.service.ProductParamService;
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
 * <p>标题: 单元测试--参数和参数值 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017-02-07
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ProductParamServiceTest {
    @Autowired
    private ProductParamService productParamService;
    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        ProductParam productParam = productParamService.selectById(0L);
        Assert.assertNull(productParam);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<ProductParam> list = productParamService.selectByIdIn(paramList);
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
        Page<ProductParam> page = new Page<ProductParam>();
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        page = productParamService.selectByWhere(page, new Wrapper(entity));
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
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        List<ProductParam> list = productParamService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        List<ProductParam> list = productParamService.selectAll(new Wrapper(entity));
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
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        int rs = productParamService.insert(entity);
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
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        int rs = productParamService.insertSelective(entity);
        Assert.assertEquals(1, rs);
		
		/*ProductCategory productCategory=new ProductCategory();
		productCategory.setCateLevel(3L);
		List<ProductCategory> list=productCategoryService.selectByWhere(new Wrapper(productCategory));
		Map<String,String> map=new HashMap<>();
		map.put("价格区间","0-100,100-500,500-1000,1000-2000,2000-5000");
		map.put("香调", "国际香调,森林香调,古龙香调,东方香调,皮革调");
		map.put("颜色", "米色,黑色,咖啡色,棕色,红色,蓝色");
		map.put("材质", "冰丝,亚麻,毛绒,天然树脂,真皮,合成树脂,纤维,竹片");
		map.put("款式", "经典,缝隙盒,通用型,高档真皮");
		map.put("商品毛重", "50.0kg,60.0kg,70.0kg,80.0kg,90.0kg");
		map.put("风格", "卡通,运动,户外,商务,其他");
		map.put("尺寸", "580*410（mm）,600*460（mm）,780*420（mm）,480*810（mm）,610*460（mm）");
		map.put("货号", "T-1550,D-1110,D-11610,Y-1710,Z-1810");
		map.put("产品编号", "Y-1670,S-1230,G-5630,H-3210,E-4310");
		for (int i = 0; i < list.size(); i++) {
			for(String key:map.keySet()){
				ProductParam entity=new ProductParam();
				entity.setCategoryId(list.get(i).getCategoryId());//Long 分类id
				entity.setParamSort(1);//Long 排序
				entity.setName(key);//String 参数名
				entity.setParamValues(map.get(key));//String 参数值文字，多个值用逗号隔开
				entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
				entity.setFormat("2");//String 1图片、2文字、3文字+图片（颜色要配图片）
				entity.setIsDisplay("1");//String 是否显示，0否1是
				entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写
				productParamService.insertSelective(entity);
			}
		}*/
    }

    /**
     * 根据主键更新记录,更新除了主键的所有字段
     *
     * @param entity
     * @return 受影响的行数
     */
    @Test
    public void test_updateById() {
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        int rs = productParamService.updateById(entity);
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
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        ProductParam condition = new ProductParam();
        condition.setId(2L);
        int rs = productParamService.updateByWhere(entity, new Wrapper(condition));
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
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        int rs = productParamService.updateByIdSelective(entity);
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
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        ProductParam condition = new ProductParam();
        condition.setId(2L);
        int rs = productParamService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = productParamService.deleteById(1L);
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
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        int rs = productParamService.deleteByWhere(new Wrapper(entity));
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
        ProductParam entity = new ProductParam();
        entity.setParamId(1L);//Long id
        entity.setCategoryId(1L);//Long 分类id
        entity.setParamSort(1);//Long 排序
        entity.setName("1");//String 参数名
        entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
        entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
        entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
        entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setIsDisplay("1");//String 是否显示，0否1是
        entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

        int rs = productParamService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        ProductParam p1 = new ProductParam();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        ProductParam p2 = new ProductParam();//p2的属性全是空值

        productParamService.selectAll(null);
        productParamService.selectAll(new Wrapper(p2));
        productParamService.countByWhere(null);
        productParamService.countByWhere(new Wrapper(p2));

        try {
            productParamService.updateByWhere(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productParamService.updateByWhereSelective(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productParamService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productParamService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productParamService.deleteByWhere(new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productParamService.deleteByWhere(null);
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
        List<ProductParam> list = new ArrayList<ProductParam>();
        for (int i = 0; i < 10; i++) {
            ProductParam entity = new ProductParam();
            entity.setParamId(1L);//Long id
            entity.setCategoryId(1L);//Long 分类id
            entity.setParamSort(1);//Long 排序
            entity.setName("1");//String 参数名
            entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
            entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
            entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
            entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setIsDisplay("1");//String 是否显示，0否1是
            entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

            list.add(entity);
        }
        boolean rs = productParamService.insertBatch(list);
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
        List<ProductParam> list = new ArrayList<ProductParam>();
        for (int i = 0; i < 10; i++) {
            ProductParam entity = new ProductParam();
            entity.setParamId(1L);//Long id
            entity.setCategoryId(1L);//Long 分类id
            entity.setParamSort(1);//Long 排序
            entity.setName("1");//String 参数名
            entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
            entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
            entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
            entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setIsDisplay("1");//String 是否显示，0否1是
            entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

            list.add(entity);
        }
        boolean rs = productParamService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<ProductParam> list = new ArrayList<ProductParam>();
        for (int i = 0; i < 10; i++) {
            ProductParam entity = new ProductParam();
            entity.setParamId(1L);//Long id
            entity.setCategoryId(1L);//Long 分类id
            entity.setParamSort(1);//Long 排序
            entity.setName("1");//String 参数名
            entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
            entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
            entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
            entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setIsDisplay("1");//String 是否显示，0否1是
            entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

            list.add(entity);
        }
        boolean rs = productParamService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<ProductParam> list = new ArrayList<ProductParam>();
        for (int i = 0; i < 10; i++) {
            ProductParam entity = new ProductParam();
            entity.setParamId(1L);//Long id
            entity.setCategoryId(1L);//Long 分类id
            entity.setParamSort(1);//Long 排序
            entity.setName("1");//String 参数名
            entity.setParamValues("1");//String 参数值文字，多个值用逗号隔开
            entity.setValuesImg("1");//String 参数值图片，多个值用逗号隔开values_img与values按下标对应
            entity.setType("1");//String 1核心参数、2辅助参数、3自定义参数。核心参数做为筛选条件。自定义参数只出现在product_parameter_mapping表中
            entity.setFormat("1");//String 1图片、2文字、3文字+图片（颜色要配图片）
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setIsDisplay("1");//String 是否显示，0否1是
            entity.setIsRequired("1");//String 是否必填，0否1是，商家发布商品的时候必填项必须填写

            list.add(entity);
        }
        boolean rs = productParamService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        ProductParam entity = new ProductParam();
        entity.setId(1L);
        productParamService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = productParamService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}