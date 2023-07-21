<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('退款并退货详情')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!--图片空间相关放大控件-->
<script type="text/javascript" src="${ctxStatic}/sicheng-seller/js/jquery.magnific-popup.min.js"></script>
<!--图片空间放大控件-->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-seller/css/magnific-popup.css">
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/trade/tradeReturnOrderDetail.js"></script>
<style type="text/css">
	 .sui-table th, .sui-table td{border-bottom: 1px solid #e6e6e6;border-top:none!important;}
	 .btn li{margin-bottom: 5px;}
	 #delModal,#freightModal{width: 500px;margin-left:-250px;border: none;}
	 #delTable td{line-height: 30px;}
	 .checked li{line-height: 25px;}
	 .steps-round-auto{width: 98%!important;}
	 .right{text-align: right!important;}
	 .sui-table th, .sui-table td{border-bottom: 1px dotted #e6e6e6;}
	 .sui-table.table-bordered{border:none!important;}
	 .typographic ul{text-align: left!important;}
	 .sui-form{margin:0!important;}
	 .sui-table{ margin-bottom: 0px!important;}
	 .hint {color: #BBB;margin-bottom: -16px;}
	 .trace{min-height: 30px;border-left:1px solid #d9d9d9;}
	.traceStatus{width:5px;height:5px;border: 3px solid #f3f3f3;background: #d9d9d9; margin-left: -6px;border-radius: 5px;margin-right: 7px;}
	.trace p{margin-bottom: 0;margin-left: 14px;color: #a09d9d;}
	.trace span{margin-left: 14px;}
	.error{width: 317px;}
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
						<span>${fns:fy('退款并退货详情')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置')}</li>
						<li>${fns:fy('售后')}</li>
						<li>${fns:fy('售后管理')}</li>
						<li>${fns:fy('退款并退货详情')}</li>
					</ul>
				</dt>
				<!-- 提示信息开始 -->
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('展示了退单的详细信息。')}</li>
								<li>${fns:fy('未处理过的退单可以在此页面进行处理。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<!-- 提示信息结束 -->
				<!--状态导航开始 -->
				<div class="main-toptab">
					<ul class="sui-nav">
						<li><a href="${ctxs}/trade/tradeReturnOrder/tradeReturnOrderList.htm">${fns:fy('退款并退货管理')}</a></li>
						<li class="active"><a href="javascript:;">${fns:fy('退款并退货详情')}</a></li>
					</ul>
				</div>
				<!--状态导航结束 --> 
				<div class="sui-row-fluid">
					<div class="span7" style="border-right: solid 1px #DDD;">
						<!-- 状态进度条开始 -->
						<div class="sui-steps-round steps-round-auto steps-5">
							<div class="finished">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class="bar"></div>
								</div>
								<label style="">${tradeReturnOrder.status}${fns:fy('申请退货')}</label>
							</div>
							
							<c:choose>
								<c:when test="${empty tradeReturnOrder.status || tradeReturnOrder.status=='10'}">
									<c:set var="class1" value="todo"></c:set>
								</c:when>
								<c:when test="${tradeReturnOrder.status=='20'}">
									<c:set var="class1" value="current"></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="class1" value="finished"></c:set>
								</c:otherwise>
							</c:choose>
							<div class="${class1}">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class="bar"></div>
								</div>
								<label style="">${fns:fy('商家处理')}</label>
							</div>
							
							<c:choose>
								<c:when test="${empty tradeReturnOrder.status || tradeReturnOrder.status=='10' || tradeReturnOrder.status=='20' || tradeReturnOrder.status == '30'}">
									<c:set var="class2" value="todo"></c:set>
								</c:when>
								<c:when test="${tradeReturnOrder.status == '40'}">
									<c:set var="class2" value="current"></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="class2" value="finished"></c:set>
								</c:otherwise>
							</c:choose>
							<div class="${class2}">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class="bar"></div>
								</div>
								<label style="">${fns:fy('买家退货')}</label>
							</div>
							
							<c:choose>
								<c:when test="${empty tradeReturnOrder.status || tradeReturnOrder.status=='10' || tradeReturnOrder.status=='20' || tradeReturnOrder.status == '30' || tradeReturnOrder.status == '40'}">
									<c:set var="class3" value="todo"></c:set>
								</c:when>
								<c:when test="${tradeReturnOrder.status == '50'}">
									<c:set var="class3" value="current"></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="class3" value="finished"></c:set>
								</c:otherwise>
							</c:choose>
							<div class="${class3}">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class="bar"></div>
								</div>
								<label style="">${fns:fy('商家收货')}</label>
							</div>
							
							<c:choose>
								<c:when test="${empty tradeReturnOrder.status || tradeReturnOrder.status=='10' || tradeReturnOrder.status=='20' || tradeReturnOrder.status == '30' || tradeReturnOrder.status == '40' || tradeReturnOrder.status == '50'}">
									<c:set var="class4" value="todo"></c:set>
								</c:when>
								<c:when test="${tradeReturnOrder.status == '60' || tradeReturnOrder.status == '70'}">
									<c:set var="class4" value="current"></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="class4" value="finished"></c:set>
								</c:otherwise>
							</c:choose>
							<div class="${class4}">
								<div class="wrap">
									<div class="round"><i class="sui-icon icon-touch-right"></i></div>
									<div class=""></div>
								</div>
								<label style="">${fns:fy('平台审核')}</label>
							</div>
						</div>
						<!-- 状态进度条结束 -->
						<sys:message content="${message}"/>
						<form action="${ctxs}/trade/tradeReturnOrder/handle2.htm" method="post" id="inputForm">
						<table class="sui-table table-bordered">
							<tbody>
								<tr class="sep-row" colspan="2" style="height:15px;"></tr>
								<tr>
									<th colspan="2"> 
										<label class="pull-left">${fns:fy('买家退货/退款申请')}</label>
									</th>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('退货/退款编号：')}</td>
									<td width="80%">${tradeReturnOrder.returnOrderId}</td>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('退货/退款原因：')}</td>
									<td width="80%">
										${fns:getDictLabel(tradeReturnOrder.returnReason, 'trade_return_reason', '')}
									</td>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('退款金额：')}</td>
									<td width="80%">¥ ${tradeReturnOrder.returnMoney}</td>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('退货数量：')}</td>
									<td width="80%">${tradeReturnOrder.returnCount}</td>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('退货退款说明：')}</td>
									<td width="80%" style="word-break: break-all;">${tradeReturnOrder.returnExplain}</td>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('凭证上传：')}</td>
									<td width="80%" class="gallery">
										<c:forEach items="${tradeReturnOrder.tradeReturnOrderVoucherList}" var="voucher">
											<a href="${ctxfs}${voucher.path}">
												<img alt="" src="${ctxfs}${voucher.path}@80x80" style="width: 80px;height: 80px;"
												onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
											</a>
										</c:forEach>
									</td>
								</tr>
							</tbody>
						</table>
						<%-- 商家处理后显示处理信息 --%>
						<c:if test="${tradeReturnOrder.businessHandle != '0'}">
							<table class="sui-table table-bordered">
								<tbody>
									<tr>
										<th colspan="2"> 
											<label class="pull-left">${fns:fy('商家退货退款处理')}</label>
										</th>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('审核状态：')}</td>
										<td width="80%">${fns:getDictLabel(tradeReturnOrder.businessHandle, 'trade_business_handle_status', '')}</td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('是否弃货：')}</td>
										<td width="80%">${fns:getDictLabel(tradeReturnOrder.isJettison, 'yes_no', '')}</td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('商家备注：')}</td>
										<td width="80%" style="word-break: break-all;">${tradeReturnOrder.businessHandleRemarks}</td>
									</tr>
								</tbody>
							</table>
						</c:if>
						<%-- 买家发货信息 --%>
						<c:if test="${not empty tradeReturnOrder.deliverProductTime}">
							<table class="sui-table table-bordered">
								<tbody>
									<tr>
										<th colspan="2"> 
											<label class="pull-left">${fns:fy('买家退货发货信息')}</label>
										</th>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('物流信息：')}</td>
										<td width="80%">
											<c:if test="${empty resultMap['Traces']}">
												<div class="logisticsMess">${fns:fy('暂无物流信息')}</div>
											</c:if>
											<c:if test="${not empty resultMap['Traces']}">
												<div class="logisticsMess">
													<c:forEach items="${resultMap['Traces']}" var="trace" varStatus="traceIndex">
														<div class="trace">
															<div class="traceStatus" style="${traceIndex.first?'background: #56d495':''}"></div>
															<span style="${traceIndex.first?'color: #56d495':''}">${trace['AcceptStation']}</span>
															<p>${trace['AcceptTime']}</p>
														</div>
													</c:forEach>
												</div>
											</c:if>
										</td>
									</tr>
								</tbody>
							</table>
						</c:if>
						<c:if test="${tradeReturnOrder.systemHandle != '0'}">
							<table class="sui-table table-bordered">
								<tbody>
									<tr>
										<th colspan="2"> 
											<label class="pull-left">${fns:fy('商城退款审核')}</label>
										</th>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('平台确认：')}</td>
										<td width="80%">${fns:getDictLabel(tradeReturnOrder.systemHandle, 'trade_system_handle_status', '')}</td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('平台备注：')}</td>
										<td width="80%" style="word-break: break-all;">${tradeReturnOrder.systemHandleRemarks}</td>
									</tr>
								</tbody>
							</table>
						</c:if>
						<c:if test="${tradeReturnOrder.systemHandle == '1'}">
							<table class="sui-table table-bordered">
								<tbody>
									<tr>
										<th colspan="2"> 
											<label class="pull-left">${fns:fy('退款详细')}</label>
										</th>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('支付方式：')}</td>
										<td width="80%">${tradeReturnOrder.settlementPayWay.name}</td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('在线退款金额：')}</td>
										<td width="80%">${tradeReturnOrder.onlineReturnMoney}</td>
									</tr>
								</tbody>
							</table>
						</c:if>
						<c:if test="${tradeReturnOrder.businessHandle == '0' && tradeReturnOrder.systemHandle == '0'}">
							<input type="hidden" name="returnOrderId" value="${tradeReturnOrder.returnOrderId}"/>
							<input type="hidden" name="type" value="${tradeReturnOrder.type}"/>
							<table class="sui-table table-bordered">
								<tbody>
									<tr>
										<th colspan="2"> 
											<label class="pull-left">${fns:fy('商家处理意见')}</label>
										</th>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('是否同意：')}</td>
										<td width="80%">
											<input type="radio" checked="checked" name="businessHandle" value="1">&nbsp;${fns:fy('同意')}
											<input type="checkbox" name="isJettison" value="1">&nbsp;${fns:fy('弃货')}<br/>
											<p class="hint">${fns:fy('如果选择弃货，买家将不用退回原商品，同意后将直接退款。')}</p><br/>
											<input type="radio" name="businessHandle" value="2">&nbsp;${fns:fy('拒绝')}
										</td>
									</tr>
									<tr>
										<td width="20%" class="right"><font color="red">*</font>${fns:fy('备注信息：')}</td>
										<td width="80%">
											<textarea rows="5" cols="50" name="businessHandleRemarks" maxLength="1024"></textarea>
											<p style="color: #ccc;">
												${fns:fy('只能提交一次，请认真选择。')}</br>
												${fns:fy('同意后等收到货后会将金额原路返还给买家。')}</br>
											</p>
										</td>
									</tr>
								</tbody>
							</table>
							<shiro:hasPermission name="trade:tradeReturnOrder:edit">
								<button type="submit" class="sui-btn btn-xlarge btn-primary" style="margin:10px 0px 10px 100px;">${fns:fy('确定')}</button>
							</shiro:hasPermission>
						</c:if>
						<a class="sui-btn btn-xlarge" style="margin:10px 0px 10px 100px;" href="${ctxs}/trade/tradeReturnOrder/tradeReturnOrderList.htm">${fns:fy('返回列表')}</a>
					</form>
					</div>
					<div class="span5">
						<div class="modal-content">
							<div class="modal-header" style="border-bottom: none;padding: 11px 0;">
								<h4 id="myModalLabel" class="modal-title">${fns:fy('相关商品交易信息')}</h4>
							</div>
							<div class="modal-body" style="padding: 0;">
								<table class="sui-table">
									<tbody>
										<tr>
											<td style="border-top:1px solid #e6e6e6;">
												<div class="typographic">
													<img src="${ctxfs}${tradeReturnOrder.tradeOrderItem.thumbnailPath}" 
													onerror="fdp.defaultImage('${ctxStatic}/images/default_store.png');" style="width: 80px;height: 80px;">
													<ul class="unstyled">
														<li><a href="javascript:;" target="_blank">${tradeReturnOrder.tradeOrderItem.name}</a></li>
														<li>
															<c:if test="${not empty tradeOrder.offsetAmount}">${fns:fy('￥')}${tradeOrder.offsetAmount} ${fns:fy('数量')}:${tradeReturnOrder.tradeOrderItem.quantity}</c:if>
															<c:if test="${empty tradeOrder.offsetAmount}">${fns:fy('￥')}${tradeReturnOrder.tradeOrderItem.price} * ${tradeReturnOrder.tradeOrderItem.quantity} (${fns:fy('数量')})</c:if>	
														</li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="typographic">
													<ul class="unstyled">
														<li>${fns:fy('运费：')}${tradeReturnOrder.tradeOrder.freight}</li>
														<li>
															<c:if test="${not empty tradeReturnOrder.tradeOrder.offsetAmount}">${fns:fy('订单总额：')} ${fns:fy('￥')}${tradeReturnOrder.tradeOrder.offsetAmount}</c:if>
															<c:if test="${empty tradeReturnOrder.tradeOrder.offsetAmount}">${fns:fy('订单总额：')}${fns:fy('￥')}${tradeReturnOrder.tradeOrder.amountPaid}</c:if>
														</li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="typographic">
													<ul class="unstyled">
														<li>${fns:fy('订单编号：')}${tradeReturnOrder.tradeOrder.orderId}</li>
														<li>${fns:fy('退单编号：')}${tradeReturnOrder.returnOrderId}</li>
													</ul>
														<span style="display:inline-block;" href="javascript:void(0)" data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center" 
															title="${fns:fy('支付方式：')}${tradeReturnOrder.tradeOrder.paymentMethodName}</br>
															${fns:fy('下单时间：')}<fmt:formatDate value="${tradeReturnOrder.tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></br>
															${fns:fy('支付时间:')}<fmt:formatDate value="${tradeReturnOrder.tradeOrder.payOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></br>
															${fns:fy('发货时间：')}<fmt:formatDate value="${tradeReturnOrder.tradeOrder.deliverProductDate}" pattern="yyyy-MM-dd HH:mm:ss"/></br>
															${fns:fy('订单完成时间:')}<fmt:formatDate value="${tradeReturnOrder.tradeOrder.productReceiptDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
															${fns:fy('更多')}<i class="sui-icon icon-pc-chevron-bottom"></i>
														</span>
													</li>
												</div>
											</td>
										</tr>
										<tr>
											<td style="border-bottom:none;">
												<div class="typographic">
													<ul class="unstyled">
														<li>${fns:fy('收货人：')}${tradeReturnOrder.tradeOrder.consignee}</li>
													</ul>
													<li>
														<span style="display:inline-block;" href="javascript:void(0)" data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center" 
														title="${fns:fy('收货地址：')}${tradeReturnOrder.tradeOrder.provinceName} ${tradeReturnOrder.tradeOrder.cityName} ${tradeReturnOrder.tradeOrder.districtName} 
														${tradeReturnOrder.tradeOrder.detailedAddress} </br>${fns:fy('联系电话：')}${tradeReturnOrder.tradeOrder.phone}">
															${fns:fy('更多')}<i class="sui-icon icon-pc-chevron-bottom"></i>
														</span>
													</li>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</dl>
			</div>
		</div>
	</div>
</body>
</html>