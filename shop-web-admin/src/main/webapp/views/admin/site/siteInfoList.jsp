<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>站点设置管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteInfoList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">站点设置列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 站点设置列表</a></li>
				<shiro:hasPermission name="site:siteInfo:edit"><li class=""><a href="${ctxa}/site/siteInfo/save1.do" > <i class="fa fa-user"></i> 站点设置添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>网站名称将显示在前台顶部欢迎信息等位置</li>
					<li>网站备案信息将显示在前台页面底部，如果没有请留空</li>
					<li>可在此处插入三方统计用代码，并于前台页面底部可以显示。</li>
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
						<shiro:hasPermission name="site:siteInfo:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/site/siteInfo/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/site/siteInfo/list.do" method="get" id="searchForm">
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
						<th>网站名称</th>
						<th>ICP备案号</th>
						<th>第三方流量统计代码</th>
						<th>网站LOGO</th>
						<th>会员中心LOGO</th>
						<th>站点联系邮箱</th>
						<th>站点联系电话</th>
						<shiro:hasPermission name="site:siteInfo:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="siteInfo">
					<tr>
						<td><a href="${ctxa}/site/siteInfo/edit1.do?id=${siteInfo.id}">${siteInfo.name}</a></td>
						<td>${siteInfo.icp}</td>
						<td>${siteInfo.code}</td>
						<td>${siteInfo.siteLogo}</td>
						<td>${siteInfo.sellerLogo}</td>
						<td>${siteInfo.email}</td>
						<td>${siteInfo.telephone}</td>
						<shiro:hasPermission name="site:siteInfo:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/site/siteInfo/edit1.do?id=${siteInfo.id}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/site/siteInfo/delete.do?id=${siteInfo.id}">
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