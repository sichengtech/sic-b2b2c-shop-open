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
package com.sicheng.admin.account;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.account.entity.AccountPlatform;
import com.sicheng.admin.account.entity.AccountUser;
import com.sicheng.admin.account.service.AccountPlatformService;
import com.sicheng.admin.account.service.AccountService;
import com.sicheng.admin.account.service.AccountUserService;
import com.sicheng.common.persistence.wrapper.Wrapper;

/**
 * 账户体系 的单元测试
 * 是操作“账户体系”的总入口，
 * 包含：开户、支付、提现、退款、商品结算、调账

 * @author 赵磊
 * @version 2018-07-13
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class AccountServiceTest {
	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountUserService accountUserService;

	@Autowired
	AccountPlatformService accountPlatformService;//平台账户

	/**
	 * 测试
	 * 开户 为会员开通资金账户
	 */
	@Test
	public void test_openAccount(){
		Long userId=12L;//会员ID
		Integer accountType=0;//账户类型（0.商家账户、1.服务账户）
		AccountUser au=accountService.openAccount(userId,accountType);
		Long auid=au.getAuId();//新开通近账户ID
		System.out.println("新开通近账户ID:"+auid);
	}

	/**
	 * 测试
	 * 支付商品费用
	 *
	 * @param id 业务id
	 * @param money 支付的金额
	 * @param couponMoney 支付使用优惠券金额（没用为0）
	 * @param totalMoney 总金额（商品合并支付的时候，总金额为多个订单的金额和；除此之外支付金额和总金额相同）（去掉优惠券）
	 * @param payWayId 支付方式id
	 * @param payWayName 支付方式名称
	 * @param outerTradeNo 外部交易记录号
	 */
	@Test
	public void test_paymentProduct(){
		Long id = 1L;
		BigDecimal money=new BigDecimal("6");
		BigDecimal couponMoney=new BigDecimal("0");
		BigDecimal totalMoney=new BigDecimal("6");
		Long payWayId=12L;
		String payWayName="赵磊支付";
		String outerTradeNo="zl_20185988686";
		accountService.paymentProduct(id, money, couponMoney, totalMoney, payWayId, payWayName, outerTradeNo);
	}

	/**
	 * 测试
	 * 会员申请提现、冻结资金 （原子操作，防止超额提现）
	 */
	@Test
	public void test_withdrawApplyUser(){
		Long accountId=2L;//会员账户
		BigDecimal money=new BigDecimal("0.01");
		accountService.withdrawApplyUser(accountId,money);
	}

	/**
	 *  测试
	 *  平台申请提现 、冻结资金 （原子操作，防止超额提现）
	 */
	@Test
	public void test_withdrawApplyPlatform(){
		Long accountId=null;
		BigDecimal money=new BigDecimal("0.01");
		accountService.withdrawApplyPlatform(accountId,money);
	}

	/**
	 * 测试
	 * 会员执行提现（把钱提走了）
	 *
	 * @param id 业务id
	 * @param accountId 会员账户的ID
	 * @param money 金额
	 * @param payWayId 支付方式id
	 * @param payWayName 支付方式名称
	 * @param outerTradeNo 外部交易记录号
	 */
	@Test
	public void test_withdrawExecuteUser(){
		Long id = 1L;
		Long accountId=2L;//会员账户
		BigDecimal money=new BigDecimal("0.01");
		Long payWayId=12L;
		String payWayName="赵磊支付";
		String outerTradeNo="zl_20185988685";
		accountService.withdrawExecuteUser(id,accountId,money,payWayId,payWayName,outerTradeNo);
	}

	/**
	 *  测试
	 *  平台执行提现（把钱提走了）
	 *
	 * @param id 业务id
	 * @param accountId 平台账户的ID，目前无用，请传入null，目前固定从平台营收账户提现。
	 * @param money 金额
	 * @param payWayId 支付方式id
	 * @param payWayName 支付方式名称
	 * @param outerTradeNo 外部交易记录号
	 */
	@Test
	public void test_withdrawExecutePlatform(){
		Long id = 1L;
		Long accountId=null;
		BigDecimal money=new BigDecimal("0.01");
		Long payWayId=12L;
		String payWayName="赵磊支付";
		String outerTradeNo="zl_20185988685";
		accountService.withdrawExecutePlatform(id,accountId,money,payWayId,payWayName,outerTradeNo);
	}


	/**
	 * 测试
	 * 拒绝会员提现（资金解冻了）
	 */
	@Test
	public void test_withdrawRefuseUser(){
		Long accountId=2L;//会员账户
		BigDecimal money=new BigDecimal("0.01");
		accountService.withdrawRefuseUser(accountId,money);
	}

	/**
	 *  测试
	 *  拒绝平台提现（资金解冻了）
	 */
	@Test
	public void test_withdrawRefusePlatform(){
		Long accountId=null;
		BigDecimal money=new BigDecimal("0.01");
		accountService.withdrawRefusePlatform(accountId,money);
	}

	/**
	 * 测试
	 * 退款，商品退款
	 *
	 * @param id 订单id
	 * @param amount 退款的金额
	 * @param couponMoney 使用优惠券金额
	 * @param payWayId 支付方式id
	 * @param payWayName 支付方式名称
	 * @param outerTradeNo 外部交易记录号
	 */
	@Test
	public void test_refundProduct() {
		Long id = 1L;
		BigDecimal money=new BigDecimal("5");
		BigDecimal couponMoney=new BigDecimal("5");
		Long payWayId=12L;
		String payWayName="赵磊支付";
		String outerTradeNo="zl_20185988685";
		accountService.refundProduct(id, money, couponMoney, payWayId, payWayName, outerTradeNo);
	}


	/**
	 * 测试
	 * 商品结算
	 *
	 * @param id 业务id
	 * @param accountId 会员账户ID
	 * @param money  总金额
	 * @param money1 订单总金额\服务总金额
	 * @param money2 商家承担的手续费用，如果商家没有承担请输入0元
	 * @param money3 结算给平台的佣金，如果没有佣金请输入0元
	 * @param money4 补贴金额
	 */
	@Test
	public void test_settlementProduct() {
		Long id=1L;//业务id
		Long accountId=2L;//会员账户
		BigDecimal money1=new BigDecimal("4");
		BigDecimal money2=new BigDecimal("1");
		BigDecimal money3=new BigDecimal("3");
		BigDecimal money4=new BigDecimal("5");
		accountService.settlementProduct(id, accountId, money1, money2, money3, money4);
	}

	/**
	 * 测试
	 * 从平台账户 调账2.3元到  会员账户
	 * @param type 账户类型 1平台账户，2会员账户
	 * @param accountId 账户ID
	 * @param money 金额，可以是负数
	 * @param serialNumber 流水号
	 * @param tradeRemarks 备注
	 */
	@Test
	public void test_accountAdjustment() {
		String serialNumber="sn384993676";
		Integer type=1;//1平台账户，2会员账户
		Long accountId=10L;//平台未结账户
		String j="0.35";
		BigDecimal money=new BigDecimal(j).negate();//金额，可以是负数
		String tradeRemarks="测试从平台账户 调账"+j+"元到  会员账户";
		accountService.accountAdjustment(type,accountId,money,serialNumber,tradeRemarks);

		Integer type2=2;//1平台账户，2会员账户
		Long accountId2=2L;
		BigDecimal money2=new BigDecimal(j);//金额，可以是负数
		String tradeRemarks2="测试从平台账户 调账"+j+"元到  会员账户";
		accountService.accountAdjustment(type2,accountId2,money2,serialNumber,tradeRemarks2);
	}

	/**
	 * 统计 平台现有资金总额
	 * 平台现有资金总额 = 平台未结账户金额 + 平台营收账户余额(含冻结金额)  + 平台保证金账户 + 每个会员账户余额(含冻结金额)
	 * @return
	 */
	@Test
	public void test_countAllMoney(){
		BigDecimal money= accountService.countAllMoney();
		System.out.println("平台现有资金总额:"+money.toString());
	}

	/**
	 * 组合测试(商家承担手续费)
	 */
	@Test
	public void test_group_shangjia() {
		//开户 为会员开通资金账户
		Long userId=-1L;//会员ID
		Integer accountType=0;//账户类型（0.商家账户、1.服务账户）
		AccountUser au=accountService.openAccount(userId,accountType);
		Long auid=au.getAuId();//新开通近账户ID

		//清空平台账户中的金额（事务会回滚）
		AccountPlatform entity=new AccountPlatform();
		entity.setOwnMoney(new BigDecimal("0"));
		entity.setFrozenMoney(new BigDecimal("0"));
		accountPlatformService.updateByWhereSelective(entity, new Wrapper().and("1=",1));

		//清空商家账户中的余额（事务会回滚）
		AccountUser accountUser= new AccountUser();
		accountUser.setOwnMoney(new BigDecimal("0"));
		accountUser.setFrozenMoney(new BigDecimal("0"));
		accountUserService.updateByWhereSelective(accountUser, new Wrapper().and("1=",1));

		//用户a买商品支付1000元
		Long id1 = 1L;
		BigDecimal money1=new BigDecimal("1000");
		BigDecimal couponMoney1=new BigDecimal("0");
		BigDecimal totalMoney1=new BigDecimal("1000");
		Long payWayId1=12L;
		String payWayName1="赵磊支付";
		String outerTradeNo1="zl_20185988686";
		accountService.paymentProduct(id1, money1, couponMoney1, totalMoney1, payWayId1, payWayName1, outerTradeNo1);

		//统计 平台现有资金总额
		BigDecimal moneyAll_1= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("994").compareTo(moneyAll_1));

		//用户b买商品支付500元
		Long id2 = 2L;
		BigDecimal money2=new BigDecimal("500");
		BigDecimal couponMoney2=new BigDecimal("0");
		BigDecimal totalMoney2=new BigDecimal("500");
		Long payWayId2=12L;
		String payWayName2="赵磊支付";
		String outerTradeNo2="zl_20185988681";
		accountService.paymentProduct(id2, money2, couponMoney2, totalMoney2, payWayId2, payWayName2, outerTradeNo2);

		//统计 平台现有资金总额
		BigDecimal moneyAll_2= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("1491").compareTo(moneyAll_2));

		//用户b退款5元
		Long id3 = 2L;
		BigDecimal money3=new BigDecimal("500");
		BigDecimal couponMoney3=new BigDecimal("0");
		Long payWayId3=12L;
		String payWayName3="赵磊支付";
		String outerTradeNo3="zl_20185988681";
		accountService.refundProduct(id3, money3, couponMoney3, payWayId3, payWayName3, outerTradeNo3);

		//统计 平台现有资金总额
		BigDecimal moneyAll_3= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("994").compareTo(moneyAll_3));

		//商品结算(平台扣除10元佣金)
		Long id_1=1L;//业务id
		Long accountId4=auid;//会员账户
		BigDecimal money1_1=new BigDecimal("1000");
		BigDecimal money2_1=new BigDecimal("6");
		BigDecimal money3_1=new BigDecimal("10");
		BigDecimal money4_1=new BigDecimal("6");
		accountService.settlementProduct(id_1, accountId4, money1_1, money2_1, money3_1, money4_1);

		//统计 平台现有资金总额
		BigDecimal moneyAll_4= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("994").compareTo(moneyAll_4));

		//商家提现984元  申请
		Long accountId5=auid;//会员账户
		BigDecimal money5=new BigDecimal("984");
		accountService.withdrawApplyUser(accountId5,money5);

		//商家提现984元  执行
		Long accountId6=auid;//会员账户
		BigDecimal money6=new BigDecimal("984");
		Long payWayId6=12L;
		String payWayName6="赵磊支付";
		String outerTradeNo6="zl_20185988685";
		accountService.withdrawExecuteUser(10L,accountId6,money6,payWayId6,payWayName6,outerTradeNo6);

		//统计 平台现有资金总额
		BigDecimal moneyAll_5= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("10").compareTo(moneyAll_5));

		//平台提现10元 申请
		Long accountId7=null;
		BigDecimal money7=new BigDecimal("10");
		accountService.withdrawApplyPlatform(accountId7,money7);
		//平台提现10元 执行
		Long accountId8=null;
		BigDecimal money8=new BigDecimal("10");
		Long payWayId8=12L;
		String payWayName8="赵磊支付";
		String outerTradeNo8="zl_20185988685";
		accountService.withdrawExecutePlatform(11L,accountId8,money8,payWayId8,payWayName8,outerTradeNo8);

		//统计 平台现有资金总额
		BigDecimal moneyAll_6= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("0").compareTo(moneyAll_6));
	}

	/**
	 * 组合测试(平台承担手续费)
	 */
	@Test
	public void test_group_pingtai() {
		//开户 为会员开通资金账户
		Long userId=-1L;//会员ID
		Integer accountType=0;//账户类型（0.商家账户、1.服务账户）
		AccountUser au=accountService.openAccount(userId,accountType);
		Long auid=au.getAuId();//新开通近账户ID

		//清空平台账户中的金额（事务会回滚）
		AccountPlatform entity=new AccountPlatform();
		entity.setOwnMoney(new BigDecimal("0"));
		entity.setFrozenMoney(new BigDecimal("0"));
		accountPlatformService.updateByWhereSelective(entity, new Wrapper().and("1=",1));

		//清空商家账户中的余额（事务会回滚）
		AccountUser accountUser= new AccountUser();
		accountUser.setOwnMoney(new BigDecimal("0"));
		accountUser.setFrozenMoney(new BigDecimal("0"));
		accountUserService.updateByWhereSelective(accountUser, new Wrapper().and("1=",1));

		//用户a买商品支付1000元
		Long id1 = 1L;
		BigDecimal money1=new BigDecimal("1000");
		BigDecimal couponMoney1=new BigDecimal("0");
		BigDecimal totalMoney1=new BigDecimal("1000");
		Long payWayId1=12L;
		String payWayName1="赵磊支付";
		String outerTradeNo1="zl_20185988686";
		accountService.paymentProduct(id1, money1, couponMoney1, totalMoney1, payWayId1, payWayName1, outerTradeNo1);

		//统计 平台现有资金总额
		BigDecimal moneyAll_1= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("994").compareTo(moneyAll_1));

		//用户b买商品支付500元
		Long id2 = 2L;
		BigDecimal money2=new BigDecimal("500");
		BigDecimal couponMoney2=new BigDecimal("0");
		BigDecimal totalMoney2=new BigDecimal("500");
		Long payWayId2=12L;
		String payWayName2="赵磊支付";
		String outerTradeNo2="zl_20185988681";
		accountService.paymentProduct(id2, money2, couponMoney2, totalMoney2, payWayId2, payWayName2, outerTradeNo2);

		//统计 平台现有资金总额
		BigDecimal moneyAll_2= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("1491").compareTo(moneyAll_2));

		//用户b退款5元
		Long id3 = 2L;
		BigDecimal money3=new BigDecimal("500");
		BigDecimal couponMoney3=new BigDecimal("0");
		Long payWayId3=12L;
		String payWayName3="赵磊支付";
		String outerTradeNo3="zl_20185988681";
		accountService.refundProduct(id3, money3, couponMoney3, payWayId3, payWayName3, outerTradeNo3);

		//统计 平台现有资金总额
		BigDecimal moneyAll_3= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("994").compareTo(moneyAll_3));

		//商品结算(平台扣除10元佣金)
		Long id_1=1L;//业务id
		Long accountId4=auid;//会员账户
		BigDecimal money1_1=new BigDecimal("1000");
		BigDecimal money2_1=new BigDecimal("0");
		BigDecimal money3_1=new BigDecimal("10");
		BigDecimal money4_1=new BigDecimal("0");
		accountService.settlementProduct(id_1, accountId4, money1_1, money2_1, money3_1, money4_1);

		//统计 平台现有资金总额
		BigDecimal moneyAll_4= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("994").compareTo(moneyAll_4));

		//商家提现990元  申请
		Long accountId5=auid;//会员账户
		BigDecimal money5=new BigDecimal("990");
		accountService.withdrawApplyUser(accountId5,money5);

		//商家提现990元  执行
		Long accountId6=auid;//会员账户
		BigDecimal money6=new BigDecimal("990");
		Long payWayId6=12L;
		String payWayName6="赵磊支付";
		String outerTradeNo6="zl_20185988685";
		accountService.withdrawExecuteUser(10L,accountId6,money6,payWayId6,payWayName6,outerTradeNo6);

		//统计 平台现有资金总额
		BigDecimal moneyAll_5= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("4").compareTo(moneyAll_5));

		//平台补平6元
		accountService.accountAdjustment(1, 14L, new BigDecimal("-6"), "1111", "补贴账户调账");

		//统计 平台现有资金总额
		BigDecimal moneyAll_7= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("10").compareTo(moneyAll_7));

		//平台提现10元 申请
		Long accountId7=null;
		BigDecimal money7=new BigDecimal("10");
		accountService.withdrawApplyPlatform(accountId7,money7);
		//平台提现10元 执行
		Long accountId8=null;
		BigDecimal money8=new BigDecimal("10");
		Long payWayId8=12L;
		String payWayName8="赵磊支付";
		String outerTradeNo8="zl_20185988685";
		accountService.withdrawExecutePlatform(11L,accountId8,money8,payWayId8,payWayName8,outerTradeNo8);

		//统计 平台现有资金总额
		BigDecimal moneyAll_6= accountService.countAllMoney();
		Assert.assertEquals(0, new BigDecimal("0").compareTo(moneyAll_6));
	}
}