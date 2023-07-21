<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("app版本管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/app/appVersionForm.js"></script>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty appVersion.id?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑app版本管理"):fns:fy("添加app版本管理")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/app/appVersion/list.do"> <i class="fa fa-user"></i> ${fns:fy("app版本管理")}</a></li>
				<shiro:hasPermission name="app:appVersion:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?'app版本管理编辑':'app版本管理添加'}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("app版本管理列表")}</li>
					<li>${fns:fy("App版本.App版本管理.操作提示5")}</li>
					<li>${fns:fy("App版本.App版本管理.操作提示6")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/app/appVersion/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="id" value="${appVersion.id}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("版本号")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="version"  maxlength="32" class="form-control input-sm" value="${appVersion.version}" placeholder="${fns:fy("App版本.App版本管理.操作提示5")}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写版本号")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("安装包类型")}：&nbsp;:</label>
							<div class="col-sm-5">
								<select name="type" class="form-control m-bot15 input-sm">
									<c:forEach items="${fns:getDictList('app_type')}" var="item">
										<option value="${item.value}" ${item.value==appVersion.type?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择安装包类型")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("是否为最新版本")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="isNewVersion" class="form-control m-bot15 input-sm">
									<c:forEach items="${fns:getDictList('app_is_new_version')}" var="item">
										<option value="${item.value}" ${item.value==appVersion.isNewVersion?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择是否为最新版本")}<p>
							</div>
						</div>


						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("上传安装包")}&nbsp;:</label>
                            <div class="col-sm-5">
								<input type="hidden" class="imgPath" name="downloadPath" value="${appVersion.downloadPath}"/>
								<div id="appPath">${appVersion.downloadPath}</div>
								<div id="vessel"></div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("App版本.App版本管理.操作提示7")}<p>
							</div>
						</div>



						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("版本说明")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="explain"  maxlength="255" class="form-control input-sm" value="${appVersion.explain}" placeholder="${fns:fy("版本说明")}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写版本说明")}<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="app:appVersion:edit">
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