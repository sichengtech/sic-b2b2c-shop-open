<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('绑定安全邮箱')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="ssoLeft"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/bindingEmail.js" type="text/javascript"></script>
<style>
	.sui-form.form-horizontal .control-label { width: 180px; display: block; }
	.infomain .control-group .controls { width: 390px; }
</style>
</head>
<body>
	<div class="sso-center">
		<dl>
			<dt>${fns:fy('绑定安全邮箱')}</dt>
			<dd>
				<div class="infomain sui-form form-horizontal">
					<sys:message content="${message}"/>
					<form id="inputForm" class="form-signin sui-form form-horizontal" action="${ctxsso}/accountSecurity/bindingEmail2.htm" method="post">
						<div class="control-group">
						<label class="control-label">${fns:fy('邮箱地址：')}</label>
							<div class="controls">
								<input type="text" id="email" name="email" value="${userMain.email}" class="input-xlarge input-xfat" placeholder="${fns:fy('输入邮箱地址')}" style="width: 261px;">
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('安全邮箱')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('图片验证码：')}</label>
							<div class="controls">
								<input type="text" id="validateCode" name="validateCode" class="input-xfat" placeholder="${fns:fy('输入验证码')}">
								<a id="refresh" href="javascript:" class="mid validateCodeRefresh" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());"><img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img"></a>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('验证码')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" style="display: block;">${fns:fy('动态码：')}</label>
							<div class="controls">
								<input type="text" id="emailVerification" name="emailVerification" class="input-xfat" placeholder="${fns:fy('输入动态码')}">
								<button type="button" id="emailSender" class="sui-btn input-xfat">${fns:fy('发送动态码')}</button>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请注意查收')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
								<button type="submit" class="sui-btn btn-primary input-xfat">${fns:fy('绑定')}</button>
							</div>
						</div>
					</form>	
				</div>
			</dd>
		</dl>
	</div>
</body>
</html>
