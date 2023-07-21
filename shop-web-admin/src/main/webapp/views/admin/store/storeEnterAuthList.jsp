<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("入驻申请管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeEnterAuthList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy("入驻申请列表")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy("入驻申请列表")}</a></li>
				<%-- <shiro:hasPermission name="store:storeEnterAuth:edit"><li class=""><a href="${ctxa}/store/storeEnterAuth/save1.do" > <i class="fa fa-user"></i> 入驻申请添加</a></li></shiro:hasPermission> --%>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.入驻申请.操作提示1")}</li>
				</ul>
			</div>
			<!-- 提示 end -->
			<sys:message content="${message}"/>
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px">
				<div class="col-sm-2">
					<div class="btn-group">
						<!-- 刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy("刷新")}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!-- 添加记录按钮 -->
						<%-- <shiro:hasPermission name="store:storeEnterAuth:edit">
						<a class="btn btn-default btn-sm tooltips" title="添加" href="${ctxa}/store/storeEnterAuth/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission> --%>
					</div>
				</div>
				<div class="col-sm-3"></div>
				<form action="${ctxa}/store/storeEnterAuth/list.do" method="get" id="searchForm">
					<div class="col-sm-2">
						<select name="status" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy("请选择")}--</option>
							<c:forEach items="${fns:getDictList('store_enter_status')}" var="item">
							<option value="${item.value}" ${item.value==storeEnterAuth.status?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<input type="text" name="sellerName"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("请输入商家名称进行搜索")}" value="${sellerName}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="storeName"  maxlength="64" class="form-control input-sm" placeholder="${fns:fy("请输入店铺名称进行搜索")}" value="${storeEnterAuth.storeName}"/>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy("搜索")}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy("入驻ID")}</th>
						<th>${fns:fy("商家名称")}</th>
						<th>${fns:fy("店铺名称")}</th>
						<th>${fns:fy("申请状态")}</th>
						<th>${fns:fy("申请时间")}</th>
						<th>${fns:fy("提交时间")}</th>
						<th>${fns:fy("操作")}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="storeEnterAuth">
					<tr>
						<td>${storeEnterAuth.enterId}</td>
						<td>${storeEnterAuth.userMain.loginName}</td>
						<td>${storeEnterAuth.storeName}</td>
						<td>${fns:getDictLabel(storeEnterAuth.status, 'store_enter_status', '')}</td>
						<td><fmt:formatDate value="${storeEnterAuth.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${storeEnterAuth.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<%--<a class="btn btn-info btn-sm" href="${ctxa}/store/storeEnterAuth/edit1.do?enterId=${storeEnterAuth.enterId}">
								<i class="fa fa-edit"></i> 编辑
							</a>
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/store/storeEnterAuth/delete.do?enterId=${storeEnterAuth.enterId}">
								<i class="fa fa-trash-o"></i> 删除
							</button>--%>
							<c:if test="${storeEnterAuth.status!='10' && storeEnterAuth.status!='40' && storeEnterAuth.status!='70' }">
								<a class="btn btn-info btn-sm" href="${ctxa}/store/storeEnterAuth/edit1.do?enterId=${storeEnterAuth.enterId}&attr=1">
									<i class="fa fa-eye"></i> ${fns:fy("查看")}
								</a>
							</c:if>
							<shiro:hasPermission name="store:storeEnterAuth:auth">
							<c:if test="${storeEnterAuth.status=='10'}">
								<a class="btn btn-success btn-sm" href="${ctxa}/store/storeEnterAuth/edit1.do?enterId=${storeEnterAuth.enterId}&attr=2">
									<i class="fa fa-check-square-o"></i> ${fns:fy("审核")}
								</a>
							</c:if>
							<c:if test="${storeEnterAuth.status=='40'}">
								<a class="btn btn-success btn-sm" href="${ctxa}/store/storeEnterAuth/edit1.do?enterId=${storeEnterAuth.enterId}&attr=2">
									<i class="fa fa-check-square-o"></i> ${fns:fy("审核")}
								</a>
							</c:if>
							<c:if test="${storeEnterAuth.status=='70'}">
								<a class="btn btn-success btn-sm" href="${ctxa}/store/storeEnterAuth/edit1.do?enterId=${storeEnterAuth.enterId}&attr=2">
									<i class="fa fa-check-square-o"></i> ${fns:fy("审核")}
								</a>
							</c:if>
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