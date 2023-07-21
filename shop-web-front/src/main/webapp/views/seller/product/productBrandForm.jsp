<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<c:set var="isEdit" value ="${not empty productBrand.brandId?true:false}"></c:set >
<title>${isEdit?fns:fy('编辑'):fns:fy('申请')}${fns:fy('品牌')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>

<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/product/productBrandForm.js"></script>
<style type="text/css">
 .sui-msg.msg-block{margin:10px!important;}
</style>
</head>
<body>
<div class="main-content">
	<div class="goods-list">
		<dl class="box store-set">
			
			<dt class="box-header">
				<h4 class="pull-left"><i class="sui-icon icon-tb-addressbook"></i><span>${isEdit?fns:fy('编辑'):fns:fy('申请')}${fns:fy('品牌')}</span></h4>
				<ul class="sui-breadcrumb">
					<li>${fns:fy('当前位置:')}</li>
					<li>${fns:fy('商品')}</li>
					<li>${fns:fy('商品管理')}</li>
					<li class="active">${isEdit?fns:fy('编辑'):fns:fy('申请')}${fns:fy('品牌')}</li>
				</ul>
			</dt>
			<div class="sui-msg msg-tips msg-block">
			 <div class="msg-con">
				 <ul>
					<h4>${fns:fy('提示信息')}</h4>
					<li>${fns:fy('品牌名称、注册/申请号、品牌所有人是必填项。')}</li>
				 </ul>
				<button type="button" data-dismiss="msgs" class="sui-close">×</button>
			 </div>
			 <s class="msg-icon" style="margin-top: 10px"></s>
			</div>
			<sys:message content="${message}"/>
			<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/product/productBrand/${isEdit?'edit2':'save2'}.htm">
				<dd>
					<input type="hidden" name="brandId" value="${productBrand.brandId}">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('品牌名称：')}</label>
						<input type="text" class="input-xlarge" name="name" value="${productBrand.name}">
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请输入品牌名称')}</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('品牌所有人：')}</label>
						<input type="text" class="input-xlarge" name="brandOwner" value="${productBrand.brandOwner}">
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('填写商标注册证或商标受理通知书的注册人或申请人信息。')}</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('品牌LOGO：')}</label> 
						<div class="input-append">
		        			<input type="hidden" class="imgPath" name="logo" value="${productBrand.logo}"/>
		        			<div id="vessel1"></div>
		        		</div>
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('品牌LOGO尺寸要求宽度为170X82像素，支持格式gif，jpg，png。')}</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>${fns:fy('商标注册证(图1)：')}</label> 
						<div class="input-append">
		        			<input type="hidden" class="imgPath" name="applyPathP1" value="${productBrand.applyPathP1}"/>
		        			<div id="vessel2"></div>
		        		</div>
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('若提供图片将优先审核,持有中文和英文各商标证可上传2张。')}</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('商标注册证(图2)：')}</label> 
						<div class="input-append">
		        			<input type="hidden" class="imgPath" name="applyPathP2" value="${productBrand.applyPathP2}"/>
		        			<div id="vessel3"></div>
		        		</div>
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('若提供图片将优先审核,持有中文和英文各商标证可上传2张。')}</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">${fns:fy('品牌介绍：')}</label>
						<textarea class="input-xlarge" name="introduction" rows="5">${productBrand.introduction}</textarea>
						<div class="formPrompt">
							<i class="msg-icon"></i>
							<div class="msg-con">${fns:fy('请填写品牌介绍')}</div>
						</div>
					</div>
					<shiro:hasPermission name="product:productBrand:edit">
					<div class="text-align pb20">
						<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('保存')}</button>
					</div>
					</shiro:hasPermission>
				</dd>
			</form>
		</dl>
		</div>
	</div>
</body>
</html>