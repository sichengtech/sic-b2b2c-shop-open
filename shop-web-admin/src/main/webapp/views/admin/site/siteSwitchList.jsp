<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>站点开关管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteSwitchList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">站点开关列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 站点开关列表</a></li>
				<shiro:hasPermission name="site:siteSwitch:edit"><li class=""><a href="${ctxa}/site/siteSwitch/save1.do" > <i class="fa fa-user"></i> 站点开关添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>站点开关管理是设置网站是否在开放。</li>
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
						<shiro:hasPermission name="site:siteSwitch:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/site/siteSwitch/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/site/siteSwitch/list.do" method="get" id="searchForm">
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
						<th>是否开放网站</th>
						<th>网站关闭消息</th>
						<shiro:hasPermission name="site:siteSwitch:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="siteSwitch">
					<tr>
						<td><a href="${ctxa}/site/siteSwitch/edit1.do?id=${siteSwitch.id}">${siteSwitch.isOpen}</a></td>
						<td>${siteSwitch.msg}</td>
						<shiro:hasPermission name="site:siteSwitch:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteSwitch/edit1.do?id=${siteSwitch.id}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteSwitch/delete.do?id=${siteSwitch.id}">
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