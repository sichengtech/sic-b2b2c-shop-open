<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<c:set var="return_refund" value ="${fns:fy('退货退款')}"></c:set> 
<c:set var="refund" value ="${fns:fy('退款')}"></c:set> 
<c:set var="refund_goods" value ="${fns:fy('退货')}"></c:set>
<title>${type eq 1?return_refund:refund }${fns:fy('详情')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="4"/><!-- 表示使用N号二级菜单  -->
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeReturnOrderDetail.js"></script>
<style type="text/css">
	.form-horizontal .form-group{margin-right: 0px;margin-left: 0px;}
	.col-sm-5{padding-top: 4px;}
	h4{font-size: 16px;}
</style>
</head>
<body>
	<!-- panel 开始  -->
	<section class="panel">
		<!-- panel-head开始 -->
	    <header class="panel-heading custom-tab tab-right">
			<h4 class="title">${type eq 1?return_refund:refund }${fns:fy('详情')}</h4>
			<%@ include  file="../include/functionBtn.jsp"%>			
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/trade/tradeReturnOrder/list.do"> <i class="fa fa-home"></i> ${type eq 1?return_refund:refund }${fns:fy('列表')}</a></li>
				<li class="active"><a href="javascript:;"> <i class="fa fa-user"></i> ${type eq 1?return_refund:refund }${fns:fy('详情')}</a></li>
			</ul>
		</header>
		<!-- panel-head结束  -->
		<!-- panel-body开始  -->
		<div class="panel-body" >
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
			    <h5>${fns:fy('操作提示')}</h5>
               	<ul>
               		<li>${fns:fy('退款管理.退货退款详情.操作提示1')}</li>
               		<li>${fns:fy('退款管理.退货退款详情.操作提示2')}</li>
               	</ul>
			</div>
			<!-- 提示结束 --> 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<!-- 表单开始 -->
					<form action="${ctxa}/trade/tradeReturnOrder/handle2.do" class="cmxform form-horizontal adminex-form" id="inputForm" method="post">
						<input type="hidden" name="returnOrderId" value="${tradeReturnOrder.returnOrderId}">
						<input type="hidden" name="stat" value="${stat}">
						<!-- 买家退货申请开始 -->
						<div>
							<h4 style="display: inline-block;">${fns:fy('买家')}${type eq 1?refund_goods:refund }${fns:fy('申请')}</h4>
						</div>
						<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('申请时间')}&nbsp;:</label>
							<div class="col-sm-5">
								<small><fmt:formatDate value="${tradeReturnOrder.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('申请人(买家)')}&nbsp;:</label>
							<div class="col-sm-5">
								<small name="memberUser.loginName">${tradeReturnOrder.userMain.loginName}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('订单编号')} &nbsp;:</label>
							<div class="col-sm-5">
								<a href="${ctxa}/trade/tradeOrder/tradeOrderDetail.do?orderId=${tradeReturnOrder.orderId}"><small>${tradeReturnOrder.orderId}</small></a>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('商品名称')}&nbsp;:</label>
							<div class="col-sm-10">
								<small>${tradeReturnOrder.tradeOrderItem.name}</small>
								<a href="${ctxf}/detail/${tradeReturnOrder.tradeOrderItem.PId}.htm" target="_blank">${fns:fy('查看')}</a>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('退款金额')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>
									<c:if test="${not empty tradeReturnOrder.returnMoney}">
										${fns:fy('￥')}${tradeReturnOrder.returnMoney}
									</c:if>
								</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('退还佣金')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${tradeReturnOrder.returnCommission} = ${tradeReturnOrder.tradeOrderItem.price} * <fmt:formatNumber value="${tradeReturnOrder.tradeOrderItem.commissionRatio}" type="percent"/></small>
							</div>
						</div>
						<c:if test="${type==1}">
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('退货数量')}&nbsp;:</label>
								<div class="col-sm-5">
									<small>${tradeReturnOrder.returnCount}</small>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${type eq '1'?refund_goods:refund }${fns:fy('原因')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${fns:getDictLabel(tradeReturnOrder.returnReason, 'trade_return_reason', '')}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${type eq '1'?refund_goods:refund }${fns:fy('说明')}&nbsp;:</label>
							<div class="col-sm-5">
								<small style="word-break: break-all;">${tradeReturnOrder.returnExplain}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('凭证上传')} &nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${tradeReturnOrder.tradeReturnOrderVoucherList}" var="voucher">
									<a target="_blank" href="${ctxfs}${voucher.path}" >
										<img alt="" src="${ctxfs}${voucher.path}@40x40" width="40px;" height="40px;">
									</a>
								</c:forEach>
							</div>
						</div>
						<!-- 买家退货申请结束  -->
						
						<!-- 商家退货处理开始  -->
						<c:set var="merchant_returns" value ="${fns:fy('商家退货')}"></c:set > 
						<c:set var="merchant_refund" value ="${fns:fy('商家退款')}"></c:set > 
						<h4>${type eq 1?merchant_returns:merchant_refund }${fns:fy('处理')}</h4>
						<div class="form-group"  style="border-top:1px solid #eff2f7;padding-top:10px;">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('商家名称')}&nbsp;:</label>
							<div class="col-sm-5">
								<small>${tradeReturnOrder.tradeOrder.BName}</small>
							</div>
						</div>
						<c:if test="${type eq 1}">
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('是否弃货')}&nbsp;:</label>
								<div class="col-sm-5">
									<small>${fns:getDictLabel(tradeReturnOrder.isJettison, 'yes_no', '')}</small>
								</div>
							</div>
						</c:if>
						<c:if test="${tradeReturnOrder.businessHandle != '0' }">
							<%-- 卖家处理后才显示处理时间 --%>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('处理时间')}&nbsp;:</label>
								<div class="col-sm-5">
									<small><fmt:formatDate value="${tradeReturnOrder.businessHandleDate}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
								</div>
							</div>
							<%--买家处理后才显示处理备注  --%>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('处理备注')}&nbsp;:</label>
								<div class="col-sm-5" style="word-break: break-all;">
									<small>${tradeReturnOrder.businessHandleRemarks}</small>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('审核结果')} &nbsp;:</label>
							<div class="col-sm-5">
								<small>${fns:getDictLabel(tradeReturnOrder.businessHandle, 'trade_business_handle_status', '')}</small>
							</div>
						</div>
						<!-- 商家退货处理结束 -->
						
						<!-- 订单支付信息开始  -->
						<%--平台未处理时显示订单支付信息 --%>
						<c:if test="${tradeReturnOrder.systemHandle == '0'}">
							<h4>${fns:fy('订单支付信息')}</h4>
							<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('支付方式')}&nbsp;:</label>
								<div class="col-sm-5">
									<small>${tradeReturnOrder.tradeOrder.paymentMethodName}</small>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('在线支付金额')} &nbsp;:</label>
								<div class="col-sm-5">
									<small>
										<c:if test="${not empty tradeReturnOrder.tradeOrder.onlinePayMoney}">
											${fns:fy('￥')}${tradeReturnOrder.tradeOrder.onlinePayMoney}
										</c:if>
									</small>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('订单总额')}&nbsp;:</label>
								<div class="col-sm-5">
									<small>
										<c:if test="${not empty tradeReturnOrder.tradeOrder.amountPaid}">
											${fns:fy('￥')}${tradeReturnOrder.tradeOrder.amountPaid}
										</c:if>
									</small>
								</div>
							</div>
							<%-- <shiro:hasPermission name="trade:tradeReturnOrder:auth">
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">微信退款&nbsp;:</label>
								<div class="col-sm-5">
									<div>
										<a href="JavaScript:void(0);" id="wxpayRefundBtn" class="btn btn-warning btn-xs m-r-5">微信退款</a>
										<a href="JavaScript:void(0);" class="btn btn-info btn-xs m-r-5" id="wxpayRefundQueryBtn">退款查询</a>
									</div>
									<small style="color:#49b6d6;">点击"退款查询",请确认在线退款成功后再点击下方的"确认提交"按钮。</small>
								</div>
							</div>
							</shiro:hasPermission> --%>
						</c:if>
						<!-- 订单支付信息结束  -->
						
						<!-- 平台退款处理开始  -->
						<%-- 平台处理后显示平台退款处理信息 --%>
						<c:if test="${tradeReturnOrder.systemHandle !=0 }">
						<h4>${fns:fy('平台退款处理')}</h4>
						<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('处理时间')}&nbsp;:</label>
							<div class="col-sm-5">
								<small><fmt:formatDate value="${tradeReturnOrder.systemAgreeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('处理备注')}&nbsp;:</label>
							<div class="col-sm-5">
								<small style="word-break: break-all;">${tradeReturnOrder.systemHandleRemarks}</small>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('审核结果')} &nbsp;:</label>
							<div class="col-sm-5">
								<small>${fns:getDictLabel(tradeReturnOrder.systemHandle, 'trade_system_handle_status', '')}</small>
							</div>
						</div>
						</c:if>
						<!-- 平台退款处理结束  -->
						
						<!-- 退款详细开始  -->
						<%-- 平台处理后显示平台退款处理信息 --%>
						<c:if test="${tradeReturnOrder.systemHandle ==1 }">
							<h4>${fns:fy('退款详请')}</h4>
							<div class="form-group" style="border-top:1px solid #eff2f7;padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('支付方式')} &nbsp;:</label>
								<div class="col-sm-5">
									<small>${tradeReturnOrder.settlementPayWay.name}</small>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('在线退款金额')}&nbsp;:</label>
								<div class="col-sm-5">
									<small>
										<c:if test="${not empty tradeReturnOrder.onlineReturnMoney}">
											${fns:fy('￥')}${tradeReturnOrder.onlineReturnMoney}
										</c:if>
									</small>
								</div>
							</div>
						</c:if>
						<!-- 退款详细处理结束  -->
						<!-- 平台退款审核开始  -->
						<%-- 平台未处理时显示平台退款审核信息  --%>
						<c:if test="${stat == '1' && tradeReturnOrder.systemHandle == '0'}">
							<h4>${fns:fy('平台退款审核')}</h4>
							<div class="form-group"  style="border-top:1px solid #eff2f7;padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('在线退款金额')}&nbsp;:</label>
								<div class="col-sm-5">
									<input type="text" name="onlineReturnMoney" class="form-control input-sm" maxlength="9"/>
								</div>
								<div class="col-sm-5">
									<p class="help-block">${fns:fy('点击“审核成功”时，请输入退款金额')}<p>
								</div>
							</div>
							<div class="form-group"  style="padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('备注信息')}&nbsp;:</label>
								<div class="col-sm-5">
									<textarea id="adminMessage" name=systemHandleRemarks class="form-control" rows="4">${tradeReturnOrder.systemHandleRemarks}</textarea>
								</div>
							</div>
							<shiro:hasPermission name="trade:tradeReturnOrder:auth">
							<div class="form-group"  style="border-top:1px solid #eff2f7;padding-top:10px;">
								<label class="control-label col-sm-2" for="inputSuccess"></label>
								<input type="hidden" class="buttonState" name="buttonState">
								<button type="button" class="btn btn-danger authError" attrr="1">
									<i class="fa fa-check"></i> ${fns:fy('审核失败')}
								</button>
								<button type="button" class="btn btn-info authOk" attrr="2">
									<i class="fa fa-check"></i> ${fns:fy('审核成功')}
								</button>
								<c:if test="${tradeReturnOrder.tradeOrder.needPay eq '1'}">
									<button type="button" class="btn btn-info authOkAndMoney" attrr="3">
										<i class="fa fa-check"></i> ${fns:fy('审核成功并退款')}
									</button>
								</c:if>
							</div>
							</shiro:hasPermission>
						</c:if>
						<!-- 平台退款审核结束   -->
					</form>
					<!-- 表单结束  -->
				</div>
			</div>
		</div>
		<!-- panel-body结束  -->
	</section>
	<!-- panel 结束 -->
</body>
</html>