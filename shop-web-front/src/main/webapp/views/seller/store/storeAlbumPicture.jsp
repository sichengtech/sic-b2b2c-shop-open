<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/views/seller/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>${fns:fy('图片空间')}</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<meta name="decorator" content="seller"/>
<meta name="menu" content="4"/><!--表示使用N号二级菜单 -->
<!--webuploader上传-->
<script type="text/javascript" src="${ctxStatic}/baiduUEditor1.4.3.2/third-party/webuploader/webuploader.js"></script>
<!-- 业务js -->
<script type="text/javascript" src="${ctx}/views/seller/store/storeAlbumPicture.js"></script>
<script type="text/javascript" src="${ctx}/views/seller/store/webUploaderAlbum.js"></script>
<!--图片空间相关放大控件-->
<script type="text/javascript" src="${ctxStatic}/sicheng-seller/js/jquery.magnific-popup.min.js"></script>
<!--图片空间放大控件-->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/sicheng-seller/css/magnific-popup.css">
<!--webuploader上传-->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/baiduUEditor1.4.3.2/third-party/webuploader/webuploader.css">
<style type="text/css">
	.sui-msg.msg-block{margin-left:10px!important;margin-right:10px!important;margin-top:-10px!important;}
	.change{min-height: 450px;border:1px solid #fdfdfd;}
	.noImgTip{margin-top: 225px;text-align: center;color: #9c9898;}
	#picture-tool{width:200px;position:absolute;z-index:9;right: 10px;top:1px;background: #e5e5e5;opacity: 0.7; border-radius: 6px;}
	#fun{position:relative;}
</style>
</head>
<body>
	<div class="main-content">
		<div class="">
			<dl class="box store-set bg-f9f9f9">
				<dt class="box-header">
					<h4 class="pull-left">
						<i class="sui-icon icon-tb-addressbook"></i>
						<span>${fns:fy('图片空间')}</span>
						<%@ include file="../include/functionBtn.jsp"%>
					</h4>
					<ul class="sui-breadcrumb">
						<li>${fns:fy('当前位置:')}</li>
						<li>${fns:fy('店铺')}</li>
						<li>${fns:fy('店铺管理')}</li>
						<li class="active">${fns:fy('图片空间')}</li>
					</ul>
				</dt>
				<address class="sui-row-fluid sui-form form-horizontal screen pt20 mb0 ${cookie.fdp_seller_operationTips.value=='0'?'point_hidden':''}" id="point">
					<div class="sui-msg msg-tips msg-block">
						<div class="msg-con">
							<ul>
								<h4>${fns:fy('提示信息')}</h4>
								<li>${fns:fy('显示当前店铺的所有图片相册。')}</li>
								<li>${fns:fy('查看相册空间的使用情况。')}</li>
								<li>${fns:fy('点击"删除"按钮可以删除图片，也可以将图片移动其他的相册中。')}</li>
							</ul>
						</div>
						<s class="msg-icon" style="margin-top: 10px"></s>
					</div>
				</address>
				<sys:message content="${message}"/>
				<dt class="left-picture">
					<input type="hidden" class="albumId" name="albumId" value="${albumId}">
					<input type="hidden" name="pageNo" value="${albumId}-${empty pageNo?'1':pageNo}"/> 
					<ul>
						<c:forEach items="${storeAlbums}" var="storeAlbum"  varStatus="s">
							<c:if test="${not empty albumId}">
								<li><a href="javaScript:;" class="${storeAlbum.albumId==albumId?'active switch':'switch'}" albumId="${storeAlbum.albumId}"><i class="sui-icon icon-folder-close"></i>${storeAlbum.albumName}(<span class="picCount">${storeAlbum.pictureCount}</span>)</a></li>
							</c:if>
							<c:if test="${empty albumId}">
								<li><a href="javaScript:;" class="${s.first==true?'active switch':'switch'}" albumId="${storeAlbum.albumId}"><i class="sui-icon icon-folder-close"></i>${storeAlbum.albumName}(<span class="picCount">${storeAlbum.pictureCount}</span>)</a></li>
							</c:if>
						</c:forEach>
					</ul>
					<shiro:hasPermission name="store:storeAlbum:edit">
					<div class="addtype">
						<a href="${ctxs}/store/storeAlbum/list.htm"><i class="sui-icon icon-touch-plus"></i>${fns:fy('管理相册')}</a>
					</div>
					</shiro:hasPermission>
				</dt>
				<dd id="fun" class="content-picture p0 m0">
					<div class="picture-zone">
						<div class="m20 picture-tool">
							<div class="cloudsize">
								<div class="pull-right text-right use">
									${fns:fy('总空间')}&nbsp;${storeAlbumSpace.totalSpaceM} / ${fns:fy('已使用')}&nbsp;<span id="storePictureSpaceM">${storeAlbumSpace.pictureSpaceM}</span>
									<p><span  id="storePictureSpace" style="width: ${storeAlbumSpace.pictureSpace/storeAlbumSpace.totalSpace*100}%"> </span></p>
								</div>
								<!--dom结构部分 -->
								<div id="uploader-demo" style="display: inline-block;">
								    <div id="fileList" class="uploader-list"></div>
								    <div id="filePicker">${fns:fy('上传图片到这里')}</div>
								</div>
							    <a href="javascript:void(0);" style="margin-right: 5%;margin-top: 2px;" class="sui-btn btn-bordered btn-xlarge btn-primary ml20 mobileBatch"><i class="sui-icon icon-touch-folder-right"></i> ${fns:fy('批量移动')}</a>
								<a href="javascript:void(0);" style="margin-top: 2px;" class="sui-btn btn-bordered btn-xlarge btn-primary ml20 deleteBatch"><i class="sui-icon icon-tb-delete"></i> ${fns:fy('批量删除')}</a>
							 	<a href="javascript:void(0);" style="margin-top: 2px;" class="sui-btn btn-bordered btn-xlarge btn-primary ml20 selectAll" isChecked="0"><i class="sui-icon sui-icon icon-check"></i> ${fns:fy('全选')}</a>
							</div>
						</div>
						<div class="m20 picture-tool" id="picture-tool">
							<div class="cloudsize" id="addFile">
							</div>
						</div>
						<div class="change"></div>
					</div>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>