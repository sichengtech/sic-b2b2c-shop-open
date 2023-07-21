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
<%@ attribute name="categoryList" type="java.util.List" required="true" description="栏目列表"%>
<c:forEach items="${categoryList}" var="tpl">
  	<%--<c:if test="${category.inList eq '1'}"> --%>
   		<c:choose>
   			<c:when test="${not empty tpl.href}">
    			<c:choose>
	    			<c:when test="${fn:indexOf(tpl.href, '://') eq -1 && fn:indexOf(tpl.href, 'mailto:') eq -1}">
	    			<c:set var="url" value="${ctx}${tpl.href}"/></c:when><c:otherwise><c:set var="url" value="${tpl.href}"/></c:otherwise>
	    		</c:choose>
   			</c:when>
   			<c:otherwise><c:set var="url" value="${ctx}/cms/list-${tpl.id}${fns:getUrlSuffix()}"/></c:otherwise>
   		</c:choose>
		<li  class="${requestScope.category.id eq tpl.id ?'menu_activ':''}">
			<c:choose><c:when test="${fn:length(tpl.name) gt 12}">
				<a href="${url}" target="${tpl.target}" style="line-height:16px;padding-top:3px;">${tpl.name}</a>
			</c:when><c:otherwise>
				 <a href="${url}" target="${tpl.target}" ${fn:length(tpl.name) gt 10?'style="font-size:12px;"':''}>${tpl.name}</a>
			</c:otherwise></c:choose></li>
	<%--</c:if> --%>
</c:forEach>