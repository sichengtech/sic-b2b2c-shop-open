<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("短信日志管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysSmsLogList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("短信日志列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("短信日志列表")}</a></li>
				<shiro:hasPermission name="sys:sysSmsLog:edit"><li class=""><a href="${ctxa}/sys/sysSmsLog/save1.do" > <i class="fa fa-user"></i> ${fns:fy("短信日志添加")}</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.短信日志.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-3">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<%-- <!-- 添加记录按钮 -->
						<shiro:hasPermission name="sys:sysSmsLog:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/sys/sysSmsLog/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission> --%>
					</div>
				</div>
				<form action="${ctxa}/sys/sysSmsLog/list.do" method="get" id="searchForm">
					<div class="col-sm-2">
						<select name="status" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("请选择状态")}--</option>
							<c:forEach items="${fns:getDictList('result_status')}" var="item">
							<option value="${item.value}" ${item.value==sysSmsLog.status?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<select name="type" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("请选择类型")}--</option>
							<c:forEach items="${fns:getDictList('sms_gateway_type')}" var="item">
							<option value="${item.value}" ${item.value==sysSmsLog.type?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
						<input name="beginSendDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${sysSmsLog.beginSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="${fns:fy("请选择开始时间")}"/>
							<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						 </div>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
						<input name="endSendDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
							value="<fmt:formatDate value="${sysSmsLog.endSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="${fns:fy("请选择结束时间")}"/>
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
						<th>${fns:fy("短信内容")}</th>
						<th>${fns:fy("模板id")}</th>
						<th>${fns:fy("发送状态")}</th>
						<th>${fns:fy("描述")}</th>
						<th>${fns:fy("短信网关类型")}</th>
						<th>${fns:fy("发送时间")}</th>
						<shiro:hasPermission name="sys:sysSmsLog:edit"><th>${fns:fy("操作")}</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="sysSmsLog">
					<tr>
						<td>${sysSmsLog.content}</td>
						<td>${not empty sysSmsLog.templatecode?sysSmsLog.templatecode:fns:fy("无")}</td>
						<td>${fns:getDictLabel(sysSmsLog.status, 'result_status', '')}</td>
						<td>${not empty sysSmsLog.bewrite?sysSmsLog.bewrite:fns:fy("无")}</td>
						<td>${fns:getDictLabel(sysSmsLog.type, 'sms_gateway_type', '')}</td>
						<td><fmt:formatDate value="${sysSmsLog.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="sys:sysSmsLog:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/sys/sysSmsLog/edit1.do?sslId=${sysSmsLog.sslId}">
								<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sys/sysSmsLog/delete.do?sslId=${sysSmsLog.sslId}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
						</td>
						</shiro:hasPermission>
					</tr>
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