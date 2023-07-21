<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("会员账户流水管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountUserSnList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("会员账户流水列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("会员账户流水列表")}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("会员账户流水管理是查看会员账户流水")}</li>
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
				<form action="${ctxa}/account/accountUserSn/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
					</div>
					<div class="col-sm-2">
						<input type="text" name="auId"  maxlength="19" class="form-control input-sm" placeholder="${fns:fy("请输入账户ID")}" value="${accountUserSn.auId}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="serialNumber"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("请输入流水号")}" value="${accountUserSn.serialNumber}"/>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
							<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${accountUserSn.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',isShowClear:true});" placeholder="${fns:fy("请选择创建开始时间")}"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${accountUserSn.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59',isShowClear:true});" placeholder="${fns:fy("请选择创建结束时间")}"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
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
						<th>${fns:fy("流水号")}</th>
						<th>${fns:fy("收入金额")}</th>
						<th>${fns:fy("支出金额")}</th>
						<th>${fns:fy("备注")}</th>
						<th>${fns:fy("创建时间")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="accountUserSn">
					<tr>
						<td>${accountUserSn.auId}</td>
						<td>${accountUserSn.serialNumber}</td>
						<td>${not empty accountUserSn.incomeMoney && accountUserSn.incomeMoney ne '0.000'?accountUserSn.incomeMoney:'0'}</td>
						<td>${not empty accountUserSn.expensesMoney && accountUserSn.expensesMoney ne '0.000'?accountUserSn.expensesMoney:'0'}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${accountUserSn.payRemarks}</textarea></td>
						<td><fmt:formatDate value="${accountUserSn.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr>
							<td datano="0" colspan="14"><div class="notData">${fns:fy("会员账户流水列表")}</div></td>
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