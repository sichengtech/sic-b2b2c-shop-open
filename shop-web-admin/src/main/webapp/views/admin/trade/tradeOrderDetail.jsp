<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('订单详情')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<style type="text/css">
	.productdetail{text-align: left;}
	#panel-body{padding-bottom: 0;}
	#table{margin-bottom: 0;}
	#panel-body address{word-break:break-all;}
</style>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${fns:fy('订单详情')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header> 
		<!-- panel-body开始 -->		 
		<div class="panel-body" id="panel-body">
			<!-- 订单信息开始 -->
			<div class="alert alert-info alert-block fade in" id="">
				<div class="row">
					<div class="col-sm-4">
						<small>${fns:fy('商家信息')}</small>
						<address class="m-t-5 m-b-5">
							<h4>${tradeOrder.BName}</h4>
								 ${fns:fy('卖家帐号')}&nbsp;:&nbsp;${tradeOrder.store.name}<br>
								 ${fns:fy('联系电话')}&nbsp;:&nbsp;${tradeOrder.storeEnter.contactNumber}<br>
								 ${fns:fy('发货时间')}&nbsp;:&nbsp;<fmt:formatDate value="${tradeOrder.deliverProductDate}" pattern="yyyy-MM-dd HH:mm:ss"/><br>
								 ${fns:fy('物流公司')}&nbsp;:&nbsp;${tradeOrder.logisticsTemplateName}<br>
								 ${fns:fy('物流单号')}&nbsp;:&nbsp;${tradeOrder.trackingNo}<br>
								 ${fns:fy('商家留言')}&nbsp;:&nbsp;${tradeOrder.sellerMemo}
						</address>
					</div>
					<div class="col-sm-4">
						<small>${fns:fy('买家信息')}</small>
						<address class="m-t-5 m-b-5">
							<h4>${tradeOrder.userMain.loginName}</h4>
							${fns:fy('收货人')}&nbsp;:&nbsp;${tradeOrder.consignee}<br>
							${fns:fy('收货地址')}&nbsp;:&nbsp;${tradeOrder.provinceName}${tradeOrder.cityName}${tradeOrder.districtName}${tradeOrder.detailedAddress}<br>
							${fns:fy('联系电话')}&nbsp;:&nbsp;${tradeOrder.phone}<br>
							${fns:fy('发票信息')}&nbsp;:&nbsp;
							<c:if test="${not empty tradeOrder.tradeDeliver.deliverTitle}">
								${fns:fy('发票抬头')} (${tradeOrder.tradeDeliver.deliverTitle})
								${fns:fy('内容')}(${tradeOrder.tradeDeliver.deliverContent}) 
								${fns:fy('类型')} (${fns:getDictLabel(tradeOrder.tradeDeliver.deliverType, 'deliver_type', '')})
							</c:if></br>
							${fns:fy('买家留言')}&nbsp;:&nbsp;${tradeOrder.memo}
						</address>
					</div>
					<div class="col-sm-4 text-right">
						<small>${fns:fy('订单信息')}</small>
						<address class="m-t-5 m-b-5">
						<h4>${fns:fy('生成订单')}</h4>
								 ${fns:fy('订单状态')}&nbsp;:&nbsp;${fns:getDictLabel(tradeOrder.orderStatus, 'trade_order_status', '')}<br>
								 ${fns:fy('支付方式')}&nbsp;:&nbsp;${tradeOrder.paymentMethodName}<br>
								 ${fns:fy('下单时间')}&nbsp;:&nbsp;<fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/><br>
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
						<button type="button" class="btn btn-default btn-sm tooltips" title="${fns:fy('刷新')}" onclick="location.reload();"><i class="fa fa-refresh"></i></button>
						<!--返回列表 -->
						<a class="btn btn-default btn-sm tooltips" title="${fns:fy('返回')}" href="#" onClick="javascript:history.go(-1);">
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
							<th>${fns:fy('商品')}</th>
							<th>${fns:fy('单价')}(${fns:fy('元')})</th> 
							<th>${fns:fy('优惠')}(${fns:fy('元')})</th> 
							<th>${fns:fy('数量')}</th> 
							<th>${fns:fy('实付*佣金比')}=${fns:fy('实付佣金')}(${fns:fy('元')})</th>
						</tr>
					</thead> 
					<tbody>
						<c:forEach items="${tradeOrder.tradeOrderItemList}" var="tradeOrderItem">
							<tr>
						 		<td width="35%">
								 	<div class="" style="float: left;margin-right: 10px;">
								 		<a href="${ctxf}/detail/${tradeOrderItem.PId}.htm" target="_blank">
								 			<img src="${ctxfs}${tradeOrderItem.thumbnailPath}" style="width: 80px;height: 80px"
												onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
								 		</a>
								 	</div>
									<dl class=" productdetail">
										<dt>
											<a href="${ctxf}/detail/${tradeOrderItem.PId}.htm" target="_blank">
												${tradeOrderItem.name}
											</a>
										</dt>
										<dd>${tradeOrderItem.skuValue}</dd>
									</dl>
								</td>
								<td width="10%">${tradeOrderItem.price}</td> 
								<td width="25%">${tradeOrderItem.benefit}</td>
								<td width="10%">${tradeOrderItem.quantity}</td>
								<%-- 通过商品分类或店铺获取商品佣金 --%>
								<c:set var ="total" value="${tradeOrderItem.price * tradeOrderItem.quantity * tradeOrderItem.commissionRatio}"/>
								<td width="20%">${tradeOrderItem.price * tradeOrderItem.quantity} * <fmt:formatNumber value="${tradeOrderItem.commissionRatio}" type="percent"/> = <fmt:formatNumber value="${total}" pattern="0.00"/></td>
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
					<h3 style="margin-top:10px;">
						<c:if test="${empty tradeOrder.freight || tradeOrder.freight=='0'}">
							(${fns:fy('免运费')})
						</c:if>
						<c:if test="${not empty tradeOrder.freight && tradeOrder.freight!='0'}">
							${fns:fy('运费')}:${not empty tradeOrder.freight && tradeOrder.freight ne '0.000'?tradeOrder.freight:'0'}${fns:fy('元')}
						</c:if>
					</h3>
				</div>
				<div class="col-sm-2 text-right">
					<div class="text-left">${fns:fy('订单应付金额')}:</div>
					<div class="text-right"><h3 style="margin:0;">${empty tradeOrder.offsetAmount?tradeOrder.amountPaid:tradeOrder.offsetAmount}${fns:fy('元')}</h3></div>
				</div>
			</div>
		</div>
	</section>
	<!-- panel end -->
</body>
</html>