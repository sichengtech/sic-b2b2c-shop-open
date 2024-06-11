<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('登录')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="sso"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/login.js" type="text/javascript"></script>
<style type="text/css">.sso-main{border-top:0px;}</style>
</head>
<body>
<div class="sso-main" style="background-image:url(${ctxfs}/tqm_pictures/logo/123.jpg); background-position:center; background-repeat:no-repeat; width:100%;">
	<div class="sui-container">
		<div class="order-tabs sso-login-box">
			<ul class="sui-nav nav-tabs">
				<li class="${ssoReg eq '1' || empty ssoReg?'active':''}"><a href="#all" data-toggle="tab">${fns:fy('帐号登录')}</a></li>
				<li class="line"></li>
				<li class="${ssoReg eq '2'?'active':''}"><a href="#payment" data-toggle="tab">${fns:fy('手机动态登录')}</a></li>
			</ul>
			<sys:message content="${message}"/>
			<div class="tab-content pl20 pr20">
				<ul id="all" class="tab-pane ${ssoReg eq '1' || empty ssoReg?'active':''} table-css">
					<form id="inputForm1" class="form-signin" action="${ctxsso}/login/login.htm" method="post">
					<input type="hidden" name="ssoReg" value="1">
					<label class="control-label label-name">
						<i class="icon"></i>
					</label>
						<input type="text" name="loginName" value="${loginName}" class="input-username" placeholder="${fns:fy('请输入会员帐号')}" value="">
					<label class="control-label label-password">
						<i class="icon"></i>
					</label>
						<input type="password" name="password" class="input-password" placeholder="${fns:fy('请输入密码')}">
					<c:if test="${isValidateCodeLogin}">
						<!--验证码开始-->
						<label class="control-label label-code"><i class="icon"></i>
							<input type="text" id="validateCode1" name="validateCode" class="input-Code" placeholder="${fns:fy('请输入验证码')}" value="">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" 
							class="mid validateCodeRefresh">
							<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img"></a>
						</label>
						<!--验证码结束-->
					</c:if>
					<label class="control-label"><input type="submit" class="input-ok" value="${fns:fy('登录')}"/></label>
					<div class="block-h20">
						<div class="forgetpassword pull-right"><a href="${ctxsso}/forgetPassWord/save1.htm">${fns:fy('忘记密码？')}</a></div>
						<div class="textl"><input type="checkbox" class="checkbox" value="1" name="rememberMe"> ${fns:fy('记住我（公共场所慎用）')}</div>
					</div>
					</form>
				</ul>
				<ul id="payment" class="tab-pane ${ssoReg eq '2'?'active':''}">
					<form id="inputForm2" class="form-signin" action="${ctxsso}/login/login.htm" method="post">
						<input type="hidden" name="ssoReg" value="2">
						<label class="control-label label-mobile"><i class="icon"></i>
							<input type="text" name="loginName" value="${loginName}" class="input-mobile" placeholder="${fns:fy('请输入手机号码')}" value="">
						</label>
						<label class="control-label label-code"><i class="icon"></i>
							<input type="text" id="validateCode2" name="validateCode" class="input-Code" placeholder="${fns:fy('请输入验证码')}" value="">
							<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" 
							class="mid validateCodeRefresh">
							<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img"></a>
						</label>
						<label class="control-label label-code"><i class="icon"></i>
							<input type="text" id="password1" name="password" class="input-Code" placeholder="${fns:fy('请输入验证码')}" value="">
							<button id="smsSender" type="button" class="post-code" style="height: 42px;">${fns:fy('发送验证码')}</button>
						</label>
						<label class="control-label">
							<input type="submit" class="input-ok" value="${fns:fy('登录')}"/>
						</label>
					</form>
				<div class="block-h20">
					<div class="forgetpassword pull-right"><a href="${ctxsso}/forgetPassWord/save1.htm">${fns:fy('忘记密码？')}</a></div>
				</div>
				</ul>
			</div>
			<div class="api-login">
				<div class="reg">
					<a href="${ctxsso}/register/save1.htm"><i class="regicon"></i>${fns:fy('立即注册')}</a>
				</div>
			</div>
		</div>
		<!--order-tabs end-->
	<!--logistics end-->
	</div>
</div>
<div class="footer-simple"></div>
<script type="text/javascript" src="${ctxStatic}/sui/1.5.1/sui.min.js"></script></body>
</html>