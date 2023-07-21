<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy("店铺等级管理")}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/store/storeLevelForm.js"></script>
<!-- 引入bootstrap-switch文件 -->
<%@ include file="../include/head_bootstrap-switch.jsp"%>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty storeLevel.levelId?true:false}"></c:set >
			<h4 class="title">${isEdit?fns:fy("编辑"):fns:fy("添加")}${fns:fy("店铺等级")}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/store/storeLevel/list.do"> <i class="fa fa-user"></i> ${fns:fy("店铺等级列表")}</a></li>
				<shiro:hasPermission name="store:storeLevel:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy("店铺等级")}${isEdit?fns:fy("编辑"):fns:fy("添加")}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy("操作提示")}</h5>
				<ul>
					<li>${fns:fy("店铺管理.店铺等级.操作提示1")}</li>
					<li>${fns:fy("店铺管理.店铺等级.操作提示2")}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/store/storeLevel/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="levelId" value="${storeLevel.levelId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("等级名称")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name" maxlength="64" class="form-control input-sm" value="${storeLevel.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写等级名称")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("可推荐商品数")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="recommendProductCount" maxlength="10" class="form-control input-sm" value="${storeLevel.recommendProductCount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写可推荐商品数")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("可发布商品数")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="releaseProcuctCount" maxlength="10" class="form-control input-sm" value="${storeLevel.releaseProcuctCount}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写可发布商品数")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("图片空间容量/b")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="pictureSpace" maxlength="10" class="form-control input-sm" value="${storeLevel.pictureSpace}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写图片空间容量/b")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("收费标准/年")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="money" class="form-control input-sm" value="${storeLevel.money}" maxlength="12"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写收费标准/年")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("申请说明")}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea name="applicationNote" class="form-control input-sm" maxlength="255">${storeLevel.applicationNote}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写申请说明")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("排序")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort" maxlength="10" class="form-control input-sm" value="${isEdit?storeLevel.sort:10}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("请填写排序")}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy("是否开启")}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${storeLevel.isOpen eq 0?"":"checked"} data-size="small" name="isOpen" value="1" style="display: none" data-on-text="${fns:fy("是")}" data-off-text="${fns:fy("否")}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy("必填项")}${fns:fy("请填写是否开启")}<p>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy("返回")}
								</button>
								<shiro:hasPermission name="${isEdit?'store:storeLevel:edit':'store:storeLevel:save'}">
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