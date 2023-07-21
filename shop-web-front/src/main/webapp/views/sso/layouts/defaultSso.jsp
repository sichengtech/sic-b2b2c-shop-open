<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/sso/include/taglib.jsp"%>
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
<link type="image/x-icon" rel="shortcut icon" href="${ctxStatic}/images/shop-ico.ico"/>

<%-- 引入title部分 --%>
<title><sitemesh:title/> - ${fns:fy('用户中心')}</title>
<%-- 引入头部文件 --%>
<%@ include  file="../include/head.jsp"%>
<%-- 引入head部分 --%>
<sitemesh:head/>
</head>
<body>
<%@ include  file="../include/top.jsp"%>
<div class="sso-main">
	<!-- 右侧栏主区main content start-->
	<!--body wrapper start-->
		<div>
			<sitemesh:body/><%-- 引入body部分 --%>
		</div>
	<!--body wrapper end-->
	<!-- 右侧栏主区main content end-->
</div>
<%-- 引入脚部文件 --%>
<%@ include  file="../include/foot1.jsp"%>
<%@ include  file="../include/foot.jsp"%>
</body>
</html>