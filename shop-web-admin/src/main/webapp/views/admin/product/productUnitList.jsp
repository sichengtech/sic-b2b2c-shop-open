<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('计量单位管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productUnitList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('计量单位列表')}</h4>
			<%@ include  file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('计量单位列表')}</a></li>
				<shiro:hasPermission name="product:productUnit:save">
				<li class=""><a href="${ctxa}/product/productUnit/save1.do" > <i class="fa fa-user"></i> ${fns:fy('计量单位添加')}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('计量单位.计量单位列表.操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<shiro:hasPermission name="product:productUnit:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy('添加')}" href="${ctxa}/product/productUnit/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/product/productUnit/list.do" method="get" id="searchForm">
					<div class="col-sm-2 col-md-offset-7">
						<input type="text" name="name"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy('名称')}" value="${productUnit.name}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy('搜索')}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束  -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy('名称')}</th>
						<th>${fns:fy('首字母')}</th>
						<th>${fns:fy('排序')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="productUnit">
					<tr>
						<td><a href="${ctxa}/product/productUnit/edit1.do?id=${productUnit.id}">${productUnit.name}</a></td>
						<td>${productUnit.firstLetter}</td>
						<td>${productUnit.sort}</td>
						<td>
							<shiro:hasPermission name="product:productUnit:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/product/productUnit/edit1.do?id=${productUnit.id}">
							  	<i class="fa fa-edit"></i> ${fns:fy('编辑')}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="product:productUnit:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/product/productUnit/delete.do?id=${productUnit.id}">
								<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
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
			<%@ include  file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束  -->
</body>
</html>