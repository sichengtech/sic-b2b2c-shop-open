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

import com.sicheng.admin.product.entity.SolrProduct;
import com.sicheng.admin.product.service.SolrProductService;
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
 * <p>标题: 单元测试--产品搜索 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fxx
 * @version 2018-01-24
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class SolrProductServiceTest {
    @Autowired
    private SolrProductService solrProductService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        SolrProduct solrProduct = solrProductService.selectById(0L);
        Assert.assertNull(solrProduct);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<SolrProduct> list = solrProductService.selectByIdIn(paramList);
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
        Page<SolrProduct> page = new Page<SolrProduct>();
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999999L);//Long 省id
        entity.setCityId(999999999L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999999L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999999L);//Long 总销量
        entity.setWeekSales(999999999L);//Long 周销量
        entity.setMonthSales(999999999L);//Long 月销量
        entity.setMonth3Sales(999999999L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999999L);//Long 收藏量
        entity.setCommentCount(999999999L);//Long 评论量
        entity.setPId(999999999L);//Long 商品id
        entity.setUId(999999999L);//Long 用户id
        entity.setStoreId(999999999L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999999L);//Long 分类id
        entity.setStoreCategoryId(999999999L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999999L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999999L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        page = solrProductService.selectByWhere(page, new Wrapper(entity));
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
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999999L);//Long 省id
        entity.setCityId(999999999L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999999L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999999L);//Long 总销量
        entity.setWeekSales(999999999L);//Long 周销量
        entity.setMonthSales(999999999L);//Long 月销量
        entity.setMonth3Sales(999999999L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999999L);//Long 收藏量
        entity.setCommentCount(999999999L);//Long 评论量
        entity.setPId(999999999L);//Long 商品id
        entity.setUId(999999999L);//Long 用户id
        entity.setStoreId(999999999L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999999L);//Long 分类id
        entity.setStoreCategoryId(999999999L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999999L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999999L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        List<SolrProduct> list = solrProductService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999999L);//Long 省id
        entity.setCityId(999999999L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999999L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999999L);//Long 总销量
        entity.setWeekSales(999999999L);//Long 周销量
        entity.setMonthSales(999999999L);//Long 月销量
        entity.setMonth3Sales(999999999L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999999L);//Long 收藏量
        entity.setCommentCount(999999999L);//Long 评论量
        entity.setPId(999999999L);//Long 商品id
        entity.setUId(999999999L);//Long 用户id
        entity.setStoreId(999999999L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999999L);//Long 分类id
        entity.setStoreCategoryId(999999999L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999999L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999999L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        //因当表中数据量较大时，查全表很耗时，所以注释了
        //List<SolrProduct> list= solrProductService.selectAll(new Wrapper(entity));
        //Assert.assertNotNull(list);
    }

    /**
     * 插入数据
     * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
     *
     * @param entity
     * @return 受影响的行数
     */
    //@Test
    public void test_insert() {
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999998L);//Long 省id
        entity.setCityId(999999998L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999998L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999998L);//Long 总销量
        entity.setWeekSales(999999998L);//Long 周销量
        entity.setMonthSales(999999998L);//Long 月销量
        entity.setMonth3Sales(999999998L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999998L);//Long 收藏量
        entity.setCommentCount(999999998L);//Long 评论量
        entity.setPId(999999998L);//Long 商品id
        entity.setUId(999999998L);//Long 用户id
        entity.setStoreId(999999998L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999998L);//Long 分类id
        entity.setStoreCategoryId(999999998L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999998L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999998L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        int rs = solrProductService.insert(entity);
        Assert.assertEquals(1, rs);
    }

    /**
     * 插入,只把非空的值插入到对应的字段
     * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
     *
     * @param entity
     * @return 受影响的行数
     */
    //@Test
    public void test_insertSelective() {
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999997L);//Long 省id
        entity.setCityId(999999997L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999997L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999997L);//Long 总销量
        entity.setWeekSales(999999997L);//Long 周销量
        entity.setMonthSales(999999997L);//Long 月销量
        entity.setMonth3Sales(999999997L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999997L);//Long 收藏量
        entity.setCommentCount(999999997L);//Long 评论量
        entity.setPId(999999997L);//Long 商品id
        entity.setUId(999999997L);//Long 用户id
        entity.setStoreId(999999997L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999997L);//Long 分类id
        entity.setStoreCategoryId(999999997L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999997L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999997L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        int rs = solrProductService.insertSelective(entity);
        Assert.assertEquals(1, rs);
    }

    /**
     * 根据主键更新记录,更新除了主键的所有字段
     *
     * @param entity
     * @return 受影响的行数
     */
    //@Test
    public void test_updateById() {
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999999L);//Long 省id
        entity.setCityId(999999999L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999999L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999999L);//Long 总销量
        entity.setWeekSales(999999999L);//Long 周销量
        entity.setMonthSales(999999999L);//Long 月销量
        entity.setMonth3Sales(999999999L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999999L);//Long 收藏量
        entity.setCommentCount(999999999L);//Long 评论量
        entity.setPId(999999999L);//Long 商品id
        entity.setUId(999999999L);//Long 用户id
        entity.setStoreId(999999999L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999999L);//Long 分类id
        entity.setStoreCategoryId(999999999L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999999L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999999L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        int rs = solrProductService.updateById(entity);
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
    //@Test
    public void test_updateByWhere() {
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999999L);//Long 省id
        entity.setCityId(999999999L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999999L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999999L);//Long 总销量
        entity.setWeekSales(999999999L);//Long 周销量
        entity.setMonthSales(999999999L);//Long 月销量
        entity.setMonth3Sales(999999999L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999999L);//Long 收藏量
        entity.setCommentCount(999999999L);//Long 评论量
        entity.setPId(999999999L);//Long 商品id
        entity.setUId(999999999L);//Long 用户id
        entity.setStoreId(999999999L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999999L);//Long 分类id
        entity.setStoreCategoryId(999999999L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999999L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999999L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        SolrProduct condition = new SolrProduct();
        condition.setId(2L);
        int rs = solrProductService.updateByWhere(entity, new Wrapper(condition));
        Assert.assertNotNull(rs);
    }

    /**
     * 根据主键更新记录,只把非空的值更到对应的字段
     *
     * @param entity 数据实体，用于存储数据，这些数据将被update到表中
     * @return 受影响的行数
     */
    //@Test
    public void test_updateByIdSelective() {
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999999L);//Long 省id
        entity.setCityId(999999999L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999999L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999999L);//Long 总销量
        entity.setWeekSales(999999999L);//Long 周销量
        entity.setMonthSales(999999999L);//Long 月销量
        entity.setMonth3Sales(999999999L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999999L);//Long 收藏量
        entity.setCommentCount(999999999L);//Long 评论量
        entity.setPId(999999999L);//Long 商品id
        entity.setUId(999999999L);//Long 用户id
        entity.setStoreId(999999999L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999999L);//Long 分类id
        entity.setStoreCategoryId(999999999L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999999L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999999L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        int rs = solrProductService.updateByIdSelective(entity);
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
    //@Test
    public void test_updateByWhereSelective() {
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999999L);//Long 省id
        entity.setCityId(999999999L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999999L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999999L);//Long 总销量
        entity.setWeekSales(999999999L);//Long 周销量
        entity.setMonthSales(999999999L);//Long 月销量
        entity.setMonth3Sales(999999999L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999999L);//Long 收藏量
        entity.setCommentCount(999999999L);//Long 评论量
        entity.setPId(999999999L);//Long 商品id
        entity.setUId(999999999L);//Long 用户id
        entity.setStoreId(999999999L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999999L);//Long 分类id
        entity.setStoreCategoryId(999999999L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999999L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999999L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        SolrProduct condition = new SolrProduct();
        condition.setId(2L);
        int rs = solrProductService.updateByWhereSelective(entity, new Wrapper(condition));
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
    //@Test
    public void test_deleteById() {
        int rs = solrProductService.deleteById(1L);
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
    //@Test
    public void test_deleteByWhere() {
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999999L);//Long 省id
        entity.setCityId(999999999L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999999L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999999L);//Long 总销量
        entity.setWeekSales(999999999L);//Long 周销量
        entity.setMonthSales(999999999L);//Long 月销量
        entity.setMonth3Sales(999999999L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999999L);//Long 收藏量
        entity.setCommentCount(999999999L);//Long 评论量
        entity.setPId(999999999L);//Long 商品id
        entity.setUId(999999999L);//Long 用户id
        entity.setStoreId(999999999L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999999L);//Long 分类id
        entity.setStoreCategoryId(999999999L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999999L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999999L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        int rs = solrProductService.deleteByWhere(new Wrapper(entity));
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
        SolrProduct entity = new SolrProduct();
        entity.setUserName("a");//String 卖家登录名
        entity.setStoreName("a");//String 店铺名称
        entity.setProvinceId(999999999L);//Long 省id
        entity.setCityId(999999999L);//Long 市id
        entity.setProvinceName("a");//String 省名称
        entity.setCityName("a");//String 市名称
        entity.setCategoryName("a");//String 分类名称
        entity.setCategoryLevel(999999999L);//Long 分类等级
        entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
        entity.setCateFirstLetter("a");//String 分类首字母
        entity.setCateParentIds("a");//String 分类父ids
        entity.setStoreCateName("a");//String 店铺分类名
        entity.setIsOpen("a");//String 是否开店
        entity.setStoreCateParentIds("a");//String 店铺分类父ids
        entity.setBrandName("a");//String 品牌名
        entity.setBrandFirstLeftter("a");//String 品牌首字母
        entity.setBrandEnglishName("a");//String 品牌英文名
        entity.setAllSales(999999999L);//Long 总销量
        entity.setWeekSales(999999999L);//Long 周销量
        entity.setMonthSales(999999999L);//Long 月销量
        entity.setMonth3Sales(999999999L);//Long 三个月销量
        entity.setCarIds("a");//String 车型ids
        entity.setParamValue("a");//String 参数值
        entity.setCollectionCount(999999999L);//Long 收藏量
        entity.setCommentCount(999999999L);//Long 评论量
        entity.setPId(999999999L);//Long 商品id
        entity.setUId(999999999L);//Long 用户id
        entity.setStoreId(999999999L);//Long 店铺id
        entity.setName("a");//String 商品名称
        entity.setStatus("a");//String 状态
        entity.setCategoryId(999999999L);//Long 分类id
        entity.setStoreCategoryId(999999999L);//Long 店内分类
        entity.setImage("a");//String 封面图
        entity.setBrandId(999999999L);//Long 品牌id
        entity.setNameSub("a");//String 副标题
        entity.setUnit("a");//String 单位
        entity.setType("a");//String 类型(1零售、2批发、3混合)
        entity.setIsGift("a");//String 是否是礼品
        entity.setIsRecommend("a");//String 是否推荐
        entity.setBenefit("a");//String 优惠
        entity.setRecommendSort(999999999L);//Long 活动积分
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
        entity.setPoint("a");//String 赠送积分
        entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
        entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
        entity.setAction("a");//String 是否参加活动
        entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
        entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
        entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
        entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setPurchasingAmount("a");//String 起购量
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        int rs = solrProductService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        SolrProduct p1 = new SolrProduct();
        p1.setCreateDate(new Date());
        //p1.setBak1("bak1");

        SolrProduct p2 = new SolrProduct();//p2的属性全是空值

        //solrProductService.selectAll(null);
        //solrProductService.selectAll(new Wrapper(p2));
        solrProductService.countByWhere(null);
        solrProductService.countByWhere(new Wrapper(p2));

        try {
            solrProductService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            solrProductService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            solrProductService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            solrProductService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            solrProductService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            solrProductService.deleteByWhere(null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }
    }

    /**
     * 批量插入
     */
    //@Test
    public void test_insertBatch() {
        List<SolrProduct> list = new ArrayList<SolrProduct>();
        for (int i = 0; i < 1; i++) {
            SolrProduct entity = new SolrProduct();
            entity.setUserName("a");//String 卖家登录名
            entity.setStoreName("a");//String 店铺名称
            entity.setProvinceId(999999999L);//Long 省id
            entity.setCityId(999999999L);//Long 市id
            entity.setProvinceName("a");//String 省名称
            entity.setCityName("a");//String 市名称
            entity.setCategoryName("a");//String 分类名称
            entity.setCategoryLevel(999999999L);//Long 分类等级
            entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
            entity.setCateFirstLetter("a");//String 分类首字母
            entity.setCateParentIds("a");//String 分类父ids
            entity.setStoreCateName("a");//String 店铺分类名
            entity.setIsOpen("a");//String 是否开店
            entity.setStoreCateParentIds("a");//String 店铺分类父ids
            entity.setBrandName("a");//String 品牌名
            entity.setBrandFirstLeftter("a");//String 品牌首字母
            entity.setBrandEnglishName("a");//String 品牌英文名
            entity.setAllSales(999999999L);//Long 总销量
            entity.setWeekSales(999999999L);//Long 周销量
            entity.setMonthSales(999999999L);//Long 月销量
            entity.setMonth3Sales(999999999L);//Long 三个月销量
            entity.setCarIds("a");//String 车型ids
            entity.setParamValue("a");//String 参数值
            entity.setCollectionCount(999999999L);//Long 收藏量
            entity.setCommentCount(999999999L);//Long 评论量
            entity.setPId(999999999L);//Long 商品id
            entity.setUId(999999999L);//Long 用户id
            entity.setStoreId(999999999L);//Long 店铺id
            entity.setName("a");//String 商品名称
            entity.setStatus("a");//String 状态
            entity.setCategoryId(999999999L);//Long 分类id
            entity.setStoreCategoryId(999999999L);//Long 店内分类
            entity.setImage("a");//String 封面图
            entity.setBrandId(999999999L);//Long 品牌id
            entity.setNameSub("a");//String 副标题
            entity.setUnit("a");//String 单位
            entity.setType("a");//String 类型(1零售、2批发、3混合)
            entity.setIsGift("a");//String 是否是礼品
            entity.setIsRecommend("a");//String 是否推荐
            entity.setBenefit("a");//String 优惠
            entity.setRecommendSort(999999999L);//Long 活动积分
            entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
            entity.setPoint("a");//String 赠送积分
            entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
            entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
            entity.setAction("a");//String 是否参加活动
            entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
            entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
            entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
            entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
            entity.setShelfTime(new Date());//java.util.Date 上架时间
            entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setPurchasingAmount("a");//String 起购量
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            list.add(entity);
        }
        boolean rs = solrProductService.insertBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    //@Test
    public void test_insertSelectiveBatch() {
        List<SolrProduct> list = new ArrayList<SolrProduct>();
        for (int i = 0; i < 1; i++) {
            SolrProduct entity = new SolrProduct();
            entity.setUserName("a");//String 卖家登录名
            entity.setStoreName("a");//String 店铺名称
            entity.setProvinceId(999999996L);//Long 省id
            entity.setCityId(999999996L);//Long 市id
            entity.setProvinceName("a");//String 省名称
            entity.setCityName("a");//String 市名称
            entity.setCategoryName("a");//String 分类名称
            entity.setCategoryLevel(999999996L);//Long 分类等级
            entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
            entity.setCateFirstLetter("a");//String 分类首字母
            entity.setCateParentIds("a");//String 分类父ids
            entity.setStoreCateName("a");//String 店铺分类名
            entity.setIsOpen("a");//String 是否开店
            entity.setStoreCateParentIds("a");//String 店铺分类父ids
            entity.setBrandName("a");//String 品牌名
            entity.setBrandFirstLeftter("a");//String 品牌首字母
            entity.setBrandEnglishName("a");//String 品牌英文名
            entity.setAllSales(999999996L);//Long 总销量
            entity.setWeekSales(999999996L);//Long 周销量
            entity.setMonthSales(999999996L);//Long 月销量
            entity.setMonth3Sales(999999996L);//Long 三个月销量
            entity.setCarIds("a");//String 车型ids
            entity.setParamValue("a");//String 参数值
            entity.setCollectionCount(999999996L);//Long 收藏量
            entity.setCommentCount(999999996L);//Long 评论量
            entity.setPId(999999996L);//Long 商品id
            entity.setUId(999999996L);//Long 用户id
            entity.setStoreId(999999996L);//Long 店铺id
            entity.setName("a");//String 商品名称
            entity.setStatus("a");//String 状态
            entity.setCategoryId(999999996L);//Long 分类id
            entity.setStoreCategoryId(999999996L);//Long 店内分类
            entity.setImage("a");//String 封面图
            entity.setBrandId(999999996L);//Long 品牌id
            entity.setNameSub("a");//String 副标题
            entity.setUnit("a");//String 单位
            entity.setType("a");//String 类型(1零售、2批发、3混合)
            entity.setIsGift("a");//String 是否是礼品
            entity.setIsRecommend("a");//String 是否推荐
            entity.setBenefit("a");//String 优惠
            entity.setRecommendSort(999999996L);//Long 活动积分
            entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
            entity.setPoint("a");//String 赠送积分
            entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
            entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
            entity.setAction("a");//String 是否参加活动
            entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
            entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
            entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
            entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
            entity.setShelfTime(new Date());//java.util.Date 上架时间
            entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setPurchasingAmount("a");//String 起购量
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            list.add(entity);
        }
        boolean rs = solrProductService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    //@Test
    public void test_updateBatch() {
        List<SolrProduct> list = new ArrayList<SolrProduct>();
        for (int i = 0; i < 1; i++) {
            SolrProduct entity = new SolrProduct();
            entity.setUserName("a");//String 卖家登录名
            entity.setStoreName("a");//String 店铺名称
            entity.setProvinceId(999999999L);//Long 省id
            entity.setCityId(999999999L);//Long 市id
            entity.setProvinceName("a");//String 省名称
            entity.setCityName("a");//String 市名称
            entity.setCategoryName("a");//String 分类名称
            entity.setCategoryLevel(999999999L);//Long 分类等级
            entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
            entity.setCateFirstLetter("a");//String 分类首字母
            entity.setCateParentIds("a");//String 分类父ids
            entity.setStoreCateName("a");//String 店铺分类名
            entity.setIsOpen("a");//String 是否开店
            entity.setStoreCateParentIds("a");//String 店铺分类父ids
            entity.setBrandName("a");//String 品牌名
            entity.setBrandFirstLeftter("a");//String 品牌首字母
            entity.setBrandEnglishName("a");//String 品牌英文名
            entity.setAllSales(999999999L);//Long 总销量
            entity.setWeekSales(999999999L);//Long 周销量
            entity.setMonthSales(999999999L);//Long 月销量
            entity.setMonth3Sales(999999999L);//Long 三个月销量
            entity.setCarIds("a");//String 车型ids
            entity.setParamValue("a");//String 参数值
            entity.setCollectionCount(999999999L);//Long 收藏量
            entity.setCommentCount(999999999L);//Long 评论量
            entity.setPId(999999999L);//Long 商品id
            entity.setUId(999999999L);//Long 用户id
            entity.setStoreId(999999999L);//Long 店铺id
            entity.setName("a");//String 商品名称
            entity.setStatus("a");//String 状态
            entity.setCategoryId(999999999L);//Long 分类id
            entity.setStoreCategoryId(999999999L);//Long 店内分类
            entity.setImage("a");//String 封面图
            entity.setBrandId(999999999L);//Long 品牌id
            entity.setNameSub("a");//String 副标题
            entity.setUnit("a");//String 单位
            entity.setType("a");//String 类型(1零售、2批发、3混合)
            entity.setIsGift("a");//String 是否是礼品
            entity.setIsRecommend("a");//String 是否推荐
            entity.setBenefit("a");//String 优惠
            entity.setRecommendSort(999999999L);//Long 活动积分
            entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
            entity.setPoint("a");//String 赠送积分
            entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
            entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
            entity.setAction("a");//String 是否参加活动
            entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
            entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
            entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
            entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
            entity.setShelfTime(new Date());//java.util.Date 上架时间
            entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setPurchasingAmount("a");//String 起购量
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            list.add(entity);
        }
        boolean rs = solrProductService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    //@Test
    public void test_updateSelectiveBatch() {
        List<SolrProduct> list = new ArrayList<SolrProduct>();
        for (int i = 0; i < 1; i++) {
            SolrProduct entity = new SolrProduct();
            entity.setUserName("a");//String 卖家登录名
            entity.setStoreName("a");//String 店铺名称
            entity.setProvinceId(999999999L);//Long 省id
            entity.setCityId(999999999L);//Long 市id
            entity.setProvinceName("a");//String 省名称
            entity.setCityName("a");//String 市名称
            entity.setCategoryName("a");//String 分类名称
            entity.setCategoryLevel(999999999L);//Long 分类等级
            entity.setStoreType("a");//String 店铺类型字典（10普通店铺，20旗舰店）
            entity.setCateFirstLetter("a");//String 分类首字母
            entity.setCateParentIds("a");//String 分类父ids
            entity.setStoreCateName("a");//String 店铺分类名
            entity.setIsOpen("a");//String 是否开店
            entity.setStoreCateParentIds("a");//String 店铺分类父ids
            entity.setBrandName("a");//String 品牌名
            entity.setBrandFirstLeftter("a");//String 品牌首字母
            entity.setBrandEnglishName("a");//String 品牌英文名
            entity.setAllSales(999999999L);//Long 总销量
            entity.setWeekSales(999999999L);//Long 周销量
            entity.setMonthSales(999999999L);//Long 月销量
            entity.setMonth3Sales(999999999L);//Long 三个月销量
            entity.setCarIds("a");//String 车型ids
            entity.setParamValue("a");//String 参数值
            entity.setCollectionCount(999999999L);//Long 收藏量
            entity.setCommentCount(999999999L);//Long 评论量
            entity.setPId(999999999L);//Long 商品id
            entity.setUId(999999999L);//Long 用户id
            entity.setStoreId(999999999L);//Long 店铺id
            entity.setName("a");//String 商品名称
            entity.setStatus("a");//String 状态
            entity.setCategoryId(999999999L);//Long 分类id
            entity.setStoreCategoryId(999999999L);//Long 店内分类
            entity.setImage("a");//String 封面图
            entity.setBrandId(999999999L);//Long 品牌id
            entity.setNameSub("a");//String 副标题
            entity.setUnit("a");//String 单位
            entity.setType("a");//String 类型(1零售、2批发、3混合)
            entity.setIsGift("a");//String 是否是礼品
            entity.setIsRecommend("a");//String 是否推荐
            entity.setBenefit("a");//String 优惠
            entity.setRecommendSort(999999999L);//Long 活动积分
            entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价
            entity.setPoint("a");//String 赠送积分
            entity.setMaxPrice(new BigDecimal("2"));//java.math.BigDecimal 最高价
            entity.setMinPrice(new BigDecimal("2"));//java.math.BigDecimal 最低价
            entity.setAction("a");//String 是否参加活动
            entity.setMaxPrice1(new BigDecimal("2"));//java.math.BigDecimal 最高零售价
            entity.setMinPrice1(new BigDecimal("2"));//java.math.BigDecimal 最低零售价
            entity.setMaxPrice2(new BigDecimal("2"));//java.math.BigDecimal 最高批发价
            entity.setMinPrice2(new BigDecimal("2"));//java.math.BigDecimal 最低批发价
            entity.setShelfTime(new Date());//java.util.Date 上架时间
            entity.setDeliverGoodsDate("a");//String 发货日期，1 一天内，2两天内，3三天内，4四天内，5五天内，6六天内，7七天内，8八天内，9九天内，10天内，11面议
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setPurchasingAmount("a");//String 起购量
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            list.add(entity);
        }
        boolean rs = solrProductService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        SolrProduct entity = new SolrProduct();
        entity.setId(1L);
        solrProductService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    //@Test
    public void test_deleteByIdIn() {
        List<Object> list = new ArrayList<Object>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        int rs = solrProductService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}