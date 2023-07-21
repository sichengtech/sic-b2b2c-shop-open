<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
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

<%-- 引入title部分 --%>
<title><sitemesh:title/> - ${fns:fy('会员中心')}</title>
<%-- 引入头部文件 --%>
<%@ include  file="../include/head.jsp"%>
<%-- 引入head部分 --%>
<sitemesh:head/>
</head>
<body>
<%@ include  file="../include/top.jsp"%>
<%@ include  file="../include/top_info.jsp"%>
<div class="sui-container">
	<!-- 左侧栏 left side start-->
	<%@ include  file="../include/left.jsp"%>
	<!-- 左侧栏  left side end-->

	<!-- 右侧栏主区main content start-->
	<div class="main-content mt20 box clearfix" style="overflow:visible">
		<!--body wrapper start-->
			<sitemesh:body/><%-- 引入body部分 --%>
		<!--body wrapper end-->
	</div>
	<!-- 右侧栏主区main content end-->
</div>
<%-- 引入脚部文件 --%>
<%@ include  file="../include/foot1.jsp"%>
<%@ include  file="../include/foot.jsp"%>
</body>
</html>