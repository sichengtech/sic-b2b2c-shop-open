<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>规则设置</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/score/scoreRuleForm.js"></script>
<!-- 引入iCheck文件 -->
<%@ include file="../include/head_iCheck.jsp"%>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">规则设置</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right"></ul>
		</header>
		<div class="panel-body">

			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示 end -->	 
			 
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 注册&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">该值为大于等于0的数，当会员注册成功后将获得相应的积分<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员每天第一次登录&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">该值为大于等于0的数，当会员每天第一次登录成功后将获得相应的积分<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 订单商品评论&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">该值为大于等于0的数，当会员评论订单成功后将获得相应的积分<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 消费额与赠送积分比例&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group"> 
									<input class="form-control input-sm error" type="text" placeholder="">
									<div class="input-group-addon">%</div>
								</div>
									<label for="firstname" class="error">不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">该值为大于0的数，例:设置为10，表明消费10单位货币赠送1积分<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 每订单最多赠送积分&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">该值为大于等于0的数，填写为0表明不限制最多积分，例:设置为100，表明每订单赠送积分最多为100积分<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button class="btn btn-primary">
									<i class="fa fa-times"></i> 返 回 
								</button>
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> 保 存 
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<!-- panel end -->
</body>
</html>