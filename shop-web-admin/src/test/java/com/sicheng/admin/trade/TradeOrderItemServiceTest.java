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
package com.sicheng.admin.trade;

import com.sicheng.admin.trade.entity.TradeOrderItem;
import com.sicheng.admin.trade.service.TradeOrderItemService;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: 单元测试--订单详情 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 范秀秀
 * @version 2017-05-12
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class TradeOrderItemServiceTest {
    @Autowired
    private TradeOrderItemService tradeOrderItemService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        TradeOrderItem tradeOrderItem = tradeOrderItemService.selectById(0L);
        Assert.assertNull(tradeOrderItem);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add(new BigDecimal("2"));
        List<TradeOrderItem> list = tradeOrderItemService.selectByIdIn(paramList);
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
        Page<TradeOrderItem> page = new Page<TradeOrderItem>();
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        page = tradeOrderItemService.selectByWhere(page, new Wrapper(entity));
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
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        List<TradeOrderItem> list = tradeOrderItemService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        List<TradeOrderItem> list = tradeOrderItemService.selectAll(new Wrapper(entity));
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
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        int rs = tradeOrderItemService.insert(entity);
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
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        int rs = tradeOrderItemService.insertSelective(entity);
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
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        int rs = tradeOrderItemService.updateById(entity);
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
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        TradeOrderItem condition = new TradeOrderItem();
        condition.setId(2L);
        int rs = tradeOrderItemService.updateByWhere(entity, new Wrapper(condition));
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
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        int rs = tradeOrderItemService.updateByIdSelective(entity);
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
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        TradeOrderItem condition = new TradeOrderItem();
        condition.setId(2L);
        int rs = tradeOrderItemService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = tradeOrderItemService.deleteById(1L);
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
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        int rs = tradeOrderItemService.deleteByWhere(new Wrapper(entity));
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
        TradeOrderItem entity = new TradeOrderItem();
        entity.setOrderItemId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setPId(1L);//Long 商品id(商品表id)
        entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
        entity.setSkuId(1L);//Long 商品sku的id
        entity.setSkuValue("1");//String sku规格值(快照)
        entity.setName("1");//String 商品名称(快照)
        entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
        entity.setQuantity(1);//Long 数量
        entity.setCategory("1");//String 商品分类(平台)(快照)
        entity.setStoreCategory("1");//String 商品分类(本店)(快照)
        entity.setBrand("1");//String 品牌(快照)
        entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
        entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
        entity.setScore(1);//Long 所用积分
        entity.setWeight(1D);//String 商品重量(快照)
        entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
        entity.setCommissionRatio("1");//String 佣金比
        entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10

        int rs = tradeOrderItemService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        TradeOrderItem p1 = new TradeOrderItem();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        TradeOrderItem p2 = new TradeOrderItem();//p2的属性全是空值

        tradeOrderItemService.selectAll(null);
        tradeOrderItemService.selectAll(new Wrapper(p2));
        tradeOrderItemService.countByWhere(null);
        tradeOrderItemService.countByWhere(new Wrapper(p2));

        try {
            tradeOrderItemService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeOrderItemService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeOrderItemService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeOrderItemService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeOrderItemService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeOrderItemService.deleteByWhere(null);
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
        List<TradeOrderItem> list = new ArrayList<TradeOrderItem>();
        for (int i = 0; i < 10; i++) {
            TradeOrderItem entity = new TradeOrderItem();
            entity.setOrderItemId(1L);//Long 主键
            entity.setOrderId(1L);//Long 订单id(订单表id)
            entity.setPId(1L);//Long 商品id(商品表id)
            entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
            entity.setSkuId(1L);//Long 商品sku的id
            entity.setSkuValue("1");//String sku规格值(快照)
            entity.setName("1");//String 商品名称(快照)
            entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
            entity.setQuantity(1);//Long 数量
            entity.setCategory("1");//String 商品分类(平台)(快照)
            entity.setStoreCategory("1");//String 商品分类(本店)(快照)
            entity.setBrand("1");//String 品牌(快照)
            entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
            entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
            entity.setScore(1);//Long 所用积分
            entity.setWeight(1D);//String 商品重量(快照)
            entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
            entity.setCommissionRatio("1");//String 佣金比
            entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setBak6("1");//String 备用字段6
            entity.setBak7("1");//String 备用字段7
            entity.setBak8("1");//String 备用字段8
            entity.setBak9("1");//String 备用字段9
            entity.setBak10("1");//String 备用字段10

            list.add(entity);
        }
        boolean rs = tradeOrderItemService.insertBatch(list);
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
        List<TradeOrderItem> list = new ArrayList<TradeOrderItem>();
        for (int i = 0; i < 10; i++) {
            TradeOrderItem entity = new TradeOrderItem();
            entity.setOrderItemId(1L);//Long 主键
            entity.setOrderId(1L);//Long 订单id(订单表id)
            entity.setPId(1L);//Long 商品id(商品表id)
            entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
            entity.setSkuId(1L);//Long 商品sku的id
            entity.setSkuValue("1");//String sku规格值(快照)
            entity.setName("1");//String 商品名称(快照)
            entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
            entity.setQuantity(1);//Long 数量
            entity.setCategory("1");//String 商品分类(平台)(快照)
            entity.setStoreCategory("1");//String 商品分类(本店)(快照)
            entity.setBrand("1");//String 品牌(快照)
            entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
            entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
            entity.setScore(1);//Long 所用积分
            entity.setWeight(1D);//String 商品重量(快照)
            entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
            entity.setCommissionRatio("1");//String 佣金比
            entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setBak6("1");//String 备用字段6
            entity.setBak7("1");//String 备用字段7
            entity.setBak8("1");//String 备用字段8
            entity.setBak9("1");//String 备用字段9
            entity.setBak10("1");//String 备用字段10

            list.add(entity);
        }
        boolean rs = tradeOrderItemService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<TradeOrderItem> list = new ArrayList<TradeOrderItem>();
        for (int i = 0; i < 10; i++) {
            TradeOrderItem entity = new TradeOrderItem();
            entity.setOrderItemId(1L);//Long 主键
            entity.setOrderId(1L);//Long 订单id(订单表id)
            entity.setPId(1L);//Long 商品id(商品表id)
            entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
            entity.setSkuId(1L);//Long 商品sku的id
            entity.setSkuValue("1");//String sku规格值(快照)
            entity.setName("1");//String 商品名称(快照)
            entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
            entity.setQuantity(1);//Long 数量
            entity.setCategory("1");//String 商品分类(平台)(快照)
            entity.setStoreCategory("1");//String 商品分类(本店)(快照)
            entity.setBrand("1");//String 品牌(快照)
            entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
            entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
            entity.setScore(1);//Long 所用积分
            entity.setWeight(1D);//String 商品重量(快照)
            entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
            entity.setCommissionRatio("1");//String 佣金比
            entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setBak6("1");//String 备用字段6
            entity.setBak7("1");//String 备用字段7
            entity.setBak8("1");//String 备用字段8
            entity.setBak9("1");//String 备用字段9
            entity.setBak10("1");//String 备用字段10

            list.add(entity);
        }
        boolean rs = tradeOrderItemService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<TradeOrderItem> list = new ArrayList<TradeOrderItem>();
        for (int i = 0; i < 10; i++) {
            TradeOrderItem entity = new TradeOrderItem();
            entity.setOrderItemId(1L);//Long 主键
            entity.setOrderId(1L);//Long 订单id(订单表id)
            entity.setPId(1L);//Long 商品id(商品表id)
            entity.setThumbnailPath("1");//String 商品缩略图地址(快照)
            entity.setSkuId(1L);//Long 商品sku的id
            entity.setSkuValue("1");//String sku规格值(快照)
            entity.setName("1");//String 商品名称(快照)
            entity.setPrice(new BigDecimal("2"));//java.math.BigDecimal 商品单价(快照)
            entity.setQuantity(1);//Long 数量
            entity.setCategory("1");//String 商品分类(平台)(快照)
            entity.setStoreCategory("1");//String 商品分类(本店)(快照)
            entity.setBrand("1");//String 品牌(快照)
            entity.setType("1");//String 销售类型，1零售型、2批发型(快照)
            entity.setProductType("1");//String 商品类型，0普通商品、1赠品、2礼品(积分兑换的礼品)(快照)
            entity.setScore(1);//Long 所用积分
            entity.setWeight(1D);//String 商品重量(快照)
            entity.setIsAdditionalComment("1");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
            entity.setCommissionRatio("1");//String 佣金比
            entity.setIsReturnStatus("1");//String 是否退货退款,0否、1退货退款、2退款
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setBak6("1");//String 备用字段6
            entity.setBak7("1");//String 备用字段7
            entity.setBak8("1");//String 备用字段8
            entity.setBak9("1");//String 备用字段9
            entity.setBak10("1");//String 备用字段10

            list.add(entity);
        }
        boolean rs = tradeOrderItemService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        TradeOrderItem entity = new TradeOrderItem();
        entity.setId(1L);
        tradeOrderItemService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = tradeOrderItemService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}