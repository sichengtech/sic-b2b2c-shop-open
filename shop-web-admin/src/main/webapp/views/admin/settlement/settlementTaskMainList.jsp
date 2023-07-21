<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('结算主任务管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementTaskMainList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('结算主任务列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('结算主任务列表')}</a></li>
				<shiro:hasPermission name="settlement:settlementTaskMain:save">
				<li class=""><a href="${ctxa}/settlement/settlementTaskMain/save1.do" > <i class="fa fa-user"></i> ${fns:fy('结算主任务添加')}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('结算任务.结算列表.操作提示1')}</li>
					<li>${fns:fy('结算任务.结算列表.操作提示2')}</li>
					<li>${fns:fy('结算任务.结算列表.操作提示3')}</li>
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
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="settlement:settlementTaskMain:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy('添加')}" href="${ctxa}/settlement/settlementTaskMain/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy('名称')}</th>
						<th>${fns:fy('结算起止时间')}</th>
						<th>${fns:fy('任务状态')}</th>
						<th>${fns:fy('任务结果')}</th>
						<shiro:hasPermission name="settlement:settlementTaskMain:edit"><th>${fns:fy('操作')}</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="settlementTaskMain">
					<tr>
						<td>${settlementTaskMain.name}</td>
						<td>
							<fmt:formatDate value="${settlementTaskMain.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/> 至${fns:fy('')}
							<fmt:formatDate value="${settlementTaskMain.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>${fns:getDictLabel(settlementTaskMain.status, 'settlement_task_main_status', '')}</td>
						<td>
							${fns:fy('总共')}${empty settlementTaskMain.sumCount?'0':settlementTaskMain.sumCount}${fns:fy('条')},
							${fns:fy('成功')}${empty settlementTaskMain.successCount?'0':settlementTaskMain.successCount},
							${fns:fy('失败')}${empty settlementTaskMain.errorCount?'0':settlementTaskMain.errorCount}
						</td>
						<shiro:hasPermission name="settlement:settlementTaskMain:edit"> 
						<td>
							
							<c:if test="${settlementTaskMain.status eq '1'}">
								<a href="${ctxa}/settlement/settlementTaskMain/runTaskSub.do?taskMainId=${settlementTaskMain.taskMainId}" class="btn btn-info btn-sm retry" id="">
									<i class="fa fa-pencil"></i> ${fns:fy('运行')}
								</a>
							</c:if>
							
							<%-- <c:if test="${settlementTaskMain.status eq '3'}">
								<a href="${ctxa}/settlement/settlementTaskMain/retryTaskSub.do?taskMainId=${settlementTaskMain.taskMainId}" class="btn btn-info btn-sm retry" id="">
									<i class="fa fa-pencil"></i> ${fns:fy('重算')}
								</a>
							</c:if> --%>
							
							<%-- <c:if test="${settlementTaskMain.status eq '3' && not empty settlementTaskMain.errorCount && settlementTaskMain.errorCount !='0'}">
								<a href="${ctxa}/settlement/settlementTaskMain/supplementTaskSub.do?taskMainId=${settlementTaskMain.taskMainId}" class="btn btn-danger btn-sm retry" id="">
									<i class="fa fa-pencil"></i> ${fns:fy('补算')}
								</a>
							</c:if> --%>
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