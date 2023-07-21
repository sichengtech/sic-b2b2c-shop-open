<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta name="decorator" content="member"/>
<style type="text/css">
	.right {text-align: right!important;}
	/* .sui-table.table-bordered th, .sui-table.table-bordered td {border-left: none;} */
	#rechargeDetailTable td{line-height: 30px;}
	.sui-table th, .sui-table td{border-top: 1px dashed #e5e5e5;}
</style>
</head>
<body>
	<!--main-center开始-->
	<div class="main-center">
		<dl class="order-tabs">
			<dt>
				<div class="position"><span>当前位置:</span><span>会员中心</span> > 财务中心 > 余额提现</div>
				<i class="sui-icon icon-tb-list"></i> 余额提现
			</dt>
			<ul class="sui-nav nav-tabs nav-large">
				<li class=""><a href="${ctxm}/finance/financeWithdrawals/save1.htm">申请提现</a></li>
				<li class=""><a href="${ctxm}/finance/financeWithdrawals/list.htm">提现记录</a></li>
				<li class="active"><a href="javascript:;">提现详情</a></li>
			</ul>
			<dd class="tab-content myfinancial">
				<div id="recharge_list" class="my-favor-goods">
					<table class="sui-table" id="rechargeDetailTable">
						<tbody>
							<tr>
								<td width="20%" class="right">提现单号：</td>
								<td width="80%">${settlementWithdrawals.withdrawalsId}</td>
							</tr>
							<tr>
								<td width="20%" class="right">收款方式：</td>
								<td width="80%">${settlementWithdrawals.settlementPayWay.name}</td>
							</tr>
							<tr>
								<td width="20%" class="right">提现金额：</td>
								<td width="80%">${settlementWithdrawals.money}</td>
							</tr>
							<tr>
								<td width="20%" class="right">创建时间：</td>
								<td width="80%">
									<fmt:formatDate value="${settlementWithdrawals.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr>
								<td width="20%" class="right">付款时间：</td>
								<td width="80%">
									<fmt:formatDate value="${settlementWithdrawals.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr>
								<td width="20%" class="right">状态：</td>
								<td width="80%">
									${fns:getDictLabel(settlementWithdrawals.status, 'settlement_withdrawals_status', '')}
								</td>
							</tr>
							<tr>
								<td colspan="1" class="right">
									<a href="${ctxm}/finance/financeWithdrawals/list.htm" class="sui-btn btn-xlarge">返回列表</a>
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
