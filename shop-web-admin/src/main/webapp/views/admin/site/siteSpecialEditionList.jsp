<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("特版管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteSpecialEditionList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("特版管理列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("特版管理列表")}</a></li>
				<shiro:hasPermission name="site:siteSpecialEdition:save"><li class=""><a href="${ctxa}/site/siteSpecialEdition/save1.do" > <i class="fa fa-user"></i> ${fns:fy("特版管理添加")}</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.特版管理.操作提示7")}</li>
					<li>${fns:fy("网站设置.特版管理.操作提示8")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="site:siteSpecialEdition:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/site/siteSpecialEdition/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/site/siteSpecialEdition/list.do" method="get" id="searchForm">
					<div class="col-sm-5">
					</div>
					<div class="col-sm-2">
						<input type="text" name="number"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("编号")}" value="${siteSpecialEdition.number}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="seName"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("特版名称")}" value="${siteSpecialEdition.seName}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("特版id")}</th>
						<th>${fns:fy("编号")}</th>
						<th>${fns:fy("特版名称")}</th>
						<th>${fns:fy("说明信息")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="siteSpecialEdition">
					<tr>
						<td><a href="${ctxa}/site/siteSpecialEdition/edit1.do?seId=${siteSpecialEdition.seId}">${siteSpecialEdition.seId}</a></td>
						<td>${siteSpecialEdition.number}</td>
						<td>${siteSpecialEdition.seName}</td>
						<td>${siteSpecialEdition.info}</td>
						<td>
							<shiro:hasPermission name="site:siteSpecialEdition:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteSpecialEdition/edit1.do?seId=${siteSpecialEdition.seId}">
								<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="site:siteSpecialEdition:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteSpecialEdition/delete.do?seId=${siteSpecialEdition.seId}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="site:siteSpecialEditionDetail:view">
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteSpecialEditionDetail/list.do?seId=${siteSpecialEdition.seId}">
								<i class="fa fa-edit"></i> ${fns:fy("特版明细")}
							</a>
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