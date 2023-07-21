<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!-- 业务js -->
<form method="post" action="#" id="inputForm">
	<table class="sui-table table-bordered-simple">
		<tbody>
			<c:forEach items="${spec1Map}" var="spec1">
				<tr>
					<th>${spec1.key}：</th>
					<td>
						<ul class="sui-tag tag-selected">
							<c:forEach items="${spec1.value}" var="spec1V">
								<li class="tag-selected">${spec1V}</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${spec2Map}" var="spec2">
				<tr>
					<th>${spec2.key}：</th>
					<td>
						<ul class="sui-tag tag-selected">
							<c:forEach items="${spec2.value}" var="spec2V">
								<li class="tag-selected">${spec2V}</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${spec3Map}" var="spec3">
				<tr>
					<th>${spec3.key}：</th>
					<td>
						<ul class="sui-tag tag-selected">
							<c:forEach items="${spec3.value}" var="spec3V">
								<li class="tag-selected">${spec3V}</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<th>${fns:fy('我要买')}：</th>
				<td>
					<input type="text" class="input-large search-query count" name="count" placeholder="${fns:fy('请输入你要买的数量')}">
				</td>
			</tr>
		</tbody>
	</table>
</form>
