<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>页面导航管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteNavList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">页面导航列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 页面导航列表</a></li>
				<shiro:hasPermission name="site:siteNav:save"><li class=""><a href="${ctxa}/site/siteNav/save1.do" > <i class="fa fa-user"></i> 页面导航添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>系统内置三个导航区域，分别为顶部导航、中部主导航、底部导航，可以向这三个导航区域添加页面导航。</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<sys:message content="${message}"/> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="site:siteNav:save">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/site/siteNav/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<%-- <form action="${ctxa}/site/siteNav/list.do" method="get" id="searchForm">
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div> 
				</form> --%>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>导航名称</th>
						<th>显示位置</th>
						<th>新窗口导航</th>
						<th>排序</th>
						<th>操作</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="siteNav">
					<tr>
						<td>${siteNav.name}</td>
						<td>${fns:getDictLabel(siteNav.location, 'site_nav_location', '')}</td>
						<td>${fns:getDictLabel(siteNav.isNewWindow, 'yes_no', '')}</td>
						<td>${siteNav.sort}</td>
						<td>
							<shiro:hasPermission name="site:siteNav:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteNav/edit1.do?id=${siteNav.id}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="site:siteNav:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteNav/delete.do?id=${siteNav.id}">
								<i class="fa fa-trash-o"></i> 删除
							</button>
							</shiro:hasPermission>
						 </td>
					</tr>
					</c:forEach>
				</tbody>
				</table>
			</div>
			<!-- table结束 -->
			<!-- 分页信息开始 -->
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>