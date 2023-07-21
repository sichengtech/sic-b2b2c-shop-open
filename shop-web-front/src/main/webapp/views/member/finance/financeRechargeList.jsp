<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>充值记录</title>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/finance/financeRechargeList.js"></script>
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
				<li><a href="${ctxm}/finance/financeRecharge/save1.htm">在线充值</a></li>
				<li class="active"><a href="">充值记录</a></li>
			</ul>
			<dd class="tab-content myfinancial">
				<sys:message content="${message}"/>
				<div id="recharge_list" class="my-favor-goods">
					<table class="default-table">
						<thead>
							<tr>
								<th>充值单号</th>
								<th class="w150">创建时间</th>
								<th class="w150">支付方式</th>
								<th class="w150">充值金额(元)</th>
								<th>状态</th>
								<th class="w180">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="recharge">
								<tr class="bd-line">
									<td>${recharge.rechargeId}</td>
									<td><fmt:formatDate value="${recharge.rechargeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td>${recharge.settlementPayWay.name}</td>
									<td>${recharge.rechargeMoney}</td>
									<td>${fns:getDictLabel(recharge.staus, 'settlement_pay_status', '')}</td>
									<td>
										<c:if test="${recharge.staus eq '0'}">
											<a href="${ctxm}/finance/financeRecharge/rechargePay.htm?rechargeId=${recharge.rechargeId}" class="sui-btn btn-large btn-info">支付</a>
											<button href="${ctxm}/finance/financeRecharge/delete.htm?rechargeId=${recharge.rechargeId}" class="sui-btn btn-large btn-danger deleteSure">删除</button>
										</c:if>
										<c:if test="${recharge.staus eq '1'}">
											<a href="${ctxm}/finance/financeRecharge/rechargeDetail.htm?rechargeId=${recharge.rechargeId}" class="sui-btn btn-large">查看</a>
										</c:if>
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
