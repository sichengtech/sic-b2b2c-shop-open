<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fns" uri="https://java.sicheng.net/jsp/jstl/fns" %>
<%@ taglib prefix="fnc" uri="https://java.sicheng.net/jsp/jstl/fnc" %>
<%@ taglib prefix="sys" uri="https://java.sicheng.net/jsp/jstl/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxa" value="${true?'/admin':''}${fns:getAdminPath()}"/>
<c:set var="ctxs" value="${false?'/seller':''}${fns:getSellerPath()}"/>
<c:set var="ctxm" value="${false?'/seller':''}${fns:getMemberPath()}"/>
<c:set var="ctxf" value="${false?'/seller':''}${fns:getFrontPath()}"/>
<c:set var="ctxsso" value="${false?'/seller':''}${fns:getSsoPath()}"/>
<c:set var="ctxu" value="/upload"/>
<c:set var="ctxStatic" value="/static/static"/>
<c:set var="ctxfs" value="${ctxu}${fns:getConfig('filestorage.dir')}"/>
<c:set var="ctxw" value="/wap${fns:getWapPath()}"/>