<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品订单')}</title>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sui/1.5.1/sui.min.css"/>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sui/1.5.1/sui-append.min.css"/>
<!-- sui -->
<script type="text/javascript" src="${ctxStatic}/sui/1.5.1/sui.min.js"></script>
<style type="text/css">
	.sui-table.table-bordered-simple th {
	    background-color: #ffffff;
	}
	p {
		margin-bottom: 0px;
		margin-top: 5px;
	}
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
<div class="" style="margin:10px 20px;">
 	<button  class="sui-btn btn-bordered btn-primary noprint" onclick="doPrint();">${fns:fy('打印')}</button>
</div>
<!--startprint-->
<table class="sui-table table-bordered-simple" style="width: 1000px;margin: 50px auto;">
  <thead>
    <tr>
        <c:set value="${fns:getSiteInfo()}" var="siteInfo"/>
      <td><img src="${siteInfo.sellerLogo != null ? ctxfs : ctxStatic}${siteInfo.sellerLogo != null ? siteInfo.sellerLogo : '/sicheng-member/images/logo.png'}"/></td>
      <td>
      	<p>${siteInfo.name}</p>
      	<p><a target="_blank" href="${ctxs}/index.htm">${fns:fy('商家中心')}</a></p>
      </td>
      <td colspan="3">
     	<div class="pull-left">
     		<p>${fns:fy('收货人：')}${tradeOrder.consignee}</p>
	      	<p>${fns:fy('会员：')}${tradeOrder.userMain.loginName}</p>
	      	<p>${fns:fy('地址：')}${tradeOrder.provinceName} ${tradeOrder.cityName} ${tradeOrder.districtName}</p>
     	</div>
      	<div class="pull-right">
      		<p>${fns:fy('邮编：')}${tradeOrder.zipCode}</p>
	      	<p>${fns:fy('电话：')}${tradeOrder.phone}</p>
	      	<p>${fns:fy('配送方式：')}${tradeOrder.logisticsTemplateName }</p>
      	</div>
      </td>
    </tr>
    <tr>
      <th>${fns:fy('订单编号：')}${tradeOrder.orderId}</th>
      <th colspan="2">${fns:fy('创建日期：')}
      	<fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd"/>
      </th>
      <th colspan="2">${fns:fy('打印日期：')}<fmt:formatDate value="${pointDate}" pattern="yyyy-MM-dd"/></th>
    </tr>
     <tr style="height: 30px;">
     	<td></td><td></td><td></td><td></td><td></td>
     </tr>
     <tr>
      <th width="25%">${fns:fy('序号')}</th>
      <th width="43%">${fns:fy('商品名称')}</th>
      <th width="11%">${fns:fy('商品价格')}</th>
      <th width="11%">${fns:fy('数量')}</th>
      <th width="10%">${fns:fy('小计')}</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${tradeOrder.tradeOrderItemList}" var="item" varStatus="ord">
  <tr>
      <td>${ord.index+1}</td>
      <td>${item.name}</td>
      <td>${item.price}</td>
      <td>${item.quantity}</td>
      <td>${fns:fy('￥')}${item.price * item.quantity}</td>
   </tr>
  </c:forEach>
      <td colspan="2">${fns:fy('附言：')}${tradeOrder.memo}</td>
      <td colspan="3">
      	<p>${fns:fy('商品价格：')}${fns:fy('￥')}${tradeOrder.amountPaid- tradeOrder.freight}</p>
      	<p>${fns:fy('支付运费：')}${fns:fy('￥')}${tradeOrder.freight}</p>
      	<p>${fns:fy('订单金额：')}${fns:fy('￥')}${tradeOrder.amountPaid}</p>
      </td>
    </tr>
    
  </tbody>
</table>
<!--endprint-->	
</body>
</html>