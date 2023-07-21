<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品订单')}</title>
<meta name="decorator" content="member"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/trade/tradeOrderList.js"></script>
<!-- 微信支付js -->
<script type="text/javascript" src="${ctx}/views/pay/weixinPay.js"></script>
<style type="text/css">
	.receiptTip{background-color: #FAFAFA;padding: 9px 19px;border-bottom: 1px solid #e6e6e6;}
	.delayTip{color:#a5a2a2;margin-top: 5px;}
	.delayDay{width: 93px;height: 25px;}
	.logisticsMess{border: solid 1px #E7E7E7;background: #FAFAFA;margin: 15px;padding: 10px;}
	.sui-table.orderlist-table td{vertical-align:middle}
	#receiptModal-footer{margin-top: 10px;text-align: right;margin-right: 20px;}
	.trace{min-height: 30px;border-left:1px solid #d9d9d9;}
	.traceStatus{width:5px;height:5px;border: 3px solid #f3f3f3;background: #d9d9d9; margin-left: -6px;border-radius: 5px;margin-right: 7px;}
	.trace p{margin-bottom: 0;margin-left: 14px;color: #a09d9d;}
	.trace span{margin-left: 14px;}
	#searchForm .input-date{width: 145px;}
</style>
<script type="text/javascript">
	var daysOfReceipt='${daysOfReceipt}';
</script>
</head>
<body>
	<!-- main-center开始 -->
	<div class="main-center">
		<dl>
			<dt>
				<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('交易中心')} > ${fns:fy('商品订单')}</div>
				<i class="sui-icon icon-tb-list"></i> ${fns:fy('商品订单')}
			</dt>
			<dd>
				<ul class="sui-nav nav-tabs">
					<li class="${orderStatus eq null?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm" >${fns:fy('全部订单')} <font></font></a></li>
					<li class="${orderStatus eq '10'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=10" >${fns:fy('待付款')} <font>(${status10Count})</font></a></li>
					<li class="${orderStatus eq '20'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=20" >${fns:fy('待发货')}<font>(${status20Count})</font></a></li>
					<li class="${orderStatus eq '30'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=30" >${fns:fy('待收货')} <font>(${status30Count})</font></a></li>
					<li class="${orderStatus eq '40'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=40" >${fns:fy('待评价')} <font>(${status40Count})</font></a></li>
					<li class="${orderStatus eq '60'?'active':'' }"><a href="${ctxm}/trade/tradeOrder/list.htm?orderStatus=60" >${fns:fy('已取消')}<font>(${status60Count})</font></a></li>
				</ul>
			</dd>
			<dd class="table-css">
				<!-- 搜索表单开始 -->
				<div class="sui-row-fluid">
					<form class="sui-form form-inline"  style="margin-top: 10px;" action="${ctxm}/trade/tradeOrder/list.htm" id="searchForm">
						<div class="span2"></div>
						<div class="span5">
							<div class="control-group">
								<label class="col-sm-3 control-label text-right">${fns:fy('下单时间：')}</label>
								<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
								id="" value="<fmt:formatDate value="${tradeOrder.beginPlaceOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="beginPlaceOrderTime" format="yyyy-MM-dd" placeholder="${fns:fy('请选择开始下单时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"> -
								<input type="text" class="form-control input-sm J-date-start input-date" nc-format="" 
								id="" value="<fmt:formatDate value="${tradeOrder.endPlaceOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="endPlaceOrderTime" format="yyyy-MM-dd" placeholder="${fns:fy('请选择结束下单时间')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"> 
							</div>
						</div>
						<div class="span4 text-align">
							<div class="control-group">
								<span class="sui-dropdown dropdown-bordered select">
									<span class="dropdown-inner">
									<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input value="${searchCate}" name="searchCate" type="hidden"><i class="caret"></i>
										<span>
											<c:choose>
												<c:when test="${empty searchCate || searchCate == '1'}">${fns:fy('商品名称')}</c:when>
												<c:when test="${searchCate == '2'}">${fns:fy('订单编号')}</c:when>
											</c:choose>
										</span>
									</a>
								  	<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="">
										<li class="${searchCate == '1' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="1">${fns:fy('商品名称')}</a></li>
										<li class="${searchCate == '2' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="2">${fns:fy('订单编号')}</a></li>
									</ul>
									</span>
							  	</span>
							  	<input type="text" name="searchValue" placeholder="" class="input-medium" value="${searchValue}" maxLength="64"/>
							</div>
						</div>
						<div class="span1">
							<div class="text-align">
								<button type="submit" class="sui-btn btn-primary">${fns:fy('搜索')}</button>
							</div>
						</div>
					</form>
				</div>
				<!--搜索表单结束  -->
				<!--table开始  -->
				<c:forEach items="${page.list}" var="tradeOrder">
					<div class="my-orderlist-info">
						<span class="pull-left"><input type="checkbox"></span>
						<span class="span3">
							<i class="sui-icon icon-tb-time"></i> 
							<fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</span>
						<span class="span3">${fns:fy('订单编号：')}${tradeOrder.orderId}</span>
						<span class="span1" style="width: 181px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${tradeOrder.store.name}</span>
					</div>
					<table class="sui-table table-bordered-simple orderlist-table">
						<tbody>
							<c:forEach items="${tradeOrder.tradeOrderItemList}" var="orderItem" varStatus="itemIndex">
								<tr>
									<td class="picture" width="80">
										<a href="${ctxf}/detail/${orderItem.PId}.htm" target="_blank">
											<img src="${ctxfs}${orderItem.thumbnailPath}@!80x80" style="width: 80px;height: 80px;"
											onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
										</a>
									</td>
									<td width="270">
										<div class="productName"><a href="${ctxf}/detail/${orderItem.PId}.htm" target="_blank">${orderItem.name}</a></div>
										<div class="unstyled">${orderItem.skuValue}</div>
									</td>
									<td class="center" width="100">${fns:fy('￥')}${orderItem.price}</td>
									<td class="center" width="50">X${orderItem.quantity}</td>
									<td class="center customer_service" width="94" orderId="${tradeOrder.orderId}">
										<c:if test="${tradeOrder.orderStatus eq '30' && (orderItem.isReturnStatus eq '0' || empty orderItem.isReturnStatus)}">
											<a href="javascript:void(0);" class="applyReturn" path="${ctxm}/trade/tradeOrder/selectReturnOrderType.htm?orderItemId=${orderItem.orderItemId}&orderId=${tradeOrder.orderId}">${fns:fy('退款退货')}</a></br>
										</c:if>
										<c:if test="${not empty orderItem.isReturnStatus && orderItem.isReturnStatus ne '0' && tradeOrder.orderStatus eq '30'}">
											<a href="${ctxm}/trade/tradeReturnOrder/save1.htm?type=${orderItem.isReturnStatus}&orderId=${tradeOrder.orderId}&orderItemId=${orderItem.orderItemId}&stat=1">${fns:fy('查看')}${orderItem.isReturnStatus eq '1'?fns:fy('退货退款'):fns:fy('退款')}</a></br>
										</c:if>
										<c:if test="${tradeOrder.orderStatus ne '10' && tradeOrder.orderStatus ne '60'}">
											<a href="${ctxm}/trade/tradeComplaint/save1.htm?orderId=${tradeOrder.orderId}&orderItemId=${orderItem.orderItemId}">${fns:fy('投诉')}</a></br>
										</c:if>
									</td>
									<c:if test="${itemIndex.first}">
										<td class="center col" width="100" rowspan="${fn:length(tradeOrder.tradeOrderItemList)}">
											${fns:fy('总额：')}${fns:fy('￥')}${empty tradeOrder.offsetAmount?tradeOrder.amountPaid:tradeOrder.offsetAmount}<br>
											(${fns:fy('含运费：')} ${fns:fy('￥')}${not empty tradeOrder.freight && tradeOrder.freight ne '0.000'?tradeOrder.freight:'0'})<br>
											<input type="hidden" class="totalFeee" value="${empty tradeOrder.offsetAmount?tradeOrder.amountPaid:tradeOrder.offsetAmount}"/>
										</td>
									</c:if>
									<c:if test="${itemIndex.first}">
										<td class="center col" width="80" rowspan="${fn:length(tradeOrder.tradeOrderItemList)}">
											<c:choose>
												<c:when test="${tradeOrder.orderStatus eq '40' || tradeOrder.orderStatus eq '50'}">
													${fns:fy('交易完成')}<br>
												</c:when>
												<c:otherwise>
													<span class="order_status" orderId="${tradeOrder.orderId}">${fns:getDictLabel(tradeOrder.orderStatus, 'trade_order_status', '')}</span><br>
												</c:otherwise>
											</c:choose>
											<c:if test="${tradeOrder.orderStatus eq '30' && tradeOrder.isNeedLogistics eq '1' && orderItem.isReturnStatus eq '0'}">
												<a href="javascript:;" class="logisticsView" orderId="${tradeOrder.orderId}">${fns:fy('查看物流')}</a></br>
											</c:if>
											<a href="${ctxm}/trade/tradeOrder/tradeOrderDetail.htm?orderId=${tradeOrder.orderId}" target="_blank">${fns:fy('订单详情')}</a></br>
											<c:if test="${not empty orderItem.isReturnStatus && orderItem.isReturnStatus ne '0' && tradeOrder.orderStatus eq '20'}">
												<a href="${ctxm}/trade/tradeReturnOrder/save1.htm?type=${orderItem.isReturnStatus}&orderId=${tradeOrder.orderId}&orderItemId=${orderItem.orderItemId}&stat=1">${fns:fy('查看')}${orderItem.isReturnStatus eq '1'?fns:fy('退货退款'):fns:fy('退款')}</a></br>
											</c:if>
										</td>
									</c:if>
									<c:if test="${itemIndex.first}">
										<td class="center col" width="110" rowspan="${fn:length(tradeOrder.tradeOrderItemList)}">
											<ul class="btn">
												<c:if test="${tradeOrder.orderStatus eq '10' }">
													<li><a href="${ctxf}/trade/order/tradeCheckorder.htm?orderIds=${tradeOrder.orderId}" class="sui-btn btn-bordered btn-primary payOrder" orderId="${tradeOrder.orderId}" ><i class="sui-icon icon-tb-refund"></i> ${fns:fy('付款')}</a></li>
													<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-danger delOrder" type="1" orderId="${tradeOrder.orderId}" style="margin-top: 10px;"><i class="sui-icon icon-ban-circle"></i> ${fns:fy('取消订单')}</a></li>
												</c:if>
												<c:if test="${(tradeOrder.needPay eq '1' || empty tradeOrder.needPay) && (tradeOrder.orderStatus eq '20') && (tradeOrder.isReturnStatus eq '0' || empty tradeOrder.isReturnStatus)}">
													<li><a href="${ctxm}/trade/tradeReturnOrder/save1.htm?orderItemId=${orderItem.orderItemId}&orderId=${tradeOrder.orderId}&type=2&isCreatReturnOrder=1" class="sui-btn btn-bordered btn-primary" orderId="${tradeOrder.orderId}" data-keyboard="false"><i class="sui-icon icon-tb-info" target="_blank"></i> ${fns:fy('订单退款')}</a></li>
													<%-- <li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-danger delOrder" orderId="${tradeOrder.orderId}" data-keyboard="false" style="margin-top: 10px;"><i class="sui-icon icon-ban-circle"></i> ${fns:fy('取消订单')}</a></li> --%>
												</c:if>
												<c:if test="${tradeOrder.orderStatus eq '30' && (tradeOrder.isReturnStatus eq '0' || empty tradeOrder.isReturnStatus)}">
													<%-- <li><a href="${ctxm}/trade/tradeReturnOrder/save1.htm?orderItemId=${orderItem.orderItemId}&orderId=${tradeOrder.orderId}&type=2&isCreatReturnOrder=1" class="sui-btn btn-bordered btn-primary returnOrderBtn" orderId="${tradeOrder.orderId}" data-keyboard="false"><i class="sui-icon icon-tb-info" target="_blank"></i> ${fns:fy('订单退款')}</a></li> --%>
													<li style="margin-top: 10px;" ><a href="javascript:void(0);" class="sui-btn btn-bordered btn-primary receiptGoods" orderId="${tradeOrder.orderId}" data-keyboard="false" type="1"><i class="sui-icon icon-tb-check"></i> ${fns:fy('确认收货')}</a></li>
													<c:if test="${empty tradeOrder.delayDays || tradeOrder.delayDays==0}">
														<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-warning delayReceipt" orderId="${tradeOrder.orderId}" data-keyboard="false" placeOrderTime="${tradeOrder.placeOrderTime}" style="margin-top: 10px;" type="1"><i class="sui-icon icon-tb-remind"></i> ${fns:fy('延迟收货')}</a></li>
													</c:if>
												</c:if>
												<c:if test="${tradeOrder.orderStatus eq '40'}">
													<li><a href="${ctxm}/trade/tradeComment/save1.htm?orderId=${tradeOrder.orderId}&isAdditionalComment=0" class="sui-btn btn-bordered commentbtn" orderId="${tradeOrder.orderId}" data-keyboard="false"><i class="sui-icon icon-tb-appreciate"></i> ${fns:fy('我要评价')}</a></li>
												</c:if>
												<c:if test="${tradeOrder.orderStatus eq '50' && tradeOrder.isAdditionalComment ne '1'}">
													<li><a href="${ctxm}/trade/tradeComment/save1.htm?orderId=${tradeOrder.orderId}&isAdditionalComment=1" class="sui-btn btn-bordered commentbtn" orderId="${tradeOrder.orderId}" data-keyboard="false"><i class="sui-icon icon-tb-appreciate"></i> ${fns:fy('追加评价')}</a></li>
												</c:if>
												<c:if test="${not empty tradeOrder.isReturnStatus && tradeOrder.isReturnStatus eq '1' && tradeOrder.orderStatus ne '40' && tradeOrder.orderStatus ne '50'}">
													${fns:fy('退货退款中')}
												</c:if>
												<c:if test="${not empty tradeOrder.isReturnStatus && tradeOrder.isReturnStatus eq '2' && tradeOrder.orderStatus ne '40' && tradeOrder.orderStatus ne '50'}">
													${fns:fy('退款中')}
												</c:if>
											</ul>
								  		</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<!-- table结束 -->
				<!-- 没有数据提示开始 -->
				<c:if test="${fn:length(page.list)=='0'}">
					<div class="no_product" style="text-align: center;color: #9a9a9a;margin-top: 300px;">
						<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无数据！')}
					</div>
				</c:if>
				<!-- 没有数据提示结束 -->
			</dd>
			<c:if test="${fn:length(page.list)>'0'}">
				<%@ include file="/views/member/include/page.jsp"%>
			</c:if>
		</dl>
	</div>
	<!-- main-center结束 -->
	<!-- 取消订单弹出框开始 -->
	<div id="delModal" tabindex="-1" role="dialog" data-hasfoot="false" class="hide" style="border-radius: 5px;">
		<div class="">
			<div class="modal-body" style="padding: 0;">
				<form id="cancelOrdersForm" name="cancelOrdersForm" action="" method="post">
					<input type="hidden" name="ordersId" value="142">
					<table class="sui-table table-bordered" id="delTable">
						<tbody>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('订单编号：')}</td>
								<td><div class="text-box"><span class="c-success" id="orderIdModal" name="ordersSn">1450000000013700</span></div></td>
							</tr>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('取消缘由：')}</td>
								<td>
									<ul class="checked">
										<c:forEach items="${fns:getDictList('trade_cancel_order_reason2')}" var="tt" varStatus="index">
											<li style="margin-bottom: 5px;">
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
	<!-- 确认收货弹出框开始 -->
	<div id="receiptModal" tabindex="-1" role="dialog" data-hasfoot="false" class="hide" style="border-radius: 5px;">
		<div class="modal-body" style="padding: 0;">
			<h4 style="text-align: center;margin-top: 20px;">
				<i class="sui-icon icon-pc-light"></i>
				${fns:fy('您是否确认')}<span class="sui-text-info">(${fns:fy('订单编号')}<span id="receiptOrderId">2610000000024701</span></span>)${fns:fy('收货完成？')}
			</h4>
			<div class="sui-text-warning receiptTip">
				${fns:fy('请注意： 如果你尚未收到商品请不要点击“确认”。大部分被骗案件都是由于提前确认付款被骗的，请谨慎操作！')}
			</div>
		</div>
		<div class="modal-footer" id="receiptModal-footer">
			<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large confirmrReceiptBtn" style="margin-right: 10px;" orderId="">${fns:fy('确定')}</button>
			<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large"  onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
		</div>
	</div>
	<!-- 确认收货弹出框结束 -->
	<!-- 延迟收货弹出框开始 -->
	<div id="delayReceiptModal" tabindex="-1" role="dialog" data-hasfoot="false" class="hide" style="border-radius: 5px;">
		<div class="">
			<div class="modal-body" style="padding: 0;">
				<form id="" name="" action="" method="post">
					<input type="hidden" name="ordersId" value="142">
					<table class="sui-table table-bordered" id="delTable">
						<tbody>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('订单编号：')}</td>
								<td><div class="text-box"><span class="c-success" id="delayReceiptId" name="ordersSn">1450000000013700</span></div></td>
							</tr>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('最晚收货时间：')}</td>
								<td>
									<div id="delayReceiptDate">2017-01-31 15:58:05</div>
									<div class="delayTip">${fns:fy('如果超过该时间买家未点击收货，系统将自动更改为收货状态')}</div>
								</td>
							</tr>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('延迟：')}</td>
								<td>
									<select class="delayDays">
										<option value="5">5${fns:fy('天')}</option>
										<option value="10">10${fns:fy('天')}</option>
										<option value="15">15${fns:fy('天')}</option>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="span2"></div>
					<div class="modal-footer" id="delayReceiptModal-footer">
						<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large delayReceiptBtn" style="margin-right: 10px;" orderId="">${fns:fy('确定')}</button>
						<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large"  onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 延迟收货弹出框结束 -->
</body>
</html>
