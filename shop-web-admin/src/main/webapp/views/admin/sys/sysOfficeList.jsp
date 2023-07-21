<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("机构列表")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- 业务js -->
<%@include file="/views/admin/include/treetable.jsp" %>
<script type="text/javascript">
	var jsonData = ${fns:toJson(list)};
	var officeType = ${fns:toJson(fns:getDictList('sys_office_type'))};
	//var getVal="${fns:jsGetVal('row.parentId')}";
	var rootId = ${not empty office.id ? office.id : 0};
</script>
<script type="text/javascript" src="${ctx}/views/admin/sys/sysOfficeList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("机构列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("机构列表")}</a></li>
				<shiro:hasPermission name="sys:office:edit">
				<li class=""><a href="${ctxa}/sys/office/form.do" > <i class="fa fa-user"></i> ${fns:fy("添加机构")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<sys:message content="${message}"/> 
		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.机构管理.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<shiro:hasPermission name="sys:office:edit">
						<!-- 添加记录按钮 -->
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/sys/office/form.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="col-sm-5"></div>
				<form action="${ctxa}/sys/office/search.do" method="get" >
					<div class="col-sm-2">
						<input type="text" class="form-control input-sm" placeholder="${fns:fy("机构名称")}" name="name" value="${name}" maxlength="20">
					</div>
					<div class="col-sm-2">
						<select class="form-control" name="type" style="height: 30px;font-size: 12px;">
							<option value="">${fns:fy("机构类型")}</option>
							<c:forEach items="${fns:getDictList('sys_office_type')}" var="ot">
								<option value="${ot.value}" ${office.type eq ot.value?"selected":""}>${ot.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered" id="treeTable">
				 <thead> 
					<tr>
						<th style="text-align: left;">${fns:fy("机构名称")}</th>
						<th>${fns:fy("归属区域")}</th>
						<th>${fns:fy("机构编码")}</th>
						<th>${fns:fy("机构类型")}</th>
						<th>${fns:fy("备注")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead> 
				<tbody id="view"></tbody>
				<script type="text/template" id="treeTableTpl">
				<tr id="{{d.row.id}}" pId="{{d.pid}}">
					<td style="text-align: left;"><a href="${ctxa}/sys/office/form.do?id={{d.row.id}}">{{d.row.name}}</a></td>
					<td>{{d.row.area.name}}</td>
					<td>{{d.row.code || ''}}</td>
					<td>{{d.dict.type}}</td>
					<td>{{d.row.remarks || ''}}</td>
					<td>
						<shiro:hasPermission name="sys:office:edit">
						<a type="button" class="btn btn-info btn-sm" href="${ctxa}/sys/office/form.do?id={{d.row.id}}" >
							 <i class="fa fa-edit"></i> ${fns:fy("编辑")}
						</a>
						<a type="button" class="btn btn-info btn-sm" href="${ctxa}/sys/office/form.do?parent.id={{d.row.id}}">
							 <i class="fa fa-level-down"></i> ${fns:fy("添加下级菜单")}
						</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:office:drop">
						<button class="btn btn-danger btn-sm deleteSure" type="button" href="${ctxa}/sys/office/delete.do?id={{d.row.id}}" >
						 	<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
						</button>
						</shiro:hasPermission>
					</td> 
				</tr>
				</script>
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