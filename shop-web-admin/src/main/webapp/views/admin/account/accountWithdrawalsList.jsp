<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("提现管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountWithdrawalsList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("提现管理")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("提现管理")}</a></li>
				<%-- <shiro:hasPermission name="account:accountWithdrawals:save"><li class=""><a href="${ctxa}/account/accountWithdrawals/save1.do" > <i class="fa fa-user"></i> 账户提现添加</a></li></shiro:hasPermission> --%>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("显示提现管理，可以进行审核和支付操作")}</li>
					<li>${fns:fy("点击支付只负责记账，还需要管理员线下进行转账！")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新${fns:fy("提现管理")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<%-- <shiro:hasPermission name="account:accountWithdrawals:save">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/account/accountWithdrawals/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission> --%>
					</div>
				</div>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("提现ID")}</th>
						<th>${fns:fy("会员")}</th>
						<th>${fns:fy("账户")}</th>
						<th>${fns:fy("提现金额(元)")}</th>
						<c:if test="${status eq '1'}"><th>${fns:fy("提现卡号")}</th></c:if>
						<th>${fns:fy("是否审核")}</th>
						<th>${fns:fy("是否支付")}</th>
						<th>${fns:fy("申请时间")}</th>
						<th>${fns:fy("支付时间")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="accountWithdrawals">
					<tr>
						<td>${accountWithdrawals.id}</td>
						<td class="user">
							<c:if test="${status eq '1'}">${accountWithdrawals.accountUser.userMain.loginName }</c:if>
							<c:if test="${status eq '2'}">${fns:fy("平台")}</c:if>
						</td>
						<td class="accountType">
							<c:if test="${status eq '1'}">${fns:getDictLabel(accountWithdrawals.accountUser.accountType, 'account_user_type', '')}</c:if>
							<c:if test="${status eq '2'}">${fns:getDictLabel(accountWithdrawals.accountPlatform.accountType, 'account_platform_type', '')}</c:if>
						</td>
						<td class="money">${accountWithdrawals.money}${fns:fy("元")}</td>
						<c:if test="${status eq '1'}"><td>${accountWithdrawals.accountTiedCard.bankCardNumber }</td></c:if>
						<td>${fns:getDictLabel(accountWithdrawals.auditStatus, 'account_audit_status', '')}</td>
						<td>${fns:getDictLabel(accountWithdrawals.isPay, 'account_is_pay', '')}</td>
						<td><fmt:formatDate value="${accountWithdrawals.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${accountWithdrawals.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<c:if test="${accountWithdrawals.auditStatus eq '1' && accountWithdrawals.isPay eq '0'}">
								<shiro:hasPermission name="account:accountWithdrawals:edit">
								<button type="button" class="btn btn-warning btn-sm pay" href="${ctxa}/account/accountWithdrawals/pay.do?id=${accountWithdrawals.id}&status=${status}">
									<i class="fa fa-credit-card"></i> ${fns:fy("支付")}
								</button>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${accountWithdrawals.auditStatus eq '0'}">
								<shiro:hasPermission name="account:accountWithdrawals:auth">
								<a class="btn btn-info btn-sm" href="${ctxa}/account/accountWithdrawals/auth1.do?id=${accountWithdrawals.id}&status=${status}">
									<i class="fa fa-edit"></i> ${fns:fy("审核")}
								</a>
								</shiro:hasPermission>
							</c:if>
						</td>
					</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr>
							<td datano="0" colspan="14"><div class="notData">${fns:fy("无查询结果")}</div></td>
						</tr>
					</c:if>
				</tbody>
				</table>
			</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>