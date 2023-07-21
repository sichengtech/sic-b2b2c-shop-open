<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('快递公司管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%> 
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/logistics/logisticsCompanyList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('快递公司列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('快递公司列表')}</a></li>
				<shiro:hasPermission name="logistics:logisticsCompany:save">
					<li class="">
						<a href="${ctxa}/logistics/logisticsCompany/save1.do" > <i class="fa fa-user"></i> ${fns:fy('快递公司添加')}
						</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->		 
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('快递公司管理.快递公司列表.操作提示1')}</li>
					<li>${fns:fy('快递公司管理.快递公司列表.操作提示2')}</li>
					<li>${fns:fy('快递公司管理.快递公司列表.操作提示3')}</li>
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
						<shiro:hasPermission name="logistics:logisticsCompany:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy('添加')}" href="${ctxa}/logistics/logisticsCompany/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/logistics/logisticsCompany/list.do" method="get" id="searchForm">
					<%-- <div class="col-sm-2">
						<input type="text" name="companyName" maxlength="64" class="form-control input-sm" placeholder="公司名称" value="${logisticsCompany.companyName}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> 搜索</button>
					</div> --%>
					<div class="col-sm-3 col-md-offset-7">
						<div class="iconic-input right">
							<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
							<a href="javaScript:;" class="search"><i class="fa fa-search"></i></a>
							<input type="text" name="companyName" maxlength="64" value="${logisticsCompany.companyName}" class="form-control input-sm pull-right" placeholder="${fns:fy('请输入快递公司名称进行搜索')}" style="border-radius: 30px;max-width:250px;">
						</div>
					</div> 
				</form>
			</div>
			<!-- 按钮结束 --> 
			<!-- Table开始 -->
			<div class="table-responsive">
				<table class="table table-hover table-condensed table-bordered">
				 <thead> 
					<tr>
						<th>${fns:fy('公司名称')}</th>
						<th>${fns:fy('首字母')}</th>
						<th>${fns:fy('公司编号')}</th>
						<th>${fns:fy('公司网址')}</th>
						<th>${fns:fy('状态')}</th>
						<th>${fns:fy('常用快递')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${page.list}" var="logisticsCompany">
					<tr>
						<td>${logisticsCompany.companyName}</td>
						<td>${logisticsCompany.largeArea}</td>
						<td>${logisticsCompany.companyNumber}</td>
						<td>${logisticsCompany.companyurl}</td>
						<td class="status_td">
							<input id="${logisticsCompany.lcId}" class="status" type="checkbox" value="${logisticsCompany.status}" ${logisticsCompany.status==1?"checked":""} data-size="small" style="display: none" data-on-text="${fns:fy('启用')}" data-off-text="${fns:fy('禁用')}"/>
						</td>
						<td class="isCommonUse_td">
							<input id="${logisticsCompany.lcId}" class="isCommonUse" type="checkbox" value="${logisticsCompany.isCommonUse}" ${logisticsCompany.isCommonUse==1?"checked":""} data-size="small" style="display: none" data-on-text="${fns:fy('是')}" data-off-text="${fns:fy('否')}"/>
						</td>
						<td>
							<shiro:hasPermission name="logistics:logisticsCompany:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/logistics/logisticsCompany/edit1.do?lcId=${logisticsCompany.lcId}">
								<i class="fa fa-edit"></i> ${fns:fy('编辑')}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="logistics:logisticsCompany:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/logistics/logisticsCompany/delete.do?lcId=${logisticsCompany.lcId}">
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
			<%@ include file="../include/page.jsp"%>
			<!-- 分页信息结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>