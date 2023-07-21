<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("菜单列表管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- <meta name="menu" content="${param.menuId}"/> 表示使用N号二级菜单 -->
<%@ include file="../include/treetable.jsp"%>
<!-- 业务js --> 
<script type="text/javascript" src="${ctx}/views/admin/sys/sysMenuList.js"></script>
<style type="text/css">
	a {color: #0a0a0a;}
	.sticky-header .main-content {padding-top: 29px;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right">
			<h4 class="title">${fns:fy("菜单列表管理")} </h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javascript:;" data-toggle="tab"> <i class="fa fa-home"></i> ${fns:fy("菜单列表")}</a></li>
				<shiro:hasPermission name="sys:menu:edit">
				<li class=""><a href="${ctxa}/sys/menu/form.do" > <i class="fa fa-user"></i> ${fns:fy("添加菜单")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.菜单管理.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示结束 --> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px;">
				<div class="col-sm-2">
					<div class="btn-group">
						<!--刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<shiro:hasPermission name="sys:menu:edit">
						<!-- 新增记录按钮 -->
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/sys/menu/form.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="col-sm-7"></div>
				<form action="${ctxa}/sys/menu/search.do" method="get" >
					<div class="col-sm-2">
						<input type="text" class="form-control input-sm" placeholder="${fns:fy("菜单名称")}" maxLength="64" name="name" value="${name}">
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<sys:message content="${message}"/>
			<form class="cmxform form-horizontal adminex-form" id="listForm" method="get" action="${ctxa}/sys/menu/updateSort.do">
				<table id="treeTable" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th style="text-align:center">${fns:fy("名称")}</th>
							<th>${fns:fy("链接")}</th>
<%--							<th>${fns:fy("编号")}</th>--%>
							<th>${fns:fy("排序")}</th>
							<th>${fns:fy("可见")}</th>
							<th>${fns:fy("权限标识")}</th>
							<th>${fns:fy("操作")}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="menu">
							<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
								<td style="text-align:left" nowrap><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i><a href="${ctxa}/sys/menu/form.do?id=${menu.id}">${menu.name}</a></td>
								<td style="text-align:left;margin:0;padding:0;">${menu.href}</td>
<%--								<td>${menu.menuNum}</td>--%>
								<td style="text-align:center">
									<shiro:hasPermission name="sys:menu:edit">
										<input type="hidden" name="ids" value="${menu.id}"/>
										<input name="sorts" class="form-control input-sm sorts" id="sort_${menu.id}" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
									</shiro:hasPermission>
									<shiro:lacksPermission name="sys:menu:edit">
										${menu.sort}
									</shiro:lacksPermission>
								</td>
								<td>${menu.isShow eq '1'?fns:fy("显示"):fns:fy("隐藏")}</td>
								<td style="text-align:left;margin:0;padding:0;">${menu.permission}</td>
								<td nowrap>
									<shiro:hasPermission name="sys:menu:edit">
									<a type="button" class="btn btn-info btn-sm" href="${ctxa}/sys/menu/form.do?id=${menu.id}" >
									 <i class="fa fa-edit"></i> ${fns:fy("编辑")}
									</a>
									<a type="button" class="btn btn-info btn-sm" href="${ctxa}/sys/menu/form.do?parent.id=${menu.id}">
										<i class="fa fa-level-down"></i> ${fns:fy("添加下级菜单")}
									</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="sys:menu:drop">
									<button class="btn btn-danger btn-sm deleteMenu" type="button" href="${ctxa}/sys/menu/delete.do?id=${menu.id}" id="${menu.id}">
										<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
									</button>
									</shiro:hasPermission>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					</table>
					<!-- table结束 -->
					<shiro:hasPermission name="sys:menu:edit">
					<div class="form-group">
						<div class="col-lg-6" >
							<button class="btn btn-info" type="submit">
								<i class="fa fa-check"></i> ${fns:fy("保存排序")}
							</button>
						</div>
					</div>
					</shiro:hasPermission>
				</form>
		 </div>
		 <!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>