<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/seller/include/taglib.jsp"%>
var arr = [
<c:forEach items="${cateList}" var="item">
{id:"${item.id}",parent_id:"${item.parentId}",name:"${item.name}",pinyin:"${item.firstLetter}",parent_ids:"${item.parentIds}"},</c:forEach>
];