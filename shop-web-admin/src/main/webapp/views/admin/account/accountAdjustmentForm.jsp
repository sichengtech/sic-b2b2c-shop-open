<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('平台调账管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/account/accountAdjustmentForm.js"></script>
<style type="text/css">
	.accountP span {text-align: center; display: inline-block;}
</style>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty accountPlatform.apId?true:false}"></c:set >
			<h4 class="title">${fns:fy('平台调账')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/account/accountPlatform/list.do"> <i class="fa fa-user"></i> ${fns:fy('平台调账')}</a></li>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('平台调账，最少选择一个操作账户')}</li>
					<li>${fns:fy('每一个账户有二种类型，需要选择一种操作账户类型')}</li>
					<li>${fns:fy('选择一种操作账户类型，要添加操作的金额')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/account/accountAdjustmentForm/edit2.do" method="post">
						<div class="form-group accountAll">
							<label class="control-label col-sm-1" for="inputSuccess" style="padding-top: 32px;"><font color="red">*</font> ${fns:fy('操作账户1')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group" style="padding-bottom: 5px;">
									<span class="input-group-addon">
										<input type="hidden" class="oldAccount" value="1">
						            	<input type="radio" name="account1" aria-label="Radio button for following text input" value="1"/>
						          	</span>
						          	<select class="form-control m-bot15 input-sm account-type">
										<option value="">--${fns:fy('请选择平台账户')}--</option>
										<c:forEach items="${list1}" var="accountPlatform">
											<option value="${accountPlatform.apId}">${fns:fy('平台账户')}--${fns:getDictLabel(accountPlatform.accountType, 'account_platform_type', '')}</option>
										</c:forEach>
									</select>
								</div>
								<div class="input-group" style="padding-bottom: 5px;">
									<span class="input-group-addon">
										<input type="hidden" class="oldAccount" value="2">
						            	<input type="radio" name="account1" aria-label="Radio button for following text input" value="2">
						          	</span>
						          	<select class="form-control m-bot15 input-sm account-type">
										<option value="">--${fns:fy('请选择商家账户')}--</option>
										<c:forEach items="${list2}" var="accountUser">
											<option value="${accountUser.auId}">${fns:fy('商家账户')}--${accountUser.userMain.loginName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<label class="control-label col-sm-1" for="inputSuccess"><font color="red">*</font> ${fns:fy('操作')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group m-bot15">
									<div class="input-group-btn handle-item">
										<input class="form-control input-sm" type="hidden" name="operation1" value="1" optype="operation"> 
										<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
											<span class="operation">${fns:fy('增加')}</span> 
										 	<span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
											<li><a href="javascript:void(0);" cvalue="1">${fns:fy('增加')}</a></li>
											<li><a href="javascript:void(0);" cvalue="0">${fns:fy('减少')}</a></li>
										</ul>
									</div>
									<input type="text" class="form-control sel_req" name="money1" value="" maxlength="8">
									<div class="input-group-addon">${fns:fy('元')}</div>
								</div>
							</div>
						</div>
						
						<div class="form-group accountAll">
							<label class="control-label col-sm-1" for="inputSuccess" style="padding-top: 32px;"><font color="red">*</font> ${fns:fy('操作账户2')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group" style="padding-bottom: 5px;">
									<span class="input-group-addon">
										<input type="hidden" class="oldAccount" value="1">
						            	<input type="radio" name="account2" aria-label="Radio button for following text input" value="1">
						          	</span>
						          	<select class="form-control m-bot15 input-sm account-type">
										<option value="">--${fns:fy('请选择平台账户')}--</option>
										<c:forEach items="${list1}" var="accountPlatform">
											<option value="${accountPlatform.apId}">${fns:fy('平台账户')}--${fns:getDictLabel(accountPlatform.accountType, 'account_platform_type', '')}</option>
										</c:forEach>
									</select>
								</div>
								<div class="input-group" style="padding-bottom: 5px;">
									<span class="input-group-addon">
										<input type="hidden" class="oldAccount" value="2">
						            	<input type="radio" name="account2" aria-label="Radio button for following text input" value="2">
						          	</span>
						          	<select class="form-control m-bot15 input-sm account-type">
										<option value="">--${fns:fy('请选择商家账户')}--</option>
										<c:forEach items="${list2}" var="accountUser">
											<option value="${accountUser.auId}">${fns:fy('商家账户')}--${accountUser.userMain.loginName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<label class="control-label col-sm-1" for="inputSuccess"><font color="red">*</font> ${fns:fy('操作')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group m-bot15">
									<div class="input-group-btn handle-item">
										<input class="form-control input-sm" type="hidden" name="operation2" value="1" optype="operation"> 
										<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
											<span class="operation">${fns:fy('增加')}</span> 
										 	<span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
											<li><a href="javascript:void(0);" cvalue="1">${fns:fy('增加')}</a></li>
											<li><a href="javascript:void(0);" cvalue="0">${fns:fy('减少')}</a></li>
										</ul>
									</div>
									<input type="text" class="form-control sel_req" name="money2" value="" maxlength="8">
									<div class="input-group-addon">${fns:fy('元')}</div>
								</div>
							</div>
						</div>
						
						<div class="form-group accountAll">
							<label class="control-label col-sm-1" for="inputSuccess" style="padding-top: 32px;"><font color="red">*</font> ${fns:fy('操作账户3')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group" style="padding-bottom: 5px;">
									<span class="input-group-addon">
										<input type="hidden" class="oldAccount" value="1">
						            	<input type="radio" name="account3" aria-label="Radio button for following text input" value="1">
						          	</span>
						          	<select class="form-control m-bot15 input-sm account-type">
										<option value="">--${fns:fy('请选择平台账户')}--</option>
										<c:forEach items="${list1}" var="accountPlatform">
											<option value="${accountPlatform.apId}">${fns:fy('平台账户')}--${fns:getDictLabel(accountPlatform.accountType, 'account_platform_type', '')}</option>
										</c:forEach>
									</select>
								</div>
								<div class="input-group" style="padding-bottom: 5px;">
									<span class="input-group-addon">
										<input type="hidden" class="oldAccount" value="2">
						            	<input type="radio" name="account3" aria-label="Radio button for following text input" value="2">
						          	</span>
						          	<select class="form-control m-bot15 input-sm account-type">
										<option value="">--${fns:fy('请选择商家账户')}--</option>
										<c:forEach items="${list2}" var="accountUser">
											<option value="${accountUser.auId}">${fns:fy('商家账户')}--${accountUser.userMain.loginName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<label class="control-label col-sm-1" for="inputSuccess"><font color="red">*</font> ${fns:fy('操作')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group m-bot15">
									<div class="input-group-btn handle-item">
										<input class="form-control input-sm" type="hidden" name="operation3" value="1" optype="operation"> 
										<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
											<span class="operation">${fns:fy('增加')}</span> 
										 	<span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
											<li><a href="javascript:void(0);" cvalue="1">${fns:fy('增加')}</a></li>
											<li><a href="javascript:void(0);" cvalue="0">${fns:fy('减少')}</a></li>
										</ul>
									</div>
									<input type="text" class="form-control sel_req" name="money3" value="" maxlength="8">
									<div class="input-group-addon">${fns:fy('元')}</div>
								</div>
							</div>
							
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-1" for="inputSuccess"><font color="red">*</font> ${fns:fy('调账原因')}&nbsp;:</label>
							<div class="col-sm-11">
								<textarea rows="3" class="form-control" name="cause" maxlength="200"></textarea>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-5" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="account:accountAdjustmentForm:edit">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy('保存')}
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
	<script type="text/template" id="handle_account" info="${fns:fy('操作账户信息模板')}">
		<p class="accountP" style="margin-left: 40px;line-height: 30px;">
			<span style="width:80px;">${fns:fy('操作账户')}{{d.account_index}}</span>
			<span style="width:200px;">{{d.select_text}}</span>
			<span style="width:180px;">{{d.input_text}}{{d.input_value}}${fns:fy('元')}</span>
		</p>
	</script>
</body>
</html>