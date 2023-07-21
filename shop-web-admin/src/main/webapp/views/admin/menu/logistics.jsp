<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<li class="menu-list nav-active">
	<a href="javascript:void(0);">
		<i class="fa fa-envelope"></i> 
		<span>物流管理</span><small>物流</small>
	</a>
	<ul class="sub-menu-list">
		<li><a href="${ctxa}/sys/area/list.do"><i class="fa fa-user"></i> <span>地区管理</span></a></li>
		<li><a href="${ctx}/views/admin/logistics/logisticsCompany.jsp"><i class="fa fa-user"></i> <span>快递公司</span></a></li>
	</ul>
</li>