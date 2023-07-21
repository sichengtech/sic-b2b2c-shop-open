<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('验证邮箱')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="ssoLeft"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/verificationEmail.js" type="text/javascript"></script>
</head>
<body>
	<sys:message content="${message}"/>
	<div class="order-tabs sso-reg-box reg-v">
		<h2><i class="sui-icon icon-tb-emoji"></i> ${fns:fy('请验证邮箱，完成注册。')}</h2>
		<p>${fns:fy('我们已将验证邮件发送至邮箱：')}${userMain.email}<br>
		${fns:fy('点击邮件内的链接即可完成注册。')}</p><br>
		<br><br><br><br><br>
		<hr>
		<h3>${fns:fy('没有收到验证邮件，怎么办？')}</h3>
		<p>${fns:fy('邮箱填写错误？')}<a id="change1" href="javascript:void(0);"> ${fns:fy('换个邮箱')}</a><br></p>
		<div id="change2" style="display:none">
			<form id="inputForm1" class="sui-form form-inline" action="${ctxsso}/email/changeEmail.htm" method="post">
				<input type="text" id="email" name="email" placeholder="${fns:fy('请输入正确的邮箱地址')}" class=""><br>
				<label class="control-label label-code"><i class="icon"></i>
					<input type="text" id="validateCode1" name="validateCode" class="input-Code" placeholder="${fns:fy('请输入验证码')}" value="">
					<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" 
					class="mid validateCodeRefresh">
					<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img"></a>
				</label><br>
				<button type="submit" class="sui-btn btn-primary">${fns:fy('确认')}</button>
			</form>
		</div>
		<p>${fns:fy('看看是否在邮箱的垃圾邮件、广告邮件的目录里')}<br>${fns:fy('稍等几分钟，若还未收到验证邮件，')}<a id="send1" href="javascript:void(0);"> ${fns:fy('重新发送验证邮件')} </a>
		<div id="send2" style="display:none">
			<form id="inputForm2" class="sui-form form-inline" action="${ctxsso}/email/sendActiveMail.htm" method="post">
				<label class="control-label label-code"><i class="icon"></i>
					<input type="text" id="validateCode2" name="validateCode" class="input-Code" placeholder="${fns:fy('请输入验证码')}" value="">
					<a id="refresh" href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" 
					class="mid validateCodeRefresh">
					<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" id="validate_map" title="${fns:fy('验证码')}" class="input-Code-img"></a>
				</label><br>
				<button type="submit" class="sui-btn btn-primary">${fns:fy('重新发送邮件')}</button>
			</form>
		</div>
		</p>
	</div>
	</div>
	<!--order-tabs end-->
<!--logistics end-->
</html>
