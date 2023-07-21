<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/seller/include/taglib.jsp"%>
<c:if test="${type ==1}">
	<table class="sui-table table-bordered-simple" id="orderTable">
		<thead>
			<tr>
				<th class="center">${fns:fy('订单编号')}</th>
				<th class="center">${fns:fy('下单时间')}</th>
				<th class="center">${fns:fy('成交时间')}</th>
				<th class="center">${fns:fy('订单金额（元）')}</th>
				<th class="center">${fns:fy('平台分佣（元）')}</th>
				<th class="center">${fns:fy('操作')}</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="order">
				<tr>
					<td class="center">${order.orderId}</td>
					<td class="center"><fmt:formatDate value="${order.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="center"><fmt:formatDate value="${order.productReceiptDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="center">${empty order.offsetAmount ? order.amountPaid : order.offsetAmount}</td>
					<td class="center">${not empty order.fee && order.fee ne '0.000'?order.fee:'0'}</td>
					<td class="center">
						<a href="${ctxs}/trade/tradeOrder/tradeOrderDetail.htm?orderId=${order.orderId}" class="sui-btn btn-xlarge btn-primary">${fns:fy('查看订单')}</a>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${fn:length(page.list)==0}">
				<tr>
					<td colspan="6" class="noDateTd">${fns:fy('很遗憾，无订单数据！')}</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</c:if>
<c:if test="${type ==2}">
	<table class="sui-table table-bordered-simple" id="orderTable">
		 <thead>
			<tr>
				<th class="center">${fns:fy('退单单号')}</th>
				<th class="center">${fns:fy('订单编号')}</th>
				<th class="center">${fns:fy('退单金额(元)')}</th>
				<th class="center">${fns:fy('退还佣金(元)')}</th>
				<th class="center">${fns:fy('商家处理时间')}</th>
				<th class="center">${fns:fy('管理操作')}</th>
			</tr>
		</thead> 
		<tbody id="orderTbody">
			<c:forEach items="${page.list}" var="returnOrder">
				<tr>
					<td class="center">${returnOrder.returnOrderId}</td>
					<td class="center">${returnOrder.orderId}</td>
					<td class="center">${returnOrder.returnMoney}</td>
					<td class="center">${returnOrder.returnCommission}</td>
					<td class="center"><fmt:formatDate value="${returnOrder.businessHandleDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="center">
						<a href="${ctxs}/trade/tradeReturnOrder/handle1.htm?returnOrderId=${returnOrder.returnOrderId}" class="sui-btn btn-xlarge btn-primary">${fns:fy('查看')}</a>
					</td> 
				</tr>
			</c:forEach>
			<c:if test="${fn:length(page.list)==0}">
				<tr>
					<td colspan="6" class="noDateTd">${fns:fy('很遗憾，无退单数据！')}</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</c:if>
<div class="settlementBillPage">
	<button type="button" class="sui-btn btn-xlarge btn-primary lastPage" disabled="${page.firstPage?'disabled':''}" pageNo="${page.pageNo}">${fns:fy('«上一页')}</button>
	<span>${page.pageNo}</span>
	<button type="button" class="sui-btn btn-xlarge btn-primary nextPage" disabled="${page.lastPage?'disabled':''}" pageNo="${page.pageNo}">${fns:fy('下一页»')}</button>
	<span>${fns:fy('共')}${page.totalPage}${fns:fy('页')}</span>
</div>
