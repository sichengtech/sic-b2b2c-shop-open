<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("管理员列表")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysAdminList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("管理员列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("管理员列表")}</a></li>
				<shiro:hasPermission name="sys:user:save"><li class=""><a href="${ctxa}/sys/user/save1.do" > <i class="fa fa-user"></i> ${fns:fy("添加管理员")}</a></li></shiro:hasPermission>
			</ul>
		</header>
		<sys:message content="${message}"/> 
		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.管理员管理.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="sys:user:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/sys/user/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="col-sm-5"></div>
				<form action="${ctxa}/sys/user/list.do" method="get">
					<div class="col-sm-2">
						<input type="text" class="form-control input-sm" placeholder="${fns:fy("登录名")}" name="loginName" value="${u.loginName}" maxlength="10">
					</div>
					<div class="col-sm-2">
						<input type="text" class="form-control input-sm" placeholder="${fns:fy("姓名")}" name="name" value="${u.name}" maxlength="10">
					</div>
					<div class="col-sm-1" style="text-align: right;">
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
						<th></th> 
						<th>${fns:fy("管理员ID")}</th>
						<th>${fns:fy("工号")}</th>
						<th>${fns:fy("登录名")}</th>
						<th>${fns:fy("姓名")}</th>
						<th>${fns:fy("电话")}</th>
						<th>${fns:fy("手机")}</th>
						<th>${fns:fy("管理操作")}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="user">
						<tr class="${user.id}">
							 <td class="detail"><i class="fa fa-plus"></i></span></td>
							 <td>${user.id}</td>
							 <td>${user.no}</td>
							 <td>${user.loginName}</td>
							 <td>${user.name}</td>
							 <td>${user.phone}</td>
							 <td>${user.mobile}</td>
							 <td>
						 		<shiro:hasPermission name="sys:user:edit">
								<a href="${ctxa}/sys/user/edit1.do?id=${user.id}" class="btn btn-info btn-sm">
									<i class="fa fa-edit"></i> ${fns:fy("编辑")}
								</a>
								 </shiro:hasPermission>
								 <shiro:hasPermission name="sys:user:drop">
								 <c:if test="${user.id!='1'}">
									<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sys/user/delete.do?id=${user.id}&loginName=${user.loginName}">
										<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
									</button>
								 </c:if>
								</shiro:hasPermission>
							 </td>
						</tr>
						<tr id="${user.id}" class="detail-extra">
							<td colspan="14" >
								<p class="dt-grid-cell">${fns:fy("工号")}：${user.no}</p>
								<p class="dt-grid-cell">${fns:fy("姓名")}：${user.name}</p>
								<p class="dt-grid-cell">${fns:fy("邮箱")}：${user.email}</p>
								<p class="dt-grid-cell">${fns:fy("电话")}：${user.phone}</p>
								<p class="dt-grid-cell">${fns:fy("手机")}：${user.mobile}</p>
								<p class="dt-grid-cell hidden">${fns:fy("用户类型")}：${fns:getDictLabel(user.userType, 'sys_user_type', fns:fy("无"))}</p>
								<p class="dt-grid-cell">${fns:fy("最后登录IP")}：${user.loginIp}</p>
								<p class="dt-grid-cell">${fns:fy("最后登录日期")}：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></p>
								<p class="dt-grid-cell">${fns:fy("是否允许登录")}：${fns:getDictLabel(user.loginFlag, 'yes_no', fns:fy("是"))}</p>
								<p class="dt-grid-cell">${fns:fy("备注")}：${user.remarks}</p>
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