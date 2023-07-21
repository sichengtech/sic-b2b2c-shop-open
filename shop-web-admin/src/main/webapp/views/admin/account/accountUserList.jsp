<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("会员账户管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountUserList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("会员账户列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("会员账户列表")}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("会员账户管理是查看会员账户信息")}</li>
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
				<form action="${ctxa}/account/accountUser/list.do" method="get" id="searchForm">
					<div class="col-sm-7">
					</div>
					<div class="col-sm-2">
						<input type="text" name="loginName"  maxlength="19" class="form-control input-sm" placeholder="${fns:fy("请输入会员名称")}" value="${loginName}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("账户ID")}</th>
						<th>${fns:fy("会员名称")}</th>
						<th>${fns:fy("账户类型")}</th>
						<th>${fns:fy("账户余额")}</th>
						<th>${fns:fy("冻结金额")}</th>
						<th>${fns:fy("创建时间")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="accountUser">
					<tr>
						<td>${accountUser.auId}</td>
						<td>${accountUser.userMain.loginName}</td>
						<td>${fns:getDictLabel(accountUser.accountType, 'account_user_type', '')}</td>
						<td>${not empty accountUser.ownMoney && accountUser.ownMoney ne '0.000'?accountUser.ownMoney:'0'}</td>
						<td>${not empty accountUser.frozenMoney && accountUser.frozenMoney ne '0.000'?accountUser.frozenMoney:'0'}</td>
						<td><fmt:formatDate value="${accountUser.createDate}" pattern="yyyy-MM-dd"/></td>
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/account/accountUserSn/list.do?auId=${accountUser.auId}&accountType=${accountUser.accountType}">
								<i class="fa fa-eye"></i> ${fns:fy("查看流水")}
							</a>
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