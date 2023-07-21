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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.admin.trade.service.TradeOrderService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;

/**
 * <p>标题: 单元测试--订单基本信息 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 * @author 张加利
 * @version 2019-11-20
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"}) 
public class TradeOrderServiceTest {
	@Autowired
	private TradeOrderService tradeOrderService;
	
	
	/**
	 * 获取单条数据
	 */
	@Test
	public void test_selectById(){
		TradeOrder tradeOrder= tradeOrderService.selectById(0L);
		Assert.assertNull(tradeOrder);
	}
	
	/**
	 * 查询 select * from a where id in(…)
	 */
	@Test
	public void test_selectByIdIn(){
		List<Object> paramList=new ArrayList<Object>();
		paramList.add("1");
		paramList.add("2");
		List<TradeOrder> list= tradeOrderService.selectByIdIn(paramList);
		Assert.assertNotNull(list);
	}
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
	 * 					入参为null，或入参为new一个实体对象但无属性值，会查全表
	 */
	@Test
	public void test_selectByWhere(){
		Page<TradeOrder> page=new Page<TradeOrder>();
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		page= tradeOrderService.selectByWhere(page, new Wrapper(entity));
		Assert.assertNotNull(page.getList());
	}
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
	 * 					入参为null，或入参为new一个实体对象但无属性值，会查全表
	 */
	@Test
	public void test_selectByWhere2(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		List<TradeOrder> list= tradeOrderService.selectByWhere(new Wrapper(entity));
		Assert.assertNotNull(list);
	}
	
	/**
	 * 查询所有数据列表，无条件
	 * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
	 */
	@Test
	public void test_selectAll(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		//因当表中数据量较大时，查全表很耗时，所以注释了
		//List<TradeOrder> list= tradeOrderService.selectAll(new Wrapper(entity));
		//Assert.assertNotNull(list);
	}
	
	/**
	 * 插入数据
	 * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
	 * @param entity
	 * @return 受影响的行数
	 */
	@Test
	public void test_insert(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999998L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999998L);//Long 买家id(关联会员表)
		entity.setStoreId(999999998L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999998L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999998L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999998L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999998L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999998L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999998L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999998L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999998L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		int rs= tradeOrderService.insert(entity);
		Assert.assertEquals(1, rs);
	}
	
	/**
	 * 插入,只把非空的值插入到对应的字段
	 * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
	 * @param entity
	 * @return 受影响的行数
	 */
	@Test
	public void test_insertSelective(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999997L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999997L);//Long 买家id(关联会员表)
		entity.setStoreId(999999997L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999997L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999997L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999997L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999997L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999997L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999997L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999997L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999997L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		int rs= tradeOrderService.insertSelective(entity);
		Assert.assertEquals(1, rs);
	}
	
	/**
	 * 根据主键更新记录,更新除了主键的所有字段
	 * @param entity
	 * @return 受影响的行数
	 */
	@Test
	public void test_updateById(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		int rs= tradeOrderService.updateById(entity);
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 根据条件更新记录,更新除了主键的所有字段
	 * @param entity 数据实体，用于存储数据，这些数据将被update到表中
	 * @param condition 条件，用于where条件，找出符合条件的数据。入参为null，或入参为new一个实体对象但无属性值，执行sql时会报错，防止更新全表
	 * @return 受影响的行数
	 * 
	 * 注意：condition.setDelFlag(null);把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
	 */
	@Test
	public void test_updateByWhere(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		TradeOrder condition=new TradeOrder();
		condition.setId(2L);
		int rs= tradeOrderService.updateByWhere(entity,new Wrapper(condition));
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 根据主键更新记录,只把非空的值更到对应的字段
	 * @param entity 数据实体，用于存储数据，这些数据将被update到表中
	 * @return 受影响的行数
	 */
	@Test
	public void test_updateByIdSelective(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		int rs= tradeOrderService.updateByIdSelective(entity);
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 根据条件更新记录,只把非空的值更到对应的字段
	 * @param entity 数据实体，用于存储数据，这些数据将被update到表中
	 * @param condition 条件，用于where条件，找出符合条件的数据。入参为null，或入参为new一个实体对象但无属性值，执行sql时会报错，防止更新全表
	 * @return 受影响的行数
	 * 
	 * 注意：condition.setDelFlag(null);把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
	 */
	@Test
	public void test_updateByWhereSelective(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		TradeOrder condition=new TradeOrder();
		condition.setId(2L);
		int rs= tradeOrderService.updateByWhereSelective(entity,new Wrapper(condition));
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 删除数据
	 * （如果有del_flag字段，就逻辑删除，更新del_flag字段为1表示删除）
	 * （如果无del_flag字段，就物理删除）
	 * @param id 主键
	 * @return 受影响的行数
	 */
	@Test
	public void test_deleteById(){
		int rs= tradeOrderService.deleteById(1L);
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 删除数据（物理删除）
	 * @param condition 删除条件。入参为null，或入参为new一个实体对象但无属性值，执行SQL会报错，防止删除全表
	 * @return 受影响的行数
	 * 
	 * 注意：data.setDelFlag(null);//把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
	 */
	@Test
	public void test_deleteByWhere(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		int rs= tradeOrderService.deleteByWhere(new Wrapper(entity));
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 根据条件查询记录总数
	 * @param condition 可为null。或new一个实体对象，用于控制del_flag、控制distinct
	 * @return 总行数
	 * 
	 * 注意：data.setDelFlag(null);//把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
	 */
	@Test
	public void test_countByWhere(){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
		int rs= tradeOrderService.countByWhere(new Wrapper(entity));
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 空值测试，防止空值导致无where条件时，删除全表、更新全表
	 */
	@Test
	public void test_emptyTest(){
		TradeOrder p1=new TradeOrder();
		p1.setCreateDate(new Date());
		//p1.setBak1("bak1");
		
		TradeOrder p2=new TradeOrder();//p2的属性全是空值
		
		//tradeOrderService.selectAll(null);
		//tradeOrderService.selectAll(new Wrapper(p2));
		tradeOrderService.countByWhere(null);
		tradeOrderService.countByWhere(new Wrapper(p2));
		
		try{
			tradeOrderService.updateByWhere(p1,new Wrapper());
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			tradeOrderService.updateByWhereSelective(p1,new Wrapper());
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			tradeOrderService.updateByWhere(p1,null);
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			tradeOrderService.updateByWhereSelective(p1,null);
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			tradeOrderService.deleteByWhere(new Wrapper());
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			tradeOrderService.deleteByWhere(null);
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
	}
	
	/**
	 * 批量插入
	 */
	@Test
	public void test_insertBatch(){
		List<TradeOrder> list=new ArrayList<TradeOrder>();
		for(int i=0;i<1;i++){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
			list.add(entity);
		}
		boolean rs= tradeOrderService.insertBatch(list);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getId());
		}
		Assert.assertEquals(true, rs);
	}
	
	/**
	 * 批量插入 selective
	 */
	@Test
	public void test_insertSelectiveBatch(){
		List<TradeOrder> list=new ArrayList<TradeOrder>();
		for(int i=0;i<1;i++){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999996L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999996L);//Long 买家id(关联会员表)
		entity.setStoreId(999999996L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999996L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999996L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999996L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999996L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999996L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999996L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999996L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999996L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
			list.add(entity);
		}
		boolean rs= tradeOrderService.insertSelectiveBatch(list);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getId());
		}
		Assert.assertEquals(true, rs);
	}
	
	@Test
	public void test_updateBatch(){
		List<TradeOrder> list=new ArrayList<TradeOrder>();
		for(int i=0;i<1;i++){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
			list.add(entity);
		}
		boolean rs= tradeOrderService.updateBatch(list);
		Assert.assertEquals(true, rs);
	}
	
	/**
	 * 批量插入 selective
	 */
	@Test
	public void test_updateSelectiveBatch(){
		List<TradeOrder> list=new ArrayList<TradeOrder>();
		for(int i=0;i<1;i++){
		TradeOrder entity=new TradeOrder();
		entity.setOrderId(999999999L);//Long 主键
		entity.setOutTradeNo("a");//String 商户订单号
		entity.setUId(999999999L);//Long 买家id(关联会员表)
		entity.setStoreId(999999999L);//Long 关联(店铺表)
		entity.setBName("a");//String 商家公司名称(快照)
		entity.setAdminId(999999999L);//Long 管理员id(关联管理员,管理员可以取消订单和确认收款)
		entity.setAddressId(999999999L);//Long 收货地址id(关联收货地址)
		entity.setConsignee("a");//String 收货人(快照)
		entity.setPhone("a");//String 电话(快照)
		entity.setZipCode("a");//String 邮编(快照)
		entity.setAmountPaid(new BigDecimal("0.1"));//BigDecimal 订单总金额
		entity.setOffsetAmount(new BigDecimal("0.1"));//BigDecimal 调整后的金额
		entity.setCouponDiscount(999999999L);//Long 优惠券id(预留)
		entity.setCashCouponId(999999999L);//Long 代金券id(预留)
		entity.setPromotionDiscount(new BigDecimal("0.1"));//BigDecimal 促销折扣，0.95表示95折(预留)
		entity.setPromotion("a");//String 促销(预留)，促销活动名称
		entity.setRedPacket(new BigDecimal("0.1"));//BigDecimal 平台红包(预留)
		entity.setFee(new BigDecimal("0.1"));//BigDecimal 支付佣金
		entity.setOrderStatus("a");//String 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、50已评价、60已取消、70退货退款中
		entity.setIsReturnStatus("a");//String 是否退货退款，0否、1退货退款、2退款
		entity.setPaymentMethodName("a");//String 支付方式名称（快照）
		entity.setPaymentMethodId(999999999L);//Long 支付方式(支付方式表Id)
		entity.setFreight(new BigDecimal("0.1"));//BigDecimal 运费，计算出来的总运费
		entity.setShippingMethodName("a");//String 运费模板名称(只用于显示)(快照)
		entity.setShippingMethodId(999999999L);//Long 运费模板详情Id,dti_id(通过此id可间接找到运费模板id)
		entity.setPoint("a");//String 赠送的积分
		entity.setIsNeedLogistics("a");//String 是否需物流，0无需物流、1需物流
		entity.setLogisticsTemplateId(999999999L);//Long 物流公司id(物流公司表外键)
		entity.setLogisticsTemplateName("a");//String 物流公司名称（快照）
		entity.setTrackingNo("a");//String 运单号
		entity.setTax("a");//String 税金
		entity.setIsInvoice("a");//String 是否开据发票，0否、1是
		entity.setDeliverId(999999999L);//Long 发票id(发票表id)
		entity.setMemo("a");//String 买家附言
		entity.setPlaceOrderTime(new Date());//java.util.Date 下单时间
		entity.setPayOrderTime(new Date());//java.util.Date 付款时间（结算时以此时间为准）
		entity.setDeliverProductDate(new Date());//java.util.Date 发货时间
		entity.setProductReceiptDate(new Date());//java.util.Date 收货时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
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
		entity.setPayOrderNumber("a");//String 支付单号
		entity.setSources("a");//String 来源(0pc、1移动端)
		entity.setPreDepositPay(new BigDecimal("0.1"));//BigDecimal 预存款支付(元)
		entity.setPayTerminal("a");//String 支付终端(0pc、1移动端)
		entity.setThirdPayOrderNumber("a");//String 第三方付款平台交易号
		entity.setOnlinePayMoney(new BigDecimal("0.1"));//BigDecimal 在线支付金额
		entity.setCancelOrderReason("a");//String 取消订单理由
		entity.setCityName("a");//String 收货地址所在市(快照)
		entity.setDetailedAddress("a");//String 详细地址(快照)
		entity.setDistrictName("a");//String 收货地址所在县(快照)
		entity.setProvinceName("a");//String 收货地址所在省(快照)
		entity.setSellerMemo("a");//String 卖家附言
		entity.setCancelOrderDate(new Date());//java.util.Date 取消订单时间
		entity.setDelayDays(888888888);//Integer 延迟收货天数
		entity.setIsAdditionalComment("a");//String 是否追评,0否、1是(订单状态是已完成时如果是0显示追评，1不显示追评)
		entity.setIsSettlement("a");//String 是否已结算
		entity.setSettlementMoney(new BigDecimal("0.1"));//BigDecimal 结算金额
		entity.setNeedPay("a");//String 0.不需要、1.需要。
			list.add(entity);
		}
		boolean rs= tradeOrderService.updateSelectiveBatch(list);
		Assert.assertEquals(true, rs);
	}
	
	/**
	 * 获取单条数据 
	 */
	@Test
	public void test_selectOne(){
		TradeOrder entity=new TradeOrder();
		entity.setId(1L);
		tradeOrderService.selectOne(new Wrapper(entity));
	}
	
	/**
	 *  批量删除数据
	 */
	@Test
	public void test_deleteByIdIn(){
		List<Object> list =new ArrayList<Object>();
		list.add(1L);
		list.add(2L);
		list.add(3L);
		int rs= tradeOrderService.deleteByIdIn(list);
		Assert.assertNotNull(rs);
	}	
	
}