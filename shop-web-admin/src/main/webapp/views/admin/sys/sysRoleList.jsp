<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("角色列表")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysRoleList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("角色列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javascript:;"> <i class="fa fa-home"></i> ${fns:fy("角色列表")}</a></li>
				<shiro:hasPermission name="sys:role:edit">
				<li><a href="${ctxa}/sys/role/form.do"> <i class="fa fa-user"></i> ${fns:fy("添加角色")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.角色管理.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px;">
				<div class="col-sm-6">
					<div class="btn-group">
						<!--刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!--添加按钮 -->
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/sys/role/form.do">
							<i class="fa fa-plus"></i>
						</a>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="iconic-input right">
						<form action="${ctxa}/sys/role.do" id="searchForm" method="">
							<input type="text" name="name" class="form-control input-sm pull-right" maxLength="" placeholder="${fns:fy("请输入角色名称搜索")}" value="${name}" style="border-radius: 30px;max-width:250px;">
							<a href="javascript:;" id="submit" class="btn" style="position: absolute;top: -8px;right: 0px;"> <i class="fa fa-search" style=""></i> </a>
						</form>
					</div>
				</div>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<sys:message content="${message}"/>
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>${fns:fy("角色名称")}</th>
						<th>${fns:fy("英文名称")}</th>
						<%--<th>数据范围</th> --%> 
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${list}" var="role">
						<tr>
							<td><a href="role/form.do?id=${role.id}">${role.name}</a></td>
							<td><a href="role/form.do?id=${role.id}">${role.enname}</a></td>
							<%-- <td>${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td> --%>
							<td>
								<shiro:hasPermission name="sys:role:edit">
								<a class="btn btn-info btn-sm" href="${ctxa}/sys/role/assign.do?id=${role.id}">${fns:fy("分配")}</a>
								<c:if test="${(role.sysData eq fns:getDictValue(fns:fy('是'), 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue(fns:fy('是'), 'yes_no', '1'))}">
									<a class="btn btn-info btn-sm" href="${ctxa}/sys/role/form.do?id=${role.id}">${fns:fy("修改")}</a>
								</c:if>
								</shiro:hasPermission>
								<shiro:hasPermission name="sys:role:drop">
								<button class="btn btn-danger btn-sm deleteRole" type="button" href="${ctxa}/sys/role/delete.do?id=${role.id}">${fns:fy("删除")}</button>
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