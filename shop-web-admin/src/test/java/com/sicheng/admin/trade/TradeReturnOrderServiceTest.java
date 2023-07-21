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

import com.sicheng.admin.trade.entity.TradeReturnOrder;
import com.sicheng.admin.trade.service.TradeReturnOrderService;
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
 * <p>标题: 单元测试--退款、退货退款订单 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 范秀秀
 * @version 2017-05-12
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class TradeReturnOrderServiceTest {
    @Autowired
    private TradeReturnOrderService tradeReturnOrderService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        TradeReturnOrder tradeReturnOrder = tradeReturnOrderService.selectById(0L);
        Assert.assertNull(tradeReturnOrder);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add(new BigDecimal("2"));
        List<TradeReturnOrder> list = tradeReturnOrderService.selectByIdIn(paramList);
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
        Page<TradeReturnOrder> page = new Page<TradeReturnOrder>();
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        page = tradeReturnOrderService.selectByWhere(page, new Wrapper(entity));
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
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        List<TradeReturnOrder> list = tradeReturnOrderService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        List<TradeReturnOrder> list = tradeReturnOrderService.selectAll(new Wrapper(entity));
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
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        int rs = tradeReturnOrderService.insert(entity);
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
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        int rs = tradeReturnOrderService.insertSelective(entity);
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
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        int rs = tradeReturnOrderService.updateById(entity);
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
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        TradeReturnOrder condition = new TradeReturnOrder();
        condition.setId(2L);
        int rs = tradeReturnOrderService.updateByWhere(entity, new Wrapper(condition));
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
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        int rs = tradeReturnOrderService.updateByIdSelective(entity);
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
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        TradeReturnOrder condition = new TradeReturnOrder();
        condition.setId(2L);
        int rs = tradeReturnOrderService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = tradeReturnOrderService.deleteById(1L);
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
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        int rs = tradeReturnOrderService.deleteByWhere(new Wrapper(entity));
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
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setReturnOrderId(1L);//Long 主键
        entity.setOrderId(1L);//Long 订单编号(订单表id)
        entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
        entity.setType("1");//String 类型，1退货退款、2退款
        entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
        entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
        entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
        entity.setReturnCount(1);//Long 退货数量
        entity.setReturnReason("1");//String 退货原因(从字典里取)
        entity.setReturnExplain("1");//String 退货说明
        entity.setApplyDate(new Date());//java.util.Date 申请时间
        entity.setUId(1L);//Long 申请人(会员表id)
        entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
        entity.setBusinessHandleRemarks("1");//String 商家处理备注
        entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
        entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
        entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
        entity.setSystemHandleRemarks("1");//String 平台处理备注
        entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
        entity.setAdminId(1L);//Long 管理员id(管理员表id)
        entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
        entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
        entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
        entity.setLogisticsTemplateName("1");//String 物流公司名称
        entity.setTrackingNo("1");//String 运单号
        entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
        entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
        entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
        entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
        entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
        entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
        entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
        entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setIsJettison("1");//String 是否弃货

        int rs = tradeReturnOrderService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        TradeReturnOrder p1 = new TradeReturnOrder();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        TradeReturnOrder p2 = new TradeReturnOrder();//p2的属性全是空值

        tradeReturnOrderService.selectAll(null);
        tradeReturnOrderService.selectAll(new Wrapper(p2));
        tradeReturnOrderService.countByWhere(null);
        tradeReturnOrderService.countByWhere(new Wrapper(p2));

        try {
            tradeReturnOrderService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeReturnOrderService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeReturnOrderService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeReturnOrderService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeReturnOrderService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeReturnOrderService.deleteByWhere(null);
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
        List<TradeReturnOrder> list = new ArrayList<TradeReturnOrder>();
        for (int i = 0; i < 10; i++) {
            TradeReturnOrder entity = new TradeReturnOrder();
            entity.setReturnOrderId(1L);//Long 主键
            entity.setOrderId(1L);//Long 订单编号(订单表id)
            entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
            entity.setType("1");//String 类型，1退货退款、2退款
            entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
            entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
            entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
            entity.setReturnCount(1);//Long 退货数量
            entity.setReturnReason("1");//String 退货原因(从字典里取)
            entity.setReturnExplain("1");//String 退货说明
            entity.setApplyDate(new Date());//java.util.Date 申请时间
            entity.setUId(1L);//Long 申请人(会员表id)
            entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
            entity.setBusinessHandleRemarks("1");//String 商家处理备注
            entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
            entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
            entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
            entity.setSystemHandleRemarks("1");//String 平台处理备注
            entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
            entity.setAdminId(1L);//Long 管理员id(管理员表id)
            entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
            entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
            entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
            entity.setLogisticsTemplateName("1");//String 物流公司名称
            entity.setTrackingNo("1");//String 运单号
            entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
            entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
            entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
            entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
            entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
            entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
            entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
            entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setIsJettison("1");//String 是否弃货

            list.add(entity);
        }
        boolean rs = tradeReturnOrderService.insertBatch(list);
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
        List<TradeReturnOrder> list = new ArrayList<TradeReturnOrder>();
        for (int i = 0; i < 10; i++) {
            TradeReturnOrder entity = new TradeReturnOrder();
            entity.setReturnOrderId(1L);//Long 主键
            entity.setOrderId(1L);//Long 订单编号(订单表id)
            entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
            entity.setType("1");//String 类型，1退货退款、2退款
            entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
            entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
            entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
            entity.setReturnCount(1);//Long 退货数量
            entity.setReturnReason("1");//String 退货原因(从字典里取)
            entity.setReturnExplain("1");//String 退货说明
            entity.setApplyDate(new Date());//java.util.Date 申请时间
            entity.setUId(1L);//Long 申请人(会员表id)
            entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
            entity.setBusinessHandleRemarks("1");//String 商家处理备注
            entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
            entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
            entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
            entity.setSystemHandleRemarks("1");//String 平台处理备注
            entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
            entity.setAdminId(1L);//Long 管理员id(管理员表id)
            entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
            entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
            entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
            entity.setLogisticsTemplateName("1");//String 物流公司名称
            entity.setTrackingNo("1");//String 运单号
            entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
            entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
            entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
            entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
            entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
            entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
            entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
            entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setIsJettison("1");//String 是否弃货

            list.add(entity);
        }
        boolean rs = tradeReturnOrderService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<TradeReturnOrder> list = new ArrayList<TradeReturnOrder>();
        for (int i = 0; i < 10; i++) {
            TradeReturnOrder entity = new TradeReturnOrder();
            entity.setReturnOrderId(1L);//Long 主键
            entity.setOrderId(1L);//Long 订单编号(订单表id)
            entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
            entity.setType("1");//String 类型，1退货退款、2退款
            entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
            entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
            entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
            entity.setReturnCount(1);//Long 退货数量
            entity.setReturnReason("1");//String 退货原因(从字典里取)
            entity.setReturnExplain("1");//String 退货说明
            entity.setApplyDate(new Date());//java.util.Date 申请时间
            entity.setUId(1L);//Long 申请人(会员表id)
            entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
            entity.setBusinessHandleRemarks("1");//String 商家处理备注
            entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
            entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
            entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
            entity.setSystemHandleRemarks("1");//String 平台处理备注
            entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
            entity.setAdminId(1L);//Long 管理员id(管理员表id)
            entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
            entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
            entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
            entity.setLogisticsTemplateName("1");//String 物流公司名称
            entity.setTrackingNo("1");//String 运单号
            entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
            entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
            entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
            entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
            entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
            entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
            entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
            entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setIsJettison("1");//String 是否弃货

            list.add(entity);
        }
        boolean rs = tradeReturnOrderService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<TradeReturnOrder> list = new ArrayList<TradeReturnOrder>();
        for (int i = 0; i < 10; i++) {
            TradeReturnOrder entity = new TradeReturnOrder();
            entity.setReturnOrderId(1L);//Long 主键
            entity.setOrderId(1L);//Long 订单编号(订单表id)
            entity.setOrderItemId(1L);//Long 订单详情编号(订单详情表id)
            entity.setType("1");//String 类型，1退货退款、2退款
            entity.setReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 退款金额
            entity.setReturnCommission(new BigDecimal("2"));//java.math.BigDecimal 退还佣金
            entity.setReturnFreight(new BigDecimal("2"));//java.math.BigDecimal 退还运费
            entity.setReturnCount(1);//Long 退货数量
            entity.setReturnReason("1");//String 退货原因(从字典里取)
            entity.setReturnExplain("1");//String 退货说明
            entity.setApplyDate(new Date());//java.util.Date 申请时间
            entity.setUId(1L);//Long 申请人(会员表id)
            entity.setBusinessHandle("1");//String 商家处理，0待审核、1同意、2拒绝
            entity.setBusinessHandleRemarks("1");//String 商家处理备注
            entity.setBusinessHandleDate(new Date());//java.util.Date 商家处理时间
            entity.setSystemHandle(1L);//Long 平台处理，0未处理、1已审核并退款、2拒绝退款
            entity.setSystemAgreeTime(new Date());//java.util.Date 平台同意退款时间（结算时以此时间为准）
            entity.setSystemHandleRemarks("1");//String 平台处理备注
            entity.setPayWayId(1L);//Long 支付方式(支付方式表id)
            entity.setAdminId(1L);//Long 管理员id(管理员表id)
            entity.setStatus("1");//String 状态，10待商家审核、20商家审核失败、30待买家退货、40待卖家收货、50待平台审核、60平台审核,退款完成，70平台审核失败
            entity.setDeliverProductTime(new Date());//java.util.Date 买家发货时间
            entity.setLogisticsTemplateId(1L);//Long 物流公司id(物流公司表外键，买家发货使用的物流公司)
            entity.setLogisticsTemplateName("1");//String 物流公司名称
            entity.setTrackingNo("1");//String 运单号
            entity.setIsProductReceipt("1");//String 商家是否收货，0否、1是
            entity.setProductReceiptTime(new Date());//java.util.Date 商家收货时间
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
            entity.setOnlineReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 在线退款金额
            entity.setAlipayReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 支付宝退款金额
            entity.setPreDepositReturnMoney(new BigDecimal("2"));//java.math.BigDecimal 预存款退款金额
            entity.setWxReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 微信退款金额
            entity.setBak1ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额1
            entity.setBak2ReturnMoeny(new BigDecimal("2"));//java.math.BigDecimal 备用退款金额2
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setIsJettison("1");//String 是否弃货

            list.add(entity);
        }
        boolean rs = tradeReturnOrderService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        TradeReturnOrder entity = new TradeReturnOrder();
        entity.setId(1L);
        tradeReturnOrderService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = tradeReturnOrderService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}