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

import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.product.service.ProductSpuService;
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
 * <p>标题: 单元测试--商品 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 赵磊
 * @version 2017-10-12
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ProductSpuServiceTest {
    @Autowired
    private ProductSpuService productSpuService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        ProductSpu productSpu = productSpuService.selectById(0L);
        Assert.assertNull(productSpu);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<ProductSpu> list = productSpuService.selectByIdIn(paramList);
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
        Page<ProductSpu> page = new Page<ProductSpu>();
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999999L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999999L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
        entity.setStoreId(999999999L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999999L);//Long 品牌
        entity.setUId(999999999L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999999);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999999);//Long 备用字段
        entity.setN2(999999999);//Long 备用字段
        entity.setN3(999999999);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        page = productSpuService.selectByWhere(page, new Wrapper(entity));
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
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999999L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999999L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
        entity.setStoreId(999999999L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999999L);//Long 品牌
        entity.setUId(999999999L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999999);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999999);//Long 备用字段
        entity.setN2(999999999);//Long 备用字段
        entity.setN3(999999999);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        List<ProductSpu> list = productSpuService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999999L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999999L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
        entity.setStoreId(999999999L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999999L);//Long 品牌
        entity.setUId(999999999L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999999);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999999);//Long 备用字段
        entity.setN2(999999999);//Long 备用字段
        entity.setN3(999999999);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        //因当表中数据量较大时，查全表很耗时，所以注释了
        //List<ProductSpu> list= productSpuService.selectAll(new Wrapper(entity));
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
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999997L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999997L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999997L);//Long 商品分类（本店）
        entity.setStoreId(999999997L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999997L);//Long 品牌
        entity.setUId(999999997L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999997);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999997L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999997);//Long 备用字段
        entity.setN2(999999997);//Long 备用字段
        entity.setN3(999999997);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        int rs = productSpuService.insert(entity);
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
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999997L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999997L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999997L);//Long 商品分类（本店）
        entity.setStoreId(999999997L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999997L);//Long 品牌
        entity.setUId(999999997L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999997);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999997L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999997);//Long 备用字段
        entity.setN2(999999997);//Long 备用字段
        entity.setN3(999999997);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        int rs = productSpuService.insertSelective(entity);
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
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999999L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999999L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
        entity.setStoreId(999999999L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999999L);//Long 品牌
        entity.setUId(999999999L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999999);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999999);//Long 备用字段
        entity.setN2(999999999);//Long 备用字段
        entity.setN3(999999999);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        int rs = productSpuService.updateById(entity);
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
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999999L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999999L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
        entity.setStoreId(999999999L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999999L);//Long 品牌
        entity.setUId(999999999L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999999);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999999);//Long 备用字段
        entity.setN2(999999999);//Long 备用字段
        entity.setN3(999999999);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        ProductSpu condition = new ProductSpu();
        condition.setId(2L);
        int rs = productSpuService.updateByWhere(entity, new Wrapper(condition));
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
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999999L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999999L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
        entity.setStoreId(999999999L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999999L);//Long 品牌
        entity.setUId(999999999L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999999);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999999);//Long 备用字段
        entity.setN2(999999999);//Long 备用字段
        entity.setN3(999999999);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        int rs = productSpuService.updateByIdSelective(entity);
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
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999999L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999999L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
        entity.setStoreId(999999999L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999999L);//Long 品牌
        entity.setUId(999999999L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999999);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999999);//Long 备用字段
        entity.setN2(999999999);//Long 备用字段
        entity.setN3(999999999);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        ProductSpu condition = new ProductSpu();
        condition.setId(2L);
        int rs = productSpuService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = productSpuService.deleteById(1L);
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
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999999L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999999L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
        entity.setStoreId(999999999L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999999L);//Long 品牌
        entity.setUId(999999999L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999999);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999999);//Long 备用字段
        entity.setN2(999999999);//Long 备用字段
        entity.setN3(999999999);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        int rs = productSpuService.deleteByWhere(new Wrapper(entity));
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
        ProductSpu entity = new ProductSpu();
        entity.setPId(999999999L);//Long id
        entity.setName("a");//String 商品名称
        entity.setCategoryId(999999999L);//Long 商品分类（平台）
        entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
        entity.setStoreId(999999999L);//Long 关联(店铺表)
        entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
        entity.setBrandId(999999999L);//Long 品牌
        entity.setUId(999999999L);//Long 关联(卖家扩展表)
        entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
        entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
        entity.setType("a");//String 1零售型、2批发型
        entity.setIsGift("a");//String 是否为赠品，0非，1是
        entity.setIsRecommend("a");//String 是否推荐，0非，1是
        entity.setRecommendSort(999999999);//Long 推荐排序
        entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
        entity.setPoint("a");//String 赠送积分
        entity.setWeight(0.1D);//Double 重量
        entity.setVolume(0.1D);//Double 体积
        entity.setBenefit("a");//String 优惠
        entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
        entity.setAction("a");//String 是否参加促销活动，0否，1是
        entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
        entity.setPurchasingAmount("a");//String 起购量
        entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
        entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
        entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
        entity.setCause("a");//String 禁售原因，审核失败原因
        entity.setShelfTime(new Date());//java.util.Date 上架时间
        entity.setCreateDate(new Date());//java.util.Date 创建日期
        entity.setUpdateDate(new Date());//java.util.Date 修改日期
        entity.setBak1("a");//String 备用字段
        entity.setBak2("a");//String 备用字段
        entity.setBak3("a");//String 备用字段
        entity.setBak4("a");//String 备用字段
        entity.setBak5("a");//String 备用字段
        entity.setBak6("a");//String 备用字段
        entity.setBak7("a");//String 备用字段
        entity.setBak8("a");//String 备用字段
        entity.setBak9("a");//String 备用字段
        entity.setBak10("a");//String 备用字段
        entity.setBak11("a");//String 备用字段
        entity.setBak12("a");//String 备用字段
        entity.setBak13("a");//String 备用字段
        entity.setBak14("a");//String 备用字段
        entity.setBak15("a");//String 备用字段
        entity.setN1(999999999);//Long 备用字段
        entity.setN2(999999999);//Long 备用字段
        entity.setN3(999999999);//Long 备用字段
        entity.setF1(0.1D);//Double 备用字段
        entity.setF2(0.1D);//Double 备用字段
        entity.setF3(0.1D);//Double 备用字段
        entity.setD1(new Date());//java.util.Date 备用字段
        entity.setD2(new Date());//java.util.Date 备用字段
        entity.setD3(new Date());//java.util.Date 备用字段
        entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
        int rs = productSpuService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        ProductSpu p1 = new ProductSpu();
        p1.setCreateDate(new Date());
        //p1.setBak1("bak1");

        ProductSpu p2 = new ProductSpu();//p2的属性全是空值

        //productSpuService.selectAll(null);
        //productSpuService.selectAll(new Wrapper(p2));
        productSpuService.countByWhere(null);
        productSpuService.countByWhere(new Wrapper(p2));

        try {
            productSpuService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            productSpuService.deleteByWhere(null);
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
        List<ProductSpu> list = new ArrayList<ProductSpu>();
        for (int i = 0; i < 1; i++) {
            ProductSpu entity = new ProductSpu();
            entity.setPId(999999999L);//Long id
            entity.setName("a");//String 商品名称
            entity.setCategoryId(999999999L);//Long 商品分类（平台）
            entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
            entity.setStoreId(999999999L);//Long 关联(店铺表)
            entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
            entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
            entity.setBrandId(999999999L);//Long 品牌
            entity.setUId(999999999L);//Long 关联(卖家扩展表)
            entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
            entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
            entity.setType("a");//String 1零售型、2批发型
            entity.setIsGift("a");//String 是否为赠品，0非，1是
            entity.setIsRecommend("a");//String 是否推荐，0非，1是
            entity.setRecommendSort(999999999);//Long 推荐排序
            entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
            entity.setPoint("a");//String 赠送积分
            entity.setWeight(0.1D);//Double 重量
            entity.setVolume(0.1D);//Double 体积
            entity.setBenefit("a");//String 优惠
            entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
            entity.setAction("a");//String 是否参加促销活动，0否，1是
            entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
            entity.setPurchasingAmount("a");//String 起购量
            entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
            entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
            entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
            entity.setCause("a");//String 禁售原因，审核失败原因
            entity.setShelfTime(new Date());//java.util.Date 上架时间
            entity.setCreateDate(new Date());//java.util.Date 创建日期
            entity.setUpdateDate(new Date());//java.util.Date 修改日期
            entity.setBak1("a");//String 备用字段
            entity.setBak2("a");//String 备用字段
            entity.setBak3("a");//String 备用字段
            entity.setBak4("a");//String 备用字段
            entity.setBak5("a");//String 备用字段
            entity.setBak6("a");//String 备用字段
            entity.setBak7("a");//String 备用字段
            entity.setBak8("a");//String 备用字段
            entity.setBak9("a");//String 备用字段
            entity.setBak10("a");//String 备用字段
            entity.setBak11("a");//String 备用字段
            entity.setBak12("a");//String 备用字段
            entity.setBak13("a");//String 备用字段
            entity.setBak14("a");//String 备用字段
            entity.setBak15("a");//String 备用字段
            entity.setN1(999999999);//Long 备用字段
            entity.setN2(999999999);//Long 备用字段
            entity.setN3(999999999);//Long 备用字段
            entity.setF1(0.1D);//Double 备用字段
            entity.setF2(0.1D);//Double 备用字段
            entity.setF3(0.1D);//Double 备用字段
            entity.setD1(new Date());//java.util.Date 备用字段
            entity.setD2(new Date());//java.util.Date 备用字段
            entity.setD3(new Date());//java.util.Date 备用字段
            entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
            list.add(entity);
        }
        boolean rs = productSpuService.insertBatch(list);
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
        List<ProductSpu> list = new ArrayList<ProductSpu>();
        for (int i = 0; i < 1; i++) {
            ProductSpu entity = new ProductSpu();
            entity.setPId(999999996L);//Long id
            entity.setName("a");//String 商品名称
            entity.setCategoryId(999999996L);//Long 商品分类（平台）
            entity.setStoreCategoryId(999999996L);//Long 商品分类（本店）
            entity.setStoreId(999999996L);//Long 关联(店铺表)
            entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
            entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
            entity.setBrandId(999999996L);//Long 品牌
            entity.setUId(999999996L);//Long 关联(卖家扩展表)
            entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
            entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
            entity.setType("a");//String 1零售型、2批发型
            entity.setIsGift("a");//String 是否为赠品，0非，1是
            entity.setIsRecommend("a");//String 是否推荐，0非，1是
            entity.setRecommendSort(999999996);//Long 推荐排序
            entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
            entity.setPoint("a");//String 赠送积分
            entity.setWeight(0.1D);//Double 重量
            entity.setVolume(0.1D);//Double 体积
            entity.setBenefit("a");//String 优惠
            entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
            entity.setAction("a");//String 是否参加促销活动，0否，1是
            entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
            entity.setPurchasingAmount("a");//String 起购量
            entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
            entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
            entity.setLtId(999999996L);//Long 运费模板的ID,express_type=1时有值
            entity.setCause("a");//String 禁售原因，审核失败原因
            entity.setShelfTime(new Date());//java.util.Date 上架时间
            entity.setCreateDate(new Date());//java.util.Date 创建日期
            entity.setUpdateDate(new Date());//java.util.Date 修改日期
            entity.setBak1("a");//String 备用字段
            entity.setBak2("a");//String 备用字段
            entity.setBak3("a");//String 备用字段
            entity.setBak4("a");//String 备用字段
            entity.setBak5("a");//String 备用字段
            entity.setBak6("a");//String 备用字段
            entity.setBak7("a");//String 备用字段
            entity.setBak8("a");//String 备用字段
            entity.setBak9("a");//String 备用字段
            entity.setBak10("a");//String 备用字段
            entity.setBak11("a");//String 备用字段
            entity.setBak12("a");//String 备用字段
            entity.setBak13("a");//String 备用字段
            entity.setBak14("a");//String 备用字段
            entity.setBak15("a");//String 备用字段
            entity.setN1(999999996);//Long 备用字段
            entity.setN2(999999996);//Long 备用字段
            entity.setN3(999999996);//Long 备用字段
            entity.setF1(0.1D);//Double 备用字段
            entity.setF2(0.1D);//Double 备用字段
            entity.setF3(0.1D);//Double 备用字段
            entity.setD1(new Date());//java.util.Date 备用字段
            entity.setD2(new Date());//java.util.Date 备用字段
            entity.setD3(new Date());//java.util.Date 备用字段
            entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
            list.add(entity);
        }
        boolean rs = productSpuService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<ProductSpu> list = new ArrayList<ProductSpu>();
        for (int i = 0; i < 1; i++) {
            ProductSpu entity = new ProductSpu();
            entity.setPId(999999999L);//Long id
            entity.setName("a");//String 商品名称
            entity.setCategoryId(999999999L);//Long 商品分类（平台）
            entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
            entity.setStoreId(999999999L);//Long 关联(店铺表)
            entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
            entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
            entity.setBrandId(999999999L);//Long 品牌
            entity.setUId(999999999L);//Long 关联(卖家扩展表)
            entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
            entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
            entity.setType("a");//String 1零售型、2批发型
            entity.setIsGift("a");//String 是否为赠品，0非，1是
            entity.setIsRecommend("a");//String 是否推荐，0非，1是
            entity.setRecommendSort(999999999);//Long 推荐排序
            entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
            entity.setPoint("a");//String 赠送积分
            entity.setWeight(0.1D);//Double 重量
            entity.setVolume(0.1D);//Double 体积
            entity.setBenefit("a");//String 优惠
            entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
            entity.setAction("a");//String 是否参加促销活动，0否，1是
            entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
            entity.setPurchasingAmount("a");//String 起购量
            entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
            entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
            entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
            entity.setCause("a");//String 禁售原因，审核失败原因
            entity.setShelfTime(new Date());//java.util.Date 上架时间
            entity.setCreateDate(new Date());//java.util.Date 创建日期
            entity.setUpdateDate(new Date());//java.util.Date 修改日期
            entity.setBak1("a");//String 备用字段
            entity.setBak2("a");//String 备用字段
            entity.setBak3("a");//String 备用字段
            entity.setBak4("a");//String 备用字段
            entity.setBak5("a");//String 备用字段
            entity.setBak6("a");//String 备用字段
            entity.setBak7("a");//String 备用字段
            entity.setBak8("a");//String 备用字段
            entity.setBak9("a");//String 备用字段
            entity.setBak10("a");//String 备用字段
            entity.setBak11("a");//String 备用字段
            entity.setBak12("a");//String 备用字段
            entity.setBak13("a");//String 备用字段
            entity.setBak14("a");//String 备用字段
            entity.setBak15("a");//String 备用字段
            entity.setN1(999999999);//Long 备用字段
            entity.setN2(999999999);//Long 备用字段
            entity.setN3(999999999);//Long 备用字段
            entity.setF1(0.1D);//Double 备用字段
            entity.setF2(0.1D);//Double 备用字段
            entity.setF3(0.1D);//Double 备用字段
            entity.setD1(new Date());//java.util.Date 备用字段
            entity.setD2(new Date());//java.util.Date 备用字段
            entity.setD3(new Date());//java.util.Date 备用字段
            entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
            list.add(entity);
        }
        boolean rs = productSpuService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<ProductSpu> list = new ArrayList<ProductSpu>();
        for (int i = 0; i < 1; i++) {
            ProductSpu entity = new ProductSpu();
            entity.setPId(999999999L);//Long id
            entity.setName("a");//String 商品名称
            entity.setCategoryId(999999999L);//Long 商品分类（平台）
            entity.setStoreCategoryId(999999999L);//Long 商品分类（本店）
            entity.setStoreId(999999999L);//Long 关联(店铺表)
            entity.setStatus("a");//String 10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
            entity.setImage("a");//String 封面图path，冗余，用于搜索列表页
            entity.setBrandId(999999999L);//Long 品牌
            entity.setUId(999999999L);//Long 关联(卖家扩展表)
            entity.setNameSub("a");//String 副标题、商品卖点,显示名称下面的小红字
            entity.setUnit("a");//String 计量单位，从计量单位表取中文值冗余在这里
            entity.setType("a");//String 1零售型、2批发型
            entity.setIsGift("a");//String 是否为赠品，0非，1是
            entity.setIsRecommend("a");//String 是否推荐，0非，1是
            entity.setRecommendSort(999999999);//Long 推荐排序
            entity.setMarketPrice(new BigDecimal("2"));//java.math.BigDecimal 市场价（只用于显示）
            entity.setPoint("a");//String 赠送积分
            entity.setWeight(0.1D);//Double 重量
            entity.setVolume(0.1D);//Double 体积
            entity.setBenefit("a");//String 优惠
            entity.setInvoice("a");//String 发票，0不可开发票，1可开发票
            entity.setAction("a");//String 是否参加促销活动，0否，1是
            entity.setDeliverGoodsDate("a");//String 发货日期，1天内，2天内，3天内
            entity.setPurchasingAmount("a");//String 起购量
            entity.setExpressType("a");//String 运费方式，0固定运费，1使用运费模板
            entity.setExpressPrice(new BigDecimal("2"));//java.math.BigDecimal 运费价格,express_type=0时有值
            entity.setLtId(999999999L);//Long 运费模板的ID,express_type=1时有值
            entity.setCause("a");//String 禁售原因，审核失败原因
            entity.setShelfTime(new Date());//java.util.Date 上架时间
            entity.setCreateDate(new Date());//java.util.Date 创建日期
            entity.setUpdateDate(new Date());//java.util.Date 修改日期
            entity.setBak1("a");//String 备用字段
            entity.setBak2("a");//String 备用字段
            entity.setBak3("a");//String 备用字段
            entity.setBak4("a");//String 备用字段
            entity.setBak5("a");//String 备用字段
            entity.setBak6("a");//String 备用字段
            entity.setBak7("a");//String 备用字段
            entity.setBak8("a");//String 备用字段
            entity.setBak9("a");//String 备用字段
            entity.setBak10("a");//String 备用字段
            entity.setBak11("a");//String 备用字段
            entity.setBak12("a");//String 备用字段
            entity.setBak13("a");//String 备用字段
            entity.setBak14("a");//String 备用字段
            entity.setBak15("a");//String 备用字段
            entity.setN1(999999999);//Long 备用字段
            entity.setN2(999999999);//Long 备用字段
            entity.setN3(999999999);//Long 备用字段
            entity.setF1(0.1D);//Double 备用字段
            entity.setF2(0.1D);//Double 备用字段
            entity.setF3(0.1D);//Double 备用字段
            entity.setD1(new Date());//java.util.Date 备用字段
            entity.setD2(new Date());//java.util.Date 备用字段
            entity.setD3(new Date());//java.util.Date 备用字段
            entity.setPublish("a");//String 发布，0放入仓库中，1立即发布
            list.add(entity);
        }
        boolean rs = productSpuService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        ProductSpu entity = new ProductSpu();
        entity.setId(1L);
        productSpuService.selectOne(new Wrapper(entity));
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
        int rs = productSpuService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}