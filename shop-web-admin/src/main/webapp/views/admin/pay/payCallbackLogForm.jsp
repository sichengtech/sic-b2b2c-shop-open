<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>第三方支付回调日志管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/pay/payCallbackLogForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty payCallbackLog.id?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}第三方支付回调日志</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/pay/payCallbackLog/list.do"> <i class="fa fa-user"></i> 第三方支付回调日志列表</a></li>
				<shiro:hasPermission name="pay:payCallbackLog:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 第三方支付回调日志${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>第三方支付回调日志管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/pay/payCallbackLog/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${payCallbackLog.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 支付方式id&nbsp;:</label>
							<div class="col-sm-5">
								<select name="payWayId" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('')}" var="item">
									<option value="${item.value}" ${item.value==payCallbackLog.payWayId?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写支付方式id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 支付方式名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="payWayName"  maxlength="64" class="form-control input-sm" value="${payCallbackLog.payWayName}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写支付方式名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 支付回调信息(大字段)&nbsp;:</label>
							<div class="col-sm-5">
								<textarea name="callbackInfo" rows="6" class="form-control"  >${payCallbackLog.callbackInfo}</textarea>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写支付回调信息(大字段)<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="pay:payCallbackLog:edit">
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