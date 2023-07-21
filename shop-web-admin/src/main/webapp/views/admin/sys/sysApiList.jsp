<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("接口管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysApiList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("接口列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("接口列表")}</a></li>
				<shiro:hasPermission name="sys:sysApi:save"><li class=""><a href="${ctxa}/sys/sysApi/save1.do" > <i class="fa fa-user"></i> ${fns:fy("接口添加")}</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.接口测试.操作提示1")}</li>
					<li>${fns:fy("系统设置.接口测试.操作提示2")}</li>
					<li>${fns:fy("系统设置.接口测试.操作提示3")}</li>
					<li>${fns:fy("系统设置.接口测试.操作提示4")}</li>
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
						<shiro:hasPermission name="sys:sysApi:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/sys/sysApi/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/sys/sysApi/list.do" method="get" id="searchForm">
					<div class="col-md-offset-5 col-sm-2">
						<select name="apiCategory" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("所属分类")}--</option>
							<c:forEach items="${fns:getDictList('sys_api_category')}" var="item">
							<option value="${item.value}" ${item.value==sysApi.apiCategory?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<input type="text" name="apiName"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("接口名")}" value="${sysApi.apiName}"/>
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
						<th>${fns:fy("所属分类")}</th>
						<th>${fns:fy("接口名")}</th>
						<th>${fns:fy("接口版本号")}</th>
						<th>${fns:fy("接口描述")}</th>
						<th>${fns:fy("接口地址")}</th>
						<th>${fns:fy("查看参数")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="sysApi">
					<tr>
						<td>${fns:getDictLabel(sysApi.apiCategory, 'sys_api_category', '')}</td>
						<td>${sysApi.apiName}</td>
						<td>${fns:getDictLabel(sysApi.apiVersion, 'sys_api_version', '')}</td>
						<td>${sysApi.apiDescribe}</td>
						<td>${sysApi.apiUrl}</td>
						<td>
							<shiro:hasPermission name="sys:sysApiParam:view">
							<a class="btn btn-info btn-sm param-view" href="javascript:void(0);" apiId="${sysApi.apiId}">
								<i class="fa fa-edit"></i> ${fns:fy("查看参数")}
							</a>
							</shiro:hasPermission>
						</td>
						<td>
							<shiro:hasPermission name="sys:sysApi:test">
							<a class="btn btn-info btn-sm" href="${ctxa}/sys/sysApi/test.do?apiId=${sysApi.apiId}">
								<i class="fa fa-edit"></i> ${fns:fy("测试")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sys:sysApi:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/sys/sysApi/edit1.do?apiId=${sysApi.apiId}">
								<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sys:sysApi:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sys/sysApi/delete.do?apiId=${sysApi.apiId}">
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