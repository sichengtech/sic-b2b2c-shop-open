<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>充值管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementRechargeForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty settlementRecharge.rechargeId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}充值</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/settlement/settlementRecharge/list.do"> <i class="fa fa-user"></i> 充值列表</a></li>
				<shiro:hasPermission name="settlement:settlementRecharge:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 充值${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>充值管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/settlement/settlementRecharge/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="rechargeId" value="${settlementRecharge.rechargeId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 充值ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="rechargeId"  maxlength="19" class="form-control input-sm" value="${settlementRecharge.rechargeId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写充值ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 充值编号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="rechargeNumber"  maxlength="64" class="form-control input-sm" value="${settlementRecharge.rechargeNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写充值编号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId"  maxlength="19" class="form-control input-sm" value="${settlementRecharge.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写会员id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 充值金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="rechargeMoney"  maxlength="12" class="form-control input-sm" value="${settlementRecharge.rechargeMoney}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写充值金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 充值时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="rechargeTime" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${settlementRecharge.rechargeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写充值时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 支付时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="payDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${settlementRecharge.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写支付时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 支付方式&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="payWayId"  maxlength="19" class="form-control input-sm" value="${settlementRecharge.payWayId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写支付方式<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 支付状态&nbsp;:</label>
							<div class="col-sm-5">
								<select name="staus" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('settlement_pay_status')}" var="item">
									<option value="${item.value}" ${item.value==settlementRecharge.staus?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写支付状态<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 支付终端&nbsp;:</label>
							<div class="col-sm-5">
								<select name="payTerminal" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('settlement_terminal_type')}" var="item">
									<option value="${item.value}" ${item.value==settlementRecharge.payTerminal?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写支付终端<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 交易号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="tradeNumber"  maxlength="64" class="form-control input-sm" value="${settlementRecharge.tradeNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写交易号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 管理员&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="adminId"  maxlength="19" class="form-control input-sm" value="${settlementRecharge.adminId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写管理员<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="settlement:settlementRecharge:edit">
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