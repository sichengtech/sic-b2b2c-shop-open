<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/seller/include/taglib.jsp"%>
<div class="seller-login header border-b">
	<div class="wraper">
		<div class="login-header">
			<div class="regbutton">
				<a href="${ctxs}/login.htm">
					<i class="sui-icon icon-tb-my"></i> ${fns:fy('已有帐号？登录')}
				</a>
			</div>
			<c:if test="${isSellerRegister=='1'}">
				<div class="regbutton">
					<a href="${ctxs}/register.htm">
						<i class="sui-icon icon-tb-edit"></i> ${fns:fy('申请入驻')}
					</a>
				</div>
			</c:if>
			<c:if test="${empty isSellerRegister}">
				<div class="regbutton">
					<a href="${ctxs}/register.htm">
						<i class="sui-icon icon-tb-edit"></i> ${fns:fy('申请入驻')}
					</a>
				</div>
			</c:if>
			<div class="logo-box">
				<img src="${ctxStatic}/sicheng-seller/images/logo.png" alt="">
			</div>
			<span class="page-name">${fns:fy('商家后台')}</span>
		</div>
	</div>
</div>
