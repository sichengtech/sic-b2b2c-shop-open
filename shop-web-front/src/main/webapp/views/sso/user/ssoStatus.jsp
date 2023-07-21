<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('状态')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="ssoLeft"/>
</head>
<body>
	<div class="order-tabs sso-reg-box reg-v">
		<h2>
			<i class="sui-icon icon-tb-emoji"></i> 
			${fns:fy('恭喜你：')}
			<c:if test="${successStatus=='1'}">
				${fns:fy('绑定邮箱成功')}
			</c:if>
			<c:if test="${successStatus=='2'}">
				${fns:fy('绑定手机成功')}
			</c:if>
			<c:if test="${successStatus=='3'}">
				${fns:fy('修改支付密码成功')}
			</c:if>
		</h2>
		<c:if test="${successStatus=='1'}">
			<p>${fns:fy('恭喜你：绑定邮箱成功')}</p>
		</c:if>
		<c:if test="${successStatus=='2'}">
			<p>${fns:fy('恭喜你：绑定手机成功')}</p>
		</c:if>
		<c:if test="${successStatus=='3'}">
			<p>${fns:fy('恭喜你：修改支付密码成功')}</p>
		</c:if>
	</div>
</html>
