<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>短信通道管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysSmsServerList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">短信通道列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 短信通道列表</a></li>
				<shiro:hasPermission name="sys:sysSmsServer:edit"><li class=""><a href="${ctxa}/sys/sysSmsServer/save1.do" > <i class="fa fa-user"></i> 短信通道添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>短信通道管理</li>
					<li>配置用于发送短信的短信网关相关信息。</li>
					<li>使用手机号注册、通过手机找回密码、各种通知类短信的发送都需要使用此配置。若未正确配置以上业务无法发出短信。</li>
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
						<shiro:hasPermission name="sys:sysSmsServer:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/sys/sysSmsServer/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/sys/sysSmsServer/list.do" method="get" id="searchForm">
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
						<th>状态</th>
						<th>短信网关URL</th>
						<th>用户名</th>
						<th>密码</th>
						<th>ACCESS KEY</th>
						<th>APP ID</th>
						<th>客户id</th>
						<th>令牌</th>
						<shiro:hasPermission name="sys:sysSmsServer:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="sysSmsServer">
					<tr>
						<td><a href="${ctxa}/sys/sysSmsServer/edit1.do?id=${sysSmsServer.id}">${sysSmsServer.status}</a></td>
						<td>${sysSmsServer.url}</td>
						<td>${sysSmsServer.username}</td>
						<td>${sysSmsServer.pwd}</td>
						<td>${sysSmsServer.accessKey}</td>
						<td>${sysSmsServer.appId}</td>
						<td>${sysSmsServer.clientId}</td>
						<td>${sysSmsServer.token}</td>
						<shiro:hasPermission name="sys:sysSmsServer:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/sys/sysSmsServer/edit1.do?id=${sysSmsServer.id}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/sys/sysSmsServer/delete.do?id=${sysSmsServer.id}">
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