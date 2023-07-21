<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>支付方式管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 业务js -->
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty settlementPayWay.payWayId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}支付方式</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/settlement/settlementPayWay/list.do"> <i class="fa fa-user"></i> 支付方式列表</a></li>
				<shiro:hasPermission name="settlement:settlementPayWay:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 支付方式${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>支付方式管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/settlement/settlementPayWay/other2.do" method="post">
						<input type="hidden" name="payWayId" value="${settlementPayWay.payWayId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 支付方式&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name" readonly="readonly" maxlength="64" class="form-control input-sm" value="${settlementPayWay.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">此项不可编辑<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 状态&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="status" value="1" ${"1"==settlementPayWay.status?"checked":""} 
								data-size="small" style="display: none" data-on-text="开启" data-off-text="关闭"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">设置该账号是否使用，请谨慎选择<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="settlement:settlementPayWay:edit">
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