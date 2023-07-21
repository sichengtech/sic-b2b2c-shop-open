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
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="value" type="java.lang.String" required="true"%>
<%@ attribute name="callback" type="java.lang.String" required="true"%>
<input id="${id}" name="${name}" type="hidden" value="${value}"/>
<%-- 使用方法： 1.将本tag写在查询的from里；2.在需要排序th列class上添加：sort-column + 排序字段名；3.后台sql添加排序引用page.orderBy；实例文件：userList.jsp、UserDao.xml --%>
<script type="text/javascript">
	$(document).ready(function() {
		var orderBy = $("#${id}").val().split(" ");
		$(".sort-column").each(function(){
			if ($(this).hasClass(orderBy[0])){
				orderBy[1] = orderBy[1]&&orderBy[1].toUpperCase()=="DESC"?"down":"up";
				$(this).html($(this).html()+" <i class=\"icon icon-arrow-"+orderBy[1]+"\"></i>");
			}
		});
		$(".sort-column").click(function(){
			var order = $(this).attr("class").split(" ");
			var sort = $("#${id}").val().split(" ");
			for(var i=0; i<order.length; i++){
				if (order[i] == "sort-column"){order = order[i+1]; break;}
			}
			if (order == sort[0]){
				sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
				$("#${id}").val(order+" DESC"!=order+" "+sort?"":order+" "+sort);
			}else{
				$("#${id}").val(order+" ASC");
			}
			${callback}
		});
	});
</script>