<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("文章栏目列表")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<%@ include file="../include/treetable.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/cms/siteArticleChannelList.js"></script>
</head>
<body>

	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("文章栏目管理")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-home"></i> ${fns:fy("文章栏目列表")}</a></li>
				<shiro:hasPermission name="cms:category:edit">
				<li class=""><a href="${ctxa}/cms/category/form.do" > <i class="fa fa-user"></i> ${fns:fy("文章栏目添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
			<div class="panel-body">
				<!-- 提示 start -->
				<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
					<h5>${fns:fy("操作提示")}</h5>
					<ul>
						<li>${fns:fy("文章管理.栏目管理.操作提示1")}</li>
					</ul>
				</div>
				<!-- 提示 end --> 
				<!-- 按钮开始 -->
				<div class="row" style="margin-bottom:10px;">
					<div class="col-sm-2">
						<div class="btn-group">
							<!--刷新按钮 -->
							<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
							<!-- 新增记录按钮 -->
							<shiro:hasPermission name="cms:category:edit">
							<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/cms/category/form.do"><i class="fa fa-plus"></i></a>
							</shiro:hasPermission>
						</div>
					</div>
					<div class="col-sm-2"> </div>
					<div class="col-sm-2"> </div>
					<div class="col-sm-2"> </div>
					<div class="col-sm-1"> </div>
					<div class="col-sm-3">
					</div>
				</div>
				<!-- 按钮结束--> 
				<!-- Table开始 -->
				<sys:message content="${message}"/>
				<form class="cmxform form-horizontal adminex-form" id="listForm" method="get" action="${ctxa}/cms/category/updateSort.do">
					<table id="treeTable" class="table table-bordered table-condensed">
						<thead>
							<tr>
								<th style="text-align: left;">${fns:fy("栏目名称")}</th>
								<th>${fns:fy("归属机构")}</th>
								<th>${fns:fy("文章模型")}</th>
								<th>${fns:fy("排序")}</th>
								<th>${fns:fy("导航菜单")}</th>
								<th>${fns:fy("栏目列表")}</th>
								<th>${fns:fy("展现方式")}</th>
								<th>${fns:fy("操作")}</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="tpl">
								<tr id="${tpl.id}" pId="${tpl.parent.id ne '1'?tpl.parent.id:'0'}">
									<td  style="text-align: left;"><a href="${ctxa}/cms/category/form.do?id=${tpl.id}">${tpl.name}</a></td>
									<td>${tpl.office.name}</td>
									<td>${fns:getDictLabel(tpl.module, 'cms_module', fns:fy("公共模型"))}</td>
									<td style="text-align:center;">
										<shiro:hasPermission name="cms:category:edit">
											<input type="hidden" name="ids" value="${tpl.id}"/>
											<input class="form-control input-sm sorts" name="sorts" type="text" value="${tpl.sort}" type="text" style="width:50px;margin:0;padding:0;text-align:center;"> 
										</shiro:hasPermission><shiro:lacksPermission name="cms:category:edit">
											${tpl.sort}
										</shiro:lacksPermission>
									</td>
									<td>${fns:getDictLabel(tpl.inMenu, 'show_hide', fns:fy("隐藏"))}</td>
									<td>${fns:getDictLabel(tpl.inList, 'show_hide', fns:fy("隐藏"))}</td>
									<td>${fns:getDictLabel(tpl.showModes, 'cms_show_modes', fns:fy("默认展现方式"))}</td>
									<td>
										<shiro:hasPermission name="cms:category:edit">
										<a class="btn btn-info btn-sm" href="${ctxa}/cms/category/form.do?id=${tpl.id}">
											<i class="fa fa-edit"></i> ${fns:fy("编辑")}
										</a>
										<a type="button" class="btn btn-info btn-sm" href="${ctxa}/cms/category/form.do?parent.id=${tpl.id}">
											<i class="fa fa-level-down"></i> ${fns:fy("添加下级栏目")}
										</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="cms:category:drop">
										<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/cms/category/delete.do?id=${tpl.id}">
											<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
										</button>
										</shiro:hasPermission>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						</table>
						<shiro:hasPermission name="cms:category:edit">
							<div class="form-group">
								<div class="col-lg-6">
									<button type="submit" class="btn btn-info m-r-5">${fns:fy("保存排序")}</button>
								</div>
							</div>
						</shiro:hasPermission>
					</form>
				<!-- table结束 -->
			</div>
	</section>
	<!-- panel end -->
</body>
</html>