<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('规格管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productSpecForm.js"></script>
<!-- 引入bootstrap-switch文件 -->
<%@ include file="../include/head_bootstrap-switch.jsp"%>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productSpec.specId?true:false}"></c:set >
			<c:set var="edit" value ="${fns:fy('编辑')}"></c:set > 
			<c:set var="save" value ="${fns:fy('添加')}"></c:set >
			<h4 class="title">${isEdit?edit:save}${fns:fy('规格')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productSpec/list.do"> <i class="fa fa-user"></i> ${fns:fy('规格列表')}</a></li>
				<shiro:hasPermission name="product:productSpec:edit">
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${fns:fy('规格')}${isEdit?edit:save}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('规格管理.规格添加.操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productSpec/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="specId" value="${productSpec.specId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('规格名')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${productSpec.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写规格名')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('规格值')}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea rows="3" id="specValues" class="form-control" name="specValues">${productSpec.specValues}</textarea>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写规格值,多个规格值请用逗号分隔(注意：请不要以逗号开头)')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('是否是颜色')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${productSpec.isColor eq 0?"":"checked"} data-size="small" name="isColor" value="1" style="display: none" data-on-text="${fns:fy('是')}" data-off-text="${fns:fy('否')}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写是否是颜色')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('排序')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="specSort"  maxlength="10" class="form-control input-sm" value="${isEdit?productSpec.specSort:'10'}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写排序')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="${isEdit?'product:productSpec:edit':'product:productSpec:save'}">
								<button type="submit" class="btn btn-info">
									<i class="fa fa-check"></i> ${fns:fy('保存')}
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