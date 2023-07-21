<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("店铺管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeForm.js"></script>
<!-- 引入bootstrap-switch文件 -->
<%@ include file="../include/head_bootstrap-switch.jsp"%>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty store.storeId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("店铺")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/store/list.do"> <i class="fa fa-user"></i> ${fns:fy("店铺列表")}</a></li>
				<shiro:hasPermission name="store:store:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("店铺")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.店铺管理.操作提示4")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/store/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="storeId" value="${store.storeId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("店铺名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" id="oldStoreName" value="${store.name}"/>
								<input type="text" name="name" maxlength="18" class="form-control input-sm" value="${store.name}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写店铺名称")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("完整详细地址")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="detailedAddress" maxlength="64" class="form-control input-sm" value="${store.detailedAddress}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写店铺完整详细地址")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("店铺主营产品")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea rows="6" class="form-control" name="industry" maxlength="250">${store.industry}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写店铺主营产品")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("店铺Logo")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="imgPath" name="logo" value="${store.logo}"/>
								<div id="vessel1"></div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("店铺管理.店铺管理.操作提示5")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("店铺横幅")}&nbsp;:</label>
							<div class="col-sm-5">
								<input id="img_banner" type="hidden" class="imgPath" name="banner" value="${store.banner}"/>
								<div id="vessel2"></div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("店铺管理.店铺管理.操作提示6")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("店铺客服电话")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeTel" maxlength="64" class="form-control input-sm" value="${store.storeTel}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写店铺客服电话")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("店铺联系QQ")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeQq" maxlength="64" class="form-control input-sm" value="${store.storeQq}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写店铺联系QQ")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy("店铺联系微信")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="storeWechat" maxlength="64" class="form-control input-sm" value="${store.storeWechat}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写店铺联系微信")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>SEO Title&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="seoTitle" maxlength="64" class="form-control input-sm" value="${store.seoTitle}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("店铺管理.店铺管理.操作提示7")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy("SEO关键字")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="seoKeyword" maxlength="64" class="form-control input-sm" value="${store.seoKeyword}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("店铺管理.店铺管理.操作提示8")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("SEO店铺描述")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea rows="6" class="form-control" name="seoDescribe" maxlength="250">${store.seoDescribe}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("店铺管理.店铺管理.操作提示9")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("店铺类型")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="storeType" class="form-control m-bot15 input-sm">
									<c:forEach items="${fns:getDictList('store_type')}" var="item">
										<option value="${item.value}" ${item.value==store.storeType?"selected":""}>${item.label}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择类型")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("店铺等级")}&nbsp;:</label>
							<div class="col-sm-5">
								<select name="levelId" class="form-control m-bot15 input-sm">
									<c:forEach items="${storeLevel}" var="item">
										<option value="${item.levelId}" ${item.levelId==store.levelId?"selected":""}>${item.name}</option>
									</c:forEach>
								</select>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择类型")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("是否开启店铺")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${store.isOpen eq 0?"":"checked"} data-size="small" name="isOpen" value="1" style="display: none" data-on-text="${fns:fy("开启")}" data-off-text="${fns:fy("关闭")}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请选择是否开启店铺")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="store:store:edit">
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