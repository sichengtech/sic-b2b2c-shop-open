<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商家相册管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeAlbumSpaceForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeAlbumSpace.albumSpaceId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}商家相册</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeAlbumSpace/list.do"> <i class="fa fa-user"></i> 商家相册列表</a></li>
				<shiro:hasPermission name="store:storeAlbumSpace:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 商家相册${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>商家相册管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeAlbumSpace/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="albumSpaceId" value="${storeAlbumSpace.albumSpaceId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 相册ID&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="albumSpaceId" maxlength="19" class="form-control input-sm" value="${storeAlbumSpace.albumSpaceId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写相册ID<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 图片数量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pictureCount" maxlength="10" class="form-control input-sm" value="${storeAlbumSpace.pictureCount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写图片数量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 相册的数量&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="albumCount" maxlength="10" class="form-control input-sm" value="${storeAlbumSpace.albumCount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写相册的数量<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 图片总空间&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="totalSpace" maxlength="10" class="form-control input-sm" value="${storeAlbumSpace.totalSpace}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写图片总空间<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 图片占用空间&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pictureSpace" maxlength="10" class="form-control input-sm" value="${storeAlbumSpace.pictureSpace}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写图片占用空间<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="store:storeAlbumSpace:edit">
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