<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/finance/financeWithdrawalsList.js"></script>
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
				<li><a href="${ctxm}/finance/financeWithdrawals/save1.htm">余额提现</a></li>
				<li class="active"><a href="javascript:;">提现记录</a></li>
			</ul>
			<dd class="tab-content myfinancial">
				<div id="recharge_list" class="my-favor-goods">
					<sys:message content="${message}"/>
					<table class="default-table">
						<thead>
							<tr>
								<th>提现单号</th>
								<th class="w150">创建时间</th>
								<th class="w150">收款方式</th>
								<th class="w150">充值金额(元)</th>
								<th>状态</th>
								<th class="w180">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="withdrawals">
								<tr class="bd-line">
									<td>${withdrawals.withdrawalsId}</td>
									<td><fmt:formatDate value="${withdrawals.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td>${withdrawals.settlementPayWay.name}</td>
									<td>${withdrawals.money}</td>
									<td>${fns:getDictLabel(withdrawals.status, 'settlement_withdrawals_status', '')}</td>
									<td>
										<c:if test="${withdrawals.status eq '0'}">
											<!-- <a href="javascript:void(0);" class="sui-btn btn-large btn-info">提现</a> -->
											<button href="${ctxm}/finance/financeWithdrawals/delete.htm?withdrawalsId=${withdrawals.withdrawalsId}" class="sui-btn btn-large btn-danger deleteSure">删除</button>
										</c:if>
										<a href="${ctxm}/finance/financeWithdrawals/withdrawalsDetail.htm?withdrawalsId=${withdrawals.withdrawalsId}" class="sui-btn btn-large btn-info">查看</a> 
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</dd>
			<%@ include file="/views/member/include/page.jsp"%>
		</dl>
	</div>
	<!--main-center结束-->
</body>
</html>
