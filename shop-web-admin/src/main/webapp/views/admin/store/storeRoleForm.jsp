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
<script type="text/javascript" src="${ctx}/views/admin/store/storeRoleForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeRole.roleId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}卖家角色</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeRole/list.do"> <i class="fa fa-user"></i> 卖家角色列表</a></li>
				<shiro:hasPermission name="store:storeRole:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 卖家角色${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>卖家角色管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeRole/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="roleId" value="${storeRole.roleId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="roleId"  maxlength="19" class="form-control input-sm" value="${storeRole.roleId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 关联(卖家表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerId"  maxlength="19" class="form-control input-sm" value="${storeRole.sellerId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写关联(卖家表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 角色名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="roleName"  maxlength="64" class="form-control input-sm" value="${storeRole.roleName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写角色名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 英文名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="enname"  maxlength="255" class="form-control input-sm" value="${storeRole.enname}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写英文名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 角色类型&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="roleType"  maxlength="64" class="form-control input-sm" value="${storeRole.roleType}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写角色类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 数据范围&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="dataScope"  maxlength="1" class="form-control input-sm" value="${storeRole.dataScope}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写数据范围<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否系统数据(0否、1是)&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="isSys" value="${item.value}" ${item.value==storeRole.isSys?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否系统数据(0否、1是)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否可用，0否、1是&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="useable"  maxlength="64" class="form-control input-sm" value="${storeRole.useable}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否可用，0否、1是<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="store:storeRole:edit">
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