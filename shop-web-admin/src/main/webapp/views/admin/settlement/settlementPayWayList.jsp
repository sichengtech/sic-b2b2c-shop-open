<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('支付方式管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementPayWayList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('支付方式列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('支付方式列表')}</a></li>
				<shiro:hasPermission name="settlement:settlementPayWay:save">
				<li class=""><a href="${ctxa}/settlement/settlementPayWay/save1.do" > <i class="fa fa-user"></i> ${fns:fy('支付方式添加')}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('支付方式.支付方式列表.操作提示1')}</li>
					<li>${fns:fy('支付方式.支付方式列表.操作提示2')}</li>
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
						<shiro:hasPermission name="settlement:settlementPayWay:save">
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy('添加')}" href="${ctxa}/settlement/settlementPayWay/save1.do"><i class="fa fa-plus"></i></a>
						</shiro:hasPermission>
					</div>
				</div>
				<form action="${ctxa}/settlement/settlementPayWay/list.do" method="get" id="searchForm">
					<div class="col-sm-3 col-md-offset-7">
						<div class="iconic-input right">
							<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
							<a href="javaScript:;" class="search"><i class="fa fa-search"></i></a>
							<input type="text" name="name" maxlength="64" value="${settlementPayWay.name}" class="form-control input-sm pull-right" 
								placeholder="${fns:fy('请输入支付方式名称')}" style="border-radius: 30px;max-width:250px;">
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
						<th>${fns:fy('支付方式')}</th>
						<th>${fns:fy('编号')}</th>
						<th>${fns:fy('服务费')}</th>
						<th>${fns:fy('使用终端')}</th>
						<th>${fns:fy('支付方式')}logo</th>
						<th>${fns:fy('状态')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="settlementPayWay">
					<tr>
						<td>${settlementPayWay.name}</td>
						<td>${settlementPayWay.payWayNum}</td>
						<td>${settlementPayWay.poundage}</td>
						<td>${fns:getDictLabel(settlementPayWay.useTerminal, 'settlement_terminal_type', '')}</td>
						<td>
							<c:if test="${not empty settlementPayWay.payWayLogo}">
								<img src="${ctxfs}${settlementPayWay.payWayLogo}@120x50"/>
							</c:if>
							<c:if test="${empty settlementPayWay.payWayLogo}">${fns:fy('无')}</c:if>
						</td>
						<td>${settlementPayWay.status==1?fns:fy('开启'):fns:fy('关闭')}</td>
						<td>
							<shiro:hasPermission name="settlement:settlementPayWayAttr:view">
							<a class="btn btn-info btn-sm attr-view" href="javascript:void(0);" payWayId="${settlementPayWay.payWayId}">
								<i class="fa fa-edit"></i> ${fns:fy('查看属性')}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="settlement:settlementPayWay:edit">
							<a class="btn btn-info btn-sm" href="${ctxa}/settlement/settlementPayWay/edit1.do?payWayId=${settlementPayWay.payWayId}">
								<i class="fa fa-edit"></i> ${fns:fy('编辑')}
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="settlement:settlementPayWay:drop">
							<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/settlement/settlementPayWay/delete.do?payWayId=${settlementPayWay.payWayId}">
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