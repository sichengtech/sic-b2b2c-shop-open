<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('订单管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseTradeOrderList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('交易订单列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('交易订单列表')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('订单管理是查看采购撮合交易订单信息')}</li>
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
				<form action="${ctxa}/purchase/purchaseTradeOrder/list.do" method="get" id="searchForm">
					<div class="col-sm-1">
					</div>
					<div class="col-sm-2">
						<input type="text" name="purchaseTradeId"  maxlength="9" class="form-control input-sm" placeholder="${fns:fy('请输入订单编号')}" value="${purchaseTradeOrder.purchaseTradeId}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="purchaseName"  maxlength="32" class="form-control input-sm" placeholder="${fns:fy('请输入采购方')}" value="${purchaseName}"/>
					</div>
					<div class="col-sm-2">
						<input type="text" name="offerName"  maxlength="32" class="form-control input-sm" placeholder="${fns:fy('请输入报价方')}" value="${offerName}"/>
					</div>
					<div class="col-sm-2">
						<select name="status" class="form-control input-sm">
							<option value="" class="firstOption">--${fns:fy('请选择空间状态')}--</option>
							<c:forEach items="${fns:getDictList('purchase_order_status')}" var="item">
								<option value="${item.value}" ${item.value==purchaseTradeOrder.status?"selected":""}>${item.label}</option>
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
						<th>${fns:fy('订单编号')}</th>
						<th>${fns:fy('采购方')}</th>
						<th>${fns:fy('报价方')}</th>
						<th>${fns:fy('采购标题')}</th>
						<th>${fns:fy('采购内容')}</th>
						<th>${fns:fy('总价格')}</th>
						<th>${fns:fy('订单状态')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="purchaseTradeOrder">
					<tr>
						<td class="detail"><i class="fa fa-plus"></i></span></td>
						<td>${purchaseTradeOrder.purchaseTradeId}</td>
						<td>${purchaseTradeOrder.purchaseUser.loginName}</td>
						<td>${purchaseTradeOrder.offerUser.loginName}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseTradeOrder.title}</textarea></td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseTradeOrder.content}</textarea></td>
						<td>${purchaseTradeOrder.price}${fns:fy('元')}</td>
						<td>${fns:getDictLabel(purchaseTradeOrder.status, 'purchase_order_status', '')}</td>
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/purchase/purchaseTradeOrderItem/list.do?ptoId=${purchaseTradeOrder.purchaseTradeId}">
								<i class="fa fa-eye"></i> ${fns:fy('查看详情')}
							</a>
							<c:if test="${purchaseTradeOrder.status!=10}">
								<a class="btn btn-info btn-sm" href="${ctxa}/purchase/purchaseTradeVoucher/list.do?purchaseTradeId=${purchaseTradeOrder.purchaseTradeId}">
									<i class="fa fa-eye"></i> ${fns:fy('查看交易凭证')}
								</a>
							</c:if>
						</td>
					</tr>
					<tr class="detail-extra">
						<td datano="0" colspan="16" >
							<p datano="0" columnno="0" class="dt-grid-cell">
								${fns:fy('采购方信息：')}${purchaseTradeOrder.purchaseUser.storeEnter.companyName}&nbsp;
								${purchaseTradeOrder.purchaseUser.storeEnter.contact}&nbsp;
								${purchaseTradeOrder.purchaseUser.mobile}
							</p>
							<p datano="0" columnno="0" class="dt-grid-cell">
								${fns:fy('报价方信息：')}${purchaseTradeOrder.offerUser.storeEnter.companyName}&nbsp;
								${purchaseTradeOrder.offerUser.storeEnter.contact}&nbsp;
								${purchaseTradeOrder.offerUser.mobile}
							</p>
						</td> 
					</tr>
					</c:forEach>
					<c:if test="${empty page.list}">
						<tr>
							<td datano="0" colspan="14"><div class="notData">${fns:fy('无查询结果')}</div></td>
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