<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('采购空间管理')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<!-- 百度上传js文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/webuploader/webuploader.min.js"></script>
<!-- MyUploader方法文件 -->
<script type="text/javascript" src="${ctxStatic}/MyUpload/js/MyUpload-2.0.0.js"></script>
<style>
	.sui-msg.msg-block {margin: 10px !important;}
	.dropdown-inner{width:111px !important;}
	.purchaseBatch .control-group{margin-top: 10px;}
	.purchaseBatch .control-group .control-label{display: inline-block;width: 120px;text-align: right;}
	.purchaseBatch .control-group label.error{margin-left: 90px;}
	.purchaseBatch .uploader-container img{vertical-align: bottom;}
	.sui-form.form-search label, .sui-form.form-inline label, .sui-form.form-search .btn-group, .sui-form.form-inline .btn-group{display: contents;}
</style>
</head>
<body>
	<div class="main-content form-style purchaseBatch">
		<div class="main-center">
			<dl class="box">
				<!-- <dt>
					<div class="position"><span>当前位置:</span><span>会员中心</span> &gt; <span>供采管理</span> &gt; 采购空间管理</div>
					<i class="sui-icon icon-tb-list"></i> 采购空间管理
					<%@ include file="../include/functionBtn.jsp"%>
				</dt> -->

				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('采购空间管理')}</span>
						<span style="margin-top: 5px; margin-left: 10px;" class="">
							<a href="javascript:void(0)" id="toolbar_operate_tips" class="sui-btn btn-bordered btn-small btn-warning" data-placement="bottom" data-toggle="tooltip" data-type="attention" title="" data-original-title="${fns:fy('操作提示')}">
								<i class="sui-icon icon-pc-light"></i>
							</a>
						</span>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('会员中心')}</li>
						<li>${fns:fy('供采管理')}</li>
						<li>${fns:fy('采购空间管理')}</li>
					</ul>
				</dt>




				<address class="sui-row-fluid sui-form form-horizontal screen mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('可以在采购空间管理来编辑用户的采购空间。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<form class="sui-form form-inline" method="post" id="inputForm" action="${ctxs}/purchase/space/save2.htm">
					<dd>
						<input type="hidden" name="spaceId" value="${purchaseSpace.spaceId}" class="input-xxlarge input-xfat">
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('采购空间Logo：')}</label> 
					        <div class="input-append">
			        			<input type="hidden" class="imgPath" name="logo" value="${purchaseSpace.logo}"/>
			        			<div id="vessel1"></div>
			        		</div>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('点击图片可查看原图。修改图片时请先删除原图片，删除按钮在图片上。建议上传200X100。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('采购空间Banner：')}</label> 
					        <div class="input-append">
			        			<input type="hidden" class="imgPath" name="banner" value="${purchaseSpace.banner}"/>
			        			<div id="vessel2"></div>
			        		</div>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('点击图片可查看原图。修改图片时请先删除原图片，删除按钮在图片上。建议上传1920X226。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('采购空间名称：')}</label> 
							<input type="text" name="name" value="${purchaseSpace.name}" class="input-xxlarge input-xfat" maxlength="64">
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请填写采购空间名称。')}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><font color="red">*</font>${fns:fy('采购空间简介：')}</label> 
							<textarea rows="5" cols="" name="synopsis" class="input-xxlarge input-xfat" maxlength="255">${purchaseSpace.synopsis}</textarea>
							<div class="formPrompt">
								<i class="msg-icon"></i>
								<div class="msg-con">${fns:fy('请填写采购空间简介。')}</div>
							</div>
						</div>
						<div class="text-align pb20">
							<button type="submit" class="sui-btn btn-xlarge btn-primary">${fns:fy('提交')}</button>
						</div>
					</dd>
				</form>
			</dl>
		</div>
	</div>
	<!-- 业务js -->
	<script type="text/javascript" src="${ctx}/views/seller/purchase/purchaseSpaceForm.js"></script>
</body>
</html>