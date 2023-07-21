<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<li class="menu-list nav-active">
	<a href="javascript:void(0);">
		<i class="fa fa-envelope"></i> 
		<span>代码生成</span><small>代码</small>
	</a>
	<ul class="sub-menu-list">
		<li><a href="${ctxa}/gen/genTable.do"><i class="fa fa-user"></i> <span>业务表配置</span></a></li>
		<li><a href="${ctxa}/gen/genScheme.do"><i class="fa fa-user"></i> <span>生成方案配置</span></a></li>
	</ul>
</li>