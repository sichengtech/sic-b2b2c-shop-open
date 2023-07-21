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
if (Servlets.isAjaxRequest(request)) {
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
<%@include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>500</title>
	<%@include file="/views/seller/include/head.jsp" %>
	<link href="${ctxStatic}/sui/1.5.1/sui.min.css" rel="stylesheet">
	<link href="${ctxStatic}/sui/1.5.1/sui-append.min.css" rel="stylesheet">
</head>
<body class="error">
	<div class="error_wraper">
		<div class="r"><img src="${ctxStatic}/sicheng-seller/images/error_500_pic.png" alt=""/></div>
		<img src="${ctxStatic}/sicheng-seller/images/error_500.png" alt=""/>
		<div class="text">
			<p class="h1">很抱歉，程序猿把页面搞坏了!</p>
			<p class="h2">您可以继续去浏览其他网页。</p>
			<a href="javascript:;" onclick="history.go(-1);" class="button">返回上一页</a>
			<a id="returnIndex" href="javascript:;" class="button">返回首页</a>
		</div>
	</div>
	
	<script type="text/javascript">
		$("#returnIndex").click(function(){
			var contextPath='${ctx}';//web项目的根目录
			var pathName=window.document.location.pathname;//URL的路径部分
			var postPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
			var arr=pathName.split("/");
			if(contextPath=='' || contextPath==null){
				if(postPath!='/sso'&&postPath!='/member'&&postPath!='/seller'){
					location.href=window.location.protocol+'//'+window.location.host+'/index.htm';
				}else{
					location.href=window.location.protocol+'//'+window.location.host+postPath+'/index.htm';
				}
			}else{
				if(arr[2]!='sso'&&arr[2]!='member'&&arr[2]!='seller'){
					location.href=window.location.protocol+'//'+window.location.host+'/'+arr[1]+'/index.htm';
				}else{
					location.href=window.location.protocol+'//'+window.location.host+'/'+arr[1]+'/'+arr[2]+'/index.htm';
				}
			}
		});
	</script>
</body>
</html>
<%
} out = pageContext.pushBody();
%>