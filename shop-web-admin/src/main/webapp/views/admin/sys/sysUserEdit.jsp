<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>修改个人信息</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>

<!-- 引入iCheck文件-->
<%@ include file="../include/head_iCheck.jsp"%>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysUserEdit.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<h4 class="title">修改个人信息</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 管理员头像&nbsp;:</label>
							<div class="col-sm-5">
								<input type="file"><a href="javascript:;" class="btn btn-white">删除</a>
							</div>
							<div class="col-sm-5">
								<p class="help-block">管理员头像尺寸要求宽度为120像素、高度为120像素，比例为1:1的图片；支持格式gif，jpg，png。<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 工号&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">工号不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写工号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 姓名&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">姓名不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写姓名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 登录名&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder=""> 
								<label for="firstname" class="error">登录名不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写登录名<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 密码&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" readonly="readonly"> 
								<label for="firstname" class="error">密码不能为空</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请确保两次密码输入一致<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 确认密码&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm error" type="text" placeholder="" readonly="readonly"> 
								<label for="firstname" class="error">两次密码不一致</label>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请确保两次密码输入一致<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">QQ&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder=""> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写QQ<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">邮箱&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder=""> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写邮箱<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">电话&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder=""> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写电话<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">手机&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" placeholder=""> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写手机<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 用户角色&nbsp;:</label>
							<div class="col-sm-5 icheck">
								<div class="square-blue single-row">
									<div class="checkbox">
										<input type="checkbox" checked style="display: none" data-switch-no-init>
										<label>买家管理角色 </label>
									</div>
								</div> 
								<div class="square-blue single-row">
									<div class="checkbox">
										<input type="checkbox" checked style="display: none" data-switch-no-init>
										<label>交易管理角色 </label>
									</div>
								</div> 
								<div class="square-blue single-row">
									<div class="checkbox">
										<input type="checkbox" checked style="display: none" data-switch-no-init>
										<label>会员管理角色 </label>
									</div>
								</div> 
								<div class="square-blue single-row">
									<div class="checkbox">
										<input type="checkbox" checked style="display: none" data-switch-no-init>
										<label>普通用户角色 </label>
									</div>
								</div> 
								<div class="square-blue single-row">
									<div class="checkbox">
										<input type="checkbox" checked style="display: none" data-switch-no-init>
										<label>网站管理角色 </label>
									</div>
								</div> 
								<div class="square-blue single-row">
									<div class="checkbox">
										<input type="checkbox" checked style="display: none" data-switch-no-init>
										<label>超级管理角色 </label>
									</div>
								</div> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">请选择用户的角色<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">是否允许登录&nbsp;:</label>
							<div class="col-sm-5">
							 <input type="checkbox" checked data-size="small" style="display: none" data-on-text="是" data-off-text="否"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">是否允许登录<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 备注&nbsp;:</label>
							<div class="col-sm-5">
								<textarea rows="6" class="form-control"></textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写备注<p>
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
		<!-- panel-body结束 -->
	</section>
	<!-- panel结束 -->
	<%@ include file="../include/head_bootstrap-switch.jsp"%>
</body>
</html>