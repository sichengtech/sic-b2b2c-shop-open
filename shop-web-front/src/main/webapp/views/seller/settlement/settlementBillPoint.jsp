<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品订单')}</title>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.10.2.min.js"></script>
<!-- sui -->
<script type="text/javascript" src="${ctxStatic}/sui/1.5.1/sui.min.js"></script>

<link rel="stylesheet" type="text/css" href="${ctxStatic}/sui/1.5.1/sui.min.css"/>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sui/1.5.1/sui-append.min.css"/>
<style type="text/css">
	.billTr{border-top:none!important;}
	.lastTr{border-bottom:1px solid #e6e6e6;}
	.title{font-size: 16px;}
	tbody th{width: 16%;font-size: 14px;}
</style>
<script language=javascript> 
	function doPrint() { 
		bdhtml=window.document.body.innerHTML; 
		sprnstr="<!--startprint-->"; 
		eprnstr="<!--endprint-->"; 
		prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17); 
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr)); 
		window.document.body.innerHTML=prnhtml; 
		window.print(); 
	}
</script> 
</head>
<body>
<!--startprint-->
<table class="sui-table" style="width: 450px;margin: 100px auto 0;">
	<thead>
		<tr>
			<th class="center title" colspan="2">${fns:fy('结算单')}</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th>${fns:fy('账单编号')}</th>
			<td>${settlementBill.billId}</td>
		</tr>
		<tr>
			<th class="billTr">${fns:fy('商家名称')}</th>
			<td class="billTr">${settlementBill.store.name}</td>
		</tr>
		<tr>
			<th class="billTr">${fns:fy('起止日期')}</th>
			<td class="billTr">
				<fmt:formatDate value="${settlementBill.beginTime}" pattern="yyyy-MM-dd"/> ${fns:fy('至')} 
				<fmt:formatDate value="${settlementBill.endTime}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr>
		  <th class="billTr">${fns:fy('出账日期')}</th>
		  <td class="billTr"><fmt:formatDate value="${settlementBill.balanceDate}" pattern="yyyy-MM-dd"/></td>
		</tr>
		<tr>
			<th class="billTr">${fns:fy('订单金额')}</th>
			<td class="billTr">${empty settlementBill.orderAmount?'0':settlementBill.orderAmount} ${fns:fy('元')}</td>
		</tr>
		<tr>
			<th class="billTr">${fns:fy('应收佣金')}</th>
			<td class="billTr">${empty settlementBill.platformCommission?'0':settlementBill.platformCommission} ${fns:fy('元')}</td>
		</tr>
		<tr>
			<th class="billTr">${fns:fy('退单金额')}</th>
			<td class="billTr">${empty settlementBill.refund?'0':settlementBill.refund} ${fns:fy('元')}</td>
		</tr>
		<tr>
			<th class="billTr">${fns:fy('应退佣金')}</th>
			<td class="billTr">${empty settlementBill.returnCommission?'0':settlementBill.returnCommission} ${fns:fy('元')}</td>
		</tr>
		<tr>
			<th class="billTr">${fns:fy('结算金额')}</th>
			<td class="billTr">${empty settlementBill.billAmount?'0':settlementBill.billAmount} ${fns:fy('元')}</td>
		</tr>
		<tr>
			<th class="billTr lastTr">${fns:fy('结算状态')}</th>
			<td class="billTr lastTr">${fns:getDictLabel(settlementBill.status, 'settlement_bill_status', '')}</td>
		</tr>
	</tbody>
</table>
<!--endprint-->	
<div class="" style="margin:37px auto;width: 68px;">
 	<button  class="sui-btn  btn-primary noprint" onclick="doPrint();">${fns:fy('打印')}</button>
</div>
</body>
</html>