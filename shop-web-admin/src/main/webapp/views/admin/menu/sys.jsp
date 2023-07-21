<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>

<%-- <li class="menu-list nav-active"><a href="javascript:void(0);"><i class="fa fa-th-list"></i> <span>系统设置</span><small>系统</small></a>
	<ul class="sub-menu-list">
		<li><a href="${ctxa}/cms/template/save.do"><i class="fa fa-user"></i> <span>模板管理</span></a></li>
		<li><a href="${ctxa}/sys/office/list.do"><i class="fa fa-user"></i> <span>机构管理</span></a></li>
		<li><a href="${ctxa}/sys/user.do"><i class="fa fa-user"></i> <span>管理员管理</span></a></li>
		<li><a href="${ctxa}/sys/role.do"><i class="fa fa-user"></i> <span>角色管理</span></a></li>
		<li><a href="${ctxa}/sys/menu.do"><i class="fa fa-user"></i> <span>菜单管理</span></a></li>
		<li><a href="${ctxa}/sys/dict.do"><i class="fa fa-user"></i> <span>数据字典</span></a></li>
		<li><a href="${ctx}/views/admin/sys/sysMail.jsp"><i class="fa fa-user"></i> <span>邮件设置</span></a></li>
		<li><a href="${ctx}/views/admin/sys/sysSMS.jsp"><i class="fa fa-user"></i> <span>短信网关</span></a></li>
		<li><a href="${ctxa}/sys/log.do"><i class="fa fa-user"></i> <span>日志查询</span></a></li>
		<li><a href="${ctx}/views/admin/sys/sysVariableTableList.jsp"><i class="fa fa-user"></i> <span>自定义表</span></a></li>
		<li><a href="${ctx}/druid" target="_blank"><i class="fa fa-user"></i> <span>连接池监视</span></a></li>
	</ul>
</li> --%>
<c:forEach items="${menuList}" var="m">
	<c:if test="${m.parentIds == param.parentIds}">
		<li class="menu-list nav-active">
			<a href="javascript:void(0);">
				<i class="fa fa-th-list"></i> 
				<span>${m.name}</span><small>${menu.name}</small>
			</a>
			<ul class="sub-menu-list">
			<c:forEach items="${menuList}" var="mc">
				<c:if test="${m.parentIds == mc.parentIds}">
					<li><a href="${fn:indexOf(menu.href, '://') eq -1 ? ctxa : ''}${mc.href}"><i class="fa fa-user"></i> <span>${mc.name}</span></a></li>
				</c:if>
			</c:forEach>
			</ul>
		</li>
	</c:if>
</c:forEach>
