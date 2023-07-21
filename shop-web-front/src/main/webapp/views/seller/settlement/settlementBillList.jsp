<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('结算管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
	tbody {margin-bottom: 20px;}
	.lastTr td{border-bottom: 1px solid #e5e5e5;}
	.sui-table.table-bordered-simple{margin-top: -1px;margin-bottom: -1px!important;}
	.sui-modal{border: none!important;border-radius:5px;}
</style>
</head>
<body>
 <div class="main-content">
	<div class="sui-row-fluid">
	<div class="goods-list">
		<dl class="box">
			<dt class="box-header">
				<h4 class="pull-left">
					<i class="sui-icon icon-tb-addressbook"></i>
					<span>${fns:fy('结算管理')}</span>
					<%@ include file="../include/functionBtn.jsp"%>
				</h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('运营')}</li>
					<li>${fns:fy('运营管理')}</li>
					<li class="active">${fns:fy('结算管理')}</li>
				</ul>
			</dt>
			<dd class="sui-row-fluid form-horizontal screen pt20 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
				<div class="sui-msg msg-tips msg-block" style="margin-left: 10px;margin-right: 10px;">
					<div class="msg-con">
						 <ul>
							<h4>${fns:fy('操作提示：')}</h4>
							<li>${fns:fy('1、当前平台与商家的结算周期为 天 / 1 。')}</li>
							<li>${fns:fy('2、账单计算公式：本期应结 = 订单金额 - 平台分佣 - 退单金额 + 退还佣金 - 店铺推广费用 + 平台红包[下单时使用] + 应退平台红包[全部退款] + 预定订单未退定金。')}</li>
							<li>${fns:fy('3、账单处理流程为：系统出账 > 商家确认 > 平台审核 > 财务支付(完成结算) 4个环节，其中“商家确认”需要商家处理，请予以关注。')}</li>
						 </ul>
					</div>
					<s class="msg-icon" style="margin-top: 10px"></s>
				</div>
			</dd>
			<dd class="table-css">
				<table class="sui-table table-bordered-simple">
					<thead>
						<tr>
							<th width="8%" class="center">${fns:fy('编号')}</th>
							<th width="9%" class="center">${fns:fy('出账日期')}</th>
							<th width="20%" class="center">${fns:fy('起止日期')}</th>
							<th width="10" class="center">${fns:fy('订单金额（元）')}</th>
							<!-- <th class="center">${fns:fy('平台分佣（元）')}</th> -->
							<th width="10%" class="center">${fns:fy('退单金额（元）')}</th>
							<!-- <th class="center">${fns:fy('退还佣金（元）')}</th> -->
							<th width="10%" class="center">${fns:fy('本期应结（元）')}</th>
							<th width="8%" class="center">${fns:fy('状态')}</th>
							<th width="13%" class="center">${fns:fy('付款时间')}</th>
							<th width="12%" class="center">${fns:fy('操作')}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="settlementBill">
							<tr>
								<td width="8%" class="center">${settlementBill.billId}</td>
								<td width="9%" class="center">
									<fmt:formatDate value="${settlementBill.balanceDate}" pattern="yyyy.MM.dd"/>
								</td>
								<td width="20%" class="center">
									<fmt:formatDate value="${settlementBill.beginTime}" pattern="yyyy.MM.dd"/>-
									<fmt:formatDate value="${settlementBill.endTime}" pattern="yyyy.MM.dd"/>
								</td>
								<td width="10%" class="center">${not empty settlementBill.orderAmount && settlementBill.orderAmount ne '0.000'?settlementBill.orderAmount:'0'}</td>
								<%-- <td class="center">${settlementBill.platformCommission}</td> --%>
						 		<td width="10%" class="center">${not empty settlementBill.refund && settlementBill.refund ne '0.000'?settlementBill.refund:'0'}</td>
								<%-- <td class="center">${settlementBill.returnCommission}</td> --%>
								<td class="center">${not empty settlementBill.billAmount && settlementBill.billAmount ne '0.000'?settlementBill.billAmount:'0'}</td>
								<td width="8%" class="center">
									${fns:getDictLabel(settlementBill.status, 'settlement_bill_status', '')}
								</td>
								<td width="13%" class="center">
									<%-- <fmt:formatDate value="${settlementBill.payDate}" pattern="yyyy-MM-dd"/> --%>
									<fmt:formatDate value="${settlementBill.payDate}" pattern="yyyy.MM.dd HH:mm:ss"/>
								</td>
								<td width="12%" class="center">
									<shiro:hasPermission name="settlement:settlementBill:edit">
										<a href="${ctxs}/settlement/settlementBill/detail.htm?billId=${settlementBill.billId}" class="sui-btn btn-bordered btn-primary btn-large">${fns:fy('查看')}</a>
										<c:if test="${settlementBill.status == '10'}">
											<button type="button" href="${ctxs}/settlement/settlementBill/confirmationBill.htm?billId=${settlementBill.billId}" data-dismiss="modal" class="sui-btn btn-bordered btn-warning btn-large sure" deleteSureId="123456">${fns:fy('确认')}</button>
										</c:if>
									</shiro:hasPermission>
								</td>
							</tr>
						</c:forEach>
						<!-- 没有数据提示开始 -->
						<c:if test="${fn:length(page.list)=='0'}">
							<tr>
								<td colspan="9" class="no_product" style="height:400px;text-align: center;color: #9a9a9a;">
									<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
								</td>
							</tr>
						</c:if>
						<!-- 没有数据提示结束 -->
					</tbody>
				</table>
				</dd>
				<c:if test="${fn:length(page.list)>'0'}">
					<%@ include file="/views/seller/include/page.jsp"%>
				</c:if>
			</dl>
		</div>
	</div>
 </div>
 <script type="text/javascript" src="${ctx}/views/seller/settlement/settlementBillList.js"></script>
</body>
</html>