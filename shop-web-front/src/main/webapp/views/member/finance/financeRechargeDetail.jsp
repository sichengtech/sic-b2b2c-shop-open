<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta name="decorator" content="member"/>
<style type="text/css">
	.right {text-align: right!important;}
	#rechargeDetailTable td{line-height: 30px;}
	.sui-table th, .sui-table td{border-top: 1px dashed #e5e5e5;}
</style>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl class="order-tabs">
			<dt>
				<div class="position"><span>当前位置:</span><span>会员中心</span> > 财务中心 > 账户充值</div>
				<i class="sui-icon icon-tb-list"></i> 账户充值
			</dt>
			<ul class="sui-nav nav-tabs nav-large">
				<li class=""><a href="${ctxm}/finance/financeRecharge/save1.htm">在线充值</a></li>
				<li class=""><a href="${ctxm}/finance/financeRecharge/list.htm">充值记录</a></li>
				<li class="active"><a href="javascript:;">充值详情</a></li>
			</ul>
			<dd class="tab-content myfinancial">
				<div id="recharge_list" class="my-favor-goods">
					<table class="sui-table" id="rechargeDetailTable">
						<tbody>
							<tr>
								<td width="20%" class="right">充值单号：</td>
								<td width="80%">${settlementRecharge.rechargeNumber}</td>
							</tr>
							<tr>
								<td width="20%" class="right">支付方式：</td>
								<td width="80%">${settlementRecharge.settlementPayWay.name}</td>
							</tr>
							<tr>
								<td width="20%" class="right">充值金额：</td>
								<td width="80%">${settlementRecharge.rechargeMoney}</td>
							</tr>
							<tr>
								<td width="20%" class="right">创建时间：</td>
								<td width="80%">
									<fmt:formatDate value="${settlementRecharge.rechargeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr>
								<td width="20%" class="right">付款时间：</td>
								<td width="80%">
									<fmt:formatDate value="${settlementRecharge.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr>
								<td width="20%" class="right">状态：</td>
								<td width="80%">
									${fns:getDictLabel(settlementRecharge.staus, 'settlement_pay_status', '')}
								</td>
							</tr>
							<tr>
								<td colspan="1" class="right">
									<a href="${ctxm}/finance/financeRecharge/list.htm" class="sui-btn btn-xlarge">返回列表</a>
								</td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</dd>
		</dl>
	</div>
	<!--main-center结束-->
</body>
</html>
