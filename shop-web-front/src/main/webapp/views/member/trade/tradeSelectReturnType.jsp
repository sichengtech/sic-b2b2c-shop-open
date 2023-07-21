<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('退款及退货处理')}</title>
<meta name="decorator" content="member"/>
<style type="text/css">
	 .sui-table th, .sui-table td{border-bottom: 1px solid #e6e6e6;border-top:none!important;}
	 .sui-table th, .sui-table td{border-bottom: 1px dotted #e6e6e6;}
	 .sui-table.table-bordered{border:none!important;}
	 .typographic ul{text-align: left!important;}
	 .sui-table{ margin-bottom: 0px!important;}
	 .selectType{border: solid 5px #e4e0e0;display: block;width: 141px;float: left;padding:42px 62px;
	 	margin: 15px;}
	 .selectType h4{font-size: 18px;line-height: 30px;text-align: center;color: #DD2726;font-weight: normal;}
     .selectType p{font-size: 12px;line-height: 20px;text-align: center;color: #999;}
     .selectType:hover{border: solid 5px #DD2726;}
     .title{border-bottom: 1px solid #e6e6e6;width: 90%;margin: 20px 10px 10px 10px;}
	 .selectType{height: 100px;}
</style>
</head>
<body>
	<div class="main-center">
		<div class="sui-row-fluid">
			<div class="goods-list">
			<dl class="">
				<div class="sui-row-fluid">
					<div class="span8" style="border-right: solid 1px #f5f5f5;">
						<div class="title">
							<h4>${fns:fy('选择退款或退货')}</h4>
						</div>
						<a class="selectType" href="${ctxm}/trade/tradeReturnOrder/save1.htm?orderItemId=${tradeOrderItem.orderItemId}&orderId=${tradeOrder.orderId}&type=2&isCreatReturnOrder=1">
							<h4>${fns:fy('我要退款')}</h4>
							<p>${fns:fy('无需退货仅申请退款')}</p>
						</a>
						<a class="selectType" href="${ctxm}/trade/tradeReturnOrder/save1.htm?orderItemId=${tradeOrderItem.orderItemId}&orderId=${tradeOrder.orderId}&type=1&isCreatReturnOrder=1">
							<h4>${fns:fy('我要退货')}</h4>
							<p>${fns:fy('申请退货及退款服务')}</p>
						</a>
					</div>
					<!-- 商品交易信息开始 -->
					<div class="span4">
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
													<img src="${ctxfs}${tradeOrderItem.thumbnailPath}" width="80" height="80"
													onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
													<ul class="unstyled">
														<li><a href="javascript:;" target="_blank">${tradeOrderItem.name}</a></li>
														<li>
															<c:if test="${not empty tradeOrder.offsetAmount}">${fns:fy('￥')}${tradeOrder.offsetAmount} ${fns:fy('数量:')}${tradeOrderItem.quantity}</c:if>
															<c:if test="${empty tradeOrder.offsetAmount}">${fns:fy('￥')}${tradeOrderItem.price} * ${tradeOrderItem.quantity} (${fns:fy('数量')})</c:if>
														</li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="typographic">
													<ul class="unstyled">
														<li>${fns:fy('运费：')} ${tradeOrder.freight}</li>
														<li>
															<c:if test="${not empty tradeOrder.offsetAmount}">${fns:fy('订单总额： ')}${fns:fy('￥')}${tradeOrder.offsetAmount}</c:if>
															<c:if test="${empty tradeOrder.offsetAmount}">${fns:fy('订单总额：')} ${fns:fy('￥')}${tradeOrder.amountPaid}</c:if>
														</li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="typographic">
													<ul class="unstyled">
														<li>${fns:fy('订单编号：')} ${tradeOrder.orderId}</li>
														<li>${fns:fy('物流单号：')}${tradeOrder.trackingNo}</li>
													</ul>
													<li>
														<span style="display:inline-block;" href="javascript:void(0)" data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center" 
															title="${fns:fy('支付方式：')}${tradeOrder.paymentMethodName}</br>
															${fns:fy('下单时间：')}<fmt:formatDate value="${tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></br>
															${fns:fy('支付时间：')}<fmt:formatDate value="${tradeOrder.payOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></br>
															${fns:fy('发货时间：')}<fmt:formatDate value="${tradeOrder.deliverProductDate}" pattern="yyyy-MM-dd HH:mm:ss"/></br>
															${fns:fy('订单完成时间：')}<fmt:formatDate value="${tradeOrder.productReceiptDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
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
														<li>${fns:fy('商家：')}  ${tradeOrder.store.name}</li>
													</ul>
													<li>
														<span style="display:inline-block;" href="javascript:void(0)" data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center" 
															title="${fns:fy('所在地区：')}${tradeOrder.store.provinceName} ${tradeOrder.store.cityName} ${tradeOrder.store.districtName}</br>
															${fns:fy('联系电话：')}${tradeOrder.store.storeTel}">
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
					<!-- 商品交易信息结束 -->
				</div>
			</dl>
			</div>
		</div>
	</div>
</body>
</html>