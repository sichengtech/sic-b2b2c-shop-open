<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("数据字典")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="8"/><!-- 表示使用N号二级菜单 -->
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysDictList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("数据字典")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javascript:"> <i class="fa fa-user"></i> ${fns:fy("字典管理")}</a></li>
				<shiro:hasPermission name="sys:dict:edit">
				<li class=""><a href="${ctxa}/sys/dict/form.do"> <i class="fa fa-user"></i> ${fns:fy("添加字典")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("系统设置.字典管理.操作提示1")}</li>
					<li>${fns:fy("系统设置.字典管理.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px;">
				<div class="col-sm-2">
					<div class="btn-group">
						<!--刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<shiro:hasPermission name="sys:dict:edit">
						<!-- 新增记录按钮 -->
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy("添加")}" href="${ctxa}/sys/dict/form.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="col-sm-6"></div>
				<form id="searchForm" modelAttribute="dict" action="${ctxa}/sys/dict.do" method="get">
					<div class="col-sm-3">
						<select class="selectpicker form-control input-sm" name="type" id="type" style="padding: 5px 4px;"> 
							<option value="">${fns:fy("请选择分类")}</option>
							<c:forEach items="${typeMap}" var="map">
						 		<option value="${map['type']}" ${dictType eq map['type']?"selected":""}>${map['type']} [${map['description']}]</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div> 
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<sys:message content="${message}"/>
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr> 
						<th>${fns:fy("值")}</th>
						<th>${fns:fy("显示标签")}</th>
						<th>${fns:fy("类型")}</th>
						<th>${fns:fy("描述")}</th>
						<th>${fns:fy("排序")}</th>
						<th>${fns:fy("管理操作")}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="dict">
						<tr>
							<td>${dict.value}</td>
							<td><a href="${ctxa}/sys/dict/form.do?id=${dict.id}">${dict.label}</a></td>
							<td><a href="javascript:" onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a></td>
							<td>${dict.description}</td>
							<td>${dict.sort}</td>
							<td>
								<shiro:hasPermission name="sys:dict:edit">
								<a href="${ctxa}/sys/dict/form.do?id=${dict.id}" class="btn btn-info btn-sm">
									<i class="fa fa-edit"></i> ${fns:fy("修改")}
								 </a>
								 <a class="btn btn-info btn-sm" href="<c:url value='${fns:getAdminPath()}/sys/dict/form.do?type=${dict.type}&sort=${dict.sort+10}'><c:param name='description' value='${dict.description}'/></c:url>">
									<i class="fa fa-edit"></i> ${fns:fy("添加键值")}
								 </a>
								</shiro:hasPermission>
								<shiro:hasPermission name="sys:dict:drop">
								 <button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sys/dict/delete.do?id=${dict.id}&type=${dict.type}" >
									<i class="fa fa-trash-o"></i> ${fns:fy("删除")}
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