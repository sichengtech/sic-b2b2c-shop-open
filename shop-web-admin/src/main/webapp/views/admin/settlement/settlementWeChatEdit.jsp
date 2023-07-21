<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>支付方式设置</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>
<!-- 业务js --> 
<script type="text/javascript" src="${ctx}/views/admin/settlement/settlementPayWayEdit.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<!-- panel-head开始 -->
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">支付方式设置</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctx}/views/admin/settlement/settlementPayWayList.jsp"> <i class="fa fa-home"></i>支付方式列表</a></li>
				<li class="active"><a href="${ctx}/views/admin/settlement/settlementPayWayEdit.jsp"> <i class="fa fa-user"></i>支付方式设置</a></li>
			</ul>
		</header>
		<!-- panel-head结束 -->
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>xxxxxxxxxxxxxxxxxxxxxxx</li>
					<li>xxxxxxxxxxxxxxxxxxxxxxx</li>
					<li>xxxxxxxxxxxxxxxxxxxxxxx</li>
				</ul>
			</div>
			<!-- 提示 end --> 
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<!-- 微信支付方式表单开始 -->
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 支付方式&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" disabled type="text" value="微信[移动客户端]"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">此项不可编辑<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商户公众号APPID&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="请输入商户公众号APPID" value=""> 
								<label for="firstname" class="error">必填项，请输入商户公众号APPID.</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，此账号的微信公众号的appID，请谨慎填写<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 商户号MCHID&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="请输入商户号MCHID" value="2b7i5fm802p0183hs6lx90qfk94ff8lm"> 
								<label for="firstname" class="error">必填项，请输入商户号MCHID.</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项,请输入商户号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">商户支付密钥&nbsp;: </label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="商户支付密钥" value="2088001525694587"> 
								<label for="firstname" class="error">必填项，请输入商户支付密钥.</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项,请输入商户支付密钥<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">状态&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" checked data-size="small" style="display: none" data-on-text="开启" data-off-text="关闭"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">设置该账号是否使用，请谨慎选择<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button class="btn btn-primary">
									<i class="fa fa-mail-reply"></i> 返回
								</button>
								<button class="btn btn-info">
									 <i class="fa fa-check"></i> 保存
								</button>
							</div>
						</div>
					</form>
					<!-- 微信支付方式表单结束 -->
				</div>
			</div>
		</div>
		<!-- panel-body结束 -->
	</section>
	<!-- panel 结束 -->
	<%@ include file="../include/head_bootstrap-switch.jsp"%>
</body>
</html>