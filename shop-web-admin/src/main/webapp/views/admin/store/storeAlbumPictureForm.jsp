<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商家相册图片管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeAlbumPictureForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeAlbumPicture.pictureId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}商家相册图片</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeAlbumPicture/list.do"> <i class="fa fa-user"></i> 商家相册图片列表</a></li>
				<shiro:hasPermission name="store:storeAlbumPicture:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 商家相册图片${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>可以查看店铺所有上传过的图片。</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeAlbumPicture/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="pictureId" value="${storeAlbumPicture.pictureId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pictureId" maxlength="19" class="form-control input-sm" value="${storeAlbumPicture.pictureId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 关联(卖家表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerId" maxlength="19" class="form-control input-sm" value="${storeAlbumPicture.sellerId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写关联(卖家表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> store_album(相册夹表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="albumId" maxlength="19" class="form-control input-sm" value="${storeAlbumPicture.albumId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写store_album(相册夹表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 图片的存储路径&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="path" maxlength="255" class="form-control input-sm" value="${storeAlbumPicture.path}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写图片的存储路径<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 原图像素，例如：200x300，单位px&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pixel" maxlength="64" class="form-control input-sm" value="${storeAlbumPicture.pixel}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写原图像素，例如：200x300，单位px<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 文件大小，单位byte&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="fileSize" maxlength="10" class="form-control input-sm" value="${storeAlbumPicture.fileSize}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写文件大小，单位byte<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="store:storeAlbumPicture:edit">
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