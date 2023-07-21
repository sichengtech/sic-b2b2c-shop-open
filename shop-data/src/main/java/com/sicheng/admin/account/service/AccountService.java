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
package com.sicheng.admin.account.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.account.dao.AccountPlatformDao;
import com.sicheng.admin.account.entity.AccountPlatform;
import com.sicheng.admin.account.entity.AccountPlatformSn;
import com.sicheng.admin.account.entity.AccountThirdpartySn;
import com.sicheng.admin.account.entity.AccountUser;
import com.sicheng.admin.account.entity.AccountUserSn;
import com.sicheng.admin.account.entity.BusinAccountMapping;
import com.sicheng.admin.settlement.dao.SettlementPayWayDao;
import com.sicheng.admin.settlement.entity.SettlementPayWay;
import com.sicheng.admin.sys.dao.SysVariableDao;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.admin.trade.dao.TradeOrderDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.StringUtils;

/**
 * 账户体系 Service
 * 是操作“账户体系”的总入口，
 * 包含：开户、支付、支付保证金、提现、退款、商品结算、服务单结算、调账、验收服务单、生成优惠券、回收优惠券
 *
 * @author 蔡龙
 * @version 2018-08-21
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class AccountService extends CrudService<AccountPlatformDao, AccountPlatform> {

	@Autowired
	AccountUserService accountUserService;//会员账户
	@Autowired
	AccountUserSnService accountUserSnService;//会员账户流水
	@Autowired
	AccountPlatformService accountPlatformService;//平台账户
	@Autowired
	AccountPlatformSnService accountPlatformSnService;//平台账户流水
	@Autowired
	AccountThirdpartySnService accountThirdpartySnService;//第三方账户资金流水
	@Autowired
	AccountPlatformDao accountPlatformDao;//平台账户
	@Autowired
	BusinAccountMappingService businAccountMappingService;//业务账号中间表
	@Autowired
	SettlementPayWayDao settlementPayWayDao;//支付方式
	@Autowired
	SysVariableDao sysVariableDao;//系统变量
	@Autowired
	TradeOrderDao tradeOrderDao;//订单表

	//平台账户的ID,平台账户有3种类型，是系统初始化时自带的账户
	private static Long ACCOUNT_PLATFORM_TEMP=10L;//平台未结算账户的ID是10，类型是0
	private static Long ACCOUNT_PLATFORM_REVENUE=11L;//平台营收账户的ID是11，类型是1
	private static Long ACCOUNT_PLATFORM_POUNDAGE=13L;//平台手续费账户的ID是13，类型是3
	private static Long ACCOUNT_PLATFORM_SUBSIDY=14L;//平台补贴账户的ID是14，类型是4

	/**
	 * 开户
	 * 为会员开通资金账户
	 *
	 * 商家会员开店成功后，系统要调用本接口来开通商家账户；
	 * 平台账户：未结算账户、营收账户、手续费账户、平台补贴账户，是系统初始化时就自带的，与本接口无关。
	 *
	 * @param userId  会员id
	 * @param accountType  账户类型（0.商家账户）
	 *
	 * @return 成功后返回已开通的账户实体，失败时抛出异常
	 */
	@Transactional(rollbackFor=Exception.class)
	public AccountUser openAccount(Long userId,Integer accountType) {
		//先查询是否已开通过同类型的账户
		AccountUser accountUser=new AccountUser();
		accountUser.setUId(userId);
		accountUser.setAccountType(accountType);
		List<AccountUser> auList=accountUserService.selectByWhere(new Wrapper(accountUser));
		if(auList.size()>0) {
			//发现重复同类型账户
			throw new RuntimeException("开通账户异常，发现重复同类型账户，请不要复复开通."+accountUser.toString());
		}

		//创建账户
		AccountUser accountUser2=new AccountUser();
		accountUser2.setUId(userId);
		accountUser2.setAccountType(accountType);
		accountUserService.insertSelective(accountUser2);
		return accountUser2;
	}

	/**
	 * 删除账户
	 */
	@Transactional(rollbackFor=Exception.class)
	public void delAccount(Long userId) {
		AccountUser accountUser=new AccountUser();
		accountUser.setUId(userId);
		accountUserService.deleteByWhere(new Wrapper(accountUser));
	}

	/**
	 * 支付商品费用
	 *
	 * 买家使用微信或支付宝付款
	 * 第三方支付公司备付金账户增加，记入pay_thirdparty_sn(6 第三方账户资金流水表)；
	 * 平台未结账户 的未结算金额增加，记入pay_platform_account(3 平台账户表)、pay_platform_account_sn(4 平台账户流水表)； 
	 * 以上两个流水表使用同一个业务流水号；
	 *
	 * @param id 业务id（订单详情id）
	 * @param money 支付的金额
	 * @param couponMoney 支付使用优惠券金额（没用为0）
	 * @param totalMoney 总金额（商品合并支付的时候，总金额为多个订单的金额和；除此之外支付金额和总金额相同）（去掉优惠券）
	 * @param payWayId 支付方式id(-1代表线下支付)
	 * @param payWayName 支付方式名称(-1代表线下支付)
	 * @param outerTradeNo 外部交易记录号(-1代表线下支付)
	 *
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object paymentProduct(Long id,BigDecimal money,BigDecimal couponMoney, BigDecimal totalMoney,Long payWayId,String payWayName,String outerTradeNo) {
		//type 支付类型，9支付商品费用
		Integer type=9;
		return paymentBase(id,type,money,couponMoney,totalMoney,payWayId,payWayName,outerTradeNo);
	}

	/**
	 * 支付的基础方法
	 * 支付商品费用\支付服务费用\支付二次服务费用\支付保证金
	 *
	 * 买家使用微信或支付宝付款
	 * 第三方支付公司备付金账户增加，记入pay_thirdparty_sn(6 第三方账户资金流水表)；
	 * 平台未结账户的金额增加，记入pay_platform_account(3 平台账户表)、pay_platform_account_sn(4 平台账户流水表)； 
	 * 以上两个流水表使用同一个业务流水号；
	 *
	 * @param id 业务id
	 * @param type 支付类型，9支付商品费用
	 * @param money 支付的金额
	 * @param couponMoney 支付使用优惠券金额（没用为0）
	 * @param totalMoney 总金额（商品合并支付的时候，总金额为多个订单的金额和；除此之外支付金额和总金额相同）（去掉优惠券）
	 * @param payWayId 支付方式id(-1代表线下支付)
	 * @param payWayName 支付方式名称(-1代表线下支付)
	 * @param outerTradeNo 外部交易记录号(-1代表线下支付)
	 *
	 * @return
	 */
	private Object paymentBase(Long id,Integer type,BigDecimal money,BigDecimal couponMoney,
							   BigDecimal totalMoney,Long payWayId,String payWayName,String outerTradeNo) {
		//错误判断
		if(!type.equals(9)){
			throw new RuntimeException("支付类型type传入错误，type="+type);
		}
		if(id==null){
			throw new RuntimeException("业务id传入不能为空");
		}
		if(payWayId==null || StringUtils.isBlank(payWayName)){
			throw new RuntimeException("支付方式payWayId传入不能为空");
		}
		if(money==null || totalMoney==null){
			throw new RuntimeException("金额传入不能为空");
		}
		//默认值
		String payWayNameString = "";
		if("-1".equals(payWayName)){
			payWayNameString = FYUtils.fyParams("线下支付");
		}
		//计算并扣除手续费用
		BigDecimal accountPoundage = new BigDecimal("0");//支付手续费用
		BigDecimal editMoney = new BigDecimal("0");//扣除手续费金额
		BigDecimal settlementPayWayPoundage = new BigDecimal("0");
		BigDecimal zero = new BigDecimal("0");
		if(payWayId != -1L){
			SettlementPayWay settlementPayWay = settlementPayWayDao.selectById(payWayId);
			settlementPayWayPoundage = settlementPayWay.getPoundage();//支付方式手续费用
		}
		if(settlementPayWayPoundage.compareTo(zero)==1 || money.compareTo(zero)==1 || totalMoney.compareTo(zero)==1){
			BigDecimal poundageProportion = money.divide(totalMoney);//订单手续费用比例
			BigDecimal totalMoneyPoundage = totalMoney.multiply(settlementPayWayPoundage);
			accountPoundage = totalMoneyPoundage.multiply(poundageProportion).setScale(2,BigDecimal.ROUND_HALF_UP);
			editMoney = money.subtract(accountPoundage);
		}

		//生成流水号
		String sn=IdGen.snowflake().toString();

		//记录业务表
		BusinAccountMapping businAccountMapping = new BusinAccountMapping();
		businAccountMapping.setBusinessId(id);
		businAccountMapping.setSerialNumber(sn);
		if(type.equals(9)){
			businAccountMapping.setBusinessType("10");//类型（10商品支付，18会员提现，20平台提现，22商品退款，30商品结算）
		}
		SysVariable sysVariable = new SysVariable();
		sysVariable.setName("service_charge_status");//系统变量键
		businAccountMapping.setServiceChargeStatus(sysVariableDao.selectByWhere(null, new Wrapper(sysVariable)).get(0).getValue());
		businAccountMappingService.insertSelective(businAccountMapping);

		//记录平台手续费账户流水
		AccountPlatformSn poundageApsn=new AccountPlatformSn();
		poundageApsn.setApId(ACCOUNT_PLATFORM_POUNDAGE);//平台手续费账户的ID是13
		poundageApsn.setSerialNumber(sn);//流水号
		String remarks1 = "";
		if(type.equals(9)){
			remarks1 = FYUtils.fyParams("支付商品订单详情1",id.toString(),payWayNameString,accountPoundage.toString());
		}
		poundageApsn.setPayRemarks(remarks1);//备注
		poundageApsn.setIncomeMoney(accountPoundage);//收入金额
		accountPlatformSnService.insertSelective(poundageApsn);//平台账户流水

		//平台未结账户的未结算金额增加
		AccountPlatform ap1=new AccountPlatform();
		if(type.equals(9) || type.equals(10) || type.equals(11)) {
			ap1.setApId(ACCOUNT_PLATFORM_TEMP);//未结算账户的ID是10
		}
		ap1.setOwnMoney(money.add(couponMoney));//支付的金额
		accountPlatformService.updateByIdSelectiveAtom(ap1);// 原子累加累减账户中的金额

		//记录平台未结账户流水
		AccountPlatformSn apsn1=new AccountPlatformSn();
		if(type.equals(9) || type.equals(10) || type.equals(11)) {
			apsn1.setApId(ACCOUNT_PLATFORM_TEMP);//未结算账户的ID是10
		}
		apsn1.setSerialNumber(sn);//流水号
		String remarks2 = "";
		if(type.equals(9)){
			remarks2 = FYUtils.fyParams("支付商品订单详情2",id.toString(),payWayNameString,accountPoundage.toString(),money.toString());
		}
		apsn1.setPayRemarks(remarks2);//备注
		apsn1.setIncomeMoney(money.add(couponMoney));//收入金额
		accountPlatformSnService.insertSelective(apsn1);//平台账户流水

		//平台补贴账户的金额增加
		AccountPlatform ap2=new AccountPlatform();
		ap2.setApId(ACCOUNT_PLATFORM_SUBSIDY);//补贴账户的ID是14
		ap2.setOwnMoney(accountPoundage);//补贴的金额
		accountPlatformService.updateByIdSelectiveAtom(ap2);// 原子累加累减账户中的金额

		//记录补贴账户流水
		AccountPlatformSn apsn2=new AccountPlatformSn();
		apsn2.setApId(ACCOUNT_PLATFORM_SUBSIDY);//补贴账户的ID是14
		apsn2.setSerialNumber(sn);//流水号
		String remarks3 = "";
		if(type.equals(9)){
			remarks3 = FYUtils.fyParams("支付商品订单详情3",id.toString(),payWayNameString,accountPoundage.toString());
		}
		apsn2.setPayRemarks(remarks3);//备注
		apsn2.setIncomeMoney(accountPoundage);//收入金额
		accountPlatformSnService.insertSelective(apsn2);//平台账户流水

		//记录第三方账户资金流水
		AccountThirdpartySn atpsn=new AccountThirdpartySn();
		atpsn.setSerialNumber(sn);
		atpsn.setMoneyFlowType("1");//资金流类型（1.付款、2.提现、3.充值、4.退款）
		atpsn.setMoney(editMoney);//交易金额
		atpsn.setPayWayId(payWayId);//支付方式id
		atpsn.setPayWayName(payWayName);//支付方式名称
		atpsn.setOuterTradeNo(outerTradeNo);//外部交易记录号
		String remarks4 = "";
		if(type.equals(9)){
			remarks4 = FYUtils.fyParams("支付商品订单详情4",id.toString(),payWayNameString,accountPoundage.toString(),editMoney.toString());
		}
		atpsn.setTradeRemarks(remarks4);//交易备注
		accountThirdpartySnService.insertSelective(atpsn);//第三方账户资金流水

		return null;
	}

	/**
	 * 会员申请提现、冻结资金 （原子操作，防止超额提现）
	 * 商家申请提现时，商家账户/服务账户中的 账户余额减少，冻结金额增加；（资金被冻结了）
	 *
	 * @param accountId 会员账户的ID
	 * @param money 金额
	 *
	 * @return 1表示成功，抛出异常表示失败
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object withdrawApplyUser(Long accountId,BigDecimal money) {
		if(accountId==null) {
			throw new NullPointerException("会员账户的ID为null");
		}
		if(money==null) {
			throw new NullPointerException("申请提现金额为null");
		}
		BigDecimal b2 = new BigDecimal("0");
		int res = money.compareTo(b2);//compareTo()比较，不考虑精确度，返回值0表示相等，-1表示小于，1表示大于。
		if(res!=1) {
			throw new RuntimeException("申请提现金额应大于0，申请提现金额="+money);
		}

		//检查账户是否存在
		AccountUser accountUser=accountUserService.selectById(accountId);
		if(accountUser==null) {
			throw new RuntimeException("会员账户不存在，accountId="+accountId);
		}

		// 会员申请提现，原子操作，防止超额提现
		// return 1表示成功，0表示未找到账户或超额提现被拦截
		int rs=accountUserService.withdrawApplyUser(accountId,money);
		if(rs==0) {
			throw new RuntimeException("超额提现被拦截，申请提现金额="+money);
		}
		return rs;
	}

	/**
	 * 平台申请提现 、冻结资金 （原子操作，防止超额提现）
	 * 平台申请提现时，平台营收账户中的 账户余额减少，冻结金额增加；（资金被冻结了）
	 *
	 * @param accountId 平台账户的ID，目前无用，请传入null，目前固定从平台营收账户提现。
	 * @param money 金额
	 *
	 * @return 1表示成功，抛出异常表示失败
	 *
	 * 超额提现会抛出异常
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object withdrawApplyPlatform(Long accountId,BigDecimal money) {
		if(money==null) {
			throw new NullPointerException("申请提现金额为null");
		}
		BigDecimal b2 = new BigDecimal("0");
		int res = money.compareTo(b2);//compareTo()比较，不考虑精确度，返回值0表示相等，-1表示小于，1表示大于。
		if(res!=1) {
			throw new RuntimeException("申请提现金额应大于0，申请提现金额="+money);
		}

		// 平台申请提现，原子操作，防止超额提现
		// return 1表示成功，0表示未找到账户或超额提现被拦截
		Long apId=ACCOUNT_PLATFORM_REVENUE;//平台营收账户的ID是11
		int rs=accountPlatformService.withdrawApplyPlatform(apId,money);
		if(rs==0) {
			throw new RuntimeException("超额提现被拦截，申请提现金额="+money);
		}
		return rs;
	}

	/**
	 * 会员执行提现（把钱提走了）
	 *
	 * 审核会员的提现申请：管理员审核同意后，
	 * 第三方支付公司备付金账户减少，记入pay_thirdparty_sn(6 第三方账户资金流水表)；
	 * 商家账户/服务账户中的余额不变，冻结金额减少，记入pay_user_account(1 会员账户表)、pay_user_account_sn(2 会员账户流水表)；
	 * 以上两个流水表使用同一个业务流水号；
	 *
	 * @param id 业务id
	 * @param accountId 会员账户的ID
	 * @param money 金额
	 * @param payWayId 支付方式id
	 * @param payWayName 支付方式名称
	 * @param outerTradeNo 外部交易记录号
	 *
	 * @return 1表示成功，抛出异常表示失败
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object withdrawExecuteUser(Long id,Long accountId,BigDecimal money,
									  Long payWayId,String payWayName,String outerTradeNo) {
		//检查账户是否存在
		AccountUser accountUser=accountUserService.selectById(accountId);
		if(accountUser==null) {
			throw new RuntimeException("会员账户不存在，会员账户ID="+accountId);
		}

		//安全检查
		if(money==null) {
			throw new NullPointerException("申请提现金额为null");
		}
		BigDecimal b2 = new BigDecimal("0");
		int res = money.compareTo(b2);//compareTo()比较，不考虑精确度，返回值0表示相等，-1表示小于，1表示大于。
		if(res!=1) {
			throw new RuntimeException("申请提现金额应大于0，申请提现金额="+money);
		}

		//生成流水号
		String sn=IdGen.snowflake().toString();

		//商家账户/服务账户中的余额不变，冻结金额减少
		int rs=accountUserService.withdrawExecuteUser(accountId,money);
		if(rs==0) {
			throw new RuntimeException("超额提现被拦截，提现金额="+money);
		}

		//记录业务表
		BusinAccountMapping businAccountMapping = new BusinAccountMapping();
		businAccountMapping.setBusinessId(id);
		businAccountMapping.setSerialNumber(sn);
		businAccountMapping.setBusinessType("18");//类型（10商品支付，18会员提现，20平台提现，22商品退款，30商品结算）
		SysVariable sysVariable = new SysVariable();
		sysVariable.setName("service_charge_status");//系统变量键
		businAccountMapping.setServiceChargeStatus(sysVariableDao.selectByWhere(null, new Wrapper(sysVariable)).get(0).getValue());
		businAccountMappingService.insertSelective(businAccountMapping);

		//记录会员账户流水
		AccountUserSn ausn=new AccountUserSn();
		ausn.setAuId(accountId);//会员账户id
		ausn.setSerialNumber(sn);//流水号
		ausn.setPayRemarks(FYUtils.fyParams("会员提现元",money.toString()));//备注
		ausn.setExpensesMoney(money);//支出金额
		accountUserSnService.insertSelective(ausn);//平台账户流水

		//记录第三方账户资金流水
		AccountThirdpartySn atpsn=new AccountThirdpartySn();
		atpsn.setSerialNumber(sn);
		atpsn.setMoneyFlowType("2");//资金流类型（1.付款、2.提现、3.充值、4.退款）
		atpsn.setMoney(money);//交易金额
		atpsn.setPayWayId(payWayId);//支付方式id
		atpsn.setPayWayName(payWayName);//支付方式名称
		atpsn.setOuterTradeNo(outerTradeNo);//外部交易记录号
		atpsn.setTradeRemarks(FYUtils.fyParams("会员提现元",money.toString()));//交易备注
		accountThirdpartySnService.insertSelective(atpsn);//第三方账户资金流水

		return rs;
	}

	/**
	 * 平台执行提现（把钱提走了）
	 *
	 * 审核平台的提现申请：管理员审核同意后，
	 * 第三方支付公司备付金账户减少，记入pay_thirdparty_sn(6 第三方账户资金流水表)；
	 * 平台营收账户中的余额不变，冻结金额减少，记入pay_platform_account(3 平台账户表)、pay_platform_account_sn(4 平台账户流水表)；
	 * 以上两个流水表使用同一个业务流水号；
	 *
	 * @param id 业务id
	 * @param accountId 平台账户的ID，目前无用，请传入null，目前固定从平台营收账户提现。
	 * @param money 金额
	 * @param payWayId 支付方式id
	 * @param payWayName 支付方式名称
	 * @param outerTradeNo 外部交易记录号
	 *
	 * @return 1表示成功，抛出异常表示失败
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object withdrawExecutePlatform(Long id,Long accountId,BigDecimal money,
										  Long payWayId,String payWayName,String outerTradeNo) {

		//安全检查
		if(money==null) {
			throw new NullPointerException("申请提现金额为null");
		}
		BigDecimal b2 = new BigDecimal("0");
		int res = money.compareTo(b2);//compareTo()比较，不考虑精确度，返回值0表示相等，-1表示小于，1表示大于。
		if(res!=1) {
			throw new RuntimeException("申请提现金额应大于0，申请提现金额="+money);
		}

		//生成流水号
		String sn=IdGen.snowflake().toString();

		//平台营收账户中的余额不变，冻结金额减少，
		Long apId=ACCOUNT_PLATFORM_REVENUE;//平台营收账户的ID是11
		int rs=accountPlatformService.withdrawExecutePlatform(apId,money);
		if(rs==0) {
			throw new RuntimeException("超额提现被拦截，提现金额="+money);
		}

		//记录业务表
		BusinAccountMapping businAccountMapping = new BusinAccountMapping();
		businAccountMapping.setBusinessId(id);
		businAccountMapping.setSerialNumber(sn);
		businAccountMapping.setBusinessType("20");//类型（10商品支付，18会员提现，20平台提现，22商品退款，30商品结算）
		SysVariable sysVariable = new SysVariable();
		sysVariable.setName("service_charge_status");//系统变量键
		businAccountMapping.setServiceChargeStatus(sysVariableDao.selectByWhere(null, new Wrapper(sysVariable)).get(0).getValue());
		businAccountMappingService.insertSelective(businAccountMapping);

		//记录平台账户流水
		AccountPlatformSn apsn=new AccountPlatformSn();
		apsn.setApId(ACCOUNT_PLATFORM_REVENUE);//平台营收账户的ID是11
		apsn.setSerialNumber(sn);//流水号
		apsn.setPayRemarks(FYUtils.fyParams("会员提现元",money.toString()));//备注
		apsn.setExpensesMoney(money);//支出金额
		accountPlatformSnService.insertSelective(apsn);//平台账户流水

		//记录第三方账户资金流水
		AccountThirdpartySn atpsn=new AccountThirdpartySn();
		atpsn.setSerialNumber(sn);
		atpsn.setMoneyFlowType("2");//资金流类型（1.付款、2.提现、3.充值、4.退款）
		atpsn.setMoney(money);//交易金额
		atpsn.setPayWayId(payWayId);//支付方式id
		atpsn.setPayWayName(payWayName);//支付方式名称
		atpsn.setOuterTradeNo(outerTradeNo);//外部交易记录号
		atpsn.setTradeRemarks(FYUtils.fyParams("会员提现元",money.toString()));//交易备注
		accountThirdpartySnService.insertSelective(atpsn);//第三方账户资金流水

		return rs;
	}

	/**
	 * 拒绝会员提现（资金解冻了）
	 * 管理员审核拒绝后，商家账户/服务账户中的冻结金额减少，余额增加；
	 * @param accountId 会员账户的ID
	 * @param money 金额
	 * @return 1表示成功，抛出异常表示失败
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object withdrawRefuseUser(Long accountId,BigDecimal money) {
		if(accountId==null) {
			throw new NullPointerException("会员账户的IDnull");
		}
		if(money==null) {
			throw new NullPointerException("申请解冻金额为null");
		}
		BigDecimal b2 = new BigDecimal("0");
		int res = money.compareTo(b2);//compareTo()比较，不考虑精确度，返回值0表示相等，-1表示小于，1表示大于。
		if(res!=1) {
			throw new RuntimeException("申请解冻金额应大于0，申请解冻金额="+money);
		}

		//检查账户是否存在
		AccountUser accountUser=accountUserService.selectById(accountId);
		if(accountUser==null) {
			throw new RuntimeException("会员账户不存在，accountId="+accountId);
		}

		// 拒绝会员提现（资金解冻了），原子操作，防止超额解冻
		// return 1表示成功，0表示未找到账户或超额解冻被拦截
		int rs=accountUserService.withdrawRefuseUser(accountId,money);
		if(rs==0) {
			throw new RuntimeException("超额解冻被拦截，申请解冻金额="+money);
		}
		return rs;
	}

	/**
	 * 拒绝平台提现（资金解冻了）
	 * 管理员审核拒绝后，平台营收账户中的冻结金额减少，余额增加；
	 * @param accountId 会员账户的ID
	 * @param money 金额
	 * @return 1表示成功，抛出异常表示失败
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object withdrawRefusePlatform(Long accountId,BigDecimal money) {
		if(money==null) {
			throw new NullPointerException("申请解冻金额为null");
		}
		BigDecimal b2 = new BigDecimal("0");
		int res = money.compareTo(b2);//compareTo()比较，不考虑精确度，返回值0表示相等，-1表示小于，1表示大于。
		if(res!=1) {
			throw new RuntimeException("申请解冻金额应大于0，申请解冻金额="+money);
		}

		// 拒绝平台提现（资金解冻了），原子操作，防止超额解冻
		// return 1表示成功，0表示未找到账户或超额解冻被拦截
		Long apId=ACCOUNT_PLATFORM_REVENUE;//平台营收账户的ID是11
		int rs=accountPlatformService.withdrawRefusePlatform(apId,money);
		if(rs==0) {
			throw new RuntimeException("超额解冻被拦截，申请解冻金额="+money);
		}
		return rs;
	}

	/**
	 * 退款，商品退款
	 *
	 * 如果是买家使用的是微信或支付宝付款的商品，并且还未结算，退款时，钱会原路退回。
	 * 平台未结账户 的金额减少，记入pay_platform_account(3 平台账户表)、pay_platform_account_sn(4 平台账户流水表)；
	 * 第三方支付公司备付金账户减少，记入pay_thirdparty_sn(6 第三方账户资金流水表)；
	 * 以上两个流水表使用同一个业务流水号；
	 *
	 * @param id 业务id（订单详情id）
	 * @param money 退款的金额(实际支付金额)
	 * @param couponMoney 使用优惠券金额
	 * @param payWayId 支付方式id
	 * @param payWayName 支付方式名称
	 * @param outerTradeNo 外部交易记录号
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object refundProduct(Long id,BigDecimal money,BigDecimal couponMoney,
								Long payWayId,String payWayName,String outerTradeNo) {
		Integer type=9;//type 支付类型，9支付商品费用
		return refundBase(id,type,money,couponMoney,payWayId,payWayName,outerTradeNo);
	}


	/**
	 * 退款
	 * 商品退款（从平台未结账户中退款）
	 * 服务保证金退款（从平台保证金账户中退款）
	 *
	 * 如果是买家使用的是微信或支付宝付款的商品，并且还未结算，退款时，钱会原路退回。
	 * 平台未结账户的金额减少，记入pay_platform_account(3 平台账户表)、pay_platform_account_sn(4 平台账户流水表)；
	 * 第三方支付公司备付金账户减少，记入pay_thirdparty_sn(6 第三方账户资金流水表)；
	 * 以上两个流水表使用同一个业务流水号；
	 *
	 * @param id 业务id
	 * @param type 退款类型，9商品费用退款
	 * @param money 退款的金额
	 * @param couponMoney 使用优惠券金额（代金券为实际金额，满减券为0）
	 * @param payWayId 支付方式id
	 * @param payWayName 支付方式名称
	 * @param outerTradeNo 外部交易记录号
	 * @return
	 */
	private Object refundBase(Long id,Integer type,BigDecimal money,BigDecimal couponMoney,
							  Long payWayId,String payWayName,String outerTradeNo) {
		//错误判断
		if(!type.equals(9)){
			throw new RuntimeException("支付类型type传入错误，type="+type);
		}
		if(id==null){
			throw new NullPointerException("业务id传入不能为空");
		}
		if(payWayId==null){
			throw new NullPointerException("支付方式payWayId传入不能为空");
		}
		String payName = "";
		if("-1".equals(payWayName)){
			payName = FYUtils.fyParams("线下支付");
		}
		//生成流水号
		String sn=IdGen.snowflake().toString();

		//记录业务表
		BusinAccountMapping businAccountMapping = new BusinAccountMapping();
		businAccountMapping.setBusinessId(id);
		businAccountMapping.setSerialNumber(sn);
		if(type.equals(9)){
			businAccountMapping.setBusinessType("22");//类型（10商品支付，18会员提现，20平台提现，22商品退款，30商品结算）
		}
		SysVariable sysVariable = new SysVariable();
		sysVariable.setName("service_charge_status");//系统变量键
		businAccountMapping.setServiceChargeStatus(sysVariableDao.selectByWhere(null, new Wrapper(sysVariable)).get(0).getValue());
		businAccountMappingService.insertSelective(businAccountMapping);

		//获取支付的手续费
		BigDecimal accountPoundage = new BigDecimal(0);
		BusinAccountMapping bap = new BusinAccountMapping();
		bap.setBusinessId(id);
		if(type.equals(9)){
			bap.setBusinessType("10");//类型（10商品支付，18会员提现，20平台提现，22商品退款，30商品结算）
		}
		List<BusinAccountMapping> businAccountMappingList = businAccountMappingService.selectByWhere(new Wrapper(bap));
		if(!businAccountMappingList.isEmpty()){
			AccountPlatformSn apfs = new AccountPlatformSn();
			apfs.setSerialNumber(businAccountMappingList.get(0).getSerialNumber());
			List<AccountPlatformSn> accountPlatformSnList = accountPlatformSnService.selectByWhere(new Wrapper(apfs));
			if(!accountPlatformSnList.isEmpty()){
				accountPoundage = accountPlatformSnList.get(0).getIncomeMoney();
			}
		}

		//未结算账户需要减少的钱=退单金额+优惠券的金额
		BigDecimal unsettledReduceMoney = money.add(couponMoney);
		//去除手续费用的退单金额
		BigDecimal removalFeeMoney = money.subtract(accountPoundage);

		//记录平台手续费账户流水
		AccountPlatformSn poundageApsn=new AccountPlatformSn();
		poundageApsn.setApId(ACCOUNT_PLATFORM_POUNDAGE);//平台手续费账户的ID是13
		poundageApsn.setSerialNumber(sn);//流水号
		String remarks1 = "";
		if(type.equals(9)){
			remarks1 = FYUtils.fyParams("退款商品订单详情1",id.toString(),payName,accountPoundage.toString());
		}
		poundageApsn.setPayRemarks(remarks1);//备注
		poundageApsn.setExpensesMoney(accountPoundage);//支出金额
		accountPlatformSnService.insertSelective(poundageApsn);//平台账户流水

		//平台未结账户的金额减少
		AccountPlatform ap1=new AccountPlatform();
		if(type.equals(9) || type.equals(10) || type.equals(11)) {
			ap1.setApId(ACCOUNT_PLATFORM_TEMP);//未结算账户的ID是10
		}
		ap1.setOwnMoney(unsettledReduceMoney.negate());//减少金额,转成负数
		accountPlatformService.updateByIdSelectiveAtom(ap1);// 原子累加累减账户中的金额

		//记录未结账户账户的流水
		AccountPlatformSn apsn=new AccountPlatformSn();
		if(type.equals(9) || type.equals(10) || type.equals(11)) {
			apsn.setApId(ACCOUNT_PLATFORM_TEMP);//未结算账户的ID是10
		}
		apsn.setSerialNumber(sn);//流水号
		String remarks2 = "";
		if(type.equals(9)){
			remarks2 = FYUtils.fyParams("退款商品订单详情2",id.toString(),payName,money.toString(),couponMoney.toString(),unsettledReduceMoney.toString());
		}
		apsn.setPayRemarks(remarks2);//备注
		apsn.setExpensesMoney(unsettledReduceMoney);//支出金额
		accountPlatformSnService.insertSelective(apsn);//平台账户流水

		//平台补贴账户减少
		AccountPlatform ap2=new AccountPlatform();
		ap2.setApId(ACCOUNT_PLATFORM_SUBSIDY);//补贴账户的ID是14
		ap2.setOwnMoney(accountPoundage.negate());//减少金额，变负数
		accountPlatformService.updateByIdSelectiveAtom(ap2);// 原子累加累减账户中的金额

		//记录补贴账户账户流水
		AccountPlatformSn apsn2=new AccountPlatformSn();
		apsn2.setApId(ACCOUNT_PLATFORM_SUBSIDY);//平台补贴账户的ID是14，类型是4
		apsn2.setSerialNumber(sn);//流水号
		String remarks3 = "";
		if(type.equals(9)){
			remarks3 = FYUtils.fyParams("退款商品订单详情3",id.toString(),payName,money.toString(),couponMoney.toString());
		}
		apsn2.setPayRemarks(remarks3);//备注
		apsn2.setExpensesMoney(accountPoundage);//支出金额
		accountPlatformSnService.insertSelective(apsn2);//平台账户流水

		//记录第三方账户资金流水
		AccountThirdpartySn atpsn=new AccountThirdpartySn();
		atpsn.setSerialNumber(sn);
		atpsn.setMoneyFlowType("4");//资金流类型（1.付款、2.提现、3.充值、4.退款）
		atpsn.setMoney(money);//交易金额
		atpsn.setPayWayId(payWayId);//支付方式id
		atpsn.setPayWayName(payWayName);//支付方式名称
		atpsn.setOuterTradeNo(outerTradeNo);//外部交易记录号
		String remarks4 = "";
		if(type.equals(9)){
			remarks4 = FYUtils.fyParams("退款商品订单详情4",id.toString(),payName,money.toString(),couponMoney.toString(),removalFeeMoney.toString());
		}
		atpsn.setTradeRemarks(remarks4);//交易备注
		accountThirdpartySnService.insertSelective(atpsn);//第三方账户资金流水

		return null;
	}

	/**
	 * 商品结算
	 * 平台未结账户中的未结算金额减少，记入pay_platform_account(3 平台账户表)、pay_platform_account_sn(4 平台账户流水表)；
	 * 商家账户中的账户余额增加，记入pay_user_account(1 会员账户表)、pay_user_account_sn(2 会员账户流水表)；
	 * 平台营收账户中的余额增加，记入pay_platform_account(3 平台账户表)、pay_platform_account_sn(4 平台账户流水表)；
	 * 以上三个流水表使用同一个业务流水号。
	 *
	 * @param id 业务id
	 * @param accountId 会员账户ID
	 * @param money1 订单总额（包括优惠券）
	 * @param money2 商家承担的手续费用
	 * @param money3 结算给平台的佣金，如果没有佣金请输入0元
	 * @param money4 补贴金额
	 *
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object settlementProduct(Long id,Long accountId,BigDecimal money1,
									BigDecimal money2,BigDecimal money3,BigDecimal money4) {
		Integer type = 10;
		return settlement(id,type,accountId,money1,money2,money3,money4);
	}

	/**
	 * 结算
	 * 平台未结账户中的未结算金额减少，记入pay_platform_account(3 平台账户表)、pay_platform_account_sn(4 平台账户流水表)；
	 * 商家账户中的账户余额增加，记入pay_user_account(1 会员账户表)、pay_user_account_sn(2 会员账户流水表)；
	 * 平台营收账户中的余额增加，记入pay_platform_account(3 平台账户表)、pay_platform_account_sn(4 平台账户流水表)；
	 * 以上三个流水表使用同一个业务流水号。
	 *
	 * @param id 业务id
	 * @param type 结算类型，10商品结算
	 * @param accountId 会员账户ID
	 * @param money1 订单支付总金额\服务支付总金额（包括优惠券）
	 * @param money2 商家承担的手续费用，如果商家没有承担请输入0元
	 * @param money3 结算给平台的佣金，如果没有佣金请输入0元
	 * @param money4 补贴金额
	 *
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object settlement(Long id,Integer type,Long accountId,BigDecimal money1,
							 BigDecimal money2,BigDecimal money3,BigDecimal money4) {

		//检查账户是否存在
		AccountUser accountUser=accountUserService.selectById(accountId);
		if(accountUser==null) {
			throw new RuntimeException("会员账户不存在，会员账户ID="+accountId);
		}

		//安全检查
		if(money1==null || money2==null || money3==null || money4==null) {
			throw new NullPointerException("结算金额为null");
		}
//		BigDecimal b2 = new BigDecimal("0");
//		int res = money1.compareTo(b2);//compareTo()比较，不考虑精确度，返回值0表示相等，-1表示小于，1表示大于。
//		if(res!=1) {
//			throw new RuntimeException("结算金额应大于0，结算金额="+money1);
//		}

		//生成流水号
		String sn=IdGen.snowflake().toString();

		//记录业务表
		BusinAccountMapping businAccountMapping = new BusinAccountMapping();
		businAccountMapping.setBusinessId(id);
		businAccountMapping.setSerialNumber(sn);
		if(type.equals(10)){
			businAccountMapping.setBusinessType("30");//类型（10商品支付，18会员提现，20平台提现，22商品退款，30商品结算）
		}
		SysVariable sysVariable = new SysVariable();
		sysVariable.setName("service_charge_status");//系统变量键
		businAccountMapping.setServiceChargeStatus(sysVariableDao.selectByWhere(null, new Wrapper(sysVariable)).get(0).getValue());
		businAccountMappingService.insertSelective(businAccountMapping);

		//计算商家结算金额
		BigDecimal settlementAmount = money1.subtract(money2).subtract(money3);

		//平台未结账户的金额减少
		AccountPlatform ap1=new AccountPlatform();
		ap1.setApId(ACCOUNT_PLATFORM_TEMP);//未结算账户的ID是10
		ap1.setOwnMoney(money1.negate());//减少金额,转成负数
		accountPlatformService.updateByIdSelectiveAtom(ap1);// 原子累加累减账户中的金额

		//记录平台未结账户流水
		AccountPlatformSn apsn1=new AccountPlatformSn();
		apsn1.setApId(ACCOUNT_PLATFORM_TEMP);//未结算账户的ID是10
		apsn1.setSerialNumber(sn);//流水号
		String remarks1 = "";
		if(type.equals(10)){
			remarks1 = FYUtils.fyParams("商品结算1",id.toString(),settlementAmount.toString(),money2.toString(),money3.toString(),money4.toString(),money1.toString());
		}
		apsn1.setPayRemarks(remarks1);//备注
		apsn1.setExpensesMoney(money1);//支出金额
		accountPlatformSnService.insertSelective(apsn1);//平台账户流水

		//平台补贴账户减少
		AccountPlatform ap2=new AccountPlatform();
		ap2.setApId(ACCOUNT_PLATFORM_SUBSIDY);//补贴账户的ID是14
		ap2.setOwnMoney(money4.negate());//减少金额，变负数
		accountPlatformService.updateByIdSelectiveAtom(ap2);// 原子累加累减账户中的金额

		//记录平台补贴账户流水
		AccountPlatformSn apsn2=new AccountPlatformSn();
		apsn2.setApId(ACCOUNT_PLATFORM_SUBSIDY);//补贴账户的ID是14
		apsn2.setSerialNumber(sn);//流水号
		String remarks2 = "";
		if(type.equals(10)){
			remarks2 = FYUtils.fyParams("商品结算2",id.toString(),settlementAmount.toString(),money2.toString(),money3.toString(),money4.toString(),money4.toString());
		}
		apsn2.setPayRemarks(remarks2);//备注
		apsn2.setIncomeMoney(money4);//收入金额
		accountPlatformSnService.insertSelective(apsn2);//平台账户流水

		//平台营收账户账户增加
		AccountPlatform ap3=new AccountPlatform();
		ap3.setApId(ACCOUNT_PLATFORM_REVENUE);//营收账户的ID是11
		ap3.setOwnMoney(money3);//增加金额
		accountPlatformService.updateByIdSelectiveAtom(ap3);// 原子累加累减账户中的金额

		//记录平台营收账户流水
		AccountPlatformSn apsn3=new AccountPlatformSn();
		apsn3.setApId(ACCOUNT_PLATFORM_REVENUE);//营收账户的ID是11
		apsn3.setSerialNumber(sn);//流水号
		String remarks3 = "";
		if(type.equals(10)){
			remarks3 = FYUtils.fyParams("商品结算3",id.toString(),settlementAmount.toString(),money2.toString(),money3.toString(),money4.toString(),money3.toString());
		}
		apsn3.setPayRemarks(remarks3);//备注
		apsn3.setIncomeMoney(money3);//收入金额
		accountPlatformSnService.insertSelective(apsn3);//平台账户流水

		//会员账户中的账户余额增加
		AccountUser au=new AccountUser();
		au.setAuId(accountId);//会员账户ID
		au.setOwnMoney(settlementAmount);//累加金额
		accountUserService.updateByIdSelectiveAtom(au);// 原子累加累减账户中的金额

		//给会员账号流水的备注
		String remarks4 = "";
		if(type.equals(10)){
			remarks4 = FYUtils.fyParams("商品结算4",id.toString(),settlementAmount.toString(),money2.toString(),money3.toString(),money4.toString());
		}

		//记录会员账户流水(结算给商家多少钱)
		AccountUserSn ausn1=new AccountUserSn();
		ausn1.setAuId(accountId);//会员账户id
		ausn1.setSerialNumber(sn);//流水号
		ausn1.setPayRemarks(remarks4);//备注
		ausn1.setIncomeMoney(settlementAmount);//收入金额
		accountUserSnService.insertSelective(ausn1);//会员账户流水

		//记录会员账户流水(商家支付手续费用)
		AccountUserSn ausn2=new AccountUserSn();
		ausn2.setAuId(accountId);//会员账户id
		ausn2.setSerialNumber(sn);//流水号
		ausn2.setPayRemarks(remarks4);//备注
		ausn2.setExpensesMoney(money2);//支出手续费金额
		accountUserSnService.insertSelective(ausn2);//会员账户流水

		//记录会员账户流水(平台扣除营收费用)
		AccountUserSn ausn3=new AccountUserSn();
		ausn3.setAuId(accountId);//会员账户id
		ausn3.setSerialNumber(sn);//流水号
		ausn3.setPayRemarks(remarks4);//备注
		ausn3.setExpensesMoney(money3);//支出营收金额
		accountUserSnService.insertSelective(ausn3);//会员账户流水

		return null;
	}


	/**
	 * 调账
	 * 账户调整
	 *
	 * @param type 账户类型 1平台账户，2会员账户
	 * @param accountId 账户ID
	 * @param money 金额，可以是负数
	 * @param serialNumber 流水号
	 * @param tradeRemarks 备注
	 *
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Object accountAdjustment(Integer type,Long accountId,BigDecimal money,
									String serialNumber,String tradeRemarks) {
		//安全检查
		if(money==null  ) {
			throw new NullPointerException("调账金额为null");
		}

		BigDecimal b2 = new BigDecimal("0");
		int res = money.compareTo(b2);//compareTo()比较，不考虑精确度，返回值0表示相等，-1表示小于，1表示大于。
		if(res==0) {
			throw new NullPointerException("调账金额为0,无需调整");
		}

		if(type==1) {
			//检查账户是否存在
			AccountPlatform accountPlatform=accountPlatformService.selectById(accountId);
			if(accountPlatform==null) {
				throw new RuntimeException("平台账户不存在，平台账户ID="+accountId);
			}

			//平台某账户的金额减少，
			AccountPlatform ap=new AccountPlatform();
			ap.setApId(accountId);//平台账户ID
			ap.setOwnMoney(money);//累加累减金额
			accountPlatformService.updateByIdSelectiveAtom(ap);// 原子累加累减账户中的金额

			//记录平台某账户的流水
			AccountPlatformSn apsn=new AccountPlatformSn();
			apsn.setApId(accountId);//平台账户ID
			apsn.setSerialNumber(serialNumber);//流水号
			apsn.setPayRemarks(tradeRemarks);//备注

			if(res==-1) {
				//是负数
				apsn.setExpensesMoney(new BigDecimal(money.toString()).negate());//支出金额
			}else {
				//是正数
				apsn.setIncomeMoney(money);//收入金额
			}
			accountPlatformSnService.insertSelective(apsn);//平台账户流水
		}else if(type==2) {
			//检查账户是否存在
			AccountUser accountUser=accountUserService.selectById(accountId);
			if(accountUser==null) {
				throw new RuntimeException("会员账户不存在，会员账户ID="+accountId);
			}

			//某会员账户中的账户余额增加
			AccountUser au=new AccountUser();
			au.setAuId(accountId);//会员账户ID
			au.setOwnMoney(money);//累加累减金额
			accountUserService.updateByIdSelectiveAtom(au);// 原子累加累减账户中的金额

			//记录会员账户流水
			AccountUserSn ausn=new AccountUserSn();
			ausn.setAuId(accountId);//会员账户id
			ausn.setSerialNumber(serialNumber);//流水号
			ausn.setPayRemarks(tradeRemarks);//备注
			if(res==-1) {
				//是负数
				ausn.setExpensesMoney(new BigDecimal(money.toString()).negate());//支出金额
			}else {
				//是正数
				ausn.setIncomeMoney(money);//收入金额
			}
			accountUserSnService.insertSelective(ausn);//会员账户流水
		}else {
			throw new RuntimeException("type账户类型传入有错，取值范围：1平台账户，2会员账户");
		}
		return null;
	}

	/**
	 * 统计 平台现有资金总额
	 * 平台现有资金总额 = 平台未结账户金额 + 平台营收账户余额  + 平台营收账户冻结金额 + 每个会员账户余额 + 每个会员账户冻结金额 - 补贴账户 。（商家支付服务费用）
	 * 平台现有资金总额 = 平台未结账户金额 + 平台营收账户余额  + 平台营收账户冻结金额 + 每个会员账户余额 + 每个会员账户冻结金额 - 补贴账户 。（平台支付服务费用）
	 * 1、不支持买家账户，无需要计算买家账户
	 * 2、平台手续费账户中的钱不计算在内
	 * @return
	 */
	public BigDecimal countAllMoney(){
		//return new BigDecimal("0");
		return accountPlatformDao.countAllMoney();
	}

	/**
	 * 计算各个账户钱
	 * 10：未结算账户(临时)总额
	 * 11：营收账户(收入)总额
	 * 14：补贴账户(支出)总额
	 * 20：商家账户总额
	 */
	public void sumMoney(){
		System.out.println("未结账户金额："+accountPlatformDao.sumMoney("10"));
		System.out.println("营收账户金额："+accountPlatformDao.sumMoney("11"));
		System.out.println("补贴账户金额："+accountPlatformDao.sumMoney("14"));
		System.out.println("商家账户金额："+accountPlatformDao.sumMoney("20"));
	}

}