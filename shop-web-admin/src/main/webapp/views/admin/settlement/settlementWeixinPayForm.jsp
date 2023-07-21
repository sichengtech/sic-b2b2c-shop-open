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
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementAlipayForm.js"></script>
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
					<li>设置支付方式的信息和开关状态。</li>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/settlement/settlementPayWay/weixin2.do" method="post">
						<input type="hidden" name="payWayId" value="${settlementPayWay.payWayId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 支付方式&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${settlementPayWay.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">此项不可编辑<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商户公众号APPID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="k1" readonly="readonly" maxlength="64" class="form-control input-sm" value="${settlementPayWay.k1}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，此账号的微信公众号的appID，请谨慎填写<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商户支付密钥&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="k2"  maxlength="64" class="form-control input-sm" value="${settlementPayWay.k2}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项,请输入商户支付密钥<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商户号MCHID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="k3"  maxlength="64" class="form-control input-sm" value="${settlementPayWay.k3}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项,请输入商户号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 状态&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" name="status" value="1" ${"1"==settlementPayWay.status?"checked":""} 
								data-size="small" style="display: none" data-on-text="开启" data-off-text="关闭"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写状态<p>
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