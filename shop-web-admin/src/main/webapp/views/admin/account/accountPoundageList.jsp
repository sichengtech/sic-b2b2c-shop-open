<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('支付手续费用管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountPoundageList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('支付手续费用管理')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('支付手续费用管理')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('记录平台支付手续费用')}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
					</div>
				</div>
				<form action="${ctxa}/account/accountPlatformSn/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
					</div>
					<div class="col-sm-2">
						<input type="text" name="apId"  maxlength="19" class="form-control input-sm" placeholder="${fns:fy('请输入平台账户ID')}" value="${accountPlatformSn.apId}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="serialNumber"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy('请输入流水号')}" value="${accountPlatformSn.serialNumber}"/>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
							<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${accountPlatformSn.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',isShowClear:true});" placeholder="${fns:fy('请选择创建开始时间')}"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
						<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${accountPlatformSn.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59',isShowClear:true});" placeholder="${fns:fy('请选择创建结束时间')}"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						</div>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy('搜索')}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>${fns:fy('流水号')}</th>
						<th>${fns:fy('支出金额')}</th>
						<th>${fns:fy('备注')}</th>
						<th>${fns:fy('创建时间')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="accountPlatformSn">
					<tr>
						<td>${accountPlatformSn.id}</td>
						<td>${accountPlatformSn.serialNumber}</td>
						<td>${not empty accountPlatformSn.expensesMoney && accountPlatformSn.expensesMoney ne '0.000'?accountPlatformSn.expensesMoney:'0'}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${accountPlatformSn.payRemarks}</textarea></td>
						<td><fmt:formatDate value="${accountPlatformSn.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr>
							<td datano="0" colspan="14"><div class="notData">${fns:fy('无查询结果')}</div></td>
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