<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('退款详情')}</title>
<meta name="decorator" content="member"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/trade/tradeReturnOrderDetail.js"></script>
<style type="text/css">
	 .header1 .nav-box .sui-nav>li.active a {border-bottom: #28a3ef solid 1px!important;}
	 .header1 .nav-box .sui-nav>li>a:hover{ background:#f9f9f9; border-bottom:#28a3ef solid 1px!important; height:37px;}
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
	 .hint{color: #BBB;margin-bottom:-16px;}
	 .sui-msg.msg-block {margin: 10px 10px 20px 10px;}
	  .sui-table.table-bordered th, .sui-table.table-bordered td{ border-left: none;}
	  .main-content .main-center dl{padding-bottom: 0;}
	  .main-content{padding-bottom: 0px;}
	  .imgDiv{float: left;margin-top: 5px;}
	  .dropdown-inner{min-width: 130px;}
</style>
</head>
<body>
	<div class="main-center">
		<div class="sui-row-fluid">
			<div class="goods-list">
			<dl class="">
				<dt>
					<c:set var="isEdit" value ="${not empty tradeReturnOrder.returnOrderId?true:false}"></c:set>
					<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('客户服务')} > <a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm">${fns:fy('退款及退货')}</a> > ${fns:fy('退款')}${isEdit?fns:fy('详情'):fns:fy('申请')}</div>
					<i class="sui-icon icon-tb-list"></i> ${fns:fy('退款')}${isEdit?fns:fy('详情'):fns:fy('申请')}
				</dt>
				<div class="sui-row-fluid">
					<div class="span7" style="border-right: solid 1px #DDD;">
						<!-- 提示信息开始 -->
						<div class="sui-msg msg-tips msg-block" >
							<div class="msg-con">
								<ul style="padding-bottom: 10px;">
									<h4>${fns:fy('提示：')}</h4>
									<li>${fns:fy('1.若提出申请后，商家拒绝退款或退货，可再次提交申请。')}</li>
									<li>${fns:fy('2.成功完成退款/退货，经过商城审核后，根据不同的支付方式，钱款将原路返回。')}</li>
								</ul>
							</div>
						</div>
						<!-- 提示信息结束 -->
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
						<!-- 买家申请开始 -->
						<sys:message content="${message}"/>
						<form action="${ctxm}/trade/tradeReturnOrder/save2.htm" method="post" id="inputForm" class="sui-form">
							<input type="hidden" name="type" value="${type}"/>
							<input type="hidden" name="orderId" value="${tradeOrder.orderId}">
							<input type="hidden" name="orderItemId" value="${tradeOrderItem.orderItemId}">
							<input type="hidden" name="storeId" value="${tradeOrder.storeId}">
							<c:if test="${empty tradeReturnOrder.returnOrderId }">
								<table class="sui-table table-bordered">
									<tbody>
										<tr>
											<th colspan="2"> 
												<label class="pull-left">${fns:fy('如果商家不同意，可以再次申请或投诉。')}</label>
											</th>
										</tr>
										<tr>
											<td width="20%" class="right"><font color="red">*</font> ${fns:fy('退款原因：')}</td>
											<td width="80%">
												<div class="control-group">
													<span class="sui-dropdown dropdown-bordered select">
														<span class="dropdown-inner">
															<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
																<input value="${fns:getDictList('trade_return_reason')[0].value}" name="returnReason" type="hidden"><i class="caret"></i>
																<span>
																	${fns:getDictList('trade_return_reason')[0]}
																</span>
															</a>
														  	<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
														  		<c:forEach items="${fns:getDictList('trade_return_reason')}" var="tr">
																	<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${tr.value}">${tr.label}</a></li>
																</c:forEach>
														  	</ul>
														</span>
												  	</span>
												</div>
											</td>
										</tr>
										<tr>
											<td width="20%" class="right"><font color="red">*</font> ${fns:fy('退款金额：')}</td>
											<td width="80%">
												<%-- <div class="input-append fixedFreightDiv">
													<input class="span2 input-fat input-small text" name="returnMoney"  type="text"  value="${tradeOrderItem.price}" style="height:28px;">
													<!-- <span class="add-on">元</span> -->
													 --%>
												<c:if test="${tradeOrder.orderStatus eq '20'}">
													<span>${not empty tradeOrder.offsetAmount?tradeOrder.offsetAmount:tradeOrder.amountPaid}${fns:fy('元')}</span>
													<input type="hidden" name="oldReturnMoney" value="${not empty tradeOrder.offsetAmount?tradeOrder.offsetAmount:tradeOrder.amountPaid}"/>
													<input type="hidden" name="returnMoney" value="${not empty tradeOrder.offsetAmount?tradeOrder.offsetAmount:tradeOrder.amountPaid}"/>
												</c:if>
												<c:if test="${tradeOrder.orderStatus ne '20'}">
													<c:if test="${not empty tradeOrder.offsetAmount}">
														<input type="hidden" name="oldReturnMoney" value="${tradeOrder.offsetAmount}">
														<input class="span2 input-fat input-small text" name="returnMoney"  type="text" value="${tradeOrder.offsetAmount}" style="height:28px;">
														<span>(${fns:fy('最多')} <span name="maxReturnMoney">${tradeOrder.offsetAmount}</span>${fns:fy('元')})</span>
													</c:if>
													<c:if test="${empty tradeOrder.offsetAmount}">
														<input type="hidden" name="oldReturnMoney" value="${tradeOrderItem.price}">
														<input class="span2 input-fat input-small text" name="returnMoney"  type="text" value="${tradeOrderItem.price}" style="height:28px;">
														<span>(${fns:fy('最多')} <span name="maxReturnMoney">${tradeOrderItem.price}</span>${fns:fy('元')})</span>
													</c:if>
													<div style="color: #ccc;">
														${fns:fy('退款金额不能超过可退金额。')}
													</div>
												</c:if>
											</td>
										</tr>
										<tr>
											<td width="20%" class="right"><font color="red">*</font> ${fns:fy('退款说明：')}</td>
											<td width="80%">
												<textarea rows="5" cols="39" name="returnExplain" maxLength="1024"></textarea>
											</td>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('上传凭证：')}</td>
											<td width="80%">
												<div>
													<div class="input-append">
									        			<input type="hidden" name="img1"/>
									        			<input type="hidden" name="img2"/>
									        			<input type="hidden" name="img3"/>
									        			<div id="vessel"></div>
									        		</div>
													<br>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
								<button class="sui-btn btn-xlarge btn-primary" type="submit" style="margin:10px 0px 10px 100px;">${fns:fy('确定')}</button>
							</c:if>
							<!-- 买家申请结束 -->
							<!-- 买家申请信息开始 -->
							<c:if test="${not empty tradeReturnOrder.returnOrderId }">
								<table class="sui-table table-bordered">
									<tbody>
										<tr class="sep-row" colspan="2" style="height:15px;"></tr>
										<tr>
											<th colspan="2"> 
												<label class="pull-left">${fns:fy('我的退款申请')}</label>
											</th>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('退款编号：')}</td>
											<td width="80%"> ${tradeReturnOrder.returnOrderId}</td>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('退款原因：')}</td>
											<td width="80%">${fns:getDictLabel(tradeReturnOrder.returnReason, 'trade_return_reason', '')}</td>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('退款金额：')}</td>
											<td width="80%">¥ ${tradeReturnOrder.returnMoney}</td>
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
														<img alt="" src="${ctxfs}${voucher.path}@80x80" width="80px;" height="80px;" onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
													</a>	
												</c:forEach>
											</td>
										</tr>
									</tbody>
								</table>
							</c:if>
							<!-- 买家申请信息结束 -->
							<!-- 商家退款处理开始 -->
							<c:if test="${tradeReturnOrder.businessHandle eq'1' || tradeReturnOrder.businessHandle eq'2'}">
								<table class="sui-table table-bordered">
									<tbody>
										<tr>
											<th colspan="2"> 
												<label class="pull-left">${fns:fy('商家退款处理')}</label>
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
							<!-- 商家处理退款结束 -->
							<!-- 平台退款处理开始 -->
							<c:if test="${tradeReturnOrder.systemHandle eq'1' || tradeReturnOrder.systemHandle eq'2'}">
								<table class="sui-table table-bordered">
									<tbody>
										<tr>
											<th colspan="2"> 
												<label class="pull-left">${fns:fy('平台退款处理')}</label>
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
							<!-- 平台退款处理结束 -->
							<!-- 退款详细开始 -->
							<c:if test="${tradeReturnOrder.systemHandle eq '1'}">
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
							<!-- 退款详细结束 -->
							<a class="sui-btn btn-xlarge" style="margin:10px 0px 10px 100px;" href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm?type=${tradeReturnOrder.type}">${fns:fy('返回列表')}</a>
						</form>
					</div>
					<!-- 商品交易信息开始 -->
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
													<img src="${ctxfs}${tradeOrderItem.thumbnailPath}" style="width: 80px;height: 80px;"
													onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
													<ul class="unstyled">
														<li><a href="javascript:;" target="_blank">${tradeOrderItem.name}</a></li>
														<li>
															<c:if test="${not empty tradeOrder.offsetAmount}">${fns:fy('￥')}${tradeOrder.offsetAmount} ${fns:fy('数量')}${tradeOrderItem.quantity}</c:if>
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
														<li>${fns:fy('运费：')}${fns:fy('￥')} ${tradeOrder.freight}</li>
														<li>
															<c:if test="${not empty tradeOrder.offsetAmount}">${fns:fy('订单总额：')} ${fns:fy('￥')}${tradeOrder.offsetAmount}</c:if>
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
														<li>${fns:fy('订单编号：')}${tradeOrder.orderId}</li>
														<li>${fns:fy('退单编号：')}${tradeReturnOrder.returnOrderId}</li>
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
														<li>${fns:fy('商家：')}${tradeOrder.store.name}</li>
													</ul>
													<li>
														<span style="display:inline-block;" href="javascript:void(0)" +data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center"
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