<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
<title>${fns:fy('订单详情')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
	 .orderMess th, .orderMess td{border-bottom:none;border-top:none;word-break:break-all;}
	 .btn li{margin-bottom: 5px;}
	 #delModal,#freightModal{width: 500px;margin-left:-250px;border: none;}
	 #delTable td{line-height: 25px;}
	 .checked li{line-height: 25px;}
	 #sui-msg{font-size: 12px;line-height: 27px;font-weight: normal;}
	 .sui-modal .modal-header{border-bottom:none!important;}
	 tfoot{background: #e6e6e6;}
	 tfoot td dl{text-align: right;margin-bottom: 5px;}
	 tfoot td dl span{font-size: 14px;font-weight: bolder;}
	 tfoot td dl dd{padding-right: 40px;}
	 .orderStatus{height: 37px;border-bottom: 1px dotted #e5e5e5;font-size: 16px;margin-bottom:20px;padding-left: 30px;}
	.sui-table.table-bordered-simple{margin-top: -1px;margin-bottom: -1px!important;}
	body{line-height: 12px!important;}
	.moreMess{height: 36px;line-height:36px; width: 350px;display:none; border: 1px solid #e6e6e6;position: absolute;bottom: -33px;left: 30px;background: #FBFBFA;padding-left: 10px;}
	.wrap{margin-bottom: 10px;}
	.sui-modal{border: none!important;border-radius:5px;}
	#steps-round{width: 82%;margin:55px auto;}
	.sui-msg.msg-block {margin: 10px !important;}
	.orderMess .memoTd{width: 60px;}
	.typographic ul{max-width: 230px;text-align: left;}
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
						<span>${fns:fy('订单详情')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('订单')}</li>
						<li>${fns:fy('商品订单')}</li>
						<li>${fns:fy('订单详情')}</li>
					</ul>
				</dt>
				<!-- 提示信息开始 -->
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('显示了订单的详细信息。')}</li>
								<li>${fns:fy('在此列表中，商家可以进行发货、取消订单等操作。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<!-- 提示信息结束 -->
				<!--状态导航开始 -->
				<div class="main-toptab">
					<ul class="sui-nav">
						<li><a href="${ctxs}/trade/tradeOrder/list.htm">${fns:fy('商品订单')}</a></li>
						<li class="active"><a href="javascript:;">${fns:fy('订单详情')}</a></li>
					</ul>
				</div>
				<!--状态导航结束 --> 

				<!-- 订单详情 开始-->
				<div class="grid-demo" id="orderMess" style="border: 1px solid #e5e5e5;margin-bottom: 30px;">
					<div class="sui-row">
						<div class="span5" style="margin-left: 10px;position: relative;">
							<table class="sui-table table-bordered-simple orderMess">
								<thead>
									<tr>
									 <th colspan="3">${fns:fy('订单信息')}</th>
									</tr>
								</thead>
								<tbody style="background: #FBFBFB;">
									<tr>
										<td style="text-align: right;">${fns:fy('收货人：')}</td>
										<td colspan="2">${tradeOrder.consignee},${tradeOrder.phone},${tradeOrder.provinceName}&nbsp;
										${tradeOrder.cityName}&nbsp;${tradeOrder.districtName}&nbsp;${tradeOrder.detailedAddress}</td>
									</tr>
									<tr>
										<td style="text-align: right;">${fns:fy('支付方式：')}</td>
										<td colspan="2"> ${tradeOrder.paymentMethodName}(${fns:fy('付款单号：')}${tradeOrder.thirdPayOrderNumber})</td>
									</tr>
									<tr>
										<td style="text-align: right;">${fns:fy('发票：')}</td>
										<td colspan="2">
											<c:if test="${not empty tradeOrder.tradeDeliver.deliverTitle}">
												${fns:fy('抬头')} (${tradeOrder.tradeDeliver.deliverTitle})${fns:fy('内容')}(${tradeOrder.tradeDeliver.deliverContent}) ${fns:fy('类型')} (${fns:getDictLabel(tradeOrder.tradeDeliver.deliverType, 'deliver_type', '')})
											</c:if>
										</td>
									</tr>
									<tr>
										<td class="">${fns:fy('商家留言：')}</td>
										<td colspan="2">${tradeOrder.sellerMemo}</td>
									</tr>
									<tr>
										<td class="memoTd" style="text-align: right;">${fns:fy('买家留言：')}</td>
										<td colspan="2">${tradeOrder.memo}</td>
									</tr>

								</tbody>
								<tfoot style="background:#F4F4F4;">
									<tr>
										<td style="text-align: right;">${fns:fy('订单编号：')}</td>
										<td>${tradeOrder.orderId}</td>
										<td class="more" style="cursor: pointer;">
											<a href="#" data-placement="bottom" data-toggle="tooltip" style="width:120px;"  data-align="right"
												title="<span>${fns:fy('下单时间：')}<fmt:formatDate value='${tradeOrder.placeOrderTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span><br/>">
												${fns:fy('更多')}<i class="sui-icon icon-tb-unfold"></i>
											</a>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
						<div class="span5" style="margin: 30px;">
							<div>
								<div class="orderStatus">${fns:fy('订单状态：')}<span>${fns:getDictLabel(tradeOrder.orderStatus, 'trade_order_status', '')}</span> </div>
								<div style="padding-left: 30px">
									<c:choose>
										<c:when test="${tradeOrder.orderStatus == '10'}">
											<p>${fns:fy('1.买家已经下单成功。')}</p>
											<p>${fns:fy('2.订单已提交商家进行备货发货准备。')}</p>
										</c:when>
										<c:when test="${tradeOrder.orderStatus == '20'}">
											<p>${fns:fy('1.买家已使用“')}${tradeOrder.paymentMethodName}${fns:fy('”方式成功对订单进行支付，支付单号 “')}${tradeOrder.orderId}${fns:fy('”。')}</p>
											<p>${fns:fy('2.买家已付款，请尽快安排发货。')}</p>
										</c:when>
										<c:when test="${tradeOrder.orderStatus == '30'}">
											<p>${fns:fy('1.商品已发出； ')}</p>
											<p style="word-break: break-all;">${fns:fy('2.发货备注：')}${tradeOrder.sellerMemo}</p>
											<p>${fns:fy('3.如果买家没有及时进行收货，系统将于')}
												<fmt:formatDate value="${lateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												${fns:fy('自动完成确认收货，完成交易。')}</p>
										</c:when>
										<c:when test="${tradeOrder.orderStatus == '40'}">
											<p>${fns:fy('1.交易已完成，买家可以对购买的商品及服务进行评价。')}</p>
											<p>${fns:fy('2.评价后的情况会在商品详细页面中显示，以供其它会员在购买时参考。')}</p>
											</p>
										</c:when>
										<c:when test="${tradeOrder.orderStatus == '50'}">
											<p>${fns:fy('1.交易已完成，买家可以对购买的商品及服务进行评价。')}</p>
											<p>${fns:fy('2.评价后的情况会在商品详细页面中显示，以供其它会员在购买时参考。')}</p>
										</c:when>
										<c:when test="${tradeOrder.orderStatus == '60'}">
											<p>${fns:fy('系统于')}<fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>${fns:fy('取消订单')}</p>
										</c:when>
									</c:choose>
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 订单详情结束 -->

				<!-- 订单进度条开始 -->
				<div class="sui-steps-round" id="steps-round">
					<div class="finished">
						<div class="wrap">
							<div class="round"><i class="sui-icon icon-pc-right"></i></div>
							<div class="bar" style="width: 137px;"></div>
						</div>
						<label>${fns:fy('提交订单')}</label>
					</div>
					<c:choose>
						<c:when test="${tradeOrder.orderStatus == '10'}">
							<div class="todo">
						</c:when>
						<c:when test="${tradeOrder.orderStatus == '20'}">
							<div class="current">
						</c:when>
						<c:otherwise>
							<div class="finished">
						</c:otherwise>
					</c:choose>
						<div class="wrap">
							<div class="round"><i class="sui-icon icon-pc-right"></i></div>
							<div class="bar" style="width: 137px;"></div>
						</div>
						<label>${fns:fy('完成付款')}</label>
					</div>
					
					<c:choose>
						<c:when test="${tradeOrder.orderStatus == '10' || tradeOrder.orderStatus == '20'}">
							<div class="todo">
						</c:when>
						<c:when test="${tradeOrder.orderStatus == '30'}">
							<div class="current">
						</c:when>
						<c:otherwise>
							<div class="finished">
						</c:otherwise>
					</c:choose>
						<div class="wrap">
							<div class="round"><i class="sui-icon icon-pc-right"></i></div>
							<div class="bar" style="width: 137px;"></div>
						</div>
						<label>${fns:fy('商家发货')}</label>
					</div>
					
					<c:choose>
					<c:when test="${tradeOrder.orderStatus == '10' || tradeOrder.orderStatus == '20' || tradeOrder.orderStatus == '30'}">
							<div class="todo">
					</c:when>
					<c:when test="${tradeOrder.orderStatus == '40'}">
						<div class="current">
					</c:when>
					<c:otherwise>
						<div class="finished">
					</c:otherwise>
					</c:choose>
						<div class="wrap">
							<div class="round"><i class="sui-icon icon-pc-right"></i></div>
							<div class="bar" style="width: 137px;"></div>
						</div>
						<label>${fns:fy('确认收货')}</label>
					</div>
					
					<c:choose>
					<c:when test="${tradeOrder.orderStatus == '10' || tradeOrder.orderStatus == '20' 
							|| tradeOrder.orderStatus == '30' || tradeOrder.orderStatus == '40'}">
							<div class="todo last">
					</c:when>
					<c:when test="${tradeOrder.orderStatus == '50'}">
						<div class="current last">
					</c:when>
					<c:otherwise>
						<div class="finished last">
					</c:otherwise>
					</c:choose>
						<div class="wrap">
							<div class="round"><i class="sui-icon icon-pc-right"></i></div>
						</div>
						<label style="width: 30px;">${fns:fy('评价')}</label>
					</div>
				</div>
				<!-- 订单进读条结束 -->

				<dd class="table-css">
					<!--table开始 -->
					<table class="sui-table table-bordered-simple orderMess">
						<thead>
							<tr>
								<th class="center">${fns:fy('商品')}</th>
								<th class="center">${fns:fy('单价（元）')}</th>
								<th class="center">${fns:fy('数量')}</th>
								<th class="center">${fns:fy('优惠')}</th>
								<th class="center">${fns:fy('实付*佣金比=应付佣金(元)')}</th>
								<th class="center">${fns:fy('交易操作')}</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${tradeOrder.tradeOrderItemList}" var="orderItem" varStatus="orderIndex">
								<tr>
									<td width="35%">
										<div class="typographic">
											<a href="${ctxf}/detail/${orderItem.PId}.htm" target="_blank">
												<img src="${ctxfs}${orderItem.thumbnailPath}" style="width: 80px;height: 80px;"
												onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
												<ul class="unstyled">
													<li><a href="${ctxf}/detail/${orderItem.PId}.htm" target="_blank">${orderItem.name}</a></li>
													<li>${orderItem.skuValue}</li>
												</ul>
											</a>
										</div>
									</td>
									<td width="10%" class="center">${fns:fy('￥')}${orderItem.price}</td>
									<td width="10%" class="center">X${orderItem.quantity}</td>
									<td width="13%" class="center">${orderItem.benefit}</td>
									<c:set var ="total" value="${orderItem.price * orderItem.quantity * orderItem.commissionRatio}"/>
									<td width="20%" class="center">${orderItem.price * orderItem.quantity} * <fmt:formatNumber value="${orderItem.commissionRatio}" type="percent"/> = <fmt:formatNumber value="${total}" pattern="0.00"/></td>
									<td width="12%" class="center">
										<ul class="btn">
											<li class="status" orderId="${tradeOrder.orderId}">${fns:getDictLabel(tradeOrder.orderStatus, 'trade_order_status', '')}</li>
											<shiro:hasPermission name="trade:tradeOrder:edit">
												<c:if test="${tradeOrder.orderStatus=='10'}">
													<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-large btn-warning delOrder" orderId="${tradeOrder.orderId}" type="2" data-keyboard="false">${fns:fy('取消订单')}</a></li>
												</c:if>
												<c:if test="${tradeOrder.orderStatus=='20'}">
													<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-large btn-success shipmentModal" orderId="${tradeOrder.orderId}">${fns:fy('订单发货')}</a></li>
												</c:if>
											</shiro:hasPermission>
										</ul>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="6">
									<dl class="align-right">
										<dd>
											<c:if test="${empty tradeOrder.freight || tradeOrder.freight=='0'}">
												(${fns:fy('免运费')})
											</c:if>
											<c:if test="${not empty tradeOrder.freight && tradeOrder.freight!='0'}">
												${fns:fy('运费:')}${tradeOrder.freight}${fns:fy('元')}
											</c:if>
										</dd>
									</dl>
									<dl class="sum">
										<span>${fns:fy('订单应付金额：')}</span>
										<font style="font-size: 22px;color: #C00;">${empty tradeOrder.offsetAmount?tradeOrder.amountPaid:tradeOrder.offsetAmount}</font><span>${fns:fy('元')}</span>
									</dl>
								</td>
							</tr>
						</tfoot>
					</table>
					 <!-- table结束 -->
				</dd>
			</dl>
			</div>
		</div>
	</div>
	<!-- 取消订单弹出框开始 -->
	<div id="delModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
		<div class="modal-content">
			<div class="modal-body" style="padding: 0;">
				<form id="cancelOrdersForm" name="cancelOrdersForm" method="post">
					<input type="hidden" name="ordersId" value="142">
					<table class="sui-table table-bordered" id="delTable" style="margin-bottom: 9px;">
						<tbody>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('订单编号：')}</td>
								<td><div class="text-box"><span class="c-success" id="orderIdModal" name="ordersSn">1450000000013700</span></div></td>
							</tr>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('取消缘由：')}</td>
								<td>
									<ul class="checked">
										<c:forEach items="${fns:getDictList('trade_cancel_order_reason1')}" var="tt" varStatus="index">
											<li>
												<input type="radio" id="reasonRadio10" name="cancelOrderReason" ${index.first?'checked':''} value="${tt.label}">
												<label for="reasonRadio10">${tt.label}</label>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="span2"></div>
					<div class="modal-footer" id="delModel-footer">
						<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large cancelOrderBtn" style="margin-right: 10px;" orderId="">${fns:fy('确定')}</button>
						<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large"  onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 取消订单弹出框结束 -->
	<!-- 修改运费弹出框开始 -->
	<div id="freightModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="border-bottom: none;padding: 11px 0;">
					<button type="button" data-dismiss="modal" aria-hidden="true" class="sui-close">×</button>
					<h4 id="myModalLabel" class="modal-title">${fns:fy('修改运费')}</h4>
				</div>
				<div class="modal-body" style="padding: 0;">
					<form id="cancelOrdersForm" name="cancelOrdersForm" action="" method="post">
						<input type="hidden" name="ordersId" value="142">
						<table class="sui-table table-bordered" id="delTable">
							<tbody>
								<tr>
									<td class="" style="text-align: right;">${fns:fy('买家：')}</td>
									<td><div class="text-box"><span class="c-success freightBuyer" name="ordersSn">adbc</span></div></td>
								</tr>
								<tr>
									<td class="" style="text-align: right;">${fns:fy('订单编号：')}</td>
									<td><div class="text-box"><span class="c-success freightOrderId" name="ordersSn">1450000000013700</span></div></td>
								</tr>
								<tr>
									<td class="" style="text-align: right;">${fns:fy('运费(元)：')}</td>
									<td><input type="text" placeholder="${fns:fy('请输入运费(元)')}" class="input-fat" style="line-height: 23px;" value="6.6"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large">${fns:fy('确定')}</button>
					<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large">${fns:fy('取消')}</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改运费弹出框结束 -->
	<script type="text/javascript" src="${ctx}/views/seller/trade/tradeOrderList.js"></script>
</body>
</html>