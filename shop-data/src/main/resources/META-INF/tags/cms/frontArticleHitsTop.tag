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
<%@ attribute name="category" type="com.sicheng.admin.cms.entity.Category" required="true" description="栏目对象"%>
<%@ attribute name="pageSize" type="java.lang.Integer" required="false" description="页面大小"%>
<c:forEach items="${fnc:getArticleList(category.site.id, category.id, not empty pageSize?pageSize:8, 'posid:2, orderBy: \"hits desc\"')}" var="article">
	<li><a href="${ctx}/cms/view-${article.category.id}-${article.id}${fns:getUrlSuffix()}" style="color:${article.color}" title="${article.title}">${fns:abbr(article.title,16)}</a></li>
</c:forEach>