<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("店铺菜单管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/treetable.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeMenuList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("店铺菜单列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("店铺菜单列表")}</a></li>
				<shiro:hasPermission name="store:storeMenu:edit">
					<li class=""><a href="${ctxa}/store/storeMenu/form.do" > <i class="fa fa-user"></i> ${fns:fy("店铺菜单")}${fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.菜单管理.操作提示1")}</li>
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
						<shiro:hasPermission name="store:storeMenu:edit">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/store/storeMenu/form.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/store/storeMenu/search.do" method="get" >
					<div class="col-sm-7">
					</div>
					<div class="col-sm-2">
						<input type="text" class="form-control input-sm" placeholder="${fns:fy("店铺菜单名称")}" name="name" value="${name}" maxlength="30"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<form class="cmxform form-horizontal adminex-form" id="listForm" method="get" action="${ctxa}/store/storeMenu/updateSort.do">
				<table id="treeTable" class="table table-hover table-condensed table-bordered">
					<thead>
						<tr>
							<th>${fns:fy("名称")}</th>
							<th>${fns:fy("链接")}</th>
<%--							<th>${fns:fy("编号")}</th>--%>
							<th>${fns:fy("排序")}</th>
							<th>${fns:fy("可见")}</th>
							<th>${fns:fy("权限标识")}</th>
							<shiro:hasPermission name="store:storeMenu:edit"><th>${fns:fy("操作")}</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="menu">
							<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
								<td style="text-align:left" nowrap><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i>${menu.name}</td>
								<td style="text-align:left;margin:0;padding:0;">${menu.href}</td>
<%--								<td>${menu.menuNum}</td>--%>
								<td style="text-align:center">
									<shiro:hasPermission name="store:storeMenu:edit">
										<input type="hidden" name="ids" value="${menu.id}"/>
										<input name="sorts" class="form-control input-sm sorts" id="sort_${menu.id}" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
									</shiro:hasPermission>
									<shiro:lacksPermission name="store:storeMenu:edit">
										${menu.sort}
									</shiro:lacksPermission>
								</td>
								<td>${menu.isShow eq '1'?fns:fy("显示"):fns:fy("隐藏")}</td>
								<td>
									<textarea class="form-control input-sm" rows="2" readonly="readonly">${menu.permission}</textarea>
								</td>
								<td nowrap>
								<shiro:hasPermission name="store:storeMenu:edit">
									<a type="button" class="btn btn-info btn-sm" href="${ctxa}/store/storeMenu/form.do?menuId=${menu.id}" >
									 <i class="fa fa-edit"></i> ${fns:fy("编辑")}
									</a>
									<a type="button" class="btn btn-info btn-sm" href="${ctxa}/store/storeMenu/form.do?parent.id=${menu.id}">
										<i class="fa fa-level-down"></i> ${fns:fy("添加下级菜单")}
									</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="store:storeMenu:drop">
									<button class="btn btn-danger btn-sm deleteMenu" type="button" href="${ctxa}/store/storeMenu/delete.do?menuId=${menu.id}" id="${menu.id}">
										<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
									</button>
								</shiro:hasPermission>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<!-- table结束 -->
				<div class="form-group">
					<div class="col-lg-6" >
						<button class="btn btn-info" type="submit">
							<i class="fa fa-check"></i> ${fns:fy("保存排序")}
						</button>
					</div>
				</div>
			</form>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>