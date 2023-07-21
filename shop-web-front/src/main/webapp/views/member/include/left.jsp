<%@ page contentType="text/html; charset=UTF-8" %>
<div class="main-menu box mt20">
	<dl>
		<dt><i class="sui-icon icon-tb-unfold pull-right"></i><i class="sui-icon icon-tb-refund"></i> ${fns:fy('交易中心')}</dt>
		<dd><a style="${menu3id eq 'tradrOrder'?'color: #e45050;':''}" href="${ctxm}/trade/tradeOrder/list.htm">• ${fns:fy('商品订单')}</a></dd>
		<dd><a style="${menu3id eq 'tradeComment'?'color: #e45050;':''}" href="${ctxm}/trade/tradeComment/list.htm">• ${fns:fy('交易评价')}</a></dd>
		<dd><a style="${menu3id eq 'tradeCart'?'color: #e45050;':''}" target="_blank" href="${ctxf}/trade/cart/list.htm">• ${fns:fy('购物车')}</a></dd>
	</dl>
	<dl>
		<dt><i class="sui-icon icon-tb-unfold pull-right"></i><i class="sui-icon icon-tb-favor"></i> ${fns:fy('收藏中心')}</dt>
		<dd><a style="${menu3id eq 'collectionProduct'?'color: #e45050;':''}" href="${ctxm}/collect/memberCollectionProduct/list.htm">• ${fns:fy('商品收藏')}</a></dd>
		<dd><a style="${menu3id eq 'collectionStore'?'color: #e45050;':''}" href="${ctxm}/collect/memberCollectionStore/list.htm">• ${fns:fy('店铺收藏')}</a></dd>
		<!-- <dd><a href="javascript:;">• ${fns:fy('会员中心')}我的足迹(不做)</a></dd> -->
	</dl>
	<dl>
		<dt><i class="sui-icon icon-tb-unfold pull-right"></i><i class="sui-icon icon-tb-emoji"></i> ${fns:fy('客户服务')}</dt>
		<dd><a style="${menu3id eq 'returnOrder'?'color: #e45050;':''}" href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm">• ${fns:fy('退款及退货')}</a></dd>
		<dd><a style="${menu3id eq 'complaint'?'color: #e45050;':''}" href="${ctxm}/trade/tradeComplaint/list.htm">• ${fns:fy('交易投诉')}</a></dd>
		<dd><a style="${menu3id eq 'consultation'?'color: #e45050;':''}" href="${ctxm}/trade/consultation/list.htm">• ${fns:fy('商品咨询')}</a></dd>
	</dl>		
	<dl>
		<dt><i class="sui-icon icon-tb-unfold pull-right"></i><i class="sui-icon icon-tb-my"></i> ${fns:fy('会员资料')}</dt>
		<dd><a style="${menu3id eq 'userMember'?'color: #e45050;':''}" href="${ctxm}/user/userMember/save1.htm">• ${fns:fy('账户信息')}</a></dd>
		<dd><a href="${ctxsso}/index.htm">• ${fns:fy('账户安全')}</a></dd>
		<dd><a style="${menu3id eq 'userAddress'?'color: #e45050;':''}" href="${ctxm}/user/memberAddress/list.htm">• ${fns:fy('收货地址')}</a></dd>
		<dd><a style="${menu3id eq 'userMessage'?'color: #e45050;':''}" href="${ctxm}/user/memberMessage/list.htm">• ${fns:fy('我的消息')}</a></dd>
		<!-- <dd><a href="javascript:;">• ${fns:fy('第三方帐号登录')}</a></dd> -->
	</dl>
	<%-- <dl>
		<dt><i class="sui-icon icon-tb-unfold pull-right"></i><i class="sui-icon icon-tb-pay"></i> ${fns:fy('财务中心')}</dt>
		<dd><a style="${menu3id eq 'preDeposit'?'color: #e45050;':''}" href="${ctxm}/finance/financePreDeposit/list.htm">• ${fns:fy('账户余额')}</a></dd>
		<dd><a style="${menu3id eq 'recharge'?'color: #e45050;':''}" href="${ctxm}/finance/financeRecharge/save1.htm">• ${fns:fy('账户充值')}</a></dd>
		<dd><a href="${ctxm}/finance/financeWithdrawals/save1.htm">• ${fns:fy('账户提现')}</a></dd>
	</dl> --%>
	<dl>
		<dt><i class="sui-icon icon-tb-unfold pull-right"></i><i class="sui-icon icon-tb-pay"></i>• ${fns:fy('用户升级')}</dt>
		<dd><a href="${ctxs}/store/storeEnterAuth/auth4.htm">• ${fns:fy('升级为供应商')}</a></dd>
		<%--<dd><a style="${menu3id eq 'openDoorStore'?'color: #e45050;':''}" href="${ctxm}/upgrade/openDoorStore/save1.htm">• ${fns:fy('升级为服务门店')}</a></dd>
		<dd><a style="${menu3id eq 'purchaser'?'color: #e45050;':''}" href="${ctxm}/upgrade/upgradePurchaser/save1.htm">• ${fns:fy('升级为采购商')}</a></dd>
		<dd><a style="${menu3id eq 'carOwner'?'color: #e45050;':''}" href="${ctxm}/upgrade/carOwner/save1.htm">• ${fns:fy('完善车主信息')}</a></dd>--%>
	</dl>		
</div>
<!--main-menu end-->
