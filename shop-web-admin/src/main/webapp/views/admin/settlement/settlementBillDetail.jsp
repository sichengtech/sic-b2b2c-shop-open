<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('账单详情')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementBillDetail.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right">
			<h4 class="title">${fns:fy('账单详情')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>
		<div class="panel-body settlementBillDelegate">
				<!-- 账单信息开始 -->
				<div class="alert alert-info alert-block fade in" id="">
					<h4>${fns:fy('账单编号')}&nbsp;:&nbsp;${settlementBill.billId}</h4>
					<p>${fns:fy('商家')} :${settlementBill.store.name}</p>
					<p>${fns:fy('起止日期')} :<fmt:formatDate value="${settlementBill.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/> ${fns:fy('至')}
							 <fmt:formatDate value="${settlementBill.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</p>
					<p>${fns:fy('出账日期')} :<fmt:formatDate value="${settlementBill.balanceDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
					<p>${fns:fy('商家平台应结金额')}(${fns:fy('元')}) : ${settlementBill.billAmount} = ${empty settlementBill.orderAmount ?'0':settlementBill.orderAmount}(${fns:fy('订单金额')}) 
								- ${empty settlementBill.platformCommission ?'0':settlementBill.platformCommission}(${fns:fy('收取佣金')})
								- ${empty settlementBill.refund ?'0':settlementBill.refund} (${fns:fy('退单金额')}) 
								+ ${empty settlementBill.returnCommission ?'0':settlementBill.returnCommission} (${fns:fy('退还佣金')}) 
								</p>
					<p>${fns:fy('结算状态')} : ${fns:getDictLabel(settlementBill.status, 'settlement_bill_status', '')}</p>
				</div>
				<!-- 账单信息结束 --> 
				<!-- 按钮开始 -->
				<div class="row" style="margin-bottom:10px;">
					<div class="col-sm-2">
						<div class="btn-group">
							<!--刷新按钮 -->
							<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
							<!--返回列表 -->
							<a class="btn btn-default btn-sm tooltips" title="${fns:fy('返回')}" href="#" onClick="javascript:history.go(-1);">
								<i class="fa fa-mail-reply"></i>
							</a>
						</div>
					</div>
					<form action="" id="searchForm">
						<input type="hidden" name="beginPlaceOrderTime" id="beginPlaceOrderTime" value="<fmt:formatDate value="${settlementBill.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
						<input type="hidden" name="endPlaceOrderTime" id="endPlaceOrderTime" value="<fmt:formatDate value="${settlementBill.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
						<input type="hidden" name="billId" value="${settlementBill.billId}"/>
						<div class="col-sm-2 col-md-offset-5">
							<input type="text" name="orderId" id="orderId" class="form-control input-sm" placeholder="${fns:fy('订单编号')}">
						</div>
						<div class="col-sm-2">
							 <select class="form-control input-sm" id="orderType" name="orderType">
								<option value="1" class="firstOption">${fns:fy('订单列表')}</option>
								<option value="2" class="firstOption">${fns:fy('退单列表')}</option> 
							</select>
						</div>
						<div class="col-sm-1" style="">
							<button type="button" class="btn btn-info btn-sm" id="search" ><i class="fa fa-search"></i>${fns:fy('搜索')}</button>
						</div>
					</form>
				</div>
				<!-- 按钮结束 --> 
				<!-- Table开始 -->
				<div class="table-responsive" id="tableDiv"></div>
			<!-- table结束 -->
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>