<%
response.setStatus(403);

//获取异常类
Throwable ex = Exceptions.getThrowable(request);

//如果是App，则按《统一数据包装体和状态码》返回信息   （赵磊2019-2-7添加）
if (AppTokenUtils.isAppRequest()) {
	String errMsg="操作权限不足.";
	String json = AppDataUtils.getJson(AppDataUtils.STATUS_SERVER_ERROR,errMsg,null,null);
	R.writeJson(response,json,"UTF-8");
}
//如果是异步请求或是wap手机端，则直接返回信息
else if (Servlets.isAjaxRequest(request)) {
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
	<title>403 - 操作权限不足</title>
	<style>
		html,body{ margin:0px; padding:50px; height:100%;background: none!important;}
		.conn{font-family: "Microsoft Yahei",Arial, Helvetica, sans-serif; font-size:40px;color:#99a2ae;}
		.conn a{color:#99a2ae; text-decoration:none}
		.conn a:hover{ color:#f75d5b; border-bottom: solid 2px #f75d5b;text-decoration: none!important;}
	</style>
	<%@include file="/views/error/head.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header"><h1 style='color:#505863; font-family:Impact, Haettenschweiler, "Franklin Gothic Bold", "Arial Black", sans-serif; font-size:130px;'>403 ERROR</h1></div>
		<%
			if (ex!=null && StringUtils.startsWith(ex.getMessage(), "msg:")){
				out.print("<div>"+StringUtils.replace(ex.getMessage(), "msg:", "")+" <br/> <br/></div>");
			}
		%>
		<div class="conn">
			<p style="margin-top: 65px;">对不起，您的操作权限不足。</p>
			<p style="margin-top: 35px;">请点击  <a href="javascript:" onclick="history.go(-1);">[ 这里 ]</a> 返回上一页。</p>
		</div>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
</body>
</html>
<%
} out = pageContext.pushBody();
%>