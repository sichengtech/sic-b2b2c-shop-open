<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fns" uri="https://java.sicheng.net/jsp/jstl/fns" %>
<%@ taglib prefix="fnc" uri="https://java.sicheng.net/jsp/jstl/fnc" %>
<%@ taglib prefix="sys" uri="https://java.sicheng.net/jsp/jstl/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxa" value="${ctx}${fns:getAdminPath()}"/>
<c:set var="ctxu" value="/upload"/>
<c:set var="ctxStatic" value="/static/static"/>
<c:set var="ctxfs" value="${ctxu}${fns:getConfig('filestorage.dir')}"/>
<%@ attribute name="name" type="java.lang.String" required="true" description="验证码输入框名称"%>
<%@ attribute name="inputCssStyle" type="java.lang.String" required="false" description="验证框样式"%>
<%@ attribute name="imageCssStyle" type="java.lang.String" required="false" description="验证码图片样式"%>
<%@ attribute name="buttonCssStyle" type="java.lang.String" required="false" description="看不清按钮样式"%>
<input type="text" id="${name}" name="${name}" maxlength="5" class="input-Code"/>
<a href="javascript:" onclick="$('.input-Code-img').attr('src','${pageContext.request.contextPath}/servlet/adminValidateCodeServlet?'+new Date().getTime());" class="mid ${name}Refresh" style="${buttonCssStyle}">
<img src="${pageContext.request.contextPath}/servlet/adminValidateCodeServlet" onclick="$('.${name}Refresh').click();" class="input-Code-img" style="${imageCssStyle}"/>
</a>