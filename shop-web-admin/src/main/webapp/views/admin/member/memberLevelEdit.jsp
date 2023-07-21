<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>编辑等级</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/member/memberLevelEdit.js"></script>
</head>
<body>

	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">编辑等级</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctx}/views/admin/member/memberLevelList.jsp"> <i class="fa fa-user"></i> 会员等级列表</a></li>
				<li class=""><a href="${ctx}/views/admin/member/memberLevelSave.jsp"> <i class="fa fa-user"></i> 新增等级</a></li>
				<li class="active"><a href="javaScript:;"> <i class="fa fa-user"></i> 编辑等级</a></li>
			</ul>
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
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员级别V1&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group"> 
									<input type="text" class="form-control input-sm error" placeholder=""> 
									<div class="input-group-addon">经验值</div>
								</div>
								<label for="firstname" class="error">不能为空</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员级别V2&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group"> 
									<input type="text" class="form-control input-sm error" placeholder=""> 
									<div class="input-group-addon">经验值</div>
								</div>
								<label for="firstname" class="error">不能为空</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员级别V3&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group"> 
									<input type="text" class="form-control input-sm error" placeholder=""> 
									<div class="input-group-addon">经验值</div>
								</div>
								<label for="firstname" class="error">不能为空</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 会员级别V4&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group"> 
									<input type="text" class="form-control input-sm error" placeholder=""> 
									<div class="input-group-addon">经验值</div>
								</div>
								<label for="firstname" class="error">不能为空</label>
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