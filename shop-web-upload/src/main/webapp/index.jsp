<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//作用：进入首页的Controller
//当浏览器访问网站的首页时，地址可能是：http://www.abc.com/,这个请求会被index.jsp处理（因web.xml中配置的welcome-file-list中有index.jsp）
//由FDP的spring mvc只拦截*.do、*.htm后缀，所以不会拦截住这个请求，spring mvc无机会处理这个请求。
//所以要转发到index.do
String path="/demo/index.htm";
request.getRequestDispatcher(path).forward(request, response);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>正在转入首页</title>
</head>
<body>
<h3>正在转入首页</h3>
</body>
</html>