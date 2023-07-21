<%
response.setStatus(403);

//获取异常类
Throwable ex = Exceptions.getThrowable(request);

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	if (ex!=null && StringUtils.startsWith(ex.getMessage(), "msg:")){
		out.print(StringUtils.replace(ex.getMessage(), "msg:", ""));
	}else{
		out.print("操作权限不足.");
	}
}

//输出异常信息页面
else {
%>
<%@page import="com.sicheng.common.web.Servlets"%>
<%@page import="com.sicheng.common.utils.Exceptions"%>
<%@page import="com.sicheng.common.utils.StringUtils"%>
<%@page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>403 - 操作权限不足</title>
	<%@include file="/views/seller/include/head.jsp" %>
</head>
<body class="error">
	<div class="error_wraper">
		<div class="r"><img src="${ctxStatic}/sicheng-seller/images/error_403_pic.png" alt=""/></div>
		<img src="${ctxStatic}/sicheng-seller/images/error_403.png" alt=""/>
		<div class="text">
			<p class="h1">对不起，您的操作权限不足。</p>
			<p class="h2">您可以继续去浏览其他网页。</p>
			<a href="javascript:" onclick="history.go(-1);" class="button">返回上一页</a>
			<a href="${ctxs}/index.htm" class="button">返回首页</a>
		</div>
	</div>
</body>
</html>
<%
} out = pageContext.pushBody();
%>