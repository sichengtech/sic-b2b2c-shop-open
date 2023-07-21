<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>token管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/sys/sysTokenForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty sysToken.TId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}token</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/sys/sysToken/list.do"> <i class="fa fa-user"></i> token列表</a></li>
				<shiro:hasPermission name="sys:sysToken:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> token${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>token管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/sys/sysToken/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="tId" value="${sysToken.TId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="tId"  maxlength="19" class="form-control input-sm" value="${sysToken.TId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 用户id(公用上传不需要存值；激活邮箱需要存值)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="userId"  maxlength="19" class="form-control input-sm" value="${sysToken.userId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写用户id(公用上传不需要存值；激活邮箱需要存值)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 令牌&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="token"  maxlength="64" class="form-control input-sm" value="${sysToken.token}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写令牌<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 业务类型：10.公用上传20.用户激活邮箱&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="type"  maxlength="4" class="form-control input-sm" value="${sysToken.type}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写业务类型：10.公用上传20.用户激活邮箱<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 状态：0.失效1.有效&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="status"  maxlength="1" class="form-control input-sm" value="${sysToken.status}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写状态：0.失效1.有效<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="sys:sysToken:edit">
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