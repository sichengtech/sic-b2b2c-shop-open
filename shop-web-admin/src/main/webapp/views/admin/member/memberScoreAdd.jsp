<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>积分增减</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<meta name="menu" content="2"/><%--表示使用N号二级菜单 --%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/score/memberScoreAdd.js"></script>
<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>
</head>
<body>

	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">积分增减</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/member/memberUser/list.do"> <i class="fa fa-home"></i> 会员列表</a></li>
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 积分增减</a></li>
			</ul>
		</header>
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in">
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
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/member/memberUser/saveScore.do" method="post">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 会员名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="loginName" readonly="readonly" maxlength="64" class="form-control input-sm" value="${memberUser.loginName}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请使用6-20个中、英文、数字及“-”符号，且不能全为数字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>增减类型&nbsp;:</label>
							<div class="col-sm-5 icheck">
							 <div class="col-sm-4 icheck">
								<div class="square-blue single-row">
									<div class="radio ">
										<input tabindex="3" type="radio" name="change" style="display: none" data-switch-no-init>
										<label>增加</label>
									</div>
								</div>
							 </div>
							 <div class="col-sm-4 icheck">
								 <div class="square-blue single-row">
									<div class="radio ">
										<input tabindex="3" type="radio" name="change" style="display: none" data-switch-no-init>
										<label>减少</label>
									</div>
								</div>
							 </div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请输入增减类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 积分值&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="point" readonly="readonly" maxlength="64" class="form-control input-sm" value="${memberUser.point}"/> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写要调整的积分值<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 描述&nbsp;:</label>
							<div class="col-sm-5">
								<textarea rows="6" class="form-control"></textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写积分变更的描述<p>
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