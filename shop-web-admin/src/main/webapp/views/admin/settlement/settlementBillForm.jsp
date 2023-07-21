<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>账单管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementBillForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty settlementBill.billId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}账单</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/settlement/settlementBill/list.do"> <i class="fa fa-user"></i> 账单列表</a></li>
				<shiro:hasPermission name="settlement:settlementBill:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 账单${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>账单管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/settlement/settlementBill/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="billId" value="${settlementBill.billId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 账单编号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="billId"  maxlength="19" class="form-control input-sm" value="${settlementBill.billId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写账单编号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商家id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerId"  maxlength="19" class="form-control input-sm" value="${settlementBill.sellerId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写商家id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 出账日期&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="balanceDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${settlementBill.balanceDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写出账日期<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 本期应结(元)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="billAmount"  maxlength="12" class="form-control input-sm" value="${settlementBill.billAmount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写本期应结(元)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 账单状态&nbsp;:</label>
							<div class="col-sm-5">
								<select name="status" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('settlement_bill_status')}" var="item">
									<option value="${item.value}" ${item.value==settlementBill.status?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写账单状态<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 订单总金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="orderAmount"  maxlength="12" class="form-control input-sm" value="${settlementBill.orderAmount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写订单总金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 平台分佣金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="platformCommission"  maxlength="12" class="form-control input-sm" value="${settlementBill.platformCommission}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写平台分佣金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 退还佣金&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="returnCommission"  maxlength="12" class="form-control input-sm" value="${settlementBill.returnCommission}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写退还佣金<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 退单金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="refund"  maxlength="12" class="form-control input-sm" value="${settlementBill.refund}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写退单金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 店铺推广费用（元）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="promotionExpenses"  maxlength="12" class="form-control input-sm" value="${settlementBill.promotionExpenses}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写店铺推广费用（元）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 红包&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="redPackets"  maxlength="12" class="form-control input-sm" value="${settlementBill.redPackets}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写红包<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 退还红包&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="returnRedPackets"  maxlength="12" class="form-control input-sm" value="${settlementBill.returnRedPackets}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写退还红包<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 预定订单未退定金(元)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="deposit"  maxlength="12" class="form-control input-sm" value="${settlementBill.deposit}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写预定订单未退定金(元)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 账单开始时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${settlementBill.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写账单开始时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 账单结束时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="endTime" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${settlementBill.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写账单结束时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 付款时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="payDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${settlementBill.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写付款时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 付款人&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="payPerson"  maxlength="64" class="form-control input-sm" value="${settlementBill.payPerson}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写付款人<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 付款备注&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="payRemarks"  maxlength="1024" class="form-control input-sm" value="${settlementBill.payRemarks}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写付款备注<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="settlement:settlementBill:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存
								</button>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
</body>
</html>