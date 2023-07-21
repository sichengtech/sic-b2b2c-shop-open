<%@ page contentType="text/html; charset=UTF-8" %>
<div class="footer-main">
	<div class="wp shopcopy">
		<c:set value="${fns:getSiteInfo()}" var="siteInfo"/>
		<div class="copyright">
			${siteInfo.icp}&nbsp;&nbsp;|&nbsp;&nbsp;${fns:getConfig('productName')}&nbsp;${fns:getConfig('version')} &copy; 2016-<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/>
			<a style="text-decoration:none;color:inherit;" target="_blank" href="http://www.sicheng.net"> SiCheng.net</a> All rights reserved.
		</div>
	</div>	
</div>