<%
response.setStatus(404);
//System.out.println("##########");
// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	out.print("页面不存在.");
}

//输出异常信息页面
else {
%>
<%@page import="com.sicheng.common.web.Servlets"%>
<%@page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>404</title>
	<%@include file="/views/seller/include/head.jsp" %>
</head>
<body class="error">
	<div class="error_wraper">
		<div class="r"><img src="${ctxStatic}/sicheng-seller/images/error_404_pic.png" alt=""/></div>
		<img src="${ctxStatic}/sicheng-seller/images/error_404.png" alt=""/>
		<div class="text">
			<p class="h1">很抱歉，小怪兽把页面弄丢了!</p>
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
out.print("<!--"+request.getAttribute("javax.servlet.forward.request_uri")+"-->");
} out = pageContext.pushBody();
%>