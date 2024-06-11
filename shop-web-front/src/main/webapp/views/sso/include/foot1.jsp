<%@ page contentType="text/html; charset=UTF-8" %>
<div class="footer-main sui-container">
	<div class="wp shopcopy">
		<c:set value="${fns:getSiteInfo()}" var="siteInfo"/>
		<div class="copyright">
			${siteInfo.icp}&nbsp;&nbsp;|&nbsp;&nbsp;Copyright © 2016-<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/> SiCheng.net All rights reserved.
		</div>
	</div>	
</div>
