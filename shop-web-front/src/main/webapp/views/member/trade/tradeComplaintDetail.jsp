<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/member/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('投诉详情')}</title>
<meta name="decorator" content="member"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/member/trade/tradeComplaintDetail.js"></script>
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
	 .sui-table.table-bordered th, .sui-table.table-bordered td{ border-left: none;} 
	 .main-content .main-center dl{padding-bottom: 0;}
	 .main-content{padding-bottom: 0px;}
	 .imgDiv{float: left;margin-top: 5px;}
	 .main-content{min-height: 300px;}
	 #talkBox p{margin-bottom: 5px;}
	 .sui-steps-round>div>label{overflow: visible;}
</style>
</head>
<body>
	<div class="main-center">
		<div class="sui-row-fluid">
			<div class="goods-list">
				<dl class="">
					<dt>
						<c:set var="isEdit" value ="${not empty tradeComplaint.complaintId?true:false}"></c:set>
						<div class="position"><span>${fns:fy('当前位置')}:</span><span>${fns:fy('会员中心')}</span> > ${fns:fy('客户服务')} > <a href="${ctxm}/trade/tradeComplaint/list.htm">${fns:fy('交易投诉')}</a> > ${fns:fy('投诉')}${isEdit?fns:fy('详情'):fns:fy('申请')}</div>
						<i class="sui-icon icon-tb-list"></i> ${fns:fy('投诉')}${isEdit?fns:fy('详情'):fns:fy('申请')}
					</dt>
					<div class="sui-row-fluid">
						<div class="span7" style="border-right: solid 1px #DDD;">
							<div class="sui-steps-round steps-round-auto steps-5">
					 			<c:choose>
									<c:when test="${empty tradeComplaint.complaintId}">
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
									<label>${fns:fy('新投诉')}</label>
					 			</div>
					 			
					 			<c:choose>
									<c:when test="${empty tradeComplaint.complaintId || tradeComplaint.status == '10'}">
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
									<c:when test="${empty tradeComplaint.complaintId || tradeComplaint.status == '10' || tradeComplaint.status == '20'}">
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
									<c:when test="${empty tradeComplaint.complaintId || tradeComplaint.status == '10' || tradeComplaint.status == '20' || tradeComplaint.status == '30'}">
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
									<c:when test="${empty tradeComplaint.complaintId || tradeComplaint.status == '10' || tradeComplaint.status == '20' || tradeComplaint.status == '30' || tradeComplaint.status == '40'}">
										<div class="todo last">
									</c:when>
									<c:when test="${tradeComplaint.status == '50'}">
										<div class="current last">
									</c:when>
									<c:otherwise>
										<div class="finished last">
									</c:otherwise>
								</c:choose>
									<div class="wrap">
						 				<div class="round"><i class="sui-icon icon-touch-right"></i></div>
						 				<div class="bar"></div>
									</div>
									<label style="margin-right: 15px;width: 36px;">${fns:fy('已完成')}</label>
					 			</div>
							</div>
							<!-- 买家投诉申请开始 -->
							<sys:message content="${message}"/>
							<form action="${ctxm}/trade/tradeComplaint/save2.htm" method="post" id="inputForm" class="sui-form">
								<input type="hidden" name="orderId" value="${tradeOrder.orderId}"/>
								<input type="hidden" name="orderDetailId" value="${tradeOrderItem.orderItemId}"/>
								<c:if test="${empty tradeComplaint.complaintId }">
									<table class="sui-table table-bordered">
										<tbody>
											<tr>
												<th colspan="2"> 
													<label class="pull-left">${fns:fy('我的投诉信息')}</label>
												</th>
											</tr>
											<tr>
												<td width="20%" class="right"><font color="red">*</font>${fns:fy('选择投诉主题：')}</td>
												<td width="80%">
													<c:forEach items="${fns:getDictList('trade_complaint_theme')}" var="theme" varStatus="themeIndex">
														<p><input type="radio" name="theme" value="${theme.value}" ${themeIndex.first?'checked="checked"':'' }/><span>${theme.label}</span></p>
													</c:forEach>
												</td>
											</tr>
											<tr>
												<td width="20%" class="right"><font color="red">*</font>${fns:fy('投诉内容：')}</td>
												<td width="80%">
													<textarea rows="5" cols="39" class="" name="content"></textarea>
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
									<button class="sui-btn btn-xlarge btn-primary" type="submit" style="margin:10px 0px 10px 50px;">${fns:fy('确定')}</button>
								</c:if>
								<!-- 买家投诉申请结束 -->
								<!-- 买家投诉信息开始 -->
								<c:if test="${not empty tradeComplaint.complaintId }">
									<table class="sui-table table-bordered">
										<tbody>
											<tr class="sep-row" colspan="2" style="height:15px;"></tr>
											<tr>
												<th colspan="2"> 
													<label class="pull-left">${fns:fy('投诉信息')}</label>
												</th>
											</tr>
											<tr>
												<td width="20%" class="right">${fns:fy('投诉主题：')}</td>
												<td width="80%">${fns:getDictLabel(tradeComplaint.theme, 'trade_complaint_theme', '')}</td>
											</tr>
											<tr>
												<td width="20%" class="right">${fns:fy('投诉时间：')}</td>
												<td width="80%"><fmt:formatDate value="${tradeComplaint.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<td width="20%" class="right">${fns:fy('投诉内容：')}</td>
												<td width="80%">${tradeComplaint.content}</td>
											</tr>
											<tr>
												<td width="20%" class="right">${fns:fy('投诉凭证：')}</td>
												<td width="80%">
													<div class="typographic gallery">
														<c:forEach items="${tradeComplaint.tradeComplaintImageList}" var="voucher">
															<a href="${ctxfs}${voucher.path}">
																<img alt="" src="${ctxfs}${voucher.path}@80x80" style="width:80px;height: 80px;"
																	onerror="fdp.defaultImage('${ctxStatic}/images/default_store.png');"/>
															</a>
														</c:forEach>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</c:if>
								<!-- 买家投诉信息结束 -->
								<!-- 卖家申诉信息开始 -->
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
								<!-- 卖家申诉信息结束 -->
								<!-- 对话详情开始 -->
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
													<div class="ncm-complain-talk" id="talkBox">
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
													<textarea id="messContent" rows="5" cols="40" class="" name="count"></textarea>
												</td>
											</tr>
										</tbody>
									</table>
									<button id="sendMessage" type="button" class="sui-btn btn-xlarge btn-primary" style="margin:10px 0px 10px 20px;">${fns:fy('发送对话')}</button>
									<a class="sui-btn btn-xlarge" style="margin:10px 0px 10px 20px;" href="javascript:;" id="refreshMessage">${fns:fy('刷新对话')}</a>
									<c:if test="${tradeComplaint.status eq '30'}">
										<button id="arbitration" href="${ctxm}/trade/tradeComplaint/arbitration.htm?complaintId=${tradeComplaint.complaintId}" type="button" class="sui-btn btn-xlarge btn-danger" style="margin:10px 0px 10px 20px;">${fns:fy('提交仲裁')}</button>
									</c:if>
								</c:if>
								<!-- 对话详情结束 -->
								<!-- 平台最终处理 -->
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
								<a class="sui-btn btn-xlarge" style="margin:10px 0px 10px 20px;" href="${ctxm}/trade/tradeComplaint/list.htm">${fns:fy('返回列表')}</a>
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
															<img src="${ctxfs}${tradeComplaint.tradeOrderItem.thumbnailPath}@80x80" style="width: 80px;height: 80px;"
															onerror="fdp.defaultImage('${ctxStatic}/images/default_goods.png');"/>
														<ul class="unstyled">
															<li><a href="javascript:;" target="_blank">${tradeOrderItem.name}</a></li>
															<li>${fns:fy('￥')}${tradeOrderItem.price} * ${tradeOrderItem.quantity} ${fns:fy('(数量)')}</li>
														</ul>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="typographic">
														<ul class="unstyled">
															<li>${fns:fy('运')}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${fns:fy('费：')}&nbsp;${tradeOrder.freight}</li>
															<li>${fns:fy('订单总额：')}${fns:fy('￥')}${tradeOrder.amountPaid}</li>
														</ul>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="typographic">
														<ul class="unstyled">
															<li>${fns:fy('订单编号：')}${tradeOrder.orderId}</li>
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
					</div>
				</dl>
			</div>
		</div>
	</div>
</body>
</html>