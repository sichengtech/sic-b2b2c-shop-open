<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('订单收款')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="4"/><!-- 表示使用N号二级菜单 -->
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/trade/tradeOrderEdit.js"></script>
</head>
<body>
	<!-- panel 开始 -->
	<section class="panel">
		<!-- panel-head开始 -->
		<header class="panel-heading custom-tab tab-right">
			<h4 class="title">${fns:fy('订单收款')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/trade/tradeOrder/list.do"> <i class="fa fa-home"></i>${fns:fy('订单列表')}</a></li>
				<li class="active"><a href="javascript:;"> <i class="fa fa-user"></i>${fns:fy('订单收款')}</a></li>
			</ul>

		</header>
		<!-- panel-head结束 -->
		<!-- panel-body开始 -->
		<div class="panel-body">
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<!-- 表单开始 -->
					<sys:message content="${message}"/>
					<form action="${ctxa}/trade/tradeOrder/receivePayment2.do" class="cmxform form-horizontal adminex-form" id="inputForm" method="post">
						<input type="hidden" name="orderId" value="${tradeOrder.orderId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('订单编号')}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" disabled type="text" name="orderId" value="${tradeOrder.orderId}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('此项不可编辑')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('支付单号')}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" disabled type="text" name="payOrderNumber" value="${tradeOrder.payOrderNumber}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('此项不可编辑')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('订单金额')}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" disabled type="text" name="amountPaid" value="${tradeOrder.amountPaid}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('此项不可编辑')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('支付时间')} &nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="payOrderTime" value="<fmt:formatDate value="${tradeOrder.payOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" placeholder="${fns:fy('请选择付款时间')}" format="yyyy-MM-dd HH:mm:ss" class="form-control" >
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请选择付款时间。')}<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('付款方式')}&nbsp;: </label>
							<div class="col-sm-5">
								<select class="form-control input-sm" id="" name="paymentMethodId">
									<option value="" class="firstOption">${fns:fy('全部')}</option> 
									 <c:forEach items="${payWays}" var="way">
										<option value="${way.payWayId}">${way.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('选填项，请选择付款方式。')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('付款终端')}&nbsp;: </label>
							<div class="col-sm-5">
								<select class="form-control input-sm" id="" name="">
									<option value="" class="firstOption">${fns:fy('全部')}</option> 
									<c:forEach items="${fns:getDictList('settlement_terminal_type')}" var="tt">
									<option ${tt.value eq tradeOrder.sources?"selected":""} value="${tt.value}">${tt.label}</option>
								 	</c:forEach> 
								</select>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('选填项，请选择付款终端。')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('第三方付款平台交易号')}&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" value="${tradeOrder.thirdPayOrderNumber}" name="thirdPayOrderNumber"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('选填项,请输入第三方付款平台交易号')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i>  ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="trade:tradeOrder:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy('保存')} 
								</button>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
					<!-- 表单结束 -->
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel 结束 -->
</html>