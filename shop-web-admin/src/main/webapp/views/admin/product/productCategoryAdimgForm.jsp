<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>分类导航广告图管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productCategoryAdimgForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productCategoryAdimg.navImgId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}分类导航广告图</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productCategoryAdimg/list.do"> <i class="fa fa-user"></i> 分类导航广告图列表</a></li>
				<shiro:hasPermission name="product:productCategoryAdimg:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 分类导航广告图${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>分类导航广告图管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productCategoryAdimg/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="navImgId" value="${productCategoryAdimg.navImgId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="navImgId"  maxlength="19" class="form-control input-sm" value="${productCategoryAdimg.navImgId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 分类ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="categoryId"  maxlength="19" class="form-control input-sm" value="${productCategoryAdimg.categoryId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写分类ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 图片&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="imgPath"  maxlength="64" class="form-control input-sm" value="${productCategoryAdimg.imgPath}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写图片<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 原图尺寸&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="originalSize"  maxlength="64" class="form-control input-sm" value="${productCategoryAdimg.originalSize}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写原图尺寸<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${productCategoryAdimg.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写排序<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 动作&nbsp;:</label>
							<div class="col-sm-5">
								<select name="action" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('site_nav_action')}" var="item">
									<option value="${item.value}" ${item.value==productCategoryAdimg.action?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写动作<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 目标&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="url"  maxlength="255" class="form-control input-sm" value="${productCategoryAdimg.url}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写目标<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 窗口打开方式&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="target"  maxlength="64" class="form-control input-sm" value="${productCategoryAdimg.target}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写窗口打开方式<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="product:productCategoryAdimg:edit">
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