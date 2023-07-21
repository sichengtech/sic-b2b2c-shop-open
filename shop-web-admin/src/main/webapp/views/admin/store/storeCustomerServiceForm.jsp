<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>店铺客服表管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeCustomerServiceForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeCustomerService.scsId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}店铺客服表</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeCustomerService/list.do"> <i class="fa fa-user"></i> 店铺客服表列表</a></li>
				<shiro:hasPermission name="store:storeCustomerService:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 店铺客服表${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>店铺客服表管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeCustomerService/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="scsId" value="${storeCustomerService.scsId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 客服id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="scsId"  maxlength="19" class="form-control input-sm" value="${storeCustomerService.scsId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写客服id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 店铺id&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeId"  maxlength="19" class="form-control input-sm" value="${storeCustomerService.storeId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写店铺id<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 客服名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${storeCustomerService.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写客服名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 客服工具（1.QQ，2.站内IM，3.旺旺）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="tool"  maxlength="1" class="form-control input-sm" value="${storeCustomerService.tool}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写客服工具（1.QQ，2.站内IM，3.旺旺）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 客服账号&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="account"  maxlength="64" class="form-control input-sm" value="${storeCustomerService.account}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写客服账号<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 类型（1.售前服务，2.售后服务）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="type"  maxlength="1" class="form-control input-sm" value="${storeCustomerService.type}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写类型（1.售前服务，2.售后服务）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${storeCustomerService.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写排序<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="store:storeCustomerService:edit">
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