<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/sso/include/taglib.jsp"%>
<c:set value="${fns:getSiteInfo()}" var="siteInfo"/>
<div class="seller-login header border-b">
	<div class="wraper">
		<div class="logo-box"><a target="_blank" href="${ctxf}/index.htm"><img src="${siteInfo.siteLogo != null ? ctxfs : ctxStatic}${siteInfo.siteLogo != null ? siteInfo.siteLogo : '/sicheng-seller/images/logo.png'}" alt="" style="width:133px;height: 35px;"/></a></div>
		<div class="sui-navbar container nav-box">
		<div class="navbar-inner"><a href="javascript:void(0)" class="sui-brand">${fns:fy('欢迎您，欢迎光临。')}</a>
			<ul class="sui-nav pull-right">
				<c:set value="${fns:getUserMain()}" var="userMain"/>
				<li><a target="_blank" href="${ctxm}/index.htm"><i class="sui-icon icon-tb-myfill"></i> ${fns:fy('会员中心')}</a></li>
				<li class="sui-dropdown"><a href="javascript:void(0);" data-toggle="dropdown" class="dropdown-toggle"><i class="sui-icon icon-tb-my"></i> ${userMain.loginName} <i class="caret"></i></a>
					<ul role="menu" class="sui-dropdown-menu">
						<li role="presentation"><a role="menuitem" tabindex="-1" href="${ctxsso}/accountSecurity/changePassword1.htm">${fns:fy('修改密码')}</a></li>
						<li role="presentation"><a role="menuitem" tabindex="-1" href="${ctxsso}/logout.htm">${fns:fy('退出')}</a></li>
					</ul>
				</li>
			</ul>
		</div>
		</div>
	</div>
</div>

