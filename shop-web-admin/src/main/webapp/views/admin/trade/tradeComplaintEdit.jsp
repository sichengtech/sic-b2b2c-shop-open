<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('投诉详情')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="4"/><!-- 表示使用N号二级菜单 -->
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeComplaintEdit.js"></script>
<style type="text/css">
	.form-horizontal .form-group{margin-right: 0px;margin-left: 0px;}
	.col-sm-5{padding-top: 4px;}
	h4{font-size: 16px;}
	.div_talk {background-color: #f5f5f5; border: 1px dashed #d8d8d8;height: 200px;
		overflow-y: scroll; padding: 8px;width: 100%;word-break: normal; word-wrap: break-word;
	}
	#talkBox{color: #999;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<!-- panel-head开始 -->
		<header class="panel-heading custom-tab tab-right">
			<h4 class="title">${fns:fy('投诉详情')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/trade/tradeComplaint/list.do"> <i class="fa fa-home"></i> ${fns:fy('投诉列表')}</a></li>
				<li class="active"><a href="javascript:;"> <i class="fa fa-user"></i> ${fns:fy('投诉详情')}</a></li>
			</ul>
		</header>
		<!-- panel-head结束 -->
		<!-- panel-body开始 -->
		<div class="panel-body" style="padding-top:0;">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('投诉管理.投诉列表.操作提示1')}</li>
					<li>${fns:fy('投诉管理.投诉列表.操作提示2')}</li>
					<li>${fns:fy('投诉管理.投诉列表.操作提示3')}</li>
					<li>${fns:fy('投诉管理.投诉列表.操作提示4')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<!-- 表单开始 -->
					<form action="${ctxa}/trade/tradeComplaint/audit.do" class="cmxform form-horizontal adminex-form" id="inputForm" method="post" stat="">
						<input type="hidden" value="${tradeComplaint.complaintId}" name="complaintId" id="complaintId"/>
						<!-- 订单信息开始 -->
						<div>
							<h4 style="display: inline-block;">${fns:fy('订单信息')}</h4>
						</div>
						<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px; margin-bottom: 0; border-bottom: none;">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('订单号')}&nbsp;:</label>
							<div class="col-sm-5">
								<a href="${ctxa}/trade/tradeOrder/tradeOrderDetail.do?orderId=${tradeComplaint.tradeOrder.orderId}"><small>${tradeComplaint.tradeOrder.orderId}</small></a>
							</div>
						</div>
						<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('下单时间')}&nbsp;:</label>
							<div class="col-sm-5">
								<small><fmt:formatDate value="${tradeComplaint.tradeOrder.placeOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('订单状态')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${fns:getDictLabel(tradeComplaint.tradeOrder.orderStatus, 'trade_order_status', '')}</small>
							</div>
						</div>
						<!-- 订单信息结束 -->

						<!-- 投诉商品开始 -->
						<h4>${fns:fy('投诉商品')}</h4>
						<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('商品名称')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${tradeComplaint.tradeOrderItem.name}</small>
								<a href="${ctxf}/detail/${tradeComplaint.tradeOrderItem.PId}.htm" target="_blank">${fns:fy('查看')}</a>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('单价')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${tradeComplaint.tradeOrderItem.price}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('购买数量')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${tradeComplaint.tradeOrderItem.quantity}</small>
							</div>
						</div>
						<!-- 投诉商品结束 -->

						<!-- 投诉信息开始 -->
						<h4>${fns:fy('投诉信息')}</h4>
						<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('投诉人')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${tradeComplaint.userMain.loginName}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('投诉时间')}&nbsp;:</label>
							<div class="col-sm-5">
								<small><fmt:formatDate value="${tradeComplaint.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('被投诉商家')} &nbsp;:</label>
							<div class="col-sm-5">
								<small>${tradeComplaint.store.name}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('投诉主题')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${fns:getDictLabel(tradeComplaint.theme, 'trade_complaint_theme', '')}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('投诉内容')}&nbsp;:</label>
							<div class="col-sm-5" style="word-break: break-all;">
								<small>${tradeComplaint.content}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('投诉状态')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${fns:getDictLabel(tradeComplaint.status, 'trade_complaint_status', '')}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('投诉凭证')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="jqthumb" style="width: 60px; height: 60px; opacity: 1;">
									<c:forEach items="${tradeComplaint.tradeComplaintImageList}" var="img">
										<img alt="" src="${ctxfs}${img.path}@40x40" width="40px;" height="40px;">
									</c:forEach>
								</div>
							</div>
						</div>
						<!-- 投诉信息结束 -->
						
						<!-- 申诉信息开始 -->
						<%-- 状态为对话中显示申诉信息 --%>
						<c:if test="${not empty tradeComplaint.replyTradeComplaint.content}">
							<h4>申诉信息</h4>
							<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('申诉时间')}&nbsp;:</label>
								<div class="col-sm-5">
									<small><fmt:formatDate value="${tradeComplaint.replyTradeComplaint.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('申诉内容')}&nbsp;:</label>
								<div class="col-sm-5" style="word-break: break-all;">
									<small>${tradeComplaint.replyTradeComplaint.content}</small>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('申诉凭证')}&nbsp;:</label>
								<c:forEach items="${tradeComplaint.replyTradeComplaint.tradeComplaintImageList}" var="voucher">
									<img alt="" src="${ctxfs}${voucher.path}@40x40" width="40px;" height="40px;">
								</c:forEach>
							</div>
						</c:if>
						<!-- 申诉信息结束 -->

						<!-- 对话详情开始 -->
						<%-- 当商家有对话信息是显示申诉信息 --%>
						<c:if test="${tradeComplaint.status == '30'}">
							<h4>${fns:fy('对话详情')}</h4>
							<div class="form-group messageDetail" style="border-top:1px solid #eff2f7;padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('对话记录')}&nbsp;:</label>
								<div class="col-sm-5">
									<div class="div_talk" id="talkBox">
										<c:if test="${empty tradeComplaint.tradeComplaintDetailList}">
											<p id="noContent">${fns:fy('暂无对话')}</p>
										</c:if>
										<c:forEach items="${tradeComplaint.tradeComplaintDetailList}" var="complaintDetail">
											<c:if test="${complaintDetail.isShield == '1'}">
												<p><span class="userType">${fns:getDictLabel(complaintDetail.userType, 'user_type', '')}：</span>${fns:fy('内容已被管理员屏蔽')}</p>
											</c:if>
											<c:if test="${complaintDetail.isShield == '0'}">
												<p class="qq"><span class="userType">${fns:getDictLabel(complaintDetail.userType, 'user_type', '')}：</span>${complaintDetail.count}
													<a class="m-l-15 shield" id="${complaintDetail.cdId}" href="javascript:;" data-talk-id="9">${fns:fy('屏蔽')}</a>
												</p>
											</c:if>										
										</c:forEach>
									</div>
								</div>
							</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('发送对话')}&nbsp;:</label>
									<div class="col-sm-5">
										<input type="hidden" name="userType" value="3"/>
										<textarea id="messCount" name="count" class="form-control" rows="5"></textarea>
										<div class="form-group" style="margin-top:10px;">
											<shiro:hasPermission name="trade:tradeComplaint:edit">
											 	<button type="button" id="sendMessage" class="btn btn-info">${fns:fy('发送对话')}</button>
												<button type="button" id="refreshMessage" class="btn btn-default">${fns:fy('刷新对话')}</button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
						</c:if>
						<!-- 对话详情结束 -->

						<!-- 平台最终处理开始 -->
						<c:if test="${tradeComplaint.status == '50'}">
							<h4>${fns:fy('平台最终处理')}</h4>
							<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('处理时间')}&nbsp;:</label>
								<div class="col-sm-5">
									<small><fmt:formatDate value="${tradeComplaint.systemHandleTime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" name="systemHandleHandelOpinion" for="inputSuccess" id="handleMess">${fns:fy('处理意见')}&nbsp;:</label>
								<div class="col-sm-5" style="word-break: break-all;">
									<small>${tradeComplaint.systemHandleHandelOpinion}</small>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('处理人')}&nbsp;:</label>
								<div class="col-sm-5">
									<small>${tradeComplaint.user.loginName}</small>
								</div>
							</div>
						</c:if>
						<!-- 平台最终处理结束 -->

						<!-- 平台处理开始 -->
						<%-- 当投诉状态为新投诉时，显示 审核并交由商家申诉 按钮 --%>
						<c:if test="${stat=='1' && tradeComplaint.status != '50'}">
							<h4>${fns:fy('平台处理')}</h4>
							<div class="form-group" id="handelDiv" style="border-top:1px solid #eff2f7;padding-top:10px;display: none;">
								<label class="control-label col-sm-2" for="inputSuccess" id="handelLabel"><font color="red">*</font>${fns:fy('处理意见')}&nbsp;:</label>
								<div class="col-sm-5">
								 	<textarea name="systemHandleHandelOpinion" class="form-control" id="messageText" rows="5"></textarea>
								 	<shiro:hasPermission name="trade:tradeComplaint:auth">
								 		<button type="button" class="btn btn-info" id="messBtn" style="margin-top: 10px;">${fns:fy('提交保存仲裁意见')}</button>
									</shiro:hasPermission>
								</div>
							</div>
							<div class="form-group" id="handelBtn" style="border-top:1px solid #eff2f7;padding-top:10px;">
								<div class="col-sm-7 col-md-offset-1">
								 	<shiro:hasPermission name="trade:tradeComplaint:auth">
								 		<c:if test="${tradeComplaint.status == '10'}">
									 		<button type="button" href="${ctxa}/trade/tradeComplaint/audit.do?complaintId=${tradeComplaint.complaintId}" class="btn btn-info" id="auditBtn">
									 			${fns:fy('审核并交由商家申诉')}
									 		</button>
									 	</c:if>
										<button class="btn btn-info" type="button" id="endComplaint">${fns:fy('直接仲裁结束投诉流程')}</button>
									</shiro:hasPermission>
								</div>
							</div>
						<!-- 平台处理结束 -->
						</c:if>
					</form>
					<!-- 表单结束 -->
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel 结束 -->
</body>
</html>