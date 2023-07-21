<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('退款管理')}</title>
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
						<span>${fns:fy('退款详情')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置')}</li>
						<li>${fns:fy('售后')}</li>
						<li>${fns:fy('售后管理')}</li>
						<li>${fns:fy('退款详情')}</li>
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
						<li><a href="${ctxs}/trade/tradeReturnOrder/tradeReturnMoneyList.htm">${fns:fy('退款管理')}</a></li>
						<li class="active"><a href="javascript:void(0);">${fns:fy('退款详情')}</a></li>
					</ul>
				</div>
				<!--状态导航结束 --> 
				<div class="sui-row-fluid">
					<div class="span7" style="border-right: solid 1px #DDD;">
						<sys:message content="${message}"/>
						<form action="${ctxs}/trade/tradeReturnOrder/handle2.htm" method="post" id="inputForm">
						<!-- 状态进度条开始 -->
						<div class="sui-steps-round steps-round-auto steps-3">
							<div class="finished">
								<div class="wrap">
									<div class="round">1</div>
									<div class="bar"></div>
								</div>
								<label>${fns:fy('提交申请')}</label>
							</div>
							<c:choose>
								<c:when test="${empty tradeReturnOrder.businessHandle || empty tradeReturnOrder.systemHandle}">
									<div class="todo">
										<div class="wrap">
											<div class="round">2</div>
											<div class="bar"></div>
										</div>
										<label>${fns:fy('商家处理')}</label>
									</div>
									<div class="todo">
										<div class="wrap">
											<div class="round">3</div>
										</div>
										<label>${fns:fy('平台处理')}</label>
									</div>
								</c:when>
								<c:otherwise>
									<div class="${tradeReturnOrder.businessHandle!='0' || tradeReturnOrder.systemHandle!='0'?'finished':'todo'}">
										<div class="wrap">
											<div class="round">2</div>
											<div class="bar"></div>
										</div>
										<label>${fns:fy('商家处理')}</label>
									</div>
									<div class="${tradeReturnOrder.systemHandle!='0'?'finished':'todo'}">
										<div class="wrap">
											<div class="round">3</div>
										</div>
										<label>${fns:fy('平台处理')}</label>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<!-- 状态进度条结束 -->
						<table class="sui-table table-bordered">
							<tbody>
								<tr class="sep-row" colspan="2" style="height:15px;"></tr>
								<tr>
									<th colspan="2"> 
										<label class="pull-left">${fns:fy('买家退款申请')}</label>
									</th>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('退款编号：')}</td>
									<td width="80%">${tradeReturnOrder.returnOrderId}</td>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('申请人（买家）：')}</td>
									<td width="80%">${tradeReturnOrder.userMain.loginName}</td>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('退款原因：')}</td>
									<td width="80%">
										${fns:getDictLabel(tradeReturnOrder.returnReason, 'trade_return_reason', '')}
									</td>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('退款金额：')}</td>
									<td width="80%">${fns:fy('¥')} ${tradeReturnOrder.returnMoney}</td>
								</tr>
								<tr>
									<td width="20%" class="right">${fns:fy('退款说明：')}</td>
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
						<c:if test="${tradeReturnOrder.businessHandle != '0'}">
							<table class="sui-table table-bordered">
								<tbody>
									<tr>
										<th colspan="2"> 
											<label class="pull-left">${fns:fy('商家处理意见')}</label>
										</th>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('处理状态：')}</td>
										<td width="80%">${fns:getDictLabel(tradeReturnOrder.businessHandle, 'trade_business_handle_status', '')}</td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('商家备注：')}</td>
										<td width="80%" style="word-break: break-all;">${tradeReturnOrder.businessHandleRemarks}</td>
									</tr>
								</tbody>
							</table>
						</c:if>
						<c:if test="${tradeReturnOrder.systemHandle != '0'}">
							<table class="sui-table table-bordered">
								<tbody>
									<tr>
										<th colspan="2"> 
											<label class="pull-left">${fns:fy('商城平台退款审核')}</label>
										</th>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('平台处理：')}</td>
										<td width="80%">${fns:getDictLabel(tradeReturnOrder.systemHandle, 'trade_system_handle_status', '')}</td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('平台备注：')}</td>
										<td width="80%" style="word-break: break-all;">${tradeReturnOrder.systemHandleRemarks}</td>
									</tr>
								</tbody>
							</table>
						</c:if>
						<c:if test="${tradeReturnOrder.businessHandle == '0' && tradeReturnOrder.systemHandle == '0'}">
							<table class="sui-table table-bordered">
								<input type="hidden" name="returnOrderId" value="${tradeReturnOrder.returnOrderId}"/>
								<input type="hidden" name="type" value="${tradeReturnOrder.type}"/>
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
											<input type="radio" name="businessHandle" value="2">&nbsp;${fns:fy('拒绝')}
										</td>
									</tr>
									<tr>
										<td width="20%" class="right"><font color="red">*</font>${fns:fy('备注信息：')}</td>
										<td width="80%">
											<textarea rows="5" cols="50" name="businessHandleRemarks" maxLength="1024">${tradeReturnOrder.businessHandleRemarks}</textarea>
											<p style="color: #ccc;">
												${fns:fy('只能提交一次，请认真选择。')}</br>
												${fns:fy('同意后会将金额以原路返还给买家。')}</br>
											</p>
										</td>
									</tr>
								</tbody>
							</table>
							<shiro:hasPermission name="trade:tradeReturnOrder:edit">
								<button type="submit" class="sui-btn btn-xlarge btn-primary" style="margin:10px 0px 10px 100px;">${fns:fy('确定')}</button>
								<a class="sui-btn btn-xlarge" style="margin:10px 0px 10px 100px;" href="${ctxs}/trade/tradeReturnOrder/tradeReturnMoneyList.htm">${fns:fy('返回列表')}</a>
							</shiro:hasPermission>
						</c:if>
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
															<c:if test="${not empty tradeOrder.offsetAmount}">${fns:fy('￥')}${tradeOrder.offsetAmount} ${fns:fy('数量:')}${tradeReturnOrder.tradeOrderItem.quantity}</c:if>
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
															<c:if test="${not empty tradeReturnOrder.tradeOrder.offsetAmount}">${fns:fy('订单总额：')}${fns:fy('￥')}${tradeReturnOrder.tradeOrder.offsetAmount}</c:if>
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
														<li>${fns:fy('物流单号：')}${tradeReturnOrder.tradeOrder.trackingNo}</li>
													</ul>
														<span style="display:inline-block;" href="javascript:void(0)" data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center" 
															title="${fns:fy('支付方式：')}${tradeReturnOrder.settlementPayWay.name}</br>
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