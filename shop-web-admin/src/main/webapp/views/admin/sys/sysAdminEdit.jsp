<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("编辑管理员")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- 引入iCheck文件 -->
<%@ include file="../include/head_iCheck.jsp"%>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysAdminEdit.js"></script>
<style type="text/css">
.icheck label{padding-left: 17px;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("编辑管理员")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/user/list.do"> <i class="fa fa-user"></i> ${fns:fy("管理员列表")}</a></li>
				<shiro:hasPermission name="sys:user:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("编辑管理员")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.管理员管理.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/> 	 
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/user/edit2.do" method="post">
						<input type="hidden" name="id" value="${user.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("管理员头像")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="imgPath" name="photo" value="${user.photo}"/>
								<div id="vessel"></div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("系统设置.管理员管理.操作提示2")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("归属公司")}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="controls">
									<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
										title="公司" url="/sys/office/treeData.do?type=1" cssClass="input-sm"/>
								</div> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请选择归属公司")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("归属部门")}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="controls">
									<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
										title="部门" url="/sys/office/treeData.do?type=2" cssClass="input-sm" notAllowSelectParent="true"/>
								</div> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请选择归属部门")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("工号")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="no" value="${user.no}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写工号")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("姓名")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="name" value="${user.name}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写姓名")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("登录名")}&nbsp;:</label>
							<div class="col-sm-5">
								<input name="oldLoginName" type="hidden" value="${user.loginName}">
								<input class="form-control input-sm" type="text" placeholder="" name="loginName" value="${user.loginName}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写登录名")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("密码")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="password" placeholder="" name="newPassword" id="newPassword"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("若不修改密码，请留空")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("确认密码")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="password" placeholder="" name="confirmNewPassword"> 
							</div>
							<div class="col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">QQ&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="qq" value="${user.qq}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写QQ")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("邮箱")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="email" value="${user.email}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写邮箱")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("电话")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="phone" value="${user.phone}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写电话")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("手机")}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder="" name="mobile" value="${user.mobile}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写手机")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("用户角色")}&nbsp;:</label>
							<div class="col-sm-5 icheck">
								<c:forEach items="${allRoles}" var="roles" >
									<div class="square-blue single-row">
										<div class="checkbox">
											<c:set var="checked" value=""></c:set>
											<c:forEach items="${user.roleIdList}" var="item" >
												<c:if test="${item==roles.id}">
												<c:set var="checked" value="checked"></c:set>
												</c:if>
											</c:forEach>
											<input type="checkbox" style="display: none" ${checked} data-switch-no-init name="roleIds" value="${roles.id}">
											<label>${roles.name}</label>
										</div>
									</div> 
								</c:forEach>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择用户的角色")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy("是否允许登录")}&nbsp;:</label>
							<div class="col-sm-5">
							 <input type="checkbox" ${user.loginFlag eq 0?"":"checked"} data-size="small" style="display: none" value="1" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}" name="loginFlag" value="66"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("是否允许登录")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("备注")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea rows="6" class="form-control" name="remarks">${user.remarks}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写备注")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("创建时间")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("上次登录")}&nbsp;:</label>
							<div class="col-sm-5">
								<label class="control-label">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;${fns:fy("时间")}：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="sys:user:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy("保存")}
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