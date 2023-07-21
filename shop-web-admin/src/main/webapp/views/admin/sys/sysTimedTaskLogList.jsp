<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("定时任务日志管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysTimedTaskLogList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("定时任务日志列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<%-- <ul class="nav nav-tabs pull-right">
				<li class="active"><a href="${ctxa}/sys/sysTimedTaskLog/list.do"> <i class="fa fa-user"></i> 定时任务日志列表</a></li>
				<shiro:hasPermission name="sys:sysTimedTaskLog:edit"><li class=""><a href="${ctxa}/sys/sysTimedTaskLog/save1.do" > <i class="fa fa-user"></i> 定时任务日志添加</a></li></shiro:hasPermission>
			</ul> --%>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.定时任务.操作提示4")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<%-- <div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="sys:sysTimedTaskLog:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/sys/sysTimedTaskLog/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/sys/sysTimedTaskLog/list.do" method="get" id="searchForm">
					<div class="col-sm-7">
					</div>
					<div class="col-sm-2">
						<input type="text" name="result"  maxlength="64" class="form-control input-sm" placeholder="执行结果" value="${sysTimedTaskLog.result}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div>
				</form>
			</div> --%>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("定时任务名称")}</th>
						<th>${fns:fy("执行开始时间")}</th>
						<th>${fns:fy("执行结束时间")}</th>
						<th>${fns:fy("执行结果")}</th>
						<!-- <th>执行状态</th> -->
						<%-- <shiro:hasPermission name="sys:sysTimedTaskLog:edit"><th>操作</th></shiro:hasPermission> --%>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="sysTimedTaskLog">
					<tr>
						<td>${taskName}</td>
						<td><fmt:formatDate value="${sysTimedTaskLog.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${sysTimedTaskLog.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<c:if test="${sysTimedTaskLog.result==0}">${fns:fy("失败")}</c:if>
							<c:if test="${sysTimedTaskLog.result==1}">${fns:fy("成功")}</c:if>
							<c:if test="${sysTimedTaskLog.result==2}">${fns:fy("执行中")}</c:if>
						</td>
						<%-- <td>${sysTimedTaskLog.status==1?"完成":"执行中"}</td> --%>
						<%-- <shiro:hasPermission name="sys:sysTimedTaskLog:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/sys/sysTimedTaskLog/edit1.do?sttlId=${sysTimedTaskLog.sttlId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sys/sysTimedTaskLog/delete.do?sttlId=${sysTimedTaskLog.sttlId}">
								<i class="fa fa-trash-o"></i> 删除
							</button>
						</td>
						</shiro:hasPermission> --%>
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