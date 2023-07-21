<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('品牌管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productBrandForm.js"></script>
<!-- 引入iCheck文件 -->
<%@ include file="../include/head_iCheck.jsp"%>
<!-- 引入bootstrap-switch文件 -->
<%@ include file="../include/head_bootstrap-switch.jsp"%>
</head>
<body>
	<!-- panel开始 -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productBrand.brandId?true:false}"></c:set >
			<c:set var="edit" value ="${fns:fy('编辑')}"></c:set > 
			<c:set var="save" value ="${fns:fy('添加')}"></c:set > 
			<h4 class="title">${isEdit?edit:save}${fns:fy('品牌')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
			<ul class="nav nav-tabs pull-right">
				<li class="${empty status?(isEdit?'action':''):''}"><a href="${ctxa}/product/productBrand/list.do"> <i class="fa fa-user"></i> ${fns:fy('品牌列表')}</a></li>
				<li class="${status=='1'?'active':''}"><a href="${ctxa}/product/productBrand/list.do?status=1"> <i class="fa fa-user"></i> ${fns:fy('审核通过列表')}</a></li>
				<li class="${status=='2'?'active':''}"><a href="${ctxa}/product/productBrand/list.do?status=2"> <i class="fa fa-user"></i> ${fns:fy('审核未通过列表')}</a></li>
				<shiro:hasPermission name="product:productBrand:edit">
				<li class="active"><a href="${ctxa}/product/productBrand/${isEdit?'edit1':'save1'}.do" > <i class="fa fa-user"></i> ${fns:fy('品牌')}${isEdit?edit:save}</a></li>
				</shiro:hasPermission>
			</ul>
		</header>
		<!-- panel-body开始 -->
		<div class="panel-body">
			<!-- 提示开始 -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('品牌管理.品牌添加.操作提示1')}</li>
					<li>${fns:fy('品牌管理.品牌添加.操作提示2')}</li>
				</ul>
			</div>
			<!-- 提示结束 -->
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" action="${ctxa}/product/productBrand/${isEdit?'edit2':'save2'}.do" method="post">
						<input type="hidden" name="brandId" value="${productBrand.brandId}">
						<input type="hidden" name="accessKey" value="${generateAccessKey}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('品牌名称')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="name"  maxlength="64" class="form-control input-sm" value="${productBrand.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请填写品牌名称')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('品牌')}LOGO&nbsp;:</label>
							<div class="col-sm-5">
								<input id="img_logo_0" type="hidden" class="imgPath" name="logo" value="${productBrand.logo}"/>
								<div id="vessel1"></div>
							</div>	
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('品牌LOGO尺寸要求宽度为')}170x82<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('展示方式')}&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('brand_display_mode')}" var="item">
								<div class="col-sm-4 icheck ">
									<div class="square-blue single-row">
										<label class="checkbox-inline">
										<input type="radio" name="displayMode" value="${item.value}" ${item.value==productBrand.displayMode || item.value=='2'?"checked":""} style="display: none" data-switch-no-init/> ${item.label}
										</label>
									</div>
								</div>
								</c:forEach>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请选择展示方式')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('推荐')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="checkbox" ${productBrand.recommend eq 0?"":"checked"} data-size="small" name="recommend" value="1" style="display: none" data-on-text="${fns:fy('推荐')}" data-off-text="${fns:fy('不推荐')}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写推荐')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('排序')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="sort"  maxlength="10" class="form-control input-sm" value="${isEdit?productBrand.sort:'10'}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写排序')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('品牌介绍')}&nbsp;:</label>
							<div class="col-sm-5">
								<textarea class="form-control input-sm" name="introduction" rows="5">${productBrand.introduction}</textarea>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写品牌介绍')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('商标注册证(图1)')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="imgPath" name="applyPathP1" value="${productBrand.applyPathP1}"/>
								<div id="vessel2"></div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请上传商标注册证(图1)')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('商标注册证(图2)')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="imgPath" name="applyPathP2" value="${productBrand.applyPathP2}"/>
								<div id="vessel3"></div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请上传商标注册证(图2)')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('品牌所有者')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text" name="brandOwner"  maxlength="64" class="form-control input-sm" value="${productBrand.brandOwner}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写品牌所有者')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('申请者')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="text"  maxlength="64" readonly="readonly" class="form-control input-sm" value="${productBrand.store.name}"/>

							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写申请者')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('审核状态')}&nbsp;:</label>
							<div class="col-sm-5">
								<c:forEach items="${fns:getDictList('brand_status')}" var="item">
								<div class="col-sm-4 icheck ">
									<div class="square-blue single-row">
										<label class="checkbox-inline">
										<input type="radio" name="status" value="${item.value}" ${item.value==productBrand.status || (productBrand.status==null && item.value=='1')?"checked":""} style="display: none" data-switch-no-init/> ${item.label}
										</label>
									</div>
								</div>
								</c:forEach>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请填写审核状态')}<p>
							</div>
						</div>
						<c:choose>
							<c:when test="${not empty productBrand.brandId}">
								<c:if test="${productBrand.status==2}">
									<div class="form-group">
										<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('审核未通过原因')}&nbsp;:</label>
										<div class="col-sm-5">
											<input type="text" name="cause"  maxlength="512" class="form-control input-sm" value="${productBrand.cause}"/>
										</div>
										<div class="col-sm-5">
											<p class="help-block">${fns:fy('请填写审核未通过原因')}<p>
										</div>
									</div>
								</c:if>
							</c:when>
						</c:choose>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="product:productBrand:edit">
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