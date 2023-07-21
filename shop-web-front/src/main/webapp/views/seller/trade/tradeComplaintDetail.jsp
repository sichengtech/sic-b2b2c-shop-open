<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('投诉详情')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/trade/tradeComplaintDetail.js"></script>
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
	 .ncm-complain-talk{background-color: #fff;border: 1px dashed #dcdcdc;max-height: 200px;overflow-y: scroll;padding: 8px;word-break: normal;word-wrap: break-word;}
	.imgDiv{float: left;margin-top: 5px;}
	#talkBox p{margin-bottom: 5px;}
	.error{width: auto;}
	label.finish{margin-right: 15px;}
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
						<span>${fns:fy('投诉详情')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('售后')}</li>
						<li>${fns:fy('售后管理')}</li>
						<li><a href="#">${fns:fy('投诉详情')} </a></li>
					</ul>
				</dt>
				<!-- 提示信息开始 -->
				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('此列表显示了投诉的详细信息。')}</li>
								<li>${fns:fy('未处理的投诉可以在此页面进行处理。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<!-- 提示信息结束 -->
				<!--状态导航开始 -->
				<dd class="sui-row-fluid sui-form form-horizontal screen pt20 mb0">
					<div class="header header1" style="background: none;height: 37px; box-shadow: none;border-bottom: 1px solid #e5e5e5;margin-bottom: 0px;">
						<div class="sui-container">
							<div class="sui-navbar container nav-box">
								<div class="navbar-inner">
									<ul class="sui-nav">
										<li class=""><a style="height: 37px;line-height: 37px;" href="${ctx}/views/seller/trade/tradeComplaintList.jsp">${fns:fy('投诉管理')}</a></li>
										<li class="active"><a style="height: 37px;line-height: 37px;" href="${ctx}/views/seller/trade/tradeComplaintInfo.jsp">${fns:fy('详情')}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</dd>
				<!--状态导航结束 -->
				<sys:message content="${message}"/>
				<div class="sui-row-fluid">
					<div class="span7" style="border-right: solid 1px #DDD;">
						<form action="${ctxs}/trade/tradeComplaint/edit2.htm" id="inputForm">
							<div class="sui-steps-round steps-round-auto steps-5">
								<div class="finished">
									<div class="wrap">
										<div class="round"><i class="sui-icon icon-touch-right"></i></div>
										<div class="bar"></div>
									</div>
									<label>${fns:fy('新投诉')}</label>
								</div>
								
								<c:choose>
									<c:when test="${tradeComplaint.status == '10'}">
										<div class="todo">
									</c:when>
									<c:when test="${tradeComplaint.status == '20'}">
										<div class="current">
									</c:when>
									<c:otherwise>
										<div class="finished">
									</c:otherwise>
								</c:choose>
									<div class="wrap">
										<div class="round"><i class="sui-icon icon-touch-right"></i></div>
										<div class="bar"></div>
									</div>
									<label>${fns:fy('待申诉')}</label>
								</div>
								
								<c:choose>
									<c:when test="${tradeComplaint.status == '10' || tradeComplaint.status == '20'}">
										<div class="todo">
									</c:when>
									<c:when test="${tradeComplaint.status == '30'}">
										<div class="current">
									</c:when>
									<c:otherwise>
										<div class="finished">
									</c:otherwise>
								</c:choose>
									<div class="wrap">
										<div class="round"><i class="sui-icon icon-touch-right"></i></div>
										<div class="bar"></div>
									</div>
									<label>${fns:fy('对话中')}</label>
								</div>
								
								<c:choose>
									<c:when test="${tradeComplaint.status == '10' || tradeComplaint.status == '20' || tradeComplaint.status == '30'}">
										<div class="todo">
									</c:when>
									<c:when test="${tradeComplaint.status == '40'}">
										<div class="current">
									</c:when>
									<c:otherwise>
										<div class="finished">
									</c:otherwise>
								</c:choose>
									<div class="wrap">
										<div class="round"><i class="sui-icon icon-touch-right"></i></div>
										<div class="bar"></div>
									</div>
									<label>${fns:fy('待仲裁')}</label>
								</div>
								
								<c:choose>
									<c:when test="${tradeComplaint.status == '10' || tradeComplaint.status == '20' 
										|| tradeComplaint.status == '30' || tradeComplaint.status == '40'}">
										<div class="todo last">
									</c:when>
									<c:when test="${tradeComplaint.status == '50'}">
										<div class="current">
									</c:when>
									<c:otherwise>
										<div class="finished last">
									</c:otherwise>
								</c:choose>
									<div class="wrap">
										<div class="round"><i class="sui-icon icon-touch-right"></i></div>
										<div class="bar"></div>
									</div>
									<label class="finish">${fns:fy('完成')}</label>
								</div>
							</div>
							<table class="sui-table table-bordered">
								<tbody>
									<tr class="sep-row" colspan="2" style="height:15px;"></tr>
									<tr>
										<th colspan="2"> 
											<label class="pull-left">${fns:fy('投诉信息')}</label>
										</th>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('投诉状态：')}</td>
										<td width="80%">${fns:getDictLabel(tradeComplaint.status, 'trade_complaint_status', '')}</td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('投诉时间：')}</td>
										<td width="80%"><fmt:formatDate value="${tradeComplaint.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('投诉主题：')}</td>
										<td width="80%">${fns:getDictLabel(tradeComplaint.theme, 'trade_complaint_theme', '')}</td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('投诉内容：')}</td>
										<td width="80%">${tradeComplaint.content}</td>
									</tr>
									<tr>
										<td width="20%" class="right">${fns:fy('投诉凭证：')}</td>
										<td width="80%">
											<div class="typographic">
												<c:forEach items="${tradeComplaint.tradeComplaintImageList}" var="voucher">
													<img alt="" src="${ctxfs}${voucher.path}@80x80" style="width:80px;height: 80px;"
													onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
							<%-- 在待申诉状态时，商家可以发出申诉信息 --%>
							<c:if test="${tradeComplaint.status eq '20'}">
								<input type="hidden" name="replyId" value="${tradeComplaint.complaintId}"/>
								<table class="sui-table table-bordered">
									<tbody>
										<tr>
											<th colspan="2"> 
												<label class="pull-left">${fns:fy('申诉信息')}</label>
											</th>
										</tr>
										<tr>
											<td width="20%" class="right"><font color="red">*</font>${fns:fy('申诉内容：')}
											</td>
											<td width="80%">
												<textarea rows="5" cols="50" class="input-xlarge input-error" name="content" maxlength="255"></textarea>
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
								<shiro:hasPermission name="trade:tradeComplaint:edit">
									<button type="submit" class="sui-btn btn-xlarge btn-primary" style="margin:10px 0px 10px 100px;">${fns:fy('确定')}</button>
								</shiro:hasPermission>
							</c:if>
							<%-- 当商家提出申诉后，显示申诉信息 --%>
							<c:if test="${not empty tradeComplaint.replyTradeComplaint.content}">
								<table class="sui-table table-bordered">
									<tbody>
										<tr>
											<th colspan="2"> 
												<label class="pull-left">${fns:fy('申诉信息')}</label>
											</th>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('申诉时间：')}</td>
											<td width="80%"><fmt:formatDate value="${tradeComplaint.replyTradeComplaint.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('申诉内容：')}</td>
											<td width="80%">${tradeComplaint.replyTradeComplaint.content}</td>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('申诉凭证：')}</td>
											<td width="80%">
												<div class="typographic">
													<c:forEach items="${tradeComplaint.replyTradeComplaint.tradeComplaintImageList}" var="voucher">
														<img alt="" src="${ctxfs}${voucher.path}@80x80" width="80px;" height="80px;">
													</c:forEach>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</c:if>	
							
							<%-- 在对话中和待仲裁状态时，商家可以发送对话 --%>
							<c:if test="${tradeComplaint.status eq '30' || tradeComplaint.status eq '40'}">
								<table class="sui-table table-bordered">
									<tbody>
										<tr>
											<th colspan="2">
												<input type="hidden" id="complaintId" name="complaintId" value="${tradeComplaint.complaintId}"/>
												<label class="pull-left">${fns:fy('对话详情')}</label>
											</th>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('对话记录：')}</td>
											<td width="80%">
												<div class="div_talk" id="talkBox">
													<c:if test="${empty tradeComplaint.tradeComplaintDetailList}">
														<p id="noContent">${fns:fy('暂无对话')}</p>
													</c:if>
													<c:forEach items="${tradeComplaint.tradeComplaintDetailList}" var="complaintDetail">
														<c:if test="${complaintDetail.isShield == '1'}">
															<p><span class="userType">${fns:getDictLabel(complaintDetail.userType, 'user_type', '')}：</span>${fns:fy('内容已被管理员屏蔽')}</p>
														</c:if>
														<c:if test="${complaintDetail.isShield == '0'}">
															<p class="qq"><span class="userType">
																${fns:getDictLabel(complaintDetail.userType, 'user_type', '')}：</span>${complaintDetail.count}
															</p>
														</c:if>										
													</c:forEach>
												</div>
											</td>
										</tr>
										<tr>
											<td width="20%" class="right"><font color="red">*</font>${fns:fy('发送对话：')}</td>
											<td width="80%">
												<textarea id="messCount" rows="5" cols="42" class="input-xlarge input-error" name="count"></textarea>
											</td>
										</tr>
									</tbody>
								</table>
								<shiro:hasPermission name="trade:tradeComplaint:edit">
									<button id="sendMessage" type="button" class="sui-btn btn-xlarge btn-primary" style="margin:10px 0px 10px 100px;">${fns:fy('发送对话')}</button>
									<c:if test="${tradeComplaint.status eq '30'}">
										<button id="arbitration" href="${ctxs}/trade/tradeComplaint/arbitration.htm?complaintId=${tradeComplaint.complaintId}" type="button" class="sui-btn btn-xlarge btn-danger" style="margin:10px 0px 10px 50px;">${fns:fy('提交仲裁')}</button>
									</c:if>
								</shiro:hasPermission>
							</c:if>
							
							<c:if test="${tradeComplaint.status eq '50'}">
								<table class="sui-table table-bordered">
									<tbody>
										<tr>
											<th colspan="2"> 
												<label class="pull-left">${fns:fy('平台最终处理')}</label>
											</th>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('处理时间：')}</td>
											<td width="80%"><fmt:formatDate value="${tradeComplaint.systemHandleTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										</tr>
										<tr>
											<td width="20%" class="right">${fns:fy('处理内容：')}</td>
											<td width="80%">${tradeComplaint.systemHandleHandelOpinion}</td>
										</tr>
									</tbody>
								</table>
							</c:if>
							<a class="sui-btn btn-xlarge" style="margin:10px 0px 10px 50px;" href="${ctxs}/trade/tradeComplaint/list.htm">${fns:fy('返回列表')}</a>
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
													<c:choose>
														<c:when test="${not empty  tradeComplaint.tradeOrderItem.thumbnailPath}">
															<img src="${ctxfs}${tradeComplaint.tradeOrderItem.thumbnailPath}@80x80" width="80" height="80">
														</c:when>
														<c:otherwise>
															<img src="${ctxStatic}/images/noimg.jpg" width="80" height="80">
														</c:otherwise>
													</c:choose>
													<ul class="unstyled">
														<li><a href="javascript:;" target="_blank">${tradeComplaint.tradeOrderItem.name}</a></li>
														<li>￥${tradeComplaint.tradeOrderItem.price} * ${tradeComplaint.tradeOrderItem.quantity} (${fns:fy('数量')})</li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="typographic">
													<ul class="unstyled">
														<li>${fns:fy('运费：')}${tradeComplaint.tradeOrder.freight}</li>
														<li>${fns:fy('订单总额：')}${fns:fy('￥')}${tradeComplaint.tradeOrder.amountPaid}</li>
													</ul>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="typographic">
													<ul class="unstyled">
														<li>${fns:fy('订单编号：')}${tradeComplaint.tradeOrder.orderId}</li>
														<li>${fns:fy('物流单号：')}${tradeComplaint.tradeOrder.trackingNo}</li>
													</ul>
														<span style="display:inline-block;" href="javascript:void(0)" data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center" 
															title="${fns:fy('支付方式：')}${tradeComplaint.tradeOrder.paymentMethodName}</br>
															${fns:fy('下单时间：')}<fmt:formatDate value="${tradeComplaint.tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></br>
															${fns:fy('支付时间:')}<fmt:formatDate value="${tradeComplaint.tradeOrder.payOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></br>
															${fns:fy('发货时间：')}<fmt:formatDate value="${tradeComplaint.tradeOrder.deliverProductDate}" pattern="yyyy-MM-dd HH:mm:ss"/></br>
															${fns:fy('订单完成时间:')}<fmt:formatDate value="${tradeComplaint.tradeOrder.productReceiptDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
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
														<li>${fns:fy('收货人：')}${tradeComplaint.tradeOrder.consignee}</li>
													</ul>
													<li>
														<span style="display:inline-block;" href="javascript:void(0)" data-placement="bottom" data-trigger="hover" data-toggle="tooltip" data-align="center" 
														title="${fns:fy('收货地址：')}${tradeComplaint.tradeOrder.provinceName} ${tradeComplaint.tradeOrder.cityName} ${tradeComplaint.tradeOrder.districtName} 
														${tradeComplaint.tradeOrder.detailedAddress} </br>${fns:fy('联系电话：')}${tradeComplaint.tradeOrder.phone}">
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