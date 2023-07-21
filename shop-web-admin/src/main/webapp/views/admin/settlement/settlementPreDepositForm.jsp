<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>预存款明细管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementPreDepositForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">${not empty settlementPreDeposit.preDepositId?'修改':'添加'}预存款明细</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/settlement/settlementPreDeposit/list.do"> <i class="fa fa-user"></i> 预存款明细列表</a></li>
				<shiro:hasPermission name="settlement:settlementPreDeposit:edit"><li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 预存款明细添加</a></li></shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>预存款明细管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/settlement/settlementPreDeposit/${not empty settlementPreDeposit.preDepositId?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="preDepositId" value="${settlementPreDeposit.preDepositId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员id(会员表id)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="uId" maxlength="19" class="form-control input-sm" value="${settlementPreDeposit.UId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写会员id(会员表id)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 可用金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="availableMoney" class="form-control input-sm" value="${settlementPreDeposit.availableMoney}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写可用金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 冻结金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="frozenMoney" class="form-control input-sm" value="${settlementPreDeposit.frozenMoney}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写冻结金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 操作金额&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="operationMoney" class="form-control input-sm" value="${settlementPreDeposit.operationMoney}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写操作金额<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 操作描述&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="operationDescribe" maxlength="1024" class="form-control input-sm" value="${settlementPreDeposit.operationDescribe}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写操作描述<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 管理员(管理员表id)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="adminId" maxlength="19" class="form-control input-sm" value="${settlementPreDeposit.adminId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写管理员(管理员表id)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段1&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak1" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak1}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段1<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段2&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak2" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak2}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段2<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段3&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak3" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak3}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段3<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段4&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak4" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak4}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段4<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段5&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak5" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak5}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段5<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段6&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak6" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak6}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段6<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段7&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak7" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak7}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段7<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段8&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak8" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak8}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段8<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段9&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak9" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak9}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段9<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备用字段10&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="bak10" maxlength="64" class="form-control input-sm" value="${settlementPreDeposit.bak10}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备用字段10<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回 
								</button>
								<shiro:hasPermission name="settlement:settlementPreDeposit:edit">
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