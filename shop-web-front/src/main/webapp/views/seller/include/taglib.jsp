<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fns" uri="https://java.sicheng.net/jsp/jstl/fns" %>
<%@ taglib prefix="fnc" uri="https://java.sicheng.net/jsp/jstl/fnc" %>
<%@ taglib prefix="sys" uri="https://java.sicheng.net/jsp/jstl/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxf" value="${fns:getConfig('ctx_front')}${fns:getConfig('frontPath')}"/>
<c:set var="ctxs" value="${fns:getConfig('ctx_front')}${fns:getConfig('sellerPath')}"/>
<c:set var="ctxm" value="${fns:getConfig('ctx_front')}${fns:getConfig('memberPath')}"/>
<c:set var="ctxsso" value="${fns:getConfig('ctx_front')}${fns:getConfig('ssoPath')}"/>
<c:set var="ctxa" value="${fns:getConfig('ctx_admin')}${fns:getConfig('adminPath')}"/>
<c:set var="ctxw" value="${fns:getConfig('ctx_wap')}${fns:getConfig('wapPath')}"/>
<c:set var="ctxStatic" value="${fns:getConfig('ctx_static')}/static"/>
<c:set var="ctxu" value="${fns:getConfig('ctx_upload')}${fns:getConfig('uploadPath')}"/>
<c:set var="ctxfs" value="${ctxu}${fns:getConfig('filestorage.dir')}"/>