<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/seller/include/taglib.jsp"%>
<div id="c_left" class="main-menu box">
	 <dl class="sui-nav nav-large menu_1">
	 	<c:if test="${not empty menu1id}">
		 	<c:forEach items="${fns:getStoreChildMenuList(menu1id)}" var="menu2">
				<c:set value="0,1,${menu1id}," var="parentIds1"/>
				<c:if test="${menu2.parentIds == parentIds1}">
					<dt name="seller_others" class="nav-header"><a><i class="r sui-icon icon-tb-right"></i><i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy(menu2.name)}</span></a></dt>
					<dd>
						<ul class="sui-nav nav-list">
							<c:forEach items="${fns:getStoreChildMenuList(menu2.id)}" var="menu3">
								<c:set value="${menu2.parentIds}${menu2.id}," var="parentIds2"/>
								<c:if test="${parentIds2 == menu3.parentIds}">
									<li class="${menu3.id == menu3id?'active':'' }"><a href="${fn:indexOf(menu3.href, '://') eq -1 ? ctxs : ''}${menu3.href}">${fns:fy(menu3.name)}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</dd>
				</c:if>
			</c:forEach> 
		</c:if>
	 	<c:if test="${empty menu1id}">
		 	<c:forEach items="${fns:getStoreChildMenuList(menu1id)}" var="menu2" varStatus="status">
				<c:set value="${fn:split(menu2.parentIds, ',')}" var="parentIds2"/>
				<c:if test="${fn:length(parentIds2)==3}">
					<dt name="seller_index" class="nav-header"><a><i class="r sui-icon icon-tb-right"></i><i class="sui-icon icon-tb-addressbook"></i><span>${fns:fy(menu2.name)}</span></a></dt>
					<dd class="${status.first?'active':'menu_2'}">
						<ul class="sui-nav nav-list">
							<c:forEach items="${fns:getStoreChildMenuList(menu2.id)}" var="menu3">
								<c:set value="${menu2.parentIds}${menu2.id}," var="parentIds3"/>
								<c:if test="${parentIds3 == menu3.parentIds}">
									<li class="${menu3.menuNum == menu3?'active':'' }"><a href="${fn:indexOf(menu3.href, '://') eq -1 ? ctxs : ''}${menu3.href}">${fns:fy(menu3.name)}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</dd>
				</c:if>
			</c:forEach> 
		</c:if>
	</dl>
</div>