<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>入驻申请（业务查看）管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeEnterList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">入驻申请（业务查看）列表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 入驻申请（业务查看）列表</a></li>
				<shiro:hasPermission name="store:storeEnter:edit"><li class=""><a href="${ctxa}/store/storeEnter/save1.do" > <i class="fa fa-user"></i> 入驻申请（业务查看）添加</a></li></shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>点击审核按钮可以对商家入驻申请进行审核。</li>
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
						<shiro:hasPermission name="store:storeEnter:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/storeEnter/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/store/storeEnter/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
						<select name="status" class="form-control m-bot15 input-sm">
							<option value="">--请选择--</option>
							<c:forEach items="${fns:getDictList('store_enter_status')}" var="item">
							<option value="${item.value}" ${item.value==storeEnter.status?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-1">
						<input type="text" name="storeName"  maxlength="64" class="form-control input-sm" placeholder="店铺名称" value="${storeEnter.storeName}"/>
					</div>
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
						<th>入驻ID</th>
						<th>申请状态</th>
						<th>店铺名称</th>
						<th>申请时间</th>
						<shiro:hasPermission name="store:storeEnter:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="storeEnter">
					<tr>
						<td><a href="${ctxa}/store/storeEnter/edit1.do?enterId=${storeEnter.enterId}">${storeEnter.enterId}</a></td>
						<td>${fns:getDictLabel(storeEnter.status, 'store_enter_status', '')}</td>
						<td>${storeEnter.storeName}</td>
						<td><fmt:formatDate value="${storeEnter.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<shiro:hasPermission name="store:storeEnter:edit">
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/store/storeEnter/edit1.do?enterId=${storeEnter.enterId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeEnter/delete.do?enterId=${storeEnter.enterId}">
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