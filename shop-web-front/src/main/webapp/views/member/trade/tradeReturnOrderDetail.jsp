<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('退款及退货详情')}</title>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/trade/tradeReturnOrderDetail.js"></script>
<meta name="decorator" content="member"/>
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
			<dl>
				<dt>
					<c:set var="isEdit" value ="${not empty tradeReturnOrder.returnOrderId?true:false}"></c:set>
					<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('客户服务')} > <a href="${ctxm}/trade/tradeReturnOrder/tradeReturnOrderList.htm">${fns:fy('退款及退货')}</a> > ${fns:fy('退货退款')}${isEdit?fns:fy('详情'):fns:fy('申请')}</div>
					<i class="sui-icon icon-tb-list"></i> ${fns:fy('退货退款')}${isEdit?fns:fy('详情'):fns:fy('申请')}
				</dt>
				<div class="sui-row-fluid">
					<div class="span7" style="border-right: solid 1px #DDD;">
						<!-- 提示信息开始 -->
						<div class="sui-msg msg-tips msg-block" >
							<div class="msg-con">
								<ul style="padding-bottom: 10px;">
									<h4>${fns:fy('提示：')}</h4>
									<li>${fns:fy('1.若您对订单进行支付后想取消购买且与商家达成一致退货退款，请填写“订单退货”内容并提交。')}</li>
									<li>${fns:fy('2.成功完成退款/退货，经过商城审核后，根据不同的支付方式，钱款将原路返回。')}</li>
								</ul>
							</div>
						</div>
						<!-- 提示信息结束 -->
						<!-- 申请进度条开始 -->
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
						<!-- 买家申请开始 -->
						<sys:message content="${message}"/>
						<form action="${ctxm}/trade/tradeReturnOrder/save2.htm" method="post" id="inputForm" class="sui-form form-inline">
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
											<td width="20%" class="right"><font color="red">*</font> ${fns:fy('退货原因：')}</td>
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
												<c:if test="${not empty tradeOrder.offsetAmount}">
													<input type="hidden" name="oldReturnMoney" value="${tradeOrder.offsetAmount}">
													<input class="span2 input-fat input-small text" name="returnMoney"  type="text"  value="${tradeOrder.offsetAmount}" style="height:28px;">
													<span>(${fns:fy('最多')} <span name="maxReturnMoney">${tradeOrder.offsetAmount}</span>${fns:fy('元')})</span>
													<div style="color: #ccc;">
														${fns:fy('退款金额不能超过可退金额。')}
													</div>
												</c:if>
												<c:if test="${empty tradeOrder.offsetAmount}">
													<input type="hidden" name="oldReturnMoney" value="${tradeOrderItem.price * tradeOrderItem.quantity}">
													<input class="span2 input-fat input-small text" name="returnMoney"  type="text"  value="${tradeOrderItem.price * tradeOrderItem.quantity}" style="height:28px;">
													<span>(${fns:fy('最多')} <span name="maxReturnMoney">${tradeOrderItem.price * tradeOrderItem.quantity}</span>${fns:fy('元')})</span>
													<div style="color: #ccc;">
														${fns:fy('退款金额不能超过可退金额。')}
													</div>
												</c:if>
											
											</td>
										</tr>
										<tr>
											<td width="20%" class="right"><font color="red">*</font> ${fns:fy('退货数量：')}</td>
											<td width="80%">
												<input name="oldReturnCount" type="hidden" value="${tradeOrderItem.quantity}"/>
												<input class="span2 input-fat input-small text" name="returnCount" data-rule-ncdec="true" data-goods-price0="0" data-rule-required="true" type="text" data-rule-number="number" data-rule-min="0" value="${tradeOrderItem.quantity}" style="height:28px;">
											</td>
										</tr>
										<tr>
											<td width="20%" class="right"><font color="red">*</font> ${fns:fy('退货说明：')}</td>
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
								<button class="sui-btn btn-xlarge btn-primary submitBtn" type="button" style="margin:10px 0px 10px 100px;" isNew="1">${fns:fy('确定')}</button>
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
											<td width="20%" class="right">${fns:fy('退货退款编号：')}</td>
											<td width="80%"> ${tradeReturnOrder.returnOrderId}</td>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('退货退款原因：')}</td>
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
								</table
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
							<!-- 商家处理退款结束 -->
							<!-- 填写退货发货信息结束 -->
							<c:if test="${tradeReturnOrder.status eq '30' && tradeReturnOrder.businessHandle eq'1' && tradeReturnOrder.isJettison eq '0'}">
								<table class="sui-table table-bordered">
									<tbody>
										<tr>
											<th colspan="2"> 
												<label class="pull-left">${fns:fy('请填写退货发货信息')}</label>
											</th>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('物流公司：')}</td>
											<td width="80%">
												<input type="hidden" name="returnOrderId" value="${tradeReturnOrder.returnOrderId}"/>
												<input type="hidden" name="logisticsTemplateName" value=""/>
												<div class="control-group">
													<span class="sui-dropdown dropdown-bordered select">
														<span class="dropdown-inner">
															<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
																<input value="-1" name="logisticsTemplateId" companyName="${company.companyName}" type="hidden"/><i class="caret"></i>
																<span name="logisticsTemplateName">${empty tradeOrder.logisticsTemplateId?fns:fy('不使用物流公司'): tradeOrder.logisticsTemplateName}</span>
															</a>
															<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
																<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="-1">${fns:fy('不使用物流公司')}</a></li>
																<c:forEach items="${logisticsCompanyList}" var="company">
																	<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="${company.lcId}">${company.companyName}</a></li>
																</c:forEach>
															</ul>
														</span>
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('物流单号：')}</td>
											<td width="80%">
												<input type="text" name="trackingNo" placeholder="${fns:fy('请输入物流单号')}" class="input-fat freightModelBuyer" style="line-height: 23px;" value="${tradeOrder.trackingNo}"/>
												<div style="color: #ccc;">${fns:fy('发货5天后，当商家选择未收到则要进行延迟时间操作； 如果超过 7 天不处理按弃货处理，直接由管理员确认退款。')}</div>
											</td>
										</tr>
									</tbody>
								</table>
								<button class="sui-btn btn-xlarge btn-primary submitBtn" type="button" style="margin:10px 0px 10px 100px;" isNew="0">${fns:fy('发货')}</button>
							</c:if>
							<!-- 填写退货发货信息结束 -->
							<!-- 退货发货信息开始 -->
							<c:if test="${not empty tradeReturnOrder.deliverProductTime}">
								<table class="sui-table table-bordered">
									<tbody>
										<tr>
											<th colspan="2"> 
												<label class="pull-left">${fns:fy('退货发货信息')}</label>
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
							<!-- 退货发货信息结束 -->
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
												<label class="pull-left"${fns:fy('退款详细')}></label>
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
															<c:if test="${not empty tradeOrder.offsetAmount}">${fns:fy('￥')}${tradeOrder.offsetAmount} ${fns:fy('数量')}:${tradeOrderItem.quantity}</c:if>
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
														<li>${fns:fy('订单编号：')} ${tradeOrder.orderId}</li>
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