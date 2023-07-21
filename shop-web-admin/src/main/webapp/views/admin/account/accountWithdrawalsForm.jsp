<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>账户提现管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountWithdrawalsForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty accountWithdrawals.id?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}账户提现</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/account/accountWithdrawals/list.do"> <i class="fa fa-user"></i> 账户提现列表</a></li>
				<shiro:hasPermission name="account:accountWithdrawals:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 账户提现${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>账户提现管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/account/accountWithdrawals/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${accountWithdrawals.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 账户id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="accountId"  maxlength="19" class="form-control input-sm" value="${accountWithdrawals.accountId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写账户id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 类型（1会员提现，2平台提现）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="status"  maxlength="1" class="form-control input-sm" value="${accountWithdrawals.status}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写类型（1会员提现，2平台提现）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 提现金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="money"  maxlength="12" class="form-control input-sm" value="${accountWithdrawals.money}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写提现金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 提现卡号id（account_tied_card(账户绑卡表)）（个人提现有值，平台提现没有值）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="tiedCardId"  maxlength="19" class="form-control input-sm" value="${accountWithdrawals.tiedCardId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写提现卡号id（account_tied_card(账户绑卡表)）（个人提现有值，平台提现没有值）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 审核是否通过（0待审核，1审核同意，2审核失败）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="auditStatus"  maxlength="1" class="form-control input-sm" value="${accountWithdrawals.auditStatus}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写审核是否通过（0待审核，1审核同意，2审核失败）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 审核理由&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="auditOpinion"  maxlength="255" class="form-control input-sm" value="${accountWithdrawals.auditOpinion}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写审核理由<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否支付（0未支付、1已支付）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="isPay"  maxlength="1" class="form-control input-sm" value="${accountWithdrawals.isPay}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否支付（0未支付、1已支付）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 支付时间&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input name="payTime" type="text" readonly="readonly" maxlength="20" class="form-control input-sm J-date-start"
									value="<fmt:formatDate value="${accountWithdrawals.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
									<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
								</div>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写支付时间<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="account:accountWithdrawals:edit">
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