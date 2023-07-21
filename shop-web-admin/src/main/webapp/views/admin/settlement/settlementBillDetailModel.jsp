<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<c:if test="${type ==1}">
	<table class="table table-hover table-condensed table-bordered">
		 <thead>
			<tr>
				<th>${fns:fy('订单编号')}</th>
				<th>${fns:fy('订单来源')}</th>
				<th>${fns:fy('下单时间')}</th>
				<th>${fns:fy('订单金额')}(${fns:fy('元')})</th>
				<th>${fns:fy('支付金额')}</th>
				<th>${fns:fy('支付方式')}</th>
				<th>${fns:fy('第三方支付交易号')}</th>
				<th>${fns:fy('支付时间')}</th>
				<th>${fns:fy('收取佣金')}(${fns:fy('元')})</th>
				<th width="17%">${fns:fy('管理操作')}</th>
			</tr>
		</thead> 
		<tbody id="orderTbody">
			<c:forEach items="${page.list}" var="order">
				<tr>
					<td>${order.orderId}</td> 
					<td>${fns:getDictLabel(order.sources, 'settlement_terminal_type', '')}</td> 
					<td><fmt:formatDate value="${order.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${empty order.offsetAmount ? order.amountPaid : order.offsetAmount}</td>
					<td>${order.onlinePayMoney}</td>
					<td>${order.paymentMethodName}</td>
					<td>${order.thirdPayOrderNumber}</td>
					<td><fmt:formatDate value="${order.payOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${not empty order.fee && order.fee ne '0.000'?order.fee:'0'}</td>
					<td>
						<a class="btn btn-info btn-sm" href="${ctxa}/trade/tradeOrder/tradeOrderDetail.do?orderId=${order.orderId}">
							<i class="fa fa-eye"></i> ${fns:fy('查看')}
						 </a>
					</td> 
				</tr>
			</c:forEach>
			<c:if test="${fn:length(page.list)==0}">
				<tr>
					<td colspan="10" class="noDateTd" style="height: 300px;color: #969696">${fns:fy('很遗憾,无订单数据')}</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</c:if>
<c:if test="${type ==2}">
	<table class="table table-hover table-condensed table-bordered">
		 <thead>
			<tr>
				<th>${fns:fy('退单单号')}</th>
				<th>${fns:fy('订单编号')}</th>
				<th>${fns:fy('退单金额')}(${fns:fy('元')})</th>
				<th>${fns:fy('退还佣金')}(${fns:fy('元')})</th>
				<th>${fns:fy('申请原因')}</th>
				<th>${fns:fy('平台处理时间')}</th>
				<th width="17%">${fns:fy('管理操作')}</th>
			</tr>
		</thead> 
		<tbody id="orderTbody">
			<c:forEach items="${page.list}" var="returnOrder">
				<tr>
					<td>${returnOrder.returnOrderId}</td>
					<td>${returnOrder.orderId}</td>
					<td>${returnOrder.returnMoney}</td>
					<td>${returnOrder.returnCommission}</td>
					<td>${returnOrder.returnReason}</td>
					<td><fmt:formatDate value="${returnOrder.systemAgreeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						 <a class="btn btn-info btn-sm" href="${ctxa}/trade/tradeReturnOrder/handle1.do?returnOrderId=${returnOrder.returnOrderId}&stat=2&type=${type}">
		                    <i class="fa fa-eye"></i> ${fns:fy('查看')}
		                 </a>
					</td> 
				</tr>
			</c:forEach>
			<c:if test="${fn:length(page.list)==0}">
				<tr>
					<td colspan="10" class="noDateTd" style="height: 300px;color: #969696">${fns:fy('很遗憾,无退单数据')}</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</c:if>
<c:if test="${fn:length(page.list)>0}">
<!-- 分页信息开始 -->
<button class="btn btn-info btn-sm lastPage" ${page.firstPage?'disabled':''} pageNo="${page.pageNo}"><< ${fns:fy('上一页')}</button>
<span>${page.pageNo}</span>
<button class="btn btn-info btn-sm nextPage" ${page.lastPage?'disabled':''} pageNo="${page.pageNo}"> ${fns:fy('下一页')}>></button>
<span>${fns:fyParam('总页数',page.totalPage)}</span>
<!-- 分页信息结束 -->
</c:if>