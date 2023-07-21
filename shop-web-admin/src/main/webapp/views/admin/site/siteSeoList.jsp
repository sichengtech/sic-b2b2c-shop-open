<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("seo设置管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteSeoList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("seo设置列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("seo设置列表")}</a></li>
				<shiro:hasPermission name="site:siteSeo:edit"><li class=""><a href="${ctxa}/site/siteSeo/save1.do" > <i class="fa fa-user"></i> ${fns:fy("seo设置添加")}</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>
						${fns:fy("seo设置使用的变量")}：1.<span>$</span>{siteName}--${fns:fy("商城名")}、2.<span>$</span>{categoryName}--${fns:fy("分类名")}、
						3.<span>$</span>{productName}--${fns:fy("商品名")}、4.<span>$</span>{storeName}--${fns:fy("店铺名")}、5.<span>$</span>{searchKey}--${fns:fy("搜索关键字")}
					</li>
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
						<shiro:hasPermission name="site:siteSeo:edit">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/site/siteSeo/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<%-- <form action="${ctxa}/site/siteSeo/list.do" method="get" id="searchForm">
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
						<th>${fns:fy("业务类型")}</th>
						<th>Title</th>
						<th>Keywords</th>
						<th>Description</th>
						<shiro:hasPermission name="site:siteSeo:edit"><th>${fns:fy("操作")}</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="siteSeo">
					<tr>
						<td><a href="${ctxa}/site/siteSeo/edit1.do?id=${siteSeo.id}">${siteSeo.type}</a></td>
						<td>${siteSeo.title}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${siteSeo.keywords}</textarea></td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${siteSeo.description}</textarea></td>
						<shiro:hasPermission name="site:siteSeo:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteSeo/edit1.do?id=${siteSeo.id}">
								<i class="fa fa-edit"></i> ${fns:fy("编辑")}
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteSeo/delete.do?id=${siteSeo.id}">
								<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
							</button>
						</td>
						</shiro:hasPermission>
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