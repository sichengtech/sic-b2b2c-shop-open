<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品统计管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript"> src="${ctx}/views/admin/product/productSpuAnalyzeList.js"</script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">商品统计列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 商品统计列表</a></li>
				<shiro:hasPermission name="product:productSpuAnalyze:edit"><li class=""><a href="${ctxa}/product/productSpuAnalyze/save1.do" > <i class="fa fa-user"></i> 商品统计添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>商品统计管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
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
						<shiro:hasPermission name="product:productSpuAnalyze:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/product/productSpuAnalyze/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/product/productSpuAnalyze/list.do" method="get" id="searchForm">
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>商品spu的id</th>
						<th>总浏览量</th>
						<th>总浏览量更新日期</th>
						<th>周浏览量</th>
						<th>周浏览量更新日期</th>
						<th>月浏览量</th>
						<th>月浏览量更新日期</th>
						<th>三个月浏览量</th>
						<th>三个月浏览量更新日期</th>
						<th>总销量</th>
						<th>总销量更新日期</th>
						<th>周销量</th>
						<th>周销量更新日期</th>
						<th>月销量</th>
						<th>月销量更新日期</th>
						<th>三个月销量</th>
						<th>三个月销量更新日期</th>
						<shiro:hasPermission name="product:productSpuAnalyze:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="productSpuAnalyze">
					<tr>
						<td><a href="${ctxa}/product/productSpuAnalyze/edit1.do?pId=${productSpuAnalyze.PId}">${productSpuAnalyze.PId}</a></td>
						<td>${productSpuAnalyze.allBrowse}</td>
						<td><fmt:formatDate value="${productSpuAnalyze.allBrowseDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${productSpuAnalyze.weekBrowse}</td>
						<td><fmt:formatDate value="${productSpuAnalyze.weekBrowseDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${productSpuAnalyze.monthBrowse}</td>
						<td><fmt:formatDate value="${productSpuAnalyze.monthBrowseDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${productSpuAnalyze.month3Browse}</td>
						<td><fmt:formatDate value="${productSpuAnalyze.month3BrowseDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${productSpuAnalyze.allSales}</td>
						<td><fmt:formatDate value="${productSpuAnalyze.allSalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${productSpuAnalyze.weekSales}</td>
						<td><fmt:formatDate value="${productSpuAnalyze.weekSalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${productSpuAnalyze.monthSales}</td>
						<td><fmt:formatDate value="${productSpuAnalyze.monthSalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${productSpuAnalyze.month3Sales}</td>
						<td><fmt:formatDate value="${productSpuAnalyze.month3SalesDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="product:productSpuAnalyze:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/product/productSpuAnalyze/edit1.do?pId=${productSpuAnalyze.PId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/product/productSpuAnalyze/delete.do?pId=${productSpuAnalyze.PId}">
								<i class="fa fa-trash-o"></i> 删除
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