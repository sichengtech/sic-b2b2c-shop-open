<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('对账差错池管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeErrorPoolList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('对账差错池列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<%-- <ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 对账差错池列表</a></li>
				<shiro:hasPermission name="trade:tradeErrorPool:save"><li class=""><a href="${ctxa}/trade/tradeErrorPool/save1.do" > <i class="fa fa-user"></i> 对账差错池添加</a></li></shiro:hasPermission>
			</ul> --%>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('差错池管理.差错池列表.操作提示1')}</li>
					<li>${fns:fy('差错池管理.差错池列表.操作提示2')}</li>
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
					</div>
				</div>
				<form action="${ctxa}/trade/tradeErrorPool/list.do" method="get" id="searchForm">
					<div class="col-sm-2 col-md-offset-3">
						<input type="text" name="orderId"  maxlength="19" class="form-control input-sm" placeholder="${fns:fy('本商城订单编号')}" value="${tradeErrorPool.orderId}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="storeName"  maxlength="255" class="form-control input-sm" placeholder="${fns:fy('店铺名称')}" value="${tradeErrorPool.storeName}"/>
					</div>
					<div class="col-sm-2">
						<select name="handlestatus" class="form-control m-bot15 input-sm">
							<option value="">--${fns:fy('请选择')}--</option>
							<c:forEach items="${fns:getDictList('error_pool_status')}" var="item">
							<option value="${item.value}" ${item.value==tradeErrorPool.handlestatus?"selected":""}>${item.label}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-1" style="text-align: right;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<button type="submit" class="btn btn-info btn-sm" ><i class="fa fa-search"></i> ${fns:fy('搜索')}</button>
					</div>
				</form>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th></th> 
						<th>${fns:fy('本商城订单编号')}</th>
						<th>${fns:fy('店铺名称')}</th>
						<th>${fns:fy('第三方平台支付交易号')}</th>
						<th>${fns:fy('类型')}</th>
						<th>${fns:fy('支付方式名称')}</th>
						<th>${fns:fy('本商城订单状态')}</th>
						<th>${fns:fy('第三方平台订单状态')}</th>
						<th>${fns:fy('下单时间')}</th>
						<th>${fns:fy('处理状态')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="tradeErrorPool" varStatus="index">
						<tr>
							<td class="detail"><i class="fa fa-plus"></i></span></td>
							<td>${tradeErrorPool.orderId}</td>
							<td>${tradeErrorPool.storeName}</td>
							<td>${tradeErrorPool.transactionId}</td>
							<td>${fns:getDictLabel(tradeErrorPool.billType, 'error_pool_type', '')}</td>
							<td>${tradeErrorPool.payWayName}</td>
							<td>${fns:getDictLabel(tradeErrorPool.orderStatus, 'trade_order_status', '')}</td>
							<td>${fns:getDictLabel(fn:trim(tradeErrorPool.transactionStatus),'trade_third_pay_status', '')}</td>
							<td><fmt:formatDate value="${tradeErrorPool.ordertime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${fns:getDictLabel(tradeErrorPool.handlestatus, 'error_pool_status', '')}</td>
							<td>
								<c:if test="${tradeErrorPool.handlestatus=='0'}">
									<shiro:hasPermission name="trade:tradeErrorPool:edit">
									<a class="btn btn-info btn-sm" href="${ctxa}/trade/tradeErrorPool/edit1.do?epId=${tradeErrorPool.epId}">
										<i class="fa fa-edit"></i> ${fns:fy('处理')}
									</a>
									</shiro:hasPermission>
								</c:if>
								<c:if test="${tradeErrorPool.handlestatus=='1'}">
									<shiro:hasPermission name="trade:tradeErrorPool:drop">
									<button type="button" class="btn btn-danger btn-sm deleteSure" href="${ctxa}/trade/tradeErrorPool/delete.do?epId=${tradeErrorPool.epId}">
										<i class="fa fa-trash-o"></i> ${fns:fy('删除')}
									</button>
									</shiro:hasPermission>
								</c:if>
							</td>
						</tr>
						<tr id="${index.count}" class="detail-extra">
							<td datano="0" colspan="14" >
								<p datano="0" columnno="1" class="dt-grid-cell">${fns:fy('店铺')}id: ${tradeErrorPool.storeId}</p>
								<p datano="0" columnno="2" class="dt-grid-cell">${fns:fy('支付方式')}id:${tradeErrorPool.payWayId}</p>
								<p datano="0" columnno="0" class="dt-grid-cell" style="word-break:break-all">${fns:fy('处理备注')}: ${tradeErrorPool.handleremark}</p>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr>
							<td colspan="11" style="line-height: 200px;color: #a2a2a2;">${fns:fy('暂无数据')}</td>
						</tr>
					</c:if>
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