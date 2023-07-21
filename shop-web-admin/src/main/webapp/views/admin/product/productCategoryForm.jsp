<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/admin/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('商品分类管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="admin"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/admin/product/productCategoryForm.js"></script>
<!-- 引入bootstrap-switch文件 -->
<%@ include file="../include/head_bootstrap-switch.jsp"%>
</head>
<body>
	<!-- panel start -->
	<section class="panel">
		<header class="panel-heading custom-tab tab-right ">
			<c:set var="isEdit" value ="${not empty productCategory.categoryId?true:false}"></c:set > 
			<c:set var="edit" value ="${fns:fy('编辑')}"></c:set > 
			<c:set var="save" value ="${fns:fy('添加')}"></c:set > 
			<h4 class="title">${isEdit?edit:save}${fns:fy('商品分类')}</h4>
			<%@ include file="../include/functionBtn.jsp"%>
		 	<ul class="nav nav-tabs pull-right">
				<li class=""><a href="${ctxa}/product/productCategory/list.do"> <i class="fa fa-home"></i> ${fns:fy('分类列表')}</a></li>
				<li class="active"><a href="javaScript:;" > <i class="fa fa-user"></i> ${isEdit?edit:save}${fns:fy('分类')}</a></li>
			</ul>
		</header>
		<div class="panel-body">
			<!-- 提示 start -->
			<div class="alert alert-info alert-block fade in ${cookie.fdp_operationTips.value=='0'?'point_hidden':''}" id="point">
				<h5>${fns:fy('操作提示')}</h5>
				<ul>
					<li>${fns:fy('商品管理.添加商品分类.操作提示1')}</li>
				</ul>
			</div>
			<!-- 提示 end -->	 
			<sys:message content="${message}"/>
			<div class="tab-content" style="margin-top:15px;">
				<div class="tab-pane active" id="home-3">
					<form class="cmxform form-horizontal adminex-form" id="inputForm" method="post" action="${ctxa}/product/productCategory/save.do">
						<input type="hidden" name="categoryId" value="${productCategory.categoryId}">
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess">${fns:fy('上级分类')}&nbsp;:</label>
							<div class="col-sm-5">
								<sys:treeselect id="parent" name="parent.id" value="${productCategory.parent.id}" labelName="parent.name" labelValue="${productCategory.parent.name}"
								title="父类id" url="/product/productCategory/treeData.do" extId="${productCategory.id}" cssClass="" allowClear="true"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请选择上级分类')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('商品分类名称')}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" name="name" type="text"  maxlength="20" value="${productCategory.name}"> 
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，该名称在前台显示')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('分佣比例')}&nbsp;:</label>
							<div class="col-sm-5">
								<div class="input-group">
									<input class="form-control input-sm" name="commission" type="text" maxlength="20" value="${productCategory.commission}"> 
									<div class="input-group-addon">%</div>
								</div>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('会在商家结算时进行计算')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font>${fns:fy('是否锁定')}&nbsp;:</label>
							<div class="col-sm-5">
							 <input type="checkbox" ${productCategory.isLocked eq 0 || productCategory.isLocked==null?"":"checked"} data-size="small" name="isLocked" value="1" style="display: none" data-on-text="${fns:fy('是')}" data-off-text="${fns:fy('否')}"/>
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('是否锁定')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"> ${fns:fy('分类图标')}&nbsp;:</label>
							<div class="col-sm-5">
								<input type="hidden" class="imgPath" name="bak1" value="${productCategory.bak1}"/>
								<div id="vessel"></div>
							</div>	
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('请上传分类图标')}
								<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"><font color="red">*</font> ${fns:fy('排序')}&nbsp;:</label>
							<div class="col-sm-5">
								<input class="form-control input-sm" type="text" name="sort" value="${isEdit?productCategory.sort:'10'}" maxlength="10">
							</div>
							<div class="col-sm-5">
								<p class="help-block">${fns:fy('必填项，请输入一个数字.')}<p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="inputSuccess"></label>
							<div class="col-sm-5">
								<button type="button" class="btn btn-primary" onclick="javascript:history.go(-1);">
									<i class="fa fa-times"></i> ${fns:fy('返回')}
								</button>
								<shiro:hasPermission name="product:productCategory:edit">
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
	</section>
	<!-- panel end -->
</body>
</html>