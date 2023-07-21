<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>角色菜单中间表管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysRoleMenuForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty sysRoleMenu.roleId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}角色菜单中间表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/sysRoleMenu/list.do"> <i class="fa fa-user"></i> 角色菜单中间表列表</a></li>
				<shiro:hasPermission name="sys:sysRoleMenu:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 角色菜单中间表${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>角色菜单中间表管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/sysRoleMenu/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="roleId" value="${sysRoleMenu.roleId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 角色编号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="roleId"  maxlength="19" class="form-control input-sm" value="${sysRoleMenu.roleId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写角色编号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 机构编号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="menuId"  maxlength="19" class="form-control input-sm" value="${sysRoleMenu.menuId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写机构编号<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="sys:sysRoleMenu:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存
								</button>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>