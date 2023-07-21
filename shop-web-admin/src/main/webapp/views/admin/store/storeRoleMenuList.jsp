<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>角色、资源中间表管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeRoleMenuList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">角色、资源中间表列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 角色、资源中间表列表</a></li>
				<shiro:hasPermission name="store:storeRoleMenu:edit"><li class=""><a href="${ctxa}/store/storeRoleMenu/save1.do" > <i class="fa fa-user"></i> 角色、资源中间表添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>角色、资源中间表管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="store:storeRoleMenu:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/storeRoleMenu/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/store/storeRoleMenu/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<input type="text" name="roleId"  maxlength="19" class="form-control input-sm" placeholder="关联(角色表)角色id" value="${storeRoleMenu.roleId}"/>
					</div>
					<div class="col-sm-1">
						<input type="text" name="menuId"  maxlength="19" class="form-control input-sm" placeholder="店铺资源表id" value="${storeRoleMenu.menuId}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>关联(角色表)角色id</th>
						<th>店铺资源表id</th>
						<shiro:hasPermission name="store:storeRoleMenu:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="storeRoleMenu">
					<tr>
						<td><a href="${ctxa}/store/storeRoleMenu/edit1.do?id=${storeRoleMenu.id}">${storeRoleMenu.roleId}</a></td>
						<td>${storeRoleMenu.menuId}</td>
						<shiro:hasPermission name="store:storeRoleMenu:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeRoleMenu/edit1.do?id=${storeRoleMenu.id}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeRoleMenu/delete.do?id=${storeRoleMenu.id}">
								<i class="fa fa-trash-o"></i> 删除
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