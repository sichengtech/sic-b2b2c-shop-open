<%@ page contentType="text/html; charset=UTF-8" %>
<div class="sso-menu box mt20">
	<c:set value="${fns:getConfig('sys.isEnglish')}" var="isEnglish"/>
	<dl>
		<dt><i class="sui-icon icon-tb-unfold pull-right"></i><i class="sui-icon icon-tb-my"></i> ${fns:fy('账户安全')}</dt>
		<dd><a style="${highlight eq 'changePassword'?'color: #e45050;':''}" href="${ctxsso}/accountSecurity/changePassword1.htm">• ${fns:fy('修改账号密码')}</a></dd>
		<%-- <dd><a style="${highlight eq 'changePayPassword'?'color: #e45050;':''}" href="${ctxsso}/accountSecurity/changePayPassword1.htm">• ${fns:fy('修改支付密码')}</a></dd> --%>
		<dd><a style="${highlight eq 'bindingEmail'?'color: #e45050;':''}" href="${ctxsso}/accountSecurity/bindingEmail1.htm">• ${fns:fy('绑定安全邮箱')}</a></dd>
		<c:if test="${isEnglish eq '0'}">
		<dd><a style="${highlight eq 'bindingMobile'?'color: #e45050;':''}" href="${ctxsso}/accountSecurity/bindingMobile1.htm">• ${fns:fy('绑定安全手机')}</a></dd>
		</c:if>
	</dl>
	<%-- <dl>
		<dt><i class="sui-icon icon-tb-unfold pull-right"></i><i class="sui-icon icon-tb-my"></i> ${fns:fy('第三方登录')}</dt>
		<dd><a style="${highlight eq 'thirdDinding'?'color: #e45050;':''}" href="${ctxsso}/third/binding.htm">• ${fns:fy('绑定')}</a></dd>
	</dl> --%>
	<dl>
		<dt><i class="sui-icon icon-tb-unfold pull-right"></i><i class="sui-icon icon-tb-my"></i> ${fns:fy('账号中心')}</dt>
		<dd><a target="_blank" href="${ctxf}/index.htm">• ${fns:fy('商城首页')}</a></dd>
		<dd><a target="_blank" href="${ctxm}/index.htm">• ${fns:fy('会员中心')}</a></dd>
		<dd><a target="_blank" href="${ctxs}/index.htm">• ${fns:fy('商家中心')}</a></dd>
	</dl>
</div>
