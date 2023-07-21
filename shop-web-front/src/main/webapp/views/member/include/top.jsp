<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/member/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>

<div class="member-header">
	<div class="sui-container">
<%--	<c:set value="${fns:getSiteInfo()}" var="siteInfo"/>--%>
<%--	<c:if test="${siteInfo.sellerLogo != null }">--%>
<%--		<div class="logo-box" style="color: white;font-size: 32px;font-weight: 500;">--%>
<%--			<a href="${ctxf}/" target="_blank">--%>
<%--			  <img src="${siteInfo.sellerLogo != null ? ctxfs : ctxStatic}${siteInfo.sellerLogo != null ? siteInfo.sellerLogo : '/sicheng-member/images/logo.png'}" alt=""/>--%>
<%--			</a>--%>
<%--		</div>--%>
<%--	</c:if>--%>
<%--	<c:if test="${siteInfo.sellerLogo == null }">--%>
		<div class="logo-box" style="color: white;font-size: 32px;font-weight: 500;">
			${fns:fy('会员中心')}
		</div>
<%--	</c:if>--%>
		<div class="sui-navbar container nav-box">
			<ul class="sui-nav">
			  <li class="${menu1id eq 'index'?'active':''}"><a href="${ctxm}/index.htm">${fns:fy('首页')}</a></li>
			  <li class="${menu1id eq 'trade'?'active':''}"><a href="${ctxm}/trade/tradeOrder/list.htm">${fns:fy('交易中心')}</a></li>
			  <li class="${menu1id eq 'collection'?'active':''}"><a href="${ctxm}/collect/memberCollectionProduct/list.htm">${fns:fy('收藏中心')}</a></li>
			  <li class="${menu1id eq 'userSevice'?'active':''}"><a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm">${fns:fy('客户服务')}</a></li>
			  <li class="${menu1id eq 'userInfo'?'active':''}"><a href="${ctxm}/user/userMember/save1.htm">${fns:fy('会员资料')}</a></li>
                <c:set value="${fns:getUserMain()}" var="userMain"/>
                <c:set value="<li class=''><a href='${ctxs}/index.htm'>${fns:fy('商家后台')}</a></li>" var="html"/>
                ${userMain.isTypeUserSeller() ? html :''}
			  <%-- <li class="${menu1id eq 'finance'?'active':''}"><a href="${ctxm}/finance/financePreDeposit/list.htm">${fns:fy('财产中心')}</a></li> --%>
			  <li class="sui-dropdown"><a href="javascript:void(0);" data-toggle="dropdown" class="dropdown-toggle">${fns:fy('账户设置')} <i class="caret"></i></a>
				<ul role="menu" class="sui-dropdown-menu">
				  <li role="presentation"><a role="menuitem" tabindex="-1" href="${ctxsso}/accountSecurity/changePassword1.htm">${fns:fy('修改密码')}</a></li>
				  <!-- <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:;">${fns:fy('实名认证')}</a></li> -->
				  <!-- <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:;">${fns:fy('版权')}</a></li> -->
					<li role="presentation"><a role="menuitem" class="zh_cn_btn" tabindex="-1" href="${ctxm}/index.htm?language=zh_CN">中文</a></li>
					<li role="presentation"><a role="menuitem" class="en_us_btn" tabindex="-1" href="${ctxm}/index.htm?language=en_US">English</a></li>
				  <li role="presentation"><a role="menuitem" tabindex="-1" href="${ctxsso}/logout.htm">${fns:fy('退出')}</a></li>
				</ul>
			  </li>
			</ul>

			<%--<div class="pull-right mt20"><img src="${ctxStatic}/sicheng-member/images/member-tel.png" alt=""/></div>--%>
			<%-- <div class="pull-right mt20"><img src="${ctxStatic}/sicheng-member/images/member-tel-eng.png" alt=""/></div> --%>
		</div>
	</div>
</div>
<!--member-header end-->