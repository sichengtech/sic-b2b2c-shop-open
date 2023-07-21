<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品订单详情')}</title>
<meta name="decorator" content="member"/>
<style type="text/css">
	#orderMessTable th, #orderMessTable td{border-bottom:none;border-top:none;word-break:break-all;}
	.btn li{margin-bottom: 5px;}
	#delModal,#freightModal{width: 500px;margin-left:-250px;border: none;}
	#sui-msg{font-size: 12px;line-height: 27px;font-weight: normal;}
	tfoot{background: #e6e6e6;}
	tfoot td dl{text-align: right;margin-bottom: 5px;}
	tfoot td dl span{font-size: 14px;font-weight: bolder;}
	tfoot td dl dd{padding-right: 40px;}
	.orderStatus{height: 37px;border-bottom: 1px dotted #e5e5e5;font-size: 16px;margin-bottom:20px;padding-left: 30px;}
	.sui-table.table-bordered-simple{margin-top: -1px;margin-bottom: -1px!important;}
	body{line-height: 12px!important;}
	#box1{padding-bottom: 0px;}
	#productTable{border-right: none;}
	#productTable td{border-left: none;}
	#productTable th{border-left: none;}
	.main-content{padding-bottom: 0;}
	.main-content .main-center dl{padding-bottom: 0;}
	#sui_steps{width:90%; margin: 60px auto;}
	#productTable .borderTd{border-top: none;border-left: 1px solid #e6e6e6;}
	.receiptTip{background-color: #FAFAFA;padding: 9px 19px;border-bottom: 1px solid #e6e6e6;}
	.delayTip{color:#a5a2a2;margin-top: 5px;}
	.delayDay{width: 93px;height: 25px;}
	.logisticsMess{border: solid 1px #E7E7E7;background: #FAFAFA;margin: 15px;padding: 10px;}
	.sui-table.orderlist-table td{vertical-align:middle}
	#receiptModal-footer{margin-top: 10px;text-align: right;margin-right: 20px;}
	.addressTbTd{width: 61px;text-align: right;}
	.typographic ul{max-width: 230px;text-align: left;}
</style>
<script type="text/javascript">
	var daysOfReceipt='${daysOfReceipt}';
</script>
</head>
<body>
	<div class="main-center">
		<div class="sui-row-fluid">
			<div class="goods-list">
				<dl class="" id="box1" style="">
					<dt>
						<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('交易中心')} > ${fns:fy('商品订单')} > ${fns:fy('订单详情')}</div>
						<i class="sui-icon icon-tb-list"></i> ${fns:fy('商品订单详情')}
					</dt>
					<!-- 订单详情 开始-->
					<div class="grid-demo" id="orderMess" style="margin-bottom: 20px;">
						<div class="sui-row">
							<div class="span5" style="margin-left: 9px;position: relative;">
								<table class="sui-table table-bordered-simple" id="orderMessTable">
									<thead>
										<tr>
										 <th colspan="3">${fns:fy('订单信息')}</th>
										</tr>
									</thead>
									<tbody style="background: #FBFBFB;">
										<tr>
											<td class="addressTbTd">${fns:fy('收货人')}：</td>
											<td colspan="2">
												${tradeOrder.consignee},${tradeOrder.phone},${tradeOrder.provinceName}&nbsp;
												${tradeOrder.cityName}&nbsp;${tradeOrder.districtName}&nbsp;${tradeOrder.detailedAddress}
											</td>
										</tr>
										<tr>
											<td>${fns:fy('支付方式：')}</td>
											<td colspan="2">${tradeOrder.paymentMethodName}　(${fns:fy('付款单号：')}${tradeOrder.thirdPayOrderNumber})</td>
										</tr>
										<tr>
											<td style="text-align: right;" class="addressTbTd">${fns:fy('发票：')}</td>
											<td colspan="2">
												<c:if test="${not empty tradeOrder.tradeDeliver.deliverTitle}">
													${fns:fy('抬头')} (${tradeOrder.tradeDeliver.deliverTitle})${fns:fy('内容')}(${tradeOrder.tradeDeliver.deliverContent}) ${fns:fy('类型')} (${fns:getDictLabel(tradeOrder.tradeDeliver.deliverType, 'deliver_type', '')})
												</c:if>
											</td>
										</tr>
										<tr>
											<td class="addressTbTd">${fns:fy('商家留言：')} </td>
											<td colspan="2">${tradeOrder.sellerMemo}</td>
										</tr>
										<tr>
											<td class="addressTbTd">${fns:fy('买家留言：')}</td>
											<td colspan="2">${tradeOrder.memo}</td>
										</tr>
									</tbody>
									<tfoot style="background:#F4F4F4;">
										<tr>
											<td class="addressTbTd">${fns:fy('订单编号：')}</td>
											<td>${tradeOrder.orderId}</td>
											<td class="more" style="cursor: pointer;">
												<a href="javascript:void(0)" data-placement="bottom" data-toggle="tooltip" style="width:120px;"  data-align="right"
													title="<span>${fns:fy('下单时间：')}<fmt:formatDate value='${tradeOrder.placeOrderTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span>">
													${fns:fy('更多')}<i class="sui-icon icon-tb-unfold"></i>
												</a>
											</td>
										</tr>
										<tr>
											<td class="addressTbTd">${fns:fy('商　　家：')}</td>
											<td>${tradeOrder.store.name}</td>
											<td class="more" style="cursor: pointer;">
											<a href="javascript:void(0)" data-placement="bottom" data-toggle="tooltip" data-align="right"
												title="<div>${fns:fy('所在地址：')}${tradeOrder.store.countryName} ${tradeOrder.store.provinceName} 
												${tradeOrder.store.cityName} ${tradeOrder.store.districtName}</div>
												<div>${fns:fy('联系电话：')}${tradeOrder.store.storeTel}</div>">
												${fns:fy('更多')}<i class="sui-icon icon-tb-unfold"></i>
											</a>
											</td>
										</tr>
									</tfoot>
								</table>
							</div>
							<div class="span5" style="margin: 30px;">
								<div style="">
									<div class="orderStatus">${fns:fy('订单状态：')}<span>${fns:getDictLabel(tradeOrder.orderStatus, 'trade_order_status', '')}</span> </div>
									<div style="padding-left: 30px" class="statusTip">
										<c:choose>
											<c:when test="${tradeOrder.orderStatus == '10'}">
												<p> 1.${fns:fy('您已经下单成功。')}</p>
												<p> 2.${fns:fy('订单已提交商家进行备货发货准备。')}</p>
												<p> 3.${fns:fy('如果您不想购买此订单的商品，请选择取消订单操作。')}</p>
											</c:when>
											<c:when test="${tradeOrder.orderStatus == '20'}">
												<p> 1.${fns:fy('您已使用')}(${empty tradeOrder.paymentMethodName?'慧付宝':tradeOrder.paymentMethodName})${fns:fy('成功对订单进行支付，支付单号')} (${tradeOrder.orderId})。</p>
												<p> 2.${fns:fy('请耐心等待卖家发货。')}</p>
											</c:when>
											<c:when test="${tradeOrder.orderStatus == '30'}">
												<p>	1.${fns:fy('商品已发出；')} </p>
												<p style="word-break: break-all;">	2. ${fns:fy('发货备注：')}${tradeOrder.sellerMemo}</p>
												<p>	3.${fns:fy('如果买家没有及时进行收货，系统将于')}
													<span class="lateTime"> 
														<fmt:formatDate value="${lateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</span>
													${fns:fy('自动完成“确认收货”，完成交易。')}
												</p>
											</c:when>
											<c:when test="${tradeOrder.orderStatus == '40'}">
												<p> 1.${fns:fy('如果收到货后出现问题，您可以联系商家协商解决。')}</p>
												<p> 2.${fns:fy('如果商家没有履行应尽的承诺，您可以申请 "投诉维权"。')}</p>
												<p> 3.${fns:fy('交易已完成，你可以对购买的商品及商家的服务进行评价。')}</p>
											</c:when>
											<c:when test="${tradeOrder.orderStatus == '50'}">
												<p> 1.${fns:fy('如果收到货后出现问题，您可以联系商家协商解决。')}</p>
												<p> 2.${fns:fy('如果商家没有履行应尽的承诺，您可以申请 "投诉维权"。')}</p>
											</c:when>
											<c:when test="${tradeOrder.orderStatus == '60'}">
												<p>${fns:fy('买家于')} <fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>${fns:fy('取消订单')}</p>
											</c:when>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 订单详情结束 -->
					<!-- 订单进度条开始 -->
					<c:if test="${tradeOrder.orderStatus != '60'}">
						<div class="sui-steps-round steps-round-auto steps-5" id="sui_steps">
							<div class="finished">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-pc-right"></i></div>
									<div class="bar"></div>
								</div>
								<label>${fns:fy('生成订单')}</label>
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
									<div class="bar"></div>
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
									<div class="bar"></div>
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
									<div class="bar"></div>
								</div>
								<label>${fns:fy('确认收货')}</label>
							</div>
							
							<c:choose>
								<c:when test="${tradeOrder.orderStatus == '10' || tradeOrder.orderStatus == '20' || tradeOrder.orderStatus == '30' || tradeOrder.orderStatus == '40'}">
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
					</c:if>
					<!-- 订单进读条结束 -->
					<dd class="table-css">
						<!--table开始 -->
						<table class="sui-table table-bordered-simple" id="productTable">
							<thead>
								<tr>
									<th class="center">${fns:fy('商品')}</th>
									<th class="center">${fns:fy('单价')}(${fns:fy('￥')})</th>
									<th class="center">${fns:fy('数量')}</th>
									<th class="center">${fns:fy('优惠')}</th>
									<th class="center">${fns:fy('状态')}</th>
									<th class="center">${fns:fy('交易操作')}</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${tradeOrder.tradeOrderItemList}" var="orderItem" varStatus="orderIndex">
									<tr>
										<td width="36%">
											<div class="typographic">
												<a href="${ctxf}/detail/${orderItem.PId}.htm" target="_blank">
													<img src="${ctxfs}${orderItem.thumbnailPath}" style="width: 80px;height: 80px"
													onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
												</a>
												<ul class="unstyled">
													<li><a href="${ctxf}/detail/${orderItem.PId}.htm" target="_blank">${orderItem.name}</a></li>
													<li>${orderItem.skuValue}</li>
												</ul>
											</div>
										</td>
										<td width="10%" class="center">${fns:fy('￥')}${orderItem.price}</td>
										<td width="8%" class="center">X${orderItem.quantity}</td>
										<td width="20%" class="center"><span>${orderItem.benefit}</span></td>
										<c:if test="${orderIndex.first}">
											<td width="12%" class="center borderTd"  rowspan="${fn:length(tradeOrder.tradeOrderItemList)}">
												<span class="order_status" orderId="${tradeOrder.orderId}">
													${fns:getDictLabel(tradeOrder.orderStatus, 'trade_order_status', '')}
												</span>
											</td>
										</c:if>
										<c:if test="${orderIndex.first}">
										<td width="14%" class="center borderTd" rowspan="${fn:length(tradeOrder.tradeOrderItemList)}">
											<ul class="btn">
												<c:if test="${tradeOrder.orderStatus eq '10' }">
													<li><a href="${ctxf}/trade/order/tradeCheckorder.htm?orderIds=${tradeOrder.orderId}" class="sui-btn btn-bordered btn-primary" orderId="${tradeOrder.orderId}" ><i class="sui-icon icon-tb-refund"></i> ${fns:fy('付款')}</a></li>
													<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-danger delOrder" type="2" orderId="${tradeOrder.orderId}" style=""><i class="sui-icon icon-ban-circle"></i> ${fns:fy('取消订单')}</a></li>										
												</c:if>
												<c:if test="${tradeOrder.orderStatus eq '20' && tradeOrder.isReturnStatus eq '0'}">
													<li><a href="${ctxm}/trade/tradeReturnOrder/save1.htm?orderItemId=${orderItem.orderItemId}&orderId=${tradeOrder.orderId}&type=2&isCreatReturnOrder=1" class="sui-btn btn-bordered btn-primary" orderId="${tradeOrder.orderId}" ><i class="sui-icon icon-tb-info"></i> ${fns:fy('订单退款')}</a></li>
													<%-- <li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-danger delOrder" orderId="${tradeOrder.orderId}" style="" type="2"><i class="sui-icon icon-ban-circle"></i> ${fns:fy('取消订单')}</a></li>	 --%>									
												</c:if>
												<c:if test="${tradeOrder.orderStatus eq '30' && tradeOrder.isReturnStatus eq '0'}">
													<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-primary receiptGoods" orderId="${tradeOrder.orderId}" data-keyboard="false" type="2"><i class="sui-icon icon-tb-check"></i> ${fns:fy('确认收货')}</a></li>
													<c:if test="${empty tradeOrder.delayDays }">
														<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-warning delayReceipt" orderId="${tradeOrder.orderId}" data-keyboard="false" placeOrderTime="${tradeOrder.placeOrderTime}" type="2"><i class="sui-icon icon-tb-remind"></i> ${fns:fy('延迟收货')}</a></li>
													</c:if>
												</c:if>
												<c:if test="${tradeOrder.orderStatus eq '40' && tradeOrder.isReturnStatus eq '0'}">
													<li><a href="${ctxm}/trade/tradeComment/save1.htm?orderId=${tradeOrder.orderId}&isAdditionalComment=0" class="sui-btn btn-bordered commentbtn" orderId="${tradeOrder.orderId}" data-keyboard="false"><i class="sui-icon icon-tb-appreciate"></i> ${fns:fy('我要评价')}</a></li>
												</c:if>
											</ul>
										</td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="6">
										<dl class="align-right">
											<dd>
												<c:if test="${empty tradeOrder.freight || tradeOrder.freight=='0'}">
													${fns:fy('(免运费)')}
												</c:if>
												<c:if test="${not empty tradeOrder.freight && tradeOrder.freight!='0'}">
													${fns:fy('运费:')}${fns:fy('￥')}${tradeOrder.freight}
												</c:if>
											</dd>
										</dl>
										<dl class="sum">
											<span>${fns:fy('订单应付金额：')}</span>
											<font style="font-size: 22px;color: #C00;">￥${empty tradeOrder.offsetAmount?tradeOrder.amountPaid:tradeOrder.offsetAmount}</font>
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
				${fns:fy('您是否确认')}<span class="sui-text-info">(${fns:fy('订单编号')}<span id="receiptOrderId">2610000000024701</span></span>)${fns:fy('收货完成?')}
			</h4>
			<div class="sui-text-warning receiptTip">
				${fns:fy('请注意： 如果你尚未收到商品请不要点击“确认”。大部分被骗案件都是由于提前确认付款被骗的，请谨慎操作！')}
			</div>
		</div>
		<div class="span3"></div>
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
					<div class="span3"></div>
					<div class="modal-footer" id="delayReceiptModal-footer">
						<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large delayReceiptBtn" style="margin-right: 10px;" orderId="">${fns:fy('确定')}</button>
						<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large"  onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 延迟收货弹出框结束 -->
	<script type="text/javascript" src="${ctx}/views/member/trade/tradeOrderList.js"></script>
</body>
</html>