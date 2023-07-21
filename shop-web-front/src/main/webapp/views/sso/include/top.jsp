<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/sso/include/taglib.jsp"%>
<%@ page import="com.sicheng.sso.utils.SsoUtils"%>
<%@ page import="com.sicheng.admin.sso.entity.UserMain"%>
<%@ page import="com.sicheng.sso.shiro.SsoAuthorizingRealm.Principal"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<div class="header-simple sui-container" >
<div class="sui-container" style="height: 90px;">
	<%
		Principal principal = SsoUtils.getPrincipal();
	%>
	<% if(principal != null) {%>
		<div class="nav">${fns:fy('您登录的帐号：')}<%= SsoUtils.getUserMain().getLoginName() %>
			<a href="${ctxsso}/logout.htm">[${fns:fy('退出')}]</a></div>
	<%-- <%} else {%>
		<div class="nav">
			<a href="#">商城首页</a>
			<a href="#">商家入驻</a>
			<a href="#">商城首页</a>
			<a href="#">商家入驻</a>
			<a href="#">商城首页</a>
		</div> --%>
	<%}%>

	<c:set value="${fns:getSiteInfo()}" var="siteInfo"/>
	<div class="logo" style="margin-bottom: 20px;">
		<a href="/" target="_blank">
			<img src="${siteInfo.siteLogo != null ? ctxfs : ctxStatic}${siteInfo.siteLogo != null ? siteInfo.siteLogo : '/sicheng-sso/images/login.png'}"
				 class="logo" style="height: 60px;margin-top: 20px;margin-right: 20px;">
		</a>
		<c:if test="${empty topStatus}">
			<span class="page-title" style="display: block;line-height: 95px">${fns:fy('用户中心')}</span>
		</c:if>
		<c:if test="${topStatus=='1'}">
			<span class="page-title" style="display: block;line-height: 95px">${fns:fy('会员登录')}</span>
		</c:if>
		<c:if test="${topStatus=='2'}">
			<span class="page-title" style="display: block;line-height: 95px">${fns:fy('会员注册')}</span>
		</c:if>
		<c:if test="${topStatus=='3'}">
			<span class="page-title" style="display: block;line-height: 95px">${fns:fy('忘记密码')}</span>
		</c:if>
	</div>
</div>
</div>
