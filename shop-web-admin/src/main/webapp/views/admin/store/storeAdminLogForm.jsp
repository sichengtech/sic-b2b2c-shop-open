<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>店铺管理员操作日志管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeAdminLogForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeAdminLog.logId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}店铺管理员操作日志</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeAdminLog/list.do"> <i class="fa fa-user"></i> 店铺管理员操作日志列表</a></li>
				<shiro:hasPermission name="store:storeAdminLog:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 店铺管理员操作日志${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>店铺管理员操作日志管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeAdminLog/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="logId" value="${storeAdminLog.logId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="logId"  maxlength="19" class="form-control input-sm" value="${storeAdminLog.logId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 关联(卖家表id)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerId"  maxlength="19" class="form-control input-sm" value="${storeAdminLog.sellerId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写关联(卖家表id)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 日志类型&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="type"  maxlength="1" class="form-control input-sm" value="${storeAdminLog.type}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写日志类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 日志标题（操作菜单）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="title"  maxlength="512" class="form-control input-sm" value="${storeAdminLog.title}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写日志标题（操作菜单）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 操作用户的IP地址&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="remoteAddr"  maxlength="255" class="form-control input-sm" value="${storeAdminLog.remoteAddr}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写操作用户的IP地址<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 操作用户代理信息&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="userAgent"  maxlength="255" class="form-control input-sm" value="${storeAdminLog.userAgent}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写操作用户代理信息<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 操作的URI&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="requestUri"  maxlength="255" class="form-control input-sm" value="${storeAdminLog.requestUri}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写操作的URI<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 操作的方式(提交方式)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="method"  maxlength="64" class="form-control input-sm" value="${storeAdminLog.method}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写操作的方式(提交方式)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 操作提交的数据&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="params"  class="form-control input-sm" value="${storeAdminLog.params}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写操作提交的数据<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 异常信息&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="exception"  class="form-control input-sm" value="${storeAdminLog.exception}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写异常信息<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="store:storeAdminLog:edit">
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