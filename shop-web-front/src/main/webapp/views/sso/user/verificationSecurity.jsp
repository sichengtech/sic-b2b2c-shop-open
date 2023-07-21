<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('安全验证')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="ssoLeft"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/verificationSecurity.js" type="text/javascript"></script>
<style>
	.sui-form.form-horizontal .control-label { width: 180px; display: block; }
</style>
</head>
<body>
	<div class="sso-center">
		<dl>
			<dt>${fns:fy('安全验证')}</dt>
			<!-- 提示信息 start -->
			<div class="sui-msg msg-tips msg-block" style="margin-top: 10px;">
				<div class="msg-con">
					<ul>
						<h4>${fns:fy('提示信息')}</h4>
						<li>${fns:fy('用户在进行安全操作会进入此页面。')}</li>
						<li>${fns:fy('例如：修改账号密码，修改支付密码，绑定安全邮箱，绑定安全手机，提现账户余额。')}</li>
					</ul>
				</div>
				<s class="msg-icon" style="margin-top: 10px"></s>
			</div>
			<!-- 提示信息 end -->
			<dd>
				<div class="infomain sui-form form-horizontal">
					<sys:message content="${message}"/>
					<form id="inputForm" class="form-signin sui-form form-horizontal" action="${ctxsso}/accountSecurity/verificationSecurity2.htm" method="post">
						<input type="hidden" id="path" name="path"  value="${path}">
						<input type="hidden" id="email" name="email" value="${email}">
						<input type="hidden" id="mobile" name="mobile" value="${mobile}">
						<div class="control-group">
							<label class="control-label">${fns:fy('验证方式：')}</label>
							<div class="controls">
								<span class="sui-dropdown dropdown-bordered select">
									<span class="dropdown-inner" style="width: 276px;">
										<a role="button" data-toggle="dropdown" href="javascript:void(0);" class="dropdown-toggle input-xfat">
											<input type="hidden" name="validatekey" id="validatekey" ><i class="caret"></i>
											<span id="validateMode">${fns:fy('请选择')}</span>
										</a>
									 	<ul role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="provinceAttr">
									 		<c:forEach items="${map}" var="m">
												<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${m.key}">${m.value}</a></li>
									 		</c:forEach>
									 	</ul>
									</span>
							 	</span>
								<div class="sui-msg msg-tips msg-naked">
								  	<div class="msg-con">${fns:fy('选择验证方式')}</div>
								  	<s class="msg-icon"></s>
								</div>
							</div>
						</div>	  
						<div class="control-group">
							<label class="control-label">${fns:fy('图片验证码：')}</label>
							<div class="controls">
								<input type="text" id="validateCode" name="validateCode" class="input-xfat input-Code" placeholder="${fns:fy('输入验证码')}">
								<a id="refresh" href="javascript:" class="mid validateCodeRefresh" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());"><img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img"></a>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('图片验证码')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">${fns:fy('动态码：')}</label>
							<div class="controls">
								<input type="text" id="verification" name="verification" class="input-xfat" placeholder="${fns:fy('输入动态码')}">
								<button type="button" id="sender" class="sui-btn input-xfat">${fns:fy('发送动态码')}</button>
								<div class="sui-msg msg-tips msg-naked">
									<div class="msg-con">${fns:fy('请注意查收')}</div>
									<s class="msg-icon"></s>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
								<button type="submit" class="sui-btn btn-primary input-xfat"> ${fns:fy('确定')} </button>
							</div>
						</div>
					</form>
				</div>
			</dd>
		</dl>
	</div>
</html>
