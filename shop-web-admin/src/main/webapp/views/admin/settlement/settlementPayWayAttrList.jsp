<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<div class="table-responsive" style="margin-top: 10px;">
	<table class="table table-condensed table-bordered" style="margin-bottom: 0px;">
		<thead>
			<tr>
				<th>${fns:fy('属性键')}</th>
				<th>${fns:fy('属性值')}</th>
				<th>${fns:fy('属性描述')}</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${payWayAttrList}" var="payWayAttr">
			<tr>
				<td>${payWayAttr.payWayKey}</td>
				<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${payWayAttr.payWayValue}</textarea></td>
				<td>${payWayAttr.payWayDescribe}</td>
			</tr>
			</c:forEach>
			<c:if test="${empty payWayAttrList}">
				<tr>
				<td colspan="3" style="line-height: 335px;color: #9c9a9a;">${fns:fy('暂无属性')}</td>
				<tr>
			</c:if>
		</tbody>
	</table>
</div>