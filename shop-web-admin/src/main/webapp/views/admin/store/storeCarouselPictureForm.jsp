<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>店铺轮播图管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeCarouselPictureForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeCarouselPicture.cpId?true:false}"></c:set >
			<h4 class="title">${isEdit?'编辑':'添加'}店铺轮播图</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeCarouselPicture/list.do"> <i class="fa fa-user"></i> 店铺轮播图列表</a></li>
				<shiro:hasPermission name="store:storeCarouselPicture:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 店铺轮播图${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>店铺轮播图管理是xxx</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
					<li>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeCarouselPicture/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="cpId" value="${storeCarouselPicture.cpId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 主键&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="cpId"  maxlength="19" class="form-control input-sm" value="${storeCarouselPicture.cpId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写主键<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 关联(卖家表)&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sellerId"  maxlength="19" class="form-control input-sm" value="${storeCarouselPicture.sellerId}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写关联(卖家表)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 图片地址&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="picturePath"  maxlength="64" class="form-control input-sm" value="${storeCarouselPicture.picturePath}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写图片地址<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 原图尺寸（单位像素）&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="originalSize"  maxlength="64" class="form-control input-sm" value="${storeCarouselPicture.originalSize}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写原图尺寸（单位像素）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${storeCarouselPicture.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写排序<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 是否开启(0否、1是)&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<label class="checkbox-inline">
								<input type="radio" name="isOpen" value="${item.value}" ${item.value==storeCarouselPicture.isOpen?"checked":""}/> ${item.label}
								</label>
								</c:forEach>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写是否开启(0否、1是)<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 动作，字典&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="action"  maxlength="10" class="form-control input-sm" value="${storeCarouselPicture.action}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写动作，字典<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 目标链接&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="url"  maxlength="255" class="form-control input-sm" value="${storeCarouselPicture.url}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写目标链接<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 窗口打开方式（1mainFrame、2 _blank、3_self、4_parent、5_top）&nbsp;:</label>
							<div class="col-sm-5">
								<select name="target" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('target')}" var="item">
									<option value="${item.value}" ${item.value==storeCarouselPicture.target?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写窗口打开方式（1mainFrame、2 _blank、3_self、4_parent、5_top）<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 标题&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="title"  maxlength="64" class="form-control input-sm" value="${storeCarouselPicture.title}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写标题<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> 文本&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="txt"  maxlength="255" class="form-control input-sm" value="${storeCarouselPicture.txt}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">请填写文本<p>
							</div>
						</div>
					
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回
								</button>
								<shiro:hasPermission name="store:storeCarouselPicture:edit">
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