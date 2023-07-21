<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("注册设置管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteRegisterForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteRegister.id?true:false}"></c:set > 
			<h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("注册设置")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/site/siteRegister/list.do"> <i class="fa fa-user"></i> ${fns:fy("注册设置列表")}</a></li>
				<shiro:hasPermission name="site:siteRegister:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("注册设置")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.注册设置.操作提示1")}</li>
					<li>${fns:fy("网站设置.注册设置.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteRegister/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${siteRegister.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("是否开放注册")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="isRegister" value="1" ${"1"==siteRegister.isRegister?"checked":""} 
								data-size="small" style="display: none" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("网站设置.注册设置.操作提示3")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("用户名最大长度")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="usernameMax" maxlength="10" class="form-control input-sm" value="${siteRegister.usernameMax}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("网站设置.注册设置.操作提示4")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("用户名最小长度")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="usernameMin" maxlength="10" class="form-control input-sm" value="${siteRegister.usernameMin}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("网站设置.注册设置.操作提示5")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("密码最大长度")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pwdMax" maxlength="10" class="form-control input-sm" value="${siteRegister.pwdMax}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("网站设置.注册设置.操作提示4")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("密码最小长度")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pwdMin" maxlength="10" class="form-control input-sm" value="${siteRegister.pwdMin}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("网站设置.注册设置.操作提示5")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("禁用用户名")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="disableUsername" maxlength="1024" class="form-control input-sm" placeholder="" rows="5" data-parsley-id="8">${siteRegister.disableUsername}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("网站设置.注册设置.操作提示6")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("注册协议")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea  name="agreement" class="form-control input-sm" placeholder="" rows="5" data-parsley-id="8">${siteRegister.agreement}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("会员显示买家注册的页面上")}<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="site:siteRegister:edit">
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