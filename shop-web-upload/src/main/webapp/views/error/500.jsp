<%
response.setStatus(500);

// 获取异常类
Throwable ex = Exceptions.getThrowable(request);
if (ex != null){
	//LoggerFactory.getLogger("500.jsp").error(ex.getMessage(), ex);
}

// 编译错误信息
StringBuilder sb = new StringBuilder("错误信息：\n");
if (ex != null) {
	sb.append(Exceptions.getStackTraceAsString(ex));
} else {
	sb.append("未知错误.\n\n");
}

// 如果是异步请求或是手机端，则直接返回信息
//if (Servlets.isAjaxRequest(request)) {
if (false) {
	out.print(sb);
}

// 输出异常信息页面
else {
%>
<%@page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@page import="com.sicheng.common.web.Servlets"%>
<%@page import="com.sicheng.common.utils.Exceptions"%>
<%@page import="com.sicheng.common.utils.StringUtils"%>
<%@page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@include file="/views/upload/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>500 - 系统内部错误</title>
	<style>
		html,body{margin:0px; padding:0px 0px 0px 50px; height:100%;background: none!important;overflow: auto!important;}
		.conn{font-family: "Microsoft Yahei",Arial, Helvetica, sans-serif; font-size:20px;color:#99a2ae;}
		.conn a{color:#99a2ae; text-decoration:none}
		.conn a:hover{ color:#f75d5b; border-bottom: solid 2px #f75d5b;text-decoration: none!important;}
		.page-header{border-bottom:none!important;}
		.hide{display: none!important;}
	</style>
	<%@include file="/views/upload/include/head.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header"><h1 style='color:#505863; font-family:Impact, Haettenschweiler, "Franklin Gothic Bold", "Arial Black", sans-serif; font-size:130px;'>500 ERROR</h1></div>
		<div class="conn">
			<p>系统内部错误</p>
		</div>
		<div class="errorMessage">
			错误信息：<%=ex==null?"未知错误.":StringUtils.toHtml(ex.getMessage())%> <br/> <br/>
			请点击“查看详细信息”按钮，将详细错误信息发送给系统管理员，谢谢！<br/> <br/>
			<div class="conn">
				<a href="javascript:" onclick="history.go(-1);">返回上一页</a> &nbsp;
				<a href="javascript:" onclick="$('.errorMessage').toggle();">查看详细信息</a>
			</div>
		</div>
		<div class="errorMessage" style="display: none;">
			<%=StringUtils.toHtml(sb.toString())%> <br/>
			<div class="conn">
				<a href="javascript:" onclick="history.go(-1);">返回上一页</a> &nbsp;
				<a href="javascript:" onclick="$('.errorMessage').toggle();">隐藏详细信息</a>
			</div>
			<br/> <br/>
		</div>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
</body>
</html>
<%
} out = pageContext.pushBody();
%>