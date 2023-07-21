<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("定时任务管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysTimedTaskList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("定时任务列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("定时任务列表")}</a></li>
				<shiro:hasPermission name="sys:sysTimedTask:save"><li class=""><a href="${ctxa}/sys/sysTimedTask/save1.do" > <i class="fa fa-user"></i> ${fns:fy("定时任务添加")}</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.定时任务.操作提示1")}</li>
					<li>${fns:fy("系统设置.定时任务.操作提示2")}</li>
					<li>${fns:fy("系统设置.定时任务.操作提示3")}</li>
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
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="sys:sysTimedTask:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/sys/sysTimedTask/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/sys/sysTimedTask/list.do" method="get" id="searchForm">
					<div class="col-sm-3">
					</div>
					<div class="col-sm-2">
						<input type="text" name="timedTaskNum"  maxlength="10" class="form-control input-sm" placeholder="${fns:fy("编号")}" value="${sysTimedTask.timedTaskNum}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="taskName"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("任务名称")}" value="${sysTimedTask.taskName}"/>
					</div>
					<div class="col-sm-2">
						<select name="status" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("请选择")}--</option>
							<c:forEach items="${fns:getDictList('is_status')}" var="item">
							<option value="${item.value}" ${item.value==sysTimedTask.status?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
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
						<th>${fns:fy("编号")}</th>
						<th>${fns:fy("任务名称")}</th>
						<th>${fns:fy("任务说明")}</th>
						<th>${fns:fy("执行时间")}</th>
						<th>${fns:fy("执行时间说明")}</th>
						<th>${fns:fy("状态")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="sysTimedTask">
					<tr>
						<td>${sysTimedTask.timedTaskNum}</td>
						<td>${sysTimedTask.taskName}</td>
						<td>${sysTimedTask.taskExplain}</td>
						<td>${sysTimedTask.taskTime}</td>
						<td>${sysTimedTask.timeExplain}</td>
						<td>${fns:getDictLabel(sysTimedTask.status, 'is_status', '')}</td>
						<td>
							<shiro:hasPermission name="sys:sysTimedTask:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/sys/sysTimedTask/edit1.do?sttId=${sysTimedTask.sttId}">
								<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sys:sysTimedTaskLog:view">
								<a class="btn btn-success btn-sm" href="${ctxa}/sys/sysTimedTaskLog/list.do?sttId=${sysTimedTask.sttId}">
									<i class="fa fa-edit"></i> ${fns:fy("日志")}
								</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sys:sysTimedTask:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sys/sysTimedTask/delete.do?sttId=${sysTimedTask.sttId}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
							</shiro:hasPermission>
						</td>
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