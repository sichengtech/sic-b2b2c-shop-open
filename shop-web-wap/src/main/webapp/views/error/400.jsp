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
		LoggerFactory.getLogger("400.jsp").warn(ex.getMessage(), ex);
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

//如果是App，则按《统一数据包装体和状态码》返回信息   （赵磊2019-2-7添加）
if (AppTokenUtils.isAppRequest()) {
	String errMsg=sb.toString();
	String json = AppDataUtils.getJson(AppDataUtils.STATUS_SERVER_ERROR,errMsg,null,null);
	R.writeJson(response,json,"UTF-8");
}
//如果是异步请求或是wap手机端，则直接返回信息
else if (Servlets.isAjaxRequest(request)) {
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
<%@page import="com.sicheng.common.utils4m.AppTokenUtils"%>
<%@page import="com.sicheng.common.web.R"%>
<%@page import="com.sicheng.common.utils4m.AppDataUtils"%>
<%@page import="com.sicheng.common.utils.Exceptions"%>
<%@page import="com.sicheng.common.utils.StringUtils"%>
<%@page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@include file="/views/error/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>400 - 请求出错</title>
	<style>
		html,body{ margin:0px; padding:50px; height:100%;background: none!important;}
		.conn{font-family: "Microsoft Yahei",Arial, Helvetica, sans-serif; font-size:20px;color:#99a2ae;}
		.conn a{color:#99a2ae; text-decoration:none}
		.conn a:hover{ color:#f75d5b; border-bottom: solid 2px #f75d5b;text-decoration: none!important;}
	</style>
	<%@include file="/views/error/head.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header"><h1 style='color:#505863; font-family:Impact, Haettenschweiler, "Franklin Gothic Bold", "Arial Black", sans-serif; font-size:130px;'>400 ERROR</h1></div>
		<div class="conn">
			<p>参数有误,服务器无法解析.</p>
		</div>
		<div class="errorMessage">
			<%=StringUtils.toHtml(sb.toString())%> <br/>
		</div>
		<div class="conn">
			<a href="javascript:" onclick="history.go(-1);">返回上一页</a> &nbsp;
		</div>
		<br/> <br/>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
</body>
</html>
<%
} out = pageContext.pushBody();
%>