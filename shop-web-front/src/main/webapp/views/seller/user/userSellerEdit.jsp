<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>编辑管理员账号(无用)</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<style type="text/css">
	.sui-msg.msg-block{margin:10px!important;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="goods-list">
			<dl class="box store-set">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>编辑管理员账号</span>
					</h4>
					<ul class="sui-breadcrumb">
						<li>当前位置:</li>
						<li>
							<a href="#">首页</a></li><li><a href="#">账号</a>
						</li>
						<li class="active">编辑管理员账号</li>
					</ul>
				</dt>

				<form class="sui-form form-inline" method="post">
					<dd>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>账号名称：</label>
							<input type="text" class="input-xlarge input-error" value="shopnc_hbj_1">
							<div class="sui-msg msg-error help-inline">
								<div class="msg-con"><span>不能为空</span></div>
								<i class="msg-icon"></i>
							</div>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">请输入账号名称,长度不要超过32个字符。</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>账号密码：</label>
							<input type="text" class="input-xlarge">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">请输入账号密码,长度不要超过32个字符。</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>重复密码：</label>
							<input type="text" class="input-xlarge">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">请输入重复密码,长度不要超过32个字符。</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>邮箱地址码：</label>
							<input type="text" class="input-xlarge" value="wc201245@shopnc.net">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">请输入邮箱地址码,长度不要超过32个字符。</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>手机号码：</label>
							<input type="text" class="input-xlarge" value="13555555555">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">请输入手机号码,长度不要超过32个字符。</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>账号组：</label>
							<span class="sui-dropdown dropdown-bordered select">
								<span class="dropdown-inner">
									<a id="drop12" role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
										<input value="hz" name="city" type="hidden">
										<i class="caret"></i>
										<span>-请选择-</span>
									</a>
									<ul id="menu12" role="menu" aria-labelledby="drop12" class="sui-dropdown-menu">
										<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0);" value="bj">管理员</a></li>
									</ul>
								</span>
							</span>
							<div class="sui-msg msg-error help-inline">
								<div class="msg-con"><span>不能为空（注意：商家账号不显示这一项）</span></div>
								<i class="msg-icon"></i>
							</div>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">请选择账号组</div>
							</div>
						</div>
					</dd>
				</form>
				<div class="clear"></div>
				<div class="text-align pb20">
					<a href="javascript:void(0);" class="sui-btn btn-xlarge">放弃操作</a>
					<a href="javascript:void(0);" class="sui-btn btn-xlarge btn-primary ml20">确认提交</a>
				</div>
			</dl>
		</div>
	</div>
</body>
</html>