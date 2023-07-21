<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("会员管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sso/userMainForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty userMain.UId?true:false}"></c:set >
			<h4 class="title">${isEdit? fns:fy("编辑") : fns:fy("添加")}${fns:fy("会员")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sso/userMain/list.do"> <i class="fa fa-user"></i> ${fns:fy("会员管理")}</a></li>
				<shiro:hasPermission name="sso:member:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("会员")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("会员管理.会员管理.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sso/userMain/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="uId" value="${userMain.UId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("用户名")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" name="oldLoginName" value="${userMain.loginName}" id="oldLoginName">
								<input type="text" name="loginName"  maxlength="64" class="form-control input-sm" value="${userMain.loginName}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项，请填写用户名")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("密码")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="password" name="password"  maxlength="64" class="form-control input-sm" value="" id="password"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写密码")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("确认密码")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="password" name="repassword"  maxlength="64" class="form-control input-sm" value=""/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写确认密码")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("邮箱")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" name="oldEmail" value="${userMain.email}" id="oldEmail">
								<input type="text" name="email"  maxlength="64" class="form-control input-sm" value="${userMain.email}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写邮箱")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("手机号")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" name="oldMobile" value="${userMain.mobile}" id="oldMobile">
								<input type="text" name="mobile"  maxlength="64" class="form-control input-sm" value="${userMain.mobile}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写手机号")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="sso:member:edit">
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