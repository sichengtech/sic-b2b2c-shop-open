<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("绑卡管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountTiedCardList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("绑卡列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("绑卡列表")}</a></li>
				<%-- <shiro:hasPermission name="account:accountTiedCard:save"><li class=""><a href="${ctxa}/account/accountTiedCard/save1.do" > <i class="fa fa-user"></i> 账户绑卡表添加</a></li></shiro:hasPermission> --%>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("管理员可以查看会员的绑卡记录，进行审核操作")}</li>
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
						<th>ID</th>
						<th>${fns:fy("会员")}</th>
						<th>${fns:fy("银行卡号")}</th>
						<th>${fns:fy("收款人")}</th>
						<th>${fns:fy("身份证号")}</th>
						<th>${fns:fy("开户银行")}</th>
						<th>${fns:fy("开户手机号")}</th>
						<th>${fns:fy("是否审核")}</th>
						<th>${fns:fy("申请时间")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="accountTiedCard">
					<tr>
						<td>${accountTiedCard.tiedCardId}</td>
						<td>${accountTiedCard.userMain.loginName}</td>
						<td>${accountTiedCard.bankCardNumber}</td>
						<td>${accountTiedCard.payee}</td>
						<td>${accountTiedCard.idNumber}</td>
						<td>${accountTiedCard.accountOpeningBank}</td>
						<td>${accountTiedCard.mobilePhoneNumber}</td>
						<td>${fns:getDictLabel(accountTiedCard.auditStatus, 'account_audit_status', '')}</td>
						<td><fmt:formatDate value="${accountTiedCard.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<c:if test="${accountTiedCard.auditStatus==0}">
								<shiro:hasPermission name="account:accountTiedCard:auth">
								<a class="btn btn-info btn-sm" href="${ctxa}/account/accountTiedCard/auth1.do?tiedCardId=${accountTiedCard.tiedCardId}">
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