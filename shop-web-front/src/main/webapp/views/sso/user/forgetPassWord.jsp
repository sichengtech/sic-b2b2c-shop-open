<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('忘记密码')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="sso"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/forgetPassWord.js" type="text/javascript"></script>
<!-- 全局变量 -->
<script type="text/javascript">
	var pwdMax=${siteRegister.pwdMax};
	var pwdMin=${siteRegister.pwdMin};
</script>
</head>
<body class="forget-main">
<div class="sui-container">
<div class="forget-main">
	<div class="box">
		<div class="forget-box">
			<dl>
				<dt class="box-header noborder">
				<ul class="sui-nav nav-tabs">
<%--					<h2 class="pull-left"><span>${fns:fy('找回密码')}</span></h2>--%>
					<li class="${ssoReg eq '1' || empty ssoReg?'active':''}"><a href="#emailRe" data-toggle="tab">${fns:fy('使用邮箱找回密码')}</a></li>
					<c:if test="${isEnglish eq '0'}">
						<li class="${ssoReg eq '2' ?'active':''}"><a href="#mobileRe" data-toggle="tab">${fns:fy('使用手机找回密码')}</a></li>
					</c:if>
					<div class="clear"></div>
				</ul>
				</dt>
				<dd>
				<sys:message content="${message}"/>
				 <div class="tab-content">
				 	<p class="message"></p>
					<div id="emailRe" class="tab-pane ${ssoReg eq '1' || empty ssoReg?'active':''}">
						<form id="inputForm1" class="form-signin" action="${ctxsso}/forgetPassWord/save2.htm?ssoReg=1" method="post">
							<label class="control-label label-name"><i class="icon"></i>
								<input type="text" id="loginName1" name="loginName" class="input-username" placeholder="${fns:fy('请输入帐号')}" value="${userMain.loginName}"/>
							</label>
							<label class="control-label label-email"><i class="icon"></i>
								<input type="text" id="email1" name="email" class="input-email" placeholder="${fns:fy('请输入邮箱地址')}" value="${userMain.email}"/>
							</label>
							<label class="control-label label-code"><i class="icon"></i>
								<input type="text" id="validateCode1" name="validateCode" class="input-code pull-left" placeholder="${fns:fy('请输入验证码')}"/>
								<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh">
									<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img">
								</a>
							</label>
							<label class="control-label label-code"><i class="icon"></i>
								<input type="text" id="emailVerification1" name="emailVerification" class="input-code pull-left" placeholder="${fns:fy('请输入验证码')}" id="code"/>
								<input type="button" id="emailSender" class="pull-right sui-btn btn-xlarge input-post" value="${fns:fy('发送邮件验证码')}"/>
								<div class="clear"></div>
							</label>
							<div class="clear"></div>
							<div id="resetpw-mail" class="resetpw" style="display:block">
								<span>${fns:fy('设置新密码')}</span>
								<div class="clear"></div>
								<label class="control-label label-password"><i class="icon"></i>
									<input type="password" id="password1" name="password" class="input-password" placeholder="${fns:fy('输入新密码')}"/>
								</label>
								<label class="control-label label-password"><i class="icon"></i>
									<input type="password" id="repassword1" name="repassword" class="input-password" placeholder="${fns:fy('再次输入新密码')}"/>
								</label>
								<label class="control-label input-ok">
									<input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="${fns:fy('重置密码')}"/>
								</label>
								<div class="clear"></div>
							</div>
						</form>
					</div>
					 <div id="mobileRe" class="tab-pane ${ssoReg eq '2'?'active':''}">
						 <form id="inputForm2" class="form-signin" action="${ctxsso}/forgetPassWord/save2.htm?ssoReg=2" method="post">
							 <label class="control-label label-mobile"><i class="icon"></i>
								 <input type="text" id="mobile2" name="mobile" class="input-mobile" placeholder="${fns:fy('请输入手机号码')}" value="${userMain.mobile}">
							 </label>
							 <label class="control-label label-code"><i class="icon"></i>
								 <input type="text" id="validateCode2" name="validateCode" class="input-code pull-left" placeholder="${fns:fy('请输入验证码')}"/>
								 <a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh">
									 <img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img">
								 </a>
							 </label>
							 <label class="control-label label-code"><i class="icon"></i>
								 <input type="text" id="smsVerification1" name="smsVerification" class="input-code pull-left" placeholder="${fns:fy('请输入验证码')}">
								 <input type="button" id="smsSender" class="pull-right sui-btn btn-xlarge input-post" value="${fns:fy('发送短信验证码')}"/>
								 <div class="clear"></div>
							 </label>
							 <div class="clear"></div>
							 <div id="resetpw-mail" class="resetpw" style="display:block">
								 <span>${fns:fy('设置新密码')}</span>
								 <div class="clear"></div>
								 <label class="control-label label-password"><i class="icon"></i>
									 <input type="password" id="password2" name="password" class="input-password" placeholder="${fns:fy('输入新密码')}"/>
								 </label>
								 <label class="control-label label-password"><i class="icon"></i>
									 <input type="password" id="repassword2" name="repassword" class="input-password" placeholder="${fns:fy('再次输入新密码')}"/>
								 </label>
								 <label class="control-label input-ok">
									 <input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="${fns:fy('重置密码')}"/>
								 </label>
								 <div class="clear"></div>
							 </div>
						 </form>
					 </div>
				 </div>
				</dd>
			</dl>
		</div>
	</div>
	</div>
</div>
<!---判断是否被ifram包住,如果包住就跳出iframe--->
<script type="text/javascript">
	if (top.location != self.location)
		top.location = self.location;
</script>
<div class="footer-simple"></div>
<script type="text/javascript" src="${ctxStatic}/sui/1.5.1/sui.min.js"></script>
</body>
</html>
