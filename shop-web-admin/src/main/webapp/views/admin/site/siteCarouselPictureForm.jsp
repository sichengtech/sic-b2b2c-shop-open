<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("网站轮播图片管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/site/siteCarouselPictureForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteCarouselPicture.id?true:false}"></c:set > 
			<h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("网站轮播图片")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/site/siteCarouselPicture/list.do"> <i class="fa fa-user"></i> ${fns:fy("网站轮播图片列表")}</a></li>
				<shiro:hasPermission name="${isEdit?'site:siteCarouselPicture:edit':'site:siteCarouselPicture:save'}">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("网站轮播图片")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("网站设置.轮播图片.操作提示3")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteCarouselPicture/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${siteCarouselPicture.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("图片")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="imgPath" name="path" value="${siteCarouselPicture.path}"/>
								<div id="vessel"></div>
							</div>
							<div class="col-sm-5">
								<h4>${fns:fy("上传轮播图片")}</h4>
								<p class="help-block">${fns:fy("网站设置.轮播图片.操作提示4")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("目标连接")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="url" maxlength="255" class="form-control input-sm" value="${siteCarouselPicture.url}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("网站设置.轮播图片.操作提示5")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("标题")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="title" maxlength="64" class="form-control input-sm" value="${siteCarouselPicture.title}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("轮播图片的标题")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("排序")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort" maxlength="10" class="form-control input-sm" value="${empty siteCarouselPicture.sort?'10':siteCarouselPicture.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("轮播图片的排序")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("状态")}&nbsp;:</label>
							<div class="col-sm-5">
								<input name="status" type="checkbox" value="1" ${siteCarouselPicture.status==0?"":"checked"} data-size="small" style="display: none" data-on-text="${fns:fy("启用")}" data-off-text="${fns:fy("禁用")}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择状态")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("类型")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<option value="">--${fns:fy("请选择")}--</option>
									<c:forEach items="${fns:getDictList('carousel_picture_type')}" var="item">
									<option value="${item.value}" ${item.value==siteCarouselPicture.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择类型")}<p>
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 导航动作&nbsp;:</label>
							<div class="col-sm-2" style="padding-right: 0px;width:130px">
								<select name="action" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('site_nav_action')}" var="item">
									<option value="${item.value}" ${item.value==siteCarouselPicture.action?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-3" style="padding-left: 0px;width:339px">
								<input type="text" name="url" maxlength="255" class="form-control input-sm" value="${siteCarouselPicture.url}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">打开连接：请输入目标的URL。搜索关键字：请输入一个词组。打开商品：请输入商品的ID。打开店铺：请输入店铺的ID。打开商品分类：请输入分类的ID<p>
							</div>
						</div> --%>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("打开方式")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="target" class="form-control m-bot15 input-sm">
									<c:forEach items="${fns:getDictList('target')}" var="item">
									<option value="${item.value}" ${item.value==siteCarouselPicture.target?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择打开方式")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("文本")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="txt" maxlength="255" class="form-control input-sm" value="${siteCarouselPicture.txt}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写文本")}<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="${isEdit?'site:siteCarouselPicture:edit':'site:siteCarouselPicture:save'}">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy("保存")}
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