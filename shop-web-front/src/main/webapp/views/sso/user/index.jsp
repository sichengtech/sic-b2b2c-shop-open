<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('用户中心')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="ssoLeft"/>
</head>
<body>
	<div class="sso-center">
	<dl>
		<dt>
			${fns:fy('您好')},${userMain.loginName} （ID:${userMain.id}）
		</dt>
		<dd>
		<div class="lastinfo">
			<p>${fns:fy('最近一次登录：')}</p>
			<p><fmt:formatDate value="${userMain.loginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;&nbsp;&nbsp;    ${userMain.loginIp}</p>
		</div>
		<ul class="safeinfo">
			<li>
				<c:if test="${userMain.emailValidate=='1'}">
					<!-- <span class="btn"><a href="javascript:void(0);" class="sui-btn btn-bordered btn-primary">解绑</a></span> -->
					<div class="text">
						<h3>
							<strong>${fns:fy('安全邮箱')}</strong> 
							<strong>${userMain.email}</strong>
						</h3>
						<p>${fns:fy('安全邮箱将可用于重置密码，账号提现，重置支付密码，建议立即设置')}</p>
					</div>
				</c:if>
				<c:if test="${userMain.emailValidate=='0'}">
					<span class="btn"><a href="${ctxsso}/accountSecurity/bindingEmail1.htm" class="sui-btn btn-bordered btn-primary">${fns:fy('绑定')}</a></span>
					<div class="text">
						<h3>
							<strong>${fns:fy('安全邮箱')}</strong> 
							<div class="sui-msg msg-naked msg-tips">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('未绑定')}</div>
							</div>
						</h3>
						<p>${fns:fy('安全邮箱将可用于重置密码，账号提现，重置支付密码，建议立即设置')}</p>
					</div>
				</c:if>
			</li>
			<li>
				<c:if test="${userMain.mobileValidate=='1'}">
					<!-- <span class="btn"><a href="javascript:void(0);" class="sui-btn btn-bordered btn-primary">解绑</a></span> -->
					<div class="text">
						<h3>
							<strong>${fns:fy('安全手机')}</strong> 
							<strong>${userMain.mobile}</strong>
						</h3>
						<p>${fns:fy('安全手机将可用于重置密码，账号提现，重置支付密码，建议立即设置')}</p>
					</div>
				</c:if>
				<c:if test="${userMain.mobileValidate=='0'}">
					<span class="btn"><a href="${ctxsso}/accountSecurity/bindingMobile1.htm" class="sui-btn btn-bordered btn-primary">${fns:fy('绑定')}</a></span>
					<div class="text">
						<h3>
							<strong>${fns:fy('安全手机')}</strong> 
							<div class="sui-msg msg-naked msg-tips">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('未绑定')}</div>
							</div>
						</h3>
						<p>${fns:fy('安全手机将可用于重置密码，账号提现，重置支付密码，建议立即设置')}</p>
					</div>
				</c:if>
			</li>

		</ul>
		</dd>
	</dl>
	</div>
</body>
</html>
