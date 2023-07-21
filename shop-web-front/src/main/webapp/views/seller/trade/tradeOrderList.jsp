<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品订单')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<meta name="menu" content="3"/><!-- 表示使用N号二级菜单 -->
<style type="text/css">
	.logisticsMess{border: solid 1px #E7E7E7;background: #FAFAFA;margin: 15px;padding: 10px;}
	.trace{min-height: 30px;border-left:1px solid #d9d9d9;}
	.traceStatus{width:5px;height:5px;border: 3px solid #f3f3f3;background: #d9d9d9; margin-left: -6px;border-radius: 5px;margin-right: 7px;}
	.trace p{margin-bottom: 0;margin-left: 14px;color: #a09d9d;}
	.trace span{margin-left: 14px;}
	#searchForm .input-date{width: 145px;}
	.sui-msg.msg-block {margin: 10px !important;}
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
						<span>${fns:fy('商品订单')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('订单')}</li>
						<li>${fns:fy('订单管理')}</li>
						<li>${fns:fy('商品订单')}</li>
					</ul>
				</dt>
				<!-- 提示信息开始 -->
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('此列表显示了商家的所有订单信息。')}</li>
								<li>${fns:fy('可以根据下单时间、买家账号、商品名称、订单编号进行搜索。')}</li>
								<li>${fns:fy('如果要搜索一天的数据，比如2017.09.04日的订单，可以输入2017.09.04-2017.09.04进行搜索。')}</li>
								<li>${fns:fy('在此列表中，商家可以进行打印、发货、取消订单、修改运费、修改价格等操作。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<!-- 提示信息结束 -->
				<!--状态导航开始 -->
				<div class="main-toptab">
					<ul class="sui-nav">
						<li class="${orderStatus == null?'active':'' }"><a href="${ctxs}/trade/tradeOrder/list.htm">${fns:fy('所有订单')}</a></li>
						<li class="${orderStatus == '10'?'active':'' }"><a href="${ctxs}/trade/tradeOrder/list.htm?orderStatus=10">${fns:fy('待付款')}</a></li>
						<li class="${orderStatus == '20'?'active':'' }"><a href="${ctxs}/trade/tradeOrder/list.htm?orderStatus=20">${fns:fy('待发货')}</a></li>
						<li class="${orderStatus == '30'?'active':'' }"><a href="${ctxs}/trade/tradeOrder/list.htm?orderStatus=30">${fns:fy('已发货')}</a></li>
						<li class="${orderStatus == '40'?'active':'' }"><a href="${ctxs}/trade/tradeOrder/list.htm?orderStatus=40">${fns:fy('已完成')}</a></li>
						<li class="${orderStatus == '50'?'active':'' }"><a href="${ctxs}/trade/tradeOrder/list.htm?orderStatus=60">${fns:fy('已取消')}</a></li>
					</ul>
				</div>
				<!--状态导航结束 --> 
				<dd class="table-css">
					<div class="sui-row-fluid">
						<form class="sui-form form-inline" action="${ctxs}/trade/tradeOrder/list.htm" style="margin-top: 10px;" method="post" id="searchForm">
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
							<div class="span3 text-align">
								<div class="control-group">
									<span class="sui-dropdown dropdown-bordered select">
										<span class="dropdown-inner">
											<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
												<input name="searchCate" type="hidden" value="${searchCate}"/><i class="caret"></i>
												<span>
													<c:choose>
														<c:when test="${empty searchCate || searchCate == '1'}">${fns:fy('买家账号')}</c:when>
														<c:when test="${searchCate == '2'}">${fns:fy('商品名称')}</c:when>
														<c:when test="${searchCate == '3'}">${fns:fy('订单编号')}</c:when>
													</c:choose>
												</span>
											</a>
											<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu" name="">
												<li class="${empty searchCate || searchCate == '1' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="1">${fns:fy('买家账号')}</a></li>
												<li class="${searchCate == '2' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="2">${fns:fy('商品名称')}</a></li>
												<li class="${searchCate == '3' ?'active':'' }" role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="3">${fns:fy('订单编号')}</a></li>
											</ul>
										</span>
									</span>
									<input type="text" name="searchValue" placeholder="" class="input-medium" maxLength="64" value="${searchValue}"/>
									<input type="hidden" class="isSelect" value="0"/>
								</div>
							</div>
							<div class="span2">
								<div class="text-align">
									<button type="button" class="sui-btn btn-primary formBtn" id="searchBtn">${fns:fy('搜索')}</button>
									<button type="button" class="sui-btn btn-primary formBtn" id="exportBtn">${fns:fy('导出')}</button>
								</div>
							</div>
						</form>
					</div>
					<!--搜索表单结束 -->
					<!--table开始 -->
					<c:forEach items="${page.list}" var="order" varStatus="index">
						<div class="orderlist-info">
							<span class="pull-left"><input type="checkbox"></span>
							<span class="span3"><i class="sui-icon icon-tb-time"></i>
								<fmt:formatDate value="${order.placeOrderTime}" pattern="yyyy-MM-dd HH:mm"/>
							</span>
							<span class="span3">${fns:fy('订单编号：')}${order.orderId}</span>
							<span class="span3"></span>
							<span class="pull-right" style="margin-right: 10px;line-height: 27px;">
								<a href="${ctxs}/trade/tradeOrder/print.htm?orderId=${order.orderId}" class="sui-btn btn-bordered btn-primary" orderId="${order.orderId}" target="_Blank">${fns:fy('打印')}</a>
							</span>
						</div>
						<table class="sui-table table-bordered-simple orderlist-table">
							<tbody>
								<c:forEach items="${order.tradeOrderItemList}" var="orderItem" varStatus="orderIndex">
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
										<td class="center" width="50">${fns:fy('￥')}${orderItem.price}</td>
										<td class="center" width="50">X${orderItem.quantity}</td>
										<c:choose>
										<c:when test="${orderIndex.first}">
											<td class="center col" width="90">${order.userMain.loginName}<i class="sui-icon icon-tb-my"></i></td>
											<td class="center col" width="100">
												<input type="hidden" class="offsetAmount" orderId="${order.orderId}" value="${order.offsetAmount}">
												${fns:fy('总额：')}${fns:fy('￥')}<span class="orderAmountPaid" orderId="${order.orderId}">${empty order.offsetAmount?order.amountPaid:order.offsetAmount}</span></br>
												(${fns:fy('含运费：')}${fns:fy('￥')}<span class="orderFreight" orderId="${order.orderId}">${order.freight eq '0.000'?'0':order.freight}</span>)</br>
												<span>${order.paymentMethodName}</span>
											</td>
											<td class="center col" width="70">
											<c:choose>
												<c:when test="${order.orderStatus == '40'}">
													${fns:fy('交易成功')}<br>
												</c:when>
												<c:when test="${order.orderStatus == '50'}">
													${fns:fy('交易成功')}<br>
													${fns:fy('买家已评')}<br>
												</c:when>
												<c:otherwise>
													<span class="status" orderId="${order.orderId}">${fns:getDictLabel(order.orderStatus, 'trade_order_status', '')}</span><br>
												</c:otherwise>
											</c:choose>
											<a href="${ctxs}/trade/tradeOrder/tradeOrderDetail.htm?orderId=${order.orderId}">${fns:fy('订单详情')}</a>
											<c:if test="${order.orderStatus == '30'}">
												<p><a href="javascript:;" class="logisticsView" orderId="${order.orderId}">${fns:fy('查看物流')}</a></p>
											</c:if>
											</td>
											<td class="center col" width="100">
												<ul class="btn">
													<shiro:hasPermission name="trade:tradeOrder:edit">
														<c:if test="${order.orderStatus == '10' && (order.isReturnStatus eq '0' || empty order.isReturnStatus)}">
															<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-warning delOrder" type="1" orderId="${order.orderId}" data-keyboard="false">${fns:fy('取消订单')}</a></li>
															<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-info freightModal1 mt5 freightBtn" freight="${order.freight}" buyer="${order.userMain.loginName}" orderId="${order.orderId}">${fns:fy('修改运费')}</a></li>
															<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-info orderMoneyModal mt5 orderMoneyBtn" amountPaid="${empty order.offsetAmount?order.amountPaid:order.offsetAmount}" buyye="${order.userMain.loginName}" orderId="${order.orderId}">${fns:fy('修改价格')}</a></li>
														</c:if>
														<c:if test="${order.orderStatus == '20' && (order.isReturnStatus eq '0' || empty order.isReturnStatus)}">
															<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-success shipmentModal mt5" orderId="${order.orderId}">${fns:fy('订单发货')}</a></li>
														</c:if>
														<c:if test="${order.orderStatus == '30' && (order.isReturnStatus eq '0' || empty order.isReturnStatus)}">
															<li><a href="javascript:void(0);" class="sui-btn btn-bordered btn-success shipmentModal mt5" orderId="${order.orderId}">${fns:fy('编辑发货')}</a></li>
														</c:if>
														<c:if test="${not empty order.isReturnStatus && order.isReturnStatus eq '1' && order.orderStatus ne '40' && order.orderStatus ne '50'}">
															${fns:fy('退货退款中')}
														</c:if>
														<c:if test="${not empty order.isReturnStatus && order.isReturnStatus eq '2' && order.orderStatus ne '40' && order.orderStatus ne '50'}">
															${fns:fy('退款中')}
														</c:if>
													</shiro:hasPermission>
												</ul>
											</td>
										</c:when>
										<c:otherwise>
											<td class="bortopnone col"></td>
										 	<td class="bortopnone col"></td>
											<td class="bortopnone col"></td>
											<td class="bortopnone col"></td>
										</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:forEach>
					<!-- table结束 -->
					<!-- 没有数据提示信息开始 -->
					<c:if test="${fn:length(page.list)==0}">
						<div class="no_product" style="height:400px;text-align: center;color: #9a9a9a;line-height: 400px;">
							<i class="sui-icon icon-notification" style="font-size: 20px;"></i> ${fns:fy('很遗憾，暂无订单！')}
						</div>
					</c:if>
					<!-- 没有数据提示信息结束 -->
				</dd>
				<c:if test="${fn:length(page.list)>'0'}">
					<%@ include file="/views/seller/include/page.jsp"%>
				</c:if>
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
										<c:forEach items="${fns:getDictList('trade_cancel_order_reason1')}" var="tt" varStatus="index">
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
	<!-- 修改运费弹出框开始 -->
	<div id="freightModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
		<div class="modal-content">
			<div class="modal-body">
				<form id="freightForm" name="cancelOrdersForm" action="" method="post">
					<input type="hidden" name="ordersId" value="142">
					<table class="sui-table table-bordered" id="delTable">
						<tbody>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('买家：')}</td>
								<td><div class="text-box"><span id="freightFormBuyer" class="c-success freightBuyer" name="ordersSn">adbc</span></div></td>
							</tr>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('订单编号：')}</td>
								<td><div class="text-box"><span class="c-success freightOrderId" id="freightFormOrderId" name="ordersSn">1450000000013700</span></div></td>
							</tr>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('运费(元)：')}</td>
								<td><input type="text" id="freightFormMoney" placeholder="${fns:fy('请输入运费(元)')}" class="input-fat" style="line-height: 23px;height: 23px;" value=""></td>
							</tr>
						</tbody>
					</table>
					<div class="span2"></div>
					<div class="modal-footer">
						<button type="button" id="updateFreightBtn" data-ok="modal" class="sui-btn btn-primary btn-large" style="margin-right:10px;" orderId="">${fns:fy('确定')}</button>
						<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large" onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 修改运费弹出框结束 -->
	<!-- 修改价格弹出框开始 -->
	<div id="orderMoneyModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
		<div class="modal-content">
			<div class="modal-body">
				<form id="cancelOrdersForm" name="cancelOrdersForm" action="" method="post">
					<input type="hidden" name="ordersId" value="142">
					<table class="sui-table table-bordered" id="delTable">
						<tbody>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('买家：')}</td>
								<td><div class="text-box"><span class="c-success updateOrderBuyer" name="ordersSn">adbc</span></div></td>
							</tr>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('订单编号：')}</td>
								<td><div class="text-box"><span class="c-success updateOrderId" name="ordersSn">1450000000013700</span></div></td>
							</tr>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('价格(元)：')}</td>
								<td><input type="text" id="amountPaidInput" placeholder="${fns:fy('请输入价格(元)')}" class="input-fat updateOrderMoney" style="line-height: 23px;height: 23px;" value="6.6"></td>
							</tr>
						</tbody>
					</table>
				</form>
				<div class="span2"></div>
				<div class="modal-footer">
					<button type="button" data-ok="modal" class="sui-btn btn-primary btn-large updateAmountPaidBtn" style="margin-right: 10px;">${fns:fy('确定')}</button>
					<button type="button" data-dismiss="modal" class="sui-btn btn-default btn-large" onclick="(function(){layer.closeAll('page');}())">${fns:fy('取消')}</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改价格弹出框结束 -->
	<!-- 查看物流弹出框开始 -->
	<div id="logisticsModal" tabindex="-1" role="dialog" data-hasfoot="false" class="sui-modal hide fade model-alert" style="border-radius: 5px;">
		<div class="modal-content">
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
								<td class="" style="text-align: right;">${fns:fy('物流公司：')}</td>
								<td><div class="text-box"><span class="c-success" id="orderIdModal" name="ordersSn">${fns:fy('顺丰快递')}</span></div></td>
							</tr>
							<tr>
								<td class="" style="text-align: right;">${fns:fy('运单号码：')}</td>
								<td><div class="text-box"><span class="c-success" id="orderIdModal" name="ordersSn">1234567</span></div></td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="logisticsMess">${fns:fy('暂无物流信息')}</div>
								</td>
							</tr>
						</tbody>

					</table>
				</form>
			</div>
		</div>
	</div>
	<!-- 查看物流弹出框结束 -->
	<script type="text/javascript" src="${ctx}/views/seller/trade/tradeOrderList.js"></script>
	<script src="${ctx}/views/seller/logistics/selectArea.js" type="text/javascript"></script>
	
</body>
</html>