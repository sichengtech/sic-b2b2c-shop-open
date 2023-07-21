<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="author" content="SiC B2B2C Shop"/>
<meta name="renderer" content="webkit">
<!-- 图标 -->
<link type="image/x-icon" rel="shortcut icon" href="${ctxStatic}/images/shop-ico.ico">
<%-- 引入title部分 --%>
<title><sitemesh:title/>-${fns:fy("管理后台")} Powered by SiC</title>
<%-- 引入头部文件 --%>
<%@ include file="../include/head.jsp"%>
<%-- 引入head部分 --%>
<sitemesh:head/>
</head>
<body class="sticky-header ${cookie.fdp_menuFoldingState.value=='0'?'left-side-collapsed':''}">
	<section>
		<!-- 左侧栏 left side start-->
		<%@ include file="../include/left.jsp"%>
		<!-- 左侧栏 left side end-->

		<!-- 右侧栏主区main content start-->
		<div class="main-content">
			<!-- 一级菜单 start-->
			<%@ include file="../include/top.jsp"%>
			<!-- 一级菜单 end-->
			<!--body wrapper start-->
			<div>
				<sitemesh:body/><%-- 引入body部分 --%>
			</div>
			<!--body wrapper end-->
			<!--footer section start-->
			<footer class="footer">
				${fns:getConfig('productName')}&nbsp;${fns:getConfig('version')} &copy; 2016-<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/>
				<a style="text-decoration:none;color:inherit;" target="_blank" href="http://www.sicheng.net"> sicheng.net</a>
			</footer>
			<!--footer section end-->
		</div>
		<!-- 右侧栏主区main content end-->
	</section>
<%-- 引入脚部文件 --%>
<%@ include file="../include/foot.jsp"%>
</body>
</html>