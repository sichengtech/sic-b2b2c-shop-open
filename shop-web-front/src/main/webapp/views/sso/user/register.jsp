<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('注册')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="sso"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/register.js" type="text/javascript"></script>
<!-- 全局变量 -->
<script type="text/javascript">
	var usernameMax=${siteRegister.usernameMax};
	var usernameMin=${siteRegister.usernameMin};
	var pwdMax=${siteRegister.pwdMax};
	var pwdMin=${siteRegister.pwdMin};
	var disableUsername='${siteRegister.disableUsername}';
</script>
<style type="text/css">
	.header-simple{width: 980px;margin: auto;}
	.array-msg-div{text-indent: 25px;margin-bottom: 20px;font-size: 14px;color: #4a4a4a;}
	.array-div p{margin-bottom: 5px;font-size: 14px;font-weight: bold;}
</style>
</head>
<body>
<div id="agree" style="display: none">${siteRegister.agreement}</div>
<div class="sso-main sso-reg-main sui-container" style="width: 980px;">
	<div class="sso-reg-info">
		<div class="api-reg" style="margin-top: 370px;"><p>${fns:fy('您可以使用第三方帐号直接登录：')}</p>
			<a href="#"><i class="qqicon"></i>${fns:fy('qq登录')}</a>
			<a href="#"><i class="wxicon"></i>${fns:fy('微信登录')}</a>
		</div>
	</div>
	<div class="order-tabs sso-reg-box" style="margin: 60px 0px;">
		<ul class="sui-nav nav-tabs">
			<li class="name ${ssoReg eq '1' || empty ssoReg?'active':''}"><a href="#nameRe" data-toggle="tab"><b></b>${fns:fy('使用帐号注册')}</a></li>
			<li class="mobile ${ssoReg eq '2'?'active':''}"><a href="#mobileRe" data-toggle="tab"><b></b>${fns:fy('使用手机注册')}</a></li>
		</ul>
		<sys:message content="${message}"/>
		<div class="tab-content pl20 pr20">
			<ul id="nameRe" class="tab-pane ${ssoReg eq '1' || empty ssoReg?'active':''} table-css">
				<form id="inputForm1" class="sui-form form-horizontal" action="${ctxsso}/register/save2.htm?ssoReg=1" method="post">
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">${fns:fy('用户帐号：')}</label>
						<div class="controls">
							<input type="text" name="loginName" id="loginName" class="input-xlarge" value="${userMain.loginName}" maxlength="64" placeholder="${fns:fy('请设置')}${siteRegister.usernameMin}~${siteRegister.usernameMax}${fns:fy('字节的用户名')}"><div class="sui-msg msg-naked msg-info" data-placement="top" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('请设置')}${siteRegister.usernameMin}~${siteRegister.usernameMax}${fns:fy('字节的用户名')}">
								<i class="msg-icon"></i><div class="msg-con">${fns:fy('帮助')}</div>
							</div>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label v-top">${fns:fy('邮箱地址：')}</label>
						<div class="controls">
							<input type="text" name="email" id="email" class="input-xlarge" placeholder="${fns:fy('请输入常用电子邮箱地址')}" value="${userMain.email}" maxlength="64">
							<div class="sui-msg msg-naked msg-info" data-placement="top" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('请输入常用电子邮箱地址')}">
								<i class="msg-icon"></i><div class="msg-con">${fns:fy('帮助')}</div>
							</div>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">${fns:fy('密码：')}</label>
						<div class="controls">
							<input type="password" name="password" id="password" class="input-xlarge"  placeholder="${fns:fy('请设置')}${siteRegister.pwdMin}~${siteRegister.pwdMax}${fns:fy('字节的密码')}">
							<div class="sui-msg msg-naked msg-info" data-placement="top" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('请设置')}${siteRegister.pwdMin}~${siteRegister.pwdMax}${fns:fy('字节的密码')}">
								<i class="msg-icon"></i><div class="msg-con">${fns:fy('帮助')}</div>
							</div>
						</div>
					</div>
					<div class="control-group info">
						<label for="inputInfo" class="control-label">${fns:fy('确认密码：')}</label>
						<div class="controls">
							<input type="password" name="repassword" id="repassword" class="input-xlarge" placeholder="${fns:fy('重复一次上面的密码')}">
							<div class="sui-msg msg-naked msg-info" data-placement="top" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('重复一次上面的密码')}">
								<i class="msg-icon"></i><div class="msg-con">${fns:fy('帮助')}</div>
							</div>
						</div>
					</div>
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label">${fns:fy('验证码：')}</label>
						<div class="controls">
							<input type="text" id="validateCode1" name="validateCode" class="input-Code" placeholder="${fns:fy('请输入验证码')}" value="">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh"><img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img"></a>
							<div class="sui-msg msg-naked msg-info" data-placement="top" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('请输入验证码')}">
								<i class="msg-icon"></i><div class="msg-con">${fns:fy('帮助')}</div>
							</div>
						</div>
					</div>
					<!--验证码结束-->
					<div class="control-group info">
						<label for="inputInfo" class="control-label"></label>
						<input id="agreeType1" type="checkbox"><span>${fns:fy('我已阅读并同意')}</span>
						<a class="agreement" href="javascript:void(0);">${fns:fy('《会员注册条款》')}</a>
					</div>
					<label class="control-label">
						<input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="${fns:fy('注册')}"/>
					</label>
				</form>
			</ul>
			<ul id="mobileRe" class="tab-pane ${ssoReg eq '2'?'active':''}">
				<form id="inputForm2" class="sui-form form-horizontal" action="${ctxsso}/register/save2.htm?ssoReg=2" method="post">
					<div class="control-group info">
						<label for="inputInfo" class="control-label">${fns:fy('手机号码：')}</label>
						<div class="controls">
							<input type="text" id="mobile" name="mobile" class="input-xlarge" placeholder="${fns:fy('请输入常用手机号码')}" value="${userMain.mobile}" maxlength="11">
							<div class="sui-msg msg-naked msg-info" data-placement="top" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('请输入常用手机号码')}"><i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('帮助')}</div>
							</div>
						</div>
					</div>
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label">${fns:fy('验证码：')}</label>
						<div class="controls">
							<input type="text" id="validateCode2" name="validateCode" class="input-Code" placeholder="${fns:fy('请输入验证码')}" value="">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCodeRefresh">
								<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img">
							</a>
							<div class="sui-msg msg-naked msg-info" data-placement="top" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('请输入验证码')}">
								<i class="msg-icon"></i><div class="msg-con">${fns:fy('帮助')}</div>
							</div>
						</div>
					</div>
					<!--验证码结束-->
					<!--验证码开始-->		
					<div class="control-group info">
						<label for="inputInfo" class="control-label">${fns:fy('短信验证码：')}</label>
						<div class="controls">
							<input type="text" id="smsVerification" name="smsVerification" class="input-Code" placeholder="${fns:fy('请输入验证码')}" value="">
							<button type="button" id="smsSender" class="post-code">${fns:fy('发送验证码')}</button>
							<div class="sui-msg msg-naked msg-info" data-placement="top" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('请输入验证码')}">
								<i class="msg-icon"></i><div class="msg-con">${fns:fy('帮助')}</div>
							</div>
						</div>
					</div>
					<!--验证码结束-->
					<div class="control-group info">
						<label id="checkbox2" class="">
							<label for="inputInfo" class="control-label"></label>
							<input id="agreeType2" type="checkbox"><span>${fns:fy('我已阅读并同意')}</span>
							<a class="agreement" href="javascript:void(0);">${fns:fy('《会员注册条款》')}</a>
						</label>
					</div>
					<label class="control-label">
						<input type="submit" class="sui-btn btn-xlarge btn-primary input-ok" value="${fns:fy('注册')}"/>
					</label>
				</form>
			</ul>
		</div>
	</div>
</div>
<div class="footer-simple"></div>
<!---判断是否被ifram包住,如果包住就跳出iframe--->
<script type="text/javascript">
	if (top.location != self.location)
		top.location = self.location;
</script>
</body>
</html>
