<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>订单详情</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<style type="text/css">
	.productdetail{text-align: left;}
	#panel-body{padding-bottom: 0;}
	#table{margin-bottom: 0;}
</style>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">订单详情</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header> 
		<!-- panel-body开始 -->		 
		<div class="panel-body" id="panel-body">
			<!-- 订单信息开始 -->
			<div class="alert alert-info alert-block fade in" id="">
				<div class="row">
					<div class="col-sm-4">
						<small>商家信息</small>
						<address class="m-t-5 m-b-5">
							<h4>${tradeOrder.BName}</h4>
								 卖家帐号&nbsp;:&nbsp;${storeSeller.loginName}<br>
								 联系电话&nbsp;:&nbsp;<br>${storeEnte.contactNumber}
								 发货时间&nbsp;:&nbsp;<fmt:formatDate value="${tradeOrder.deliverProductDate}" pattern="yyyy-MM-dd HH:mm:ss"/><br>
								 物流公司&nbsp;:&nbsp;${tradeOrder.logisticsTemplateName}<br>
								 物流单号&nbsp;:&nbsp;${tradeOrder.trackingNo}
						</address>
					</div>
					<div class="col-sm-4">
						<small>买家信息</small>
						<address class="m-t-5 m-b-5">
							<h4>${userMain.loginName}</h4>
							收 货 人&nbsp;:&nbsp;${tradeOrder.consignee}<br>
							收货地址&nbsp;:&nbsp;${tradeOrder.address}<br>
							联系电话&nbsp;:&nbsp;${tradeOrder.phone}<br>
							发票信息&nbsp;:&nbsp;
							<c:if test="${not empty tradeDeliver.deliverTitle}">
								抬头 (${tradeDeliver.deliverTitle})内容(${tradeDeliver.deliverContent}) 类型 (${fns:getDictLabel(tradeDeliver.deliverType, 'deliver_type', '')})
							</c:if></br>
							买家留言&nbsp;:&nbsp;${tradeOrder.memo}
						</address>
					</div>
					<div class="col-sm-4 text-right">
						<small>订单信息</small>
						<address class="m-t-5 m-b-5">
						<h4>生成订单</h4>
								 订单状态&nbsp;:&nbsp;${fns:getDictLabel(tradeOrder.orderStatus, 'trade_order_status', '')}<br>
								 支付方式&nbsp;:&nbsp;${tradeOrder.paymentMethodName}<br>
								 下单时间 &nbsp;:&nbsp;<fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/><br>
						</address>
					</div>
				</div>
			</div>
			<!-- 订单信息结束 --> 
			<!-- 按钮开始 -->
			<div class="row" style="margin-bottom:10px;">
				<div class="col-sm-2">
					<div class="btn-group">
						<!--刷新按钮 -->
						<button type="button" class="btn btn-default btn-sm tooltips" title="刷新" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!--返回列表 -->
						<a class="btn btn-default btn-sm tooltips" title="返回" href="#" onClick="javascript:history.go(-1);">
							<i class="fa fa-mail-reply"></i>
						</a>
					</div>
				</div>
			</div>
			<!-- 按钮结束 -->
			<!-- Table开始	-->
			<div class="table-responsive">
				<table class="table table-hover table-condensed" id="table">
					<thead> 
						<tr>
							<th>商品</th>
							<th>单价(元)</th> 
							<th>优惠(元)</th> 
							<th>数量</th> 
							<th>实付*佣金比=实付佣金(元)</th>
						</tr>
					</thead> 
					<tbody>
						<c:forEach items="${tradeOrderItemList}" var="tradeOrderItem">
							<tr>
						 		<td>
								 	<div class="row">
									 	<div class="col-sm-2">
									 		<a href="http://java.bizpower.com/web/goods/165" target="_blank">
									 			<c:choose>
									 				<c:when test="${not empty  tradeOrderItem.thumbnailPath}">
									 					<img src="${tradeOrderItem.thumbnailPath}" width="60" height="60">
									 				</c:when>
									 				<c:otherwise>
											 			<img src="http://java.bizpower.com/upload/image/d8/f3/d8f30d5f39319ddc6775d5ada34fe74d.jpg@60w_60h">
									 				</c:otherwise>
									 			</c:choose>
									 		</a>
									 	</div>
										<dl class="col-sm-10 productdetail">
										 <dt>
											<a href="http://java.bizpower.com/web/goods/165" target="_blank">
												${tradeOrderItem.name}
											</a>
										 </dt>
										 <dd>${tradeOrderItem.skuValue}</dd>
										</dl>
								 	</div>
								</td>
								<td>${tradeOrderItem.price}</td> 
								<td></td>
								<td>${tradeOrderItem.quantity}</td>
								<%-- 通过商品分类或店铺获取商品佣金 --%>
								<c:set var ="total" value="${tradeOrderItem.price * tradeOrderItem.commissionRatio}"/>
								<td>${tradeOrderItem.price} * <fmt:formatNumber value="${tradeOrderItem.commissionRatio}" type="percent"/> = <fmt:formatNumber value="${total}" pattern="0.00"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- table结束 -->
		</div>
		<!-- panel-body结束 -->
		<div class="panel-footer">
			<div class="row">
				<div class="col-sm-10 text-left">
					<h3 style="margin-top:10px;">(免运费)</h3>
				</div>
				<div class="col-sm-2 text-right">
					<div class="text-left">订单应付金额:</div>
					<div class="text-right"><h3 style="margin:0;">${tradeOrder.amountPaid}元</h3></div>
				</div>
			</div>
		</div>
	</section>
	<!-- panel end -->
</body>
</html>