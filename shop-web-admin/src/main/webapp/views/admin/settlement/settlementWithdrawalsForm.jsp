<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>提现管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementWithdrawalsForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty settlementWithdrawals.withdrawalsId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}提现</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/settlement/settlementWithdrawals/list.do"> <i class="fa fa-user"></i> 提现列表</a></li>
				<shiro:hasPermission name="settlement:settlementWithdrawals:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 提现${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>提现管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/settlement/settlementWithdrawals/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="withdrawalsId" value="${settlementWithdrawals.withdrawalsId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="withdrawalsId"  maxlength="19" class="form-control input-sm" value="${settlementWithdrawals.withdrawalsId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 提现编号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="rechargeNumber"  maxlength="64" class="form-control input-sm" value="${settlementWithdrawals.rechargeNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写提现编号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId"  maxlength="19" class="form-control input-sm" value="${settlementWithdrawals.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写会员id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 提现金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="money"  maxlength="12" class="form-control input-sm" value="${settlementWithdrawals.money}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写提现金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 收款账号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="receivablesNumber"  maxlength="19" class="form-control input-sm" value="${settlementWithdrawals.receivablesNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写收款账号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 收款方式&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="payWayId"  maxlength="19" class="form-control input-sm" value="${settlementWithdrawals.payWayId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写收款方式<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 提现状态&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('settlement_withdrawals_status')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="status" value="${item.value}" ${item.value==settlementWithdrawals.status?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写提现状态<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 交易号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="transactionNumber"  maxlength="64" class="form-control input-sm" value="${settlementWithdrawals.transactionNumber}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写交易号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 提现终端&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('settlement_terminal_type')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="payTerminal" value="${item.value}" ${item.value==settlementWithdrawals.payTerminal?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写提现终端<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 申请时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${settlementWithdrawals.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写申请时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 支付时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="payTime" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${settlementWithdrawals.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写支付时间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 管理员&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="adminId"  maxlength="19" class="form-control input-sm" value="${settlementWithdrawals.adminId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写管理员<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 拒绝提现理由&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="rejectionReason"  maxlength="512" class="form-control input-sm" value="${settlementWithdrawals.rejectionReason}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写拒绝提现理由<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 开户名&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="accountName"  maxlength="64" class="form-control input-sm" value="${settlementWithdrawals.accountName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写开户名<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="settlement:settlementWithdrawals:edit">
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