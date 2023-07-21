<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("角色分配")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!--multi-select 需要的js-->
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-multi-select/js/jquery.multi-select.js"></script>
<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/jquery-multi-select/js/jquery.quicksearch.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysRoleAssignedList.js"></script>

<!-- multi-select -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-admin/js/jquery-multi-select/css/multi-select.css"/>
<style type="text/css">
	#assignedForm{width: 454px;margin: 0 auto;}
	#selectUser{width: 372px;}
	#assignedBtn{float: right;margin-top: -31px;}
	#row{margin-right:0px; margin-left:0px;}
	.assignedLable{text-align: center;padding-left: 0;width: 186px;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("角色列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header> 
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 角色信息开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h4>${fns:fy("角色名称")}&nbsp;:&nbsp;${role.name}</h4>
				<p>${fns:fy("英文名称")}:${role.enname}</p>
				<p>${fns:fy("角色类型")}: ${fns:getDictLabel(role.roleType, 'sys_role_type', '')}</p>
				<p>${fns:fy("数据范围")}: ${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</p>
			</div>
			<!--角色信息结束 --> 
			<!-- 分配角色开始 --> 
			<sys:message content="${message}"/>
			<div class="row" style="margin-right: 0px;margin-left: 0px;">
				<div class="">
					<section class="panel">
						<header class="panel-heading" style="padding-top: 0;">
								${fns:fy("分配角色")}
						</header>
						<div class="panel-body">
							<form action="${ctxa}/sys/role/assignrole.do" class="form-horizontal" id="assignedForm" method="post" >
								<input type="hidden" value="${role.id}" name="id"/>
								<input type="hidden" value="" id="inRoleUser" name="inRoleUser"/>
								<div class="" id="selectUser">
									<div class="row" style="margin-bottom: 10px;" id="row">
										 <div class="assignedLable pull-left" style="">
										 	<small>${fns:fy("待分配用户")}</small>
										 </div>
										 <div class="assignedLable pull-right" style="">
										 	<small>${fns:fy("已分配用户")}</small>
										 </div>
									</div>
									<div class="" style="margin-bottom: 0px;">
										<select name="country" class="multi-select" multiple="" id="my_multi_select3">
											<c:forEach items="${notInRoleUserList}" var="user">
												<option class="inRoleUser" id="${user.id}" value="${user.id}">${user.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<shiro:hasPermission name="sys:role:edit">
								<div class="row" id="row">
									<div class="" id="assignedBtn">
										<button class="btn btn-info btn-sm" type="button">
											<i class="fa fa-check"></i> ${fns:fy("保存")}
										</button>
									</div>
								</div>
								</shiro:hasPermission>
							</form>
						</div>
					</section>
				</div>
			</div>
			<!-- 分配角色开始 --> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>${fns:fy("登录名")}</th>
						<th>${fns:fy("姓名")}</th>
						<th>${fns:fy("电话")}</th>
						<th>${fns:fy("手机")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${inRoleuserList}" var="user">
						<tr>
							<td><a href="${ctxa}/sys/user/form.do?id=${user.id}">${user.loginName}</a></td>
							<td>${user.name}</td>
							<td>${user.phone}</td>
							<td>${user.mobile}</td>
							<shiro:hasPermission name="sys:role:drop">
								<td>
									<a class="btn btn-danger btn-sm deleteMenu" href="${ctxa}/sys/role/outrole.do?userId=${user.id}&roleId=${role.id}" 
									onclick="return confirmx('${fns:fy("确认要将用户")}<b>[${user.name}]</b>${fns:fy("从")}<b>[${role.name}]</b>${fns:fy("角色中移除吗")}', this.href)">${fns:fy("移除")}</a>
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
	<!--multi-select 需要的js-->
	<script type="text/javascript" src="${ctxStatic}/sicheng-admin/js/multi-select-init.js"></script>
</body>
</html>