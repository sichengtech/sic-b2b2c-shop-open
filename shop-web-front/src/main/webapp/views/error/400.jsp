<%
response.setStatus(400);

// 获取异常类
Throwable ex = Exceptions.getThrowable(request);

// 编译错误信息
StringBuilder sb = new StringBuilder("错误信息：\n");
if (ex != null) {
	if (ex instanceof BindException) {
		for (ObjectError e : ((BindException)ex).getGlobalErrors()){
			sb.append("☆" + e.getDefaultMessage() + "(" + e.getObjectName() + ")\n");
		}
		for (FieldError e : ((BindException)ex).getFieldErrors()){
			sb.append("☆" + e.getDefaultMessage() + "(" + e.getField() + ")\n");
		}
		//LoggerFactory.getLogger("400.jsp").warn(ex.getMessage(), ex);
	}else if (ex instanceof ConstraintViolationException) {
		for (ConstraintViolation<?> v : ((ConstraintViolationException)ex).getConstraintViolations()) {
			sb.append("☆" + v.getMessage() + "(" + v.getPropertyPath() + ")\n");
		}
	} else {
		//sb.append(Exceptions.getStackTraceAsString(ex));
		sb.append("☆" + ex.getMessage());
	}
} else {
	sb.append("未知错误.\n\n");
}

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	out.print(sb);
}

// 输出异常信息页面
else {
%>
<%@page import="javax.validation.ConstraintViolation"%>
<%@page import="javax.validation.ConstraintViolationException"%>
<%@page import="org.springframework.validation.BindException"%>
<%@page import="org.springframework.validation.ObjectError"%>
<%@page import="org.springframework.validation.FieldError"%>
<%@page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@page import="com.sicheng.common.web.Servlets"%>
<%@page import="com.sicheng.common.utils.Exceptions"%>
<%@page import="com.sicheng.common.utils.StringUtils"%>
<%@page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>400</title>
	<%@include file="/views/seller/include/head.jsp" %>
</head>
<body class="error">
	<div class="error_wraper">
		<div class="r"><img src="${ctxStatic}/sicheng-seller/images/error_400_pic.png" alt=""/></div>
		<img src="${ctxStatic}/sicheng-seller/images/error_400.png" alt=""/>
		<div class="text">
			<p class="h1">${empty message?'参数有误,服务器无法解析.':message }</p>
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