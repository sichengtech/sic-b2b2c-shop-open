<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('用户中心')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="sso"/>
<!-- 业务js -->
<script src="${ctx}/views/sso/user/activeMail.js" type="text/javascript"></script>
</head>
<body>
	<div class="order-tabs sso-reg-box reg-ok" style="text-align: center;">
		<input type="hidden" class="status" value="${status}"/>
		<c:choose>
			<c:when test="${status == '2'}">
				<h2 style="text-align: center;">
					<i class="sui-icon icon-tb-emoji"></i> ${message}
				</h2>
				<p>${fns:fy('请妥善保管您的帐号及密码。')}</p>
				<br>
				<p class="sui-btn disabled btn-success mt20">${fns:fy('即将跳转至首页,将在')} <span id="mes">5</span> ${fns:fy('秒钟后返回首页！')}</p>
			</c:when>
			<c:otherwise>
				<h2 style="text-align: center;">
					<i class="sui-icon icon-tb-emoji"></i> ${message}
				</h2>
				<br>
				<p class="sui-btn disabled btn-success mt20">${fns:fy('请联系管理员')}</p>
			</c:otherwise>
		</c:choose>
	</div>
	</div>
</html>
