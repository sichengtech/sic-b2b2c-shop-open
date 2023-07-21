<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<div class="modal-content">
	<div class="modal-body" style="padding: 0;">
		<form id="cancelOrdersForm" name="cancelOrdersForm" action="" method="post">
			<input type="hidden" name="ordersId" value="142">
			<table class="sui-table table-bordered" id="delTable">
				<tbody>
					<tr>
						<td class="" style="text-align: right;">${fns:fy('订单编号：')}</td>
						<td><div class="text-box"><span class="c-success" id="orderIdModal" name="ordersSn">${tradeOrder.orderId}</span></div></td>
					</tr>
					<tr>
						<td class="" style="text-align: right;">${fns:fy('物流公司：')}</td>
						<td><div class="text-box"><span class="c-success" id="orderIdModal" name="ordersSn">${tradeOrder.logisticsTemplateName}</span></div></td>
					</tr>
					<tr>
						<td class="" style="text-align: right;">${fns:fy('运单号码：')}</td>
						<td><div class="text-box"><span class="c-success" id="orderIdModal" name="ordersSn">${tradeOrder.trackingNo}</span></div></td>
					</tr>
					<tr>
						<td colspan="2">
							<c:if test="${empty resultMap['Traces']}">
								<div class="logisticsMess">${fns:fy('暂无物流信息')}</div>
							</c:if>
							<c:if test="${not empty resultMap['Traces']}">
								<div class="logisticsMess">
									<c:forEach items="${resultMap['Traces']}" var="trace" varStatus="traceIndex">
										<div class="trace">
											<div class="traceStatus" style="${traceIndex.first?'background: #56d495':''}"></div>
											<span style="${traceIndex.first?'color: #56d495':''}">${trace['AcceptStation']}</span>
											<p>${trace['AcceptTime']}</p>
										</div>
									</c:forEach>
								</div>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
