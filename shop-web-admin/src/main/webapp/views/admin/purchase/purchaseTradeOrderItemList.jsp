<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('撮合交易订单详情管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/purchase/purchaseTradeOrderItemList.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('撮合交易订单详情列表')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> ${fns:fy('撮合交易订单详情列表')}</a></li>
			</ul>
		</header>

		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('撮合交易订单详情管理是查看采购订单详情')}</li>
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
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始 -->
			<div class="table-responsive">
			<table class="table table-hover table-condensed table-bordered">
				<thead>
					<tr>
						<th>${fns:fy('订单详情id')}</th>
						<th>${fns:fy('订单id')}</th>
						<th>${fns:fy('产品名称')}</th>
						<th>${fns:fy('规格型号')}</th>
						<th>${fns:fy('品牌')}</th>
						<th>${fns:fy('数量')}</th>
						<th>${fns:fy('采购单价')}</th>
						<th>${fns:fy('单位')}</th>
						<th>${fns:fy('采购备注')}</th>
						<th>${fns:fy('报价单价')}</th>
						<th>${fns:fy('报价备注')}</th>
						<th>${fns:fy('操作')}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="purchaseTradeOrderItem">
					<tr>
						<td>${purchaseTradeOrderItem.ptoiId}</td>
						<td>${purchaseTradeOrderItem.ptoId}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseTradeOrderItem.name}</textarea></td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseTradeOrderItem.model}</textarea></td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseTradeOrderItem.brand}</textarea></td>
						<td>${purchaseTradeOrderItem.amount}</td>
						<td>${purchaseTradeOrderItem.priceRequirement}</td>
						<td>${purchaseTradeOrderItem.unit}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseTradeOrderItem.purchaseRemarks}</textarea></td>
						<td>${purchaseTradeOrderItem.offerPrice}</td>
						<td><textarea class="form-control input-sm" rows="2" readonly="readonly">${purchaseTradeOrderItem.offerRemarks}</textarea></td>
						<td>
							<a class="btn btn-info btn-sm" href="${ctxa}/purchase/purchaseTradeOrder/list.do?purchaseTradeId=${purchaseTradeOrderItem.ptoId}">
								<i class="fa fa-eye"></i> ${fns:fy('查看采购订单')}
							</a>
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