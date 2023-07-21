<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>卖家角色管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeRoleList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">卖家角色列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 卖家角色列表</a></li>
				<shiro:hasPermission name="store:storeRole:edit"><li class=""><a href="${ctxa}/store/storeRole/save1.do" > <i class="fa fa-user"></i> 卖家角色添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>卖家角色管理是xxx</li>
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
						<shiro:hasPermission name="store:storeRole:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/storeRole/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/store/storeRole/list.do" method="get" id="searchForm">
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
						<th>主键</th>
						<th>关联(卖家表)</th>
						<th>角色名称</th>
						<th>英文名称</th>
						<th>角色类型</th>
						<th>数据范围</th>
						<th>是否系统数据(0否、1是)</th>
						<th>是否可用，0否、1是</th>
						<th>逻辑删除，0正常、1删除、2审核</th>
						<th>创建时间</th>
						<th>更新时间</th>
						<shiro:hasPermission name="store:storeRole:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="storeRole">
					<tr>
						<td><a href="${ctxa}/store/storeRole/edit1.do?roleId=${storeRole.roleId}">${storeRole.roleId}</a></td>
						<td>${storeRole.sellerId}</td>
						<td>${storeRole.roleName}</td>
						<td>${storeRole.enname}</td>
						<td>${storeRole.roleType}</td>
						<td>${storeRole.dataScope}</td>
						<td>${fns:getDictLabel(storeRole.isSys, 'yes_no', '')}</td>
						<td>${storeRole.useable}</td>
						<td>${fns:getDictLabel(storeRole.delFlag, 'del_flag', '')}</td>
						<td><fmt:formatDate value="${storeRole.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${storeRole.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="store:storeRole:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeRole/edit1.do?roleId=${storeRole.roleId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeRole/delete.do?roleId=${storeRole.roleId}">
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