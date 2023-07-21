<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>第三方账户资金流水管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountThirdpartySnForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty accountThirdpartySn.id?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}第三方账户资金流水</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/account/accountThirdpartySn/list.do"> <i class="fa fa-user"></i> 第三方账户资金流水列表</a></li>
				<shiro:hasPermission name="account:accountThirdpartySn:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 第三方账户资金流水${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>第三方账户资金流水管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/account/accountThirdpartySn/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${accountThirdpartySn.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 流水号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="serialNumber"  maxlength="64" class="form-control input-sm" value="${accountThirdpartySn.serialNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写流水号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 资金流类型（1.付款、2.提现、3.充值、4.退款）&nbsp;:</label>
							<div class="col-sm-5">
								<select name="moneyFlowType" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('account_thirdparty_sn')}" var="item">
									<option value="${item.value}" ${item.value==accountThirdpartySn.moneyFlowType?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写资金流类型（1.付款、2.提现、3.充值、4.退款）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 交易金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="money"  maxlength="12" class="form-control input-sm" value="${accountThirdpartySn.money}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写交易金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 交易渠道&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="payWayId"  maxlength="19" class="form-control input-sm" value="${accountThirdpartySn.payWayId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写交易渠道<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 支付方式名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="payWayName"  maxlength="64" class="form-control input-sm" value="${accountThirdpartySn.payWayName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写支付方式名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 外部交易记录号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="outerTradeNo"  maxlength="64" class="form-control input-sm" value="${accountThirdpartySn.outerTradeNo}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写外部交易记录号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 交易备注&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="tradeRemarks"  maxlength="255" class="form-control input-sm" value="${accountThirdpartySn.tradeRemarks}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写交易备注<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="account:accountThirdpartySn:edit">
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