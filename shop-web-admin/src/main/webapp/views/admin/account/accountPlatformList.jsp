<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("平台账户管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountPlatformList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("平台账户列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("平台账户列表")}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("平台账户管理是查看平台的账户信息")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
					</div>
				</div>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("账户id")}</th>
						<th>${fns:fy("账户类型")}</th>
						<th>${fns:fy("账户余额")}</th>
						<th>${fns:fy("冻结金额")}</th>
						<th>${fns:fy("创建时间")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="accountPlatform">
					<c:if test="${accountPlatform.apId ne '13'}">
					<tr>
						<td>${accountPlatform.apId}</td>
						<td>${fns:getDictLabel(accountPlatform.accountType, 'account_platform_type', '')}</td>
						<td>${not empty accountPlatform.ownMoney && accountPlatform.ownMoney ne '0.000'?accountPlatform.ownMoney:'0'}</td>
						<td>${not empty accountPlatform.frozenMoney && accountPlatform.frozenMoney ne '0.000'?accountPlatform.frozenMoney:'0'}</td>
						<td><fmt:formatDate value="${accountPlatform.createDate}" pattern="yyyy-MM-dd"/></td>
						<td>
							<c:if test="${accountPlatform.apId eq 11}">
								<a class="btn btn-warning btn-sm" href="${ctxa}/account/accountPlatform/withdrawal1.do">
									<i class="fa fa-credit-card"></i> ${fns:fy("提现")}
								</a>
							</c:if>
							<a class="btn btn-info btn-sm" href="${ctxa}/account/accountPlatformSn/list.do?apId=${accountPlatform.apId}">
								<i class="fa fa-eye"></i> ${fns:fy("查看流水")}
							</a>
						</td>
					</tr>
					</c:if>
					</c:forEach>
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