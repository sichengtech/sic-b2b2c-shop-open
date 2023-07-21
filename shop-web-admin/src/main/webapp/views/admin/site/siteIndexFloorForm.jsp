<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>网站首页楼层管理</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<%@ include file="../include/head_bootstrap-switch.jsp"%> 
<!-- 业务js -->
<script type="text/javascript" src="${ctxStatic}/ajaxfileupload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctxStatic}/fdp/upload.js"></script>
<script type="text/javascript" src="${ctx}/views/admin/site/siteIndexFloorForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty siteIndexFloor.id?true:false}"></c:set > 
			<h4 class="title">${isEdit?'编辑':'添加'}网站首页楼层</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/site/siteIndexFloor/list.do"> <i class="fa fa-user"></i> 网站首页楼层列表</a></li>
				<shiro:hasPermission name="${isEdit?'site:siteIndexFloor:edit':'site:siteIndexFloor:save'}">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> 网站首页楼层${isEdit?'编辑':'添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>操作提示</h5>
				<ul>
					<li>编辑设置首页楼层基本信息内容，如名称、排序、是否显示等。</li>
				</ul>
			</div>
			<!-- 提示结束 -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/site/siteIndexFloor/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${siteIndexFloor.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 名称&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name" maxlength="64" class="form-control input-sm" value="${siteIndexFloor.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写名称<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 色彩风格&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="color" maxlength="64" class="form-control input-sm" value="${siteIndexFloor.color}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写色彩风格<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 显示&nbsp;:</label>
							<div class="col-sm-5">
								<input name="isDisplay" type="checkbox" value="1" ${siteIndexFloor.isDisplay==1?"checked":""} data-size="small" style="display: none" data-on-text="显示" data-off-text="隐藏"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">必选项，请选择是否显示<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 导航文字&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="text" maxlength="64" class="form-control input-sm" value="${siteIndexFloor.text}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写导航文字<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 导航图片&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="existSize_imgPath" value="${not empty siteIndexFloor.imgPath?'1':'0'}"/>
								<input id="fileUpload_imgPath" name="fileUpload" type="file" thumbnail="100x100" tokenPath="${ctxa}/sys/sysToken/getToken.do" suffix="imgPath" allowType="jpg,jpeg,png,bmp" fileSize="5242880" class="fileUploadClass"/>
								<div class="result_imgPath"></div>
								<div class="uploadImgDiv" id="img_imgPath_0" style="${not empty siteIndexFloor.imgPath?'':'display:none;'}">
									<input type="hidden" class="imgPath" name="imgPath" value="${siteIndexFloor.imgPath}"/>
									<div class="result"></div>
									<img class="preview" src="${ctxfs}${siteIndexFloor.imgPath}@!100x100" style="${not empty siteIndexFloor.imgPath?'':'display:none;'}"/>
									<div class="uploadCloseBtn">
										<img alt="" src="${ctxStatic}/images/close.gif"/>
									</div>
								</div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">请上传导航图片<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 楼层类型&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('site_floor_type')}" var="item">
									<option value="${item.value}" ${item.value==siteIndexFloor.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必选项，请选择楼层类型<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 模板风格&nbsp;:</label>
							<div class="col-sm-5">
								<select name="style" class="form-control m-bot15 input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${fns:getDictList('site_template_style')}" var="item">
									<option value="${item.value}" ${item.value==siteIndexFloor.style?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必选项，请选择模板风格<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> 排序&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort" maxlength="10" class="form-control input-sm" value="${siteIndexFloor.sort}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">必填项，请填写排序<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> 返 回 
								</button>
								<shiro:hasPermission name="${isEdit?'site:siteIndexFloor:edit':'site:siteIndexFloor:save'}">
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